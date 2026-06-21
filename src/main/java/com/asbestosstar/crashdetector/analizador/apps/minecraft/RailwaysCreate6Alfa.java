package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RailwaysCreate6Alfa implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String claveFaltante = "";

	// Flags para comprobaciones globales (evita repetir contains() en cada línea)
	private boolean tieneObjects = false;
	private boolean tieneRegistrate = false;
	private boolean tieneRailways = false;

	private static final String TEXTO_OBJECTS = "at java.util.Objects.requireNonNull(";
	private static final String TEXTO_REGISTRATE = "at com.tterrag.registrate.util.entry.RegistryEntry.get(";
	private static final String TEXTO_RAILWAYS = "railways";
	private static final String TEXTO_NPE_REGISTRY = "java.lang.NullPointerException: Registry entry not present:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_OBJECTS, TEXTO_REGISTRATE, TEXTO_RAILWAYS, TEXTO_NPE_REGISTRY };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(TEXTO_OBJECTS)) {
			tieneObjects = true;
		}

		if (linea.contains(TEXTO_REGISTRATE)) {
			tieneRegistrate = true;
		}

		if (linea.contains(TEXTO_RAILWAYS)) {
			tieneRailways = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó o el contexto global no coincide, no hacemos nada
		if (activado || !tieneObjects || !tieneRegistrate || !tieneRailways || linea == null) {
			return;
		}

		// Buscar la línea que contiene el NullPointer con la clave de registro
		if (linea.contains(TEXTO_NPE_REGISTRY)) {
			int idx = linea.indexOf("present:");
			if (idx >= 0) {
				claveFaltante = linea.substring(idx + "present:".length()).trim();
			}

			if (claveFaltante == null || claveFaltante.isEmpty()) {
				return;
			}

			mensaje = MonitorDePID.idioma.errorRailwaysCreate6Alfa(claveFaltante);
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new RailwaysCreate6Alfa();
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
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeRailwaysCreate6Alfa();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoRailwaysCreate6Alfa())
				.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "railways_6_alfa";
	}

	/**
	 * Indica si este verificador debe "ocupar" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos se comprueba:
	 * <ul>
	 * <li>Que el verificador ya se haya activado.</li>
	 * <li>Que el trazo contenga el mensaje de NPE con "Registry entry not
	 * present".</li>
	 * <li>Opcionalmente, se intenta afinar con la clave faltante y/o el frame de
	 * Registrate si están presentes.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo antes que
	 * marcar un trazo que no pertenezca realmente a este error.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		// Coincidencia fuerte: el propio mensaje de NPE de Registry
		if (t.contains(TEXTO_NPE_REGISTRY)) {
			return true;
		}

		// Afinar con la clave faltante y el frame de Registrate, si los tenemos
		if (!claveFaltante.isEmpty() && t.contains("Registry entry not present:") && t.contains(claveFaltante)
				&& t.contains(TEXTO_REGISTRATE)) {
			return true;
		}

		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}