package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class JPMSIllegalAccess implements Verificaciones {

	private boolean posibleJPMS = false;
	private boolean activado = false;
	private String enlace = "";

	private String claseOrigen = "";
	private String moduloOrigen = "";
	private String claseDestino = "";
	private String moduloDestino = "";

	@Override
	public void verificar(Consola consola) {

		// Detección global ligera
		if (consola.contenido_verificar.contains("IllegalAccessException")
				&& consola.contenido_verificar.contains("in module")
				&& consola.contenido_verificar.contains("cannot access")) {

			posibleJPMS = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {

		if (!posibleJPMS)
			return;

		if (linea.contains("IllegalAccessException") && linea.contains("in module")
				&& linea.contains("cannot access")) {

			// FILTRO FALSO POSITIVO NETTY + Unsafe
			if (linea.contains("io.netty") && linea.contains("jdk.internal.misc.Unsafe")
					&& linea.contains("module java.base")) {

				// Es el falso positivo clásico de Netty en Java 17+ comun con ModernFix y
				// PhysicsMod
				return;
			}

			// Ejemplo:
			// class A (in module X) cannot access a member of class B (in module Y)

			try {
				int idxClassA = linea.indexOf("class ");
				int idxModuleA = linea.indexOf("(in module ", idxClassA);

				if (idxClassA != -1 && idxModuleA != -1) {
					claseOrigen = linea.substring(idxClassA + 6, idxModuleA).trim();

					int finModuleA = linea.indexOf(")", idxModuleA);
					moduloOrigen = linea.substring(idxModuleA + 11, finModuleA).trim();

					int idxClassB = linea.indexOf("class ", finModuleA);
					int idxModuleB = linea.indexOf("(in module ", idxClassB);

					if (idxClassB != -1 && idxModuleB != -1) {
						claseDestino = linea.substring(idxClassB + 6, idxModuleB).trim();

						int finModuleB = linea.indexOf(")", idxModuleB);
						moduloDestino = linea.substring(idxModuleB + 11, finModuleB).trim();
					}
				}
			} catch (Throwable ignored) {
			}

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new JPMSIllegalAccess();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1700;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeJPMSIllegalAccess(claseOrigen, moduloOrigen, claseDestino, moduloDestino)
				+ this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreJPMSIllegalAccess();
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
		return "jpms_illegal_access";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}