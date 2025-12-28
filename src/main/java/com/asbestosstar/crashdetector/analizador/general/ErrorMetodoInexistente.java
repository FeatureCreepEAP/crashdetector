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

				// Obtener la siguiente línea significativa (la primera que empieza con "at ")
				String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);
				this.lineaSiguiente = "";
				for (int i = numLinea + 1; i < lineas.length; i++) {
					String sig = lineas[i].trim();
					if (sig.startsWith("at ")) {
						this.lineaSiguiente = sig;
						break;
					}
				}

				// 👇 Resetear todas las banderas antes de analizar
				this.create = false;
				this.epicfight = false;
				this.azurelib = false;
				this.minecraft = false;
				this.dangerzone = false;
				this.featurecreep = false;
				this.modlauncher = false;
				this.minecraftforge = false;
				this.neoforged = false;
				this.fabricloader = false;
				this.pillowmc = false;

				// 👇 Analizar la línea del error (la que contiene el mensaje) Y la siguiente
				// línea (stacktrace)
				String targetLine = !lineaSiguiente.isEmpty() ? lineaSiguiente : linea;

				// Primero, analizar la línea del error (por ejemplo, "The mod attempted to
				// call...") para encontrar "net.minecraftforge"
				if (linea.toLowerCase().contains("net.minecraftforge")
						|| linea.toLowerCase().contains("minecraftforge")) {
					minecraftforge = true;
				}

				// Luego, analizar la línea del stacktrace (si existe)
				if (!lineaSiguiente.isEmpty()) {
					if (targetLine.contains("com/simibubi/create") || targetLine.contains("com.simibubi.create")) {
						create = true;
					} else if (targetLine.contains("yesman/epicfight") || targetLine.contains("yesman.epicfight")) {
						epicfight = true;
					} else if (targetLine.contains("mod/azure/azurelib") || targetLine.contains("mod.azure.azurelib")) {
						azurelib = true;
					} else if (targetLine.contains("asbestosstar/") || targetLine.contains("asbestosstar.")) {
						featurecreep = true;
					} else if (targetLine.contains("dangerzone/") || targetLine.contains("dangerzone.")) {
						dangerzone = true;
					} else if (targetLine.contains("net/fabricmc/") || targetLine.contains("net.fabricmc.")) {
						fabricloader = true;
					} else if (targetLine.contains("net/neoforged/") || targetLine.contains("net.neoforged.")) {
						neoforged = true;
					} else if (targetLine.contains("net/pillowmc/") || targetLine.contains("net.pillowmc.")) {
						pillowmc = true;
					} else if (targetLine.contains("cpw/mods/modlauncher")
							|| targetLine.contains("cpw.mods.modlauncher")) {
						modlauncher = true;
					} else if (targetLine.contains("net/minecraftforge") || targetLine.contains("net.minecraftforge")) {
						minecraftforge = true;
					} else if ((targetLine.contains("net/minecraft/") || targetLine.contains("net.minecraft."))
							&& !targetLine.contains("net/minecraftforge/")
							&& !targetLine.contains("net.minecraftforge.")) {
						minecraft = true;
					}
				}

				this.enlaceHtml = consola.agregarErrorALectador(numLinea, this);
				this.activado = true;

				// Construir mensaje
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

				// Agregar mensajes específicos solo si se detectaron
				if (create) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_create());
				}
				if (epicfight) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_epicfight());
				}
				if (azurelib) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_azurelib());
				}
				if (featurecreep) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_featurecreep());
				}
				if (dangerzone) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_dangerzone());
				}
				if (modlauncher) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_modlauncher());
				}
				if (minecraftforge) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_minecraftforge());
				}
				if (neoforged) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_neoforged());
				}
				if (fabricloader) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_fabricloader());
				}
				if (pillowmc) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_pillowmc());
				}
				// Solo mostrar "minecraft" si realmente es un error interno de Minecraft (muy
				// raro)
				if (minecraft && !create && !epicfight && !azurelib && !featurecreep && !dangerzone && !minecraftforge
						&& !neoforged && !fabricloader && !pillowmc) {
					sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_minecraft());
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