package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.bajo.hw.gpu.HacerVerificacionGPU;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.gui.tipos.gpu.GPUFixGUI;
import com.asbestosstar.crashdetector.gui.tipos.gpu.GPUFixOptimusPrime;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;

public class VerificacionGPU implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private float prioridad = 0;
	public static boolean hayProblema = false;

	private boolean inicioGPU = false;
	private boolean finGPU = false;
	private boolean inicioOpenGL = false;
	private boolean finOpenGL = false;
	private boolean advertenciaGPU = false;

	@Override
	public String[] patronesRapidos() {
		return new String[] { HacerVerificacionGPU.LOG_INICIO, HacerVerificacionGPU.LOG_FIN,
				HacerVerificacionGPU.LOG_OPENGL_INICIO, HacerVerificacionGPU.LOG_OPENGL_FIN,
				HacerVerificacionGPU.MSG_ADVERTENCIA };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || linea.isEmpty() || activado) {
			return;
		}

		if (linea.contains(HacerVerificacionGPU.LOG_INICIO)) {
			inicioGPU = true;
		}

		if (linea.contains(HacerVerificacionGPU.LOG_FIN)) {
			finGPU = true;
		}

		if (linea.contains(HacerVerificacionGPU.LOG_OPENGL_INICIO)) {
			inicioOpenGL = true;
		}

		if (linea.contains(HacerVerificacionGPU.LOG_OPENGL_FIN)) {
			finOpenGL = true;
		}

		if (linea.contains(HacerVerificacionGPU.MSG_ADVERTENCIA)) {
			advertenciaGPU = true;
			activarAdvertenciaGPU();
		}
	}

	@Override
	public void finalizarArchivo(Consola consola,
			com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo estado) {
		if (activado) {
			return;
		}

		if ((inicioGPU && !finGPU) || (inicioOpenGL && !finOpenGL)) {
			activarCrashGPU();
			return;
		}

		if (advertenciaGPU) {
			activarAdvertenciaGPU();
		}
	}

	private void activarCrashGPU() {
		this.activado = true;
		this.prioridad = 1500.0f;
		hayProblema = true;

		this.mensaje = MonitorDePID.idioma.gpu_crash_posible() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_crash_causas() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_crash_recomendaciones() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_nota_precision() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_parche_info();
	}

	private void activarAdvertenciaGPU() {
		this.activado = true;
		this.prioridad = -1000.0f; // No es tan importante
		hayProblema = true;

		this.mensaje = MonitorDePID.idioma.gpu_no_optima() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_no_optima_detalles() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_nota_precision() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_consumo_energia() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_recomendaciones_rendimiento() + Verificaciones.nl_html
				+ MonitorDePID.idioma.gpu_parche_info();
	}

	@Override
	public Verificaciones nueva() {
		return new VerificacionGPU();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return prioridad;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_verificacion_gpu();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(MonitorDePID.idioma.nombre_verificacion_gpu())
				.agregarBoton(MonitorDePID.idioma.gpuFixTitulo(), retener -> {
					GPUFixGUI gui = TipoGUI.GPU_FIX.obtenerGUIPredeterminado(GPUFixOptimusPrime.ID,
							GPUFixOptimusPrime::new);
					gui.init();
				}, true).agregarBoton(MonitorDePID.idioma.desactivar_parche_gpu(), retener -> {
					ConfigDeParches.obtenerInstancia().establecerActivo("transformacion_de_minecraft_codigo_gpu",
							false);
				}, true).construir();
	}

	@Override
	public String id() {
		return "transformacion_de_minecraft_codigo_gpu";
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
	public boolean recomendadoParaCorperata() {
		return true;
	}
}