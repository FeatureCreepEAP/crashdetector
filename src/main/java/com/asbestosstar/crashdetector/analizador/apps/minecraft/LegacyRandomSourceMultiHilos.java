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

public class LegacyRandomSourceMultiHilos implements VerificacionRapida {

	boolean activado = false;

	private static final String LEGACY_RANDOM_SOURCE_MULTI_HILOS = "net.minecraft.ReportedException: Accessing LegacyRandomSource from multiple threads";

	@Override
	public String[] patronesRapidos() {
		return new String[] { LEGACY_RANDOM_SOURCE_MULTI_HILOS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(LEGACY_RANDOM_SOURCE_MULTI_HILOS)) {
			activado = true;
		}
	}

	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		if (consola.contenido_verificar.contains(LEGACY_RANDOM_SOURCE_MULTI_HILOS)) {
			activado = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return false;
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new LegacyRandomSourceMultiHilos();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

	@Override
	public float prioridad() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public String mensaje() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.mensajeLegacyRandomSourceMultiHilos();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombreLegacyRandomSourceMultiHilos();
	}

	@Override
	public QuickFix solucion() {
		// TODO Auto-generated method stub
		return new Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod("Unsafe World Random Access Detector"))
				.construir();

	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "legacy_random_source_multihilo";
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