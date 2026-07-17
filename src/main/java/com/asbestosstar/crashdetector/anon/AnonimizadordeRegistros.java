package com.asbestosstar.crashdetector.anon;

import com.asbestosstar.crashdetector.Statics;

import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class AnonimizadordeRegistros {

	private static final Set<String> USUARIOS_PRIVILEGIADOS = new HashSet<String>(
			Arrays.asList("root", "admin", "administrator", "sudo", "adm", "sysadmin", "superuser", "system", "daemon",
					"bin", "sys", "games", "nobody", "ftp", "www-data", "mysql", "postgres", "nagios", "nfsnobody"));

	public static String anonimizar(String contenido) {
		if (contenido == null || contenido.isEmpty()) {
			return contenido;
		}

		contenido = convertirRutasAReletivas(contenido);

		Set<String> nombresDeUsuario = obtenerNombresUsarios(contenido);

		contenido = anonimizarIP(contenido);
		contenido = anonimizarNombreDeUsuarioEnRutas(contenido);
		contenido = anonimizarUsuariosGenericos(contenido);
		contenido = anonimizarTokens(contenido);
		contenido = anonimizarUUID(contenido);
		contenido = anonimizarIPServidor(contenido);

		if (!nombresDeUsuario.isEmpty()) {
			contenido = reemplazarPalabrasIgnorandoMayusculas(contenido, nombresDeUsuario, "anon");
		}

		return contenido;
	}

	public static Set<String> obtenerNombresUsarios(String contenido) {
		Set<String> usuarios = new LinkedHashSet<String>();

		extraerUsuariosDeRutas(contenido, "C:/Users/", '/', usuarios);
		extraerUsuariosDeRutas(contenido, "C:\\Users\\", '\\', usuarios);
		extraerUsuariosDeRutas(contenido, "C:\\Users/", '/', usuarios);
		extraerUsuariosDeRutas(contenido, "/home/", '/', usuarios);
		extraerUsuariosDeRutas(contenido, "/Users/", '/', usuarios);

		extraerDespuesDeClave(contenido, "username:", usuarios);
		extraerDespuesDeClave(contenido, "username=", usuarios);
		extraerDespuesDeClave(contenido, "Setting user:", usuarios);
		extraerArgumentoComa(contenido, "--username,", usuarios);
		extraerUserLine(contenido, usuarios);
		extraerUsuarioMinecraft(contenido, usuarios);

		return usuarios;
	}

	public static String anonimizarIP(String texto) {
		texto = anonimizarIPv4(texto);
		texto = anonimizarIPv6(texto);
		return texto;
	}

	public static String anonimizarNombreDeUsuarioEnRutas(String texto) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (i < texto.length()) {
			int inicio = encontrarInicioRuta(texto, i);

			if (inicio < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			out.append(texto, i, inicio);

			int fin = inicio;
			while (fin < texto.length() && !esFinRuta(texto.charAt(fin))) {
				fin++;
			}

			String ruta = texto.substring(inicio, fin);
			out.append(AnonimizadorDeRuta.anonimizarNombreDeUsuario(ruta));
			i = fin;
		}

		return out.toString();
	}

	public static String anonimizarUsuariosGenericos(String texto) {
		texto = reemplazarDespuesDeClave(texto, "username:", "username: anon", true);
		texto = reemplazarDespuesDeClave(texto, "username=", "username: anon", true);
		texto = reemplazarUserLine(texto);
		texto = reemplazarUsuarioMinecraft(texto);
		texto = reemplazarUuidMinecraft(texto);
		texto = reemplazarDespuesDeClave(texto, "Setting user:", "Setting user: anon", false);
		texto = reemplazarArgumentoComa(texto, "--username,", "--username, anon");
		return texto;
	}

	public static String anonimizarTokens(String texto) {
		texto = reemplazarSessionId(texto);
		texto = reemplazarArgumentoToken(texto, "--accessToken", "--accessToken anon:anon");
		texto = reemplazarBearer(texto);
		texto = reemplazarSessionSimple(texto);
		texto = reemplazarArgumentoComa(texto, "--clientId,", "--clientId, anon");
		texto = reemplazarArgumentoComa(texto, "--xuid,", "--xuid, anon");
		return texto;
	}

	private static String anonimizarUUID(String texto) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (i < texto.length()) {
			if (hayUuidConGuiones(texto, i)) {
				out.append("anon-anon-anon-anon-anon");
				i += 36;
			} else if (hayUuidSinGuiones(texto, i)) {
				out.append("anon");
				i += 32;
			} else {
				out.append(texto.charAt(i));
				i++;
			}
		}

		return out.toString();
	}

	public static String anonimizarIPServidor(String texto) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (i < texto.length()) {
			int pos = indexOfIgnoreCase(texto, "connect '", i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			out.append(texto, i, pos);

			int hostInicio = pos + "connect '".length();
			int dosPuntos = texto.indexOf(':', hostInicio);
			int cierre = texto.indexOf('\'', hostInicio);

			if (dosPuntos > hostInicio && cierre > dosPuntos && soloDigitos(texto, dosPuntos + 1, cierre)) {
				out.append("connect 'anon:****'");
				i = cierre + 1;
			} else {
				out.append(texto.charAt(pos));
				i = pos + 1;
			}
		}

		return out.toString();
	}

	public static String convertirRutasAReletivas(String contenido) {
		if (contenido == null || contenido.isEmpty()) {
			return contenido;
		}

		Path basePath = Statics.CARPETA_DE_APP.toPath().toAbsolutePath().normalize();

		String base = basePath.toString();
		base = base.replace("\\", "/");
		contenido = contenido.replace("\\", "/");

		try {
			base = URLDecoder.decode(base);
			contenido = URLDecoder.decode(contenido);
		} catch (Throwable t) {
			// Mantener contenido original si hay escape URL inválido.
		}

		if (base.endsWith("/")) {
			base = base.substring(0, base.length() - 1);
		}

		contenido = reemplazarLiteral(contenido, base + "/", "./");
		contenido = reemplazarLiteral(contenido, base, ".");

		return contenido;
	}

	private static String anonimizarIPv4(String texto) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (i < texto.length()) {
			IPv4Match m = leerIPv4(texto, i);
			if (m != null && !esIPv4Permitida(m.ip) && !pareceVersionAntes(texto, i)) {
				out.append("anon");
				i = m.fin;
			} else {
				out.append(texto.charAt(i));
				i++;
			}
		}

		return out.toString();
	}

	private static String anonimizarIPv6(String texto) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (i < texto.length()) {
			int fin = leerPosibleIPv6(texto, i);
			if (fin > i) {
				String ip = texto.substring(i, fin);
				if (!"::1".equals(ip) && !"::".equals(ip)) {
					out.append("anon");
				} else {
					out.append(ip);
				}
				i = fin;
			} else {
				out.append(texto.charAt(i));
				i++;
			}
		}

		return out.toString();
	}

	private static void extraerUsuariosDeRutas(String texto, String prefijo, char separador, Set<String> usuarios) {
		int i = 0;
		while (true) {
			int pos = indexOfIgnoreCase(texto, prefijo, i);
			if (pos < 0) {
				return;
			}

			int inicio = pos + prefijo.length();
			int fin = texto.indexOf(separador, inicio);

			if (fin > inicio) {
				agregarUsuarioSiValido(texto.substring(inicio, fin), usuarios);
				i = fin + 1;
			} else {
				i = inicio;
			}
		}
	}

	private static void extraerDespuesDeClave(String texto, String clave, Set<String> usuarios) {
		int i = 0;
		while (true) {
			int pos = indexOfIgnoreCase(texto, clave, i);
			if (pos < 0) {
				return;
			}

			int inicio = pos + clave.length();
			while (inicio < texto.length() && Character.isWhitespace(texto.charAt(inicio))) {
				inicio++;
			}

			int fin = leerNombreSimple(texto, inicio);
			if (fin > inicio) {
				agregarUsuarioSiValido(texto.substring(inicio, fin), usuarios);
			}

			i = Math.max(inicio + 1, fin);
		}
	}

	private static void extraerArgumentoComa(String texto, String clave, Set<String> usuarios) {
		int i = 0;
		while (true) {
			int pos = indexOfIgnoreCase(texto, clave, i);
			if (pos < 0) {
				return;
			}

			int inicio = pos + clave.length();
			while (inicio < texto.length() && Character.isWhitespace(texto.charAt(inicio))) {
				inicio++;
			}

			int fin = leerNombreSimple(texto, inicio);
			if (fin > inicio) {
				agregarUsuarioSiValido(texto.substring(inicio, fin), usuarios);
			}

			i = Math.max(inicio + 1, fin);
		}
	}

	private static void extraerUserLine(String texto, Set<String> usuarios) {
		int i = 0;
		while (true) {
			int pos = indexOfIgnoreCase(texto, "User ", i);
			if (pos < 0) {
				return;
			}

			int inicio = pos + 5;
			int fin = inicio;
			while (fin < texto.length() && esNombreLineaUser(texto.charAt(fin))) {
				fin++;
			}

			if (fin > inicio) {
				agregarUsuarioSiValido(texto.substring(inicio, fin), usuarios);
			}

			i = Math.max(inicio + 1, fin);
		}
	}

	private static void extraerUsuarioMinecraft(String texto, Set<String> usuarios) {
		int i = 0;
		while (i < texto.length()) {
			if (!esCaracterNombreMc(texto.charAt(i))) {
				i++;
				continue;
			}

			int inicio = i;
			int fin = leerNombreSimple(texto, i);

			if (fin - inicio >= 3 && fin - inicio <= 16) {
				int j = fin;
				while (j < texto.length() && Character.isWhitespace(texto.charAt(j))) {
					j++;
				}

				if (j < texto.length() && texto.charAt(j) == ':') {
					j++;
					while (j < texto.length() && Character.isWhitespace(texto.charAt(j))) {
						j++;
					}

					if (empiezaConAlgunaIgnoreCase(texto, j, "logged in", "joined", "has", "connected",
							"disconnected")) {
						agregarUsuarioSiValido(texto.substring(inicio, fin), usuarios);
					}
				}
			}

			i = Math.max(fin, i + 1);
		}
	}

	private static String reemplazarDespuesDeClave(String texto, String clave, String reemplazo, boolean ignorarCase) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = ignorarCase ? indexOfIgnoreCase(texto, clave, i) : texto.indexOf(clave, i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			int inicio = pos + clave.length();
			int valorInicio = inicio;
			while (valorInicio < texto.length() && Character.isWhitespace(texto.charAt(valorInicio))) {
				valorInicio++;
			}

			int fin = leerNombreSimple(texto, valorInicio);
			if (fin > valorInicio) {
				out.append(texto, i, pos);
				out.append(reemplazo);
				i = fin;
			} else {
				out.append(texto, i, inicio);
				i = inicio;
			}
		}

		return out.toString();
	}

	private static String reemplazarArgumentoComa(String texto, String clave, String reemplazo) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = indexOfIgnoreCase(texto, clave, i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			int inicio = pos + clave.length();
			while (inicio < texto.length() && Character.isWhitespace(texto.charAt(inicio))) {
				inicio++;
			}

			int fin = inicio;
			while (fin < texto.length() && texto.charAt(fin) != ',' && !Character.isWhitespace(texto.charAt(fin))) {
				fin++;
			}

			if (fin > inicio) {
				out.append(texto, i, pos);
				out.append(reemplazo);
				i = fin;
			} else {
				out.append(texto, i, inicio);
				i = inicio;
			}
		}

		return out.toString();
	}

	private static String reemplazarArgumentoToken(String texto, String clave, String reemplazo) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = indexOfIgnoreCase(texto, clave, i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			int inicio = pos + clave.length();

			if (inicio < texto.length() && texto.charAt(inicio) == ',') {
				inicio++;
			}

			while (inicio < texto.length() && Character.isWhitespace(texto.charAt(inicio))) {
				inicio++;
			}

			int fin = inicio;
			while (fin < texto.length() && texto.charAt(fin) != ',' && !Character.isWhitespace(texto.charAt(fin))) {
				fin++;
			}

			if (fin > inicio) {
				out.append(texto, i, pos);
				out.append(reemplazo);
				i = fin;
			} else {
				out.append(texto, i, inicio);
				i = inicio;
			}
		}

		return out.toString();
	}

	private static String reemplazarUserLine(String texto) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = indexOfIgnoreCase(texto, "User ", i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			int inicio = pos + 5;
			int fin = inicio;
			while (fin < texto.length() && esNombreLineaUser(texto.charAt(fin))) {
				fin++;
			}

			if (fin > inicio) {
				out.append(texto, i, pos);
				out.append("User anon");
				i = fin;
			} else {
				out.append(texto, i, inicio);
				i = inicio;
			}
		}

		return out.toString();
	}

	private static String reemplazarUsuarioMinecraft(String texto) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (i < texto.length()) {
			if (!esCaracterNombreMc(texto.charAt(i))) {
				out.append(texto.charAt(i));
				i++;
				continue;
			}

			int inicio = i;
			int fin = leerNombreSimple(texto, i);
			boolean reemplazar = false;

			if (fin - inicio >= 3 && fin - inicio <= 16) {
				int j = fin;
				while (j < texto.length() && Character.isWhitespace(texto.charAt(j))) {
					j++;
				}

				if (j < texto.length() && texto.charAt(j) == ':') {
					j++;
					while (j < texto.length() && Character.isWhitespace(texto.charAt(j))) {
						j++;
					}

					reemplazar = empiezaConAlgunaIgnoreCase(texto, j, "logged in", "joined", "has", "connected",
							"disconnected");
				}
			}

			if (reemplazar) {
				out.append("anon");
			} else {
				out.append(texto, inicio, fin);
			}

			i = fin;
		}

		return out.toString();
	}

	private static String reemplazarUuidMinecraft(String texto) {
		String clave = "uuid='";
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = indexOfIgnoreCase(texto, clave, i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			int inicio = pos + clave.length();
			int fin = inicio;
			while (fin < texto.length() && esHexOGuion(texto.charAt(fin))) {
				fin++;
			}

			if (fin > inicio) {
				out.append(texto, i, pos);
				out.append("uuid='anon-anon-anon-anon-anon");
				i = fin;
			} else {
				out.append(texto, i, inicio);
				i = inicio;
			}
		}

		return out.toString();
	}

	private static String reemplazarSessionId(String texto) {
		String clave = "Session ID is token:";
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = indexOfIgnoreCase(texto, clave, i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			int inicio = pos + clave.length();
			int dosPuntos = texto.indexOf(':', inicio);
			if (dosPuntos < 0) {
				out.append(texto, i, inicio);
				i = inicio;
				continue;
			}

			int fin = dosPuntos + 1;
			while (fin < texto.length() && !Character.isWhitespace(texto.charAt(fin))) {
				fin++;
			}

			out.append(texto, i, pos);
			out.append("Session ID is token:anon:anon");
			i = fin;
		}

		return out.toString();
	}

	private static String reemplazarBearer(String texto) {
		String clave = "Bearer ";
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = indexOfIgnoreCase(texto, clave, i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			int inicio = pos + clave.length();
			int fin = inicio;
			while (fin < texto.length() && esCaracterTokenBearer(texto.charAt(fin))) {
				fin++;
			}

			if (fin > inicio) {
				out.append(texto, i, pos);
				out.append("Bearer anon:anon");
				i = fin;
			} else {
				out.append(texto, i, inicio);
				i = inicio;
			}
		}

		return out.toString();
	}

	private static String reemplazarSessionSimple(String texto) {
		String clave = "session: ";
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = indexOfIgnoreCase(texto, clave, i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			int inicio = pos + clave.length();
			int fin = inicio;
			while (fin < texto.length() && esLetraNumeroGuion(texto.charAt(fin))) {
				fin++;
			}

			if (fin > inicio) {
				out.append(texto, i, pos);
				out.append("session: anon");
				i = fin;
			} else {
				out.append(texto, i, inicio);
				i = inicio;
			}
		}

		return out.toString();
	}

	private static String reemplazarPalabrasIgnorandoMayusculas(String texto, Set<String> palabras, String reemplazo) {
		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (i < texto.length()) {
			String encontrada = null;

			for (String palabra : palabras) {
				if (palabra == null || palabra.isEmpty()) {
					continue;
				}

				int len = palabra.length();
				if (i + len <= texto.length() && texto.regionMatches(true, i, palabra, 0, len)) {
					encontrada = palabra;
					break;
				}
			}

			if (encontrada != null) {
				out.append(reemplazo);
				i += encontrada.length();
			} else {
				out.append(texto.charAt(i));
				i++;
			}
		}

		return out.toString();
	}

	private static String reemplazarLiteral(String texto, String buscar, String reemplazo) {
		if (buscar == null || buscar.isEmpty()) {
			return texto;
		}

		StringBuilder out = new StringBuilder(texto.length());
		int i = 0;

		while (true) {
			int pos = texto.indexOf(buscar, i);
			if (pos < 0) {
				out.append(texto, i, texto.length());
				break;
			}

			out.append(texto, i, pos);
			out.append(reemplazo);
			i = pos + buscar.length();
		}

		return out.toString();
	}

	private static int encontrarInicioRuta(String texto, int desde) {
		int mejor = -1;

		for (int i = desde; i < texto.length(); i++) {
			if (pareceInicioRutaWindows(texto, i) || empiezaConIgnoreCase(texto, i, "/home/")
					|| empiezaConIgnoreCase(texto, i, "/Users/")) {
				mejor = i;
				break;
			}
		}

		return mejor;
	}

	private static boolean pareceInicioRutaWindows(String s, int i) {
		return i + 3 < s.length() && Character.isLetter(s.charAt(i)) && s.charAt(i + 1) == ':'
				&& (s.charAt(i + 2) == '/' || s.charAt(i + 2) == '\\');
	}

	private static boolean esFinRuta(char c) {
		return Character.isWhitespace(c) || c == '"' || c == '\'' || c == '<' || c == '>' || c == '|';
	}

	private static IPv4Match leerIPv4(String texto, int inicio) {
		if (inicio > 0) {
			char prev = texto.charAt(inicio - 1);
			if (Character.isLetterOrDigit(prev) || prev == '-' || prev == '_') {
				return null;
			}
		}

		int i = inicio;
		int puntos = 0;

		while (puntos < 4) {
			int numInicio = i;
			int digitos = 0;

			while (i < texto.length() && Character.isDigit(texto.charAt(i)) && digitos < 3) {
				i++;
				digitos++;
			}

			if (digitos == 0) {
				return null;
			}

			if (puntos < 3) {
				if (i >= texto.length() || texto.charAt(i) != '.') {
					return null;
				}
				i++;
			}

			puntos++;

			if (i == numInicio) {
				return null;
			}
		}

		if (i < texto.length() && Character.isDigit(texto.charAt(i))) {
			return null;
		}

		return new IPv4Match(texto.substring(inicio, i), i);
	}

	private static boolean pareceVersionAntes(String texto, int inicio) {
		String antes = texto.substring(Math.max(0, inicio - 12), inicio).toLowerCase(Locale.ROOT);
		return antes.endsWith("version: ") || antes.endsWith("version ");
	}

	private static boolean esIPv4Permitida(String ip) {
		if ("0.0.0.0".equals(ip) || "1.0.0.1".equals(ip) || "8.8.8.8".equals(ip) || "8.8.4.4".equals(ip)) {
			return true;
		}

		return ip.startsWith("127.");
	}

	private static int leerPosibleIPv6(String texto, int inicio) {
		if (!esHexODosPuntos(texto.charAt(inicio))) {
			return -1;
		}

		int i = inicio;
		int dosPuntos = 0;

		while (i < texto.length() && esHexODosPuntos(texto.charAt(i))) {
			if (texto.charAt(i) == ':') {
				dosPuntos++;
			}
			i++;
		}

		if (dosPuntos < 2) {
			return -1;
		}

		return i;
	}

	private static boolean hayUuidConGuiones(String s, int i) {
		int[] guiones = { 8, 13, 18, 23 };

		if (i + 36 > s.length()) {
			return false;
		}

		for (int x = 0; x < 36; x++) {
			char c = s.charAt(i + x);
			boolean debeSerGuion = false;

			for (int g : guiones) {
				if (x == g) {
					debeSerGuion = true;
					break;
				}
			}

			if (debeSerGuion) {
				if (c != '-') {
					return false;
				}
			} else if (!esHex(c)) {
				return false;
			}
		}

		return true;
	}

	private static boolean hayUuidSinGuiones(String s, int i) {
		if (i + 32 > s.length()) {
			return false;
		}

		for (int x = 0; x < 32; x++) {
			if (!esHex(s.charAt(i + x))) {
				return false;
			}
		}

		return true;
	}

	private static int leerNombreSimple(String texto, int inicio) {
		int i = inicio;
		while (i < texto.length() && esCaracterNombreMc(texto.charAt(i))) {
			i++;
		}
		return i;
	}

	private static void agregarUsuarioSiValido(String usuario, Set<String> usuarios) {
		if (usuario == null) {
			return;
		}

		String u = usuario.trim();
		if (u.length() < 3 || USUARIOS_PRIVILEGIADOS.contains(u.toLowerCase(Locale.ROOT))) {
			return;
		}

		usuarios.add(u);
	}

	private static boolean empiezaConAlgunaIgnoreCase(String s, int pos, String... opciones) {
		for (String op : opciones) {
			if (empiezaConIgnoreCase(s, pos, op)) {
				return true;
			}
		}
		return false;
	}

	private static boolean empiezaConIgnoreCase(String s, int pos, String buscar) {
		return pos >= 0 && pos + buscar.length() <= s.length()
				&& s.regionMatches(true, pos, buscar, 0, buscar.length());
	}

	private static int indexOfIgnoreCase(String s, String buscar, int desde) {
		int max = s.length() - buscar.length();
		for (int i = Math.max(0, desde); i <= max; i++) {
			if (s.regionMatches(true, i, buscar, 0, buscar.length())) {
				return i;
			}
		}
		return -1;
	}

	private static boolean soloDigitos(String s, int inicio, int fin) {
		if (inicio >= fin) {
			return false;
		}

		for (int i = inicio; i < fin; i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	private static boolean esNombreLineaUser(char c) {
		return Character.isLetterOrDigit(c) || c == '.' || c == '_' || c == '-';
	}

	private static boolean esCaracterNombreMc(char c) {
		return Character.isLetterOrDigit(c) || c == '_';
	}

	private static boolean esLetraNumeroGuion(char c) {
		return Character.isLetterOrDigit(c) || c == '-';
	}

	private static boolean esHexOGuion(char c) {
		return esHex(c) || c == '-';
	}

	private static boolean esHexODosPuntos(char c) {
		return esHex(c) || c == ':';
	}

	private static boolean esHex(char c) {
		return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
	}

	private static boolean esCaracterTokenBearer(char c) {
		return Character.isLetterOrDigit(c) || c == '-' || c == '.' || c == '_' || c == '~' || c == ':' || c == '/'
				|| c == '?' || c == '#' || c == '[' || c == ']' || c == '@' || c == '!' || c == '$' || c == '&'
				|| c == '\'' || c == '(' || c == ')' || c == '*' || c == '+' || c == ',' || c == ';' || c == '=';
	}

	private static class IPv4Match {
		final String ip;
		final int fin;

		IPv4Match(String ip, int fin) {
			this.ip = ip;
			this.fin = fin;
		}
	}

	public static void main(String[] args) {
		String[] logs = { "Session ID is token:abc123:xyz789", "User john.doe logged in from 192.168.1.1",
				"C:\\Users\\john.doe\\AppData\\Roaming\\.minecraft\\logs\\latest.log",
				"/home/johndoe/.minecraft/logs/latest.log", "UserUUID: 123e4567-e89b-12d3-a456-567890abcdef",
				"connect 'play.minecraftserver.com:25565'", "User root logged in from 8.8.8.8",
				"UUID: 123e4567e89b12d3a456567890abcdef", "Connected with username: CarlosSilva",
				"Connecting to server at 2001:0db8:85a3::8a2e:0370:7334",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx.xxxxx" };

		for (String log : logs) {
			System.out.println("Original     : " + log);
			System.out.println("Anonimizado  : " + anonimizar(log));
			System.out.println();
		}
	}
}