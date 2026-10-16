package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Comprueba que los enlaces entre clases del programa y sus pruebas
 * unitarias (@ProbadoPor en una direccion, @PruebaDe en la otra) esten
 * completos y sean simetricos. Asi ninguna verificacion queda cubierta
 * "solo de nombre" y ninguna prueba apunta a una clase que no la declara.
 */
class PruebaSimetriaDeAnotaciones {

	private static final String PAQUETE_ANALIZADOR = "com.asbestosstar.crashdetector.analizador";

	private static final String PAQUETE_PRUEBAS = "com.asbestosstar.crashdetector.pruebas";

	@Test
	void losEnlacesEntreClasesYPruebasSonSimetricos() {
		ClassLoader cargador = getClass().getClassLoader();
		List<String> problemas = new ArrayList<String>();

		List<Class<?>> clasesAnalizador = cargarClases(cargador, PAQUETE_ANALIZADOR, "target/classes", problemas);
		List<Class<?>> clasesPrueba = cargarClases(cargador, PAQUETE_PRUEBAS, "target/test-classes", problemas);

		assertFalse(clasesAnalizador.isEmpty(),
				"No se encontro ninguna clase del analizador en target/classes. Ejecuta las pruebas desde Maven para que la compilacion exista.");
		assertFalse(clasesPrueba.isEmpty(),
				"No se encontro ninguna clase de prueba en target/test-classes.");

		// Direccion 1: toda clase del analizador con @ProbadoPor debe apuntar a
		// pruebas reales que la declaren en @PruebaDe.
		for (Class<?> clase : clasesAnalizador) {
			ProbadoPor anotacion = clase.getAnnotation(ProbadoPor.class);
			if (anotacion == null) {
				continue;
			}
			for (String nombrePrueba : anotacion.valor()) {
				Class<?> prueba = resolver(cargador, nombrePrueba, problemas, clase.getName());
				if (prueba == null) {
					continue;
				}
				PruebaDe contra = prueba.getAnnotation(PruebaDe.class);
				if (contra == null) {
					problemas.add("La prueba " + nombrePrueba + " no tiene @PruebaDe, pero la clase "
							+ clase.getName() + " declara @ProbadoPor hacia ella.");
				} else if (!contiene(contra.valor(), clase.getName())) {
					problemas.add("Enlace incompleto: " + clase.getName() + " apunta a " + nombrePrueba
							+ " con @ProbadoPor, pero esa prueba no apunta de vuelta en su @PruebaDe.");
				}
			}
		}

		// Direccion 2: toda prueba con @PruebaDe debe apuntar a clases reales
		// que la declaren en @ProbadoPor.
		for (Class<?> prueba : clasesPrueba) {
			PruebaDe anotacion = prueba.getAnnotation(PruebaDe.class);
			if (anotacion == null) {
				continue;
			}
			for (String nombreClase : anotacion.valor()) {
				Class<?> clase = resolver(cargador, nombreClase, problemas, prueba.getName());
				if (clase == null) {
					continue;
				}
				ProbadoPor contra = clase.getAnnotation(ProbadoPor.class);
				if (contra == null) {
					problemas.add("La clase " + nombreClase + " no tiene @ProbadoPor, pero la prueba "
							+ prueba.getName() + " declara @PruebaDe hacia ella.");
				} else if (!contiene(contra.valor(), prueba.getName())) {
					problemas.add("Enlace incompleto: " + prueba.getName() + " apunta a " + nombreClase
							+ " con @PruebaDe, pero esa clase no apunta de vuelta en su @ProbadoPor.");
				}
			}
		}

		StringBuilder informe = new StringBuilder("Enlaces de pruebas rotos:");
		for (String problema : problemas) {
			informe.append("\n - ").append(problema);
		}
		assertTrue(problemas.isEmpty(), informe.toString());
	}

	/** Carga por reflexion todas las clases compiladas de un paquete. */
	private List<Class<?>> cargarClases(ClassLoader cargador, String paquete, String raizCompilado,
			List<String> problemas) {
		List<Class<?>> clases = new ArrayList<Class<?>>();
		Path carpetaPaquete = Paths.get(raizCompilado, paquete.replace('.', '/'));
		if (!Files.isDirectory(carpetaPaquete)) {
			return clases;
		}

		try (Stream<Path> archivos = Files.walk(carpetaPaquete)) {
			archivos.filter(p -> p.getFileName().toString().endsWith(".class"))
					.filter(p -> !p.getFileName().toString().contains("$"))
						.filter(p -> !p.getFileName().toString().equals("package-info.class"))
					.forEach(p -> {
						Path relativa = carpetaPaquete.relativize(p);
							String rutaClase = relativa.toString().replace(File.separatorChar, '.');
						rutaClase = rutaClase.substring(0, rutaClase.length() - ".class".length());
						String nombreCompleto = paquete + '.' + rutaClase;
						try {
							clases.add(Class.forName(nombreCompleto, false, cargador));
						} catch (Throwable error) {
							problemas.add("No se pudo cargar la clase " + nombreCompleto + ": " + error);
						}
					});
		} catch (IOException error) {
			problemas.add("No se pudo recorrer " + carpetaPaquete + ": " + error);
		}
		return clases;
	}

	private Class<?> resolver(ClassLoader cargador, String nombre, List<String> problemas, String origen) {
		try {
			return Class.forName(nombre, false, cargador);
		} catch (Throwable error) {
			problemas.add("El enlace declarado en " + origen + " apunta a una clase que no existe: " + nombre);
			return null;
		}
	}

	private boolean contiene(String[] valores, String buscado) {
		for (String valor : valores) {
			if (buscado.equals(valor)) {
				return true;
			}
		}
		return false;
	}
}
