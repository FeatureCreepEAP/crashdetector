package com.asbestosstar.crashdetector.bittorrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Mapea el flujo lógico de un torrent a uno o varios archivos locales.
 *
 * Todas las rutas se normalizan y deben permanecer dentro de la raíz de
 * descarga.
 */
final class AlmacenTorrent {

	private final MetainfoTorrent metainfo;
	private final Path raiz;
	private final List<SegmentoArchivo> segmentos;
	private final Object bloqueoEscritura = new Object();

	AlmacenTorrent(MetainfoTorrent metainfo, Path raiz) throws IOException {
		this.metainfo = metainfo;
		this.raiz = raiz.toAbsolutePath().normalize();
		Files.createDirectories(this.raiz);
		this.segmentos = crearSegmentos();
	}

	Path raiz() {
		return raiz;
	}

	private List<SegmentoArchivo> crearSegmentos() throws IOException {
		List<SegmentoArchivo> resultado = new ArrayList<SegmentoArchivo>();
		boolean multifile = metainfo.archivos().size() > 1
				|| !metainfo.archivos().get(0).partesRuta().get(0).equals(metainfo.nombre());
		Path base = multifile ? resolverSeguro(raiz, metainfo.nombre()) : raiz;
		if (multifile) {
			Files.createDirectories(base);
		}

		for (MetainfoTorrent.ArchivoTorrent archivo : metainfo.archivos()) {
			Path ruta = base;
			for (String parte : archivo.partesRuta()) {
				ruta = resolverSeguro(ruta, parte);
			}
			Path padre = ruta.getParent();
			if (padre != null) {
				Files.createDirectories(padre);
			}
			resultado.add(new SegmentoArchivo(ruta, archivo.desplazamiento(), archivo.longitud()));
		}
		return resultado;
	}

	private static Path resolverSeguro(Path base, String parte) throws IOException {
		Path normalizado = base.resolve(parte).normalize();
		if (!normalizado.startsWith(base.normalize())) {
			throw new IOException(MonitorDePID.idioma.bittorrentTorrentNoValido());
		}
		return normalizado;
	}

	BitSet verificarExistentes(int hilos, ProgresoVerificacion progreso) throws Exception {
		final BitSet completos = new BitSet(metainfo.cantidadPiezas());
		java.util.concurrent.ExecutorService ejecutor = java.util.concurrent.Executors
				.newFixedThreadPool(Math.max(1, hilos));
		final java.util.concurrent.atomic.AtomicInteger siguiente = new java.util.concurrent.atomic.AtomicInteger();
		final java.util.concurrent.atomic.AtomicInteger revisadas = new java.util.concurrent.atomic.AtomicInteger();
		List<java.util.concurrent.Future<?>> futuros = new ArrayList<java.util.concurrent.Future<?>>();

		for (int i = 0; i < Math.max(1, hilos); i++) {
			futuros.add(ejecutor.submit(new Runnable() {
				@Override
				public void run() {
					while (true) {
						int pieza = siguiente.getAndIncrement();
						if (pieza >= metainfo.cantidadPiezas()) {
							return;
						}
						try {
							if (verificarPieza(pieza)) {
								synchronized (completos) {
									completos.set(pieza);
								}
							}
						} catch (IOException ignorado) {
						}
						int cantidad = revisadas.incrementAndGet();
						if (progreso != null) {
							progreso.actualizar(cantidad, metainfo.cantidadPiezas());
						}
					}
				}
			}));
		}
		for (java.util.concurrent.Future<?> futuro : futuros) {
			futuro.get();
		}
		ejecutor.shutdown();
		return completos;
	}

	boolean verificarPieza(int indice) throws IOException {
		int longitud = metainfo.longitudPieza(indice);
		byte[] datos = leer((long) indice * metainfo.tamanoPieza(), longitud, false);
		if (datos == null || datos.length != longitud) {
			return false;
		}
		try {
			byte[] hash = MessageDigest.getInstance("SHA-1").digest(datos);
			return UtilBitTorrent.igualesConstante(hash, metainfo.hashPieza(indice));
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	byte[] leerPieza(int indice) throws IOException {
		return leer((long) indice * metainfo.tamanoPieza(), metainfo.longitudPieza(indice), true);
	}

	byte[] leerBloque(int indicePieza, int inicio, int longitud) throws IOException {
		if (inicio < 0 || longitud < 0 || inicio + longitud < inicio
				|| inicio + longitud > metainfo.longitudPieza(indicePieza)) {
			throw new IOException();
		}
		return leer((long) indicePieza * metainfo.tamanoPieza() + inicio, longitud, true);
	}

	void escribirPieza(int indice, byte[] datos) throws IOException {
		if (datos == null || datos.length != metainfo.longitudPieza(indice)) {
			throw new IOException();
		}
		escribir((long) indice * metainfo.tamanoPieza(), datos);
	}

	private byte[] leer(long desplazamiento, int longitud, boolean exigirCompleto) throws IOException {
		byte[] salida = new byte[longitud];
		int escrito = 0;
		long actual = desplazamiento;
		for (SegmentoArchivo segmento : segmentos) {
			if (actual >= segmento.fin() || actual + (longitud - escrito) <= segmento.inicio) {
				continue;
			}
			long inicioEnSegmento = Math.max(0L, actual - segmento.inicio);
			int cantidad = (int) Math.min(longitud - escrito, segmento.longitud - inicioEnSegmento);
			if (!Files.isRegularFile(segmento.ruta)) {
				return exigirCompleto ? null : null;
			}
			try (RandomAccessFile archivo = new RandomAccessFile(segmento.ruta.toFile(), "r")) {
				if (archivo.length() < inicioEnSegmento + cantidad) {
					return null;
				}
				archivo.seek(inicioEnSegmento);
				archivo.readFully(salida, escrito, cantidad);
			}
			escrito += cantidad;
			actual += cantidad;
			if (escrito == longitud) {
				return salida;
			}
		}
		return null;
	}

	private void escribir(long desplazamiento, byte[] datos) throws IOException {
		synchronized (bloqueoEscritura) {
			int leido = 0;
			long actual = desplazamiento;
			for (SegmentoArchivo segmento : segmentos) {
				if (actual >= segmento.fin() || actual + (datos.length - leido) <= segmento.inicio) {
					continue;
				}
				long inicioEnSegmento = Math.max(0L, actual - segmento.inicio);
				int cantidad = (int) Math.min(datos.length - leido, segmento.longitud - inicioEnSegmento);
				Path padre = segmento.ruta.getParent();
				if (padre != null) {
					Files.createDirectories(padre);
				}
				try (RandomAccessFile archivo = new RandomAccessFile(segmento.ruta.toFile(), "rw")) {
					archivo.seek(inicioEnSegmento);
					archivo.write(datos, leido, cantidad);
				}
				leido += cantidad;
				actual += cantidad;
				if (leido == datos.length) {
					return;
				}
			}
			throw new IOException();
		}
	}

	long bytesCompletos(BitSet piezas) {
		long total = 0L;
		for (int i = piezas.nextSetBit(0); i >= 0; i = piezas.nextSetBit(i + 1)) {
			total += metainfo.longitudPieza(i);
		}
		return total;
	}

	interface ProgresoVerificacion {
		void actualizar(int revisadas, int total);
	}

	private static final class SegmentoArchivo {
		final Path ruta;
		final long inicio;
		final long longitud;

		SegmentoArchivo(Path ruta, long inicio, long longitud) {
			this.ruta = ruta;
			this.inicio = inicio;
			this.longitud = longitud;
		}

		long fin() {
			return inicio + longitud;
		}
	}
}
