package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores cuando una clase registrada como listener de eventos no
 * contiene métodos válidos. Detecta específicamente el error:
 *
 * "No listeners found in class [nombre de clase]"
 *
 * Utiliza Buscardor para identificar mods que contienen la clase problemática.
 */
public class ErrorSinListenersEnClase implements Verificaciones {

	private boolean activado = false;
	private boolean posible = false;

	private String mensaje = "";
	private String nombreClase = "";

	private List<String> modsUbicacion = new ArrayList<>();

	private String enlaceHtml = "";

	private static final String TEXTO_ERROR = "No listeners found in class ";

	/**
	 * Verificación global barata.
	 * <p>
	 * Solo revisa si el log completo contiene el texto base del error. Así evitamos
	 * revisar línea por línea cuando el error claramente no existe.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		if (consola.contenido_verificar.contains(TEXTO_ERROR)) {
			posible = true;
		}
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón:
	 *
	 * "No listeners found in class <clase>"
	 *
	 * en la línea actual, extrae el nombre de la clase, localiza los mods que
	 * contienen esa clase y registra la línea en el lector.
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

		int inicio = linea.indexOf(TEXTO_ERROR);

		if (inicio < 0) {
			return;
		}

		// Mover al inicio real del nombre de clase.
		inicio += TEXTO_ERROR.length();

		if (inicio >= linea.length()) {
			return;
		}

		// Buscar final de la clase.
		// Normalmente termina en espacio o final de línea.
		int fin = linea.indexOf(' ', inicio);

		if (fin < 0) {
			fin = linea.length();
		}

		// Extraer nombre de clase.
		nombreClase = linea.substring(inicio, fin).trim();

		if (nombreClase.isEmpty()) {
			return;
		}

		// Convierte el nombre de clase de notación con puntos
		// a notación con barras.
		String classPath = nombreClase.replace('.', '/');

		// Busca mods que contienen esta clase.
		List<ArchivoDeMod> modsPotenciales = Buscardor.buscarModsConTermino(classPath + ".class");

		// Extrae las ubicaciones para publicar de cada mod encontrado.
		for (ArchivoDeMod mod : modsPotenciales) {
			modsUbicacion.add(mod.ubicacion_para_publicar());
		}

		mensaje = MonitorDePID.idioma.errorSinListenersEnClase(nombreClase, modsUbicacion) + Verificaciones.nl_html;

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorSinListenersEnClase();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 880.0f; // Prioridad alta
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
		return MonitorDePID.idioma.nombre_de_error_sin_listeners_en_clase();
	}

	@Override
	public QuickFix solucion() {

		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_sin_listeners_en_clase(nombreClase, modsUbicacion))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_sin_listeners_en_clase(nombreClase))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_sin_listeners_en_clase(nombreClase, modsUbicacion))
				.construir();
	}

	@Override
	public String id() {
		return "sin_listeners_en_clase";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve true cuando:
	 * <ul>
	 * <li>El verificador ya se activó.</li>
	 * <li>El trazo contiene el texto completo del error con la clase conocida, o
	 * como fallback muy estricto el patrón base.</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (nombreClase != null && !nombreClase.isEmpty()) {

			String esperado = TEXTO_ERROR + nombreClase;

			return t.contains(esperado);
		}

		// Fallback muy estricto.
		return t.contains(TEXTO_ERROR);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}