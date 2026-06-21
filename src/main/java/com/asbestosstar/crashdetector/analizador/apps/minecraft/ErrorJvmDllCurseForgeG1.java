package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorJvmDllCurseForgeG1 implements Verificaciones {

	private static final Set<String> REPORTADOS_GLOBAL = Collections.synchronizedSet(new HashSet<>());

	private boolean posibleJvmDll = false;
	private boolean posibleG1 = false;
	private boolean posibleCurseForge = false;
	private boolean posibleOverwolf = false;
	private boolean activado = false;
	private String enlace = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { "EXCEPTION_ACCESS_VIOLATION", "Problematic frame:", "jvm.dll", "GCTaskThread", "UseG1GC",
				"g1 gc", "DCFInstanceId", "minecraft.launcher.brand=CurseForge", "curseforge\\minecraft",
				"curseforge/minecraft", "CurseForge", "Overwolf", "OWClient.dll", "OWUtils.dll" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado)
			return;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (consola == null || linea == null || activado)
			return;

		if (linea.indexOf("EXCEPTION_ACCESS_VIOLATION") >= 0)
			posibleJvmDll = true;

		if (linea.indexOf("Problematic frame:") >= 0 || linea.indexOf("jvm.dll") >= 0)
			posibleJvmDll = true;

		if (linea.indexOf("GCTaskThread") >= 0)
			posibleG1 = true;

		if (linea.indexOf("g1 gc") >= 0 || linea.indexOf("UseG1GC") >= 0)
			posibleG1 = true;

		if (linea.indexOf("DCFInstanceId") >= 0 || linea.indexOf("minecraft.launcher.brand=CurseForge") >= 0
				|| linea.indexOf("curseforge\\minecraft") >= 0 || linea.indexOf("curseforge/minecraft") >= 0
				|| linea.indexOf("CurseForge") >= 0) {
			posibleCurseForge = true;
		}

		if (linea.indexOf("Overwolf") >= 0 || linea.indexOf("OWClient.dll") >= 0 || linea.indexOf("OWUtils.dll") >= 0
				|| linea.indexOf("overwolf") >= 0) {
			posibleOverwolf = true;
		}

		if (linea.indexOf("Problematic frame:") >= 0 || linea.indexOf("jvm.dll") >= 0
				|| linea.indexOf("GCTaskThread") >= 0) {
			activarSiCompleto(consola, num);
		}
	}

	private void activarSiCompleto(Consola consola, int num) {
		if (activado)
			return;

		if (!posibleJvmDll || !posibleG1 || !posibleCurseForge)
			return;

		String clave = id();

		if (!REPORTADOS_GLOBAL.add(clave))
			return;

		this.enlace = consola.agregarErrorALectador(num, this);
		this.activado = true;
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		if (consola == null || activado)
			return;

		activarSiCompleto(consola, 0);
	}

	public static void reiniciarGlobal() {
		REPORTADOS_GLOBAL.clear();
	}

	@Override
	public VerificacionesLegacy nueva() {
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