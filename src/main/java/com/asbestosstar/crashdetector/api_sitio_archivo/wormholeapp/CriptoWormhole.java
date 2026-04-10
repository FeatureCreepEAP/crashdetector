package com.asbestosstar.crashdetector.api_sitio_archivo.wormholeapp;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utilidades criptográficas para wormhole.app.
 *
 * Replica la parte de crypto.rs: - HKDF-SHA256 - derivación de claves -
 * generación de nonce - cifrado de metadata - SHA-1 en hexadecimal
 */
public final class CriptoWormhole {

	public static final int LONGITUD_CLAVE = 16;
	public static final int LONGITUD_NONCE = 12;

	private static final SecureRandom ALEATORIO = new SecureRandom();

	private CriptoWormhole() {
	}

	public static byte[] bytesAleatorios(int n) {
		byte[] ret = new byte[n];
		ALEATORIO.nextBytes(ret);
		return ret;
	}

	public static byte[] derivarTokenAutenticacion(byte[] claveMaestra, byte[] sal) throws GeneralSecurityException {
		return derivarClave(claveMaestra, sal, "authentication".getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] derivarClaveMeta(byte[] claveMaestra, byte[] sal) throws GeneralSecurityException {
		return derivarClave(claveMaestra, sal, "metadata".getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] derivarClaveContenido(byte[] claveMaestra, byte[] sal) throws GeneralSecurityException {
		return hkdfDerivar(claveMaestra, sal, "Content-Encoding: aes128gcm\0".getBytes(StandardCharsets.UTF_8),
				LONGITUD_CLAVE);
	}

	public static byte[] derivarBaseNonce(byte[] claveMaestra, byte[] sal) throws GeneralSecurityException {
		return hkdfDerivar(claveMaestra, sal, "Content-Encoding: nonce\0".getBytes(StandardCharsets.UTF_8),
				LONGITUD_NONCE);
	}

	public static byte[] derivarClave(byte[] claveMaestra, byte[] sal, byte[] info) throws GeneralSecurityException {
		return hkdfDerivar(claveMaestra, sal, info, LONGITUD_CLAVE);
	}

	/**
	 * HKDF-SHA256 mínimo.
	 */
	public static byte[] hkdfDerivar(byte[] ikm, byte[] sal, byte[] info, int longitud)
			throws GeneralSecurityException {

		byte[] prk = hkdfExtract(sal, ikm);
		return hkdfExpand(prk, info, longitud);
	}

	private static byte[] hkdfExtract(byte[] sal, byte[] ikm) throws GeneralSecurityException {
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec key = new SecretKeySpec(sal, "HmacSHA256");
		mac.init(key);
		return mac.doFinal(ikm);
	}

	private static byte[] hkdfExpand(byte[] prk, byte[] info, int longitud) throws GeneralSecurityException {
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec key = new SecretKeySpec(prk, "HmacSHA256");
		mac.init(key);

		int hashLen = 32;
		int bloques = (int) Math.ceil((double) longitud / (double) hashLen);

		byte[] okm = new byte[longitud];
		byte[] t = new byte[0];
		int offset = 0;

		for (int i = 1; i <= bloques; i++) {
			mac.reset();
			mac.init(key);
			mac.update(t);
			mac.update(info);
			mac.update((byte) i);
			t = mac.doFinal();

			int copiar = Math.min(t.length, longitud - offset);
			System.arraycopy(t, 0, okm, offset, copiar);
			offset += copiar;
		}

		return okm;
	}

	/**
	 * Genera el nonce RFC 8188 haciendo XOR del número de secuencia con los últimos
	 * 4 bytes de la base del nonce.
	 */
	public static byte[] generarNonce(byte[] baseNonce, int secuencia) {
		if (baseNonce == null || baseNonce.length != LONGITUD_NONCE) {
			throw new IllegalArgumentException("La base del nonce debe tener " + LONGITUD_NONCE + " bytes");
		}

		byte[] nonce = baseNonce.clone();
		int len = nonce.length;

		int existente = ByteBuffer.wrap(nonce, len - 4, 4).getInt();
		int xor = existente ^ secuencia;

		byte[] bytes = ByteBuffer.allocate(4).putInt(xor).array();
		System.arraycopy(bytes, 0, nonce, len - 4, 4);

		return nonce;
	}

	/**
	 * Cifra metadata con AES-GCM usando IV de 16 bytes.
	 *
	 * En el Rust compartido: - el IV es aleatorio de 16 bytes - el resultado final
	 * es IV + ciphertext
	 */
	public static byte[] cifrarMetadata(byte[] metadataPlano, byte[] claveMeta) throws GeneralSecurityException {
		byte[] iv = bytesAleatorios(LONGITUD_CLAVE);

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		SecretKeySpec key = new SecretKeySpec(claveMeta, "AES");
		GCMParameterSpec spec = new GCMParameterSpec(128, iv);

		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] cifrado = cipher.doFinal(metadataPlano);

		byte[] ret = new byte[iv.length + cifrado.length];
		System.arraycopy(iv, 0, ret, 0, iv.length);
		System.arraycopy(cifrado, 0, ret, iv.length, cifrado.length);
		return ret;
	}

	public static String sha1Hex(byte[] data) throws GeneralSecurityException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(data);
		return aHex(md.digest());
	}

	public static byte[] sha1(byte[] data) throws GeneralSecurityException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(data);
		return md.digest();
	}

	public static String aHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(Character.forDigit((b >>> 4) & 0xF, 16));
			sb.append(Character.forDigit(b & 0xF, 16));
		}
		return sb.toString();
	}

	public static String base64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	public static String base64UrlSinPadding(byte[] data) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
	}
}