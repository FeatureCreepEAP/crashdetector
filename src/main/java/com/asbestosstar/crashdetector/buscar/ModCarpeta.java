package com.asbestosstar.crashdetector.buscar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.cargador.Cargador;
import com.asbestosstar.crashdetector.cargador.CargadorFeatureCreep;
import com.asbestosstar.crashdetector.cargador.CargadorMCForge;

/**
 * Clase que procesa carpetas para buscar mods y clases dentro de ellas. Soporta
 * tanto mods de HOI4 como módulos de JBoss en estructuras de carpetas. Maneja
 * archivos JAR/ZIP anidados dentro de estructuras de carpetas (especialmente
 * para JBoss).
 *
 * NOTA: Las subcarpetas normalmente NO son mods independientes, sino parte del
 * mismo mod.
 * 
 * En Versiones Viajas de Minecraft Forge (circa 1.6.4) es posible tiene mods en
 * carpetas (millienaire 1.6.4 desde TLMods) pero el formato de es comun y
 * siempre diferente, no necesita mcmod.info
 */
public class ModCarpeta implements ArchivoDeMod {

	public ArchivoDeMod desde;
	public String ubicacion;
	public List<ArchivoDeMod> mods_en_mod = new ArrayList<>();
	public List<String> nombres = new ArrayList<>();
	public List<String> clases = new ArrayList<>();
	public List<String> archivos = new ArrayList<>();

	private final Map<String, Path> mapaRutasClase = new java.util.concurrent.ConcurrentHashMap<>();
	private final Map<String, byte[]> cacheBytesClase = new java.util.concurrent.ConcurrentHashMap<>();

	// Métricas para decidir precarga (opcional) en mods pequeños
	private long totalTamanoClases = 0L;
	private int totalConteoClases = 0;

	// Umbrales de precarga (ajustables)
	private static final int EAGER_MAX_CLASES = 150;
	private static final long EAGER_MAX_BYTES = 1_500_000L; // ~1.5 MB

	private final Path rutaRaiz;
	public List<Cargador> cargadores_de_mod = new ArrayList<Cargador>();

	public String version = "";

	/**
	 * Constructor principal que procesa una carpeta de mods. Detecta
	 * automáticamente si es un mod de HOI4 o un módulo de JBoss.
	 */
	public ModCarpeta(String ubicacion, ArchivoDeMod desde, Path rutaRaiz) {
		this.ubicacion = ubicacion;
		this.desde = desde;
		this.rutaRaiz = rutaRaiz;

		try {
			procesarCarpeta(rutaRaiz, true);
			// Precarga opcional sólo si el mod es pequeño
			precargarSiPequeno();
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	/**
	 * Procesa recursivamente una carpeta para identificar mods y clases.
	 *
	 * @param ruta   Ruta a procesar
	 * @param esRaiz Indica si es la carpeta raíz del mod actual
	 */
	private boolean procesarCarpeta(Path ruta, boolean esRaiz) {
		boolean contieneDefinicionMod = false;

		try (DirectoryStream<Path> flujoDirectorio = Files.newDirectoryStream(ruta)) {
			for (Path entrada : flujoDirectorio) {
				String nombre = entrada.getFileName().toString();
				archivos.add(nombre);

				if (Files.isDirectory(entrada)) {
					// Procesar subcarpeta
					boolean subContieneDefinicionMod = procesarCarpeta(entrada, false);

					// Si la subcarpeta contiene un mod definido y estamos en un nivel raíz,
					// entonces es un mod independiente
					if (subContieneDefinicionMod && esRaiz) {
						String nuevaUbicacion = ubicacion + "/" + nombre;
						mods_en_mod.add(new ModCarpeta(nuevaUbicacion, this, entrada));
					}
				} else {
					// Procesar archivo
					if (nombre.endsWith("modules.xml") || nombre.endsWith(".mod")) {
						contieneDefinicionMod = true;

						if (nombre.endsWith("modules.xml")) {
							byte[] mxmlbytes = Files.readAllBytes(entrada);
							nombres.addAll(CargadorFeatureCreep.parsearNombreModuloJBoss(mxmlbytes));
							if (this.version.isEmpty()) {
								this.version = CargadorFeatureCreep.parsearVersionModuloJBoss(mxmlbytes);
							}
						} else if (nombre.endsWith(".mod")) {
							byte[] archivomodbytes = Files.readAllBytes(entrada);
							nombres.addAll(CargadorFeatureCreep.parsearNombreModHOI4(archivomodbytes));
							if (this.version.isEmpty()) {
								this.version = CargadorFeatureCreep.parsearVersionModuloJBoss(archivomodbytes);
							}
						}
					} else if (nombre.endsWith(".class")) {
						procesarClase(entrada);
					} else if (nombre.endsWith("MANIFEST.MF")) {
						nombres.addAll(ProcesadorManifiesto.obtenerNombresDeModulo(
								new Manifest(new FileInputStream(entrada.toAbsolutePath().toString()))));
					}

					else if (nombre.equals("mcmod.info") || nombre.equals("META-INF/mcmod.info")) {
						byte[] content = Files.readAllBytes(entrada);
						if (content != null) {
							String texto = new String(content, StandardCharsets.UTF_8);

							agregarNombresSinDuplicados(CargadorMCForge.parsearIdModMcmodInfo(texto));

							if (this.version.isEmpty()) {
								this.version = CargadorMCForge.parsearVersionModMcmodInfo(texto);
							}

						}
					}

					else if (esArchivoAnidado(nombre)) {
						procesarArchivoAnidado(entrada, nombre);
					} else {
						// No cargamos bytes por adelantado para mejorar rendimiento.
					}
				}
			}

			for (Cargador cargador : Cargador.cargadores) {
				if (cargador.suporteModsDeCarpetas() && cargador.modEsDeCargador(this)) {
					cargadores_de_mod.add(cargador);
				}
			}

		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
		}

		return contieneDefinicionMod;
	}

	/**
	 * Procesa un archivo de clase. No carga bytes por adelantado; sólo registra la
	 * ruta y nombres (Java e interno).
	 */
	private void procesarClase(Path entrada) {
		String nombreClaseJava = entrada.toString().replace(rutaRaiz.toString(), "").replace(File.separator, ".")
				.replace(".class", "");
		if (nombreClaseJava.startsWith(".")) {
			nombreClaseJava = nombreClaseJava.substring(1);
		}
		clases.add(nombreClaseJava);

		// Nombre interno estilo ASM: pkg/Clase (sin .class)
		String nombreInterno = entrada.toString().replace(rutaRaiz.toString(), "").substring(1)
				.replace(File.separator, "/").replace(".class", "");

		// Guardamos ruta para carga perezosa
		mapaRutasClase.put(nombreInterno, entrada);
		totalConteoClases++;
		try {
			totalTamanoClases += Files.size(entrada);
		} catch (IOException e) {
			// no crítico; continuar
			CrashDetectorLogger.logException(e);
		}

		if (nombreClaseJava.endsWith("module-info")) {
			byte[] bytes = this.obtenerBytesClase(nombreClaseJava);
			if (bytes != null) {
				this.nombre().addAll(this.obtenerNombresDeModuleInfo(bytes));
			}
		}

	}

	/**
	 * Si el módulo es pequeño, precarga bytes para acelerar accesos posteriores.
	 */
	private void precargarSiPequeno() {
		if (totalConteoClases <= EAGER_MAX_CLASES && totalTamanoClases <= EAGER_MAX_BYTES) {
			for (Map.Entry<String, Path> e : mapaRutasClase.entrySet()) {
				try {
					cacheBytesClase.put(e.getKey(), Files.readAllBytes(e.getValue()));
				} catch (IOException ex) {
					CrashDetectorLogger.logException(ex);
				}
			}
		}
	}

	/**
	 * Procesa archivos anidados (JAR, ZIP, etc.) creando instancias de ModPKZip.
	 */
	private void procesarArchivoAnidado(Path entrada, String nombre) {
		try {
			String nuevaUbicacion = ubicacion + "/" + nombre;
			// Usar el constructor de File en lugar de InputStream para máxima eficiencia de
			// RAM
			mods_en_mod.add(new ModPKZip(entrada.toFile(), this));
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
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
			if (this.archivos().contains(archivo))
				return this.ubicacion() + "/" + archivo;
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

		// canonical forms
		String tDots = t.replace('/', '.'); // com.foo.Bar
		String tSlashes = t.replace('.', '/'); // com/foo/Bar

		// archivo: tu lista 'archivos' solo guarda nombres de archivo (basename),
		// pero el usuario puede pasar "x/y/z.txt". En ese caso, también probamos
		// basename.
		String baseName = t;
		int slash = baseName.lastIndexOf('/');
		if (slash >= 0)
			baseName = baseName.substring(slash + 1);
		int back = baseName.lastIndexOf('\\');
		if (back >= 0)
			baseName = baseName.substring(back + 1);

		boolean tieneArchivo = archivos.contains(termino) || archivos.contains(baseName)
				|| archivos.contains(baseName + ".class");

		// clase exacta: tu almacenamiento humano es dotted, tu índice real es internal
		boolean tieneClaseExacta = clases.contains(tDots) || mapaRutasClase.containsKey(tSlashes);

		// paquete: aceptar "com.foo" o "com/foo"
		boolean tienePaquete = mapaRutasClase.keySet().stream().anyMatch(c -> c.startsWith(tSlashes))
				|| clases.stream().anyMatch(c -> c.startsWith(tDots));

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
		if (mapaRutasClase.containsKey(interno))
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

		String interno = normalizarNombreInterno(nombreClase);

		byte[] cached = cacheBytesClase.get(interno);
		if (cached != null)
			return cached;

		Path ruta = mapaRutasClase.get(interno);
		if (ruta == null)
			return null;

		try {
			byte[] bytes = Files.readAllBytes(ruta);
			// publish once (if another thread won, reuse theirs)
			byte[] prev = cacheBytesClase.putIfAbsent(interno, bytes);
			return (prev != null) ? prev : bytes;
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	@Override
	public List<String> obtenerTodosLosNombresDeClases() {
		// Devolver los nombres internos (pkg/Clase) conocidos
		return new ArrayList<>(mapaRutasClase.keySet());
	}

	@Override
	public List<Cargador> cargadores() {
		return cargadores_de_mod;
	}

	/**
	 * Precarga los bytes de TODAS las clases de esta carpeta en el caché interno.
	 *
	 * @return número de clases cargadas en caché en esta instancia
	 */
	public int precargarTodasLasClases() {
		int cargadas = 0;
		for (Map.Entry<String, Path> e : mapaRutasClase.entrySet()) {
			final String interno = e.getKey();
			if (cacheBytesClase.containsKey(interno)) {
				continue; // ya en caché
			}
			try {
				byte[] data = Files.readAllBytes(e.getValue());
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
	 * Precarga de forma RECURSIVA todas las clases de esta carpeta y de sus mods
	 * anidados (ModCarpeta/ModPKZip).
	 *
	 * @return número total de clases precargadas (esta instancia + anidados)
	 */
	public int precargarTodasLasClasesRecursivo() {
		int total = precargarTodasLasClases();
		for (ArchivoDeMod hijo : mods_en_mod) {
			try {
				if (hijo instanceof ModCarpeta) {
					total += ((ModCarpeta) hijo).precargarTodasLasClasesRecursivo();
				} else if (hijo instanceof ModPKZip) {
					total += ((ModPKZip) hijo).precargarTodasLasClasesRecursivo();
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
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
		return version;
	}
}
