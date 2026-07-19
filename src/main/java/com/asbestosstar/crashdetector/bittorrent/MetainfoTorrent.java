package com.asbestosstar.crashdetector.bittorrent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Metainfo BitTorrent v1 inmutable.
 *
 * Conserva exactamente los bytes bencode del diccionario info, ya que el
 * info-hash es SHA-1 sobre esos bytes y no sobre una recodificación
 * equivalente.
 */
public final class MetainfoTorrent {

	private final byte[] torrentBytes;
	private final byte[] infoBytes;
	private final Map<String, Object> raiz;
	private final Map<String, Object> info;
	private final String nombre;
	private final int tamanoPieza;
	private final byte[] hashesPiezas;
	private final List<ArchivoTorrent> archivos;
	private final List<String> trackers;
	private final long longitudTotal;
	private final boolean privado;

	private MetainfoTorrent(byte[] torrentBytes, byte[] infoBytes, Map<String, Object> raiz, Map<String, Object> info,
			String nombre, int tamanoPieza, byte[] hashesPiezas, List<ArchivoTorrent> archivos, List<String> trackers,
			long longitudTotal, boolean privado) {
		this.torrentBytes = torrentBytes;
		this.infoBytes = infoBytes;
		this.raiz = raiz;
		this.info = info;
		this.nombre = nombre;
		this.tamanoPieza = tamanoPieza;
		this.hashesPiezas = hashesPiezas;
		this.archivos = Collections.unmodifiableList(new ArrayList<ArchivoTorrent>(archivos));
		this.trackers = Collections.unmodifiableList(new ArrayList<String>(trackers));
		this.longitudTotal = longitudTotal;
		this.privado = privado;
	}

	public static MetainfoTorrent leer(Path archivo) throws IOException {
		if (archivo == null || !Files.isRegularFile(archivo) || !Files.isReadable(archivo)) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}
		return desdeBytes(Files.readAllBytes(archivo));
	}

	public static MetainfoTorrent desdeBytes(byte[] datos) throws IOException {
		if (datos == null || datos.length < 4) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}

		ResultadoRaiz resultado;
		try {
			resultado = leerRaizConInfoCrudo(datos);
		} catch (IOException e) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido(), e);
		}

		if (resultado.inicioInfo < 0 || resultado.finInfo <= resultado.inicioInfo) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentSinInfo());
		}

		byte[] infoCrudo = new byte[resultado.finInfo - resultado.inicioInfo];
		System.arraycopy(datos, resultado.inicioInfo, infoCrudo, 0, infoCrudo.length);
		return construir(datos.clone(), infoCrudo, resultado.raiz);
	}

	/**
	 * Construye un metainfo completo con un diccionario info recibido mediante BEP
	 * 9. Los trackers proceden del magnet.
	 */
	public static MetainfoTorrent desdeInfoBytes(byte[] infoBytes, List<String> trackers) throws IOException {
		if (infoBytes == null || infoBytes.length == 0) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentSinInfo());
		}
		Object valor;
		try {
			valor = Bencode.leer(infoBytes);
		} catch (IOException e) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido(), e);
		}
		if (!(valor instanceof Map<?, ?>)) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentSinInfo());
		}

		Map<String, Object> raiz = new LinkedHashMap<String, Object>();
		List<String> listaTrackers = trackers == null ? Collections.<String>emptyList() : trackers;
		if (!listaTrackers.isEmpty()) {
			raiz.put("announce", listaTrackers.get(0));
			List<Object> niveles = new ArrayList<Object>();
			for (String tracker : listaTrackers) {
				List<Object> nivel = new ArrayList<Object>();
				nivel.add(tracker);
				niveles.add(nivel);
			}
			raiz.put("announce-list", niveles);
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> info = (Map<String, Object>) valor;
		raiz.put("info", info);
		byte[] torrent = Bencode.codificar(raiz);
		return construir(torrent, infoBytes.clone(), raiz);
	}

	@SuppressWarnings("unchecked")
	private static MetainfoTorrent construir(byte[] torrentBytes, byte[] infoBytes, Map<String, Object> raiz)
			throws IOException {
		Object infoObjeto = raiz.get("info");
		if (!(infoObjeto instanceof Map<?, ?>)) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentSinInfo());
		}
		Map<String, Object> info = (Map<String, Object>) infoObjeto;
		String nombre = texto(preferir(info, "name.utf-8", "name"));
		validarParteRuta(nombre);

		long piezaLarga = numero(info.get("piece length"), -1L);
		if (piezaLarga <= 0L || piezaLarga > 64L * 1024L * 1024L) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}
		int tamanoPieza = (int) piezaLarga;

		byte[] hashes = bytes(info.get("pieces"));
		if (hashes == null || hashes.length == 0 || hashes.length % 20 != 0) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}

		List<ArchivoTorrent> archivos = new ArrayList<ArchivoTorrent>();
		long total = 0L;
		Object archivosObjeto = info.get("files");
		if (archivosObjeto instanceof List<?>) {
			for (Object elemento : (List<?>) archivosObjeto) {
				if (!(elemento instanceof Map<?, ?>)) {
					throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
				}
				Map<String, Object> archivo = (Map<String, Object>) elemento;
				long longitud = numero(archivo.get("length"), -1L);
				if (longitud < 0L) {
					throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
				}
				Object rutaObjeto = preferir(archivo, "path.utf-8", "path");
				if (!(rutaObjeto instanceof List<?>)) {
					throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
				}
				List<String> partes = new ArrayList<String>();
				for (Object parte : (List<?>) rutaObjeto) {
					String valor = texto(parte);
					validarParteRuta(valor);
					partes.add(valor);
				}
				if (partes.isEmpty()) {
					throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
				}
				archivos.add(new ArchivoTorrent(partes, longitud, total));
				total = sumarSeguro(total, longitud);
			}
		} else {
			long longitud = numero(info.get("length"), -1L);
			if (longitud < 0L) {
				throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
			}
			archivos.add(new ArchivoTorrent(Collections.singletonList(nombre), longitud, 0L));
			total = longitud;
		}

		if (total <= 0L) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}
		long piezasEsperadas = (total + tamanoPieza - 1L) / tamanoPieza;
		if (piezasEsperadas != hashes.length / 20L) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}

		List<String> trackers = extraerTrackers(raiz);
		boolean privado = numero(info.get("private"), 0L) == 1L;
		return new MetainfoTorrent(torrentBytes, infoBytes, raiz, info, nombre, tamanoPieza, hashes.clone(), archivos,
				trackers, total, privado);
	}

	public String nombre() {
		return nombre;
	}

	public int tamanoPieza() {
		return tamanoPieza;
	}

	public int cantidadPiezas() {
		return hashesPiezas.length / 20;
	}

	public int longitudPieza(int indice) {
		if (indice < 0 || indice >= cantidadPiezas()) {
			throw new IndexOutOfBoundsException();
		}
		if (indice == cantidadPiezas() - 1) {
			long resto = longitudTotal - ((long) indice * tamanoPieza);
			return (int) resto;
		}
		return tamanoPieza;
	}

	public byte[] hashPieza(int indice) {
		if (indice < 0 || indice >= cantidadPiezas()) {
			throw new IndexOutOfBoundsException();
		}
		byte[] hash = new byte[20];
		System.arraycopy(hashesPiezas, indice * 20, hash, 0, 20);
		return hash;
	}

	public long longitudTotal() {
		return longitudTotal;
	}

	public List<ArchivoTorrent> archivos() {
		return archivos;
	}

	public List<String> trackers() {
		return trackers;
	}

	public boolean esPrivado() {
		return privado;
	}

	public byte[] infoBytes() {
		return infoBytes.clone();
	}

	public byte[] torrentBytes() {
		return torrentBytes.clone();
	}

	public byte[] infoHash() {
		try {
			return MessageDigest.getInstance("SHA-1").digest(infoBytes);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public String infoHashHex() {
		return UtilBitTorrent.hex(infoHash());
	}

	public String crearMagnet(List<String> trackersAdicionales) {
		Set<String> todos = new LinkedHashSet<String>(trackers);
		if (trackersAdicionales != null) {
			todos.addAll(trackersAdicionales);
		}
		StringBuilder magnet = new StringBuilder("magnet:?xt=urn:btih:");
		magnet.append(infoHashHex());
		magnet.append("&dn=").append(UtilBitTorrent.codificarParametroTexto(nombre));
		magnet.append("&xl=").append(longitudTotal);
		for (String tracker : todos) {
			magnet.append("&tr=").append(UtilBitTorrent.codificarParametroTexto(tracker));
		}
		return magnet.toString();
	}

	public static String agregarTrackersAMagnet(String magnet, List<String> trackers) {
		EnlaceMagnet enlace = EnlaceMagnet.analizar(magnet);
		return enlace.conTrackersAdicionales(trackers).comoTexto();
	}

	public static void validarMagnet(String magnet) {
		EnlaceMagnet.analizar(magnet);
	}

	private static List<String> extraerTrackers(Map<String, Object> raiz) {
		Set<String> resultado = new LinkedHashSet<String>();
		agregarTracker(resultado, raiz.get("announce"));
		Object lista = raiz.get("announce-list");
		if (lista instanceof List<?>) {
			for (Object nivel : (List<?>) lista) {
				if (nivel instanceof List<?>) {
					for (Object tracker : (List<?>) nivel) {
						agregarTracker(resultado, tracker);
					}
				} else {
					agregarTracker(resultado, nivel);
				}
			}
		}
		return new ArrayList<String>(resultado);
	}

	private static void agregarTracker(Set<String> resultado, Object valor) {
		String texto = textoNullable(valor);
		if (texto != null && !texto.trim().isEmpty()) {
			resultado.add(texto.trim());
		}
	}

	private static Object preferir(Map<String, Object> mapa, String primero, String segundo) {
		Object valor = mapa.get(primero);
		return valor != null ? valor : mapa.get(segundo);
	}

	private static String texto(Object valor) throws IOException {
		String texto = textoNullable(valor);
		if (texto == null || texto.isEmpty()) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}
		return texto;
	}

	private static String textoNullable(Object valor) {
		if (valor instanceof byte[]) {
			return new String((byte[]) valor, StandardCharsets.UTF_8);
		}
		if (valor instanceof String) {
			return (String) valor;
		}
		return null;
	}

	private static byte[] bytes(Object valor) {
		return valor instanceof byte[] ? (byte[]) valor : null;
	}

	private static long numero(Object valor, long porDefecto) {
		return valor instanceof Number ? ((Number) valor).longValue() : porDefecto;
	}

	private static long sumarSeguro(long a, long b) throws IOException {
		long resultado = a + b;
		if (b < 0 || resultado < a) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}
		return resultado;
	}

	private static void validarParteRuta(String parte) throws IOException {
		if (parte == null || parte.isEmpty() || ".".equals(parte) || "..".equals(parte) || parte.indexOf('/') >= 0
				|| parte.indexOf('\\') >= 0 || parte.indexOf('\0') >= 0 || parte.indexOf(':') >= 0) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}
	}

	private static ResultadoRaiz leerRaizConInfoCrudo(byte[] datos) throws IOException {
		if (datos[0] != 'd') {
			throw new IOException();
		}
		int posicion = 1;
		Map<String, Object> raiz = new LinkedHashMap<String, Object>();
		int inicioInfo = -1;
		int finInfo = -1;
		while (posicion < datos.length && datos[posicion] != 'e') {
			Bencode.Resultado claveResultado = Bencode.leerUno(datos, posicion);
			if (!(claveResultado.valor() instanceof byte[])) {
				throw new IOException();
			}
			String clave = new String((byte[]) claveResultado.valor(), StandardCharsets.UTF_8);
			posicion = claveResultado.siguiente();
			int inicio = posicion;
			Bencode.Resultado valorResultado = Bencode.leerUno(datos, posicion);
			posicion = valorResultado.siguiente();
			raiz.put(clave, valorResultado.valor());
			if ("info".equals(clave)) {
				inicioInfo = inicio;
				finInfo = posicion;
			}
		}
		if (posicion >= datos.length || datos[posicion] != 'e' || posicion + 1 != datos.length) {
			throw new IOException();
		}
		return new ResultadoRaiz(raiz, inicioInfo, finInfo);
	}

	private static final class ResultadoRaiz {
		final Map<String, Object> raiz;
		final int inicioInfo;
		final int finInfo;

		ResultadoRaiz(Map<String, Object> raiz, int inicioInfo, int finInfo) {
			this.raiz = raiz;
			this.inicioInfo = inicioInfo;
			this.finInfo = finInfo;
		}
	}

	public static final class ArchivoTorrent {
		private final List<String> partesRuta;
		private final long longitud;
		private final long desplazamiento;

		ArchivoTorrent(List<String> partesRuta, long longitud, long desplazamiento) {
			this.partesRuta = Collections.unmodifiableList(new ArrayList<String>(partesRuta));
			this.longitud = longitud;
			this.desplazamiento = desplazamiento;
		}

		public List<String> partesRuta() {
			return partesRuta;
		}

		public long longitud() {
			return longitud;
		}

		public long desplazamiento() {
			return desplazamiento;
		}
	}
}
