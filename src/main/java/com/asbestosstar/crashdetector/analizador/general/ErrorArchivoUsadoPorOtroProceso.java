package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorArchivoUsadoPorOtroProceso implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde se detectó el error
	private String enlace = "";

	// Ruta del archivo bloqueado, si se puede obtener
	private String archivo = "";

	private static final String FILE_SYSTEM_EXCEPTION = "java.nio.file.FileSystemException";
	private static final String FILE_SYSTEM_EXCEPTION_MARKER = "java.nio.file.FileSystemException:";
	private static final String TEXTO_ARCHIVO_USADO = "The process cannot access the file because it is being used by another process";
	private static final String TEXTO_ARCHIVO_USADO_CON_PREFIJO = ": The process cannot access the file because it is being used by another process";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FILE_SYSTEM_EXCEPTION, TEXTO_ARCHIVO_USADO };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Línea principal del error.
		if (lineaContieneArchivoUsado(linea)) {

			this.archivo = extraerArchivo(linea);
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneArchivoUsado(String linea) {
		return linea.contains(FILE_SYSTEM_EXCEPTION) && linea.contains(TEXTO_ARCHIVO_USADO);
	}

	private String extraerArchivo(String linea) {
		int inicio = linea.indexOf(FILE_SYSTEM_EXCEPTION_MARKER);
		if (inicio < 0) {
			return "";
		}

		inicio += FILE_SYSTEM_EXCEPTION_MARKER.length();

		int fin = linea.indexOf(TEXTO_ARCHIVO_USADO_CON_PREFIJO, inicio);
		if (fin < 0) {
			return "";
		}

		String resultado = linea.substring(inicio, fin).trim();

		if (resultado.length() > 0) {
			return resultado;
		}

		return "";
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorArchivoUsadoPorOtroProceso();
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
		return MonitorDePID.idioma.mensajeErrorArchivoUsadoPorOtroProceso(this.archivo) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorArchivoUsadoPorOtroProceso();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "archivo_usado_por_otro_proceso";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}