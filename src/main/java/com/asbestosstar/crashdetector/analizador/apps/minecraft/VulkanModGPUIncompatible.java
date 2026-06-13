package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class VulkanModGPUIncompatible implements Verificaciones {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleVulkanGPU = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		// Detección global ligera: buscar ambas pistas estables
		if (consola.contenido_verificar.contains("Failed to find a suitable GPU")
				&& consola.contenido_verificar.contains("net.vulkanmod.vulkan")) {

			posibleVulkanGPU = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleVulkanGPU)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Salida temprana si no hay indicios globales
		if (!posibleVulkanGPU) {
			return;
		}

		// Verificación precisa en la línea del error
		if (linea.contains("Failed to find a suitable GPU") || linea.contains("net.vulkanmod.vulkan")) {

			if (!activado) {
				this.enlace = consola.agregarErrorALectador(num, this);
				this.activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new VulkanModGPUIncompatible();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1550;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeVulkanModNoEncuentraGPU() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreVulkanModNoEncuentraGPU();
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
		return "vulkan_mod_no_encuentra_gpu";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}