package com.asbestosstar.crashdetector.gui.tipos.correo;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * Datos de conexión de una cuenta IMAP. El secreto se mantiene como char[] para
 * poder borrarlo de memoria cuando deja de ser necesario.
 */
public final class CuentaCorreo {

	private final String id;
	private final String nombre;
	private final String servidorImap;
	private final int puerto;
	private final String usuario;
	private final char[] secreto;
	private final String carpeta;

	public CuentaCorreo(String id, String nombre, String servidorImap, int puerto, String usuario, char[] secreto,
			String carpeta) {
		this.id = normalizarId(id);
		this.nombre = limpiar(nombre);
		this.servidorImap = limpiar(servidorImap);
		this.puerto = puerto;
		this.usuario = limpiar(usuario);
		this.secreto = secreto == null ? new char[0] : Arrays.copyOf(secreto, secreto.length);
		this.carpeta = limpiar(carpeta);
	}

	public static CuentaCorreo nueva(String nombre, String servidorImap, int puerto, String usuario, char[] secreto,
			String carpeta) {
		return new CuentaCorreo(UUID.randomUUID().toString(), nombre, servidorImap, puerto, usuario, secreto, carpeta);
	}

	private static String normalizarId(String valor) {
		String limpio = limpiar(valor);
		return limpio.isEmpty() ? UUID.randomUUID().toString() : limpio;
	}

	private static String limpiar(String valor) {
		return valor == null ? "" : valor.trim();
	}

	public String id() {
		return id;
	}

	public String nombre() {
		return nombre;
	}

	public String servidorImap() {
		return servidorImap;
	}

	public int puerto() {
		return puerto;
	}

	public String usuario() {
		return usuario;
	}

	public char[] copiarSecreto() {
		return Arrays.copyOf(secreto, secreto.length);
	}

	public String carpeta() {
		return carpeta;
	}

	public CuentaCorreo copiar() {
		return new CuentaCorreo(id, nombre, servidorImap, puerto, usuario, secreto, carpeta);
	}

	public void borrarSecreto() {
		Arrays.fill(secreto, '\0');
	}

	public boolean esValida() {
		return !nombre.isEmpty() && !servidorImap.isEmpty() && puerto > 0 && puerto <= 65535 && !usuario.isEmpty()
				&& secreto.length > 0 && !carpeta.isEmpty();
	}

	@Override
	public String toString() {
		return nombre.isEmpty() ? usuario : nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CuentaCorreo)) {
			return false;
		}
		CuentaCorreo otra = (CuentaCorreo) obj;
		return Objects.equals(id, otra.id);
	}
}
