package com.asbestosstar.crashdetector.analizador.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores NoSuchFieldError que ocurren cuando un mod intenta acceder a
 * un campo que ya no existe en la versión actual del juego u otro mod. Soporta
 * formatos: - "java.lang.NoSuchFieldError: DEAD_PLANKS" -
 * "java.lang.NoSuchFieldError: Class X does not have member field
 * 'paquete.Clase$Tipo nombreCampo'"
 */
public class ErrorCampoInexistente implements Verificaciones {

	/**
	 * Bandera para indicar si ya se detectó este problema en el log.
	 */
	private boolean activado = false;

	/**
	 * Bandera global ligera: solo indica si el log contiene alguna referencia a
	 * {@code java.lang.NoSuchFieldError}. Sirve como filtro rápido para el análisis
	 * línea a línea.
	 */
	private boolean posibleNoSuchField = false;

	/**
	 * Bandera interna para indicar que ya se encontró la línea del error y se está
	 * esperando la primera línea de stack que comience por {@code "at "}.
	 */
	private boolean esperandoLineaStack = false;

	private String mensaje = "";
	private String enlaceHtml = "";

	// Datos que se extraen durante el análisis y se usan al construir el mensaje
	private String nombreCampoDetectado = "";
	private String lineaError = "";
	private String lineaStack = "";

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
	public void verificar(Consola consola) {
		// Análisis global muy ligero: solo comprobamos si aparece el texto base
		// de NoSuchFieldError en algún punto del log.
		// Si no aparece, la verificación por línea saldrá inmediatamente y evitamos
		// trabajo innecesario.
		String contenido = consola.contenido_verificar;
		this.posibleNoSuchField = contenido != null && contenido.contains("java.lang.NoSuchFieldError:");
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || !posibleNoSuchField) {
			return;
		}

		if (activado && !esperandoLineaStack) {
			return;
		}

		String l = linea;

		if (!activado) {
			if (l.indexOf("java.lang.NoSuchFieldError:") < 0) {
				return;
			}

			String nombreCampo = extraerCampoNoSuchField(l);

			if (nombreCampo == null || nombreCampo.isEmpty()) {
				return;
			}

			this.nombreCampoDetectado = nombreCampo;
			this.lineaError = l.trim();
			this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			resetearBanderasMods();

			this.activado = true;
			this.esperandoLineaStack = true;
			return;
		}

		if (esperandoLineaStack) {
			String s = l.trim();

			if (s.startsWith("at ")) {
				this.lineaStack = s;
				detectarModDesdeStack(s);
				this.esperandoLineaStack = false;
			}
		}
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

	// Utilidad para escapar HTML básico
	private String escapeHtml(String s) {
		if (s == null)
			return "";
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

		// Si ya construimos el mensaje previamente, devolverlo tal cual
		if (mensaje != null && !mensaje.isEmpty()) {
			return mensaje;
		}

		// Construir mensaje: primero el mensaje localizado, luego un bloque
		// monoespaciado con contexto y enlace
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

	/**
	 * Marca trazos que pertenezcan a este problema.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se ha activado, y</li>
	 * <li>El trazo contiene claramente "NoSuchFieldError".</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a asociar un
	 * trazo que no corresponda realmente a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		return trazo.trace.contains("NoSuchFieldError");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

}