package com.asbestosstar.crashdetector;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

/**
 * Appender mínimo que escribe directamente en un OutputStream.
 *
 * Se usa para evitar depender de OutputStreamAppender, que no existe en algunas
 * versiones viejas de Log4j2.
 */
public final class AppenderFlujoCompatible extends AbstractAppender {

	private final OutputStream destino;
	private final boolean flushInmediato;

	/**
	 * Crea un appender que escribe eventos en el flujo indicado.
	 *
	 * @param nombre             nombre del appender
	 * @param layout             layout usado para serializar eventos
	 * @param filtro             filtro opcional
	 * @param ignorarExcepciones si true, Log4j2 suprime excepciones
	 * @param destino            flujo de salida destino
	 * @param flushInmediato     si true, hace flush tras cada evento
	 */
	public AppenderFlujoCompatible(String nombre, Layout<? extends Serializable> layout, Filter filtro,
			boolean ignorarExcepciones, OutputStream destino, boolean flushInmediato) {
		super(nombre, filtro, layout, ignorarExcepciones);
		this.destino = destino;
		this.flushInmediato = flushInmediato;
	}

	@Override
	public void append(LogEvent event) {
		if (event == null || destino == null || getLayout() == null) {
			return;
		}

		try {
			byte[] bytes = getLayout().toByteArray(event);
			if (bytes != null && bytes.length > 0) {
				synchronized (destino) {
					destino.write(bytes);
					if (flushInmediato || event.isEndOfBatch()) {
						destino.flush();
					}
				}
			}
		} catch (IOException e) {
			error("No se pudo escribir en el flujo del appender " + getName(), e);
			if (!ignoreExceptions()) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void stop() {
		try {
			if (destino != null) {
				destino.flush();
			}
		} catch (IOException e) {
			error("No se pudo hacer flush al detener el appender " + getName(), e);
		}
		super.stop();
	}
}