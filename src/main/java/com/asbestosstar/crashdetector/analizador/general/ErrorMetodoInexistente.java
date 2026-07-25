package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores NoSuchMethodError.
 *
 * Este error ocurre cuando un mod intenta llamar a un método que no existe en
 * la versión instalada de Minecraft, del cargador de mods, de una biblioteca o
 * de otro mod.
 *
 * El analizador rápido detecta primero la línea NoSuchMethodError. Debido a que
 * consola.lineas_verificar todavía puede ser null durante esa coincidencia, se
 * guarda temporalmente el número de línea.
 *
 * Cuando termina el análisis del archivo, se consulta exclusivamente la línea
 * inmediatamente posterior:
 *
 * consola.lineas_verificar[numeroLineaError + 1]
 *
 * No se recorren todas las líneas del stack trace.
 */
public class ErrorMetodoInexistente implements Verificaciones {

	private static final String PREFIJO_ERROR = "java.lang.NoSuchMethodError:";

	private boolean activado = false;
	private boolean errorPendiente = false;

	private String mensaje = "";
	private String enlaceHtml = "";

	/**
	 * Firma completa del método inexistente.
	 *
	 * Ejemplo:
	 *
	 * 'void net.minecraft.world.entity.npc.VillagerType.registerBiomeType(...)'
	 */
	private String firmaMetodo = "";

	/**
	 * Nombre simple del método inexistente.
	 *
	 * Ejemplo:
	 *
	 * registerBiomeType
	 */
	private String nombreMetodoDetectado = "";

	/**
	 * Línea inmediatamente posterior al NoSuchMethodError.
	 *
	 * Ejemplo:
	 *
	 * at net.hibiscus.naturespirit.NatureSpirit.lambda$commonSetup$0(
	 * NatureSpirit.java:148)
	 */
	private String lineaSiguiente = "";

	/**
	 * Clase y método que realizaron la llamada.
	 *
	 * Ejemplo:
	 *
	 * net.hibiscus.naturespirit.NatureSpirit.lambda$commonSetup$0
	 */
	private String metodoLlamador = "";

	/**
	 * Consola en la que se detectó el error pendiente.
	 */
	private Consola consolaPendiente;

	/**
	 * Línea original que contiene NoSuchMethodError.
	 */
	private String lineaErrorPendiente = "";

	/**
	 * Índice base cero de la línea que contiene NoSuchMethodError.
	 */
	private int numeroLineaError = -1;

	// Banderas para generar recomendaciones específicas.
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

	/**
	 * El motor rápido solamente necesita buscar NoSuchMethodError.
	 *
	 * No se registra "at " como patrón porque la línea llamadora se obtiene
	 * directamente desde consola.lineas_verificar al finalizar el archivo.
	 */
	@Override
	public String[] patronesRapidos() {
		return new String[] { PREFIJO_ERROR };
	}

	/**
	 * Recibe una coincidencia del analizador rápido.
	 *
	 * En este momento consola.lineas_verificar puede continuar siendo null, por lo
	 * que solamente se guarda la información del error para procesarla después en
	 * finalizarArchivo().
	 */
	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null || activado || errorPendiente) {

			return;
		}

		if (!evento.linea.contains(PREFIJO_ERROR)) {
			return;
		}

		registrarErrorPendiente(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Mantiene compatibilidad con el análisis tradicional línea por línea.
	 *
	 * Si lineas_verificar ya existe, se puede obtener inmediatamente la línea
	 * posterior. Si todavía es null, el error queda pendiente para
	 * finalizarArchivo().
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numLinea) {

		if (consola == null || linea == null || activado || errorPendiente || !linea.contains(PREFIJO_ERROR)) {

			return;
		}

		registrarErrorPendiente(consola, linea, numLinea);

		if (consola.lineas_verificar != null && consola.lineas_verificar.length > 0) {

			procesarLineaInmediataPosterior(consola);
		}
	}

	/**
	 * Guarda la información básica del error mientras se espera a que
	 * consola.lineas_verificar esté disponible.
	 */
	private void registrarErrorPendiente(Consola consola, String linea, int numLinea) {

		this.firmaMetodo = extraerFirmaMetodo(linea);

		if (firmaMetodo == null || firmaMetodo.isEmpty()) {
			CrashDetectorLogger.log("[ErrorMetodoInexistente] " + "No se pudo extraer la firma: " + linea);

			return;
		}

		this.nombreMetodoDetectado = extraerMetodoNoSuchMethod(linea);

		this.consolaPendiente = consola;
		this.lineaErrorPendiente = linea;
		this.numeroLineaError = numLinea;

		this.lineaSiguiente = "";
		this.metodoLlamador = "";
		this.errorPendiente = true;

		CrashDetectorLogger.log("[ErrorMetodoInexistente] Procesando: " + linea);

		CrashDetectorLogger.log("[ErrorMetodoInexistente] Método inexistente: " + firmaMetodo);

		CrashDetectorLogger.log("[ErrorMetodoInexistente] Error guardado en índice: " + numeroLineaError);
	}

	/**
	 * Se ejecuta después de terminar de leer el archivo.
	 *
	 * Aquí consola.lineas_verificar ya debería estar disponible. Se toma
	 * exclusivamente la línea inmediata posterior al NoSuchMethodError.
	 */
	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {

		if (activado || !errorPendiente || consola == null || consolaPendiente == null || consola != consolaPendiente) {

			return;
		}

		procesarLineaInmediataPosterior(consola);
	}

	/**
	 * Obtiene exclusivamente:
	 *
	 * consola.lineas_verificar[numeroLineaError + 1]
	 *
	 * No busca otras líneas y no recorre todas las entradas "at ...".
	 */
	private void procesarLineaInmediataPosterior(Consola consola) {
		if (activado || !errorPendiente || consola == null) {
			return;
		}

		String[] lineas = consola.lineas_verificar;

		if (lineas == null || lineas.length == 0) {
			CrashDetectorLogger
					.log("[ErrorMetodoInexistente] " + "consola.lineas_verificar todavía es null o está vacío");

			return;
		}

		/*
		 * numeroDeLinea del motor rápido es base cero.
		 *
		 * Por lo tanto, el elemento inmediatamente posterior se encuentra en:
		 *
		 * numeroLineaError + 1
		 */
		int indiceSiguiente = numeroLineaError + 1;

		if (indiceSiguiente < 0 || indiceSiguiente >= lineas.length) {
			CrashDetectorLogger.log("[ErrorMetodoInexistente] " + "No existe una línea posterior. "
					+ "Índice del error=" + numeroLineaError + ", total de líneas=" + lineas.length);

			/*
			 * Registrar de todos modos el error, pero sin método llamador.
			 */
			this.lineaSiguiente = "";
			this.metodoLlamador = "desconocido";

			activarDeteccion(consola);
			return;
		}

		String siguiente = lineas[indiceSiguiente];

		this.lineaSiguiente = siguiente == null ? "" : siguiente.trim();

		CrashDetectorLogger.log("[ErrorMetodoInexistente] Línea inmediata posterior: " + lineaSiguiente);

		this.metodoLlamador = extraerMetodoLlamador(lineaSiguiente);

		if (metodoLlamador == null || metodoLlamador.isEmpty()) {
			metodoLlamador = "desconocido";
		}

		CrashDetectorLogger.log("[ErrorMetodoInexistente] Método llamador: " + metodoLlamador);

		activarDeteccion(consola);
	}

	/**
	 * Construye el mensaje y registra la verificación después de obtener la línea
	 * inmediatamente posterior.
	 */
	private void activarDeteccion(Consola consola) {
		if (activado || !errorPendiente || consola == null || firmaMetodo == null || firmaMetodo.isEmpty()) {

			return;
		}

		resetearBanderas();

		String lineaObjetivo = !lineaSiguiente.isEmpty() ? lineaSiguiente : lineaErrorPendiente;

		detectarOrigen(lineaErrorPendiente, lineaObjetivo);

		StringBuilder sb = new StringBuilder();

		/*
		 * Primer argumento: método que no existe.
		 *
		 * Segundo argumento: método que intentó llamar al método inexistente.
		 */
		sb.append(MonitorDePID.idioma.errorMetodoInexistente(firmaMetodo, metodoLlamador));

		/*
		 * Mostrar la línea completa del stack para conservar el archivo, número de
		 * línea, nombre del JAR y demás información útil.
		 */
		if (!lineaSiguiente.isEmpty()) {
			sb.append(Verificaciones.nl_html);
			sb.append("<span style='color:#888888; " + "font-family:monospace;'>");
			sb.append(escapeHtml(lineaSiguiente));
			sb.append("</span>");
		}

		agregarRecomendaciones(sb);

		/*
		 * Guardar el mensaje antes de llamar agregarErrorALectador(), porque ese método
		 * podría consultar mensaje() inmediatamente.
		 */
		this.mensaje = sb.toString();

		CrashDetectorLogger.log("[ErrorMetodoInexistente] Activando detección para: " + nombreMetodoDetectado);

		try {
			this.enlaceHtml = consola.agregarErrorALectador(numeroLineaError, this);

			if (enlaceHtml != null && !enlaceHtml.isEmpty()) {
				this.mensaje = this.mensaje + Verificaciones.nl_html + enlaceHtml;
			}

			this.activado = true;

			CrashDetectorLogger.log("[ErrorMetodoInexistente] " + "Detección activada correctamente");
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		} finally {
			limpiarEstadoPendiente();
		}
	}

	/**
	 * Restablece el estado temporal utilizado durante el análisis rápido.
	 */
	private void limpiarEstadoPendiente() {
		this.errorPendiente = false;
		this.consolaPendiente = null;
		this.lineaErrorPendiente = "";
		this.numeroLineaError = -1;
	}

	/**
	 * Restablece las banderas antes de detectar el posible origen de la llamada.
	 */
	private void resetearBanderas() {
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

	/**
	 * Detecta qué mod, cargador o componente parece haber realizado la llamada.
	 */
	private void detectarOrigen(String lineaError, String lineaObjetivo) {

		String errorMinusculas = lineaError == null ? "" : lineaError.toLowerCase();

		String objetivoMinusculas = lineaObjetivo == null ? "" : lineaObjetivo.toLowerCase();

		if (errorMinusculas.contains("net.minecraftforge") || errorMinusculas.contains("minecraftforge")) {

			minecraftforge = true;
		}

		if (contiene(objetivoMinusculas, "com/simibubi/create", "com.simibubi.create")) {

			create = true;

		} else if (contiene(objetivoMinusculas, "yesman/epicfight", "yesman.epicfight")) {

			epicfight = true;

		} else if (contiene(objetivoMinusculas, "mod/azure/azurelib", "mod.azure.azurelib")) {

			azurelib = true;

		} else if (contiene(objetivoMinusculas, "asbestosstar/", "asbestosstar.")) {

			featurecreep = true;

		} else if (contiene(objetivoMinusculas, "dangerzone/", "dangerzone.")) {

			dangerzone = true;

		} else if (contiene(objetivoMinusculas, "net/fabricmc/", "net.fabricmc.")) {

			fabricloader = true;

		} else if (contiene(objetivoMinusculas, "net/neoforged/", "net.neoforged.")) {

			neoforged = true;

		} else if (contiene(objetivoMinusculas, "net/pillowmc/", "net.pillowmc.")) {

			pillowmc = true;

		} else if (contiene(objetivoMinusculas, "cpw/mods/modlauncher", "cpw.mods.modlauncher")) {

			modlauncher = true;

		} else if (contiene(objetivoMinusculas, "net/minecraftforge", "net.minecraftforge")) {

			minecraftforge = true;

		} else if ((objetivoMinusculas.contains("net/minecraft/") || objetivoMinusculas.contains("net.minecraft."))
				&& !objetivoMinusculas.contains("net/minecraftforge/")
				&& !objetivoMinusculas.contains("net.minecraftforge.")) {

			minecraft = true;
		}
	}

	/**
	 * Añade recomendaciones específicas según el mod o cargador detectado.
	 */
	private void agregarRecomendaciones(StringBuilder sb) {
		if (sb == null) {
			return;
		}

		if (create) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_create());
		}

		if (epicfight) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_epicfight());
		}

		if (azurelib) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_azurelib());
		}

		if (featurecreep) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_featurecreep());
		}

		if (dangerzone) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_dangerzone());
		}

		if (modlauncher) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_modlauncher());
		}

		if (minecraftforge) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_minecraftforge());
		}

		if (neoforged) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_neoforged());
		}

		if (fabricloader) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_fabricloader());
		}

		if (pillowmc) {
			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_pillowmc());
		}

		/*
		 * Mostrar la recomendación genérica de Minecraft solamente cuando no se haya
		 * detectado una recomendación más específica.
		 */
		if (minecraft && !create && !epicfight && !azurelib && !featurecreep && !dangerzone && !minecraftforge
				&& !neoforged && !fabricloader && !pillowmc) {

			sb.append(Verificaciones.nl_html).append(MonitorDePID.idioma.faltar_de_clases_minecraft());
		}
	}

	/**
	 * Extrae la firma completa del método inexistente.
	 *
	 * También funciona cuando la línea comienza con "Suppressed:".
	 */
	private static String extraerFirmaMetodo(String linea) {
		if (linea == null) {
			return null;
		}

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

	/**
	 * Extrae solamente el nombre del método inexistente.
	 *
	 * Ejemplo:
	 *
	 * 'void paquete.Clase.registerBiomeType(java.lang.Object)'
	 *
	 * Resultado:
	 *
	 * registerBiomeType
	 */
	private static String extraerMetodoNoSuchMethod(String linea) {
		String firma = extraerFirmaMetodo(linea);

		if (firma == null || firma.isEmpty()) {
			return "";
		}

		int parentesis = firma.indexOf('(');

		String sinArgumentos = parentesis >= 0 ? firma.substring(0, parentesis) : firma;

		int punto = sinArgumentos.lastIndexOf('.');

		if (punto < 0 || punto + 1 >= sinArgumentos.length()) {
			return sinArgumentos.replace("'", "").trim();
		}

		return sinArgumentos.substring(punto + 1).replace("'", "").trim();
	}

	/**
	 * Extrae el método llamador desde la línea inmediatamente posterior.
	 *
	 * Entrada:
	 *
	 * at net.hibiscus.naturespirit.NatureSpirit.lambda$commonSetup$0(
	 * NatureSpirit.java:148) ~[natures_spirit-2.2.5-1.20.1.jar:2.2.5-1.20.1]
	 *
	 * Resultado:
	 *
	 * net.hibiscus.naturespirit.NatureSpirit.lambda$commonSetup$0
	 */
	private static String extraerMetodoLlamador(String lineaStack) {
		if (lineaStack == null) {
			return "";
		}

		String resultado = lineaStack.trim();

		if (resultado.isEmpty()) {
			return "";
		}

		/*
		 * La línea inmediata debería ser una entrada del stack trace. Si no lo es, no
		 * se intenta buscar otra línea.
		 */
		if (!resultado.startsWith("at ")) {
			return "";
		}

		resultado = resultado.substring(3).trim();

		int parentesis = resultado.indexOf('(');

		if (parentesis >= 0) {
			resultado = resultado.substring(0, parentesis).trim();
		}

		return resultado;
	}

	/**
	 * Comprueba si el texto contiene alguna de las dos variantes.
	 */
	private static boolean contiene(String texto, String varianteConBarras, String varianteConPuntos) {

		if (texto == null) {
			return false;
		}

		return texto.contains(varianteConBarras) || texto.contains(varianteConPuntos);
	}

	/**
	 * Escapa texto antes de insertarlo en HTML.
	 */
	private static String escapeHtml(String texto) {
		if (texto == null) {
			return "";
		}

		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&#39;");
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
	public String[] ocupaTrazo() {
		return new String[] { "NoSuchMethodError" };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}