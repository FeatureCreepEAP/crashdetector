package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.IdentityHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta cuando Sodium (o un mod derivado) provoca un fallo de hilo en el
 * sistema de renderizado de Minecraft.
 *
 * El error típico es:
 * "java.lang.IllegalStateException: Rendersystem called from wrong thread"
 *
 * Esto ocurre cuando Sodium intenta acceder al sistema de renderizado de
 * Minecraft desde un hilo incorrecto, lo que puede deberse a incompatibilidad
 * con el hardware (como SPARC/Solaris con llvmpipe), conflictos con otros mods,
 * o problemas con la API de renderizado de Fabric/NeoForge.
 *
 * La verificación requiere que el registro mencione Sodium (o Embeddium/Rubidium)
 * para activarse, evitando falsos positivos con otros mods que puedan generar
 * el mismo error de hilo.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaSodiumYLWJGL"
 * }, nota = "Cubre deteccion de RenderSystem llamado desde hilo incorrecto con Sodium y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaSodiumYLWJGL" },
		nota = "Cubre deteccion de RenderSystem llamado desde hilo incorrecto con Sodium y falsos positivos")
public class ErrorRenderSystemHiloIncorrectoSodium implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	/*
	 * La misma instancia de Verificaciones puede recibir líneas procedentes de más
	 * de una Consola. El estado se conserva por identidad de Consola para evitar
	 * combinar pruebas procedentes de archivos diferentes.
	 */
	private final Map<Consola, Boolean> tieneSodium = new IdentityHashMap<Consola, Boolean>();

	private static final String RENDER_SYSTEM_WRONG_THREAD = "Rendersystem called from wrong thread";

	private static final String SODIUM_MOD = "sodium";

	private static final String SODIUM_PACKAGE = "net.caffeinemc";

	private static final String EMBEDDIUM = "embeddium";

	private static final String RUBIDIUM = "rubidium";

	@Override
	public String[] patronesRapidos() {
		return new String[] { RENDER_SYSTEM_WRONG_THREAD, SODIUM_MOD, SODIUM_PACKAGE, EMBEDDIUM,
				RUBIDIUM };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || consola == null || linea == null) {
			return;
		}

		/*
		 * Registra si esta consola menciona Sodium o uno de sus derivados.
		 */
		if (lineaContieneSodium(linea)) {
			tieneSodium.put(consola, true);
		}

		/*
		 * El error principal es "Rendersystem called from wrong thread". Se activa
		 * solo si el registro menciona Sodium (o Embeddium/Rubidium).
		 */
		if (lineaContieneErrorDeHilo(linea) && tieneContextoSodium(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	/**
	 * Comprueba si una línea contiene el error de RenderSystem llamado desde el
	 * hilo incorrecto. Solo se activa con el mensaje específico, no con cualquier
	 * IllegalStateException.
	 */
	private boolean lineaContieneErrorDeHilo(String linea) {
		return linea.contains(RENDER_SYSTEM_WRONG_THREAD);
	}

	/**
	 * Verifica si una línea menciona Sodium o uno de sus derivados.
	 */
	private boolean lineaContieneSodium(String linea) {
		String lineaMinuscula = linea.toLowerCase();
		return lineaMinuscula.contains(SODIUM_MOD) || lineaMinuscula.contains(SODIUM_PACKAGE)
				|| lineaMinuscula.contains(EMBEDDIUM) || lineaMinuscula.contains(RUBIDIUM);
	}

	/**
	 * Verifica si la consola ya registró menciones de Sodium.
	 */
	private boolean tieneContextoSodium(Consola consola) {
		Boolean encontrado = tieneSodium.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorRenderSystemHiloIncorrectoSodium();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1600;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorRenderSystemHiloIncorrectoSodium() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorRenderSystemHiloIncorrectoSodium();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "error_render_system_hilo_incorrecto_sodium";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
