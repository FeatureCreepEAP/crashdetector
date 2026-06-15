package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FloralEnchantmentsTagKeyNull implements VerificacionRapida {

	private boolean encontradoFloral = false;
	private boolean activado = false;
	private String enlace = "";

	private static final String FLORALENCH = "floralench";
	private static final String NULL_POINTER_EXCEPTION = "java.lang.NullPointerException";
	private static final String TAGKEY_LOCATION = "TagKey.location";
	private static final String MAP_ENTRY_GET_KEY = "Map$Entry.getKey()";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FLORALENCH, TAGKEY_LOCATION, MAP_ENTRY_GET_KEY };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(FLORALENCH)) {
			encontradoFloral = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Buscar referencia global al mod
		if (consola.contenido_verificar.contains(FLORALENCH)) {
			encontradoFloral = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!encontradoFloral || activado || linea == null) {
			return;
		}

		if (linea.contains(NULL_POINTER_EXCEPTION) && linea.contains(TAGKEY_LOCATION)
				&& linea.contains(MAP_ENTRY_GET_KEY)) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return encontradoFloral && !activado;
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