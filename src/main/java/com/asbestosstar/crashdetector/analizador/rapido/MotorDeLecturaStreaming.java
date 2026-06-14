package com.asbestosstar.crashdetector.analizador.rapido;

import java.io.IOException;
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

/**
 * Motor de lectura streaming que procesa el log por bloques.
 */
public final class MotorDeLecturaStreaming {

	private static final int BUFFER_SIZE = 1024 * 1024; // 1 MiB inicial
	private final MotorBusquedaBytes motorBytes;
	private AutomataDePatrones automata;
	private final Map<String, List<VerificacionRapida>> patronesAVerificaciones = new HashMap<>();

	public MotorDeLecturaStreaming() {
		this.motorBytes = MotoresBusqueda.crear();
	}

	public void procesar(Consola consola, List<VerificacionRapida> verificaciones, List<Verificaciones> legacy,
			EstadoAnalisisArchivo estado) {

		inicializarAutomata(verificaciones);

		Path path = consola.archivo;
		if (path == null)
			return;

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

						String contenidoLinea;

						if (restoAnterior > 0) {
							int lenLinea = finLinea - inicioActual;

							byte[] lineaCompleta = new byte[restoAnterior + lenLinea];
							System.arraycopy(bufferResto, 0, lineaCompleta, 0, restoAnterior);
							System.arraycopy(heapBuffer, inicioActual, lineaCompleta, restoAnterior, lenLinea);

							contenidoLinea = new String(lineaCompleta, StandardCharsets.UTF_8);
							restoAnterior = 0;
						} else {
							contenidoLinea = new String(heapBuffer, inicioActual, finLinea - inicioActual,
									StandardCharsets.UTF_8);
						}

						if (!contenidoLinea.isEmpty() && contenidoLinea.charAt(contenidoLinea.length() - 1) == '\r') {
							contenidoLinea = contenidoLinea.substring(0, contenidoLinea.length() - 1);
						}

						procesarLinea(consola, contenidoLinea, numeroLineaActual++, verificaciones, legacy, estado);

						inicioActual = finLinea + 1;
					}

					// If the position array filled, continue scanning the same block.
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
				String ultimaLinea = new String(bufferResto, 0, restoAnterior, StandardCharsets.UTF_8);

				if (!ultimaLinea.isEmpty() && ultimaLinea.charAt(ultimaLinea.length() - 1) == '\r') {
					ultimaLinea = ultimaLinea.substring(0, ultimaLinea.length() - 1);
				}

				procesarLinea(consola, ultimaLinea, numeroLineaActual++, verificaciones, legacy, estado);
			}

		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
		}
	}

	public void procesarLinea(Consola consola, String linea, int numeroLinea, List<VerificacionRapida> verificaciones,
			List<Verificaciones> legacy, EstadoAnalisisArchivo estado) {

		if (linea == null || linea.isEmpty())
			return;

		if (consola.verificacion_de_stacktrace != null) {
			consola.verificacion_de_stacktrace.procesarLineaIncremental(linea, numeroLinea);
		}

		if (automata == null) {
			inicializarAutomata(verificaciones);
		}

		if (automata != null) {
			List<AutomataDePatrones.Coincidencia> coincidencias = automata.buscar(linea);

			for (AutomataDePatrones.Coincidencia coincidencia : coincidencias) {
				List<VerificacionRapida> vers = patronesAVerificaciones.get(coincidencia.patron);

				if (vers == null)
					continue;

				for (VerificacionRapida ver : vers) {
					try {
						EventoDeCoincidencia evento = new EventoDeCoincidencia(consola, consola.archivo, ver,
								coincidencia.patron, linea, numeroLinea, coincidencia.posicion,
								coincidencia.posicion + coincidencia.patron.length());

						estado.agregarCoincidencia(evento);
						ver.verificarCoincidencia(evento);
					} catch (Exception e) {
						CrashDetectorLogger.logException(e);
					}
				}
			}
		}

		// Only true "every-line" verifications should run here.
		for (VerificacionRapida ver : verificaciones) {
			try {
				if (ver.necesitaTodasLasLineas()) {
					ver.verificarPorLinea(consola, linea, numeroLinea);
				}
			} catch (Exception e) {
				CrashDetectorLogger.logException(e);
			}
		}

		if (legacy != null) {
			for (Verificaciones ver : legacy) {
				try {
					if (ver.necesitaTodasLasLineas()) {
						ver.verificarPorLinea(consola, linea, numeroLinea);
					}
				} catch (Exception e) {
					CrashDetectorLogger.logException(e);
				}
			}
		}
	}

	private void inicializarAutomata(List<VerificacionRapida> verificaciones) {
		if (automata != null) {
			return;
		}

		patronesAVerificaciones.clear();
		for (VerificacionRapida ver : verificaciones) {
			String[] patrones = ver.patronesRapidos();
			if (patrones == null) {
				CrashDetectorLogger
						.log("[DEBUG_LOG] ADVERTENCIA: Verificación " + ver.id() + " devolvió patrones null");
				continue;
			}
			for (String patron : patrones) {
				if (patron != null && !patron.isEmpty()) {
					patronesAVerificaciones.computeIfAbsent(patron, k -> new ArrayList<>()).add(ver);
				}
			}
		}

		if (!patronesAVerificaciones.isEmpty()) {
			String[] todosLosPatrones = patronesAVerificaciones.keySet().toArray(new String[0]);
			CrashDetectorLogger
					.log("[DEBUG_LOG] Inicializando autómata con " + todosLosPatrones.length + " patrones únicos");
			this.automata = new AutomataDePatrones(todosLosPatrones);
		} else {
			CrashDetectorLogger.log("[DEBUG_LOG] No se encontraron patrones para inicializar el autómata");
		}
	}
}
