package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores cuando un mod tiene una configuración inválida de access
 * transformer. Detecta específicamente el error "Invalid access transformer
 * line in [nombre.jar]".
 */
public class ErrorAccessTransformerInvalido implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreJar = "";
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Analiza cada línea del registro buscando el patrón específico de error de
		// access transformer
		String[] lineas = contenidoConsola.split(Verificaciones.nl);
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			// Detecta el error específico de access transformer inválido
			if (linea.contains("Invalid access transformer line in")) {
				// Extrae el nombre del JAR problemático usando expresión regular
				Pattern pattern = Pattern.compile("Invalid access transformer line in ([^:]+):");
				Matcher matcher = pattern.matcher(linea);
				if (matcher.find()) {
					nombreJar = matcher.group(1);
					mensaje = MonitorDePID.idioma.errorAccessTransformerInvalido(nombreJar) + Verificaciones.nl_html;
					enlaceHtml = consola.agregarErrorALectador(i, this);
					activado = true;
					break; // Detiene al encontrar el primer error
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorAccessTransformerInvalido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 910.0f; // Prioridad muy alta - error crítico que impide cargar mods
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_access_transformer_invalido();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_access_transformer_invalido(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_access_transformer_invalido(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_access_transformer_invalido()).construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "access_transformer_invalido";
	}
	
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
	
	
}