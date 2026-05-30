package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class AccesoDenegadoBackupConfig implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleErrorAcceso = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Ruta del archivo original
	private String archivoOrigen = "";

	// Ruta del archivo backup
	private String archivoBackup = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global por rendimiento:
		// AccessDeniedException junto con "->" suele indicar fallo al crear backup
		if (consola.contenido_verificar.contains("java.nio.file.AccessDeniedException")
				&& consola.contenido_verificar.contains("->")) {
			posibleErrorAcceso = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleErrorAcceso)
			return;

		if (linea.contains("AccessDeniedException") && linea.contains("->")) {

			// Extraer rutas origen y destino
			extraerRutas(linea);

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	// Extrae las rutas separadas por "->"
	private void extraerRutas(String linea) {
		try {
			int idx = linea.indexOf("AccessDeniedException:");
			if (idx == -1)
				return;

			String rutas = linea.substring(idx + "AccessDeniedException:".length()).trim();

			String[] partes = rutas.split("->");
			if (partes.length >= 2) {
				archivoOrigen = partes[0].trim();
				archivoBackup = partes[1].trim();
			} else {
				archivoOrigen = rutas;
			}
		} catch (Exception e) {
			// Ignorar errores de parseo
		}
	}

	@Override
	public Verificaciones nueva() {
		return new AccesoDenegadoBackupConfig();
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
		return MonitorDePID.idioma.mensajeAccesoDenegadoBackupConfig(archivoOrigen, archivoBackup) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAccesoDenegadoBackupConfig();
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
		return "acceso_denegado_backup_config";
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
