package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FloralEnchantmentsTagKeyNull implements Verificaciones {

	private boolean encontradoFloral = false;
	private boolean activado = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Buscar referencia global al mod
		if (consola.contenido_verificar.toLowerCase().contains("floralench")) {
			encontradoFloral = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!encontradoFloral)
			return;

		if (linea.contains("java.lang.NullPointerException") && linea.contains("TagKey.location")
				&& linea.contains("Map$Entry.getKey()")) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new FloralEnchantmentsTagKeyNull();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1100;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeFloralEnchantments() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreFloralEnchantments();
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
		return "floral_enchantments_crash";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}
