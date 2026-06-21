package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorJvmDllC2Sodium implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String FATAL_ERROR = "A fatal error has been detected by the Java Runtime Environment";
	private static final String EXCEPTION_ACCESS_VIOLATION = "EXCEPTION_ACCESS_VIOLATION";
	private static final String SIGBUS = "SIGBUS";
	private static final String SIGSEGV = "SIGSEGV";
	private static final String PROBLEMATIC_FRAME = "Problematic frame:";
	private static final String JVM_DLL = "jvm.dll";
	private static final String LIBJVM_DYLIB = "libjvm.dylib";
	private static final String LIBJVM_SO = "libjvm.so";

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
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		if (linea.contains(PROBLEMATIC_FRAME)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		if (lineaContieneJVM(linea)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		if (linea.contains(CURRENT_COMPILE_TASK) && lineaContieneSodium(linea)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneJvmFatal(String linea) {
		return (linea.contains(FATAL_ERROR) || linea.contains(EXCEPTION_ACCESS_VIOLATION) || linea.contains(SIGBUS)
				|| linea.contains(SIGSEGV) || linea.contains(PROBLEMATIC_FRAME)) || lineaContieneJVM(linea);
	}

	private boolean lineaContieneJVM(String linea) {
		return linea.contains(JVM_DLL) || linea.contains(LIBJVM_DYLIB) || linea.contains(LIBJVM_SO);
	}

	private boolean lineaContieneC2(String linea) {
		return linea.contains(C2_COMPILER_THREAD) || linea.contains(CURRENT_COMPILE_TASK)
				|| linea.contains(C2_COMPILE_METHOD);
	}

	private boolean lineaContieneSodium(String linea) {
		return linea.contains(SODIUM_NEW) || linea.contains(SODIUM_OLD) || linea.contains(EMBEDDIUM_PACKAGE)
				|| linea.contains(EMBEDDIUM) || linea.contains(RUBIDIUM) || linea.contains(CHUNK_BUILDER)
				|| linea.contains(CLONED_CHUNK_CACHE);
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
}