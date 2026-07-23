package com.asbestosstar.crashdetector.buscar;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caché exclusivamente en memoria de clases transferidas desde la JVM
 * observada.
 *
 * Los bytes se asocian al PID que los produjo. Cuando cambia el PID, el
 * contenido anterior se elimina automáticamente, por lo que nunca sobrevive al
 * reinicio de la aplicación observada.
 */
public final class ClasesTransferidas {

	private static final ConcurrentHashMap<String, byte[]> CLASES = new ConcurrentHashMap<String, byte[]>();
	private static final Object BLOQUEO_PROCESO = new Object();
	private static volatile long pidOrigen = Long.MIN_VALUE;

	private ClasesTransferidas() {
	}

	public static void guardar(long pid, String nombreClase, byte[] bytes) {
		String interno = normalizarNombreInterno(nombreClase);
		if (pid <= 0L || interno == null || interno.isEmpty() || !bytesClaseValidos(bytes)) {
			throw new IllegalArgumentException("Clase transferida no válida");
		}
		asegurarProceso(pid);
		CLASES.put(interno, bytes.clone());
	}

	public static byte[] obtener(long pid, String nombreClase) {
		if (pid <= 0L) {
			return null;
		}
		asegurarProceso(pid);
		String interno = normalizarNombreInterno(nombreClase);
		byte[] bytes = interno == null ? null : CLASES.get(interno);
		return bytes == null ? null : bytes.clone();
	}

	public static boolean contiene(long pid, String nombreClase) {
		if (pid <= 0L) {
			return false;
		}
		asegurarProceso(pid);
		String interno = normalizarNombreInterno(nombreClase);
		return interno != null && CLASES.containsKey(interno);
	}

	public static int cantidad(long pid) {
		if (pid <= 0L) {
			return 0;
		}
		asegurarProceso(pid);
		return CLASES.size();
	}

	public static Map<String, Integer> nombresYLongitudes(long pid) {
		if (pid <= 0L) {
			return Collections.emptyMap();
		}
		asegurarProceso(pid);
		TreeMap<String, Integer> ordenado = new TreeMap<String, Integer>();
		for (Map.Entry<String, byte[]> entrada : CLASES.entrySet()) {
			byte[] bytes = entrada.getValue();
			ordenado.put(entrada.getKey().replace('/', '.'), Integer.valueOf(bytes == null ? 0 : bytes.length));
		}
		return Collections.unmodifiableMap(new LinkedHashMap<String, Integer>(ordenado));
	}

	public static void limpiar(long pid) {
		if (pid <= 0L) {
			return;
		}
		asegurarProceso(pid);
		CLASES.clear();
	}

	public static void asegurarProceso(long pid) {
		if (pidOrigen == pid) {
			return;
		}
		synchronized (BLOQUEO_PROCESO) {
			if (pidOrigen != pid) {
				CLASES.clear();
				pidOrigen = pid;
			}
		}
	}

	public static String normalizarNombreInterno(String nombre) {
		if (nombre == null) {
			return null;
		}
		String valor = nombre.trim();
		if (valor.startsWith("L") && valor.endsWith(";") && valor.length() > 2) {
			valor = valor.substring(1, valor.length() - 1);
		}
		if (valor.toLowerCase(java.util.Locale.ROOT).endsWith(".class")) {
			valor = valor.substring(0, valor.length() - 6);
		}
		valor = valor.replace('.', '/');
		while (valor.startsWith("/")) {
			valor = valor.substring(1);
		}
		return valor;
	}

	private static boolean bytesClaseValidos(byte[] bytes) {
		return bytes != null && bytes.length >= 8 && (bytes[0] & 0xff) == 0xca && (bytes[1] & 0xff) == 0xfe
				&& (bytes[2] & 0xff) == 0xba && (bytes[3] & 0xff) == 0xbe;
	}
}
