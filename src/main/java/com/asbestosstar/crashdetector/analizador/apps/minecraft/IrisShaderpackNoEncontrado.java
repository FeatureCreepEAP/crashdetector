package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class IrisShaderpackNoEncontrado implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error
	private boolean posibleErrorShaderpack = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Nombre del shaderpack detectado
	private String shaderpack = "";

	private static final String FILE_SYSTEM_NOT_FOUND = "FileSystemNotFoundException";
	private static final String FILE_SYSTEM_NOT_FOUND_COMPLETO = "java.nio.file.FileSystemNotFoundException";
	private static final String SHADERPACKS = "shaderpacks";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FILE_SYSTEM_NOT_FOUND, SHADERPACKS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneErrorShaderpack(evento.linea)) {
			posibleErrorShaderpack = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Detección global por rendimiento
		if (consola.contenido_verificar.contains(FILE_SYSTEM_NOT_FOUND_COMPLETO)
				&& consola.contenido_verificar.contains(SHADERPACKS)) {
			posibleErrorShaderpack = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleErrorShaderpack && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleErrorShaderpack || activado || linea == null) {
			return;
		}

		if (lineaContieneErrorShaderpack(linea)) {

			// Intentar extraer el nombre del shaderpack desde la ruta
			this.shaderpack = extraerNombreShaderpack(linea);

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	// Extrae el nombre del archivo .zip desde la línea del log
	private String extraerNombreShaderpack(String linea) {
		int idx = linea.lastIndexOf(SHADERPACKS);
		if (idx == -1)
			return "";

		String sub = linea.substring(idx);

		int slashUnix = sub.lastIndexOf('/');
		int slashWindows = sub.lastIndexOf('\\');
		int slash = Math.max(slashUnix, slashWindows);

		if (slash != -1) {
			return limpiarEspacios(sub, slash + 1, sub.length());
		}

		return "";
	}

	private boolean lineaContieneErrorShaderpack(String linea) {
		return linea.contains(FILE_SYSTEM_NOT_FOUND) && linea.contains(SHADERPACKS);
	}

	private String limpiarEspacios(String texto, int inicio, int fin) {
		while (inicio < fin && texto.charAt(inicio) <= ' ') {
			inicio++;
		}

		while (fin > inicio && texto.charAt(fin - 1) <= ' ') {
			fin--;
		}

		if (inicio >= fin) {
			return "";
		}

		return texto.substring(inicio, fin);
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

}