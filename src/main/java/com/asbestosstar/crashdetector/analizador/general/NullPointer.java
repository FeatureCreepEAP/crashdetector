package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Verificación especializada para detectar y resumir todas las
 * {@code NullPointerException} aparecidas en la consola.
 * <p>
 * - Analiza tanto el texto crudo de consola como los trazos ya extraídos por
 * {@link VerificacionDeStackTrace} para asegurar que no se escape ningún caso.
 * - Para cada NPE identifica (si es posible) el método y el objeto nulo
 * implicado, y añade un posible «origen» (JAR, modid o clase) usando la
 * información que {@code VerificacionDeStackTrace} ya depuró —por lo que no se
 * re‑implementan sus listas de exclusión. - La salida es un bloque HTML con
 * lista de errores, pensado para mostrarse en la UI del Crash Detector.
 */
public class NullPointer implements Verificaciones {

	/* ------------------ Expresiones Regulares ------------------ */

	/** Encabezado típico: java.lang.NullPointerException: <texto opcional> */
	private static final Pattern CABECERA_NPE = Pattern.compile("java\\.lang\\.NullPointerException(?::\\s*)?(.*)",
			Pattern.CASE_INSENSITIVE);

	/** Formatos «Cannot invoke \"X\" because the return value of \"Y\" es nulo» */
	private static final Pattern FORMATO_CANNOT = Pattern
			.compile("Cannot\\s+(invoke|read|assign)[^\"]*\"([^\"]+)\"[^\"]*\"([^\"]+)\"");

	private static final String NL = Verificaciones.nl;

	/* ------------------ Estado de la verificación ------------------ */

	private final Set<String> errores = new HashSet<>();
	private boolean activado = false;

	/* ------------------ Lógica principal ------------------ */

	@Override
	public void verificar(Consola consola) {
		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

		/* 1) Reunir todas las líneas que puedan contener una NPE */
		List<String> lineasAInspeccionar = new ArrayList<>();
		// Texto completo de consola
		for (String l : consola.contenido_verificar.split(NL))
			lineasAInspeccionar.add(l);
		// Trazos detectados (no fatales y fatales)
		VerificacionDeStackTrace.obtenerTraces(consola.contenido_verificar)
				.forEach(t -> añadirLineas(t, lineasAInspeccionar));
		VerificacionDeStackTrace.obtenerTracesFatal(consola.contenido_verificar)
				.forEach(t -> añadirLineas(t, lineasAInspeccionar));

		/* 2) Analizar cada línea */
		for (String linea : lineasAInspeccionar) {
			String metodo = "método desconocido";
			String objeto = "objeto";

			Matcher mCannot = FORMATO_CANNOT.matcher(linea);
			Matcher mCabecera = CABECERA_NPE.matcher(linea);

			if (mCannot.find()) {
				// «Cannot invoke \"X\" because the return value of \"Y\"»
				metodo = mCannot.group(2);
				objeto = mCannot.group(3);
			} else if (mCabecera.find() && !mCabecera.group(1).trim().replace(" ", "").isEmpty()) {
				// Encabezado con información adicional tras los dos puntos
				metodo = mCabecera.group(1).trim();
			} else if (!linea.contains("NullPointerException")) {
				// No es una línea relevante
				continue;
			}

			// Buscar la mejor pista de origen usando la info depurada por VdST
			String origen = detectarOrigen(vdst);

			String mensaje = MonitorDePID.idioma.null_pointer_error(metodo, objeto);
			if (!origen.isEmpty())
				mensaje += " (" + origen + ")";

			errores.add(mensaje);
			activado = true;
		}
	}

	/* ------------------ Métodos auxiliares ------------------ */

	private static void añadirLineas(String bloque, List<String> destino) {
		for (String l : bloque.split(NL))
			destino.add(l);
	}

	/**
	 * Determina un posible origen del fallo siguiendo esta prioridad:
	 * <ol>
	 * <li>Primer JAR no filtrado en {@link VerificacionDeStackTrace#jars}</li>
	 * <li>Primer modid en {@link VerificacionDeStackTrace#modids}</li>
	 * <li>Primera clase del primer paquete en
	 * {@link VerificacionDeStackTrace#packs}</li>
	 * </ol>
	 */
	private static String detectarOrigen(VerificacionDeStackTrace vdst) {
		// 1) JAR
		if (!vdst.jars.isEmpty()) {
			String clave = vdst.jars.keySet().iterator().next();
			int idx = clave.indexOf(MonitorDePID.idioma.nivel());
			return idx == -1 ? clave : clave.substring(0, idx);
		}
		// 2) ModID
		if (!vdst.modids.isEmpty()) {
			return vdst.modids.entrySet().iterator().next().getKey().key0;
		}
		// 3) Clase
		if (!vdst.packs.isEmpty()) {
			return vdst.packs.entrySet().iterator().next().getKey().key0;
		}
		return ""; // Sin pista disponible
	}

	/* ------------------ Interface Verificaciones ------------------ */

	@Override
	public Verificaciones nueva() {
		return new NullPointer();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 50f; // Prioridad alta para NPE
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder("<ul>");
		errores.forEach(e -> sb.append("<li>").append(e).append("</li>"));
		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_null_pointer();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
}
