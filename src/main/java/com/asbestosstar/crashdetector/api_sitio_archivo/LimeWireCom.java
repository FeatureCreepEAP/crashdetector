package com.asbestosstar.crashdetector.api_sitio_archivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.CifradorLimeWire;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.CifradorLimeWire.MaterialArchivo;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.ClienteHttpLimeWire;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.CredencialesLimeWire;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.MultipartLimeWire;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.ReplicacionLimeWire;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.ReplicacionLimeWire.ClaveUsuario;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.ReplicacionLimeWire.EstadoComparticion;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.SesionLimeWire;

/** Adaptador de LimeWire para CompartirInstanciaGUI. */
public final class LimeWireCom implements SitioDeArchivoAPI {

	private static final long GIB = 1024L * 1024L * 1024L;
	private static final long LIMITE_PREDETERMINADO = 4L * GIB;

	@Override
	public String nombre() {
		return "limewire.com";
	}

	@Override
	public List<String> sitiosPorDefecto() {
		return Collections.singletonList("https://limewire.com");
	}

	@Override
	public PoliticaDeRetencion politicaGeneral() {
		return PoliticaDeRetencion.SUBIR_Y_OLVIDAR;
	}

	@Override
	public void validarArchivoZip(Path archivoZip) throws ErrorDeArchivo, ArchivoDemasiadoGrande, ServicioNoSoportado {
		Objects.requireNonNull(archivoZip, "archivoZip no puede ser null");
		if (!Files.isRegularFile(archivoZip) || !Files.isReadable(archivoZip)) {
			throw new ErrorDeArchivo("El ZIP de LimeWire no existe o no se puede leer: " + archivoZip);
		}
		String nombre = archivoZip.getFileName() == null ? "" : archivoZip.getFileName().toString();
		if (!nombre.toLowerCase(Locale.ROOT).endsWith(".zip")) {
			throw new ErrorDeArchivo("LimeWire solamente recibe un archivo .zip desde CompartirInstanciaGUI");
		}
		long tamano;
		try {
			tamano = Files.size(archivoZip);
		} catch (IOException e) {
			throw new ErrorDeArchivo("No se pudo leer el tamaño del ZIP", e);
		}
		if (tamano <= 0L) {
			throw new ErrorDeArchivo("El ZIP está vacío");
		}
		long limite = limiteConfigurado();
		if (tamano > limite) {
			throw new ArchivoDemasiadoGrande(tamano, limite);
		}
	}

	@Override
	public SesionDeTransferencia publicarArchivoZip(Path archivoZip, ObservadorDeTransferencia observador)
			throws ErrorDeArchivo, ArchivoDemasiadoGrande, ErrorConPublicar, ServicioNoSoportado {
		validarArchivoZip(archivoZip);

		final CredencialesLimeWire credenciales = CredencialesLimeWire.desdeEntorno();
		final SesionLimeWire sesion;
		try {
			sesion = new SesionLimeWire(archivoZip);
		} catch (IOException e) {
			credenciales.close();
			throw new ErrorDeArchivo("No se pudo crear la sesión LimeWire", e);
		}

		ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
			Thread hilo = new Thread(r, "limewire-subida-" + sesion.id());
			hilo.setDaemon(true);
			return hilo;
		});

		executor.submit(() -> {
			MaterialArchivo material = null;
			try {
				sesion.cambiarEstado(EstadoDeTransferencia.CONECTANDO);
				notificarEstado(observador, EstadoDeTransferencia.CONECTANDO);
				if (observador != null) {
					observador.alConocerTamanoTotal(sesion.tamanoBytes());
				}

				ClienteHttpLimeWire http = new ClienteHttpLimeWire(credenciales);
				ReplicacionLimeWire replicacion = new ReplicacionLimeWire(http, credenciales);
				ClaveUsuario claveUsuario = replicacion.obtenerOCrearClaveUsuario();
				material = new CifradorLimeWire().crearMaterialArchivo(claveUsuario.id, claveUsuario.clavePublica);

				EstadoComparticion estado = replicacion.crearEstadoInicial(archivoZip.getFileName().toString(),
						sesion.tamanoBytes(), material);
				sesion.establecerCodigo(estado.idBucket);
				if (observador != null) {
					observador.alRecibirCodigo(estado.idBucket);
				}

				sesion.cambiarEstado(EstadoDeTransferencia.SUBIENDO);
				notificarEstado(observador, EstadoDeTransferencia.SUBIENDO);
				new MultipartLimeWire(http).subir(archivoZip, estado, material, observador, sesion);

				replicacion.marcarSubidaCompleta(estado, material);
				String enlace = replicacion.buscarEnlaceCompartible(estado);
				if (enlace != null) {
					sesion.establecerEnlace(enlace);
					if (observador != null) {
						observador.alRecibirEnlace(enlace);
					}
				}

				sesion.finalizar();
				notificarEstado(observador, EstadoDeTransferencia.FINALIZADA);
				if (observador != null) {
					observador.alFinalizar(sesion);
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				sesion.fallar("Error al publicar el ZIP en LimeWire", t);
				notificarEstado(observador, EstadoDeTransferencia.ERROR);
				if (observador != null) {
					observador.alError(t.getMessage(), t);
				}
			} finally {
				if (material != null) {
					material.limpiar();
				}
				credenciales.close();
				executor.shutdown();
			}
		});

		return sesion;
	}

	private static void notificarEstado(ObservadorDeTransferencia observador, EstadoDeTransferencia estado) {
		if (observador != null) {
			observador.alCambiarEstado(estado);
		}
	}

	private static long limiteConfigurado() {
		String valor = System.getProperty("crashdetector.limewire.maxBytes");
		if (valor == null || valor.trim().isEmpty()) {
			return LIMITE_PREDETERMINADO;
		}
		try {
			long n = Long.parseLong(valor.trim());
			return n > 0L ? n : LIMITE_PREDETERMINADO;
		} catch (NumberFormatException e) {
			return LIMITE_PREDETERMINADO;
		}
	}
}
