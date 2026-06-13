package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorJvmDllCurseForgeG1 implements Verificaciones {

	// Indica si el log contiene un fallo fatal nativo dentro de Java
	private boolean posibleJvmDll = false;

	// Indica si el fallo ocurrió en un hilo del recolector de basura G1
	private boolean posibleG1 = false;

	// Indica si el lanzamiento parece venir de CurseForge o de un perfil
	// administrado por CurseForge
	private boolean posibleCurseForge = false;

	// Indica si el log muestra indicios de Overwolf o DLLs relacionadas
	private boolean posibleOverwolf = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde se detectó el error principal
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Detección global ligera: fallo nativo de la JVM.
		if (contenido.contains("EXCEPTION_ACCESS_VIOLATION") && contenido.contains("Problematic frame:")
				&& contenido.contains("jvm.dll")) {
			posibleJvmDll = true;
		} else {
			return;
		}

		// Esta variante específica ocurre durante trabajo del recolector G1.
		if (contenido.contains("GCTaskThread") && (contenido.contains("g1 gc") || contenido.contains("UseG1GC"))) {
			posibleG1 = true;
		} else {
			return;
		}

		// CurseForge puede aparecer como launcher propio o como lanzamiento
		// administrado
		// por CurseForge usando el launcher vanilla.
		if (contenido.contains("DCFInstanceId") || contenido.contains("minecraft.launcher.brand=CurseForge")
				|| contenido.contains("curseforge\\minecraft") || contenido.contains("curseforge/minecraft")
				|| contenido.contains("CurseForge")) {
			posibleCurseForge = true;
		} else {
			return;
		}

		// No usamos esto como requisito absoluto, porque no todos los hs_err muestran
		// la lista completa de DLLs cargadas. Solo sirve para reforzar el mensaje.
		if (contenido.contains("Overwolf") || contenido.contains("OWClient.dll") || contenido.contains("OWUtils.dll")
				|| contenido.contains("overwolf")) {
			posibleOverwolf = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleOverwolf)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no está el patrón principal.
		if (!posibleJvmDll || !posibleG1 || !posibleCurseForge) {
			return;
		}

		// Línea principal del fallo fatal.
		if (!activado && linea.contains("Problematic frame:")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Fallback para logs con formato raro.
		if (!activado && linea.contains("jvm.dll")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Fallback adicional: marcar la línea del hilo GC si aparece antes.
		if (!activado && linea.contains("GCTaskThread")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorJvmDllCurseForgeG1();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1550;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorJvmDllCurseForgeG1(posibleOverwolf) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorJvmDllCurseForgeG1();
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
		return "error_jvm_dll_curseforge_g1";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}