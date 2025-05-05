package com.asbestosstar.crashdetectormc;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetectormc.analyzador.Verificaciones;
import com.asbestosstar.crashdetectormc.grepr.BusquedaArchivos;
import com.asbestosstar.crashdetectormc.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetectormc.gui.NoRegistroDeLauncher;

public class MonitorDePID {

	public static Path carpeta = new File("crash_detector/").toPath();
	public static File ArchivoDeCodioError0 = new File("crash_detector/ArchivoDeCodioError0");
	public static Path ultima_mods = carpeta.resolve("ultima_mods");
	public static Path viajo_ultima_mods = carpeta.resolve("viajo_ultima_mods");
	public static List<Consola> consolas = new ArrayList<Consola>();

	public static String nl = System.lineSeparator();
	public static Idioma idioma = Idioma.detectar();
	public static String local;
	public static String enlance;
	public static long pid;
	public static boolean resultos = false;

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

		ArchivoDeCodioError0.delete();
		File html = new File("crash_detector/pantilla.htm");
		if (!html.exists()) {
			carpeta.toFile().mkdirs();
			try (InputStream inputStream = MonitorDePID.class.getResourceAsStream("/pantilla.htm");
					FileOutputStream outputStream = new FileOutputStream(html)) {

				if (inputStream == null) {
					throw new RuntimeException("El archivo de CrashDetector no tiene pantilla.htm");
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

		String mods = "";
		if (ultima_mods.toFile().exists()) {
			try {
				mods = leer_archivo(ultima_mods);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		new File(viajo_ultima_mods.toString()).delete();
		try {
			new File(viajo_ultima_mods.toString()).createNewFile();
			FileWriter escribidor = new FileWriter(viajo_ultima_mods.toFile());
			escribidor.write(mods);
			escribidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		new File(ultima_mods.toString()).delete();
		try {
			new File(ultima_mods.toString()).createNewFile();
			FileWriter escribidor = new FileWriter(ultima_mods.toFile());
			escribidor.write(actuales.toString());
			escribidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long pid = ProcessHandle.current().pid();
		System.out.println("PID: " + pid);
		String jar;

		try {
			URI uriJar = MonitorDePID.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			String uriJarString = uriJar.toString();

			if (uriJarString.startsWith("union:")) {// Para Modlauncher
				uriJarString = uriJarString.replace("union:", "file://");
			}

			jar = new File(new URI(uriJarString).getPath()).getAbsolutePath().split(".jar")[0] + ".jar";
		} catch (Exception e) {
			System.err.println(idioma.no_se_donde_esta_jar());
			return;
		}

		// Get the Java binary path
		Optional<String> javaBinary = ProcessHandle.current().info().command();
		if (javaBinary.isEmpty()) {
			System.err.println("");
			return;
		}

		// Launch the child monitor process
		try {
			String cp = System.getProperty("java.class.path") + File.pathSeparator + jar;
			System.out.println("******************" + cp);

			new ProcessBuilder(javaBinary.get(), "-cp", cp, "com.asbestosstar.crashdetectormc.MonitorDePID",
					"--monitor", String.valueOf(pid)).inheritIO().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void monitor_proceso(long pid) {
		Instant utc = Instant.now();
		List<Consola> consolas_sin_processando = Consola.obtenerConsolas();
		MonitorDePID.pid = pid;
		System.out.println(idioma.buscando_para_pid(pid));
		CountDownLatch latch = new CountDownLatch(1); // Necesito por que sin esta preceso esta muerte

		while (true) {
			Optional<ProcessHandle> processo = ProcessHandle.of(pid);
			boolean viva = processo.map(ProcessHandle::isAlive).orElse(false);

			if (!viva) {

				// System.out.println( escribes el codio de error aqui );

				System.out.println(idioma.pid_esta_muerto(pid));
				CrashDetectorLogger.log(idioma.pid_esta_muerto(pid));
				StringBuilder constructor = new StringBuilder();

				Instant lugeo = Instant.now();
				Duration duration = Duration.between(utc, lugeo);

				if (duration.getSeconds() >= 10) {// Para las consolas completa
				} else {

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}

				CrashDetectorLogger.log("Finalizando Contento de Consolas");

				consolas_sin_processando.addAll(Consola.obtenerConsolas());

				for (Consola consola : consolas_sin_processando) {
					consola.finalizarContento(utc);
					if (consola.nueva) {
						consolas.add(consola);
					}
				}

				if (!Consola.tiene_registro_de_launcher(consolas)) {
					obtenerCosolaDeLauncher(utc);
				}

				CrashDetectorLogger.log("Analyzador Consolas");

				for (Consola consola : consolas) {
					consola.analyzar(constructor);
				}
				String res = constructor.toString();
				if (res.replace(" ", "").equals("")) {
					constructor.append(idioma.noResultos());
				} else {
					resultos = true;
				}

				System.out.println(res);

				if (activar()) {

					if (GraphicsEnvironment.isHeadless()) {

						local = GeneradorDeInformacion.generarLocal(consolas, constructor, utc).getAbsolutePath();
						System.out.println(idioma.local_headless(enlance));
						latch.countDown();
					} else {
						SwingUtilities
								.invokeLater(() -> new CrashDetectorGUI(consolas, constructor, utc).setVisible(true));
						latch.countDown();
					}

				} else {
					latch.countDown();
				}
				ArchivoDeCodioError0.delete();
				viajo_ultima_mods.toFile().delete();
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

	// Compatible approach (Java 7+)
	public static String leer_archivo(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	public static boolean activar() {
		for (Consola cons : consolas) {
			if (cons.archivo.toString().contains("crash-reports")) {
				return true;
			}
			for (Verificaciones ver : cons.analyzador.verificaciones) {
				if (ver.activado() && ver.anularNormal()) {
					return true;
				}

			}

		}
		if (ArchivoDeCodioError0.exists()) {
			return false;
		}
		return true;
	}

	public static void obtenerCosolaDeLauncher(Instant utc) {
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

}