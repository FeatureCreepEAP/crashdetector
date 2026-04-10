package com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp;

import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Cifrador incremental RFC 8188 aes128gcm.
 *
 * Estructura emitida: - cabecera de 21 bytes en la primera salida - registros
 * cifrados
 *
 * Cada registro cifra: - datos - byte delimitador 1 si no es el último - byte
 * delimitador 2 si es el último
 */
public final class CifradorDeFlujo {

	private static final int LONGITUD_TAG = 16;

	public static final int TAMANO_REGISTRO_POR_DEFECTO = 64 * 1024;

	private final byte[] claveContenido;
	private final byte[] baseNonce;
	private final int tamanoRegistro;
	private final int maximoPlanoPorRegistro;
	private final byte[] sal;

	private int secuencia;
	private boolean cabeceraEmitida;
	private boolean finalizado;

	private final ByteArrayOutputStream buffer;

	private CifradorDeFlujo(byte[] claveContenido, byte[] baseNonce, int tamanoRegistro, byte[] sal) {
		this.claveContenido = claveContenido;
		this.baseNonce = baseNonce;
		this.tamanoRegistro = tamanoRegistro;
		this.sal = sal.clone();
		this.secuencia = 0;
		this.cabeceraEmitida = false;
		this.finalizado = false;
		this.buffer = new ByteArrayOutputStream();

		int overheadPorRegistro = LONGITUD_TAG + 1;
		this.maximoPlanoPorRegistro = tamanoRegistro - overheadPorRegistro;
	}

	public static CifradorDeFlujo nuevoAleatorio(byte[] claveMaestra, int tamanoRegistro)
			throws GeneralSecurityException {
		byte[] sal = CriptoWormhole.bytesAleatorios(CriptoWormhole.LONGITUD_CLAVE);
		return conSal(claveMaestra, tamanoRegistro, sal);
	}

	public static CifradorDeFlujo conSal(byte[] claveMaestra, int tamanoRegistro, byte[] sal)
			throws GeneralSecurityException {
		byte[] claveContenido = CriptoWormhole.derivarClaveContenido(claveMaestra, sal);
		byte[] baseNonce = CriptoWormhole.derivarBaseNonce(claveMaestra, sal);
		return new CifradorDeFlujo(claveContenido, baseNonce, tamanoRegistro, sal);
	}

	public byte[] sal() {
		return sal.clone();
	}

	public byte[] actualizar(byte[] data) throws GeneralSecurityException {
		return actualizar(data, 0, data.length);
	}

	public byte[] actualizar(byte[] data, int offset, int longitud) throws GeneralSecurityException {
		if (finalizado) {
			throw new IllegalStateException("El cifrador ya fue finalizado");
		}

		ByteArrayOutputStream salida = new ByteArrayOutputStream();

		if (!cabeceraEmitida) {
			escribirCabecera(salida);
			cabeceraEmitida = true;
		}

		if (longitud > 0) {
			buffer.write(data, offset, longitud);
		}

		while (buffer.size() >= maximoPlanoPorRegistro) {
			byte[] acumulado = buffer.toByteArray();
			byte[] bloquePlano = new byte[maximoPlanoPorRegistro];
			System.arraycopy(acumulado, 0, bloquePlano, 0, maximoPlanoPorRegistro);

			byte[] resto = new byte[acumulado.length - maximoPlanoPorRegistro];
			System.arraycopy(acumulado, maximoPlanoPorRegistro, resto, 0, resto.length);

			buffer.reset();
			buffer.write(resto, 0, resto.length);

			byte[] registro = cifrarRegistro(bloquePlano, false);
			salida.write(registro, 0, registro.length);
		}

		return salida.toByteArray();
	}

	public byte[] finalizar() throws GeneralSecurityException {
		if (finalizado) {
			throw new IllegalStateException("El cifrador ya fue finalizado");
		}

		ByteArrayOutputStream salida = new ByteArrayOutputStream();

		if (!cabeceraEmitida) {
			escribirCabecera(salida);
			cabeceraEmitida = true;
		}

		byte[] resto = buffer.toByteArray();
		buffer.reset();

		if (resto.length > 0 || secuencia == 0) {
			byte[] registroFinal = cifrarRegistro(resto, true);
			salida.write(registroFinal, 0, registroFinal.length);
		}

		finalizado = true;
		return salida.toByteArray();
	}

	private void escribirCabecera(ByteArrayOutputStream salida) {
		salida.write(sal, 0, sal.length);
		salida.write((tamanoRegistro >>> 24) & 0xFF);
		salida.write((tamanoRegistro >>> 16) & 0xFF);
		salida.write((tamanoRegistro >>> 8) & 0xFF);
		salida.write(tamanoRegistro & 0xFF);
		salida.write(0);
	}

	private byte[] cifrarRegistro(byte[] plano, boolean ultimo) throws GeneralSecurityException {
		byte[] padded = agregarDelimitador(plano, ultimo);
		byte[] nonce = CriptoWormhole.generarNonce(baseNonce, secuencia);

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		SecretKeySpec key = new SecretKeySpec(claveContenido, "AES");
		GCMParameterSpec spec = new GCMParameterSpec(128, nonce);

		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] cifrado = cipher.doFinal(padded);

		secuencia++;
		return cifrado;
	}

	private byte[] agregarDelimitador(byte[] data, boolean ultimo) {
		byte delimitador = (byte) (ultimo ? 2 : 1);
		byte[] ret = new byte[data.length + 1];
		System.arraycopy(data, 0, ret, 0, data.length);
		ret[ret.length - 1] = delimitador;
		return ret;
	}
}