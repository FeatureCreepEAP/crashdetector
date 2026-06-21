package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class OptifineObsoleta implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	private boolean errorOptifine = false;
	private boolean uriInvalida = false;
	private boolean incompatibilidad = false;
	private boolean servicioFallido = false;

	private static final String ERROR_OPTIFINE = "Error loading OptiFine ZIP file";
	private static final String URI_INVALIDA = "URI is not hierarchical";
	private static final String INCOMPATIBILIDAD = "cpw.mods.modlauncher.api.IncompatibleEnvironmentException";
	private static final String SERVICIO_FALLIDO = "Service failed to load OptiFine";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ERROR_OPTIFINE, URI_INVALIDA, INCOMPATIBILIDAD, SERVICIO_FALLIDO };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		actualizarIndicios(evento.linea);
		intentarActivar();
	}

	private void actualizarIndicios(String texto) {
		if (texto.contains(ERROR_OPTIFINE)) {
			errorOptifine = true;
		}

		if (texto.contains(URI_INVALIDA)) {
			uriInvalida = true;
		}

		if (texto.contains(INCOMPATIBILIDAD)) {
			incompatibilidad = true;
		}

		if (texto.contains(SERVICIO_FALLIDO)) {
			servicioFallido = true;
		}
	}

	private void intentarActivar() {
		if (activado) {
			return;
		}

		if (errorOptifine && uriInvalida && incompatibilidad && servicioFallido) {
			this.mensaje = MonitorDePID.idioma.optifineObsoleta() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new OptifineObsoleta();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_optifine_obsoleta();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "optifine_obsoleta";
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

}