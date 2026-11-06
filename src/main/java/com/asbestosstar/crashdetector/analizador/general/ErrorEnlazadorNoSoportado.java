package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta errores genéricos donde la plataforma no soporta el enlazador nativo
 * de Java (Foreign Function & Memory API).
 *
 * El error típico es:
 * "java.lang.UnsupportedOperationException: Platform does not support native linker"
 *
 * Esto ocurre cuando LWJGL (u otra biblioteca) intenta usar la API FFM de Java
 * para cargar bibliotecas nativas, pero la plataforma no proporciona un
 * enlazador nativo. Es común en Solaris/SPARC, algunas configuraciones de ARM,
 * y otras plataformas poco comunes.
 *
 * La cadena de errores es:
 * BootstrapMethodError → UnsupportedOperationException: Platform does not support native linker
 * → Linker.nativeLinker() → org.lwjgl.system.ffm.BCCallDown
 *
 * Esta verificación es general y se activa independientemente de qué mod o
 * biblioteca provoque el error.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaSodiumYLWJGL"
 * }, nota = "Cubre deteccion de enlazador nativo no soportado y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaSodiumYLWJGL" },
		nota = "Cubre deteccion de enlazador nativo no soportado y falsos positivos")
public class ErrorEnlazadorNoSoportado implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String LINKER_NO_SOPORTADO = "Platform does not support native linker";

	private static final String LINKER_NATIVE = "Linker.nativeLinker";

	private static final String FFM_BCCALLDOWN = "BCCallDown";

	private static final String UNSUPPORTED_OPERATION = "UnsupportedOperationException";

	@Override
	public String[] patronesRapidos() {
		return new String[] { LINKER_NO_SOPORTADO, LINKER_NATIVE, FFM_BCCALLDOWN, UNSUPPORTED_OPERATION };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || consola == null || linea == null) {
			return;
		}

		if (esErrorEnlazadorNoSoportado(linea)) {
			activar(consola, numeroDeLinea);
		}
	}

	/**
	 * Comprueba si una línea contiene un error de enlazador no soportado.
	 */
	private boolean esErrorEnlazadorNoSoportado(String linea) {
		// Patrón principal: "Platform does not support native linker"
		if (linea.contains(LINKER_NO_SOPORTADO)) {
			return true;
		}

		// Patrón alternativo: UnsupportedOperationException con Linker.nativeLinker y BCCallDown
		if (linea.contains(UNSUPPORTED_OPERATION) && linea.contains(LINKER_NATIVE) && linea.contains(FFM_BCCALLDOWN)) {
			return true;
		}

		return false;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorEnlazadorNoSoportado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1550;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorEnlazadorNoSoportado("") + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorEnlazadorNoSoportado();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "error_enlazador_no_soportado";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
