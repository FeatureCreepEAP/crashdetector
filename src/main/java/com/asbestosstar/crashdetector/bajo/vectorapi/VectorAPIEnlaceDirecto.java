package com.asbestosstar.crashdetector.bajo.vectorapi;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorSpecies;

/**
 * Punto aislado que enlaza directamente con la Vector API.
 *
 * La clase solamente debe ejecutarse despues de haber resuelto el modulo
 * jdk.incubator.vector.
 */
final class VectorAPIEnlaceDirecto {

	private VectorAPIEnlaceDirecto() {
	}

	static boolean disponible() {
		VectorSpecies<Byte> especie = ByteVector.SPECIES_PREFERRED;

		if (especie == null || especie.length() <= 0) {
			return false;
		}

		/*
		 * Verifica exactamente los enlaces que usan los motores: SPECIES_PREFERRED,
		 * length(), fromArray(), eq() y anyTrue().
		 */
		byte[] prueba = new byte[especie.length()];
		ByteVector vector = ByteVector.fromArray(especie, prueba, 0);
		VectorMask<Byte> mascara = vector.eq((byte) 0);

		return mascara.anyTrue();
	}
}
