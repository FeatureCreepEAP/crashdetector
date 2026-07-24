package com.asbestosstar.crashdetector.api_sitio_archivo.limewire;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI.EstadoDeTransferencia;
import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI.ModoDeTransferencia;
import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI.SesionDeTransferencia;
import com.asbestosstar.crashdetector.api_sitio_archivo.limewire.MultipartLimeWire.ControlCancelacion;

/** Estado vivo de una publicación LimeWire. */
public final class SesionLimeWire implements SesionDeTransferencia, ControlCancelacion {

	private final String id = UUID.randomUUID().toString();
	private final Path archivo;
	private final long tamano;
	private final Instant creada = Instant.now();
	private final CountDownLatch finalizacion = new CountDownLatch(1);
	private final AtomicBoolean cancelada = new AtomicBoolean(false);
	private final AtomicReference<EstadoDeTransferencia> estado = new AtomicReference<EstadoDeTransferencia>(
			EstadoDeTransferencia.PENDIENTE);
	private final AtomicReference<String> enlace = new AtomicReference<String>();
	private final AtomicReference<String> codigo = new AtomicReference<String>();
	private final AtomicReference<ErrorConPublicar> error = new AtomicReference<ErrorConPublicar>();

	public SesionLimeWire(Path archivo) throws IOException {
		this.archivo = archivo;
		this.tamano = Files.size(archivo);
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public String nombreServicio() {
		return "limewire.com";
	}

	@Override
	public Path archivoZip() {
		return archivo;
	}

	@Override
	public long tamanoBytes() {
		return tamano;
	}

	@Override
	public ModoDeTransferencia modo() {
		return ModoDeTransferencia.SUBIDA_REMOTA;
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
		return creada;
	}

	@Override
	public boolean requiereMantenerSesionAbierta() {
		return false;
	}

	@Override
	public void esperarFinalizacion() throws InterruptedException, ErrorConPublicar {
		finalizacion.await();
		ErrorConPublicar fallo = error.get();
		if (fallo != null) {
			throw fallo;
		}
	}

	@Override
	public void cancelar() {
		if (cancelada.compareAndSet(false, true)) {
			estado.set(EstadoDeTransferencia.CANCELADA);
			finalizacion.countDown();
		}
	}

	@Override
	public boolean cancelada() {
		return cancelada.get();
	}

	@Override
	public void close() {
		cancelar();
	}

	public void cambiarEstado(EstadoDeTransferencia nuevo) {
		if (!cancelada.get()) {
			estado.set(nuevo);
		}
	}

	public void establecerEnlace(String nuevo) {
		enlace.set(nuevo);
	}

	public void establecerCodigo(String nuevo) {
		codigo.set(nuevo);
	}

	public void finalizar() {
		if (!cancelada.get()) {
			estado.set(EstadoDeTransferencia.FINALIZADA);
			finalizacion.countDown();
		}
	}

	public void fallar(String mensaje, Throwable causa) {
		if (!cancelada.get()) {
			estado.set(EstadoDeTransferencia.ERROR);
			error.set(new ErrorConPublicar(mensaje, causa));
			finalizacion.countDown();
		}
	}
}
