package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detecta errores IncompatibleClassChangeError causados por intentar heredar de
 * una clase marcada como 'final' en versiones recientes de Minecraft o mods.
 * 
 * Ejemplo: java.lang.IncompatibleClassChangeError: class A cannot inherit from
 * final class B
 */
public class ErrorClaseFinalExtendida implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	// Patrón reutilizable para extraer la clase hija y la clase padre final.
	private static final Pattern PATRON_CLASE_FINAL = Pattern.compile(
			"java\\.lang\\.IncompatibleClassChangeError: class ([^ ]+) cannot inherit from final class ([^\\s]+)");

	/**
	 * Bandera ligera para saber si el log contiene indicios de este error. Se usa
	 * como filtro rápido antes del análisis línea a línea.
	 */
	private boolean posibleErrorClaseFinal = false;

	@Override
	public void verificar(Consola consola) {
		// Trabajo global mínimo: solo comprobamos si el texto base del error aparece
		// en algún punto del log. Si no, la verificación por línea se saltará.
		String contenido = consola.contenido_verificar;
		if (contenido == null) {
			posibleErrorClaseFinal = false;
			return;
		}

		posibleErrorClaseFinal = contenido.contains("IncompatibleClassChangeError: class")
				&& contenido.contains("cannot inherit from final class");
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó o sabemos que el log no contiene este tipo de error,
		// no hacemos trabajo adicional.
		if (this.activado || !posibleErrorClaseFinal || linea == null) {
			return;
		}

		Matcher m = PATRON_CLASE_FINAL.matcher(linea);
		if (m.find()) {
			String claseHija = m.group(1);
			String clasePadreFinal = m.group(2);

			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorClaseFinalExtendida(claseHija, clasePadreFinal) + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// Para evitar falsos positivos, solo marcamos el trazo si:
		// - El verificador ya se activó
		// - El trazo no es nulo
		// - El texto del trazo contiene la forma básica del error
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		String t = trazo.trace;
		if (!(t.contains("IncompatibleClassChangeError: class") && t.contains("cannot inherit from final class"))) {
			return false;
		}
		// Comprobación más precisa usando el mismo patrón que en la verificación.
		return PATRON_CLASE_FINAL.matcher(t).find();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorClaseFinalExtendida();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 820.0f; // Alta: impide la carga del mod
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionErrorClaseFinalExtendida())
				.construir();
	}

	@Override
	public String id() {
		return "clase_final_extendida";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorClaseFinalExtendida();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
