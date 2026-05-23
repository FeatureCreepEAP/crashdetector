package com.asbestosstar.crashdetector.dto.modpack.curseforge;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.VersionJuego;

public class ModCurseForge extends InternetMod {

	private final long identificador;
	private final String nombre;
	private final String autor;
	private final String enlaceProyecto;
	private final String descripcionCorta;
	private final boolean favorito; // no usado, pero necesario para la interfaz
	private final String nombreLocalizado;
	private final VersionJuegoCurseForge ultimaVersionJuego;
	private final long actualizado;
	private final long descargasTotales;
	private final boolean disponible;
	private final boolean analizable; // no usado, pero necesario

	private final String urlIcono;

	public ModCurseForge(Json.Nodo nodo) {

		this.identificador = nodo.obtener("id").comoLargo();
		this.nombre = nodo.obtener("name").comoCadena();
		this.descripcionCorta = nodo.obtener("summary").comoCadena();

		// Autor
		String autorTmp = "";
		Json.Nodo autores = nodo.obtener("authors");
		if (autores.esArreglo() && autores.tamano() > 0) {
			autorTmp = autores.en(0).obtener("name").comoCadena();
		}
		this.autor = autorTmp;

		// Enlace del proyecto
		this.enlaceProyecto = nodo.obtener("links").obtener("websiteUrl").comoCadena();

		// Fecha de actualización
		String fechaStr = nodo.obtener("dateModified").comoCadena();
		this.actualizado = fechaStr.isEmpty() ? 0L : java.time.Instant.parse(fechaStr).toEpochMilli();

		// Descargas
		this.descargasTotales = nodo.obtener("downloadCount").comoLargo();

		// Disponible
		this.disponible = nodo.obtener("isAvailable").comoBooleano();

		// Icono del mod
		String iconoTmp = null;
		Json.Nodo logo = nodo.obtener("logo");
		if (logo != null) {
			String thumb = logo.obtener("thumbnailUrl").comoCadena();
			if (thumb != null && !thumb.isEmpty()) {
				iconoTmp = thumb;
			} else {
				String url = logo.obtener("url").comoCadena();
				if (url != null && !url.isEmpty()) {
					iconoTmp = url;
				}
			}
		}

		// Última versión de juego (tomar la primera si existe)
		VersionJuegoCurseForge ultimaTmp = null;
		Json.Nodo latestFiles = nodo.obtener("latestFiles");
		if (latestFiles.esArreglo() && latestFiles.tamano() > 0) {
			Json.Nodo primerArchivo = latestFiles.en(0);
			Json.Nodo versionesJuego = primerArchivo.obtener("gameVersions");
			if (versionesJuego.esArreglo() && versionesJuego.tamano() > 0) {
				String nombreVersion = versionesJuego.en(0).comoCadena();
				ultimaTmp = new VersionJuegoCurseForge(nombreVersion);
			}
		}
		this.urlIcono = iconoTmp;
		this.ultimaVersionJuego = ultimaTmp;

		this.favorito = false;
		this.nombreLocalizado = this.nombre;
		this.analizable = false;
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
		return urlIcono;
	}
}