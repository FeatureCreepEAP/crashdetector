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

		VerificacionDeStackTrace.denegados.add(
				contentido -> contentido.contains("Error starting SoundSystem. Turning off sounds & music"));//Tenemos ErrorSistemaSonido
																													
	}
}
