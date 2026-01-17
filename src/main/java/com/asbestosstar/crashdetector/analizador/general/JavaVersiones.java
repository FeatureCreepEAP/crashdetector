package com.asbestosstar.crashdetector.analizador.general;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class JavaVersiones implements Verificaciones {

	private boolean activado = false;
	private final Set<String> mensajes = new HashSet<>();
	// Nuevo: para almacenar la clase específica con problema de versión
	private String claseConProblema = null;
	// Nuevo: patrón para detectar errores de versión de clase específica
	private final Pattern patronVersionClase = Pattern.compile(
			"UnsupportedClassVersionError:\\s*([a-zA-Z0-9_/]+)\\s+has been compiled by a more recent version of the Java Runtime \\(class file version (\\d+)\\.0\\), this version of the Java Runtime only recognizes class file versions up to (\\d+)\\.0");

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Buscar errores de versión de clase específica
		Matcher matcher = patronVersionClase.matcher(contenidoConsola);
		while (matcher.find()) {
			claseConProblema = matcher.group(1).replace("/", ".");
			String versionCompilada = matcher.group(2);
			String versionActual = matcher.group(3);

			// Determinar qué versión de Java se necesita
			String versionJavaNecesaria = determinarVersionJava(versionCompilada);
			mensajes.add(MonitorDePID.idioma.javaObsoleta() + " JVM: " + versionJavaNecesaria);
			activado = true;
		}

		// Verificación de versión Java 22 no soportada
		if (contenidoConsola.contains("Unsupported class file major version")) {
			if (contenidoConsola.contains("--fml.forgeVersion, 4")
					|| contenidoConsola.contains("--fml.forgeVersion, 3")) {
				if (contenidoConsola.contains("java version 2") && !contenidoConsola.contains("java version 20")
						&& !contenidoConsola.contains("java version 21")) {
					mensajes.add(MonitorDePID.idioma.java22());
					activado = true;
				}
			}
		}

		// Verificación de versión Java obsoleta (genérica)
		if (contenidoConsola.contains("has been compiled by a more recent version of the Java Runtime")
				&& claseConProblema == null) {
			mensajes.add(MonitorDePID.idioma.javaObsoleta());
			activado = true;
		}

		// Resto de las verificaciones existentes
		// Verificación de incompatibilidad con Java 8
		if (contenidoConsola.contains(
				"class jdk.internal.loader.ClassLoaders$AppClassLoader cannot be cast to class java.net.URLClassLoader")) {
			mensajes.add(MonitorDePID.idioma.errorCompatibilidadJava8());
			activado = true;
		}

		// Verificación de Java 9+ no soportado
		if (contenidoConsola.contains(
				"java.base/jdk.internal.loader.ClassLoaders$AppClassLoader cannot be cast to java.base/java.net.URLClassLoader")) {
			mensajes.add(MonitorDePID.idioma.errorJava9NoSoportado());
			activado = true;
		}

		// Verificación de requerimiento Java 8
		if (contenidoConsola.contains("net/minecraft/client/main/Main : Unsupported major.minor version 52.0")) {
			mensajes.add(MonitorDePID.idioma.errorJava8Requerido());
			activado = true;
		}

		// https://github.com/HMCL-dev/HMCL/blob/main/HMCLCore/src/main/java/org/jackhuang/hmcl/game/CrashReportAnalyzer.java
		if (contenidoConsola.contains("Open J9 is not supported") || contenidoConsola.contains("OpenJ9 is incompatible")
				|| contenidoConsola.contains(".J9VMInternals")) {
			mensajes.add(MonitorDePID.idioma.openJ9NoSoportado());
			activado = true;
		}

		// Verificación de necesidad de JDK 11
		if (contenidoConsola.contains(
				"no such method: sun.misc.Unsafe.defineAnonymousClass(Class,byte[],Object[])Class/invokeVirtual")
				|| contenidoConsola.contains(
						"java.lang.IllegalArgumentException: The requested compatibility level JAVA_11 could not be set. Level is not supported by the active JRE or ASM version")) {
			mensajes.add(MonitorDePID.idioma.necesitasJDK11());
			activado = true;
		}
	}

	// Nuevo: método para determinar la versión de Java a partir del número de clase
	private String determinarVersionJava(String versionClase) {
		int version = Integer.parseInt(versionClase);
		switch (version) {
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
		default:
			if (version < 50)
				return "1.6 o anterior";
			else if (version == 50)
				return "1.6";
			else if (version == 51)
				return "1.7";
			else if (version == 52)
				return "1.8";
			else if (version == 53)
				return "9";
			else if (version == 54)
				return "10";
			else if (version == 55)
				return "11";
			else if (version == 56)
				return "12";
			else if (version == 57)
				return "13";
			else if (version == 58)
				return "14";
			else if (version == 59)
				return "15";
			else if (version == 60)
				return "16";
			else
				return "desconocida (" + version + ")";
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
		for (String msg : mensajes) {
			html.append("<li>").append(msg).append("</li>");
		}

		// Nuevo: agregar información sobre la clase específica si se detectó
		if (claseConProblema != null && !claseConProblema.isEmpty()) {
			html.append("<li><b>").append("Clase").append(":</b> ").append(claseConProblema).append("</li>");
		}

		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_java_versiones();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionParaJavaInstallar())

				// Botón para Red Hat OpenJDK (Windows/x86_x64 + Linux/x86_x64 + SRC)
				.agregarBoton("Java Windows-x86_x64+Linux-x86_x64+SRC", (bool) -> {
					abrirEnNavegador("https://developers.redhat.com/products/openjdk/download");
				})

				// Botón para OpenLogic (Mac Intel)
				.agregarBoton("Java Mac Intel", (bool) -> {
					abrirEnNavegador("https://www.openlogic.com/openjdk-downloads");
				})

				// Botón para JDK PPC Mac (GitHub)
				.agregarBoton("Java Mac PPC", (bool) -> {
					abrirEnNavegador("https://github.com/nilsvanvelzen/mac_ppc_openjdk8u60");
				})

				// Botón para Oracle Java Downloads
				.agregarBoton("Java Sun Microsystems", (bool) -> {
					abrirEnNavegador("https://www.oracle.com/java/technologies/downloads/");
				})

				// Botón para Tribblix JDK (Solaris/Illumos)
				.agregarBoton("Java 15+ Solaris/Iluminos", (bool) -> {
					abrirEnNavegador("https://pkgs.tribblix.org/openjdk/");
				}).construir();
	}

	/**
	 * Abre una URL en el navegador predeterminado del sistema
	 * 
	 * @param url La dirección web a abrir
	 */
	private void abrirEnNavegador(String url) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.BROWSE)) {
					desktop.browse(new URI(url));
				}
			}
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
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
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}