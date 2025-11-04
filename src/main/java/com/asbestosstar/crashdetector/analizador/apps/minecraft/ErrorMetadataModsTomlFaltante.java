package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.Buscardor;

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

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificar(Consola, String, int)}, llamada por el analizador línea a
	 * línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
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
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		// Detecta el error específico de metadata faltante
		if (linea.contains("mods.toml missing metadata for modid")) {

			// Extrae el modid faltante usando expresión regular
			Pattern pattern = Pattern.compile("mods\\.toml missing metadata for modid (\\w+)");
			Matcher matcher = pattern.matcher(linea);
			if (matcher.find()) {
				modIdFaltante = matcher.group(1);

				// Busca mods que podrían estar causando el problema
				modsPotenciales = Buscardor.obtenerModsConNombre(modIdFaltante);

				mensaje = MonitorDePID.idioma.errorMetadataModsTomlFaltante(modIdFaltante, modsPotenciales)
						+ Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
			}
		}
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
		if (!activado)
			return "";
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
			String esperado = "mods.toml missing metadata for modid " + modIdFaltante;
			return t.contains(esperado);
		}

		// Fallback muy estricto si por alguna razón no se capturó el modid.
		return t.contains("mods.toml missing metadata for modid ");
	}

}
