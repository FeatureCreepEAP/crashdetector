package com.asbestosstar.crashdetector.waifu;

import java.util.List;

/**
 * Clase principal para mapear toda la respuesta nueva de la API Waifu.
 *
 * Estructura nueva:
 *
 * data
 *   gameVersions[]
 *     version
 *     loader
 *     mods
 *       count
 *       edges[]
 *         node
 *           curseforgeProjectId
 *           modrinthProjectId
 *           modIds[]
 *           classes[]
 *             name
 */
public class RespuestaWaifu {

	public Datos data;

	/**
	 * Contenedor principal de datos.
	 */
	public static class Datos {

		public List<VersionDelJuego> gameVersions;
	}

	/**
	 * Una versión/cargador del juego.
	 */
	public static class VersionDelJuego {

		public String version;
		public String loader;
		public Mods mods;
	}

	/**
	 * Resultado de mods para una versión/cargador.
	 */
	public static class Mods {

		public Integer count;
		public List<Arista> edges;
	}

	/**
	 * Arista GraphQL.
	 */
	public static class Arista {

		public Nodo node;
	}

	/**
	 * Nodo de mod encontrado.
	 */
	public static class Nodo {

		public Integer curseforgeProjectId;
		public String modrinthProjectId;
		public List<String> modIds;
		public List<ClaseEncontrada> classes;
	}

	/**
	 * Clase encontrada dentro del mod.
	 */
	public static class ClaseEncontrada {

		public String name;
	}

	/**
	 * Información simplificada del mod para usar en la GUI.
	 */
	public static class Mod {

		public String name;
		public Integer curseforgeProjectId;
		public String modrinthProjectId;

		public String cargador;
		public String version_del_juego;
		public String claseEncontrada;
		public Integer cantidadClasesEncontradas;
	}
}