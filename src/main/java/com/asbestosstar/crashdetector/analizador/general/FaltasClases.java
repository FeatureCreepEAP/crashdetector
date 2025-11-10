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
import com.asbestosstar.crashdetector.TriMap;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu;
import com.asbestosstar.crashdetector.waifu.VersionWaifu;
import com.asbestosstar.crashdetector.waifu.WaifuAPI;

public class FaltasClases implements Verificaciones {

	private boolean activado = false;
	public boolean create = false;
	public boolean epicfight = false;
	public boolean azurelib = false;

	/**
	 * Mapa de clase formateada (com/x/Y -> com/x/Y) -> origen (modid/jar/paquete).
	 * Contiene TODAS las clases detectadas (antes de filtrar kotlin/essential).
	 */
	private final Map<String, String> clases = new HashMap<>();

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

	@Override
	public void verificar(Consola consola) {
		this.vdst = consola.verificacion_de_stacktrace;

		// Agregar clases faltantes desde stacktraces fatales
		for (TriMap.TripleKey<String, Integer, Integer> llave : vdst.clases_fatales_no_existentes.keySet()) {
			String claseCruda = llave.key1; // nombre de la clase tal cual viene
			int nivel_prioridad = llave.key2; // nivel de prioridad (no lo usamos aquí)
			int numero_linea_consola = llave.key3; // número de línea en la consola
			String sospechoso = vdst.clases_fatales_no_existentes.get(claseCruda, nivel_prioridad,
					numero_linea_consola);

			String claseFormateada = formatearClase(claseCruda);
			if (!esNombreClaseValido(claseFormateada)) {
				continue;
			}
			// Ignorar clases no relevantes (kotlin, gg/essential, etc.)
			if (esClaseNoRelevante(claseFormateada)) {
				continue;
			}
			// Si ya tenemos esta clase, no recalculamos origen ni enlace
			if (!todos.add(claseFormateada)) {
				continue;
			}

			String origenLimpio = limpiarOrigen(sospechoso);
			clases.put(claseFormateada, origenLimpio);

			String enlace = consola.agregarErrorALectador(numero_linea_consola, this);
			enlacesPorClase.put(claseFormateada, enlace);
		}

		// El resto del trabajo (por línea) se hace en verificar(Consola, String, int)
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (linea == null) {
			return;
		}

		// Saltar líneas con WARN (sin excepciones)
		if (linea.contains("/WARN]") || linea.contains("Warn")
				|| VerificacionDeStackTrace.esLineaDeAdvertenciaEstandar(linea)) {
			return;
		}

		// Eliminar prefijo de log tipo "[hh:mm:ss] [Server thread/INFO]: ..."
		int indiceDosPuntos = linea.indexOf(':');
		if (indiceDosPuntos != -1 && linea.charAt(0) == '[') {
			linea = linea.substring(indiceDosPuntos + 1).trim();
		}

		String claseCruda = null;

		// Procesar errores de clase faltante
		if (linea.contains("java.lang.ClassNotFoundException:") || linea.contains("java.lang.NoClassDefFoundError:")) {

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

		String claseFormateada = formatearClase(claseCruda);
		if (!esNombreClaseValido(claseFormateada)) {
			return;
		}
		// Ignorar clases no relevantes (kotlin, gg/essential, etc.)
		if (esClaseNoRelevante(claseFormateada)) {
			return;
		}
		// Si ya vimos esta clase (en fatales o en otra línea), no volvemos a buscar
		// origen
		if (!todos.add(claseFormateada)) {
			return;
		}

		// Buscar el origen en la misma línea o líneas cercanas
		String origen = encontrarOrigenEnLinea(linea, numero_de_linea, consola);
		String origenLimpio = limpiarOrigen(origen);
		clases.putIfAbsent(claseFormateada, origenLimpio);

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
	 * paquete) utilizando los métodos de VerificacionDeStackTrace en lugar de regex
	 */
	private String limpiarOrigen(String origen) {
		if (origen == null || origen.isEmpty()) {
			return "";
		}

		// 1. Si parece un modid directo (sin slash, sin punto, y no termina en .jar)
		if (!origen.contains("/") && !origen.contains(".") && !origen.endsWith(".jar")) {
			if (!VerificacionDeStackTrace.esModNoPermite(origen)) {
				return origen; // ej: "railways"
			}
		}

		// 2. Intentar extraer un JAR usando VerificacionDeStackTrace
		List<String> jars_encontrados = VerificacionDeStackTrace.extraerJarsDeLinea(origen);
		for (String jar : jars_encontrados) {
			if (jar.contains(".jar") && !VerificacionDeStackTrace.isJarNoPermite(jar)) {
				return jar;
			}
		}

		// 3. Intentar extraer modid usando VerificacionDeStackTrace
		String modid = VerificacionDeStackTrace.extraerModidDeLinea(origen);
		if (modid != null && !VerificacionDeStackTrace.esModNoPermite(modid)) {
			return modid;
		}

		// 4. Intentar extraer paquete usando VerificacionDeStackTrace
		String paquete = VerificacionDeStackTrace.extraerPaqueteDeLinea(origen);
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

		String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

		// Buscar hacia abajo (líneas siguientes)
		for (int i = numeroLinea + 1; i < Math.min(numeroLinea + 20, lineas.length); i++) {
			String siguienteLinea = lineas[i].trim();
			resultado = buscarOrigenEnLinea(siguienteLinea);
			if (!resultado.isEmpty()) {
				return resultado;
			}
		}

		// Buscar en las líneas anteriores
		for (int i = numeroLinea - 1; i >= Math.max(0, numeroLinea - 5); i--) {
			String lineaAnterior = lineas[i].trim();
			resultado = buscarOrigenEnLinea(lineaAnterior);
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
		// 1. Buscar JARs
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

		// 3. Buscar paquete
		String pack = VerificacionDeStackTrace.extraerPaqueteDeLinea(linea);
		if (pack != null) {
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

	// Validar que el nombre siga el patrón de una clase Java (en formato con puntos
	// o barras)
	private boolean esNombreClaseValido(String clase) {
		if (clase == null || clase.isEmpty()) {
			return false;
		}
		// Convertir a formato punto para validación
		String dotForm = clase.replace('/', '.');
		return dotForm.matches("[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)+");
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

		StringBuilder html = new StringBuilder("<ul>");
		for (Map.Entry<String, String> entry : clasesFiltradas.entrySet()) {
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
	 * Intenta inferir un origen plausible a partir de packs, modids y jars
	 * detectados en otros stacktraces. Prefiere: 1) pack que sea prefijo del
	 * paquete de la clase, 2) modid presente en la ruta, 3) jar cuyo nombre
	 * contenga el prefijo del paquete.
	 */
	private String inferirOrigenParaClase(String claseSlash, VerificacionDeStackTrace vdst) {
		if (claseSlash == null || claseSlash.isEmpty())
			return "";

		String pkgSlash = claseSlash.contains("/") ? claseSlash.substring(0, claseSlash.lastIndexOf('/')) : claseSlash;
		String[] seg = pkgSlash.split("/");
		String pref1 = seg.length >= 1 ? seg[0] : "";
		String pref2 = seg.length >= 2 ? (seg[0] + "/" + seg[1]) : pref1;

		// 1) packs: buscar pack que sea prefijo del paquete de la clase
		for (TriMap.TripleKey<String, Integer, Integer> k : vdst.packs.keySet()) {
			String packDot = k.key1; // "com.ejemplo"
			String packSlash = packDot.replace('.', '/');
			if (!packSlash.isEmpty() && (pkgSlash.startsWith(packSlash) || packSlash.startsWith(pref2))) {
				return packDot;
			}
		}

		// 2) modids: si el modid aparece dentro del path de la clase o prefijos
		for (TriMap.TripleKey<String, Integer, Integer> k : vdst.modids.keySet()) {
			String modid = k.key1;
			if (modid == null || modid.isEmpty())
				continue;
			if (claseSlash.contains(modid) || pref1.equals(modid) || pref2.contains(modid)) {
				return modid;
			}
		}

		// 3) jars: elegir jar cuyo nombre contenga el prefijo del paquete
		String[] candidatos = { pref1, seg.length >= 2 ? seg[1] : "" };
		for (Map.Entry<String, Boolean> ent : vdst.jars.entrySet()) {
			String clave = ent.getKey(); // "jarName.jar" + nivel + "x,y"
			String jar = extraerJarDesdeClaveJars(clave);
			if (jar == null || jar.isEmpty())
				continue;
			String low = jar.toLowerCase();
			for (String c : candidatos) {
				if (c != null && !c.isEmpty() && low.contains(c.toLowerCase())) {
					return jar;
				}
			}
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

	@Override
	public QuickFix solucion() {
		// Asegurarse de trabajar con el conjunto filtrado de clases
		postProcesarSiNecesario();

		// Crear selectores
		JComboBox<String> cargadorCombo = new JComboBox<>();
		JComboBox<String> versionCombo = new JComboBox<>();
		JComboBox<String> claseCombo = new JComboBox<>();

		// Obtener lista de versiones soportadas
		Map<String, List<String>> versionesPorCargador = new HashMap<>();
		for (VersionWaifu version : WaifuAPI.versiones) {
			versionesPorCargador.computeIfAbsent(version.cargador, k -> new ArrayList<>())
					.add(version.version_del_juego);
		}

		// Configurar combo de cargadores
		cargadorCombo.setModel(new DefaultComboBoxModel<>(versionesPorCargador.keySet().toArray(new String[0])));

		// Configurar combo de versiones
		cargadorCombo.addActionListener(e -> {
			String cargador = (String) cargadorCombo.getSelectedItem();
			if (cargador != null) {
				versionCombo.setModel(
						new DefaultComboBoxModel<>(versionesPorCargador.get(cargador).toArray(new String[0])));
			}
		});

		// Clases mostradas en el combo: ya filtradas de kotlin/essential, etc.
		Set<String> clavesCombo = clasesFiltradas != null ? clasesFiltradas.keySet() : clases.keySet();
		claseCombo.setModel(new DefaultComboBoxModel<>(clavesCombo.toArray(new String[0])));

		// Seleccionar primeros elementos
		if (cargadorCombo.getItemCount() > 0) {
			cargadorCombo.setSelectedIndex(0);
			versionCombo.setModel(new DefaultComboBoxModel<>(
					versionesPorCargador.get(cargadorCombo.getSelectedItem()).toArray(new String[0])));
		}
		if (claseCombo.getItemCount() > 0) {
			claseCombo.setSelectedIndex(0);
		}

		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionFaltasClases())
				.agregarComponente(new QuickFix.SelectorGUI(cargadorCombo))
				.agregarComponente(new QuickFix.SelectorGUI(versionCombo))
				.agregarComponente(new QuickFix.SelectorGUI(claseCombo))
				.agregarBoton(MonitorDePID.idioma.buscar(), (retener) -> {
					List<RespuestaWaifu.Mod> modsEncontrados = new ArrayList<>();

					String cargador = (String) cargadorCombo.getSelectedItem();
					String version = (String) versionCombo.getSelectedItem();
					String clase = (String) claseCombo.getSelectedItem();

					if (cargador != null && version != null && clase != null) {
						VersionWaifu versionSeleccionada = WaifuAPI.obtainerVersion(cargador, version);
						modsEncontrados.addAll(WaifuAPI.obtanerModDesdeClase(clase, versionSeleccionada));

						// Crear popup con resultados
						JTextArea textoResultados = new JTextArea(15, 40);
						textoResultados.setEditable(false);

						if (modsEncontrados.isEmpty()) {
							textoResultados.setText(MonitorDePID.idioma.noResultados() + " " + clase);
						} else {
							StringBuilder sb = new StringBuilder("Mods encontrados para ").append(clase).append(":\n");

							for (RespuestaWaifu.Mod mod : modsEncontrados) {
								sb.append("\nMod: ").append(mod.name);
								if (mod.curseforgeProjectId != null) {
									sb.append("\nCurseForge URL: https://api.waifu.neoforged.net/mod_url/")
											.append(mod.curseforgeProjectId);
								}
								if (mod.modrinthProjectId != null) {
									sb.append("\nModrinth URL: https://api.waifu.neoforged.net/mod_url/")
											.append(mod.modrinthProjectId);
								}
								sb.append("\n-------------------");
							}
							textoResultados.setText(sb.toString());
						}

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

}
