package com.asbestosstar.crashdetector.api_sito_archivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementación inicial para wormhole.app.
 *
 * IMPORTANTE: - Esta clase por ahora define la API y el comportamiento
 * esperado. - La integración real de red/transferencia queda para después.
 *
 * Reglas actuales de esta implementación:
 *
 * - Hasta 5 GiB: Se considera una subida "normal". El emisor no necesita
 * permanecer abierto después de iniciar la transferencia.
 *
 * - Más de 5 GiB y hasta 10 GiB: La transferencia requiere mantener la sesión
 * abierta hasta que el receptor termine la descarga.
 *
 * - Más de 10 GiB: No soportado.
 *
 * Esto deja preparada la API para modelar correctamente el comportamiento
 * especial de Wormhole frente a otros servicios futuros.
 */
public class WormHoleApp implements SitioDeArchivoAPI {

	private static final long GIB = 1024L * 1024L * 1024L;
	private static final long LIMITE_SUBIDA_NORMAL = 5L * GIB;
	private static final long LIMITE_MAXIMO = 10L * GIB;

	private static final String URL_WORMHOLE = "https://wormhole.app";

	@Override
	public String nombre() {
		return "wormhole.app";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		return Collections.singletonList(URL_WORMHOLE);
	}

	@Override
	public PoliticaDeRetencion politicaGeneral() {
		// Wormhole cambia su comportamiento según el tamaño del archivo.
		return PoliticaDeRetencion.VARIABLE;
	}

	@Override
	public void validarArchivoZip(Path archivoZip) throws ErrorDeArchivo, ArchivoDemasiadoGrande, ServicioNoSoportado {
		Objects.requireNonNull(archivoZip, "archivoZip no puede ser null");

		if (!Files.exists(archivoZip)) {
			throw new ErrorDeArchivo("El archivo ZIP no existe: " + archivoZip);
		}

		if (!Files.isRegularFile(archivoZip)) {
			throw new ErrorDeArchivo("La ruta no corresponde a un archivo normal: " + archivoZip);
		}

		String nombre = archivoZip.getFileName() != null ? archivoZip.getFileName().toString() : "";
		if (!nombre.toLowerCase().endsWith(".zip")) {
			throw new ErrorDeArchivo("Solo se admite un único archivo .zip");
		}

		final long tamano;
		try {
			tamano = Files.size(archivoZip);
		} catch (IOException e) {
			throw new ErrorDeArchivo("No se pudo leer el tamaño del archivo: " + archivoZip, e);
		}

		if (tamano <= 0) {
			throw new ErrorDeArchivo("El archivo ZIP está vacío");
		}

		if (tamano > LIMITE_MAXIMO) {
			throw new ArchivoDemasiadoGrande(tamano, LIMITE_MAXIMO);
		}
	}

	@Override
	public SesionDeTransferencia publicarArchivoZip(Path archivoZip, ObservadorDeTransferencia observador)
			throws ErrorDeArchivo, ArchivoDemasiadoGrande, ErrorConPublicar, ServicioNoSoportado {

		validarArchivoZip(archivoZip);

		final long tamano;
		try {
			tamano = Files.size(archivoZip);
		} catch (IOException e) {
			throw new ErrorDeArchivo("No se pudo leer el tamaño del archivo: " + archivoZip, e);
		}

		final boolean requiereSesionActiva = tamano > LIMITE_SUBIDA_NORMAL;
		final ModoDeTransferencia modo = requiereSesionActiva ? ModoDeTransferencia.TRANSFERENCIA_CON_EMISOR_ACTIVO
				: ModoDeTransferencia.SUBIDA_REMOTA;

		SesionWormhole sesion = new SesionWormhole(archivoZip, tamano, modo, requiereSesionActiva);

		if (observador != null) {
			observador.alCambiarEstado(EstadoDeTransferencia.CONECTANDO);
			observador.alConocerTamanoTotal(tamano);
		}

		/*
		 * Integración real pendiente:
		 *
		 * Aquí es donde en el futuro se conectará la implementación real con
		 * magic-wormhole o con el flujo equivalente que uses en Java.
		 *
		 * En base al código de referencia que compartiste: - se establece conexión - se
		 * genera un código/passphrase - se informa progreso - al finalizar se notifica
		 * el fin
		 *
		 * Para dejar la API preparada, generamos una sesión coherente y marcamos el
		 * comportamiento esperado según el tamaño.
		 */

		sesion.setEstado(
				requiereSesionActiva ? EstadoDeTransferencia.ESPERANDO_DESCARGA : EstadoDeTransferencia.FINALIZADA);

		/*
		 * En Wormhole el dato principal a compartir suele ser un código. Como esta
		 * implementación todavía no hace la conexión real, el valor queda como
		 * placeholder técnico.
		 */
		String codigoProvisional = "pendiente-integracion-" + UUID.randomUUID().toString().substring(0, 8);
		sesion.setCodigo(codigoProvisional);

		/*
		 * Algunos clientes generan además un URI o enlace compartible derivado del
		 * código. Dejamos una URL provisional para que la API ya contemple ese dato
		 * desde ahora.
		 */
		sesion.setEnlace(URL_WORMHOLE);

		if (observador != null) {
			observador.alRecibirCodigo(codigoProvisional);
			observador.alRecibirEnlace(URL_WORMHOLE);

			if (!requiereSesionActiva) {
				observador.alActualizarProgreso(tamano, tamano);
				observador.alCambiarEstado(EstadoDeTransferencia.FINALIZADA);
				observador.alFinalizar(sesion);
			} else {
				observador.alCambiarEstado(EstadoDeTransferencia.ESPERANDO_DESCARGA);
			}
		}

		if (!requiereSesionActiva) {
			sesion.marcarComoFinalizada();
		}

		return sesion;
	}

	/**
	 * Devuelve el modo operativo que Wormhole usaría para un tamaño concreto.
	 *
	 * Este helper puede resultar útil para UI, validaciones previas o mensajes al
	 * usuario antes de empezar la transferencia.
	 */
	public ModoWormhole resolverModo(long tamanoBytes) throws ArchivoDemasiadoGrande {
		if (tamanoBytes <= 0) {
			return ModoWormhole.SUBIDA_NORMAL_HASTA_5_GIB;
		}
		if (tamanoBytes > LIMITE_MAXIMO) {
			throw new ArchivoDemasiadoGrande(tamanoBytes, LIMITE_MAXIMO);
		}
		if (tamanoBytes <= LIMITE_SUBIDA_NORMAL) {
			return ModoWormhole.SUBIDA_NORMAL_HASTA_5_GIB;
		}
		return ModoWormhole.REQUIERE_MANTENER_ABIERTO_HASTA_10_GIB;
	}

	/**
	 * Modos específicos de wormhole.app según el tamaño.
	 */
	public enum ModoWormhole {
		/**
		 * Hasta 5 GiB. Se trata como una publicación normal.
		 */
		SUBIDA_NORMAL_HASTA_5_GIB,

		/**
		 * Más de 5 GiB y hasta 10 GiB. El emisor debe seguir abierto hasta completar la
		 * descarga.
		 */
		REQUIERE_MANTENER_ABIERTO_HASTA_10_GIB
	}

	/**
	 * Implementación simple de sesión para dejar el contrato listo desde ahora.
	 */
	private static final class SesionWormhole implements SesionDeTransferencia {

		private final String id;
		private final Path archivoZip;
		private final long tamanoBytes;
		private final ModoDeTransferencia modo;
		private final boolean requiereMantenerSesionAbierta;
		private final Instant creadaEn;

		private final CountDownLatch latchFinalizacion;
		private final AtomicBoolean cancelada;
		private final AtomicReference<EstadoDeTransferencia> estado;
		private final AtomicReference<String> enlace;
		private final AtomicReference<String> codigo;
		private final AtomicReference<ErrorConPublicar> errorFinal;

		private SesionWormhole(Path archivoZip, long tamanoBytes, ModoDeTransferencia modo,
				boolean requiereMantenerSesionAbierta) {
			this.id = UUID.randomUUID().toString();
			this.archivoZip = archivoZip;
			this.tamanoBytes = tamanoBytes;
			this.modo = modo;
			this.requiereMantenerSesionAbierta = requiereMantenerSesionAbierta;
			this.creadaEn = Instant.now();

			this.latchFinalizacion = new CountDownLatch(1);
			this.cancelada = new AtomicBoolean(false);
			this.estado = new AtomicReference<>(EstadoDeTransferencia.PENDIENTE);
			this.enlace = new AtomicReference<>(null);
			this.codigo = new AtomicReference<>(null);
			this.errorFinal = new AtomicReference<>(null);
		}

		@Override
		public String id() {
			return id;
		}

		@Override
		public String nombreServicio() {
			return "wormhole.app";
		}

		@Override
		public Path archivoZip() {
			return archivoZip;
		}

		@Override
		public long tamanoBytes() {
			return tamanoBytes;
		}

		@Override
		public ModoDeTransferencia modo() {
			return modo;
		}

		@Override
		public EstadoDeTransferencia estado() {
			return estado.get();
		}

		@Override
		public String enlace() {
			return enlace.get();
		}

		@Override
		public String codigo() {
			return codigo.get();
		}

		@Override
		public Instant creadaEn() {
			return creadaEn;
		}

		@Override
		public boolean requiereMantenerSesionAbierta() {
			return requiereMantenerSesionAbierta;
		}

		@Override
		public void esperarFinalizacion() throws InterruptedException, ErrorConPublicar {
			latchFinalizacion.await();

			ErrorConPublicar error = errorFinal.get();
			if (error != null) {
				throw error;
			}
		}

		@Override
		public void cancelar() {
			if (cancelada.compareAndSet(false, true)) {
				estado.set(EstadoDeTransferencia.CANCELADA);
				latchFinalizacion.countDown();
			}
		}

		@Override
		public void close() {
			cancelar();
		}

		private void setEstado(EstadoDeTransferencia nuevoEstado) {
			estado.set(nuevoEstado);
		}

		private void setEnlace(String nuevoEnlace) {
			enlace.set(nuevoEnlace);
		}

		private void setCodigo(String nuevoCodigo) {
			codigo.set(nuevoCodigo);
		}

		private void marcarComoFinalizada() {
			estado.set(EstadoDeTransferencia.FINALIZADA);
			latchFinalizacion.countDown();
		}

		@SuppressWarnings("unused")
		private void marcarComoError(String mensaje, Throwable causa) {
			estado.set(EstadoDeTransferencia.ERROR);
			errorFinal.set(new ErrorConPublicar(mensaje, causa));
			latchFinalizacion.countDown();
		}
	}
}