package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
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

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

		// Agregar clases faltantes desde stacktraces fatales
		for (Entry<String, String> clase : vdst.fatal_clases_no_existe.entrySet()) {
			if (todos.add(clase.getKey())) {
				clases.put(clase.getKey().replace(".", "/"), clase.getValue());
			}
		}

		// Procesar línea por línea la consola
		for (String linea : contenidoConsola.split(Verificaciones.nl)) {
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
				clases.putIfAbsent(clase.replace(".", "/"), "");
			}
		}

		// TODO mejor
		for (String clase : clases.keySet()) {
			if (clase.startsWith("gg/essential/") || clase.startsWith("kotlin/") || clase.startsWith("kotlinx/")) {
				clases.remove(clase);
			}

		}

		activado = !clases.isEmpty();
	}

	// Validar que el nombre siga el patrón de una clase Java
	private boolean esNombreClaseValido(String clase) {
		return clase.matches("[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)+");
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
		for (Entry<String, String> clase : clases.entrySet()) {
			String valor = "";
			if (!clase.getValue().isEmpty()) {
				valor = " (" + clase.getValue() + ")";
			}
			if (clase.getKey().trim().startsWith("com/simibubi/create")) {
				create = true;
			}
			if (clase.getKey().trim().startsWith("yesman/epicfight")) {
				epicfight = true;
			}
			html.append("<li>").append(clase.getKey()).append(valor).append("</li>");

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