package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de GeckoLib donde un archivo de animación .json no puede ser
 * encontrado (Unable to find).
 *
 * Ejemplo: software.bernie.geckolib.GeckoLibException:
 * afomni:animations/echoecho.animation.json: Unable to find animation file.
 *
 * Generalmente causado por: - Archivo de animación faltante en el sistema de
 * archivos del mod. - Ruta mal configurada en el registro del modelo/animación.
 * - Error de minusculas/mayúsculas en el nombre del archivo (Case sensitivity).
 */
public class AnimacionGeckoLibInExiste implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";
	private String archivo = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para activar el análisis línea por línea
		// Buscamos la nueva firma del error: "Unable to find animation file"
		if (log.contains("GeckoLibException") && log.contains("Unable to find animation file")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		// Buscamos la línea específica que contiene la excepción y el error de búsqueda
		if (linea.contains("GeckoLibException") && linea.contains("Unable to find animation file")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Intentar extraer el nombre del archivo antes de ": Unable to find animation
			// file"
			int separador = linea.indexOf(": Unable to find animation file");
			if (separador > 0) {
				String temp = linea.substring(0, separador).trim();

				// La línea suele ser: "software.bernie.geckolib.GeckoLibException:
				// modid:ruta/file.json"
				// Debemos separar la excepción de la ruta.
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
		return new AnimacionGeckoLibInExiste();
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

		return MonitorDePID.idioma.mensajeAnimacionGeckoInexiste(archivo) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAnimacionGeckoInexiste();
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
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}