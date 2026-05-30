package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error de Iron's Spellbooks donde el mod está marcado
 * incorrectamente como compatible con 1.20.1 cuando en realidad es para 1.21.1,
 * causando un NoSuchMethodError al intentar usar
 * ResourceLocation.fromNamespaceAndPath.
 */
public class ErrorIronSpellbooksVersion implements Verificaciones {

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
	 * Se busca el patrón característico del error donde Iron's Spellbooks falla al
	 * crear su instancia porque intenta usar un método que no existe en la versión
	 * de Minecraft actual.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Tercera línea: "at [.../irons_spellbooks@1.20.1-3.4.0.11/...]
		// SchoolRegistry.<clinit>(SchoolRegistry.java:29)"
		if (linea.contains("irons_spellbooks@1.20.1") && linea.contains("SchoolRegistry.<clinit>")
				&& consola.contenido_verificar.contains("ResourceLocation.fromNamespaceAndPath")) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de versión de Iron's
			// Spellbooks
			mensaje = MonitorDePID.idioma.errorIronSpellbooksVersion() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorIronSpellbooksVersion();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Alta prioridad: rompe la carga del mod
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorIronSpellbooksVersion();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorIronSpellbooksVersion())
				.construir();
	}

	@Override
	public String id() {
		return "error_iron_spellbooks_version";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad de versión de Iron's Spellbooks.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("Failed to create mod instance") && t.contains("ModID: irons_spellbooks")
				&& t.contains("java.lang.NoSuchMethodError") && t.contains("ResourceLocation.fromNamespaceAndPath")
				&& t.contains("SchoolRegistry.<clinit>");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}