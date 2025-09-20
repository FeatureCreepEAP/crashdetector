package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class MCForgeModsSuspechoso implements Verificaciones {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final Map<String, String> enlacesPorError = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Patrones auxiliares para extraer modid en bloques cercanos
		// Disparo de bloque: línea cabecera del diagnóstico
		Pattern pEncontradoDurante = Pattern.compile(
		    "(?i)(?:encountered\\s+an\\s+(?:error|exception)|caught\\s+exception)\\s+during\\s+[^\\r\\n]*"
		);
		Pattern pForModidInline = Pattern.compile("(?i)for\\s+modid\\s+([a-z0-9_\\-.]+)");
		Pattern pModIdColon = Pattern.compile("(?i)\\bMod\\s*ID\\s*:\\s*([a-z0-9_\\-.]+)");
		Pattern pModidColonEq = Pattern.compile("(?i)\\bmodid\\s*[:=]\\s*([a-z0-9_\\-.]+)");
		Pattern pAffectedMod = Pattern.compile("(?i)\\bAffected\\s+mod\\s*:\\s*([a-z0-9_\\-.]+)");
		Pattern pModidEq = Pattern.compile("(?i)\\bmodid=([a-z0-9_\\-.]+)");

		// Patrón unificado: acepta "encountered an error/exception" o "caught exception"
		Pattern patronDespachoModid = Pattern.compile(
		    "(?i)(?:encountered\\s+an\\s+(?:error|exception)|caught\\s+exception)\\s+during\\s+.*?dispatch\\s+for\\s+modid\\s+([a-z0-9_\\-.]+)"
		);

		
		
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];

			// 1) Bloque "Suspected Mod:"
			if (linea.contains("Suspected Mod:")) {
				if (i + 1 < lineas.length) {
					String modLinea = lineas[i + 1].trim();
					int inicio = modLinea.indexOf('(');
					int fin = modLinea.indexOf(')');
					String modID = modLinea;
					if (inicio != -1 && fin != -1 && inicio < fin) {
						modID = modLinea.substring(inicio + 1, fin).trim();
					}
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID.trim();
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i + 1, this);
						enlacesPorError.put(mensaje, enlace);
					}
					activado = true;
				}
				continue;
			}

			// 2) Línea "Failed to create mod instance. ModID: <id>"
			if (linea.contains("Failed to create mod instance. ModID:")) {
				try {
					String prefijo = "Failed to create mod instance. ModID: ";
					int indiceInicio = linea.indexOf(prefijo);
					if (indiceInicio != -1) {
						indiceInicio += prefijo.length();
						String resto = linea.substring(indiceInicio).trim();
						StringBuilder sb = new StringBuilder();
						int j = 0;
						while (j < resto.length()) {
							char c = resto.charAt(j);
							if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == '+') {
								sb.append(c);
								j++;
							} else {
								break;
							}
						}
						String modID = sb.toString().trim();
						if (!modID.isEmpty()) {
							String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
							if (errores.add(mensaje)) {
								String enlace = consola.agregarErrorALectador(i, this);
								enlacesPorError.put(mensaje, enlace);
							}
							activado = true;
						}
					}
				} catch (Exception e) {
					consola.agregarErrorALectador(i, this);
				}
				continue;
			}

			// 3) Línea "Failure message: (...)"
			if (linea.contains("Failure message:")) {
				int inicio = linea.indexOf('(');
				if (inicio != -1) {
					int fin = linea.indexOf(')', inicio + 1);
					if (fin != -1) {
						String modID = linea.substring(inicio + 1, fin).trim();
						String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
						if (errores.add(mensaje)) {
							String enlace = consola.agregarErrorALectador(i, this);
							enlacesPorError.put(mensaje, enlace);
						}
						activado = true;
					}
				}
				// no continuar; puede coincidir también con el caso 4 más abajo
			}

			// 4) Detección directa “... dispatch for modid <id>”
			Matcher matcherDespacho = patronDespachoModid.matcher(linea);
			while (matcherDespacho.find()) {
			    String modID = matcherDespacho.group(1).trim();
			    String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
			    if (errores.add(mensaje)) {
			        String enlace = consola.agregarErrorALectador(i, this);
			        enlacesPorError.put(mensaje, enlace);
			    }
			    activado = true;
			}


			// 5) Detección robusta por bloque: “Encountered an error/exception during ...”
			Matcher mEnc = pEncontradoDurante.matcher(linea);
			if (mEnc.find()) {
				String modID = null;

				// intento inline “for modid <id>”
				Matcher mInline = pForModidInline.matcher(linea);
				if (mInline.find()) {
					modID = mInline.group(1).trim();
				}

				// si no está inline, buscar en un bloque siguiente (ventana limitada)
				if (modID == null) {
					int ventana = Math.min(lineas.length, i + 25);
					for (int k = i; k < ventana; k++) {
						String l2 = lineas[k];

						Matcher mId1 = pModIdColon.matcher(l2);
						if (mId1.find()) {
							modID = mId1.group(1).trim();
							i = k; // anclar el enlace donde se encontró el modid
							break;
						}
						Matcher mId2 = pModidColonEq.matcher(l2);
						if (mId2.find()) {
							modID = mId2.group(1).trim();
							i = k;
							break;
						}
						Matcher mId3 = pAffectedMod.matcher(l2);
						if (mId3.find()) {
							modID = mId3.group(1).trim();
							i = k;
							break;
						}
						Matcher mId4 = pModidEq.matcher(l2);
						if (mId4.find()) {
							modID = mId4.group(1).trim();
							i = k;
							break;
						}
					}
				}

				if (modID != null && !modID.isEmpty()) {
					String mensaje = MonitorDePID.idioma.mcforge_mod_sospechoso() + modID;
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
					}
					activado = true;
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new MCForgeModsSuspechoso();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f;
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String error : errores) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			html.append("<li>").append(error).append(" ").append(enlace).append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_mcforge_mod_sespechoso();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible()).construir();
	}
}
