package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RegistrosMalMapeados implements Verificaciones {

	private boolean activado = false;
	private final Set<String> entradas = new HashSet<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Patrón 1: "Found a missing id from the world kubejs:note_1"
		Pattern patternFaltanIDs = Pattern.compile("Found a missing id from the world ([\\w\\d_:-]+:[\\w\\d_:-]+)");

		// Patrón 2: "Unidentified mapping from registry minecraft:itemkubejs:100k_card:
		// 35229"
		Pattern patternMapeoNoIdentificado = Pattern
				.compile("Unidentified mapping from registry [^:]+:([\\w\\d_:-]+:[\\w\\d_:-]+)");

		// Patrón 3: Bloques densos como "kubejs:ender_pearl_block_1x:
		// 24927kubejs:ender_pearl_block_2x: 24928"
		Pattern patternBloqueDensa = Pattern.compile("([\\w\\d_:-]+:[\\w\\d_:-]+)(?=:\\s*\\d+|$)");

		Matcher matcherFaltanIDs = patternFaltanIDs.matcher(contenidoConsola);
		Matcher matcherMapeoNoIdentificado = patternMapeoNoIdentificado.matcher(contenidoConsola);

		// Extraer desde "Found a missing id" lines
		while (matcherFaltanIDs.find()) {
			String entrada = matcherFaltanIDs.group(1).trim();
			if (entrada.contains(":")) {
				entradas.add(entrada);
				activado = true;
			}
		}

		// Extraer desde "Unidentified mapping from registry" lines
		while (matcherMapeoNoIdentificado.find()) {
			String entrada = matcherMapeoNoIdentificado.group(1).trim();
			if (entrada.contains(":")) {
				entradas.add(entrada);
				activado = true;
			}
		}

		// Extraer desde bloques densos (varias entradas en una sola línea)
		Pattern blockPattern = Pattern.compile("Unidentified mapping from registry [^:]+:([^$$]+)");
		Matcher blockMatcher = blockPattern.matcher(contenidoConsola);

		while (blockMatcher.find()) {
			String blockContent = blockMatcher.group(1);
			Matcher entryMatcher = patternBloqueDensa.matcher(blockContent);

			while (entryMatcher.find()) {
				String entrada = entryMatcher.group(1).trim();
				if (entrada.contains(":")) {
					entradas.add(entrada);
					activado = true;
				}
			}
		}

		// Eliminar cualquier entrada que contenga timestamps o datos inválidos
		entradas.removeIf(entrada -> entrada.matches(".*\\d{2}:\\d{2}:\\d{2}.*"));
	}

	@Override
	public Verificaciones nueva() {
		return new RegistrosMalMapeados();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Alta prioridad
	}

	@Override
	public String mensaje() {
		if (entradas.isEmpty())
			return "";

		StringBuilder html = new StringBuilder();
		html.append(

				"<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
						+ MonitorDePID.idioma.solucionRegistrosMalMapeados() + "</b> "

		);
		html.append("<ul>");
		for (String entrada : entradas) {
			html.append("<li>").append(entrada).append("</li>");
		}
		html.append("</ul>");

		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_registros_mal_mapeados();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionRegistrosMalMapeados())
				.construir();
	}

	@Override
	public boolean anularNormal() {
		return true;// Normalment el juego salir normalmente, pero esta problema generalment es solo
					// cuando conectarse a un mundo o servidor
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "registros_mal_mapeados";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}