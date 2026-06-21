package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class JPMSIllegalAccess implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private String claseOrigen = "";
	private String moduloOrigen = "";
	private String claseDestino = "";
	private String moduloDestino = "";

	private final List<ArchivoDeMod> modsRelacionados = new ArrayList<>();

	private static final String ILLEGAL_ACCESS_EXCEPTION = "IllegalAccessException";
	private static final String IN_MODULE = "in module";
	private static final String CANNOT_ACCESS = "cannot access";

	private static final String CLASS = "class ";
	private static final String INTERFACE = "interface ";
	private static final String IN_MODULE_PARENTESIS = "(in module ";
	private static final String IN = " in ";

	private static final String IO_NETTY = "io.netty";
	private static final String JDK_INTERNAL_UNSAFE = "jdk.internal.misc.Unsafe";
	private static final String MODULE_JAVA_BASE = "module java.base";

	private static final String FORGE_PLUGIN_FINDER = "mezz.jei.forge.startup.ForgePluginFinder";
	private static final String EXPANDEDAE_JEI = "lu.kolja.expandedae.xmod.recipemanager.JEI";
	private static final String MODULE_JEI = "module jei";
	private static final String MODULE_EXPANDEDAE = "module expandedae";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ILLEGAL_ACCESS_EXCEPTION, IN_MODULE, CANNOT_ACCESS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		if (lineaContieneJPMSIllegalAccess(linea)) {

			if (linea.contains(IO_NETTY) && linea.contains(JDK_INTERNAL_UNSAFE) && linea.contains(MODULE_JAVA_BASE)) {
				return;
			}

			if (linea.contains(FORGE_PLUGIN_FINDER) && linea.contains(EXPANDEDAE_JEI) && linea.contains(MODULE_JEI)
					&& linea.contains(MODULE_EXPANDEDAE)) {
				return;
			}

			extraerDatos(linea);
			buscarModsRelacionados();

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneJPMSIllegalAccess(String linea) {
		return linea.contains(ILLEGAL_ACCESS_EXCEPTION) && linea.contains(IN_MODULE) && linea.contains(CANNOT_ACCESS);
	}

	private void extraerDatos(String linea) {
		try {
			int idxClassA = linea.indexOf(CLASS);
			int idxModuleA = linea.indexOf(IN_MODULE_PARENTESIS, idxClassA);

			if (idxClassA != -1 && idxModuleA != -1 && idxModuleA > idxClassA) {
				claseOrigen = limpiarClase(linea.substring(idxClassA + CLASS.length(), idxModuleA));

				int finModuleA = linea.indexOf(")", idxModuleA);
				if (finModuleA != -1) {
					moduloOrigen = limpiarTermino(
							linea.substring(idxModuleA + IN_MODULE_PARENTESIS.length(), finModuleA));

					int idxClassB = linea.indexOf(CLASS, finModuleA);
					int idxModuleB = linea.indexOf(IN_MODULE_PARENTESIS, idxClassB);

					if (idxClassB != -1 && idxModuleB != -1 && idxModuleB > idxClassB) {
						claseDestino = limpiarClase(linea.substring(idxClassB + CLASS.length(), idxModuleB));

						int finModuleB = linea.indexOf(")", idxModuleB);
						if (finModuleB != -1) {
							moduloDestino = limpiarTermino(
									linea.substring(idxModuleB + IN_MODULE_PARENTESIS.length(), finModuleB));
						}
					}
				}
			}
		} catch (Throwable ignorado) {
		}
	}

	private void buscarModsRelacionados() {
		try {
			Buscador.cargar();
			modsRelacionados.clear();

			agregarResultados(claseOrigen);
			agregarResultados(claseOrigen.replace('.', '/'));

			agregarResultados(claseDestino);
			agregarResultados(claseDestino.replace('.', '/'));

			agregarResultados(moduloOrigen);
			agregarResultados(moduloDestino);
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

	private String limpiarClase(String clase) {
		if (clase == null) {
			return "";
		}

		String limpia = sinEspaciosLaterales(clase);

		if (limpia.startsWith(CLASS)) {
			limpia = sinEspaciosLaterales(limpia.substring(CLASS.length()));
		}

		if (limpia.startsWith(INTERFACE)) {
			limpia = sinEspaciosLaterales(limpia.substring(INTERFACE.length()));
		}

		int modulo = limpia.indexOf(IN);
		if (modulo > -1) {
			limpia = sinEspaciosLaterales(limpia.substring(0, modulo));
		}

		return limpiarComillas(limpia.replace('/', '.'));
	}

	private String limpiarTermino(String texto) {
		if (texto == null) {
			return "";
		}

		return limpiarComillas(texto);
	}

	private String limpiarComillas(String texto) {
		if (texto == null)
			return "";

		String limpia = sinEspaciosLaterales(texto);
		StringBuilder sb = new StringBuilder(limpia.length());

		for (int i = 0; i < limpia.length(); i++) {
			char c = limpia.charAt(i);

			if (c != '\'' && c != '"') {
				sb.append(c);
			}
		}

		return sinEspaciosLaterales(sb.toString());
	}

	private String sinEspaciosLaterales(String texto) {
		if (texto == null)
			return "";

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

	private String formatearMods(List<ArchivoDeMod> mods) {
		if (mods == null || mods.isEmpty()) {
			return "";
		}

		return mods.stream().map(mod -> "<b>" + Buscador.rutaParaPublicar(mod.ubicacion_para_publicar()) + "</b>")
				.distinct().collect(Collectors.joining(", "));
	}

	@Override
	public Verificaciones nueva() {
		return new JPMSIllegalAccess();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1700;
	}

	@Override
	public String mensaje() {
		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.mensajeJPMSIllegalAccess(claseOrigen, moduloOrigen, claseDestino, moduloDestino));

		String mods = formatearMods(modsRelacionados);
		if (!mods.isEmpty()) {
			sb.append("<p>(").append(mods).append(")</p>");
		}

		sb.append(this.enlace);
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreJPMSIllegalAccess();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "jpms_illegal_access";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}