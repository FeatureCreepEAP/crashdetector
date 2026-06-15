package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta mods duplicados en Fabric. Gracias a Aternos porque esta es
 * una implementacion de su codex: https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModDuplicadoFabric implements VerificacionRapida {

	private boolean posibleModDuplicadoFabric = false;
	private boolean activado = false;

	private String mensaje = "";
	private String nombreMod = "";
	private String rutaMod = "";
	private String enlace = "";

	private boolean esperandoLineaDuplicado = false;
	private int lineasEsperando = 0;

	private static final String TEXTO_ERROR_CRITICO = "A critical error occurred";
	private static final String TEXTO_DUPLICATE_VERSIONS = "Duplicate versions for mod ID";
	private static final String TEXTO_MOD_RESOLUTION = "net.fabricmc.loader.discovery.ModResolutionException";
	private static final String TEXTO_EXCEPCION_DUPLICADO = "net.fabricmc.loader.discovery.ModResolutionException: Duplicate versions for mod ID '";
	private static final String TEXTO_AT = " at ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR_CRITICO, TEXTO_DUPLICATE_VERSIONS, TEXTO_MOD_RESOLUTION };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneIndicioFabricDuplicado(evento.linea)) {
			posibleModDuplicadoFabric = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

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

		if (contenido.contains(TEXTO_DUPLICATE_VERSIONS) && contenido.contains(TEXTO_MOD_RESOLUTION)) {
			posibleModDuplicadoFabric = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleModDuplicadoFabric && !activado;
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

		if (linea.contains(TEXTO_ERROR_CRITICO)) {
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
		if (!linea.contains(TEXTO_EXCEPCION_DUPLICADO)) {
			return;
		}

		DatosModDuplicado datos = extraerDatosModDuplicado(linea);

		if (datos == null || datos.nombreMod.isEmpty()) {
			return;
		}

		this.nombreMod = datos.nombreMod;
		this.rutaMod = datos.rutaMod;
		this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

		this.mensaje = MonitorDePID.idioma.mensajeModDuplicadoFabric(nombreMod) + Verificaciones.nl_html + enlace;

		this.activado = true;
	}

	private boolean lineaContieneIndicioFabricDuplicado(String linea) {
		return linea.contains(TEXTO_DUPLICATE_VERSIONS) || linea.contains(TEXTO_MOD_RESOLUTION)
				|| linea.contains(TEXTO_ERROR_CRITICO);
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

		return trazo.trace.contains(TEXTO_DUPLICATE_VERSIONS) && trazo.trace.contains(TEXTO_MOD_RESOLUTION);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}