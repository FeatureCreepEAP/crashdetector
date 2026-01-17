package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;
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

	/**
	 * Verificación global.
	 * <p>
	 * En este verificador no es necesario un análisis global costoso; toda la
	 * detección se hace por línea en {@link #verificar(Consola, String, int)}, que
	 * es llamado para cada línea del log. Este método se mantiene para cumplir con
	 * la interfaz.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se realiza lógica aquí; todo se hace en verificar(Consola, String, int).
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó o la línea es nula, no seguimos
		if (activado || linea == null) {
			return;
		}

		// Condiciones mínimas para considerar este error
		if (linea.contains("Failed starting WATERMeDIA for Forge")
				&& linea.contains("is NOT compatible with WaterMedia")) {

			// Extraer "Nombre(id)" después de "Forge: "
			int forgeIdx = linea.indexOf("Forge:");
			if (forgeIdx >= 0) {
				int start = forgeIdx + "Forge: ".length();
				int open = linea.indexOf('(', start);
				int close = (open >= 0) ? linea.indexOf(')', open + 1) : -1;
				int endName = (open >= 0) ? open : linea.indexOf(" is NOT", start);

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
					for (ArchivoDeMod m : Buscardor.buscarModsConTermino(termino)) {
						modsUbicacion.add(m.ubicacion_para_publicar());
					}
				} catch (Throwable t) {
					CrashDetectorLogger
							.log("WaterMediaXenonIncompatible: fallo en búsqueda de mods: " + t.getMessage());
				}
			}

			mensaje = MonitorDePID.idioma.errorWaterMediaXenonIncompatible(
					modNombre.isEmpty() ? "(desconocido)" : modNombre, modId.isEmpty() ? "(desconocido)" : modId,
					modsUbicacion) + Verificaciones.nl_html;

			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
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
						modNombre.isEmpty() ? "(desconocido)" : modNombre, modId.isEmpty() ? "(desconocido)" : modId))
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
		if (!t.contains("Failed starting WATERMeDIA for Forge") || !t.contains("is NOT compatible with WaterMedia")) {
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

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
