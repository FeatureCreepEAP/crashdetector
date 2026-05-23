package com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.analizador.general.LanzerDesAnimado;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

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
	 * Carga los datos iniciales desde los archivos JSON.
	 */
	protected void cargarDatos() {
		this.lanzadores = DetectorLanzer.DETECTORES_DE_LANZERES;
		this.noRecomendados = new LinkedHashMap<String, Map<String, String>>();

		cargarNoRecomendados();
	}

	/**
	 * Carga los lanzadores no recomendados desde el archivo JSON.
	 *
	 * Soporta cualquier código de idioma presente en el archivo. También normaliza
	 * códigos históricos erróneos: kp -> ko jp -> ja
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
							if (valor != null && valor.esObjeto()) {
								Map<String, String> motivos = new LinkedHashMap<String, String>();

								for (String lang : obtenerClaves(valor)) {
									Nodo motivoNode = valor.obtener(lang);
									if (motivoNode != null && !motivoNode.esObjeto() && !motivoNode.esArreglo()) {
										String motivo = motivoNode.comoCadena();
										if (motivo != null && !motivo.trim().isEmpty()) {
											String codigo = normalizarCodigoIdioma(lang);
											if (codigo != null) {
												motivos.put(codigo, motivo);
											}
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
				// Ignorar errores de carga; se mantiene comportamiento previo
			}
		}
	}

	/**
	 * Obtiene las claves de un objeto JSON sin depender de clases internas.
	 */
	protected List<String> obtenerClaves(Nodo nodo) {
		return nodo.claves();
	}

	/**
	 * Guarda los datos en los archivos JSON.
	 */
	protected void guardarDatos() {
		guardarNoRecomendados();
	}

	/**
	 * Guarda los lanzadores no recomendados en el archivo JSON.
	 *
	 * Guarda usando códigos normalizados y permite cualquier idioma presente.
	 */
	private void guardarNoRecomendados() {
		try {
			Nodo raiz = Json.crearObjeto();

			for (Map.Entry<String, Map<String, String>> entry : noRecomendados.entrySet()) {
				String id = entry.getKey();
				Map<String, String> motivos = entry.getValue();

				Nodo objetoLanzador = Json.crearObjeto();
				if (motivos != null) {
					for (Map.Entry<String, String> motivoEntry : motivos.entrySet()) {
						String lang = normalizarCodigoIdioma(motivoEntry.getKey());
						String motivo = motivoEntry.getValue();

						if (lang != null && motivo != null && !motivo.trim().isEmpty()) {
							objetoLanzador.obtener(lang).poner(motivo);
						}
					}
				}

				raiz.obtener(id).poner(objetoLanzador);
			}

			Files.write(archivoNoRecomendados, raiz.escribir().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// Mantener comportamiento silencioso previo
		}
	}

	/**
	 * Obtiene todos los lanzadores disponibles.
	 * 
	 * @return Lista de nombres de lanzadores
	 */
	public List<String> obtenerNombresLanzadores() {
		List<String> nombres = new ArrayList<String>();
		for (DetectorLanzer lanzador : lanzadores) {
			nombres.add(lanzador.id());
		}
		return nombres;
	}

	/**
	 * Obtiene los lanzadores no recomendados actualmente.
	 * 
	 * @return Mapa con nombres de lanzadores y sus motivos por idioma
	 */
	public Map<String, Map<String, String>> obtenerNoRecomendados() {
		Map<String, Map<String, String>> copia = new LinkedHashMap<String, Map<String, String>>();
		for (Map.Entry<String, Map<String, String>> e : noRecomendados.entrySet()) {
			copia.put(e.getKey(), new LinkedHashMap<String, String>(e.getValue()));
		}
		return copia;
	}

	/**
	 * Agrega un lanzador a la lista de no recomendados.
	 * 
	 * @param id      ID del lanzador
	 * @param motivos Mapa de motivos por idioma
	 */
	public void agregarANoRecomendados(String id, Map<String, String> motivos) {
		Map<String, String> normalizados = new LinkedHashMap<String, String>();

		if (motivos != null) {
			for (Map.Entry<String, String> e : motivos.entrySet()) {
				String codigo = normalizarCodigoIdioma(e.getKey());
				String valor = e.getValue();
				if (codigo != null && valor != null && !valor.trim().isEmpty()) {
					normalizados.put(codigo, valor);
				}
			}
		}

		noRecomendados.put(id, normalizados);
	}

	/**
	 * Quita un lanzador de la lista de no recomendados.
	 * 
	 * @param id ID del lanzador
	 */
	public void quitarDeNoRecomendados(String id) {
		noRecomendados.remove(id);
	}

	/**
	 * Obtiene si un lanzador está marcado como desaconsejado por CrashDetector.
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

	/**
	 * Normaliza códigos de idioma y conserva compatibilidad histórica.
	 */
	protected String normalizarCodigoIdioma(String codigo) {
		if (codigo == null) {
			return null;
		}

		String c = codigo.trim().toLowerCase();
		if (c.isEmpty()) {
			return null;
		}

		if ("kp".equals(c)) {
			return "ko";
		}
		if ("jp".equals(c)) {
			return "ja";
		}

		return c;
	}

	/**
	 * Convierte un nombre visible de idioma a código dinámicamente.
	 */
	protected String obtenerCodigoIdiomaDinamico(String nombreVisible) {
		String codigo = Idioma.codigoDesdeNombreVisible(nombreVisible);
		return normalizarCodigoIdioma(codigo);
	}
}