package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores al cargar datos NBT de un mundo, típicamente causados por
 * corrupción del archivo level.dat, playerdata/*.dat o chunks. Extrae el byte
 * corrupto y proporciona orientación detallada para la reparación.
 */
public class ErrorCargaNBTMundoCorrupto implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;

	private String mensaje = "";
	private String byteCorrupto = "desconocido";

	private static final String TEXTO_LOADING_NBT = "net.minecraft.ReportedException: Loading NBT data";
	private static final String TEXTO_MALFORMED_INPUT = "java.io.UTFDataFormatException: malformed input";
	private static final String TEXTO_BYTE = "malformed input around byte ";

	@Override
	public void verificar(Consola consola) {

		// Chequeo global barato:
		// si el log completo no contiene las dos señales principales,
		// no hace falta procesar línea por línea.
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_LOADING_NBT) && contenido.contains(TEXTO_MALFORMED_INPUT)) {
			this.analizarLineas = true;
			this.byteCorrupto = extraerByteCorrupto(contenido);
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!analizarLineas || this.activado) {
			return;
		}

		if (linea == null) {
			return;
		}

		if (linea.contains(TEXTO_LOADING_NBT)) {
			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorCargaNBTMundoCorruptoConByte(byteCorrupto) + enlaceHtml;
			this.activado = true;
		}
	}

	private String extraerByteCorrupto(String contenido) {
		if (contenido == null || contenido.isEmpty()) {
			return "desconocido";
		}

		int inicioTexto = contenido.indexOf(TEXTO_BYTE);
		if (inicioTexto < 0) {
			return "desconocido";
		}

		int inicioNumero = inicioTexto + TEXTO_BYTE.length();
		int finNumero = inicioNumero;

		while (finNumero < contenido.length() && Character.isDigit(contenido.charAt(finNumero))) {
			finNumero++;
		}

		if (finNumero <= inicioNumero) {
			return "desconocido";
		}

		return contenido.substring(inicioNumero, finNumero);
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo != null && trazo.trace != null && trazo.trace.contains(TEXTO_LOADING_NBT)
				&& trazo.trace.contains(TEXTO_MALFORMED_INPUT);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCargaNBTMundoCorrupto();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 1000.0f;
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionErrorCargaNBTMundoCorrupto())
				.construir();
	}

	@Override
	public String id() {
		return "carga_nbt_mundo_corrupto";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorCargaNBTMundoCorrupto();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}