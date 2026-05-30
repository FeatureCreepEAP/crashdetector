package com.asbestosstar.crashdetector.analizador.general;

import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class VersionInvalidaSemver implements Verificaciones {

	private boolean posibleErrorVersion = false;
	private boolean activado = false;
	private String enlace = "";
	private String ubicacionMod = "";
	private String versionInvalida = "";

	@Override
	public void verificar(Consola consola) {

		// Detección global ligera
		if (consola.contenido_verificar.contains("IllegalArgumentException")
				&& consola.contenido_verificar.contains("Empty pre-release")) {

			posibleErrorVersion = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		if (!posibleErrorVersion)
			return;

		if (linea.contains("IllegalArgumentException") && linea.contains("Empty pre-release")) {

			// Extraer la version invalida (antes de ": Empty pre-release")
			int idx = linea.indexOf(":");
			if (idx != -1) {

				String resto = linea.substring(idx + 1).trim();

				int idxEmpty = resto.indexOf(": Empty pre-release");
				if (idxEmpty != -1) {
					versionInvalida = resto.substring(0, idxEmpty).trim();
				}
			}

			// Buscar el mod que tenga esa version
			if (!versionInvalida.isEmpty()) {

				List<ArchivoDeMod> mods = Buscador.obtenerTodosLosModsYSubmodsRecursivos();

				for (ArchivoDeMod mod : mods) {
					String v = mod.version();
					if (v != null && v.equals(versionInvalida)) {
						ubicacionMod = mod.ubicacion();
						break;
					}
				}
			}

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new VersionInvalidaSemver();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1600;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeVersionInvalida(versionInvalida, ubicacionMod) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreVersionInvalida();
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
		return "version_invalida_semver";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}