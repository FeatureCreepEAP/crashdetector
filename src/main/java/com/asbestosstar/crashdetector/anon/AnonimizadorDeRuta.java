package com.asbestosstar.crashdetector.anon;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class AnonimizadorDeRuta {

    // Lista de nombres de usuario privilegiados que NO deben ser anónimizados
    private static final List<String> USUARIOS_PRIVILEGIADOS = Arrays.asList(
        "root", "admin", "administrator", "sudo", "adm", "sysadmin", "superuser", "system", "daemon",
        "bin", "sys", "games", "nobody", "ftp", "www-data", "mysql", "postgres", "nagios", "nfsnobody"
    );

    /**
     * Anónimiza el nombre de usuario en una ruta si corresponde al directorio home del usuario actual.
     * 
     * @param rutaOriginal La ruta original del archivo o directorio.
     * @return La ruta con el nombre de usuario reemplazado por "anon" si aplica.
     */
    public static String anonimizarNombreDeUsuario(String rutaOriginal) {
        // Obtener el directorio home del usuario actual
        String directorioHome = System.getProperty("user.home");
        if (directorioHome == null || directorioHome.isEmpty()) {
            return rutaOriginal; // No se puede determinar el directorio home
        }

        // Convertir a File para normalizar la ruta
        File homeFile = new File(directorioHome);
        File archivo = new File(rutaOriginal);
        
        // Si la ruta no está dentro de user.home, devolverla sin cambios
        if (!esSubRuta(homeFile, archivo)) {
            return rutaOriginal;
        }

        // Obtener los segmentos de la ruta home (ej. /home/john, C:\Users\John)
        String[] segmentosHome = homeFile.getAbsolutePath().split(File.separator);
        String[] segmentosRuta = archivo.getAbsolutePath().split(File.separator);

        // Buscar el índice del nombre de usuario en la ruta
        int indiceUsuario = -1;
        for (int i = 0; i < segmentosRuta.length; i++) {
            if (i < segmentosHome.length && segmentosRuta[i].equals(segmentosHome[i])) {
                continue;
            }
            if (i == segmentosHome.length - 1 && segmentosRuta[i].equals(segmentosHome[i])) {
                continue;
            }
            if (i == segmentosHome.length && !esUsuarioPrivilegiado(segmentosRuta[i])) {
                indiceUsuario = i;
                break;
            }
        }

        // Si no se encontró el nombre de usuario o está en la lista de privilegiados, devolver sin cambios
        if (indiceUsuario == -1) {
            return rutaOriginal;
        }

        // Reemplazar el nombre de usuario por "anon"
        StringBuilder rutaAnonimizada = new StringBuilder();
        for (int i = 0; i < segmentosRuta.length; i++) {
            if (i == indiceUsuario) {
                rutaAnonimizada.append("anon");
            } else {
                rutaAnonimizada.append(segmentosRuta[i]);
            }
            if (i < segmentosRuta.length - 1) {
                rutaAnonimizada.append(File.separator);
            }
        }

        return rutaAnonimizada.toString();
    }

    /**
     * Verifica si un archivo es subdirectorio o igual a otro directorio.
     */
    private static boolean esSubRuta(File padre, File hijo) {
        try {
            File padreCanonico = padre.getCanonicalFile();
            File hijoCanonico = hijo.getCanonicalFile();
            while (hijoCanonico != null) {
                if (hijoCanonico.equals(padreCanonico)) {
                    return true;
                }
                hijoCanonico = hijoCanonico.getParentFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Determina si el nombre de usuario es privilegiado.
     */
    private static boolean esUsuarioPrivilegiado(String nombre) {
        return USUARIOS_PRIVILEGIADOS.contains(nombre.toLowerCase());
    }
}