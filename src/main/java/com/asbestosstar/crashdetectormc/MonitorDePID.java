package com.asbestosstar.crashdetectormc;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.zip.ZipInputStream;

import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetectormc.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetectormc.buscar.Buscardor;
import com.asbestosstar.crashdetectormc.buscar.ModPKZip;

public class MonitorDePID {

	
	public static Path carpeta = new File("crash_detector/").toPath();
	public static Path ultima_mods = carpeta.resolve("ultima_mods");
	public static Path viajo_ultima_mods = carpeta.resolve("viajo_ultima_mods");
	public static String nl = System.lineSeparator();
	public static Idioma idioma = Idioma.detectar();
	public static String local;
	public static String enlance;

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("--monitor")) {
			long pid = Long.parseLong(args[1]);
			monitor_proceso(pid);
			return;
		}

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
			File archivo_de_ub = ub.toFile();
			if (archivo_de_ub.exists() && archivo_de_ub.isDirectory()) {
				for (String mod_ubic : archivo_de_ub.list()) {
					if (primera) {
						primera = false;
						actuales.append(nl);
					}
					actuales.append(mod_ubic);
				}

			} else {
				System.out.println(archivo_de_ub.getAbsolutePath() + idioma.carpeta_de_mods_no_valido());
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
			URI uri_jar =MonitorDePID.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			String uri_de_jar_string = uri_jar.toString().replace("union:", "file://").split(".jar")[0]+".jar";//Porque la union sistema de archivos
			jar = new File(uri_de_jar_string)
					.getAbsolutePath();
		} catch (URISyntaxException e) {
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
			new ProcessBuilder(javaBinary.get(), "-cp", System.getProperty("java.class.path")+File.pathSeparator+jar, "com.asbestosstar.crashdetectormc.MonitorDePID",
					"--monitor", String.valueOf(pid)).inheritIO().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void monitor_proceso(long pid) {
		System.out.println(idioma.buscando_para_pid(pid));
		Instant utc = Instant.now();
		CountDownLatch latch = new CountDownLatch(1); // Necesito por que sin esta preceso esta muerte

		while (true) {
			boolean viva = ProcessHandle.of(pid).map(ProcessHandle::isAlive).orElse(false);

			if (!viva) {
				System.out.println(idioma.pid_esta_muerto(pid));
				StringBuilder constructor = new StringBuilder();
//				try {
//					for (String mod:leer_archivo(ultima_mods).split(nl)) {
//						File arc = new File(mod);
//						if(arc.isFile()) {
//						Buscardor.mods.add(new ModPKZip(mod,ArchivoDeMod.origin,new ZipInputStream(new FileInputStream(arc))));
//						}
//						
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} manaña
				
				
				
				List<Consola> consolas = Consola.obtenerConsolas(utc);
				for (Consola consola : consolas) {
					consola.analyzar(constructor);
				}
				System.out.println(constructor.toString());

				if (GraphicsEnvironment.isHeadless()) {

					local = GeneradorDeInformacion.generarLocal(consolas, constructor, utc).getAbsolutePath();
					System.out.println(idioma.local_headless(enlance));
					latch.countDown();
				} else {
					SwingUtilities.invokeLater(() -> new CrashDetectorGUI(consolas,constructor, utc).setVisible(true));
					latch.countDown();
				}
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

}