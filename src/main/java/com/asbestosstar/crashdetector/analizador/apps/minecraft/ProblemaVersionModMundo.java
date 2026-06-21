package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta discrepancias de versión en mods utilizados por un mundo
 * guardado. Gracias a Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaVersionModMundo implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Datos detectados. No se limpian entre ejecuciones porque esta verificación
	// puede
	// ejecutarse sobre varios logs.
	private final List<String> nombresMods = new ArrayList<>();
	private final List<String> versionesEsperadas = new ArrayList<>();
	private final List<String> versionesActuales = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	private static final String INICIO = "This world was saved with mod ";
	private static final String MARCADOR_VERSION_ESPERADA = " version ";
	private static final String MARCADOR_VERSION_ACTUAL = " and it is now at version ";
	private static final String FINAL = ", things may not work well";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INICIO, MARCADOR_VERSION_ACTUAL, FINAL };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación precisa por línea.
	 *
	 * Esta parte también evita regex. Usa indexOf/subString porque es mucho más
	 * rápido en logs grandes.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		int indiceInicio = linea.indexOf(INICIO);
		if (indiceInicio < 0) {
			return;
		}

		int inicioNombreMod = indiceInicio + INICIO.length();

		int indiceVersionEsperada = linea.indexOf(MARCADOR_VERSION_ESPERADA, inicioNombreMod);
		if (indiceVersionEsperada < 0) {
			return;
		}

		int inicioVersionEsperada = indiceVersionEsperada + MARCADOR_VERSION_ESPERADA.length();

		int indiceVersionActual = linea.indexOf(MARCADOR_VERSION_ACTUAL, inicioVersionEsperada);
		if (indiceVersionActual < 0) {
			return;
		}

		int inicioVersionActual = indiceVersionActual + MARCADOR_VERSION_ACTUAL.length();

		int indiceFinal = linea.indexOf(FINAL, inicioVersionActual);
		if (indiceFinal < 0) {
			indiceFinal = linea.length();
		}

		String nombreMod = linea.substring(inicioNombreMod, indiceVersionEsperada).trim();
		String versionEsperada = linea.substring(inicioVersionEsperada, indiceVersionActual).trim();
		String versionActual = linea.substring(inicioVersionActual, indiceFinal).trim();

		if (nombreMod.isEmpty() || versionEsperada.isEmpty() || versionActual.isEmpty()) {
			return;
		}

		if (contieneMod(nombreMod, versionEsperada, versionActual)) {
			return;
		}

		nombresMods.add(nombreMod);
		versionesEsperadas.add(versionEsperada);
		versionesActuales.add(versionActual);
		enlaces.add(consola.agregarErrorALectador(num, this));

		this.activado = true;
	}

	private boolean contieneMod(String nombreMod, String versionEsperada, String versionActual) {
		for (int i = 0; i < nombresMods.size(); i++) {
			if (nombresMods.get(i).equalsIgnoreCase(nombreMod)
					&& versionesEsperadas.get(i).equalsIgnoreCase(versionEsperada)
					&& versionesActuales.get(i).equalsIgnoreCase(versionActual)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaVersionModMundo();
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
		return 800.0f;
	}

	/**
	 * Devuelve el mensaje de error.
	 */
	@Override
	public String mensaje() {
		if (nombresMods.size() > 1) {
			return MonitorDePID.idioma.mensajeVersionModMundoPlural(nombresMods, versionesEsperadas, versionesActuales)
					+ enlacesComoTexto();
		}

		if (nombresMods.size() == 1) {
			return MonitorDePID.idioma.mensajeVersionModMundoSingular(nombresMods.get(0), versionesEsperadas.get(0),
					versionesActuales.get(0)) + enlacesComoTexto();
		}

		return "";
	}

	/**
	 * Devuelve los enlaces a las líneas detectadas.
	 */
	private String enlacesComoTexto() {
		if (enlaces.isEmpty()) {
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for (String enlace : enlaces) {
			if (enlace != null && !enlace.isEmpty()) {
				builder.append(enlace);
			}
		}

		return builder.toString();
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaVersionModMundo();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		for (int i = 0; i < nombresMods.size(); i++) {
			String nombreMod = nombresMods.get(i);
			String versionEsperada = versionesEsperadas.get(i);

			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarModConVersion(nombreMod, versionEsperada));
		}

		return builder.construir();
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public String id() {
		return "version_mod_mundo";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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