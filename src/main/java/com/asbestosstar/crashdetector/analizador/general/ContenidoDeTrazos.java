package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.LineaTrazo;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;
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

		Matcher emparejador = Pattern.compile("\\d+").matcher(segmento);
		while (emparejador.find()) {
			try {
				numeros.add(Integer.parseInt(emparejador.group()));
			} catch (NumberFormatException ignorado) {
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

	/**
	 * Precalcula, de forma multi-hilo, todos los niveles de trazos ocupados usando
	 * ocupaTrazo(TraceInfo) de las otras verificaciones activadas.
	 */
	private static Set<Integer> calcularNivelesOcupados(VerificacionDeStackTrace vdst) {
		if (vdst == null || vdst.nivel_trazo == null || vdst.nivel_trazo.isEmpty() || MonitorDePID.analizador == null
				|| MonitorDePID.analizador.verificaciones_activados == null
				|| MonitorDePID.analizador.verificaciones_activados.isEmpty()) {
			return Collections.emptySet();
		}

		List<Map.Entry<Integer, TraceInfo>> entradas_nivel_trazo = new ArrayList<>(vdst.nivel_trazo.entrySet());
		final List<Verificaciones> verificaciones_activadas = new ArrayList<>(
				MonitorDePID.analizador.verificaciones_activados);

		final int total = entradas_nivel_trazo.size();
		int numero_hilos = Runtime.getRuntime().availableProcessors() * MULTIPLICADOR_HILOS;
		if (numero_hilos < 1)
			numero_hilos = 1;
		if (numero_hilos > total)
			numero_hilos = total;

		if (numero_hilos <= 1) {
			Set<Integer> niveles_ocupados = new HashSet<>();
			for (Map.Entry<Integer, TraceInfo> entrada : entradas_nivel_trazo) {
				Integer nivel = entrada.getKey();
				TraceInfo trazo = entrada.getValue();
				for (Verificaciones verif : verificaciones_activadas) {
					try {
						if (verif.ocupaTrazo(trazo)) {
							niveles_ocupados.add(nivel);
							break;
						}
					} catch (Throwable ignorado) {
					}
				}
			}
			return niveles_ocupados;
		}

		int tamano_bloque = (total + numero_hilos - 1) / numero_hilos;
		ExecutorService servicio_ejecucion = Executors.newFixedThreadPool(numero_hilos);
		List<Future<Set<Integer>>> futuros = new ArrayList<>();

		for (int indice_hilo = 0; indice_hilo < numero_hilos; indice_hilo++) {
			final int desde = indice_hilo * tamano_bloque;
			if (desde >= total)
				break;
			final int hasta = Math.min(total, desde + tamano_bloque);

			Callable<Set<Integer>> tarea = () -> {
				Set<Integer> niveles_locales = new HashSet<>();
				for (int i = desde; i < hasta; i++) {
					Map.Entry<Integer, TraceInfo> entrada = entradas_nivel_trazo.get(i);
					Integer nivel = entrada.getKey();
					TraceInfo trazo = entrada.getValue();
					for (Verificaciones verif : verificaciones_activadas) {
						try {
							if (verif.ocupaTrazo(trazo)) {
								niveles_locales.add(nivel);
								break;
							}
						} catch (Throwable ignorado) {
						}
					}
				}
				return niveles_locales;
			};

			futuros.add(servicio_ejecucion.submit(tarea));
		}

		Set<Integer> niveles_ocupados = new HashSet<>();
		for (Future<Set<Integer>> futuro : futuros) {
			try {
				niveles_ocupados.addAll(futuro.get());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				CrashDetectorLogger.logException(e);
			}
		}

		servicio_ejecucion.shutdown();
		return niveles_ocupados;
	}

	private static boolean esTrazoOcupado(Set<Integer> niveles_ocupados, int nivel) {
		return niveles_ocupados != null && !niveles_ocupados.isEmpty()
				&& niveles_ocupados.contains(Integer.valueOf(nivel));
	}

	private static String normalizarOrigen(String origen) {
		if (origen == null)
			return "";
		String t = origen.trim();
		// Para dedupe: jar/modid/pack suelen venir iguales; solo normalizamos espacios
		t = t.replaceAll("\\s+", " ");
		return t;
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
		if (clase == null || clase.trim().isEmpty() || !Buscardor.hablicar.obtener())
			return "";
		return "<a href=\"cfr://" + clase + "\">[CFR]</a>";
	}

	@Override
	public void verificar(Consola consola) {

		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;
		if (vdst == null)
			return;

		// Niveles ocupados por otras verificaciones
		final Set<Integer> niveles_ocupados = calcularNivelesOcupados(vdst);

		// Obtener trazos (preferir contenedor nuevo)
		List<TraceInfo> traces = vdst.trazos_completos;
		if (traces == null || traces.isEmpty()) {
			traces = new ArrayList<>();
			if (vdst.nivel_trazo != null) {
				traces.addAll(vdst.nivel_trazo.values());
			}
		}

		if (traces.isEmpty())
			return;

		// =====================================================
		// AGRUPAR POR ORIGEN Y QUEDARSE CON EL MAS CRITICO
		// =====================================================

		Map<String, Problema> mejorPorOrigen = new HashMap<>();

		for (TraceInfo info : traces) {

			if (info == null)
				continue;

			int nivelTrace = info.nivel > 0 ? info.nivel : -1;
			if (nivelTrace > 0 && esTrazoOcupado(niveles_ocupados, nivelTrace)) {
				continue;
			}

			List<LineaTrazo> lineas = (info.lineas == null) ? Collections.<LineaTrazo>emptyList() : info.lineas;

			for (LineaTrazo lt : lineas) {

				if (lt == null)
					continue;

				if (lt.nivel > 0 && esTrazoOcupado(niveles_ocupados, lt.nivel)) {
					continue;
				}

				// Dedupe global por linea (cross-log)
				String claveLineaGlobal = claveGlobalLinea(lt);
				if (!TRAZOS_GLOBALES_VISTOS.add(claveLineaGlobal)) {
					continue;
				}

				// Normalizar origen
				String origenNorm = normalizarOrigen(lt.origen);
				if (origenNorm.isEmpty()) {
					origenNorm = (lt.clase == null ? "" : lt.clase);
				}
				if (origenNorm.isEmpty())
					continue;

				// === FILTRO: modids generados tipo dgd000 ===
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

				// Comparar criticidad
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

		if (mejorPorOrigen.isEmpty())
			return;

		activado = true;

		// Convertir a lista y ordenar
		List<Problema> problemas = new ArrayList<>(mejorPorOrigen.values());
		problemas.sort(comparadorNumerico((Problema p) -> p.nivel));

		// =====================================================
		// RENDER HTML
		// =====================================================

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

		contento.put(consola.archivo.getFileName().toString(), new StringBuilder(sb.toString().trim()));
	}

	private static boolean esModidFalsoGenerado(String origen) {
		if (origen == null)
			return false;
		return origen.matches("^[a-z]{3}\\d{3}$");
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}
}
