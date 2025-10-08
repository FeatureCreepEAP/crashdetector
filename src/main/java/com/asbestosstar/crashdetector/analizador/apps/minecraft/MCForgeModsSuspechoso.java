package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Detecta mods sospechosos y excepciones relacionadas con Forge.
 *
 * Créditos y agradecimiento: algunas heurísticas y patrones se inspiran en el
 * trabajo de Aternos (codex-minecraft).
 * https://github.com/aternosorg/codex-minecraft
 */
public class MCForgeModsSuspechoso implements Verificaciones {

	private boolean activado = false;

	/**
	 * Conjunto de mensajes finales únicos que se mostrarán.
	 */
	private final Set<String> errores = new HashSet<>();

	/**
	 * Enlace HTML (ancla) por mensaje final.
	 */
	private final Map<String, String> enlacesPorError = new HashMap<>();

	/**
	 * ModID asociado a cada mensaje final (cuando aplique). Sirve para que el
	 * buscador encuentre las ubicaciones del mod.
	 */
	private final Map<String, String> modidPorError = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Patrones auxiliares para extraer modid en bloques cercanos
		Pattern pEncontradoDurante = Pattern.compile(
				"(?i)(?:encountered\\s+an\\s+(?:error|exception)|caught\\s+exception)\\s+during\\s+[^\\r\\n]*");
		Pattern pForModidInline = Pattern.compile("(?i)for\\s+modid\\s+([a-z0-9_\\-.]+)");
		Pattern pModIdColon = Pattern.compile("(?i)\\bMod\\s*ID\\s*:\\s*([a-z0-9_\\-.]+)");
		Pattern pModidColonEq = Pattern.compile("(?i)\\bmodid\\s*[:=]\\s*([a-z0-9_\\-.]+)");
		Pattern pAffectedMod = Pattern.compile("(?i)\\bAffected\\s+mod\\s*:\\s*([a-z0-9_\\-.]+)");
		Pattern pModidEq = Pattern.compile("(?i)\\bmodid=([a-z0-9_\\-.]+)");

		// Patrón directo: “... dispatch for modid <id>”
		Pattern patronDespachoModid = Pattern.compile(
				"(?i)(?:encountered\\s+an\\s+(?:error|exception)|caught\\s+exception)\\s+during\\s+.*?dispatch\\s+for\\s+modid\\s+([a-z0-9_\\-.]+)");

		// “Caught exception from <Nombre legible>”
		Pattern pCaughtFrom = Pattern.compile("(?i)Caught\\s+exception\\s+from\\s+([^\\(\\n]+)");

		// ModLoadingException multilinea
		Pattern pModLoading = Pattern.compile(
				"ModLoadingException:\\s+([^\\(\\n]+?)\\s*\\(([^\\)\\n]+)\\)\\s+encountered\\s+an\\s+error\\s+during\\s+the\\s+([a-zA-Z_]+)\\s+event\\s+phase",
				Pattern.CASE_INSENSITIVE);

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];

			// 1) Bloque "Suspected Mod:"
			if (linea.contains("Suspected Mod:")) {
				if (i + 1 < lineas.length) {
					String modLinea = lineas[i + 1].trim();
					int inicio = modLinea.indexOf('(');
					int fin = modLinea.indexOf(')');
					String modID = modLinea;
					if (inicio != -1 && fin != -1 && inicio < fin) {
						modID = modLinea.substring(inicio + 1, fin).trim();
					}
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID.trim();
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i + 1, this);
						enlacesPorError.put(mensaje, enlace);
						modidPorError.put(mensaje, modID.trim());
					}
					activado = true;
				}
				continue;
			}

			// 2) "Failed to create mod instance. ModID: <id>"
			if (linea.contains("Failed to create mod instance. ModID:")) {
				try {
					String prefijo = "Failed to create mod instance. ModID: ";
					int indiceInicio = linea.indexOf(prefijo);
					if (indiceInicio != -1) {
						indiceInicio += prefijo.length();
						String resto = linea.substring(indiceInicio).trim();
						StringBuilder sb = new StringBuilder();
						int j = 0;
						while (j < resto.length()) {
							char c = resto.charAt(j);
							if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == '+') {
								sb.append(c);
								j++;
							} else {
								break;
							}
						}
						String modID = sb.toString().trim();
						if (!modID.isEmpty()) {
							String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
							if (errores.add(mensaje)) {
								String enlace = consola.agregarErrorALectador(i, this);
								enlacesPorError.put(mensaje, enlace);
								modidPorError.put(mensaje, modID);
							}
							activado = true;
						}
					}
				} catch (Exception e) {
					consola.agregarErrorALectador(i, this);
				}
				continue;
			}

			// 3) "Failure message: (modid)"
			if (linea.contains("Failure message:")) {
				int inicio = linea.indexOf('(');
				if (inicio != -1) {
					int fin = linea.indexOf(')', inicio + 1);
					if (fin != -1) {
						String modID = linea.substring(inicio + 1, fin).trim();
						String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
						if (errores.add(mensaje)) {
							String enlace = consola.agregarErrorALectador(i, this);
							enlacesPorError.put(mensaje, enlace);
							modidPorError.put(mensaje, modID);
						}
						activado = true;
					}
				}
			}

			// 4) “… dispatch for modid <id>”
			Matcher matcherDespacho = patronDespachoModid.matcher(linea);
			while (matcherDespacho.find()) {
				String modID = matcherDespacho.group(1).trim();
				String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
				if (errores.add(mensaje)) {
					String enlace = consola.agregarErrorALectador(i, this);
					enlacesPorError.put(mensaje, enlace);
					modidPorError.put(mensaje, modID);
				}
				activado = true;
			}

			// 5) Bloques “Encountered an error/exception …” o “Caught exception during …”
			Matcher mEnc = pEncontradoDurante.matcher(linea);
			if (mEnc.find()) {
				String modID = null;

				// intento inline “for modid <id>”
				Matcher mInline = pForModidInline.matcher(linea);
				if (mInline.find()) {
					modID = mInline.group(1).trim();
				}

				// si no está inline, buscarlo dentro de un bloque siguiente
				if (modID == null) {
					int ventana = Math.min(lineas.length, i + 25);
					for (int k = i; k < ventana; k++) {
						String l2 = lineas[k];

						Matcher mId1 = pModIdColon.matcher(l2);
						if (mId1.find()) {
							modID = mId1.group(1).trim();
							i = k;
							break;
						}
						Matcher mId2 = pModidColonEq.matcher(l2);
						if (mId2.find()) {
							modID = mId2.group(1).trim();
							i = k;
							break;
						}
						Matcher mId3 = pAffectedMod.matcher(l2);
						if (mId3.find()) {
							modID = mId3.group(1).trim();
							i = k;
							break;
						}
						Matcher mId4 = pModidEq.matcher(l2);
						if (mId4.find()) {
							modID = mId4.group(1).trim();
							i = k;
							break;
						}
					}
				}

				if (modID != null && !modID.isEmpty()) {
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
						modidPorError.put(mensaje, modID);
					}
					activado = true;
				}
			}

			// 6) “Caught exception from <Nombre legible>” (no siempre hay modid)
			if (linea.toLowerCase().contains("caught exception from ")) {
				Matcher m = pCaughtFrom.matcher(linea);
				if (m.find()) {
					String nombreLegible = m.group(1).trim();
					if (!nombreLegible.isEmpty()) {
						String mensaje = MonitorDePID.idioma.mensajeModExcepcionSingular(nombreLegible);
						if (errores.add(mensaje)) {
							String enlace = consola.agregarErrorALectador(i, this);
							enlacesPorError.put(mensaje, enlace);
						}
						activado = true;
					}
				}
			}

			// 7) ModLoadingException (escaneo con ventana pequeña)
			if (linea.contains("ModLoadingException:")) {
				StringBuilder ventana = new StringBuilder(linea);
				for (int k = 1; k <= 4 && i + k < lineas.length; k++) {
					ventana.append('\n').append(lineas[i + k]);
				}
				Matcher m = pModLoading.matcher(ventana.toString());
				if (m.find()) {
					String nombreLegible = m.group(1).trim();
					String modid = m.group(2).trim();
					String etiqueta = (modid.isEmpty() ? nombreLegible : modid);
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + etiqueta;

					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
						if (!modid.isEmpty()) {
							modidPorError.put(mensaje, modid);
						}
					}
					activado = true;
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new MCForgeModsSuspechoso();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f;
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String error : errores) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			String extraUbicaciones = "";

			// Si hay modid asociado al mensaje, consultar las ubicaciones por nombre
			// (modid)
			String modid = modidPorError.get(error);
			if (modid != null && !modid.isEmpty()) {
				List<String> ubicaciones = Buscardor.obtenerModsConNombre(modid);
				if (!ubicaciones.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < ubicaciones.size(); i++) {
						if (i > 0)
							sb.append(", ");
						sb.append(ubicaciones.get(i));
					}
					extraUbicaciones = " (" + sb + ")";
				}
			}

			html.append("<li>").append(error).append(extraUbicaciones);
			if (!enlace.isEmpty()) {
				html.append(" ").append(enlace);
			}
			html.append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_mcforge_mod_sespechoso();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "mcforge_mods_suspechoso";
	}




}
