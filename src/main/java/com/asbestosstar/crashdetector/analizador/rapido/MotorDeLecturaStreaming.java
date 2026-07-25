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
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.motor.MotorBusquedaBytes;
import com.asbestosstar.crashdetector.analizador.rapido.motor.MotoresBusqueda;
import com.asbestosstar.crashdetector.limpiador.LimpiadorDeRegistro;
import com.asbestosstar.crashdetector.limpiador.LimpiadorNingun;

public final class MotorDeLecturaStreaming {

	private static final int BUFFER_SIZE = 1024 * 1024;

	private final MotorBusquedaBytes motorBytes;
	private AutomataDePatrones automata;
	private final Map<String, List<Verificaciones>> patronesAVerificaciones = new HashMap<>();

	public MotorDeLecturaStreaming() {
		this.motorBytes = MotoresBusqueda.crear();
	}

	public void procesarLineas(Consola consola, String[] lineas, List<Verificaciones> verificacionesPatrones,
			List<Verificaciones> verificacionesLineales, EstadoAnalisisArchivo estado, ProcesadorVDSTAsync vdstAsync) {

		inicializarAutomata(verificacionesPatrones);

		if (lineas == null) {
			return;
		}

		for (int i = 0; i < lineas.length; i++) {
			procesarLinea(consola, lineas[i], i, verificacionesLineales, estado, vdstAsync);
			estado.lineasLeidas = i + 1;
		}
	}

	public void procesarEnVivo(Consola consola, InputStream inputStream, List<Verificaciones> verificacionesPatrones,
			List<Verificaciones> verificacionesLineales, EstadoAnalisisArchivo estado, ProcesadorVDSTAsync vdstAsync) {

		inicializarAutomata(verificacionesPatrones);

		if (inputStream == null) {
			return;
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			String linea;
			int numeroLinea = 0;

			while ((linea = reader.readLine()) != null) {
				procesarLinea(consola, linea, numeroLinea, verificacionesLineales, estado, vdstAsync);

				numeroLinea++;
				estado.lineasLeidas = numeroLinea;
			}

		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
		}
	}

	public void procesarArchivo(Consola consola, List<Verificaciones> verificacionesPatrones,
			List<Verificaciones> verificacionesLineales, EstadoAnalisisArchivo estado, ProcesadorVDSTAsync vdstAsync) {

		if (consola == null || consola.archivo == null) {
			return;
		}

		/*
		 * Antes de ejecutar verificarCoincidencia(...) o verificarPorLinea(...),
		 * asegurar que los tres campos de contenido estén disponibles.
		 *
		 * - consola.contenido - consola.contenido_verificar - consola.lineas_verificar
		 */
		try {
			asegurarContenidoDesdeArchivo(consola);
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			return;
		}

		if (consola.lineas_verificar == null) {
			CrashDetectorLogger
					.log("No se pudieron preparar lineas_verificar para: " + String.valueOf(consola.archivo));
			return;
		}

		/*
		 * Analizar exactamente lineas_verificar conserva la semántica de
		 * Consola.finalizarContenido(...): respeta linea_original y los limpiadores.
		 *
		 * Además, los tres campos ya existen cuando una verificación consulta la
		 * Consola durante verificarCoincidencia(...) o verificarPorLinea(...).
		 */
		procesarLineas(consola, consola.lineas_verificar, verificacionesPatrones, verificacionesLineales, estado,
				vdstAsync);

		try {
			estado.bytesLeidos = java.nio.file.Files.size(consola.archivo);
		} catch (IOException e) {
			/*
			 * El análisis ya terminó. Un fallo al consultar el tamaño solamente afecta la
			 * estadística y no invalida los resultados.
			 */
			CrashDetectorLogger.logException(e);
		}
	}

	/**
	 * Completa únicamente los campos ausentes de Consola.
	 *
	 * contenido conserva el archivo original completo.
	 *
	 * contenido_verificar contiene el archivo desde linea_original y después de
	 * aplicar el limpiador correspondiente.
	 *
	 * lineas_verificar se deriva de contenido_verificar.
	 */
	private void asegurarContenidoDesdeArchivo(Consola consola) throws IOException {

		boolean faltaContenido = consola.contenido == null;

		boolean faltaContenidoVerificar = consola.contenido_verificar == null;

		boolean faltanLineasVerificar = consola.lineas_verificar == null;

		if (!faltaContenido && !faltaContenidoVerificar && !faltanLineasVerificar) {

			return;
		}

		String contenidoArchivo = consola.contenido;

		if (contenidoArchivo == null) {
			contenidoArchivo = MonitorDePID.leer_archivo(consola.archivo);
		}

		if (contenidoArchivo == null) {
			contenidoArchivo = "";
		}

		if (faltaContenido) {
			consola.contenido = contenidoArchivo;
		}

		if (faltaContenidoVerificar) {
			String paraVerificar = construirContenidoParaVerificar(contenidoArchivo, consola.linea_original);

			consola.contenido_verificar = aplicarLimpiador(consola, paraVerificar);
		}

		if (consola.contenido_verificar == null) {
			consola.contenido_verificar = "";
		}

		if (faltanLineasVerificar) {
			consola.lineas_verificar = consola.contenido_verificar.split("\\r?\\n", -1);
		}
	}

	private String construirContenidoParaVerificar(String contenidoArchivo, int lineaOriginal) {

		String[] lineas = contenidoArchivo.split("\\r?\\n", -1);

		int inicio = Math.max(0, Math.min(lineaOriginal, lineas.length));

		StringBuilder resultado = new StringBuilder(contenidoArchivo.length() + 64);

		for (int i = inicio; i < lineas.length; i++) {
			resultado.append(lineas[i]);

			if (i < lineas.length - 1) {
				resultado.append('\n');
			}
		}

		return resultado.toString();
	}

	/**
	 * Reproduce el comportamiento actual de Consola.finalizarContenido(...).
	 *
	 * Si más de un limpiador coincide, se conserva el resultado del último.
	 */
	private String aplicarLimpiador(Consola consola, String contenidoParaVerificar) {

		boolean limpiado = false;
		String resultado = contenidoParaVerificar;

		for (LimpiadorDeRegistro limpiador : Consola.limpiadores) {

			if (limpiador == null || !limpiador.predicado(consola.archivo)) {

				continue;
			}

			String contenidoLimpio = limpiador.limpiarConsola(contenidoParaVerificar);

			resultado = contenidoLimpio == null ? "" : contenidoLimpio;

			consola.limpiador = limpiador;

			limpiado = true;
		}

		if (!limpiado) {
			consola.limpiador = new LimpiadorNingun();

			resultado = contenidoParaVerificar;
		}

		return resultado == null ? "" : resultado;
	}

	private void guardarLineaStreaming(byte[] datos, int inicio, int fin, StringBuilder contenidoStreaming,
			List<String> lineasStreaming) {

		if (datos == null || contenidoStreaming == null || lineasStreaming == null || fin < inicio) {
			return;
		}

		if (fin > inicio && datos[fin - 1] == '\r') {
			fin--;
		}

		String lineaTexto = new String(datos, inicio, fin - inicio, StandardCharsets.UTF_8);

		lineasStreaming.add(lineaTexto);

		if (contenidoStreaming.length() > 0) {
			contenidoStreaming.append('\n');
		}

		contenidoStreaming.append(lineaTexto);
	}

	public void procesarLinea(Consola consola, byte[] datos, int inicio, int fin, int numeroLinea,
			List<Verificaciones> verificacionesLineales, boolean necesitaStringPorLinea, EstadoAnalisisArchivo estado,
			ProcesadorVDSTAsync vdstAsync) {

		procesarLinea(consola, datos, inicio, fin, numeroLinea, verificacionesLineales, necesitaStringPorLinea, estado,
				null, vdstAsync);
	}

	public void procesarLinea(Consola consola, String linea, int numeroLinea,
			List<Verificaciones> verificacionesLineales, EstadoAnalisisArchivo estado, ProcesadorVDSTAsync vdstAsync) {

		if (linea == null || linea.isEmpty()) {
			return;
		}

		byte[] bytes = linea.getBytes(StandardCharsets.UTF_8);

		procesarLinea(consola, bytes, 0, bytes.length, numeroLinea, verificacionesLineales, true, estado, linea,
				vdstAsync);
	}

	private void procesarLinea(Consola consola, byte[] datos, int inicio, int fin, int numeroLinea,
			List<Verificaciones> verificacionesLineales, boolean necesitaStringPorLinea, EstadoAnalisisArchivo estado,
			String lineaYaDecodificada, ProcesadorVDSTAsync vdstAsync) {

		if (datos == null || fin <= inicio) {
			return;
		}

		if (fin > inicio && datos[fin - 1] == '\r') {
			fin--;
		}

		String linea = lineaYaDecodificada;

		if (vdstAsync != null) {
			if (linea == null) {
				linea = new String(datos, inicio, fin - inicio, StandardCharsets.UTF_8);
			}

			vdstAsync.aceptarLinea(linea, numeroLinea);
		}

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

							/*
							 * Las instancias de Verificaciones se comparten entre logs para conservar sus
							 * resultados en Analizador.toString(). Una sola consola puede mutar cada
							 * verificacion a la vez.
							 */
							synchronized (ver) {
								ver.verificarCoincidencia(evento);
							}
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
					synchronized (ver) {
						ver.verificarPorLinea(consola, linea, numeroLinea);
					}
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

				patronesAVerificaciones.computeIfAbsent(patron, k -> new ArrayList<Verificaciones>()).add(ver);
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