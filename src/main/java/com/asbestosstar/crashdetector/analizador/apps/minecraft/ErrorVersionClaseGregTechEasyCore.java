package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de versión de clase incompatible causados por
 * GregTechEasyCore donde AbstractFurnaceBlockEntity ha sido compilado con una
 * versión más reciente de Java que la que está usando el servidor.
 */
public class ErrorVersionClaseGregTechEasyCore implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoGTECore = false;

	/**
	 * Método de compatibilidad — busca si GregTechEasyCore está presente en el
	 * contenido completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si GregTechEasyCore está presente en el contenido del registro
		if (consola.contenido_verificar != null) {
			encontradoGTECore = consola.contenido_verificar.toLowerCase().contains("gtecore");
		}
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde AbstractFurnaceBlockEntity
	 * ha sido compilado con una versión más reciente de Java.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de versión de clase incompatible de
		// GregTechEasyCore
		if (linea.contains("java.lang.UnsupportedClassVersionError")
				&& linea.contains("net/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity")
				&& linea.contains("has been compiled by a more recent version of the Java Runtime")
				&& encontradoGTECore) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de GregTechEasyCore
			mensaje = MonitorDePID.idioma.errorVersionClaseGregTechEasyCore() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorVersionClaseGregTechEasyCore();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f; // Prioridad máxima: rompe la carga del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorVersionClaseGregTechEasyCore();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorVersionClaseGregTechEasyCore()).construir();
	}

	@Override
	public String id() {
		return "error_version_clase_gtecore";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de versión de
	 * clase incompatible de GregTechEasyCore.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("java.lang.UnsupportedClassVersionError")
				&& t.contains("net/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity")
				&& t.contains("has been compiled by a more recent version of the Java Runtime")
				&& t.toLowerCase().contains("gtecore");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}