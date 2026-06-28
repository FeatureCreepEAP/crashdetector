package com.asbestosstar.crashdetector.buscar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.cargador.Cargador;
import com.asbestosstar.crashdetector.cargador.CargadorBukkit;
import com.asbestosstar.crashdetector.cargador.CargadorCanary;
import com.asbestosstar.crashdetector.cargador.CargadorFabric;
import com.asbestosstar.crashdetector.cargador.CargadorFeatureCreep;
import com.asbestosstar.crashdetector.cargador.CargadorFlint;
import com.asbestosstar.crashdetector.cargador.CargadorLiteLoader;
import com.asbestosstar.crashdetector.cargador.CargadorMCForge;
import com.asbestosstar.crashdetector.cargador.CargadorMeddle;
import com.asbestosstar.crashdetector.cargador.CargadorNilLoader;
import com.asbestosstar.crashdetector.cargador.CargadorRift;
import com.asbestosstar.crashdetector.cargador.CargadorSponge;
import com.asbestosstar.crashdetector.cargador.ml.AnalizadorModsTomlForge;

public class ModPKZip implements ArchivoDeMod {

	public ArchivoDeMod desde;
	public String ubicacion;
	public List<ArchivoDeMod> mods_en_mod = new ArrayList<>();
	public List<String> nombres = new ArrayList<>();
	public List<String> clases = new ArrayList<>();
	public List<String> archivos = new ArrayList<>();

	// Único estado mutable necesario: Los bytes crudos de ESTE archivo específico
	private final byte[] bytesZip;

	private final Map<String, String> mapaEntradaPorClase = new HashMap<>();
	private final Map<String, byte[]> cacheBytesEntrada = new java.util.concurrent.ConcurrentHashMap<>();
	private final Map<String, byte[]> cacheBytesClase = new java.util.concurrent.ConcurrentHashMap<>();

	public List<Cargador> cargadores_de_mod = new ArrayList<Cargador>();
	public boolean meta_tiene_referencia_de_mcreator = false;
	public String version = "";

	/**
	 * CONSTRUCTOR PRINCIPAL (Para el archivo raíz en disco). Lee el archivo una
	 * sola vez. Si tiene hijos anidados, se pasan directamente al constructor de
	 * InputStream sin volver a tocar el disco.
	 */
	public ModPKZip(File archivo, ArchivoDeMod desde) {
		CrashDetectorLogger.log("en mod pkzip (disco) " + archivo.getAbsolutePath());
		this.ubicacion = archivo.getAbsolutePath();
		this.desde = desde;

		try (InputStream fis = new java.io.FileInputStream(archivo)) {
			this.bytesZip = leerTodo(fis);
			indexarYProcesarMetadatos();
			descubrirCargadores();
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * CONSTRUCTOR PARA ANIDADOS (Hijos dentro de otros JARs). Recibe los bytes ya
	 * extraídos del padre. Cero accesos a disco.
	 */
	public ModPKZip(String ubicacion, ArchivoDeMod desde, byte[] bytesHijo) {
		CrashDetectorLogger.log("en mod pkzip (anidado) " + ubicacion);
		this.ubicacion = ubicacion;
		this.desde = desde;
		this.bytesZip = bytesHijo;

		try {
			indexarYProcesarMetadatos();
			descubrirCargadores();
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * ÍNDICE DE UNA SOLA PASADA: Recorre el ZIP, extrae metadata puntual, y procesa
	 * hijos recursivamente al instante sin volver a leer el stream padre.
	 */
	private void indexarYProcesarMetadatos() throws IOException {
		try (JarInputStream zip = new JarInputStream(new ByteArrayInputStream(bytesZip))) {
			Manifest man = zip.getManifest();
			if (man != null)
				procesarManifest(man);

			ZipEntry e;
			while ((e = zip.getNextEntry()) != null) {
				final String nombreArchivo = e.getName();
				archivos.add(nombreArchivo);

				if (esArchivoAnidado(nombreArchivo)) {
					// EXTRAER HIJO INMEDIATAMENTE Y RECURSAR
					byte[] bytesHijo = leer(zip);
					if (bytesHijo != null) {
						String nuevaUbicacion = ubicacion + "!/" + nombreArchivo;
						// Llamada recursiva directa usando el constructor de bytes
						mods_en_mod.add(new ModPKZip(nuevaUbicacion, this, bytesHijo));
					}
				} else if (nombreArchivo.endsWith(".class")) {
					String nombreClaseJava = nombreArchivo.replace('/', '.').replace(".class", "");
					clases.add(nombreClaseJava);
					String nombreInterno = nombreArchivo.substring(0, nombreArchivo.length() - 6);
					mapaEntradaPorClase.put(nombreInterno, nombreArchivo);

					if (nombreClaseJava.endsWith("module-info")) {
						byte[] bytes = obtenerBytesClase(nombreClaseJava);
						if (bytes != null)
							nombres.addAll(this.obtenerNombresDeModuleInfo(bytes));
					}
				} else {
					procesarMetadataSiAplica(nombreArchivo);
				}
				zip.closeEntry();
			}
		}
	}

	/**
	 * Lee solo los bytes de los archivos de metadata crítica.
	 */
	private void procesarMetadataSiAplica(String nombreArchivo) {
		try {
			if (nombreArchivo.equals("fabric.mod.json") || nombreArchivo.equals("quilt.mod.json")
					|| nombreArchivo.equals("riftmod.json") || nombreArchivo.equals("litemod.json")
					|| nombreArchivo.equals("mcmod.info") || nombreArchivo.equals("META-INF/mcmod.info")
					|| nombreArchivo.equals("plugin.yml") || nombreArchivo.equals("paper-plugin.yml")
					|| nombreArchivo.equals("flintmodule.json") || nombreArchivo.equals("META-INF/sponge_plugins.json")
					|| nombreArchivo.equals("meta-inf/sponge_plugins.json") || nombreArchivo.endsWith("mods.toml")
					|| nombreArchivo.toLowerCase(java.util.Locale.ROOT).endsWith(".inf")
					|| nombreArchivo.equals("fcflat.properties")
					|| nombreArchivo.toLowerCase(java.util.Locale.ROOT).endsWith(".nilmod.css")) {

				byte[] content = leerEntrada(nombreArchivo);
				if (content != null) {
					String texto = new String(content, StandardCharsets.UTF_8);
					parsearTextoMetadata(nombreArchivo, texto, content);
				}
			} else if (nombreArchivo.endsWith("modules.xml") || nombreArchivo.endsWith(".mod")) {
				byte[] content = leerEntrada(nombreArchivo);
				if (content != null)
					parsearBytesMetadata(nombreArchivo, content);
			}
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	private void parsearTextoMetadata(String nombreArchivo, String texto, byte[] bytes) throws IOException {
		if (texto == null || texto.isEmpty())
			return;

		if (nombreArchivo.equals("fabric.mod.json")) {
			agregarNombresSinDuplicados(CargadorFabric.parsearIdModFabric(texto));
			if (this.version.isEmpty())
				this.version = CargadorFabric.parsearVersionModFabric(texto);
		} else if (nombreArchivo.equals("quilt.mod.json")) {
			agregarNombresSinDuplicados(CargadorFabric.parsearIdModQuilt(texto));
			if (this.version.isEmpty())
				this.version = CargadorFabric.parsearVersionModQuilt(texto);
		} else if (nombreArchivo.equals("riftmod.json")) {
			agregarNombresSinDuplicados(CargadorRift.parsearIdModRift(texto));
			if (this.version.isEmpty())
				this.version = CargadorRift.parsearVersionModRift(texto);
		} else if (nombreArchivo.equals("litemod.json")) {
			agregarNombresSinDuplicados(CargadorLiteLoader.parsearIdModLiteLoader(texto));
			if (this.version.isEmpty())
				this.version = CargadorLiteLoader.parsearVersionModLiteLoader(texto);
		} else if (nombreArchivo.equals("mcmod.info") || nombreArchivo.equals("META-INF/mcmod.info")) {
			agregarNombresSinDuplicados(CargadorMCForge.parsearIdModMcmodInfo(texto));
			if (this.version.isEmpty())
				this.version = CargadorMCForge.parsearVersionModMcmodInfo(texto);
		} else if (nombreArchivo.equals("plugin.yml") || nombreArchivo.equals("paper-plugin.yml")) {
			agregarNombresSinDuplicados(CargadorBukkit.parsearIdModBukkit(texto));
			if (this.version.isEmpty())
				this.version = CargadorBukkit.parsearVersionModBukkit(texto);
		} else if (nombreArchivo.endsWith("mods.toml")) {
			agregarNombresSinDuplicados(CargadorMCForge.parsearIdModMCForge(texto));
			if (this.version.isEmpty())
				this.version = AnalizadorModsTomlForge.extraerVersionPrincipal(texto);
		} else if (nombreArchivo.toLowerCase(java.util.Locale.ROOT).endsWith(".inf")) {
			agregarNombresSinDuplicados(CargadorCanary.parsearIdModCanary(texto));
			if (this.version.isEmpty())
				this.version = CargadorCanary.parsearVersionModCanary(texto);
		} else if (nombreArchivo.equals("flintmodule.json")) {
			agregarNombresSinDuplicados(CargadorFlint.parsearIdModFlint(texto));
			if (this.version.isEmpty())
				this.version = CargadorFlint.parsearVersionModFlint(texto);
		} else if (nombreArchivo.equals("META-INF/sponge_plugins.json")
				|| nombreArchivo.equals("meta-inf/sponge_plugins.json")) {
			agregarNombresSinDuplicados(CargadorSponge.parsearIdPluginSponge(texto));
			if (this.version.isEmpty())
				this.version = CargadorSponge.parsearVersionPluginSponge(texto);
		} else if (nombreArchivo.toLowerCase(java.util.Locale.ROOT).endsWith(".nilmod.css")) {
			agregarNombresSinDuplicados(CargadorNilLoader.parsearIdModNilLoaderDesdeNombreArchivo(nombreArchivo));
			if (this.version.isEmpty())
				this.version = CargadorNilLoader.parsearVersionModNilLoader(texto);
		} else if (nombreArchivo.equals("fcflat.properties")) {
			agregarNombresSinDuplicados(CargadorFeatureCreep.parsearIdModFlat(bytes));
			if (this.version.isEmpty())
				this.version = CargadorFeatureCreep.parsearVersionModFlat(bytes);
		}

		if (texto.toLowerCase().contains("mcreator")) {
			meta_tiene_referencia_de_mcreator = true;
		}
	}

	private void parsearBytesMetadata(String nombreArchivo, byte[] content) throws IOException {
		if (content == null)
			return;
		if (nombreArchivo.endsWith("modules.xml")) {
			agregarNombresSinDuplicados(CargadorFeatureCreep.parsearNombreModuloJBoss(content));
			if (this.version.isEmpty())
				this.version = CargadorFeatureCreep.parsearVersionModuloJBoss(content);
		} else if (nombreArchivo.endsWith(".mod")) {
			agregarNombresSinDuplicados(CargadorFeatureCreep.parsearNombreModHOI4(content));
			if (this.version.isEmpty())
				this.version = CargadorFeatureCreep.parsearVersionModHOI4(content);
		}
	}

	private void procesarManifest(Manifest man) {
		if (man == null)
			return;
		try {
			agregarNombresSinDuplicados(ProcesadorManifiesto.obtenerNombresDeModulo(man));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
		try {
			if (CargadorMeddle.manifestEsDeMeddle(man)) {
				agregarNombresSinDuplicados(CargadorMeddle.parsearNombresManifestMeddle(man));
				if (this.version.isEmpty()) {
					String v = CargadorMeddle.parsearVersionManifestMeddle(man);
					if (v != null && !v.trim().isEmpty())
						this.version = v.trim();
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
		try {
			if (this.version.isEmpty()) {
				java.util.jar.Attributes attr = man.getMainAttributes();
				if (attr != null) {
					String iv = attr.getValue("Implementation-Version");
					if (iv != null && !iv.trim().isEmpty())
						this.version = iv.trim();
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
		try {
			if (man.toString() != null && man.toString().toLowerCase().contains("mcreator"))
				meta_tiene_referencia_de_mcreator = true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private void descubrirCargadores() {
		for (Cargador cargador : Cargador.cargadores) {
			try {
				if (cargador.modEsDeCargador(this))
					cargadores_de_mod.add(cargador);
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}
	}

	// --- LECTURA DE BYTES ---

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

	private static byte[] leerTodo(InputStream in) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream(Math.max(32 * 1024, in.available()));
		byte[] tmp = new byte[64 * 1024];
		int n;
		while ((n = in.read(tmp)) != -1)
			buf.write(tmp, 0, n);
		return buf.toByteArray();
	}

	private static byte[] leer(InputStream input) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[64 * 1024];
		int bytesRead;
		while ((bytesRead = input.read(data)) != -1)
			buffer.write(data, 0, bytesRead);
		return buffer.toByteArray();
	}

	private boolean esArchivoAnidado(String nombreArchivo) {
		return nombreArchivo.endsWith(".jar") || nombreArchivo.endsWith(".zip") || nombreArchivo.endsWith(".fpm")
				|| nombreArchivo.endsWith(".litemod") || nombreArchivo.endsWith(".war")
				|| nombreArchivo.endsWith(".ear") || nombreArchivo.endsWith(".rar");
	}

	// --- INTERFAZ ARCHIVODEMOD ---

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
	public List<String> archivos() {
		return archivos;
	}

	@Override
	public List<Cargador> cargadores() {
		return cargadores_de_mod;
	}

	@Override
	public boolean MetaDataTieneReferenciaDeMCReator() {
		return meta_tiene_referencia_de_mcreator;
	}

	@Override
	public String version() {
		return version;
	}

	@Override
	public boolean existeClase(String nombreClase) {
		if (nombreClase == null || nombreClase.isEmpty())
			return false;
		String interno = normalizarNombreInterno(nombreClase);
		return mapaEntradaPorClase.containsKey(interno) || clases.contains(interno.replace('/', '.'));
	}

	@Override
	public byte[] obtenerBytesClase(String nombreClase) {
		if (nombreClase == null || nombreClase.isEmpty())
			return null;
		String interno = normalizarNombreInterno(nombreClase);
		byte[] cached = cacheBytesClase.get(interno);
		if (cached != null)
			return cached;

		String nombreEntrada = mapaEntradaPorClase.get(interno);
		if (nombreEntrada == null)
			return null;

		try {
			byte[] data = leerEntrada(nombreEntrada);
			if (data != null)
				cacheBytesClase.put(interno, data);
			return data;
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	@Override
	public List<String> obtenerTodosLosNombresDeClases() {
		return new ArrayList<>(mapaEntradaPorClase.keySet());
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
				String r = mod.obtenerNombreRecursivo(nombre);
				if (r != null)
					return r;
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
				String r = mod.obtenerArchivoRecursivo(archivo);
				if (r != null)
					return r;
			}
		}
		return null;
	}

	@Override
	public List<ArchivoDeMod> buscarModsCon(String termino) {
		List<ArchivoDeMod> resultados = new ArrayList<>();
		if (termino == null || termino.isEmpty())
			return resultados;
		String t = termino;
		if (t.endsWith(".class"))
			t = t.substring(0, t.length() - 6);
		String tDots = t.replace('/', '.');
		String tSlashes = t.replace('.', '/');

		boolean tieneArchivo = archivos.contains(termino) || archivos.contains(tSlashes + ".class")
				|| archivos.contains(tDots);
		boolean tieneClaseExacta = clases.contains(tDots) || mapaEntradaPorClase.containsKey(tSlashes)
				|| mapaEntradaPorClase.containsKey(normalizarNombreInterno(t));
		boolean tienePaquete = mapaEntradaPorClase.keySet().stream().anyMatch(c -> c.startsWith(tSlashes))
				|| clases.stream().anyMatch(c -> c.startsWith(tDots));

		if (tieneArchivo || tieneClaseExacta || tienePaquete)
			resultados.add(this);
		for (ArchivoDeMod mod : mods_en_mod)
			resultados.addAll(mod.buscarModsCon(termino));
		return resultados;
	}

	// --- PRECACHE RECURSIVO ---

	public int precargarTodasLasClases() {
		int cargadas = 0;
		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytesZip))) {
			ZipEntry entrada;
			while ((entrada = zip.getNextEntry()) != null) {
				String nombreEntrada = entrada.getName();
				if (nombreEntrada.endsWith(".class")) {
					String nombreInterno = nombreEntrada.substring(0, nombreEntrada.length() - 6);
					if (mapaEntradaPorClase.containsKey(nombreInterno) && !cacheBytesClase.containsKey(nombreInterno)) {
						byte[] data = leer(zip);
						if (data != null) {
							cacheBytesClase.put(nombreInterno, data);
							cargadas++;
						}
					}
				}
				zip.closeEntry();
			}
		} catch (IOException ex) {
			CrashDetectorLogger.logException(ex);
		}
		return cargadas;
	}

	public int precargarTodasLasClasesRecursivo() {
		int total = precargarTodasLasClases();
		for (ArchivoDeMod hijo : mods_en_mod) {
			if (hijo instanceof ModPKZip)
				total += ((ModPKZip) hijo).precargarTodasLasClasesRecursivo();
		}
		return total;
	}

	private static String normalizarNombreInterno(String nombre) {
		String n = nombre;
		if (n.endsWith(".class"))
			n = n.substring(0, n.length() - 6);
		return n.replace('.', '/');
	}
}