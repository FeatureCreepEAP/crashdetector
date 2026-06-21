package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class BiomesOPlentyFogDataCustomLiquids implements Verificaciones {

	private static final Set<String> REPORTADOS_GLOBAL = Collections.synchronizedSet(new HashSet<>());

	// Indica si el log contiene indicios globales del error.
	// Esto evita hacer trabajo innecesario línea por línea.
	private boolean posibleProblemaBOP = false;

	// Indica si se encontró la excepción específica de FogData.
	private boolean posibleFogData = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String ERROR_FOGDATA = "class org.joml.Vector4f cannot be cast to class net.minecraft.client.renderer.fog.FogData";

	@Override
	public String[] patronesRapidos() {
		return new String[] {
				"class org.joml.Vector4f cannot be cast to class net.minecraft.client.renderer.fog.FogData",
				"biomesoplenty" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado)
			return;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (consola == null || linea == null || activado)
			return;

		// Verificación precisa en la línea del error principal.
		if (linea.indexOf(ERROR_FOGDATA) >= 0) {
			posibleFogData = true;
			activarSiCompleto(consola, num);
			return;
		}

		// Alternativa: si el lector encuentra primero la referencia a Biomes O' Plenty,
		// también puede enlazar ahí.
		if (linea.indexOf("biomesoplenty") >= 0) {
			posibleProblemaBOP = true;
			activarSiCompleto(consola, num);
		}
	}

	private void activarSiCompleto(Consola consola, int num) {
		if (activado || consola == null)
			return;

		if (!posibleFogData || !posibleProblemaBOP)
			return;

		if (!REPORTADOS_GLOBAL.add(id()))
			return;

		this.enlace = consola.agregarErrorALectador(num, this);
		this.activado = true;
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		// Se ejecuta una vez al terminar de leer el archivo físico o inyectado.
		activarSiCompleto(consola, 0);
	}

	public static void reiniciarGlobal() {
		REPORTADOS_GLOBAL.clear();
	}

	@Override
	public Verificaciones nueva() {
		return new BiomesOPlentyFogDataCustomLiquids();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeBiomesOPlentyFogDataLiquidosPersonalizados() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreBiomesOPlentyFogDataLiquidosPersonalizados();
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
		return "biomes_o_plenty_fogdata_liquidos_personalizados";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}