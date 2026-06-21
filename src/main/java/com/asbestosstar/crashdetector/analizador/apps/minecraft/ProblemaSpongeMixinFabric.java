package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.LinkedHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores fatales de SpongeMixin en Fabric. Mantiene la lógica original
 * por línea pero añade pre-check global para mejorar rendimiento en logs
 * grandes.
 */
public class ProblemaSpongeMixinFabric implements Verificaciones {

	private boolean activado = false;

	// mod -> enlace
	private final Map<String, String> modsConEnlace = new LinkedHashMap<>();

	private static final String TEXTO_MIXIN_TRANSFORMER = "MixinTransformerError";
	private static final String TEXTO_INVALID_MIXIN = "InvalidMixinException";
	private static final String TEXTO_MIXIN_APPLY = "MixinApplyError";
	private static final String TEXTO_CRITICAL_INJECTION = "Critical injection failure";
	private static final String TEXTO_MIXIN_INITIALISATION = "MixinInitialisationError";
	private static final String TEXTO_FROM_MOD = "from mod ";
	private static final String TEXTO_MIXINS_JSON = ".mixins.json";
	private static final String TEXTO_CONFIG = "config ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_MIXIN_TRANSFORMER, TEXTO_INVALID_MIXIN, TEXTO_MIXIN_APPLY, TEXTO_CRITICAL_INJECTION,
				TEXTO_MIXIN_INITIALISATION, TEXTO_FROM_MOD, TEXTO_MIXINS_JSON };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		boolean lineaEsErrorFatal = lineaEsErrorFatalMixin(linea);

		// Caso clásico: "... from mod X"
		if (lineaEsErrorFatal && linea.contains(TEXTO_FROM_MOD)) {

			int indice = linea.indexOf(TEXTO_FROM_MOD);
			if (indice == -1)
				return;

			String candidato = linea.substring(indice + TEXTO_FROM_MOD.length()).trim();

			int fin = candidato.indexOf(' ');
			if (fin == -1)
				fin = candidato.length();

			String nombreMod = candidato.substring(0, fin).trim();

			if (!nombreMod.isEmpty() && !modsConEnlace.containsKey(nombreMod)) {

				String enlace = consola.agregarErrorALectador(numero_de_linea, this);
				modsConEnlace.put(nombreMod, enlace);

				this.activado = true;
			}
		}

		// Nuevo caso: MixinInitialisationError con mixins.json
		if (linea.contains(TEXTO_MIXIN_INITIALISATION) && linea.contains(TEXTO_MIXINS_JSON)) {

			String enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Extraer nombre del config mixin
			int inicio = linea.indexOf(TEXTO_CONFIG);
			if (inicio != -1) {

				String restante = linea.substring(inicio + TEXTO_CONFIG.length()).trim();

				int fin = restante.indexOf(' ');
				if (fin == -1)
					fin = restante.length();

				String mixinConfig = restante.substring(0, fin).trim();

				// Obtener modId aproximado (antes de ".mixins.json")
				int punto = mixinConfig.indexOf(TEXTO_MIXINS_JSON);
				String nombreMod = punto > 0 ? mixinConfig.substring(0, punto) : mixinConfig;

				if (!nombreMod.isEmpty() && !modsConEnlace.containsKey(nombreMod)) {
					modsConEnlace.put(nombreMod, enlace);
					this.activado = true;
				}
			}
		}
	}

	private boolean lineaContieneIndicioMixinFatal(String linea) {
		return lineaEsErrorFatalMixin(linea) || linea.contains(TEXTO_FROM_MOD) || linea.contains(TEXTO_MIXINS_JSON);
	}

	private boolean lineaEsErrorFatalMixin(String linea) {
		return linea.contains(TEXTO_MIXIN_TRANSFORMER) || linea.contains(TEXTO_INVALID_MIXIN)
				|| linea.contains(TEXTO_MIXIN_APPLY) || linea.contains(TEXTO_CRITICAL_INJECTION)
				|| linea.contains(TEXTO_MIXIN_INITIALISATION);
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaSpongeMixinFabric();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 800.0f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : modsConEnlace.entrySet()) {

			sb.append(MonitorDePID.idioma.mensajeModFatal(entry.getKey())).append(entry.getValue())
					.append(Verificaciones.nl_html);
		}

		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaModFatal();
	}

	@Override
	public QuickFix solucion() {

		Builder builder = new Builder(nombre());

		for (String mod : modsConEnlace.keySet()) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(mod) + Verificaciones.nl_html);
		}

		return builder.construir();
	}

	@Override
	public String id() {
		return "problema_spongemixin_fabric_multiple";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}