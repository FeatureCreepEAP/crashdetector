package com.asbestosstar.crashdetector.bittorrent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Codificador y lector bencode sin dependencias externas.
 *
 * El lector conserva las cadenas como byte[] para no destruir datos binarios,
 * como hashes de piezas, listas compactas de pares o el diccionario info.
 */
public final class Bencode {

	private static final int PROFUNDIDAD_MAXIMA = 96;
	private static final int ELEMENTOS_MAXIMOS = 1_000_000;
	private static final int CADENA_MAXIMA = 64 * 1024 * 1024;

	private Bencode() {
	}

	public static byte[] codificar(Object valor) {
		ByteArrayOutputStream salida = new ByteArrayOutputStream();
		try {
			codificar(valor, salida);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return salida.toByteArray();
	}

	public static Object leer(byte[] datos) throws IOException {
		Resultado resultado = leerUno(datos, 0);
		if (resultado.siguiente() != datos.length) {
			throw new IOException("Datos bencode adicionales al final");
		}
		return resultado.valor();
	}

	public static Resultado leerUno(byte[] datos, int desplazamiento) throws IOException {
		if (datos == null) {
			throw new IOException("Datos bencode nulos");
		}
		Lector lector = new Lector(datos, desplazamiento);
		Object valor = lector.leerValor(0);
		return new Resultado(valor, lector.posicion);
	}

	@SuppressWarnings("unchecked")
	private static void codificar(Object valor, ByteArrayOutputStream salida) throws IOException {
		if (valor instanceof byte[]) {
			byte[] datos = (byte[]) valor;
			escribirAscii(salida, String.valueOf(datos.length));
			salida.write(':');
			salida.write(datos);
			return;
		}
		if (valor instanceof String) {
			codificar(((String) valor).getBytes(StandardCharsets.UTF_8), salida);
			return;
		}
		if (valor instanceof Number) {
			salida.write('i');
			escribirAscii(salida, String.valueOf(((Number) valor).longValue()));
			salida.write('e');
			return;
		}
		if (valor instanceof List<?>) {
			salida.write('l');
			for (Object elemento : (List<?>) valor) {
				codificar(elemento, salida);
			}
			salida.write('e');
			return;
		}
		if (valor instanceof Map<?, ?>) {
			Map<String, Object> mapa = (Map<String, Object>) valor;
			List<String> claves = new ArrayList<String>(mapa.keySet());
			Collections.sort(claves, COMPARADOR_CLAVES);
			salida.write('d');
			for (String clave : claves) {
				codificar(clave, salida);
				codificar(mapa.get(clave), salida);
			}
			salida.write('e');
			return;
		}
		throw new IllegalArgumentException(
				"Tipo bencode no soportado: " + (valor == null ? "null" : valor.getClass().getName()));
	}

	private static final Comparator<String> COMPARADOR_CLAVES = new Comparator<String>() {
		@Override
		public int compare(String a, String b) {
			byte[] aa = a.getBytes(StandardCharsets.UTF_8);
			byte[] bb = b.getBytes(StandardCharsets.UTF_8);
			int limite = Math.min(aa.length, bb.length);
			for (int i = 0; i < limite; i++) {
				int diferencia = (aa[i] & 0xff) - (bb[i] & 0xff);
				if (diferencia != 0) {
					return diferencia;
				}
			}
			return aa.length - bb.length;
		}
	};

	private static void escribirAscii(ByteArrayOutputStream salida, String texto) throws IOException {
		salida.write(texto.getBytes(StandardCharsets.US_ASCII));
	}

	public static final class Resultado {
		private final Object valor;
		private final int siguiente;

		Resultado(Object valor, int siguiente) {
			this.valor = valor;
			this.siguiente = siguiente;
		}

		public Object valor() {
			return valor;
		}

		public int siguiente() {
			return siguiente;
		}
	}

	private static final class Lector {
		private final byte[] datos;
		private int posicion;
		private int elementos;

		Lector(byte[] datos, int posicion) throws IOException {
			if (posicion < 0 || posicion > datos.length) {
				throw new IOException("Desplazamiento bencode no válido");
			}
			this.datos = datos;
			this.posicion = posicion;
		}

		Object leerValor(int profundidad) throws IOException {
			if (profundidad > PROFUNDIDAD_MAXIMA) {
				throw new IOException("Bencode demasiado anidado");
			}
			if (++elementos > ELEMENTOS_MAXIMOS) {
				throw new IOException("Bencode con demasiados elementos");
			}
			byte tipo = actual();
			if (tipo == 'i') {
				return Long.valueOf(leerEntero());
			}
			if (tipo == 'l') {
				return leerLista(profundidad + 1);
			}
			if (tipo == 'd') {
				return leerDiccionario(profundidad + 1);
			}
			if (tipo >= '0' && tipo <= '9') {
				return leerBytes();
			}
			throw error("Tipo bencode desconocido");
		}

		private long leerEntero() throws IOException {
			esperar('i');
			int inicio = posicion;
			while (actual() != 'e') {
				posicion++;
			}
			if (inicio == posicion) {
				throw error("Entero bencode vacío");
			}
			String texto = new String(datos, inicio, posicion - inicio, StandardCharsets.US_ASCII);
			posicion++;
			if ("-0".equals(texto) || (texto.length() > 1 && texto.charAt(0) == '0')
					|| (texto.startsWith("-0") && texto.length() > 2)) {
				throw error("Entero bencode no canónico");
			}
			try {
				return Long.parseLong(texto);
			} catch (NumberFormatException e) {
				throw error("Entero bencode no válido");
			}
		}

		private List<Object> leerLista(int profundidad) throws IOException {
			esperar('l');
			List<Object> lista = new ArrayList<Object>();
			while (actual() != 'e') {
				lista.add(leerValor(profundidad));
			}
			posicion++;
			return lista;
		}

		private Map<String, Object> leerDiccionario(int profundidad) throws IOException {
			esperar('d');
			Map<String, Object> mapa = new LinkedHashMap<String, Object>();
			while (actual() != 'e') {
				byte[] claveBytes = leerBytes();
				String clave = new String(claveBytes, StandardCharsets.UTF_8);
				mapa.put(clave, leerValor(profundidad));
			}
			posicion++;
			return mapa;
		}

		private byte[] leerBytes() throws IOException {
			int inicio = posicion;
			while (actual() != ':') {
				byte b = actual();
				if (b < '0' || b > '9') {
					throw error("Longitud de cadena bencode no válida");
				}
				posicion++;
			}
			if (inicio == posicion) {
				throw error("Longitud de cadena bencode vacía");
			}
			String texto = new String(datos, inicio, posicion - inicio, StandardCharsets.US_ASCII);
			if (texto.length() > 1 && texto.charAt(0) == '0') {
				throw error("Longitud bencode no canónica");
			}
			posicion++;
			int cantidad;
			try {
				cantidad = Integer.parseInt(texto);
			} catch (NumberFormatException e) {
				throw error("Longitud bencode fuera de rango");
			}
			if (cantidad < 0 || cantidad > CADENA_MAXIMA || posicion + cantidad < posicion
					|| posicion + cantidad > datos.length) {
				throw error("Cadena bencode truncada o demasiado grande");
			}
			byte[] valor = new byte[cantidad];
			System.arraycopy(datos, posicion, valor, 0, cantidad);
			posicion += cantidad;
			return valor;
		}

		private void esperar(char esperado) throws IOException {
			if (actual() != (byte) esperado) {
				throw error("Marcador bencode inesperado");
			}
			posicion++;
		}

		private byte actual() throws IOException {
			if (posicion < 0 || posicion >= datos.length) {
				throw error("Fin inesperado de bencode");
			}
			return datos[posicion];
		}

		private IOException error(String mensaje) {
			return new IOException(mensaje + " en posición " + posicion);
		}
	}
}
