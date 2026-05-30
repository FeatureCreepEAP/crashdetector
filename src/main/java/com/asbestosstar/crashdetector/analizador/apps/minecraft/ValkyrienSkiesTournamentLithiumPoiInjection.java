package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta un fallo de mixin/inyección relacionado con Valkyrien Skies
 * Tournament y versiones antiguas de Lithium o forks basados en Lithium
 * antiguo.
 *
 * <p>
 * Ejemplo típico: <br>
 * Caused by:
 * org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException...
 * <br>
 * ... merged by
 * me.jellysquid.mods.lithium.mixin.ai.poi.PointOfInterestStorageMixin ... <br>
 * ... valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager ...
 *
 * <p>
 * Este patrón suele aparecer cuando hay una incompatibilidad entre VS
 * Tournament y una versión vieja de Lithium, o un fork basado en Lithium viejo
 * como Radium Reforged.
 */
public class ValkyrienSkiesTournamentLithiumPoiInjection implements Verificaciones {

	// Indica si apareció la excepción de inyección inválida
	private boolean indicioInvalidInjection = false;

	// Indica si apareció el mixin de Lithium relacionado con POI
	private boolean indicioLithiumPoiMixin = false;

	// Indica si apareció el mixin de Valkyrien Skies relacionado con POI
	private boolean indicioVSPoiMixin = false;

	// Indica si esta verificación ya quedó activada
	private boolean activado = false;

	// Enlace a la línea más representativa del error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Búsqueda global ligera para evitar trabajo innecesario por línea
		String contenido = consola.contenido_verificar;

		if (contenido.contains("org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException")) {
			indicioInvalidInjection = true;
		}

		if (contenido.contains("me.jellysquid.mods.lithium.mixin.ai.poi.PointOfInterestStorageMixin")) {
			indicioLithiumPoiMixin = true;
		}

		if (contenido.contains("valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager")) {
			indicioVSPoiMixin = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Solo activar cuando ya están presentes los indicios globales
		if (activado || !indicioInvalidInjection || !indicioLithiumPoiMixin || !indicioVSPoiMixin) {
			return;
		}

		// Preferimos enlazar la línea de la InvalidInjectionException
		if (linea.contains("org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Respaldo si la línea útil es la del merge del mixin
		if (linea.contains("me.jellysquid.mods.lithium.mixin.ai.poi.PointOfInterestStorageMixin")
				|| linea.contains("valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ValkyrienSkiesTournamentLithiumPoiInjection();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1420;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeValkyrienSkiesTournamentLithiumPoiInjection() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreValkyrienSkiesTournamentLithiumPoiInjection();
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
		return "valkyrienskies_tournament_lithium_poi_injection";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}