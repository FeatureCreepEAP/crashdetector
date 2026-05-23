package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.mapas.TriMap;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu;
import com.asbestosstar.crashdetector.waifu.WaifuAPI;

public class FaltasClases implements Verificaciones {

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
	 * Marca para asegurarnos de que el post-procesado (completar orígenes +
	 * filtrado) solo se hace una vez.
	 */
	private boolean postProcesado = false;

	public boolean posible = false;

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
	public void verificar(Consola consola) {
		this.vdst = consola.verificacion_de_stacktrace;
		String cont = consola.contenido_verificar;

		if (cont.contains("ClassMetadataNotFoundException") && !cont.contains("ClassNotFoundException")
				&& !cont.contains("NoClassDefFoundError") && vdst.clases_fatales_no_existentes.isEmpty()

		) {
			return;
		}
		posible = true;

		// Agregar clases faltantes desde stacktraces fatales
		for (TriMap.TripleKey<String, Integer, Integer> llave : vdst.clases_fatales_no_existentes.keySet()) {
			String claseCruda = llave.key1; // nombre de la clase tal cual viene
			int nivel_prioridad = llave.key2; // nivel de prioridad (no lo usamos aquí)
			int numero_linea_consola = llave.key3; // número de línea en la consola
			String sospechoso = vdst.clases_fatales_no_existentes.get(claseCruda, nivel_prioridad,
					numero_linea_consola);

			if (numero_linea_consola > 0) {
				String linea_menos1 = consola.lineas_verificar[numero_linea_consola - 1];
				if (linea_menos1.toLowerCase().contains("catching")
						|| linea_menos1.toLowerCase().contains("rhino.CachedClassInfo")) {
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

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null) {
			return;
		}
		if (!posible) {
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
				linea = linea.substring(indiceDosPuntos + 1).trim();
			}

			String[] llevas = { "java.lang.ClassNotFoundException:", "java.lang.NoClassDefFoundError:" };

			for (String lleva : llevas) {
				int index = linea.indexOf(lleva);
				if (index != -1) {
					String candidato = linea.substring(index + lleva.length()).trim();
					if (!candidato.isEmpty()) {
						claseCruda = candidato.split("[\\s\\)]")[0].trim();
						break;
					}
				}
			}

		} else if (linea.contains("Error loading class:")) {
			// Caso antiguo, se deja por seguridad
			int index = linea.indexOf("Error loading class:");
			if (index != -1) {
				String candidato = linea.substring(index + "Error loading class:".length()).trim();
				if (!candidato.isEmpty()) {
					claseCruda = candidato.split("[\\s\\)]")[0].trim();
				}
			}
		}

		if (claseCruda == null) {
			return;
		}

		if (numero_de_linea > 0) {
			String linea_menos1 = consola.lineas_verificar[numero_de_linea - 1];
			// CrashDetectorLogger.log(linea_menos1+ " linea menos 1");
			if (linea_menos1.toLowerCase().contains("catching")
					|| linea_menos1.toLowerCase().contains("rhino.CachedClassInfo")) {// A veces lineas tiene esta
				return;// TODO apender a Advertencia faltas clases
			}
		}

		if (linea.contains("Valkyrian skies compatibility disabled")) {// A veces lineas tiene esta
			return;// TODO apender a Advertencia faltas clases
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
		// Buscar el origen en la misma línea o líneas cercanas
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
			String actual = clases.getOrDefault(claseFormateada, "");
			if ((actual == null || actual.isEmpty()) && origenLimpio != null && !origenLimpio.isEmpty()) {
				clases.put(claseFormateada, origenLimpio);
			}
			return;
		}

		// Primera vez que aparece
		clases.put(claseFormateada, origenLimpio);

		CrashDetectorLogger.log("Fatals clases clase no advertencia " + claseFormateada);

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
		if (l == null)
			return false;
		String t = l.trim();

		// Permitir líneas comentadas tipo "// at ..."
		if (t.startsWith("//"))
			t = t.substring(2).trim();

		return t.startsWith("at ") || t.startsWith("Caused by:") || t.startsWith("Suppressed:") || t.startsWith("...")
				|| t.startsWith("SECURE-BOOTSTRAP");
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
	 * Si ignoremos un clase o linea
	 * 
	 * @return
	 */
	public static boolean ignorarClaseOLinea(String str) {
		for (String ign : ignorar) {
			if (str.replace(".", "/").contains(ign.replace(".", "/"))) {
				return true;
			}
		}
		return false;
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

		String[] lineas = consola.lineas_verificar;

		// Buscar hacia abajo SOLO dentro del mismo bloque de stacktrace
		for (int i = numeroLinea + 1; i < lineas.length && i <= numeroLinea + 50; i++) {
			String siguiente = lineas[i];
			if (!esLineaStackish(siguiente)) {
				break; // Ya salimos del stacktrace, no seguir buscando
			}
			resultado = buscarOrigenEnLinea(siguiente.trim());
			if (!resultado.isEmpty()) {
				return resultado;
			}
		}

		// Buscar hacia arriba SOLO dentro del mismo bloque de stacktrace
		for (int i = numeroLinea - 1; i >= 0 && i >= numeroLinea - 20; i--) {
			String anterior = lineas[i];
			if (!esLineaStackish(anterior)) {
				break; // Ya salimos del stacktrace, no seguir buscando
			}
			resultado = buscarOrigenEnLinea(anterior.trim());
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
		// 1) Buscar JARs (prioridad más alta)
		List<String> jarsEncontrados = VerificacionDeStackTrace.extraerJarsDeLinea(linea);
		for (String jar : jarsEncontrados) {
			if (jar.contains(".jar") && !VerificacionDeStackTrace.isJarNoPermite(jar)) {
				return jar;
			}
		}

		// 2) Buscar modid:
		// IMPORTANTE: ignorar modids detectados desde líneas "handler$"
		// porque suelen ser artefactos de mixins y generan falsos positivos.
		if (!linea.contains("handler$")) {// TODO filtrar hablerers importantes, pero no hld000 o cdk000
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

	/**
	 * Formatea un nombre de clase a formato con barras en lugar de puntos,
	 * eliminando cualquier basura extra (paréntesis, espacios, etc.).
	 */
	private String formatearClase(String clase) {
		if (clase == null) {
			return "";
		}
		// Quitar contenido después de '('
		int indiceParentesis = clase.indexOf('(');
		if (indiceParentesis != -1) {
			clase = clase.substring(0, indiceParentesis).trim();
		}
		// Quitar contenido después de primer espacio
		int indiceEspacio = clase.indexOf(' ');
		if (indiceEspacio != -1) {
			clase = clase.substring(0, indiceEspacio).trim();
		}
		// Convertir puntos a barras
		return clase.replace(".", "/");
	}

	// Validar que el nombre siga el patrón de una clase Java.
	// Ahora permite '$' para clases internas (inner classes).
	private boolean esNombreClaseValido(String clase) {
		if (clase == null || clase.isEmpty()) {
			return false;
		}
		// Convertir a formato punto para validación
		String dotForm = clase.replace('/', '.');

		// Segmentos tipo paquete/clase:
		// - empiezan por letra o '_'
		// - luego letras, dígitos, '_' o '$' (para inner classes)
		return dotForm.matches("[a-zA-Z_][a-zA-Z0-9_\\$]*(\\.[a-zA-Z_][a-zA-Z0-9_\\$]*)+");
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

			if (claseFormateada.trim().startsWith("net/minecraftforge")) {
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
		if (vdst == null || clases.isEmpty())
			return;

		for (Map.Entry<String, String> e : clases.entrySet()) {
			String clase = e.getKey();
			if (esClaseNoRelevante(clase)) {
				continue;
			}
			if (e.getValue() != null && !e.getValue().isEmpty())
				continue;

			String origen = inferirOrigenParaClase(clase, vdst);
			if (origen != null && !origen.isEmpty()) {
				e.setValue(origen);
			}
		}
	}

	/**
	 * Intenta inferir un origen plausible (jar / modid / paquete) para una clase
	 * faltante usando los TraceInfo ya construidos.
	 *
	 * Orden de preferencia: 1) Línea cuya clase coincida exactamente 2) Línea cuyo
	 * paquete sea prefijo más largo 3) Modid plausible visto en trazos relacionados
	 * 4) Jar permitido visto en trazos relacionados
	 */
	private String inferirOrigenParaClase(String claseSlash, VerificacionDeStackTrace vdst) {
		if (claseSlash == null || claseSlash.isEmpty() || vdst == null) {
			return "";
		}

		// Seguridad: usar solo el nuevo contenedor canónico
		List<TraceInfo> trazos;
		try {
			trazos = vdst.trazos_completos;
		} catch (Throwable t) {
			return "";
		}

		if (trazos == null || trazos.isEmpty()) {
			return "";
		}

		String paqueteClase = claseSlash.contains("/") ? claseSlash.substring(0, claseSlash.lastIndexOf('/'))
				: claseSlash;

		String mejorPaquete = "";
		String mejorModid = "";
		String mejorJar = "";

		for (TraceInfo trace : trazos) {
			if (trace == null || trace.lineas == null) {
				continue;
			}

			for (VerificacionDeStackTrace.LineaTrazo lt : trace.lineas) {
				if (lt == null) {
					continue;
				}

				String origen = lt.origen;
				String claseLinea = lt.clase;

				if (origen == null || origen.isEmpty()) {
					continue;
				}

				// 1) Coincidencia EXACTA de clase → máxima prioridad
				if (claseLinea != null && claseLinea.equals(claseSlash)) {
					String limpio = limpiarOrigen(origen);
					if (!limpio.isEmpty()) {
						return limpio;
					}
				}

				// 2) Paquete más específico permitido
				if (claseLinea != null && claseLinea.startsWith(paqueteClase)) {
					String paqueteLinea = claseLinea.contains("/")
							? claseLinea.substring(0, claseLinea.lastIndexOf('/'))
							: "";

					if (!paqueteLinea.isEmpty() && paqueteClase.startsWith(paqueteLinea)
							&& paqueteLinea.length() > mejorPaquete.length() && !esPaqueteNoPermitido(paqueteLinea)) {

						mejorPaquete = paqueteLinea;
					}
				}

				// 3) Modid plausible
				if (mejorModid.isEmpty() && !origen.contains("/") && !origen.endsWith(".jar")
						&& !VerificacionDeStackTrace.esModNoPermite(origen) && !esModidSospechoso(origen)) {

					// heurística: el modid aparece en el paquete
					if (paqueteClase.contains(origen)) {
						mejorModid = origen;
					}
				}

				// 4) Jar permitido
				if (mejorJar.isEmpty() && origen.endsWith(".jar") && !VerificacionDeStackTrace.isJarNoPermite(origen)) {

					mejorJar = origen;
				}
			}
		}

		// Resolver por prioridad
		if (!mejorPaquete.isEmpty()) {
			return mejorPaquete.replace('/', '.');
		}
		if (!mejorModid.isEmpty()) {
			return mejorModid;
		}
		if (!mejorJar.isEmpty()) {
			return mejorJar;
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
							sb.append("Mods encontrados para ").append(clase).append(":\n");

							for (RespuestaWaifu.Mod mod : modsEncontrados) {

								sb.append("\nMod: ").append(mod.name);

								if (mod.cargador != null || mod.version_del_juego != null) {
									sb.append("\nVersión: ").append(mod.cargador != null ? mod.cargador : "desconocido")
											.append(" ").append(mod.version_del_juego != null ? mod.version_del_juego
													: "desconocida");
								}

								if (mod.claseEncontrada != null && !mod.claseEncontrada.isEmpty()) {
									sb.append("\nClase encontrada: ").append(mod.claseEncontrada);
								}

								if (mod.cantidadClasesEncontradas != null) {
									sb.append("\nCoincidencias: ").append(mod.cantidadClasesEncontradas);
								}

								if (mod.curseforgeProjectId != null) {
									sb.append("\nCurseForge URL: https://cflookup.com/")
											.append(mod.curseforgeProjectId);
								}

								if (mod.modrinthProjectId != null) {
									sb.append("\nModrinth URL: https://modrinth.com/mod/")
											.append(mod.modrinthProjectId);
								}

								sb.append("\n-------------------");
							}

							textoResultados.setText(sb.toString());
						}

						textoResultados.setCaretPosition(0);

						JOptionPane.showMessageDialog(null, new JScrollPane(textoResultados), "Resultados de búsqueda",
								JOptionPane.INFORMATION_MESSAGE);
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

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
