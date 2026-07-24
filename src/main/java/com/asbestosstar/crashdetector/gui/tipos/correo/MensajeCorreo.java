package com.asbestosstar.crashdetector.gui.tipos.correo;

import java.time.Instant;
import java.util.Objects;

/**
 * Copia local, deliberadamente sencilla, de un mensaje IMAP.
 */
public final class MensajeCorreo {

	private final String cuentaId;
	private final String carpeta;
	private final long uid;
	private final String remitente;
	private final String destinatarios;
	private final String asunto;
	private final Instant fecha;
	private final String cuerpo;
	private final boolean leido;

	public MensajeCorreo(String cuentaId, String carpeta, long uid, String remitente, String destinatarios,
			String asunto, Instant fecha, String cuerpo, boolean leido) {
		this.cuentaId = seguro(cuentaId);
		this.carpeta = seguro(carpeta);
		this.uid = uid;
		this.remitente = seguro(remitente);
		this.destinatarios = seguro(destinatarios);
		this.asunto = seguro(asunto);
		this.fecha = fecha == null ? Instant.EPOCH : fecha;
		this.cuerpo = seguro(cuerpo);
		this.leido = leido;
	}

	private static String seguro(String valor) {
		return valor == null ? "" : valor;
	}

	public String cuentaId() {
		return cuentaId;
	}

	public String carpeta() {
		return carpeta;
	}

	public long uid() {
		return uid;
	}

	public String remitente() {
		return remitente;
	}

	public String destinatarios() {
		return destinatarios;
	}

	public String asunto() {
		return asunto;
	}

	public Instant fecha() {
		return fecha;
	}

	public String cuerpo() {
		return cuerpo;
	}

	public boolean leido() {
		return leido;
	}

	public String claveUnica() {
		return cuentaId + '\u0000' + carpeta + '\u0000' + uid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cuentaId, carpeta, uid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MensajeCorreo)) {
			return false;
		}
		MensajeCorreo otro = (MensajeCorreo) obj;
		return uid == otro.uid && Objects.equals(cuentaId, otro.cuentaId) && Objects.equals(carpeta, otro.carpeta);
	}
}
