package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class StackTracesDenegadosDeMinecraftPorDefecto {

	public static void init() {
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("Preparing crash report with UUID")); // Excluir
																														// líneas
																														// que
																														// contienen
																														// "Preparing
																														// crash
																														// report
																														// with
																														// UUID"
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("Failed to complete lifecycle event"));
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("Crash report saved to"));
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("Mod Loading has failed"));
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains(
				"Could not determine mod trust worthiness, Assuming Jar was downloaded from trusted source!"));// FUCK
																												// STOPMODREPOSTS
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("org.watermedia.videolan4j.discovery.providers"));
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("libflite.so"));// TTS no fatal y es
																									// comun en TL linux
																									// y mac. TODO
																									// agregar una
																									// verificacion para
																									// este
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("com.mojang.text2speech"));// TTS no
																											// fatal y
																											// es comun
																											// en TL
																											// linux y
																											// mac. TODO
																											// agregar
																											// una
																											// verificacion
																											// para este

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.trim().equals("Stacktrace:"));// Incluir líneas
																										// que sean
																										// exactamente
																										// "Stacktrace:"
																										// o contengan
																										// "Stacktrace:"

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.trim().contains("Error during pre-loading phase"));// Incluir líneas que
																									// sean exactamente
																									// "Stacktrace:" o
																									// contengan
																									// "Stacktrace:"

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.trim().contains("Failed to initialize mod containers"));// Incluir líneas
																										// que sean
																										// exactamente
																										// "Stacktrace:"
																										// o contengan
																										// "Stacktrace:"

		// como inicio
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.trim().startsWith("Stacktrace:"));
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("de.markusbordihn.modsoptimizer.data.JsonFileParser.parseJson"));// No
																														// es
																														// fatal

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("RealmsServiceException"));

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("java.net.UnknownHostException: api.modrinth.com"));

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("Unreported exception thrown!"));

		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("See https://github.com/google/gson/blob/main/Troubleshooting.md#malformed-json"));// TODO
																												// verificar
																												// si
																												// esta
																												// siempre
																												// bien

		VerificacionDeStackTrace.denegados.add(
				contentido -> contentido.contains("Unable to create Lookup for") && contentido.contains("optifine."));// Comun
																														// problema,
																														// pero
																														// no
																														// creo
																														// es
																														// fatal
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("Unable to create custom") && contentido.contains("optifine."));// Comun
																														// problema,
																														// pero
																														// no
																														// creo
																														// es
																														// fatal

		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("https://vazkiimods.github.io/Patchouli/docs/upgrading/upgrade-guide-120"));// Creo no es
																										// fatal

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("com.google.gson.JsonObject")
				&& contentido.split(Verificaciones.nl).length == 1);/// Creo no es fatal pero puedo ser incorrecto

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("Not a JSON Object"));
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("Server is unreachable (ICE failed)"));// essential

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("patreon"));// Si hay una problema
																								// puedo eliminar

		VerificacionDeStackTrace.denegados.add(
				contentido -> contentido.contains("java.io.FileNotFoundException") && contentido.contains("minecraft"));// Comun
																														// problema,
																														// pero
																														// no
																														// creo
																														// es
																														// fatal

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("net.minecraftforge.fml.VersionChecker"));// Si hay una problema
																									// puedo eliminar

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("net.minecraftforge.fml.VersionChecker"));// Si hay una problema
																									// puedo eliminar

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("net.neoforged.fml.VersionChecker"));

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("No bonuses were provided"));// apotheosis
																												// Si
																												// hay
																												// una
																												// problema
																												// puedo
																												// eliminar

		VerificacionDeStackTrace.denegados.add(
				contentido -> contentido.contains("rg.betterx.bclib.client.models.CustomModelBakery.addItemModel"));// Puedo
																													// cambiar
																													// si

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("com.sonicether.soundphysics.SoundPhysicsMod.CONFIG"));// Tenemos
																												// ErrorSistemaSonido
		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("umpaz.farmersrespite.common.crafting.KettlePouringRecipe$Serializer"));// Tenemos

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains(
				"java.lang.NullPointerException: Cannot invoke \"net.mehvahdjukaar.polytone.utils.CompoundReloader.earlyProcess(net.minecraft.server.packs.resources.ResourceManager)\" because \"net.mehvahdjukaar.polytone.Polytone.COMPOUND_RELOADER\" is null"));// nada

		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe$Serializer.fromJson")

				|| contentido.contains(
						"vectorwing.farmersdelight.common.crafting.CookingPotRecipe$Serializer.readIngredients")

		);

		// Calio MultiJsonDataLoader errores de carga de datos (Data loaders)
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("io.github.apace100.calio.data.MultiJsonDataLoader"));

		// Errores de sintaxis JSON por items desconocidos (recetas/loottables de mods
		// quitados) (¿posible eliminar?)
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("com.google.gson.JsonSyntaxException: Unknown item"));

		// Errores de recetas con tipos inválidos o no soportados (ej. mods de datos
		// quitados)(¿posible eliminar?)
		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("com.google.gson.JsonSyntaxException: Invalid or unsupported recipe type"));

		// Errores de parseo de archivos de datos de TACZ (no suelen ser fatales o son
		// de recursos)
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("tacz")
				&& contentido.contains("Couldn't parse data file") && contentido.contains("from"));

		// Errores de parseo matemático/sintaxis de mclib (común en animaciones/modelos,
		// usualmente no fatal)
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("com.eliotlash.mclib.math.MathBuilder.parseSymbols"));

		// Ignorar errores internos de carga y caché de GeckoLib
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("software.bernie.geckolib.cache.")
				|| contentido.contains("software.bernie.geckolib.loading."));

		// Ignorar errores de escaneo y preparación de datos de TACZ (usualmente no
		// fatales)
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("com.tacz.guns.util.ResourceScanner.scanDirectory")
						&& contentido.contains("com.tacz.guns.resource.manager.JsonDataManager.prepare"));

		// Ignorar errores de AllTheLeaks relacionados con Iron's Spellbooks (problema
		// conocido y controlado)
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains(
				"java.lang.NoClassDefFoundError: io/redspace/ironsspellbooks/entity/mobs/dead_king_boss/DeadKingMusicManager")
				&& contentido.contains("alltheleaks"));

		// Ignorar errores de carga de clase genéricos para Iron's Spellbooks
		// (DeadKingMusicManager)
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains(
				"java.lang.ClassNotFoundException: io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingMusicManager")
				&& contentido.contains("cpw.mods.cl.ModuleClassLoader.loadClass")
				&& contentido.contains("java.lang.ClassLoader.loadClass"));

		// Ignorar NPE de ServerLevel donde la instancia es null (error de estado
		// interno)
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains(
				"java.lang.NullPointerException: Cannot invoke \"net.minecraft.server.level.ServerLevel.m_6857_()\" because \"serverlevel2\" is null"));

		// Ignorar referencias internas de Connector/Authlib
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("connector$authlib"));

		// Ignorar errores de inicialización relacionados con AllTheLeaks
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("java.lang.ExceptionInInitializerError")
						&& contentido.contains("dev.uncandango.alltheleaks.leaks.IssueManager"));

		// Ignorar errores de VarHandler nulo causados por AllTheLeaks (reflexión)
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("java.lang.RuntimeException: VarHandler is null")
						&& contentido.contains("dev.uncandango.alltheleaks.utils.ReflectionHelper.getFieldFromClass"));

		// Ignorar errores de conexión de Citadel (WebHelper) al intentar acceder a URLs
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("com.github.alexthe666.citadel.web.WebHelper.getURLContents")
						&& contentido.contains("java.net.SocketTimeoutException"));

		// Ignorar errores de conexión al cargar la lista de contribuidores de Quark
		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("org.violetmoon.quark.base.handler.ContributorRewardHandler$ThreadContributorListLoader.run")
				&& contentido.contains("java.net.SocketTimeoutException"));

		// Ignorar errores de conexión de Conjurer Illager al buscar contribuidores
		VerificacionDeStackTrace.denegados.add(
				contentido -> contentido.contains("com.legacy.conjurer_illager.MLSupporter$GetSupportersThread.run")
						&& contentido.contains("java.net.ConnectException: Connection timed out"));

		// Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("gg.essential.lib.okhttp3")
				&& contentido.contains("java.net.UnknownHostException:"));

		// Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("gg.essential.lib.okhttp3")
				&& contentido.contains("java.net.SocketException: Network is unreachable: getsockopt"));

		// Ignorar errores de red de Essential al conectar con los servidores de Mojang
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("gg.essential.lib.okhttp3")
				&& contentido.contains("java.net.SocketTimeoutException"));

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("gg.essential")
				&& contentido.contains("AuthenticationUnavailableException"));

		// Ignorar NPE de Mixin causado por Connector (Sinytra Connector)
		VerificacionDeStackTrace.denegados.add(contentido ->
		// contentido.contains("java.lang.NullPointerException: Cannot invoke
		// \"org.spongepowered.asm.mixin.transformer.ClassInfo.isMixin()\" because
		// \"superClass\" is null")&&
		contentido.contains("org.sinytra.connector.service.ConnectorLoaderService"));

		// Ignorar errores de reflexión interna de LibJF Unsafe
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("java.lang.NoSuchFieldException: delegate")
						&& contentido.contains("io.gitlab.jfronny.libjf.unsafe.MixinPlugin"));

		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("com.tiviacz.travelersbackpack.TravelersBackpack.readOldCommonConfig"));

		// nada

//		VerificacionDeStackTrace.denegados.add(c -> c.contains("Could not find any mod for modid 'rechiseled'!")
//				&& c.contains("com.supermartijn642.rechiseled.registration.RechiseledRegistrationImpl"));

		// Kotlin de Essential Mod: infraestructura de coroutines
		VerificacionDeStackTrace.denegados.add(c -> c.contains("java.lang.ClassNotFoundException: kotlin")
				|| c.contains("java.lang.ClassNotFoundException: gg.essential")

				|| c.contains("java.nio.file.NoSuchFileException: C:\\Windows\\System32\\etc\\hosts")
				|| c.contains("Missing elements in vertex:") && c.contains("gg.essential")

		);

		// Ignorar compatibilidad JMI ↔ FTB Chunks (no suele ser fatal)
		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("me.frankv.jmi.compat.ftbchunks.FTBChunksCompat"));

		// Ignorar JMI ModCompatFactory (compatibilidad de mods, usualmente no fatal)
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("me.frankv.jmi.api.ModCompatFactory"));

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("Interface mixin contains a non-public method"));// Comun
																										// positiva
																										// falsa con
																										// SpongeMixin,
																										// no es fatal y
																										// no se puede
																										// arreglar

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("WaterMediaBinaries"));// https://github.com/WaterMediaTeam/watermedia-v3/blob/77d7f4635d417b9b8e35907d966029fcd249c6fa/src/main/java/org/watermedia/api/media/players/FFMediaPlayer.java#L1341

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("Reflective setAccessible(true) disabled")
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

		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("com.seibel.distanthorizons.core.level.DhClientServerLevel.isRendering"));

		VerificacionDeStackTrace.denegados.add(contentido -> contentido
				.contains("foundry.veil.impl.client.render.dynamicbuffer.VanillaShaderCompiler"));

		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("team.creative.creativecore")
				&& contentido.contains("registerReloadListener called on wrong thread"));

		VerificacionDeStackTrace.denegados
				.add(contentido -> contentido.contains("foundry.veil.impl.resource.VeilResourceManagerImpl"));

		// TLauncher: aborta el lanzamiento mostrando mensaje de skin (no es un crash
		// real)
		VerificacionDeStackTrace.denegados
				.add(c -> c.contains("MinecraftLauncher$MinecraftLauncherAborted: shown skin message")
						&& c.contains("MinecraftLauncher.checkExtraConditions"));

		// Error conocido de coremod (field_to_method.js biome), aparece en este log y
		// no indica fallo crítico
		VerificacionDeStackTrace.denegados.add(c -> c
				.contains("Error occurred applying transform of coremod coremods/field_to_method.js function biome")
				&& c.contains("java.lang.IllegalStateException: Field f_47437_ is not private and an instance field")
				&& c.contains("ASMAPI.redirectFieldToMethod"));

		// Conflicto de mixins entre CheatUtils y Sodium/Embeddium (inyección inválida,
		// pero común)
		VerificacionDeStackTrace.denegados.add(c -> c.contains("InvalidInjectionException")
				&& c.contains("onRenderChunkLayer") && c.contains("handler$zpp000$cheatutils$onRenderChunkLayer")
				&& c.contains("WorldRendererMixin"));

		// HauntedHarvest: integración opcional con JEI falla por falta de
		// Supplementaries (no fatal)
		VerificacionDeStackTrace.denegados
				.add(c -> c.contains("Failed to load: net.mehvahdjukaar.hauntedharvest.integration.JEICompat")
						&& c.contains("NoClassDefFoundError: net/mehvahdjukaar/supplementaries/Supplementaries")
						&& c.contains("mezz.jei.forge.startup.ForgePluginFinder"));

		// MrCrayfish Vehicle: falta archivo de cosméticos → NPE durante carga por
		// defecto (no rompe ejecución)
		VerificacionDeStackTrace.denegados
				.add(c -> c.contains("Missing cosmetic definitions file: /data/vehicle/vehicles/cosmetics/")
						&& c.contains("java.lang.NullPointerException")
						&& c.contains("VehicleProperties.loadDefaultCosmetics"));

		// Forge VersionChecker: fallo al procesar JSON de actualización (muy común, no
		// crítico)
		VerificacionDeStackTrace.denegados
				.add(c -> c.contains("Forge Version Check") && c.contains("Failed to process update information")
						&& c.contains("net.minecraftforge.fml.VersionChecker$1.process"));

		// Forge VersionChecker: promos null (error de datos remotos, no afecta
		// ejecución del juego)
		VerificacionDeStackTrace.denegados.add(c -> c.contains(
				"java.lang.NullPointerException: Cannot invoke \"java.util.Map.get(Object)\" because \"promos\" is null")
				&& c.contains("net.minecraftforge.fml.VersionChecker$1.process"));

		// Errores de modelos JSON (faltan loaders o estructura inválida), típicos en
		// assets de mods
		VerificacionDeStackTrace.denegados.add(c -> c.contains("com.google.gson.JsonParseException")
				&& (c.contains("Model loader 'dtru:palm_fronds' not found")
						|| c.contains("Model loader 'botania:floating_flower' not found")
						|| c.contains("Composite model requires a \"children\" element")
						|| c.contains("for LLibrary bug not putting particle into textures set")));

		// Dreamtinker: fluidos no registrados en JSON (problema de datos, normalmente
		// no fatal)
		VerificacionDeStackTrace.denegados.add(c -> c.contains(
				"com.google.gson.JsonSyntaxException: Unable to parse fluid as registry minecraft:fluid does not contain ID dreamtinker:"));

		// EPCA/GeckoLib: errores de parseo Molang/animaciones durante carga de recursos
		VerificacionDeStackTrace.denegados.add(c -> c.contains("org.tdddd.epca.impl.") && (c.contains("MolangParser")
				|| c.contains("BakedAnimationsAdapter") || c.contains("FileLoader") || c.contains("GeckoLibCache")));

		// AzureLib Armor: errores de parseo Molang/animaciones durante carga de
		// recursos
		VerificacionDeStackTrace.denegados.add(c -> c.contains("mod.azure.azurelibarmor.")
				&& (c.contains("MolangParser") || c.contains("MathBuilder") || c.contains("BakedAnimationsAdapter")
						|| c.contains("FileLoader") || c.contains("AzureLibCache")));

		// AzureLib: errores de parseo/caché de animaciones durante carga de recursos
		VerificacionDeStackTrace.denegados.add(c -> c.contains("mod.azure.azurelib.") && (c.contains("MathBuilder")
				|| c.contains("MolangParser") || c.contains("AzBakedAnimationsAdapter") || c.contains("FileLoader")
				|| c.contains("AzBakedAnimationCache") || c.contains("AzResourceCache")));

		// GeckoLib: MolangParser como cascada de animaciones/modelos
		VerificacionDeStackTrace.denegados.add(c -> c.contains("software.bernie.geckolib.core.molang.MolangParser"));

		// Moonlight/Immersive Weathering: generación dinámica de recursos/texturas
		VerificacionDeStackTrace.denegados
				.add(c -> c.contains("com.ordana.immersive_weathering.dynamicpack.ClientDynamicResourcesHandler")
						&& c.contains("net.mehvahdjukaar.moonlight.api.resources"));

		// Moonlight: wrappers/eventos de reload de recursos dinámicos
		VerificacionDeStackTrace.denegados.add(c -> c.contains("net.mehvahdjukaar.moonlight.")
				&& (c.contains("TextureImage") || c.contains("DynResourceGenerator")
						|| c.contains("MoonlightEventsHelper") || c.contains("ReloadInstanceWrapper")));

		// Physics Mod: ColladaParser devuelve null al cargar modelo estático
		VerificacionDeStackTrace.denegados.add(c -> c.contains("net.diebuddies.model.ColladaParser")
				&& c.contains("Cannot invoke \"java.util.Map.values()\""));

		// Paladin Furniture Mod: overlay/generación de assets antes de que Minecraft
		// tenga TextureManager
		VerificacionDeStackTrace.denegados.add(c -> c.contains("com.unlikepaladin.pfm.")
				&& (c.contains("PFMGeneratingOverlay") || c.contains("ClientOverlaySetter")
						|| c.contains("PFMAssetGenerator") || c.contains("PFMRuntimeResources")
						|| c.contains("PaladinFurnitureModForge") || c.contains("PathPackRPWrapper")));

		// QuickPack: lectura rápida de recursos/pack, cascada de resource loading
		VerificacionDeStackTrace.denegados.add(c -> c.contains("me.drex.quickpack.packs.FastFilePackResources"));

		// Palladium addon packs: errores de source/pack manager durante carga de addons
		VerificacionDeStackTrace.denegados.add(c -> c.contains("net.threetag.palladium.addonpack.")
				&& (c.contains("AddonPackManagerImpl$ModPackSource") || c.contains("AddonPackManager")));

		// Cold Sweat: registry/config loading durante inicialización
		VerificacionDeStackTrace.denegados
				.add(c -> c.contains("com.momosoftworks.coldsweat.config.ConfigLoadingHandler$CreateRegistries"));

		// KubeJS: errores/cascadas de script manager durante carga cliente
		VerificacionDeStackTrace.denegados
				.add(c -> c.contains("dev.latvian.mods.kubejs.") && (c.contains("ScriptManager")
						|| c.contains("KubeJSClient") || c.contains("KubeJSForge") || c.contains("KubeJSPlugins")));

		// Custom Crosshair: ImageIO/custom crosshair asset loading
		VerificacionDeStackTrace.denegados.add(c -> c.contains("com.wjbaker.ccm.")
				&& (c.contains("CustomCrosshairDrawer") || c.contains("CustomCrosshairMod")));

		// PointBlank: extensión/registro durante carga, aparece como cascada aquí
		VerificacionDeStackTrace.denegados.add(c -> c.contains("com.vicmatskiv.pointblank.")
				&& (c.contains("ExtensionRegistry") || c.contains("PointBlankMod")));

		// EMI/JEMI/JEI: integración EMI↔JEI durante llamada de plugins
		VerificacionDeStackTrace.denegados
				.add(c -> (c.contains("dev.emi.emi.platform.EmiAgnos") || c.contains("dev.emi.emi.jemi.JemiUtil"))
						&& c.contains("mezz.jei"));

		// JEI startup/plugin caller como cascada de integración
		VerificacionDeStackTrace.denegados.add(c -> c.contains("mezz.jei.")
				&& (c.contains("PluginCaller") || c.contains("JeiStarter") || c.contains("JustEnoughItemsClient")
						|| c.contains("JustEnoughItemsClientSafeRunner") || c.contains("JustEnoughItems")));

		// HauntedHarvest JEICompat: clase opcional faltante de Supplementaries
		VerificacionDeStackTrace.denegados.add(c -> c.contains("net.mehvahdjukaar.hauntedharvest.integration.JEICompat")
				&& c.contains("net/mehvahdjukaar/supplementaries/Supplementaries"));
		
		
		VerificacionDeStackTrace.denegados.add(c -> c.contains("observable.Observable.clientInit")
				&& c.contains("dev.architectury.registry.client.keymappings.forge.KeyMappingRegistryImpl.register"));
		
		

	}
}
