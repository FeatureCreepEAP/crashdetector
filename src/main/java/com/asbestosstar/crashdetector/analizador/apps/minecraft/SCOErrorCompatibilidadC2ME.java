package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Analiza errores de compatibilidad entre C2ME y mods de conexión. Detecta
 * específicamente el error de IllegalAccessException relacionado con el sistema
 * de módulos de Java. Recomienda usar C3ME en lugar de C2ME cuando se utilizan
 * mods de conexión.
 */
public class SCOErrorCompatibilidadC2ME implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private boolean c2mePresente = false;
	private boolean connectorPresente = false;
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Detecta la presencia de C2ME en los logs
		if (contenidoConsola.contains("com.ishland.c2me")) {
			c2mePresente = true;
		}

		// Detecta la presencia de mods de conexión
		if (contenidoConsola.contains("SINYTRA CONNECTOR IS PRESENT!")
				|| contenidoConsola.contains("specialcompatibilityoperation")) {
			connectorPresente = true;
		}

		// Analiza cada línea del registro para detectar los errores específicos
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			// Detecta el error específico de acceso ilegal entre módulos de Java
			if (linea.contains("java.lang.IllegalAccessException")
					&& linea.contains("cannot access class jdk.internal.misc.Unsafe")
					&& linea.contains("because module java.base does not export") && c2mePresente
					&& connectorPresente) {
				mensaje = MonitorDePID.idioma.errorCompatibilidadC2ME() + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(i, this);
				activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new SCOErrorCompatibilidadC2ME();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 945.0f; // Alta prioridad - error que impide la carga correcta del juego
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_compatibilidad_c2me();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_compatibilidad_c2me())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_compatibilidad_c2me())
				.agregarEtiqueta(MonitorDePID.idioma.paso3_compatibilidad_c2me()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "sco_error_compatibilidad_c2me";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}