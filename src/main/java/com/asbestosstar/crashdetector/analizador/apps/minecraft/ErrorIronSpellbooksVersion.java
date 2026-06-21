package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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

	private static final String IRONS_SPELLBOOKS_1201 = "irons_spellbooks@1.20.1";
	private static final String SCHOOL_REGISTRY_CLINIT = "SchoolRegistry.<clinit>";
	private static final String RESOURCE_LOCATION_METHOD = "ResourceLocation.fromNamespaceAndPath";
	private static final String FAILED_TO_CREATE_MOD_INSTANCE = "Failed to create mod instance";
	private static final String IRONS_SPELLBOOKS_MOD_ID = "ModID: irons_spellbooks";
	private static final String NO_SUCH_METHOD_ERROR = "java.lang.NoSuchMethodError";

	@Override
	public String[] patronesRapidos() {
		return new String[] { IRONS_SPELLBOOKS_1201, SCHOOL_REGISTRY_CLINIT, RESOURCE_LOCATION_METHOD,
				IRONS_SPELLBOOKS_MOD_ID, NO_SUCH_METHOD_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
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

		// Tercera línea: "at [.../irons_spellbooks@1.20.1-3.4.0.11/...]
		// SchoolRegistry.<clinit>(SchoolRegistry.java:29)"
		if (linea.contains(IRONS_SPELLBOOKS_1201) && linea.contains(SCHOOL_REGISTRY_CLINIT)) {

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

		return t.contains(FAILED_TO_CREATE_MOD_INSTANCE) && t.contains(IRONS_SPELLBOOKS_MOD_ID)
				&& t.contains(NO_SUCH_METHOD_ERROR) && t.contains(RESOURCE_LOCATION_METHOD)
				&& t.contains(SCHOOL_REGISTRY_CLINIT);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}