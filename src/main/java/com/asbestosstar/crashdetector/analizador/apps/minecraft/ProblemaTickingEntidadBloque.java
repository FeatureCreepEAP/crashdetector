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
	private String enlaceHtml = "";

	/**
	 * Verifica si el log contiene un problema de ticking en una entidad de bloque.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		String[] lineas = contenido.split("\n");

		boolean encontrado = false;

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i].trim();

			// Busca la línea que indica un problema de ticking en una entidad de bloque
			if (linea.equals("Description: Ticking block entity")) {
				encontrado = true;
				enlaceHtml = consola.agregarErrorALectador(i, this); // Registrar la línea del error

				// Busca el nombre de la entidad en las siguientes líneas
				for (int j = i + 1; j < Math.min(i + 20, lineas.length); j++) {
					String lineaActual = lineas[j].trim();

					// Extrae el nombre de la entidad
					if (lineaActual.startsWith("Name: ")) {
						this.nombreEntidad = lineaActual.substring("Name: ".length()).trim();
					}

					// Extrae el tipo de la entidad
					if (lineaActual.startsWith("Block: ") || lineaActual.contains("Block entity being ticked")) {
						// Formato: "Block: modid:tipo"
						int indiceMod = lineaActual.indexOf(":");
						if (indiceMod > 0 && lineaActual.contains(":")) {
							String[] partes = lineaActual.split(":");
							if (partes.length >= 2) {
								this.tipoEntidad = partes[0].trim() + ":" + partes[1].trim();
							}
						}
					}

					// Extrae las coordenadas del bloque
					if (lineaActual.startsWith("Block location: World: (")) {
						// Formato: "Block location: World: (x,y,z)"
						int inicio = lineaActual.indexOf("(") + 1;
						int fin = lineaActual.indexOf(")");
						if (inicio > 0 && fin > inicio) {
							String coordsTexto = lineaActual.substring(inicio, fin);
							String[] coords = coordsTexto.split(",");
							if (coords.length == 3) {
								try {
									this.coordenadas[0] = Integer.parseInt(coords[0].trim());
									this.coordenadas[1] = Integer.parseInt(coords[1].trim());
									this.coordenadas[2] = Integer.parseInt(coords[2].trim());
								} catch (NumberFormatException e) {
									// Ignora errores de formato
								}
							}
						}
					}
				}

				if (!nombreEntidad.isEmpty() || !tipoEntidad.isEmpty() || coordenadas[0] != 0 || coordenadas[1] != 0
						|| coordenadas[2] != 0) {
					this.mensaje = MonitorDePID.idioma.mensajeTickingEntidadBloque(nombreEntidad, tipoEntidad,
							coordenadas) + " " + enlaceHtml;
					activado = true;
				}
				break;
			}
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

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}