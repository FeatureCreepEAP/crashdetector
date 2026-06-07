package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorJvmDllC2Sodium implements Verificaciones {

	private boolean posibleJvm = false;
	private boolean posibleC2 = false;
	private boolean posibleSodium = false;
	private boolean activado = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		if ((contenido.contains("A fatal error has been detected by the Java Runtime Environment")
				|| contenido.contains("EXCEPTION_ACCESS_VIOLATION") || contenido.contains("SIGBUS")
				|| contenido.contains("SIGSEGV")) && contenido.contains("Problematic frame:")
				&& (contenido.contains("jvm.dll") || contenido.contains("libjvm.dylib")
						|| contenido.contains("libjvm.so"))) {
			posibleJvm = true;
		}

		if (contenido.contains("C2 CompilerThread") || contenido.contains("Current CompileTask:")
				|| contenido.contains("C2Compiler::compile_method")) {
			posibleC2 = true;
		}

		if (contenido.contains("net.caffeinemc.mods.sodium") || contenido.contains("me.jellysquid.mods.sodium")
				|| contenido.contains("org.embeddedt.embeddium") || contenido.contains("embeddium")
				|| contenido.contains("rubidium") || contenido.contains("ChunkBuilderMeshingTask::execute")
				|| contenido.contains("ClonedChunkSectionCache::acquire")) {
			posibleSodium = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleJvm || !posibleC2 || !posibleSodium) {
			return;
		}

		if (!activado && linea.contains("Problematic frame:")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		if (!activado && (linea.contains("jvm.dll") || linea.contains("libjvm.dylib") || linea.contains("libjvm.so"))) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		if (!activado && linea.contains("Current CompileTask:")
				&& (linea.contains("net.caffeinemc.mods.sodium") || linea.contains("me.jellysquid.mods.sodium")
						|| linea.contains("org.embeddedt.embeddium") || linea.contains("embeddium")
						|| linea.contains("rubidium") || linea.contains("ChunkBuilderMeshingTask::execute")
						|| linea.contains("ClonedChunkSectionCache::acquire"))) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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