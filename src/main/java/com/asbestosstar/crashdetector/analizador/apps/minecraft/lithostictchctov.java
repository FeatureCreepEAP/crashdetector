package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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

	private static final String PATRON = "Caused by: java.lang.RuntimeException: "
			+ "Unknown registry key in ResourceKey[minecraft:root / minecraft:worldgen/structure_type]: lithostitched:jigsaw";
	private static final String REGISTRY_KEY = "Unknown registry key in ResourceKey[minecraft:root / minecraft:worldgen/structure_type]: lithostitched:jigsaw";
	private static final String FIRMA_CTOV = "ctov";

	@Override
	public String[] patronesRapidos() {
		return new String[] { REGISTRY_KEY, "lithostitched:jigsaw", FIRMA_CTOV };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
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

		// Detectar la línea que contiene el patrón principal del error
		if (!patronDetectado && linea.contains(PATRON)) {
			patronDetectado = true;
			lineaPatron = numero_de_linea;
		}

		// Detectar la firma "ctov" en cualquier parte del log
		if (!firmaDetectada && linea.contains(FIRMA_CTOV)) {
			firmaDetectada = true;
		}

		// Solo activamos cuando ambas condiciones se han cumplido
		if (patronDetectado && firmaDetectada) {
			this.mensaje = MonitorDePID.idioma.lithostichctov() + VerificacionesLegacy.nl_html;

			// Enlazar la línea donde apareció el patrón principal, si la tenemos
			if (lineaPatron >= 0) {
				this.enlaceHtml = consola.agregarErrorALectador(lineaPatron, this);
			}

			activado = true;
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
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

		return t.contains(REGISTRY_KEY) && t.contains(FIRMA_CTOV);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}