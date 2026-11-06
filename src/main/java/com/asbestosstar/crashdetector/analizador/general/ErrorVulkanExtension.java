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
 * Detecta errores de extensiones de Vulkan no disponibles
 * (VK_ERROR_EXTENSION_NOT_PRESENT) que impiden la creación de la instancia
 * de Vulkan.
 *
 * Ocurre cuando Vulkan no está soportado en la plataforma o las extensiones
 * requeridas no están disponibles.
 */
@ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaErroresGraficos" },
		nota = "Cubre deteccion de VK_ERROR_EXTENSION_NOT_PRESENT y errores de Vulkan")
public class ErrorVulkanExtension implements Verificaciones {

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
		return new String[] { "VK_ERROR_EXTENSION_NOT_PRESENT", "VK_ERROR_INCOMPATIBLE_DRIVER" };
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

		// Detectar errores de extensiones de Vulkan
		// Ejemplo: "VK_ERROR_EXTENSION_NOT_PRESENT: Error creating instance"
		if (linea.contains("VK_ERROR_EXTENSION_NOT_PRESENT")
				|| linea.contains("VK_ERROR_INCOMPATIBLE_DRIVER")) {
			String clave = "error_vulkan_extension";
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
		sb.append(MonitorDePID.idioma.error_vulkan_extension_titulo_y_detalle());
		if (!enlace.isEmpty()) {
			sb.append(" ").append(enlace);
		}
		return sb.toString();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorVulkanExtension();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 810.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_vulkan_extension();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_para_error_vulkan_extension_titulo()).construir();
	}

	@Override
	public String id() {
		return "error_vulkan_extension";
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
		return Criticalidad.ERROR;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}
