package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores cuando un mod tiene dependencias con campos obligatorios
 * faltantes. Detecta específicamente el error "Missing required field mandatory
 * in dependency" y muestra el nombre del JAR problemático.
 */
public class ErrorDependenciaModFaltante implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreJar = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Analiza cada línea del registro buscando el patrón específico de error de
		// dependencia
		for (String linea : contenidoConsola.split(Verificaciones.nl)) {
			// Detecta el error específico de campo obligatorio faltante en dependencias
			if (linea.contains("Missing required field mandatory in dependency")) {
				// Extrae el nombre del JAR problemático usando expresión regular
				Pattern pattern = Pattern.compile("Missing required field mandatory in dependency \\(([^)]+)\\)");
				Matcher matcher = pattern.matcher(linea);
				if (matcher.find()) {
					nombreJar = matcher.group(1);
					mensaje = MonitorDePID.idioma.errorDependenciaModFaltante(nombreJar) + Verificaciones.nl_html;
					activado = true;
					break; // Detiene al encontrar el primer error (es crítico y ocurre una vez)
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDependenciaModFaltante();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 920.0f; // Prioridad muy alta - error crítico que impide cargar mods
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_dependencia_mod_faltante();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_dependencia_mod_faltante(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_dependencia_mod_faltante(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_dependencia_mod_faltante()).construir();
	}
}