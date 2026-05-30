package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores NoSuchMethodError que ocurren cuando un mod intenta llamar a
 * un metodo que ya no existe en la version actual del juego o de otro mod.
 */
public class ErrorMetodoInexistente implements Verificaciones {

	private static final String PREFIJO_ERROR = "java.lang.NoSuchMethodError:";

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String firmaMetodo = "";
	private String lineaSiguiente = "";

	/**
	 * Chequeo global barato.
	 */
	private boolean posibleNoSuchMethodError = false;

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
		String contenido = consola.contenido_verificar;

		if (contenido == null) {
			posibleNoSuchMethodError = false;
			return;
		}

		posibleNoSuchMethodError = contenido.contains(PREFIJO_ERROR);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numLinea) {
		if (activado || !posibleNoSuchMethodError || linea == null) {
			return;
		}

		if (!linea.contains(PREFIJO_ERROR)) {
			return;
		}

		this.firmaMetodo = extraerFirmaMetodo(linea);
		if (firmaMetodo == null || firmaMetodo.isEmpty()) {
			return;
		}

		// Obtener la siguiente línea significativa
		String[] lineas = consola.lineas_verificar;

		this.lineaSiguiente = "";

		for (int i = numLinea + 1; i < lineas.length; i++) {
			String sig = lineas[i].trim();

			if (sig.startsWith("at ")) {
				this.lineaSiguiente = sig;
				break;
			}
		}

		// Resetear banderas
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

		String targetLine = !lineaSiguiente.isEmpty() ? lineaSiguiente : linea;

		String lowerError = linea.toLowerCase();

		if (lowerError.contains("net.minecraftforge") || lowerError.contains("minecraftforge")) {
			minecraftforge = true;
		}

		if (!lineaSiguiente.isEmpty()) {

			if (contiene(targetLine, "com/simibubi/create", "com.simibubi.create")) {
				create = true;

			} else if (contiene(targetLine, "yesman/epicfight", "yesman.epicfight")) {
				epicfight = true;

			} else if (contiene(targetLine, "mod/azure/azurelib", "mod.azure.azurelib")) {
				azurelib = true;

			} else if (contiene(targetLine, "asbestosstar/", "asbestosstar.")) {
				featurecreep = true;

			} else if (contiene(targetLine, "dangerzone/", "dangerzone.")) {
				dangerzone = true;

			} else if (contiene(targetLine, "net/fabricmc/", "net.fabricmc.")) {
				fabricloader = true;

			} else if (contiene(targetLine, "net/neoforged/", "net.neoforged.")) {
				neoforged = true;

			} else if (contiene(targetLine, "net/pillowmc/", "net.pillowmc.")) {
				pillowmc = true;

			} else if (contiene(targetLine, "cpw/mods/modlauncher", "cpw.mods.modlauncher")) {
				modlauncher = true;

			} else if (contiene(targetLine, "net/minecraftforge", "net.minecraftforge")) {
				minecraftforge = true;

			} else if ((targetLine.contains("net/minecraft/") || targetLine.contains("net.minecraft."))
					&& !targetLine.contains("net/minecraftforge/") && !targetLine.contains("net.minecraftforge.")) {

				minecraft = true;
			}
		}

		this.enlaceHtml = consola.agregarErrorALectador(numLinea, this);
		this.activado = true;

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

		if (minecraft && !create && !epicfight && !azurelib && !featurecreep && !dangerzone && !minecraftforge
				&& !neoforged && !fabricloader && !pillowmc) {

			sb.append(nl_html).append(MonitorDePID.idioma.faltar_de_clases_minecraft());
		}

		this.mensaje = sb.toString();
	}

	/**
	 * Extrae la firma del método sin regex.
	 */
	private static String extraerFirmaMetodo(String linea) {
		int inicio = linea.indexOf(PREFIJO_ERROR);

		if (inicio < 0) {
			return null;
		}

		inicio += PREFIJO_ERROR.length();

		if (inicio >= linea.length()) {
			return null;
		}

		return linea.substring(inicio).trim();
	}

	private static boolean contiene(String texto, String a, String b) {
		return texto.contains(a) || texto.contains(b);
	}

	private String escapeHtml(String s) {
		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
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
		return trazo != null && trazo.trace != null && trazo.trace.contains("NoSuchMethodError");
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/general/" + this.getClass().getSimpleName()
				+ ".java";
	}
}