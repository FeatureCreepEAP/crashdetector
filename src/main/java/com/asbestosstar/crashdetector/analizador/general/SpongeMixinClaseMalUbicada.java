package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class SpongeMixinClaseMalUbicada implements Verificaciones {

	// Indicio global
	private boolean posibleErrorMixin = false;

	// Estado de activación
	private boolean activado = false;

	// Enlace al log
	private String enlace = "";

	// Datos extraídos
	private String claseConflictiva = "";
	private String paqueteMixin = "";
	private String archivoMixin = "";

	@Override
	public void verificar(Consola consola) {

		// Detección ligera global
		if (consola.contenido_verificar.contains("IllegalClassLoadError")
				&& consola.contenido_verificar.contains("defined mixin package")
				&& consola.contenido_verificar.contains("cannot be referenced directly")) {

			posibleErrorMixin = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numeroLinea) {

		if (!posibleErrorMixin)
			return;

		if (linea.contains("IllegalClassLoadError") && linea.contains("defined mixin package")
				&& linea.contains("cannot be referenced directly")) {

			try {
				// Extraer clase conflictiva
				int idxClase = linea.indexOf(":");
				int idxEs = linea.indexOf(" is in a defined mixin package");

				if (idxClase != -1 && idxEs != -1) {
					claseConflictiva = linea.substring(idxClase + 1, idxEs).trim();
				}

				// Extraer paquete mixin
				int idxPaquete = linea.indexOf("defined mixin package");
				int idxOwned = linea.indexOf(" owned by");

				if (idxPaquete != -1 && idxOwned != -1) {
					paqueteMixin = linea.substring(idxPaquete + "defined mixin package".length(), idxOwned).trim();
				}

				// Extraer archivo mixins.json
				int idxOwnedBy = linea.indexOf("owned by");
				int idxCannot = linea.indexOf(" and cannot");

				if (idxOwnedBy != -1 && idxCannot != -1) {
					archivoMixin = linea.substring(idxOwnedBy + "owned by".length(), idxCannot).trim();
				}

			} catch (Throwable ignorado) {
			}

			this.enlace = consola.agregarErrorALectador(numeroLinea, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new SpongeMixinClaseMalUbicada();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1650;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeMixinClaseMalUbicada(claseConflictiva, paqueteMixin, archivoMixin)
				+ this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreMixinClaseMalUbicada();
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
		return "mixin_clase_mal_ubicada";
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