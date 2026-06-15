package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores críticos relacionados con plugins de JEI (Just Enough Items)
 * que causan crashes. Detecta específicamente el error "Caught an error from
 * mod plugin: class [clase] [modid]:[plugin_id]". Este error puede causar
 * crashes durante la inicialización del juego.
 */
public class ErrorJEIPluginFallido implements VerificacionRapida {

	private boolean activado = false;
	private boolean posible = false;

	private String mensaje = "";
	private String nombreClase = "";
	private String modId = "";
	private String pluginId = "";
	private String enlaceHtml = "";

	private static final String TEXTO_ERROR = "Caught an error from mod plugin: class";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(TEXTO_ERROR)) {
			posible = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación global barata.
	 * <p>
	 * Solo revisa si el texto base aparece en el log completo. Así evitamos hacer
	 * trabajo línea por línea cuando este error claramente no aparece.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// Método de compatibilidad — no hace nada en modo rápido/streaming.
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posible && !activado;
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón: "Caught an error from mod plugin: class [CLASE]
	 * [MODID]:[PLUGIN_ID]" en la línea actual, extrae los campos y registra el
	 * enlace.
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

		// Mover el índice al comienzo de la clase.
		inicio += TEXTO_ERROR.length();

		// Saltar espacios después del texto base.
		while (inicio < linea.length() && linea.charAt(inicio) == ' ') {
			inicio++;
		}

		if (inicio >= linea.length()) {
			return;
		}

		// La clase termina en el siguiente espacio.
		int finClase = linea.indexOf(' ', inicio);
		if (finClase < 0 || finClase <= inicio) {
			return;
		}

		nombreClase = linea.substring(inicio, finClase);

		if (nombreClase.isEmpty()) {
			return;
		}

		// Después de la clase viene "modid:pluginId".
		int inicioMod = finClase + 1;

		// Saltar espacios antes de modid:pluginId.
		while (inicioMod < linea.length() && linea.charAt(inicioMod) == ' ') {
			inicioMod++;
		}

		if (inicioMod >= linea.length()) {
			return;
		}

		int separador = linea.indexOf(':', inicioMod);
		if (separador < 0 || separador <= inicioMod) {
			return;
		}

		// El modId termina antes de los dos puntos.
		modId = linea.substring(inicioMod, separador);

		if (modId.isEmpty()) {
			return;
		}

		int inicioPlugin = separador + 1;
		if (inicioPlugin >= linea.length()) {
			return;
		}

		// El pluginId termina en el siguiente espacio o al final de la línea.
		int finPlugin = linea.indexOf(' ', inicioPlugin);
		if (finPlugin < 0) {
			finPlugin = linea.length();
		}

		pluginId = linea.substring(inicioPlugin, finPlugin);

		if (pluginId.isEmpty()) {
			return;
		}

		mensaje = MonitorDePID.idioma.errorJEIPluginFallido(nombreClase, modId, pluginId) + Verificaciones.nl_html;
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorJEIPluginFallido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 700.0f; // Máxima prioridad - error crítico que causa crashes
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
		return MonitorDePID.idioma.nombre_de_error_jei_plugin_fallido();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_jei_plugin_fallido(modId))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_jei_plugin_fallido(modId))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_jei_plugin_fallido(modId)).construir();
	}

	@Override
	public String id() {
		return "jei_plugin_fallido";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la línea completa característica del error, incluyendo
	 * clase, modId y pluginId, o como fallback muy estricto contiene el patrón base
	 * y menciona JEI.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo antes que
	 * marcar un trazo que no corresponda a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!nombreClase.isEmpty() && !modId.isEmpty() && !pluginId.isEmpty()) {
			String esperado = TEXTO_ERROR + " " + nombreClase + " " + modId + ":" + pluginId;
			return t.contains(esperado);
		}

		// Fallback muy estricto si por alguna razón no se capturaron todos los datos.
		return t.contains(TEXTO_ERROR) && t.contains("JEI");
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}