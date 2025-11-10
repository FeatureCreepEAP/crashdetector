package com.asbestosstar.crashdetector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashDetectorLogger {

	public static final String LOG_FILE_PATH = MonitorDePID.carpeta.resolve("log.txt").toString();
	public static final String LOG_ERR_FILE_PATH = MonitorDePID.carpeta.resolve("log_err.txt").toString();

	
	public static void log(String message) {
		try {
			// Ensure the directory exists
			Path logDir = MonitorDePID.carpeta;
			if (!Files.exists(logDir)) {
				Files.createDirectories(logDir);
			}

			// Open the log file in append mode
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
				// Write the timestamp and message
				writer.write(getTimestamp() + " - " + message);
				writer.newLine();
			}
		} catch (IOException e) {
		}
	}

	public static void logException(Throwable throwable) {
		try {
			// Convert the exception stack trace to a string
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwable.printStackTrace(pw);
			String stackTrace = sw.toString();

			// Log the stack trace
			log("EXCEPTION OCCURRED: " + stackTrace);
		} catch (Exception e) {
			System.err.println("Failed to log exception: " + e.getMessage());
		}
	}

	/**
	 * Returns the current timestamp in a readable format.
	 *
	 * @return The formatted timestamp.
	 */
	private static String getTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

}