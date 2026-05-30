package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
	private boolean analizarLineas = false;
	private String enlace = "";
	private String modelo = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global ligero
		if (log.contains("Unable to find model") && log.contains(".geo.json")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("Unable to find model") && linea.contains(".geo.json")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Intentar extraer el nombre del modelo antes de ": Unable to find model"
			int separador = linea.indexOf(": Unable to find model");
			if (separador > 0) {
				modelo = linea.substring(0, separador).trim();
			}

			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
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

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}