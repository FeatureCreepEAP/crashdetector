package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el fallo de inicio de WaterMedia en Forge por incompatibilidad con
 * Xenon. No usa expresiones regulares; busca substrings clave en una sola
 * línea.
 *
 * Ejemplo de línea: java.lang.RuntimeException: Failed starting WATERMeDIA for
 * Forge: Xenon(xenon) is NOT compatible with WaterMedia. Please replace it with
 * Embeddium (embeddium) or Sodium (sodium)
 */
public class WaterMediaXenonIncompatible implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private String modNombre = "";
	private String modId = "";
	private final List<String> modsUbicacion = new ArrayList<>();

	private static final String FAILED_WATERMEDIA_FORGE = "Failed starting WATERMeDIA for Forge";
	private static final String NOT_COMPATIBLE_WATERMEDIA = "is NOT compatible with WaterMedia";
	private static final String FORGE_PREFIX = "Forge: ";
	private static final String IS_NOT = " is NOT";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILED_WATERMEDIA_FORGE, NOT_COMPATIBLE_WATERMEDIA };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Condiciones mínimas para considerar este error
		if (lineaContieneError(linea)) {

			// Extraer "Nombre(id)" después de "Forge: "
			int forgeIdx = linea.indexOf("Forge:");
			if (forgeIdx >= 0) {
				int start = forgeIdx + FORGE_PREFIX.length();
				int open = linea.indexOf('(', start);
				int close = (open >= 0) ? linea.indexOf(')', open + 1) : -1;
				int endName = (open >= 0) ? open : linea.indexOf(IS_NOT, start);

				if (endName > start) {
					modNombre = linea.substring(start, endName).trim();
				}
				if (open >= 0 && close > open) {
					modId = linea.substring(open + 1, close).trim();
				}
			}

			// Búsqueda opcional de JARs que contengan el mod (no bloquea la activación)
			String termino = !modId.isEmpty() ? modId : modNombre;
			if (termino != null && !termino.isEmpty()) {
				try {
					for (ArchivoDeMod m : Buscador.buscarModsConTermino(termino)) {
						modsUbicacion.add(m.ubicacion_para_publicar());
					}
				} catch (Throwable t) {
					CrashDetectorLogger
							.log("WaterMediaXenonIncompatible: fallo en búsqueda de mods: " + t.getMessage());
				}
			}

			mensaje = MonitorDePID.idioma.errorWaterMediaXenonIncompatible(
					modNombre.isEmpty() ? "(desconocido)" : modNombre, modId.isEmpty() ? "(desconocido)" : modId,
					modsUbicacion) + VerificacionesLegacy.nl_html;

			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	private boolean lineaContieneError(String linea) {
		return linea.contains(FAILED_WATERMEDIA_FORGE) && linea.contains(NOT_COMPATIBLE_WATERMEDIA);
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new WaterMediaXenonIncompatible();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Evita el inicio correcto del juego
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeWaterMediaXenonIncompatible();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1WaterMediaXenonIncompatible(
						modNombre.isEmpty() ? MonitorDePID.idioma.desconocido() : modNombre,
						modId.isEmpty() ? MonitorDePID.idioma.desconocido() : modId))
				.agregarEtiqueta(MonitorDePID.idioma.paso2WaterMediaXenonIncompatible(modsUbicacion))
				.agregarEtiqueta(MonitorDePID.idioma.paso3WaterMediaXenonIncompatible()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "watermedia_xenon";
	}

	/**
	 * Indica si este verificador debe "ocupar" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo se marca como perteneciente a este
	 * problema cuando:
	 * <ul>
	 * <li>El verificador ya se activó.</li>
	 * <li>El trazo contiene el mensaje base de fallo de WaterMedia.</li>
	 * <li>Y además menciona que el mod es "NOT compatible with WaterMedia".</li>
	 * <li>Opcionalmente, si se dispone de {@code modNombre} o {@code modId}, se
	 * intenta afinar usando esos datos.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo antes que
	 * asociar un trazo que no corresponda realmente a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		// Patrón base del error
		if (!t.contains(FAILED_WATERMEDIA_FORGE) || !t.contains(NOT_COMPATIBLE_WATERMEDIA)) {
			return false;
		}

		// Si no tenemos nombre ni id, con el patrón base ya es suficientemente
		// específico
		if ((modNombre == null || modNombre.isEmpty()) && (modId == null || modId.isEmpty())) {
			return true;
		}

		// Afinar si conocemos el nombre o id del mod
		if (modNombre != null && !modNombre.isEmpty() && t.contains(modNombre)) {
			return true;
		}
		if (modId != null && !modId.isEmpty() && t.contains(modId)) {
			return true;
		}

		// Fallback: solo patrón base
		return true;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}