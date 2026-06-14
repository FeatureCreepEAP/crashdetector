package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre Fabric API (específicamente
 * fabric-resource-loader-v0) y OptiFine que provocan un error de inyección
 * crítica relacionado con ReloadableResourceManager durante la inicialización
 * del juego.
 */
public class ConflictoFabricAPIOptiFine implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;
	public boolean posibleConflicto = false;

	private static final String OPTIFINE_MINUSCULA = "optifine";
	private static final String OPTIFINE_MIXTA = "Optifine";

	private static final String MIXIN_FAILED = "Mixin apply for mod fabric_resource_loader_v0 failed";
	private static final String MIXINS_JSON = "fabric-resource-loader-v0.mixins.json";
	private static final String RESOURCE_MANAGER_MIXIN = "ReloadableResourceManagerImplMixin";
	private static final String INVALID_INJECTION = "InvalidInjectionException";
	private static final String CRITICAL_INJECTION = "Critical injection failure";
	private static final String NO_TARGETS = "could not find any targets matching";
	private static final String RESOURCE_MANAGER = "ReloadableResourceManager";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { OPTIFINE_MINUSCULA, OPTIFINE_MIXTA, MIXIN_FAILED, MIXINS_JSON, RESOURCE_MANAGER_MIXIN,
				INVALID_INJECTION, CRITICAL_INJECTION, NO_TARGETS, RESOURCE_MANAGER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(OPTIFINE_MINUSCULA) || linea.contains(OPTIFINE_MIXTA)) {
			encontradoOptiFine = true;
		}

		if (lineaContieneErrorFabricResourceLoader(linea)) {
			posibleConflicto = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — busca si OptiFine está presente en el contenido
	 * completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Modo streaming puro: puede no existir contenido_verificar
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Verificamos si OptiFine está presente en el contenido del registro
		String cont = consola.contenido_verificar;
		if (cont.contains(OPTIFINE_MINUSCULA) || cont.contains(OPTIFINE_MIXTA)) {
			encontradoOptiFine = true;

			// fabric-resource-loader-v0
			if (cont.contains(MIXIN_FAILED) && cont.contains(MIXINS_JSON) && cont.contains(RESOURCE_MANAGER_MIXIN)
					&& cont.contains(INVALID_INJECTION) && cont.contains(CRITICAL_INJECTION)
					&& cont.contains(NO_TARGETS) && cont.contains(RESOURCE_MANAGER)) {
				posibleConflicto = true;
			}
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleConflicto && !activado;
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
		if (activado || linea == null || linea.isEmpty()) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		if (!encontradoOptiFine || !posibleConflicto) {
			return;
		}

		// Verificamos si la línea contiene el error de inyección crítica de
		// fabric-resource-loader-v0
		if (lineaContieneErrorFabricResourceLoader(linea)) {
			activar(consola, numero_de_linea);
		}
	}

	private boolean lineaContieneErrorFabricResourceLoader(String linea) {
		return linea.contains(MIXIN_FAILED) && linea.contains(MIXINS_JSON) && linea.contains(RESOURCE_MANAGER_MIXIN)
				&& linea.contains(INVALID_INJECTION) && linea.contains(CRITICAL_INJECTION) && linea.contains(NO_TARGETS)
				&& linea.contains(RESOURCE_MANAGER);
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		// Enlazar a la línea del error en el lector
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		// Mensaje de error en HTML con referencia al conflicto entre Fabric API y
		// OptiFine
		mensaje = MonitorDePID.idioma.errorConflictoFabricAPIOptiFine() + Verificaciones.nl_html;
		activado = true;
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

		return lineaContieneErrorFabricResourceLoader(t) && encontradoOptiFine;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}