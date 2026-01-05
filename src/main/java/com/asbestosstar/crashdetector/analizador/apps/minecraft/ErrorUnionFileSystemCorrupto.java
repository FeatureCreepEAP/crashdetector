package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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
	private String enlaceHtml = "";

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificar(Consola, String, int)}, llamada por el analizador línea a
	 * línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca la combinación:
	 * <ul>
	 * <li>"cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException"</li>
	 * <li>"java.util.zip.ZipException: zip END header not found"</li>
	 * </ul>
	 * en la línea actual. Cuando la encuentra:
	 * <ul>
	 * <li>Intenta localizar el nombre del .jar problemático escaneando el contenido
	 * completo del log.</li>
	 * <li>Registra la línea en el lector.</li>
	 * <li>Activa el verificador y construye el mensaje de error.</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		// Detecta el error específico de UnionFileSystem con ZipException
		if (linea.contains("cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException")
				&& linea.contains("java.util.zip.ZipException: zip END header not found")) {

			// Intenta identificar el nombre del archivo problemático en el contenido
			// completo
			String contenidoConsola = consola.contenido_verificar;
			Pattern pattern = Pattern.compile("at.*?/(.*?\\.jar)");
			Matcher matcher = pattern.matcher(contenidoConsola);
			if (matcher.find()) {
				nombreArchivo = matcher.group(1);
			}

			mensaje = MonitorDePID.idioma.errorUnionFileSystemCorrupto(nombreArchivo) + Verificaciones.nl_html;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
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
		if (!activado)
			return "";
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
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene tanto la clase de UnionFileSystem como la ZipException
	 * concreta "zip END header not found".</li>
	 * </ul>
	 * Es intencionadamente conservador: mejor un falso negativo que marcar un trazo
	 * que no corresponde a este problema.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;
		return t.contains("cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException")
				&& t.contains("java.util.zip.ZipException: zip END header not found");
	}
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"+this.getClass().getSimpleName()+".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
	
	
	
	
	
	
	
	
}
