package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores al cargar datos NBT de un mundo, típicamente causados por
 * corrupción del archivo level.dat, playerdata/*.dat o chunks. Extrae el byte
 * corrupto y proporciona orientación detallada para la reparación.
 */
public class ErrorCargaNBTMundoCorrupto implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";
	private String byteCorrupto = "desconocido";

	private static final String TEXTO_LOADING_NBT = "net.minecraft.ReportedException: Loading NBT data";
	private static final String TEXTO_MALFORMED_INPUT = "java.io.UTFDataFormatException: malformed input";
	private static final String TEXTO_BYTE = "malformed input around byte ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_LOADING_NBT, TEXTO_MALFORMED_INPUT, TEXTO_BYTE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(TEXTO_MALFORMED_INPUT) || evento.linea.contains(TEXTO_BYTE)) {
			this.byteCorrupto = extraerByteCorrupto(evento.linea);
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(TEXTO_BYTE)) {
			this.byteCorrupto = extraerByteCorrupto(linea);
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
	public boolean recomendadoParaCorperata() {
		return true;
	}
}