package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NoTieneMemoria implements Verificaciones {

	private boolean activado = false;
	private final CDStringBuilder mensajes = new CDStringBuilder();
	private boolean esProblemaDeMemoriaInsuficiente = false;
	private boolean esProblemaDeMemoriaExcesiva = false;
	private boolean esProblemaPermGen = false;

	private static final String OUT_OF_MEMORY = "java.lang.OutOfMemoryError";
	private static final String GC_OVERHEAD = "GC overhead limit exceeded";
	private static final String DIRECT_BUFFER = "Direct buffer memory";
	private static final String NATIVE_THREAD = "unable to create new native thread";
	private static final String OUT_OF_MEMORY_ERROR = "Out of Memory Error";
	private static final String INSUFFICIENT_MEMORY = "Insufficient memory";
	private static final String PROBLEM_WITH_RAM = "Problem with RAM";
	private static final String COULD_NOT_RESERVE = "Could not reserve enough space for";
	private static final String SIZE_EXCEEDS_MAX = "The specified size exceeds the maximum representable size";
	private static final String INVALID_HEAP = "Invalid maximum heap size";
	private static final String JVM_INSUFFICIENT_MEMORY = "There is insufficient memory for the Java Runtime Environment to continue";
	private static final String PERMGEN_ERROR = "PermGen error";
	private static final String PERMGEN_SPACE = "java.lang.OutOfMemoryError: PermGen space";
	private static final String PERMGEN_EXIT_CODE = "-805306369";

	@Override
	public String[] patronesRapidos() {
		return new String[] { OUT_OF_MEMORY, GC_OVERHEAD, DIRECT_BUFFER, NATIVE_THREAD, OUT_OF_MEMORY_ERROR,
				INSUFFICIENT_MEMORY, PROBLEM_WITH_RAM, COULD_NOT_RESERVE, SIZE_EXCEEDS_MAX, INVALID_HEAP,
				JVM_INSUFFICIENT_MEMORY, PERMGEN_ERROR, PERMGEN_SPACE, PERMGEN_EXIT_CODE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (linea == null || linea.isEmpty() || activado) {
			return;
		}

		if (!lineaEsRelevante(linea)) {
			return;
		}

		procesarContenido(linea);

		if (activado && consola != null) {
			consola.agregarErrorALectador(num, this);
		}
	}

	private boolean lineaEsRelevante(String linea) {
		return linea.contains(OUT_OF_MEMORY) || linea.contains(GC_OVERHEAD) || linea.contains(DIRECT_BUFFER)
				|| linea.contains(NATIVE_THREAD) || linea.contains(OUT_OF_MEMORY_ERROR)
				|| linea.contains(INSUFFICIENT_MEMORY) || linea.contains(PROBLEM_WITH_RAM)
				|| linea.contains(COULD_NOT_RESERVE) || linea.contains(SIZE_EXCEEDS_MAX) || linea.contains(INVALID_HEAP)
				|| linea.contains(JVM_INSUFFICIENT_MEMORY) || linea.contains(PERMGEN_ERROR)
				|| linea.contains(PERMGEN_SPACE) || linea.contains(PERMGEN_EXIT_CODE);
	}

	private void procesarContenido(String contenidoConsola) {
		if (contenidoConsola == null || contenidoConsola.isEmpty() || activado) {
			return;
		}

		// Muchos de las ideas son de TLauncher signatures.json o HMCL
		// CrashAnalyzer.java

		// Verificar si es un problema de memoria insuficiente (el juego necesita MÁS
		// memoria)
		if (esProblemaMemoriaInsuficiente(contenidoConsola)) {
			esProblemaDeMemoriaInsuficiente = true;
			mensajes.append(MonitorDePID.idioma.noTieneMemoria()).append(VerificacionesLegacy.nl_html)
					.append(MonitorDePID.idioma.recomendacionMemoria());
		}

		// Verificar si es un problema de memoria excesiva (se asignó DEMASIADA memoria)
		if (esProblemaMemoriaExcesiva(contenidoConsola)) {
			esProblemaDeMemoriaExcesiva = true;
			mensajes.append(MonitorDePID.idioma.memoriaExcesiva()).append(VerificacionesLegacy.nl_html)
					.append(MonitorDePID.idioma.recomendacionMemoriaExcesiva());
		}

		// Verificación específica de PermGen
		if (contenidoConsola.contains(PERMGEN_EXIT_CODE) || contenidoConsola.contains(PERMGEN_ERROR)
				|| contenidoConsola.contains(PERMGEN_SPACE)) {

			esProblemaPermGen = true;
			mensajes.append(VerificacionesLegacy.nl_html).append(MonitorDePID.idioma.permGenError());
			mensajes.append(VerificacionesLegacy.nl_html).append(MonitorDePID.idioma.noTieneMemoria());
		}

		// Si se detectó algún problema de memoria, activamos el verificador
		if (esProblemaDeMemoriaInsuficiente || esProblemaDeMemoriaExcesiva || esProblemaPermGen) {
			verificarJVMMemoria();
			activado = true;
		}
	}

	// Falta de memoria asignada al heap u otros límites típicos de OOME
	protected boolean esProblemaMemoriaInsuficiente(String contenidoConsola) {
		if (contenidoConsola == null) {
			return false;
		}

		if (!contenidoConsola.contains(OUT_OF_MEMORY)) {
			return false;
		}

		if (contenidoConsola.contains(COULD_NOT_RESERVE) || contenidoConsola.contains(SIZE_EXCEEDS_MAX)
				|| contenidoConsola.contains(INVALID_HEAP) || contenidoConsola.contains(JVM_INSUFFICIENT_MEMORY)) {

			return false;
		}

		return contenidoConsola.contains("Java heap space") || contenidoConsola.contains(GC_OVERHEAD)
				|| contenidoConsola.contains("Requested array size exceeds VM limit")
				|| contenidoConsola.contains(DIRECT_BUFFER) || contenidoConsola.contains(NATIVE_THREAD)
				|| contenidoConsola.contains(OUT_OF_MEMORY_ERROR)
				|| contenidoConsola.contains("Caused by: java.lang.OutOfMemoryError")
				|| contenidoConsola.contains("Too small maximum heap") || contenidoConsola.contains(INSUFFICIENT_MEMORY)
				|| contenidoConsola.contains(PROBLEM_WITH_RAM);
	}

	/**
	 * Determina si el problema es que se asignó demasiada memoria al juego (dejando
	 * insuficiente para el sistema operativo u otros procesos)
	 */
	protected boolean esProblemaMemoriaExcesiva(String contenidoConsola) {
		if (contenidoConsola == null) {
			return false;
		}

		return contenidoConsola.contains(COULD_NOT_RESERVE) || contenidoConsola.contains(SIZE_EXCEEDS_MAX)
				|| contenidoConsola.contains(INVALID_HEAP) || contenidoConsola.contains(JVM_INSUFFICIENT_MEMORY);
	}

	private void verificarJVMMemoria() {
		String modeloJVM = System.getProperty("sun.arch.data.model");
		String arquitecturaOS = System.getProperty("os.arch");
		boolean esJVM32Bits = false;

		if (modeloJVM != null && modeloJVM.equals("32")) {
			esJVM32Bits = true;
		} else if (arquitecturaOS != null) {
			esJVM32Bits = arquitecturaOS.matches("(i?[3-6]86|x86|ppc32|ppc|powerpc|armv\\d+)")
					|| (arquitecturaOS.endsWith("86")
							&& System.getProperty("os.name").toLowerCase().contains("windows"));
		}

		if (esJVM32Bits) {
			mensajes.append(VerificacionesLegacy.nl_html).append(MonitorDePID.idioma.error32BitMemoria());
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new NoTieneMemoria();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f; // Prioridad crítica para errores de memoria
	}

	@Override
	public String mensaje() {
		return mensajes.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_no_tiene_memoria();
	}

	@Override
	public QuickFix solucion() {
		CDStringBuilder solucion = new CDStringBuilder();

		// Soluciones específicas para memoria insuficiente
		if (esProblemaDeMemoriaInsuficiente) {
			solucion.append(MonitorDePID.idioma.aumentarMemoriaHeap()).append(VerificacionesLegacy.nl_html);
		}

		// Soluciones específicas para memoria excesiva
		if (esProblemaDeMemoriaExcesiva) {
			solucion.append(MonitorDePID.idioma.disminuirMemoriaHeap()).append(VerificacionesLegacy.nl_html);
		}

		// Soluciones para PermGen
		if (esProblemaPermGen) {
			solucion.append(MonitorDePID.idioma.aumentarMemoriaPermgen()).append(VerificacionesLegacy.nl_html);
		}

		// Soluciones comunes
		solucion.append(MonitorDePID.idioma.utilizarJVM64Bits()).append(VerificacionesLegacy.nl_html)
				.append(MonitorDePID.idioma.optimizarCodigo()).append(VerificacionesLegacy.nl_html)
				.append(MonitorDePID.idioma.utilizarRecolectorBasuraEficiente());

		return new QuickFix.Builder(nombre()).agregarEtiqueta(solucion.toString()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "no_tiene_memoria";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}