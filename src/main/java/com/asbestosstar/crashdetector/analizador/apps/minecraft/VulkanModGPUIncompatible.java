package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class VulkanModGPUIncompatible implements Verificaciones {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleVulkanGPU = false;

	// Indica si apareció el error principal de GPU incompatible/no encontrada
	private boolean indicioGPU = false;

	// Indica si apareció VulkanMod en el stacktrace o paquete
	private boolean indicioVulkanMod = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String FAILED_GPU = "Failed to find a suitable GPU";
	private static final String VULKANMOD = "net.vulkanmod.vulkan";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILED_GPU, VULKANMOD };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(FAILED_GPU)) {
			indicioGPU = true;
		}

		if (evento.linea.contains(VULKANMOD)) {
			indicioVulkanMod = true;
		}

		if (indicioGPU && indicioVulkanMod) {
			posibleVulkanGPU = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salida temprana si no hay indicios globales
		if (!posibleVulkanGPU || activado || linea == null) {
			return;
		}

		// Verificación precisa en la línea del error
		if (linea.contains(FAILED_GPU) || linea.contains(VULKANMOD)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
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
	@Override
	public String[] ocupaTrazo() {
		return new String[0];
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