package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class IrisSombrasTerreno implements VerificacionRapida {

	private boolean posibleErrorIris = false;
	private boolean activado = false;
	private String enlace = "";

	private static final String IRIS_RENDER_TERRAIN_SHADOWS = "$iris$renderTerrainShadows";

	@Override
	public String[] patronesRapidos() {
		return new String[] { IRIS_RENDER_TERRAIN_SHADOWS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(IRIS_RENDER_TERRAIN_SHADOWS)) {
			posibleErrorIris = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Detección global: el log contiene el método de Iris
		if (consola.contenido_verificar.contains(IRIS_RENDER_TERRAIN_SHADOWS)) {
			posibleErrorIris = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleErrorIris && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleErrorIris || activado || linea == null) {
			return;
		}

		if (linea.contains(IRIS_RENDER_TERRAIN_SHADOWS)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new IrisSombrasTerreno();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeIrisSombrasTerreno() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreIrisSombrasTerreno();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "iris_sombras_terreno";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}