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
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
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

	@Override
	public void verificar(Consola consola) {
		// No se realiza procesamiento aquí, ya que este método se llama antes del
		// análisis por línea
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Verificación rápida por línea para mejor rendimiento
		// Buscamos el patrón de error de ejecución de tarea
		if (linea.contains("Failed to execute task") && linea.contains(".class")) {
			String clase = extraerNombreClase(linea);
			if (!clase.isEmpty()) {
				// Almacenamos la clase y su número de línea
				clasesConProblema.put(clase, consola.agregarErrorALectador(numero_de_linea, this));
				activado = true;
			}
		}
	}

	/**
	 * Extrae el nombre de la clase de la línea de error de forma dinámica
	 * 
	 * @param linea línea de texto a analizar
	 * @return nombre de la clase si se encuentra, cadena vacía en caso contrario
	 */
	private String extraerNombreClase(String linea) {
		try {
			// Buscamos la parte que contiene el nombre de la clase
			int inicio = linea.indexOf("Failed to execute task");
			if (inicio != -1) {
				// Tomamos el texto después de "Failed to execute task"
				String resto = linea.substring(inicio + "Failed to execute task".length()).trim();
				// Dividimos por espacios para obtener las partes individuales
				String[] partes = resto.split("\\s+");

				if (partes.length > 0) {
					String clasePosible = partes[0];
					// Verificamos si contiene ".class" para confirmar que es una clase
					if (clasePosible.contains(".class")) {
						// Extraemos solo el nombre de la clase hasta ".class"
						int indiceFin = clasePosible.indexOf(".class") + ".class".length();
						String clase = clasePosible.substring(0, indiceFin);
						// Eliminamos comillas y otros caracteres de formato
						clase = clase.replace("\"", "").replace("'", "").trim();
						return clase;
					}
				}
			}
		} catch (Exception e) {
			// En caso de error, no devolvemos nada
		}
		return "";
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		// Verificamos si el trazo contiene información relevante
		if (trazo == null || trazo.trace == null || !activado) {
			return false;
		}

		String contenido = trazo.trace.toLowerCase();
		return contenido.contains("failed to execute task") && contenido.contains("class")
				&& contenido.contains("executionexception");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}