package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EliminadorDeMod;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.mapas.BiMap;

public class SpongeMixinConfigsProblematicos implements VerificacionRapida {

	private boolean activado = false;
	private final Map<String, Integer> sm_config_con_linea = new HashMap<>();
	private final Map<String, Boolean> sm_config_es_fatal = new HashMap<>();
	private final Map<String, String> enlacesPorConfig = new HashMap<>();
	public boolean posibleErrorMixinPorLinea = false;

	private static final String TEXTO_RESOURCE = "The specified resource '";
	private static final String TEXTO_INVALID_OR_UNREADABLE = "' was invalid or could not be read";
	private static final String TEXTO_MIXINS = "mixins";
	private static final String TEXTO_JSON = ".json";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_RESOURCE, TEXTO_INVALID_OR_UNREADABLE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneMixinConfigProblematico(evento.linea)) {
			posibleErrorMixinPorLinea = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
//		sm_config_con_linea.clear();
//		sm_config_es_fatal.clear();
//		enlacesPorConfig.clear();

		if (consola == null) {
			return;
		}

		// Origen existente: parser de stacktrace
		if (consola.verificacion_de_stacktrace != null && consola.verificacion_de_stacktrace.sm_config != null) {
			BiMap<String, Integer, Boolean> configs = consola.verificacion_de_stacktrace.sm_config;

			for (BiMap.DoubleKey<String, Integer> clave : configs.keySet()) {
				String nombreArchivo = clave.key0;
				int linea = clave.key1;
				boolean esFatal = configs.get(nombreArchivo, linea);

				CrashDetectorLogger.log("JSON in SM Problematico " + nombreArchivo);

				sm_config_con_linea.put(nombreArchivo, linea);
				sm_config_es_fatal.put(nombreArchivo, esFatal);
				enlacesPorConfig.put(nombreArchivo, consola.agregarErrorALectador(linea, this));
				this.activado = true;
			}
		}

		String cont = consola.contenido_verificar;
		if (cont == null || cont.isEmpty()) {
			return;
		}

		if (cont.contains(TEXTO_RESOURCE) && cont.contains(TEXTO_INVALID_OR_UNREADABLE)) {
			posibleErrorMixinPorLinea = true;
		}

		// activado = !sm_config_con_linea.isEmpty();
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleErrorMixinPorLinea;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int i) {
		if (linea == null || linea.isEmpty()) {
			return;
		}

		if (!lineaContieneMixinConfigProblematico(linea)) {
			return;
		}

		posibleErrorMixinPorLinea = true;

		int ini = linea.indexOf(TEXTO_RESOURCE) + TEXTO_RESOURCE.length();
		int fin = linea.indexOf("'", ini);

		if (fin > ini) {
			String nombre = linea.substring(ini, fin).trim();

			// Solo mixins *.json
			if (nombre.contains(TEXTO_MIXINS) && nombre.endsWith(TEXTO_JSON)
					&& !sm_config_con_linea.containsKey(nombre)) {
				sm_config_con_linea.put(nombre, i);
				sm_config_es_fatal.put(nombre, true);

				if (consola != null) {
					enlacesPorConfig.put(nombre, consola.agregarErrorALectador(i, this));
				}

				activado = true;
			}
		}
	}

	private boolean lineaContieneMixinConfigProblematico(String linea) {
		return linea.contains(TEXTO_RESOURCE) && linea.contains(TEXTO_INVALID_OR_UNREADABLE);
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
		CrashDetectorLogger.log("Agregando Mensaje de SM Problematicos");
		if (sm_config_con_linea.isEmpty()) {
			CrashDetectorLogger.log("sm_config_con_linea no tiene nada");
			return "";
		}

		StringBuilder html = new StringBuilder();
		html.append("<span style='color: #").append(Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas())
				.append("; font-weight: bold;'>").append(MonitorDePID.idioma.config_spongemixin_problematico())
				.append("</span>").append(Verificaciones.nl_html).append("<ul>");

		List<String> listItems = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : sm_config_con_linea.entrySet()) {
			String sm = entry.getKey();

			CrashDetectorLogger.log(" SM en mensaje " + sm);
			boolean isFatal = sm_config_es_fatal.getOrDefault(sm, false);

			String jars_de_sm_string = "";
			List<String> jars_de_sm = Buscador.obtenerUbicaciones(Buscador.buscarModsConTermino(sm));

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
			List<String> jars = Buscador.obtenerUbicaciones(Buscador.buscarModsConTermino(sm));

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