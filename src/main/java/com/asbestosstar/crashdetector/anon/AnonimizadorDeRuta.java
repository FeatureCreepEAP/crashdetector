package com.asbestosstar.crashdetector.anon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnonimizadorDeRuta {

	private static final Set<String> USUARIOS_PRIVILEGIADOS = new HashSet<>(
			Arrays.asList("root", "admin", "administrator", "sudo", "adm", "sysadmin", "superuser", "system", "daemon",
					"bin", "sys", "games", "nobody", "ftp", "www-data", "mysql", "postgres", "nagios", "nfsnobody"));

	private static final Pattern PATRON_RUTA_WINDOWS_USUARIO = Pattern
			.compile("^(?<prefijo>[A-Za-z]:/Users/)(?<usuario>[^/]+)(?<resto>/.*)?$", Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_RUTA_UNIX_USUARIO = Pattern
			.compile("^(?<prefijo>/(?:home|Users)/)(?<usuario>[^/]+)(?<resto>/.*)?$", Pattern.CASE_INSENSITIVE);

	/**
	 * Anónimiza una ruta:
	 *
	 * - Si está dentro del directorio actual, reemplaza el prefijo por "." - Si
	 * está dentro del home actual, reemplaza el prefijo por "~" - Si no está en
	 * ninguno de esos dos, anonimiza el nombre de usuario
	 *
	 * Siempre devuelve la ruta con separadores "/" para que el resultado sea
	 * consistente entre plataformas y en logs.
	 *
	 * @param rutaOriginal Ruta original.
	 * @return Ruta anonimizada.
	 */
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

		// Prioridad 1: directorio actual -> ./...
		String relativaActual = relativizarSiCorresponde(rutaNormalizada, directorioActual, ".");
		if (relativaActual != null) {
			return relativaActual;
		}

		// Prioridad 2: home -> ~/...
		String relativaHome = relativizarSiCorresponde(rutaNormalizada, directorioHome, "~");
		if (relativaHome != null) {
			return relativaHome;
		}

		// Si no pertenece al directorio actual ni al home, anonimizar usuario
		return anonimizarUsuarioEnRuta(rutaNormalizada);
	}

	/**
	 * Normaliza la ruta para comparaciones de texto: - Convierte "\" en "/" -
	 * Elimina barras repetidas - Elimina barra final, salvo si es raíz
	 */
	private static String normalizarRuta(String ruta) {
		if (ruta == null) {
			return null;
		}

		String normalizada = ruta.replace('\\', '/');

		// Evitar "//" repetidos en la parte restante de la ruta
		while (normalizada.contains("//")) {
			normalizada = normalizada.replace("//", "/");
		}

		// Eliminar barra final si no es raíz tipo "C:/" o "/"
		if (normalizada.length() > 1 && normalizada.endsWith("/")) {
			if (!normalizada.matches("^[A-Za-z]:/$")) {
				normalizada = normalizada.substring(0, normalizada.length() - 1);
			}
		}

		return normalizada;
	}

	/**
	 * Determina si una ruta es absoluta en formato Windows o Unix.
	 */
	private static boolean esRutaAbsoluta(String ruta) {
		if (ruta == null || ruta.isEmpty()) {
			return false;
		}

		return ruta.matches("^[A-Za-z]:/.*") || ruta.startsWith("/");
	}

	/**
	 * Intenta relativizar una ruta respecto a un prefijo base.
	 *
	 * Ejemplos: - base = C:/Users/Pepe/.minecraft, marcador = "." - ruta =
	 * C:/Users/Pepe/.minecraft/logs/latest.log -> ./logs/latest.log
	 *
	 * - base = C:/Users/Pepe, marcador = "~" - ruta =
	 * C:/Users/Pepe/storage/file.txt -> ~/storage/file.txt
	 */
	private static String relativizarSiCorresponde(String ruta, String base, String marcador) {
		if (ruta == null || base == null || base.isEmpty()) {
			return null;
		}

		boolean esWindows = ruta.matches("^[A-Za-z]:/.*") && base.matches("^[A-Za-z]:/.*");

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

	/**
	 * Reemplaza el nombre de usuario por "anon" si la ruta contiene un home de
	 * Windows o Unix/macOS.
	 */
	private static String anonimizarUsuarioEnRuta(String ruta) {
		Matcher matcherWindows = PATRON_RUTA_WINDOWS_USUARIO.matcher(ruta);
		if (matcherWindows.matches()) {
			String usuario = matcherWindows.group("usuario");
			if (!esUsuarioPrivilegiado(usuario)) {
				String resto = matcherWindows.group("resto");
				return matcherWindows.group("prefijo") + "anon" + (resto == null ? "" : resto);
			}
			return ruta;
		}

		Matcher matcherUnix = PATRON_RUTA_UNIX_USUARIO.matcher(ruta);
		if (matcherUnix.matches()) {
			String usuario = matcherUnix.group("usuario");
			if (!esUsuarioPrivilegiado(usuario)) {
				String resto = matcherUnix.group("resto");
				return matcherUnix.group("prefijo") + "anon" + (resto == null ? "" : resto);
			}
			return ruta;
		}

		return ruta;
	}

	private static boolean esUsuarioPrivilegiado(String nombre) {
		return nombre != null && USUARIOS_PRIVILEGIADOS.contains(nombre.toLowerCase(Locale.ROOT));
	}
}