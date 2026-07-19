package com.asbestosstar.crashdetector.bittorrent;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigString;

/** Configuración global del cliente BitTorrent puro Java 8. */
public final class ConfiguracionBitTorrent {

	public static final ConfigString TRACKERS = ConfigString.de("bittorrent.trackers", "");

	/*
	 * Se conservan estas claves por compatibilidad con la GUI anterior. Esta
	 * versión sin dependencias no implementa todavía DHT, PEX ni LSD y siempre las
	 * fuerza a false al guardar.
	 */
	public static final ConfigBoolean USAR_DHT = ConfigBoolean.de("bittorrent.usar_dht", false);
	public static final ConfigBoolean USAR_PEX = ConfigBoolean.de("bittorrent.usar_pex", false);
	public static final ConfigBoolean USAR_LSD = ConfigBoolean.de("bittorrent.usar_lsd", false);

	public static final ConfigBoolean SEGUIR_COMPARTIENDO = ConfigBoolean.de("bittorrent.seguir_compartiendo", true);
	public static final ConfigString PUERTO = ConfigString.de("bittorrent.puerto", "6891");
	public static final ConfigString HILOS_HASH = ConfigString.de("bittorrent.hilos_hash",
			String.valueOf(Math.max(1, Runtime.getRuntime().availableProcessors())));
	public static final ConfigString MAXIMO_PARES = ConfigString.de("bittorrent.maximo_pares", "40");
	public static final ConfigString PARES_SIMULTANEOS = ConfigString.de("bittorrent.pares_simultaneos", "8");
	public static final ConfigString SOLICITUDES_POR_PAR = ConfigString.de("bittorrent.solicitudes_por_par", "8");
	public static final ConfigString TIMEOUT_SEGUNDOS = ConfigString.de("bittorrent.timeout_segundos", "20");

	private ConfiguracionBitTorrent() {
	}

	public static List<String> obtenerTrackers() {
		return analizarTrackers(TRACKERS.obtener());
	}

	public static void guardarTrackers(String texto) {
		List<String> trackers = analizarTrackers(texto);
		StringBuilder normalizado = new StringBuilder();
		for (String tracker : trackers) {
			if (normalizado.length() > 0) {
				normalizado.append('\n');
			}
			normalizado.append(tracker);
		}
		TRACKERS.escribir(normalizado.toString());
	}

	public static List<String> analizarTrackers(String texto) {
		Set<String> unicos = new LinkedHashSet<String>();
		if (texto == null || texto.trim().isEmpty()) {
			return new ArrayList<String>();
		}
		String[] lineas = texto.split("[\\r\\n,;]+");
		for (String linea : lineas) {
			String tracker = linea == null ? "" : linea.trim();
			if (tracker.isEmpty() || tracker.startsWith("#")) {
				continue;
			}
			validarTracker(tracker);
			unicos.add(tracker);
		}
		return new ArrayList<String>(unicos);
	}

	public static void validarTracker(String tracker) {
		try {
			URI uri = new URI(tracker);
			String esquema = uri.getScheme() == null ? "" : uri.getScheme().toLowerCase(Locale.ROOT);
			if (!("http".equals(esquema) || "https".equals(esquema) || "udp".equals(esquema)) || uri.getHost() == null
					|| uri.getHost().trim().isEmpty()) {
				throw new IllegalArgumentException(MonitorDePID.idioma.bittorrentTrackerNoValido(tracker));
			}
			if ("udp".equals(esquema) && uri.getPort() <= 0) {
				throw new IllegalArgumentException(MonitorDePID.idioma.bittorrentTrackerNoValido(tracker));
			}
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalArgumentException(MonitorDePID.idioma.bittorrentTrackerNoValido(tracker), e);
		}
	}

	public static int obtenerPuerto() {
		return enteroEntre(PUERTO.obtener(), 1, 65535, 6891);
	}

	public static int obtenerHilosHash() {
		return enteroEntre(HILOS_HASH.obtener(), 1, Math.max(2, Runtime.getRuntime().availableProcessors() * 4),
				Math.max(1, Runtime.getRuntime().availableProcessors()));
	}

	public static int obtenerMaximoPares() {
		return enteroEntre(MAXIMO_PARES.obtener(), 1, 500, 40);
	}

	public static int obtenerParesSimultaneos() {
		return enteroEntre(PARES_SIMULTANEOS.obtener(), 1, 64, 8);
	}

	public static int obtenerSolicitudesPorPar() {
		return enteroEntre(SOLICITUDES_POR_PAR.obtener(), 1, 64, 8);
	}

	public static int obtenerTimeoutMilisegundos() {
		return enteroEntre(TIMEOUT_SEGUNDOS.obtener(), 5, 300, 20) * 1000;
	}

	private static int enteroEntre(String texto, int minimo, int maximo, int porDefecto) {
		try {
			int valor = Integer.parseInt(texto == null ? "" : texto.trim());
			return Math.max(minimo, Math.min(maximo, valor));
		} catch (Exception e) {
			return porDefecto;
		}
	}
}
