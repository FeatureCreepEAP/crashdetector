package com.asbestosstar.crashdetector.dto.modpack.bbsmc;

import java.util.zip.CRC32;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.VersionJuego;

public class ModBBSMC extends InternetMod {

	private final String idTexto;
	private final long identificador;
	private final String nombre;
	private final String autor;
	private final String enlaceProyecto;
	private final String descripcionCorta;
	private final boolean favorito;
	private final String nombreLocalizado;
	private final VersionJuegoBBSMC ultimaVersionJuego;
	private final long actualizado;
	private final long descargasTotales;
	private final boolean disponible;
	private final boolean analizable;
	private final String urlIcono;

	public ModBBSMC(Json.Nodo nodo) {
		this.idTexto = obtenerCadenaPrimera(nodo.obtener("project_id"), nodo.obtener("id"), nodo.obtener("slug"), "");
		this.identificador = idTexto == null || idTexto.isEmpty() ? 0L : idTextoALargoEstable(idTexto);

		this.nombre = obtenerCadenaPrimera(nodo.obtener("title"), nodo.obtener("name"), nodo.obtener("slug"), "");
		this.autor = obtenerCadenaSeguro(nodo.obtener("author"), "");
		this.descripcionCorta = obtenerCadenaPrimera(nodo.obtener("description"), nodo.obtener("summary"), "");
		this.enlaceProyecto = crearEnlaceProyecto(nodo);
		this.actualizado = parsearFecha(obtenerCadenaPrimera(nodo.obtener("date_modified"), nodo.obtener("updated"),
				nodo.obtener("published"), null));
		this.descargasTotales = obtenerLargoSeguro(nodo.obtener("downloads"), 0L);
		this.disponible = true;
		this.urlIcono = obtenerCadenaSeguro(nodo.obtener("icon_url"), null);

		VersionJuegoBBSMC ultimaTmp = null;
		Json.Nodo versiones = nodo.obtener("versions");
		if (versiones != null && versiones.esArreglo() && versiones.tamano() > 0) {
			String nombreVersion = versiones.en(versiones.tamano() - 1).comoCadena();
			ultimaTmp = new VersionJuegoBBSMC(nombreVersion);
		}
		this.ultimaVersionJuego = ultimaTmp;

		this.favorito = false;
		this.nombreLocalizado = this.nombre;
		this.analizable = false;
	}

	protected String crearEnlaceProyecto(Json.Nodo nodo) {
		String slug = obtenerCadenaSeguro(nodo.obtener("slug"), "");
		String id = obtenerCadenaPrimera(nodo.obtener("project_id"), nodo.obtener("id"), slug);

		if (slug != null && !slug.isEmpty()) {
			return "https://bbsmc.net/mod/" + slug;
		}

		if (id != null && !id.isEmpty()) {
			return "https://bbsmc.net/mod/" + id;
		}

		return "https://bbsmc.net/";
	}

	public String obtenerIdTexto() {
		return idTexto;
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

	protected static long idTextoALargoEstable(String texto) {
		CRC32 crc = new CRC32();
		crc.update(texto.getBytes(java.nio.charset.StandardCharsets.UTF_8));
		return crc.getValue();
	}

	protected static long parsearFecha(String fecha) {
		try {
			if (fecha == null || fecha.trim().isEmpty()) {
				return 0L;
			}
			return java.time.Instant.parse(fecha).toEpochMilli();
		} catch (Throwable t) {
			return 0L;
		}
	}

	protected static String obtenerCadenaPrimera(Json.Nodo a, Json.Nodo b, String def) {
		String av = obtenerCadenaSeguro(a, null);
		if (av != null && !av.isEmpty()) {
			return av;
		}

		String bv = obtenerCadenaSeguro(b, null);
		if (bv != null && !bv.isEmpty()) {
			return bv;
		}

		return def;
	}

	protected static String obtenerCadenaPrimera(Json.Nodo a, Json.Nodo b, Json.Nodo c, String def) {
		String av = obtenerCadenaSeguro(a, null);
		if (av != null && !av.isEmpty()) {
			return av;
		}

		String bv = obtenerCadenaSeguro(b, null);
		if (bv != null && !bv.isEmpty()) {
			return bv;
		}

		String cv = obtenerCadenaSeguro(c, null);
		if (cv != null && !cv.isEmpty()) {
			return cv;
		}

		return def;
	}

	protected static String obtenerCadenaSeguro(Json.Nodo nodo, String def) {
		try {
			String v = nodo.comoCadena();
			return v != null ? v : def;
		} catch (Throwable t) {
			return def;
		}
	}

	protected static long obtenerLargoSeguro(Json.Nodo nodo, long def) {
		try {
			return nodo.comoLargo();
		} catch (Throwable t) {
			return def;
		}
	}
}