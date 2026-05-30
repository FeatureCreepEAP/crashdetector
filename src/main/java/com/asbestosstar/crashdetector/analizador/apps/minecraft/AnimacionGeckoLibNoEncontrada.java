package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de GeckoLib donde un archivo de animación .json no puede ser
 * cargado o encontrado.
 *
 * Ejemplo: software.bernie.geckolib.GeckoLibException:
 * limbus_weapons:animations/gaze_-_converted.json: Error loading animation file
 *
 * Generalmente causado por: - Archivo de animación faltante - Ruta mal
 * configurada en el código del mod - Error de sintaxis JSON dentro del archivo
 * de animación
 */
public class AnimacionGeckoLibNoEncontrada implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";
	private String archivo = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global ligero para activar el análisis línea por línea
		if (log.contains("GeckoLibException") && log.contains("Error loading animation file")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		// Buscamos la línea específica que contiene la excepción y el error de carga
		if (linea.contains("GeckoLibException") && linea.contains("Error loading animation file")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Intentar extraer el nombre del archivo antes de ": Error loading animation
			// file"
			int separador = linea.indexOf(": Error loading animation file");
			if (separador > 0) {
				String temp = linea.substring(0, separador).trim();

				// A veces la línea trae "software.bernie.geckolib.GeckoLibException: " al
				// inicio
				// La ruta del archivo suele ser lo último antes de los dos puntos.
				// Buscamos el último espacio para separar la basura de la ruta.
				int ultimoEspacio = temp.lastIndexOf(' ');
				if (ultimoEspacio > 0) {
					archivo = temp.substring(ultimoEspacio).trim();
				} else {
					archivo = temp;
				}
			}

			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new AnimacionGeckoLibNoEncontrada();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {

		return MonitorDePID.idioma.mensajeAnimacionGeckoNoEncontrada(archivo) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAnimacionGeckoNoEncontrada();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "animacion_geckolib_no_encontrada";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}