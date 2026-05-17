package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class JPMSIllegalAccess implements Verificaciones {

	private boolean posibleJPMS = false;
	private boolean activado = false;
	private String enlace = "";

	private String claseOrigen = "";
	private String moduloOrigen = "";
	private String claseDestino = "";
	private String moduloDestino = "";

	private final List<ArchivoDeMod> modsRelacionados = new ArrayList<>();

	@Override
	public void verificar(Consola consola) {

		if (consola.contenido_verificar == null) {
			return;
		}

		if (consola.contenido_verificar.contains("IllegalAccessException")
				&& consola.contenido_verificar.contains("in module")
				&& consola.contenido_verificar.contains("cannot access")) {

			posibleJPMS = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {

		if (!posibleJPMS || activado || linea == null)
			return;

		if (linea.contains("IllegalAccessException") && linea.contains("in module")
				&& linea.contains("cannot access")) {

			if (linea.contains("io.netty") && linea.contains("jdk.internal.misc.Unsafe")
					&& linea.contains("module java.base")) {
				return;
			}

			if (linea.contains("mezz.jei.forge.startup.ForgePluginFinder")
					&& linea.contains("lu.kolja.expandedae.xmod.recipemanager.JEI") && linea.contains("module jei")
					&& linea.contains("module expandedae")) {
				return;
			}

			extraerDatos(linea);
			buscarModsRelacionados();

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private void extraerDatos(String linea) {
		try {
			int idxClassA = linea.indexOf("class ");
			int idxModuleA = linea.indexOf("(in module ", idxClassA);

			if (idxClassA != -1 && idxModuleA != -1 && idxModuleA > idxClassA) {
				claseOrigen = limpiarClase(linea.substring(idxClassA + "class ".length(), idxModuleA));

				int finModuleA = linea.indexOf(")", idxModuleA);
				if (finModuleA != -1) {
					moduloOrigen = limpiarTermino(linea.substring(idxModuleA + "(in module ".length(), finModuleA));

					int idxClassB = linea.indexOf("class ", finModuleA);
					int idxModuleB = linea.indexOf("(in module ", idxClassB);

					if (idxClassB != -1 && idxModuleB != -1 && idxModuleB > idxClassB) {
						claseDestino = limpiarClase(linea.substring(idxClassB + "class ".length(), idxModuleB));

						int finModuleB = linea.indexOf(")", idxModuleB);
						if (finModuleB != -1) {
							moduloDestino = limpiarTermino(
									linea.substring(idxModuleB + "(in module ".length(), finModuleB));
						}
					}
				}
			}
		} catch (Throwable ignorado) {
		}
	}

	private void buscarModsRelacionados() {
		try {
			Buscardor.cargar();
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
		if (termino == null || termino.trim().isEmpty()) {
			return;
		}

		try {
			List<ArchivoDeMod> encontrados = Buscardor.buscarModsConTermino(termino.trim());
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

		String limpia = clase.trim();

		if (limpia.startsWith("class ")) {
			limpia = limpia.substring("class ".length()).trim();
		}

		if (limpia.startsWith("interface ")) {
			limpia = limpia.substring("interface ".length()).trim();
		}

		int modulo = limpia.indexOf(" in ");
		if (modulo > -1) {
			limpia = limpia.substring(0, modulo).trim();
		}

		return limpia.replace('/', '.').replace("'", "").replace("\"", "").trim();
	}

	private String limpiarTermino(String texto) {
		if (texto == null) {
			return "";
		}

		return texto.replace("'", "").replace("\"", "").trim();
	}

	private String formatearMods(List<ArchivoDeMod> mods) {
		if (mods == null || mods.isEmpty()) {
			return "";
		}

		return mods.stream().map(mod -> "<b>" + Buscardor.rutaParaPublicar(mod.ubicacion_para_publicar()) + "</b>")
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

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}
}