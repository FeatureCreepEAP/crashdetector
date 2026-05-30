package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class DivineRPGDimensionalInventoryNPE implements Verificaciones {

	private boolean posibleError = false;
	private boolean activado = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		if (consola.contenido_verificar.contains("divinerpg.capability.DimensionalInventory.saveInventory")
				&& consola.contenido_verificar.contains("Cannot invoke")
				&& consola.contenido_verificar.contains("is null")) {

			posibleError = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		if (!posibleError)
			return;

		if (linea.contains("DimensionalInventory.saveInventory")) {

			if (!activado) {
				this.enlace = consola.agregarErrorALectador(num, this);
				this.activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new DivineRPGDimensionalInventoryNPE();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1800;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeDivineRPGDimensionalInventoryNPE() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDivineRPGDimensionalInventoryNPE();
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
		return "divinerpg_dimensional_inventory_npe";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}