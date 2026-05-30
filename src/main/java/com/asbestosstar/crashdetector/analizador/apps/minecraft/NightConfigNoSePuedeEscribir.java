package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NightConfigNoSePuedeEscribir implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleErrorEscritura = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Ruta del archivo de configuración afectado
	private String rutaConfig = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global por rendimiento
		if (consola.contenido_verificar.contains("com.electronwill.nightconfig.core.io.WritingException")
				&& consola.contenido_verificar.contains("Failed to write")) {
			posibleErrorEscritura = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleErrorEscritura)
			return;

		if (linea.contains("WritingException") && linea.contains("Failed to write")) {

			// Extraer la ruta del archivo desde la línea
			this.rutaConfig = extraerRuta(linea);

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	// Intenta extraer la ruta del archivo después de "to:"
	private String extraerRuta(String linea) {
		try {
			int idx = linea.lastIndexOf("to:");
			if (idx == -1)
				return "";

			return linea.substring(idx + 3).trim();
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public Verificaciones nueva() {
		return new NightConfigNoSePuedeEscribir();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1100;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeNightConfigNoSePuedeEscribir(rutaConfig) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreNightConfigNoSePuedeEscribir();
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
		return "nightconfig_no_se_puede_escribir";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}
