package com.asbestosstar.crashdetector.dto.modpack.minecraftstorage;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.VersionJuego;

public class ModMinecraftStorage extends InternetMod {

	private final long identificador;
	private final String nombre;
	private final String slug;
	private final String descripcionCorta;
	private final String enlaceProyecto;
	private final String urlIcono;
	private final long vistas;
	private final long descargas;
	private final long actualizado;

	public ModMinecraftStorage(Json.Nodo nodo, String idioma) {
		this.identificador = nodo.obtener("id").comoLargo();
		this.nombre = nodo.obtener("title").comoCadena();
		this.slug = nodo.obtener("slug").comoCadena();

		this.descripcionCorta = obtenerIntro(nodo, idioma);
		this.enlaceProyecto = "https://minecraftstorage.com/mods/" + slug;

		this.urlIcono = obtenerUrlImagen(nodo);

		this.vistas = nodo.obtener("views").comoLargo();
		this.descargas = nodo.obtener("downloads").comoLargo();
		this.actualizado = 0L;
	}

	@Override
	public long obtenerIdentificador() {
		return identificador;
	}

	@Override
	public String obtenerNombre() {
		return nombre;
	}

	@Override
	public String obtenerAutor() {
		return "";
	}

	@Override
	public String obtenerEnlaceProyecto() {
		return enlaceProyecto;
	}

	@Override
	public String obtenerDescripcionCorta() {
		return descripcionCorta;
	}

	@Override
	public boolean esFavorito() {
		return false;
	}

	@Override
	public String obtenerNombreLocalizado() {
		return nombre;
	}

	@Override
	public VersionJuego obtenerUltimaVersionJuego() {
		return null;
	}

	@Override
	public long obtenerActualizado() {
		return actualizado;
	}

	@Override
	public long obtenerDescargasTotales() {
		return descargas;
	}

	@Override
	public boolean esDisponible() {
		return true;
	}

	@Override
	public boolean esAnalizable() {
		return true;
	}

	@Override
	public String urlIcon() {
		return urlIcono;
	}

	public long obtenerVistas() {
		return vistas;
	}

	private static String obtenerUrlImagen(Json.Nodo nodo) {

		Json.Nodo thumbnail = nodo.obtener("thumbnail");

		String ruta = thumbnail.obtener("full").comoCadena();

		if (ruta == null || ruta.trim().isEmpty() || "null".equalsIgnoreCase(ruta.trim())) {
			ruta = thumbnail.obtener("medium").comoCadena();
		}

		if (ruta == null || ruta.trim().isEmpty() || "null".equalsIgnoreCase(ruta.trim())) {
			return "";
		}

		return "https://cdn.minecraftstorage.com/" + ruta;
	}

	private static String obtenerIntro(Json.Nodo nodo, String idioma) {

		Json.Nodo intros = nodo.obtener("intros");

		if (!intros.esArreglo()) {
			return "";
		}

		String idiomaNormalizado = normalizarIdioma(idioma);

		String respaldoIngles = "";
		String primerTexto = "";

		for (int i = 0; i < intros.tamano(); i++) {
			Json.Nodo intro = intros.en(i);

			String idiomaIntro = intro.obtener("language").comoCadena();
			String texto = intro.obtener("text").comoCadena();

			if (primerTexto.isEmpty()) {
				primerTexto = texto;
			}

			if ("en".equalsIgnoreCase(idiomaIntro)) {
				respaldoIngles = texto;
			}

			if (idiomaNormalizado.equalsIgnoreCase(idiomaIntro)) {
				return texto;
			}
		}

		if (!respaldoIngles.isEmpty()) {
			return respaldoIngles;
		}

		return primerTexto;
	}

	private static String normalizarIdioma(String idioma) {

		if (idioma == null) {
			return "en";
		}

		String i = idioma.trim().toLowerCase();

		if (i.startsWith("es")) {
			return "es";
		}

		if (i.startsWith("ru")) {
			return "ru";
		}

		return "en";
	}
}