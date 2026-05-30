package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de módulos faltantes relacionados con Groovy Modloader (GML),
 * específicamente cuando Jackson core no se encuentra pero es requerido por
 * Jackson module paramnames, común en mods como Valkyrien Skies.
 */
public class ErrorGroovyModloaderModuloFaltante implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoGML = false;

	/**
	 * Método de compatibilidad — busca si Groovy Modloader está presente en el
	 * contenido completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si Groovy Modloader o mods relacionados están presentes en el
		// contenido del registro
		if (consola.contenido_verificar != null) {
			String contenido = consola.contenido_verificar.toLowerCase();
			encontradoGML = contenido.contains("groovy") || contenido.contains("gml");
		}
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde un módulo Jackson no se
	 * encuentra pero es requerido por otro módulo Jackson, común en entornos con
	 * Groovy Modloader.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de módulo faltante de Jackson
		if (linea.contains("java.lang.module.FindException")
				&& linea.contains("Module com.fasterxml.jackson.core not found")
				&& linea.contains("required by com.fasterxml.jackson") && encontradoGML) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de módulos de GML
			mensaje = MonitorDePID.idioma.errorGroovyModloaderModuloFaltante() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorGroovyModloaderModuloFaltante();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f; // Alta prioridad: rompe la carga del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorGroovyModloaderModuloFaltante();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorGroovyModloaderModuloFaltante()).construir();
	}

	@Override
	public String id() {
		return "error_gml_modulo_faltante";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de módulos
	 * faltantes de Groovy Modloader.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("java.lang.module.FindException") && t.contains("Module com.fasterxml.jackson.core not found")
				&& t.contains("required by com.fasterxml.jackson.module.paramnames")
				&& (t.toLowerCase().contains("groovy modloader") || t.toLowerCase().contains("valkyrien")
						|| t.toLowerCase().contains("valkerian") || t.toLowerCase().contains("gml"));
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}