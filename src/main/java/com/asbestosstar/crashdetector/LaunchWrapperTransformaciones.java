package com.asbestosstar.crashdetector;

import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.ParcheClassNode;
import com.asbestosstar.crashdetector.parches.TransASM;

import net.minecraft.launchwrapper.IClassTransformer;

/**
 * Transformer para entornos basados en LaunchWrapper, como Rift.
 *
 * Solo transforma clases que sean objetivo de al menos un parche activo.
 */
public class LaunchWrapperTransformaciones implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {

		// Algunas clases pueden llegar como null
		if (basicClass == null) {
			return null;
		}

		// Preferir el nombre transformado cuando exista
		String className = transformedName != null ? transformedName : name;

		// No transformar clases internas de CrashDetector
		if (esClaseExcluida(className)) {
			return basicClass;
		}

		String nombreCodigo = normalizarNombre(className);

		boolean objetivoBytecode = esObjetivoDeParcheByteArray(nombreCodigo);
		boolean objetivoASM = esObjetivoDeParcheASM(nombreCodigo);

		// Si no es objetivo de ningún parche, devolver exactamente los bytes originales
		if (!objetivoBytecode && !objetivoASM) {
			return basicClass;
		}

		byte[] bytes = basicClass;

		// Solo aplicar parches byte[] si la clase realmente es objetivo
		if (objetivoBytecode) {
			bytes = Parche.aplicarParches(bytes, nombreCodigo);
		}

		// Solo aplicar ASM si la clase realmente es objetivo
		if (objetivoASM && Transformaciones.hayASM()) {
			bytes = TransASM.obtenerBytes(nombreCodigo, bytes);
		}

		return bytes;
	}

	/**
	 * Convierte nombres con barras a nombres con puntos.
	 */
	private static String normalizarNombre(String className) {
		return className == null ? null : className.replace("/", ".");
	}

	/**
	 * Verifica si una clase pertenece a paquetes que no deben transformarse.
	 */
	private static boolean esClaseExcluida(String className) {

		if (className == null) {
			return true;
		}

		return className.startsWith("com.asbestosstar.crashdetector.")
				|| className.startsWith("com/asbestosstar/crashdetector/") || className.startsWith("org.objectweb.asm.")
				|| className.startsWith("org/objectweb/asm/");
	}

	/**
	 * Verifica si existe al menos un parche activo de tipo byte[] para esta clase.
	 */
	private static boolean esObjetivoDeParcheByteArray(String nombreCodigo) {

		if (nombreCodigo == null) {
			return false;
		}

		for (Parche<?> parche : Parche.parches) {
			if (!parche.activar()) {
				continue;
			}

			if (!parche.clases().contains(nombreCodigo)) {
				continue;
			}

			// Solo considerar parches que trabajen sobre byte[]
			if (parche.tipo() == byte[].class) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Verifica si existe al menos un parche activo ASM/ClassNode para esta clase.
	 */
	private static boolean esObjetivoDeParcheASM(String nombreCodigo) {

		if (nombreCodigo == null) {
			return false;
		}

		for (Parche<?> parche : Parche.parches) {
			if (!parche.activar()) {
				continue;
			}

			if (!parche.clases().contains(nombreCodigo)) {
				continue;
			}

			// Los parches ASM suelen implementarse como ParcheClassNode
			if (parche instanceof ParcheClassNode) {
				return true;
			}
		}

		return false;
	}
}