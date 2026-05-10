package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta mods sospechosos y excepciones relacionadas con Forge.
 *
 * Versión optimizada: - Usa verificación global ligera. - Usa verificación por
 * línea para agregar enlace exacto. - No usa Pattern/Matcher. - No hace split
 * local del log completo.
 */
public class MCForgeModsSuspechoso implements Verificaciones {

	private boolean posibleForgeModSospechoso = false;
	private boolean activado = false;

	private boolean encontradoModSospechoso = false;
	private boolean encontradoModSection = false;

	// Conjunto para almacenar los mensajes de error únicos
	private final Set<String> errores = new HashSet<>();

	// Mapas para almacenar enlaces y mod IDs asociados con cada error
	private final Map<String, String> enlacesPorError = new HashMap<>();
	private final Map<String, String> modidPorError = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		// Detección global ligera.
		// No se limpian estructuras aquí porque esta verificación puede ejecutarse
		// sobre varios archivos de log con la misma instancia.
		if (contenido.contains("Suspected Mod:") || contenido.contains("Suspected Mods:")
				|| contenido.contains("-- MOD ") || contenido.contains("Failed to create mod instance. ModID:")
				|| contieneIgnoreCase(contenido, "for modid ") || contenido.contains("Mod ID:")
				|| contenido.contains("modid=")) {
			posibleForgeModSospechoso = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!posibleForgeModSospechoso || linea == null) {
			return;
		}

		String línea = linea.trim();

		if (línea.isEmpty()) {
			return;
		}

		// Saltar líneas de nivel DEBUG/TRACE
		if (esLineaDebugOTrace(línea)) {
			return;
		}

		// 1) Detectar secciones -- MOD modid --
		String modIDSeccion = extraerModidDeSeccionMod(línea);
		if (esModidValido(modIDSeccion)) {
			encontradoModSection = true;
			registrarModSospechoso(consola, numero_de_linea, modIDSeccion);
			return;
		}

		// 2) Detectar sección "Suspected Mods:" o "Suspected Mod:"
		if (línea.equalsIgnoreCase("Suspected Mod:") || línea.equalsIgnoreCase("Suspected Mods:")) {
			encontradoModSospechoso = true;
			return;
		}

		// 3) Terminar la sección al encontrar "Stacktrace:"
		if (línea.indexOf("Stacktrace:") >= 0) {
			encontradoModSospechoso = false;
			return;
		}

		// 4) Procesar los mods dentro de "Suspected Mods:"
		if (encontradoModSospechoso) {
			if (línea.startsWith("--") || línea.indexOf("Details:") >= 0) {
				return;
			}

			// En el formato por línea ya no se necesita saltar manualmente las líneas
			// Mixin class / Target / at TRANSFORMER. Simplemente se ignoran aquí.
			if (línea.startsWith("Mixin class:") || línea.startsWith("Target:")
					|| línea.startsWith("at TRANSFORMER/")) {
				return;
			}

			String modID = extraerModidDeSuspectedMod(línea);

			if (!esModidValido(modID)) {
				modID = extraerModidDeLinea(línea, true);
			}

			if (esModidValido(modID)) {
				registrarModSospechoso(consola, numero_de_linea, modID);
			}

			return;
		}

		// 5) Detectar "Failed to create mod instance"
		if (línea.indexOf("Failed to create mod instance. ModID:") >= 0) {
			String modID = extraerDespuesDeTexto(línea, "Failed to create mod instance. ModID:", false);

			if (esModidValido(modID)) {
				registrarModSospechoso(consola, numero_de_linea, modID);
			}

			return;
		}

		// 6) Detectar dispatch para modid
		String modIDDespacho = extraerModidDeDespacho(línea);
		if (esModidValido(modIDDespacho)) {
			registrarModSospechoso(consola, numero_de_linea, modIDDespacho);
			return;
		}

		// 7) Otros errores con indicadores, fuera de secciones específicas
		if (!encontradoModSection && contieneIndicadorError(línea)) {
			String modID = extraerModidDeLinea(línea, false);

			if (esModidValido(modID)) {
				registrarModSospechoso(consola, numero_de_linea, modID);
			}
		}
	}

	/**
	 * Detecta líneas tipo: [main/DEBUG] ... [Render thread/TRACE] ...
	 */
	private boolean esLineaDebugOTrace(String linea) {
		if (!linea.startsWith("[")) {
			return false;
		}

		int cierre = linea.indexOf(']');
		if (cierre <= 0) {
			return false;
		}

		int barra = linea.lastIndexOf('/', cierre);
		if (barra < 0 || barra + 1 >= cierre) {
			return false;
		}

		String nivel = linea.substring(barra + 1, cierre);
		return nivel.equalsIgnoreCase("DEBUG") || nivel.equalsIgnoreCase("TRACE");
	}

	/**
	 * Extrae modid de líneas tipo: -- MOD modid --
	 */
	private String extraerModidDeSeccionMod(String linea) {
		if (!empiezaConIgnoreCase(linea, "-- MOD ")) {
			return null;
		}

		int inicio = "-- MOD ".length();
		int fin = linea.indexOf("--", inicio);

		if (fin < 0) {
			fin = linea.length();
		}

		return limpiarModid(linea.substring(inicio, fin).trim());
	}

	/**
	 * Extrae el modid de una línea de la sección "Suspected Mods".
	 *
	 * Ejemplos: Knight Lib (knightlib), Version: 1.4.2 Fabric Item Group API (v1)
	 * (fabric_item_group_api_v1)
	 *
	 * Si hay dos grupos entre paréntesis, usa el último.
	 */
	private String extraerModidDeSuspectedMod(String linea) {
		if (linea == null || linea.indexOf('(') < 0 || linea.indexOf(')') < 0) {
			return null;
		}

		int cierre = linea.lastIndexOf(')');
		int apertura = linea.lastIndexOf('(', cierre);

		if (apertura < 0 || cierre <= apertura + 1) {
			return null;
		}

		String modID = linea.substring(apertura + 1, cierre).trim();
		return limpiarModid(modID);
	}

	/**
	 * Extrae el modid de una línea usando búsquedas manuales.
	 */
	private String extraerModidDeLinea(String linea, boolean esSeccionSuspectedMod) {
		if (linea == null || linea.indexOf("Object with ID ") >= 0 || linea.isEmpty()) {
			return null;
		}

		if (esSeccionSuspectedMod) {
			String modIDSospechoso = extraerModidDeSuspectedMod(linea);
			if (esModidValido(modIDSospechoso)) {
				return modIDSospechoso;
			}
		}

		String modID = extraerModidDeSeccionMod(linea);
		if (esModidValido(modID)) {
			return modID;
		}

		modID = extraerDespuesDeTexto(linea, "for modid ", true);
		if (esModidValido(modID)) {
			return modID;
		}

		modID = extraerDespuesDeTexto(linea, "Mod ID:", true);
		if (esModidValido(modID)) {
			return modID;
		}

		modID = extraerDespuesDeTexto(linea, "modid=", true);
		if (esModidValido(modID)) {
			return modID;
		}

		return null;
	}

	/**
	 * Detecta líneas tipo: encountered an error during ... dispatch for modid
	 * examplemod encountered an exception during ... dispatch for modid examplemod
	 * caught exception during ... dispatch for modid examplemod
	 */
	private String extraerModidDeDespacho(String linea) {
		if (linea == null || linea.isEmpty()) {
			return null;
		}

		if (!contieneIgnoreCase(linea, "dispatch")) {
			return null;
		}

		if (!contieneIgnoreCase(linea, "for modid ")) {
			return null;
		}

		boolean inicioValido = contieneIgnoreCase(linea, "encountered an error")
				|| contieneIgnoreCase(linea, "encountered an exception")
				|| contieneIgnoreCase(linea, "caught exception");

		if (!inicioValido) {
			return null;
		}

		return extraerDespuesDeTexto(linea, "for modid ", true);
	}

	/**
	 * Extrae el primer token válido de modid después de cierto texto.
	 */
	private String extraerDespuesDeTexto(String linea, String texto, boolean ignorarMayusculas) {
		if (linea == null || texto == null) {
			return null;
		}

		int indice = ignorarMayusculas ? indexOfIgnoreCase(linea, texto) : linea.indexOf(texto);

		if (indice < 0) {
			return null;
		}

		int inicio = indice + texto.length();

		while (inicio < linea.length() && Character.isWhitespace(linea.charAt(inicio))) {
			inicio++;
		}

		if (inicio >= linea.length()) {
			return null;
		}

		int fin = inicio;

		while (fin < linea.length() && esCaracterDeModid(linea.charAt(fin))) {
			fin++;
		}

		if (fin <= inicio) {
			return null;
		}

		return limpiarModid(linea.substring(inicio, fin));
	}

	/**
	 * Detecta si la línea contiene palabras típicas de error.
	 */
	private boolean contieneIndicadorError(String linea) {
		return contieneIgnoreCase(linea, "error") || contieneIgnoreCase(linea, "exception")
				|| contieneIgnoreCase(linea, "fail") || contieneIgnoreCase(linea, "crash")
				|| contieneIgnoreCase(linea, "problem") || contieneIgnoreCase(linea, "unable")
				|| contieneIgnoreCase(linea, "cannot") || contieneIgnoreCase(linea, "didn't")
				|| contieneIgnoreCase(linea, "couldn't") || contieneIgnoreCase(linea, "missing")
				|| contieneIgnoreCase(linea, "corrupt") || contieneIgnoreCase(linea, "invalid")
				|| contieneIgnoreCase(linea, "unsupported");
	}

	/**
	 * Registra el error y evita duplicados.
	 */
	private void registrarModSospechoso(Consola consola, int indiceLinea, String modID) {
		if (!esModidValido(modID)) {
			return;
		}

		String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;

		if (errores.add(mensaje)) {
			String enlace = consola.agregarErrorALectador(indiceLinea, this);
			enlacesPorError.put(mensaje, enlace);
			modidPorError.put(mensaje, modID);
		}

		activado = true;
	}

	/**
	 * Limpia símbolos que a veces quedan pegados al modid.
	 */
	private String limpiarModid(String modID) {
		if (modID == null) {
			return null;
		}

		modID = modID.trim();

		while (!modID.isEmpty()) {
			char ultimo = modID.charAt(modID.length() - 1);

			if (ultimo == ',' || ultimo == ';' || ultimo == ':' || ultimo == ')' || ultimo == ']') {
				modID = modID.substring(0, modID.length() - 1).trim();
			} else {
				break;
			}
		}

		while (!modID.isEmpty()) {
			char primero = modID.charAt(0);

			if (primero == '(' || primero == '[') {
				modID = modID.substring(1).trim();
			} else {
				break;
			}
		}

		return modID;
	}

	private boolean esModidValido(String modID) {
		if (modID == null || modID.isEmpty()) {
			return false;
		}

		if (modID.indexOf(".java") >= 0) {
			return false;
		}

		for (int i = 0; i < modID.length(); i++) {
			if (!esCaracterDeModid(modID.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	private boolean esCaracterDeModid(char c) {
		return Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == '+';
	}

	private boolean empiezaConIgnoreCase(String texto, String prefijo) {
		if (texto == null || prefijo == null || prefijo.length() > texto.length()) {
			return false;
		}

		return texto.regionMatches(true, 0, prefijo, 0, prefijo.length());
	}

	private boolean contieneIgnoreCase(String texto, String buscar) {
		return indexOfIgnoreCase(texto, buscar) >= 0;
	}

	/**
	 * Busca texto ignorando mayúsculas/minúsculas sin crear copias con
	 * toLowerCase().
	 */
	private int indexOfIgnoreCase(String texto, String buscar) {
		if (texto == null || buscar == null) {
			return -1;
		}

		int largoTexto = texto.length();
		int largoBuscar = buscar.length();

		if (largoBuscar == 0) {
			return 0;
		}

		if (largoBuscar > largoTexto) {
			return -1;
		}

		int limite = largoTexto - largoBuscar;

		for (int i = 0; i <= limite; i++) {
			if (texto.regionMatches(true, i, buscar, 0, largoBuscar)) {
				return i;
			}
		}

		return -1;
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
				List<String> ubicaciones = Buscardor.obtenerModsConNombre(modid);

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
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}