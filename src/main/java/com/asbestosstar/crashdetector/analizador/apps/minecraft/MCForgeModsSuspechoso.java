package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta mods sospechosos y excepciones relacionadas con Forge.
 */
public class MCForgeModsSuspechoso implements Verificaciones {

	private boolean activado = false;

	private static class EstadoConsola {
		boolean posibleForgeModSospechoso = false;
		boolean encontradoModSospechoso = false;
		boolean encontradoStacktrace = false;
		boolean encontradoModSection = false;
	}

	private final Map<Consola, EstadoConsola> estadosPorConsola = new HashMap<>();

	private final Set<String> errores = new HashSet<>();
	private final Map<String, String> enlacesPorError = new HashMap<>();
	private final Map<String, String> modidPorError = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		EstadoConsola estado = obtenerEstado(consola);
		String c = consola.contenido_verificar;

		estado.posibleForgeModSospechoso = c.contains("Suspected Mod:") || c.contains("Suspected Mods:")
				|| c.contains("-- MOD ") || c.contains("Failed to create mod instance. ModID:")
				|| c.contains("for modid ") || c.contains("Mod ID:") || c.contains("ModID:") || c.contains("modid=")
				|| (c.contains("dispatch") && (c.contains("encountered an error")
						|| c.contains("encountered an exception") || c.contains("caught exception")));
	}

	@Override
	public void verificarPorLinea(Consola consola, String lineaOriginal, int numero_de_linea) {
		if (consola == null || lineaOriginal == null) {
			return;
		}

		EstadoConsola estado = estadosPorConsola.get(consola);
		if (estado == null) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso: estado null en linea " +
			// numero_de_linea);
			return;
		}

		if (!estado.posibleForgeModSospechoso) {
			return;
		}

		String linea = lineaOriginal.trim();

		if (linea.isEmpty()) {
			return;
		}

		String modDeFilaSuspected = extraerFilaSuspectedModDirecta(linea);
		if (modDeFilaSuspected != null) {
			registrarMod(consola, numero_de_linea, modDeFilaSuspected);
			return;
		}

		// CrashDetectorLogger.log("MCForgeModsSuspechoso linea " + numero_de_linea + ":
		// " + linea);

		if (esLineaDebugOTrace(linea)) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso saltada DEBUG/TRACE");
			return;
		}

		String modDeSeccion = extraerSeccionMod(linea);
		if (modDeSeccion != null) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso MOD section detectada: " +
			// modDeSeccion);
			estado.encontradoModSection = true;
			registrarMod(consola, numero_de_linea, modDeSeccion);
			return;
		}

		if (linea.contains("Suspected Mod:") || linea.contains("Suspected Mods:")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso ENTRA en Suspected Mods en
			// linea " + numero_de_linea);
			estado.encontradoModSospechoso = true;
			estado.encontradoStacktrace = false;
			return;
		}

		if (linea.contains("Stacktrace:")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SALE de Suspected Mods por
			// Stacktrace en linea " + numero_de_linea);
			estado.encontradoModSospechoso = false;
			estado.encontradoStacktrace = true;
			return;
		}

		if (estado.encontradoModSospechoso) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso procesando linea dentro de
			// Suspected Mods: " + linea);
			procesarLineaSuspectedMods(consola, linea, numero_de_linea);
			return;
		}

		if (linea.contains("Failed to create mod instance. ModID:")) {
			String prefijo = "Failed to create mod instance. ModID: ";
			int indiceInicio = linea.indexOf(prefijo);

			// CrashDetectorLogger.log("MCForgeModsSuspechoso Failed to create mod instance
			// detectado");

			if (indiceInicio != -1) {
				String modID = extraerToken(linea.substring(indiceInicio + prefijo.length()).trim());
				// CrashDetectorLogger.log("MCForgeModsSuspechoso modID extraido de Failed
				// instance: " + modID);
				registrarMod(consola, numero_de_linea, modID);
			}

			return;
		}

		String modDespacho = extraerDespachoModid(linea);
		if (modDespacho != null) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso mod despacho detectado: " +
			// modDespacho);
			registrarMod(consola, numero_de_linea, modDespacho);
			return;
		}

		if (!estado.encontradoModSection && tieneIndicadorError(linea)) {
			String modID = extraerModidDeLinea(linea, false);
			// CrashDetectorLogger.log("MCForgeModsSuspechoso fallback indicador error,
			// modID=" + modID + ", linea=" + linea);
			registrarMod(consola, numero_de_linea, modID);
		}
	}

	private void procesarLineaSuspectedMods(Consola consola, String linea, int numeroDeLinea) {
		if (linea == null) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods: linea null");
			return;
		}

		linea = linea.trim();

		// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods analiza: " +
		// linea);

		if (linea.isEmpty()) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods saltada:
			// vacia");
			return;
		}

		if (linea.startsWith("--")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods saltada: empieza
			// con --");
			return;
		}

		if (linea.startsWith("Details:")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods saltada:
			// Details");
			return;
		}

		if (linea.startsWith("Issue tracker")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods saltada: Issue
			// tracker");
			return;
		}

		if (linea.startsWith("at ")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods saltada: at");
			return;
		}

		if (linea.startsWith("Mixin class:")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods saltada: Mixin
			// class");
			return;
		}

		if (linea.startsWith("Target:")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods saltada:
			// Target");
			return;
		}

		if (linea.startsWith("Version:")) {
			// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods saltada: Version
			// directa");
			return;
		}

		String modID = extraerSuspectedMod(linea);

		// CrashDetectorLogger.log("MCForgeModsSuspechoso SuspectedMods modID extraido:
		// " + modID);

		if (modID != null) {
			registrarMod(consola, numeroDeLinea, modID);
		}
	}

	private static String extraerFilaSuspectedModDirecta(String linea) {
		if (linea == null) {
			return null;
		}

		String s = linea.trim();

		// Fila real:
		// Oculus (oculus), Version: 1.8.0
		// Create: Power Grid (powergrid), Version: 0∙5∙5∙1
		// Create (create), Version: 6.0.8
		if (!s.contains("), Version:")) {
			return null;
		}

		int comaVersion = s.indexOf("), Version:");
		if (comaVersion <= 0) {
			return null;
		}

		int cierre = comaVersion;

		int abre = s.lastIndexOf('(', cierre);
		if (abre < 0 || cierre <= abre + 1) {
			return null;
		}

		String modID = s.substring(abre + 1, cierre).trim();

		if (!esModIdValidoReal(modID)) {
			return null;
		}

		return modID;
	}

	private EstadoConsola obtenerEstado(Consola consola) {
		EstadoConsola estado = estadosPorConsola.get(consola);

		if (estado == null) {
			estado = new EstadoConsola();
			estadosPorConsola.put(consola, estado);
		}

		return estado;
	}

	private void registrarMod(Consola consola, int numeroDeLinea, String modID) {
		// CrashDetectorLogger.log("registrarMod llamado con modID=" + modID + " linea="
		// + numeroDeLinea);

		if (!esModIdValidoReal(modID)) {
			// CrashDetectorLogger.log("registrarMod rechazado por esModIdValidoReal: " +
			// modID);
			return;
		}

		String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;

		if (errores.add(mensaje)) {
			String enlace = consola.agregarErrorALectador(numeroDeLinea, this);
			enlacesPorError.put(mensaje, enlace);
			modidPorError.put(mensaje, modID);
			// CrashDetectorLogger.log("registrarMod agregado: " + modID);
		} else {
			// CrashDetectorLogger.log("registrarMod duplicado: " + modID);
		}

		activado = true;
	}

	private static boolean esModIdValidoReal(String modID) {
		if (modID == null) {
			return false;
		}

		String m = modID.trim();

		if (m.isEmpty() || m.contains(".java")) {
			return false;
		}

		if (m.equalsIgnoreCase("unknown") || m.equalsIgnoreCase("ghz") || m.equalsIgnoreCase("v1")) {
			return false;
		}

		for (int i = 0; i < m.length(); i++) {
			char c = m.charAt(i);

			boolean valido = Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == '+';

			if (!valido) {
				return false;
			}
		}

		return true;
	}

	private static boolean esLineaDebugOTrace(String linea) {
		if (!linea.startsWith("[")) {
			return false;
		}

		int corchete = linea.indexOf(']');
		if (corchete == -1) {
			return false;
		}

		String cabecera = linea.substring(0, corchete);
		return cabecera.contains("/DEBUG") || cabecera.contains("/TRACE");
	}

	private static String extraerForModid(String linea) {
		String lower = linea.toLowerCase();
		int idx = lower.indexOf("for modid ");

		if (idx == -1) {
			return null;
		}

		return extraerToken(linea.substring(idx + "for modid ".length()).trim());
	}

	private static String extraerModIdDosPuntos(String linea) {
		String lower = linea.toLowerCase();

		int idx = lower.indexOf("mod id:");
		if (idx == -1) {
			idx = lower.indexOf("modid:");
		}

		if (idx == -1) {
			return null;
		}

		int colon = linea.indexOf(':', idx);
		if (colon == -1) {
			return null;
		}

		return extraerToken(linea.substring(colon + 1).trim());
	}

	private static String extraerModidIgual(String linea) {
		String lower = linea.toLowerCase();
		int idx = lower.indexOf("modid=");

		if (idx == -1) {
			return null;
		}

		return extraerToken(linea.substring(idx + "modid=".length()));
	}

	private static String extraerSeccionMod(String linea) {
		if (linea == null) {
			return null;
		}

		String limpia = linea.trim();

		if (!limpia.startsWith("--") || !limpia.endsWith("--")) {
			return null;
		}

		String interior = limpia.substring(2, limpia.length() - 2).trim();
		String lower = interior.toLowerCase();

		// NeoForge crash report section:
		// -- Mod loading issue for: crashdetector_tutorial_extention_english --
		if (lower.startsWith("mod loading issue for:")) {
			String modID = extraerToken(interior.substring("Mod loading issue for:".length()).trim());

			if (esModIdValidoReal(modID)) {
				return modID;
			}

			return null;
		}

		// Generic NeoForge section sin mod id:
		// -- Mod loading issue --
		// This must NOT become "loading".
		if (lower.equals("mod loading issue") || lower.startsWith("mod loading issue ")) {
			return null;
		}

		// Normal Forge section:
		// -- MOD oculus --
		// -- Mod create --
		if (!lower.startsWith("mod ")) {
			return null;
		}

		String modID = extraerToken(interior.substring(4).trim());

		if (!esModIdValidoReal(modID)) {
			return null;
		}

		return modID;
	}

	private static String extraerSuspectedMod(String linea) {
		if (linea == null) {
			// CrashDetectorLogger.log("extraerSuspectedMod: linea null");
			return null;
		}

		String s = linea.trim();

		// CrashDetectorLogger.log("extraerSuspectedMod entrada: " + s);

		if (s.isEmpty()) {
			// CrashDetectorLogger.log("extraerSuspectedMod: vacia");
			return null;
		}

		if (!s.contains("Version:")) {
			// CrashDetectorLogger.log("extraerSuspectedMod: no contiene Version:");
			return null;
		}

		int cierre = s.indexOf(')');
		// CrashDetectorLogger.log("extraerSuspectedMod cierre=" + cierre);

		if (cierre < 0) {
			// CrashDetectorLogger.log("extraerSuspectedMod: no encontro )");
			return null;
		}

		int abre = s.lastIndexOf('(', cierre);
		// CrashDetectorLogger.log("extraerSuspectedMod abre=" + abre);

		if (abre < 0 || cierre <= abre + 1) {
			// CrashDetectorLogger.log("extraerSuspectedMod: parentesis invalidos");
			return null;
		}

		String modID = s.substring(abre + 1, cierre).trim();

		// CrashDetectorLogger.log("extraerSuspectedMod candidato=" + modID);

		if (!esModIdValidoReal(modID)) {
			// CrashDetectorLogger.log("extraerSuspectedMod: candidato invalido=" + modID);
			return null;
		}

		// CrashDetectorLogger.log("extraerSuspectedMod OK=" + modID);
		return modID;
	}

	private static String extraerDespachoModid(String linea) {
		String lower = linea.toLowerCase();

		boolean tieneDespacho = lower.contains("encountered an error") || lower.contains("encountered an exception")
				|| lower.contains("caught exception");

		if (!tieneDespacho || !lower.contains("dispatch")) {
			return null;
		}

		return extraerForModid(linea);
	}

	private static boolean tieneIndicadorError(String linea) {
		String lower = linea.toLowerCase();

		return lower.contains("error") || lower.contains("exception") || lower.contains("fail")
				|| lower.contains("crash") || lower.contains("problem") || lower.contains("unable")
				|| lower.contains("cannot") || lower.contains("didn't") || lower.contains("couldn't")
				|| lower.contains("missing") || lower.contains("corrupt") || lower.contains("invalid")
				|| lower.contains("unsupported");
	}

	private static String extraerModidDeLinea(String linea, boolean esSeccionSuspectedMod) {
		if (linea == null || linea.contains("Object with ID ") || linea.isEmpty()) {
			return null;
		}

		if (esSeccionSuspectedMod && linea.contains("(") && linea.contains(")")) {
			String m = extraerSuspectedMod(linea);

			if (m != null) {
				return m;
			}
		}

		String m;

		m = extraerSeccionMod(linea);
		if (m != null) {
			return m;
		}

		m = extraerForModid(linea);
		if (m != null) {
			return m;
		}

		m = extraerModIdDosPuntos(linea);
		if (m != null) {
			return m;
		}

		m = extraerModidIgual(linea);
		if (m != null) {
			return m;
		}

		return null;
	}

	private static String extraerToken(String texto) {
		if (texto == null || texto.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);

			if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == '+') {
				sb.append(c);
			} else {
				break;
			}
		}

		String resultado = sb.toString();
		return resultado.isEmpty() ? null : resultado;
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
		if (errores.isEmpty()) {
			return "";
		}

		StringBuilder html = new StringBuilder("<ul>");

		for (String error : errores) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			String extraUbicaciones = "";

			String modid = modidPorError.get(error);

			if (modid != null && !modid.isEmpty()) {
				List<String> ubicaciones = Buscador.obtenerModsConNombre(modid);

				if (!ubicaciones.isEmpty()) {
					StringBuilder sb = new StringBuilder();

					for (int i = 0; i < ubicaciones.size(); i++) {
						if (i > 0) {
							sb.append(", ");
						}

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
		return new QuickFix.Builder(nombre()).agregarEtiqueta("No hay solución disponible").construir();
	}

	@Override
	public String id() {
		return "mcforge_mods_sospechoso";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}