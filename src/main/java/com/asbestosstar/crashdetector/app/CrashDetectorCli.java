package com.asbestosstar.crashdetector.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.grepr.BusquedaArchivos;
import com.asbestosstar.crashdetector.app.container.ContainerCli;
import com.asbestosstar.crashdetector.app.ansible.AnsibleCli;

/**
 * Gestiona los argumentos públicos de la aplicación independiente.
 *
 * MonitorDePID conserva únicamente el transporte de entradas hacia su proceso
 * hijo y la inyección final en Consola. La interpretación de comandos, grep,
 * validación de rutas y preparación de texto pertenecen a esta clase.
 */
public final class CrashDetectorCli {

	/**
	 * Procesa los argumentos públicos de CrashDetectorApp.
	 *
	 * @return {@code true} cuando el comando ya terminó o cuando un error exige
	 *         detener el arranque; {@code false} cuando debe continuar el arranque
	 *         normal
	 */
	public static boolean procesarArgumentos(String[] args) {
		if (args == null || args.length == 0) {
			return false;
		}

		String comando = args[0] == null ? "" : args[0].toLowerCase(Locale.ROOT);

		if ("--help".equals(comando) || "-h".equals(comando) || "help".equals(comando)) {
			mostrarAyuda();
			return true;
		}

		if ("grepr".equals(comando) || "fgrepr".equals(comando)) {
			ejecutarGrep(args);
			return true;
		}

		if (AnsibleCli.esComandoAnsible(comando)) {
			/*
			 * Logs y playbooks preparan una entrada y continúan el arranque. Status, ayuda
			 * y errores terminan aquí.
			 */
			return !AnsibleCli.procesar(args);
		}

		if (ContainerCli.esComandoContainer(comando)) {
			/*
			 * Si la captura funciona, continúa el arranque para que MonitorDePID analice el
			 * temporal. Ayuda y errores terminan aquí.
			 */
			return !ContainerCli.procesar(args);
		}

		if (esComandoAnalizarArchivos(comando)) {
			return !configurarArchivosLog(args, 1);
		}

		if (esComandoAnalizarTexto(comando)) {
			return !configurarTextosLog(args, 1);
		}

		return false;
	}

	public static void mostrarAyuda() {
		System.out.println("Uso:");
		System.out.println("  java -jar CrashDetectorApp.jar");
		System.out.println("  java -jar CrashDetectorApp.jar grepr [-i] <regex> [directorio]");
		System.out.println("  java -jar CrashDetectorApp.jar fgrepr [-i] <texto> [directorio]");
		System.out.println("  java -jar CrashDetectorApp.jar analizar <log1> [log2 ...]");
		System.out.println("  java -jar CrashDetectorApp.jar analizar-texto \"<contenido1>\" [\"<contenido2>\" ...]");
		System.out.println("  java -jar CrashDetectorApp.jar container <plataforma> logs <objetivo> [opciones]");
		System.out.println("  java -jar CrashDetectorApp.jar ansible <logs|playbook|status> [opciones]");
		System.out.println();
		System.out.println("Comandos:");
		System.out.println("  grepr          Busca mediante una expresión regular.");
		System.out.println("  fgrepr         Busca texto literal.");
		System.out.println("  analizar       Analiza uno o varios logs indicados mediante sus rutas.");
		System.out.println("  analizar-texto Analiza el contenido real de uno o varios logs recibido por CLI.");
		System.out.println("  container      Obtiene y analiza logs de Kubernetes, OpenShift, Rancher, Docker,");
		System.out.println("                 Podman u Oracle Solaris Zones.");
		System.out.println("  ansible        Recopila logs remotos, ejecuta playbooks explícitos o muestra estado.");
		System.out.println();
		System.out.println("Use 'container --help' para las opciones de cada plataforma.");
		System.out.println("Use 'ansible --help' para recopilación remota y playbooks.");
		System.out.println();
		System.out.println("Opciones de grep:");
		System.out.println("  -i, --ignore-case  Ignora mayúsculas y minúsculas.");
		System.out.println();
		System.out.println("Cada argumento de analizar-texto representa un log independiente.");
		System.out.println("Use comillas para conservar espacios y saltos de línea.");
		System.out.println();
		System.out.println("Alias de analizar: analyse, analyze y logs.");
		System.out.println("Alias de analizar-texto: analyse-text, analyze-text, log-text y logs-text.");
	}

	private static boolean esComandoAnalizarArchivos(String comando) {
		return "analizar".equals(comando) || "analyse".equals(comando) || "analyze".equals(comando)
				|| "logs".equals(comando) || "--analizar".equals(comando) || "--analyse".equals(comando)
				|| "--analyze".equals(comando) || "--logs".equals(comando);
	}

	private static boolean esComandoAnalizarTexto(String comando) {
		return "analizar-texto".equals(comando) || "analizartexto".equals(comando) || "analyse-text".equals(comando)
				|| "analyze-text".equals(comando) || "log-text".equals(comando) || "logs-text".equals(comando)
				|| "--analizar-texto".equals(comando) || "--analyse-text".equals(comando)
				|| "--analyze-text".equals(comando) || "--log-text".equals(comando) || "--logs-text".equals(comando);
	}

	private static void ejecutarGrep(String[] args) {
		boolean usarRegex = "grepr".equalsIgnoreCase(args[0]);
		boolean ignorarMayusculas = false;
		String directorio = Statics.CARPETA_DE_APP.getAbsolutePath();

		int indice = 1;
		while (indice < args.length) {
			String argumento = args[indice];

			if ("-i".equals(argumento) || "--ignore-case".equals(argumento)) {
				ignorarMayusculas = true;
				indice++;
				continue;
			}

			if ("--help".equals(argumento) || "-h".equals(argumento)) {
				mostrarAyuda();
				return;
			}

			if ("--".equals(argumento)) {
				indice++;
			}
			break;
		}

		if (indice >= args.length) {
			System.err.println("Error: falta la cadena de búsqueda.");
			mostrarAyuda();
			return;
		}

		String cadenaBusqueda = args[indice++];

		if (indice < args.length) {
			directorio = args[indice++];
		}

		if (indice < args.length) {
			System.err.println("Error: se recibieron argumentos adicionales para grep.");
			mostrarAyuda();
			return;
		}

		List<String> resultados = BusquedaArchivos.buscar(directorio, cadenaBusqueda, usarRegex, ignorarMayusculas);

		for (String resultado : resultados) {
			System.out.println(resultado);
		}
	}

	private static boolean configurarArchivosLog(String[] args, int primerIndice) {
		if (primerIndice >= args.length) {
			System.err.println("Error: falta al menos un archivo de log para analizar.");
			mostrarAyuda();
			return false;
		}

		List<String> rutasValidadas = new ArrayList<String>();
		Set<String> rutasSinDuplicados = new LinkedHashSet<String>();

		for (int i = primerIndice; i < args.length; i++) {
			String argumento = args[i];

			if ("--".equals(argumento)) {
				continue;
			}

			if (argumento == null || argumento.trim().isEmpty()) {
				System.err.println("Error: se recibió una ruta de log vacía.");
				return false;
			}

			File archivo;
			try {
				archivo = new File(argumento).getCanonicalFile();
			} catch (IOException e) {
				System.err.println("Error al resolver la ruta del log: " + argumento);
				System.err.println(e.getMessage());
				return false;
			}

			if (!archivo.exists()) {
				System.err.println("Error: el archivo de log no existe: " + archivo.getAbsolutePath());
				return false;
			}

			if (!archivo.isFile()) {
				System.err.println("Error: la ruta no es un archivo: " + archivo.getAbsolutePath());
				return false;
			}

			if (!archivo.canRead()) {
				System.err.println("Error: no se puede leer el archivo de log: " + archivo.getAbsolutePath());
				return false;
			}

			String rutaCanonica = archivo.getAbsolutePath();
			if (rutasSinDuplicados.add(rutaCanonica)) {
				rutasValidadas.add(rutaCanonica);
			}
		}

		if (rutasValidadas.isEmpty()) {
			System.err.println("Error: no se recibió ningún archivo de log válido.");
			return false;
		}

		MonitorDePID.configurarEntradasCLI(rutasValidadas, Collections.<String>emptyList());
		MonitorDePID.forzarIdeMode = true;

		for (String ruta : rutasValidadas) {
			System.out.println("Log preparado para el proceso monitor: " + ruta);
		}

		return true;
	}

	private static boolean configurarTextosLog(String[] args, int primerIndice) {
		if (primerIndice >= args.length) {
			System.err.println("Error: falta el texto del log para analizar.");
			mostrarAyuda();
			return false;
		}

		List<String> textosValidados = new ArrayList<String>();
		Set<String> textosSinDuplicados = new LinkedHashSet<String>();

		for (int i = primerIndice; i < args.length; i++) {
			String texto = args[i];

			if ("--".equals(texto)) {
				continue;
			}

			if (texto == null || texto.trim().isEmpty()) {
				System.err.println("Error: se recibió un texto de log vacío.");
				return false;
			}

			if (textosSinDuplicados.add(texto)) {
				textosValidados.add(texto);
			}
		}

		if (textosValidados.isEmpty()) {
			System.err.println("Error: no se recibió ningún texto de log válido.");
			return false;
		}

		List<String> temporalesCreados = new ArrayList<String>();

		try {
			for (String texto : textosValidados) {
				Path temporal = MonitorDePID.crearArchivoTemporalTextoCLI(texto);
				temporalesCreados.add(temporal.toFile().getCanonicalPath());
			}
		} catch (IOException e) {
			eliminarTemporalesCreados(temporalesCreados);
			System.err.println("Error al preparar el texto del log:");
			System.err.println(e.getMessage());
			return false;
		}

		MonitorDePID.configurarEntradasCLI(Collections.<String>emptyList(), temporalesCreados);
		MonitorDePID.forzarIdeMode = true;

		for (int i = 0; i < temporalesCreados.size(); i++) {
			System.out.println("Texto de log preparado para el proceso monitor: entrada " + (i + 1));
		}

		return true;
	}

	private static void eliminarTemporalesCreados(List<String> rutas) {
		for (String ruta : rutas) {
			try {
				Files.deleteIfExists(new File(ruta).toPath());
			} catch (IOException ignorado) {
				// No ocultamos el error que impidió preparar la entrada CLI.
			}
		}
	}
}
