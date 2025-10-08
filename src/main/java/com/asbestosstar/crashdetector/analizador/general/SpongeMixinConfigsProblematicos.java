package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EliminadorDeMod;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.buscar.Buscardor;

public class SpongeMixinConfigsProblematicos implements Verificaciones {

	private boolean activado = false;
	private final Map<String, Integer> sm_config_con_linea = new HashMap<>();
	private final Map<String, Boolean> sm_config_es_fatal = new HashMap<>();
	private final Map<String, String> enlacesPorConfig = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		sm_config_con_linea.clear();
		sm_config_es_fatal.clear();
		enlacesPorConfig.clear();

		// Origen existente: parser de stacktrace
		BiMap<String, Integer, Boolean> configs = consola.verificacion_de_stacktrace.sm_config;
		for (BiMap.DoubleKey<String, Integer> clave : configs.keySet()) {
			String nombreArchivo = clave.key0;
			int linea = clave.key1;
			boolean esFatal = configs.get(nombreArchivo, linea);

			sm_config_con_linea.put(nombreArchivo, linea);
			sm_config_es_fatal.put(nombreArchivo, esFatal);
			enlacesPorConfig.put(nombreArchivo, consola.agregarErrorALectador(linea, this));
		}

		activado = !sm_config_con_linea.isEmpty();
	}

	@Override
	public void verificar(Consola consola, String linea, int i) {
		if (linea.contains("The specified resource '") && linea.contains("' was invalid or could not be read")) {
			int ini = linea.indexOf("The specified resource '") + "The specified resource '".length();
			int fin = linea.indexOf("'", ini);
			if (fin > ini) {
				String nombre = linea.substring(ini, fin).trim();
				// Solo mixins *.json
				if (nombre.contains("mixins") && nombre.endsWith(".json") && !sm_config_con_linea.containsKey(nombre)) {
					sm_config_con_linea.put(nombre, i);
					sm_config_es_fatal.put(nombre, true);
					enlacesPorConfig.put(nombre, consola.agregarErrorALectador(i, this));
					activado = true;
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new SpongeMixinConfigsProblematicos();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f; // Prioridad media-alta para errores de inicialización
	}

	@Override
	public String mensaje() {
		if (sm_config_con_linea.isEmpty())
			return "";
		Buscardor.cargar();

		StringBuilder html = new StringBuilder();
		html.append("<span style='color: #").append(Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas())
				.append("; font-weight: bold;'>").append(MonitorDePID.idioma.config_spongemixin_problematico())
				.append("</span>").append(Verificaciones.nl_html).append("<ul>");

		List<String> listItems = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : sm_config_con_linea.entrySet()) {
			String sm = entry.getKey();
			int lineNumber = entry.getValue();
			boolean isFatal = sm_config_es_fatal.getOrDefault(sm, false);

			String jars_de_sm_string = "";
			List<String> jars_de_sm = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(sm));

			if (!jars_de_sm.isEmpty()) {
				List<String> boldJars = new ArrayList<>();
				for (String jar : jars_de_sm) {
					boldJars.add("<strong>" + jar + "</strong>");
				}

				jars_de_sm_string = " (" + String.join(", ", boldJars) + ")";
			}

			// Obtener el enlace para esta configuración
			String enlace = enlacesPorConfig.getOrDefault(sm, "");

			// Si es fatal, agregar indicación
			String prefix = isFatal ? MonitorDePID.idioma.posibilidad_fatal() : "";

			listItems.add("<li>" + prefix + sm + jars_de_sm_string + " " + enlace + "</li>");
		}

		html.append(String.join("", listItems)).append("</ul>");

		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_spongemixin_configs_problematicos();
	}

	@Override
	public QuickFix solucion() {
		QuickFix.Builder builder = new QuickFix.Builder(
				MonitorDePID.idioma.nombre_de_spongemixin_configs_problematicos());

		// Agregar botón para cada JAR encontrado
		for (String sm : sm_config_con_linea.keySet()) {
			List<String> jars = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(sm));

			for (String jar : jars) {
				String buttonText = MonitorDePID.idioma.eliminar() + ": " + jar;

				builder.agregarBoton(buttonText, retener -> {
					try {
						// Eliminar el JAR completo
						EliminadorDeMod.eliminarMod(jar);

						// Mostrar mensaje solo si no es modo headless
						if (!EliminadorDeMod.esModoHeadless()) {
							JOptionPane.showMessageDialog(null,
									MonitorDePID.idioma.jar_eliminado_exitosamente() + ": " + jar,
									MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e) {
						// Log de error
						CrashDetectorLogger.logException(e);

						// Mensaje de error solo en modo GUI
						if (!EliminadorDeMod.esModoHeadless()) {
							JOptionPane.showMessageDialog(null,
									MonitorDePID.idioma.error_al_eliminar_jar() + ": " + jar,
									MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		}

		return builder.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "spongemixin_configs_problematicos";
	}
}
