package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detecta errores al cargar datos NBT de un mundo, típicamente causados por
 * corrupción del archivo level.dat, playerdata/*.dat o chunks. Extrae el byte
 * corrupto y proporciona orientación detallada para la reparación.
 */
public class ErrorCargaNBTMundoCorrupto implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private static final Pattern PATRON_BYTE_CORRUPTO = Pattern
			.compile("java\\.io\\.UTFDataFormatException: malformed input around byte (\\d+)");

	@Override
	public void verificar(Consola consola) {
		// Este método no se usa; el análisis se hace por línea
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (this.activado) {
			return;
		}

		if (linea.contains("net.minecraft.ReportedException: Loading NBT data")) {
			String contenido = consola.contenido_verificar;
			if (contenido.contains("java.io.UTFDataFormatException: malformed input")) {
				Matcher m = PATRON_BYTE_CORRUPTO.matcher(contenido);
				String byteCorrupto = "desconocido";
				if (m.find()) {
					byteCorrupto = m.group(1);
				}

				String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				this.mensaje = MonitorDePID.idioma.errorCargaNBTMundoCorruptoConByte(byteCorrupto) + enlaceHtml;
				this.activado = true;
			}
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo.trace.contains("net.minecraft.ReportedException: Loading NBT data")
				&& trazo.trace.contains("java.io.UTFDataFormatException: malformed input");
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