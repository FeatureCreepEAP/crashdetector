package com.asbestosstar.crashdetector.analizador.general;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class JavaVersiones implements Verificaciones {

	private boolean activado = false;
	private final Set<String> mensajes = new HashSet<>();
	private String claseConProblema = null;

	private static final String TEXTO_UNSUPPORTED_CLASS = "UnsupportedClassVersionError:";
	private static final String TEXTO_JAVA22 = "Unsupported class file major version";
	private static final String TEXTO_JAVA8 = "Unsupported major.minor version 52.0";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		// Buscar UnsupportedClassVersionError sin regex
		int idx = contenido.indexOf(TEXTO_UNSUPPORTED_CLASS);
		while (idx >= 0) {
			int finLinea = contenido.indexOf('\n', idx);
			if (finLinea < 0)
				finLinea = contenido.length();
			String linea = contenido.substring(idx, finLinea);

			// Extraer nombre de clase
			String clase = extraerClase(linea);
			if (clase != null) {
				claseConProblema = clase;
				mensajes.add(MonitorDePID.idioma.javaObsoleta() + " JVM: " + determinarVersionJava(linea));
				activado = true;
			}

			idx = contenido.indexOf(TEXTO_UNSUPPORTED_CLASS, finLinea);
		}

		// Java 22 no soportada
		if (contenido.contains(TEXTO_JAVA22)) {
			mensajes.add(MonitorDePID.idioma.java22());
			activado = true;
		}

		// Verificación de Java 8 requerida
		if (contenido.contains(TEXTO_JAVA8)) {
			mensajes.add(MonitorDePID.idioma.errorJava8Requerido());
			activado = true;
		}
	}

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
		// Buscar número de class file version
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
		for (String msg : mensajes)
			html.append("<li>").append(msg).append("</li>");
		if (claseConProblema != null)
			html.append("<li><b>Clase:</b> ").append(claseConProblema).append("</li>");
		html.append("</ul>");
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
		builder.agregarBoton("Java Windows-x86_x64+Linux-x86_x64+SRC",
				(bool) -> abrirEnNavegador("https://developers.redhat.com/products/openjdk/download"));
		builder.agregarBoton("Java Mac Intel",
				(bool) -> abrirEnNavegador("https://www.openlogic.com/openjdk-downloads"));
		builder.agregarBoton("Java Mac PPC",
				(bool) -> abrirEnNavegador("https://github.com/nilsvanvelzen/mac_ppc_openjdk8u60"));
		builder.agregarBoton("Java Sun Microsystems",
				(bool) -> abrirEnNavegador("https://www.oracle.com/java/technologies/downloads/"));
		builder.agregarBoton("Java 15+ Solaris/Iluminos",
				(bool) -> abrirEnNavegador("https://pkgs.tribblix.org/openjdk/"));
		return builder.construir();
	}

	private void abrirEnNavegador(String url) {
		try {
			if (java.awt.Desktop.isDesktopSupported()) {
				java.awt.Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Exception e) {
			com.asbestosstar.crashdetector.CrashDetectorLogger.logException(e);
		}
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
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}