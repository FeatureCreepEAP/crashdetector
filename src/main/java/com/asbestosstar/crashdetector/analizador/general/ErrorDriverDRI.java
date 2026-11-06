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
 * Detecta fallos del controlador DRI (Direct Rendering Infrastructure) de
 * software rasterización, como swrast_dri.so, que provocan SIGBUS durante
 * operaciones de OpenGL.
 *
 * Es común cuando no hay aceleración por hardware disponible y el sistema
 * recurre a la rasterización por software de Mesa.
 */
@ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaErroresGraficos" },
		nota = "Cubre deteccion de crashes de swrast_dri.so y otros controladores DRI")
public class ErrorDriverDRI implements Verificaciones {

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
		return new String[] { "swrast_dri.so", "dri.so" };
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

		// Detectar crashes en controladores DRI de software rasterización
		// Ejemplo: "C  [swrast_dri.so+0xd04cb8]  try_update_scene_state+0x2f4"
		if (linea.contains("swrast_dri.so") || linea.contains("dri.so")) {
			// Solo activar si es un frame problemático o un crash, no una mención casual
			if (linea.contains("Problematic frame:") || linea.contains("SIGBUS")
					|| linea.contains("SIGSEGV") || linea.contains("[") && linea.contains("dri.so+")) {
				String clave = "error_driver_dri";
				if (MENSAJES_GLOBALES_VISTOS.contains(clave)) {
					return;
				}
				MENSAJES_GLOBALES_VISTOS.add(clave);

 			this.enlace = (consola != null) ? consola.agregarErrorALectador(numero_de_linea, this) : "";
 			this.mensaje = construirMensaje();
 			this.activado = true;
			}
		}
	}

	private String construirMensaje() {
		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.error_driver_dri_titulo_y_detalle());
		if (!enlace.isEmpty()) {
			sb.append(" ").append(enlace);
		}
		return sb.toString();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDriverDRI();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 850.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_driver_dri();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_para_error_driver_dri_titulo()).construir();
	}

	@Override
	public String id() {
		return "error_driver_dri";
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
