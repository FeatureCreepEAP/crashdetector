package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.TriMap;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu;
import com.asbestosstar.crashdetector.waifu.VersionWaifu;
import com.asbestosstar.crashdetector.waifu.WaifuAPI;

public class FaltasClases implements Verificaciones {

	private boolean activado = false;
	public boolean create = false;
	public boolean epicfight = false;
	private final Map<String, String> clases = new HashMap<>();
	public final Set<String> todos = new LinkedHashSet<>();
	private final Map<String, String> enlacesPorClase = new HashMap<>(); // Clase en formato ruta -> enlace HTML

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

		// Agregar clases faltantes desde stacktraces fatales
		for (TriMap.TripleKey<String, Integer, Integer> key : vdst.clases_fatales_no_existentes.keySet()) {
			String clase = key.key1; // nombre de la clase
			int nivel_prioridad = key.key2; // nivel de prioridad
			int numero_linea_consola = key.key3; // número de línea en la consola
			String sospechoso = vdst.clases_fatales_no_existentes.get(clase, nivel_prioridad, numero_linea_consola);

			// Limpiar el origen usando los métodos de VerificacionDeStackTrace
			String origenLimpio = limpiarOrigen(sospechoso);

			// Asegurarnos de que la clase está en formato con barras
			String claseFormateada = formatearClase(clase);
			if (todos.add(clase)) {
				clases.put(claseFormateada, origenLimpio);
				String enlace = consola.agregarErrorALectador(numero_linea_consola, this);
				enlacesPorClase.put(claseFormateada, enlace);
			}
		}

		// Procesar línea por línea la consola
		String[] lineas = contenidoConsola.split(Verificaciones.nl);
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];

			// Eliminar prefijo de log
			int indiceDosPuntos = linea.indexOf(':');
			if (indiceDosPuntos != -1 && linea.charAt(0) == '[') {
				linea = linea.substring(indiceDosPuntos + 1).trim();
			}

			// Saltar líneas con WARN (sin excepciones)
			if (linea.contains("/WARN]") || linea.contains("Warn")) {
				continue;
			}

			String clase = null;

			// Procesar errores de clase faltante
			if (linea.contains("java.lang.ClassNotFoundException:")
					|| linea.contains("java.lang.NoClassDefFoundError:")) {

				String[] llevas = { "java.lang.ClassNotFoundException:", "java.lang.NoClassDefFoundError:" };

				for (String lleva : llevas) {
					int index = linea.indexOf(lleva);
					if (index != -1) {
						String candidate = linea.substring(index + lleva.length()).trim();
						if (!candidate.isEmpty()) {
							clase = candidate.split("[\\s\\)]")[0].trim();
							break;
						}
					}
				}

			} else if (linea.contains("Error loading class:")) {
				// Este caso ya no debería ocurrir por el filtro anterior
				int index = linea.indexOf("Error loading class:");
				if (index != -1) {
					String candidate = linea.substring(index + "Error loading class:".length()).trim();
					if (!candidate.isEmpty()) {
						clase = candidate.split("[\\s\\)]")[0].trim();
					}
				}
			}

			// Validar formato de clase antes de agregarla
			if (clase != null && esNombreClaseValido(clase) && todos.add(clase)) {
				String claseFormateada = formatearClase(clase);
				// Buscar el origen en la misma línea o líneas cercanas
				String origen = encontrarOrigenEnLinea(linea, i, consola);
				String origenLimpio = limpiarOrigen(origen);
				clases.putIfAbsent(claseFormateada, origenLimpio);
				String enlace = consola.agregarErrorALectador(i, this);
				enlacesPorClase.put(claseFormateada, enlace);
			}
		}

		// Filtrar clases no relevantes
		for (String clase : new ArrayList<>(clases.keySet())) {
			if (clase.startsWith("gg/essential/") || clase.startsWith("kotlin/") || clase.startsWith("kotlinx/")) {
				String enlace = enlacesPorClase.get(clase);
				clases.remove(clase);
				enlacesPorClase.remove(clase);
			}
		}

		activado = !clases.isEmpty();
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
	 * Verifica si un paquete está en la lista de elementos no permitidos
	 */
	private boolean esPaqueteNoPermitido(String pack) {
		for (String prefix : VerificacionDeStackTrace.package_no_permite) {
			if (pack.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Busca el origen (JAR, modid o paquete) en la línea actual o en líneas
	 * cercanas utilizando los métodos de VerificacionDeStackTrace en lugar de
	 * implementar lógica propia
	 */
	private String encontrarOrigenEnLinea(String linea, int numeroLinea, Consola consola) {
		// Primero intentamos encontrar en la misma línea usando los métodos existentes
		String resultado = buscarOrigenEnLinea(linea);
		if (!resultado.isEmpty()) {
			return resultado;
		}

		// Si no encontramos en la misma línea, buscar en las líneas cercanas
		String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

		// ¡CRUCIAL! Buscar desde el fondo hacia arriba (cerca del error), no desde
		// arriba
		for (int i = numeroLinea + 1; i < Math.min(numeroLinea + 20, lineas.length); i++) {
			String siguienteLinea = lineas[i].trim();
			resultado = buscarOrigenEnLinea(siguienteLinea);
			if (!resultado.isEmpty()) {
				return resultado;
			}
		}

		// También buscar en las líneas anteriores (a veces el error está en líneas
		// previas)
		for (int i = numeroLinea - 1; i >= Math.max(0, numeroLinea - 5); i--) {
			String lineaAnterior = lineas[i].trim();
			resultado = buscarOrigenEnLinea(lineaAnterior);
			if (!resultado.isEmpty()) {
				return resultado;
			}
		}

		// Si no encontramos nada, devolvemos una cadena vacía
		return "";
	}

	/**
	 * Busca origen en una línea específica utilizando los métodos existentes
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
			// No necesitamos verificar con packNoEsPermite porque ya lo hace el método
			// original
			return pack;
		}

		return "";
	}

	/**
	 * Formatea un nombre de clase a formato con barras en lugar de puntos
	 */
	private String formatearClase(String clase) {
		// Primero eliminamos cualquier contenido adicional después de la clase
		int indiceParentesis = clase.indexOf('(');
		if (indiceParentesis != -1) {
			clase = clase.substring(0, indiceParentesis).trim();
		}

		int indiceEspacio = clase.indexOf(' ');
		if (indiceEspacio != -1) {
			clase = clase.substring(0, indiceEspacio).trim();
		}

		// Convertimos puntos a barras
		return clase.replace(".", "/");
	}

	// Validar que el nombre siga el patrón de una clase Java
	private boolean esNombreClaseValido(String clase) {
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
		return activado;
	}

	@Override
	public float prioridad() {
		return 925.0f; // Máxima prioridad para errores de clases faltantes
	}

	@Override
	public String mensaje() {
		if (clases.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (Map.Entry<String, String> entry : clases.entrySet()) {
			String claseFormateada = entry.getKey();
			String valor = !entry.getValue().isEmpty() ? " (" + entry.getValue() + ")" : "";
			String enlace = enlacesPorClase.getOrDefault(claseFormateada, "");

			if (claseFormateada.trim().startsWith("com/simibubi/create")) {
				create = true;
			}
			if (claseFormateada.trim().startsWith("yesman/epicfight")) {
				epicfight = true;
			}

			html.append("<li>").append(claseFormateada).append(valor);
			// Agregar el enlace solo si existe
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

		return MonitorDePID.idioma.falta_de_clases_fatales() + html;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_faltar_de_clases();
	}

	// En la clase FaltasClases
	@Override
	public QuickFix solucion() {
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

		// Configurar combo de clases
		claseCombo.setModel(new DefaultComboBoxModel<>(clases.keySet().toArray(new String[0])));

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
}