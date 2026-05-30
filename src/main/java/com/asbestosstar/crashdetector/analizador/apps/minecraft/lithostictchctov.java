package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class lithostictchctov implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	// Estado interno para reproducir la lógica "global" pero en modo por línea:
	// el error solo se considera válido si en algún punto del log aparece
	// el patrón de registry y en algún punto aparece también "ctov".
	private boolean patronDetectado = false;
	private boolean firmaDetectada = false;
	private int lineaPatron = -1;

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificarPorLinea(Consola, String, int)}, llamada por el analizador
	 * línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Replica la lógica original:
	 * <ul>
	 * <li>Se requiere que en el log aparezca el patrón completo del
	 * RuntimeException con "lithostitched:jigsaw".</li>
	 * <li>Y que en algún punto aparezca también "ctov".</li>
	 * </ul>
	 * Cuando ambas condiciones se cumplen, se construye el mensaje y se enlaza la
	 * línea donde apareció el patrón principal.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			return;
		}

		final String patron = "Caused by: java.lang.RuntimeException: "
				+ "Unknown registry key in ResourceKey[minecraft:root / minecraft:worldgen/structure_type]: lithostitched:jigsaw";
		final String firmaCTOV = "ctov";

		// Detectar la línea que contiene el patrón principal del error
		if (!patronDetectado && linea.contains(patron)) {
			patronDetectado = true;
			lineaPatron = numero_de_linea;
		}

		// Detectar la firma "ctov" en cualquier parte del log
		if (!firmaDetectada && linea.contains(firmaCTOV)) {
			firmaDetectada = true;
		}

		// Solo activamos cuando ambas condiciones se han cumplido
		if (patronDetectado && firmaDetectada) {
			this.mensaje = MonitorDePID.idioma.lithostichctov() + Verificaciones.nl_html;

			// Enlazar la línea donde apareció el patrón principal, si la tenemos
			if (lineaPatron >= 0) {
				this.enlaceHtml = consola.agregarErrorALectador(lineaPatron, this);
			}

			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new lithostictchctov();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 3.0f;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_faltar_de_liyhostictchctov();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "lithostichctov";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene tanto el mensaje de Unknown registry key con
	 * "lithostitched:jigsaw" como la firma "ctov".</li>
	 * </ul>
	 * Es deliberadamente conservador: mejor un falso negativo que marcar un trazo
	 * que no corresponda realmente a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(
				"Unknown registry key in ResourceKey[minecraft:root / minecraft:worldgen/structure_type]: lithostitched:jigsaw")
				&& t.contains("ctov");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}
