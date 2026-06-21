package com.asbestosstar.crashdetector.analizador.rapido;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.motor.MotorBusquedaBytes;
import com.asbestosstar.crashdetector.analizador.rapido.motor.MotoresBusqueda;

public final class MotorDeLecturaStreaming {

	private static final int BUFFER_SIZE = 1024 * 1024;

	private final MotorBusquedaBytes motorBytes;
	private AutomataDePatrones automata;
	private final Map<String, List<Verificaciones>> patronesAVerificaciones = new HashMap<>();

	public MotorDeLecturaStreaming() {
		this.motorBytes = MotoresBusqueda.crear();
	}

	public void procesarLineas(Consola consola, String[] lineas, List<Verificaciones> verificacionesPatrones,
			List<Verificaciones> verificacionesLineales, EstadoAnalisisArchivo estado) {
		inicializarAutomata(verificacionesPatrones);

		if (lineas == null) {
			return;
		}

		for (int i = 0; i < lineas.length; i++) {
			procesarLinea(consola, lineas[i], i, verificacionesLineales, estado);
			estado.lineasLeidas = i + 1;
		}
	}

	public void procesarEnVivo(Consola consola, InputStream inputStream, List<Verificaciones> verificacionesPatrones,
			List<Verificaciones> verificacionesLineales, EstadoAnalisisArchivo estado) {
		inicializarAutomata(verificacionesPatrones);

		if (inputStream == null) {
			return;
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			String linea;
			int numeroLinea = 0;

			while ((linea = reader.readLine()) != null) {
				procesarLinea(consola, linea, numeroLinea, verificacionesLineales, estado);

				numeroLinea++;
				estado.lineasLeidas = numeroLinea;
			}

		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
		}
	}

	public void procesarArchivo(Consola consola, List<Verificaciones> verificacionesPatrones,
			List<Verificaciones> verificacionesLineales, EstadoAnalisisArchivo estado) {
		inicializarAutomata(verificacionesPatrones);

		Path path = consola.archivo;

		if (path == null) {
			return;
		}

		boolean necesitaStringPorLinea = verificacionesLineales != null && !verificacionesLineales.isEmpty();

		try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), "r"); FileChannel channel = raf.getChannel()) {

			ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
			byte[] heapBuffer = new byte[BUFFER_SIZE];

			byte[] bufferResto = new byte[BUFFER_SIZE * 2];
			int restoAnterior = 0;

			int[] posicionesSaltoLinea = new int[4096];

			long posicionGlobal = 0;
			int numeroLineaActual = 0;

			while (channel.read(buffer) != -1) {
				buffer.flip();

				int bytesLeidos = buffer.remaining();
				buffer.get(heapBuffer, 0, bytesLeidos);

				int inicioActual = 0;

				while (inicioActual < bytesLeidos) {
					int totalPosiciones = motorBytes.buscar(heapBuffer, inicioActual, bytesLeidos, (byte) '\n',
							posicionesSaltoLinea, posicionesSaltoLinea.length);

					if (totalPosiciones <= 0) {
						break;
					}

					for (int i = 0; i < totalPosiciones; i++) {
						int finLinea = posicionesSaltoLinea[i];

						if (restoAnterior > 0) {
							int lenLinea = finLinea - inicioActual;

							byte[] lineaCompleta = new byte[restoAnterior + lenLinea];

							System.arraycopy(bufferResto, 0, lineaCompleta, 0, restoAnterior);
							System.arraycopy(heapBuffer, inicioActual, lineaCompleta, restoAnterior, lenLinea);

							procesarLinea(consola, lineaCompleta, 0, lineaCompleta.length, numeroLineaActual,
									verificacionesLineales, necesitaStringPorLinea, estado);

							restoAnterior = 0;
						} else {
							procesarLinea(consola, heapBuffer, inicioActual, finLinea, numeroLineaActual,
									verificacionesLineales, necesitaStringPorLinea, estado);
						}

						numeroLineaActual++;
						inicioActual = finLinea + 1;
					}

					if (totalPosiciones < posicionesSaltoLinea.length) {
						break;
					}
				}

				if (inicioActual < bytesLeidos) {
					restoAnterior = bytesLeidos - inicioActual;

					if (restoAnterior > bufferResto.length) {
						bufferResto = new byte[Math.max(restoAnterior, bufferResto.length * 2)];
					}

					System.arraycopy(heapBuffer, inicioActual, bufferResto, 0, restoAnterior);
				}

				posicionGlobal += bytesLeidos;
				estado.bytesLeidos = posicionGlobal;
				estado.lineasLeidas = numeroLineaActual;

				buffer.clear();
			}

			if (restoAnterior > 0) {
				procesarLinea(consola, bufferResto, 0, restoAnterior, numeroLineaActual, verificacionesLineales,
						necesitaStringPorLinea, estado);

				estado.lineasLeidas = numeroLineaActual + 1;
			}

		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
		}
	}

	public void procesarLinea(Consola consola, byte[] datos, int inicio, int fin, int numeroLinea,
			List<Verificaciones> verificacionesLineales, boolean necesitaStringPorLinea, EstadoAnalisisArchivo estado) {
		procesarLinea(consola, datos, inicio, fin, numeroLinea, verificacionesLineales, necesitaStringPorLinea, estado,
				null);
	}

	public void procesarLinea(Consola consola, String linea, int numeroLinea,
			List<Verificaciones> verificacionesLineales, EstadoAnalisisArchivo estado) {
		if (linea == null || linea.isEmpty()) {
			return;
		}

		byte[] bytes = linea.getBytes(StandardCharsets.UTF_8);

		procesarLinea(consola, bytes, 0, bytes.length, numeroLinea, verificacionesLineales, true, estado, linea);
	}

	private void procesarLinea(Consola consola, byte[] datos, int inicio, int fin, int numeroLinea,
			List<Verificaciones> verificacionesLineales, boolean necesitaStringPorLinea, EstadoAnalisisArchivo estado,
			String lineaYaDecodificada) {
		if (datos == null || fin <= inicio) {
			return;
		}

		if (fin > inicio && datos[fin - 1] == '\r') {
			fin--;
		}

		String linea = lineaYaDecodificada;

		if (automata != null) {
			List<AutomataDePatrones.Coincidencia> coincidencias = automata.buscar(datos, inicio, fin);

			if (coincidencias != null && !coincidencias.isEmpty()) {
				if (linea == null) {
					linea = new String(datos, inicio, fin - inicio, StandardCharsets.UTF_8);
				}

				for (AutomataDePatrones.Coincidencia base : coincidencias) {
					List<Verificaciones> verificacionesDelPatron = patronesAVerificaciones.get(base.patron);

					if (verificacionesDelPatron == null || verificacionesDelPatron.isEmpty()) {
						continue;
					}

					int inicioEnLinea = base.inicio - inicio;
					int finEnLinea = base.fin - inicio;

					for (Verificaciones ver : verificacionesDelPatron) {
						try {
							EventoDeCoincidencia evento = new EventoDeCoincidencia(consola, consola.archivo, ver,
									base.patron, linea, numeroLinea, inicioEnLinea, finEnLinea, estado);

							ver.verificarCoincidencia(evento);
						} catch (Exception e) {
							CrashDetectorLogger.logException(e);
						}
					}
				}
			}
		}

		if (necesitaStringPorLinea) {
			if (linea == null) {
				linea = new String(datos, inicio, fin - inicio, StandardCharsets.UTF_8);
			}

			for (Verificaciones ver : verificacionesLineales) {
				try {
					ver.verificarPorLinea(consola, linea, numeroLinea);
				} catch (Exception e) {
					CrashDetectorLogger.logException(e);
				}
			}
		}
	}

	private void inicializarAutomata(List<Verificaciones> verificacionesPatrones) {
		patronesAVerificaciones.clear();
		automata = null;

		if (verificacionesPatrones == null || verificacionesPatrones.isEmpty()) {
			CrashDetectorLogger.log("[DEBUG_LOG] No hay verificaciones para inicializar el autómata");
			return;
		}

		for (Verificaciones ver : verificacionesPatrones) {
			if (ver == null) {
				continue;
			}

			String[] patrones = ver.patronesRapidos();

			if (patrones == null || patrones.length == 0) {
				continue;
			}

			for (String patron : patrones) {
				if (patron == null || patron.isEmpty()) {
					continue;
				}

				patronesAVerificaciones.computeIfAbsent(patron, k -> new ArrayList<>()).add(ver);
			}
		}

		if (patronesAVerificaciones.isEmpty()) {
			CrashDetectorLogger.log("[DEBUG_LOG] No se encontraron patrones para inicializar el autómata");
			return;
		}

		String[] todosLosPatrones = patronesAVerificaciones.keySet().toArray(new String[0]);

		CrashDetectorLogger
				.log("[DEBUG_LOG] Inicializando autómata BYTE con " + todosLosPatrones.length + " patrones únicos");

		this.automata = new AutomataDePatrones(todosLosPatrones);
	}
}