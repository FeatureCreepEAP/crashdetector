# Using VisualVM to Diagnose Modpack Lag

VisualVM's CPU Sampler is one of the best tools for solving lag in modpacks. It connects directly to the JVM to gather information and works on almost any modloader, launcher, or version **without requiring you to install any mods in your pack**. VisualVM is comparable to Spark or CDSampler/CDProfiler, but it is much more powerful and was historically part of the official JDK.

## Getting Started

To get started, download VisualVM from here: [https://visualvm.github.io/download.html](https://visualvm.github.io/download.html)

You will need a **JDK** (not just a JRE) installed on your system to run it. Once you have downloaded and extracted the zip file, navigate to the `bin` folder and run `visualvm`.

## Connecting to the Game

In this example, the game is frozen at the main menu because it is too laggy to even press a button—actually playing the game would be even worse. We need to work out exactly why it is so slow, so we will attach VisualVM to the game process.

![Lag screen and selected process](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/bad_screen_perf.png)

I have selected the correct process on the left side. **`cpw.mods.bootstraplauncher.Bootstrap`** is the correct entrypoint class for ModLauncher/MinecraftForge/NeoForge on 1.20.1 for most launchers. 

> **Note:** If you are using a different modloader or version, you will have a different entrypoint class and will need to select that instead (it is normally quite easy to work out). Some launchers—notably **MultiMC, PolyMC, PrismLauncher, ATLauncher, Modrinth App, and PvP clients**—will have their own wrapper classes, so you will need to select the wrapper instead.

Once you double-click the correct process, you will be brought to an overview screen. Select the **Sampler** tab, and then select **CPU**.

![Sampler Screen](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/cpusample.png)

You will then see a list of running threads. You should sort them by **Total Time (CPU)** by clicking on that column header.

![Threads](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/threads.png)

## Identifying the Problematic Threads

Most of the time, you only care about two main threads:
*   **Render Thread** (responsible for visual performance and FPS)
*   **Server Thread** (responsible for TPS and server/world lag)

While other background threads *can* be problematic if they act as blockers, in this case, none of them are:
*   `ProxySysOutSysErr-Escritor` is a log printer for CrashDetector and does not block anything.
*   `DefaultDispatcher-worker-11` is the Essential Discord Integration and does not block anything.
*   The `SimpleVoiceChat` threads are also not blocking anything.
*   The rest are not taking up any significant amount of time.

In our case, the **Server Thread** is not taking up much time, so it is fine. The **Render Thread** is where our problem lies.

## Analysing the Hotspots

Looking at the Render Thread, we can see that the vast majority of the slowdown comes from three methods:
*   `net.minecraft.client.renderer.GameRenderer.m_109093_()` — **1,015,241 ms (33.9%)**
*   `net.minecraft.client.Minecraft.m_91398_()` — **905,544 ms (30.1%)**
*   `com.mojang.blaze3d.systems.RenderSystem.limitDisplayFPS()` — **561,413 ms (18.6%)**

### 1. Xaero's Minimap (Poor Scaling)
![Xaero breakdown](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/xaero.png)

Drilling down into `m_109093_()`, we find `preTick` and `postTick` events. Under `preTick`, you will notice that on the main Render Thread, **Xaero's Minimap loops through *all* entities to check if they should be visible on the map.** This causes a massive **12% slowdown** because it does not scale well. When you have hundreds of mods—specifically mods that also look through the Curios API and entity capabilities—this single-threaded check becomes a massive bottleneck. 

For context, JourneyMap (which is also installed) runs this check on a separate thread and does not take up nearly as much time. Xaero's approach simply does not scale well. Whilst it might be fine without Curios and with fewer entities, in a 900-mod list, it breaks down.

### 2. Immersive Portals & MCreator "Slop"
![Immersive Portals post tick](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/immersiveportalstick.png)
![Immersive Portals game render](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/immersiveportalspixelprotect.png)

Another major issue is **Immersive Portals**, which constantly injects itself into other mods' processes. In fact, the developers even have a warning in chat stating that the mod does not run well when a lot of mods are installed. 

As seen in the profiler, Immersive Portals is heavily involved in problems under `net.minecraft.client.Minecraft.m_91383_()` via frequent `postTick` calls. They are also deeply embedded in the `gameRender` method, where their redirects frequently trigger **Pixel Protection's procedures**, which pop up in other places as well. **Pixel Protection's Player Render Procedures alone are taking up over 12% of the total time—likely nearing 25% of the Render Thread's time.** 

This is a prime example of poorly optimised MCreator code. This is the exact same mod that notoriously implemented BrightSDK back in the day. If we look at other hotspots, we will see that many of the worst-performing mods are MCreator mods. They often lack correct caching and multithreading practices, leaving everything on the current thread. This might be fine for one or two mods, but it does not scale—especially when executed every single tick alongside hundreds of other mods.

### 3. More MCreator Inefficiency
![MCreator performance](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/mcreatorperf.png)

As further proof, the image above shows **just two** MCreator mods consuming **4% of the total CPU time** in a pack with 900 mods. That is a disproportionately massive performance hit for just two mods.

### 4. KubeJS Overhead
![KubeJS performance](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/kubejsperf.png)

Even worse, some mods are calling **KubeJS scripts** on the main thread, eating up another **5% of the time**. I did not even write these scripts; another mod is leveraging KubeJS to do things, which is an incredibly inefficient way to handle procedures.

### 5. FPS Reducer
![FPS Reducer performance](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/fpsreducer.png)

Most of the slowdown attributed to `limitDisplayFPS` is coming from the mod **FPS Reducer**. This mod likely makes the underlying issues *worse*, as it tries to artificially limit frames that are already being dragged down by the other bottlenecks mentioned above. You do not need this mod in most cases.

## Conclusion

By simply looking through the KubeJS hooks, removing (or writing patches for) **Immersive Portals**, **Xaero's Maps**, **FPS Reducer**, **Pixel Protection**, and a few other MCreator mods, **we would gain back roughly half of our total performance**. Making these changes would likely allow us to get past the frozen main menu screen so we can run further tests in-game.
