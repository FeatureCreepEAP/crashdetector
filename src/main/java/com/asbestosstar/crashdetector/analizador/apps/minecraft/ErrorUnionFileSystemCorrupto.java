package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores específicos relacionados con UnionFileSystem y archivos de
 * mod corruptos. Detecta específicamente el error
 * "cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException".
 *
 * Este error es común en modpacks debido a problemas con archivos JAR
 * corruptos, descargas incompletas o problemas del lanzador.
 */
public class ErrorUnionFileSystemCorrupto implements Verificaciones {

	private boolean activado = false;
	private boolean posible = false;

	private String mensaje = "";

	private String nombreArchivo = "un archivo de mod";

	private boolean esModpack = false;

	private String enlaceHtml = "";

	private static final String TEXTO_UNION = "cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException";

	private static final String TEXTO_ZIP = "java.util.zip.ZipException: zip END header not found";

	/**
	 * Verificación global barata.
	 * <p>
	 * Solo revisa si el log completo contiene ambas partes del error. Así evitamos
	 * revisar línea por línea cuando este error claramente no existe.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_UNION) && contenido.contains(TEXTO_ZIP)) {

			posible = true;
		}
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca la combinación:
	 * <ul>
	 * <li>UnionFileSystem$UncheckedIOException</li>
	 * <li>zip END header not found</li>
	 * </ul>
	 *
	 * Cuando la encuentra:
	 * <ul>
	 * <li>Intenta localizar el nombre del JAR problemático.</li>
	 * <li>Registra la línea en el lector.</li>
	 * <li>Activa el verificador.</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Si ya se activó o el chequeo global dijo que no es posible,
		// no seguimos revisando líneas.
		if (activado || !posible) {
			return;
		}

		if (linea == null) {
			return;
		}

		// Detecta el error específico.
		if (!linea.contains(TEXTO_UNION) || !linea.contains(TEXTO_ZIP)) {

			return;
		}

		// Intentar identificar el archivo JAR problemático
		// escaneando el contenido completo del log.
		String contenidoConsola = consola.contenido_verificar;

		if (contenidoConsola != null) {

			String jarDetectado = buscarPrimerJarEnContenido(contenidoConsola);

			if (jarDetectado != null && !jarDetectado.isEmpty()) {
				nombreArchivo = jarDetectado;
			}
		}

		mensaje = MonitorDePID.idioma.errorUnionFileSystemCorrupto(nombreArchivo) + Verificaciones.nl_html;

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		activado = true;
	}

	/**
	 * Busca el primer archivo .jar mencionado en el log.
	 * <p>
	 * Reemplaza el regex:
	 *
	 * "at.*?/(.*?\\.jar)"
	 *
	 * usando búsqueda manual mucho más rápida.
	 * </p>
	 */
	private String buscarPrimerJarEnContenido(String contenido) {

		if (contenido == null || contenido.isEmpty()) {
			return null;
		}

		int posicionJar = contenido.indexOf(".jar");

		if (posicionJar < 0) {
			return null;
		}

		// Buscar inicio aproximado del nombre/ruta.
		int inicio = posicionJar;

		while (inicio > 0) {

			char c = contenido.charAt(inicio - 1);

			// Permitimos caracteres comunes de rutas.
			if (esCaracterRuta(c)) {
				inicio--;
			} else {
				break;
			}
		}

		// Incluir ".jar"
		int fin = posicionJar + 4;

		if (inicio >= fin || fin > contenido.length()) {
			return null;
		}

		String ruta = contenido.substring(inicio, fin).trim();

		if (ruta.isEmpty()) {
			return null;
		}

		// Extraer solo el nombre del archivo.
		return extraerNombreArchivo(ruta);
	}

	/**
	 * Indica si el carácter puede formar parte de una ruta.
	 */
	private boolean esCaracterRuta(char c) {

		return Character.isLetterOrDigit(c) || c == '/' || c == '\\' || c == '.' || c == '_' || c == '-' || c == ':';
	}

	/**
	 * Extrae solo el nombre del archivo desde una ruta completa.
	 */
	private String extraerNombreArchivo(String ruta) {

		if (ruta == null || ruta.isEmpty()) {
			return ruta;
		}

		int ultimoSlash = ruta.lastIndexOf('/');
		int ultimoBackslash = ruta.lastIndexOf('\\');

		int ultimoSeparador = Math.max(ultimoSlash, ultimoBackslash);

		if (ultimoSeparador >= 0 && ultimoSeparador + 1 < ruta.length()) {

			return ruta.substring(ultimoSeparador + 1);
		}

		return ruta;
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
		return 965.0f; // Máxima prioridad
	}

	@Override
	public String mensaje() {

		if (!activado) {
			return "";
		}

		return mensaje + enlaceHtml;
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

	@Override
	public String id() {
		return "union_filesystem_corrupto";
	}

	/**
	 * Indica si este verificador ocupa un trazo concreto.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve true cuando el trazo contiene
	 * ambas partes exactas del error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (!activado || trazo == null || trazo.trace == null) {

			return false;
		}

		String t = trazo.trace;

		return t.contains(TEXTO_UNION) && t.contains(TEXTO_ZIP);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}