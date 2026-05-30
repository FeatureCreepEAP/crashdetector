package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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

	// =========================
	// Verificación global barata
	// =========================
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty())
			return;

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_UNSUPPORTED_CLASS) || contenido.contains(TEXTO_JAVA22)
				|| contenido.contains(TEXTO_JAVA8)) {
			posibleErrorJava = true;
		}

		if (contenido.contains(TEXTO_PROBLEMATIC_FRAME)
				&& (contenido.contains(TEXTO_LIBJVM_LINUX) || contenido.contains(TEXTO_JVM_WINDOWS))) {
			posibleFrameJavaProblematico = true;
		}
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
				String limpia = linea.trim();

				if (limpia.startsWith("---------------") || limpia.startsWith("Stack:")
						|| limpia.startsWith("Native frames:") || limpia.startsWith("Java frames:")
						|| limpia.startsWith("siginfo:") || limpia.startsWith("Registers:")) {
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
		if (termino == null || termino.trim().isEmpty()) {
			return;
		}

		try {
			List<ArchivoDeMod> encontrados = Buscador.buscarModsConTermino(termino.trim());

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
		return linea.substring(start, fin).replace("/", ".").trim();
	}

	private String determinarVersionJava(String linea) {
		int idx = linea.indexOf("class file version");
		if (idx < 0)
			return "desconocida";
		int start = idx + "class file version".length();
		while (start < linea.length() && !Character.isDigit(linea.charAt(start)))
			start++;
		if (start >= linea.length())
			return "desconocida";
		int end = start;
		while (end < linea.length() && Character.isDigit(linea.charAt(end)))
			end++;
		String versionClase = linea.substring(start, end);
		switch (Integer.parseInt(versionClase)) {
		case 61:
			return "17";
		case 62:
			return "18";
		case 63:
			return "19";
		case 64:
			return "20";
		case 65:
			return "21";
		case 66:
			return "22";
		case 52:
			return "1.8";
		case 51:
			return "1.7";
		case 50:
			return "1.6";
		default:
			return "desconocida (" + versionClase + ")";
		}
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
			html.append("<li><b>Clase:</b> ").append(claseConProblema);

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