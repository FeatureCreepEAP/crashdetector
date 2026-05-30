package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta mods duplicados en Fabric. Gracias a Aternos porque esta es
 * una implementacion de su codex: https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModDuplicadoFabric implements Verificaciones {

	private boolean posibleModDuplicadoFabric = false;
	private boolean activado = false;

	private String mensaje = "";
	private String nombreMod = "";
	private String rutaMod = "";
	private String enlace = "";

	private boolean esperandoLineaDuplicado = false;
	private int lineasEsperando = 0;

	private static final String TEXTO_ERROR_CRITICO = "A critical error occurred";
	private static final String TEXTO_EXCEPCION_DUPLICADO = "net.fabricmc.loader.discovery.ModResolutionException: Duplicate versions for mod ID '";
	private static final String TEXTO_AT = " at ";

	/**
	 * Verificacion global ligera.
	 *
	 * Se ejecuta primero. No se limpian campos porque esta verificacion puede
	 * ejecutarse sobre varios archivos de log con la misma instancia.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains("Duplicate versions for mod ID")
				&& contenido.contains("net.fabricmc.loader.discovery.ModResolutionException")) {
			posibleModDuplicadoFabric = true;
		}
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta el mod duplicado y agrega enlace a la linea exacta.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleModDuplicadoFabric || activado || linea == null || linea.isEmpty()) {
			return;
		}

		String l = linea.trim();

		if (l.contains(TEXTO_ERROR_CRITICO)) {
			esperandoLineaDuplicado = true;
			lineasEsperando = 0;
		}

		if (esperandoLineaDuplicado) {
			lineasEsperando++;

			if (lineasEsperando > 8) {
				esperandoLineaDuplicado = false;
				lineasEsperando = 0;
			}
		}

		// El error puede estar en la misma linea del texto critico o unas lineas
		// después.
		if (!l.contains(TEXTO_EXCEPCION_DUPLICADO)) {
			return;
		}

		DatosModDuplicado datos = extraerDatosModDuplicado(l);

		if (datos == null || datos.nombreMod.isEmpty()) {
			return;
		}

		this.nombreMod = datos.nombreMod;
		this.rutaMod = datos.rutaMod;
		this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

		this.mensaje = MonitorDePID.idioma.mensajeModDuplicadoFabric(nombreMod) + Verificaciones.nl_html + enlace;

		this.activado = true;
	}

	/**
	 * Extrae datos de:
	 *
	 * net.fabricmc.loader.discovery.ModResolutionException: Duplicate versions for
	 * mod ID 'modid' ... at ruta
	 *
	 * Sin Pattern/Matcher.
	 */
	private DatosModDuplicado extraerDatosModDuplicado(String linea) {
		int inicio = linea.indexOf(TEXTO_EXCEPCION_DUPLICADO);

		if (inicio < 0) {
			return null;
		}

		int inicioNombre = inicio + TEXTO_EXCEPCION_DUPLICADO.length();
		int finNombre = linea.indexOf('\'', inicioNombre);

		if (finNombre <= inicioNombre) {
			return null;
		}

		String mod = linea.substring(inicioNombre, finNombre).trim();
		String ruta = extraerRutaMod(linea, finNombre + 1);

		return new DatosModDuplicado(mod, ruta);
	}

	/**
	 * Extrae la ruta después de " at " hasta ']' o final de linea.
	 */
	private String extraerRutaMod(String linea, int desde) {
		int indiceAt = linea.indexOf(TEXTO_AT, desde);

		if (indiceAt < 0) {
			return "";
		}

		int inicioRuta = indiceAt + TEXTO_AT.length();

		while (inicioRuta < linea.length() && Character.isWhitespace(linea.charAt(inicioRuta))) {
			inicioRuta++;
		}

		if (inicioRuta >= linea.length()) {
			return "";
		}

		int finRuta = linea.indexOf(']', inicioRuta);

		if (finRuta < 0) {
			finRuta = linea.length();
		}

		return linea.substring(inicioRuta, finRuta).trim();
	}

	private static class DatosModDuplicado {
		private final String nombreMod;
		private final String rutaMod;

		private DatosModDuplicado(String nombreMod, String rutaMod) {
			this.nombreMod = nombreMod;
			this.rutaMod = rutaMod;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModDuplicadoFabric();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema.
	 */
	@Override
	public float prioridad() {
		return 850.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaModDuplicadoFabric();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionEliminarModDuplicado(rutaMod))
				.construir();
	}

	@Override
	public String id() {
		return "mod_duplicado_fabric";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains("Duplicate versions for mod ID")
				&& trazo.trace.contains("net.fabricmc.loader.discovery.ModResolutionException");
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}