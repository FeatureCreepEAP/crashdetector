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
	private boolean viErrorMixin = false;
	private boolean analizarLineas = false;

	// mod -> enlace
	private final Map<String, String> modsConEnlace = new LinkedHashMap<>();

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global rápido (solo contains)
		if (log.contains("MixinTransformerError: An unexpected critical error was encountered")
				&& log.contains("from mod ")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null)
			return;

		boolean linea_vi_error_mixin = false;
		// Esperar explícitamente la línea del error crítico
		if (linea.contains("MixinTransformerError") || linea.contains("InvalidMixinException")
				|| linea.contains("MixinApplyError") || linea.contains("Critical injection failure")) {
			linea_vi_error_mixin = true;
			this.viErrorMixin = true;
			return;
		}

		// Solo después de haber detectado el error fatal
		if (linea_vi_error_mixin && linea.contains("from mod ")) {

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

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}