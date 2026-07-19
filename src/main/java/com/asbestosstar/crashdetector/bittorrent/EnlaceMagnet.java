package com.asbestosstar.crashdetector.bittorrent;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.asbestosstar.crashdetector.MonitorDePID;

/** Magnet BitTorrent v1 con soporte BTIH hexadecimal y Base32. */
public final class EnlaceMagnet {

	private final byte[] infoHash;
	private final String nombre;
	private final Long longitud;
	private final List<String> trackers;
	private final List<DireccionPeer> paresExplicitos;

	private EnlaceMagnet(byte[] infoHash, String nombre, Long longitud, List<String> trackers,
			List<DireccionPeer> paresExplicitos) {
		this.infoHash = infoHash;
		this.nombre = nombre;
		this.longitud = longitud;
		this.trackers = Collections.unmodifiableList(new ArrayList<String>(trackers));
		this.paresExplicitos = Collections.unmodifiableList(new ArrayList<DireccionPeer>(paresExplicitos));
	}

	public static EnlaceMagnet analizar(String magnet) {
		String valor = magnet == null ? "" : magnet.trim();
		if (!valor.toLowerCase(Locale.ROOT).startsWith("magnet:?")) {
			throw new IllegalArgumentException(MonitorDePID.idioma.bittorrentMagnetNoValido());
		}

		String consulta = valor.substring(valor.indexOf('?') + 1);
		String nombre = null;
		Long longitud = null;
		byte[] infoHash = null;
		Set<String> trackers = new LinkedHashSet<String>();
		Set<DireccionPeer> pares = new LinkedHashSet<DireccionPeer>();

		for (String parametro : consulta.split("&")) {
			if (parametro.isEmpty()) {
				continue;
			}
			int igual = parametro.indexOf('=');
			String clave = igual < 0 ? parametro : parametro.substring(0, igual);
			String contenido = igual < 0 ? "" : parametro.substring(igual + 1);
			String decodificado = UtilBitTorrent.decodificarParametroTexto(contenido);

			if ("xt".equalsIgnoreCase(clave) && decodificado.toLowerCase(Locale.ROOT).startsWith("urn:btih:")) {
				String hashTexto = decodificado.substring("urn:btih:".length()).trim();
				byte[] candidato = hashTexto.length() == 40 ? UtilBitTorrent.desdeHex(hashTexto)
						: UtilBitTorrent.desdeBase32(hashTexto);
				if (candidato.length == 20) {
					infoHash = candidato;
				}
			} else if ("dn".equalsIgnoreCase(clave)) {
				nombre = decodificado;
			} else if ("xl".equalsIgnoreCase(clave)) {
				try {
					longitud = Long.valueOf(decodificado);
				} catch (NumberFormatException ignorado) {
				}
			} else if ("tr".equalsIgnoreCase(clave)) {
				ConfiguracionBitTorrent.validarTracker(decodificado);
				trackers.add(decodificado);
			} else if ("x.pe".equalsIgnoreCase(clave)) {
				DireccionPeer peer = parsearPeer(decodificado);
				if (peer != null) {
					pares.add(peer);
				}
			}
		}

		if (infoHash == null || infoHash.length != 20) {
			throw new IllegalArgumentException(MonitorDePID.idioma.bittorrentMagnetNoValido());
		}
		return new EnlaceMagnet(infoHash, nombre, longitud, new ArrayList<String>(trackers),
				new ArrayList<DireccionPeer>(pares));
	}

	public byte[] infoHash() {
		return infoHash.clone();
	}

	public String infoHashHex() {
		return UtilBitTorrent.hex(infoHash);
	}

	public String nombre() {
		return nombre;
	}

	public Long longitud() {
		return longitud;
	}

	public List<String> trackers() {
		return trackers;
	}

	public List<DireccionPeer> paresExplicitos() {
		return paresExplicitos;
	}

	public EnlaceMagnet conTrackersAdicionales(List<String> adicionales) {
		Set<String> todos = new LinkedHashSet<String>(trackers);
		if (adicionales != null) {
			for (String tracker : adicionales) {
				ConfiguracionBitTorrent.validarTracker(tracker);
				todos.add(tracker);
			}
		}
		return new EnlaceMagnet(infoHash, nombre, longitud, new ArrayList<String>(todos), paresExplicitos);
	}

	public String comoTexto() {
		StringBuilder texto = new StringBuilder("magnet:?xt=urn:btih:");
		texto.append(infoHashHex());
		if (nombre != null && !nombre.isEmpty()) {
			texto.append("&dn=").append(UtilBitTorrent.codificarParametroTexto(nombre));
		}
		if (longitud != null && longitud.longValue() >= 0L) {
			texto.append("&xl=").append(longitud.longValue());
		}
		for (String tracker : trackers) {
			texto.append("&tr=").append(UtilBitTorrent.codificarParametroTexto(tracker));
		}
		for (DireccionPeer peer : paresExplicitos) {
			texto.append("&x.pe=").append(UtilBitTorrent.codificarParametroTexto(peer.toString()));
		}
		return texto.toString();
	}

	private static DireccionPeer parsearPeer(String texto) {
		try {
			URI uri = new URI("tcp://" + texto);
			if (uri.getHost() == null || uri.getPort() <= 0 || uri.getPort() > 65535) {
				return null;
			}
			return new DireccionPeer(uri.getHost(), uri.getPort());
		} catch (Exception e) {
			return null;
		}
	}
}
