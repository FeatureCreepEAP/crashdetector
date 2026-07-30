package com.asbestosstar.crashdetector.universalator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.asbestosstar.crashdetector.Statics;

/**
 * Escáner ligero inspirado en la idea de Universalator para localizar mods que
 * parecen ser solamente del cliente.
 */
public class EscanerUniversalator {

	private static final String[] HEURISTICAS_NOMBRE = new String[] { "modmenu", "embeddiumplus", "oculus", "sodium",
			"iris", "journeymap", "xaeros", "inventoryhud", "appleskin", "neat", "controlling", "shulkerboxtooltip",
			"entityculling", "reeses_sodium_options", "lambdynamiclights", "fancymenu", "drippyloading",
			"notenoughanimations", "emi", "rei", "jei" };

	public List<ResultadoEscaneoUniversalator> escanear() throws IOException {
		List<ResultadoEscaneoUniversalator> resultados = new ArrayList<ResultadoEscaneoUniversalator>();

		if (Statics.carpetas_de_mods == null) {
			return resultados;
		}

		for (Path carpeta : Statics.carpetas_de_mods) {
			if (carpeta == null || !Files.isDirectory(carpeta)) {
				continue;
			}

			java.io.File[] archivos = carpeta.toFile().listFiles();
			if (archivos == null) {
				continue;
			}

			for (java.io.File archivo : archivos) {
				if (archivo == null || !archivo.isFile()) {
					continue;
				}
				String nombre = archivo.getName().toLowerCase(Locale.ROOT);
				if (!nombre.endsWith(".jar")) {
					continue;
				}
				ResultadoEscaneoUniversalator resultado = analizarMod(archivo.toPath());
				if (resultado != null && resultado.tieneRazones()) {
					resultados.add(resultado);
				}
			}
		}

		Collections.sort(resultados);
		return resultados;
	}

	private ResultadoEscaneoUniversalator analizarMod(Path archivo) {
		String nombre = archivo.getFileName().toString();
		ResultadoEscaneoUniversalator resultado = new ResultadoEscaneoUniversalator(archivo, nombre);
		String nombreMin = nombre.toLowerCase(Locale.ROOT);

		for (String patron : HEURISTICAS_NOMBRE) {
			if (nombreMin.contains(patron)) {
				resultado.agregarRazon(ResultadoEscaneoUniversalator.RAZON_HEURISTICA_NOMBRE);
				resultado.incrementarConfianza(25);
				break;
			}
		}

		ZipFile zip = null;
		try {
			zip = new ZipFile(archivo.toFile());
			leerMetadata(zip, "fabric.mod.json", resultado);
			leerMetadata(zip, "quilt.mod.json", resultado);
			leerMetadata(zip, "META-INF/mods.toml", resultado);
			leerMetadata(zip, "META-INF/neoforge.mods.toml", resultado);
			leerMetadata(zip, "mcmod.info", resultado);
		} catch (Exception e) {
			// ignorar un jar corrupto o no estándar
		} finally {
			if (zip != null) {
				try {
					zip.close();
				} catch (IOException ignored) {
				}
			}
		}

		return resultado.tieneRazones() ? resultado : null;
	}

	private void leerMetadata(ZipFile zip, String entryName, ResultadoEscaneoUniversalator resultado)
			throws IOException {
		ZipEntry entry = zip.getEntry(entryName);
		if (entry == null) {
			return;
		}
		String txt = leerEntrada(zip, entry);
		if (txt == null || txt.trim().isEmpty()) {
			return;
		}
		String x = txt.toLowerCase(Locale.ROOT);

		if (x.contains("\"environment\"") && x.contains("client")) {
			resultado.agregarRazon(ResultadoEscaneoUniversalator.RAZON_ENTORNO_CLIENTE);
			resultado.incrementarConfianza(55);
		}
		if (x.contains("\"side\"") && x.contains("client")) {
			resultado.agregarRazon(ResultadoEscaneoUniversalator.RAZON_METADATOS_CLIENTE);
			resultado.incrementarConfianza(45);
		}
		if (x.contains("client_side_only") || x.contains("clientsideonly") || x.contains("displaytest")
				|| x.contains("client_only") || x.contains("client-only")) {
			resultado.agregarRazon(ResultadoEscaneoUniversalator.RAZON_METADATOS_CLIENTE);
			resultado.incrementarConfianza(35);
		}
		if (x.contains("modmenu") || x.contains("journeymap") || x.contains("xaero") || x.contains("appleskin")
				|| x.contains("fancymenu") || x.contains("rei") || x.contains("jei") || x.contains("emi")) {
			resultado.agregarRazon(ResultadoEscaneoUniversalator.RAZON_HEURISTICA_NOMBRE);
			resultado.incrementarConfianza(15);
		}
	}

	private String leerEntrada(ZipFile zip, ZipEntry entry) throws IOException {
		InputStream in = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			in = zip.getInputStream(entry);
			byte[] buffer = new byte[4096];
			int total = 0;
			int leidos;
			while ((leidos = in.read(buffer)) >= 0 && total < 1024 * 1024) {
				out.write(buffer, 0, leidos);
				total += leidos;
			}
			return new String(out.toByteArray(), StandardCharsets.UTF_8);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignored) {
				}
			}
		}
	}
}
