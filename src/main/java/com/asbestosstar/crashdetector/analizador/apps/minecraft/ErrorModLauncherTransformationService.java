package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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

	private static final String SERVICE_CONFIGURATION_ERROR = "java.util.ServiceConfigurationError";
	private static final String ITRANSFORMATION_SERVICE = "cpw.mods.modlauncher.api.ITransformationService";
	private static final String PROVIDER = "Provider";
	private static final String PROVIDER_CON_ESPACIO = "Provider ";
	private static final String COULD_NOT_BE_INSTANTIATED = "could not be instantiated";
	private static final String COULD_NOT_BE_INSTANTIATED_CON_ESPACIO = " could not be instantiated";

	@Override
	public String[] patronesRapidos() {
		return new String[] { SERVICE_CONFIGURATION_ERROR, ITRANSFORMATION_SERVICE, COULD_NOT_BE_INSTANTIATED };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde un proveedor de
	 * ITransformationService no puede ser instanciado.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea que contiene el error de ServiceConfigurationError con
		// ITransformationService
		if (linea.contains(SERVICE_CONFIGURATION_ERROR) && linea.contains(ITRANSFORMATION_SERVICE)
				&& linea.contains(PROVIDER) && linea.contains(COULD_NOT_BE_INSTANTIATED)) {

			// Extraemos el nombre de la clase proveedora del error
			int inicioClase = linea.indexOf(PROVIDER_CON_ESPACIO);
			int finClase = linea.indexOf(COULD_NOT_BE_INSTANTIATED_CON_ESPACIO);

			if (inicioClase >= 0) {
				inicioClase += PROVIDER_CON_ESPACIO.length();
			}

			if (inicioClase >= 0 && finClase > inicioClase) {
				claseProveedor = limpiarClaseProveedor(linea.substring(inicioClase, finClase));
			} else {
				// Si no encontramos el patrón exacto, intentamos encontrar la clase de otra
				// manera, sin usar split() para evitar trabajo innecesario en el bucle
				// caliente.
				claseProveedor = buscarClaseProveedorFallback(linea);
			}

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de ITransformationService
			mensaje = MonitorDePID.idioma.errorModLauncherTransformationService(claseProveedor)
					+ Verificaciones.nl_html;
			activado = true;
		}
	}

	private String buscarClaseProveedorFallback(String linea) {
		int inicio = 0;
		int largo = linea.length();

		while (inicio < largo) {
			while (inicio < largo && linea.charAt(inicio) == ' ') {
				inicio++;
			}

			if (inicio >= largo) {
				break;
			}

			int fin = inicio + 1;
			while (fin < largo && linea.charAt(fin) != ' ') {
				fin++;
			}

			String token = linea.substring(inicio, fin);

			if (token.contains("TransformationService") && !token.contains("ITransformationService")) {
				return limpiarClaseProveedor(token);
			}

			inicio = fin + 1;
		}

		return "";
	}

	private String limpiarClaseProveedor(String texto) {
		if (texto == null) {
			return "";
		}

		int inicio = 0;
		int fin = texto.length();

		while (inicio < fin && esCaracterBasuraProveedor(texto.charAt(inicio))) {
			inicio++;
		}

		while (fin > inicio && esCaracterBasuraProveedor(texto.charAt(fin - 1))) {
			fin--;
		}

		return texto.substring(inicio, fin);
	}

	private boolean esCaracterBasuraProveedor(char c) {
		return c == ',' || c == ':' || c == ' ' || c == '\t';
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
	public String[] ocupaTrazo() {
		return new String[] { SERVICE_CONFIGURATION_ERROR, ITRANSFORMATION_SERVICE, COULD_NOT_BE_INSTANTIATED };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}