package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre Flywheel (mod de Create) y versiones antiguas de
 * Sodium o sus forks que no cumplen con el requisito mínimo de versión
 * 0.6.0-beta.2.
 */
public class ConflictoFlywheelRubidium implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradaLinea1 = false;
	private boolean posibleConflicto = false;

	private static final String FAILURE_FLYWHEEL_SODIUM = "Failure message: Mod flywheel only supports sodium";
	private static final String VERSION_MINIMA = "0.6.0-beta.2 or above";
	private static final String CURRENTLY_SODIUM = "Currently, sodium is";

	private static final String SODIUM = "sodium";
	private static final String RUBIDIUM = "rubidium";
	private static final String EMBEDDIUM = "embeddium";

	private static final String V_05 = "0.5.";
	private static final String V_04 = "0.4.";
	private static final String V_03 = "0.3.";
	private static final String V_02 = "0.2.";
	private static final String V_01 = "0.1.";
	private static final String V_00 = "0.0.";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILURE_FLYWHEEL_SODIUM, VERSION_MINIMA, CURRENTLY_SODIUM, RUBIDIUM, EMBEDDIUM, SODIUM };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(FAILURE_FLYWHEEL_SODIUM) && linea.contains(VERSION_MINIMA)) {
			posibleConflicto = true;
			encontradaLinea1 = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón de error donde Flywheel requiere Sodium 0.6.0-beta.2 o
	 * superior, pero se encuentra una versión anterior como Sodium 0.5.3 o forks
	 * basados en versiones antiguas.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null || linea.isEmpty()) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Primera línea: "Failure message: Mod flywheel only supports sodium
		// 0.6.0-beta.2 or above"
		if (linea.contains(FAILURE_FLYWHEEL_SODIUM) && linea.contains(VERSION_MINIMA)) {
			posibleConflicto = true;
			encontradaLinea1 = true;
			return;
		}

		if (!posibleConflicto || !encontradaLinea1) {
			return;
		}

		// Segunda línea: "Currently, sodium is 0.5.3" o similar
		if (linea.contains(CURRENTLY_SODIUM)) {
			// Verificamos si la versión actual es inferior a 0.6.0-beta.2
			if (contieneVersionAntigua(linea)) {
				activar(consola, numero_de_linea);
			} else {
				// Si encontramos la segunda línea pero la versión es compatible, reiniciamos
				encontradaLinea1 = false;
			}
			return;
		}

		// Si encontramos la primera línea pero no la segunda en la siguiente iteración,
		// y encontramos otra línea que sugiere una versión incompatible
		// Buscamos también posibles forks de Sodium con versiones antiguas
		if (contieneNombreSodiumOFork(linea) && contieneVersionAntigua(linea)) {
			activar(consola, numero_de_linea);
		}
	}

	private boolean contieneVersionAntigua(String linea) {
		return linea.contains(V_05) || linea.contains(V_04) || linea.contains(V_03) || linea.contains(V_02)
				|| linea.contains(V_01) || linea.contains(V_00);
	}

	private boolean contieneNombreSodiumOFork(String linea) {
		return contieneAsciiIgnoreCase(linea, SODIUM) || contieneAsciiIgnoreCase(linea, RUBIDIUM)
				|| contieneAsciiIgnoreCase(linea, EMBEDDIUM);
	}

	private boolean contieneAsciiIgnoreCase(String texto, String patronMinuscula) {
		int max = texto.length() - patronMinuscula.length();
		for (int i = 0; i <= max; i++) {
			int j = 0;
			while (j < patronMinuscula.length()) {
				char c = texto.charAt(i + j);
				if (c >= 'A' && c <= 'Z') {
					c = (char) (c + 32);
				}

				if (c != patronMinuscula.charAt(j)) {
					break;
				}
				j++;
			}

			if (j == patronMinuscula.length()) {
				return true;
			}
		}
		return false;
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		// Enlazar a la línea del error en el lector
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		// Mensaje de error en HTML con referencia a la incompatibilidad
		mensaje = MonitorDePID.idioma.errorConflictoFlywheelSodium() + Verificaciones.nl_html;
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoFlywheelRubidium();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Prioridad alta: rompe la carga de mods
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoFlywheelSodium();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoConflictoFlywheelSodium())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_flywheel_sodium";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre Flywheel y Sodium.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { FAILURE_FLYWHEEL_SODIUM, VERSION_MINIMA };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}