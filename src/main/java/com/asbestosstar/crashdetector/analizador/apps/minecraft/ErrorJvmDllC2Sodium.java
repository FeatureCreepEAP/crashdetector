package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorJvmDllC2Sodium implements Verificaciones {

	// Indica si el log contiene un fallo fatal nativo del JVM
	private boolean posibleJvmDll = false;

	// Indica si el fallo ocurrió en el compilador C2 de Java
	private boolean posibleC2 = false;

	// Indica si el compilador C2 estaba compilando código de Sodium o un fork
	// similar
	private boolean posibleSodium = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde se detectó el error principal
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Detección global ligera: el fallo debe ser un acceso inválido dentro de
		// jvm.dll.
		if (contenido.contains("EXCEPTION_ACCESS_VIOLATION") && contenido.contains("Problematic frame:")
				&& contenido.contains("jvm.dll")) {
			posibleJvmDll = true;
		}

		// Esta variante específica ocurre en el compilador C2, no en un hilo normal de
		// Minecraft.
		if (contenido.contains("C2 CompilerThread") || contenido.contains("Current CompileTask:")) {
			posibleC2 = true;
		}

		// Sodium, Rubidium y Embeddium suelen aparecer en la tarea de compilación.
		// El caso visto con más frecuencia es ClonedChunkSectionCache::acquire.
		if (contenido.contains("me.jellysquid.mods.sodium") || contenido.contains("org.embeddedt.embeddium")
				|| contenido.contains("embeddium") || contenido.contains("rubidium")
				|| contenido.contains("ClonedChunkSectionCache::acquire")) {
			posibleSodium = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si el log no tiene los indicios globales necesarios.
		if (!posibleJvmDll || !posibleC2 || !posibleSodium) {
			return;
		}

		// Línea principal del fallo nativo.
		if (!activado && linea.contains("Problematic frame:")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Si el lector no encuentra la línea anterior por formato extraño, usar la
		// línea de jvm.dll.
		if (!activado && linea.contains("jvm.dll")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// También permitir que se marque directamente la línea de la tarea de
		// compilación.
		if (!activado && linea.contains("Current CompileTask:")
				&& (linea.contains("me.jellysquid.mods.sodium") || linea.contains("org.embeddedt.embeddium")
						|| linea.contains("embeddium") || linea.contains("rubidium")
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