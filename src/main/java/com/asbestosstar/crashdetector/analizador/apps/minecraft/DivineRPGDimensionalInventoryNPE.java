package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class DivineRPGDimensionalInventoryNPE implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String SAVE_INVENTORY_COMPLETO = "divinerpg.capability.DimensionalInventory.saveInventory";
	private static final String SAVE_INVENTORY_CORTO = "DimensionalInventory.saveInventory";
	private static final String CANNOT_INVOKE = "Cannot invoke";
	private static final String IS_NULL = "is null";

	@Override
	public String[] patronesRapidos() {
		return new String[] { SAVE_INVENTORY_COMPLETO, SAVE_INVENTORY_CORTO, CANNOT_INVOKE, IS_NULL };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (activado || linea == null) {
			return;
		}

		if (linea.contains(SAVE_INVENTORY_CORTO)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneError(String linea) {
		return linea.contains(SAVE_INVENTORY_COMPLETO) || linea.contains(SAVE_INVENTORY_CORTO)
				|| linea.contains(CANNOT_INVOKE) || linea.contains(IS_NULL);
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

}