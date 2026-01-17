package com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.analizador.general.LanzerDesAnimado;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

public abstract class LanzerMaloGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {
	public static Map<String, Supplier<LanzerMaloGUI>> GUIS = new HashMap<String, Supplier<LanzerMaloGUI>>();

	protected List<DetectorLanzer> lanzadores;
	protected Map<String, Map<String, String>> noRecomendados;

	protected Path archivoNoRecomendados = LanzerDesAnimado.ARCHIVO_DESANIMADOS;

	/**
	 * NOSOTROS dice es malo, pero la corporacion es diferente
	 * 
	 * @return true si CrashDetector desaconseja este lanzador
	 */
	public static boolean nosotrosDiceEsMalo(DetectorLanzer lanzer) {
		return lanzer.desanimado();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.LANZER_MALO;
	}

	/**
	 * Carga los datos iniciales desde los archivos JSON
	 */
	protected void cargarDatos() {
		this.lanzadores = DetectorLanzer.DETECTORES_DE_LANZERES;
		this.noRecomendados = new HashMap<>();

		cargarNoRecomendados();
	}

	/**
	 * Carga los lanzadores no recomendados desde el archivo JSON
	 */
	private void cargarNoRecomendados() {
		if (archivoNoRecomendados.toFile().exists() && archivoNoRecomendados.toFile().length() > 0) {
			try {
				String contenido = new String(Files.readAllBytes(archivoNoRecomendados), StandardCharsets.UTF_8);
				if (contenido != null && !contenido.trim().isEmpty()) {
					Nodo raiz = Json.leer(contenido);
					if (raiz.esObjeto()) {
						for (String key : obtenerClaves(raiz)) {
							Nodo valor = raiz.obtener(key);
							if (valor.esObjeto()) {
								Map<String, String> motivos = new HashMap<>();
								for (String lang : obtenerClaves(valor)) {
									Nodo motivoNode = valor.obtener(lang);
									if (motivoNode != null && !motivoNode.esObjeto() && !motivoNode.esArreglo()) {
										String motivo = motivoNode.comoCadena();
										if (motivo != null && !motivo.trim().isEmpty()) {
											motivos.put(lang, motivo);
										}
									}
								}
								if (!noRecomendados.containsKey(key)) {
									noRecomendados.put(key, motivos);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// Ignorar errores
			}
		}
	}

	/**
	 * Obtiene las claves de un objeto JSON
	 */
	/**
	 * Obtiene las claves de un objeto JSON sin depender de clases internas
	 * (Gson/DMR).
	 */
	protected List<String> obtenerClaves(Nodo nodo) {
		return nodo.claves();
	}

	/**
	 * Guarda los datos en los archivos JSON
	 */
	protected void guardarDatos() {
		guardarNoRecomendados();
	}

	/**
	 * Guarda los lanzadores no recomendados en el archivo JSON
	 */
	private void guardarNoRecomendados() {
		try {
			// Crear un objeto vacío
			Nodo raiz = Json.crearObjeto();
			for (Map.Entry<String, Map<String, String>> entry : noRecomendados.entrySet()) {
				String id = entry.getKey();
				Map<String, String> motivos = entry.getValue();

				Nodo objetoLanzador = Json.crearObjeto();
				for (Map.Entry<String, String> motivoEntry : motivos.entrySet()) {
					String lang = motivoEntry.getKey();
					String motivo = motivoEntry.getValue();
					objetoLanzador.obtener(lang).poner(motivo);
				}
				raiz.obtener(id).poner(objetoLanzador);
			}

			Files.write(archivoNoRecomendados, raiz.escribir().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// Manejar error
		}
	}

	/**
	 * Obtiene todos los lanzadores disponibles
	 * 
	 * @return Lista de nombres de lanzadores
	 */
	public List<String> obtenerNombresLanzadores() {
		List<String> nombres = new ArrayList<>();
		for (DetectorLanzer lanzador : lanzadores) {
			nombres.add(lanzador.id());
		}
		return nombres;
	}

	/**
	 * Obtiene los lanzadores no recomendados actualmente
	 * 
	 * @return Mapa con nombres de lanzadores y sus motivos por idioma
	 */
	public Map<String, Map<String, String>> obtenerNoRecomendados() {
		return new HashMap<>(noRecomendados);
	}

	/**
	 * Agrega un lanzador a la lista de no recomendados
	 * 
	 * @param id      ID del lanzador
	 * @param motivos Mapa de motivos por idioma
	 */
	public void agregarANoRecomendados(String id, Map<String, String> motivos) {
		noRecomendados.put(id, motivos);
	}

	/**
	 * Quita un lanzador de la lista de no recomendados
	 * 
	 * @param id ID del lanzador
	 */
	public void quitarDeNoRecomendados(String id) {
		noRecomendados.remove(id);
	}

	/**
	 * Obtiene si un lanzador está marcado como desaconsejado por CrashDetector
	 * 
	 * @param id ID del lanzador
	 * @return true si CrashDetector lo desaconseja
	 */
	public boolean esDesaconsejadoPorCrashDetector(String id) {
		for (DetectorLanzer lanzador : lanzadores) {
			if (lanzador.id().equals(id)) {
				return nosotrosDiceEsMalo(lanzador);
			}
		}
		return false;
	}
}