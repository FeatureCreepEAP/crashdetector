package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.bajo.hw.gpu.HacerVerificacionGPU;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;

public class VerificacionGPU implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private float prioridad = 0;

	@Override
	public void verificar(Consola consola) {
		String log = consola.contenido_verificar;

		boolean inicio = log.contains(HacerVerificacionGPU.LOG_INICIO);
		boolean fin = log.contains(HacerVerificacionGPU.LOG_FIN);

		boolean openglInicio = log.contains(HacerVerificacionGPU.LOG_OPENGL_INICIO);
		boolean openglFin = log.contains(HacerVerificacionGPU.LOG_OPENGL_FIN);

		boolean advertenciaGPU = log.contains(HacerVerificacionGPU.MSG_ADVERTENCIA);

		// ==========================================================
		// 1. POSIBLE CRASH POR GPU / OPENGL
		// ==========================================================
		if ((inicio && !fin) || (openglInicio && !openglFin)) {

			this.activado = true;
			this.prioridad = 1500.0f;

			this.mensaje = MonitorDePID.idioma.gpu_crash_posible() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_crash_causas() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_crash_recomendaciones() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_nota_precision() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_parche_info();

			return;
		}

		// ==========================================================
		// 2. GPU NO ÓPTIMA
		// ==========================================================
		if (advertenciaGPU) {

			this.activado = true;
			this.prioridad = -1000.0f;// No esta importante

			this.mensaje = MonitorDePID.idioma.gpu_no_optima() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_no_optima_detalles() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_nota_precision() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_consumo_energia() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_recomendaciones_rendimiento() + Verificaciones.nl_html
					+ MonitorDePID.idioma.gpu_parche_info();
		}
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
				.agregarBoton(MonitorDePID.idioma.desactivar_parche_gpu(), retener -> {
					ConfigDeParches.obtenerInstancia().establecerActivo("transformacion_de_minecraft_codigo_gpu",
							false);
				}, true).construir();
	}

	@Override
	public String id() {
		return "transformacion_de_minecraft_codigo_gpu";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}
