package com.asbestosstar.crashdetector.buscar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.cargador.Cargador;
import com.asbestosstar.crashdetector.cargador.CargadorFabric;
import com.asbestosstar.crashdetector.cargador.CargadorFeatureCreep;
import com.asbestosstar.crashdetector.cargador.CargadorMCForge;

/**
 * Clase que procesa archivos ZIP/JAR para buscar mods y clases dentro de ellos.
 * Evita precargar bytes de cada entrada. Lee el ZIP completo en memoria una vez
 * y luego realiza carga perezosa (con caché) de las entradas cuando se
 * solicitan.
 */
public class ModPKZip implements ArchivoDeMod {

	public ArchivoDeMod desde;
	public String ubicacion;
	public List<ArchivoDeMod> mods_en_mod = new ArrayList<>();
	public List<String> nombres = new ArrayList<>();
	public List<String> clases = new ArrayList<>();
	public List<String> archivos = new ArrayList<>();

	// Bytes crudos del ZIP para poder reabrirlo y leer entradas bajo demanda
	private final byte[] bytesZip;

	// Mapa de nombre de clase interno ("pkg/Clase") -> nombre de entrada en el ZIP
	// ("pkg/Clase.class")
	private final Map<String, String> mapaEntradaPorClase = new HashMap<>();

	// Cachés de bytes de entradas ya leídas
	private final Map<String, byte[]> cacheBytesEntrada = new HashMap<>();
	private final Map<String, byte[]> cacheBytesClase = new HashMap<>();

	public List<Cargador> cargadores_de_mod = new ArrayList<Cargador>();
	public boolean meta_tiene_referencia_de_mcreator = false;

	/**
	 * Constructor principal que procesa un archivo ZIP/JAR. Indexa nombres y clases
	 * sin cargar bytes de todas las entradas. Sólo se leen bytes de metadatos
	 * necesarios para extraer IDs (mods.toml, fabric.mod.json, modules.xml, .mod).
	 */
	public ModPKZip(String ubicacion, ArchivoDeMod desde, InputStream inputStream) {
		CrashDetectorLogger.log("en mod pkzip " + ubicacion);
		this.ubicacion = ubicacion;
		this.desde = desde;

		try {
			this.bytesZip = leerTodo(inputStream);
			indexarYProcesarMetadatos();
			descubrirCargadores();
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Lee completamente un InputStream a un arreglo de bytes.
	 */
	private static byte[] leerTodo(InputStream in) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream(Math.max(32 * 1024, in.available()));
		byte[] tmp = new byte[64 * 1024];
		int n;
		while ((n = in.read(tmp)) != -1) {
			buf.write(tmp, 0, n);
		}
		return buf.toByteArray();
	}

	/**
	 * Recorre el ZIP para: - Llenar 'archivos' - Mapear clases (nombre interno ->
	 * entrada .class) y llenar 'clases' - Detectar y leer SOLO metadatos necesarios
	 * para extraer 'nombres' y banderas - Construir mods anidados (jar/zip dentro)
	 */
	private void indexarYProcesarMetadatos() throws IOException {
		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytesZip))) {
			ZipEntry e;
			while ((e = zip.getNextEntry()) != null) {
				final String nombreArchivo = e.getName();
				archivos.add(nombreArchivo);

				if (esArchivoAnidado(nombreArchivo)) {
					// Para anidados: crear ModPKZip hijo con bytes del anidado (sin precargar
					// entradas internas).
					byte[] bytesAnidado = leerEntrada(nombreArchivo);
					if (bytesAnidado != null) {
						InputStream nested = new ByteArrayInputStream(bytesAnidado);
						String nuevaUbicacion = ubicacion + "!/" + nombreArchivo;
						mods_en_mod.add(new ModPKZip(nuevaUbicacion, this, nested));
					}
				} else if (nombreArchivo.endsWith(".class")) {
					// Registrar clase sin leer bytes
					String nombreClaseJava = nombreArchivo.replace('/', '.').replace(".class", "");
					clases.add(nombreClaseJava);

					String nombreInterno = nombreArchivo.substring(0, nombreArchivo.length() - 6); // sin ".class"
					mapaEntradaPorClase.put(nombreInterno, nombreArchivo);
				} else if (nombreArchivo.endsWith("modules.xml")) {
					// Sólo leer esta entrada para extraer IDs JBoss
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						nombres.addAll(CargadorFeatureCreep.parsearNombreModuloJBoss(content));
					}
				} else if (nombreArchivo.endsWith(".mod")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						nombres.addAll(CargadorFeatureCreep.parsearNombreModHOI4(content));
					}
				} else if (nombreArchivo.equals("fabric.mod.json")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String texto = new String(content, StandardCharsets.UTF_8);
						nombres.addAll(CargadorFabric.parsearIdModFabric(texto));
						if (texto.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				} else if (nombreArchivo.endsWith("mods.toml")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String toml = new String(content, StandardCharsets.UTF_8);
						nombres.addAll(CargadorMCForge.parsearIdModMCForge(toml));
						if (toml.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				}
				zip.closeEntry();
			}
		}
	}

	private void descubrirCargadores() {
		for (Cargador cargador : Cargador.cargadores) {
			try {
				if (cargador.modEsDeCargador(this)) {
					cargadores_de_mod.add(cargador);
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}
	}

	/**
	 * Lee y devuelve los bytes de una entrada específica por nombre. Usa un caché
	 * simple para evitar reescaneos repetidos.
	 */
	private byte[] leerEntrada(String nombreEntrada) throws IOException {
		byte[] cache = cacheBytesEntrada.get(nombreEntrada);
		if (cache != null)
			return cache;

		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytesZip))) {
			ZipEntry e;
			while ((e = zip.getNextEntry()) != null) {
				if (nombreEntrada.equals(e.getName())) {
					byte[] data = leer(zip);
					cacheBytesEntrada.put(nombreEntrada, data);
					zip.closeEntry();
					return data;
				}
				zip.closeEntry();
			}
		}
		return null;
	}

	/**
	 * Lee el contenido de un flujo de entrada en un arreglo de bytes.
	 */
	private static byte[] leer(InputStream input) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[64 * 1024];
		int bytesRead;
		while ((bytesRead = input.read(data)) != -1) {
			buffer.write(data, 0, bytesRead);
		}
		return buffer.toByteArray();
	}

	/**
	 * Devuelve true si el archivo es un contenedor anidado (como .jar, .zip, etc.)
	 */
	private boolean esArchivoAnidado(String nombreArchivo) {
		return nombreArchivo.endsWith(".jar") || nombreArchivo.endsWith(".zip") || nombreArchivo.endsWith(".fpm")
				|| nombreArchivo.endsWith(".litemod") || nombreArchivo.endsWith(".war")
				|| nombreArchivo.endsWith(".ear") || nombreArchivo.endsWith(".rar");
	}

	// Métodos de búsqueda recursiva

	@Override
	public ArchivoDeMod obtenerDesde() {
		return desde;
	}

	@Override
	public List<ArchivoDeMod> mods_en_mods() {
		return mods_en_mod;
	}

	@Override
	public List<String> nombre() {
		return nombres;
	}

	@Override
	public String ubicacion() {
		return ubicacion;
	}

	@Override
	public List<String> clases() {
		return clases;
	}

	@Override
	public boolean tieneNombreRecursivo(String nombre) {
		if (this.nombres.contains(nombre))
			return true;
		for (ArchivoDeMod mod : mods_en_mods()) {
			if (mod.tieneNombreRecursivo(nombre))
				return true;
		}
		return false;
	}

	@Override
	public String obtenerNombreRecursivo(String nombre) {
		if (tieneNombreRecursivo(nombre)) {
			if (this.nombres.contains(nombre))
				return this.ubicacion();
			for (ArchivoDeMod mod : mods_en_mods()) {
				String resultado = mod.obtenerNombreRecursivo(nombre);
				if (resultado != null)
					return resultado;
			}
		}
		return null;
	}

	@Override
	public boolean tieneArchivoRecursivo(String archivo) {
		if (this.archivos().contains(archivo))
			return true;
		for (ArchivoDeMod mod : mods_en_mods()) {
			if (mod.tieneArchivoRecursivo(archivo))
				return true;
		}
		return false;
	}

	@Override
	public String obtenerArchivoRecursivo(String archivo) {
		if (tieneArchivoRecursivo(archivo)) {
			if (this.archivos.contains(archivo))
				return this.ubicacion() + "!/" + archivo;
			for (ArchivoDeMod mod : mods_en_mods()) {
				String resultado = mod.obtenerArchivoRecursivo(archivo);
				if (resultado != null)
					return resultado;
			}
		}
		return null;
	}

	@Override
	public List<String> archivos() {
		return archivos;
	}

	@Override
	public List<ArchivoDeMod> buscarModsCon(String termino) {
		List<ArchivoDeMod> resultados = new ArrayList<>();

		boolean tieneArchivo = archivos.contains(termino);
		boolean tieneClase = clases.contains(termino);
		String rutaPaquete = termino.replace('.', '/');
		boolean tienePaquete = clases.stream().anyMatch(clase -> clase.startsWith(rutaPaquete));

		if (tieneArchivo || tieneClase || tienePaquete) {
			resultados.add(this);
		}

		for (ArchivoDeMod mod : mods_en_mod) {
			resultados.addAll(mod.buscarModsCon(termino));
		}

		return resultados;
	}

	@Override
	public boolean existeClase(String nombreClase) {
		// Convertir nombre de clase de formato interno (ej: "java/lang/Object") a
		// formato Java (ej: "java.lang.Object")
		String formatoJava = nombreClase.replace('/', '.');
		return clases.contains(formatoJava);
	}

	/**
	 * Devuelve los bytes de la clase solicitada. Acepta nombre interno "pkg/Clase"
	 * o con puntos "pkg.Clase"; ignora ".class". Carga perezosa y cachea el
	 * resultado.
	 */
	@Override
	public byte[] obtenerBytesClase(String nombreClase) {
		if (nombreClase == null || nombreClase.isEmpty())
			return null;

		// Normalizar a nombre interno ASM (pkg/Clase)
		String interno = normalizarNombreInterno(nombreClase);

		// 1) caché de clases
		byte[] cached = cacheBytesClase.get(interno);
		if (cached != null)
			return cached;

		// 2) buscar la entrada real "pkg/Clase.class"
		String nombreEntrada = mapaEntradaPorClase.get(interno);
		if (nombreEntrada == null)
			return null;

		try {
			byte[] data = leerEntrada(nombreEntrada);
			if (data != null) {
				cacheBytesClase.put(interno, data);
			}
			return data;
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	@Override
	public List<String> obtenerTodosLosNombresDeClases() {
		// Devolver los nombres internos (pkg/Clase) conocidos
		return new ArrayList<>(mapaEntradaPorClase.keySet());
	}

	@Override
	public List<Cargador> cargadores() {
		return cargadores_de_mod;
	}

	@Override
	public boolean MetaDataTieneReferenciaDeMCReator() {
		return meta_tiene_referencia_de_mcreator;
	}

	// --- Métodos utilitarios nuevos ---

	/**
	 * Precarga los bytes de TODAS las clases de este ZIP en el caché interno. Útil
	 * si se desea evitar lecturas repetidas del ZIP durante un análisis intensivo.
	 *
	 * @return número de clases cargadas en caché en esta instancia
	 */
	public int precargarTodasLasClases() {
		int cargadas = 0;
		for (Map.Entry<String, String> e : mapaEntradaPorClase.entrySet()) {
			final String interno = e.getKey();
			if (cacheBytesClase.containsKey(interno)) {
				continue; // ya en caché
			}
			try {
				byte[] data = leerEntrada(e.getValue());
				if (data != null) {
					cacheBytesClase.put(interno, data);
					cargadas++;
				}
			} catch (IOException ex) {
				CrashDetectorLogger.logException(ex);
			}
		}
		return cargadas;
	}

	/**
	 * Precarga de forma RECURSIVA todas las clases de este ZIP y de sus archivos
	 * anidados que también sean ModPKZip.
	 *
	 * @return número total de clases cargadas (esta instancia + anidados ModPKZip)
	 */
	public int precargarTodasLasClasesRecursivo() {
		int total = precargarTodasLasClases();
		for (ArchivoDeMod hijo : mods_en_mod) {
			if (hijo instanceof ModPKZip) {
				total += ((ModPKZip) hijo).precargarTodasLasClasesRecursivo();
			}
		}
		return total;
	}

	// --- util ---

	private static String normalizarNombreInterno(String nombre) {
		String n = nombre;
		if (n.endsWith(".class"))
			n = n.substring(0, n.length() - 6);
		// si viene con puntos, convertir a slashes
		n = n.replace('.', '/');
		return n;
	}
}
