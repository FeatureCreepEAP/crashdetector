package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre Multiworld y mods de rendimiento (Sodium, Embeddium,
 * Rubidium) que provocan un error de clase incompatible durante la
 * inicialización del cliente.
 */
public class ConflictoMultiworldRendimiento implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String INCOMPATIBLE_CLASS_CHANGE = "IncompatibleClassChangeError";
	private static final String FABRIC_LOADER_GET_INSTANCE = "net.fabricmc.loader.api.FabricLoader.getInstance()";
	private static final String METHODREF_CONSTANT = "must be Methodref constant";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { INCOMPATIBLE_CLASS_CHANGE, FABRIC_LOADER_GET_INSTANCE, METHODREF_CONSTANT };
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
	 * Se busca el patrón característico del error: "IncompatibleClassChangeError",
	 * "net.fabricmc.loader.api.FabricLoader.getInstance()" y "must be Methodref
	 * constant".
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		if (lineaContieneErrorMultiworld(linea)) {
			activar(consola, numero_de_linea);
		}
	}

	private boolean lineaContieneErrorMultiworld(String linea) {
		return linea.contains(INCOMPATIBLE_CLASS_CHANGE) && linea.contains(FABRIC_LOADER_GET_INSTANCE)
				&& linea.contains(METHODREF_CONSTANT);
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		// Enlazar a la línea del error en el lector
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		// Mensaje de error en HTML con referencia a mods de rendimiento
		mensaje = MonitorDePID.idioma.errorConflictoMultiworldRendimiento() + Verificaciones.nl_html;
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoMultiworldRendimiento();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Alta prioridad: rompe inicialización del cliente
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoMultiworldRendimiento();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoConflictoMultiworldRendimiento())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_multiworld_rendimiento";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Ultra conservador: solo devuelve true si el trazo contiene las tres cadenas
	 * clave exactas del error. Evita falsos positivos.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return lineaContieneErrorMultiworld(trazo.trace);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}