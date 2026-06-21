package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de GeckoLib donde un modelo .geo.json no puede ser
 * encontrado.
 *
 * Ejemplo: cobbledgacha:geo/gacha_machine_13.geo.json: Unable to find model
 *
 * Generalmente causado por: - Archivo faltante - Ruta mal configurada - Error
 * en resources del mod
 */
public class ModeloGeckoLibNoEncontrado implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";
	private String modelo = "";

	private static final String UNABLE_TO_FIND_MODEL = "Unable to find model";
	private static final String GEO_JSON = ".geo.json";
	private static final String SEPARADOR_MODELO = ": Unable to find model";

	@Override
	public String[] patronesRapidos() {
		return new String[] { UNABLE_TO_FIND_MODEL, GEO_JSON };
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

		if (lineaContieneModeloGeckoNoEncontrado(linea)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Intentar extraer el nombre del modelo antes de ": Unable to find model"
			int separador = linea.indexOf(SEPARADOR_MODELO);
			if (separador > 0) {
				modelo = linea.substring(0, separador).trim();
			}

			activado = true;
		}
	}

	private boolean lineaContieneModeloGeckoNoEncontrado(String linea) {
		return linea.contains(UNABLE_TO_FIND_MODEL) && linea.contains(GEO_JSON);
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ModeloGeckoLibNoEncontrado();
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
		return MonitorDePID.idioma.mensajeModeloGeckoNoEncontrado(modelo) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreModeloGeckoNoEncontrado();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "modelo_geckolib_no_encontrado";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}