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
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores ServiceConfigurationError relacionados con IDependencyLocator
 * en Forge o NeoForge. Este error ocurre cuando un archivo de servicio de
 * modlauncher esta corrupto, es de version incorrecta, o el mod no es
 * compatible con la version actual del cargador de mods.
 */
public class ErrorConfiguracionServicioIDependencyLocator implements Verificaciones {

	private static final String PATRON_ERROR = "Caused by: java\\.util\\.ServiceConfigurationError: .*IDependencyLocator: Unable to load ([^\\s]+)";

	private boolean activado = false;
	private String mensaje = "";
	private String claseProblematica = "";
	private List<String> modsUbicacion = new ArrayList<>();
	private String enlaceHtml = "";

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificar(Consola, String, int)}, que es llamado por el sistema de
	 * análisis línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
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
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no es necesario seguir comprobando más líneas.
		if (activado) {
			return;
		}

		if (linea.contains("ServiceConfigurationError") && linea.contains("IDependencyLocator")) {
			Pattern patron = Pattern.compile(PATRON_ERROR);
			Matcher matcher = patron.matcher(linea);
			if (matcher.find()) {
				claseProblematica = matcher.group(1);

				// Buscar el mod que contiene esta clase
				String classPath = claseProblematica.replace('.', '/') + ".class";
				List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino(classPath);

				for (ArchivoDeMod mod : mods) {
					modsUbicacion.add(mod.ubicacion_para_publicar());
				}

				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;

				// Construir el mensaje final ahora que tenemos toda la info relevante
				mensaje = MonitorDePID.idioma.errorConfiguracionServicio(claseProblematica,
						modsUbicacion.isEmpty() ? null : modsUbicacion) + Verificaciones.nl_html + enlaceHtml;
			}
		}
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
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"+this.getClass().getSimpleName()+".java";
	}
	
	
	
}
