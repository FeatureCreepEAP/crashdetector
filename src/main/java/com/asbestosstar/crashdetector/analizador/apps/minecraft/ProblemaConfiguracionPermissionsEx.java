package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta errores de configuración en PermissionsEx (PEX).Gracias a
 * Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaConfiguracionPermissionsEx implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";

	private static final String MENSAJE_ERROR = "Your configuration must be fixed before PEX will enable";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MENSAJE_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		if (evento.linea.contains(MENSAJE_ERROR)) {
			activar();
		}
	}

	/**
	 * Verifica si el log contiene el error de configuración de PermissionsEx.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || activado) {
			return;
		}

		if (consola.contenido_verificar.contains(MENSAJE_ERROR)) {
			activar();
		}
	}

	private void activar() {
		this.mensaje = MonitorDePID.idioma.mensajeConfiguracionPermissionsEx() + Verificaciones.nl_html;
		this.activado = true;
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return false;

	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaConfiguracionPermissionsEx();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema (baja).
	 */
	@Override
	public float prioridad() {
		return 300.0f;
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
		return MonitorDePID.idioma.nombreProblemaConfiguracionPermissionsEx();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionConfigurarPermissionsEx())
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarPluginPermissionsEx()).construir();
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "config_permissions_ex";
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