package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ProblemaDependenciaModFabric implements Verificaciones {

	private boolean activado = false;

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
	private static final Pattern P_FALTANTE_WILDCARD =
			Pattern.compile(
			"^-\\s*mod\\s+.+?\\(([^\\)]+)\\).*?requires\\s+any\\s+([0-9\\.x]+)\\s+version\\s+of\\s+([a-z0-9_\\-]+)\\s*,\\s*which\\s+is\\s+missing",
			Pattern.CASE_INSENSITIVE);

	private final Set<String> problemasDetectados = new LinkedHashSet<>();
	private final Set<String> problemasSalida = new LinkedHashSet<>();

	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub

	}

@Override
public void verificar(Consola consola, String linea, int numero_de_linea) {

    // Limpiar códigos de color y caracteres problemáticos
    String lineaLimpia = limpiarLinea(linea);

    // -------------------------------------------------
    // Caso: sugerencia de instalación de dependencia
    // Ejemplo:
    // - Install fabric-api, version 0.92.0 or later.
    // -------------------------------------------------
    Matcher mInst = P_INSTALAR.matcher(lineaLimpia);
    if (mInst.find()) {

        String dependencia = mInst.group(1).trim();
        String version = normalizarVersion(mInst.group(2));

        String problema = MonitorDePID.idioma.dependenciaInstalar(
                dependencia,
                version
        );

        registrarProblema(consola, numero_de_linea, problema);
        return;
    }

    // -------------------------------------------------
    // Caso: sugerencia de reemplazar versión
    // Ejemplo:
    // - Replace mod (fabric-api) with any version between X and Y
    // -------------------------------------------------
    Matcher mReemp = P_REEMPLAZO_RANGO.matcher(lineaLimpia);
    if (mReemp.find()) {

        String dependencia = mReemp.group(1).toLowerCase();
        String vMin = normalizarVersion(mReemp.group(2));
        String vMax = normalizarVersion(mReemp.group(3));

        String problema = MonitorDePID.idioma.dependenciaReemplazarRango(
                dependencia,
                vMin,
                vMax
        );

        registrarProblema(consola, numero_de_linea, problema);
        return;
    }

    // -------------------------------------------------
    // Caso: dependencia faltante con versión mínima
    // Ejemplo:
    // requires version 1.0 or later of fabric-api
    // -------------------------------------------------
    Matcher mFalta = P_FALTANTE_MINIMO.matcher(lineaLimpia);
    if (mFalta.find()) {

        String mod = mFalta.group(1);
        String version = normalizarVersion(mFalta.group(2));
        String dependencia = mFalta.group(3).toLowerCase();

        String problema = MonitorDePID.idioma.dependenciaFaltanteMinima(
                mod,
                dependencia,
                version
        );

        registrarProblema(consola, numero_de_linea, problema);
        return;
    }

    // -------------------------------------------------
    // Caso: dependencia faltante con comodín
    // Ejemplo:
    // requires any 3.0.x version of bclib
    // -------------------------------------------------
    Matcher mWildcard = P_FALTANTE_WILDCARD.matcher(lineaLimpia);
    if (mWildcard.find()) {

        String mod = mWildcard.group(1);
        String version = normalizarVersion(mWildcard.group(2));
        String dependencia = mWildcard.group(3).toLowerCase();

        String problema = MonitorDePID.idioma.dependenciaFaltanteWildcard(
                mod,
                dependencia,
                version
        );

        registrarProblema(consola, numero_de_linea, problema);
        return;
    }

    // -------------------------------------------------
    // Caso: versión incorrecta presente
    // -------------------------------------------------
    Matcher mRango = P_RANGO_PRESENTE_INCORRECTO.matcher(lineaLimpia);
    if (mRango.find()) {

        String mod = mRango.group(1);
        String vMin = normalizarVersion(mRango.group(2));
        String vMax = normalizarVersion(mRango.group(3));
        String dependencia = mRango.group(4).toLowerCase();
        String actual = normalizarVersion(mRango.group(5));

        String problema = MonitorDePID.idioma.dependenciaVersionIncorrecta(
                mod,
                dependencia,
                vMin,
                vMax,
                actual
        );

        registrarProblema(consola, numero_de_linea, problema);
    }
}


private void registrarProblema(Consola consola, int linea, String problema) {

    // deduplicate using the logical problem only
    if (!problemasDetectados.add(problema)) {
        return;
    }

    activado = true;

    String enlace = consola.agregarErrorALectador(linea, this);

    problemasSalida.add(problema + " " + enlace);
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

	    if (problemasSalida.isEmpty()) {
	        CrashDetectorLogger.log("No problemas");
	        return "";
	    }

	    StringBuilder sb = new StringBuilder();

	    for (String p : problemasSalida) {
	        sb.append(" - ")
	          .append(p)
	          .append(Verificaciones.nl_html);
	    }

	    return sb.toString().trim();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaDependenciaMod();
	}

	@Override
	public QuickFix solucion() {

	    Builder builder = new QuickFix.Builder(nombre());

	    for (String problema : problemasDetectados) {
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
