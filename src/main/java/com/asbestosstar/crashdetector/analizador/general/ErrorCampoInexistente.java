package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores NoSuchFieldError que ocurren cuando un mod intenta acceder a
 * un campo que ya no existe en la versión actual del juego u otro mod.
 */
public class ErrorCampoInexistente implements com.asbestosstar.crashdetector.analizador.Verificaciones {

	private boolean activado = false;

	private String mensaje = "";
	private String enlaceHtml = "";

	private String nombreCampoDetectado = "";
	private String lineaError = "";
	private String lineaStack = "";

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
	public String[] patronesRapidos() {
		return new String[] { "java.lang.NoSuchFieldError:" };
	}

	@Override
	public void verificarCoincidencia(com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia evento) {
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || activado) {
			return;
		}

		if (linea.contains("java.lang.NoSuchFieldError:")) {
			this.lineaError = linea.trim();
			this.nombreCampoDetectado = extraerCampoNoSuchField(linea);
			return;
		}

		if (this.nombreCampoDetectado == null || this.nombreCampoDetectado.isEmpty()) {
			return;
		}
		this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		resetearBanderasMods();

		this.lineaStack = encontrarLineaStackCulpable(consola.lineas_verificar, numero_de_linea);
		detectarModDesdeStack(this.lineaStack);

		this.activado = true;
	}

	private String encontrarLineaStackCulpable(String[] lineas, int numeroDeLineaError) {
		if (lineas == null || lineas.length == 0) {
			return "";
		}

		// numero_de_linea normalmente viene en base 1 desde el lector.
		// La línea siguiente en el array es el mismo índice.
		int inicio = numeroDeLineaError;

		if (inicio < 0) {
			inicio = 0;
		}

		if (inicio >= lineas.length) {
			return "";
		}

		String primeraLineaStack = "";
		String mejorLinea = "";

		int max = inicio + 80;
		if (max > lineas.length) {
			max = lineas.length;
		}

		for (int i = inicio; i < max; i++) {
			String linea = lineas[i];

			if (linea == null) {
				continue;
			}

			String s = linea.trim();

			if (s.startsWith("Caused by:") && s.indexOf("java.lang.NoSuchFieldError:") < 0) {
				break;
			}

			if (!s.startsWith("at ")) {
				continue;
			}

			if (primeraLineaStack.isEmpty()) {
				primeraLineaStack = s;
			}

			if (esLineaFramework(s)) {
				continue;
			}

			mejorLinea = s;

			if (esLineaModMuyProbable(s)) {
				return mejorLinea;
			}
		}

		if (!mejorLinea.isEmpty()) {
			return mejorLinea;
		}

		return primeraLineaStack;
	}

	private boolean esLineaModMuyProbable(String s) {
		if (s == null || s.isEmpty()) {
			return false;
		}

		if (s.indexOf("~[") >= 0 && s.indexOf(".jar") >= 0 && s.indexOf("client-intermediary.jar") < 0
				&& s.indexOf("server-intermediary.jar") < 0 && s.indexOf("fabric-loader") < 0
				&& s.indexOf("fabric-api") < 0 && s.indexOf("lwjgl") < 0) {
			return true;
		}

		return s.indexOf("io.github.") >= 0 || s.indexOf("com.github.") >= 0 || s.indexOf("curse.maven.") >= 0
				|| s.indexOf("com.simibubi.") >= 0 || s.indexOf("yesman.epicfight") >= 0 || s.indexOf("mod.azure.") >= 0
				|| s.indexOf("asbestosstar.") >= 0 || s.indexOf("dangerzone.") >= 0 || s.indexOf("vectorwing.") >= 0;
	}

	private boolean esLineaFramework(String s) {
		return s.indexOf("net.fabricmc.") >= 0 || s.indexOf("net/fabricmc/") >= 0 || s.indexOf("net.minecraft.") >= 0
				|| s.indexOf("net/minecraft/") >= 0 || s.indexOf("com.mojang.") >= 0 || s.indexOf("com/mojang/") >= 0
				|| s.indexOf("org.lwjgl.") >= 0 || s.indexOf("org/lwjgl/") >= 0 || s.indexOf("java.") >= 0
				|| s.indexOf("javax.") >= 0 || s.indexOf("jdk.") >= 0 || s.indexOf("sun.") >= 0
				|| s.indexOf("cpw.mods.modlauncher") >= 0 || s.indexOf("cpw/mods/modlauncher") >= 0
				|| s.indexOf("net.minecraftforge") >= 0 || s.indexOf("net/minecraftforge") >= 0
				|| s.indexOf("net.neoforged") >= 0 || s.indexOf("net/neoforged") >= 0
				|| s.indexOf("org.prismlauncher.") >= 0 || s.indexOf("org/prismlauncher/") >= 0;
	}

	private void resetearBanderasMods() {
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
	}

	private void detectarModDesdeStack(String target) {
		if (target == null || target.isEmpty()) {
			return;
		}

		if (target.indexOf("com/simibubi/create") >= 0 || target.indexOf("com.simibubi.create") >= 0) {
			create = true;
		} else if (target.indexOf("yesman/epicfight") >= 0 || target.indexOf("yesman.epicfight") >= 0) {
			epicfight = true;
		} else if (target.indexOf("mod/azure/azurelib") >= 0 || target.indexOf("mod.azure.azurelib") >= 0) {
			azurelib = true;
		} else if (target.indexOf("asbestosstar/") >= 0 || target.indexOf("asbestosstar.") >= 0) {
			featurecreep = true;
		} else if (target.indexOf("dangerzone/") >= 0 || target.indexOf("dangerzone.") >= 0) {
			dangerzone = true;
		} else if (target.indexOf("net/fabricmc/") >= 0 || target.indexOf("net.fabricmc.") >= 0) {
			fabricloader = true;
		} else if (target.indexOf("net/neoforged/") >= 0 || target.indexOf("net.neoforged.") >= 0) {
			neoforged = true;
		} else if (target.indexOf("net/pillowmc/") >= 0 || target.indexOf("net.pillowmc.") >= 0) {
			pillowmc = true;
		} else if (target.indexOf("cpw/mods/modlauncher") >= 0 || target.indexOf("cpw.mods.modlauncher") >= 0) {
			modlauncher = true;
		} else if (target.indexOf("net/minecraftforge") >= 0 || target.indexOf("net.minecraftforge") >= 0) {
			minecraftforge = true;
		} else if ((target.indexOf("net/minecraft/") >= 0 || target.indexOf("net.minecraft.") >= 0)
				&& target.indexOf("net/minecraftforge/") < 0 && target.indexOf("net.minecraftforge.") < 0) {
			minecraft = true;
		}
	}

	private String extraerCampoNoSuchField(String linea) {
		String base = "java.lang.NoSuchFieldError:";
		int idx = linea.indexOf(base);

		if (idx < 0) {
			return "";
		}

		String resto = linea.substring(idx + base.length()).trim();

		int primeraComilla = resto.indexOf('\'');
		if (primeraComilla >= 0) {
			int segundaComilla = resto.indexOf('\'', primeraComilla + 1);
			if (segundaComilla > primeraComilla) {
				String crudo = resto.substring(primeraComilla + 1, segundaComilla).trim();
				int espacio = crudo.lastIndexOf(' ');
				return espacio >= 0 ? crudo.substring(espacio + 1).trim() : crudo;
			}
		}

		int fin = 0;
		while (fin < resto.length()) {
			char c = resto.charAt(fin);
			if (!Character.isLetterOrDigit(c) && c != '_' && c != '$') {
				break;
			}
			fin++;
		}

		return fin > 0 ? resto.substring(0, fin) : "";
	}

	private String escapeHtml(String s) {
		if (s == null) {
			return "";
		}
		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCampoInexistente();
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
		if (!activado) {
			return "";
		}

		if (mensaje != null && !mensaje.isEmpty()) {
			return mensaje;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.errorCampoInexistente(nombreCampoDetectado, lineaError));

		if (!lineaStack.isEmpty()) {
			sb.append(Verificaciones.nl_html);
			sb.append("<span style='color:#888888; font-family:monospace;'>");
			sb.append(escapeHtml(lineaStack));
			sb.append("</span>");
		}

		sb.append(Verificaciones.nl_html);
		sb.append(enlaceHtml);

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
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_campo_inexistente();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_campo_inexistente())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_campo_inexistente()).construir();
	}

	@Override
	public String id() {
		return "error_campo_inexistente";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.indexOf("NoSuchFieldError") >= 0;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}