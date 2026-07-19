package com.asbestosstar.crashdetector.heapdump;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Analizador HPROF binario de dos pasadas.
 *
 * Está diseñado para Java 8 y para volcados grandes: no conserva objetos
 * individuales ni referencias. Agrega cantidad y tamaño superficial estimado
 * por clase, y después puede relacionar las clases con los JAR de mods
 * conocidos.
 */
public final class AnalizadorHprofRapido {

	private static final int REGISTRO_STRING = 0x01;
	private static final int REGISTRO_LOAD_CLASS = 0x02;
	private static final int REGISTRO_HEAP_DUMP = 0x0c;
	private static final int REGISTRO_HEAP_DUMP_SEGMENT = 0x1c;

	private static final int SUB_CLASS_DUMP = 0x20;
	private static final int SUB_INSTANCE_DUMP = 0x21;
	private static final int SUB_OBJECT_ARRAY_DUMP = 0x22;
	private static final int SUB_PRIMITIVE_ARRAY_DUMP = 0x23;
	private static final int SUB_PRIMITIVE_ARRAY_SIN_DATOS = 0xc3;
	private static final int SUB_HEAP_DUMP_INFO = 0xfe;

	private static final int MAXIMO_STRING_GUARDADO = 1024 * 1024;
	private static final long ALINEACION_OBJETO = 8L;
	private static final long CABECERA_ARREGLO_ESTIMADA = 16L;
	private static final long CABECERA_OBJETO_ESTIMADA = 16L;

	private AnalizadorHprofRapido() {
	}

	public interface Progreso {
		void actualizar(int porcentaje, String detalle);
	}

	public static ResultadoHeapDump analizar(File archivo, boolean identificarMods, Progreso progreso,
			AtomicBoolean cancelado) throws IOException {
		validarArchivo(archivo);

		Metadatos metadatos = new Metadatos();
		primeraPasada(archivo, metadatos, progreso, cancelado);
		ResultadoAcumulado acumulado = segundaPasada(archivo, metadatos, progreso, cancelado);

		List<EstadisticaHeap> lista = new ArrayList<EstadisticaHeap>(acumulado.porClase.values());

		if (identificarMods) {
			Set<String> clases = new HashSet<String>();
			for (EstadisticaHeap estadistica : lista) {
				clases.add(estadistica.clase());
			}

			Map<String, String> mods = IndiceModsHeap.resolver(clases, progreso, cancelado);
			for (EstadisticaHeap estadistica : lista) {
				String jar = mods.get(estadistica.clase());
				estadistica.establecerMod(IndiceModsHeap.clasificarBiblioteca(estadistica.clase(), jar));
			}
		} else {
			for (EstadisticaHeap estadistica : lista) {
				estadistica.establecerMod(IndiceModsHeap.clasificarBiblioteca(estadistica.clase(), null));
			}
		}

		if (progreso != null) {
			progreso.actualizar(100, archivo.getName());
		}

		return new ResultadoHeapDump(archivo, lista, acumulado.bytesTotales, acumulado.objetosTotales,
				metadatos.tamanoId);
	}

	private static void primeraPasada(File archivo, Metadatos metadatos, Progreso progreso, AtomicBoolean cancelado)
			throws IOException {
		try (RandomAccessFile entrada = new RandomAccessFile(archivo, "r")) {
			leerCabecera(entrada, metadatos);
			long longitud = entrada.length();

			while (entrada.getFilePointer() < longitud) {
				comprobarCancelacion(cancelado);
				long inicioRegistro = entrada.getFilePointer();
				int etiqueta = leerU1(entrada);
				leerU4(entrada);
				long largo = leerU4(entrada);
				long inicioCuerpo = entrada.getFilePointer();
				long fin = sumaSegura(inicioCuerpo, largo, longitud);

				switch (etiqueta) {
				case REGISTRO_STRING:
					leerString(entrada, largo, metadatos);
					break;
				case REGISTRO_LOAD_CLASS:
					leerLoadClass(entrada, metadatos);
					break;
				case REGISTRO_HEAP_DUMP:
				case REGISTRO_HEAP_DUMP_SEGMENT:
					leerSubregistrosHeap(entrada, fin, metadatos, null, false, cancelado);
					break;
				default:
					break;
				}

				entrada.seek(fin);
				notificar(progreso, 5 + porcentaje(inicioRegistro, longitud, 40), archivo.getName());
			}
		}

		for (Map.Entry<Long, Long> entrada : metadatos.nombreIdPorClase.entrySet()) {
			String nombre = metadatos.strings.get(entrada.getValue());
			if (nombre != null) {
				metadatos.nombrePorClase.put(entrada.getKey(), normalizarNombreClase(nombre));
			}
		}
	}

	private static ResultadoAcumulado segundaPasada(File archivo, Metadatos metadatos, Progreso progreso,
			AtomicBoolean cancelado) throws IOException {
		ResultadoAcumulado resultado = new ResultadoAcumulado();

		try (RandomAccessFile entrada = new RandomAccessFile(archivo, "r")) {
			Metadatos cabecera = new Metadatos();
			leerCabecera(entrada, cabecera);

			if (cabecera.tamanoId != metadatos.tamanoId) {
				throw new IOException("El tamaño de identificador HPROF cambió entre pasadas");
			}

			long longitud = entrada.length();
			while (entrada.getFilePointer() < longitud) {
				comprobarCancelacion(cancelado);
				long inicioRegistro = entrada.getFilePointer();
				int etiqueta = leerU1(entrada);
				leerU4(entrada);
				long largo = leerU4(entrada);
				long inicioCuerpo = entrada.getFilePointer();
				long fin = sumaSegura(inicioCuerpo, largo, longitud);

				if (etiqueta == REGISTRO_HEAP_DUMP || etiqueta == REGISTRO_HEAP_DUMP_SEGMENT) {
					leerSubregistrosHeap(entrada, fin, metadatos, resultado, true, cancelado);
				}

				entrada.seek(fin);
				notificar(progreso, 45 + porcentaje(inicioRegistro, longitud, 44), archivo.getName());
			}
		}

		return resultado;
	}

	private static void leerCabecera(RandomAccessFile entrada, Metadatos metadatos) throws IOException {
		StringBuilder cabecera = new StringBuilder();
		int caracter;

		while ((caracter = entrada.read()) != -1 && caracter != 0) {
			if (cabecera.length() > 200) {
				throw new IOException("Cabecera HPROF demasiado larga");
			}
			cabecera.append((char) caracter);
		}

		if (caracter == -1 || !cabecera.toString().startsWith("JAVA PROFILE")) {
			throw new IOException("El archivo no tiene una cabecera HPROF válida");
		}

		int tamanoId = entrada.readInt();
		if (tamanoId != 4 && tamanoId != 8) {
			throw new IOException("Tamaño de identificador HPROF no compatible: " + tamanoId);
		}

		metadatos.tamanoId = tamanoId;
		entrada.readLong();
	}

	private static void leerString(RandomAccessFile entrada, long largo, Metadatos metadatos) throws IOException {
		if (largo < metadatos.tamanoId) {
			throw new IOException("Registro STRING HPROF truncado");
		}

		long id = leerId(entrada, metadatos.tamanoId);
		long cantidad = largo - metadatos.tamanoId;

		if (cantidad > MAXIMO_STRING_GUARDADO) {
			saltar(entrada, cantidad);
			return;
		}

		byte[] datos = new byte[(int) cantidad];
		entrada.readFully(datos);
		metadatos.strings.put(Long.valueOf(id), new String(datos, StandardCharsets.UTF_8));
	}

	private static void leerLoadClass(RandomAccessFile entrada, Metadatos metadatos) throws IOException {
		leerU4(entrada);
		long claseId = leerId(entrada, metadatos.tamanoId);
		leerU4(entrada);
		long nombreId = leerId(entrada, metadatos.tamanoId);
		metadatos.nombreIdPorClase.put(Long.valueOf(claseId), Long.valueOf(nombreId));
	}

	private static void leerSubregistrosHeap(RandomAccessFile entrada, long fin, Metadatos metadatos,
			ResultadoAcumulado resultado, boolean acumular, AtomicBoolean cancelado) throws IOException {
		while (entrada.getFilePointer() < fin) {
			comprobarCancelacion(cancelado);
			int sub = leerU1(entrada);

			switch (sub) {
			case 0xff:
				leerId(entrada, metadatos.tamanoId);
				break;
			case 0x01:
				leerId(entrada, metadatos.tamanoId);
				leerId(entrada, metadatos.tamanoId);
				break;
			case 0x02:
			case 0x03:
				leerId(entrada, metadatos.tamanoId);
				leerU4(entrada);
				leerU4(entrada);
				break;
			case 0x04:
			case 0x06:
				leerId(entrada, metadatos.tamanoId);
				leerU4(entrada);
				break;
			case 0x05:
			case 0x07:
			case 0x89:
			case 0x8a:
			case 0x8b:
			case 0x8c:
			case 0x8d:
			case 0x90:
				leerId(entrada, metadatos.tamanoId);
				break;
			case 0x08:
				leerId(entrada, metadatos.tamanoId);
				leerU4(entrada);
				leerU4(entrada);
				break;
			case 0x8e:
				leerId(entrada, metadatos.tamanoId);
				leerU4(entrada);
				leerU4(entrada);
				break;
			case SUB_HEAP_DUMP_INFO:
				leerU4(entrada);
				leerId(entrada, metadatos.tamanoId);
				break;
			case SUB_CLASS_DUMP:
				leerClassDump(entrada, metadatos);
				break;
			case SUB_INSTANCE_DUMP:
				leerInstanceDump(entrada, metadatos, resultado, acumular);
				break;
			case SUB_OBJECT_ARRAY_DUMP:
				leerObjectArrayDump(entrada, metadatos, resultado, acumular);
				break;
			case SUB_PRIMITIVE_ARRAY_DUMP:
				leerPrimitiveArrayDump(entrada, metadatos, resultado, acumular, true);
				break;
			case SUB_PRIMITIVE_ARRAY_SIN_DATOS:
				leerPrimitiveArrayDump(entrada, metadatos, resultado, acumular, false);
				break;
			default:
				throw new IOException(String.format(java.util.Locale.ROOT, "Subregistro HPROF no compatible: 0x%02x",
						Integer.valueOf(sub)));
			}
		}

		if (entrada.getFilePointer() != fin) {
			throw new IOException("Longitud inconsistente dentro del HEAP_DUMP");
		}
	}

	private static void leerClassDump(RandomAccessFile entrada, Metadatos metadatos) throws IOException {
		long claseId = leerId(entrada, metadatos.tamanoId);
		leerU4(entrada);

		for (int i = 0; i < 6; i++) {
			leerId(entrada, metadatos.tamanoId);
		}

		long tamanoInstancia = leerU4(entrada);
		metadatos.tamanoInstanciaPorClase.put(Long.valueOf(claseId), Long.valueOf(tamanoInstancia));

		int constantes = leerU2(entrada);
		for (int i = 0; i < constantes; i++) {
			leerU2(entrada);
			int tipo = leerU1(entrada);
			saltarValor(entrada, tipo, metadatos.tamanoId);
		}

		int estaticos = leerU2(entrada);
		for (int i = 0; i < estaticos; i++) {
			leerId(entrada, metadatos.tamanoId);
			int tipo = leerU1(entrada);
			saltarValor(entrada, tipo, metadatos.tamanoId);
		}

		int campos = leerU2(entrada);
		for (int i = 0; i < campos; i++) {
			leerId(entrada, metadatos.tamanoId);
			leerU1(entrada);
		}
	}

	private static void leerInstanceDump(RandomAccessFile entrada, Metadatos metadatos, ResultadoAcumulado resultado,
			boolean acumular) throws IOException {
		leerId(entrada, metadatos.tamanoId);
		leerU4(entrada);
		long claseId = leerId(entrada, metadatos.tamanoId);
		long largoDatos = leerU4(entrada);

		if (acumular) {
			long tamano = valor(metadatos.tamanoInstanciaPorClase.get(Long.valueOf(claseId)));
			if (tamano <= 0L) {
				tamano = alinear(CABECERA_OBJETO_ESTIMADA + largoDatos);
			}

			String clase = nombreClase(metadatos, claseId);
			resultado.agregar(clase, 1L, tamano);
		}

		saltar(entrada, largoDatos);
	}

	private static void leerObjectArrayDump(RandomAccessFile entrada, Metadatos metadatos, ResultadoAcumulado resultado,
			boolean acumular) throws IOException {
		leerId(entrada, metadatos.tamanoId);
		leerU4(entrada);
		long cantidad = leerU4(entrada);
		long claseId = leerId(entrada, metadatos.tamanoId);

		if (acumular) {
			long bytes = alinear(CABECERA_ARREGLO_ESTIMADA + multiplicarSeguro(cantidad, metadatos.tamanoId));
			String clase = nombreClase(metadatos, claseId);
			resultado.agregar(clase, 1L, bytes);
		}

		saltar(entrada, multiplicarSeguro(cantidad, metadatos.tamanoId));
	}

	private static void leerPrimitiveArrayDump(RandomAccessFile entrada, Metadatos metadatos,
			ResultadoAcumulado resultado, boolean acumular, boolean tieneDatos) throws IOException {
		leerId(entrada, metadatos.tamanoId);
		leerU4(entrada);
		long cantidad = leerU4(entrada);
		int tipo = leerU1(entrada);
		int tamanoElemento = tamanoTipo(tipo, metadatos.tamanoId);

		if (acumular) {
			long bytes = alinear(CABECERA_ARREGLO_ESTIMADA + multiplicarSeguro(cantidad, tamanoElemento));
			resultado.agregar(nombreTipoPrimitivo(tipo) + "[]", 1L, bytes);
		}

		if (tieneDatos) {
			saltar(entrada, multiplicarSeguro(cantidad, tamanoElemento));
		}
	}

	private static void saltarValor(RandomAccessFile entrada, int tipo, int tamanoId) throws IOException {
		saltar(entrada, tamanoTipo(tipo, tamanoId));
	}

	private static int tamanoTipo(int tipo, int tamanoId) throws IOException {
		switch (tipo) {
		case 2:
			return tamanoId;
		case 4:
		case 8:
			return 1;
		case 5:
		case 9:
			return 2;
		case 6:
		case 10:
			return 4;
		case 7:
		case 11:
			return 8;
		default:
			throw new IOException("Tipo HPROF desconocido: " + tipo);
		}
	}

	private static String nombreTipoPrimitivo(int tipo) {
		switch (tipo) {
		case 4:
			return "boolean";
		case 5:
			return "char";
		case 6:
			return "float";
		case 7:
			return "double";
		case 8:
			return "byte";
		case 9:
			return "short";
		case 10:
			return "int";
		case 11:
			return "long";
		default:
			return MonitorDePID.idioma.heapVisorTipoPrimitivoDesconocido();
		}
	}

	private static String nombreClase(Metadatos metadatos, long claseId) {
		String nombre = metadatos.nombrePorClase.get(Long.valueOf(claseId));
		return nombre == null ? MonitorDePID.idioma.heapVisorClaseId(Long.toHexString(claseId)) : nombre;
	}

	private static String normalizarNombreClase(String nombre) {
		if (nombre == null) {
			return MonitorDePID.idioma.heapVisorClaseDesconocida();
		}

		String valor = nombre.replace('/', '.');

		if (valor.startsWith("[")) {
			int dimensiones = 0;
			while (dimensiones < valor.length() && valor.charAt(dimensiones) == '[') {
				dimensiones++;
			}

			String base;
			char tipo = dimensiones < valor.length() ? valor.charAt(dimensiones) : '?';
			switch (tipo) {
			case 'Z':
				base = "boolean";
				break;
			case 'B':
				base = "byte";
				break;
			case 'C':
				base = "char";
				break;
			case 'S':
				base = "short";
				break;
			case 'I':
				base = "int";
				break;
			case 'J':
				base = "long";
				break;
			case 'F':
				base = "float";
				break;
			case 'D':
				base = "double";
				break;
			case 'L':
				int fin = valor.indexOf(';', dimensiones);
				base = fin > dimensiones ? valor.substring(dimensiones + 1, fin) : valor;
				break;
			default:
				base = valor;
				break;
			}

			StringBuilder normalizado = new StringBuilder(base);
			for (int i = 0; i < dimensiones; i++) {
				normalizado.append("[]");
			}
			return normalizado.toString();
		}

		return valor;
	}

	private static int porcentaje(long posicion, long total, int amplitud) {
		if (total <= 0L) {
			return 0;
		}
		return (int) Math.min(amplitud, (posicion * amplitud) / total);
	}

	private static void notificar(Progreso progreso, int porcentaje, String detalle) {
		if (progreso != null) {
			progreso.actualizar(Math.max(0, Math.min(100, porcentaje)), detalle);
		}
	}

	private static long leerId(RandomAccessFile entrada, int tamanoId) throws IOException {
		return tamanoId == 4 ? leerU4(entrada) : entrada.readLong();
	}

	private static int leerU1(RandomAccessFile entrada) throws IOException {
		return entrada.readUnsignedByte();
	}

	private static int leerU2(RandomAccessFile entrada) throws IOException {
		return entrada.readUnsignedShort();
	}

	private static long leerU4(RandomAccessFile entrada) throws IOException {
		return ((long) entrada.readInt()) & 0xffffffffL;
	}

	private static void saltar(RandomAccessFile entrada, long cantidad) throws IOException {
		if (cantidad < 0L) {
			throw new IOException("Longitud HPROF negativa");
		}
		long destino = entrada.getFilePointer() + cantidad;
		if (destino < entrada.getFilePointer() || destino > entrada.length()) {
			throw new EOFException("Registro HPROF truncado");
		}
		entrada.seek(destino);
	}

	private static long sumaSegura(long a, long b, long limite) throws IOException {
		long resultado = a + b;
		if (b < 0L || resultado < a || resultado > limite) {
			throw new EOFException("Longitud de registro HPROF fuera del archivo");
		}
		return resultado;
	}

	private static long multiplicarSeguro(long a, long b) throws IOException {
		if (a < 0L || b < 0L || (a != 0L && b > Long.MAX_VALUE / a)) {
			throw new IOException("Tamaño HPROF desbordado");
		}
		return a * b;
	}

	private static long alinear(long valor) {
		long resto = valor % ALINEACION_OBJETO;
		return resto == 0L ? valor : valor + (ALINEACION_OBJETO - resto);
	}

	private static long valor(Long valor) {
		return valor == null ? 0L : valor.longValue();
	}

	private static void validarArchivo(File archivo) throws IOException {
		if (archivo == null || !archivo.isFile() || !archivo.canRead()) {
			throw new IOException("No se puede leer el heap dump");
		}
	}

	private static void comprobarCancelacion(AtomicBoolean cancelado) {
		if (Thread.currentThread().isInterrupted() || (cancelado != null && cancelado.get())) {
			throw new CancellationException();
		}
	}

	private static final class Metadatos {
		int tamanoId;
		final Map<Long, String> strings = new HashMap<Long, String>();
		final Map<Long, Long> nombreIdPorClase = new HashMap<Long, Long>();
		final Map<Long, String> nombrePorClase = new HashMap<Long, String>();
		final Map<Long, Long> tamanoInstanciaPorClase = new HashMap<Long, Long>();
	}

	private static final class ResultadoAcumulado {
		final Map<String, EstadisticaHeap> porClase = new HashMap<String, EstadisticaHeap>();
		long bytesTotales;
		long objetosTotales;

		void agregar(String clase, long cantidad, long bytes) {
			EstadisticaHeap estadistica = porClase.get(clase);
			if (estadistica == null) {
				estadistica = new EstadisticaHeap(clase);
				porClase.put(clase, estadistica);
			}
			estadistica.agregar(cantidad, bytes);
			objetosTotales += cantidad;
			bytesTotales += bytes;
		}
	}
}
