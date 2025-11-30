package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Detecta errores de ServiceConfigurationError relacionados con
 * ITransformationService de ModLauncher, donde un proveedor no puede ser
 * instanciado, lo que indica un mod con una implementación defectuosa.
 */
public class ErrorModLauncherTransformationService implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String claseProveedor = "";

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {
		// Se usa el método verificar(Consola, String, int) en lugar de este.
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde un proveedor de
	 * ITransformationService no puede ser instanciado.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de ServiceConfigurationError con
		// ITransformationService
		if (linea.contains("java.util.ServiceConfigurationError")
				&& linea.contains("cpw.mods.modlauncher.api.ITransformationService") && linea.contains("Provider")
				&& linea.contains("could not be instantiated")) {

			// Extraemos el nombre de la clase proveedora del error
			int inicioClase = linea.indexOf("Provider ") + "Provider ".length();
			int finClase = linea.indexOf(" could not be instantiated");
			if (inicioClase > -1 && finClase > inicioClase) {
				claseProveedor = linea.substring(inicioClase, finClase).trim();
			} else {
				// Si no encontramos el patrón exacto, intentamos encontrar la clase de otra
				// manera
				String[] partes = linea.split(" ");
				for (int i = 0; i < partes.length; i++) {
					if (partes[i].contains("TransformationService") && !partes[i].contains("ITransformationService")) {
						claseProveedor = partes[i].replace(",", "").replace(":", "").trim();
						break;
					}
				}
			}

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de ITransformationService
			mensaje = MonitorDePID.idioma.errorModLauncherTransformationService(claseProveedor)
					+ Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorModLauncherTransformationService();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f; // Prioridad máxima: rompe la carga del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorModLauncherTransformationService();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorModLauncherTransformationService(claseProveedor))
				.construir();
	}

	@Override
	public String id() {
		return "error_modlauncher_transformation_service";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * ITransformationService de ModLauncher.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("java.util.ServiceConfigurationError")
				&& t.contains("cpw.mods.modlauncher.api.ITransformationService") && t.contains("Provider")
				&& t.contains("could not be instantiated");
	}
}