package com.asbestosstar.crashdetector.api_sito_registro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.mapas.BiMap;
import com.asbestosstar.crashdetector.mapas.BiMap.DoubleKey;

public interface APIdeSitioDeRegistro {

	List<APIdeSitioDeRegistro> APIS = new ArrayList<APIdeSitioDeRegistro>();

	public static APIdeSitioDeRegistro obtenerAPIdeConfig() throws NoAPIdeRegistro {
		String nom = com.asbestosstar.crashdetector.Config.obtenerInstancia().obtenerApiSeleccionada();
		for (APIdeSitioDeRegistro reg : APIS) {
			if (reg.nombre().equals(nom)) {
				return reg;
			}
		}
		throw new NoAPIdeRegistro();
	}

	String nombre();

	/**
	 * Publica un registro completo leyendo su contenido desde la Consola.
	 * 
	 * @return enlace único del registro publicado.
	 */
	String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar;

	/**
	 * Publica texto arbitrario (sin depender de la clase Consola). Las
	 * implementaciones deben aplicar sus validaciones de tamaño/líneas.
	 *
	 * @param nombreSugerido solo informativo (algunos servicios lo ignoran)
	 * @param contenido      texto a publicar
	 * @return enlace del contenido publicado
	 */
	public String publicarTexto(String nombreSugerido, String contenido)
			throws DemasiadoGrande, LimteDeTasa, ErrorConPublicar;

	/**
	 * Sitios por defecto de la API.
	 */
	public List<String> sitiosPorDefecto();

	public static String sitioDeConfig() {
		return com.asbestosstar.crashdetector.Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado();
	}

// En APIdeSitioDeRegistro
	/**
	 * PUBLICACIÓN EN PARTES (COMPORTAMIENTO POR DEFECTO SIN LÍMITES): - Por defecto
	 * NO divide el contenido. Publica en una sola petición. - Cualquier API que
	 * requiera límites/partición DEBE sobrescribir este método.
	 *
	 * @return lista con un único enlace si la publicación se hace en una sola
	 *         pieza.
	 */
	default List<String> publicarRegistroEnPartes(com.asbestosstar.crashdetector.Consola registro)
			throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro, LimteDeTasa {
		final String nombre = (registro.archivo != null) ? registro.archivo.getFileName().toString() : "log.txt";
		final String contenido = registro.obtainerContenidoParaPublicar();

		String enlace = publicarTexto(nombre, contenido); // sin división por defecto
		ArrayList<String> lista = new ArrayList<>();
		lista.add(enlace);
		return lista;
	}

	/**
	 * Si puedes obtener una enlace a linea
	 * 
	 * @return
	 */
	public boolean soporteEnlacesALinea();

	/**
	 * Enlace a numero linea en los registros
	 * 
	 * @param linea_numera
	 * @return
	 */
	public default String obtenerEnlaceDeLinea(int linea_numera) {
		/*
		 * (ES) Mapea una línea global `n` al enlace de la parte correcta y devuelve
		 * `urlParte + "#L<lineaLocal>"`. Requiere que: - El llamador haya fijado el
		 * grupo actual (ThreadLocal) con registrarGrupoActual(..) - Cada parte se haya
		 * registrado con registrarParte(gid, idx, url, desde, hasta)
		 */
		int n = Math.max(1, linea_numera);
		String gid = grupoActual().get();
		if (gid == null) {
			// (ES) Sin contexto de grupo: fallback neutro (ancla local).
			return "#L" + n;
		}

		for (Entry<DoubleKey<String, Integer>, ParteInfo> e : indicePartes().entrySet()) {
			DoubleKey<String, Integer> dk = e.getKey();
			if (!java.util.Objects.equals(dk.key0, gid))
				continue;
			ParteInfo p = e.getValue();
			if (n >= p.desde && n <= p.hasta) {
				int lineaLocal = n - p.desde + 1;
				// (ES) Si tu visor usa otro formato (p.ej. ?hl=), cámbialo aquí.
				return p.url + "#L" + lineaLocal;
			}
		}
		// (ES) No se encontró parte (p.ej. aún no se registró): fallback.
		return "#L" + n;
	}

	/**
	 * (ES) Info de una parte publicada: URL y rango de líneas globales [desde,
	 * hasta].
	 */
	static class ParteInfo {
		final String url;
		final int desde;
		final int hasta;

		ParteInfo(String url, int desde, int hasta) {
			this.url = url;
			this.desde = desde;
			this.hasta = hasta;
		}
	}

	public ThreadLocal<String> grupoActual();

	public BiMap<String, Integer, ParteInfo> indicePartes();

	public default void registrarGrupoActual(String grupoId) {
		grupoActual().set(grupoId);
	}

	/** Registra una parte publicada con su URL y su rango de líneas globales. */
	public default void registrarParte(String grupoId, int parteIndexBase1, String url, int desde, int hasta) {
		indicePartes().put(grupoId, parteIndexBase1, new ParteInfo(url, desde, hasta));
	}

	
	/**
	 * Lo opcional eliminador de registros gui
	 * @return
	 */
	public default Supplier<TipoGUI> eliminador(){
		return null;
	}
	
	
	
	
	
	
}
