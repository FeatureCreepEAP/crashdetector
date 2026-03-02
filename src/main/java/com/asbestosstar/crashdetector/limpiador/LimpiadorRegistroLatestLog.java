package com.asbestosstar.crashdetector.limpiador;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Limpiador de latest.log que: 1) SOLO limpia líneas de trazas (stack traces).
 * 2) Conserva los niveles/hilos/horas en LÍNEAS NORMALES (no-traza). 3) En
 * LÍNEAS DE TRAZA elimina el prefijo del logger para que la traza quede limpia
 * y legible.
 *
 * Ejemplo: [14:24:19] [Worker-ResourceReload-8/INFO]: at
 * paquete.Clase.metodo(Clase.java:123) -> at
 * paquete.Clase.metodo(Clase.java:123)
 *
 * Además: - Normaliza líneas "at ..." con tabs/espacios al inicio -> " at ..."
 * - Quita bloques "[Clase:metodo:línea]: " que ensucian la lectura. - Limpia
 * rutas de transformadores (TRANSFORMER/, mixin/, modid@ver/...) delante del
 * nombre de clase.
 *
 * Todo en español como se solicitó.
 */
public class LimpiadorRegistroLatestLog implements LimpiadorDeRegistro {

	/** Separador típico entre el prefijo del logger y el cuerpo. */
	private static final String SEPARADOR_PREFIJO = "]: ";

	/** Detecta "at ..." con tabs/espacios al inicio (post-prefijo). */
	private static final Pattern AT_CON_TABS = Pattern.compile("^[\\t ]*at\\s+");

	/**
	 * Normaliza líneas de traza con rutas de transformadores delante del nombre de
	 * clase: at
	 * TRANSFORMER/geckolib@4.7.4/software.bernie.geckolib.core.molang.MolangParser.parseOneLine(MolangParser.java:246)
	 * Deja solo "paquete.Clase.metodo(...)".
	 */
	private static final Pattern AT_CON_TRANSFORMADOR = Pattern
			.compile("^(\\s*)at\\s+(?:[A-Z_]+/)?(?:[^/]+/)+([\\w.$]+\\.[\\w$<>]+\\([^)]*\\))(.*)$");

	/**
	 * Quita bloques "[paquete.Clase:metodo:linea]: " en el CUERPO de la traza. Ej:
	 * [software.bernie.geckolib.loading.json.typeadapter.BakedAnimationsAdapter:deserialize:43]:
	 */
	private static final Pattern BLOQUE_CLASE_METODO_LINEA = Pattern.compile("\\[[\\w.$]+:[\\w$<>]+:\\d+\\]:\\s*");

	/** Elimina ": " inicial residual en el cuerpo tras limpiezas. */
	private static final Pattern DOS_PUNTOS_INICIALES = Pattern.compile("^:\\s+");

	@Override
	public String limpiarConsola(String contenido) {
		if (contenido == null || contenido.isEmpty())
			return contenido;

		String[] lineas = contenido.split("\n", -1); // conservar líneas vacías
		StringBuilder out = new StringBuilder(contenido.length());

		for (String linea : lineas) {
			// 1) Partir en prefijo/cuerpo si existe el separador típico
			int iSep = linea.indexOf(SEPARADOR_PREFIJO);
			String prefijo = null;
			String cuerpo = linea;
			if (iSep != -1) {
				prefijo = linea.substring(0, iSep + SEPARADOR_PREFIJO.length()); // incluye "]: "
				cuerpo = linea.substring(iSep + SEPARADOR_PREFIJO.length());
			}

			// 2) Decidir si es traza (tanto con como sin prefijo)

			// Eliminar bloques tipo [Clase:metodo:linea]: antes de decidir
			String cuerpoSinBloques = BLOQUE_CLASE_METODO_LINEA.matcher(cuerpo).replaceAll("");

			if (esLineaTraza(cuerpoSinBloques)) {

				// 3) Limpiar SOLO el cuerpo de la traza
				String limpio = limpiarCuerpoTraza(cuerpo);

				// 4) Para TRazas: quitar SIEMPRE el prefijo del logger (requisito del usuario)
				// Devolvemos únicamente la traza normalizada.
				out.append(limpio).append("\n");
			} else {
				// 5) Línea normal -> NO tocar (conserva niveles/hilo/hora)
				out.append(linea).append("\n");
			}
		}

		return out.toString();
	}

	/** Detecta cabeceras de excepción tipo "Caused by:" o "Suppressed:" */
	private static final Pattern CABECERA_EXCEPCION = Pattern
			.compile("^(Caused by:|Suppressed:|Exception in thread|[\\w.$]+(?:Exception|Error):).*");

	/**
	 * Regla de detección de traza: - Comienza (tras prefijo) con "at " permitiendo
	 * tabs/espacios. - O, tras quitar bloques [Clase:metodo:linea]: contiene "at ".
	 * - O encaja con patrones típicos de transformadores.
	 */
	private boolean esLineaTraza(String cuerpo) {
		if (cuerpo == null || cuerpo.isEmpty())
			return false;

		String sinBloques = BLOQUE_CLASE_METODO_LINEA.matcher(cuerpo).replaceAll("");

		// 1) at ...
		if (AT_CON_TABS.matcher(sinBloques).find())
			return true;

		// 2) Cabeceras de excepción
		if (CABECERA_EXCEPCION.matcher(sinBloques).matches())
			return true;

		// 3) Heurísticas transformadores
		if (sinBloques.contains(" at TRANSFORMER/") || sinBloques.contains(" at mixin/")
				|| sinBloques.contains(" at MODULE/"))
			return true;

		if ((sinBloques.startsWith("TRANSFORMER/") || sinBloques.startsWith("mixin/")
				|| sinBloques.startsWith("MODULE/")) && sinBloques.contains(".java:") && sinBloques.contains("(")
				&& sinBloques.contains(")"))
			return true;

		return false;
	}

	/**
	 * Limpia y normaliza SOLO el CUERPO de la traza: - Quita bloques
	 * [Clase:metodo:linea]: - Elimina rutas de transformadores previas a
	 * "paquete.Clase.metodo(...)" - Normaliza indentación: " at ..." - Quita ": "
	 * inicial residual si aparece
	 */
	private String limpiarCuerpoTraza(String cuerpo) {
		String trabajado = cuerpo;

		// a) Quitar bloques "[Clase:metodo:linea]: "
		trabajado = BLOQUE_CLASE_METODO_LINEA.matcher(trabajado).replaceAll("");

		// b) Quitar rutas de transformador delante del nombre de clase (si existen)
		Matcher mTrans = AT_CON_TRANSFORMADOR.matcher(trabajado);
		if (mTrans.matches()) {
			trabajado = mTrans.group(1) + "at " + mTrans.group(2) + mTrans.group(3);
		}

		// c) Normalizar "at ..." -> " at ..."
		if (AT_CON_TABS.matcher(trabajado).find()) {
			trabajado = "  at " + trabajado.replaceFirst("^[\\t ]*at\\s+", "");
		} else if (trabajado.startsWith("at ")) {
			trabajado = "  " + trabajado;
		}

		// d) Quitar ": " inicial residual
		trabajado = DOS_PUNTOS_INICIALES.matcher(trabajado).replaceFirst("");

		return trabajado;
	}

	@Override
	public boolean predicado(Path archivo) {
		// Solo archivos latest.log
		return archivo != null && archivo.toString().endsWith("latest.log")
				|| archivo.toString().endsWith("cd_launcherlog");
	}
}
