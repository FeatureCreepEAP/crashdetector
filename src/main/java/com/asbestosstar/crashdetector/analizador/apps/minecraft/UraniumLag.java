package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class UraniumLag implements Verificaciones {

	boolean activado = false;

	@Override
	public String[] patronesRapidos() {
		return new String[0];
	}

	public void verificar() {
		Path archivoMods = MonitorDePID.ultimo_mods;

		if (archivoMods != null && Files.exists(archivoMods)) {
			try {
				// 1. Filtrado rápido: leemos todo el archivo a memoria y buscamos el texto
				String contenido = new String(Files.readAllBytes(archivoMods));

				if (contenido.contains("uranium")) {
					// 2. Solo si existe el texto, ejecutamos la búsqueda pesada en las clases
					activado = Buscador.existeClaseEnAlgunMod("net.yosa.uranium.Uranium");
				} else {
					activado = false;
				}
			} catch (IOException e) {
				CrashDetectorLogger.logException(e);
				activado = false;
			}
		} else {
			activado = false;
		}
	}

	public void verificarCoincidencia(EventoDeCoincidencia evento) {
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		verificar();
	}

	@Override
	public Verificaciones nueva() {
		return new UraniumLag();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.uraniumLag();
	}

	@Override
	public String nombre() {
		return "UraniumLag";
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "uraniumlag";
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}