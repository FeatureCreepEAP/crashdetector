package com.asbestosstar.crashdetector.heapdump;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Relaciona nombres de clases del heap con los JAR de mods conocidos.
 *
 * Solo indexa las clases que aparecen en el resultado HPROF, por lo que evita
 * conservar en memoria un índice completo de cientos de JAR.
 */
public final class IndiceModsHeap {

	private IndiceModsHeap() {
	}

	public static Map<String, String> resolver(Set<String> clases, AnalizadorHprofRapido.Progreso progreso,
			AtomicBoolean cancelado) {
		Map<String, String> resultado = new HashMap<String, String>();
		Set<String> pendientes = new HashSet<String>();
		Map<String, Set<String>> originalesPorNormalizada = new HashMap<String, Set<String>>();

		for (String clase : clases) {
			String normalizada = normalizarClaseParaJar(clase);
			if (normalizada != null) {
				pendientes.add(normalizada);
				Set<String> originales = originalesPorNormalizada.get(normalizada);
				if (originales == null) {
					originales = new HashSet<String>();
					originalesPorNormalizada.put(normalizada, originales);
				}
				originales.add(clase);
			}
		}

		File archivoLista = MonitorDePID.ultimo_mods.toFile();
		if (!archivoLista.isFile() || pendientes.isEmpty()) {
			return resultado;
		}

		java.util.List<File> jars = leerJars(archivoLista);
		int total = jars.size();

		for (int indice = 0; indice < total && !pendientes.isEmpty(); indice++) {
			comprobarCancelacion(cancelado);
			File jar = jars.get(indice);

			if (progreso != null) {
				progreso.actualizar(90 + (int) Math.min(9L, (indice * 9L) / Math.max(1, total)), jar.getName());
			}

			try (JarFile archivoJar = new JarFile(jar, false)) {
				java.util.Enumeration<JarEntry> entradas = archivoJar.entries();

				while (entradas.hasMoreElements() && !pendientes.isEmpty()) {
					comprobarCancelacion(cancelado);
					JarEntry entrada = entradas.nextElement();
					String nombre = entrada.getName();

					if (entrada.isDirectory() || !nombre.endsWith(".class")
							|| nombre.startsWith("META-INF/versions/")) {
						continue;
					}

					String clase = nombre.substring(0, nombre.length() - 6).replace('/', '.');
					if (pendientes.remove(clase)) {
						Set<String> originales = originalesPorNormalizada.get(clase);
						if (originales != null) {
							for (String original : originales) {
								resultado.put(original, jar.getName());
							}
						}
					}
				}
			} catch (Exception ignorado) {
				/*
				 * Un JAR corrupto no debe impedir el análisis del resto. El escáner principal
				 * ya puede informar por separado de JAR problemáticos.
				 */
			}
		}

		return resultado;
	}

	private static java.util.List<File> leerJars(File archivoLista) {
		java.util.List<File> jars = new java.util.ArrayList<File>();

		try (BufferedReader lector = new BufferedReader(new FileReader(archivoLista))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				String ruta = linea.trim();
				if (ruta.isEmpty()) {
					continue;
				}
				File archivo = new File(ruta);
				if (archivo.isFile() && archivo.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".jar")) {
					jars.add(archivo);
				}
			}
		} catch (Exception ignorado) {
		}

		return jars;
	}

	public static String clasificarBiblioteca(String clase, String jar) {
		if (jar != null && !jar.trim().isEmpty()) {
			return jar;
		}

		String valor = clase == null ? "" : clase;
		if (valor.startsWith("net.minecraft.") || valor.startsWith("com.mojang.")) {
			return MonitorDePID.idioma.heapVisorGrupoMinecraft();
		}
		if (valor.startsWith("net.minecraftforge.") || valor.startsWith("cpw.mods.")) {
			return MonitorDePID.idioma.heapVisorGrupoForge();
		}
		if (valor.startsWith("net.fabricmc.")) {
			return MonitorDePID.idioma.heapVisorGrupoFabric();
		}
		if (valor.startsWith("org.lwjgl.")) {
			return MonitorDePID.idioma.heapVisorGrupoLwjgl();
		}
		if (valor.startsWith("java.") || valor.startsWith("javax.") || valor.startsWith("sun.")
				|| valor.startsWith("jdk.")) {
			return MonitorDePID.idioma.heapVisorGrupoJava();
		}
		if (valor.endsWith("[]")) {
			return MonitorDePID.idioma.heapVisorGrupoArreglos();
		}
		return MonitorDePID.idioma.heapVisorSinMod();
	}

	private static String normalizarClaseParaJar(String clase) {
		if (clase == null) {
			return null;
		}

		String normalizada = clase;
		while (normalizada.endsWith("[]")) {
			normalizada = normalizada.substring(0, normalizada.length() - 2);
		}

		if (normalizada.startsWith("[") || esPrimitivo(normalizada)) {
			return null;
		}
		return normalizada;
	}

	private static boolean esPrimitivo(String nombre) {
		return "boolean".equals(nombre) || "byte".equals(nombre) || "char".equals(nombre) || "short".equals(nombre)
				|| "int".equals(nombre) || "long".equals(nombre) || "float".equals(nombre) || "double".equals(nombre);
	}

	private static void comprobarCancelacion(AtomicBoolean cancelado) {
		if (Thread.currentThread().isInterrupted() || (cancelado != null && cancelado.get())) {
			throw new java.util.concurrent.CancellationException();
		}
	}
}
