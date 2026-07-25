package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta:
 *
 * 1. Errores de versión de clase Java, como UnsupportedClassVersionError. 2. Un
 * problematic frame que realmente pertenece a jvm.dll, libjvm.so o
 * libjvm.dylib.
 *
 * El encabezado "Problematic frame:" por sí solo nunca activa la verificación.
 */
public class JavaVersiones implements Verificaciones {

	private static final int MAX_LINEAS_DESPUES_PROBLEMATIC_FRAME = 4;

	private boolean activado = false;

	private final Set<String> mensajes = new HashSet<String>();
	private final List<ArchivoDeMod> modsRelacionados = new ArrayList<ArchivoDeMod>();

	private final Map<Consola, EstadoFrameProblematico> estadosFrame = new IdentityHashMap<Consola, EstadoFrameProblematico>();

	private String claseConProblema = null;
	private String enlace = null;

	private static final String TEXTO_UNSUPPORTED_CLASS = "UnsupportedClassVersionError:";

	private static final String TEXTO_JAVA22 = "Unsupported class file major version";

	private static final String TEXTO_JAVA8 = "Unsupported major.minor version 52.0";

	private static final String TEXTO_PROBLEMATIC_FRAME = "Problematic frame:";

	/*
	 * Se incluyen los corchetes porque las líneas reales del frame suelen ser:
	 *
	 * # C [libjvm.so+0x...] # V [jvm.dll+0x...]
	 *
	 * Esto evita aceptar menciones generales a la biblioteca en otras secciones del
	 * hs_err.
	 */
	private static final String FRAME_LIBJVM_LINUX = "[libjvm.so";

	private static final String FRAME_JVM_WINDOWS = "[jvm.dll";

	private static final String FRAME_LIBJVM_MAC = "[libjvm.dylib";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_UNSUPPORTED_CLASS, TEXTO_JAVA22, TEXTO_JAVA8, TEXTO_PROBLEMATIC_FRAME,
				FRAME_LIBJVM_LINUX, FRAME_JVM_WINDOWS, FRAME_LIBJVM_MAC };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null) {

			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {

		if (linea == null || linea.isEmpty() || consola == null || activado) {

			return;
		}

		verificarFrameProblematico(consola, linea, numeroDeLinea);

		if (activado) {
			return;
		}

		verificarVersionDeClase(consola, linea, numeroDeLinea);
	}

	/**
	 * Registra el encabezado y solamente activa la verificación cuando una línea de
	 * frame JVM aparece inmediatamente después.
	 */
	private void verificarFrameProblematico(Consola consola, String linea, int numeroDeLinea) {

		EstadoFrameProblematico estado = obtenerEstadoFrame(consola);

		if (linea.contains(TEXTO_PROBLEMATIC_FRAME)) {
			/*
			 * El encabezado no identifica la biblioteca causante. Solamente abre una
			 * ventana corta en la que se espera la línea del frame.
			 */
			estado.lineaEncabezado = numeroDeLinea;

			return;
		}

		if (!lineaContieneFrameJvm(linea)) {
			return;
		}

		if (!estaDespuesDelEncabezado(estado.lineaEncabezado, numeroDeLinea)) {

			/*
			 * Una mención a libjvm/jvm.dll fuera de la sección "Problematic frame:" no
			 * cuenta.
			 */
			return;
		}

		mensajes.add(MonitorDePID.idioma.javaProblematica());

		enlace = consola.agregarErrorALectador(numeroDeLinea, this);

		activado = true;
		estadosFrame.clear();
	}

	private EstadoFrameProblematico obtenerEstadoFrame(Consola consola) {

		EstadoFrameProblematico estado = estadosFrame.get(consola);

		if (estado == null) {
			estado = new EstadoFrameProblematico();
			estadosFrame.put(consola, estado);
		}

		return estado;
	}

	/**
	 * La línea real del frame debe aparecer después del encabezado y dentro de una
	 * distancia corta.
	 */
	private boolean estaDespuesDelEncabezado(int lineaEncabezado, int lineaActual) {

		if (lineaEncabezado < 0 || lineaActual <= lineaEncabezado) {

			return false;
		}

		return lineaActual - lineaEncabezado <= MAX_LINEAS_DESPUES_PROBLEMATIC_FRAME;
	}

	private boolean lineaContieneFrameJvm(String linea) {

		return linea.contains(FRAME_LIBJVM_LINUX) || linea.contains(FRAME_JVM_WINDOWS)
				|| linea.contains(FRAME_LIBJVM_MAC);
	}

	private void verificarVersionDeClase(Consola consola, String linea, int numeroDeLinea) {

		if (linea.contains(TEXTO_UNSUPPORTED_CLASS)) {
			String clase = extraerClase(linea);

			if (clase != null) {
				claseConProblema = clase;

				buscarModsRelacionados();

				mensajes.add(MonitorDePID.idioma.javaObsoleta() + " JVM: " + determinarVersionJava(linea));

				enlace = consola.agregarErrorALectador(numeroDeLinea, this);

				activado = true;
				return;
			}
		}

		if (linea.contains(TEXTO_JAVA22)) {
			mensajes.add(MonitorDePID.idioma.java22());

			enlace = consola.agregarErrorALectador(numeroDeLinea, this);

			activado = true;
			return;
		}

		if (linea.contains(TEXTO_JAVA8)) {
			mensajes.add(MonitorDePID.idioma.errorJava8Requerido());

			enlace = consola.agregarErrorALectador(numeroDeLinea, this);

			activado = true;
		}
	}

	private void buscarModsRelacionados() {
		try {
			Buscador.cargar();

			modsRelacionados.clear();

			agregarResultados(claseConProblema);
			agregarResultados(claseConProblema.replace('.', '/'));

		} catch (Throwable ignorado) {
		}
	}

	private void agregarResultados(String termino) {
		if (termino == null || sinEspaciosLaterales(termino).isEmpty()) {

			return;
		}

		try {
			List<ArchivoDeMod> encontrados = Buscador.buscarModsConTermino(sinEspaciosLaterales(termino));

			if (encontrados != null) {
				modsRelacionados.addAll(encontrados);
			}

		} catch (Throwable ignorado) {
		}
	}

	private String formatearMods(List<ArchivoDeMod> mods) {

		if (mods == null || mods.isEmpty()) {
			return "";
		}

		return mods.stream().map(mod -> "<b>" + Buscador.rutaParaPublicar(mod.ubicacion_para_publicar()) + "</b>")
				.distinct().collect(Collectors.joining(", "));
	}

	private String extraerClase(String linea) {
		if (linea == null) {
			return null;
		}

		int inicio = linea.indexOf(TEXTO_UNSUPPORTED_CLASS);

		if (inicio < 0) {
			return null;
		}

		inicio += TEXTO_UNSUPPORTED_CLASS.length();

		while (inicio < linea.length() && Character.isWhitespace(linea.charAt(inicio))) {

			inicio++;
		}

		if (inicio >= linea.length()) {
			return null;
		}

		int fin = linea.indexOf(" has been compiled", inicio);

		if (fin < 0) {
			fin = linea.length();
		}

		return sinEspaciosLaterales(linea.substring(inicio, fin).replace("/", "."));
	}

	private String determinarVersionJava(String linea) {

		int indice = linea.indexOf("class file version");

		if (indice < 0) {
			return MonitorDePID.idioma.desconocida();
		}

		int inicio = indice + "class file version".length();

		while (inicio < linea.length() && !Character.isDigit(linea.charAt(inicio))) {

			inicio++;
		}

		if (inicio >= linea.length()) {
			return MonitorDePID.idioma.desconocida();
		}

		int fin = inicio;

		while (fin < linea.length() && Character.isDigit(linea.charAt(fin))) {

			fin++;
		}

		String versionClase = linea.substring(inicio, fin);

		try {
			int versionNumero = Integer.parseInt(versionClase);

			switch (versionNumero) {
			case 52:
				return "1.8";

			case 51:
				return "1.7";

			case 50:
				return "1.6";

			default:
				break;
			}

			/*
			 * Java 9 corresponde a la versión de clase 53.
			 */
			if (versionNumero >= 53) {
				return String.valueOf(versionNumero - 44);
			}

		} catch (NumberFormatException excepcion) {
			return MonitorDePID.idioma.desconocida() + " (" + versionClase + ")";
		}

		return MonitorDePID.idioma.desconocida() + " (" + versionClase + ")";
	}

	private String sinEspaciosLaterales(String texto) {

		if (texto == null) {
			return "";
		}

		int inicio = 0;
		int fin = texto.length();

		while (inicio < fin && Character.isWhitespace(texto.charAt(inicio))) {

			inicio++;
		}

		while (fin > inicio && Character.isWhitespace(texto.charAt(fin - 1))) {

			fin--;
		}

		return texto.substring(inicio, fin);
	}

	@Override
	public Verificaciones nueva() {
		return new JavaVersiones();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 925.0f;
	}

	@Override
	public String mensaje() {
		if (mensajes.isEmpty()) {
			return "";
		}

		StringBuilder html = new StringBuilder("<ul>");

		for (String mensaje : mensajes) {
			html.append("<li>").append(mensaje).append("</li>");
		}

		if (claseConProblema != null) {
			html.append("<li><b>").append(MonitorDePID.idioma.clase()).append(":</b> ").append(claseConProblema);

			String mods = formatearMods(modsRelacionados);

			if (!mods.isEmpty()) {
				html.append(" (").append(mods).append(")");
			}

			html.append("</li>");
		}

		html.append("</ul>");

		if (enlace != null) {
			html.append(enlace);
		}

		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_java_versiones();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new QuickFix.Builder(nombre());

		builder.agregarEtiqueta(MonitorDePID.idioma.solucionParaJavaInstallar());

		return builder.construir();
	}

	@Override
	public String id() {
		return "java_versiones";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

	/**
	 * Estado del encabezado "Problematic frame:" de una sola Consola.
	 */
	private static final class EstadoFrameProblematico {

		private int lineaEncabezado = -1;
	}
}