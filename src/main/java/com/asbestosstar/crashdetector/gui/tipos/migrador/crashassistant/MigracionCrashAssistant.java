package com.asbestosstar.crashdetector.gui.tipos.migrador.crashassistant;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.PirataMC;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.config.toml.Toml;

public class MigracionCrashAssistant {

	public static final String MODO_ORIGINAL = "original";
	public static final String MODO_PRIMITIVA = "primitiva";
	public static final String LOGGING_MANTENER = "mantener_actual";
	public static final String LOGGING_MIGRAR_CA = "migrar_desde_ca";
	public static final String MODO_CENTRO_SOPORTE = "centro_soporte";

	// Archivos de Crash Assistant: relativos al directorio actual de la instancia.
	public Path carpetaCA = Paths.get("config", "crash_assistant");
	public Path archivoProblematicMods = carpetaCA.resolve("problematic_mods_config.json");
	public Path archivoProblematicModsRespaldo = carpetaCA.resolve("problematic_mods.json");
	public Path archivoConfigToml = carpetaCA.resolve("config.toml");
	public Path carpetaOverrides = carpetaCA.resolve("crash_assistant_localization_overrides");

	// Archivo de CrashDetector.
	public Path archivoModsDesanimados = Statics.carpeta.resolve("mods_desanimados.json");

	public boolean migrarProblematicMods() {
		try {
			Path origen = Files.exists(archivoProblematicMods) ? archivoProblematicMods : archivoProblematicModsRespaldo;

			if (!Files.exists(origen)) {
				return false;
			}

			Nodo ca = Json.leer(new String(Files.readAllBytes(origen), StandardCharsets.UTF_8));
			if (ca == null || !ca.esObjeto()) {
				return false;
			}

			Nodo cd = Files.exists(archivoModsDesanimados)
					? Json.leer(new String(Files.readAllBytes(archivoModsDesanimados), StandardCharsets.UTF_8))
					: Json.leer("[]");

			if (cd == null || !cd.esArreglo()) {
				cd = Json.leer("[]");
			}

			for (String modid : ca.claves()) {
				Nodo entradaCA = ca.obtener(modid);
				if (entradaCA == null || !entradaCA.esObjeto()) {
					continue;
				}

				boolean anularNormal = leerBooleano(entradaCA, "should_crash_on_startup", false);
				String descripcion = leerCadena(entradaCA, "msg", "");

				Nodo entradaCD = buscarPorModid(cd, modid);
				if (entradaCD == null) {
					entradaCD = Json.crearObjeto();
					entradaCD.obtener("modid").poner(modid);
					cd.agregar(entradaCD);
				}

				entradaCD.obtener("abrir_cd_si_coincide").poner(anularNormal);

				Nodo razon = entradaCD.obtener("razon");
				if (razon == null || !razon.esObjeto()) {
					razon = Json.crearObjeto();
					entradaCD.obtener("razon").poner(razon);
				}

				if (descripcion != null && !descripcion.trim().isEmpty()) {
					razon.obtener("en").poner(descripcion.trim());
				}
			}

			Files.write(archivoModsDesanimados, cd.aBytesUtf8());
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public boolean migrarConfigToml(String modoPrincipal, boolean migrarSitioLogging) {
		try {
			if (!Files.exists(archivoConfigToml)) {
				return false;
			}

			Toml.Nodo raiz = Toml.leer(new String(Files.readAllBytes(archivoConfigToml), StandardCharsets.UTF_8));

			Toml.Nodo general = raiz.obtener("general");
			Toml.Nodo text = raiz.obtener("text");
			Toml.Nodo generatedMessage = raiz.obtener("generated_message");
			Toml.Nodo piracy = raiz.obtener("piracy");

			if (modoPrincipal == null || modoPrincipal.trim().isEmpty()) {
				modoPrincipal = MODO_ORIGINAL;
			}

			String helpLink = leerCadenaToml(general, "help_link", "");

			if (MODO_PRIMITIVA.equals(modoPrincipal)) {
				ConfigString.de("guitipo_principal", "estilo_lanzer").escribir("primitiva");

				if (helpLink != null && !helpLink.trim().isEmpty()
						&& !"CHANGE_ME".equalsIgnoreCase(helpLink.trim())) {
					ConfigString.de("dialogo.compartir.primitiva.centro_soporte.url", "https://example.com/")
							.escribir(helpLink.trim());
				}
			} else if (MODO_CENTRO_SOPORTE.equals(modoPrincipal)) {
				ConfigString.de("guitipo_principal", "estilo_lanzer").escribir("centro_soporte");

				if (helpLink != null && !helpLink.trim().isEmpty()
						&& !"CHANGE_ME".equalsIgnoreCase(helpLink.trim())) {
					ConfigString.de("dialogo.compartir.centro_soporte.url_soporte", "https://example.com/")
							.escribir(helpLink.trim());
				}

				migrarTextosCentroSoporteDesdeToml(text, generatedMessage);
				migrarOverridesCentroSoporte();
			}

			String defaultLang = leerCadenaToml(general, "default_lang", "");
			String codigoIdioma = convertirIdiomaA2Caracteres(defaultLang);
			if (codigoIdioma != null && !codigoIdioma.isEmpty()) {
				Idioma.idioma_respaldo.escribir(codigoIdioma);
			}

			if (migrarSitioLogging) {
				migrarSitioLogging(general);
			}

			boolean launcherLog = leerBooleanoToml(general, "generate_own_launcher_log", false);
			ConfigBoolean.de("proxy_sysout_syserr", false).escribir(launcherLog);

			String modpackName = leerCadenaToml(text, "modpack_name", "");
			if (modpackName != null && !modpackName.trim().isEmpty()) {
				Statics.nombre_cd.escribir(modpackName.trim());
			}

			boolean piracyEnabled = leerBooleanoToml(piracy, "enabled", false);
			boolean piracyNotification = leerBooleanoToml(generatedMessage, "piracy_notification", piracyEnabled);
			PirataMC.config.escribir(piracyEnabled && piracyNotification);

			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public void migrarSitioLogging(Toml.Nodo general) {
		String uploadTo = leerCadenaToml(general, "upload_to", "");

		if ("mclo.gs".equalsIgnoreCase(uploadTo) || "mclogs".equalsIgnoreCase(uploadTo)) {
			ConfigString.de("api_de_registro", "securelogger").escribir("mclogs");
		} else if ("gnomebot.dev".equalsIgnoreCase(uploadTo) || "gnomebotdev".equalsIgnoreCase(uploadTo)) {
			ConfigString.de("api_de_registro", "securelogger").escribir("gnomebotdev");
		}
	}

	public void migrarTextosCentroSoporteDesdeToml(Toml.Nodo text, Toml.Nodo generatedMessage) {
		String lang = MonitorCodigoIdiomaSeguro();

		String modpackName = leerCadenaToml(text, "modpack_name", "");
		String supportName = leerCadenaToml(text, "support_name", "");
		String supportPlace = leerCadenaToml(text, "support_place", "");
		String textUnderCrashed = leerCadenaToml(generatedMessage, "text_under_crashed", "");
		String warningAfterUpload = leerCadenaToml(generatedMessage, "warning_after_upload_all_button_press", "");

		if (modpackName != null && !modpackName.trim().isEmpty()) {
			ConfigString.de("dialogo.compartir.centro_soporte.texto_bajo_titulo." + lang,
					com.asbestosstar.crashdetector.MonitorDePID.idioma.centroSoporteTextoBajoTitulo())
					.escribir(modpackName.trim());
		}

		if (textUnderCrashed != null && !textUnderCrashed.trim().isEmpty()) {
			ConfigString.de("dialogo.compartir.centro_soporte.texto_superior." + lang,
					com.asbestosstar.crashdetector.MonitorDePID.idioma.centroSoporteTextoSuperior())
					.escribir(textUnderCrashed.trim());
		}

		if (warningAfterUpload != null && !warningAfterUpload.trim().isEmpty()) {
			ConfigString.de("dialogo.compartir.centro_soporte.texto_aviso." + lang,
					com.asbestosstar.crashdetector.MonitorDePID.idioma.centroSoporteTextoAviso())
					.escribir(warningAfterUpload.trim());
		}

		if (supportName != null && !supportName.trim().isEmpty()) {
			ConfigString.de("dialogo.compartir.centro_soporte.nombre_soporte." + lang, "Support")
					.escribir(supportName.trim());
		}

		if (supportPlace != null && !supportPlace.trim().isEmpty()) {
			ConfigString.de("dialogo.compartir.centro_soporte.lugar_soporte." + lang, "support channel")
					.escribir(supportPlace.trim());
		}
	}

	public void migrarOverridesCentroSoporte() {
		try {
			if (!Files.exists(carpetaOverrides)) {
				return;
			}

			try (java.util.stream.Stream<Path> stream = Files.list(carpetaOverrides)) {
				stream.filter(p -> p.getFileName().toString().toLowerCase().endsWith(".json"))
						.forEach(p -> migrarOverrideCentroSoporte(p));
			}
		} catch (Throwable t) {
		}
	}

	public void migrarOverrideCentroSoporte(Path archivo) {
		try {
			String nombre = archivo.getFileName().toString();
			String langCA = nombre.substring(0, nombre.length() - ".json".length());
			String langCD = convertirIdiomaA2Caracteres(langCA);

			Nodo json = Json.leer(new String(Files.readAllBytes(archivo), StandardCharsets.UTF_8));
			if (json == null || !json.esObjeto()) {
				return;
			}

			migrarOverrideSiExiste(json, "gui.crashed.title",
					"dialogo.compartir.centro_soporte.texto_bajo_titulo." + langCD);

			migrarOverrideSiExiste(json, "gui.comment.dont_send_screenshot",
					"dialogo.compartir.centro_soporte.texto_aviso." + langCD);

			migrarOverrideSiExiste(json, "gui.upload_all",
					"dialogo.compartir.centro_soporte.boton_upload_all." + langCD);

			migrarOverrideSiExiste(json, "gui.request_help",
					"dialogo.compartir.centro_soporte.boton_ayuda." + langCD);

			migrarOverrideSiExiste(json, "generated_message.text_under_crashed",
					"dialogo.compartir.centro_soporte.texto_superior." + langCD);

			migrarOverrideSiExiste(json, "generated_message.warning_after_upload_all_button_press",
					"dialogo.compartir.centro_soporte.texto_aviso." + langCD);
		} catch (Throwable t) {
		}
	}

	public void migrarOverrideSiExiste(Nodo json, String claveCA, String claveCD) {
		try {
			Nodo n = json.obtener(claveCA);
			if (n == null || n.esObjeto() || n.esArreglo()) {
				return;
			}

			String valor = n.comoCadena();
			if (valor == null || valor.trim().isEmpty()) {
				return;
			}

			ConfigString.de(claveCD, "").escribir(valor.trim());
		} catch (Throwable t) {
		}
	}

	public Nodo buscarPorModid(Nodo arreglo, String modid) {
		if (arreglo == null || !arreglo.esArreglo() || modid == null) {
			return null;
		}

		for (int i = 0; i < arreglo.tamano(); i++) {
			Nodo n = arreglo.en(i);
			if (n == null || !n.esObjeto()) {
				continue;
			}

			Nodo m = n.obtener("modid");
			if (m == null || m.esObjeto() || m.esArreglo()) {
				continue;
			}

			String existente = m.comoCadena();
			if (modid.equalsIgnoreCase(existente)) {
				return n;
			}
		}

		return null;
	}

	public boolean simpleModeActivado() {
		try {
			if (!Files.exists(archivoConfigToml)) {
				return false;
			}

			Toml.Nodo raiz = Toml.leer(new String(Files.readAllBytes(archivoConfigToml), StandardCharsets.UTF_8));
			return leerBooleanoToml(raiz.obtener("simple_mode"), "enabled", false);
		} catch (Throwable t) {
			return false;
		}
	}

	public String avisoSiNoUsaPrimitiva() {
		return com.asbestosstar.crashdetector.MonitorDePID.idioma.migradorCAAvisoUrlSoporteWysiwyg();
	}

	public String avisoNoMigrado() {
		return com.asbestosstar.crashdetector.MonitorDePID.idioma.migradorCAAvisoNoMigrado();
	}

	public boolean leerBooleano(Nodo obj, String clave, boolean defecto) {
		try {
			Nodo n = obj.obtener(clave);
			if (n == null) {
				return defecto;
			}
			return n.comoBooleano();
		} catch (Throwable t) {
			return defecto;
		}
	}

	public String leerCadena(Nodo obj, String clave, String defecto) {
		try {
			Nodo n = obj.obtener(clave);
			if (n == null || n.esObjeto() || n.esArreglo()) {
				return defecto;
			}
			return n.comoCadena();
		} catch (Throwable t) {
			return defecto;
		}
	}

	public boolean leerBooleanoToml(Toml.Nodo obj, String clave, boolean defecto) {
		try {
			if (obj == null || !obj.esObjeto()) {
				return defecto;
			}
			return obj.obtener(clave).comoBooleano();
		} catch (Throwable t) {
			return defecto;
		}
	}

	public String leerCadenaToml(Toml.Nodo obj, String clave, String defecto) {
		try {
			if (obj == null || !obj.esObjeto()) {
				return defecto;
			}
			return obj.obtener(clave).comoCadena();
		} catch (Throwable t) {
			return defecto;
		}
	}

	public String convertirIdiomaA2Caracteres(String caLang) {
		if (caLang == null) {
			return "es";
		}

		String limpio = caLang.trim().toLowerCase();
		if (limpio.equals("none") || limpio.isEmpty()) {
			return "es";
		}

		int guion = limpio.indexOf('_');
		if (guion > 0) {
			return limpio.substring(0, guion);
		}

		if (limpio.length() >= 2) {
			return limpio.substring(0, 2);
		}

		return limpio;
	}

	public String MonitorCodigoIdiomaSeguro() {
		try {
			String codigo = com.asbestosstar.crashdetector.MonitorDePID.idioma.codigo();
			if (codigo == null || codigo.trim().isEmpty()) {
				return "es";
			}
			return convertirIdiomaA2Caracteres(codigo);
		} catch (Throwable t) {
			return "es";
		}
	}
}