package com.asbestosstar.crashdetector.waifu;

import java.util.List;

/**
 * Clase principal para mapear toda la respuesta de la API
 */
public class RespuestaWaifu {
    public Datos data;

    /**
     * Estructura interna para los datos de la versión del juego
     */
    public static class Datos {
        public VersionDelJuego gameVersion;
    }

    /**
     * Estructura para las clases relacionadas con mods
     */
    public static class VersionDelJuego {
        public ClasesDeMods classes;
    }

    /**
     * Lista de resultados (edges) de las clases de mods
     */
    public static class ClasesDeMods {
        public List<Arista> edges;
    }

    /**
     * Nodo que contiene definiciones de mods
     */
    public static class Arista {
        public Nodo node;
    }

    /**
     * Definición de un mod con sus datos
     */
    public static class Nodo {
        public List<Definicion> definitions;
    }

    /**
     * Datos específicos de un mod
     */
    public static class Definicion {
        public Mod mod;
    }

    /**
     * Información del mod (nombre, IDs)
     */
    public static class Mod {
        public String name; // Nombre del mod
        public Integer curseforgeProjectId; // ID de CurseForge
        public String modrinthProjectId; // ID de Modrinth
    }
}