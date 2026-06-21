package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class AnimacionGeckoLibInExiste implements Verificaciones {

	private static final Set<String> REPORTADOS_GLOBAL = Collections.synchronizedSet(new HashSet<>());

	private boolean activado = false;
	private String enlace = "";
	private String archivo = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { "Unable to find animation file", "GeckoLibException" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado)
			return;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (consola == null || linea == null || activado)
			return;

		if (linea.indexOf("Unable to find animation file") < 0)
			return;

		if (linea.indexOf("GeckoLibException") < 0)
			return;

		String archivoDetectado = extraerArchivo(linea);

		String clave = archivoDetectado;
		if (clave == null || clave.isEmpty())
			clave = linea;

		if (!REPORTADOS_GLOBAL.add(clave))
			return;

		this.archivo = archivoDetectado;
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	private static String extraerArchivo(String linea) {
		int fin = linea.indexOf(": Unable to find animation file");
		if (fin < 0)
			return "";

		int inicio = linea.lastIndexOf(' ', fin - 1);
		if (inicio < 0)
			inicio = linea.lastIndexOf(':', fin - 1);

		if (inicio < 0)
			inicio = 0;
		else
			inicio++;

		while (inicio < fin && linea.charAt(inicio) <= ' ')
			inicio++;

		while (fin > inicio && linea.charAt(fin - 1) <= ' ')
			fin--;

		return linea.substring(inicio, fin);
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		// No necesita procesamiento final.
	}

	public static void reiniciarGlobal() {
		REPORTADOS_GLOBAL.clear();
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new AnimacionGeckoLibInExiste();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeAnimacionGeckoInexiste(archivo) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAnimacionGeckoInexiste();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "animacion_geckolib_no_encontrada";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}