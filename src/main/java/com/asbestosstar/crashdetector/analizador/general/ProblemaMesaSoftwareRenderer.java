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
 * Detecta cuando Minecraft usa el renderizador por software Mesa/llvmpipe en
 * lugar de aceleración por hardware, lo que provoca fallos de LWJGL.
 *
 * Es común en Solaris/Illumos, SPARC, y sistemas sin GPU compatible.
 */
@ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaMesaYNativa" },
		nota = "Cubre deteccion de Mesa/llvmpipe con crash de LWJGL")
public class ProblemaMesaSoftwareRenderer implements Verificaciones {

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

	private boolean detectadoLlvmpipe = false;
	private boolean detectadoMesa = false;
	private boolean detectadoCrashLwjgl = false;

	@Override
	public String[] patronesRapidos() {
		return new String[] { "llvmpipe", "Mesa", "org.lwjgl.system.Checks.check",
				"org.lwjgl.glfw.GLFW.nglfwSetPreeditCallback" };
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

		// Detectar llvmpipe (renderizador por software de Mesa)
		if (linea.contains("llvmpipe")) {
			detectadoLlvmpipe = true;
		}

		// Detectar Mesa como proveedor de OpenGL
		if (linea.contains("Mesa") && (linea.contains("Graphics Vendor") || linea.contains("Backend API")
				|| linea.contains("Graphics Drivers"))) {
			detectadoMesa = true;
		}

		// Detectar el crash de LWJGL asociado (NullPointerException en Checks.check)
		if (linea.contains("org.lwjgl.system.Checks.check")
				|| linea.contains("org.lwjgl.glfw.GLFW.nglfwSetPreeditCallback")) {
			detectadoCrashLwjgl = true;
		}

		// Activar solo si hay llvmpipe + crash de LWJGL
		if (detectadoLlvmpipe && detectadoCrashLwjgl) {
			String clave = "mesa_llvmpipe";
			if (MENSAJES_GLOBALES_VISTOS.contains(clave)) {
				return;
			}
			MENSAJES_GLOBALES_VISTOS.add(clave);

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = construirMensaje();
			this.activado = true;
		}
	}

	private String construirMensaje() {
		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.problema_mesa_titulo_y_detalle());
		if (!enlace.isEmpty()) {
			sb.append(" ").append(enlace);
		}
		return sb.toString();
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaMesaSoftwareRenderer();
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
		return MonitorDePID.idioma.nombre_problema_mesa();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_para_problema_mesa_titulo()).construir();
	}

	@Override
	public String id() {
		return "problema_mesa_software_renderer";
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
