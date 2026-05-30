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
 * Detecta errores generales de ClassCastException en logs de Minecraft.
 */
public class ClassCastExceptionGeneral implements Verificaciones {

	private boolean posibleError = false;
	private boolean activado = false;

	private String lineaClassCast = "";
	private String enlace = "";

	private String claseOrigen = "";
	private String claseDestino = "";

	private final List<ArchivoDeMod> modsClaseOrigen = new ArrayList<>();
	private final List<ArchivoDeMod> modsClaseDestino = new ArrayList<>();

	@Override
	public void verificar(Consola consola) {

		if (consola.contenido_verificar.contains("java.lang.ClassCastException:")
				&& consola.contenido_verificar.contains(" cannot be cast to ")) {
			posibleError = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleError || activado || linea == null) {
			return;
		}

		if (linea.contains("java.lang.ClassCastException:") && linea.contains(" cannot be cast to ")) {

			if (linea.contains("class java.lang.Integer cannot be cast to class java.util.List")) {
				String[] lineas = consola.contenido_verificar.split("\\R", -1);

				if (num >= 0 && num < lineas.length && lineas[num].contains("oneauras-cart-optimizer")) {
					return;
				}
			}

			this.lineaClassCast = linea.trim();
			this.enlace = consola.agregarErrorALectador(num, this);

			extraerClases(lineaClassCast);
			buscarModsRelacionados();

			this.activado = true;
		}
	}

	private void extraerClases(String linea) {
		try {
			String texto = linea;

			int inicio = texto.indexOf("java.lang.ClassCastException:");
			if (inicio > -1) {
				texto = texto.substring(inicio + "java.lang.ClassCastException:".length()).trim();
			}

			String separador = " cannot be cast to ";
			int idx = texto.indexOf(separador);

			if (idx == -1) {
				return;
			}

			claseOrigen = limpiarClase(texto.substring(0, idx));
			claseDestino = limpiarClase(texto.substring(idx + separador.length()));
		} catch (Throwable ignorado) {
		}
	}

	private void buscarModsRelacionados() {
		try {
			Buscador.cargar();

			if (claseOrigen != null && !claseOrigen.isEmpty()) {
				modsClaseOrigen.addAll(buscarModsConClase(claseOrigen));
			}

			if (claseDestino != null && !claseDestino.isEmpty() && !claseDestino.equals(claseOrigen)) {
				modsClaseDestino.addAll(buscarModsConClase(claseDestino));
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
	public Verificaciones nueva() {
		return new ClassCastExceptionGeneral();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1380;
	}

	@Override
	public String mensaje() {
		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.mensajeClassCastExceptionGeneral(lineaClassCast));

		String modsOrigen = formatearMods(modsClaseOrigen);
		String modsDestino = formatearMods(modsClaseDestino);

		List<String> valores = new ArrayList<>();

		if (!modsOrigen.isEmpty()) {
			valores.add(modsOrigen);
		}

		if (!modsDestino.isEmpty()) {
			valores.add(modsDestino);
		}

		if (!valores.isEmpty()) {
			sb.append("<p>(").append(String.join(", ", valores)).append(")</p>");
		}

		sb.append(this.enlace);
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreClassCastExceptionGeneral();
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
		return "class_cast_exception_general";
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