package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores donde Minecraft no puede localizar el directorio de assets.
 *
 * Ejemplo: Exception in thread "main"
 * joptsimple.OptionArgumentConversionException: Cannot parse argument
 * '...assets' of option assetsDir
 *
 * Este problema suele ocurrir cuando el launcher no instala correctamente los
 * assets del juego.
 */
public class ProblemaAssetsDirFaltante implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String OPTION_ARGUMENT_CONVERSION_EXCEPTION = "OptionArgumentConversionException";
	private static final String ASSETS_DIR = "assetsDir";

	@Override
	public String[] patronesRapidos() {
		return new String[] { OPTION_ARGUMENT_CONVERSION_EXCEPTION, ASSETS_DIR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (lineaContieneProblemaAssetsDir(linea)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneProblemaAssetsDir(String linea) {
		return linea.contains(OPTION_ARGUMENT_CONVERSION_EXCEPTION) && linea.contains(ASSETS_DIR);
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaAssetsDirFaltante();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		return MonitorDePID.idioma.problemaAssetsDirFaltante() + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaAssetsDirFaltante();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_assets_dir_faltante";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (trazo == null || trazo.trace == null)
			return false;

		return lineaContieneProblemaAssetsDir(trazo.trace);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}