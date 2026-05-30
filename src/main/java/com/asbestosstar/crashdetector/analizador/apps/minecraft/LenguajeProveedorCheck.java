package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class LenguajeProveedorCheck implements Verificaciones {

	private final Set<String> errores = new HashSet<>();
	public boolean activado = false;
	private final Map<String, String> enlacesPorError = new HashMap<>();

	// Cache del contenido de la consola dividido por líneas para evitar hacer split
	// repetidos.
	private boolean posiblePorConsola = false;

	/**
	 * Verifica el contenido de la consola para detectar errores relacionados con
	 * proveedores de lenguaje. Este método procesa cada línea de la salida de la
	 * consola para identificar problemas relacionados con proveedores de lenguaje
	 * faltantes o incompatibles. Extrae detalles relevantes como el nombre del
	 * archivo JAR, el proveedor requerido, su versión y la versión disponible.
	 * Además, añade un mensaje detallado al {@code CDStringBuilder} proporcionado.
	 *
	 * @param consola La consola que contiene el texto a analizar.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		boolean posible = contenido.contains("needs language provider")
				|| contenido.contains("Failed to load language provider")
				|| contenido.contains("Loading language provider");

		if (posible) {
			posiblePorConsola = true;
		}
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Se ejecuta para cada línea de la consola. Cuando encuentra una línea con el
	 * patrón "Mod File ... needs language provider ...", extrae los datos
	 * relevantes y busca más información en las líneas siguientes (como "We have
	 * found ...") usando el array cacheado de líneas.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		if (consola == null || linea == null) {
			return;
		}

		if (!posiblePorConsola) {
			return;
		}

		String lineaActual = linea.trim();
		String[] lineasConsola = consola.lineas_verificar;

		if (lineaActual.contains("Mod File") && lineaActual.contains("needs language provider")) {
			try {
				// Extraer detalles del error
				String archivoJar = lineaActual.split("Mod File ")[1].split(" needs")[0].trim();
				String proveedor = lineaActual.split("language provider ")[1].split(" ")[0].trim();
				String req = proveedor.split(":")[1];
				String encontrado = "";

				// Buscar versión disponible en líneas posteriores
				for (int j = numero_de_linea; j < lineasConsola.length; j++) {
					if (lineasConsola[j].contains("We have found ")) {
						encontrado = lineasConsola[j].split("We have found ")[1].split("§")[0].trim();
						break;
					}
				}

				// Construir mensaje base
				String mensaje = MonitorDePID.idioma.errorProveedorVersion(archivoJar, proveedor.split(":")[0], req,
						encontrado);

				// Agregar mensaje especial para JavaFML/MCForge
				if (proveedor.toLowerCase().contains("javafml")) {
					mensaje += Verificaciones.nl_html + MonitorDePID.idioma.errorJavaFML_MCForge();
				}

				// Solo registrar si es un error nuevo
				if (errores.add(mensaje)) {
					String enlace = consola.agregarErrorALectador(numero_de_linea, this);
					enlacesPorError.put(mensaje, enlace);
				}
				activado = true;

			} catch (Exception e) {
				CrashDetectorLogger.logException(e);
				// Registrar la línea incluso si falla el parseo
				consola.agregarErrorALectador(numero_de_linea, this);
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new LenguajeProveedorCheck();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450.0f;
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String error : errores) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			html.append("<li>").append(error).append(" ").append(enlace).append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_lenguaje_proveedor_check();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		return "lenguaje_proveedor_check";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>Ya se ha activado esta verificación, y</li>
	 * <li>El trazo contiene claramente el patrón de error de proveedor de lenguaje
	 * de ModLauncher: una línea con "Mod File" y "needs language provider".</li>
	 * </ul>
	 * Es deliberadamente conservador: se prefiere un falso negativo antes que
	 * asociar un trazo que no corresponda realmente a este problema.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("Mod File") && t.contains("needs language provider");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}
