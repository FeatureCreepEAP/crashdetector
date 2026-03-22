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
		
		VerificacionDeStackTrace.denegados.add(contentido ->
        contentido.contains("net.neoforged.fml.VersionChecker"));
		
		
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

		
		
		
		VerificacionDeStackTrace.denegados.add(contentido -> contentido.contains("com.tiviacz.travelersbackpack.TravelersBackpack.readOldCommonConfig"));

		
		
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
		VerificacionDeStackTrace.denegados.add(contentido ->
		        contentido.contains("me.frankv.jmi.compat.ftbchunks.FTBChunksCompat"));
		
		// Ignorar JMI ModCompatFactory (compatibilidad de mods, usualmente no fatal)
		VerificacionDeStackTrace.denegados.add(contentido ->
		        contentido.contains("me.frankv.jmi.api.ModCompatFactory"));
		

	}
}
