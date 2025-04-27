package com.asbestosstar.crashdetectormc.grepr;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BusquedaArchivos {
    public static List<String> buscar(String directorio, String cadenaBusqueda, boolean usarRegex, boolean ignorarMayusculas) {
        List<String> resultados = new ArrayList<>();
        Path startDir = Paths.get(directorio);

        try {
            Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (!attrs.isDirectory()) {
                        String contenido = leerArchivoComoUTF8(file);
                        if (contenido.isEmpty()) return FileVisitResult.CONTINUE;

                        boolean encontrado;
                        if (usarRegex) {
                            int flags = ignorarMayusculas ? Pattern.CASE_INSENSITIVE : 0;
                            Pattern pattern = Pattern.compile(cadenaBusqueda, flags);
                            encontrado = pattern.matcher(contenido).find();
                        } else {
                            String target = cadenaBusqueda;
                            String content = contenido;
                            if (ignorarMayusculas) {
                                target = target.toLowerCase();
                                content = content.toLowerCase();
                            }
                            encontrado = content.contains(target);
                        }

                        if (encontrado) {
                            resultados.add(file.toAbsolutePath().toString());
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            resultados.add("Error: " + e.getMessage());
        }

        return resultados;
    }

    private static String leerArchivoComoUTF8(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }
}