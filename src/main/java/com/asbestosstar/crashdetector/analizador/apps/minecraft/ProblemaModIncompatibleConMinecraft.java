package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta mods incompatibles con la versión de Minecraft en Forge.
 * Gracias a Aternos porque esta es una implementacion de su codex:
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModIncompatibleConMinecraft implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";

	private final List<String> nombresMods = new ArrayList<>();
	private final List<String> versionesMinecraft = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	private static final String TEXTO_INICIO = "The mod ";
	private static final String TEXTO_MEDIO = " does not wish to run in Minecraft version Minecraft ";
	private static final String TEXTO_FINAL = ". You will have to remove it to play.";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_MEDIO, TEXTO_FINAL };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta el mod exacto, la version de Minecraft exacta y agrega enlace a la
	 * linea del log.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		DatosModIncompatible datos = extraerDatos(linea);

		if (datos == null) {
			return;
		}

		if (contieneEntrada(datos.nombreMod, datos.versionMinecraft)) {
			return;
		}

		nombresMods.add(datos.nombreMod);
		versionesMinecraft.add(datos.versionMinecraft);
		enlaces.add(consola.agregarErrorALectador(numero_de_linea, this));

		activado = true;
		reconstruirMensaje();
	}

	private boolean contieneIndicioModIncompatible(String linea) {
		return linea.contains(TEXTO_MEDIO) || linea.contains(TEXTO_FINAL);
	}

	/**
	 * Extrae datos de una linea como:
	 *
	 * The mod examplemod does not wish to run in Minecraft version Minecraft
	 * 1.20.1. You will have to remove it to play.
	 *
	 * Sin Pattern/Matcher.
	 */
	private DatosModIncompatible extraerDatos(String linea) {
		int inicio = linea.indexOf(TEXTO_INICIO);

		if (inicio < 0) {
			return null;
		}

		int inicioMod = inicio + TEXTO_INICIO.length();
		int finMod = linea.indexOf(TEXTO_MEDIO, inicioMod);

		if (finMod <= inicioMod) {
			return null;
		}

		String nombreMod = linea.substring(inicioMod, finMod).trim();

		int inicioVersion = finMod + TEXTO_MEDIO.length();
		int finVersion = linea.indexOf(TEXTO_FINAL, inicioVersion);

		if (finVersion <= inicioVersion) {
			return null;
		}

		String versionMinecraft = linea.substring(inicioVersion, finVersion).trim();

		if (!esNombreModValido(nombreMod) || !esVersionMinecraftValida(versionMinecraft)) {
			return null;
		}

		return new DatosModIncompatible(nombreMod, versionMinecraft);
	}

	/**
	 * El patron original usaba \S+ para el mod, entonces aceptamos cualquier token
	 * sin espacios.
	 */
	private boolean esNombreModValido(String nombreMod) {
		if (nombreMod == null || nombreMod.isEmpty()) {
			return false;
		}

		for (int i = 0; i < nombreMod.length(); i++) {
			if (Character.isWhitespace(nombreMod.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * El patron original aceptaba [0-9\.]+.
	 */
	private boolean esVersionMinecraftValida(String version) {
		if (version == null || version.isEmpty()) {
			return false;
		}

		for (int i = 0; i < version.length(); i++) {
			char c = version.charAt(i);

			if (!Character.isDigit(c) && c != '.') {
				return false;
			}
		}

		return true;
	}

	private boolean contieneEntrada(String nombreMod, String versionMinecraft) {
		for (int i = 0; i < nombresMods.size(); i++) {
			if (nombresMods.get(i).equalsIgnoreCase(nombreMod)
					&& versionesMinecraft.get(i).equalsIgnoreCase(versionMinecraft)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Reconstruye el mensaje HTML basado en las listas de mods/versiones.
	 */
	private void reconstruirMensaje() {
		if (nombresMods.isEmpty()) {
			mensaje = "";
			activado = false;
			return;
		}

		if (nombresMods.size() > 1) {
			mensaje = MonitorDePID.idioma.mensajeModIncompatibleConMinecraftPlural(nombresMods, versionesMinecraft);
		} else {
			mensaje = MonitorDePID.idioma.mensajeModIncompatibleConMinecraftSingular(nombresMods.get(0),
					versionesMinecraft.get(0));
		}

		StringBuilder sb = new StringBuilder(mensaje);

		for (String enlace : enlaces) {
			if (enlace != null && !enlace.isEmpty()) {
				sb.append(" ").append(enlace);
			}
		}

		mensaje = sb.toString();
	}

	private static class DatosModIncompatible {
		private final String nombreMod;
		private final String versionMinecraft;

		private DatosModIncompatible(String nombreMod, String versionMinecraft) {
			this.nombreMod = nombreMod;
			this.versionMinecraft = versionMinecraft;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModIncompatibleConMinecraft();
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
		return 900.0f;
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
		return MonitorDePID.idioma.nombreProblemaModIncompatibleConMinecraft();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		// Agregar solucion para cada mod afectado
		for (int i = 0; i < nombresMods.size(); i++) {
			String nombreMod = nombresMods.get(i);
			String versionMC = versionesMinecraft.get(i);

			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(nombreMod));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionForge(versionMC));
		}

		return builder.construir();
	}

	@Override
	public String id() {
		return "mod_incompaible_con_minecraft_version";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_MEDIO, TEXTO_FINAL };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}