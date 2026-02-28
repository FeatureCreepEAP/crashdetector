package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el uso de OptiFine en un servidor dedicado, lo cual provoca múltiples
 * errores porque OptiFine intenta cargar clases del cliente en un entorno de
 * servidor.
 */
public class ErrorOptiFineServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;

	/**
	 * Método de compatibilidad — busca si OptiFine está presente en el contenido
	 * completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si OptiFine está presente en el contenido del registro
		if (consola.contenido_verificar != null) {
			encontradoOptiFine = consola.contenido_verificar.toLowerCase().contains("optifine.patcher.applypatch");
		}
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde OptiFine falla en un
	 * servidor dedicado porque intenta cargar clases del cliente.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de carga de clase para servidor
		// dedicado de OptiFine
		if (linea.contains("Base resource not found") && encontradoOptiFine) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al uso incorrecto de OptiFine
			mensaje = MonitorDePID.idioma.errorOptiFineServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorOptiFineServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f; // Prioridad máxima: rompe la carga del servidor
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorOptiFineServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorOptiFineServidor())
				.construir();
	}

	@Override
	public String id() {
		return "error_optifine_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre OptiFine y servidor dedicado.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("java.io.IOException: Base resource not found:")
				&& t.contains("LAYER SERVICE/optifine/optifine.Patcher.applyPatch")
				&& t.toLowerCase().contains("optifine");
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