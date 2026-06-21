package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta específicamente errores ZipException que incluyen el nombre del
 * archivo JAR corrupto. Extrae y muestra el nombre del archivo afectado para
 * facilitar la identificación.
 */
public class ErrorJarCorruptoConNombre implements Verificaciones {

	private static final String PREFIJO = "Error analyzing [";
	private static final String SUFIJO = "]: java.util.zip.ZipException: zip END header not found";
	private static final String ZIP_END_HEADER_NOT_FOUND = "java.util.zip.ZipException: zip END header not found";
	private static final String EXTENSION_JAR = ".jar";

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { PREFIJO, ZIP_END_HEADER_NOT_FOUND, EXTENSION_JAR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!lineaContieneJarCorruptoConNombre(linea)) {
			return;
		}

		String rutaCompleta = extraerRutaJar(linea);
		if (rutaCompleta == null) {
			return;
		}

		String nombreJar = extraerNombreArchivo(rutaCompleta);

		String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		this.mensaje = MonitorDePID.idioma.errorJarCorruptoConNombre(nombreJar) + enlaceHtml;
		this.activado = true;
	}

	private static boolean lineaContieneJarCorruptoConNombre(String linea) {
		return linea.contains(PREFIJO) && linea.contains(EXTENSION_JAR) && linea.contains(SUFIJO);
	}

	private static String extraerRutaJar(String linea) {
		int inicio = linea.indexOf(PREFIJO);
		if (inicio < 0) {
			return null;
		}

		int inicioRuta = inicio + PREFIJO.length();

		int fin = linea.indexOf(SUFIJO, inicioRuta);
		if (fin < 0 || fin <= inicioRuta) {
			return null;
		}

		String ruta = linea.substring(inicioRuta, fin).trim();
		if (ruta.length() == 0 || !ruta.endsWith(EXTENSION_JAR)) {
			return null;
		}

		return ruta;
	}

	private static String extraerNombreArchivo(String rutaCompleta) {
		int barraNormal = rutaCompleta.lastIndexOf('/');
		int barraWindows = rutaCompleta.lastIndexOf('\\');
		int ultimaBarra = Math.max(barraNormal, barraWindows);

		if (ultimaBarra >= 0 && ultimaBarra + 1 < rutaCompleta.length()) {
			return rutaCompleta.substring(ultimaBarra + 1);
		}

		return rutaCompleta;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { SUFIJO, ZIP_END_HEADER_NOT_FOUND };
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorJarCorruptoConNombre();
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
		return 865.0f;
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionJarCorrupto()).construir();
	}

	@Override
	public String id() {
		return "jar_corrupto_con_nombre";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorJarCorruptoConNombre();
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}