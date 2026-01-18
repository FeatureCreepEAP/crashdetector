package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Collections;
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
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.mapas.TriMap;

/**
 * Para el contenido en "at" en Traces. Ahora las listas de jars, modids y
 * paquetes se combinan en una sola lista ordenada por nivel (el nivel más bajo
 * primero). El bloque de configuraciones de llaves "{}" se mantiene separado
 * como antes.
 */
public class ContenidoDeTrazos implements Verificaciones {

	public boolean activado = false;

	// Conjuntos para evitar duplicados
	public Set<String> todos_identificadores = new HashSet<>(); // incluye jars / modids / paquetes
	public Set<String> todos_sm_configs = new HashSet<>();

	public Map<String, StringBuilder> contento = new HashMap<>();

	private static final int MULTIPLICADOR_HILOS = 2;

	private static class Problema {
		String nombre;
		String nivel;
		boolean fatal;
		String enlace;

		Problema(String nombre, String nivel, boolean fatal, String enlace) {
			this.nombre = nombre;
			this.nivel = nivel;
			this.fatal = fatal;
			this.enlace = enlace;
		}
	}

	private static List<Double> parsearNivelNumerico(String texto) {
		List<Double> numeros = new ArrayList<>();
		if (texto == null)
			return numeros;

		Pattern patron = Pattern.compile("\\b(\\d+[\\.,]?\\d*)\\b");
		Matcher emparejador = patron.matcher(texto);

		while (emparejador.find()) {
			String grupo = emparejador.group(1).replace(',', '.');
			try {
				numeros.add(Double.parseDouble(grupo));
			} catch (NumberFormatException ignorado) {
			}
		}
		return numeros;
	}

	/**
	 * Extrae los números [nivel, línea, …] a partir de la sub-cadena posterior al
	 * último espacio (donde se encuentra «nivelX,Y»). No interpreta comas como
	 * decimales, sino como separador.
	 */
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
	 * ocupaTrazo(TraceInfo) de las otras verificaciones activadas. El resultado es
	 * independiente por cada Consola (usa solo el vdst de esa consola).
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
		if (numero_hilos < 1) {
			numero_hilos = 1;
		}
		if (numero_hilos > total) {
			numero_hilos = total;
		}

		// Versión de un solo hilo (lista pequeña o 1 hilo calculado)
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
			if (desde >= total) {
				break;
			}
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

	/**
	 * Comprueba si el nivel indicado pertenece a un trazo que ya está ocupado por
	 * otra verificación (usando el conjunto precalculado).
	 */
	private static boolean esTrazoOcupado(Set<Integer> niveles_ocupados, int nivel) {
		if (niveles_ocupados == null || niveles_ocupados.isEmpty()) {
			return false;
		}
		return niveles_ocupados.contains(Integer.valueOf(nivel));
	}

	@Override
	public void verificar(Consola consola) {
		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

		// Precálculo por consola de todos los niveles ocupados (multi-hilo)
		final Set<Integer> niveles_ocupados = calcularNivelesOcupados(vdst);

		StringBuilder constructor = new StringBuilder();
		String mensaje_fatal = consola.obtainerMensajeFatalUltimaTrace();
		if (mensaje_fatal != null && !mensaje_fatal.trim().isEmpty()) {
			constructor.append(nl_html).append("<strong>")
					.append(MonitorDePID.idioma.mensaje_de_trace_fatal_ultima_no_traductado()).append("</strong> ")
					.append(mensaje_fatal).append(nl_html);
		}

		String mensaje_normal = consola.obtenerMensajeUltimaTrace();
		if (mensaje_normal != null && !mensaje_normal.trim().isEmpty() && !mensaje_normal.equals(mensaje_fatal)) {
			constructor.append(nl_html).append("<strong>")
					.append(MonitorDePID.idioma.mensaje_de_trace_ultima_no_traductado()).append("</strong> ")
					.append(mensaje_normal).append(nl_html);
		}

		List<Problema> problemas = new ArrayList<>();
		Set<String> nombres_vistos = new HashSet<>();

		// Procesar jars
		if (!vdst.jars.isEmpty()) {
			for (Entry<String, Boolean> jar : vdst.jars.entrySet()) {
				String[] arreglo_nivel = jar.getKey().split(Pattern.quote(MonitorDePID.idioma.nivel()));
				String texto_nivel = "";
				String enlace = "";
				int numero_linea_consola = -1;
				int numero_nivel = -1;

				if (arreglo_nivel.length > 1) {
					texto_nivel = MonitorDePID.idioma.nivel() + arreglo_nivel[1];
					// Extraer nivel y número de línea de la consola (formato "nivel,linea")
					String[] partes_nivel = arreglo_nivel[1].split(",");
					if (partes_nivel.length > 0) {
						try {
							numero_nivel = Integer.parseInt(partes_nivel[0]);
						} catch (NumberFormatException ignorado) {
						}
					}
					if (partes_nivel.length > 1) {
						try {
							numero_linea_consola = Integer.parseInt(partes_nivel[1]);
							enlace = consola.agregarErrorALectador(numero_linea_consola, this);
						} catch (NumberFormatException ignorado) {
						}
					}
				}

				// Si el nivel corresponde a un trazo ocupado, no se muestra este jar
				if (numero_nivel != -1 && esTrazoOcupado(niveles_ocupados, numero_nivel)) {
					continue;
				}

				String nombre_jar = jar.getKey().split("\\.jar")[0] + ".jar";

				if (nombres_vistos.add(nombre_jar)) {
					problemas.add(new Problema(nombre_jar, texto_nivel, jar.getValue(), enlace));
				}
			}
		}

		// Procesar modids
		if (!vdst.modids.isEmpty()) {
			Buscardor.cargar();
			for (TriMap.TripleKey<String, Integer, Integer> llave : vdst.modids.keySet()) {
				String identificador = llave.key1; // modid
				int nivel = llave.key2; // nivel
				int linea = llave.key3; // número de línea en consola
				boolean fatal = vdst.modids.get(identificador, nivel, linea);

				// Saltar si el nivel pertenece a un trazo ocupado
				if (esTrazoOcupado(niveles_ocupados, nivel)) {
					continue;
				}

				String texto_nivel = MonitorDePID.idioma.nivel() + nivel + "," + linea;
				String enlace = consola.agregarErrorALectador(linea, this);

				List<String> jars_mod = Buscardor.obtenerModsConNombre(identificador);
				String nombre_mostrar = jars_mod.isEmpty() ? identificador : String.join(", ", jars_mod);

				if (nombres_vistos.add(nombre_mostrar)) {
					problemas.add(new Problema(nombre_mostrar, texto_nivel, fatal, enlace));
				}
			}
		}

		// Procesar paquetes
		if (!vdst.packs.isEmpty()) {
			Buscardor.cargar();
			for (TriMap.TripleKey<String, Integer, Integer> llave : vdst.packs.keySet()) {
				String identificador = llave.key1; // paquete
				int nivel = llave.key2; // nivel
				int linea = llave.key3; // número de línea en consola
				boolean fatal = vdst.packs.get(identificador, nivel, linea);

				// Saltar si el nivel pertenece a un trazo ocupado
				if (esTrazoOcupado(niveles_ocupados, nivel)) {
					continue;
				}

				String texto_nivel = MonitorDePID.idioma.nivel() + nivel + "," + linea;
				String enlace = consola.agregarErrorALectador(linea, this);

				String ruta_paquete = obtenerRutaDePaquete(identificador);
				List<String> jars_paquete = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(ruta_paquete));
				String nombre_mostrar = jars_paquete.isEmpty() ? identificador : String.join(", ", jars_paquete);

				if (nombres_vistos.add(nombre_mostrar)) {
					problemas.add(new Problema(nombre_mostrar, texto_nivel, fatal, enlace));
				}
			}
		}

		if (!problemas.isEmpty()) {
			activado = true;
			constructor.append(nl_html).append(MonitorDePID.idioma.problematico_jar()).append(nl_html).append("<ul>");

			// Ordenar usando el comparador numérico existente
			problemas.sort(comparadorNumerico((Problema p) -> p.nivel));

			for (Problema p : problemas) {
				constructor.append("<li>");
				if (p.fatal) {
					constructor.append(MonitorDePID.idioma.posibilidad_fatal());
				}
				constructor.append(p.nombre).append(" ").append(p.nivel);
				if (p.enlace != null && !p.enlace.isEmpty()) {
					constructor.append(" ").append(p.enlace);
				}
				constructor.append("</li>");
			}
			constructor.append("</ul>");
		}

		// Procesar configuraciones en llaves "{}"
		TriMap<String, Integer, Integer, Boolean> configs_inyectadas = new TriMap<>();
		for (TriMap.TripleKey<String, Integer, Integer> llave : vdst.braces.keySet()) {
			String contenido = llave.key1;
			int nivel = llave.key2;
			int linea = llave.key3;
			boolean fatal = vdst.braces.get(contenido, nivel, linea);

			for (String ind : VerificacionDeStackTrace.eliminarDuplicados(contenido.split(","))) {
				String limpiado = ind.replace("pl:runtimedistcleaner:A", "").replace("re:classloading", "")
						.replace("pl:mixin:APP:", "").replace("re:computing_frames", "")
						.replace("pl:accesstransformer:B", "").replace("pl:mixin:A", "").replace("xf:fml", "")
						.replace("featurecreep", "").replace("re:mixin", "").replace("xf:crashdetector:default", "");

				// No añadir si el nivel pertenece a un trazo ocupado
				if (!limpiado.isEmpty() && !esTrazoOcupado(niveles_ocupados, nivel)) {
					configs_inyectadas.put(limpiado, nivel, linea, fatal);
				}
			}
		}

		TriMap<String, Integer, Integer, Boolean> configs_filtradas = new TriMap<>();
		if (!configs_inyectadas.isEmpty()) {
			activado = true;
			int contador = 0;
			for (TriMap.TripleKey<String, Integer, Integer> llave : configs_inyectadas.keySet()) {
				String nombre = llave.key1;
				int nivel = llave.key2;
				int linea = llave.key3;
				boolean fatal = configs_inyectadas.get(nombre, nivel, linea);

				if (!todos_sm_configs.contains(nombre)) {
					todos_sm_configs.add(nombre);
					if (contador < 20) {
						configs_filtradas.put(nombre, nivel, linea, fatal);
						contador++;
					}
				}
			}
		}

		if (!configs_filtradas.isEmpty()) {
			constructor.append(nl_html).append(MonitorDePID.idioma.corchetes_ondulados()).append(nl_html)
					.append("<ul>");

			List<TriMap.TripleKey<String, Integer, Integer>> llaves_ordenadas = new ArrayList<>(
					configs_filtradas.keySet());
			llaves_ordenadas
					.sort(Comparator.comparingInt((TriMap.TripleKey<String, Integer, Integer> llave) -> llave.key2)
							.thenComparingInt(llave -> llave.key3));

			for (TriMap.TripleKey<String, Integer, Integer> llave : llaves_ordenadas) {
				String nombre = llave.key1;
				int nivel = llave.key2;
				int linea = llave.key3;
				boolean fatal = configs_filtradas.get(nombre, nivel, linea);
				String enlace = consola.agregarErrorALectador(linea, this);

				String nombre_limpio = nombre.split("\\.json")[0].replace(".mixins", "").replace(".mixin", "")
						.replace("mixins.", "").replace("mixin.", "");

				constructor.append("<li>");
				if (fatal) {
					constructor.append(MonitorDePID.idioma.posibilidad_fatal());
				}
				constructor.append(nombre_limpio).append(" ").append(MonitorDePID.idioma.nivel()).append(nivel)
						.append(",").append(linea);
				if (enlace != null && !enlace.isEmpty()) {
					constructor.append(" ").append(enlace);
				}
				constructor.append("</li>");
			}
			constructor.append("</ul>");
		}

		if (!constructor.toString().isEmpty()) {
			contento.put(consola.archivo.getFileName().toString(), new StringBuilder(constructor.toString().trim()));
		}
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

	/**
	 * Convierte una referencia completa de método a la ruta del paquete.
	 */
	private static String obtenerRutaDePaquete(String entrada) {
		if (entrada == null || entrada.trim().isEmpty()) {
			return "";
		}
		String antes_del_parentesis = entrada.split("\\(", 2)[0].trim();
		antes_del_parentesis = antes_del_parentesis.replaceAll("\\$[^\\.]*", "");
		int ultimo_indice_punto = antes_del_parentesis.lastIndexOf('.');
		if (ultimo_indice_punto <= 0) {
			return "";
		}
		String nombre_completo_clase = antes_del_parentesis.substring(0, ultimo_indice_punto);
		String[] partes_paquete = nombre_completo_clase.split("\\.");
		StringBuilder ruta_paquete = new StringBuilder();
		for (int i = 0; i < partes_paquete.length; i++) {
			if (i > 0) {
				ruta_paquete.append("/");
			}
			ruta_paquete.append(partes_paquete[i]);
		}
		return ruta_paquete.toString();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		return "contenido_de_trazos";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// Esta verificación no ocupa ningún trazo
		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
