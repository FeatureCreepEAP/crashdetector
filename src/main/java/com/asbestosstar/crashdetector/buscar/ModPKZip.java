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
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.cargador.Cargador;
import com.asbestosstar.crashdetector.cargador.CargadorBukkit;
import com.asbestosstar.crashdetector.cargador.CargadorFabric;
import com.asbestosstar.crashdetector.cargador.CargadorFeatureCreep;
import com.asbestosstar.crashdetector.cargador.CargadorLiteLoader;
import com.asbestosstar.crashdetector.cargador.CargadorMCForge;
import com.asbestosstar.crashdetector.cargador.CargadorMeddle;
import com.asbestosstar.crashdetector.cargador.CargadorNilLoader;
import com.asbestosstar.crashdetector.cargador.CargadorRift;
import com.asbestosstar.crashdetector.cargador.ml.AnalizadorModsTomlForge;

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
	private final Map<String, byte[]> cacheBytesEntrada = new java.util.concurrent.ConcurrentHashMap<>();
	private final Map<String, byte[]> cacheBytesClase = new java.util.concurrent.ConcurrentHashMap<>();

	public List<Cargador> cargadores_de_mod = new ArrayList<Cargador>();
	public boolean meta_tiene_referencia_de_mcreator = false;
	public String version = "";

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
	 * Recorre el ZIP para: - Llenar 'archivos' - Mapear clases - Detectar y leer
	 * SOLO metadatos necesarios - Construir mods anidados
	 */
	private void indexarYProcesarMetadatos() throws IOException {
		try (JarInputStream zip = new JarInputStream(new ByteArrayInputStream(bytesZip))) {
			Manifest man = zip.getManifest();
			if (man != null) {

				procesarManifest(man);

			}

			ZipEntry e;
			while ((e = zip.getNextEntry()) != null) {
				final String nombreArchivo = e.getName();
				archivos.add(nombreArchivo);

				if (esArchivoAnidado(nombreArchivo)) {
					// Para anidados: crear ModPKZip hijo con bytes del anidado
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

					String nombreInterno = nombreArchivo.substring(0, nombreArchivo.length() - 6);
					mapaEntradaPorClase.put(nombreInterno, nombreArchivo);

					if (nombreClaseJava.endsWith("module-info")) {
						byte[] bytes = this.obtenerBytesClase(nombreClaseJava);
						if (bytes != null) {
							nombres.addAll(this.obtenerNombresDeModuleInfo(bytes));
						}
					}
				} else if (nombreArchivo.endsWith("modules.xml")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						agregarNombresSinDuplicados(CargadorFeatureCreep.parsearNombreModuloJBoss(content));

						if (this.version.isEmpty()) {
							this.version = CargadorFeatureCreep.parsearVersionModuloJBoss(content);
						}
					}
				} else if (nombreArchivo.endsWith(".mod")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						agregarNombresSinDuplicados(CargadorFeatureCreep.parsearNombreModHOI4(content));

						if (this.version.isEmpty()) {
							this.version = CargadorFeatureCreep.parsearVersionModHOI4(content);
						}
					}
				} else if (nombreArchivo.equals("fabric.mod.json")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String texto = new String(content, StandardCharsets.UTF_8);

						agregarNombresSinDuplicados(CargadorFabric.parsearIdModFabric(texto));

						if (this.version.isEmpty()) {
							this.version = CargadorFabric.parsearVersionModFabric(texto);
						}

						if (texto.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				} else if (nombreArchivo.equals("riftmod.json")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String texto = new String(content, StandardCharsets.UTF_8);

						agregarNombresSinDuplicados(CargadorRift.parsearIdModRift(texto));

						if (this.version.isEmpty()) {
							this.version = CargadorRift.parsearVersionModRift(texto);
						}

						if (texto.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				} else if (nombreArchivo.equals("litemod.json")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String texto = new String(content, StandardCharsets.UTF_8);

						agregarNombresSinDuplicados(CargadorLiteLoader.parsearIdModLiteLoader(texto));

						if (this.version.isEmpty()) {
							this.version = CargadorLiteLoader.parsearVersionModLiteLoader(texto);
						}

						if (texto.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				} else if (nombreArchivo.equals("mcmod.info") || nombreArchivo.equals("META-INF/mcmod.info")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String texto = new String(content, StandardCharsets.UTF_8);

						agregarNombresSinDuplicados(CargadorMCForge.parsearIdModMcmodInfo(texto));

						if (this.version.isEmpty()) {
							this.version = CargadorMCForge.parsearVersionModMcmodInfo(texto);
						}

						if (texto.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				} else if (nombreArchivo.equals("fcflat.properties")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						agregarNombresSinDuplicados(CargadorFeatureCreep.parsearIdModFlat(content));

						if (this.version.isEmpty()) {
							this.version = CargadorFeatureCreep.parsearVersionModFlat(content);
						}
					}
				}

				else if (nombreArchivo.equals("plugin.yml") || nombreArchivo.equals("paper-plugin.yml")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String texto = new String(content, StandardCharsets.UTF_8);

						agregarNombresSinDuplicados(CargadorBukkit.parsearIdModBukkit(texto));

						if (this.version.isEmpty()) {
							this.version = CargadorBukkit.parsearVersionModBukkit(texto);
						}

						if (texto.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				}

				else if (nombreArchivo.toLowerCase(java.util.Locale.ROOT).endsWith(".nilmod.css")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String texto = new String(content, StandardCharsets.UTF_8);

						agregarNombresSinDuplicados(
								CargadorNilLoader.parsearIdModNilLoaderDesdeNombreArchivo(nombreArchivo));

						if (this.version.isEmpty()) {
							this.version = CargadorNilLoader.parsearVersionModNilLoader(texto);
						}

						if (texto.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				}

				else if (nombreArchivo.endsWith("mods.toml")) {
					byte[] content = leerEntrada(nombreArchivo);
					if (content != null) {
						String toml = new String(content, StandardCharsets.UTF_8);

						agregarNombresSinDuplicados(CargadorMCForge.parsearIdModMCForge(toml));

						if (this.version.isEmpty()) {
							this.version = AnalizadorModsTomlForge.extraerVersionPrincipal(toml);
						}

						if (toml.toLowerCase().contains("mcreator")) {
							meta_tiene_referencia_de_mcreator = true;
						}
					}
				}

				zip.closeEntry();
			}
		}
	}

	/**
	 * Procesa metadata obtenida desde el Manifest del JAR.
	 *
	 * Importante: META-INF/MANIFEST.MF no siempre aparece como una entrada normal
	 * del ZIP, por eso esta logica va separada y usa JarInputStream.getManifest().
	 */
	private void procesarManifest(Manifest man) {
		if (man == null) {
			return;
		}

		try {
			// Mantener la logica general ya existente
			agregarNombresSinDuplicados(ProcesadorManifiesto.obtenerNombresDeModulo(man));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		try {
			// Delegar el parseo especifico de Meddle al propio cargador
			if (CargadorMeddle.manifestEsDeMeddle(man)) {
				agregarNombresSinDuplicados(CargadorMeddle.parsearNombresManifestMeddle(man));

				if (this.version.isEmpty()) {
					String versionMeddle = CargadorMeddle.parsearVersionManifestMeddle(man);
					if (versionMeddle != null && !versionMeddle.trim().isEmpty()) {
						this.version = versionMeddle.trim();
					}
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		try {
			// Version generica de manifest si existe, sin pisar otras ya detectadas
			if (this.version.isEmpty()) {
				java.util.jar.Attributes attr = man.getMainAttributes();

				if (attr != null) {
					String implementationVersion = attr.getValue("Implementation-Version");

					if (implementationVersion != null) {
						implementationVersion = implementationVersion.trim();
						if (!implementationVersion.isEmpty()) {
							this.version = implementationVersion;
						}
					}
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		try {
			String textoManifest = man.toString();
			if (textoManifest != null && textoManifest.toLowerCase().contains("mcreator")) {
				meta_tiene_referencia_de_mcreator = true;
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	/**
	 * Agrega nombres evitando duplicados y vacios.
	 */
	private void agregarNombresSinDuplicados(List<String> nuevos) {
		if (nuevos == null) {
			return;
		}

		for (String nombre : nuevos) {
			if (nombre == null) {
				continue;
			}

			String limpio = nombre.trim();

			if (!limpio.isEmpty() && !this.nombres.contains(limpio)) {
				this.nombres.add(limpio);
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
		if (termino == null || termino.isEmpty())
			return resultados;

		String t = termino;
		if (t.endsWith(".class"))
			t = t.substring(0, t.length() - 6);

		String tDots = t.replace('/', '.'); // com.foo.Bar
		String tSlashes = t.replace('.', '/'); // com/foo/Bar

		boolean tieneArchivo = archivos.contains(termino) || archivos.contains(tSlashes + ".class")
				|| archivos.contains(tDots);

		boolean tieneClaseExacta = clases.contains(tDots) || mapaEntradaPorClase.containsKey(tSlashes) || // internal
																											// index
				mapaEntradaPorClase.containsKey(normalizarNombreInterno(t));

		boolean tienePaquete = mapaEntradaPorClase.keySet().stream().anyMatch(c -> c.startsWith(tSlashes)) // internal
				|| clases.stream().anyMatch(c -> c.startsWith(tDots)); // dots

		if (tieneArchivo || tieneClaseExacta || tienePaquete) {
			resultados.add(this);
		}

		for (ArchivoDeMod mod : mods_en_mod) {
			resultados.addAll(mod.buscarModsCon(termino));
		}
		return resultados;
	}

	@Override
	public boolean existeClase(String nombreClase) {
		if (nombreClase == null || nombreClase.isEmpty())
			return false;

		String interno = normalizarNombreInterno(nombreClase);
		if (mapaEntradaPorClase.containsKey(interno))
			return true;

		String dots = interno.replace('/', '.');
		return clases.contains(dots);
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
	/**
	 * Precarga los bytes de TODAS las clases de este ZIP en el caché interno. Útil
	 * si se desea evitar lecturas repetidas del ZIP durante un análisis intensivo.
	 *
	 * @return número de clases cargadas en caché en esta instancia
	 */
	public int precargarTodasLasClases() {
		int cargadas = 0;
		try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytesZip))) {
			ZipEntry entrada;
			while ((entrada = zip.getNextEntry()) != null) {
				String nombreEntrada = entrada.getName();
				if (nombreEntrada.endsWith(".class")) {
					String nombreInterno = nombreEntrada.substring(0, nombreEntrada.length() - 6); // sin ".class"

					// Solo procesar si es una clase indexada (evita redundancias con clases no
					// mapeadas)
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

	@Override
	public String version() {
		// TODO Auto-generated method stub
		return "version";
	}
}
