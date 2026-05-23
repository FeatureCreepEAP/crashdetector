package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.VersionJuego;

public class ModTlmods extends InternetMod {

	private final long identificador;
	private final String nombre;
	private final String autor;
	private final String enlaceProyecto;
	private final String descripcionCorta;
	private final boolean favorito;
	private final String nombreLocalizado;
	private final VersionJuegoTlmods ultimaVersionJuego;
	private final long actualizado;
	private final long descargasTotales;
	private final boolean disponible;
	private final boolean analizable;
	private long imagenPrincipal;

	public ModTlmods(Json.Nodo nodo) {
		this.identificador = nodo.obtener("id").comoLargo();
		this.nombre = nodo.obtener("name").comoCadena();
		this.autor = nodo.obtener("author").comoCadena();
		this.enlaceProyecto = nodo.obtener("linkProject").comoCadena().trim();
		this.descripcionCorta = nodo.obtener("shortDescription").comoCadena();
		this.favorito = nodo.obtener("favorite").comoBooleano();
		this.nombreLocalizado = nodo.obtener("lanName").comoCadena();
		this.imagenPrincipal = nodo.obtener("picture").comoLargo();
		Json.Nodo nodoVersion = nodo.obtener("lastGameVersion");
		this.ultimaVersionJuego = !"null".equals(nodoVersion.escribir()) ? new VersionJuegoTlmods(nodoVersion) : null;

		this.actualizado = nodo.obtener("updated").comoLargo();
		this.descargasTotales = nodo.obtener("downloadALL").comoLargo();
		this.disponible = nodo.obtener("available").comoBooleano();
		this.analizable = nodo.obtener("parser").comoBooleano();
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
		return autor;
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
		return favorito;
	}

	@Override
	public String obtenerNombreLocalizado() {
		return nombreLocalizado;
	}

	@Override
	public VersionJuego obtenerUltimaVersionJuego() {
		return ultimaVersionJuego;
	}

	@Override
	public long obtenerActualizado() {
		return actualizado;
	}

	@Override
	public long obtenerDescargasTotales() {
		return descargasTotales;
	}

	@Override
	public boolean esDisponible() {
		return disponible;
	}

	@Override
	public boolean esAnalizable() {
		return analizable;
	}

	@Override
	public String urlIcon() {
		return "https://tlmods.org/files/pictures/compress/" + String.valueOf(imagenPrincipal) + ".png";
	}
}