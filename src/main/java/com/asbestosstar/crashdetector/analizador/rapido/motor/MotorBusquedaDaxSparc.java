package com.asbestosstar.crashdetector.analizador.rapido.motor;

import java.util.function.IntUnaryOperator;

import com.asbestosstar.crashdetector.stream.intstream.CDOracleIntStream;

/**
 * Motor de búsqueda de bytes respaldado por Oracle DAX Stream Offload.
 *
 * <p>
 * La biblioteca Oracle acelera flujos cuyo origen es {@code int[]}. Por ello,
 * este motor convierte ventanas grandes de bytes a enteros, genera mediante DAX
 * un mapa de coincidencias de 0 y 1, y finalmente traduce los bits marcados a
 * posiciones del buffer original.
 * </p>
 *
 * <p>
 * Para ventanas pequeñas, valores no incluidos en los marcadores DAX o fallos
 * de enlace con la biblioteca nativa, se usa el motor escalar.
 * </p>
 */
public final class MotorBusquedaDaxSparc implements MotorBusquedaBytes {

	/**
	 * La propia documentación de Oracle recomienda más de 50 000 elementos. La
	 * conversión byte[] -> int[] añade coste, por lo que usamos un umbral
	 * conservador configurable.
	 */
	private static final int UMBRAL_DAX = Math.max(50_000,
			Integer.getInteger("crashdetector.dax.umbral", 262_144).intValue());

	private static final MotorBusquedaEscalar ESCALAR = new MotorBusquedaEscalar();

	/*
	 * Operadores sin captura. Oracle recomienda constantes y miembros estáticos
	 * para que el analizador de Stream Offload pueda reconocer el
	 * predicado/operador.
	 */
	private static final IntUnaryOperator MARCAR_NUL = valor -> valor == 0 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_TAB = valor -> valor == 9 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_LF = valor -> valor == 10 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_CR = valor -> valor == 13 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_ESPACIO = valor -> valor == 32 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_COMILLAS = valor -> valor == 34 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_APOSTROFO = valor -> valor == 39 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_PARENTESIS_ABRE = valor -> valor == 40 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_PARENTESIS_CIERRA = valor -> valor == 41 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_COMA = valor -> valor == 44 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_GUION = valor -> valor == 45 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_PUNTO = valor -> valor == 46 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_BARRA = valor -> valor == 47 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_DOS_PUNTOS = valor -> valor == 58 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_PUNTO_Y_COMA = valor -> valor == 59 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_IGUAL = valor -> valor == 61 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_CORCHETE_ABRE = valor -> valor == 91 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_BARRA_INVERSA = valor -> valor == 92 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_CORCHETE_CIERRA = valor -> valor == 93 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_LLAVE_ABRE = valor -> valor == 123 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_BARRA_VERTICAL = valor -> valor == 124 ? 1 : 0;
	private static final IntUnaryOperator MARCAR_LLAVE_CIERRA = valor -> valor == 125 ? 1 : 0;

	@Override
	public int buscar(byte[] datos, int inicio, int fin, byte valor, int[] posiciones, int maxPosiciones) {

		validarArgumentos(datos, inicio, fin, posiciones);

		int capacidad = Math.min(Math.max(maxPosiciones, 0), posiciones.length);

		if (capacidad == 0 || inicio == fin) {
			return 0;
		}

		int longitud = fin - inicio;
		IntUnaryOperator marcador = marcadorPara(valor);

		if (longitud < UMBRAL_DAX || marcador == null) {
			return ESCALAR.buscar(datos, inicio, fin, valor, posiciones, capacidad);
		}

		int[] enteros = convertirAEnteros(datos, inicio, fin);

		try {
			int[] mapa = CDOracleIntStream.deParalelo(enteros).map(marcador).toArray();

			int total = 0;

			for (int i = 0; i < mapa.length && total < capacidad; i++) {
				if (mapa[i] != 0) {
					posiciones[total++] = inicio + i;
				}
			}

			return total;
		} catch (LinkageError e) {
			return ESCALAR.buscar(datos, inicio, fin, valor, posiciones, capacidad);
		} catch (RuntimeException e) {
			return ESCALAR.buscar(datos, inicio, fin, valor, posiciones, capacidad);
		}
	}

	@Override
	public String nombre() {
		return "oracle-dax-sparc";
	}

	public static int umbralDax() {
		return UMBRAL_DAX;
	}

	private static int[] convertirAEnteros(byte[] datos, int inicio, int fin) {

		int[] resultado = new int[fin - inicio];

		for (int i = inicio, destino = 0; i < fin; i++, destino++) {
			/*
			 * Conservamos el valor byte con signo. Los delimitadores ASCII soportados se
			 * encuentran en el intervalo positivo 0..127.
			 */
			resultado[destino] = datos[i];
		}

		return resultado;
	}

	private static IntUnaryOperator marcadorPara(byte valor) {
		switch (valor) {
		case 0:
			return MARCAR_NUL;
		case '\t':
			return MARCAR_TAB;
		case '\n':
			return MARCAR_LF;
		case '\r':
			return MARCAR_CR;
		case ' ':
			return MARCAR_ESPACIO;
		case '"':
			return MARCAR_COMILLAS;
		case '\'':
			return MARCAR_APOSTROFO;
		case '(':
			return MARCAR_PARENTESIS_ABRE;
		case ')':
			return MARCAR_PARENTESIS_CIERRA;
		case ',':
			return MARCAR_COMA;
		case '-':
			return MARCAR_GUION;
		case '.':
			return MARCAR_PUNTO;
		case '/':
			return MARCAR_BARRA;
		case ':':
			return MARCAR_DOS_PUNTOS;
		case ';':
			return MARCAR_PUNTO_Y_COMA;
		case '=':
			return MARCAR_IGUAL;
		case '[':
			return MARCAR_CORCHETE_ABRE;
		case '\\':
			return MARCAR_BARRA_INVERSA;
		case ']':
			return MARCAR_CORCHETE_CIERRA;
		case '{':
			return MARCAR_LLAVE_ABRE;
		case '|':
			return MARCAR_BARRA_VERTICAL;
		case '}':
			return MARCAR_LLAVE_CIERRA;
		default:
			return null;
		}
	}

	private static void validarArgumentos(byte[] datos, int inicio, int fin, int[] posiciones) {

		if (datos == null) {
			throw new NullPointerException("datos");
		}

		if (posiciones == null) {
			throw new NullPointerException("posiciones");
		}

		if (inicio < 0 || fin < inicio || fin > datos.length) {
			throw new IndexOutOfBoundsException(
					"Ventana inválida: inicio=" + inicio + ", fin=" + fin + ", longitud=" + datos.length);
		}
	}
}
