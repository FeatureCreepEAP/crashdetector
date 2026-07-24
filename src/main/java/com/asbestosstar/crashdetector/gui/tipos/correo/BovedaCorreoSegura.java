package com.asbestosstar.crashdetector.gui.tipos.correo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.asbestosstar.crashdetector.Statics;

/**
 * Bóveda local cifrada para cuentas y mensajes en caché.
 *
 * Formato: cabecera + parámetros KDF + AES-256-GCM. No se usa serialización de
 * objetos para evitar la deserialización de clases arbitrarias.
 */
public final class BovedaCorreoSegura {

	private static final byte[] MAGIC = new byte[] { 'C', 'D', 'M', 'A', 'I', 'L', '0', '2' };
	private static final int VERSION = 2;
	private static final int ITERACIONES = 310_000;
	private static final int LONGITUD_SALT = 16;
	private static final int LONGITUD_NONCE = 12;
	private static final int LONGITUD_CLAVE_BITS = 256;
	private static final int LONGITUD_CLAVE_MINIMA = 12;
	private static final int MAX_CUENTAS = 100;
	private static final int MAX_MENSAJES = 5_000;
	private static final int MAX_STRING_BYTES = 8 * 1024 * 1024;
	private static final int MAX_ARCHIVO_BYTES = 128 * 1024 * 1024;

	private final SecureRandom aleatorio = new SecureRandom();
	private final Path directorio;
	private final Path archivo;

	public BovedaCorreoSegura() {
		this.directorio = new java.io.File(Statics.carpeta_mundial_como_archivo, "mail_reader_secure").toPath();
		this.archivo = directorio.resolve("mail.vault");
	}

	public Path directorio() {
		return directorio;
	}

	public boolean existe() {
		return Files.isRegularFile(archivo);
	}

	public DatosBoveda crear(char[] clave) throws IOException, GeneralSecurityException {
		DatosBoveda datos = new DatosBoveda();
		guardar(datos, clave);
		return datos;
	}

	public DatosBoveda abrir(char[] clave) throws IOException, GeneralSecurityException {
		validarClave(clave);
		if (!existe()) {
			throw new IOException("mail vault does not exist");
		}

		long tamano = Files.size(archivo);
		if (tamano <= 0 || tamano > MAX_ARCHIVO_BYTES) {
			throw new IOException("mail vault has an invalid size");
		}

		byte[] cifrado = null;
		byte[] plano = null;
		byte[] salt = null;
		byte[] nonce = null;
		SecretKey claveDerivada = null;

		try (DataInputStream in = new DataInputStream(new BufferedInputStream(Files.newInputStream(archivo)))) {
			byte[] magicLeido = new byte[MAGIC.length];
			in.readFully(magicLeido);
			if (!Arrays.equals(MAGIC, magicLeido)) {
				throw new IOException("mail vault header is invalid");
			}

			int version = in.readInt();
			if (version != VERSION) {
				throw new IOException("mail vault version is unsupported");
			}

			int iteraciones = in.readInt();
			if (iteraciones < 100_000 || iteraciones > 5_000_000) {
				throw new IOException("mail vault KDF parameters are invalid");
			}

			salt = leerBytesLimitados(in, 64);
			nonce = leerBytesLimitados(in, 64);
			cifrado = leerBytesLimitados(in, MAX_ARCHIVO_BYTES);

			claveDerivada = derivarClave(clave, salt, iteraciones);
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, claveDerivada, new GCMParameterSpec(128, nonce));
			cipher.updateAAD(aad(version, iteraciones));
			plano = cipher.doFinal(cifrado);
			return deserializar(plano);
		} catch (AEADBadTagException e) {
			throw e;
		} finally {
			borrar(cifrado);
			borrar(plano);
			borrar(salt);
			borrar(nonce);
		}
	}

	public void guardar(DatosBoveda datos, char[] clave) throws IOException, GeneralSecurityException {
		if (datos == null) {
			throw new IllegalArgumentException("mail vault data cannot be null");
		}
		validarClave(clave);

		crearDirectorioSeguro();

		byte[] salt = new byte[LONGITUD_SALT];
		byte[] nonce = new byte[LONGITUD_NONCE];
		aleatorio.nextBytes(salt);
		aleatorio.nextBytes(nonce);

		byte[] plano = null;
		byte[] cifrado = null;
		Path temporal = directorio.resolve("mail.vault.tmp");

		try {
			plano = serializar(datos);
			SecretKey claveDerivada = derivarClave(clave, salt, ITERACIONES);
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, claveDerivada, new GCMParameterSpec(128, nonce));
			cipher.updateAAD(aad(VERSION, ITERACIONES));
			cifrado = cipher.doFinal(plano);

			try (DataOutputStream out = new DataOutputStream(
					new BufferedOutputStream(Files.newOutputStream(temporal)))) {
				out.write(MAGIC);
				out.writeInt(VERSION);
				out.writeInt(ITERACIONES);
				escribirBytes(out, salt);
				escribirBytes(out, nonce);
				escribirBytes(out, cifrado);
			}

			fijarPermisosArchivo(temporal);
			try {
				Files.move(temporal, archivo, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
			} catch (AtomicMoveNotSupportedException e) {
				Files.move(temporal, archivo, StandardCopyOption.REPLACE_EXISTING);
			}
			fijarPermisosArchivo(archivo);
		} finally {
			borrar(plano);
			borrar(cifrado);
			borrar(salt);
			borrar(nonce);
			Files.deleteIfExists(temporal);
		}
	}

	private void validarClave(char[] clave) {
		if (clave == null || clave.length < LONGITUD_CLAVE_MINIMA) {
			throw new IllegalArgumentException("mail vault password is too short");
		}
	}

	private void crearDirectorioSeguro() throws IOException {
		Files.createDirectories(directorio);
		try {
			Set<PosixFilePermission> permisos = EnumSet.of(PosixFilePermission.OWNER_READ,
					PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE);
			Files.setPosixFilePermissions(directorio, permisos);
		} catch (UnsupportedOperationException ignored) {
			// Windows y otros sistemas sin permisos POSIX.
		}
	}

	private void fijarPermisosArchivo(Path ruta) throws IOException {
		try {
			Set<PosixFilePermission> permisos = EnumSet.of(PosixFilePermission.OWNER_READ,
					PosixFilePermission.OWNER_WRITE);
			Files.setPosixFilePermissions(ruta, permisos);
		} catch (UnsupportedOperationException ignored) {
			// Windows y otros sistemas sin permisos POSIX.
		}
	}

	private SecretKey derivarClave(char[] clave, byte[] salt, int iteraciones) throws GeneralSecurityException {
		PBEKeySpec spec = new PBEKeySpec(clave, salt, iteraciones, LONGITUD_CLAVE_BITS);
		try {
			SecretKeyFactory fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			byte[] bytes = fabrica.generateSecret(spec).getEncoded();
			try {
				return new SecretKeySpec(bytes, "AES");
			} finally {
				borrar(bytes);
			}
		} finally {
			spec.clearPassword();
		}
	}

	private byte[] aad(int version, int iteraciones) {
		ByteBuffer buffer = ByteBuffer.allocate(MAGIC.length + Integer.BYTES * 2);
		buffer.put(MAGIC);
		buffer.putInt(version);
		buffer.putInt(iteraciones);
		return buffer.array();
	}

	private byte[] serializar(DatosBoveda datos) throws IOException {
		BorrableByteArrayOutputStream bytes = new BorrableByteArrayOutputStream();
		try (DataOutputStream out = new DataOutputStream(bytes)) {
			out.writeInt(VERSION);
			out.writeInt(datos.cuentas.size());
			for (CuentaCorreo cuenta : datos.cuentas) {
				escribirString(out, cuenta.id());
				escribirString(out, cuenta.nombre());
				escribirString(out, cuenta.servidorImap());
				out.writeInt(cuenta.puerto());
				escribirString(out, cuenta.usuario());
				char[] secreto = cuenta.copiarSecreto();
				try {
					escribirChars(out, secreto);
				} finally {
					Arrays.fill(secreto, '\0');
				}
				escribirString(out, cuenta.carpeta());
			}

			out.writeInt(datos.mensajes.size());
			for (MensajeCorreo mensaje : datos.mensajes) {
				escribirString(out, mensaje.cuentaId());
				escribirString(out, mensaje.carpeta());
				out.writeLong(mensaje.uid());
				escribirString(out, mensaje.remitente());
				escribirString(out, mensaje.destinatarios());
				escribirString(out, mensaje.asunto());
				out.writeLong(mensaje.fecha().toEpochMilli());
				escribirString(out, mensaje.cuerpo());
				out.writeBoolean(mensaje.leido());
			}
		}
		return bytes.toByteArrayYBorrar();
	}

	private DatosBoveda deserializar(byte[] plano) throws IOException {
		DatosBoveda datos = new DatosBoveda();
		try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(plano))) {
			int versionInterna = in.readInt();
			if (versionInterna != VERSION) {
				throw new IOException("mail vault payload version is unsupported");
			}

			int cuentas = leerCantidad(in, MAX_CUENTAS);
			for (int i = 0; i < cuentas; i++) {
				String id = leerString(in);
				String nombre = leerString(in);
				String host = leerString(in);
				int puerto = in.readInt();
				String usuario = leerString(in);
				char[] secreto = leerChars(in);
				String carpeta = leerString(in);
				try {
					datos.cuentas.add(new CuentaCorreo(id, nombre, host, puerto, usuario, secreto, carpeta));
				} finally {
					Arrays.fill(secreto, '\0');
				}
			}

			int mensajes = leerCantidad(in, MAX_MENSAJES);
			for (int i = 0; i < mensajes; i++) {
				String cuentaId = leerString(in);
				String carpeta = leerString(in);
				long uid = in.readLong();
				String remitente = leerString(in);
				String destinatarios = leerString(in);
				String asunto = leerString(in);
				Instant fecha = Instant.ofEpochMilli(in.readLong());
				String cuerpo = leerString(in);
				boolean leido = in.readBoolean();
				datos.mensajes.add(new MensajeCorreo(cuentaId, carpeta, uid, remitente, destinatarios, asunto,
						fecha, cuerpo, leido));
			}

			if (in.read() != -1) {
				throw new IOException("mail vault payload contains trailing data");
			}
		} catch (EOFException e) {
			throw new IOException("mail vault payload is truncated", e);
		}
		return datos;
	}

	private int leerCantidad(DataInputStream in, int maximo) throws IOException {
		int cantidad = in.readInt();
		if (cantidad < 0 || cantidad > maximo) {
			throw new IOException("mail vault item count is invalid");
		}
		return cantidad;
	}

	private void escribirString(DataOutputStream out, String valor) throws IOException {
		byte[] bytes = (valor == null ? "" : valor).getBytes(StandardCharsets.UTF_8);
		if (bytes.length > MAX_STRING_BYTES) {
			throw new IOException("mail vault string is too large");
		}
		escribirBytes(out, bytes);
	}

	private String leerString(DataInputStream in) throws IOException {
		byte[] bytes = leerBytesLimitados(in, MAX_STRING_BYTES);
		try {
			return new String(bytes, StandardCharsets.UTF_8);
		} finally {
			borrar(bytes);
		}
	}

	private void escribirChars(DataOutputStream out, char[] valor) throws IOException {
		char[] seguro = valor == null ? new char[0] : valor;
		if (seguro.length > 1_000_000) {
			throw new IOException("mail vault secret is too large");
		}
		out.writeInt(seguro.length);
		for (char c : seguro) {
			out.writeChar(c);
		}
	}

	private char[] leerChars(DataInputStream in) throws IOException {
		int longitud = in.readInt();
		if (longitud < 0 || longitud > 1_000_000) {
			throw new IOException("mail vault secret length is invalid");
		}
		char[] chars = new char[longitud];
		for (int i = 0; i < longitud; i++) {
			chars[i] = in.readChar();
		}
		return chars;
	}

	private void escribirBytes(DataOutputStream out, byte[] bytes) throws IOException {
		out.writeInt(bytes.length);
		out.write(bytes);
	}

	private byte[] leerBytesLimitados(DataInputStream in, int maximo) throws IOException {
		int longitud = in.readInt();
		if (longitud < 0 || longitud > maximo) {
			throw new IOException("mail vault byte array length is invalid");
		}
		byte[] bytes = new byte[longitud];
		in.readFully(bytes);
		return bytes;
	}

	private static void borrar(byte[] bytes) {
		if (bytes != null) {
			Arrays.fill(bytes, (byte) 0);
		}
	}

	private static final class BorrableByteArrayOutputStream extends ByteArrayOutputStream {
		byte[] toByteArrayYBorrar() {
			byte[] copia = toByteArray();
			Arrays.fill(buf, (byte) 0);
			reset();
			return copia;
		}

	}

	public static final class DatosBoveda {
		private final List<CuentaCorreo> cuentas = new ArrayList<CuentaCorreo>();
		private final List<MensajeCorreo> mensajes = new ArrayList<MensajeCorreo>();

		public List<CuentaCorreo> cuentas() {
			return cuentas;
		}

		public List<MensajeCorreo> mensajes() {
			return mensajes;
		}

		public void borrarSecretos() {
			for (CuentaCorreo cuenta : cuentas) {
				cuenta.borrarSecreto();
			}
		}
	}
}
