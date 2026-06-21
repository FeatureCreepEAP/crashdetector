package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class AccesoDenegadoBackupConfig implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Ruta del archivo original
	private String archivoOrigen = "";

	// Ruta del archivo backup
	private String archivoBackup = "";

	private static final String ACCESS_DENIED_EXCEPTION = "java.nio.file.AccessDeniedException";
	private static final String ACCESS_DENIED_SIMPLE = "AccessDeniedException";
	private static final String ACCESS_DENIED_MARKER = "AccessDeniedException:";
	private static final String FLECHA_BACKUP = "->";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ACCESS_DENIED_EXCEPTION, FLECHA_BACKUP };
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

		if (lineaContieneAccesoDenegadoBackup(linea)) {

			// Extraer rutas origen y destino
			extraerRutas(linea);

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneAccesoDenegadoBackup(String linea) {
		return linea.contains(ACCESS_DENIED_SIMPLE) && linea.contains(FLECHA_BACKUP);
	}

	// Extrae las rutas separadas por "->"
	private void extraerRutas(String linea) {
		try {
			int idx = linea.indexOf(ACCESS_DENIED_MARKER);
			if (idx == -1)
				return;

			int inicioRutas = idx + ACCESS_DENIED_MARKER.length();
			int idxFlecha = linea.indexOf(FLECHA_BACKUP, inicioRutas);

			if (idxFlecha >= 0) {
				archivoOrigen = linea.substring(inicioRutas, idxFlecha).trim();
				archivoBackup = linea.substring(idxFlecha + FLECHA_BACKUP.length()).trim();
			} else {
				archivoOrigen = linea.substring(inicioRutas).trim();
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

}