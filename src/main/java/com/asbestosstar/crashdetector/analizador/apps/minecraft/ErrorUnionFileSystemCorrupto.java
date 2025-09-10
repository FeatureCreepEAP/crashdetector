package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores específicos relacionados con UnionFileSystem y archivos de
 * mod corruptos. Detecta específicamente el error
 * "cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException". Este error es
 * común en modpacks debido a problemas con el lanzador.
 */
public class ErrorUnionFileSystemCorrupto implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreArchivo = "un archivo de mod";
	private boolean esModpack = false;

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Analiza cada línea del registro buscando el patrón específico de error de
		// UnionFileSystem
		for (String linea : contenidoConsola.split(Verificaciones.nl)) {
			// Detecta el error específico de UnionFileSystem con ZipException
			if (linea.contains("cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException")
					&& linea.contains("java.util.zip.ZipException: zip END header not found")) {

				// Intenta identificar el nombre del archivo problemático en las líneas
				// siguientes
				Pattern pattern = Pattern.compile("at.*?/(.*?\\.jar)");
				Matcher matcher = pattern.matcher(contenidoConsola);
				if (matcher.find()) {
					nombreArchivo = matcher.group(1);
				}

				mensaje = MonitorDePID.idioma.errorUnionFileSystemCorrupto(nombreArchivo) + Verificaciones.nl_html;
				activado = true;
				break; // Detiene al encontrar el primer error
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorUnionFileSystemCorrupto();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 965.0f; // Máxima prioridad - error crítico específico de lanzadores de modpacks
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_union_filesystem_corrupto();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_union_filesystem_corrupto(nombreArchivo))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_union_filesystem_corrupto())
				.agregarEtiqueta(MonitorDePID.idioma.paso3_union_filesystem_corrupto()).construir();
	}
}