package com.asbestosstar.crashdetectormc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

public class MonitorDePID {

	public static String nl = System.lineSeparator();
	public static Idioma idioma = Idioma.detectar();
	
	
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("--monitor")) {
			long pid = Long.parseLong(args[1]);
			monitor_proceso(pid);
			return;
		}

		Path carpeta = new File("crash_detector/").toPath();
		Path ultima_mods = carpeta.resolve("ultima_mods");
		Path viajo_ultima_mods = carpeta.resolve("viajo_ultima_mods");
		carpeta.toFile().mkdirs();
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
			jar = new File(MonitorDePID.class.getProtectionDomain().getCodeSource().getLocation().toURI())
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
			new ProcessBuilder(javaBinary.get(), "-cp", jar, "com.asbestosstar.crashdetectormc.MonitorDePID",
					"--monitor", String.valueOf(pid)).inheritIO().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void monitor_proceso(long pid) {
		System.out.println(idioma.buscando_para_pid(pid));
		Instant utc = Instant.now();
		while (true) {
			boolean viva = ProcessHandle.of(pid).map(ProcessHandle::isAlive).orElse(false);

			if (!viva) {
				System.out.println(idioma.pid_esta_muerto(pid));
				StringBuilder constructor = new StringBuilder();
				
				List<Consola> consolas = Consola.obtainerConsolas(utc);
				for(Consola consola:consolas) {
					consola.analyzar(constructor);
				}
				
				System.out.println(constructor.toString());
				
				showPopupMessage(constructor.toString());
				break;
			}

			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	private static void showPopupMessage(String message) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(null, message, "Proceso Crash Detector", JOptionPane.ERROR_MESSAGE);
		});
	}

	// Compatible approach (Java 7+)
	public static String leer_archivo(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

}