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
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones.Criticalidad;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu;
import com.asbestosstar.crashdetector.waifu.VersionWaifu;
import com.asbestosstar.crashdetector.waifu.WaifuAPI;

public class AdvertenciaFaltasClases implements Verificaciones {

	private boolean activado = false;
	private final Set<String> clases = new LinkedHashSet<>();
	private final Map<String, String> enlacesPorClase = new HashMap<>(); // Clase -> enlace HTML

	@Override
	public void verificar(Consola consola) {
	    String contenidoConsola = consola.contenido_verificar;
	    
	    // Dividir el contenido UNA SOLA VEZ (no en cada iteración)
	    String[] lineas = contenidoConsola.split(Verificaciones.nl);
	    
	    for (int i = 0; i < lineas.length; i++) {
	        String linea = lineas[i];
	        if (linea.contains("Error loading class:") && linea.contains("WARN")) {
	            try {
	                String clase = linea.split("Error loading class: ")[1].split(" ")[0].trim();
	                String claseFormateada = clase.replace(".", "/");
	                
	                // Solo registrar el enlace si es una clase nueva
	                if (clases.add(claseFormateada)) {
	                    String enlace = consola.agregarErrorALectador(i, this);
	                    enlacesPorClase.put(claseFormateada, enlace);
	                }
	            } catch (Exception ignored) {
	                // Registrar sin procesamiento de clase
	                consola.agregarErrorALectador(i, this);
	            }
	        }
	    }
	    
	    activado = !clases.isEmpty();
	}

	@Override
	public Verificaciones nueva() {
		return new AdvertenciaFaltasClases();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 2.0f;
	}

	@Override
	public String mensaje() {
		if (clases.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String clase : clases) {
			String enlace = enlacesPorClase.get(clase);
			html.append("<li>").append(clase).append(" - ").append(enlace).append("</li>");
		}
		html.append("</ul>");

		return MonitorDePID.idioma.faltar_de_clases_advertencia() + html.toString();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_faltar_de_clases_advertencia();
	}

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
		claseCombo.setModel(new DefaultComboBoxModel<>(clases.toArray(new String[0])));

		// Seleccionar primeros elementos
		if (cargadorCombo.getItemCount() > 0) {
			cargadorCombo.setSelectedIndex(0);
			versionCombo.setModel(new DefaultComboBoxModel<>(
					versionesPorCargador.get(cargadorCombo.getSelectedItem()).toArray(new String[0])));
		}
		if (claseCombo.getItemCount() > 0) {
			claseCombo.setSelectedIndex(0);
		}

		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionParaAdvertenciaFaltasClases())
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
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "advertencia_faltas_clases";
	}
	
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}

}