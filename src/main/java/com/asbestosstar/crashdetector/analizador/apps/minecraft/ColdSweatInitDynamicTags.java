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
 * Detecta un fallo asociado al mod Cold Sweat durante la construcción o
 * inicialización de etiquetas dinámicas.
 *
 * <p>
 * La detección se basa en la presencia conjunta de indicios como:
 * <ul>
 * <li>$cold_sweat$onBuildStart</li>
 * <li>com.momosoftworks.coldsweat.api.event.core.init.InitDynamicTagsEvent.fillTag</li>
 * <li>java.lang.NullPointerException: Cannot invoke
 * "net.minecraft.core.Registry.m_123023_()" because "this.registry" is
 * null</li>
 * </ul>
 *
 * <p>
 * Esto suele indicar un problema interno en Cold Sweat al trabajar con
 * registros o tags dinámicos, normalmente por incompatibilidad, error del mod o
 * combinación conflictiva con otro contenido.
 */
public class ColdSweatInitDynamicTags implements Verificaciones {

	private static final String INDICIO_BUILD_START = "$cold_sweat$onBuildStart";
	private static final String INDICIO_FILL_TAG = "com.momosoftworks.coldsweat.api.event.core.init.InitDynamicTagsEvent.fillTag";
	private static final String INDICIO_REGISTRY_NULO = "java.lang.NullPointerException: Cannot invoke \"net.minecraft.core.Registry.m_123023_()\" because \"this.registry\" is null";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	// Indica si apareció el mixin o punto de inyección de Cold Sweat
	private boolean indicioBuildStart = false;

	// Indica si apareció el método de llenado de tags dinámicos
	private boolean indicioFillTag = false;

	// Indica si apareció el NPE típico del registro nulo
	private boolean indicioRegistryNulo = false;

	// Indica si esta verificación ya quedó activada
	private boolean activado = false;

	// Enlace a la línea más representativa del error
	private String enlace = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INDICIO_BUILD_START, INDICIO_FILL_TAG, INDICIO_REGISTRY_NULO };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(INDICIO_BUILD_START)) {
			indicioBuildStart = true;
		}

		if (linea.contains(INDICIO_FILL_TAG)) {
			indicioFillTag = true;
		}

		if (linea.contains(INDICIO_REGISTRY_NULO)) {
			indicioRegistryNulo = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Solo activar cuando ya están presentes los indicios globales
		if (activado || linea == null || !indicioBuildStart || !indicioFillTag || !indicioRegistryNulo) {
			return;
		}

		// Preferimos enlazar la línea del NPE, porque suele ser la más clara
		if (linea.contains(INDICIO_REGISTRY_NULO)) {
			activar(consola, num);
			return;
		}

		// Respaldo por si el formato del log hace más útil la otra línea
		if (linea.contains(INDICIO_FILL_TAG)) {
			activar(consola, num);
		}
	}

	private void activar(Consola consola, int num) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		this.enlace = consola.agregarErrorALectador(num, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ColdSweatInitDynamicTags();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeColdSweatInitDynamicTags() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreColdSweatInitDynamicTags();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "cold_sweat_init_dynamic_tags";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}