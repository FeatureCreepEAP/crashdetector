package com.asbestosstar.crashdetector;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.grepr.BusquedaArchivos;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.NoRegistroDeLauncher;

public class MonitorDePID {

	public static final String mensaje_de_registro_lanzer_completo = "Puedes ignorar esta linea, solo es para CrashDetector, este mensaje es siempre en espanol";
	public static Path carpeta = new File("crash_detector/").toPath();
	public static File ArchivoDeCodigoError0 = new File("crash_detector/ArchivoDeCodigoError0");
	public static Path ultimo_mods = carpeta.resolve("ultima_mods");
	//public static Path viajo_ultima_mods = carpeta.resolve("viajo_ultima_mods");
	public static List<Consola> consolas = new ArrayList<Consola>();
	public static Analizador analizador = new Analizador();

	public static String nl = System.lineSeparator();
	public static Idioma idioma = Idioma.detectar();
	public static String local;
	public static String enlace;
	public static long pid;
	public static boolean resultados = false;
	public static boolean tiene_mensaje_de_registro_launcher_completa = false;
	public static boolean consola_de_launcher_inyectado = false;
	public static StringBuilder contenidoInforme;

	/**
	 * Es diferente en el process diferente
	 */
	public static Instant utc = Instant.now();

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("--monitor")) {
			long pid = Long.parseLong(args[1]);
			monitor_proceso(pid);
			return;
		}

		if (args.length > 0 && (args[0].equals("grepr") || args[0].equals("fgrepr"))) {
			boolean useRegex = args[0].equals("grepr");
			boolean caseInsensitive = false;
			String searchString = "";
			String directory = System.getProperty("user.dir");

			int index = 1;
			while (index < args.length) {
				String arg = args[index];
				if (arg.equals("-i")) {
					caseInsensitive = true;
					index++;
				} else if (arg.equals("--help")) {
					mostrarAyudaCLI();
					return;
				} else {
					break;
				}
			}

			if (index >= args.length) {
				System.out.println("Error: Falta cadena de búsqueda");
				mostrarAyudaCLI();
				return;
			}

			searchString = args[index++];
			if (index < args.length) {
				directory = args[index];
			}

			List<String> resultados = BusquedaArchivos.buscar(directory, searchString, useRegex, caseInsensitive);
			for (String res : resultados) {
				System.out.println(res);
			}
			return;
		}

		ArchivoDeCodigoError0.delete();
		File html = new File("crash_detector/pantilla.htm");

		copiarACarpetaDesdeJar("/pantilla.htm", html);

		copiarACarpetaDesdeJar("/imagenes/gura.png", new File("crash_detector/imagenes/gura.png"));
		copiarACarpetaDesdeJar("/imagenes/clio.png", new File("crash_detector/imagenes/clio.png"));
		copiarACarpetaDesdeJar("/imagenes/hamu.png", new File("crash_detector/imagenes/hamu.png"));
		copiarACarpetaDesdeJar("/imagenes/nanashi_mumei.png", new File("crash_detector/imagenes/nanashi_mumei.png"));
		copiarACarpetaDesdeJar("/imagenes/shion.png", new File("crash_detector/imagenes/shion.png"));

		copiarACarpetaDesdeJar("/imagenes/boton_agregar.png", new File("crash_detector/imagenes/boton_agregar.png"));
		copiarACarpetaDesdeJar("/imagenes/boton_compartir.png",
				new File("crash_detector/imagenes/boton_compartir.png"));
		copiarACarpetaDesdeJar("/imagenes/boton_actualizar.png",
				new File("crash_detector/imagenes/boton_actualizar.png"));
		copiarACarpetaDesdeJar("/imagenes/boton_archivos.png", new File("crash_detector/imagenes/boton_archivos.png"));
		copiarACarpetaDesdeJar("/imagenes/boton_config.png", new File("crash_detector/imagenes/boton_config.png"));
		copiarACarpetaDesdeJar("/imagenes/cd_logo.png", new File("crash_detector/imagenes/cd_logo.png"));
		copiarACarpetaDesdeJar("/imagenes/profeco.jpg", new File("crash_detector/imagenes/profeco.jpg"));

		
		
		copiarACarpetaDesdeJar("/imagenes/mod.png", new File("crash_detector/imagenes/mod.png"));
		copiarACarpetaDesdeJar("/imagenes/clase.png", new File("crash_detector/imagenes/clase.png"));
		copiarACarpetaDesdeJar("/imagenes/metodo.png", new File("crash_detector/imagenes/metodo.png"));
		copiarACarpetaDesdeJar("/imagenes/campo.png", new File("crash_detector/imagenes/campo.png"));
		copiarACarpetaDesdeJar("/imagenes/paquete.png", new File("crash_detector/imagenes/paquete.png"));
		copiarACarpetaDesdeJar("/imagenes/referencia_metodo.png", new File("crash_detector/imagenes/referencia_metodo.png"));
		copiarACarpetaDesdeJar("/imagenes/referencia_campo.png", new File("crash_detector/imagenes/referencia_campo.png"));

		
		
		
		String mods = "";
		if (ultimo_mods.toFile().exists()) {
			try {
				mods = leer_archivo(ultimo_mods);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		new File(viajo_ultima_mods.toString()).delete();
//		try {
//			new File(viajo_ultima_mods.toString()).createNewFile();
//			FileWriter escribidor = new FileWriter(viajo_ultima_mods.toFile());
//			escribidor.write(mods);
//			escribidor.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		StringBuilder actuales = new StringBuilder();
		boolean primera = true;

		for (Path ub : Statics.carpetas_de_mods) {
			File dir = ub.toFile();

			if (!dir.exists() || !dir.isDirectory()) {
				CrashDetectorLogger.log(dir.getAbsolutePath() + idioma.carpeta_de_mods_no_valido());
				continue;
			}

			File[] archivosMods = dir.listFiles();
			if (archivosMods == null) {
				CrashDetectorLogger.log(dir.getAbsolutePath() + idioma.carpeta_de_mods_no_valido());
				continue;
			}

			for (File archivoMod : archivosMods) {
				if (archivoMod.isFile()) {
					if (primera) {
						primera = false;
					} else {
						actuales.append(nl);
					}

					String pathCompleta = archivoMod.getAbsolutePath();
					actuales.append(pathCompleta);
				}
			}
		}

		new File(ultimo_mods.toString()).delete();
		try {
			new File(ultimo_mods.toString()).createNewFile();
			FileWriter escribidor = new FileWriter(ultimo_mods.toFile());
			escribidor.write(actuales.toString());
			escribidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long pid = obtenerPID();
		System.out.println("PID: " + pid);
		String jar;

		try {
			URI uriJar = MonitorDePID.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			String uriJarString = uriJar.toString();

			if (uriJarString.startsWith("union:")) {// Para Modlauncher
				uriJarString = uriJarString.replace("union:", "file://");
			}

			if (uriJarString.startsWith("jar:")) {
				uriJarString = uriJarString.substring(4); // elimnar "jar:"
			}

			CrashDetectorLogger.log(uriJarString);
			URI cd_uri = new URI(uriJarString);
			String cd_uri_path = cd_uri.getPath();
			CrashDetectorLogger.log(cd_uri_path);
			jar = new File(cd_uri_path).getAbsolutePath().split(".jar")[0] + ".jar";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(idioma.no_se_donde_esta_jar());
			return;
		}

		// Get the Java binary path
		String javaBinary = jvm();
		if (javaBinary == null || javaBinary.isEmpty()) {
			System.err.println("CD NO PUEDE OBTENER JVM");
			return;
		} else {
			System.out.println("JVM " + javaBinary);
		}

		// Launch the child monitor process
		try {
			String cp = System.getProperty("java.class.path") + File.pathSeparator + jar;
			// System.out.println("******************" + cp);

			new ProcessBuilder(javaBinary, "-cp", cp, "com.asbestosstar.crashdetector.MonitorDePID", "--monitor",
					String.valueOf(pid)).inheritIO().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void monitor_proceso(long pid) {
		List<Consola> consolas_sin_processando = Consola.obtenerConsolas();
		MonitorDePID.pid = pid;
		System.out.println(idioma.buscando_para_pid(pid));
		CountDownLatch latch = new CountDownLatch(1); // Necesito por que sin esta preceso esta muerte

		while (true) {

			if (!viva(pid)) {

				// System.out.println( escribes el codio de error aqui );

				System.out.println(idioma.pid_esta_muerto(pid));
				System.out.println(mensaje_de_registro_lanzer_completo);
				CrashDetectorLogger.log(idioma.pid_esta_muerto(pid));

				CrashDetectorLogger.log("Finalizando Contento de Consolas");

				// if (duration.getSeconds() >= 25) {// Para las consolas completa
				// } else {

				consolas_sin_processando.addAll(Consola.obtenerConsolas());

				if (!ArchivoDeCodigoError0.exists() && !Consola.tiene_registro_de_launcher(consolas_sin_processando)) {
					try {// Cuando tiene una informe de crash esta codio 0 y tiene tiempo para esperar
						Thread.sleep(500);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}

				// }

				for (Consola consola : consolas_sin_processando) {
					consola.finalizarContenido(utc, false);
					if (consola.nueva) {
						consolas.add(consola);
					}
				}

				if (activar() && !GraphicsEnvironment.isHeadless()) {
					if (!Consola.tiene_registro_de_launcher(consolas)) {
						obtenerConsolaDeLauncher(utc);
					}
				}
				
				historia_mods();
				

				Instant luego = Instant.now();
				recargar(true, luego);
				System.gc();

				if (activar()) {
					CrashDetectorLogger.log("activar ");

					if (GraphicsEnvironment.isHeadless()) {
						CrashDetectorLogger.log("headless ");

						System.out.println(idioma.local_headless(enlace));
						fin(latch);

					} else {
						CrashDetectorLogger.log("no headless ");

						SwingUtilities.invokeLater(() -> new CrashDetectorGUI(utc, latch).setVisible(true));
					}

				} else {
					fin(latch);
				}

//				try {
//					latch.await(); // Muerte cunado el popup se cerrada
//				} catch (InterruptedException e) {
//					Thread.currentThread().interrupt();
//				}

				break;

			}

//			try {
//				Thread.sleep(4000);
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//				break;
//			}
		}
	}

//	private static void showPopupMessage(String message, CountDownLatch latch) {
//		SwingUtilities.invokeLater(() -> {
//			JOptionPane pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
//			JDialog dialog = pane.createDialog("Proceso Crash Detector");
//			dialog.setModal(true);
//			dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//				@Override
//				public void windowClosed(java.awt.event.WindowEvent e) {
//					latch.countDown(); // Release the latch when the dialog is closed
//				}
//			});
//			dialog.setVisible(true);
//		});
//	}

	public static void fin(CountDownLatch latch) {
		// TODO Auto-generated method stub

		ArchivoDeCodigoError0.delete();
		//viajo_ultima_mods.toFile().delete();
		latch.countDown();
		System.exit(0);
	}
	
	
	
	
	
	
	public static void historia_mods() {

	    try {
	        Path directorioHistorial = carpeta.resolve("historia_mods");
	        Files.createDirectories(directorioHistorial);

	        File[] archivosHistorial = directorioHistorial.toFile().listFiles();
	        int siguienteNumero = 0;
	        if (archivosHistorial != null) {
	            for (File archivo : archivosHistorial) {
	                String nombre = archivo.getName();
	                if (nombre.matches("\\d{6}\\.falla") || nombre.matches("\\d{6}\\.exito")) {
	                    int num = Integer.parseInt(nombre.substring(0, 6));
	                    if (num >= siguienteNumero) siguienteNumero = num + 1;
	                }
	            }
	        }

	        String extension = activar() ? "falla" : "exito";
	        String nombreArchivo = String.format("%06d.%s", siguienteNumero, extension);
	        Path archivoHistorial = directorioHistorial.resolve(nombreArchivo);

	        if (ultimo_mods.toFile().exists()) {
	            String contenido = new String(Files.readAllBytes(ultimo_mods), StandardCharsets.UTF_8);
	            Files.write(archivoHistorial, contenido.getBytes(StandardCharsets.UTF_8));
	        }
	    } catch (IOException e) {
	        CrashDetectorLogger.log("Error al crear historial de modlist: " + e.getMessage());
	    }
	}
	
	
	
	
	
	
	

	/**
	 * asegura los registros son completa para 20 segundos
	 * 
	 * @param luego el tiempo ACTUAL AHORA
	 */
	private static void finalizarConsolasLentas(Instant luego) {
		// TODO Auto-generated method stub

		if (Consola.tiene_registro_de_launcher(consolas) && !consola_de_launcher_inyectado) {
			Duration duration = Duration.between(luego, Instant.now());

			while (!tiene_mensaje_de_registro_launcher_completa && duration.getSeconds() < 20) {// TODO Config para
																								// tiempo
				CrashDetectorLogger.log("reincinar finalizacion " + duration.getSeconds());
				duration = Duration.between(luego, Instant.now());
				for (Consola consola : consolas) {
					consola.finalizarContenido(utc, false);
				}

			}
			CrashDetectorLogger.log("tiene_mensaje_de_registro_launcher_completa es valor "
					+ String.valueOf(tiene_mensaje_de_registro_launcher_completa));
		}

	}

	public static String analizar(List<Consola> consolas) {
		analizador = new Analizador();
		analizador.analizar(consolas);
		return analizador.toString();
	}

	// Compatible approach (Java 7+)
	public static String leer_archivo(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	public static boolean activar() {
		for (Consola cons : consolas) {
			if (cons.archivo.toString().contains("crash-reports")) {
				return true;
			}
			for (Verificaciones ver : analizador.verificaciones) {
				if (ver.activado() && ver.anularNormal()) {
					return true;
				}

			}

		}
		if (ArchivoDeCodigoError0.exists()) {
			return false;
		}
		return true;
	}

	public static void obtenerConsolaDeLauncher(Instant utc) {
		// para detener el programa
		JFrame frame_blanco = new JFrame();
		frame_blanco.setUndecorated(true);
		frame_blanco.setSize(0, 0);
		frame_blanco.setLocationRelativeTo(null);
		frame_blanco.setVisible(true);

		CountDownLatch templach = new CountDownLatch(1);

		SwingUtilities.invokeLater(() -> {
			NoRegistroDeLauncher noreg = new NoRegistroDeLauncher(frame_blanco, utc);
			noreg.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosed(java.awt.event.WindowEvent e) {
					templach.countDown();
				}
			});

		});

		try {
			templach.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		frame_blanco.dispose();
	}

	private static void mostrarAyudaCLI() {
		System.out.println("Uso: java -jar CrashDetectorMC.jar [grepr|fgrepr] [-i] <cadena> [directorio]");
		System.out.println("  grepr: Busca usando regex (por defecto)");
		System.out.println("  fgrepr: Busca sin regex");
		System.out.println("  -i: Ignorar mayúsculas/minúsculas");
	}

	/**
	 * Obtiene el PID actual usando RuntimeMXBean (Java 8 compatible)
	 */
	public static long obtenerPID() {
		String nombre = ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(nombre.split("@")[0]);
	}

	public static String jvm() {
		// Step 1: Try using ProcessHandle via reflection (Java 9+)
		try {
			Class<?> processHandleClass = Class.forName("java.lang.ProcessHandle");
			java.lang.reflect.Method ofMethod = processHandleClass.getMethod("of", long.class);
			Object optionalHandle = ofMethod.invoke(null, obtenerPID());

			if (optionalHandle != null) {
				java.lang.reflect.Method isPresentMethod = optionalHandle.getClass().getMethod("isPresent");
				boolean isPresent = (boolean) isPresentMethod.invoke(optionalHandle);

				if (isPresent) {
					java.lang.reflect.Method getMethod = optionalHandle.getClass().getMethod("get");
					Object processHandle = getMethod.invoke(optionalHandle);

					java.lang.reflect.Method infoMethod = processHandleClass.getMethod("info");
					Object processInfo = infoMethod.invoke(processHandle);

					Class<?> infoClass = Class.forName("java.lang.ProcessHandle$Info");
					java.lang.reflect.Method commandMethod = infoClass.getMethod("command");
					Object commandOpt = commandMethod.invoke(processInfo);

					if (commandOpt != null) {
						java.lang.reflect.Method isPresentOpt = commandOpt.getClass().getMethod("isPresent");
						boolean commandPresent = (boolean) isPresentOpt.invoke(commandOpt);

						if (commandPresent) {
							java.lang.reflect.Method getOpt = commandOpt.getClass().getMethod("get");
							return (String) getOpt.invoke(commandOpt);
						}
					}
				}
			}
		} catch (Exception ignored) {
			// Java viaja
		}

		return obtenerRutaEjecutable8();

	}

	/**
	 * Obtiene la ruta completa del ejecutable del proceso actual
	 * 
	 * @return Ruta completa del ejecutable o null si no se encuentra
	 */
	private static String obtenerRutaEjecutable8() {
		try {
			String sistemaOperativo = System.getProperty("os.name").toLowerCase();

			if (sistemaOperativo.contains("win")) {
				// Usar PowerShell primero (más confiable que wmic)
				ProcessBuilder pb = new ProcessBuilder("powershell",
						"Get-WmiObject -Class Win32_Process -Filter \"ProcessId = " + obtenerPID()
								+ "\" | Select-Object -ExpandProperty ExecutablePath");

				Process proceso = pb.start();
				try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {

					String linea;
					while ((linea = lector.readLine()) != null) {
						String limpiado = linea.trim();
						if (!limpiado.isEmpty()) {
							// Devolver la ruta completa del ejecutable
							return limpiado;
						}
					}
				}

				// Si PowerShell falla, usar tasklist como último recurso
				pb = new ProcessBuilder("tasklist", "/FI", "PID eq " + obtenerPID(), "/FO", "CSV", "/NH");
				proceso = pb.start();
				try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
					String linea;
					while ((linea = lector.readLine()) != null) {
						if (!linea.trim().isEmpty()) {
							String[] valores = new CSVParser().parsear(linea);
							if (valores.length > 0 && !valores[0].trim().isEmpty()) {
								return valores[0].trim(); // Nombre del ejecutable
							}
						}
					}
				}
			} else {
				// Unix/Linux/macOS: Usar ps para obtener el comando
				ProcessBuilder pb = new ProcessBuilder("ps", "-p", String.valueOf(obtenerPID()), "-o", "comm=");
				Process proceso = pb.start();
				try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
					String linea;
					while ((linea = lector.readLine()) != null) {
						String limpiado = linea.trim();
						if (!limpiado.isEmpty()) {
							return limpiado;
						}
					}
				}
			}

			// No se encontró información del proceso
			return null;

		} catch (Exception e) {
			// Registrar errores para diagnóstico
			System.err.println("Error al obtener ruta ejecutable: " + e.getMessage());
			return null;
		}
	}

	public static boolean viva(long pid) {
		try {
			// Intentar usar ProcessHandle (Java 9+)
			Class<?> processHandleClase = Class.forName("java.lang.ProcessHandle");
			java.lang.reflect.Method meth = processHandleClase.getMethod("of", long.class);
			Object optionalHandle = meth.invoke(null, pid);

			if (optionalHandle != null) {
				java.lang.reflect.Method isPresentMethod = optionalHandle.getClass().getMethod("isPresent");
				boolean existe = (boolean) isPresentMethod.invoke(optionalHandle);

				if (existe) {
					java.lang.reflect.Method getMethod = optionalHandle.getClass().getMethod("get");
					Object processHandle = getMethod.invoke(optionalHandle);

					java.lang.reflect.Method estaViviaMeth = processHandle.getClass().getMethod("isAlive");
					return (boolean) estaViviaMeth.invoke(processHandle);
				}
			}
			return false;
		} catch (Exception e) {
			// Si falla (Java 8 o ProcessHandle no disponible), usa el método de comandos
			// del sistema
			return viva8(pid);
		}
	}

	public static boolean viva8(long pid) {
		try {
			String os = System.getProperty("os.name").toLowerCase();

			if (os.contains("win")) {
				// Intentar con PowerShell primero
				ProcessBuilder pb = new ProcessBuilder("powershell",
						"try { Get-Process -Id " + pid + " -ErrorAction Stop; exit 0 } catch { exit 1 }");

				Process proceso = pb.start();
				int codigoSalida = proceso.waitFor();

				if (codigoSalida == 0) {
					return true; // El proceso existe
				}

				// Si PowerShell falla, usar tasklist como respaldo
				// return verificarConTasklist(pid);
			} else {
				// En Unix/Linux/macOS, usar ps
				ProcessBuilder pb = new ProcessBuilder("ps", "-p", String.valueOf(pid));
				Process proceso = pb.start();
				int codigoSalida = proceso.waitFor();
				return codigoSalida == 0;
			}
		} catch (Exception e) {
			// Error al ejecutar el comando, asumir que el proceso no existe
			return false;
		}
		return false;

	}

	/**
	 * Verifica si un proceso está activo usando tasklist en Windows
	 * 
	 * @param pid El ID del proceso a verificar
	 * @return true si el proceso existe y está activo
	 * @throws IOException Si hay un error al ejecutar el comando
	 */
//	private static boolean verificarConTasklist(long pid) throws IOException, InterruptedException {
//	    ProcessBuilder pb = new ProcessBuilder("tasklist", "/FI", "PID eq " + pid, "/FO", "CSV", "/NH");
//	    Process proceso = pb.start();
//	    try (BufferedReader reader = new BufferedReader(
//	            new InputStreamReader(proceso.getInputStream()))) {
//	        
//	        String linea;
//	        while ((linea = reader.readLine()) != null) {
//	            String limpiado = linea.trim();
//	            // Si la salida contiene información del proceso, está activo
//	            if (!limpiado.isEmpty() && !limpiado.startsWith("INFO:") && !limpiado.contains("No tasks")) {
//	                return true;
//	            }
//	        }
//	    }
//	    
//	    // Si no se encontró el proceso
//	    return false;
//	}

	public static void copiarACarpetaDesdeJar(String ubicacion_en_jar, File resultdo) {
		if (!resultdo.exists()) {
			resultdo.getParentFile().mkdirs();
			try (InputStream inputStream = MonitorDePID.class.getResourceAsStream(ubicacion_en_jar);
					FileOutputStream outputStream = new FileOutputStream(resultdo)) {

				if (inputStream == null) {
					throw new RuntimeException("El archivo de CrashDetector no tiene "+ubicacion_en_jar);
				}
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

			} catch (IOException e) {
				System.err.println("No puede extractar HTML de CrashDetector: " + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param finalizar_contento si quires finalizar_contento
	 * @param luego              el tiempo ACTUAL AHORA, Solo necesitas si quieres
	 *                           finalizar_contento
	 */
	public static void recargar(boolean finalizar_contento, Instant luego) {
		StringBuilder constructor = new StringBuilder();
		Buscardor.mods.clear();
		Buscardor.cargado = false;
		if (finalizar_contento) {
			finalizarConsolasLentas(luego);
		}
		for (Consola consola : consolas) {
			consola.verificacion_de_stacktrace = new VerificacionDeStackTrace(consola);
			consola.enlance = null;
		}
		CrashDetectorLogger.log("Analyzador Consolas");

		String res = analizar(consolas);

		if (res.replace(" ", "").equals("")) {
			constructor.append(idioma.noResultados());
		} else {
			constructor.append(res);
			resultados = true;
		}

		CrashDetectorLogger.log("resultdos " + res);
		contenidoInforme = constructor;
		local = GeneradorDeInformacion.generarLocal(consolas, utc).getAbsolutePath();
	}

}