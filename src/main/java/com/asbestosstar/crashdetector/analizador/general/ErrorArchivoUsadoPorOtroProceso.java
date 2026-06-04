package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorArchivoUsadoPorOtroProceso implements Verificaciones {

	// Indica si el log contiene un error de archivo bloqueado por otro proceso
	private boolean posibleArchivoBloqueado = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde se detectó el error
	private String enlace = "";

	// Ruta del archivo bloqueado, si se puede obtener
	private String archivo = "";

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Detección global ligera: error típico de Windows cuando otro proceso tiene el
		// archivo abierto.
		if (contenido.contains("java.nio.file.FileSystemException") && contenido
				.contains("The process cannot access the file because it is being used by another process")) {
			posibleArchivoBloqueado = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales.
		if (!posibleArchivoBloqueado) {
			return;
		}

		// Línea principal del error.
		if (!activado && linea.contains("java.nio.file.FileSystemException")
				&& linea.contains("The process cannot access the file because it is being used by another process")) {

			this.archivo = extraerArchivo(linea);
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private String extraerArchivo(String linea) {
		int inicio = linea.indexOf("java.nio.file.FileSystemException:");
		if (inicio < 0) {
			return "";
		}

		inicio += "java.nio.file.FileSystemException:".length();

		int fin = linea.indexOf(": The process cannot access the file because it is being used by another process",
				inicio);
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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