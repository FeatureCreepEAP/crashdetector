package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta errores causados por entidades de bloques al ser
 * actualizadas. Gracias a Aternos por que esta es una implementacion de su
 * codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaTickingEntidadBloque implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreEntidad = "";
	private String tipoEntidad = "";
	private int[] coordenadas = new int[3]; // [x, y, z]
	private boolean coordenadasValidas = false; // indica si las coordenadas fueron parseadas correctamente
	private String enlaceHtml = "";

	// Estado para el análisis por línea
	private boolean hayTickingBlockEntityEnLog = false;
	private boolean bloqueActivo = false;
	private int lineaInicioBloque = -1;

	/**
	 * Verifica si el log contiene un problema de ticking en una entidad de bloque.
	 * <p>
	 * En esta versión solo se hace una comprobación global rápida para detectar si
	 * existe el texto "Description: Ticking block entity". El análisis detallado se
	 * realiza línea a línea en {@link #verificar(Consola, String, int)}.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		hayTickingBlockEntityEnLog = contenido.contains("Description: Ticking block entity");
	}

	/**
	 * Análisis por línea del log.
	 * <p>
	 * - Cuando se detecta la línea "Description: Ticking block entity" se marca el
	 * inicio de un bloque y se registra el enlace HTML. - Durante las siguientes
	 * ~20 líneas se buscan:
	 * <ul>
	 * <li>Nombre de la entidad (línea que empieza por "Name: ")</li>
	 * <li>Tipo de entidad (línea que empieza por "Block: " o contiene "Block entity
	 * being ticked")</li>
	 * <li>Coordenadas ("Block location: World: (x,y,z)")</li>
	 * </ul>
	 * Cada vez que se obtiene información suficiente se actualiza el mensaje final.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si globalmente no hay indicios de este tipo de crash, no hacemos nada
		if (!hayTickingBlockEntityEnLog || linea == null) {
			return;
		}

		String l = linea.trim();

		// Inicio del bloque de "Ticking block entity"
		if (l.equals("Description: Ticking block entity")) {
			bloqueActivo = true;
			lineaInicioBloque = numero_de_linea;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this); // Registrar la línea del error
			// No activamos aún el verificador hasta tener más datos útiles
			return;
		}

		// Si no estamos dentro de un bloque relevante, ignoramos
		if (!bloqueActivo) {
			return;
		}

		// Limitar la búsqueda a unas pocas líneas después de la descripción
		int delta = numero_de_linea - lineaInicioBloque;
		if (delta <= 0 || delta > 20) {
			bloqueActivo = false;
			return;
		}

		// Extrae el nombre de la entidad
		if (l.startsWith("Name: ")) {
			this.nombreEntidad = l.substring("Name: ".length()).trim();
			actualizarMensajeSiCorresponde();
		}

		// Extrae el tipo de la entidad
		if (l.startsWith("Block: ") || l.contains("Block entity being ticked")) {
			// Formato típico: "Block: modid:tipo"
			int indiceMod = l.indexOf(":");
			if (indiceMod > 0 && l.contains(":")) {
				String[] partes = l.split(":");
				if (partes.length >= 2) {
					this.tipoEntidad = partes[0].trim() + ":" + partes[1].trim();
					actualizarMensajeSiCorresponde();
				}
			}
		}

		// Extrae las coordenadas del bloque
		if (l.startsWith("Block location: World: (")) {
			// Formato: "Block location: World: (x,y,z)"
			int inicio = l.indexOf("(") + 1;
			int fin = l.indexOf(")");
			if (inicio > 0 && fin > inicio) {
				String coordsTexto = l.substring(inicio, fin);
				String[] coords = coordsTexto.split(",");
				if (coords.length == 3) {
					try {
						this.coordenadas[0] = Integer.parseInt(coords[0].trim());
						this.coordenadas[1] = Integer.parseInt(coords[1].trim());
						this.coordenadas[2] = Integer.parseInt(coords[2].trim());
						this.coordenadasValidas = true;
						actualizarMensajeSiCorresponde();
					} catch (NumberFormatException e) {
						// Ignora errores de formato
					}
				}
			}
		}
	}

	/**
	 * Actualiza el mensaje final si ya tenemos al menos algún dato útil del bloque
	 * (nombre, tipo o coordenadas válidas).
	 */
	private void actualizarMensajeSiCorresponde() {
		if (enlaceHtml == null) {
			return;
		}

		// Reutilizamos la misma lógica básica que la implementación original:
		// si hay algún campo con información, construimos el mensaje.
		if (!nombreEntidad.isEmpty() || !tipoEntidad.isEmpty() || coordenadasValidas) {
			this.mensaje = MonitorDePID.idioma.mensajeTickingEntidadBloque(nombreEntidad, tipoEntidad, coordenadas)
					+ " " + enlaceHtml;
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaTickingEntidadBloque();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema (alta).
	 */
	@Override
	public float prioridad() {
		return 800.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaTickingEntidadBloque();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarEntidadBloque(nombreEntidad, coordenadas))
				.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "problema_ticking_entidad_bloque";
	}

	/**
	 * Indica si este verificador debe "ocupar" un trazo concreto del stack trace.
	 * <p>
	 * La intención es asociar únicamente los trazos que claramente pertenecen a un
	 * crash de "Ticking block entity". Para evitar falsos positivos:
	 * <ul>
	 * <li>Primero comprobamos que el verificador ya se ha activado.</li>
	 * <li>Buscamos la cadena genérica del problema ("Description: Ticking block
	 * entity").</li>
	 * <li>Si hay información más específica (tipo de entidad, nombre o
	 * coordenadas), intentamos utilizarla para afinar la coincidencia.</li>
	 * </ul>
	 * Es deliberadamente conservador: se prefiere un falso negativo antes que
	 * marcar un trazo que no pertenezca realmente a este error.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		// Comprobación genérica del tipo de crash
		if (t.contains("Description: Ticking block entity")) {
			return true;
		}

		// Si conocemos el tipo de entidad, muchos crash reports incluyen algo como:
		// "Block entity being ticked: <tipoEntidad>@..."
		if (!tipoEntidad.isEmpty() && t.contains("Block entity being ticked") && t.contains(tipoEntidad)) {
			return true;
		}

		// En algunos casos el nombre de la entidad aparece tal cual en el trazo
		if (!nombreEntidad.isEmpty() && t.contains(nombreEntidad)) {
			return true;
		}

		// Coincidencia con la línea de ubicación del bloque si las coordenadas son
		// válidas
		if (coordenadasValidas) {
			String coordsTexto = "World: (" + coordenadas[0] + "," + coordenadas[1] + "," + coordenadas[2] + ")";
			if (t.contains(coordsTexto)) {
				return true;
			}
		}

		return false;
	}

}
