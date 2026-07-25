package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.IdentityHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorJvmDllC2Sodium implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	/*
	 * La misma instancia de Verificaciones puede recibir líneas procedentes de más
	 * de una Consola. El estado se conserva por identidad de Consola para evitar
	 * combinar pruebas procedentes de archivos diferentes.
	 */
	private final Map<Consola, EstadoDeteccion> estados = new IdentityHashMap<Consola, EstadoDeteccion>();

	/*
	 * La línea real del problematic frame normalmente aparece inmediatamente
	 * después del encabezado, aunque se toleran comentarios o líneas vacías.
	 */
	private static final int MAX_LINEAS_DESPUES_PROBLEMATIC_FRAME = 4;

	/*
	 * El método que se está compilando puede aparecer varias líneas después de
	 * "Current CompileTask:".
	 */
	private static final int MAX_LINEAS_DESPUES_CURRENT_COMPILE_TASK = 12;

	private static final String FATAL_ERROR = "A fatal error has been detected by the Java Runtime Environment";

	private static final String EXCEPTION_ACCESS_VIOLATION = "EXCEPTION_ACCESS_VIOLATION";

	private static final String SIGBUS = "SIGBUS";
	private static final String SIGSEGV = "SIGSEGV";

	private static final String PROBLEMATIC_FRAME = "Problematic frame:";

	private static final String JVM_DLL = "[jvm.dll";
	private static final String LIBJVM_DYLIB = "[libjvm.dylib";
	private static final String LIBJVM_SO = "[libjvm.so";

	private static final String C2_COMPILER_THREAD = "C2 CompilerThread";

	private static final String CURRENT_COMPILE_TASK = "Current CompileTask:";

	private static final String C2_COMPILE_METHOD = "C2Compiler::compile_method";

	private static final String SODIUM_NEW = "net.caffeinemc.mods.sodium";

	private static final String SODIUM_OLD = "me.jellysquid.mods.sodium";

	private static final String EMBEDDIUM_PACKAGE = "org.embeddedt.embeddium";

	private static final String EMBEDDIUM = "embeddium";
	private static final String RUBIDIUM = "rubidium";

	private static final String CHUNK_BUILDER = "ChunkBuilderMeshingTask::execute";

	private static final String CLONED_CHUNK_CACHE = "ClonedChunkSectionCache::acquire";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FATAL_ERROR, EXCEPTION_ACCESS_VIOLATION, SIGBUS, SIGSEGV, PROBLEMATIC_FRAME, JVM_DLL,
				LIBJVM_DYLIB, LIBJVM_SO, C2_COMPILER_THREAD, CURRENT_COMPILE_TASK, C2_COMPILE_METHOD, SODIUM_NEW,
				SODIUM_OLD, EMBEDDIUM_PACKAGE, EMBEDDIUM, RUBIDIUM, CHUNK_BUILDER, CLONED_CHUNK_CACHE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null) {

			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroLinea) {

		if (activado || consola == null || linea == null) {

			return;
		}

		EstadoDeteccion estado = obtenerEstado(consola);

		/*
		 * Un nuevo encabezado fatal representa el comienzo de un nuevo informe. Se
		 * elimina cualquier evidencia parcial anterior de esta Consola.
		 */
		if (linea.contains(FATAL_ERROR)) {
			estado = new EstadoDeteccion();
			estado.jvmFatal = true;
			estados.put(consola, estado);

		} else if (lineaContieneExcepcionFatal(linea)) {
			/*
			 * Permite analizar informes truncados que contengan la señal o la excepción,
			 * aunque falte el encabezado inicial.
			 */
			estado.jvmFatal = true;
		}

		/*
		 * Este texto es solamente el encabezado de la sección.
		 *
		 * No se activa aquí. Se espera la línea que contiene el frame real.
		 */
		if (linea.contains(PROBLEMATIC_FRAME)) {
			estado.lineaEncabezadoProblematicFrame = numeroLinea;

			estado.problematicFrameEsJvm = false;
			estado.lineaProblematicFrameJvm = -1;
		}

		/*
		 * Una aparición de jvm.dll o libjvm solamente cuenta cuando está justo después
		 * del encabezado "Problematic frame:".
		 *
		 * Una referencia a libjvm encontrada en otra sección del hs_err no puede
		 * activar esta verificación.
		 */
		if (lineaContieneJVM(linea) && estaDentroDeVentana(estado.lineaEncabezadoProblematicFrame, numeroLinea,
				MAX_LINEAS_DESPUES_PROBLEMATIC_FRAME, false)) {

			estado.problematicFrameEsJvm = true;
			estado.lineaProblematicFrameJvm = numeroLinea;
		}

		/*
		 * Registra que el fallo ocurrió dentro del compilador C2.
		 */
		if (lineaContieneC2(linea)) {
			estado.compiladorC2 = true;
		}

		/*
		 * Marca el comienzo de la sección que identifica el método que C2 estaba
		 * compilando.
		 */
		if (linea.contains(CURRENT_COMPILE_TASK)) {
			estado.lineaCurrentCompileTask = numeroLinea;
		}

		/*
		 * Sodium solamente cuenta cuando aparece en la sección "Current CompileTask:",
		 * no por aparecer casualmente en cualquier otra parte del registro.
		 */
		if (lineaContieneSodium(linea) && estaDentroDeVentana(estado.lineaCurrentCompileTask, numeroLinea,
				MAX_LINEAS_DESPUES_CURRENT_COMPILE_TASK, true)) {

			estado.compileTaskEsSodium = true;
		}

		activarSiCorresponde(consola, estado);
	}

	private EstadoDeteccion obtenerEstado(Consola consola) {

		EstadoDeteccion estado = estados.get(consola);

		if (estado == null) {
			estado = new EstadoDeteccion();
			estados.put(consola, estado);
		}

		return estado;
	}

	private void activarSiCorresponde(Consola consola, EstadoDeteccion estado) {

		if (activado || estado == null) {
			return;
		}

		if (!estado.jvmFatal || !estado.problematicFrameEsJvm || !estado.compiladorC2 || !estado.compileTaskEsSodium) {

			return;
		}

		/*
		 * El enlace apunta a la línea real de jvm.dll/libjvm, no al encabezado genérico
		 * "Problematic frame:".
		 */
		this.enlace = consola.agregarErrorALectador(estado.lineaProblematicFrameJvm, this);

		this.activado = true;

		/*
		 * Ya se obtuvo una coincidencia completa. No se necesita conservar el estado
		 * parcial de las demás Consolas.
		 */
		estados.clear();
	}

	private boolean lineaContieneExcepcionFatal(String linea) {

		return linea.contains(EXCEPTION_ACCESS_VIOLATION) || linea.contains(SIGBUS) || linea.contains(SIGSEGV);
	}

	private boolean lineaContieneJVM(String linea) {

		return linea.contains(JVM_DLL) || linea.contains(LIBJVM_DYLIB) || linea.contains(LIBJVM_SO);
	}

	private boolean lineaContieneC2(String linea) {

		return linea.contains(C2_COMPILER_THREAD) || linea.contains(C2_COMPILE_METHOD);
	}

	private boolean lineaContieneSodium(String linea) {

		return linea.contains(SODIUM_NEW) || linea.contains(SODIUM_OLD) || linea.contains(EMBEDDIUM_PACKAGE)
				|| linea.contains(EMBEDDIUM) || linea.contains(RUBIDIUM) || linea.contains(CHUNK_BUILDER)
				|| linea.contains(CLONED_CHUNK_CACHE);
	}

	/**
	 * Comprueba que una línea esté cerca de un encabezado anterior.
	 *
	 * @param lineaEncabezado    número de línea del encabezado, o -1 si no apareció
	 * @param lineaActual        número de la línea actual
	 * @param distanciaMaxima    cantidad máxima de líneas permitida
	 * @param permitirMismaLinea true cuando encabezado y dato pueden compartir
	 *                           línea
	 */
	private boolean estaDentroDeVentana(int lineaEncabezado, int lineaActual, int distanciaMaxima,
			boolean permitirMismaLinea) {

		if (lineaEncabezado < 0 || lineaActual < lineaEncabezado) {

			return false;
		}

		int distancia = lineaActual - lineaEncabezado;

		if (!permitirMismaLinea && distancia == 0) {
			return false;
		}

		return distancia <= distanciaMaxima;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorJvmDllC2Sodium();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorJvmDllC2Sodium() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorJvmDllC2Sodium();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "error_jvm_dll_c2_sodium";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	/**
	 * Estado parcial de detección perteneciente a un solo archivo o Consola.
	 */
	private static final class EstadoDeteccion {

		private boolean jvmFatal;

		private int lineaEncabezadoProblematicFrame = -1;

		private boolean problematicFrameEsJvm;

		private int lineaProblematicFrameJvm = -1;

		private boolean compiladorC2;

		private int lineaCurrentCompileTask = -1;

		private boolean compileTaskEsSodium;
	}
}