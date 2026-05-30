package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta un error de implementación faltante en DecoratedPotBlockEntity.
 *
 * Ejemplo: Receiver class
 * com.min01.archaeology.blockentity.DecoratedPotBlockEntity does not define or
 * inherit an implementation of the resolved method 'abstract
 * net.minecraft.core.BlockPos getBlockPos()'
 *
 * Es un error común en la versión 1.19.2 de L_Enders_Cataclysm. Solución
 * conocida: Instalar el mod PotFix.
 */
public class ErrorPotBlockEntity implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global: Buscamos la combinación de la clase y el error de método
		if (log.contains("com.min01.archaeology.blockentity.DecoratedPotBlockEntity")
				&& log.contains("does not define or inherit an implementation")) {
			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		// Buscamos la línea exacta del error
		if (linea.contains("DecoratedPotBlockEntity")
				&& linea.contains("does not define or inherit an implementation")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorPotBlockEntity();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorPotBlockEntity() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorPotBlockEntity();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_pot_block_entity";
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
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}