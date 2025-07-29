package com.asbestosstar.crashdetector.anon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnonimizadordeRegistros {

	private static final Pattern PATRON_IPV4 = Pattern
			.compile("(?<!version: )(?<!version )(?<!([0-9]|-|\\w))" + "([0-9]{1,3}\\.){3}[0-9]{1,3}(?![0-9])");

	private static final Pattern[] FILTROS_IPV4 = { Pattern.compile("^127\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$"), // loop‑back
			Pattern.compile("^0\\.0\\.0\\.0$"), Pattern.compile("^1\\.0\\.0\\.1$"), Pattern.compile("^8\\.8\\.8\\.8$"),
			Pattern.compile("^8\\.8\\.4\\.4$") };

	/* ---------- IPv6 (nuevo patrón simplificado) ---------------------- */
	/*
	 * Idea: cualquier secuencia con 2‑7 «:» y compuesta sólo por dígitos hex / ‘:’
	 * → casi todos los literales IPv6 válidos.
	 */
	private static final Pattern PATRON_IPV6 = Pattern.compile("\\b(?:[0-9A-Fa-f]{0,4}:){2,7}[0-9A-Fa-f]{0,4}\\b");

	private static final Pattern[] FILTROS_IPV6 = { Pattern.compile("^::1$"), Pattern.compile("^::$") };

	private static final Pattern[] PATRONES_USUARIO_WINDOWS = {
			Pattern.compile("C:\\\\Users\\\\([^\\\\]+)\\\\", Pattern.CASE_INSENSITIVE),
			Pattern.compile("C:\\\\/Users\\//([^\\//]+)\\//", Pattern.CASE_INSENSITIVE),
			Pattern.compile("C:\\\\Users\\//([^\\//]+)\\//", Pattern.CASE_INSENSITIVE) };

	private static final Pattern[] PATRONES_USUARIO_UNIX = {
			Pattern.compile("(?<!\\w)/home/([^/]+)/", Pattern.CASE_INSENSITIVE),
			Pattern.compile("(?<!\\w)/Users/([^/]+)/", Pattern.CASE_INSENSITIVE) };

	private static final Pattern PATRON_USUARIO_GENERIC = Pattern.compile("\\busername[:=]\\s*([A-Za-z0-9_]{3,16})\\b",
			Pattern.CASE_INSENSITIVE);

	
 private static final Pattern PATRON_USUARIO_MINECRAFT = Pattern.compile(	
		    "\\b([A-Za-z0-9_]{3,16})\\b(?=\\s*:(?:\\s*logged in| joined| has| connected| disconnected))");
	
 
	private static final Pattern PATRON_USER_LINE = Pattern.compile("\\bUser\\s+([A-Za-z0-9._-]+)\\b",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern PATRON_USERNAME_SETTING = Pattern.compile("Setting user:\\s*([A-Za-z0-9_]{3,16})");

	private static final Pattern PATRON_USERNAME_ARGUMENT = Pattern.compile("--username,\\s*([A-Za-z0-9_]{3,16})");

	private static final Pattern PATRON_UUID_MINECRAFT = Pattern.compile("uuid='[0-9A-Fa-f\\-]+(?='|$)");

	private static final Pattern PATRON_ID_SESION = Pattern.compile("Session ID is token:[^:]+:[^\\s]+");
	//private static final Pattern PATRON_TOKEN_ACCESO = Pattern.compile("--accessToken [^ ]+");
	private static final Pattern PATRON_TOKEN_BEARER = Pattern.compile("Bearer [A-Za-z0-9-._~:/?#\\[\\]@!$&'()*+,;=]+");
	private static final Pattern PATRON_TOKEN_SESION = Pattern.compile("session: [A-Za-z0-9-]+");

	private static final Pattern PATRON_UUID_NORMAL = Pattern
			.compile("[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}");
	private static final Pattern PATRON_UUID_SIN_GUIONES = Pattern.compile("[0-9A-Fa-f]{32}");

	private static final Pattern PATRON_IP_SERVIDOR = Pattern.compile("connect '([\\w\\.-]+):(\\d+)'",
			Pattern.CASE_INSENSITIVE);

	private static final List<String> USUARIOS_PRIVILEGIADOS = Arrays.asList("root", "admin", "administrator", "sudo",
			"adm", "sysadmin", "superuser", "system", "daemon", "bin", "sys", "games", "nobody", "ftp", "www-data",
			"mysql", "postgres", "nagios", "nfsnobody");

	private static final Pattern PATRON_TOKEN_ACCESO_PARAM = Pattern
			.compile("--accessToken(?:\\s*,\\s*|\\s+)([^\\s,]+)");
	
	private static final Pattern PATRON_CLIENT_ID = Pattern.compile("--clientId,\\s*([^,]+)");
	private static final Pattern PATRON_XUID = Pattern.compile("--xuid,\\s*([^,]+)");
	

	public static String anonimizar(String contenido) {
		Set<String> nombres_de_usario = obtenerNombresUsarios(contenido);

		contenido = anonimizarIP(contenido);
		contenido = anonimizarNombreDeUsuarioEnRutas(contenido);
		contenido = anonimizarUsuariosGenericos(contenido);
		contenido = anonimizarTokens(contenido);
		contenido = anonimizarUUID(contenido);
		contenido = anonimizarIPServidor(contenido);

		for (String usario : nombres_de_usario) {
			contenido = contenido.replaceAll("(?i)" + Pattern.quote(usario), "anon");
		}

		return contenido;
	}


	  private static Set<String> obtenerNombresUsarios(String contenido) {
	        Set<String> usuarios = new HashSet<>();

	        for (Pattern p : PATRONES_USUARIO_WINDOWS) {
	            Matcher m = p.matcher(contenido);
	            while (m.find()) {
	                String user = m.group(1);
	                if (!USUARIOS_PRIVILEGIADOS.contains(user.toLowerCase())) {
	                    usuarios.add(user);
	                }
	            }
	        }

	        for (Pattern p : PATRONES_USUARIO_UNIX) {
	            Matcher m = p.matcher(contenido);
	            while (m.find()) {
	                String user = m.group(1);
	                if (!USUARIOS_PRIVILEGIADOS.contains(user.toLowerCase())) {
	                    usuarios.add(user);
	                }
	            }
	        }

	        Matcher mGeneric = PATRON_USUARIO_GENERIC.matcher(contenido);
	        while (mGeneric.find()) {
	            usuarios.add(mGeneric.group(1));
	        }

	        Matcher mMc = PATRON_USUARIO_MINECRAFT.matcher(contenido);
	        while (mMc.find()) {
	            usuarios.add(mMc.group(1));
	        }

	        Matcher mUserLine = PATRON_USER_LINE.matcher(contenido);
	        while (mUserLine.find()) {
	            usuarios.add(mUserLine.group(1));
	        }

	        Matcher mSetting = PATRON_USERNAME_SETTING.matcher(contenido);
	        while (mSetting.find()) {
	            usuarios.add(mSetting.group(1));
	        }

	        Matcher mArg = PATRON_USERNAME_ARGUMENT.matcher(contenido);
	        while (mArg.find()) {
	            usuarios.add(mArg.group(1));
	        }

	        return usuarios;
	    }
	
	
	private static String anonimizarIP(String linea) {
		/* IPv4 */
		Matcher m4 = PATRON_IPV4.matcher(linea);
		if (m4.find()) {
			boolean permitido = false;
			for (Pattern f : FILTROS_IPV4)
				if (f.matcher(linea).find()) {
					permitido = true;
					break;
				}
			if (!permitido)
				linea = m4.replaceAll("anon");
		}
		/* IPv6 */
		Matcher m6 = PATRON_IPV6.matcher(linea);
		if (m6.find()) {
			boolean permitido = false;
			for (Pattern f : FILTROS_IPV6)
				if (f.matcher(linea).find()) {
					permitido = true;
					break;
				}
			if (!permitido)
				linea = m6.replaceAll("anon");
		}
		return linea;
	}

	private static String anonimizarNombreDeUsuarioEnRutas(String linea) {
		// Windows
		for (Pattern p : PATRONES_USUARIO_WINDOWS) {
			Matcher m = p.matcher(linea);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String user = m.group(1);
				if (USUARIOS_PRIVILEGIADOS.contains(user.toLowerCase()))
					m.appendReplacement(sb, m.group());
				else
					m.appendReplacement(sb, "C\\\\Users\\\\anon\\\\"); // se duplica ‘\’ para regex
			}
			m.appendTail(sb);
			linea = sb.toString();
		}
		/* Unix / macOS */
		for (Pattern p : PATRONES_USUARIO_UNIX) {
			Matcher m = p.matcher(linea);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String user = m.group(1);
				if (USUARIOS_PRIVILEGIADOS.contains(user.toLowerCase()))
					m.appendReplacement(sb, m.group());
				else
					m.appendReplacement(sb, m.group(0).replace(user, "anon"));
			}
			m.appendTail(sb);
			linea = sb.toString();
		}
		return linea;
	}

	private static String anonimizarUsuariosGenericos(String linea) {
		linea = PATRON_USUARIO_GENERIC.matcher(linea).replaceAll("username: anon");

		linea = PATRON_USER_LINE.matcher(linea).replaceAll("User anon");

		linea = PATRON_USUARIO_MINECRAFT.matcher(linea).replaceAll("anon");

		linea = PATRON_UUID_MINECRAFT.matcher(linea).replaceAll("uuid='anon-anon-anon-anon-anon'");

		linea = PATRON_USERNAME_SETTING.matcher(linea).replaceAll("Setting user: anon");

		linea = PATRON_USERNAME_ARGUMENT.matcher(linea).replaceAll("--username, anon");

		return linea;
	}
	
    private static String anonimizarTokens(String linea) {
        linea = PATRON_ID_SESION.matcher(linea).replaceAll("Session ID is token:anon:anon");
        linea = PATRON_TOKEN_ACCESO_PARAM.matcher(linea).replaceAll("--accessToken anon:anon");
        linea = PATRON_TOKEN_BEARER.matcher(linea).replaceAll("Bearer anon:anon");
        linea = PATRON_TOKEN_SESION.matcher(linea).replaceAll("session: anon");
        
        linea = PATRON_CLIENT_ID.matcher(linea).replaceAll("--clientId, anon");
        linea = PATRON_XUID.matcher(linea).replaceAll("--xuid, anon");
        
        return linea;
    }

	private static String anonimizarUUID(String linea) {
		linea = PATRON_UUID_NORMAL.matcher(linea).replaceAll("anon-anon-anon-anon-anon");
		linea = PATRON_UUID_SIN_GUIONES.matcher(linea).replaceAll("anon");
		return linea;
	}

	private static String anonimizarIPServidor(String linea) {
		return PATRON_IP_SERVIDOR.matcher(linea).replaceAll("connect 'anon:****'");
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
