package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Verificación especializada para detectar y resumir todas las
 * {@code NullPointerException} (NPE) que aparecen en la consola.
 * <p>
 * - Analiza tanto el texto crudo de la consola como los trazos ya extraídos por
 * {@link VerificacionDeStackTrace} para asegurar que no se escape ningún caso.
 * - Para cada NPE, identifica (si es posible) el método y el objeto nulo
 * implicado. - Añade un posible origen (JAR, modid o clase) usando solo
 * información del trazo actual, evitando falsos positivos al no usar datos
 * globales de otros errores. - Agrupa errores idénticos mostrando todos los
 * JARs relacionados en un solo mensaje. - La salida es un bloque HTML con lista
 * de errores, pensado para mostrarse en la UI.
 */
public class NullPointer implements Verificaciones {

	/**
	 * Separador de líneas, definido en la interfaz base
	 */
	private static final String NL = Verificaciones.nl;

	/**
	 * Almacena los mensajes de error únicos detectados, agrupados por tipo de error
	 */
	private final Map<String, Set<String>> errores = new HashMap<>();

	/**
	 * Indica si se encontró al menos una NPE
	 */
	private boolean activado = false;

	/**
	 * Almacena el enlace HTML por mensaje base (tanto para líneas sueltas como para
	 * trazos). NOTA: la clave SIEMPRE es el mensaje base SIN orígenes añadidos.
	 */
	private final Map<String, String> enlacesPorLinea = new HashMap<>();

	public static List<String> lineas_ignorar = new ArrayList<>();

	public boolean posiblePorLinea = false;

	static {
		// Ejemplo de línea a ignorar (puedes añadir más patrones específicos aquí)
		lineas_ignorar.add("Cannot invoke \"java.util.Map.get(Object)\" because \"promos\" is null");// MCForge
																										// VersionChecker,
																										// no es fatal
		lineas_ignorar.add("com.github.alexthe666.citadel.web.WebHelper:getURLContent");// Comun con citadel pero no es
																						// malo

		lineas_ignorar.add(
				"Cannot invoke \"org.spongepowered.asm.mixin.transformer.ClassInfo.isMixin()\" because \"superClass\" is null");// Generalmente
																																// esta
																																// bien

		// Mixin/EMI/JEI: ClassInfo.forType devuelve null durante análisis de
		// superclases
		lineas_ignorar.add(
				"Cannot invoke \"org.spongepowered.asm.mixin.transformer.ClassInfo.hasSuperClass(org.spongepowered.asm.mixin.transformer.ClassInfo)\" because the return value of \"org.spongepowered.asm.mixin.transformer.ClassInfo.forType(org.objectweb.asm.Type, org.spongepowered.asm.mixin.transformer.ClassInfo$TypeLookup)\" is null");

		// PFM/cliente: TextureManager todavía es null durante generación/carga de
		// recursos
		lineas_ignorar.add(
				"Cannot invoke \"net.minecraft.client.renderer.texture.TextureManager.m_118495_(net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.texture.AbstractTexture)\" because the return value of \"net.minecraft.client.Minecraft.m_91097_()\" is null");

	}

	@Override
	public void verificar(Consola consola) {

		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;
		if (vdst == null || vdst.trazos_completos == null) {
			return;
		}

		if (!consola.contenido_verificar.contains("")) {
			return;
		}
		posiblePorLinea = true;

		for (TraceInfo trace : vdst.trazos_completos) {

			if (trace == null || trace.trace == null) {
				continue;
			}

			if (!trace.trace.contains("NullPointerException")) {
				continue;
			}

			// === Extraer mensaje NPE ===
			String metodo = "método desconocido";
			String objeto = "objeto";

			DatosNPE datos = extraerDatosNPE(trace.trace, true);

			if (datos == null) {
				continue;
			}

			metodo = datos.metodo;
			objeto = datos.objeto;

			// === Inferir origen SOLO desde este TraceInfo ===
			String origen = inferirOrigenDesdeTrace(trace);

			String mensajeBase = MonitorDePID.idioma.null_pointer_error(metodo, objeto);

			enlacesPorLinea.putIfAbsent(mensajeBase, consola.agregarErrorALectador(trace.consolaLineaComenzar, this));

			errores.computeIfAbsent(mensajeBase, k -> new HashSet<>());
			if (!origen.isEmpty()) {
				errores.get(mensajeBase).add(origen);
			}

			activado = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posiblePorLinea)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int i) {

		if (!posiblePorLinea) {
			return;
		}

		if (linea.contains("NullPointerException") && !linea.contains("at ")
				&& VerificacionDeStackTrace.tracePermite(linea)) {
			procesarLineaSinTraza(linea, consola.verificacion_de_stacktrace, i, consola);
		}
	}

	/**
	 * Procesa una línea con NPE que no tiene stack trace completo
	 */
	private void procesarLineaSinTraza(String linea, VerificacionDeStackTrace vdst, int numeroLinea, Consola consola) {

		if (lineas_ignorar.stream().anyMatch(patron -> linea.contains(patron))) {
			return; // Ignorar esta línea específica
		}

		String metodo = "desconocido";
		String objeto = "desconocido";

		DatosNPE datos = extraerDatosNPE(linea, false);

		if (datos == null) {
			return;
		}

		metodo = datos.metodo;
		objeto = datos.objeto;

		// Buscar origen SOLO en esta línea
		String origen = detectarOrigenEnLinea(linea, vdst, numeroLinea);
		String mensajeBase = MonitorDePID.idioma.null_pointer_error(metodo, objeto);

		// Registrar el error en el sistema de lectura (enlace para la línea suelta)
		enlacesPorLinea.put(mensajeBase, consola.agregarErrorALectador(numeroLinea, this));

		// Agregar el error al mapa, agrupando por mensaje base
		errores.computeIfAbsent(mensajeBase, k -> new HashSet<>());
		if (!origen.isEmpty()) {
			errores.get(mensajeBase).add(origen);
		}

		activado = true;
	}

	private static class DatosNPE {
		String metodo;
		String objeto;

		DatosNPE(String metodo, String objeto) {
			this.metodo = metodo;
			this.objeto = objeto;
		}
	}

	/**
	 * Extrae datos de una NPE sin regex. Cubre: 1) Cannot invoke/read/assign
	 * "...metodo..." because "...objeto..." 2) JsonObject.algo() ... "...objeto..."
	 * is null 3) java.lang.NullPointerException: detalle
	 */
	private static DatosNPE extraerDatosNPE(String texto, boolean permitirCabecera) {
		if (texto == null) {
			return null;
		}

		DatosNPE cannot = extraerFormatoCannot(texto);
		if (cannot != null) {
			return cannot;
		}

		DatosNPE json = extraerFormatoJson(texto);
		if (json != null) {
			return json;
		}

		if (permitirCabecera) {
			String detalle = extraerDetalleCabeceraNPE(texto);
			if (detalle != null && !detalle.isEmpty()) {
				return new DatosNPE(detalle, "objeto");
			}
		}

		return null;
	}

	/**
	 * Equivalente aproximado de:
	 * Cannot\s+(invoke|read|assign)[^\"]*\"([^\"]+)\"[^\"]*\"([^\"]+)\"
	 */
	private static DatosNPE extraerFormatoCannot(String texto) {
		int pos = indexOfIgnoreCase(texto, "Cannot");
		if (pos < 0) {
			return null;
		}

		int accionInicio = saltarEspacios(texto, pos + "Cannot".length());

		if (!empiezaConPalabra(texto, accionInicio, "invoke") && !empiezaConPalabra(texto, accionInicio, "read")
				&& !empiezaConPalabra(texto, accionInicio, "assign")) {
			return null;
		}

		int busqueda = accionInicio;

		while (true) {
			int q1 = texto.indexOf('"', busqueda);
			if (q1 < 0) {
				return null;
			}

			int q2 = texto.indexOf('"', q1 + 1);
			if (q2 < 0) {
				return null;
			}

			int q3 = texto.indexOf('"', q2 + 1);
			if (q3 < 0) {
				return null;
			}

			int q4 = texto.indexOf('"', q3 + 1);
			if (q4 < 0) {
				return null;
			}

			String entreComillas = texto.substring(q2 + 1, q3);
			if (!contieneIgnoreCase(entreComillas, "because")) {
				// Avanzamos al cierre del primer par para evitar desalineaciones simétricas
				busqueda = q2 + 1;
				continue;
			}

			String metodo = texto.substring(q1 + 1, q2).trim();
			String objeto = texto.substring(q3 + 1, q4).trim();

			// Si la estructura es un match real ("because"), obligamos a romper o avanzar
			if (!metodo.isEmpty() && !objeto.isEmpty()) {
				return new DatosNPE(metodo, objeto);
			}

			// Si cayó aquí (p.ej. método vacío), avanzamos pasando TODO el bloque evaluado
			// para evitar procesar q3 y q4 como si fueran un nuevo q1 y q2.
			busqueda = q4 + 1;
		}
	}

	/**
	 * Equivalente aproximado de: JsonObject\.[a-zA-Z]+\(\).*\"([^\"]+)\" is null
	 */
	private static DatosNPE extraerFormatoJson(String texto) {
		int pos = texto.indexOf("JsonObject.");
		if (pos < 0) {
			return null;
		}

		int metodoInicio = pos + "JsonObject.".length();
		if (metodoInicio >= texto.length() || !esLetraAscii(texto.charAt(metodoInicio))) {
			return null;
		}

		int parentesis = texto.indexOf("()", metodoInicio);
		if (parentesis < 0) {
			return null;
		}

		for (int i = metodoInicio; i < parentesis; i++) {
			if (!esLetraAscii(texto.charAt(i))) {
				return null;
			}
		}

		int busqueda = parentesis + 2;

		while (true) {
			int q1 = texto.indexOf('"', busqueda);
			if (q1 < 0) {
				return null;
			}

			int q2 = texto.indexOf('"', q1 + 1);
			if (q2 < 0) {
				return null;
			}

			int despues = saltarEspacios(texto, q2 + 1);
			if (empiezaConFrase(texto, despues, "is null")) {
				String objeto = texto.substring(q1 + 1, q2).trim();
				if (!objeto.isEmpty()) {
					return new DatosNPE("JsonObject.*()", objeto);
				}
			}

			busqueda = q1 + 1;
		}
	}

	private static int indexOfDesde(String texto, String buscar, int desde) {
		if (texto == null || buscar == null || desde < 0) {
			return -1;
		}

		int max = texto.length() - buscar.length();
		for (int i = desde; i <= max; i++) {
			if (texto.regionMatches(false, i, buscar, 0, buscar.length())) {
				return i;
			}
		}

		return -1;
	}

	private static boolean empiezaConFrase(String texto, int pos, String frase) {
		return pos >= 0 && pos + frase.length() <= texto.length()
				&& texto.regionMatches(false, pos, frase, 0, frase.length());
	}

	private static int saltarEspacios(String texto, int pos) {
		while (pos < texto.length() && Character.isWhitespace(texto.charAt(pos))) {
			pos++;
		}
		return pos;
	}

	private static boolean contieneIgnoreCase(String texto, String buscar) {
		return indexOfIgnoreCase(texto, buscar) >= 0;
	}

	private static boolean esLetraAscii(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	/**
	 * Equivalente aproximado de: java\.lang\.NullPointerException(?::\s*)?(.*)
	 */
	private static String extraerDetalleCabeceraNPE(String texto) {
		int pos = indexOfIgnoreCase(texto, "java.lang.NullPointerException");
		if (pos < 0) {
			return null;
		}

		int fin = pos + "java.lang.NullPointerException".length();

		fin = saltarEspacios(texto, fin);

		if (fin >= texto.length()) {
			return "";
		}

		if (texto.charAt(fin) == ':') {
			return texto.substring(fin + 1).trim();
		}

		return texto.substring(fin).trim();
	}

	private static int indexOfIgnoreCase(String texto, String buscar) {
		int max = texto.length() - buscar.length();
		for (int i = 0; i <= max; i++) {
			if (texto.regionMatches(true, i, buscar, 0, buscar.length())) {
				return i;
			}
		}
		return -1;
	}

	private static boolean empiezaConPalabra(String texto, int pos, String palabra) {
		if (pos < 0 || pos + palabra.length() > texto.length()) {
			return false;
		}

		if (!texto.regionMatches(true, pos, palabra, 0, palabra.length())) {
			return false;
		}

		int fin = pos + palabra.length();
		return fin >= texto.length() || !Character.isLetterOrDigit(texto.charAt(fin));
	}

	/**
	 * Busca un origen (mod, JAR o clase) DIRECTAMENTE en el trazo del error. Esto
	 * evita asociar errores con mods que solo aparecen en otros trazos.
	 */
	private String detectarOrigenEnTraza(String trazo, VerificacionDeStackTrace vdst, int lineaConsola) {
		String[] lines = trazo.split(NL);

		// Primero, buscar en las primeras líneas del stack trace (donde ocurre el
		// error)
		for (int i = 0; i < Math.min(lines.length, 5); i++) {
			String linea = lines[i];
			String origen = detectarOrigenEnLinea(linea, vdst, lineaConsola + i);
			if (!origen.isEmpty()) {
				return origen;
			}
		}

		// Si no encontramos nada en las primeras líneas, buscar en el resto del trazo
		for (int i = 5; i < lines.length; i++) {
			String linea = lines[i];
			String origen = detectarOrigenEnLinea(linea, vdst, lineaConsola + i);
			if (!origen.isEmpty()) {
				return origen;
			}
		}

		return ""; // No se encontró origen en este trazo
	}

	/**
	 * Busca un origen (JAR, modid o clase) DIRECTAMENTE en una línea específica.
	 * Normaliza los nombres para evitar problemas con codificación y sufijos.
	 */
	private String detectarOrigenEnLinea(String linea, VerificacionDeStackTrace vdst, int numeroLineaConsola) {
		// 1. Buscar JAR en la línea
		List<String> jarsEncontrados = VerificacionDeStackTrace.extraerJarsDeLinea(linea);
		for (String jar : jarsEncontrados) {
			if (jar.contains(".jar") && !VerificacionDeStackTrace.isJarNoPermite(jar)) {
				return jar;
			}
		}

		// 2. Buscar modid
		String modid = VerificacionDeStackTrace.extraerModidDeLinea(linea);
		if (modid != null && !VerificacionDeStackTrace.esModNoPermite(modid)) {
			return modid;
		}

		// 3. Buscar paquete/clase
		String pack = VerificacionDeStackTrace.extraerPaqueteDeLinea(linea);
		if (pack != null) {
			// Crear una representación adecuada para el método packNoEsPermitido
			String representacion = "0," + numeroLineaConsola; // nivel 0, número de línea real
			if (!vdst.packNoEsPermite(pack, representacion, false)) {
				return pack;
			}
		}

		return ""; // No se encontró origen en esta línea
	}

	/**
	 * Infiere el origen de una NPE usando EXCLUSIVAMENTE las líneas del TraceInfo
	 * actual. No usa datos globales ni otros trazos.
	 */
	private String inferirOrigenDesdeTrace(TraceInfo trace) {

		if (trace == null || trace.lineas == null) {
			return "";
		}

		String mejorJar = "";
		String mejorModid = "";
		String mejorPaquete = "";

		for (VerificacionDeStackTrace.LineaTrazo lt : trace.lineas) {

			if (lt == null || lt.origen == null || lt.origen.isEmpty()) {
				continue;
			}

			String origen = lt.origen;

			// 1) JAR permitido (máxima prioridad)
			if (origen.endsWith(".jar") && !VerificacionDeStackTrace.isJarNoPermite(origen)) {
				return origen;
			}

			// 2) Modid permitido
			if (!origen.contains("/") && !origen.endsWith(".jar") && !VerificacionDeStackTrace.esModNoPermite(origen)) {

				if (mejorModid.isEmpty()) {
					mejorModid = origen;
				}
			}

			// 3) Paquete permitido
			if (origen.contains("/")) {

				boolean permitido = true;
				for (String prefijo : VerificacionDeStackTrace.package_no_permite) {
					if (origen.startsWith(prefijo)) {
						permitido = false;
						break;
					}
				}

				if (permitido && origen.length() > mejorPaquete.length()) {
					mejorPaquete = origen;
				}
			}
		}

		if (!mejorModid.isEmpty()) {
			return mejorModid;
		}
		if (!mejorPaquete.isEmpty()) {
			return mejorPaquete;
		}

		return "";
	}

	@Override
	public Verificaciones nueva() {
		return new NullPointer();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 50f; // Alta prioridad: NPE suele ser crítico
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder("<ul>");

		// Para cada tipo de error
		for (Map.Entry<String, Set<String>> entry : errores.entrySet()) {
			// Usar SIEMPRE la clave base (sin orígenes) para buscar el enlace
			final String claveBase = entry.getKey();

			// Construimos el texto que se mostrará (al que sí añadiremos orígenes, si hay)
			String mensajeMostrado = claveBase;
			Set<String> origenes = entry.getValue();

			// Si hay orígenes, añadirlos al mensaje mostrado (no a la clave)
			if (!origenes.isEmpty()) {
				StringBuilder origenesStr = new StringBuilder();
				for (String origen : origenes) {
					if (origenesStr.length() > 0) {
						origenesStr.append(", ");
					}
					origenesStr.append(origen);
				}
				mensajeMostrado += " (" + origenesStr.toString() + ")";
			}

			// Recuperar el enlace con la CLAVE BASE (sin orígenes añadidos)
			String enlace = enlacesPorLinea.getOrDefault(claveBase, "");
			if (!enlace.isEmpty()) {
				mensajeMostrado += " " + enlace;
			}

			sb.append("<li>").append(mensajeMostrado).append("</li>");
		}

		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_null_pointer();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "nullpointer";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}
