package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta cuando el cargador de Vulkan no se puede cargar
 * ("Failed to load Vulkan loader"), impidiendo el uso de Vulkan como backend
 * gráfico.
 *
 * Ocurre cuando Vulkan no está instalado o las bibliotecas del cargador no
 * están disponibles en la plataforma.
 */
@ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaErroresGraficos" },
		nota = "Cubre deteccion de 'Failed to load Vulkan loader'")
public class ErrorCargadorVulkan implements Verificaciones {

	private static final java.util.Set<String> MENSAJES_GLOBALES_VISTOS = java.util.Collections
			.synchronizedSet(new java.util.HashSet<>());

	/**
	 * Limpia el estado global de mensajes agregados.
	 */
	public static void reiniciarGlobal() {
		MENSAJES_GLOBALES_VISTOS.clear();
	}

	private boolean activado = false;

	private String mensaje = "";
	private String enlace = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { "Failed to load Vulkan loader" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			return;
		}

		if (linea == null || linea.isEmpty()) {
			return;
		}

		// Detectar el error de cargador de Vulkan
		// Ejemplo: "Failed to load Vulkan loader"
		if (linea.contains("Failed to load Vulkan loader")) {
			String clave = "error_cargador_vulkan";
			if (MENSAJES_GLOBALES_VISTOS.contains(clave)) {
				return;
			}
			MENSAJES_GLOBALES_VISTOS.add(clave);

			this.enlace = (consola != null) ? consola.agregarErrorALectador(numero_de_linea, this) : "";
			this.mensaje = construirMensaje();
			this.activado = true;
		}
	}

	private String construirMensaje() {
		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.error_cargador_vulkan_titulo_y_detalle());
		if (!enlace.isEmpty()) {
			sb.append(" ").append(enlace);
		}
		return sb.toString();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCargadorVulkan();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 800.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_cargador_vulkan();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(
						MonitorDePID.idioma.solucion_para_error_cargador_vulkan_titulo()).construir();
	}

	@Override
	public String id() {
		return "error_cargador_vulkan";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}
