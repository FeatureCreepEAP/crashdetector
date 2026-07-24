package com.asbestosstar.crashdetector.api_sitio_archivo.limewire;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Primitivas criptográficas aisladas para LimeWire.
 *
 * El formato observado usa claves públicas P-256 sin comprimir de 65 bytes y
 * valores privados cifrados de 48 bytes. La implementación usa ECDH P-256,
 * HKDF-SHA256 y AES-256-GCM con nonces deterministas derivados de una clave
 * única por objeto. Toda la lógica queda aquí para que pueda sustituirse sin
 * tocar el transporte si LimeWire cambia su formato interno.
 */
public final class CifradorLimeWire {

	private static final String CURVA = "secp256r1";
	private static final int TAMANO_COORDENADA = 32;
	private static final int TAMANO_NONCE = 12;
	private static final SecureRandom ALEATORIO = new SecureRandom();

	public MaterialArchivo crearMaterialArchivo(String idClaveUsuario, String clavePublicaUsuarioBase64)
			throws GeneralSecurityException {
		if (idClaveUsuario == null || idClaveUsuario.trim().isEmpty()) {
			throw new GeneralSecurityException("La clave de usuario no tiene id");
		}

		PublicKey clavePublicaUsuario = decodificarPublica(clavePublicaUsuarioBase64);
		KeyPair claveArchivo = generarPar();
		KeyPair claveEfimera = generarPar();

		byte[] privadaArchivo = enteroFijo(((ECPrivateKey) claveArchivo.getPrivate()).getS(), TAMANO_COORDENADA);
		byte[] secretoEnvoltura = ecdh(claveArchivo.getPrivate(), clavePublicaUsuario);
		byte[] claveEnvoltura = hkdf(secretoEnvoltura, null, "limewire:file-private-key", 32);
		byte[] nonceEnvoltura = hkdf(secretoEnvoltura, null, "limewire:file-private-key:nonce", TAMANO_NONCE);
		byte[] privadaCifrada = aesGcm(true, claveEnvoltura, nonceEnvoltura, privadaArchivo, null);

		byte[] secretoContenido = ecdh(claveEfimera.getPrivate(), claveArchivo.getPublic());
		byte[] claveContenido = hkdf(secretoContenido, null, "limewire:file-content", 32);
		byte[] nonceContenido = hkdf(secretoContenido, null, "limewire:file-content:nonce", TAMANO_NONCE);

		return new MaterialArchivo(UUID.randomUUID().toString(), idClaveUsuario,
				Base64.getEncoder().encodeToString(codificarPublica((ECPublicKey) claveArchivo.getPublic())),
				Base64.getEncoder().encodeToString(privadaCifrada),
				Base64.getEncoder().encodeToString(codificarPublica((ECPublicKey) claveEfimera.getPublic())),
				claveContenido, nonceContenido);
	}

	private static KeyPair generarPar() throws GeneralSecurityException {
		KeyPairGenerator generador = KeyPairGenerator.getInstance("EC");
		generador.initialize(new ECGenParameterSpec(CURVA), ALEATORIO);
		return generador.generateKeyPair();
	}

	private static PublicKey decodificarPublica(String base64) throws GeneralSecurityException {
		byte[] raw;
		try {
			raw = Base64.getDecoder().decode(base64);
		} catch (IllegalArgumentException e) {
			throw new GeneralSecurityException("Clave pública LimeWire en Base64 inválido", e);
		}
		if (raw.length != 65 || raw[0] != 0x04) {
			throw new GeneralSecurityException("Se esperaba una clave pública P-256 sin comprimir de 65 bytes");
		}

		byte[] xb = Arrays.copyOfRange(raw, 1, 33);
		byte[] yb = Arrays.copyOfRange(raw, 33, 65);
		ECPoint punto = new ECPoint(new BigInteger(1, xb), new BigInteger(1, yb));

		AlgorithmParameters parametros = AlgorithmParameters.getInstance("EC");
		parametros.init(new ECGenParameterSpec(CURVA));
		ECParameterSpec spec = parametros.getParameterSpec(ECParameterSpec.class);
		return KeyFactory.getInstance("EC").generatePublic(new ECPublicKeySpec(punto, spec));
	}

	private static byte[] codificarPublica(ECPublicKey clave) {
		byte[] x = enteroFijo(clave.getW().getAffineX(), TAMANO_COORDENADA);
		byte[] y = enteroFijo(clave.getW().getAffineY(), TAMANO_COORDENADA);
		byte[] ret = new byte[65];
		ret[0] = 0x04;
		System.arraycopy(x, 0, ret, 1, x.length);
		System.arraycopy(y, 0, ret, 33, y.length);
		return ret;
	}

	private static byte[] enteroFijo(BigInteger valor, int longitud) {
		byte[] origen = valor.toByteArray();
		byte[] ret = new byte[longitud];
		int copiar = Math.min(origen.length, longitud);
		System.arraycopy(origen, origen.length - copiar, ret, longitud - copiar, copiar);
		return ret;
	}

	private static byte[] ecdh(PrivateKey privada, PublicKey publica) throws GeneralSecurityException {
		KeyAgreement acuerdo = KeyAgreement.getInstance("ECDH");
		acuerdo.init(privada);
		acuerdo.doPhase(publica, true);
		return acuerdo.generateSecret();
	}

	private static byte[] hkdf(byte[] ikm, byte[] sal, String info, int longitud) throws GeneralSecurityException {
		Mac mac = Mac.getInstance("HmacSHA256");
		byte[] salReal = sal == null ? new byte[32] : sal;
		mac.init(new SecretKeySpec(salReal, "HmacSHA256"));
		byte[] prk = mac.doFinal(ikm);

		byte[] salida = new byte[longitud];
		byte[] anterior = new byte[0];
		int offset = 0;
		int contador = 1;
		while (offset < longitud) {
			mac.init(new SecretKeySpec(prk, "HmacSHA256"));
			mac.update(anterior);
			mac.update(info.getBytes(StandardCharsets.UTF_8));
			mac.update((byte) contador++);
			anterior = mac.doFinal();
			int copiar = Math.min(anterior.length, longitud - offset);
			System.arraycopy(anterior, 0, salida, offset, copiar);
			offset += copiar;
		}
		return salida;
	}

	private static byte[] aesGcm(boolean cifrar, byte[] clave, byte[] nonce, byte[] datos, byte[] aad)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(cifrar ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, new SecretKeySpec(clave, "AES"),
				new GCMParameterSpec(128, nonce));
		if (aad != null && aad.length > 0) {
			cipher.updateAAD(aad);
		}
		return cipher.doFinal(datos);
	}

	public static final class MaterialArchivo {
		private final String idClaveArchivo;
		private final String idClaveUsuario;
		private final String clavePublicaArchivo;
		private final String clavePrivadaArchivoCifrada;
		private final String clavePublicaEfimera;
		private final byte[] claveContenido;
		private final byte[] nonceBase;

		private MaterialArchivo(String idClaveArchivo, String idClaveUsuario, String clavePublicaArchivo,
				String clavePrivadaArchivoCifrada, String clavePublicaEfimera, byte[] claveContenido, byte[] nonceBase) {
			this.idClaveArchivo = idClaveArchivo;
			this.idClaveUsuario = idClaveUsuario;
			this.clavePublicaArchivo = clavePublicaArchivo;
			this.clavePrivadaArchivoCifrada = clavePrivadaArchivoCifrada;
			this.clavePublicaEfimera = clavePublicaEfimera;
			this.claveContenido = claveContenido.clone();
			this.nonceBase = nonceBase.clone();
		}

		public byte[] cifrarParte(byte[] plano, int numeroParte) throws GeneralSecurityException {
			if (numeroParte <= 0) {
				throw new GeneralSecurityException("El número de parte debe empezar en 1");
			}
			byte[] nonce = nonceBase.clone();
			byte[] secuencia = ByteBuffer.allocate(4).putInt(numeroParte).array();
			for (int i = 0; i < 4; i++) {
				nonce[nonce.length - 4 + i] ^= secuencia[i];
			}
			byte[] aad = ByteBuffer.allocate(4).putInt(numeroParte).array();
			return aesGcm(true, claveContenido, nonce, plano, aad);
		}

		public String idClaveArchivo() {
			return idClaveArchivo;
		}

		public String idClaveUsuario() {
			return idClaveUsuario;
		}

		public String clavePublicaArchivo() {
			return clavePublicaArchivo;
		}

		public String clavePrivadaArchivoCifrada() {
			return clavePrivadaArchivoCifrada;
		}

		public String clavePublicaEfimera() {
			return clavePublicaEfimera;
		}

		public void limpiar() {
			Arrays.fill(claveContenido, (byte) 0);
			Arrays.fill(nonceBase, (byte) 0);
		}
	}

	public static String sha256Base64(byte[] datos) throws GeneralSecurityException {
		return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(datos));
	}
}
