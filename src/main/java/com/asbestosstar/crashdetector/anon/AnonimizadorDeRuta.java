package com.asbestosstar.crashdetector.anon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AnonimizadorDeRuta {

	private static final Set<String> USUARIOS_PRIVILEGIADOS = new HashSet<String>(
			Arrays.asList("root", "admin", "administrator", "sudo", "adm", "sysadmin", "superuser", "system", "daemon",
					"bin", "sys", "games", "nobody", "ftp", "www-data", "mysql", "postgres", "nagios", "nfsnobody"));

	public static String anonimizarNombreDeUsuario(String rutaOriginal) {
		if (rutaOriginal == null || rutaOriginal.isEmpty()) {
			return rutaOriginal;
		}

		String rutaNormalizada = normalizarRuta(rutaOriginal);
		if (!esRutaAbsoluta(rutaNormalizada)) {
			return rutaOriginal;
		}

		String directorioActual = normalizarRuta(System.getProperty("user.dir"));
		String directorioHome = normalizarRuta(System.getProperty("user.home"));

		String relativaActual = relativizarSiCorresponde(rutaNormalizada, directorioActual, ".");
		if (relativaActual != null) {
			return relativaActual;
		}

		String relativaHome = relativizarSiCorresponde(rutaNormalizada, directorioHome, "~");
		if (relativaHome != null) {
			return relativaHome;
		}

		return anonimizarUsuarioEnRuta(rutaNormalizada);
	}

	private static String normalizarRuta(String ruta) {
		if (ruta == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder(ruta.length());
		boolean barraAnterior = false;

		for (int i = 0; i < ruta.length(); i++) {
			char c = ruta.charAt(i);
			if (c == '\\') {
				c = '/';
			}

			if (c == '/') {
				if (!barraAnterior) {
					sb.append(c);
				}
				barraAnterior = true;
			} else {
				sb.append(c);
				barraAnterior = false;
			}
		}

		String normalizada = sb.toString();

		if (normalizada.length() > 1 && normalizada.endsWith("/") && !esRaizWindows(normalizada)) {
			normalizada = normalizada.substring(0, normalizada.length() - 1);
		}

		return normalizada;
	}

	private static boolean esRutaAbsoluta(String ruta) {
		if (ruta == null || ruta.isEmpty()) {
			return false;
		}

		return esRutaWindowsAbsoluta(ruta) || ruta.startsWith("/");
	}

	private static String relativizarSiCorresponde(String ruta, String base, String marcador) {
		if (ruta == null || base == null || base.isEmpty()) {
			return null;
		}

		boolean esWindows = esRutaWindowsAbsoluta(ruta) && esRutaWindowsAbsoluta(base);

		String rutaCmp = esWindows ? ruta.toLowerCase(Locale.ROOT) : ruta;
		String baseCmp = esWindows ? base.toLowerCase(Locale.ROOT) : base;

		if (rutaCmp.equals(baseCmp)) {
			return marcador;
		}

		if (rutaCmp.startsWith(baseCmp + "/")) {
			return marcador + ruta.substring(base.length());
		}

		return null;
	}

	private static String anonimizarUsuarioEnRuta(String ruta) {
		String r = ruta;
		String rLower = ruta.toLowerCase(Locale.ROOT);

		if (esRutaWindowsAbsoluta(r) && empiezaConIgnoreCase(r, 2, "/Users/")) {
			return anonimizarDespuesDePrefijo(r, r.substring(0, 2) + "/Users/");
		}

		if (rLower.startsWith("/home/")) {
			return anonimizarDespuesDePrefijo(r, "/home/");
		}

		if (rLower.startsWith("/users/")) {
			return anonimizarDespuesDePrefijo(r, "/Users/");
		}

		return ruta;
	}

	private static String anonimizarDespuesDePrefijo(String ruta, String prefijo) {
		if (!empiezaConIgnoreCase(ruta, 0, prefijo)) {
			return ruta;
		}

		int inicioUsuario = prefijo.length();
		if (inicioUsuario >= ruta.length()) {
			return ruta;
		}

		int finUsuario = ruta.indexOf('/', inicioUsuario);
		if (finUsuario < 0) {
			finUsuario = ruta.length();
		}

		if (finUsuario <= inicioUsuario) {
			return ruta;
		}

		String usuario = ruta.substring(inicioUsuario, finUsuario);
		if (esUsuarioPrivilegiado(usuario)) {
			return ruta;
		}

		String resto = finUsuario < ruta.length() ? ruta.substring(finUsuario) : "";
		return prefijo + "anon" + resto;
	}

	private static boolean esUsuarioPrivilegiado(String nombre) {
		return nombre != null && USUARIOS_PRIVILEGIADOS.contains(nombre.toLowerCase(Locale.ROOT));
	}

	private static boolean esRaizWindows(String ruta) {
		return ruta.length() == 3 && Character.isLetter(ruta.charAt(0)) && ruta.charAt(1) == ':'
				&& ruta.charAt(2) == '/';
	}

	private static boolean esRutaWindowsAbsoluta(String ruta) {
		return ruta != null && ruta.length() >= 3 && Character.isLetter(ruta.charAt(0)) && ruta.charAt(1) == ':'
				&& ruta.charAt(2) == '/';
	}

	private static boolean empiezaConIgnoreCase(String texto, int inicio, String buscar) {
		return texto != null && buscar != null && inicio >= 0 && inicio + buscar.length() <= texto.length()
				&& texto.regionMatches(true, inicio, buscar, 0, buscar.length());
	}
}