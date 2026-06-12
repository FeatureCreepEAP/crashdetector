package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre Fabric API (específicamente
 * fabric-resource-loader-v0) y OptiFine que provocan un error de inyección
 * crítica relacionado con ReloadableResourceManager durante la inicialización
 * del juego.
 */
public class ConflictoFabricAPIOptiFine implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;
	public boolean posibleConflicto = false;

	/**
	 * Método de compatibilidad — busca si OptiFine está presente en el contenido
	 * completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si OptiFine está presente en el contenido del registro
		String cont = consola.contenido_verificar;
		if (cont != null) {
			if (cont.contains("optifine") || cont.contains("Optifine")) {
				encontradoOptiFine = true;

				// fabric-resource-loader-v0
				if (cont.contains("Mixin apply for mod fabric_resource_loader_v0 failed")
						&& cont.contains("fabric-resource-loader-v0.mixins.json")
						&& cont.contains("ReloadableResourceManagerImplMixin")
						&& cont.contains("InvalidInjectionException") && cont.contains("Critical injection failure")
						&& cont.contains("could not find any targets matching")
						&& cont.contains("ReloadableResourceManager") && encontradoOptiFine) {
					posibleConflicto = true;
				}

			}
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleConflicto)
			return false;

		return true;
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error de inyección crítica de
	 * fabric-resource-loader-v0 con ReloadableResourceManager, que ocurre cuando
	 * Fabric API y OptiFine no son compatibles.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Verificamos si la línea contiene el error de inyección crítica de
		// fabric-resource-loader-v0
		if (linea.contains("Mixin apply for mod fabric_resource_loader_v0 failed")
				&& linea.contains("fabric-resource-loader-v0.mixins.json")
				&& linea.contains("ReloadableResourceManagerImplMixin") && linea.contains("InvalidInjectionException")
				&& linea.contains("Critical injection failure") && linea.contains("could not find any targets matching")
				&& linea.contains("ReloadableResourceManager") && encontradoOptiFine) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al conflicto entre Fabric API y
			// OptiFine
			mensaje = MonitorDePID.idioma.errorConflictoFabricAPIOptiFine() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoFabricAPIOptiFine();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Alta prioridad: rompe la inicialización del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoFabricAPIOptiFine();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoConflictoFabricAPIOptiFine())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_fabricapi_optifine";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Conservador: solo devuelve true si el trazo contiene las cadenas clave
	 * exactas del error y se ha detectado OptiFine.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("Mixin apply for mod fabric_resource_loader_v0 failed")
				&& t.contains("fabric-resource-loader-v0.mixins.json")
				&& t.contains("ReloadableResourceManagerImplMixin") && t.contains("InvalidInjectionException")
				&& t.contains("Critical injection failure") && t.contains("could not find any targets matching")
				&& t.contains("ReloadableResourceManager") && encontradoOptiFine;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}