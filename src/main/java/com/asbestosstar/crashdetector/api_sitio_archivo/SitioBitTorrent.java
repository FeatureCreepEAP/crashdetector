package com.asbestosstar.crashdetector.api_sitio_archivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.bittorrent.ConfiguracionBitTorrent;
import com.asbestosstar.crashdetector.bittorrent.CreadorTorrentV1;
import com.asbestosstar.crashdetector.bittorrent.MotorBitTorrent;
import com.asbestosstar.crashdetector.bittorrent.SesionTorrent;

/**
 * Adaptador BitTorrent para CompartirInstanciaGUI.
 *
 * Crea un .torrent para el ZIP exportado, empieza a sembrarlo y devuelve el
 * magnet como enlace compartible. La JVM debe permanecer abierta mientras el
 * soporte descarga, especialmente cuando el usuario es el único seeder.
 */
public final class SitioBitTorrent implements SitioDeArchivoAPI {

	@Override
	public String nombre() {
		return "bittorrent";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		return ConfiguracionBitTorrent.obtenerTrackers();
	}

	@Override
	public PoliticaDeRetencion politicaGeneral() {
		return PoliticaDeRetencion.REQUIERE_EMISOR_ACTIVO;
	}

	@Override
	public void validarArchivoZip(Path archivoZip) throws ErrorDeArchivo, ArchivoDemasiadoGrande, ServicioNoSoportado {
		if (archivoZip == null || !Files.isRegularFile(archivoZip) || !Files.isReadable(archivoZip)) {
			throw new ErrorDeArchivo(MonitorDePID.idioma.bittorrentArchivoCompartirNoValido());
		}
		String nombre = archivoZip.getFileName() == null ? "" : archivoZip.getFileName().toString();
		if (!nombre.toLowerCase(java.util.Locale.ROOT).endsWith(".zip")) {
			throw new ErrorDeArchivo(MonitorDePID.idioma.bittorrentArchivoCompartirDebeSerZip());
		}
		try {
			if (Files.size(archivoZip) <= 0L) {
				throw new ErrorDeArchivo(MonitorDePID.idioma.bittorrentOrigenVacio());
			}
		} catch (IOException e) {
			throw new ErrorDeArchivo(MonitorDePID.idioma.bittorrentArchivoCompartirNoValido(), e);
		}
	}

	@Override
	public SesionDeTransferencia publicarArchivoZip(Path archivoZip, ObservadorDeTransferencia observador)
			throws ErrorDeArchivo, ArchivoDemasiadoGrande, ErrorConPublicar, ServicioNoSoportado {
		validarArchivoZip(archivoZip);

		try {
			if (ConfiguracionBitTorrent.obtenerTrackers().isEmpty()) {
				throw new ErrorConPublicar(MonitorDePID.idioma.bittorrentSinTrackersNiDht());
			}
			if (observador != null) {
				observador.alCambiarEstado(EstadoDeTransferencia.CONECTANDO);
				observador.alConocerTamanoTotal(Files.size(archivoZip));
			}

			Path carpeta = Statics.carpeta.resolve("bittorrent");
			Files.createDirectories(carpeta);
			String base = limpiarNombre(archivoZip.getFileName().toString());
			Path torrent = carpeta.resolve(base + "-" + System.currentTimeMillis() + ".torrent");

			CreadorTorrentV1.Resultado creado = CreadorTorrentV1.crear(archivoZip,
					ConfiguracionBitTorrent.obtenerTrackers(), false, (procesados, total) -> {
						if (observador != null) {
							observador.alActualizarProgreso(procesados, total);
						}
					});
			Files.write(torrent, creado.bytesTorrent());

			Path raizAlmacenamiento = archivoZip.toAbsolutePath().normalize().getParent();
			SesionTorrent sesionMotor = MotorBitTorrent.obtenerInstancia().sembrarTorrent(torrent, raizAlmacenamiento,
					creado.nombre());
			Sesion sesion = new Sesion(archivoZip, torrent, creado.magnet(), sesionMotor);

			if (observador != null) {
				observador.alRecibirCodigo(torrent.toAbsolutePath().toString());
				observador.alRecibirEnlace(creado.magnet());
				observador.alCambiarEstado(EstadoDeTransferencia.ESPERANDO_DESCARGA);
				observador.alFinalizar(sesion);
			}
			return sesion;
		} catch (ErrorDeArchivo e) {
			throw e;
		} catch (Exception e) {
			throw new ErrorConPublicar(MonitorDePID.idioma.bittorrentErrorCrearComparticion(), e);
		}
	}

	private static String limpiarNombre(String nombre) {
		String valor = nombre == null ? MonitorDePID.idioma.bittorrentNombreArchivoPorDefecto() : nombre;
		valor = valor.replaceAll("[^a-zA-Z0-9._-]", "_");
		return valor.isEmpty() ? MonitorDePID.idioma.bittorrentNombreArchivoPorDefecto() : valor;
	}

	private static final class Sesion implements SesionDeTransferencia {
		private final String id = UUID.randomUUID().toString();
		private final Path archivo;
		private final Path torrent;
		private final String magnet;
		private final SesionTorrent sesionMotor;
		private final Instant creada = Instant.now();
		private final AtomicBoolean cancelada = new AtomicBoolean(false);

		Sesion(Path archivo, Path torrent, String magnet, SesionTorrent sesionMotor) {
			this.archivo = archivo;
			this.torrent = torrent;
			this.magnet = magnet;
			this.sesionMotor = sesionMotor;
		}

		@Override
		public String id() {
			return id;
		}

		@Override
		public String nombreServicio() {
			return "bittorrent";
		}

		@Override
		public Path archivoZip() {
			return archivo;
		}

		@Override
		public long tamanoBytes() {
			try {
				return Files.size(archivo);
			} catch (IOException e) {
				return 0L;
			}
		}

		@Override
		public ModoDeTransferencia modo() {
			return ModoDeTransferencia.DISTRIBUCION_P2P;
		}

		@Override
		public EstadoDeTransferencia estado() {
			return cancelada.get() ? EstadoDeTransferencia.CANCELADA : EstadoDeTransferencia.ESPERANDO_DESCARGA;
		}

		@Override
		public String enlace() {
			return magnet;
		}

		@Override
		public String codigo() {
			return torrent.toAbsolutePath().toString();
		}

		@Override
		public Instant creadaEn() {
			return creada;
		}

		@Override
		public boolean requiereMantenerSesionAbierta() {
			return true;
		}

		@Override
		public void esperarFinalizacion() {
			/*
			 * La preparación ya terminó. El seeding continúa en MotorBitTorrent y la GUI
			 * puede mostrar el magnet sin bloquearse hasta que el soporte descargue.
			 */
		}

		@Override
		public void cancelar() {
			if (cancelada.compareAndSet(false, true)) {
				sesionMotor.detener();
			}
		}

		@Override
		public void close() {
			cancelar();
		}
	}
}
