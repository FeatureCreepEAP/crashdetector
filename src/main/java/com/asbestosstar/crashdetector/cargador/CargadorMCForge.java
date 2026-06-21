package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.cargador.ml.AnalizadorModsTomlForge;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Es para MinecraftForge, no para JBoss Forge.
 */
public class CargadorMCForge implements Cargador {

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		for (String archivo : mod.archivos()) {
			if (archivo == null || archivo.isEmpty()) {
				continue;
			}

			String ruta = archivo.indexOf('\\') >= 0 ? archivo.replace('\\', '/') : archivo;

			if (ruta.equals("mcmod.info") || ruta.equals("META-INF/mcmod.info") || ruta.equals("META-INF/mods.toml")) {
				return true;
			}

			if (ruta.startsWith("META-INF/services/")) {
				String servicio = ruta.substring("META-INF/services/".length());

				if (servicio.startsWith("cpw") || servicio.startsWith("net.minecraftforge")) {
					return true;
				}
			}
		}

		return mod.ubicacion_para_publicar().contains("kotlinforforge");
	}

	@Override
	public boolean cargadorEsActivado() {

		// Forge moderno usando ModLauncher propio de MinecraftForge
		boolean forgeModLauncher = !Cargador.claseExiste("cpw.mods.modlauncher.api.TargetType")
				&& !Cargador.claseExiste("net.neoforged.neoforgespi.transformation.ClassProcessor")
				&& Cargador.claseExiste("cpw.mods.modlauncher.api.ITransformationService");

		// Forge clasico:
		// - cpw.mods.fml.relauncher.IFMLLoadingPlugin -> Forge viejo
		// - net.minecraftforge.fml.relauncher.IFMLLoadingPlugin -> Forge mas nuevo
		// pre-ModLauncher
		boolean forgeLegacy = Cargador.claseExiste("cpw.mods.fml.relauncher.IFMLLoadingPlugin")
				|| Cargador.claseExiste("net.minecraftforge.fml.relauncher.IFMLLoadingPlugin");

		return forgeModLauncher || forgeLegacy;
	}

	@Override
	public String id() {
		return "minecraftforge";
	}

	/**
	 * Extrae los modId desde un archivo mods.toml de Forge.
	 */
	public static List<String> parsearIdModMCForge(String toml) throws IOException {
		return AnalizadorModsTomlForge.extraerModIds(toml);
	}

	/**
	 * Extrae los modid desde un archivo mcmod.info.
	 *
	 * mcmod.info suele ser un arreglo de objetos, aunque a veces puede venir como
	 * objeto unico. Se devuelve una lista de ids unicos.
	 */
	public static List<String> parsearIdModMcmodInfo(String texto) throws IOException {
		List<String> salida = new ArrayList<>();

		try {
			Json.Nodo raiz = Json.leer(texto);

			// Caso mas comun: arreglo
			if (raiz.esArreglo()) {
				for (int i = 0; i < raiz.tamano(); i++) {
					Json.Nodo entrada = raiz.en(i);
					if (entrada == null) {
						continue;
					}

					Json.Nodo modidNodo = entrada.obtener("modid");
					if (modidNodo == null) {
						continue;
					}

					String modid = modidNodo.comoCadena();
					if (modid != null) {
						modid = modid.trim();
						if (!modid.isEmpty() && !salida.contains(modid)) {
							salida.add(modid);
						}
					}
				}
			} else {
				// Respaldo: objeto unico
				Json.Nodo modidNodo = raiz.obtener("modid");
				if (modidNodo != null) {
					String modid = modidNodo.comoCadena();
					if (modid != null) {
						modid = modid.trim();
						if (!modid.isEmpty() && !salida.contains(modid)) {
							salida.add(modid);
						}
					}
				}
			}

			return salida;
		} catch (Throwable t) {
			// Respaldo por regex
			Pattern pmodid = Pattern.compile("\"modid\"\\s*:\\s*\"([^\"]+)\"");
			Matcher mmodid = pmodid.matcher(texto);

			while (mmodid.find()) {
				String modid = mmodid.group(1).trim();
				if (!modid.isEmpty() && !salida.contains(modid)) {
					salida.add(modid);
				}
			}

			return salida;
		}
	}

	/**
	 * Extrae la version desde un archivo mcmod.info.
	 *
	 * Si hay varias entradas, devuelve la primera version no vacia encontrada.
	 */
	public static String parsearVersionModMcmodInfo(String texto) {
		try {
			Json.Nodo raiz = Json.leer(texto);

			// Caso mas comun: arreglo
			if (raiz.esArreglo()) {
				for (int i = 0; i < raiz.tamano(); i++) {
					Json.Nodo entrada = raiz.en(i);
					if (entrada == null) {
						continue;
					}

					Json.Nodo versionNodo = entrada.obtener("version");
					if (versionNodo == null) {
						continue;
					}

					String version = versionNodo.comoCadena();
					if (version != null) {
						version = version.trim();
						if (!version.isEmpty()) {
							return version;
						}
					}
				}
			} else {
				// Respaldo: objeto unico
				Json.Nodo versionNodo = raiz.obtener("version");
				if (versionNodo != null) {
					String version = versionNodo.comoCadena();
					if (version != null) {
						version = version.trim();
						if (!version.isEmpty()) {
							return version;
						}
					}
				}
			}
		} catch (Throwable t) {
			// Si falla Json, continuar con regex
		}

		// Respaldo por regex
		Pattern pverCadena = Pattern.compile("\"version\"\\s*:\\s*\"([^\"]+)\"");
		Matcher mverCadena = pverCadena.matcher(texto);

		if (mverCadena.find()) {
			String version = mverCadena.group(1).trim();
			if (!version.isEmpty()) {
				return version;
			}
		}

		Pattern pverNumero = Pattern.compile("\"version\"\\s*:\\s*([-+]?[0-9]+(?:\\.[0-9]+)?)");
		Matcher mverNumero = pverNumero.matcher(texto);

		if (mverNumero.find()) {
			String version = mverNumero.group(1).trim();
			if (!version.isEmpty()) {
				return version;
			}
		}

		return "";
	}

	public boolean suporteModsDeCarpetas() {
		return true;
	}

}