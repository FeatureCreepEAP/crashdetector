package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FabricMCRuntimeErrorProvidedBy implements Verificaciones {

	private boolean activado = false;
	private final Set<String> modIdsProblematicos = new HashSet<>();
	private final Map<String, String> enlacesPorModId = new HashMap<>();

	private static final String ENTRYPOINT_STAGE = "Could not execute entrypoint stage";
	private static final String PROVIDED_BY = "provided by";
	private static final String PROVIDED_BY_COMILLA = "provided by '";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ENTRYPOINT_STAGE, PROVIDED_BY };
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
	 * Busca líneas que contengan:
	 * <ul>
	 * <li>"Could not execute entrypoint stage"</li>
	 * <li>"provided by"</li>
	 * </ul>
	 * e intenta extraer el mod ID entre comillas simples después de "provided by ".
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!linea.contains(ENTRYPOINT_STAGE) || !linea.contains(PROVIDED_BY)) {
			return;
		}

		// Buscamos el mod ID que está entre comillas simples después de "provided by"
		int startIndex = linea.indexOf(PROVIDED_BY_COMILLA);
		if (startIndex >= 0) {
			// Ajustamos el índice para saltar "provided by '"
			startIndex += PROVIDED_BY_COMILLA.length();

			int endIndex = linea.indexOf('\'', startIndex);
			if (endIndex > startIndex) {
				// Extraemos solo el texto entre las comillas simples (el mod ID)
				String modId = linea.substring(startIndex, endIndex);

				// Solo registrar si es nuevo
				if (modIdsProblematicos.add(modId)) {
					String enlace = consola.agregarErrorALectador(numero_de_linea, this);
					enlacesPorModId.put(modId, enlace);
				}

				activado = true;
			}
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new FabricMCRuntimeErrorProvidedBy();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Prioridad media-alta para errores de inicialización
	}

	@Override
	public String mensaje() {
		if (modIdsProblematicos.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String modId : modIdsProblematicos) {
			String enlace = enlacesPorModId.getOrDefault(modId, "");
			html.append("<li>").append(MonitorDePID.idioma.modids_problematicos()).append(" <b>").append(modId)
					.append("</b> ").append(enlace).append("</li>").append(VerificacionesLegacy.nl_html);
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_fabricmc_runtime_error_provided_by();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		return "fabricmc_runtime_error_providedby";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene el patrón base "Could not execute entrypoint stage"
	 * junto con "provided by 'modid'" para alguno de los mod IDs problemáticos
	 * detectados.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefieren falsos negativos a marcar
	 * trazos que no correspondan a este problema concreto.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!t.contains(ENTRYPOINT_STAGE) || !t.contains(PROVIDED_BY)) {
			return false;
		}

		for (String modId : modIdsProblematicos) {
			String fragmento = PROVIDED_BY_COMILLA + modId + "'";
			if (t.contains(fragmento)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}