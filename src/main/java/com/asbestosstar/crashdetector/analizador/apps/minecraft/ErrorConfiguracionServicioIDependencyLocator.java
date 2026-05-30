package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores ServiceConfigurationError relacionados con IDependencyLocator
 * en Forge o NeoForge. Este error ocurre cuando un archivo de servicio de
 * modlauncher esta corrupto, es de version incorrecta, o el mod no es
 * compatible con la version actual del cargador de mods.
 */
public class ErrorConfiguracionServicioIDependencyLocator implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String claseProblematica = "";
	private List<String> modsUbicacion = new ArrayList<>();
	private String enlaceHtml = "";

	public boolean posible = false;

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificarPorLinea(Consola, String, int)}, que es llamado por el
	 * sistema de análisis línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
		String cont = consola.contenido_verificar;
		if (cont.contains("ServiceConfigurationError") && cont.contains("IDependencyLocator")) {
			posible = true;
		}
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca líneas con: "ServiceConfigurationError" + "IDependencyLocator" y aplica
	 * el patrón PATRON_ERROR para extraer la clase problemática. Luego intenta
	 * localizar los mods que contienen dicha clase.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó o el chequeo global dijo que no es posible,
		// no seguimos revisando más líneas.
		if (activado || !posible) {
			return;
		}

		// Verificación rápida inicial.
		if (!linea.contains("ServiceConfigurationError") || !linea.contains("IDependencyLocator")) {
			return;
		}

		// Texto que aparece justo antes del nombre de la clase.
		String marcador = "IDependencyLocator: Unable to load ";

		int inicio = linea.indexOf(marcador);

		// Si no existe el marcador, salir.
		if (inicio < 0) {
			return;
		}

		// Mover al inicio real del nombre de clase.
		inicio += marcador.length();

		// Buscar el final de la clase.
		// Normalmente termina en espacio o final de línea.
		int fin = linea.indexOf(' ', inicio);

		if (fin < 0) {
			fin = linea.length();
		}

		// Extraer clase problemática.
		claseProblematica = linea.substring(inicio, fin).trim();

		// Validación básica.
		if (claseProblematica.isEmpty()) {
			return;
		}

		// Buscar el mod que contiene esta clase.
		String classPath = claseProblematica.replace('.', '/') + ".class";

		List<ArchivoDeMod> mods = Buscador.buscarModsConTermino(classPath);

		for (ArchivoDeMod mod : mods) {
			modsUbicacion.add(mod.ubicacion_para_publicar());
		}

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		activado = true;

		// Construir mensaje final.
		mensaje = MonitorDePID.idioma.errorConfiguracionServicio(claseProblematica,
				modsUbicacion.isEmpty() ? null : modsUbicacion) + Verificaciones.nl_html + enlaceHtml;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorConfiguracionServicioIDependencyLocator();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Prioridad alta: impide el inicio del juego
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_configuracion_servicio();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_configuracion_servicio(modsUbicacion))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_configuracion_servicio()).construir();
	}

	@Override
	public String id() {
		return "error_configuracion_servicio"; // Sin acentos, en minusculas, guiones bajos
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} si:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la firma del problema de configuración de servicio para
	 * IDependencyLocator, idealmente con la clase problemática.</li>
	 * </ul>
	 * Es deliberadamente conservador: mejor un falso negativo que marcar un trazo
	 * que no corresponde a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (claseProblematica != null && !claseProblematica.isEmpty()) {
			return t.contains("ServiceConfigurationError") && t.contains("IDependencyLocator")
					&& t.contains(claseProblematica);
		}

		// Fallback muy estricto si por alguna razón no se guardó la clase.
		return t.contains("ServiceConfigurationError") && t.contains("IDependencyLocator")
				&& t.contains("Unable to load");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}
