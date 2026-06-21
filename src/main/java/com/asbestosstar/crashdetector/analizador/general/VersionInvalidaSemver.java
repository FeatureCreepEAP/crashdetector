package com.asbestosstar.crashdetector.analizador.general;

import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class VersionInvalidaSemver implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";
	private String ubicacionMod = "";
	private String versionInvalida = "";

	private static final String TEXTO_ILLEGAL_ARGUMENT = "IllegalArgumentException";
	private static final String TEXTO_EMPTY_PRE_RELEASE = "Empty pre-release";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ILLEGAL_ARGUMENT, TEXTO_EMPTY_PRE_RELEASE };
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

		if (lineaContieneVersionInvalida(linea)) {

			// Extraer la version invalida (antes de ": Empty pre-release")
			int idx = linea.indexOf(":");
			if (idx != -1) {

				String resto = linea.substring(idx + 1).trim();

				int idxEmpty = resto.indexOf(": " + TEXTO_EMPTY_PRE_RELEASE);
				if (idxEmpty != -1) {
					versionInvalida = resto.substring(0, idxEmpty).trim();
				}
			}

			// Buscar el mod que tenga esa version
			if (!versionInvalida.isEmpty()) {
				try {
					List<ArchivoDeMod> mods = Buscador.obtenerTodosLosModsYSubmodsRecursivos();

					for (ArchivoDeMod mod : mods) {
						String v = mod.version();
						if (v != null && v.equals(versionInvalida)) {
							ubicacionMod = mod.ubicacion();
							break;
						}
					}
				} catch (Throwable ignorado) {
				}
			}

			if (consola != null) {
				this.enlace = consola.agregarErrorALectador(num, this);
			}

			this.activado = true;
		}
	}

	private boolean lineaContieneVersionInvalida(String linea) {
		return linea.contains(TEXTO_ILLEGAL_ARGUMENT) && linea.contains(TEXTO_EMPTY_PRE_RELEASE);
	}

	@Override
	public VerificacionesLegacy nueva() {
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

}