package com.asbestosstar.crashdetector.analizador.general;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

public class DifDeMods implements Verificaciones {
	private boolean activado = false;
	private String mensajeHTML = "";

	@Override
	public void verificar(Consola consola) {
		try {
			// Obtener lista actual de mods
			Set<String> modsActual = obtenerMods(MonitorDePID.ultimo_mods);

			// Buscar últimos archivos históricos (exito, falla, instantanea)
			Path exitoFile = obtenerUltimoArchivo("exito");
			Path fallaFile = obtenerUltimoArchivo("falla");
			Path instantaneaFile = obtenerUltimoArchivo("instantanea"); // Nuevo archivo de instantánea

			// Determinar archivo más reciente globalmente para instantáneas
			Path archivoInstantaneaUltimo = null;
			if (instantaneaFile != null) { // Si hay alguna instantánea, usamos la más reciente
				archivoInstantaneaUltimo = instantaneaFile;
			}

			// Comparar con la instantánea más reciente (si existe)
			List<String> diffInstantanea = new ArrayList<>();
			if (archivoInstantaneaUltimo != null) {
				Set<String> modsInstantanea = obtenerMods(archivoInstantaneaUltimo);
				diffInstantanea = compararMods(modsInstantanea, modsActual);
			}

			// Ajustar archivos a comparar según el último estado para exito/falla
			// (Lógica original para comparar con el último exito o falla, no con la
			// instantánea)
			Path archivoUltimo = null;
			if (exitoFile != null && fallaFile != null) {
				int numExito = obtenerNumeroArchivo(exitoFile);
				int numFalla = obtenerNumeroArchivo(fallaFile);

				if (numExito > numFalla) {
					archivoUltimo = exitoFile;
				} else {
					archivoUltimo = fallaFile;
				}
			} else {
				if (exitoFile != null) {
					archivoUltimo = exitoFile;
				} else {
					archivoUltimo = fallaFile;
				}
			}

			// Si el archivo más reciente es un éxito, no comparar con falla
			if (archivoUltimo != null && archivoUltimo.toString().endsWith(".exito") || archivoUltimo.toString().endsWith(".instantanea")) {
				fallaFile = null;
			}

			// Comparar con último éxito
			List<String> diffExito = new ArrayList<>();
			if (exitoFile != null) {
				Set<String> modsExito = obtenerMods(exitoFile);
				diffExito = compararMods(modsExito, modsActual);
			}

			// Comparar con última falla (si no es el último estado)
			List<String> diffFalla = new ArrayList<>();
			if (fallaFile != null) {
				Set<String> modsFalla = obtenerMods(fallaFile);
				diffFalla = compararMods(modsFalla, modsActual);
			}

			// Generar HTML si hay diferencias
			if (!diffInstantanea.isEmpty() || !diffExito.isEmpty() || !diffFalla.isEmpty()) {
				activado = true;
				mensajeHTML = generarHTML(diffInstantanea, diffExito, diffFalla, archivoInstantaneaUltimo != null,
						exitoFile != null, fallaFile != null);
			}

		} catch (IOException e) {
			CrashDetectorLogger.log("Error comparando mods: " + e.getMessage());
		}
	}

	private Set<String> obtenerMods(Path archivo) throws IOException {
		return Files.readAllLines(archivo).stream().filter(line -> !line.trim().isEmpty()).collect(Collectors.toSet());
	}

	private List<String> compararMods(Set<String> viejos, Set<String> nuevos) {
		List<String> diff = new ArrayList<>();
		nuevos.stream().filter(mod -> !viejos.contains(mod)).forEach(mod -> diff.add("+ " + mod));
		viejos.stream().filter(mod -> !nuevos.contains(mod)).forEach(mod -> diff.add("- " + mod));
		return diff;
	}

	private Path obtenerUltimoArchivo(String extension) {
		Path dir = MonitorDePID.carpeta.resolve("historia_mods");
		if (!Files.exists(dir))
			return null;

		File[] files = dir.toFile().listFiles((d, name) -> name.matches("\\d{6}\\.\\Q" + extension + "\\E"));

		if (files == null || files.length == 0)
			return null;

		List<File> sortedFiles = Arrays.stream(files).sorted((f1, f2) -> {
			int num1 = obtenerNumeroArchivo(f1.toPath());
			int num2 = obtenerNumeroArchivo(f2.toPath());
			return Integer.compare(num2, num1); // Orden descendente
		}).collect(Collectors.toList());

		if (!sortedFiles.isEmpty()) {
			return sortedFiles.get(0).toPath(); // Devuelve la más reciente
		}

		return null;
	}

	private int obtenerNumeroArchivo(Path archivo) {
		String nombre = archivo.getFileName().toString();
		return Integer.parseInt(nombre.substring(0, 6));
	}

	// Modificado para incluir la comparación con la instantánea al principio
	private String generarHTML(List<String> diffInstantanea, List<String> diffExito, List<String> diffFalla,
			boolean tieneInstantanea, boolean tieneExito, boolean tieneFalla) {
		StringBuilder html = new StringBuilder();

		// Mostrar diferencias con la instantánea más reciente primero
		if (tieneInstantanea) {
			html.append("<div style='margin:10px 0;padding:10px;border:1px solid #ccc;background-color:#f0f8ff;'>") // Fondo
																													// azul
																													// claro
																													// para
																													// instantánea
					.append("<h3 style='color:#2196F3;'>") // Color de encabezado azul
					.append(MonitorDePID.idioma.desdeUltimaInstantanea()).append(" (")
					.append(extensionToNombre("instantanea")).append("):</h3>"); // Nuevo texto localizado

			if (diffInstantanea.isEmpty()) {
				html.append("<p style='color:green'>").append(MonitorDePID.idioma.noHayCambios()).append("</p>");
			} else {
				html.append("<ul>");
				for (String linea : diffInstantanea) {
					String color = linea.startsWith("+") ? "green" : "red";
					html.append("<li style='color:" + color + "'>").append(linea).append("</li>");
				}
				html.append("</ul>");
			}
			html.append("</div>");
		}

		// Mostrar diferencias con el último éxito
		if (tieneExito) {
			html.append("<div style='margin:10px 0;padding:10px;border:1px solid #ccc'>").append("<h3>")
					.append(MonitorDePID.idioma.desdeUltimoExito()).append(" (").append(extensionToNombre("exito"))
					.append("):</h3>");

			if (diffExito.isEmpty()) {
				html.append("<p style='color:green'>").append(MonitorDePID.idioma.noHayCambios()).append("</p>");
			} else {
				html.append("<ul>");
				for (String linea : diffExito) {
					String color = linea.startsWith("+") ? "green" : "red";
					html.append("<li style='color:" + color + "'>").append(linea).append("</li>");
				}
				html.append("</ul>");
			}
			html.append("</div>");
		}

		// Mostrar diferencias con la última falla
		if (tieneFalla) {
			html.append("<div style='margin:10px 0;padding:10px;border:1px solid #ccc'>").append("<h3>")
					.append(MonitorDePID.idioma.desdeUltimoIntento()).append(" (").append(extensionToNombre("falla"))
					.append("):</h3>");

			if (diffFalla.isEmpty()) {
				html.append("<p style='color:green'>").append(MonitorDePID.idioma.noHayCambios()).append("</p>");
			} else {
				html.append("<ul>");
				for (String linea : diffFalla) {
					String color = linea.startsWith("+") ? "green" : "red";
					html.append("<li style='color:" + color + "'>").append(linea).append("</li>");
				}
				html.append("</ul>");
			}
			html.append("</div>");
		}

		return html.toString();
	}

	private String extensionToNombre(String extension) {
		if ("exito".equals(extension)) {
			return MonitorDePID.idioma.exito();
		} else if ("falla".equals(extension)) {
			return MonitorDePID.idioma.fallo();
		} else if ("instantanea".equals(extension)) {
			return MonitorDePID.idioma.instantanea(); // Nuevo texto localizado
		}
		return extension; // Por si acaso
	}

	@Override
	public String mensaje() {
		return mensajeHTML;
	}

	@Override
	public Verificaciones nueva() {
		return new DifDeMods();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return -1000; // Prioridad baja, se mantiene igual
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.diferentesDeLasMods();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public String id() {
		return "dif_de_mods";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}
}