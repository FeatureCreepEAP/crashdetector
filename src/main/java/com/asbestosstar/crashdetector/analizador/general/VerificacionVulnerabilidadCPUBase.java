package com.asbestosstar.crashdetector.analizador.general;

import java.awt.Desktop;
import java.net.URI;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.ResultadoVulnerabilidadCPU;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Implementación común para las verificaciones de Meltdown y Spectre.
 *
 * La verificación se activa al encontrar "Processor Name:" en el inventario del
 * registro, igual que RaptorLakeInestable. El detector compara ese nombre con
 * el CPU local antes de consultar el estado del kernel; de esa manera no
 * atribuye al archivo analizado los parches de otra computadora.
 */
abstract class VerificacionVulnerabilidadCPUBase implements Verificaciones {

	private static final String TEXTO_PROCESSOR_NAME = "Processor Name:";

	private boolean activado;
	private String mensaje = "";
	private String enlace = "";
	private ResultadoVulnerabilidadCPU ultimoResultado;

	protected abstract ConfigBoolean obtenerConfig();

	protected abstract ResultadoVulnerabilidadCPU evaluar(String cpu);

	protected abstract void marcarProblemaGlobal();

	protected abstract String nombreVisible();

	protected abstract String idInterno();

	protected abstract float prioridadInterna();

	protected abstract String textoDesactivar();

	@Override
	public final String[] patronesRapidos() {
		return new String[] { TEXTO_PROCESSOR_NAME };
	}

	@Override
	public final void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado || obtenerConfig().obtener()) {
			return;
		}
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public final void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || obtenerConfig().obtener()) {
			return;
		}

		String cpu = obtenerCPUDeLinea(linea);
		if (cpu == null || cpu.isEmpty()) {
			return;
		}

		ResultadoVulnerabilidadCPU resultado;
		try {
			resultado = evaluar(cpu);
		} catch (Throwable t) {
			CrashDetectorLogger.log(MonitorDePID.idioma.errorComprobacionSeguridadCPU(nombreVisible(), t.getMessage()));
			CrashDetectorLogger.logException(t);
			return;
		}

		if (resultado == null) {
			return;
		}

		CrashDetectorLogger.log(resultado.construirResumenTexto());

		// Si está mitigado o el CPU no está afectado, se deja constancia en el log,
		// pero no se presenta una advertencia al usuario.
		if (!resultado.requiereAdvertencia()) {
			return;
		}

		this.ultimoResultado = resultado;
		this.enlace = consola == null ? "" : consola.agregarErrorALectador(numeroDeLinea, this);
		this.mensaje = resultado.construirMensajeHtml();
		if (!enlace.isEmpty()) {
			this.mensaje += " " + enlace;
		}
		this.activado = true;
		marcarProblemaGlobal();
	}

	/**
	 * Extrae el texto posterior a "Processor Name:" sin usar una expresión regular.
	 */
	private String obtenerCPUDeLinea(String linea) {
		if (linea == null) {
			return null;
		}
		int indice = linea.indexOf(TEXTO_PROCESSOR_NAME);
		if (indice < 0) {
			return null;
		}
		int inicio = indice + TEXTO_PROCESSOR_NAME.length();
		while (inicio < linea.length() && Character.isWhitespace(linea.charAt(inicio))) {
			inicio++;
		}
		return inicio < linea.length() ? linea.substring(inicio).trim() : null;
	}

	@Override
	public final boolean activado() {
		return activado;
	}

	@Override
	public final float prioridad() {
		return prioridadInterna();
	}

	@Override
	public final String mensaje() {
		return mensaje;
	}

	@Override
	public final String nombre() {
		return nombreVisible();
	}

	@Override
	public final QuickFix solucion() {
		QuickFix.Builder builder = new QuickFix.Builder(nombreVisible()).agregarBoton(textoDesactivar(),
				retener -> obtenerConfig().escribir(true), true);

		if (ultimoResultado != null && ultimoResultado.getEnlaceOficial() != null
				&& !ultimoResultado.getEnlaceOficial().isEmpty()) {
			builder.agregarBoton(MonitorDePID.idioma.abrirDocumentacionOficialSeguridadCPU(), retener -> {
				abrirEnNavegador(ultimoResultado.getEnlaceOficial());
			}, false);
		}

		return builder.construir();
	}

	private void abrirEnNavegador(String url) {
		try {
			if (url == null || url.isEmpty() || !Desktop.isDesktopSupported()) {
				return;
			}
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				desktop.browse(new URI(url));
			}
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	@Override
	public final String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public final String id() {
		return idInterno();
	}

	@Override
	public final Documento docs() {
		return Documento.NINGUN;
	}
}
