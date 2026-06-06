
# Minecraft Launcher Recommendations

## Recommended Launchers

### TLauncher
- **Description**: TLauncher is a versatile and user-friendly developer focused launcher that supports a wide range of Minecraft versions and modpacks with a UI similar to the old vanilla launcher like you remember it.
- **Pros**:
  - Supports (.)minecraft folder format of vanilla launcher which is good for developement of modloaders and using less common modloaders or development versions, or clients.
  - Maven Repos with vanilla jars and Minecraft Forge jars.
  - High Quality Developer Console with great logging.
  - Fast and Lightweight
  - Easy to fix issues in JSONs or the launcher itself
  - Easy to decompile
  - Easy to make mods for the launcher itself
  - Jar format makes it easy to run on a wide rangle of computers and if there is some issue with something like JavaFX (Like on MacOS Mojave) it is not too hard to bypass
  - Writes the console contents to a file (Useful for some older crash analysers)
  - Multilingual community
  - TLMods is in multple languages allowing you to read curseforge descriptions in multiple languages and search for mods in your language
  - TLMods does not need a CurseForge API Key
  - TLMods allows you to create TL format modpacks and install curseforge modpacks (Though for creation we recommend the similarly named ATLauncher as it can make CurseForge Packs which eventually make their way to TLMods, TLMods is also not generally the best launcher for premade modpacks compared to Prism Launcher or ATLauncher)
  - Ability to  temp change username for testing username specific stuff or in VMs for suspicous mods without needing it changing the microsoft account.
  - Ability to quickly share logs with SecureLogger, a raw gzipped log sharing API. By default 10MB of GZipped Logs without line limits.
  - Ability to change TL Mods and Secure Logger and other endpoints in the config
  - Custom Cape and High Res Skin Mod
  - Ability to turn off anonymous stats
  - Advanced Security Features and Warnings
  - Built in Crash Analysis
  - TLGuard to warn about problematic CIS servers (which Mojang often lacks in)
  - Mirrors and proxies in Russia to help you get access to the files if the official sources are inaccessible
  - 1 click installs of many modloaders and optifine, but you can easilly install official versions of modloaders, unlike with many launchers
  - Easy to change JVM for a custom one
  - Advanced Custom Args
  - Minecraft Changelog on front
  - The Support channels give you many freedoms not found in other discords.
  - CrashDetector's Dev  has the community guide role in the discord server (though is not connected to the company in any way) so can give you help with Crashes if you ping them. Many of CrashDetector's features such as Crash Analysis, Quick Log Sharing, Link to support channels, the dev console, the custom crash reasons jsons,the refresh button, the open folder button,GUI Config, the user interface, the mods searcher, the skin system, the gpu switcher,and relauncher were influnced by TLauncher who saw TLaunchers userfriendly implementations of advanced developer level features and  saw how well it fit with their goals and learnt from their wisdom and mistakes.

- **Cons**:
  - Do not use the exe or deb versions. Use the Jar version from https://tlauncher.org/jar/ because they are known to be problematic. Only advantage is it has the ability to set the GPU to dedicated but this is easy to do manually by just setting the JVMs (in the runtime folder in the .minecraft folder) and going to the graphics setting and setting it to high performance.
  - Sometimes, especially with older versions of the Launcher, some newer versions of the game or modloaders wont work without slight modification
  - On some versions,especially with the grey TL Logo next to them, TL Adds stuff to the JSONs, many launchers, such as the vanilla launcher do not support these changes and you will likely need to delete that version from the versions folder and reinstall it to use it with another launcher
  - Weaker support for Premade Modpacks compared to many launchers, sometimes the modpacks from CurseForge end up downloading with issues
  - Many people who are not devs do not know that most Mod Development Kits (Except LiteLoader's) do not force usage of a Microsoft Account so call TLauncher a "pirate" launcher even though it is actually far easier to setup TLauncher with a Microsoft account that it is with most Mod Development Kits. Many support channels will shun you for using it.
  - Their competitors who do not like that they have the best and most popular Minecraft Launcher worked with journalists to create a bunch of fake malware videos which convinced a lot of people that it was malware. Most examples did not show any code at all and the single case I saw that did was out of context. None of the ones we saw were from known security research firms and almost all of the falsified evidence was only on Windows (and mostly from the exe installer which I already said you should not use), we expect most developers to be using Mac/BSD, Linux, or SysV, all the common issues I hear about such as it changing the hosts file (due to issues connecting to auth) and MinecraftUIListener.executeFix (Which exists to call netsh winsock reset to fix an issue with networking and failing to connect to servers with netty) and checking for Windows Updates (KB4515384 was widely known to cause lots of problems) and playing with registry keys (to do things like get hardware information and use the correct GPU) exist to fix issues with Windows and thats why you do not see equivelants in UNIX, there is almost no reason to use Windows for Minecraft Java Edition anyhow, almost every computer capable of running Windows is capable of running Linux, Minecraft is optimised for Linux much more so than Windows, the distro we recommened for Most players is either Fedora (fast moving and good for games) or Red Hat Enterprise Linux 9 (A nice stable distro which lots of enterprise packages build against). You can use FabricMC's fork of CFR or the version of CFR from your Operating System's package manager to decompile the jar files accociated with TLauncher as well as view all the config files.
- **Image**: ![TLauncher](https://tlauncher.org/assets/images/front_page/settings_versions_en_2_0_v1.png) 

### Prism Launcher
- **Description**: Prism Launcher is ideal for those who prefer premade modpacks or have problematic hardware configurations.
- **Pros**:
  - Somewhat lightweight
  - Preconfigured modpacks for easy setup from a wide range of providers.
  - Strong community support and regular updates.
  - The custom bootstrap allows for the usage of problematic hardware or software. Fewer driver, smash stacking,or Apple Silicon related issues.
  - The custom bootstrap also changes how mods behave, which can sometimes work in your favour.
  - Lots of Dev tools built in such as VisualVM support
  - Advanced instance format
  - Advanced Args and Agents support
  - Easily change and manage multiple custom VMs
  - Nice Mod Search Tools
  - Based on PolyMC which is based on MultiMC
  - Open Source
  - Has Cat
  - Has Dark Theme
  - Openly pro LGBTQIA+
  - Allows for having more than 1 username without chaning the username on the Microsoft Account, similar to TLauncher
  - Good Dev Console
- **Cons**:
  - Limited to premade modpacks, less flexibility for custom setups as it does not support the traditional (.)minecraft folder and the bootstrap may get in the way of some custom content. Though it is often possible to get what you want working with a little config work, Prism has a strong config system.
  - While the custom bootstrap may help some mods, some mods may have a harder time,especially if they need access to the args. Also if you are testing a mod or a modpack or modloader (especially with modloaders) a problem that will exist on most other launchers will not happen on MMC/Poly/Prism meaning that you may not be aware of many issues plauging your product.
  - Interface may be less intuitive for some users.

- **Image**: ![Prism Launcher](https://prismlauncher.org/_astro/linux_light.jDSJ-320_Z2wzXS8.png) 

## Mid Launchers

### Official Minecraft Launcher
- **Description**: The official launcher provided by Mojang for Minecraft.
- **Pros**:
  - Supports all most Minecraft versions (with exceptions with "lost" versions which the community were later able to recover).
  - Many modloaders and mods develop against this, this launcher is largelly considered to be the reference launcher. The (.)minecraft folder format is targetted by most modloaders and custom clients.
  - Regular updates directly from Mojang.
- **Cons**:
  - Limited modding support without additional tools.
  - Lack some advanced features found in third-party launchers.
  - Its handling of Microsoft Accounts is ironically bad, if often get logged out or have issues signing in or playing the game even when you are already signed in, especially when offline.
  - It is very slow and buggy in new versions
  - The console is a mess in new versions. System.out.println and System.err.println often do not show up, especially on Linux
  - Chromium Based
  - The GUI in new versions is a mess
  - The old versions of the launcher from around 2015 were pretty nice, but soon after that the launcher got UI overhauls which added a whole load of issues
- **Image**: ![Official Launcher](https://store-images.s-microsoft.com/image/apps.58166.14247769038588514.a7fa20d7-bc1c-464b-bf78-16dfcd742fe5.951d56d7-284a-4e70-9f44-ed125a7e3fa3?q=90&w=320&h=180) **

### ATLauncher
- **Description**: A popular launcher known for its extensive collection of modpacks and tools for modpack developers, almost like an unofficial TLauncher of Modpacks.
- **Pros**:
  - Easy and reliable installation of modpacks, this is the launcher supported by many Modpack Companies such as Luna Pixel Studios due to many other launchers having corrupt game or mod installs.
  - Supports custom modpack creation and exporting in many different formats, especially on Linux.
  - Regular updates and community support.
  - Quite Customisable
  - Has Jar version
  - Easy to decompile
  - Easy to Mod the launcher itself
- **Cons**:
  - Interface can be overwhelming for new users.
  - We have noticed a few stability issues, especially on Linux
  - Its instance format is more complex and less advanced than Prism Launcher's making it harder to support custom modloaders and clients.
  -  Limited to premade modpacks, less flexibility for custom setups as it does not support the traditional (.)minecraft folder
- **Image**: ![ATLauncher](https://flathub.org/api/appOgImage/com.atlauncher.ATLauncher?locale=en) **

### TLLegacy/Legacy Launcher
- **Description**: A more lightweight version of TLauncher
- **Pros**:
  - Supports legacy Minecraft versions.
  - Simple and lightweight.
  - Good for nostalgia or specific modding projects.
  - Open Source
  - Many (but not all) of the things TLauncher has (But a worse GUI in our opion and less focused on Modding)
- **Cons**:
  - Missing a lot of things from TLauncher
  - We dont like the GUI
  - Many of the same issues as TLauncher as well
- **Image**: ![TLLegacy](https://imgproxy.flathub.org/insecure/dpr:1/f:webp/rs:fill-down/aHR0cHM6Ly9kbC5mbGF0aHViLm9yZy9tZWRpYS9jaC90bGF1bi9UTC83NzRiYThjMmFmOTA1YjFjNDBjOTUzODliNjM2MzFhOS9zY3JlZW5zaG90cy9pbWFnZS0xX29yaWcucG5n) 

### CurseForge App (with Mojang Launcher)
- **Description**: CurseForge's official app and modpack maker
- **Pros**:
  - Official from Curseforge, strong integration with CurseForge and does not need the 3rd party downloads enabled on the mods to use those mods.
  - Gives money to the creators unlike most other 3rd party launchers when you install mods through them, this means many modpack creators will ask you to either use this or the Modrinth App
- **Cons**:
  - Do not use the Overwolf version
  - More telemetry than other launchers
  - AutoUpdating even if your version of MacOS does not support the new version
  - More rigid than most other launchers
  - Despite going through the Mojang Launcher it does not have support for the (.)minecraft folder format.
  - Slow,especically on limited GPU resources
  - If you have a massive modpack with thousands of mods it can prevent the Cruseforge app from showing any of the modpacks you have.
  - The installs have been known to be corrupt a lot
  - Eats a lot of resources and runs in the background a lot
  - Has 2 consoles, one in CurseForge itself, one in the Mojang Launcher, The one on CurseForge is missing a lot of info, notable System.out and System.err
  - By default in new version the Skip Launcher Setting is used
- **Image**: ![CursedForge](https://console-apps.overwolf.com/prod/apps/cchhcaiapeikjbdbpfplgmpobbcdkdaphclbmkbj/assets/0b10dd56-c32d-47d2-b144-5e66064f5336.webp) 


*(Note: Most other launchers not specifically mentioned fall into this mid-tier category based on their general features and user feedback.)*

## Condemned Launchers

### Modrinth App (Theseus)
- **Description**: While Modrinth is a great mod repository, its app has issues with stability and support.
- **Pros**:
  - Access to a wide range of mods through Modrinth repository.
  - Gives money to the creators unlike most other 3rd party launchers when you install mods through them, this means many modpack creators will ask you to either use this or the CurseForge App
  - A fork called Astralrinth fixes some of the security issues by default
  - The GUI has a modern feel to it
  - Open Source
  - You can turn off much of the telemetry
- **Cons**:
  - The UI has been known to have some bugs in it
  - Limited support for MCForge in the past.
  - Mixed user feedback and community support.
  - We had issues getting it to work on RHEL9
  - Not good privacy policy https://modrinth.com/legal/privacy
  - It has a custom bootstrap, unknown effects
  - No Support for CurseForge packs

- **Image**: ![Modrinth App](https://cdn-raw.modrinth.com/app-landing/cobblemon-launcher.webp) *(Replace with actual image URL)*

### CurseForge App with Skip Launcher
- **Description**: The CurseForge app is known for its mod repository, but the skip launcher option is problematic.
- **Pros**:
  - Most of the advantages of CurseForge app but now you do not need to deal with the awful vanilla launcher
- **Cons**:
  - Same issues as with Mojang Launcher
  - There is no more vanilla launcher console, only the one built into CurseForge which as mentioned before does not have SysOut and SysErr which is needed for many crashes. It does not save this to a file either so this info is basically lost, in order to get this info you will need to use a tool like ProxySysOutSysErr in CrashDetector or some alternative.
  - On some mods the game either crashes for unknown reasons or loads forever for some reason (especially with Dawn of Berk modpack). Though this is either inconsistant or only on Windows as we were not able to reproduce these ourselves, but know it is a common issue.
- **Image**: ![CursedForge](https://console-apps.overwolf.com/prod/apps/cchhcaiapeikjbdbpfplgmpobbcdkdaphclbmkbj/assets/0b10dd56-c32d-47d2-b144-5e66064f5336.webp)
### SKLauncher
- **Description**: A common launcher with lots of issues
- **Pros**:
  - It is considered in some ways lightweight
  - Strong modding capabilities
  - Support for traditional (.)minecraft folder
  - Has Jar version
  - Easy to decompile
  - Easy to Mod the launcher itself 

- **Cons**:
  - It adds an agent which may possibly conflict with other agents
  - I often had issues getting it to open, especially on RHEL9.
  - Ive seen many issues with it not installing mods,modpacks, or the game correctly.
  - No support for CurseForge packs
  - As a cracked launcher many support channels will shun you for using it.
- **Image**: ![SKLauncher](https://skmedix.pl/images/hero.1.svg) *(Replace with actual image URL)*

### PvP Clients (Lunar Client, Badlion, WaterClient, Feather Client, LabyMod, NoRisk, etc.)
- **Description**: These clients often are designed as premade modpacks with custom versions of the mods.
- **Pros**:
  - Optimised for PvP gameplay with various enhancements.
  - Has better integration than using the mods normally in some cases
- **Cons**:
  - Security risks and potential bans on servers that do not allow them.
  - Performance issues and bugs specific to PvP clients.
  - Limited support for non-PvP gameplay and modding.
  - They often are similar to premade modpacks but with custom versions of many of the mods. Many of them are old versions and replacing or disabling them causes issues
  - Often many mods conflict with these clients setups, there are many crashes involved with clients like FeatherClient and Lunar Client
  - Many are obfuscated and have custom bootstraps which change a lot
- **Image**: ![PvP Clients](https://a.storyblok.com/f/150231/994x665/2ac26d0a6b/lunar-client-mods.png) *(Replace with actual image URL)*

### FreshCraft
- **Description**: A relatively new launcher that has not gained much positive feedback. It failes to do things like setting up the assets folder.
- **Pros**:
  - None significant compared to established launchers.
- **Cons**:
  - Unstable and prone to crashes.
  - Limited features and modpack support.
  - Poor community support and lack of updates.
  - Windows Only, exe, closed source, hard to decompile
- **Image**: ![FreshClient](https://i.ytimg.com/vi/CYZj8uCtU3I/maxresdefault.jpg) *(Replace with actual image URL)*

### Flatpak Version of TLLegacy/Legacy Launcher
- **Description**: The Flatpak version of TLLegacy has additional issues compared to its standalone counterpart.
- **Pros**:
  - Access to TLLegacy features through Flatpak platform.
- **Cons**:
  - Performance degradation due to Flatpak overhead.
  - Dev console and many versions do not work
  - Lack of updates and support compared to standalone version.
  - A lot of changes and non working things
- **Image**: ![Flatpak TLLegacy](https://example.com/flatpak-tllegacy-image.png) *(Replace with actual image URL)*

### LauncherFenix
- **Description**: A launcher that has faced criticism for its outdated features and poor performance.
- **Pros**:
  - None significant compared to modern launchers.
- **Cons**:
  - Outdated user interface and design.
  - Limited modpack support and features.
  - Frequent crashes and bugs reported by users.
- **Image**: ![LauncherFenix](https://example.com/launcherfenix-image.png) *(Replace with actual image URL)*

### Crystal Launcher
- **Description**: A launcher that has not gained much traction due to its various issues.
- **Pros**:
  - None significant compared to other available launchers.
- **Cons**:
  - Poor performance and stability issues.
  - Limited features and modpack support.
  - Lack of community support and regular updates.
- **Image**: ![Crystal Launcher](https://example.com/crystal-launcher-image.png) *(Replace with actual image URL)*

### Battly Launcher
- **Description**: A launcher that has faced significant criticism for its performance and security.
- **Pros**:
  - None significant compared to reputable launchers.
- **Cons**:
  - Frequent security vulnerabilities and potential for malware.
  - Poor performance on most systems and configurations.
  - Lack of regular updates and community support.
- **Image**: ![Battly Launcher](https://example.com/battly-launcher-image.png) *(Replace with actual image URL)*

### Titan Launcher
- **Description**: A launcher known for its association with pirated content and security risks.
- **Pros**:
  - None, as it is generally not recommended due to serious concerns.
- **Cons**:
  - High risk of malware and viruses.
  - Potential bans on official servers and communities.
  - Poor performance and stability issues.
- **Image**: ![Titan Launcher](https://example.com/titan-launcher-image.png) *(Replace with actual image URL)*

*(Note: The images provided are placeholders and should be replaced with actual URLs pointing to relevant images of each launcher.)*

