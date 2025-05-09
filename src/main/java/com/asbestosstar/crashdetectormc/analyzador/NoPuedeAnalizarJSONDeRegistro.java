package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

public class NoPuedeAnalizarJSONDeRegistro implements Verificaciones {

	boolean activado = false;

	/**
	 * Verifica el contenido de la consola para detectar errores de análisis en
	 * archivos JSON de mods. Este método procesa cada línea de la salida de la
	 * consola para identificar problemas relacionados con el análisis fallido de
	 * archivos JSON en mods. Extrae detalles relevantes como el nombre del archivo
	 * JAR y el recurso problemático, y añade un mensaje detallado al
	 * {@code CDStringBuilder} proporcionado.
	 *
	 * @param contenido_de_consola La salida cruda de la consola que puede contener
	 *                             mensajes de error.
	 * @param constructor          Una instancia de {@code CDStringBuilder} usada
	 *                             para construir el mensaje final de error.
	 *
	 *                             El formato esperado del mensaje de error en la
	 *                             consola es:
	 * 
	 *                             <pre>
	 * java.lang.IllegalStateException: Failed to parse [recurso_problematico] from pack [archivo_jar]
	 *                             </pre>
	 *
	 *                             Ejemplo:
	 * 
	 *                             <pre>
	 * java.lang.IllegalStateException: Failed to parse souls_like_bosses:worldgen/structure/lothric_castle.json from pack souls_like_bosses_1.1_Forge+Fabric-1.20.1.jar
	 *                             </pre>
	 */
	@Override
	public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
		// Dividir el contenido de la consola en líneas individuales para procesarlas
		String[] lineas = contenido_de_consola.split(nl);

		for (String linea : lineas) {

			// Verificar si la línea contiene el patrón que indica un error de análisis
			if (linea.contains("Failed to parse") && linea.contains(".json from pack")) {
				CrashDetectorLogger.log("Se detectó un error de análisis en un archivo JSON de Registro.");

				try {
					// Extraer el nombre del archivo JAR
					String archivoJar = linea.split("from pack ")[1].split("\\.jar")[0].trim() + ".jar";
					CrashDetectorLogger.log("Archivo JAR: " + archivoJar);

					// Extraer el recurso problemático
					String recurso = linea.split("Failed to parse ")[1].split(" from pack")[0].trim();
					CrashDetectorLogger.log("Recurso Problemático: " + recurso);

					// Añadir un mensaje de error incluyendo el nombre del archivo JAR y el recurso
					// problemático
					constructor.append(MonitorDePID.idioma.errorConJSONDeRegistro(archivoJar, recurso)).append(nl_html);

					activado = true;

				} catch (Exception e) {
					CrashDetectorLogger.logException(e);
				}
			}
		}

	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new NoPuedeAnalizarJSONDeRegistro();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
