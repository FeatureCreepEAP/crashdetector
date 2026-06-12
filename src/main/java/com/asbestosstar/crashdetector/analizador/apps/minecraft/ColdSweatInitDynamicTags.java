package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
	public void verificar(Consola consola) {
		// Búsqueda global ligera para evitar trabajo innecesario por línea
		String contenido = consola.contenido_verificar;

		if (contenido.contains("$cold_sweat$onBuildStart")) {
			indicioBuildStart = true;
		}

		if (contenido.contains("com.momosoftworks.coldsweat.api.event.core.init.InitDynamicTagsEvent.fillTag")) {
			indicioFillTag = true;
		}

		if (contenido.contains(
				"java.lang.NullPointerException: Cannot invoke \"net.minecraft.core.Registry.m_123023_()\" because \"this.registry\" is null")) {
			indicioRegistryNulo = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (activado || !indicioBuildStart || !indicioFillTag || !indicioRegistryNulo) {
			return false;
		}
		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Solo activar cuando ya están presentes los indicios globales
		if (activado || !indicioBuildStart || !indicioFillTag || !indicioRegistryNulo) {
			return;
		}

		// Preferimos enlazar la línea del NPE, porque suele ser la más clara
		if (linea.contains(
				"java.lang.NullPointerException: Cannot invoke \"net.minecraft.core.Registry.m_123023_()\" because \"this.registry\" is null")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Respaldo por si el formato del log hace más útil la otra línea
		if (linea.contains("com.momosoftworks.coldsweat.api.event.core.init.InitDynamicTagsEvent.fillTag")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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