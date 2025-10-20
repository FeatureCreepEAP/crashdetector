package com.asbestosstar.crashdetector.anon;

import java.io.File;

public class AnonimizadorDeRuta {

//    // Lista de nombres de usuario privilegiados que NO deben ser anónimizados
//    private static final List<String> USUARIOS_PRIVILEGIADOS = Arrays.asList(
//        "root", "admin", "administrator", "sudo", "adm", "sysadmin", "superuser", "system", "daemon",
//        "bin", "sys", "games", "nobody", "ftp", "www-data", "mysql", "postgres", "nagios", "nfsnobody"
//    );

	/**
	 * Anónimiza el nombre de usuario en una ruta si corresponde al directorio home
	 * del usuario actual.
	 * 
	 * @param rutaOriginal La ruta original del archivo o directorio.
	 * @return La ruta con el nombre de usuario reemplazado por "anon" si aplica.
	 */
	/**
	 * Anónimiza la ruta reemplazando el directorio actual con "." y el directorio
	 * home del usuario actual con "~". - Si la ruta está dentro del directorio
	 * actual, se reemplaza el prefijo con ".". - Si la ruta está dentro del
	 * directorio home del usuario actual, se reemplaza el prefijo con "~".
	 * 
	 * @param rutaOriginal La ruta original del archivo o directorio.
	 * @return La ruta con los reemplazos correspondientes.
	 */
	public static String anonimizarNombreDeUsuario(String rutaOriginal) {
		String directorioHome = System.getProperty("user.home");
		if (directorioHome == null || directorioHome.isEmpty()) {
			return rutaOriginal;
		}

		File archivo = new File(rutaOriginal);

		if (!archivo.isAbsolute()) {
			return rutaOriginal;
		}

		try {
			String directorioActual = System.getProperty("user.dir");
			File directorioActualFile = new File(directorioActual);
			String directorioActualCanonico = directorioActualFile.getCanonicalPath();

			File directorioHomeFile = new File(directorioHome);
			String directorioHomeCanonico = directorioHomeFile.getCanonicalPath();

			String archivoCanonico = archivo.getCanonicalPath();

			// Verificar si la ruta pertenece al directorio actual (prioridad alta)
			if (archivoCanonico.startsWith(directorioActualCanonico)) {
				// Reemplazar el directorio actual con "."
				return "." + archivoCanonico.substring(directorioActualCanonico.length());
			}

			// Verificar si pertenece al directorio home del usuario actual
			if (archivoCanonico.startsWith(directorioHomeCanonico)) {
				// Reemplazar el directorio home con "~"
				return "~" + archivoCanonico.substring(directorioHomeCanonico.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rutaOriginal;
	}
//
//    /**
//     * Verifica si un archivo es subdirectorio o igual a otro directorio.
//     */
//    private static boolean esSubRuta(File padre, File hijo) {
//        try {
//            File padreCanonico = padre.getCanonicalFile();
//            File hijoCanonico = hijo.getCanonicalFile();
//            while (hijoCanonico != null) {
//                if (hijoCanonico.equals(padreCanonico)) {
//                    return true;
//                }
//                hijoCanonico = hijoCanonico.getParentFile();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * Determina si el nombre de usuario es privilegiado.
//     */
//    private static boolean esUsuarioPrivilegiado(String nombre) {
//        return USUARIOS_PRIVILEGIADOS.contains(nombre.toLowerCase());
//    }
}