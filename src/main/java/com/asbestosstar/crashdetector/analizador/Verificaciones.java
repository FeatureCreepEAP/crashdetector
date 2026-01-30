package com.asbestosstar.crashdetector.analizador;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public interface Verificaciones {

	public static String nl = System.lineSeparator();
	public static String nl_html = "<br>";

	/**
	 * Aqui es donde verificar. Se ejecuta una vez por consola.
	 * 
	 * @param consola
	 */
	public void verificar(Consola consola);

	/**
	 * Aqui es donde verificar contenido en una linea specific. Se ejecuta para
	 * todos lineas.
	 * 
	 * @param consola
	 */
	public default void verificar(Consola consola, String linea, int numero_de_linea) {
		// Por defecto hace nada, para muchos puede usar verificar(Consola)
	}

	/**
	 * Una nueva instancia
	 * 
	 * @return
	 */
	public Verificaciones nueva();

	public boolean activado();

	/**
	 * Si esta activado abrimos crashdectector se cerrada normalmente
	 * 
	 * @return
	 */
	public default boolean anularNormal() {
		return false;
	}

	public float prioridad();

	/**
	 * Solo activar cuando activado()
	 * 
	 * @return
	 */
	public String mensaje();

	public String nombre();

	public QuickFix solucion();

	public boolean ocupaTrazo(TraceInfo trazo);

	public static void abrirEnNavegador(String url) {
		try {
			if (java.awt.Desktop.isDesktopSupported()) {
				java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
			}
		} catch (Exception e) {
			// Manejo de excepción silencioso
		}
	}

	public default Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ERROR;
	}

	/**
	 * Una id sin simbolas o accentos o espacios
	 * 
	 * @return
	 */
	public String id();

	/**
	 * La documentacion de la
	 * 
	 * @return
	 */
	public Documento docs();

	/**
	 * Enlace a el codigo de esta archivo
	 * 
	 * @return
	 */
	public String enlaceACodigo();

	/**
	 * Si esta verificacion para usarios Corperatas, VTuber Fan Proyectos, y
	 * ModPacks.La mayoría no lo son, pero algunos ejemplos incluyen problemas
	 * relacionados con los controladores, errores de la máquina virtual de Java
	 * (JVM), instalaciones de la JVM y problemas con instalaciones de aplicaciones
	 * o modificaciones corruptas,mudos corruptas, o problemas específicos de
	 * determinados ordenadores.
	 * 
	 * @return
	 */
	public default boolean recomendadoParaCorperata() {
		return false;
	}

	// @Override
	public default String comoString() {// no puedemos usar toString en interfaz
		StringBuilder constructor = new StringBuilder();
		String tituloColor = Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas();
		constructor.append("<span style='color: #").append(tituloColor).append("; font-weight: bold;'>")
				.append(nombre()).append("</span>");
		constructor.append("<br>").append(mensaje());

		String colorenlace = Config.obtenerInstancia().obtenerColorEnlace();

		if (this.solucion() != null && this.solucion() != QuickFix.NINGUN) {
			String enlaceHtml = "<a href=\"" + this.solucion().obtenerEnlace() + "\" style=\"color:" + colorenlace
					+ ";\">QuickFix</a>";
			if (!constructor.toString().endsWith(nl_html) && !constructor.toString().endsWith(nl)) {
				constructor.append(nl_html);
			}
			constructor.append(enlaceHtml);
		}

		constructor.append("<hr style='border: 0; border-top: 1px solid #").append(tituloColor)
				.append("; margin: 8px 0;' />");

		return constructor.toString();

	}

}
