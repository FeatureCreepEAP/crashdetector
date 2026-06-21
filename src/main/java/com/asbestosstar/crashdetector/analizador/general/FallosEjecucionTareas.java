package com.asbestosstar.crashdetector.analizador.general;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FallosEjecucionTareas implements Verificaciones {

	// Mapa para almacenar clases con problemas y sus números de línea
	private final Map<String, String> clasesConProblema = new HashMap<>();
	// Conjunto para almacenar los mensajes HTML finales
	private final Set<String> mensajesHtml = new HashSet<>();
	// Bandera para indicar si se detectó algún problema
	private boolean activado = false;

	private static final String FAILED_TO_EXECUTE_TASK = "Failed to execute task";
	private static final String CLASS_EXTENSION = ".class";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILED_TO_EXECUTE_TASK, CLASS_EXTENSION };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Verificación rápida por línea para mejor rendimiento
		// Buscamos el patrón de error de ejecución de tarea
		if (lineaContieneFalloEjecucion(linea)) {
			String clase = extraerNombreClase(linea);
			if (!clase.isEmpty()) {
				// Almacenamos la clase y su número de línea
				clasesConProblema.put(clase, consola.agregarErrorALectador(numero_de_linea, this));
				activado = true;
			}
		}
	}

	private boolean lineaContieneFalloEjecucion(String linea) {
		return linea.contains(FAILED_TO_EXECUTE_TASK) && linea.contains(CLASS_EXTENSION);
	}

	/**
	 * Extrae el nombre de la clase de la línea de error de forma dinámica
	 * 
	 * @param linea línea de texto a analizar
	 * @return nombre de la clase si se encuentra, cadena vacía en caso contrario
	 */
	private String extraerNombreClase(String linea) {
		if (linea == null)
			return "";

		try {
			// Buscamos la parte que contiene el nombre de la clase
			int inicio = linea.indexOf(FAILED_TO_EXECUTE_TASK);
			if (inicio != -1) {
				// Tomamos el texto después de "Failed to execute task"
				int inicioResto = inicio + FAILED_TO_EXECUTE_TASK.length();

				while (inicioResto < linea.length() && Character.isWhitespace(linea.charAt(inicioResto))) {
					inicioResto++;
				}

				int finResto = inicioResto;
				while (finResto < linea.length() && !Character.isWhitespace(linea.charAt(finResto))) {
					finResto++;
				}

				if (finResto > inicioResto) {
					String clasePosible = linea.substring(inicioResto, finResto);

					// Verificamos si contiene ".class" para confirmar que es una clase
					int indiceClass = clasePosible.indexOf(CLASS_EXTENSION);
					if (indiceClass >= 0) {
						// Extraemos solo el nombre de la clase hasta ".class"
						int indiceFin = indiceClass + CLASS_EXTENSION.length();
						String clase = clasePosible.substring(0, indiceFin);
						// Eliminamos comillas y otros caracteres de formato
						clase = limpiarClase(clase);
						return clase;
					}
				}
			}
		} catch (Exception e) {
			// En caso de error, no devolvemos nada
		}
		return "";
	}

	private String limpiarClase(String clase) {
		if (clase == null)
			return "";

		int inicio = 0;
		int fin = clase.length();

		while (inicio < fin && (Character.isWhitespace(clase.charAt(inicio)) || clase.charAt(inicio) == '"'
				|| clase.charAt(inicio) == '\'')) {
			inicio++;
		}

		while (fin > inicio && (Character.isWhitespace(clase.charAt(fin - 1)) || clase.charAt(fin - 1) == '"'
				|| clase.charAt(fin - 1) == '\'')) {
			fin--;
		}

		return clase.substring(inicio, fin);
	}

	@Override
	public String mensaje() {
		if (mensajesHtml.isEmpty() && !clasesConProblema.isEmpty()) {
			procesarResultados();
		}

		if (mensajesHtml.isEmpty()) {
			return "";
		}

		CDStringBuilder html = new CDStringBuilder();
		html.append("<ul>");
		for (String mensaje : mensajesHtml) {
			html.append("<li>").append(mensaje).append("</li>");
		}
		html.append("</ul>");
		return html.append(MonitorDePID.idioma.recomendacion_fallos_ejecucion()).toString();
	}

	private void procesarResultados() {
		// Cargamos el buscador de mods una sola vez
		Buscador.cargar();
		String colorError = Config.obtenerInstancia().obtenerColorError();

		// Procesamos cada clase con problema
		for (Map.Entry<String, String> entrada : clasesConProblema.entrySet()) {
			String clase = entrada.getKey();
			String enlace = entrada.getValue();

			// Buscamos mods relacionados con esta clase
			// Reemplazamos puntos por barras para el formato de búsqueda
			List<ArchivoDeMod> modsRelacionados = Buscador.buscarModsConTermino(clase.replace(".", "/"));

			// Construimos el mensaje de error con el formato "clase (mod1, mod2)"
			StringBuilder mensaje = new StringBuilder();
			mensaje.append(MonitorDePID.idioma.fallo_ejecucion_tarea_descripcion(clase));

			// Si encontramos mods relacionados, los añadimos al mensaje entre paréntesis
			if (!modsRelacionados.isEmpty()) {
				mensaje.append(" (");
				for (int i = 0; i < modsRelacionados.size(); i++) {
					mensaje.append(modsRelacionados.get(i).ubicacion_para_publicar());
					if (i < modsRelacionados.size() - 1) {
						mensaje.append(", ");
					}
				}
				mensaje.append(")");
			}

			// Obtenemos el enlace HTML para esta línea

			// Formateamos el mensaje final con color y enlace
			String mensajeHtml = "<span style='color:#" + colorError + "'>" + mensaje.toString() + "</span> " + enlace;

			// Añadimos el mensaje al conjunto de resultados
			mensajesHtml.add(mensajeHtml);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new FallosEjecucionTareas();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_fallos_ejecucion_tareas();
	}

	@Override
	public QuickFix solucion() {
		// Creamos el objeto QuickFix con el nombre del analizador
		QuickFix.Builder constructor = new QuickFix.Builder(nombre());

		// Añadimos una etiqueta explicativa general
		constructor.agregarEtiqueta(MonitorDePID.idioma.recomendacion_fallos_ejecucion());

		// Si hay clases con problemas, mostramos información detallada
		if (activado && !clasesConProblema.isEmpty()) {
			// Procesamos cada clase
			for (String clase : clasesConProblema.keySet()) {
				StringBuilder info = new StringBuilder();
				info.append(MonitorDePID.idioma.info_clase_problematica());
				info.append(" ");
				info.append(clase);

				// Buscamos mods relacionados para esta clase
				List<ArchivoDeMod> modsRelacionados = Buscador.buscarModsConTermino(clase.replace(".", "/"));
				if (!modsRelacionados.isEmpty()) {
					info.append(" (");
					for (int i = 0; i < modsRelacionados.size(); i++) {
						info.append(modsRelacionados.get(i).ubicacion_para_publicar());
						if (i < modsRelacionados.size() - 1) {
							info.append(", ");
						}
					}
					info.append(")");
				}

				// Añadimos la información como etiqueta
				constructor.agregarEtiqueta(info.toString());
			}
		} else {
			constructor.agregarEtiqueta(MonitorDePID.idioma.no_se_encontraron_clases_problema());
		}

		return constructor.construir();
	}

	@Override
	public String id() {
		return "fallos_ejecucion_tareas";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { "failed to execute task", "executionexception" };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}