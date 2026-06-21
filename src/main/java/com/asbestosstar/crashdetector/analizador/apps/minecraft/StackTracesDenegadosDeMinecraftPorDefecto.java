package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;

public class StackTracesDenegadosDeMinecraftPorDefecto {

	public static void init() {

		VerificacionDeStackTrace.agregarDenegadoContiene("Preparing crash report with UUID"); // Excluir
		// líneas
		// que
		// contienen
		// "Preparing
		// crash
		// report
		// with
		// UUID"

		VerificacionDeStackTrace.agregarDenegadoContiene("Failed to complete lifecycle event");
		VerificacionDeStackTrace.agregarDenegadoContiene("Crash report saved to");
		VerificacionDeStackTrace.agregarDenegadoContiene("Mod Loading has failed");

		VerificacionDeStackTrace.agregarDenegadoContiene(
				"Could not determine mod trust worthiness, Assuming Jar was downloaded from trusted source!");// FUCK
		// STOPMODREPOSTS

		VerificacionDeStackTrace.agregarDenegadoContiene("org.watermedia.videolan4j.discovery.providers");

		VerificacionDeStackTrace.agregarDenegadoContiene("libflite.so");// TTS no fatal y es
// comun en TL linux
// y mac. TODO
// agregar una
// verificacion para
// este

		VerificacionDeStackTrace.agregarDenegadoContiene("com.mojang.text2speech");// TTS no
// fatal y
// es comun
// en TL
// linux y
// mac. TODO
// agregar
// una
// verificacion
// para este

		VerificacionDeStackTrace.agregarDenegadoContiene("Error during pre-loading phase");// Incluir líneas que
		// sean exactamente
		// "Stacktrace:" o
		// contengan
		// "Stacktrace:"

		VerificacionDeStackTrace.agregarDenegadoContiene("Failed to initialize mod containers");// Incluir líneas
		// que sean
		// exactamente
		// "Stacktrace:"
		// o contengan
		// "Stacktrace:"

		VerificacionDeStackTrace
				.agregarDenegadoContiene("de.markusbordihn.modsoptimizer.data.JsonFileParser.parseJson");// No
		// es
		// fatal

		VerificacionDeStackTrace.agregarDenegadoContiene("RealmsServiceException");
		VerificacionDeStackTrace.agregarDenegadoContiene("java.net.UnknownHostException: api.modrinth.com");
		VerificacionDeStackTrace.agregarDenegadoContiene("Unreported exception thrown!");

		VerificacionDeStackTrace.agregarDenegadoContiene(
				"See https://github.com/google/gson/blob/main/Troubleshooting.md#malformed-json");// TODO
		// verificar
		// si
		// esta
		// siempre
		// bien

		VerificacionDeStackTrace
				.agregarDenegadoContiene("https://vazkiimods.github.io/Patchouli/docs/upgrading/upgrade-guide-120");// Creo
																													// no
																													// es
		// fatal

		VerificacionDeStackTrace.agregarDenegadoContiene("Not a JSON Object");
		VerificacionDeStackTrace.agregarDenegadoContiene("Server is unreachable (ICE failed)");// essential

		VerificacionDeStackTrace.agregarDenegadoContiene("patreon");// Si hay una problema
// puedo eliminar

		VerificacionDeStackTrace.agregarDenegadoContiene("net.minecraftforge.fml.VersionChecker");// Si hay una problema
		// puedo eliminar

		VerificacionDeStackTrace.agregarDenegadoContiene("net.minecraftforge.fml.VersionChecker");// Si hay una problema
		// puedo eliminar

		VerificacionDeStackTrace.agregarDenegadoContiene("net.neoforged.fml.VersionChecker");

		VerificacionDeStackTrace.agregarDenegadoContiene("No bonuses were provided");// apotheosis
// Si
// hay
// una
// problema
// puedo
// eliminar

		VerificacionDeStackTrace
				.agregarDenegadoContiene("rg.betterx.bclib.client.models.CustomModelBakery.addItemModel");// Puedo
// cambiar
// si

		VerificacionDeStackTrace.agregarDenegadoContiene("com.sonicether.soundphysics.SoundPhysicsMod.CONFIG");// Tenemos
		// ErrorSistemaSonido

		VerificacionDeStackTrace
				.agregarDenegadoContiene("umpaz.farmersrespite.common.crafting.KettlePouringRecipe$Serializer");// Tenemos

		VerificacionDeStackTrace.agregarDenegadoContiene(
				"java.lang.NullPointerException: Cannot invoke \"net.mehvahdjukaar.polytone.utils.CompoundReloader.earlyProcess(net.minecraft.server.packs.resources.ResourceManager)\" because \"net.mehvahdjukaar.polytone.Polytone.COMPOUND_RELOADER\" is null");// nada

//Calio MultiJsonDataLoader errores de carga de datos (Data loaders)
		VerificacionDeStackTrace.agregarDenegadoContiene("io.github.apace100.calio.data.MultiJsonDataLoader");

//Errores de sintaxis JSON por items desconocidos (recetas/loottables de mods
//quitados) (¿posible eliminar?)
		VerificacionDeStackTrace.agregarDenegadoContiene("com.google.gson.JsonSyntaxException: Unknown item");

//Errores de recetas con tipos inválidos o no soportados (ej. mods de datos
//quitados)(¿posible eliminar?)
		VerificacionDeStackTrace
				.agregarDenegadoContiene("com.google.gson.JsonSyntaxException: Invalid or unsupported recipe type");

//Errores de parseo matemático/sintaxis de mclib (común en animaciones/modelos,
//usualmente no fatal)
		VerificacionDeStackTrace.agregarDenegadoContiene("com.eliotlash.mclib.math.MathBuilder.parseSymbols");

//Ignorar referencias internas de Connector/Authlib
		VerificacionDeStackTrace.agregarDenegadoContiene("connector$authlib");

		VerificacionDeStackTrace
				.agregarDenegadoContiene("com.tiviacz.travelersbackpack.TravelersBackpack.readOldCommonConfig");

//Ignorar compatibilidad JMI ↔ FTB Chunks (no suele ser fatal)
		VerificacionDeStackTrace.agregarDenegadoContiene("me.frankv.jmi.compat.ftbchunks.FTBChunksCompat");

//Ignorar JMI ModCompatFactory (compatibilidad de mods, usualmente no fatal)
		VerificacionDeStackTrace.agregarDenegadoContiene("me.frankv.jmi.api.ModCompatFactory");

		VerificacionDeStackTrace.agregarDenegadoContiene("Interface mixin contains a non-public method");// Comun
		// positiva
		// falsa con
		// SpongeMixin,
		// no es fatal y
		// no se puede
		// arreglar

		VerificacionDeStackTrace.agregarDenegadoContiene("WaterMediaBinaries");// https://github.com/WaterMediaTeam/watermedia-v3/blob/77d7f4635d417b9b8e35907d966029fcd249c6fa/src/main/java/org/watermedia/api/media/players/FFMediaPlayer.java#L1341

		VerificacionDeStackTrace
				.agregarDenegadoContiene("com.seibel.distanthorizons.core.level.DhClientServerLevel.isRendering");

		VerificacionDeStackTrace
				.agregarDenegadoContiene("foundry.veil.impl.client.render.dynamicbuffer.VanillaShaderCompiler");

		VerificacionDeStackTrace.agregarDenegadoContiene("foundry.veil.impl.resource.VeilResourceManagerImpl");

//Dreamtinker: fluidos no registrados en JSON (problema de datos, normalmente
//no fatal)
		VerificacionDeStackTrace.agregarDenegadoContiene(
				"com.google.gson.JsonSyntaxException: Unable to parse fluid as registry minecraft:fluid does not contain ID dreamtinker:");

//GeckoLib: MolangParser como cascada de animaciones/modelos
		VerificacionDeStackTrace.agregarDenegadoContiene("software.bernie.geckolib.core.molang.MolangParser");

//QuickPack: lectura rápida de recursos/pack, cascada de resource loading
		VerificacionDeStackTrace.agregarDenegadoContiene("me.drex.quickpack.packs.FastFilePackResources");

//Cold Sweat: registry/config loading durante inicialización
		VerificacionDeStackTrace
				.agregarDenegadoContiene("com.momosoftworks.coldsweat.config.ConfigLoadingHandler$CreateRegistries");

		VerificacionDeStackTrace.agregarDenegadoTodos("Stacktrace:");// Incluir
		// líneas
//que sean
//exactamente
//"Stacktrace:"
//o contengan
//"Stacktrace:"

//como inicio
		VerificacionDeStackTrace.agregarDenegadoTodos("Stacktrace:");

		VerificacionDeStackTrace.agregarDenegadoTodos("Unable to create Lookup for", "optifine.");// Comun
//problema,
//pero
//no
//creo
//es
//fatal

		VerificacionDeStackTrace.agregarDenegadoTodos("Unable to create custom", "optifine.");// Comun
//problema,
//pero
//no
//creo
//es
//fatal

		VerificacionDeStackTrace.agregarDenegadoUnaLinea("com.google.gson.JsonObject");/// Creo no es fatal pero puedo
																						/// ser
/// incorrecto

		VerificacionDeStackTrace.agregarDenegadoTodos("java.io.FileNotFoundException", "minecraft");// Comun
//problema,
//pero
//no
//creo
//es
//fatal

		VerificacionDeStackTrace.agregarDenegadoCualquiera(
				"vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe$Serializer.fromJson",
				"vectorwing.farmersdelight.common.crafting.CookingPotRecipe$Serializer.readIngredients");

		VerificacionDeStackTrace.agregarDenegadoTodos("tacz", "Couldn't parse data file", "from");

//Ignorar errores internos de carga y caché de GeckoLib
		VerificacionDeStackTrace.agregarDenegadoCualquiera("software.bernie.geckolib.cache.",
				"software.bernie.geckolib.loading.");

//Ignorar errores de escaneo y preparación de datos de TACZ (usualmente no
//fatales)
		VerificacionDeStackTrace.agregarDenegadoTodos("com.tacz.guns.util.ResourceScanner.scanDirectory",
				"com.tacz.guns.resource.manager.JsonDataManager.prepare");

//Ignorar errores de AllTheLeaks relacionados con Iron's Spellbooks (problema
//conocido y controlado)
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"java.lang.NoClassDefFoundError: io/redspace/ironsspellbooks/entity/mobs/dead_king_boss/DeadKingMusicManager",
				"alltheleaks");

//Ignorar errores de carga de clase genéricos para Iron's Spellbooks
//(DeadKingMusicManager)
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"java.lang.ClassNotFoundException: io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingMusicManager",
				"cpw.mods.cl.ModuleClassLoader.loadClass", "java.lang.ClassLoader.loadClass");

//Ignorar NPE de ServerLevel donde la instancia es null (error de estado
//interno)
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"java.lang.NullPointerException: Cannot invoke \"net.minecraft.server.level.ServerLevel.m_6857_()\" because \"serverlevel2\" is null");

//Ignorar errores de inicialización relacionados con AllTheLeaks
		VerificacionDeStackTrace.agregarDenegadoTodos("java.lang.ExceptionInInitializerError",
				"dev.uncandango.alltheleaks.leaks.IssueManager");

//Ignorar errores de VarHandler nulo causados por AllTheLeaks (reflexión)
		VerificacionDeStackTrace.agregarDenegadoTodos("java.lang.RuntimeException: VarHandler is null",
				"dev.uncandango.alltheleaks.utils.ReflectionHelper.getFieldFromClass");

//Ignorar errores de conexión de Citadel (WebHelper) al intentar acceder a URLs
		VerificacionDeStackTrace.agregarDenegadoTodos("com.github.alexthe666.citadel.web.WebHelper.getURLContents",
				"java.net.SocketTimeoutException");

//Ignorar errores de conexión al cargar la lista de contribuidores de Quark
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"org.violetmoon.quark.base.handler.ContributorRewardHandler$ThreadContributorListLoader.run",
				"java.net.SocketTimeoutException");

//Ignorar errores de conexión de Conjurer Illager al buscar contribuidores
		VerificacionDeStackTrace.agregarDenegadoTodos("com.legacy.conjurer_illager.MLSupporter$GetSupportersThread.run",
				"java.net.ConnectException: Connection timed out");

//Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.agregarDenegadoTodos("gg.essential.lib.okhttp3", "java.net.UnknownHostException:");

//Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.agregarDenegadoTodos("gg.essential.lib.okhttp3",
				"java.net.SocketException: Network is unreachable: getsockopt");

//Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.agregarDenegadoTodos("gg.essential.lib.okhttp3", "java.net.SocketTimeoutException");

		VerificacionDeStackTrace.agregarDenegadoTodos("gg.essential", "AuthenticationUnavailableException");

//Ignorar NPE de Mixin causado por Connector (Sinytra Connector)
		VerificacionDeStackTrace.agregarDenegadoTodos(
//contentido.contains("java.lang.NullPointerException: Cannot invoke
//\"org.spongepowered.asm.mixin.transformer.ClassInfo.isMixin()\" because
//\"superClass\" is null")&&
				"org.sinytra.connector.service.ConnectorLoaderService");

//Ignorar errores de reflexión interna de LibJF Unsafe
		VerificacionDeStackTrace.agregarDenegadoTodos("java.lang.NoSuchFieldException: delegate",
				"io.gitlab.jfronny.libjf.unsafe.MixinPlugin");

//Kotlin de Essential Mod: infraestructura de coroutines
		VerificacionDeStackTrace.agregarDenegadoCualquiera("java.lang.ClassNotFoundException: kotlin",
				"java.lang.ClassNotFoundException: gg.essential",
				"java.nio.file.NoSuchFileException: C:\\Windows\\System32\\etc\\hosts");
		VerificacionDeStackTrace.agregarDenegadoTodos("Missing elements in vertex:", "gg.essential");

		VerificacionDeStackTrace.agregarDenegadoTodos("Reflective setAccessible(true) disabled",
				"io.netty.util.internal.ReflectionUtil.trySetAccessible");// Común en
//Java 9+
//con
//módulos,
//no es
//fatal y
//no se
//puede
//arreglar
//sin
//modificar
//el
//entorno
//de
//ejecución
//(ej.
//agregar
//--add-opens)

		VerificacionDeStackTrace.agregarDenegadoTodos("team.creative.creativecore",
				"registerReloadListener called on wrong thread");

//TLauncher: aborta el lanzamiento mostrando mensaje de skin (no es un crash
//real)
		VerificacionDeStackTrace.agregarDenegadoTodos("MinecraftLauncher$MinecraftLauncherAborted: shown skin message",
				"MinecraftLauncher.checkExtraConditions");

//Error conocido de coremod (field_to_method.js biome), aparece en este log y
//no indica fallo crítico
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"Error occurred applying transform of coremod coremods/field_to_method.js function biome",
				"java.lang.IllegalStateException: Field f_47437_ is not private and an instance field",
				"ASMAPI.redirectFieldToMethod");

//Conflicto de mixins entre CheatUtils y Sodium/Embeddium (inyección inválida,
//pero común)
		VerificacionDeStackTrace.agregarDenegadoTodos("InvalidInjectionException", "onRenderChunkLayer",
				"handler$zpp000$cheatutils$onRenderChunkLayer", "WorldRendererMixin");

//HauntedHarvest: integración opcional con JEI falla por falta de
//Supplementaries (no fatal)
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"Failed to load: net.mehvahdjukaar.hauntedharvest.integration.JEICompat",
				"NoClassDefFoundError: net/mehvahdjukaar/supplementaries/Supplementaries",
				"mezz.jei.forge.startup.ForgePluginFinder");

//MrCrayfish Vehicle: falta archivo de cosméticos → NPE durante carga por
//defecto (no rompe ejecución)
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"Missing cosmetic definitions file: /data/vehicle/vehicles/cosmetics/",
				"java.lang.NullPointerException", "VehicleProperties.loadDefaultCosmetics");

//Forge VersionChecker: fallo al procesar JSON de actualización (muy común, no
//crítico)
		VerificacionDeStackTrace.agregarDenegadoTodos("Forge Version Check", "Failed to process update information",
				"net.minecraftforge.fml.VersionChecker$1.process");

//Forge VersionChecker: promos null (error de datos remotos, no afecta
//ejecución del juego)
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"java.lang.NullPointerException: Cannot invoke \"java.util.Map.get(Object)\" because \"promos\" is null",
				"net.minecraftforge.fml.VersionChecker$1.process");

//Errores de modelos JSON (faltan loaders o estructura inválida), típicos en
//assets de mods
		VerificacionDeStackTrace.agregarDenegadoTodos("com.google.gson.JsonParseException",
				"Model loader 'dtru:palm_fronds' not found");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.google.gson.JsonParseException",
				"Model loader 'botania:floating_flower' not found");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.google.gson.JsonParseException",
				"Composite model requires a \"children\" element");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.google.gson.JsonParseException",
				"for LLibrary bug not putting particle into textures set");

//EPCA/GeckoLib: errores de parseo Molang/animaciones durante carga de recursos
		VerificacionDeStackTrace.agregarDenegadoTodos("org.tdddd.epca.impl.", "MolangParser");
		VerificacionDeStackTrace.agregarDenegadoTodos("org.tdddd.epca.impl.", "BakedAnimationsAdapter");
		VerificacionDeStackTrace.agregarDenegadoTodos("org.tdddd.epca.impl.", "FileLoader");
		VerificacionDeStackTrace.agregarDenegadoTodos("org.tdddd.epca.impl.", "GeckoLibCache");

//AzureLib Armor: errores de parseo Molang/animaciones durante carga de
//recursos
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelibarmor.", "MolangParser");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelibarmor.", "MathBuilder");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelibarmor.", "BakedAnimationsAdapter");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelibarmor.", "FileLoader");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelibarmor.", "AzureLibCache");

//AzureLib: errores de parseo/caché de animaciones durante carga de recursos
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelib.", "MathBuilder");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelib.", "MolangParser");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelib.", "AzBakedAnimationsAdapter");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelib.", "FileLoader");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelib.", "AzBakedAnimationCache");
		VerificacionDeStackTrace.agregarDenegadoTodos("mod.azure.azurelib.", "AzResourceCache");

//Moonlight/Immersive Weathering: generación dinámica de recursos/texturas
		VerificacionDeStackTrace.agregarDenegadoTodos(
				"com.ordana.immersive_weathering.dynamicpack.ClientDynamicResourcesHandler",
				"net.mehvahdjukaar.moonlight.api.resources");

//Moonlight: wrappers/eventos de reload de recursos dinámicos
		VerificacionDeStackTrace.agregarDenegadoTodos("net.mehvahdjukaar.moonlight.", "TextureImage");
		VerificacionDeStackTrace.agregarDenegadoTodos("net.mehvahdjukaar.moonlight.", "DynResourceGenerator");
		VerificacionDeStackTrace.agregarDenegadoTodos("net.mehvahdjukaar.moonlight.", "MoonlightEventsHelper");
		VerificacionDeStackTrace.agregarDenegadoTodos("net.mehvahdjukaar.moonlight.", "ReloadInstanceWrapper");

//Physics Mod: ColladaParser devuelve null al cargar modelo estático
		VerificacionDeStackTrace.agregarDenegadoTodos("net.diebuddies.model.ColladaParser",
				"Cannot invoke \"java.util.Map.values()\"");

//Paladin Furniture Mod: overlay/generación de assets antes de que Minecraft
//tenga TextureManager
		VerificacionDeStackTrace.agregarDenegadoTodos("com.unlikepaladin.pfm.", "PFMGeneratingOverlay");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.unlikepaladin.pfm.", "ClientOverlaySetter");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.unlikepaladin.pfm.", "PFMAssetGenerator");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.unlikepaladin.pfm.", "PFMRuntimeResources");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.unlikepaladin.pfm.", "PaladinFurnitureModForge");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.unlikepaladin.pfm.", "PathPackRPWrapper");

//Palladium addon packs: errores de source/pack manager durante carga de addons
		VerificacionDeStackTrace.agregarDenegadoTodos("net.threetag.palladium.addonpack.",
				"AddonPackManagerImpl$ModPackSource");
		VerificacionDeStackTrace.agregarDenegadoTodos("net.threetag.palladium.addonpack.", "AddonPackManager");

//KubeJS: errores/cascadas de script manager durante carga cliente
		VerificacionDeStackTrace.agregarDenegadoTodos("dev.latvian.mods.kubejs.", "ScriptManager");
		VerificacionDeStackTrace.agregarDenegadoTodos("dev.latvian.mods.kubejs.", "KubeJSClient");
		VerificacionDeStackTrace.agregarDenegadoTodos("dev.latvian.mods.kubejs.", "KubeJSForge");
		VerificacionDeStackTrace.agregarDenegadoTodos("dev.latvian.mods.kubejs.", "KubeJSPlugins");

//Custom Crosshair: ImageIO/custom crosshair asset loading
		VerificacionDeStackTrace.agregarDenegadoTodos("com.wjbaker.ccm.", "CustomCrosshairDrawer");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.wjbaker.ccm.", "CustomCrosshairMod");

//PointBlank: extensión/registro durante carga, aparece como cascada aquí
		VerificacionDeStackTrace.agregarDenegadoTodos("com.vicmatskiv.pointblank.", "ExtensionRegistry");
		VerificacionDeStackTrace.agregarDenegadoTodos("com.vicmatskiv.pointblank.", "PointBlankMod");

//EMI/JEMI/JEI: integración EMI↔JEI durante llamada de plugins
		VerificacionDeStackTrace.agregarDenegadoTodos("dev.emi.emi.platform.EmiAgnos", "mezz.jei");
		VerificacionDeStackTrace.agregarDenegadoTodos("dev.emi.emi.jemi.JemiUtil", "mezz.jei");

//JEI startup/plugin caller como cascada de integración
		VerificacionDeStackTrace.agregarDenegadoTodos("mezz.jei.", "PluginCaller");
		VerificacionDeStackTrace.agregarDenegadoTodos("mezz.jei.", "JeiStarter");
		VerificacionDeStackTrace.agregarDenegadoTodos("mezz.jei.", "JustEnoughItemsClient");
		VerificacionDeStackTrace.agregarDenegadoTodos("mezz.jei.", "JustEnoughItemsClientSafeRunner");
		VerificacionDeStackTrace.agregarDenegadoTodos("mezz.jei.", "JustEnoughItems");

//HauntedHarvest JEICompat: clase opcional faltante de Supplementaries
		VerificacionDeStackTrace.agregarDenegadoTodos("net.mehvahdjukaar.hauntedharvest.integration.JEICompat",
				"net/mehvahdjukaar/supplementaries/Supplementaries");

		VerificacionDeStackTrace.agregarDenegadoTodos("observable.Observable.clientInit",
				"dev.architectury.registry.client.keymappings.forge.KeyMappingRegistryImpl.register");

	}
}
