package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.LinkedHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores fatales de SpongeMixin en Fabric. Mantiene la lógica original
 * por línea pero añade pre-check global para mejorar rendimiento en logs
 * grandes.
 */
public class ProblemaSpongeMixinFabric implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;

	// mod -> enlace
	private final Map<String, String> modsConEnlace = new LinkedHashMap<>();

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		if (log.contains("MixinTransformerError") || log.contains("InvalidMixinException")
				|| log.contains("MixinApplyError") || log.contains("Critical injection failure")
				|| log.contains("MixinInitialisationError")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null)
			return;

		boolean lineaEsErrorFatal = false;

		// Errores fatales conocidos
		if (linea.contains("MixinTransformerError") || linea.contains("InvalidMixinException")
				|| linea.contains("MixinApplyError") || linea.contains("Critical injection failure")
				|| linea.contains("MixinInitialisationError")) {

			lineaEsErrorFatal = true;
		}

		// Caso clásico: "... from mod X"
		if (lineaEsErrorFatal && linea.contains("from mod ")) {

			int indice = linea.indexOf("from mod ");
			if (indice == -1)
				return;

			String candidato = linea.substring(indice + "from mod ".length()).trim();

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
		if (linea.contains("MixinInitialisationError") && linea.contains(".mixins.json")) {

			String enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Extraer nombre del config mixin
			int inicio = linea.indexOf("config ");
			if (inicio != -1) {

				String restante = linea.substring(inicio + "config ".length()).trim();

				int fin = restante.indexOf(' ');
				if (fin == -1)
					fin = restante.length();

				String mixinConfig = restante.substring(0, fin).trim();

				// Obtener modId aproximado (antes de ".mixins.json")
				int punto = mixinConfig.indexOf(".mixins.json");
				String nombreMod = punto > 0 ? mixinConfig.substring(0, punto) : mixinConfig;

				if (!modsConEnlace.containsKey(nombreMod)) {
					modsConEnlace.put(nombreMod, enlace);
					this.activado = true;
				}
			}
		}
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}