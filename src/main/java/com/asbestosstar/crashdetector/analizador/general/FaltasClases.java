package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.mapas.TriMap;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu;
import com.asbestosstar.crashdetector.waifu.WaifuAPI;

public class FaltasClases implements VerificacionRapida {

	private boolean activado = false;
	public boolean create = false;
	public boolean epicfight = false;
	public boolean azurelib = false;

	// Apps
	public boolean minecraft = false;
	public boolean dangerzone = false;
	// public boolean nxopen = false;

	// Cargadores
	public boolean featurecreep = false;
	public boolean modlauncher = false;
	public boolean minecraftforge = false;
	public boolean neoforged = false;
	public boolean fabricloader = false;
	public boolean pillowmc = false;

	/**
	 * Mapa de clase formateada (com/x/Y -> com/x/Y) -> origen (modid/jar/paquete).
	 * Contiene TODAS las clases detectadas (antes de filtrar kotlin/essential).
	 */
	private final Map<String, String> clases = new HashMap<>();

	/**
	 * Ignorar lineas o trazos con son Strings
	 */
	public static List<String> ignorar = new ArrayList<>();

	/**
	 * Cache de elementos a ignorar en formato normalizado con '/'.
	 */
	private static List<String> ignorarNormalizado = null;

	/**
	 * Si ignoremos un clase o linea
	 * 
	 * @return
	 */
	public static boolean ignorarClaseOLinea(String str) {
		if (str == null) {
			return false;
		}

		// CrashDetectorLogger.log(str);

		if (ignorarNormalizado == null) {
			synchronized (FaltasClases.class) {
				if (ignorarNormalizado == null) {
					List<String> lista = new ArrayList<>();
					for (String s : ignorar) {
						if (s != null) {
							lista.add(s.replace('.', '/'));
						}
					}
					ignorarNormalizado = lista;
				}
			}
		}

		// Early exit: if the string doesn't contain '.' or '/', it can't be a
		// class/path
		// But it might be a modid. The previous implementation always did replace.

		// Optimization: only normalize once and avoid repeat allocations if possible
		// However, for contains check, we need to match the format.
		String strNorm = str.indexOf('.') >= 0 ? str.replace('.', '/') : str;

		for (String ign : ignorarNormalizado) {
			if (strNorm.contains(ign)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Conjunto auxiliar para evitar duplicados. Guardamos el nombre de la clase en
	 * formato ruta (con "/").
	 */
	public final Set<String> todos = new LinkedHashSet<>();

	/**
	 * Clase en formato ruta (com/x/Y) -> enlace HTML de la consola.
	 */
	private final Map<String, String> enlacesPorClase = new HashMap<>();

	/**
	 * Referencia al analizador de stacktrace para poder completar orígenes más
	 * tarde (una vez que se hayan procesado todas las líneas).
	 */
	private VerificacionDeStackTrace vdst;

	/**
	 * Mapa filtrado que se usará realmente para mensaje(), activado() y solucion():
	 * contiene solo clases relevantes (se filtran gg/essential, kotlin, kotlinx).
	 */
	private Map<String, String> clasesFiltradas = null;

	/**
	 * Conjunto global de clases detectadas para evitar duplicados entre logs.
	 */
	private static final Set<String> CLASES_GLOBALES_VISTAS = Collections.synchronizedSet(new HashSet<>());

	/**
	 * Marca para asegurarnos de que el post-procesado (completar orígenes +
	 * filtrado) solo se hace una vez.
	 */
	private boolean postProcesado = false;

	public boolean posible = false;

	/**
	 * Limpia el conjunto de clases vistas globalmente.
	 */
	public static void reiniciarGlobal() {
		CLASES_GLOBALES_VISTAS.clear();
	}

	static {
		ignorar.add("avaritia");// Positiva falsa comun con KubJS y avaritia TODO agregar a advertencia
		ignorar.add("DeadKingMusicManager");// Positiva falsa comun con IronSpellbooker y alltheleaks pero necesitemos
											// considerar otras casos por que all the leaks a veces tiene problemas

		ignorar.add("TeamProperty");
		ignorar.add("WaterMediaBinaries");
		ignorar.add("yesman/epicfight/epicskins/user/AuthenticationHelperImpl");

		// HauntedHarvest: integración opcional con JEI busca Supplementaries.
		// En este caso específico no debe contarse como clase fatal faltante.
		ignorar.add("net/mehvahdjukaar/supplementaries/Supplementaries");
		ignorar.add("FT_Face");

	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { "java.lang.ClassNotFoundException:", "java.lang.NoClassDefFoundError:",
				"Error loading class:", "ClassMetadataNotFoundException" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		posible = true;
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		this.vdst = consola.verificacion_de_stacktrace;
		String cont = consola.contenido_verificar;

		if (cont != null) {
			if (cont.contains("ClassMetadataNotFoundException") && !cont.contains("ClassNotFoundException")
					&& !cont.contains("NoClassDefFoundError")
					&& (vdst == null || vdst.clases_fatales_no_existentes.isEmpty())

			) {
				return;
			}
		} else if (vdst == null || vdst.clases_fatales_no_existentes.isEmpty()) {
			return;
		}
		posible = true;

		// Agregar clases faltantes desde stacktraces fatales
		if (vdst != null && vdst.clases_fatales_no_existentes != null) {
			for (TriMap.TripleKey<String, Integer, Integer> llave : vdst.clases_fatales_no_existentes.keySet()) {
				String claseCruda = llave.key1; // nombre de la clase tal cual viene
				int nivel_prioridad = llave.key2; // nivel de prioridad (no lo usamos aquí)
				int numero_linea_consola = llave.key3; // número de línea en la consola
				String sospechoso = vdst.clases_fatales_no_existentes.get(claseCruda, nivel_prioridad,
						numero_linea_consola);

				if (numero_linea_consola > 0 && consola.lineas_verificar != null
						&& numero_linea_consola < consola.lineas_verificar.length) {
					String linea_menos1 = consola.lineas_verificar[numero_linea_consola - 1];
					if (linea_menos1.contains("catching") || linea_menos1.contains("Catching") ||

							linea_menos1.contains("rhino.CachedClassInfo")) {
						continue; // Skip catching errors
					}
				}

				String claseFormateada = formatearClase(claseCruda);
				if (!esNombreClaseValido(claseFormateada)) {
					continue;
				}

				if (ignorarClaseOLinea(claseFormateada)) {
					continue;
				}

				// Ignorar clases no relevantes (kotlin, gg/essential, etc.)
				if (esClaseNoRelevante(claseFormateada)) {
					continue;
				}

				// Deduplicación global entre logs
				if (!CLASES_GLOBALES_VISTAS.add(claseFormateada)) {
					continue;
				}

				String origenLimpio = limpiarOrigen(sospechoso);

				// Si ya vimos esta clase antes, SOLO actualizamos si falta origen
				if (!todos.add(claseFormateada)) {
					String actual = clases.getOrDefault(claseFormateada, "");
					if ((actual == null || actual.isEmpty()) && origenLimpio != null && !origenLimpio.isEmpty()) {
						clases.put(claseFormateada, origenLimpio);
					}
					continue;
				}

				// Primera vez que aparece
				clases.put(claseFormateada, origenLimpio);

				String enlace = consola.agregarErrorALectador(numero_linea_consola, this);
				enlacesPorClase.put(claseFormateada, enlace);
			}
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posible)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || linea.isEmpty()) {
			return;
		}
		if (!posible) {
			return;
		}

		// Optimization: Quick check for interesting patterns before doing more
		// expensive checks
		if (!linea.contains("java.lang.ClassNotFoundException:") && !linea.contains("java.lang.NoClassDefFoundError:")
				&& !linea.contains("Error loading class:")) {
			return;
		}

		String claseCruda = null;

		// Procesar errores de clase faltante
		if (linea.contains("java.lang.ClassNotFoundException:") || linea.contains("java.lang.NoClassDefFoundError:")) {

			// Saltar líneas con WARN (sin excepciones)
			if (linea.contains("/WARN]") || linea.contains("Warn")
					|| VerificacionDeStackTrace.esLineaDeAdvertenciaEstandar(linea)) {
				return;
			}

			if (ignorarClaseOLinea(linea)) {
				return;
			}

			// Eliminar prefijo de log tipo "[hh:mm:ss] [Server thread/INFO]: ..."
			int indiceDosPuntos = linea.indexOf(':');
			if (indiceDosPuntos != -1 && linea.charAt(0) == '[') {
				// Safety check: is it a real timestamp format? [00:00:00]
				if (indiceDosPuntos < 20) {
					linea = linea.substring(indiceDosPuntos + 1).trim();
				}
			}

			if (linea.contains("java.lang.ClassNotFoundException:")) {
				int index = linea.indexOf("java.lang.ClassNotFoundException:");
				String candidato = linea.substring(index + 33).trim();
				if (!candidato.isEmpty()) {
					claseCruda = primerTokenClase(candidato);
				}
			} else {
				int index = linea.indexOf("java.lang.NoClassDefFoundError:");
				String candidato = linea.substring(index + 31).trim();
				if (!candidato.isEmpty()) {
					claseCruda = primerTokenClase(candidato);
				}
			}

		} else if (linea.contains("Error loading class:")) {
			// Caso antiguo, se deja por seguridad
			int index = linea.indexOf("Error loading class:");
			String candidato = linea.substring(index + 20).trim();
			if (!candidato.isEmpty()) {
				claseCruda = primerTokenClase(candidato);
			}
		}

		if (claseCruda == null) {
			return;
		}

		if (numero_de_linea > 0 && consola.lineas_verificar != null
				&& numero_de_linea < consola.lineas_verificar.length) {
			String linea_menos1 = consola.lineas_verificar[numero_de_linea - 1];
			if (linea_menos1.toLowerCase().contains("catching") || linea_menos1.contains("rhino.CachedClassInfo")) {
				return;
			}
		}

		if (linea.contains("Valkyrian skies compatibility disabled")) {
			return;
		}

		// [28Dec2025 00:14:05.843] [modloading-worker-0/INFO] [STDERR/]:
		// [dev.latvian.mods.rhino.CachedClassInfo:getDeclaredMethods:194]: [Rhino]
		// Failed to get declared methods for
		// com.momosoftworks.coldsweat.compat.kubejs.KubeBindings:
		// java.lang.NoClassDefFoundError:
		// dev/latvian/mods/kubejs/level/BlockContainerJS
		String claseFormateada = formatearClase(claseCruda);
		if (!esNombreClaseValido(claseFormateada)) {
			return;
		}
		// Ignorar clases no relevantes (kotlin, gg/essential, etc.)
		if (esClaseNoRelevante(claseFormateada)) {
			return;
		}

		// Deduplicación global entre logs
		if (!CLASES_GLOBALES_VISTAS.add(claseFormateada)) {
			return;
		}

		// Buscar el origen en la misma línea o líneas cercanas
		String origen = encontrarOrigenEnLinea(linea, numero_de_linea, consola);
		String origenLimpio = limpiarOrigen(origen);

		// Si no encontramos origen en el stacktrace cercano,
		// intentamos usar la línea anterior tipo "Failed to create mod instance..."
		if (origenLimpio == null || origenLimpio.isEmpty()) {
			String origenPrevio = buscarOrigenEnLineaAnterior(numero_de_linea, consola);
			if (origenPrevio != null && !origenPrevio.isEmpty()) {
				origenLimpio = origenPrevio;
			}
		}

		// Si ya existe la clase, solo rellenamos si le falta origen
		if (!todos.add(claseFormateada)) {
			String actual = clases.get(claseFormateada);
			if ((actual == null || actual.isEmpty()) && (origenLimpio != null && !origenLimpio.isEmpty())) {
				clases.put(claseFormateada, origenLimpio);
			}
			return;
		}

		// Primera vez que aparece
		clases.put(claseFormateada, origenLimpio);

		// CrashDetectorLogger.log("Fatals clases clase no advertencia " +
		// claseFormateada);

		String enlace = consola.agregarErrorALectador(numero_de_linea, this);
		enlacesPorClase.put(claseFormateada, enlace);
	}

	/**
	 * Determina si una clase debe ser filtrada por no ser relevante (por ejemplo,
	 * kotlin/kotlinx o gg/essential).
	 */
	private boolean esClaseNoRelevante(String claseFormateada) {
		String c = claseFormateada.trim();
		return c.startsWith("gg/essential/") || c.startsWith("kotlin/") || c.startsWith("kotlinx/");
	}

	// Detecta si una línea "parece" parte de un stacktrace.
	// Esto evita que el escaneo se vaya a otras secciones del log (WARN/INFO
	// sueltos).
	private boolean esLineaStackish(String l) {
		if (l == null || l.isEmpty())
			return false;

		int start = 0;
		while (start < l.length() && Character.isWhitespace(l.charAt(start))) {
			start++;
		}

		if (start >= l.length())
			return false;

		// Permitir líneas comentadas tipo "// at ..."
		if (l.startsWith("//", start)) {
			start += 2;
			while (start < l.length() && Character.isWhitespace(l.charAt(start))) {
				start++;
			}
		}

		if (start >= l.length())
			return false;

		return l.startsWith("at ", start) || l.startsWith("Caused by:", start) || l.startsWith("Suppressed:", start)
				|| l.startsWith("...", start) || l.startsWith("SECURE-BOOTSTRAP", start);
	}

	// Heurística para rechazar modids sospechosos típicos de handlers/mixins.
	// Ejemplo real: "hld000" no es un mod, es un shard interno.
	private boolean esModidSospechoso(String modid) {
		if (modid == null)
			return true;

		// Token corto que termina en 3+ dígitos -> casi siempre falso positivo.
		if (modid.length() > 6)
			return false;

		int consecutivos = 0;

		for (int i = modid.length() - 1; i >= 0; i--) {
			char c = modid.charAt(i);

			if (c >= '0' && c <= '9') {
				consecutivos++;

				if (consecutivos >= 3)
					return true;
			} else {
				return false;
			}
		}

		return false;
	}

	/**
	 * Post-procesa las clases solo una vez: completa orígenes que falten usando
	 * VerificacionDeStackTrace y filtra las clases de gg/essential y kotlin/kotlinx
	 * para que no aparezcan ni cuenten como activadas.
	 */
	private void postProcesarSiNecesario() {
		if (postProcesado) {
			return;
		}
		postProcesado = true;

		// Completar orígenes si tenemos referencia al analizador de stacktrace
		if (vdst != null && !clases.isEmpty()) {
			completarOrigenesSiFaltan(vdst);
		}

		// Filtrar clases no relevantes EN ESTA FASE (redundante si ya se filtró antes,
		// pero seguro por si se coló alguna).
		clasesFiltradas = new LinkedHashMap<>();
		for (Map.Entry<String, String> e : clases.entrySet()) {
			String clase = e.getKey();
			if (esClaseNoRelevante(clase)) {
				continue;
			}
			clasesFiltradas.put(clase, e.getValue());
		}
	}

	/**
	 * Limpia el origen para que solo contenga información relevante (JAR, modid o
	 * paquete). Ahora también acepta nombres de jar "crudos" sin corchetes.
	 */
	private String limpiarOrigen(String origen) {
		if (origen == null || origen.isEmpty()) {
			return "";
		}

		String o = origen.trim();

		// 0) Si ya es un jar "crudo" (sin corchetes), aceptarlo directo
		// Ej: "Create-DnDesire-1.20.1-0.1b.Release-Early-Dev.jar"
		if (o.endsWith(".jar") && !VerificacionDeStackTrace.isJarNoPermite(o)) {
			return o;
		}

		// 1. Si parece un modid directo (sin slash, sin punto, y no termina en .jar)
		if (!o.contains("/") && !o.contains(".") && !o.endsWith(".jar")) {
			if (!VerificacionDeStackTrace.esModNoPermite(o)) {
				return o; // ej: "railways"
			}
		}

		// 2. Intentar extraer un JAR desde línea con corchetes
		List<String> jars_encontrados = VerificacionDeStackTrace.extraerJarsDeLinea(o);
		for (String jar : jars_encontrados) {
			if (jar.contains(".jar") && !VerificacionDeStackTrace.isJarNoPermite(jar)) {
				return jar;
			}
		}

		// 3. Intentar extraer modid
		String modid = VerificacionDeStackTrace.extraerModidDeLinea(o);
		if (modid != null && !VerificacionDeStackTrace.esModNoPermite(modid)) {
			return modid;
		}

		// 4. Intentar extraer paquete
		String paquete = VerificacionDeStackTrace.extraerPaqueteDeLinea(o);
		if (paquete != null && !esPaqueteNoPermitido(paquete)) {
			return paquete;
		}

		// 5. No se encontró nada útil
		return "";
	}

	/**
	 * Verifica si un paquete está en la lista de elementos no permitidos.
	 */
	private boolean esPaqueteNoPermitido(String pack) {
		for (String prefijo : VerificacionDeStackTrace.package_no_permite) {
			if (pack.startsWith(prefijo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Busca el origen (JAR, modid o paquete) en la línea actual o en líneas
	 * cercanas utilizando los métodos de VerificacionDeStackTrace.
	 */
	private String encontrarOrigenEnLinea(String linea, int numeroLinea, Consola consola) {
		String resultado = buscarOrigenEnLinea(linea);
		if (!resultado.isEmpty()) {
			return resultado;
		}

		if (consola.lineas_verificar == null) {
			return "";
		}

		String[] lineas = consola.lineas_verificar;
		int totalLineas = lineas.length;

		// Buscar hacia abajo SOLO dentro del mismo bloque de stacktrace
		int maxAbajo = Math.min(numeroLinea + 50, totalLineas - 1);
		for (int i = numeroLinea + 1; i <= maxAbajo; i++) {
			String siguiente = lineas[i];
			if (!esLineaStackish(siguiente)) {
				break; // Ya salimos del stacktrace, no seguir buscando
			}
			resultado = buscarOrigenEnLinea(siguiente);
			if (!resultado.isEmpty()) {
				return resultado;
			}
		}

		// Buscar hacia arriba SOLO dentro del mismo bloque de stacktrace
		int maxArriba = Math.max(0, numeroLinea - 20);
		for (int i = numeroLinea - 1; i >= maxArriba; i--) {
			String anterior = lineas[i];
			if (!esLineaStackish(anterior)) {
				break; // Ya salimos del stacktrace, no seguir buscando
			}
			resultado = buscarOrigenEnLinea(anterior);
			if (!resultado.isEmpty()) {
				return resultado;
			}
		}

		return "";
	}

	/**
	 * Busca origen en una línea específica utilizando los métodos existentes.
	 */
	private String buscarOrigenEnLinea(String linea) {
		if (linea == null || linea.isEmpty())
			return "";

		// 1) Buscar JARs (prioridad más alta)
		List<String> jarsEncontrados = VerificacionDeStackTrace.extraerJarsDeLinea(linea);
		if (jarsEncontrados != null && !jarsEncontrados.isEmpty()) {
			for (String jar : jarsEncontrados) {
				if (jar.contains(".jar") && !VerificacionDeStackTrace.isJarNoPermite(jar)) {
					return jar;
				}
			}
		}

		// 2) Buscar modid:
		// IMPORTANTE: ignorar modids detectados desde líneas "handler$"
		// porque suelen ser artefactos de mixins y generan falsos positivos.
		if (linea.indexOf("handler$") < 0) {
			String modid = VerificacionDeStackTrace.extraerModidDeLinea(linea);
			if (modid != null && !VerificacionDeStackTrace.esModNoPermite(modid) && !esModidSospechoso(modid)) {
				return modid;
			}
		}

		// 3) Buscar paquete
		String pack = VerificacionDeStackTrace.extraerPaqueteDeLinea(linea);
		if (pack != null && !esPaqueteNoPermitido(pack)) {
			return pack;
		}

		return "";
	}

	// Parser rápido: valida nombres tipo paquete/clase sin regex.
	// Acepta formato con "/" o ".", y acepta "$" para clases internas.
	// Requiere al menos 2 segmentos: paquete + clase.
	private boolean esNombreClaseValido(String clase) {
		if (clase == null) {
			return false;
		}

		int len = clase.length();
		if (len == 0) {
			return false;
		}

		boolean inicioSegmento = true;
		boolean vioSeparador = false;
		int largoSegmento = 0;

		for (int i = 0; i < len; i++) {
			char c = clase.charAt(i);

			if (c == '/' || c == '.') {
				if (inicioSegmento || largoSegmento == 0) {
					return false;
				}
				vioSeparador = true;
				inicioSegmento = true;
				largoSegmento = 0;
				continue;
			}

			if (inicioSegmento) {
				if (!esInicioIdentificadorJavaRapido(c)) {
					return false;
				}
				inicioSegmento = false;
				largoSegmento = 1;
			} else {
				if (!esParteIdentificadorJavaRapido(c)) {
					return false;
				}
				largoSegmento++;
			}
		}

		return vioSeparador && !inicioSegmento && largoSegmento > 0;
	}

	// Inicio permitido: letra ASCII, "_".
	// No usa Character.isJavaIdentifierStart porque es más caro y permite Unicode.
	private boolean esInicioIdentificadorJavaRapido(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_';
	}

	// Parte permitida: letra ASCII, número, "_", "$".
	private boolean esParteIdentificadorJavaRapido(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_' || c == '$';
	}

	// Reemplazo rápido para candidato.split("[\\s\\)]")[0].trim()
	private String primerTokenClase(String texto) {
		if (texto == null) {
			return "";
		}

		int inicio = 0;
		int len = texto.length();

		while (inicio < len && Character.isWhitespace(texto.charAt(inicio))) {
			inicio++;
		}

		int fin = inicio;
		while (fin < len) {
			char c = texto.charAt(fin);
			if (Character.isWhitespace(c) || c == ')') {
				break;
			}
			fin++;
		}

		return fin > inicio ? texto.substring(inicio, fin) : "";
	}

	// Formatea sin replace(regex). Corta en '(' o espacio y convierte "." a "/".
	private String formatearClase(String clase) {
		if (clase == null) {
			return "";
		}

		int fin = clase.length();

		for (int i = 0; i < clase.length(); i++) {
			char c = clase.charAt(i);
			if (c == '(' || Character.isWhitespace(c)) {
				fin = i;
				break;
			}
		}

		StringBuilder sb = new StringBuilder(fin);
		for (int i = 0; i < fin; i++) {
			char c = clase.charAt(i);
			sb.append(c == '.' ? '/' : c);
		}

		return sb.toString();
	}

	@Override
	public Verificaciones nueva() {
		return new FaltasClases();
	}

	@Override
	public boolean activado() {
		// Asegurarse de que las clases no relevantes se han filtrado antes de
		// decidir si está activado o no.
		postProcesarSiNecesario();
		this.activado = clasesFiltradas != null && !clasesFiltradas.isEmpty();
		return this.activado;
	}

	@Override
	public float prioridad() {
		return 1025.0f; // Máxima prioridad para errores de clases faltantes
	}

	@Override
	public String mensaje() {
		// Filtrar y completar orígenes antes de construir el mensaje
		postProcesarSiNecesario();

		if (clasesFiltradas == null || clasesFiltradas.isEmpty())
			return "";

		// Copia filtrada para evitar modificar clasesFiltradas
		Map<String, String> clasesParaMostrar = new LinkedHashMap<>(clasesFiltradas);

		// eliminar voxy + org/sqlite/JDBC del mensaje final
		for (Map.Entry<String, String> entry : clasesFiltradas.entrySet()) {
			String clase = entry.getKey();
			String origen = entry.getValue();
			if ("org/sqlite/JDBC".equals(clase) && origen != null && origen.toLowerCase().contains("voxy")) {
				clasesParaMostrar.remove(clase);
			}
		}

		if (clasesParaMostrar.isEmpty()) {
			return "";
		}

		StringBuilder html = new StringBuilder("<ul>");
		for (Map.Entry<String, String> entry : clasesParaMostrar.entrySet()) {
			String claseFormateada = entry.getKey();
			String valor = !entry.getValue().isEmpty() ? " (" + entry.getValue() + ")" : "";
			String enlace = enlacesPorClase.getOrDefault(claseFormateada, "");

			if (claseFormateada.trim().startsWith("com/simibubi/create")) {
				create = true;
			}
			if (claseFormateada.trim().startsWith("yesman/epicfight")) {
				epicfight = true;
			}
			if (claseFormateada.trim().startsWith("mod/azure/azurelib")) {
				azurelib = true;
			}

			if (claseFormateada.trim().startsWith("net/minecraftforge")
					|| claseFormateada.trim().startsWith("cpw/mods")) {
				minecraftforge = true;
			}
			if (claseFormateada.trim().startsWith("featurecreep/")
					|| claseFormateada.trim().startsWith("asbestosstar/")) {
				featurecreep = true;
			}
			if (claseFormateada.trim().startsWith("net/fabricmc/")) {
				fabricloader = true;
			}
			if (claseFormateada.trim().startsWith("net/neoforged/")) {
				neoforged = true;
			}
			if (claseFormateada.trim().startsWith("net/pillowmc/")) {
				pillowmc = true;
			}

			if (claseFormateada.trim().startsWith("net/minecraft/") || claseFormateada.trim().startsWith("game/")
					|| claseFormateada.trim().startsWith("juego/")
					|| claseFormateada.trim().startsWith("obf/class_unknown")) {
				if (!claseFormateada.trim().startsWith("net/minecraftforge/")) {
					minecraft = true;
				}
			}

			if (claseFormateada.trim().startsWith("dangerzone/")) {
				dangerzone = true;
			}

			html.append("<li>").append(claseFormateada).append(valor);
			if (!enlace.isEmpty()) {
				html.append(" ").append(enlace);
			}
			html.append("</li>");
		}
		html.append("</ul>");

		if (create) {
			html.append(MonitorDePID.idioma.faltar_de_clases_create());
		}
		if (epicfight) {
			html.append(MonitorDePID.idioma.faltar_de_clases_epicfight());
		}
		if (azurelib) {
			html.append(MonitorDePID.idioma.faltar_de_clases_azurelib());
		}

		if (minecraft) {
			html.append(MonitorDePID.idioma.faltar_de_clases_minecraft());
		}
		if (dangerzone) {
			html.append(MonitorDePID.idioma.faltar_de_clases_dangerzone());
		}
		if (featurecreep) {
			html.append(MonitorDePID.idioma.faltar_de_clases_featurecreep());
		}

		if (modlauncher) {
			html.append(MonitorDePID.idioma.faltar_de_clases_modlauncher());
		}
		if (minecraftforge) {
			html.append(MonitorDePID.idioma.faltar_de_clases_minecraftforge());
		}
		if (neoforged) {
			html.append(MonitorDePID.idioma.faltar_de_clases_neoforged());
		}
		if (fabricloader) {
			html.append(MonitorDePID.idioma.faltar_de_clases_fabricloader());
		}
		if (pillowmc) {
			html.append(MonitorDePID.idioma.faltar_de_clases_pillowmc());
		}

		return MonitorDePID.idioma.falta_de_clases_fatales() + html;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_faltar_de_clases();
	}

	/**
	 * Completa el origen de cada clase faltante si actualmente está vacío, usando
	 * información agregada por VerificacionDeStackTrace (packs, modids, jars).
	 */
	private void completarOrigenesSiFaltan(VerificacionDeStackTrace vdst) {
		if (vdst == null || clases.isEmpty()) {
			return;
		}

		IndiceOrigenes indice = construirIndiceOrigenes(vdst);

		for (Map.Entry<String, String> e : clases.entrySet()) {
			String clase = e.getKey();

			if (esClaseNoRelevante(clase)) {
				continue;
			}

			String actual = e.getValue();
			if (actual != null && !actual.isEmpty()) {
				continue;
			}

			String origen = inferirOrigenParaClaseRapido(clase, indice);
			if (origen != null && !origen.isEmpty()) {
				e.setValue(origen);
			}
		}
	}

	private static class IndiceOrigenes {
		final Map<String, String> origenPorClase = new HashMap<>();
		final Map<String, String> origenPorPaquete = new HashMap<>();
		String primerJar = "";
		String primerModid = "";
	}

	private IndiceOrigenes construirIndiceOrigenes(VerificacionDeStackTrace vdst) {
		IndiceOrigenes idx = new IndiceOrigenes();

		if (vdst.trazos_completos == null) {
			return idx;
		}

		for (TraceInfo trace : vdst.trazos_completos) {
			if (trace == null || trace.lineas == null) {
				continue;
			}

			for (VerificacionDeStackTrace.LineaTrazo lt : trace.lineas) {
				if (lt == null || lt.origen == null || lt.origen.isEmpty() || lt.clase == null || lt.clase.isEmpty()) {
					continue;
				}

				String origen = lt.origen;
				String clase = lt.clase;

				idx.origenPorClase.putIfAbsent(clase, origen);

				int slash = clase.lastIndexOf('/');
				if (slash > 0) {
					String paquete = clase.substring(0, slash);
					if (!esPaqueteNoPermitido(paquete)) {
						idx.origenPorPaquete.putIfAbsent(paquete, origen);
					}
				}

				if (idx.primerJar.isEmpty() && origen.endsWith(".jar")
						&& !VerificacionDeStackTrace.isJarNoPermite(origen)) {
					idx.primerJar = origen;
				}

				if (idx.primerModid.isEmpty() && origen.indexOf('/') < 0 && !origen.endsWith(".jar")
						&& !VerificacionDeStackTrace.esModNoPermite(origen) && !esModidSospechoso(origen)) {
					idx.primerModid = origen;
				}
			}
		}

		return idx;
	}

	private String inferirOrigenParaClaseRapido(String claseSlash, IndiceOrigenes idx) {
		if (claseSlash == null || claseSlash.isEmpty() || idx == null) {
			return "";
		}

		String exacto = idx.origenPorClase.get(claseSlash);
		if (exacto != null && !exacto.isEmpty()) {
			return limpiarOrigen(exacto);
		}

		int slash = claseSlash.lastIndexOf('/');
		while (slash > 0) {
			String paquete = claseSlash.substring(0, slash);

			String origen = idx.origenPorPaquete.get(paquete);
			if (origen != null && !origen.isEmpty()) {
				String limpio = limpiarOrigen(origen);
				if (!limpio.isEmpty()) {
					return limpio;
				}
			}

			slash = claseSlash.lastIndexOf('/', slash - 1);
		}

		if (!idx.primerModid.isEmpty() && claseSlash.contains(idx.primerModid)) {
			return idx.primerModid;
		}

		if (!idx.primerJar.isEmpty()) {
			return idx.primerJar;
		}

		return "";
	}

	/**
	 * Extrae el "xxx.jar" de la clave usada en vdst.jars (que es "xxx.jar" +
	 * idiomaNivel + "nivel,linea").
	 */
	private String extraerJarDesdeClaveJars(String clave) {
		if (clave == null)
			return null;
		int p = clave.indexOf(".jar");
		if (p >= 0)
			return clave.substring(0, p + 4);
		return null;
	}

	// Extrae un origen desde una línea tipo:
	// "Failed to create mod instance. ModID: steampowered, class ..."
	// Devuelve el modid si es plausible y no está en la lista no permitida.
	private String extraerOrigenDeLineaModInstance(String linea) {
		if (linea == null)
			return "";

		String t = linea.trim();
		int idx = t.indexOf("ModID:");
		if (idx == -1)
			return "";

		// Cortar desde "ModID:" hacia adelante
		String tail = t.substring(idx + "ModID:".length()).trim();

		// El modid suele terminar en coma o espacio
		String modid = tail.split("[,\\s]")[0].trim();

		if (modid.isEmpty())
			return "";
		if (VerificacionDeStackTrace.esModNoPermite(modid))
			return "";
		if (esModidSospechoso(modid))
			return "";

		return modid;
	}

	// Busca origen en la línea anterior del log.
	// Solo se usa si NO encontramos origen en el stacktrace cercano.
	private String buscarOrigenEnLineaAnterior(int numeroLinea, Consola consola) {
		if (consola == null)
			return "";
		String[] lineas = consola.lineas_verificar;
		if (numeroLinea <= 0 || numeroLinea >= lineas.length)
			return "";

		String anterior = lineas[numeroLinea - 1];
		return extraerOrigenDeLineaModInstance(anterior);
	}

	@Override
	public QuickFix solucion() {

		// Asegurarse de trabajar con el conjunto filtrado de clases
		postProcesarSiNecesario();

		// Crear selector de clases solamente.
		// La nueva WaifuAPI busca en todos los cargadores/versiones a la vez.
		JComboBox<String> claseCombo = new JComboBox<>();

		// Clases mostradas en el combo: ya filtradas de kotlin/essential, etc.
		Set<String> clavesCombo = clasesFiltradas != null ? clasesFiltradas.keySet() : clases.keySet();
		claseCombo.setModel(new DefaultComboBoxModel<>(clavesCombo.toArray(new String[0])));

		if (claseCombo.getItemCount() > 0) {
			claseCombo.setSelectedIndex(0);
		}

		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionFaltasClases())
				.agregarComponente(new QuickFix.SelectorGUI(claseCombo))
				.agregarBoton(MonitorDePID.idioma.buscar(), (retener) -> {

					List<RespuestaWaifu.Mod> modsEncontrados = new ArrayList<>();

					String clase = (String) claseCombo.getSelectedItem();

					if (clase != null) {

						modsEncontrados.addAll(WaifuAPI.obtanerModDesdeClase(clase));

						JTextArea textoResultados = new JTextArea(15, 40);
						textoResultados.setEditable(false);

						if (modsEncontrados.isEmpty()) {

							textoResultados.setText(MonitorDePID.idioma.noResultados() + " " + clase);

						} else {

							StringBuilder sb = new StringBuilder();
							sb.append(MonitorDePID.idioma.modsEncontradosPara(clase)).append(":\n");

							for (RespuestaWaifu.Mod mod : modsEncontrados) {

								sb.append("\n").append(MonitorDePID.idioma.mod()).append(": ").append(mod.name);

								if (mod.cargador != null || mod.version_del_juego != null) {
									sb.append("\n").append(MonitorDePID.idioma.version()).append(": ")
											.append(mod.cargador != null ? mod.cargador
													: MonitorDePID.idioma.desconocido())
											.append(" ").append(mod.version_del_juego != null ? mod.version_del_juego
													: MonitorDePID.idioma.desconocida());
								}

								if (mod.claseEncontrada != null && !mod.claseEncontrada.isEmpty()) {
									sb.append("\n").append(MonitorDePID.idioma.claseEncontrada()).append(": ")
											.append(mod.claseEncontrada);
								}

								if (mod.cantidadClasesEncontradas != null) {
									sb.append("\n").append(MonitorDePID.idioma.coincidencias()).append(": ")
											.append(mod.cantidadClasesEncontradas);
								}

								if (mod.curseforgeProjectId != null) {
									sb.append("\n").append(MonitorDePID.idioma.curseForgeUrl())
											.append(": https://cflookup.com/").append(mod.curseforgeProjectId);
								}

								if (mod.modrinthProjectId != null) {
									sb.append("\n").append(MonitorDePID.idioma.modrinthUrl())
											.append(": https://modrinth.com/mod/").append(mod.modrinthProjectId);
								}

								sb.append("\n-------------------");
							}

							textoResultados.setText(sb.toString());
						}

						textoResultados.setCaretPosition(0);

						JOptionPane.showMessageDialog(null, new JScrollPane(textoResultados),
								MonitorDePID.idioma.resultadosDeBusqueda(), JOptionPane.INFORMATION_MESSAGE);
					}
				}, true).construir();
	}

	@Override
	public String id() {
		return "faltas_clases";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// De momento no reclamamos trazos concretos para evitar solaparnos con otros
		// analizadores más específicos.
		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}
