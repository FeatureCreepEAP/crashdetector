package com.asbestosstar.crashdetector.analizador.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta cuando el usuario NO está usando un launcher de la lista recomendada.
 * Este verificador ignora completamente el archivo de launchers desaconsejados.
 */
public class LanzerNoAnimado implements Verificaciones {

	public static final Path ARCHIVO_ANIMADOS = Statics.carpeta.resolve("lanzeres_animados.json");

	private boolean activado = false;
	private String mensaje = "";
	boolean completa = false;

	@Override
	public void verificar(Consola consola) {
		if (completa) {
			return;
		}
		this.completa = true;

		String LANZADOR_ACTUAL = Statics.lanzer_del_app;
		if (LANZADOR_ACTUAL == null) {
			return;
		}

		LANZADOR_ACTUAL = LANZADOR_ACTUAL.trim();
		if (LANZADOR_ACTUAL.isEmpty()) {
			return;
		}

		CrashDetectorLogger.log("Lanzer Actual " + LANZADOR_ACTUAL);

		// Si el archivo de launchers animados no existe, no hacer nada
		if (!ARCHIVO_ANIMADOS.toFile().exists()) {
			return;
		}

		String contenido;
		try {
			contenido = new String(Files.readAllBytes(ARCHIVO_ANIMADOS), java.nio.charset.StandardCharsets.UTF_8);
		} catch (IOException e) {
			return;
		}

		if (contenido == null || contenido.trim().isEmpty()) {
			return;
		}

		// Parsear lista de launchers recomendados
		Nodo raiz;
		try {
			raiz = Json.leer(contenido);
		} catch (Exception e) {
			return;
		}

		if (raiz == null || !raiz.esArreglo()) {
			return;
		}

		// Usar LinkedHashSet para conservar el orden original del JSON
		Set<String> launchersAnimados = new java.util.LinkedHashSet<>();
		int tam = raiz.tamano();

		for (int i = 0; i < tam; i++) {
			Nodo item = raiz.en(i);
			if (item != null && !item.esObjeto() && !item.esArreglo()) {
				String id = item.comoCadena();
				if (id != null) {
					id = id.trim();
					if (!id.isEmpty()) {
						launchersAnimados.add(id);
					}
				}
			}
		}

		if (launchersAnimados.isEmpty()) {
			return;
		}

		// Si el launcher actual NO está en la lista de recomendados, advertir
		if (!launchersAnimados.contains(LANZADOR_ACTUAL)) {
			StringBuilder sb = new StringBuilder();
			sb.append(MonitorDePID.idioma.lanzer_no_animado_titulo(LANZADOR_ACTUAL));
			sb.append(nl_html).append(MonitorDePID.idioma.lanzer_no_animado_problemas_comunes());
			sb.append(nl_html).append(MonitorDePID.idioma.lanzer_no_animado_usar_animados());
			sb.append(" ");

			boolean primero = true;
			for (String id : launchersAnimados) {
				if (!primero) {
					sb.append(", ");
				}
				sb.append("<code>").append(id).append("</code>");
				primero = false;
			}

			this.mensaje = sb.toString();
			this.activado = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {

		return false;
	}

	@Override
	public Verificaciones nueva() {
		return new LanzerNoAnimado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_lanzer_no_animado();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.lanzer_no_animado_cambiar_a_animado());
		return builder.construir();
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "lanzer_no_animado";
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}