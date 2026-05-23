package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

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

		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.equals("Stacktrace:"));// Incluir
																											// líneas
// que sean
// exactamente
// "Stacktrace:"
// o contengan
// "Stacktrace:"

//como inicio
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.startsWith("Stacktrace:"));

		VerificacionDeStackTrace.agregarDenegadoPredicado(
				contentido -> contentido.contains("Unable to create Lookup for") && contentido.contains("optifine."));// Comun
		// problema,
		// pero
		// no
		// creo
		// es
		// fatal

		VerificacionDeStackTrace.agregarDenegadoPredicado(
				contentido -> contentido.contains("Unable to create custom") && contentido.contains("optifine."));// Comun
		// problema,
		// pero
		// no
		// creo
		// es
		// fatal

		VerificacionDeStackTrace
				.agregarDenegadoPredicado(contentido -> contentido.contains("com.google.gson.JsonObject")
						&& contentido.split(Verificaciones.nl).length == 1);/// Creo no es fatal pero puedo ser
																			/// incorrecto

		VerificacionDeStackTrace.agregarDenegadoPredicado(
				contentido -> contentido.contains("java.io.FileNotFoundException") && contentido.contains("minecraft"));// Comun
		// problema,
		// pero
		// no
		// creo
		// es
		// fatal

		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido
				.contains("vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe$Serializer.fromJson")
				|| contentido.contains(
						"vectorwing.farmersdelight.common.crafting.CookingPotRecipe$Serializer.readIngredients"));

		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.contains("tacz")
				&& contentido.contains("Couldn't parse data file") && contentido.contains("from"));

//Ignorar errores internos de carga y caché de GeckoLib
		VerificacionDeStackTrace
				.agregarDenegadoPredicado(contentido -> contentido.contains("software.bernie.geckolib.cache.")
						|| contentido.contains("software.bernie.geckolib.loading."));

//Ignorar errores de escaneo y preparación de datos de TACZ (usualmente no
//fatales)
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				contentido -> contentido.contains("com.tacz.guns.util.ResourceScanner.scanDirectory")
						&& contentido.contains("com.tacz.guns.resource.manager.JsonDataManager.prepare"));

//Ignorar errores de AllTheLeaks relacionados con Iron's Spellbooks (problema
//conocido y controlado)
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.contains(
				"java.lang.NoClassDefFoundError: io/redspace/ironsspellbooks/entity/mobs/dead_king_boss/DeadKingMusicManager")
				&& contentido.contains("alltheleaks"));

//Ignorar errores de carga de clase genéricos para Iron's Spellbooks
//(DeadKingMusicManager)
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.contains(
				"java.lang.ClassNotFoundException: io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingMusicManager")
				&& contentido.contains("cpw.mods.cl.ModuleClassLoader.loadClass")
				&& contentido.contains("java.lang.ClassLoader.loadClass"));

//Ignorar NPE de ServerLevel donde la instancia es null (error de estado
//interno)
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.contains(
				"java.lang.NullPointerException: Cannot invoke \"net.minecraft.server.level.ServerLevel.m_6857_()\" because \"serverlevel2\" is null"));

//Ignorar errores de inicialización relacionados con AllTheLeaks
		VerificacionDeStackTrace
				.agregarDenegadoPredicado(contentido -> contentido.contains("java.lang.ExceptionInInitializerError")
						&& contentido.contains("dev.uncandango.alltheleaks.leaks.IssueManager"));

//Ignorar errores de VarHandler nulo causados por AllTheLeaks (reflexión)
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				contentido -> contentido.contains("java.lang.RuntimeException: VarHandler is null")
						&& contentido.contains("dev.uncandango.alltheleaks.utils.ReflectionHelper.getFieldFromClass"));

//Ignorar errores de conexión de Citadel (WebHelper) al intentar acceder a URLs
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				contentido -> contentido.contains("com.github.alexthe666.citadel.web.WebHelper.getURLContents")
						&& contentido.contains("java.net.SocketTimeoutException"));

//Ignorar errores de conexión al cargar la lista de contribuidores de Quark
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido
				.contains("org.violetmoon.quark.base.handler.ContributorRewardHandler$ThreadContributorListLoader.run")
				&& contentido.contains("java.net.SocketTimeoutException"));

//Ignorar errores de conexión de Conjurer Illager al buscar contribuidores
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				contentido -> contentido.contains("com.legacy.conjurer_illager.MLSupporter$GetSupportersThread.run")
						&& contentido.contains("java.net.ConnectException: Connection timed out"));

//Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.contains("gg.essential.lib.okhttp3")
				&& contentido.contains("java.net.UnknownHostException:"));

//Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.contains("gg.essential.lib.okhttp3")
				&& contentido.contains("java.net.SocketException: Network is unreachable: getsockopt"));

//Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.contains("gg.essential.lib.okhttp3")
				&& contentido.contains("java.net.SocketTimeoutException"));

		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido -> contentido.contains("gg.essential")
				&& contentido.contains("AuthenticationUnavailableException"));

//Ignorar NPE de Mixin causado por Connector (Sinytra Connector)
		VerificacionDeStackTrace.agregarDenegadoPredicado(contentido ->
//contentido.contains("java.lang.NullPointerException: Cannot invoke
//\"org.spongepowered.asm.mixin.transformer.ClassInfo.isMixin()\" because
//\"superClass\" is null")&&
		contentido.contains("org.sinytra.connector.service.ConnectorLoaderService"));

//Ignorar errores de reflexión interna de LibJF Unsafe
		VerificacionDeStackTrace
				.agregarDenegadoPredicado(contentido -> contentido.contains("java.lang.NoSuchFieldException: delegate")
						&& contentido.contains("io.gitlab.jfronny.libjf.unsafe.MixinPlugin"));

//Kotlin de Essential Mod: infraestructura de coroutines
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("java.lang.ClassNotFoundException: kotlin")
				|| c.contains("java.lang.ClassNotFoundException: gg.essential")
				|| c.contains("java.nio.file.NoSuchFileException: C:\\Windows\\System32\\etc\\hosts")
				|| c.contains("Missing elements in vertex:") && c.contains("gg.essential"));

		VerificacionDeStackTrace
				.agregarDenegadoPredicado(contentido -> contentido.contains("Reflective setAccessible(true) disabled")
						&& contentido.contains("io.netty.util.internal.ReflectionUtil.trySetAccessible"));// Común en
// Java 9+
// con
// módulos,
// no es
// fatal y
// no se
// puede
// arreglar
// sin
// modificar
// el
// entorno
// de
// ejecución
// (ej.
// agregar
// --add-opens)

		VerificacionDeStackTrace
				.agregarDenegadoPredicado(contentido -> contentido.contains("team.creative.creativecore")
						&& contentido.contains("registerReloadListener called on wrong thread"));

//TLauncher: aborta el lanzamiento mostrando mensaje de skin (no es un crash
//real)
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				c -> c.contains("MinecraftLauncher$MinecraftLauncherAborted: shown skin message")
						&& c.contains("MinecraftLauncher.checkExtraConditions"));

//Error conocido de coremod (field_to_method.js biome), aparece en este log y
//no indica fallo crítico
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c
				.contains("Error occurred applying transform of coremod coremods/field_to_method.js function biome")
				&& c.contains("java.lang.IllegalStateException: Field f_47437_ is not private and an instance field")
				&& c.contains("ASMAPI.redirectFieldToMethod"));

//Conflicto de mixins entre CheatUtils y Sodium/Embeddium (inyección inválida,
//pero común)
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("InvalidInjectionException")
				&& c.contains("onRenderChunkLayer") && c.contains("handler$zpp000$cheatutils$onRenderChunkLayer")
				&& c.contains("WorldRendererMixin"));

//HauntedHarvest: integración opcional con JEI falla por falta de
//Supplementaries (no fatal)
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				c -> c.contains("Failed to load: net.mehvahdjukaar.hauntedharvest.integration.JEICompat")
						&& c.contains("NoClassDefFoundError: net/mehvahdjukaar/supplementaries/Supplementaries")
						&& c.contains("mezz.jei.forge.startup.ForgePluginFinder"));

//MrCrayfish Vehicle: falta archivo de cosméticos → NPE durante carga por
//defecto (no rompe ejecución)
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				c -> c.contains("Missing cosmetic definitions file: /data/vehicle/vehicles/cosmetics/")
						&& c.contains("java.lang.NullPointerException")
						&& c.contains("VehicleProperties.loadDefaultCosmetics"));

//Forge VersionChecker: fallo al procesar JSON de actualización (muy común, no
//crítico)
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				c -> c.contains("Forge Version Check") && c.contains("Failed to process update information")
						&& c.contains("net.minecraftforge.fml.VersionChecker$1.process"));

//Forge VersionChecker: promos null (error de datos remotos, no afecta
//ejecución del juego)
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains(
				"java.lang.NullPointerException: Cannot invoke \"java.util.Map.get(Object)\" because \"promos\" is null")
				&& c.contains("net.minecraftforge.fml.VersionChecker$1.process"));

//Errores de modelos JSON (faltan loaders o estructura inválida), típicos en
//assets de mods
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("com.google.gson.JsonParseException")
				&& (c.contains("Model loader 'dtru:palm_fronds' not found")
						|| c.contains("Model loader 'botania:floating_flower' not found")
						|| c.contains("Composite model requires a \"children\" element")
						|| c.contains("for LLibrary bug not putting particle into textures set")));

//EPCA/GeckoLib: errores de parseo Molang/animaciones durante carga de recursos
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("org.tdddd.epca.impl.")
				&& (c.contains("MolangParser") || c.contains("BakedAnimationsAdapter") || c.contains("FileLoader")
						|| c.contains("GeckoLibCache")));

//AzureLib Armor: errores de parseo Molang/animaciones durante carga de
//recursos
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("mod.azure.azurelibarmor.")
				&& (c.contains("MolangParser") || c.contains("MathBuilder") || c.contains("BakedAnimationsAdapter")
						|| c.contains("FileLoader") || c.contains("AzureLibCache")));

//AzureLib: errores de parseo/caché de animaciones durante carga de recursos
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				c -> c.contains("mod.azure.azurelib.") && (c.contains("MathBuilder") || c.contains("MolangParser")
						|| c.contains("AzBakedAnimationsAdapter") || c.contains("FileLoader")
						|| c.contains("AzBakedAnimationCache") || c.contains("AzResourceCache")));

//Moonlight/Immersive Weathering: generación dinámica de recursos/texturas
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				c -> c.contains("com.ordana.immersive_weathering.dynamicpack.ClientDynamicResourcesHandler")
						&& c.contains("net.mehvahdjukaar.moonlight.api.resources"));

//Moonlight: wrappers/eventos de reload de recursos dinámicos
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("net.mehvahdjukaar.moonlight.")
				&& (c.contains("TextureImage") || c.contains("DynResourceGenerator")
						|| c.contains("MoonlightEventsHelper") || c.contains("ReloadInstanceWrapper")));

//Physics Mod: ColladaParser devuelve null al cargar modelo estático
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("net.diebuddies.model.ColladaParser")
				&& c.contains("Cannot invoke \"java.util.Map.values()\""));

//Paladin Furniture Mod: overlay/generación de assets antes de que Minecraft
//tenga TextureManager
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("com.unlikepaladin.pfm.")
				&& (c.contains("PFMGeneratingOverlay") || c.contains("ClientOverlaySetter")
						|| c.contains("PFMAssetGenerator") || c.contains("PFMRuntimeResources")
						|| c.contains("PaladinFurnitureModForge") || c.contains("PathPackRPWrapper")));

//Palladium addon packs: errores de source/pack manager durante carga de addons
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("net.threetag.palladium.addonpack.")
				&& (c.contains("AddonPackManagerImpl$ModPackSource") || c.contains("AddonPackManager")));

//KubeJS: errores/cascadas de script manager durante carga cliente
		VerificacionDeStackTrace
				.agregarDenegadoPredicado(c -> c.contains("dev.latvian.mods.kubejs.") && (c.contains("ScriptManager")
						|| c.contains("KubeJSClient") || c.contains("KubeJSForge") || c.contains("KubeJSPlugins")));

//Custom Crosshair: ImageIO/custom crosshair asset loading
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("com.wjbaker.ccm.")
				&& (c.contains("CustomCrosshairDrawer") || c.contains("CustomCrosshairMod")));

//PointBlank: extensión/registro durante carga, aparece como cascada aquí
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("com.vicmatskiv.pointblank.")
				&& (c.contains("ExtensionRegistry") || c.contains("PointBlankMod")));

//EMI/JEMI/JEI: integración EMI↔JEI durante llamada de plugins
		VerificacionDeStackTrace.agregarDenegadoPredicado(
				c -> (c.contains("dev.emi.emi.platform.EmiAgnos") || c.contains("dev.emi.emi.jemi.JemiUtil"))
						&& c.contains("mezz.jei"));

//JEI startup/plugin caller como cascada de integración
		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("mezz.jei.")
				&& (c.contains("PluginCaller") || c.contains("JeiStarter") || c.contains("JustEnoughItemsClient")
						|| c.contains("JustEnoughItemsClientSafeRunner") || c.contains("JustEnoughItems")));

//HauntedHarvest JEICompat: clase opcional faltante de Supplementaries
		VerificacionDeStackTrace
				.agregarDenegadoPredicado(c -> c.contains("net.mehvahdjukaar.hauntedharvest.integration.JEICompat")
						&& c.contains("net/mehvahdjukaar/supplementaries/Supplementaries"));

		VerificacionDeStackTrace.agregarDenegadoPredicado(c -> c.contains("observable.Observable.clientInit")
				&& c.contains("dev.architectury.registry.client.keymappings.forge.KeyMappingRegistryImpl.register"));

	}
}
