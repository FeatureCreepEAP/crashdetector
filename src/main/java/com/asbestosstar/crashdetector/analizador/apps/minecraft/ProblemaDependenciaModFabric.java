package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class ProblemaDependenciaModFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	// Using precompiled regex patterns to match dependency problems
	private static final Pattern P_INSTALAR = Pattern
			.compile("^-\\s*Install\\s+([^,]+),\\s*version\\s+([^\\.]+)\\s+or\\s+later\\.", Pattern.CASE_INSENSITIVE);
	private static final Pattern P_REEMPLAZO_RANGO = Pattern.compile(
			"^-\\s*Replace\\s+.*?\\(([^\\)]+)\\).*?with\\s+any\\s+version\\s+between\\s+([^\\s]+)\\s*\\(inclusive\\)\\s*and\\s*([^\\s]+)\\s*\\(exclusive\\)\\.",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern P_FALTANTE_MINIMO = Pattern.compile(
			"^-\\s*mod\\s+.+?\\(([^\\)]+)\\)\\s*[^\\)]*\\)?\\s*.*?requires\\s+version\\s+([^\\s]+)\\s*.*?or\\s+later\\s+of\\s+([a-z0-9_\\-]+)\\s*,\\s*which\\s+is\\s+missing",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern P_RANGO_PRESENTE_INCORRECTO = Pattern.compile(
			"^-\\s*mod\\s+.+?\\(([^\\)]+)\\).*?requires\\s+any\\s+version\\s+between\\s+([^\\s]+)\\s*\\(inclusive\\)\\s*and\\s*([^\\s]+)\\s*\\(exclusive\\)\\s+of\\s+mod\\s+.+?\\(([^\\)]+)\\).*?wrong\\s+version\\s+is\\s+present:\\s*([^!\\s]+)!",
			Pattern.CASE_INSENSITIVE);

	// Using a set to track unique dependencies and mods
	private final Set<String> problemas = new LinkedHashSet<>();

	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub

	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Limpiar la línea de caracteres extraños o códigos de color
		String lineaLimpia = limpiarLinea(linea);

		// Procesar patrones de sugerencias "Instalar" y "Reemplazar"
		Matcher mInst = P_INSTALAR.matcher(lineaLimpia);
		if (mInst.find()) {
			String nombreDependencia = mInst.group(1).trim();
			String versionMinima = normalizarVersion(mInst.group(2));
			String problema = "Instalar " + nombreDependencia + " mínima " + versionMinima;
			if (problemas.add(problema)) {
				// Registrar el error y agregar el enlace
				consola.agregarErrorALectador(numero_de_linea, this);
			}
			return; // Salir después de procesar
		}

		Matcher mReemp = P_REEMPLAZO_RANGO.matcher(lineaLimpia);
		if (mReemp.find()) {
			String idDependencia = mReemp.group(1).toLowerCase();
			String versionMinima = normalizarVersion(mReemp.group(2));
			String versionMaxima = normalizarVersion(mReemp.group(3));
			String problema = "Reemplazar " + idDependencia + " rango: " + versionMinima + ":" + versionMaxima;
			if (problemas.add(problema)) {
				// Registrar el error y agregar el enlace
				consola.agregarErrorALectador(numero_de_linea, this);
			}
			return; // Salir después de procesar
		}

		// Procesar bloque "Más detalles" para versiones faltantes y rangos incorrectos
		Matcher mFalta = P_FALTANTE_MINIMO.matcher(lineaLimpia);
		if (mFalta.find()) {
			String idMod = mFalta.group(1);
			String versionMinima = normalizarVersion(mFalta.group(2));
			String idDependencia = mFalta.group(3).toLowerCase();
			String problema = idMod + " requiere " + idDependencia + " mínima " + versionMinima;
			if (problemas.add(problema)) {
				// Registrar el error y agregar el enlace
				consola.agregarErrorALectador(numero_de_linea, this);
			}
			return; // Salir después de procesar
		}

		// Caso: rango de versiones presente pero incorrecta
		Matcher mRango = P_RANGO_PRESENTE_INCORRECTO.matcher(lineaLimpia);
		if (mRango.find()) {
			String modSolicitante = mRango.group(1);
			String versionMinima = normalizarVersion(mRango.group(2));
			String versionMaxima = normalizarVersion(mRango.group(3));
			String idDependencia = mRango.group(4).toLowerCase();
			String versionActual = normalizarVersion(mRango.group(5));
			String problema = modSolicitante + " rango: " + versionMinima + ":" + versionMaxima + ":" + versionActual;
			if (problemas.add(problema)) {
				// Registrar el error y agregar el enlace
				consola.agregarErrorALectador(numero_de_linea, this);
			}
			return; // Salir después de procesar
		}
	}

	// Helper function to clean up log lines (remove color codes, special
	// characters)
	private static String limpiarLinea(String s) {
		if (s == null)
			return "";
		String out = s.replaceAll("[\\u00A7][0-9A-FK-ORa-fk-or]", "") // Remove color codes
				.replaceAll("\\uFFFD.", "") // Remove broken characters
				.replace('∙', '.').replace('’', '\'').replace('‘', '\'').replace('“', '"').replace('”', '"')
				.replaceAll("\\s{2,}", " ").trim(); // Collapse spaces
		return out;
	}

	// Normalize version strings
	private static String normalizarVersion(String v) {
		if (v == null)
			return "";
		return limpiarLinea(v).trim();
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaDependenciaModFabric();
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
		return MonitorDePID.idioma.nombreProblemaDependenciaMod();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new QuickFix.Builder(nombre());

		for (String problema : problemas) {
			builder.agregarEtiqueta(problema);
		}

		return builder.construir();
	}

	@Override
	public String id() {
		return "dependencia_mod_fabric";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false; // No trace-based checks for this class
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
