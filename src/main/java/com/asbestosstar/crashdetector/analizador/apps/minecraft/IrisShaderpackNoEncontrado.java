package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class IrisShaderpackNoEncontrado implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleErrorShaderpack = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Nombre del shaderpack detectado
	private String shaderpack = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global por rendimiento
		if (consola.contenido_verificar.contains("java.nio.file.FileSystemNotFoundException")
				&& consola.contenido_verificar.contains("shaderpacks")) {
			posibleErrorShaderpack = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleErrorShaderpack)
			return;

		if (linea.contains("FileSystemNotFoundException") && linea.contains("shaderpacks")) {

			// Intentar extraer el nombre del shaderpack desde la ruta
			this.shaderpack = extraerNombreShaderpack(linea);

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	// Extrae el nombre del archivo .zip desde la línea del log
	private String extraerNombreShaderpack(String linea) {
		try {
			int idx = linea.lastIndexOf("shaderpacks");
			if (idx == -1)
				return "";

			String sub = linea.substring(idx);

			// Soporte para rutas Windows y Unix
			sub = sub.replace("\\", "/");

			int slash = sub.lastIndexOf("/");
			if (slash != -1)
				return sub.substring(slash + 1).trim();

			return "";
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public Verificaciones nueva() {
		return new IrisShaderpackNoEncontrado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1250;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeIrisShaderpackNoEncontrado(shaderpack) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreIrisShaderpackNoEncontrado();
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
		return "iris_shaderpack_no_encontrado";
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
