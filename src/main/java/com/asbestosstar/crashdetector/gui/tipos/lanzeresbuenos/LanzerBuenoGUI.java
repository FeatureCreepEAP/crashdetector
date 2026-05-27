package com.asbestosstar.crashdetector.gui.tipos.lanzeresbuenos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.analizador.general.LanzerNoAnimado;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI base para gestionar lanzadores "animados" (recomendados/alentados).
 * Formato JSON: arreglo de strings, por ejemplo:
 * ["prismlauncher","tlauncher","minecraft_server"]
 *
 * No hay razones por idioma; solo IDs.
 */
public abstract class LanzerBuenoGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {
	public static final long serialVersionUID = 1L;

	public static Map<String, Supplier<LanzerBuenoGUI>> GUIS = new HashMap<String, Supplier<LanzerBuenoGUI>>();

	public List<DetectorLanzer> lanzadores;

	/** Conjunto de IDs recomendados (archivo JSON: arreglo de strings). */
	public Set<String> recomendados;

	/** Archivo de recomendados: se reutiliza el mismo que usa LanzerNoAnimado. */
	public Path archivoRecomendados = LanzerNoAnimado.ARCHIVO_ANIMADOS;

	/**
	 * NOSOTROS dice es bueno.
	 * 
	 * @return true si CrashDetector alienta este lanzador
	 */
	public static boolean nosotrosDiceEsBueno(DetectorLanzer lanzer) {
		return lanzer.animado();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.LANZER_BUENO;
	}

	/**
	 * Carga datos iniciales desde el archivo JSON.
	 */
	public void cargarDatos() {
		this.lanzadores = DetectorLanzer.DETECTORES_DE_LANZERES;
		this.recomendados = new HashSet<>();
		cargarRecomendadosDesdeArchivo();
	}

	/**
	 * Lee el archivo JSON (arreglo de strings) y llena el conjunto.
	 */
	public void cargarRecomendadosDesdeArchivo() {
		if (archivoRecomendados == null || !archivoRecomendados.toFile().exists()
				|| archivoRecomendados.toFile().length() <= 0) {
			return;
		}

		try {
			String contenido = new String(Files.readAllBytes(archivoRecomendados), StandardCharsets.UTF_8);
			if (contenido == null || contenido.trim().isEmpty()) {
				return;
			}

			Nodo raiz = Json.leer(contenido);
			if (raiz == null || !raiz.esArreglo()) {
				return;
			}

			int tam = raiz.tamano();
			for (int i = 0; i < tam; i++) {
				Nodo item = raiz.en(i);
				if (item != null && !item.esObjeto() && !item.esArreglo()) {
					String id = item.comoCadena();
					if (id != null) {
						id = id.trim();
						if (!id.isEmpty()) {
							recomendados.add(id);
						}
					}
				}
			}
		} catch (Exception ignored) {
			// Ignorar errores de lectura/parseo
		}
	}

	/**
	 * Guarda el conjunto de recomendados al archivo JSON como arreglo de strings.
	 */
	public void guardarDatos() {
		try {
			// Como Json no expone crearArreglo(), usamos leer("[]") y agregamos
			Nodo raiz = Json.leer("[]");
			for (String id : recomendados) {
				if (id != null) {
					String limpio = id.trim();
					if (!limpio.isEmpty()) {
						raiz.agregar(limpio);
					}
				}
			}
			Files.write(archivoRecomendados, raiz.escribir().getBytes(StandardCharsets.UTF_8));
		} catch (IOException ignored) {
			// Ignorar errores
		}
	}

	/**
	 * Devuelve todos los IDs de lanzadores conocidos por el sistema.
	 */
	public List<String> obtenerNombresLanzadores() {
		java.util.ArrayList<String> nombres = new java.util.ArrayList<>();
		for (DetectorLanzer lanzador : lanzadores) {
			nombres.add(lanzador.id());
		}
		return nombres;
	}

	/**
	 * Agrega un ID al conjunto de recomendados.
	 */
	public void agregarARecomendados(String id) {
		if (id == null)
			return;
		String limpio = id.trim();
		if (!limpio.isEmpty()) {
			recomendados.add(limpio);
		}
	}

	/**
	 * Quita un ID del conjunto de recomendados.
	 */
	public void quitarDeRecomendados(String id) {
		if (id == null)
			return;
		recomendados.remove(id.trim());
	}

	/**
	 * Devuelve true si un ID está marcado como "bueno" por CrashDetector (animado),
	 * usando LanzerBuenoGUI.nosotrosDiceEsBueno(lanzer).
	 */
	public boolean esRecomendadoPorCrashDetector(String id) {
		if (id == null)
			return false;
		for (DetectorLanzer lanzador : lanzadores) {
			if (lanzador != null && id.equals(lanzador.id())) {
				return nosotrosDiceEsBueno(lanzador);
			}
		}
		return false;
	}
}
