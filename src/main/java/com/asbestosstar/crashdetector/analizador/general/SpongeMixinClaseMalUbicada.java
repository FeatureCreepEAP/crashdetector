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

public class SpongeMixinClaseMalUbicada implements Verificaciones {

	private boolean activado = false;

	private String enlace = "";

	private String claseConflictiva = "";
	private String paqueteMixin = "";
	private String archivoMixin = "";

	private final List<ArchivoDeMod> modsRelacionados = new ArrayList<>();

	private static final String TEXTO_ILLEGAL_CLASS_LOAD = "IllegalClassLoadError";
	private static final String TEXTO_DEFINED_MIXIN_PACKAGE = "defined mixin package";
	private static final String TEXTO_CANNOT_BE_REFERENCED = "cannot be referenced directly";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ILLEGAL_CLASS_LOAD, TEXTO_DEFINED_MIXIN_PACKAGE, TEXTO_CANNOT_BE_REFERENCED };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroLinea) {

		if (lineaContieneErrorMixin(linea)) {
			extraerDatos(linea);
			buscarModsRelacionados();

			if (consola != null) {
				this.enlace = consola.agregarErrorALectador(numeroLinea, this);
			}

			this.activado = true;
		}
	}

	private boolean lineaContieneErrorMixin(String linea) {
		return linea.contains(TEXTO_ILLEGAL_CLASS_LOAD) && linea.contains(TEXTO_DEFINED_MIXIN_PACKAGE)
				&& linea.contains(TEXTO_CANNOT_BE_REFERENCED);
	}

	private void extraerDatos(String linea) {
		try {
			int idxClase = linea.indexOf(":");
			int idxEs = linea.indexOf(" is in a defined mixin package");

			if (idxClase != -1 && idxEs != -1 && idxEs > idxClase) {
				claseConflictiva = limpiarTermino(linea.substring(idxClase + 1, idxEs));
			}

			int idxPaquete = linea.indexOf(TEXTO_DEFINED_MIXIN_PACKAGE);
			int idxOwned = linea.indexOf(" owned by");

			if (idxPaquete != -1 && idxOwned != -1 && idxOwned > idxPaquete) {
				paqueteMixin = limpiarTermino(
						linea.substring(idxPaquete + TEXTO_DEFINED_MIXIN_PACKAGE.length(), idxOwned));
			}

			int idxOwnedBy = linea.indexOf("owned by");
			int idxCannot = linea.indexOf(" and cannot");

			if (idxOwnedBy != -1 && idxCannot != -1 && idxCannot > idxOwnedBy) {
				archivoMixin = limpiarTermino(linea.substring(idxOwnedBy + "owned by".length(), idxCannot));
			}
		} catch (Throwable ignorado) {
		}
	}

	private void buscarModsRelacionados() {
		try {
			Buscador.cargar();
			modsRelacionados.clear();

			agregarResultados(claseConflictiva);
			agregarResultados(claseConflictiva.replace('.', '/'));

			agregarResultados(paqueteMixin);
			agregarResultados(paqueteMixin.replace('.', '/'));

			agregarResultados(archivoMixin);
		} catch (Throwable ignorado) {
		}
	}

	private void agregarResultados(String termino) {
		if (termino == null || termino.trim().isEmpty()) {
			return;
		}

		try {
			List<ArchivoDeMod> encontrados = Buscador.buscarModsConTermino(termino.trim());

			if (encontrados != null) {
				modsRelacionados.addAll(encontrados);
			}
		} catch (Throwable ignorado) {
		}
	}

	private String limpiarTermino(String texto) {
		if (texto == null) {
			return "";
		}

		String limpio = texto.trim();

		if (limpio.startsWith("class ")) {
			limpio = limpio.substring("class ".length()).trim();
		}

		if (limpio.startsWith("interface ")) {
			limpio = limpio.substring("interface ".length()).trim();
		}

		return limpio.replace("'", "").replace("\"", "").trim();
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
		return new SpongeMixinClaseMalUbicada();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1650;
	}

	@Override
	public String mensaje() {
		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.mensajeMixinClaseMalUbicada(claseConflictiva, paqueteMixin, archivoMixin));

		String mods = formatearMods(modsRelacionados);
		if (!mods.isEmpty()) {
			sb.append("<p>(").append(mods).append(")</p>");
		}

		sb.append(this.enlace);
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreMixinClaseMalUbicada();
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
		return "mixin_clase_mal_ubicada";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}