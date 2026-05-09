package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores generales de ClassCastException en logs de Minecraft.
 *
 * <p>
 * Estos errores indican que una clase fue tratada como si fuera otra clase o
 * interfaz incompatible. Eso puede pasar por:
 *
 * <ul>
 * <li>Mods incompatibles entre sí.</li>
 * <li>Mixins o transformers que alteran clases de forma no compatible.</li>
 * <li>Otro mod presente en el stacktrace que provoca el "miscast".</li>
 * </ul>
 *
 * <p>
 * Importante: un ClassCastException no siempre es fatal, pero con bastante
 * frecuencia sí termina causando el fallo principal.
 */
public class ClassCastExceptionGeneral implements Verificaciones {

	// Indica si el log contiene indicios globales de un ClassCastException
	private boolean posibleError = false;

	// Indica si esta verificación ya quedó activada
	private boolean activado = false;

	// Guarda la línea principal del ClassCastException
	private String lineaClassCast = "";

	// Enlace a la línea más representativa del error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Limpiar estado porque la misma instancia puede analizar más de un log
		posibleError = false;
		activado = false;
		lineaClassCast = "";
		enlace = "";

		// Detección global ligera para evitar trabajo innecesario por línea
		if (consola.contenido_verificar.contains("java.lang.ClassCastException:")
				&& consola.contenido_verificar.contains(" cannot be cast to ")) {
			posibleError = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales o si ya fue activado
		if (!posibleError || activado) {
			return;
		}

		// Confirmación precisa por línea
		if (linea.contains("java.lang.ClassCastException:") && linea.contains(" cannot be cast to ")) {

			// Ignorar caso conocido:
			// java.lang.ClassCastException: class java.lang.Integer cannot be cast to class
			// java.util.List
			// at ...oneauras-cart-optimizer...
			if (linea.contains("class java.lang.Integer cannot be cast to class java.util.List")) {
				String[] lineas = consola.contenido_verificar.split("\\R", -1);

				// num debe ser el número de línea actual. Si num empieza en 1, la siguiente
				// línea está en lineas[num]. Si num empieza en 0, ajusta esto a num + 1.
				if (num >= 0 && num < lineas.length && lineas[num].contains("oneauras-cart-optimizer")) {
					return;
				}
			}

			this.lineaClassCast = linea.trim();
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ClassCastExceptionGeneral();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1380;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeClassCastExceptionGeneral(lineaClassCast) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreClassCastExceptionGeneral();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "class_cast_exception_general";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}
}