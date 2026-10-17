package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta el conflicto de inicialización en el que Distant Horizons intenta
 * usar la API de Chunky antes de que Chunky haya terminado de cargarse.
 *
 * La comprobación se hace sobre cada {@link VerificacionDeStackTrace.TraceInfo}
 * completo. Así no se combinan accidentalmente líneas de dos trazas distintas.
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaConflictoChunkyDistantHorizons" },
		nota = "Cubre la firma completa del trazo, descartes, identidad y registro")
public class ConflictoChunkyDistantHorizons implements Verificaciones {

	private static final String EXCEPCION = "IllegalStateException: Chunky is not loaded.";
	private static final String PROVIDER = "org.popcraft.chunky.ChunkyProvider.get";
	private static final String CHUNKY_ACCESSOR =
			"com.seibel.distanthorizons.forge.wrappers.modAccessor.ChunkyAccessor.bindOnGenerationProgressEvent";
	private static final String ABSTRACT_ACCESSOR =
			"com.seibel.distanthorizons.core.wrapperInterfaces.modAccessor.AbstractChunkyAccessor.tryRunFirstTimeSetup";

	private boolean excepcionEncontrada;
	private boolean providerEncontrado;
	private boolean accessorEncontrado;
	private boolean abstractAccessorEncontrado;
	private boolean activado;
	private String enlaceHtml = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { EXCEPCION, PROVIDER, CHUNKY_ACCESSOR, ABSTRACT_ACCESSOR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		registrarLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		registrarLinea(consola, linea, numeroDeLinea);
	}

	private void registrarLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (linea.contains(EXCEPCION)) {
			excepcionEncontrada = true;
		}
		if (linea.contains(PROVIDER)) {
			providerEncontrado = true;
		}
		if (linea.contains(CHUNKY_ACCESSOR)) {
			accessorEncontrado = true;
		}
		if (linea.contains(ABSTRACT_ACCESSOR)) {
			abstractAccessorEncontrado = true;
		}

		if (!excepcionEncontrada || !providerEncontrado || !accessorEncontrado || !abstractAccessorEncontrado) {
			return;
		}

		if (consola != null) {
			enlaceHtml = consola.agregarErrorALectador(numeroDeLinea, this);
		}
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoChunkyDistantHorizons();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return activado ? MonitorDePID.idioma.errorConflictoChunkyDistantHorizons() + Verificaciones.nl_html
				+ enlaceHtml : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoChunkyDistantHorizons();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(
				MonitorDePID.idioma.pasoConflictoChunkyDistantHorizons()).construir();
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { EXCEPCION, PROVIDER, CHUNKY_ACCESSOR, ABSTRACT_ACCESSOR };
	}

	@Override
	public String id() {
		return "conflicto_chunky_distant_horizons";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
