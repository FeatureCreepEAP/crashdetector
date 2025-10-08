package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.TriMap;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Para el contenido en "at" en Traces. Ahora las listas de jars, modids y
 * paquetes se combinan en una sola lista ordenada por nivel (el nivel más bajo
 * primero). El bloque de configuraciones de llaves "{}" se mantiene separado
 * como antes.
 */
public class ContentoDeTraces implements Verificaciones {

	public boolean activado = false;

	// Conjuntos para evitar duplicados
	public Set<String> todos_identificadores = new HashSet<>(); // incluye jars / modids / paquetes
	public Set<String> todos_sm_configs = new HashSet<>();

	public Map<String, StringBuilder> contento = new HashMap<>();

	private static class Problema {
		String nombre;
		String nivel;
		boolean fatal;
		String enlace; // Nuevo campo para el enlace HTML

		Problema(String nombre, String nivel, boolean fatal, String enlace) {
			this.nombre = nombre;
			this.nivel = nivel;
			this.fatal = fatal;
			this.enlace = enlace;
		}
	}

	private static List<Double> parseNivelNumerico(String texto) {
		List<Double> numeros = new ArrayList<>();
		if (texto == null)
			return numeros;

		Pattern pattern = Pattern.compile("\\b(\\d+[\\.,]?\\d*)\\b");
		Matcher matcher = pattern.matcher(texto);

		while (matcher.find()) {
			String grupo = matcher.group(1).replace(',', '.');
			try {
				numeros.add(Double.parseDouble(grupo));
			} catch (NumberFormatException ignored) {
			}
		}
		return numeros;
	}

	/**
	 * Extrae los numeros [nivel, linea, …] a partir de la sub‑cadena posterior al
	 * ultimo espacio (donde se encuentra «nivelX,Y»). No interpreta comas como
	 * decimales, sino como separador.
	 */
	private static List<Integer> extraerNumerosNivel(String texto) {
		List<Integer> nums = new ArrayList<>();
		if (texto == null)
			return nums;

		int idx = texto.lastIndexOf(' ');
		String segmento = idx == -1 ? texto : texto.substring(idx + 1);

		Matcher m = Pattern.compile("\\d+").matcher(segmento);
		while (m.find()) {
			try {
				nums.add(Integer.parseInt(m.group()));
			} catch (NumberFormatException ignored) {
			}
		}
		return nums;
	}

	private static <T> Comparator<T> compNumerico(java.util.function.Function<T, String> extractor) {
		return Comparator.comparingInt((T e) -> {
			List<Integer> n = extraerNumerosNivel(extractor.apply(e));
			return n.isEmpty() ? Integer.MAX_VALUE : n.get(0);
		}).thenComparingInt(e -> {
			List<Integer> n = extraerNumerosNivel(extractor.apply(e));
			return n.size() > 1 ? n.get(1) : Integer.MAX_VALUE;
		}).thenComparingInt(e -> {
			List<Integer> n = extraerNumerosNivel(extractor.apply(e));
			return n.size() > 2 ? n.get(2) : Integer.MAX_VALUE;
		});
	}

	@Override
	public void verificar(Consola consola) {//TODO, no incluir trazos ocupados
		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;
		StringBuilder constructor = new StringBuilder();
		String mensajeFatal = consola.obtainerMensajeFatalUltimaTrace();
		if (mensajeFatal != null && !mensajeFatal.trim().isEmpty()) {
			constructor.append(nl_html).append("<strong>")
					.append(MonitorDePID.idioma.mensaje_de_trace_fatal_ultima_no_traductado()).append("</strong> ")
					.append(mensajeFatal).append(nl_html);
		}

		String mensajeNormal = consola.obtenerMensajeUltimaTrace();
		if (mensajeNormal != null && !mensajeNormal.trim().isEmpty() && !mensajeNormal.equals(mensajeFatal)) {
			constructor.append(nl_html).append("<strong>")
					.append(MonitorDePID.idioma.mensaje_de_trace_ultima_no_traductado()).append("</strong> ")
					.append(mensajeNormal).append(nl_html);
		}

		List<Problema> problemas = new ArrayList<>();
		Set<String> nombresVistos = new HashSet<>();

		// Procesar jars
		if (!vdst.jars.isEmpty()) {
			for (Entry<String, Boolean> jar : vdst.jars.entrySet()) {
				String[] lvlInfoArr = jar.getKey().split(Pattern.quote(MonitorDePID.idioma.nivel()));
				String nivel = "";
				String enlace = "";
				int numeroLineaConsola = -1;

				if (lvlInfoArr.length > 1) {
					nivel = MonitorDePID.idioma.nivel() + lvlInfoArr[1];
					// Extraer el número de línea de la consola (segundo número después de la coma)
					String[] partesNivel = lvlInfoArr[1].split(",");
					if (partesNivel.length > 1) {
						try {
							numeroLineaConsola = Integer.parseInt(partesNivel[1]);
							enlace = consola.agregarErrorALectador(numeroLineaConsola, this);
						} catch (NumberFormatException ignored) {
						}
					}
				}

				String jarNombre = jar.getKey().split("\\.jar")[0] + ".jar";

				if (nombresVistos.add(jarNombre)) {
					problemas.add(new Problema(jarNombre, nivel, jar.getValue(), enlace));
				}
			}
		}

		// Procesar modids
		if (!vdst.modids.isEmpty()) {
			Buscardor.cargar();
			for (TriMap.TripleKey<String, Integer, Integer> key : vdst.modids.keySet()) {
				String identificador = key.key1; // modid
				int nivel = key.key2; // nivel
				int linea = key.key3; // número de línea en consola
				boolean fatal = vdst.modids.get(identificador, nivel, linea);

				String nivelStr = MonitorDePID.idioma.nivel() + nivel + "," + linea;
				String enlace = consola.agregarErrorALectador(linea, this);

				List<String> jarsMod = Buscardor.obtenerModsConNombre(identificador);
				String nombreMostrar = jarsMod.isEmpty() ? identificador : String.join(", ", jarsMod);

				if (nombresVistos.add(nombreMostrar)) {
					problemas.add(new Problema(nombreMostrar, nivelStr, fatal, enlace));
				}
			}
		}

		// Procesar paquetes
		if (!vdst.packs.isEmpty()) {
			Buscardor.cargar();
			for (TriMap.TripleKey<String, Integer, Integer> key : vdst.packs.keySet()) {
				String identificador = key.key1; // paquete
				int nivel = key.key2; // nivel
				int linea = key.key3; // número de línea en consola
				boolean fatal = vdst.packs.get(identificador, nivel, linea);

				String nivelStr = MonitorDePID.idioma.nivel() + nivel + "," + linea;
				String enlace = consola.agregarErrorALectador(linea, this);

				String rutaPack = obtenerRutaDePaquete(identificador);
				List<String> jarsPack = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(rutaPack));
				String nombreMostrar = jarsPack.isEmpty() ? identificador : String.join(", ", jarsPack);

				if (nombresVistos.add(nombreMostrar)) {
					problemas.add(new Problema(nombreMostrar, nivelStr, fatal, enlace));
				}
			}
		}

		if (!problemas.isEmpty()) {
			activado = true;
			constructor.append(nl_html).append(MonitorDePID.idioma.problematico_jar()) // Reutilizamos el texto
																						// existente
					.append(nl_html).append("<ul>");

			problemas.sort(compNumerico((Problema p) -> p.nivel));

			for (Problema p : problemas) {
				constructor.append("<li>");
				if (p.fatal)
					constructor.append(MonitorDePID.idioma.posibilidad_fatal());
				constructor.append(p.nombre).append(" ").append(p.nivel);
				// Agregar el enlace si existe
				if (p.enlace != null && !p.enlace.isEmpty()) {
					constructor.append(" ").append(p.enlace);
				}
				constructor.append("</li>");
			}
			constructor.append("</ul>");
		}

		// Para los braces, ahora usamos TriMap
		TriMap<String, Integer, Integer, Boolean> configsInject = new TriMap<>();
		for (TriMap.TripleKey<String, Integer, Integer> key : vdst.braces.keySet()) {
			String content = key.key1;
			int nivel = key.key2;
			int linea = key.key3;
			boolean fatal = vdst.braces.get(content, nivel, linea);

			// Procesar cada contenido de llaves
			for (String ind : VerificacionDeStackTrace.eliminarDuplicados(content.split(","))) {
				String limpiado = ind.replace("pl:runtimedistcleaner:A", "").replace("re:classloading", "")
						.replace("pl:mixin:APP:", "").replace("re:computing_frames", "")
						.replace("pl:accesstransformer:B", "").replace("pl:mixin:A", "").replace("xf:fml", "")
						.replace("featurecreep", "").replace("re:mixin", "").replace("xf:crashdetector:default", "");

				if (!limpiado.isEmpty()) {
					configsInject.put(limpiado, nivel, linea, fatal);
				}
			}
		}

		TriMap<String, Integer, Integer, Boolean> smConfigsFilt = new TriMap<>();
		if (!configsInject.isEmpty()) {
			activado = true;
			int count = 0;
			for (TriMap.TripleKey<String, Integer, Integer> key : configsInject.keySet()) {
				String nombre = key.key1;
				int nivel = key.key2;
				int linea = key.key3;
				boolean fatal = configsInject.get(nombre, nivel, linea);

				if (!todos_sm_configs.contains(nombre)) {
					todos_sm_configs.add(nombre);
					if (count < 20) {
						smConfigsFilt.put(nombre, nivel, linea, fatal);
						count++;
					}
				}
			}
		}

		if (!smConfigsFilt.isEmpty()) {
			constructor.append(nl_html).append(MonitorDePID.idioma.corchetes_ondulados()).append(nl_html)
					.append("<ul>");

			// Ordenar por nivel y línea
			List<TriMap.TripleKey<String, Integer, Integer>> sortedKeys = new ArrayList<>(smConfigsFilt.keySet());
			sortedKeys.sort(Comparator.comparingInt((TriMap.TripleKey<String, Integer, Integer> key) -> key.key2)
					.thenComparingInt(key -> key.key3));

			for (TriMap.TripleKey<String, Integer, Integer> key : sortedKeys) {
				String nombre = key.key1;
				int nivel = key.key2;
				int linea = key.key3;
				boolean fatal = smConfigsFilt.get(nombre, nivel, linea);
				String enlace = consola.agregarErrorALectador(linea, this);

				String cleanConf = nombre.split("\\.json")[0].replace(".mixins", "").replace(".mixin", "")
						.replace("mixins.", "").replace("mixin.", "");

				constructor.append("<li>");
				if (fatal) {
					constructor.append(MonitorDePID.idioma.posibilidad_fatal());
				}
				constructor.append(cleanConf).append(" ").append(MonitorDePID.idioma.nivel()).append(nivel).append(",")
						.append(linea);
				// Agregar el enlace
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
		return new ContentoDeTraces();
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
		for (Map.Entry<String, StringBuilder> entry : contento.entrySet()) {
			String titulo = "<span style='color: #" + Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas()
					+ "; font-weight: bold;'>";
			constructor.append(titulo).append(entry.getKey()).append("</span>");
			constructor.append(entry.getValue().toString());
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
	private static String obtenerRutaDePaquete(String input) {
		if (input == null || input.trim().isEmpty()) {
			return "";
		}
		String antesDelParentesis = input.split("\\(", 2)[0].trim();
		antesDelParentesis = antesDelParentesis.replaceAll("\\$[^\\.]*", "");
		int ultimoIndicePunto = antesDelParentesis.lastIndexOf('.');
		if (ultimoIndicePunto <= 0) {
			return "";
		}
		String nombreCompletoClase = antesDelParentesis.substring(0, ultimoIndicePunto);
		String[] partesDelPaquete = nombreCompletoClase.split("\\.");
		StringBuilder rutaDelPaquete = new StringBuilder();
		for (int i = 0; i < partesDelPaquete.length; i++) {
			if (i > 0) {
				rutaDelPaquete.append("/");
			}
			rutaDelPaquete.append(partesDelPaquete[i]);
		}
		return rutaDelPaquete.toString();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "contenido_de_trazos";
	}
	
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
}