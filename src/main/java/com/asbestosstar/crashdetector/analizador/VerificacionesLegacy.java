package com.asbestosstar.crashdetector.analizador;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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
	public default void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
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
	 * Método default que genera automáticamente el enlace al código fuente de la
	 * verificación que implementa esta interfaz.
	 * 
	 * @return String con la URL completa al código fuente en el repositorio
	 */
	public default String enlaceACodigo() {
		// Raíz del repositorio para enlaces
		String base = Statics.GIT;

		// Obtener el nombre completo de la clase que implementa esta interfaz
		String nombreClase = this.getClass().getName();

		// Reemplazar los puntos por barras para formar la ruta de archivo
		String rutaClase = nombreClase.replace('.', '/') + ".java";

		// Concatenar todo para formar la URL final
		return base + "src/main/java/" + rutaClase;
	}

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

		if (mensaje().isEmpty()) {
			return "";
		}

		constructor.append("<span style='color: #").append(tituloColor).append("; font-weight: bold;'>")
				.append(nombre()).append("</span>");
		constructor.append("<br>").append(mensaje());

		String colorenlace = Config.obtenerInstancia().obtenerColorEnlace();

		// Enlace a documentación
		if (this.docs() != null && this.docs() != Documento.NINGUN) {
			String enlaceDocs = this.docs().enlace();

			if (enlaceDocs != null && !enlaceDocs.isEmpty()) {
				String enlaceHtmlDocs = "<a href=\"" + enlaceDocs + "\" style=\"color:" + colorenlace + ";\">Docs</a>";

				if (!constructor.toString().endsWith(nl_html) && !constructor.toString().endsWith(nl)) {
					constructor.append(nl_html);
				}

				constructor.append(enlaceHtmlDocs);
			}
		}

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

	/**
	 * Se ejecuta cuando el motor rápido encuentra uno de los patrones declarados.
	 */
	public default void verificarCoincidencia(EventoDeCoincidencia evento) {
	}

	public default boolean quiereAnalizarLineas() {
		return true;
	}

	/**
	 * Devuelve patrones literales que sirven como disparadores rápidos.
	 *
	 * El motor usa estos textos en un escáner rápido. Cuando uno aparece, la
	 * verificación recibe un EventoDeCoincidencia con archivo, línea y contexto.
	 */
	public default String[] patronesRapidos() {
		return new String[0];
	}

	/**
	 * Indica si esta verificación necesita ver todas las líneas aunque no haya
	 * coincidencia rápida.
	 */
	public default boolean necesitaTodasLasLineas() {
		return false;
	}

	/**
	 * Indica si la verificación quiere activarse para el escaneo línea a línea a
	 * partir de este momento (por ejemplo, tras encontrar un patrón global).
	 */
	public default boolean activarEscaneoPorLinea(Consola consola) {
		return false;
	}

}
