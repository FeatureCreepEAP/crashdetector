# Uso de VisualVM para diagnosticar lag en Modpacks

El muestreador de CPU (CPU Sampler) de VisualVM es una de las mejores herramientas para solucionar el lag en los modpacks. Se conecta directamente a la JVM para recopilar información y funciona en casi cualquier modloader, launcher o versión **sin requerir que instales mods en tu pack**. VisualVM es comparable a Spark o CDSampler/CDProfiler, pero es mucho más potente y anteriormente formaba parte del JDK oficial.

## Primeros pasos

Para empezar, descarga VisualVM desde aquí: [https://visualvm.github.io/download.html](https://visualvm.github.io/download.html)

Necesitarás tener un **JDK** (no solo un JRE) instalado en tu sistema para poder ejecutarlo. Una vez que hayas descargado y extraído el archivo zip, ve a la carpeta `bin` y ejecuta `visualvm`.

## Conectándose al juego

En este ejemplo, el juego está congelado en el menú principal porque tiene demasiado lag como para siquiera presionar un botón; obviamente, jugarlo sería aún peor. Necesitamos saber exactamente por qué está tan lento, así que vamos a conectar VisualVM al proceso del juego.

![Pantalla de lag y proceso seleccionado](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/bad_screen_perf.png)

He seleccionado el proceso correcto en el lado izquierdo. **`cpw.mods.bootstraplauncher.Bootstrap`** es la clase de entrada (entrypoint) correcta para ModLauncher/MinecraftForge/NeoForge en la versión 1.20.1 en la mayoría de los launchers.

> **Nota:** Si estás usando un modloader o versión diferente, tendrás una clase de entrada distinta y tendrás que seleccionar esa en su lugar (normalmente es muy fácil deducir cuál es). Algunos launchers—notablemente **MultiMC, PolyMC, PrismLauncher, ATLauncher, Modrinth App y clientes PvP**—tendrán sus propias clases *wrapper*, por lo que tendrás que seleccionar el *wrapper* en lugar del *bootstrap*.

Una vez que hagas doble clic en el proceso correcto, llegarás a una pantalla de resumen. Selecciona la pestaña **Sampler** y luego haz clic en **CPU**.

![Pantalla del Sampler](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/cpusample.png)

Entonces verás una lista de hilos (*threads*) en ejecución. Debes ordenarlos por **Total Time (CPU)** haciendo clic en el encabezado de esa columna.

![Hilos](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/threads.png)

## Identificando los hilos problemáticos

La mayor parte del tiempo, solo te interesan dos hilos principales:
*   **Render Thread** (Hilo de renderizado, responsable del rendimiento visual y los FPS)
*   **Server Thread** (Hilo del servidor, responsable del TPS y el lag del mundo/servidor)

Aunque otros hilos en segundo plano *pueden* ser problemáticos si actúan como bloqueadores, en este caso ninguno lo es:
*   `ProxySysOutSysErr-Escritor` es un impresor de registros (logs) de CrashDetector y no bloquea nada.
*   `DefaultDispatcher-worker-11` es la integración con Discord de Essential y no bloquea nada.
*   Los hilos de `SimpleVoiceChat` tampoco están bloqueando nada.
*   El resto no está consumiendo una cantidad de tiempo significativa.

En nuestro caso, el **Server Thread** no está ocupando mucho tiempo, así que está bien. El **Render Thread** es donde radica nuestro problema.

## Analizando los puntos críticos (Hotspots)

Al mirar el Render Thread, podemos ver que la gran mayoría de la desaceleración proviene de tres métodos:
*   `net.minecraft.client.renderer.GameRenderer.m_109093_()` — **1,015,241 ms (33.9%)**
*   `net.minecraft.client.Minecraft.m_91398_()` — **905,544 ms (30.1%)**
*   `com.mojang.blaze3d.systems.RenderSystem.limitDisplayFPS()` — **561,413 ms (18.6%)**

### 1. Xaero's Minimap (Mala escalabilidad)
![Desglose de Xaero](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/xaero.png)

Al profundizar en `m_109093_()`, nos encontramos con los eventos `preTick` y `postTick`. En el `preTick`, notarás que, en el hilo principal de Renderizado, **Xaero's Minimap recorre *todas* las entidades para verificar si deberían ser visibles en el minimapa.** Esto causa una masiva **desaceleración del 12%** porque no escala bien. Cuando tienes cientos de mods—específicamente mods que también revisan el API de Curios y las capacidades de las entidades—esta verificación en un solo hilo se convierte en un cuello de botella enorme.

Como contexto, JourneyMap (que también está instalado) realiza esta verificación en un hilo separado y no consume ni de cerca tanto tiempo. El enfoque de Xaero simplemente no escala bien. Aunque podría funcionar sin Curios y con menos entidades, en una lista de 900 mods, colapsa.

### 2. Immersive Portals y la "basura" de MCreator
![Immersive Portals post tick](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/immersiveportalstick.png)
![Immersive Portals game render](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/immersiveportalspixelprotect.png)

Otro problema importante es **Immersive Portals**, el cual se inyecta constantemente en los procesos de otros mods. De hecho, los propios desarrolladores tienen una advertencia en el chat que dice que el mod no funciona bien cuando hay muchos mods instalados.

Como se ve en el perfilador, Immersive Portals está muy involucrado en problemas dentro de `net.minecraft.client.Minecraft.m_91383_()` mediante frecuentes llamadas `postTick`. También están profundamente incrustados en el método `gameRender`, donde sus redirecciones frecuentemente activan los **procedimientos de Pixel Protection**, que también aparecen en otros lugares. **Los procedimientos de renderizado de jugador de Pixel Protection por sí solos están consumiendo más del 12% del tiempo total—probablemente acercándose al 25% del tiempo del Render Thread.**

Este es un ejemplo perfecto de código de MCreator mal optimizado. Es exactamente el mismo mod que implementó notoriamente el BrightSDK en su época. Si observamos otros puntos críticos, veremos que muchos de los mods con peor rendimiento son mods de MCreator. A menudo carecen de prácticas correctas de almacenamiento en caché (*caching*) y multihilo, dejando todo en el hilo actual. Esto podría ser aceptable para uno o dos mods, pero no escala—especialmente cuando se ejecuta en cada *tick* junto con cientos de otros mods.

### 3. Más ineficiencia de MCreator
![Rendimiento de MCreator](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/mcreatorperf.png)

Como prueba adicional, la imagen de arriba muestra **solo dos** mods de MCreator consumiendo el **4% del tiempo total de CPU** en un pack de 900 mods. Ese es un impacto de rendimiento desproporcionadamente masivo para solo dos mods.

### 4. Sobrecarga de KubeJS
![Rendimiento de KubeJS](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/kubejsperf.png)

Incluso peor, algunos mods están llamando **scripts de KubeJS** en el hilo principal, devorando otro **5% del tiempo**. Ni siquiera escribí estos scripts; otro mod está usando KubeJS para hacer las cosas, lo cual es una forma increíblemente ineficiente de manejar procedimientos.

### 5. FPS Reducer
![Rendimiento de FPS Reducer](https://github.com/FeatureCreepEAP/crashdetector/blob/main/src/main/resources/imagenes/fpsreducer.png)

La mayor parte de la desaceleración atribuida a `limitDisplayFPS` proviene del mod **FPS Reducer**. Este mod probablemente hace que los problemas subyacentes sean *peores*, ya que intenta limitar artificialmente los cuadros (frames) que ya están siendo arrastrados por los demás cuellos de botella mencionados anteriormente. No necesitas este mod en la mayoría de los casos.

## Conclusión

Con solo revisar los enlaces de KubeJS, y eliminar (o escribir *patches* para) **Immersive Portals**, **Xaero's Maps**, **FPS Reducer**, **Pixel Protection** y algunos otros mods de MCreator, **recuperaríamos aproximadamente la mitad de nuestro rendimiento total**. Hacer estos cambios probablemente nos permitiría pasar de la pantalla congelada del menú principal para poder realizar más pruebas dentro del juego.
