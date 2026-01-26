package com.asbestosstar.crashdetector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import com.asbestosstar.crashdetector.gui.tipos.consola.ConsolaDesarrolladorGUI;

public class CrashDetectorLogger {

	public static final String LOG_FILE_PATH = Statics.carpeta.resolve("log.txt").toString();
	public static final String LOG_ERR_FILE_PATH = Statics.carpeta.resolve("log_err.txt").toString();

	// Buffer mientras la consola aún no existe
	private static final List<String> buffer = new ArrayList<>();

	public static synchronized void log(String message) {

		String linea = getTimestamp() + " - " + message;

		try {
			Path logDir = Statics.carpeta;
			if (!Files.exists(logDir)) {
				Files.createDirectories(logDir);
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
				writer.write(linea);
				writer.newLine();
			}

		} catch (Exception ignored) {
		}

		// Intentar enviar a consola TL
		enviarALaConsola(linea);
	}

	public static void logException(Throwable throwable) {

		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwable.printStackTrace(pw);

			log("EXCEPTION: " + sw.toString());

		} catch (Exception e) {
			System.err.println("Failed to log exception: " + e.getMessage());
		}
	}

	/**
	 * Intenta enviar una línea a la consola. Si no existe aún, queda en buffer.
	 */
	private static synchronized void enviarALaConsola(String linea) {

		// ¿Está habilitada la consola dev?
		if (!ConfigMundial.obtenerInstancia().obtenerConsolaDesarrollo()) {
			return;
		}

		ConsolaDesarrolladorGUI consola = consola();

		if (consola == null) {
			buffer.add(linea);
			return;
		}

		// Vaciar buffer primero
		if (!buffer.isEmpty()) {
			for (String l : buffer) {
				consola.agregarLinea(l);
			}
			buffer.clear();
		}

		// Añadir línea actual
		consola.agregarLinea(linea);
	}

	/**
	 * Devuelve la consola si ya fue creada.
	 */
	public static @Nullable ConsolaDesarrolladorGUI consola() {
		try {
			return (ConsolaDesarrolladorGUI) MonitorDePID.consola_des;
		} catch (Throwable t) {
			return null;
		}
	}

	private static String getTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
}
