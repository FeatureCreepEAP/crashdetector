package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores relacionados con el teselado de bloques en Minecraft sin usar
 * regex. Usa verificación global ligera y verificación por línea.
 */
public class BloqueTeselado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_TESSELADO = "Tesselating block in world";

	/**
	 * Verificación global ligera: revisa si el log completo contiene indicios del
	 * error.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		if (contieneIgnoreCase(consola.contenido_verificar, TEXTO_TESSELADO)) {
			activado = true;
			mensaje = MonitorDePID.idioma.errorDeBloqueTeselado();
		}
	}

	/**
	 * Verificación por línea: agrega enlace a la línea exacta donde ocurre el
	 * error.
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (contieneIgnoreCase(linea, TEXTO_TESSELADO)) {
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			mensaje += " " + enlace;
		}
	}

	private boolean contieneIgnoreCase(String texto, String buscar) {
		if (texto == null || buscar == null || buscar.length() > texto.length()) {
			return false;
		}

		int limite = texto.length() - buscar.length();
		for (int i = 0; i <= limite; i++) {
			if (texto.regionMatches(true, i, buscar, 0, buscar.length())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Verificaciones nueva() {
		return new BloqueTeselado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_bloque_teselado();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "bloqueteselado";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		return contieneIgnoreCase(trazo.trace, TEXTO_TESSELADO);
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

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}