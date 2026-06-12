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
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores IncompatibleClassChangeError causados por intentar heredar de
 * una clase marcada como final.
 *
 * Ejemplo: java.lang.IncompatibleClassChangeError: class A cannot inherit from
 * final class B
 */
public class ErrorClaseFinalExtendida implements Verificaciones {

	private boolean activado = false;
	private boolean posibleErrorClaseFinal = false;

	private String mensaje = "";
	private String claseHija = "";
	private String clasePadreFinal = "";

	private final List<ArchivoDeMod> modsClaseHija = new ArrayList<>();
	private final List<ArchivoDeMod> modsClasePadreFinal = new ArrayList<>();

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		if (contenido == null) {
			return;
		}

		posibleErrorClaseFinal = contenido.contains("IncompatibleClassChangeError: class")
				&& contenido.contains(" cannot inherit from final class ");
	}

	public boolean quiereAnalizarLineas() {
		if (!posibleErrorClaseFinal)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (this.activado || !posibleErrorClaseFinal || linea == null) {
			return;
		}

		if (!linea.contains("IncompatibleClassChangeError: class")
				|| !linea.contains(" cannot inherit from final class ")) {
			return;
		}

		if (!extraerClases(linea)) {
			return;
		}

		buscarModsRelacionados();

		String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.errorClaseFinalExtendida(claseHija, clasePadreFinal));

		String modsHija = formatearMods(modsClaseHija);
		String modsPadre = formatearMods(modsClasePadreFinal);

		List<String> valores = new ArrayList<>();

		if (!modsHija.isEmpty()) {
			valores.add(modsHija);
		}

		if (!modsPadre.isEmpty()) {
			valores.add(modsPadre);
		}

		if (!valores.isEmpty()) {
			sb.append("<p>(").append(String.join(", ", valores)).append(")</p>");
		}

		sb.append(enlaceHtml);

		this.mensaje = sb.toString();
		this.activado = true;
	}

	private boolean extraerClases(String linea) {
		String inicioTexto = "IncompatibleClassChangeError: class ";
		String separador = " cannot inherit from final class ";

		int inicio = linea.indexOf(inicioTexto);
		if (inicio == -1) {
			return false;
		}

		inicio += inicioTexto.length();

		int medio = linea.indexOf(separador, inicio);
		if (medio == -1) {
			return false;
		}

		String hija = linea.substring(inicio, medio).trim();
		String padre = linea.substring(medio + separador.length()).trim();

		if (hija.isEmpty() || padre.isEmpty()) {
			return false;
		}

		this.claseHija = limpiarClase(hija);
		this.clasePadreFinal = limpiarClase(padre);

		return !this.claseHija.isEmpty() && !this.clasePadreFinal.isEmpty();
	}

	private void buscarModsRelacionados() {
		try {
			Buscador.cargar();

			modsClaseHija.clear();
			modsClasePadreFinal.clear();

			if (claseHija != null && !claseHija.isEmpty()) {
				modsClaseHija.addAll(buscarModsConClase(claseHija));
			}

			if (clasePadreFinal != null && !clasePadreFinal.isEmpty() && !clasePadreFinal.equals(claseHija)) {
				modsClasePadreFinal.addAll(buscarModsConClase(clasePadreFinal));
			}
		} catch (Throwable ignorado) {
		}
	}

	private List<ArchivoDeMod> buscarModsConClase(String clase) {
		List<ArchivoDeMod> encontrados = new ArrayList<>();

		try {
			String clasePunto = limpiarClase(clase);
			String claseInterna = clasePunto.replace('.', '/');

			List<ArchivoDeMod> resultadoInterno = Buscador.buscarModsConTermino(claseInterna);
			if (resultadoInterno != null) {
				encontrados.addAll(resultadoInterno);
			}

			if (!claseInterna.equals(clasePunto)) {
				List<ArchivoDeMod> resultadoPunto = Buscador.buscarModsConTermino(clasePunto);
				if (resultadoPunto != null) {
					encontrados.addAll(resultadoPunto);
				}
			}
		} catch (Throwable ignorado) {
		}

		return encontrados.stream().distinct().collect(Collectors.toList());
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

		int espacio = limpia.indexOf(' ');
		if (espacio > -1) {
			limpia = limpia.substring(0, espacio).trim();
		}

		return limpia.replace('/', '.').replace("'", "").replace("\"", "").trim();
	}

	private String formatearMods(List<ArchivoDeMod> mods) {
		if (mods == null || mods.isEmpty()) {
			return "";
		}

		return mods.stream().map(mod -> "<b>" + Buscador.rutaParaPublicar(mod.ubicacion_para_publicar()) + "</b>")
				.distinct().collect(Collectors.joining(", "));
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains("IncompatibleClassChangeError: class")
				&& trazo.trace.contains(" cannot inherit from final class ");
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorClaseFinalExtendida();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 820.0f;
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionErrorClaseFinalExtendida())
				.construir();
	}

	@Override
	public String id() {
		return "clase_final_extendida";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorClaseFinalExtendida();
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}