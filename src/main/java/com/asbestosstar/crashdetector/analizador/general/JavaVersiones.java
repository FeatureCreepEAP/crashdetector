package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de versión de clase Java: UnsupportedClassVersionError,
 * clases compiladas con versiones más recientes que la JVM utilizada.
 * Optimizado: global barato + verificación por línea.
 */
public class JavaVersiones implements Verificaciones {

	private boolean activado = false;
	private boolean posibleErrorJava = false;

	private final Set<String> mensajes = new HashSet<>();
	private String claseConProblema = null;
	private String enlace = null;

	private static final String TEXTO_UNSUPPORTED_CLASS = "UnsupportedClassVersionError:";
	private static final String TEXTO_JAVA22 = "Unsupported class file major version";
	private static final String TEXTO_JAVA8 = "Unsupported major.minor version 52.0";

	private final List<ArchivoDeMod> modsRelacionados = new ArrayList<>();

	private boolean posibleFrameJavaProblematico = false;
	private boolean dentroDeFrameProblematico = false;

	private static final String TEXTO_PROBLEMATIC_FRAME = "Problematic frame:";
	private static final String TEXTO_LIBJVM_LINUX = "libjvm.so";
	private static final String TEXTO_JVM_WINDOWS = "jvm.dll";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_UNSUPPORTED_CLASS, TEXTO_JAVA22, TEXTO_JAVA8, TEXTO_PROBLEMATIC_FRAME,
				TEXTO_LIBJVM_LINUX, TEXTO_JVM_WINDOWS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneErrorJava(evento.linea)) {
			posibleErrorJava = true;
		}

		if (evento.linea.contains(TEXTO_PROBLEMATIC_FRAME) || evento.linea.contains(TEXTO_LIBJVM_LINUX)
				|| evento.linea.contains(TEXTO_JVM_WINDOWS)) {
			posibleFrameJavaProblematico = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	// =========================
	// Verificación por línea
	// =========================
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || linea.isEmpty() || activado)
			return;

		if (posibleFrameJavaProblematico) {
			if (linea.contains(TEXTO_PROBLEMATIC_FRAME)) {
				dentroDeFrameProblematico = true;
				return;
			}

			if (dentroDeFrameProblematico) {
				if (linea.contains(TEXTO_LIBJVM_LINUX) || linea.contains(TEXTO_JVM_WINDOWS)) {
					mensajes.add(MonitorDePID.idioma.javaProblematica());
					enlace = consola.agregarErrorALectador(numero_de_linea, this);
					activado = true;
					return;
				}

				// In hs_err_pid logs, the actual frame normally appears shortly after:
				// # Problematic frame:
				// # C [libjvm.so+...]
				// If we reach a new section before finding libjvm/jvm.dll, stop tracking.
				if (empiezaConSinEspacios(linea, "---------------") || empiezaConSinEspacios(linea, "Stack:")
						|| empiezaConSinEspacios(linea, "Native frames:")
						|| empiezaConSinEspacios(linea, "Java frames:") || empiezaConSinEspacios(linea, "siginfo:")
						|| empiezaConSinEspacios(linea, "Registers:")) {
					dentroDeFrameProblematico = false;
				}
			}
		}

		if (!posibleErrorJava)
			return;

		if (linea.contains(TEXTO_UNSUPPORTED_CLASS)) {
			// Extraer nombre de clase
			String clase = extraerClase(linea);
			if (clase != null) {
				claseConProblema = clase;

				buscarModsRelacionados();

				mensajes.add(MonitorDePID.idioma.javaObsoleta() + " JVM: " + determinarVersionJava(linea));

				enlace = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
			}
		}

		if (linea.contains(TEXTO_JAVA22)) {
			mensajes.add(MonitorDePID.idioma.java22());
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}

		if (linea.contains(TEXTO_JAVA8)) {
			mensajes.add(MonitorDePID.idioma.errorJava8Requerido());
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	private boolean lineaContieneErrorJava(String linea) {
		return linea.contains(TEXTO_UNSUPPORTED_CLASS) || linea.contains(TEXTO_JAVA22) || linea.contains(TEXTO_JAVA8);
	}

	private void buscarModsRelacionados() {
		try {
			Buscador.cargar();

			modsRelacionados.clear();

			agregarResultados(claseConProblema);
			agregarResultados(claseConProblema.replace('.', '/'));
		} catch (Throwable ignorado) {
		}
	}

	private void agregarResultados(String termino) {
		if (termino == null || sinEspaciosLaterales(termino).isEmpty()) {
			return;
		}

		try {
			List<ArchivoDeMod> encontrados = Buscador.buscarModsConTermino(sinEspaciosLaterales(termino));

			if (encontrados != null) {
				modsRelacionados.addAll(encontrados);
			}
		} catch (Throwable ignorado) {
		}
	}

	private String formatearMods(List<ArchivoDeMod> mods) {
		if (mods == null || mods.isEmpty()) {
			return "";
		}

		return mods.stream().map(mod -> "<b>" + Buscador.rutaParaPublicar(mod.ubicacion_para_publicar()) + "</b>")
				.distinct().collect(Collectors.joining(", "));
	}

	// =========================
	// Helpers
	// =========================
	private String extraerClase(String linea) {
		if (linea == null)
			return null;

		int start = linea.indexOf(TEXTO_UNSUPPORTED_CLASS);
		if (start < 0)
			return null;

		start += TEXTO_UNSUPPORTED_CLASS.length();

		while (start < linea.length() && Character.isWhitespace(linea.charAt(start)))
			start++;

		if (start >= linea.length())
			return null;

		int fin = linea.indexOf(" has been compiled", start);
		if (fin < 0)
			fin = linea.length();

		return sinEspaciosLaterales(linea.substring(start, fin).replace("/", "."));
	}

	private String determinarVersionJava(String linea) {
		int idx = linea.indexOf("class file version");
		if (idx < 0)
			return MonitorDePID.idioma.desconocida();

		int start = idx + "class file version".length();

		while (start < linea.length() && !Character.isDigit(linea.charAt(start)))
			start++;

		if (start >= linea.length())
			return MonitorDePID.idioma.desconocida();

		int end = start;
		while (end < linea.length() && Character.isDigit(linea.charAt(end)))
			end++;

		String versionClase = linea.substring(start, end);

		try {
			int versionNum = Integer.parseInt(versionClase);

			// Control de versiones antiguas (Mapeo manual para el formato "1.x")
			switch (versionNum) {
			case 52:
				return "1.8";
			case 51:
				return "1.7";
			case 50:
				return "1.6";
			}

			// Fórmula para Java 9 (versión de clase 53) en adelante.
			// Restando 44 obtenemos el número de Java correcto de forma dinámica.
			// Ejemplos:
			// Versión 65 - 44 = Java 21
			// Versión 74 - 44 = Java 30
			if (versionNum >= 53) {
				return String.valueOf(versionNum - 44);
			}
		} catch (NumberFormatException e) {
			return MonitorDePID.idioma.desconocida() + " (" + versionClase + ")";
		}

		return MonitorDePID.idioma.desconocida() + " (" + versionClase + ")";
	}

	private boolean empiezaConSinEspacios(String texto, String prefijo) {
		if (texto == null || prefijo == null)
			return false;

		int inicio = 0;

		while (inicio < texto.length() && Character.isWhitespace(texto.charAt(inicio))) {
			inicio++;
		}

		if (texto.length() - inicio < prefijo.length())
			return false;

		return texto.regionMatches(inicio, prefijo, 0, prefijo.length());
	}

	private String sinEspaciosLaterales(String texto) {
		if (texto == null)
			return "";

		int inicio = 0;
		int fin = texto.length();

		while (inicio < fin && Character.isWhitespace(texto.charAt(inicio))) {
			inicio++;
		}

		while (fin > inicio && Character.isWhitespace(texto.charAt(fin - 1))) {
			fin--;
		}

		return texto.substring(inicio, fin);
	}

	// =========================
	// Métodos estándar de Verificaciones
	// =========================
	@Override
	public Verificaciones nueva() {
		return new JavaVersiones();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 925.0f;
	}

	@Override
	public String mensaje() {
		if (mensajes.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");

		for (String msg : mensajes) {
			html.append("<li>").append(msg).append("</li>");
		}

		if (claseConProblema != null) {
			html.append("<li><b>" + MonitorDePID.idioma.clase() + ":</b> ").append(claseConProblema);

			String mods = formatearMods(modsRelacionados);

			if (!mods.isEmpty()) {
				html.append(" (").append(mods).append(")");
			}

			html.append("</li>");
		}

		html.append("</ul>");

		if (enlace != null) {
			html.append(enlace);
		}

		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_java_versiones();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new QuickFix.Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucionParaJavaInstallar());
		return builder.construir();
	}

	@Override
	public String id() {
		return "java_versiones";
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
	public boolean recomendadoParaCorperata() {
		return true;
	}
}