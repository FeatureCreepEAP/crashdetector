package com.asbestosstar.crashdetector.api_sitio_archivo;

import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API genérica para sitios de compartición de archivos.
 *
 * Diseñada específicamente para subir UN ÚNICO archivo ZIP.
 *
 * Esta interfaz contempla dos familias de servicios:
 *
 * 1) Servicios de "subir y olvidar": Se sube el archivo, se obtiene un enlace y
 * el proceso puede cerrarse.
 *
 * 2) Servicios que requieren mantener la sesión viva: El emisor debe seguir
 * abierto/conectado hasta que el receptor termine la descarga, o el enlace deja
 * de ser utilizable.
 *
 * La idea es que implementaciones futuras como Wormhole, LimeWire o BitTorrent
 * puedan compartir el mismo contrato sin forzar un modelo único de
 * transferencia.
 */
public interface SitioDeArchivoAPI {

	public static final Map<String, SitioDeArchivoAPI> SERVICIOS_REGISTRADOS = new HashMap<>();

	/**
	 * Nombre corto del servicio.
	 *
	 * Ejemplos: - wormhole.app - limewire.com - bittorrent
	 */
	String nombre();

	/**
	 * URLs conocidas o configurables del servicio.
	 *
	 * Para algunos servicios puede ser una sola URL.
	 */
	List<String> sitiosPorDefecto();

	/**
	 * Indica si esta implementación acepta el archivo ZIP dado.
	 *
	 * Debe validar, por ejemplo: - que exista - que sea un .zip - que el tamaño
	 * esté dentro de los límites soportados
	 */
	void validarArchivoZip(Path archivoZip) throws ErrorDeArchivo, ArchivoDemasiadoGrande, ServicioNoSoportado;

	/**
	 * Inicia la publicación del archivo ZIP.
	 *
	 * El resultado incluye: - enlace o código compartible - modo de transferencia -
	 * información de si hay que mantener la sesión abierta
	 *
	 * En servicios como Wormhole, este método puede además dejar viva una sesión de
	 * transferencia que luego deberá mantenerse abierta hasta completar la
	 * descarga.
	 */
	SesionDeTransferencia publicarArchivoZip(Path archivoZip, ObservadorDeTransferencia observador)
			throws ErrorDeArchivo, ArchivoDemasiadoGrande, ErrorConPublicar, ServicioNoSoportado;

	/**
	 * Devuelve la política general del servicio.
	 *
	 * Ojo: algunas implementaciones pueden variar según el tamaño del archivo, así
	 * que este valor es orientativo y la decisión final puede venir en la sesión
	 * devuelta por publicarArchivoZip(...).
	 */
	PoliticaDeRetencion politicaGeneral();

	/**
	 * Define cómo se comporta el servicio respecto a la permanencia del emisor.
	 */
	enum PoliticaDeRetencion {
		/**
		 * Se sube el archivo y luego el emisor puede cerrar el proceso.
		 */
		SUBIR_Y_OLVIDAR,

		/**
		 * El emisor debe permanecer activo para que el receptor pueda completar la
		 * descarga.
		 */
		REQUIERE_EMISOR_ACTIVO,

		/**
		 * La política depende del tamaño, modo o configuración elegida.
		 */
		VARIABLE
	}

	/**
	 * Modo concreto usado en una transferencia específica.
	 *
	 * Esto permite que un mismo servicio ofrezca más de una modalidad.
	 */
	enum ModoDeTransferencia {
		/**
		 * Subida normal a un servicio remoto.
		 */
		SUBIDA_REMOTA,

		/**
		 * Transferencia efímera que necesita que el emisor siga abierto.
		 */
		TRANSFERENCIA_CON_EMISOR_ACTIVO,

		/**
		 * Reserva para implementaciones futuras tipo BitTorrent.
		 */
		DISTRIBUCION_P2P
	}

	/**
	 * Estado de la transferencia.
	 */
	enum EstadoDeTransferencia {
		PENDIENTE, CONECTANDO, SUBIENDO, ESPERANDO_DESCARGA, DESCARGANDO_DESDE_EMISOR, FINALIZADA, CANCELADA, ERROR
	}

	/**
	 * Callback sencillo para informar progreso, código, enlace y errores.
	 *
	 * Está pensado para mapear fácilmente eventos como los que ya viste en la
	 * referencia de Wormhole.
	 */
	interface ObservadorDeTransferencia {

		/**
		 * Se llama cuando cambia el estado general.
		 */
		default void alCambiarEstado(EstadoDeTransferencia estado) {
		}

		/**
		 * Se llama cuando ya se conoce el tamaño total del archivo.
		 */
		default void alConocerTamanoTotal(long bytesTotales) {
		}

		/**
		 * Se llama durante la subida o envío del archivo.
		 */
		default void alActualizarProgreso(long bytesEnviados, long bytesTotales) {
		}

		/**
		 * Se llama cuando el servicio genera un código o passphrase.
		 *
		 * En Wormhole suele ser el dato principal para compartir.
		 */
		default void alRecibirCodigo(String codigo) {
		}

		/**
		 * Se llama cuando ya existe un enlace o URI compartible.
		 */
		default void alRecibirEnlace(String enlace) {
		}

		/**
		 * Se llama cuando la transferencia termina correctamente.
		 */
		default void alFinalizar(SesionDeTransferencia sesion) {
		}

		/**
		 * Se llama ante cualquier fallo.
		 */
		default void alError(String mensaje, Throwable causa) {
		}
	}

	/**
	 * Representa una transferencia viva o ya creada.
	 *
	 * En servicios "subir y olvidar" puede cerrarse inmediatamente después de
	 * publicar.
	 *
	 * En servicios efímeros puede ser necesario conservar esta sesión abierta hasta
	 * completar la descarga.
	 */
	interface SesionDeTransferencia extends AutoCloseable {

		String id();

		String nombreServicio();

		Path archivoZip();

		long tamanoBytes();

		ModoDeTransferencia modo();

		EstadoDeTransferencia estado();

		/**
		 * Enlace compartible si existe.
		 *
		 * Puede ser null si el servicio usa solamente un código.
		 */
		String enlace();

		/**
		 * Código o passphrase si existe.
		 *
		 * Puede ser null para sitios que solo devuelven URL.
		 */
		String codigo();

		/**
		 * Momento en que se creó la sesión.
		 */
		Instant creadaEn();

		/**
		 * Indica si el proceso debe seguir vivo para que el receptor pueda descargar
		 * correctamente.
		 */
		boolean requiereMantenerSesionAbierta();

		/**
		 * Bloquea hasta que la transferencia termine o falle.
		 *
		 * En implementaciones "subir y olvidar" puede retornar enseguida.
		 */
		void esperarFinalizacion() throws InterruptedException, ErrorConPublicar;

		/**
		 * Cancela la transferencia si todavía está activa.
		 */
		void cancelar();

		/**
		 * Cierra recursos asociados a la sesión.
		 */
		@Override
		void close();
	}

	/**
	 * Error general de publicación o transferencia.
	 */
	class ErrorConPublicar extends Exception {
		private static final long serialVersionUID = 1L;

		public ErrorConPublicar(String mensaje) {
			super(mensaje);
		}

		public ErrorConPublicar(String mensaje, Throwable causa) {
			super(mensaje, causa);
		}
	}

	/**
	 * El archivo no cumple el formato esperado o no puede leerse.
	 */
	class ErrorDeArchivo extends Exception {
		private static final long serialVersionUID = 1L;

		public ErrorDeArchivo(String mensaje) {
			super(mensaje);
		}

		public ErrorDeArchivo(String mensaje, Throwable causa) {
			super(mensaje, causa);
		}
	}

	/**
	 * El tamaño excede lo soportado por la implementación.
	 */
	class ArchivoDemasiadoGrande extends Exception {
		private static final long serialVersionUID = 1L;

		private final long bytesArchivo;
		private final long bytesMaximos;

		public ArchivoDemasiadoGrande(long bytesArchivo, long bytesMaximos) {
			super("Archivo demasiado grande: " + bytesArchivo + " bytes; máximo soportado: " + bytesMaximos + " bytes");
			this.bytesArchivo = bytesArchivo;
			this.bytesMaximos = bytesMaximos;
		}

		public long getBytesArchivo() {
			return bytesArchivo;
		}

		public long getBytesMaximos() {
			return bytesMaximos;
		}
	}

	/**
	 * La implementación existe, pero la operación concreta todavía no está
	 * soportada.
	 */
	class ServicioNoSoportado extends Exception {
		private static final long serialVersionUID = 1L;

		public ServicioNoSoportado(String mensaje) {
			super(mensaje);
		}
	}
}