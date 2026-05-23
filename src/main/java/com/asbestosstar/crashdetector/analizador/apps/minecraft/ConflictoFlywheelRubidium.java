package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
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

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {
		// Se usa el método verificar(Consola, String, int) en lugar de este.
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
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Primera línea: "Failure message: Mod flywheel only supports sodium
		// 0.6.0-beta.2 or above"
		if (linea.contains("Failure message: Mod flywheel only supports sodium")
				&& linea.contains("0.6.0-beta.2 or above")) {
			encontradaLinea1 = true;
		}

		// Segunda línea: "Currently, sodium is 0.5.3" o similar
		if (encontradaLinea1 && linea.contains("Currently, sodium is")) {
			// Verificamos si la versión actual es inferior a 0.6.0-beta.2
			if (linea.contains("0.5.") || linea.contains("0.4.") || linea.contains("0.3.") || linea.contains("0.2.")
					|| linea.contains("0.1.") || linea.contains("0.0.")) {

				// Enlazar a la línea del error en el lector
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

				// Mensaje de error en HTML con referencia a la incompatibilidad
				mensaje = MonitorDePID.idioma.errorConflictoFlywheelSodium() + Verificaciones.nl_html;
				activado = true;
			} else {
				// Si encontramos la segunda línea pero la versión es compatible, reiniciamos
				encontradaLinea1 = false;
			}
		}

		// Si encontramos la primera línea pero no la segunda en la siguiente iteración,
		// y encontramos otra línea que sugiere una versión incompatible
		if (encontradaLinea1 && !linea.contains("Currently, sodium is")) {
			// Buscamos también posibles forks de Sodium con versiones antiguas
			if (linea.toLowerCase().contains("rubidium") || linea.toLowerCase().contains("embeddium")
					|| linea.toLowerCase().contains("sodium")) {
				// Verificamos si hay alguna mención de versiones antiguas
				if (linea.contains("0.5.") || linea.contains("0.4.") || linea.contains("0.3.") || linea.contains("0.2.")
						|| linea.contains("0.1.") || linea.contains("0.0.")) {

					// Enlazar a la línea del error en el lector
					enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

					// Mensaje de error en HTML con referencia a la incompatibilidad
					mensaje = MonitorDePID.idioma.errorConflictoFlywheelSodium() + Verificaciones.nl_html;
					activado = true;
				}
			}
		}
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("Failure message: Mod flywheel only supports sodium") && t.contains("0.6.0-beta.2 or above")
				&& (t.contains("Currently, sodium is") || t.toLowerCase().contains("rubidium")
						|| t.toLowerCase().contains("embeddium"));
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}