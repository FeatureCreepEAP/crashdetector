package com.asbestosstar.crashdetector.analizador.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores NoSuchMethodError que ocurren cuando un mod intenta llamar a
 * un metodo que ya no existe en la version actual del juego o de otro mod.
 */
public class ErrorMetodoInexistente implements Verificaciones {

	private static final Pattern PATRON = Pattern.compile("java\\.lang\\.NoSuchMethodError:\\s*(.+)$");

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String firmaMetodo = "";
	private String lineaSiguiente = "";

	// Flags para detectar mods específicos
	private boolean create = false;
	private boolean epicfight = false;
	private boolean azurelib = false;
	private boolean minecraft = false;
	private boolean dangerzone = false;
	private boolean featurecreep = false;
	private boolean modlauncher = false;
	private boolean minecraftforge = false;
	private boolean neoforged = false;
	private boolean fabricloader = false;
	private boolean pillowmc = false;

	@Override
	public void verificar(Consola consola, String linea, int numLinea) {
		if (activado)
			return;

		if (linea.contains("java.lang.NoSuchMethodError:")) {
			Matcher m = PATRON.matcher(linea);
			if (m.find()) {
				this.firmaMetodo = m.group(1).trim();

				// Obtener la siguiente linea si empieza con "at "
				String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);
				if (numLinea + 1 < lineas.length) {
					String sig = lineas[numLinea + 1].trim();
					if (sig.startsWith("at ")) {
						this.lineaSiguiente = sig;
					}
				}

				// Detectar mods específicos en el stacktrace
				for (int i = Math.max(0, numLinea - 10); i < Math.min(lineas.length, numLinea + 10); i++) {
					String l = lineas[i];
					// Check for both / and . versions of the package names
					if (l.contains("com/simibubi/create") || l.contains("com.simibubi.create")) {
						create = true;
					} else if (l.contains("yesman/epicfight") || l.contains("yesman.epicfight")) {
						epicfight = true;
					} else if (l.contains("mod/azure/azurelib") || l.contains("mod.azure.azurelib")) {
						azurelib = true;
					} else if (l.contains("net/minecraftforge") || l.contains("net.minecraftforge")) {
						minecraftforge = true;
					} else if (l.contains("featurecreep/") || l.contains("featurecreep.") || l.contains("asbestosstar/")
							|| l.contains("asbestosstar.")) {
						featurecreep = true;
					} else if (l.contains("net/fabricmc/") || l.contains("net.fabricmc.")) {
						fabricloader = true;
					} else if (l.contains("net/neoforged/") || l.contains("net.neoforged.")) {
						neoforged = true;
					} else if (l.contains("net/pillowmc/") || l.contains("net.pillowmc.")) {
						pillowmc = true;
					} else if ((l.contains("net/minecraft/") || l.contains("net.minecraft."))
							&& !l.contains("net/minecraftforge/") && !l.contains("net.minecraftforge.")) {
						minecraft = true;
					} else if (l.contains("dangerzone/") || l.contains("dangerzone.")) {
						dangerzone = true;
					}
				}

				this.enlaceHtml = consola.agregarErrorALectador(numLinea, this);
				this.activado = true;

				// Construir el mensaje con las detecciones de mods
				StringBuilder sb = new StringBuilder();
				sb.append(MonitorDePID.idioma.errorMetodoInexistente(firmaMetodo, firmaMetodo));
				if (!lineaSiguiente.isEmpty()) {
					sb.append(Verificaciones.nl_html);
					sb.append("<span style='color:#888888; font-family:monospace;'>");
					sb.append(escapeHtml(lineaSiguiente));
					sb.append("</span>");
				}
				sb.append(Verificaciones.nl_html);
				sb.append(enlaceHtml);

				// Agregar mensajes específicos para mods detectados
				if (create) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_create());
				}
				if (epicfight) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_epicfight());
				}
				if (azurelib) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_azurelib());
				}
				if (minecraft) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_minecraft());
				}
				if (dangerzone) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_dangerzone());
				}
				if (featurecreep) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_featurecreep());
				}
				if (modlauncher) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_modlauncher());
				}
				if (minecraftforge) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_minecraftforge());
				}
				if (neoforged) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_neoforged());
				}
				if (fabricloader) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_fabricloader());
				}
				if (pillowmc) {
					sb.append(MonitorDePID.idioma.faltar_de_clases_pillowmc());
				}

				this.mensaje = sb.toString();
			}
		}
	}

	private String escapeHtml(String s) {
		return s.replace("&", "&amp;").replace("<", "<").replace(">", ">").replace("\"", "&quot;");
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMetodoInexistente();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1100.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_metodo_inexistente();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_metodo_inexistente())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_metodo_inexistente()).construir();
	}

	@Override
	public String id() {
		return "error_metodo_inexistente";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo.trace.contains("NoSuchMethodError");
	}

	@Override
	public void verificar(Consola consola) {
		// No se usa; el sistema llama a verificar(Consola, String, int)
	}
}