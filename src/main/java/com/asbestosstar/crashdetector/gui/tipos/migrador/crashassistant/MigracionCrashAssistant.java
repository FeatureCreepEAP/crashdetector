package com.asbestosstar.crashdetector.gui.tipos.migrador.crashassistant;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.PirataMC;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.config.toml.Toml;

public class MigracionCrashAssistant {

	public Path archivoProblematicMods = Statics.carpeta.resolve("config/crash_assistant/problematic_mods_config.json");
	public Path archivoProblematicModsRespaldo = Statics.carpeta
			.resolve("config/crash_assistant/problematic_mods.json");
	public Path archivoConfigToml = Statics.carpeta.resolve("config/crash_assistant/config.toml");
	public Path archivoModsDesanimados = Statics.carpeta.resolve("mods_desanimados.json");

	public boolean migrarProblematicMods() {
		try {
			Path origen = Files.exists(archivoProblematicMods) ? archivoProblematicMods
					: archivoProblematicModsRespaldo;

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
		} catch (Exception e) {
			return false;
		}
	}

	public boolean migrarConfigToml(boolean usarPrimitiva) {
		try {
			if (!Files.exists(archivoConfigToml)) {
				return false;
			}

			Toml.Nodo raiz = Toml.leer(new String(Files.readAllBytes(archivoConfigToml), StandardCharsets.UTF_8));

			Toml.Nodo general = raiz.obtener("general");
			Toml.Nodo simpleMode = raiz.obtener("simple_mode");
			Toml.Nodo text = raiz.obtener("text");
			Toml.Nodo generatedMessage = raiz.obtener("generated_message");
			Toml.Nodo piracy = raiz.obtener("piracy");

			boolean simpleActivado = leerBooleanoToml(simpleMode, "enabled", false);

			if (usarPrimitiva || simpleActivado) {
				ConfigString.de("guitipo_principal", "estilo_lanzer").escribir("primitiva");
				String helpLink = leerCadenaToml(general, "help_link", "");
				if (helpLink != null && !helpLink.trim().isEmpty() && !"CHANGE_ME".equalsIgnoreCase(helpLink.trim())) {
					ConfigString.de("dialogo.compartir.primitiva.centro_soporte.url", "https://example.com/")
							.escribir(helpLink.trim());
				}
			}

			String defaultLang = leerCadenaToml(general, "default_lang", "");
			String codigoIdioma = convertirIdiomaA2Caracteres(defaultLang);
			if (codigoIdioma != null && !codigoIdioma.isEmpty()) {
				Idioma.idioma_respaldo.escribir(codigoIdioma);
			}

			String uploadTo = leerCadenaToml(general, "upload_to", "");
			if ("mclo.gs".equalsIgnoreCase(uploadTo) || "mclogs".equalsIgnoreCase(uploadTo)) {
				ConfigString.de("api_de_registro", "mclogs").escribir("mclogs");
			} else if ("gnomebot.dev".equalsIgnoreCase(uploadTo) || "gnomebotdev".equalsIgnoreCase(uploadTo)) {
				ConfigString.de("api_de_registro", "mclogs").escribir("gnomebotdev");
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
		} catch (Exception e) {
			return false;
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
		} catch (Exception e) {
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
		} catch (Exception e) {
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
		} catch (Exception e) {
			return defecto;
		}
	}

	public boolean leerBooleanoToml(Toml.Nodo obj, String clave, boolean defecto) {
		try {
			if (obj == null || !obj.esObjeto()) {
				return defecto;
			}
			return obj.obtener(clave).comoBooleano();
		} catch (Exception e) {
			return defecto;
		}
	}

	public String leerCadenaToml(Toml.Nodo obj, String clave, String defecto) {
		try {
			if (obj == null || !obj.esObjeto()) {
				return defecto;
			}
			return obj.obtener(clave).comoCadena();
		} catch (Exception e) {
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
}