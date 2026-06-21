package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta si el plugin AuthMe causó el cierre del servidor.Gracias a
 * Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaCierreAuthMe implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	private static final String MENSAJE_AUTHME = "[AuthMe] THE SERVER IS GOING TO SHUT DOWN AS DEFINED IN THE CONFIGURATION!";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MENSAJE_AUTHME };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		if (evento.linea.contains(MENSAJE_AUTHME)) {
			activar();
		}
	}

	private void activar() {
		this.mensaje = MonitorDePID.idioma.mensajeCierreAuthMe() + Verificaciones.nl_html;
		this.activado = true;
	}

	/**
	 * Crea una nueva instancia de este verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaCierreAuthMe();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema (alta).
	 */
	@Override
	public float prioridad() {
		return 1000.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaCierreAuthMe();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionCierreAuthMe())
				.agregarEtiqueta(MonitorDePID.idioma.solucionConfigurarPluginAuthMe())
				.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferenteAuthMe())
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPluginAuthMe()).construir();
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "cierre_authme";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}