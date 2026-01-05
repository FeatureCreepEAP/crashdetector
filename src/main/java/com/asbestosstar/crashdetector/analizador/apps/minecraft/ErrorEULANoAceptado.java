package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta que el servidor no puede iniciarse porque el usuario no ha aceptado
 * el EULA de Minecraft, lo cual requiere cambiar 'eula=false' a 'eula=true' en
 * eula.txt.
 */
public class ErrorEULANoAceptado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {
		// Se usa el método verificar(Consola, String, int) en lugar de este.
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el mensaje característico que indica que el usuario necesita aceptar
	 * el EULA para poder ejecutar el servidor.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el mensaje de EULA no aceptado
		if (linea.contains("You need to agree to the EULA in order to run the server") && linea.contains("eula.txt")) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al EULA no aceptado
			mensaje = MonitorDePID.idioma.errorEULANoAceptado() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorEULANoAceptado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f; // Prioridad máxima: es un error de configuración básico que detiene todo
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorEULANoAceptado();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorEULANoAceptado())
				.construir();
	}

	@Override
	public String id() {
		return "error_eula_no_aceptado";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene el mensaje de EULA no aceptado.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("You need to agree to the EULA in order to run the server") && t.contains("eula.txt");
	}
	
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"+this.getClass().getSimpleName()+".java";
	}
	
	@Override
	public boolean recomendadoParaCorperata() {
		return true;//Si la paquete para servidores no tiene el archivo de eula.txt
	}
	
	
	
}