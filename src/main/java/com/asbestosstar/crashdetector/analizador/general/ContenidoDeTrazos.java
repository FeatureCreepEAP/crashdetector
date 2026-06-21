package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.LineaTrazo;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Renderiza contenido basado en "at ..." de los stacktraces.
 *
 * Reglas: - No mostrar trazos ocupados por otras verificaciones (ocupaTrazo). -
 * No duplicar trazos que aparecen en mas de 1 log: se muestran solo una vez
 * (global). - No duplicar items dentro del mismo log. - CFR siempre basado en
 * la clase de la linea.
 */
public class ContenidoDeTrazos implements Verificaciones {

	public boolean activado = false;

	// Dedupe global de items mostrados (persistente entre consolas)
	// Clave: (origen_normalizado | clase | nivel | lineaConsola)
	public final Set<String> todos_identificadores = new HashSet<>();

	public final Set<String> todos_sm_configs = new HashSet<>();
	public final Map<String, StringBuilder> contento = new HashMap<>();

	private static final int MULTIPLICADOR_HILOS = 2;

	// Dedupe global de lineas de trace (persistente entre consolas)
	// Si la misma linea (clase+linea) aparece en multiples logs, solo se muestra
	// una vez.
	private static final Set<String> TRAZOS_GLOBALES_VISTOS = Collections.synchronizedSet(new HashSet<>());

	/**
	 * Limpia el conjunto de trazos vistos globalmente.
	 */
	public static void reiniciarGlobal() {
		TRAZOS_GLOBALES_VISTOS.clear();
	}

	private static class Problema {
		String nombre; // jar/modid/pack/clase (posiblemente combinado)
		String nivel; // "nivelX,Y"
		boolean fatal;
		String enlace; // enlaceLinea + [CFR]

		Problema(String nombre, String nivel, boolean fatal, String enlace) {
			this.nombre = nombre;
			this.nivel = nivel;
			this.fatal = fatal;
			this.enlace = enlace;
		}
	}

	private static List<Integer> extraerNumerosDeNivel(String texto) {
		List<Integer> numeros = new ArrayList<>();
		if (texto == null)
			return numeros;

		int indice = texto.lastIndexOf(' ');
		String segmento = indice == -1 ? texto : texto.substring(indice + 1);

		// Escaneo manual equivalente a Pattern.compile("\\d+"): toma cada secuencia
		// máxima de dígitos [0-9] y la convierte a entero.
		int n = segmento.length();
		int i = 0;
		while (i < n) {
			char c = segmento.charAt(i);
			if (c >= '0' && c <= '9') {
				int ini = i;
				do {
					i++;
				} while (i < n && segmento.charAt(i) >= '0' && segmento.charAt(i) <= '9');
				try {
					numeros.add(Integer.parseInt(segmento.substring(ini, i)));
				} catch (NumberFormatException ignorado) {
				}
			} else {
				i++;
			}
		}
		return numeros;
	}

	private static <T> Comparator<T> comparadorNumerico(java.util.function.Function<T, String> extractor) {
		return Comparator.comparingInt((T elemento) -> {
			List<Integer> n = extraerNumerosDeNivel(extractor.apply(elemento));
			return n.isEmpty() ? Integer.MAX_VALUE : n.get(0);
		}).thenComparingInt(elemento -> {
			List<Integer> n = extraerNumerosDeNivel(extractor.apply(elemento));
			return n.size() > 1 ? n.get(1) : Integer.MAX_VALUE;
		}).thenComparingInt(elemento -> {
			List<Integer> n = extraerNumerosDeNivel(extractor.apply(elemento));
			return n.size() > 2 ? n.get(2) : Integer.MAX_VALUE;
		});
	}

	private static boolean esTrazoOcupado(Set<Integer> niveles_ocupados, int nivel) {
		return niveles_ocupados != null && !niveles_ocupados.isEmpty()
				&& niveles_ocupados.contains(Integer.valueOf(nivel));
	}

	private static String normalizarOrigen(String origen) {
		if (origen == null)
			return "";
		String t = origen.trim();
		// Para dedupe: jar/modid/pack suelen venir iguales; solo normalizamos espacios.
		// Equivale a replaceAll("\\s+", " "): colapsa cada secuencia de espacios en
		// uno.
		t = colapsarEspacios(t);
		return t;
	}

	private static boolean esEspacioRegex(char c) {
		// Equivale a \s de Java: [ \t\n\x0B\f\r]
		return c == ' ' || c == '\t' || c == '\n' || c == '\u000B' || c == '\f' || c == '\r';
	}

	/**
	 * Equivale a s.replaceAll("\\s+", " "): reemplaza cada secuencia máxima de
	 * caracteres de espacio (\s) por un único ' '. Escaneo manual de un solo paso.
	 */
	private static String colapsarEspacios(String s) {
		int n = s.length();
		StringBuilder sb = new StringBuilder(n);
		boolean enEspacio = false;
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			if (esEspacioRegex(c)) {
				if (!enEspacio) {
					sb.append(' ');
					enEspacio = true;
				}
			} else {
				sb.append(c);
				enEspacio = false;
			}
		}
		return sb.toString();
	}

	private static String claveGlobalLinea(LineaTrazo lt) {
		// Global dedupe por "misma linea" en logs distintos.
		// No incluye 'nivel' para que el mismo frame en otro trace/log se dedupe.
		// Si quieres mas agresivo, deja asi. Si quieres mas laxo, anade lt.origen.
		return (lt.clase == null ? "" : lt.clase) + "|" + lt.lineaConsola;
	}

	private static String claveItemGlobal(LineaTrazo lt) {
		// Global dedupe de item mostrado: evita repetir en diferentes logs
		// Incluye nivel y linea para mantener contexto.
		String origen = normalizarOrigen(lt.origen);
		String clase = lt.clase == null ? "" : lt.clase;
		return origen + "|" + clase + "|" + lt.nivel + "|" + lt.lineaConsola;
	}

	private static String construirEnlaceCfr(String clase) {
		if (clase == null || clase.trim().isEmpty() || !Buscador.hablicar.obtener())
			return "";
		return "<a href=\"cfr://" + clase + "\">[CFR]</a>";
	}

	@Override
	public String[] patronesRapidos() {
		// No necesita activar por línea.
		// Usa vdst.trazos_completos en finalizarArchivo().
		return new String[0];
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		// Simplemente nos activamos para indicar que queremos ser procesados al final
		// del archivo
		// this.activado = true;
	}

	@Override
	public void finalizarArchivo(Consola consola,
			com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo estado) {

		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

		if (vdst == null) {
			return;
		}

		List<TraceInfo> traces = vdst.trazos_completos;

		if (traces == null || traces.isEmpty()) {
			traces = new ArrayList<>();

			if (vdst.nivel_trazo != null) {
				traces.addAll(vdst.nivel_trazo.values());
			}
		}

		if (traces.isEmpty()) {
			return;
		}

		// =====================================================
		// Ya no llamamos ocupaTrazo() aquí.
		// Los trazos ocupados se filtraron antes en VerificacionDeStackTrace.
		// Esta clase solo renderiza los trazos buenos restantes.
		// =====================================================

		Map<String, Problema> mejorPorOrigen = new HashMap<>();

		for (TraceInfo info : traces) {

			if (info == null) {
				continue;
			}

			// Seguridad extra por si algún trace ocupado llegó desde una ruta vieja.
			if (info.ocupado) {
				continue;
			}

			List<LineaTrazo> lineas = info.lineas == null ? Collections.<LineaTrazo>emptyList() : info.lineas;

			for (LineaTrazo lt : lineas) {

				if (lt == null) {
					continue;
				}

				String claveLineaGlobal = claveGlobalLinea(lt);

				if (!TRAZOS_GLOBALES_VISTOS.add(claveLineaGlobal)) {
					continue;
				}

				String origenNorm = normalizarOrigen(lt.origen);

				if (origenNorm.isEmpty()) {
					origenNorm = lt.clase == null ? "" : lt.clase;
				}

				if (origenNorm.isEmpty()) {
					continue;
				}

				if (esModidFalsoGenerado(origenNorm)) {
					continue;
				}

				String textoNivel = MonitorDePID.idioma.nivel() + lt.nivel + "," + lt.lineaConsola;

				String enlaceLinea = consola.agregarErrorALectador(lt.lineaConsola, this);

				String enlaceCfr = construirEnlaceCfr(lt.clase);
				String enlace = enlaceLinea + (enlaceCfr.isEmpty() ? "" : " " + enlaceCfr);

				Problema nuevo = new Problema(origenNorm, textoNivel, lt.fatal, enlace);

				Problema actual = mejorPorOrigen.get(origenNorm);

				if (actual == null) {
					mejorPorOrigen.put(origenNorm, nuevo);
					continue;
				}

				List<Integer> nNuevo = extraerNumerosDeNivel(nuevo.nivel);
				List<Integer> nActual = extraerNumerosDeNivel(actual.nivel);

				int nivelNuevo = nNuevo.isEmpty() ? Integer.MAX_VALUE : nNuevo.get(0);
				int nivelActual = nActual.isEmpty() ? Integer.MAX_VALUE : nActual.get(0);

				if (nivelNuevo < nivelActual) {
					mejorPorOrigen.put(origenNorm, nuevo);
					continue;
				}

				if (nivelNuevo == nivelActual) {
					int lineaNuevo = nNuevo.size() > 1 ? nNuevo.get(1) : Integer.MAX_VALUE;
					int lineaActual = nActual.size() > 1 ? nActual.get(1) : Integer.MAX_VALUE;

					if (lineaNuevo < lineaActual) {
						mejorPorOrigen.put(origenNorm, nuevo);
					}
				}
			}
		}

		if (mejorPorOrigen.isEmpty()) {
			return;
		}

		activado = true;

		List<Problema> problemas = new ArrayList<>(mejorPorOrigen.values());
		problemas.sort(comparadorNumerico((Problema p) -> p.nivel));

		StringBuilder sb = new StringBuilder();

		sb.append(nl_html).append(MonitorDePID.idioma.problematico_jar()).append(nl_html).append("<ul>");

		for (Problema p : problemas) {
			sb.append("<li>");

			if (p.fatal) {
				sb.append(MonitorDePID.idioma.posibilidad_fatal());
			}

			sb.append(p.nombre).append(" ").append(p.nivel);

			if (p.enlace != null && !p.enlace.isEmpty()) {
				sb.append(" ").append(p.enlace);
			}

			sb.append("</li>");
		}

		sb.append("</ul>");

		String nombreArchivo = consola.archivo != null ? consola.archivo.getFileName().toString() : "unknown";

		contento.put(nombreArchivo, new StringBuilder(sb.toString().trim()));
	}

	public boolean quiereAnalizarLineas() {
		return false;
	}

	private static boolean esModidFalsoGenerado(String origen) {
		if (origen == null)
			return false;
		// Equivale a "^[a-z]{3}\\d{3}$": exactamente 3 letras minúsculas seguidas de 3
		// dígitos.
		if (origen.length() != 6)
			return false;
		for (int i = 0; i < 3; i++) {
			char c = origen.charAt(i);
			if (c < 'a' || c > 'z')
				return false;
		}
		for (int i = 3; i < 6; i++) {
			char c = origen.charAt(i);
			if (c < '0' || c > '9')
				return false;
		}
		return true;
	}

	@Override
	public Verificaciones nueva() {
		return new ContenidoDeTrazos();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 10;
	}

	@Override
	public String mensaje() {
		StringBuilder constructor = new StringBuilder();
		constructor.append(MonitorDePID.idioma.infoDeTrazos()).append(nl_html);
		for (Map.Entry<String, StringBuilder> entrada : contento.entrySet()) {
			String titulo = "<span style='color: #" + Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas()
					+ "; font-weight: bold;'>";
			constructor.append(titulo).append(entrada.getKey()).append("</span>");
			constructor.append(entrada.getValue().toString());
		}
		return constructor.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_contenido_de_stacktrace();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "contenido_de_trazos";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}
