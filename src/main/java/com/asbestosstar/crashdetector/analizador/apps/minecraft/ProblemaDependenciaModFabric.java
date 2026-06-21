package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ProblemaDependenciaModFabric implements Verificaciones {

	private boolean activado = false;

	private final Set<String> problemasDetectados = new LinkedHashSet<String>();
	private final Set<String> problemasSalida = new LinkedHashSet<String>();

	private static final String INSTALL = "- Install ";
	private static final String REPLACE = "- Replace ";
	private static final String MOD_REQUIRES = "- Mod ";
	private static final String WRONG_VERSION = "wrong version is present:";
	private static final String MISSING = "which is missing";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INSTALL, REPLACE, MOD_REQUIRES, WRONG_VERSION, MISSING };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {

		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!lineaContienePistaRapida(linea)) {
			return;
		}

		String lineaLimpia = limpiarLineaRapida(linea).trim();

		if (lineaLimpia.isEmpty()) {
			return;
		}

		String lower = lineaLimpia.toLowerCase(Locale.ROOT);

		if (procesarInstalar(consola, numero_de_linea, lineaLimpia, lower)) {
			return;
		}

		if (procesarReemplazoRango(consola, numero_de_linea, lineaLimpia, lower)) {
			return;
		}

		if (procesarFaltanteMinimo(consola, numero_de_linea, lineaLimpia, lower)) {
			return;
		}

		if (procesarFaltanteWildcard(consola, numero_de_linea, lineaLimpia, lower)) {
			return;
		}

		procesarRangoPresenteIncorrecto(consola, numero_de_linea, lineaLimpia, lower);
	}

	private boolean lineaContienePistaRapida(String linea) {
		return linea.indexOf("Install") >= 0 || linea.indexOf("Replace") >= 0 || linea.indexOf("requires") >= 0
				|| linea.indexOf("missing") >= 0 || linea.indexOf("wrong version") >= 0;
	}

	private boolean procesarInstalar(Consola consola, int numeroDeLinea, String linea, String lower) {
		if (!lower.startsWith("- install ")) {
			return false;
		}

		int inicioDep = "- install ".length();
		int coma = linea.indexOf(',', inicioDep);
		if (coma <= inicioDep) {
			return false;
		}

		int idxVersion = lower.indexOf("version ", coma);
		if (idxVersion < 0) {
			return false;
		}

		int inicioVersion = idxVersion + "version ".length();
		int idxOrLater = lower.indexOf(" or later", inicioVersion);
		if (idxOrLater <= inicioVersion) {
			return false;
		}

		String dependencia = linea.substring(inicioDep, coma).trim();
		String version = normalizarVersion(linea.substring(inicioVersion, idxOrLater));

		if (dependencia.isEmpty() || version.isEmpty()) {
			return false;
		}

		registrarProblema(consola, numeroDeLinea, MonitorDePID.idioma.dependenciaInstalar(dependencia, version));
		return true;
	}

	private boolean procesarReemplazoRango(Consola consola, int numeroDeLinea, String linea, String lower) {
		if (!lower.startsWith("- replace ")) {
			return false;
		}

		int idxWith = lower.indexOf(" with any version between ");
		if (idxWith < 0) {
			return false;
		}

		String dependencia = extraerUltimoParentesisAntesDe(linea, idxWith);
		if (dependencia.isEmpty() || !esIdModValido(dependencia)) {
			return false;
		}

		int inicioMin = idxWith + " with any version between ".length();
		int idxInclusive = lower.indexOf(" (inclusive)", inicioMin);
		if (idxInclusive <= inicioMin) {
			return false;
		}

		int idxAnd = lower.indexOf(" and ", idxInclusive);
		if (idxAnd < 0) {
			return false;
		}

		int inicioMax = idxAnd + " and ".length();
		int idxExclusive = lower.indexOf(" (exclusive)", inicioMax);
		if (idxExclusive <= inicioMax) {
			return false;
		}

		String vMin = normalizarVersion(linea.substring(inicioMin, idxInclusive));
		String vMax = normalizarVersion(linea.substring(inicioMax, idxExclusive));

		if (vMin.isEmpty() || vMax.isEmpty()) {
			return false;
		}

		registrarProblema(consola, numeroDeLinea,
				MonitorDePID.idioma.dependenciaReemplazarRango(dependencia.toLowerCase(Locale.ROOT), vMin, vMax));
		return true;
	}

	private boolean procesarFaltanteMinimo(Consola consola, int numeroDeLinea, String linea, String lower) {
		if (!lower.startsWith("- mod ")) {
			return false;
		}

		int idxRequires = lower.indexOf(" requires version ");
		if (idxRequires < 0) {
			idxRequires = lower.indexOf("requires version ");
		}

		int idxOrLater = lower.indexOf(" or later of ", idxRequires);
		int idxMissing = lower.indexOf("which is missing", idxOrLater);

		if (idxRequires < 0 || idxOrLater < 0 || idxMissing < 0) {
			return false;
		}

		String mod = extraerUltimoParentesisAntesDe(linea, idxRequires);
		if (mod.isEmpty() || !esIdModValido(mod)) {
			return false;
		}

		int inicioVersion = idxRequires
				+ textoEncontradoLength(lower, idxRequires, " requires version ", "requires version ");
		if (idxOrLater <= inicioVersion) {
			return false;
		}

		int inicioDep = idxOrLater + " or later of ".length();
		int finDep = buscarFinIdMod(linea, inicioDep);

		if (finDep <= inicioDep) {
			return false;
		}

		String version = normalizarVersion(linea.substring(inicioVersion, idxOrLater));
		String dependencia = linea.substring(inicioDep, finDep).trim().toLowerCase(Locale.ROOT);

		if (version.isEmpty() || dependencia.isEmpty() || !esIdModValido(dependencia)) {
			return false;
		}

		registrarProblema(consola, numeroDeLinea,
				MonitorDePID.idioma.dependenciaFaltanteMinima(mod, dependencia, version));
		return true;
	}

	private boolean procesarFaltanteWildcard(Consola consola, int numeroDeLinea, String linea, String lower) {
		if (!lower.startsWith("- mod ")) {
			return false;
		}

		int idxRequires = lower.indexOf(" requires any ");
		if (idxRequires < 0) {
			idxRequires = lower.indexOf("requires any ");
		}

		int idxVersionOf = lower.indexOf(" version of ", idxRequires);
		int idxMissing = lower.indexOf("which is missing", idxVersionOf);

		if (idxRequires < 0 || idxVersionOf < 0 || idxMissing < 0) {
			return false;
		}

		String mod = extraerUltimoParentesisAntesDe(linea, idxRequires);
		if (mod.isEmpty() || !esIdModValido(mod)) {
			return false;
		}

		int inicioVersion = idxRequires + textoEncontradoLength(lower, idxRequires, " requires any ", "requires any ");
		if (idxVersionOf <= inicioVersion) {
			return false;
		}

		int inicioDep = idxVersionOf + " version of ".length();
		int finDep = buscarFinIdMod(linea, inicioDep);

		if (finDep <= inicioDep) {
			return false;
		}

		String version = normalizarVersion(linea.substring(inicioVersion, idxVersionOf));
		String dependencia = linea.substring(inicioDep, finDep).trim().toLowerCase(Locale.ROOT);

		if (version.isEmpty() || dependencia.isEmpty() || !esIdModValido(dependencia)) {
			return false;
		}

		registrarProblema(consola, numeroDeLinea,
				MonitorDePID.idioma.dependenciaFaltanteWildcard(mod, dependencia, version));
		return true;
	}

	private boolean procesarRangoPresenteIncorrecto(Consola consola, int numeroDeLinea, String linea, String lower) {
		if (!lower.startsWith("- mod ")) {
			return false;
		}

		int idxBetween = lower.indexOf(" requires any version between ");
		if (idxBetween < 0) {
			idxBetween = lower.indexOf("requires any version between ");
		}

		int idxWrong = lower.indexOf("wrong version is present:");
		if (idxBetween < 0 || idxWrong < 0) {
			return false;
		}

		String mod = extraerUltimoParentesisAntesDe(linea, idxBetween);
		if (mod.isEmpty() || !esIdModValido(mod)) {
			return false;
		}

		int inicioMin = idxBetween + textoEncontradoLength(lower, idxBetween, " requires any version between ",
				"requires any version between ");

		int idxInclusive = lower.indexOf(" (inclusive)", inicioMin);
		if (idxInclusive <= inicioMin) {
			return false;
		}

		int idxAnd = lower.indexOf(" and ", idxInclusive);
		if (idxAnd < 0) {
			return false;
		}

		int inicioMax = idxAnd + " and ".length();
		int idxExclusive = lower.indexOf(" (exclusive)", inicioMax);
		if (idxExclusive <= inicioMax) {
			return false;
		}

		int idxOfMod = lower.indexOf(" of mod ", idxExclusive);
		if (idxOfMod < 0 || idxOfMod > idxWrong) {
			return false;
		}

		String dependencia = extraerUltimoParentesisAntesDe(linea, idxWrong);
		if (dependencia.isEmpty() || !esIdModValido(dependencia)) {
			return false;
		}

		int inicioActual = idxWrong + "wrong version is present:".length();
		while (inicioActual < linea.length() && Character.isWhitespace(linea.charAt(inicioActual))) {
			inicioActual++;
		}

		int finActual = linea.indexOf('!', inicioActual);
		if (finActual < 0) {
			finActual = linea.length();
		}

		if (finActual <= inicioActual) {
			return false;
		}

		String vMin = normalizarVersion(linea.substring(inicioMin, idxInclusive));
		String vMax = normalizarVersion(linea.substring(inicioMax, idxExclusive));
		String actual = normalizarVersion(linea.substring(inicioActual, finActual));

		if (vMin.isEmpty() || vMax.isEmpty() || actual.isEmpty()) {
			return false;
		}

		registrarProblema(consola, numeroDeLinea, MonitorDePID.idioma.dependenciaVersionIncorrecta(mod,
				dependencia.toLowerCase(Locale.ROOT), vMin, vMax, actual));
		return true;
	}

	private static int textoEncontradoLength(String lower, int idx, String conEspacioInicial,
			String sinEspacioInicial) {
		if (idx >= 0 && lower.startsWith(conEspacioInicial, idx)) {
			return conEspacioInicial.length();
		}
		return sinEspacioInicial.length();
	}

	private static String extraerUltimoParentesisAntesDe(String linea, int limite) {
		if (linea == null || limite <= 0) {
			return "";
		}

		int limiteSeguro = Math.min(limite, linea.length());
		int parCierra = linea.lastIndexOf(')', limiteSeguro);
		if (parCierra < 0) {
			return "";
		}

		int parAbre = linea.lastIndexOf('(', parCierra);
		if (parAbre < 0 || parCierra <= parAbre + 1) {
			return "";
		}

		return linea.substring(parAbre + 1, parCierra).trim();
	}

	private static int buscarFinIdMod(String linea, int inicio) {
		if (linea == null || inicio < 0 || inicio >= linea.length()) {
			return -1;
		}

		int i = inicio;

		while (i < linea.length() && Character.isWhitespace(linea.charAt(i))) {
			i++;
		}

		int comienzo = i;

		while (i < linea.length()) {
			char c = linea.charAt(i);

			if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_' || c == '-') {
				i++;
			} else {
				break;
			}
		}

		return i > comienzo ? i : -1;
	}

	private static boolean esIdModValido(String s) {
		if (s == null || s.isEmpty()) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean valido = (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_' || c == '-';

			if (!valido) {
				return false;
			}
		}

		return true;
	}

	private void registrarProblema(Consola consola, int linea, String problema) {
		if (!problemasDetectados.add(problema)) {
			return;
		}

		activado = true;

		String enlace = consola.agregarErrorALectador(linea, this);
		problemasSalida.add(problema + " " + enlace);
	}

	private static String limpiarLineaRapida(String s) {
		if (s == null || s.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder(s.length());
		boolean espacioPrevio = false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == '\u00A7') {
				if (i + 1 < s.length()) {
					i++;
				}
				continue;
			}

			if (c == '\uFFFD') {
				if (i + 1 < s.length()) {
					i++;
				}
				continue;
			}

			if (c == '∙') {
				c = '.';
			} else if (c == '’' || c == '‘') {
				c = '\'';
			} else if (c == '“' || c == '”') {
				c = '"';
			}

			if (Character.isWhitespace(c)) {
				if (!espacioPrevio && sb.length() > 0) {
					sb.append(' ');
					espacioPrevio = true;
				}
			} else {
				sb.append(c);
				espacioPrevio = false;
			}
		}

		int len = sb.length();
		if (len > 0 && sb.charAt(len - 1) == ' ') {
			sb.setLength(len - 1);
		}

		return sb.toString();
	}

	private static String normalizarVersion(String v) {
		if (v == null) {
			return "";
		}
		return limpiarLineaRapida(v).trim();
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
			sb.append(" - ").append(p).append(Verificaciones.nl_html);
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
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}