package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EliminadorDeMod;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta configuraciones JSON de Sponge Mixin relacionadas con un fallo.
 *
 * Hay dos fuentes:
 *
 * 1. Errores directos por línea: - "The specified resource '...' was invalid or
 * could not be read" - MixinApplyError - InvalidMixinException -
 * InvalidInjectionException - MixinTransformerError - "FAILED during APPLY"
 *
 * 2. VerificacionDeStackTrace: al finalizar el archivo se reutiliza su
 * extractor público de JSON de excepciones Mixin. Esto evita depender de la
 * implementación interna de VerificacionDeStackTrace.sm_config/BiMap.
 *
 * Es importante NO tratar cualquier aparición de ".mixins.json" como error,
 * porque los crash reports de Forge/NeoForge suelen listar muchos mixins
 * normales dentro de los metadatos de cada frame del stack.
 */
public class SpongeMixinConfigsProblematicos implements Verificaciones {

	private boolean activado = false;

	private final Map<String, Integer> sm_config_con_linea = new HashMap<>();
	private final Map<String, Boolean> sm_config_es_fatal = new HashMap<>();
	private final Map<String, String> enlacesPorConfig = new HashMap<>();

	private static final String TEXTO_RESOURCE = "The specified resource '";
	private static final String TEXTO_INVALID_OR_UNREADABLE = "' was invalid or could not be read";

	private static final String TEXTO_MIXIN_APPLY_ERROR = "MixinApplyError:";
	private static final String TEXTO_INVALID_MIXIN = "InvalidMixinException:";
	private static final String TEXTO_INVALID_INJECTION = "InvalidInjectionException:";
	private static final String TEXTO_MIXIN_TRANSFORMER_ERROR = "MixinTransformerError:";
	private static final String TEXTO_FAILED_DURING_APPLY = "FAILED during APPLY";

	private static final String TEXTO_MIXINS = "mixins";
	private static final String TEXTO_JSON = ".json";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_RESOURCE, TEXTO_INVALID_OR_UNREADABLE, TEXTO_MIXIN_APPLY_ERROR, TEXTO_INVALID_MIXIN,
				TEXTO_INVALID_INJECTION, TEXTO_MIXIN_TRANSFORMER_ERROR, TEXTO_FAILED_DURING_APPLY };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroLinea) {
		if (linea == null || linea.isEmpty()) {
			return;
		}

		/*
		 * Caso legacy:
		 *
		 * The specified resource 'xxxxx.mixins.json' was invalid or could not be read
		 */
		if (lineaContieneResourceMixinInvalido(linea)) {
			String nombre = extraerResourceEntreComillas(linea);

			if (esNombreMixinJson(nombre)) {
				registrarConfig(consola, nombre, numeroLinea, true);
			}
		}

		/*
		 * Nuevos formatos de error Mixin.
		 *
		 * Sólo extraemos JSON si la MISMA línea tiene un contexto inequívoco de error.
		 * No basta con que aparezca ".mixins.json", porque los metadatos normales de
		 * Forge contienen cientos de esos nombres.
		 */
		if (esLineaDeErrorMixin(linea)) {
			for (String nombre : extraerJsonsDeLinea(linea)) {
				if (esNombreMixinJson(nombre)) {
					registrarConfig(consola, nombre, numeroLinea, true);
				}
			}
		}
	}

	/**
	 * El motor rápido termina VerificacionDeStackTrace antes de llamar a
	 * finalizarArchivo() de las verificaciones. Por ello aquí podemos reutilizar su
	 * extractor público sobre el contenido completo del log.
	 *
	 * No accedemos directamente a vdst.sm_config porque es un BiMap propio y no
	 * queremos acoplar esta verificación a su representación interna.
	 */
	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		importarDesdeVerificacionDeStackTrace(consola);
	}

	private void importarDesdeVerificacionDeStackTrace(Consola consola) {
		if (consola == null || consola.verificacion_de_stacktrace == null) {
			return;
		}

		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;

		String contenido = obtenerContenidoParaVDST(consola);

		if (contenido == null || contenido.isEmpty()) {
			return;
		}

		List<String> jsons;

		try {
			jsons = vdst.obtenerArchivosJsonEnMixinExceptions(contenido);
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			return;
		}

		if (jsons == null || jsons.isEmpty()) {
			return;
		}

		/*
		 * LinkedHashSet: - elimina duplicados - conserva el orden en que VDST encontró
		 * los JSON
		 */
		Set<String> unicos = new LinkedHashSet<>(jsons);

		for (String nombreCrudo : unicos) {
			String nombre = normalizarNombreJson(nombreCrudo);

			if (!esNombreMixinJson(nombre)) {
				continue;
			}

			UbicacionMixin ubicacion = localizarJsonProblematico(consola, nombre);

			if (ubicacion != null) {
				registrarConfig(consola, nombre, ubicacion.numeroLinea, ubicacion.fatal);
			} else {
				/*
				 * VDST ya determinó que el JSON apareció dentro de una excepción Mixin. Aunque
				 * no consigamos reconstruir la línea exacta, conservamos el hallazgo.
				 *
				 * No se crea enlace de línea si numeroLinea == -1.
				 */
				registrarConfig(consola, nombre, -1, true);
			}
		}
	}

	private String obtenerContenidoParaVDST(Consola consola) {
		if (consola.contenido_verificar != null && !consola.contenido_verificar.isEmpty()) {
			return consola.contenido_verificar;
		}

		if (consola.contenido != null && !consola.contenido.isEmpty()) {
			return consola.contenido;
		}

		if (consola.lineas_verificar != null && consola.lineas_verificar.length > 0) {
			return String.join("\n", consola.lineas_verificar);
		}

		return null;
	}

	private UbicacionMixin localizarJsonProblematico(Consola consola, String nombre) {
		if (consola == null || nombre == null || nombre.isEmpty()) {
			return null;
		}

		String[] lineas = consola.lineas_verificar;

		if (lineas == null || lineas.length == 0) {
			String contenido = consola.contenido_verificar;

			if (contenido == null || contenido.isEmpty()) {
				contenido = consola.contenido;
			}

			if (contenido == null || contenido.isEmpty()) {
				return null;
			}

			lineas = contenido.split("\\r?\\n", -1);
		}

		/*
		 * Primera pasada: buscar el JSON en una línea que además sea claramente un
		 * error Mixin.
		 */
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];

			if (linea != null && linea.contains(nombre) && esLineaDeErrorMixin(linea)) {
				return new UbicacionMixin(i, true);
			}
		}

		/*
		 * Segunda pasada: VDST puede haber encontrado el JSON en otra variante de
		 * excepción que no esté todavía en nuestra lista de marcadores. Si la línea
		 * contiene además org.spongepowered.asm.mixin, sigue siendo una ubicación mucho
		 * más fiable que una aparición cualquiera en los metadatos de un frame.
		 */
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];

			if (linea != null && linea.contains(nombre) && linea.contains("org.spongepowered.asm.mixin")) {
				return new UbicacionMixin(i, true);
			}
		}

		return null;
	}

	private void registrarConfig(Consola consola, String nombreCrudo, int numeroLinea, boolean fatal) {
		String nombre = normalizarNombreJson(nombreCrudo);

		if (!esNombreMixinJson(nombre)) {
			return;
		}

		Integer lineaAnterior = sm_config_con_linea.get(nombre);

		/*
		 * Si ya existía sin línea (-1) y ahora encontramos una línea real, actualizamos
		 * la ubicación.
		 */
		if (lineaAnterior == null || (lineaAnterior.intValue() < 0 && numeroLinea >= 0)) {
			sm_config_con_linea.put(nombre, numeroLinea);
		}

		/*
		 * Una detección fatal nunca debe degradarse posteriormente a no fatal.
		 */
		if (fatal || !sm_config_es_fatal.containsKey(nombre)) {
			sm_config_es_fatal.put(nombre, fatal);
		}

		/*
		 * Crear el enlace sólo cuando tenemos una línea válida.
		 */
		if (consola != null && numeroLinea >= 0 && !enlacesPorConfig.containsKey(nombre)) {
			try {
				String enlace = consola.agregarErrorALectador(numeroLinea, this);

				if (enlace != null) {
					enlacesPorConfig.put(nombre, enlace);
				}
			} catch (Exception e) {
				CrashDetectorLogger.logException(e);
			}
		}

		activado = true;
	}

	private boolean lineaContieneResourceMixinInvalido(String linea) {
		return linea.contains(TEXTO_RESOURCE) && linea.contains(TEXTO_INVALID_OR_UNREADABLE);
	}

	private boolean esLineaDeErrorMixin(String linea) {
		if (linea == null || linea.isEmpty()) {
			return false;
		}

		/*
		 * El caso antiguo sigue siendo un error explícito.
		 */
		if (lineaContieneResourceMixinInvalido(linea)) {
			return true;
		}

		return linea.contains(TEXTO_MIXIN_APPLY_ERROR) || linea.contains(TEXTO_INVALID_MIXIN)
				|| linea.contains(TEXTO_INVALID_INJECTION) || linea.contains(TEXTO_MIXIN_TRANSFORMER_ERROR)
				|| linea.contains(TEXTO_FAILED_DURING_APPLY);
	}

	private String extraerResourceEntreComillas(String linea) {
		int ini = linea.indexOf(TEXTO_RESOURCE);

		if (ini < 0) {
			return null;
		}

		ini += TEXTO_RESOURCE.length();

		int fin = linea.indexOf('\'', ini);

		if (fin <= ini) {
			return null;
		}

		return linea.substring(ini, fin).trim();
	}

	/**
	 * Extrae tokens que terminan en ".json" sin usar regex.
	 *
	 * Ejemplos que reconoce:
	 *
	 * Mixin [valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager] in
	 * config [valkyrienskies-common.mixins.json] ->
	 * valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager ->
	 *
	 * Los delimitadores [, (, ', ", etc. se eliminan mediante
	 * normalizarNombreJson().
	 */
	private List<String> extraerJsonsDeLinea(String linea) {
		List<String> resultado = new ArrayList<>();

		if (linea == null || linea.indexOf(TEXTO_JSON) < 0) {
			return resultado;
		}

		int desde = 0;

		while (desde < linea.length()) {
			int json = linea.indexOf(TEXTO_JSON, desde);

			if (json < 0) {
				break;
			}

			int inicio = json - 1;

			while (inicio >= 0 && esCaracterDeNombreJson(linea.charAt(inicio))) {
				inicio--;
			}

			inicio++;

			int fin = json + TEXTO_JSON.length();

			if (inicio < fin) {
				String candidato = normalizarNombreJson(linea.substring(inicio, fin));

				if (esNombreMixinJson(candidato) && !resultado.contains(candidato)) {
					resultado.add(candidato);
				}
			}

			desde = json + TEXTO_JSON.length();
		}

		return resultado;
	}

	private boolean esCaracterDeNombreJson(char c) {
		/*
		 * Nombres de recursos Mixin suelen contener letras, números, puntos, guiones,
		 * guiones bajos, barras y ocasionalmente '$'.
		 *
		 * ':' NO se incluye porque normalmente separa: config.mixins.json:NombreDeMixin
		 */
		return Character.isLetterOrDigit(c) || c == '.' || c == '-' || c == '_' || c == '/' || c == '\\' || c == '$';
	}

	private String normalizarNombreJson(String nombre) {
		if (nombre == null) {
			return null;
		}

		String s = nombre.trim();

		while (!s.isEmpty() && esDelimitadorInicial(s.charAt(0))) {
			s = s.substring(1).trim();
		}

		/*
		 * Si nos llega "config.mixins.json:ClaseMixin", quedarnos sólo con el JSON.
		 */
		int json = s.indexOf(TEXTO_JSON);

		if (json >= 0) {
			s = s.substring(0, json + TEXTO_JSON.length());
		}

		while (!s.isEmpty() && esDelimitadorFinal(s.charAt(s.length() - 1))) {
			s = s.substring(0, s.length() - 1).trim();
		}

		return s;
	}

	private boolean esDelimitadorInicial(char c) {
		return c == '[' || c == '(' || c == '{' || c == '\'' || c == '"' || c == ':' || c == '>';
	}

	private boolean esDelimitadorFinal(char c) {
		return c == ']' || c == ')' || c == '}' || c == '\'' || c == '"' || c == ':' || c == ',' || c == ';';
	}

	private boolean esNombreMixinJson(String nombre) {
		if (nombre == null || nombre.isEmpty()) {
			return false;
		}

		return nombre.endsWith(TEXTO_JSON) && nombre.toLowerCase().contains(TEXTO_MIXINS);
	}

	@Override
	public Verificaciones nueva() {
		return new SpongeMixinConfigsProblematicos();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f;
	}

	@Override
	public String mensaje() {
		CrashDetectorLogger.log("Agregando Mensaje de SM Problematicos");

		if (sm_config_con_linea.isEmpty()) {
			CrashDetectorLogger.log("sm_config_con_linea no tiene nada");
			return "";
		}

		StringBuilder html = new StringBuilder();

		html.append("<span style='color: #").append(Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas())
				.append("; font-weight: bold;'>").append(MonitorDePID.idioma.config_spongemixin_problematico())
				.append("</span>").append(Verificaciones.nl_html).append("<ul>");

		List<String> listItems = new ArrayList<>();

		for (Map.Entry<String, Integer> entry : sm_config_con_linea.entrySet()) {
			String sm = entry.getKey();

			CrashDetectorLogger.log(" SM en mensaje " + sm);

			boolean isFatal = sm_config_es_fatal.getOrDefault(sm, false);

			String jars_de_sm_string = "";
			List<String> jars_de_sm = Buscador.obtenerUbicaciones(Buscador.buscarModsConTermino(sm));

			if (!jars_de_sm.isEmpty()) {
				List<String> boldJars = new ArrayList<>();

				for (String jar : jars_de_sm) {
					boldJars.add("<strong>" + jar + "</strong>");
				}

				jars_de_sm_string = " (" + String.join(", ", boldJars) + ")";
			}

			String enlace = enlacesPorConfig.getOrDefault(sm, "");
			String prefix = isFatal ? MonitorDePID.idioma.posibilidad_fatal() : "";

			listItems.add("<li>" + prefix + sm + jars_de_sm_string + " " + enlace + "</li>");
		}

		html.append(String.join("", listItems)).append("</ul>");

		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_spongemixin_configs_problematicos();
	}

	@Override
	public QuickFix solucion() {
		QuickFix.Builder builder = new QuickFix.Builder(
				MonitorDePID.idioma.nombre_de_spongemixin_configs_problematicos());

		for (String sm : sm_config_con_linea.keySet()) {
			List<String> jars = Buscador.obtenerUbicaciones(Buscador.buscarModsConTermino(sm));

			for (String jar : jars) {
				String buttonText = MonitorDePID.idioma.eliminar() + ": " + jar;

				builder.agregarBoton(buttonText, retener -> {
					try {
						EliminadorDeMod.eliminarMod(jar);

						if (!EliminadorDeMod.esModoHeadless()) {
							JOptionPane.showMessageDialog(null,
									MonitorDePID.idioma.jar_eliminado_exitosamente() + ": " + jar,
									MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e) {
						CrashDetectorLogger.logException(e);

						if (!EliminadorDeMod.esModoHeadless()) {
							JOptionPane.showMessageDialog(null,
									MonitorDePID.idioma.error_al_eliminar_jar() + ": " + jar,
									MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		}

		return builder.construir();
	}

	@Override
	public String id() {
		return "spongemixin_configs_problematicos";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	private static final class UbicacionMixin {
		final int numeroLinea;
		final boolean fatal;

		UbicacionMixin(int numeroLinea, boolean fatal) {
			this.numeroLinea = numeroLinea;
			this.fatal = fatal;
		}
	}
}
