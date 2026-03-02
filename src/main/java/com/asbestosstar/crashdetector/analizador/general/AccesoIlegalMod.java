package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores IllegalAccessError donde un mod intenta acceder a un método o
 * campo privado/protegido.
 *
 * Ejemplo: java.lang.IllegalAccessError: class X tried to access private method
 * Y
 */
public class AccesoIlegalMod implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	private String claseOrigen = "";
	private String miembroAccedido = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global ligero
		if (log.contains("java.lang.IllegalAccessError") && log.contains(" tried to access ")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("java.lang.IllegalAccessError") && linea.contains(" tried to access ")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Extraer clase que intentó acceder
			int inicioClase = linea.indexOf("class ");
			int finClase = linea.indexOf(" tried to access");

			if (inicioClase > -1 && finClase > inicioClase) {
				claseOrigen = linea.substring(inicioClase + 6, finClase).trim();
			}

			// Extraer miembro accedido (método/campo)
			int inicioMiembro = linea.indexOf("tried to access") + "tried to access".length();
			if (inicioMiembro > -1 && inicioMiembro < linea.length()) {
				miembroAccedido = linea.substring(inicioMiembro).trim();
			}

			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new AccesoIlegalMod();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f;
	}

	@Override
	public String mensaje() {

		return MonitorDePID.idioma.mensajeAccesoIlegalMod(claseOrigen, miembroAccedido) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAccesoIlegalMod();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "acceso_ilegal_mod";
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