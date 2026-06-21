package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores cuando falta metadata en mods.toml para un mod específico.
 * Detecta específicamente el error "mods.toml missing metadata for modid
 * [modid]". Utiliza Buscardor para identificar mods que podrían estar causando
 * el problema.
 */
public class ErrorMetadataModsTomlFaltante implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";
	private String modIdFaltante = "";
	private List<String> modsPotenciales = null;
	private String enlaceHtml = "";

	private static final String TEXTO_ERROR = "mods.toml missing metadata for modid ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
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
	 * Busca el patrón: "mods.toml missing metadata for modid <modid>" en la línea
	 * actual, extrae el modid y usa Buscardor para obtener mods potencialmente
	 * relacionados. También registra la línea en el lector.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		int inicio = linea.indexOf(TEXTO_ERROR);
		if (inicio < 0) {
			return;
		}

		inicio += TEXTO_ERROR.length();

		if (inicio >= linea.length()) {
			return;
		}

		int fin = buscarFinToken(linea, inicio);

		modIdFaltante = linea.substring(inicio, fin).trim();

		if (modIdFaltante.isEmpty()) {
			return;
		}

		// Busca mods que podrían estar causando el problema.
		modsPotenciales = Buscador.obtenerModsConNombre(modIdFaltante);

		mensaje = MonitorDePID.idioma.errorMetadataModsTomlFaltante(modIdFaltante, modsPotenciales)
				+ Verificaciones.nl_html;
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		activado = true;
	}

	/**
	 * Busca el final del token del modid.
	 * <p>
	 * Reemplaza el comportamiento de \w+ sin usar regex. Acepta letras, números,
	 * guion bajo, punto y guion medio para ser un poco más tolerante con modid
	 * reales.
	 * </p>
	 */
	private int buscarFinToken(String texto, int inicio) {
		int i = inicio;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (esCaracterDeModId(c)) {
				i++;
			} else {
				break;
			}
		}

		return i;
	}

	/**
	 * Indica si un carácter puede pertenecer al modid.
	 */
	private boolean esCaracterDeModId(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_' || c == '-'
				|| c == '.';
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMetadataModsTomlFaltante();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 925.0f; // Prioridad muy alta - error crítico que impide cargar mods
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
		return MonitorDePID.idioma.nombre_de_error_metadata_mods_toml_faltante();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_metadata_mods_toml_faltante(modIdFaltante, modsPotenciales))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_metadata_mods_toml_faltante(modIdFaltante))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_metadata_mods_toml_faltante(modIdFaltante)).construir();
	}

	@Override
	public String id() {
		return "metadata_mods_toml_faltante";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene el texto exacto del error, incluyendo el modid si está
	 * disponible.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a marcar un
	 * trazo que no corresponda a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (modIdFaltante != null && !modIdFaltante.isEmpty()) {
			String esperado = TEXTO_ERROR + modIdFaltante;
			return t.contains(esperado);
		}

		// Fallback muy estricto si por alguna razón no se capturó el modid.
		return t.contains(TEXTO_ERROR);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}