package com.asbestosstar.crashdetector.gui.tipos.busquedabinaria;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;

/**
 * Motor reversible de búsqueda binaria de mods.
 *
 * <p>
 * No borra mods. Los renombra temporalmente con una extensión que los
 * cargadores no reconocen y conserva un manifiesto para poder restaurarlos
 * después de una interrupción.
 * </p>
 *
 * <p>
 * La partición es consciente de dependencias: si se desactiva una dependencia,
 * también se desactivan sus dependientes. Las bibliotecas requeridas por los
 * mods que permanecen activos nunca se desactivan.
 * </p>
 */
public final class MotorBusquedaBinariaMods {

	private static final String SUFIJO_DESACTIVADO = ".cd-binary-search-disabled";
	private static final String NOMBRE_MANIFIESTO = "sesion.properties";

	private final Path carpetaEstado = Statics.carpeta.resolve("busqueda_binaria_mods");
	private final Path manifiesto = carpetaEstado.resolve(NOMBRE_MANIFIESTO);

	private final LinkedHashSet<Path> todosLosMods = new LinkedHashSet<Path>();
	private final Map<Path, Set<Path>> dependencias = new LinkedHashMap<Path, Set<Path>>();
	private final Map<Path, Set<Path>> dependientes = new LinkedHashMap<Path, Set<Path>>();
	private final LinkedHashSet<Path> candidatos = new LinkedHashSet<Path>();
	private final LinkedHashSet<Path> candidatosPrueba = new LinkedHashSet<Path>();
	private final LinkedHashSet<Path> desactivadosPrueba = new LinkedHashSet<Path>();
	private final List<String> historial = new ArrayList<String>();

	private int ronda;
	private boolean iniciada;
	private boolean terminada;

	public static final class Prueba {
		public final int ronda;
		public final List<Path> candidatos;
		public final List<Path> candidatosDesactivados;
		public final List<Path> todosDesactivados;

		private Prueba(int ronda, Collection<Path> candidatos, Collection<Path> candidatosDesactivados,
				Collection<Path> todosDesactivados) {
			this.ronda = ronda;
			this.candidatos = listaOrdenada(candidatos);
			this.candidatosDesactivados = listaOrdenada(candidatosDesactivados);
			this.todosDesactivados = listaOrdenada(todosDesactivados);
		}
	}

	public static final class ResultadoRonda {
		public enum Tipo {
			CONTINUAR, UNICO, GRUPO, INCONSISTENTE
		}

		public final Tipo tipo;
		public final Prueba siguiente;
		public final List<Path> resultado;

		private ResultadoRonda(Tipo tipo, Prueba siguiente, Collection<Path> resultado) {
			this.tipo = tipo;
			this.siguiente = siguiente;
			this.resultado = listaOrdenada(resultado);
		}

		public static ResultadoRonda continuar(Prueba prueba) {
			return new ResultadoRonda(Tipo.CONTINUAR, prueba, Collections.<Path>emptyList());
		}

		public static ResultadoRonda unico(Path mod) {
			return new ResultadoRonda(Tipo.UNICO, null, Collections.singletonList(mod));
		}

		public static ResultadoRonda grupo(Collection<Path> mods) {
			return new ResultadoRonda(Tipo.GRUPO, null, mods);
		}

		public static ResultadoRonda inconsistente() {
			return new ResultadoRonda(Tipo.INCONSISTENTE, null, Collections.<Path>emptyList());
		}
	}

	public boolean hayRecuperacionPendiente() {
		return Files.isRegularFile(manifiesto);
	}

	/**
	 * Restaura una sesión interrumpida sin necesitar el estado en memoria.
	 */
	public synchronized int restaurarRecuperacionPendiente() throws IOException {
		if (!Files.isRegularFile(manifiesto)) {
			return 0;
		}

		Properties p = cargarManifiesto();
		int cantidad = parsearEntero(p.getProperty("cantidad"), 0);
		int restaurados = 0;

		for (int i = 0; i < cantidad; i++) {
			String originalTexto = p.getProperty("original." + i);
			String desactivadoTexto = p.getProperty("desactivado." + i);
			if (originalTexto == null || desactivadoTexto == null) {
				continue;
			}

			Path original = Paths.get(originalTexto);
			Path desactivado = Paths.get(desactivadoTexto);
			if (!Files.exists(desactivado)) {
				continue;
			}
			if (Files.exists(original)) {
				throw new IOException("Existen a la vez el mod original y su copia desactivada: " + original);
			}

			Files.createDirectories(original.toAbsolutePath().normalize().getParent());
			moverConRespaldo(desactivado, original);
			restaurados++;
		}

		Files.deleteIfExists(manifiesto);
		limpiarCarpetaEstado();
		return restaurados;
	}

	public synchronized void iniciar() throws IOException {
		if (hayRecuperacionPendiente()) {
			throw new IOException("RECUPERACION_PENDIENTE");
		}

		limpiarEstadoEnMemoria();
		escanearModsFisicos();

		if (todosLosMods.size() < 2) {
			throw new IOException("NO_HAY_MODS");
		}

		inicializarMapas();
		construirDependenciasConBuscador();
		construirDependenciasDeMetadatos();
		construirMapaDeDependientes();

		candidatos.addAll(todosLosMods);
		iniciada = true;
		terminada = false;
	}

	public synchronized Prueba prepararPrimeraPrueba() throws IOException {
		comprobarIniciada();
		return prepararSiguientePrueba();
	}

	public synchronized ResultadoRonda registrarResultado(boolean problemaPersiste) throws IOException {
		comprobarIniciada();

		int rondaTerminada = ronda;
		LinkedHashSet<Path> probados = new LinkedHashSet<Path>(candidatosPrueba);

		restaurarTodoInterno();

		if (problemaPersiste) {
			candidatos.removeAll(probados);
			historial.add("PERSISTE:" + rondaTerminada + ":" + unirNombres(probados));
		} else {
			candidatos.retainAll(probados);
			historial.add("DESAPARECE:" + rondaTerminada + ":" + unirNombres(probados));
		}

		candidatosPrueba.clear();
		desactivadosPrueba.clear();

		if (candidatos.isEmpty()) {
			terminada = true;
			return ResultadoRonda.inconsistente();
		}

		if (candidatos.size() == 1) {
			terminada = true;
			Path unico = candidatos.iterator().next();
			return ResultadoRonda.unico(unico);
		}

		LinkedHashSet<Path> siguienteSubconjunto = elegirSubconjuntoSeguro(candidatos);
		if (siguienteSubconjunto.isEmpty() || siguienteSubconjunto.size() >= candidatos.size()) {
			terminada = true;
			return ResultadoRonda.grupo(candidatos);
		}

		Prueba siguiente = aplicarPrueba(siguienteSubconjunto);
		return ResultadoRonda.continuar(siguiente);
	}

	public synchronized void restaurarTodo() throws IOException {
		restaurarTodoInterno();
		iniciada = false;
		terminada = true;
		candidatosPrueba.clear();
		desactivadosPrueba.clear();
	}

	public synchronized List<Path> obtenerCandidatos() {
		return listaOrdenada(candidatos);
	}

	public synchronized List<Path> obtenerDesactivadosPrueba() {
		return listaOrdenada(desactivadosPrueba);
	}

	public synchronized List<String> obtenerHistorial() {
		return new ArrayList<String>(historial);
	}

	public synchronized boolean estaTerminada() {
		return terminada;
	}

	private Prueba prepararSiguientePrueba() throws IOException {
		LinkedHashSet<Path> subconjunto = elegirSubconjuntoSeguro(candidatos);
		if (subconjunto.isEmpty() || subconjunto.size() >= candidatos.size()) {
			throw new IOException("SIN_DIVISION_SEGURA");
		}
		return aplicarPrueba(subconjunto);
	}

	private Prueba aplicarPrueba(Set<Path> subconjuntoCandidatos) throws IOException {
		restaurarTodoInterno();

		candidatosPrueba.clear();
		candidatosPrueba.addAll(subconjuntoCandidatos);

		desactivadosPrueba.clear();
		for (Path mod : subconjuntoCandidatos) {
			agregarDependientesRecursivos(mod, desactivadosPrueba);
		}

		// Solo se pueden mover archivos que estaban en el conjunto inicial.
		desactivadosPrueba.retainAll(todosLosMods);

		if (desactivadosPrueba.isEmpty()) {
			throw new IOException("SIN_DIVISION_SEGURA");
		}

		LinkedHashMap<Path, Path> movimientos = new LinkedHashMap<Path, Path>();
		for (Path original : listaOrdenada(desactivadosPrueba)) {
			if (!Files.isRegularFile(original)) {
				throw new IOException("No existe el mod que se intentó desactivar: " + original);
			}
			movimientos.put(original, obtenerRutaDesactivadaDisponible(original));
		}

		/*
		 * El manifiesto se guarda ANTES del primer movimiento. Así, incluso si la JVM
		 * se detiene entre dos renombrados, la siguiente ejecución sabe exactamente qué
		 * rutas debe restaurar. Las entradas que todavía no se movieron se omiten.
		 */
		guardarManifiesto(movimientos);
		try {
			for (Map.Entry<Path, Path> movimiento : movimientos.entrySet()) {
				moverConRespaldo(movimiento.getKey(), movimiento.getValue());
			}
		} catch (IOException e) {
			rollbackMovimientos(movimientos);
			throw e;
		}

		ronda++;
		return new Prueba(ronda, candidatos, candidatosPrueba, desactivadosPrueba);
	}

	/**
	 * Selecciona un conjunto de candidatos cercano a la mitad y cerrado respecto de
	 * dependientes. La unión de cierres también es un cierre seguro.
	 */
	private LinkedHashSet<Path> elegirSubconjuntoSeguro(Set<Path> universo) {
		LinkedHashSet<Path> vacio = new LinkedHashSet<Path>();
		if (universo == null || universo.size() < 2) {
			return vacio;
		}

		int objetivo = Math.max(1, universo.size() / 2);
		List<Path> ordenados = listaOrdenada(universo);
		List<LinkedHashSet<Path>> cierres = new ArrayList<LinkedHashSet<Path>>();

		LinkedHashSet<Path> mejor = null;
		int mejorDistancia = Integer.MAX_VALUE;

		for (Path candidato : ordenados) {
			LinkedHashSet<Path> cierre = new LinkedHashSet<Path>();
			agregarDependientesRecursivos(candidato, cierre);
			cierre.retainAll(universo);

			if (cierre.isEmpty() || cierre.size() >= universo.size()) {
				continue;
			}

			cierres.add(cierre);
			int distancia = Math.abs(cierre.size() - objetivo);
			if (mejor == null || distancia < mejorDistancia
					|| (distancia == mejorDistancia && compararConjuntos(cierre, mejor) < 0)) {
				mejor = new LinkedHashSet<Path>(cierre);
				mejorDistancia = distancia;
			}
		}

		Collections.sort(cierres, new Comparator<LinkedHashSet<Path>>() {
			@Override
			public int compare(LinkedHashSet<Path> a, LinkedHashSet<Path> b) {
				int c = Integer.compare(a.size(), b.size());
				return c != 0 ? c : compararConjuntos(a, b);
			}
		});

		LinkedHashSet<Path> acumulado = new LinkedHashSet<Path>();
		for (LinkedHashSet<Path> cierre : cierres) {
			LinkedHashSet<Path> propuesta = new LinkedHashSet<Path>(acumulado);
			propuesta.addAll(cierre);
			if (propuesta.size() >= universo.size()) {
				continue;
			}
			int distanciaActual = acumulado.isEmpty() ? Integer.MAX_VALUE : Math.abs(acumulado.size() - objetivo);
			int distanciaPropuesta = Math.abs(propuesta.size() - objetivo);
			if (distanciaPropuesta <= distanciaActual) {
				acumulado = propuesta;
			}
		}

		if (!acumulado.isEmpty()) {
			int distancia = Math.abs(acumulado.size() - objetivo);
			if (mejor == null || distancia < mejorDistancia
					|| (distancia == mejorDistancia && compararConjuntos(acumulado, mejor) < 0)) {
				mejor = acumulado;
			}
		}

		return mejor == null ? vacio : mejor;
	}

	private void agregarDependientesRecursivos(Path mod, Set<Path> salida) {
		if (mod == null || !salida.add(mod)) {
			return;
		}
		for (Path dependiente : dependientes.getOrDefault(mod, Collections.<Path>emptySet())) {
			agregarDependientesRecursivos(dependiente, salida);
		}
	}

	private void escanearModsFisicos() throws IOException {
		String rutaCrashDetector = MonitorDePID.obtenerRutaJarCrashDetector();
		Path crashDetector = rutaCrashDetector == null ? null
				: Paths.get(rutaCrashDetector).toAbsolutePath().normalize();

		for (Path carpeta : Statics.carpetas_de_mods) {
			if (carpeta == null || !Files.isDirectory(carpeta)) {
				continue;
			}

			File[] archivos = carpeta.toFile().listFiles();
			if (archivos == null) {
				continue;
			}

			for (File archivo : archivos) {
				if (!archivo.isFile() || !esExtensionDeModActiva(archivo.getName())) {
					continue;
				}

				Path ruta = archivo.toPath().toAbsolutePath().normalize();
				if (crashDetector != null && rutasIguales(ruta, crashDetector)) {
					continue;
				}
				todosLosMods.add(ruta);
			}
		}
	}

	private boolean esExtensionDeModActiva(String nombre) {
		String n = nombre.toLowerCase(Locale.ROOT);
		return n.endsWith(".jar") || n.endsWith(".zip") || n.endsWith(".litemod") || n.endsWith(".fpm")
				|| n.endsWith(".nilmod") || n.endsWith(".mod");
	}

	private void inicializarMapas() {
		for (Path mod : todosLosMods) {
			dependencias.put(mod, new LinkedHashSet<Path>());
			dependientes.put(mod, new LinkedHashSet<Path>());
		}
	}

	private void construirDependenciasConBuscador() {
		try {
			List<ArchivoDeMod> raices = Buscador.obtenerModsPrimerNivel();
			final Map<ArchivoDeMod, Path> raizFisica = new LinkedHashMap<ArchivoDeMod, Path>();
			final Map<String, Path> claseARaiz = new HashMap<String, Path>();
			final Map<Path, List<ArchivoDeMod>> nodosPorRaiz = new LinkedHashMap<Path, List<ArchivoDeMod>>();

			for (ArchivoDeMod raiz : raices) {
				Path ruta = normalizarRutaArchivo(raiz.ubicacion());
				if (ruta == null || !todosLosMods.contains(ruta)) {
					continue;
				}
				raizFisica.put(raiz, ruta);
				List<ArchivoDeMod> nodos = new ArrayList<ArchivoDeMod>();
				agregarNodosRecursivos(raiz, nodos);
				nodosPorRaiz.put(ruta, nodos);

				for (ArchivoDeMod nodo : nodos) {
					for (String clase : nodo.obtenerTodosLosNombresDeClases()) {
						String normalizada = normalizarClase(clase);
						if (normalizada != null) {
							claseARaiz.put(normalizada, ruta);
						}
					}
				}
			}

			int hilos = Math.max(1,
					Math.min(nodosPorRaiz.size(), Math.max(1, Runtime.getRuntime().availableProcessors() - 1)));
			ExecutorService pool = Executors.newFixedThreadPool(hilos);
			List<Future<Map.Entry<Path, Set<Path>>>> futuros = new ArrayList<Future<Map.Entry<Path, Set<Path>>>>();

			for (final Map.Entry<Path, List<ArchivoDeMod>> entrada : nodosPorRaiz.entrySet()) {
				futuros.add(pool.submit(new Callable<Map.Entry<Path, Set<Path>>>() {
					@Override
					public Map.Entry<Path, Set<Path>> call() {
						LinkedHashSet<Path> deps = new LinkedHashSet<Path>();
						for (ArchivoDeMod nodo : entrada.getValue()) {
							analizarReferenciasNodo(nodo, entrada.getKey(), claseARaiz, deps);
						}
						return new java.util.AbstractMap.SimpleImmutableEntry<Path, Set<Path>>(entrada.getKey(), deps);
					}
				}));
			}

			try {
				for (Future<Map.Entry<Path, Set<Path>>> futuro : futuros) {
					Map.Entry<Path, Set<Path>> resultado = futuro.get();
					dependencias.get(resultado.getKey()).addAll(resultado.getValue());
				}
			} finally {
				pool.shutdownNow();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			CrashDetectorLogger.logException(e);
		} catch (ExecutionException e) {
			CrashDetectorLogger.logException(e);
		} catch (Throwable t) {
			// La búsqueda binaria sigue disponible con las dependencias de metadatos.
			CrashDetectorLogger.logException(t);
		}
	}

	private void analizarReferenciasNodo(ArchivoDeMod nodo, Path raizOrigen, Map<String, Path> claseARaiz,
			Set<Path> salida) {
		try {
			for (String clase : nodo.obtenerTodosLosNombresDeClases()) {
				String claseInterna = normalizarClase(clase);
				if (claseInterna == null || !nodo.existeClase(claseInterna)) {
					continue;
				}

				for (ArchivoDeMod.InfoMetodo metodo : nodo.obtenerMetodosConReferencias(claseInterna)) {
					for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
						agregarDestinoReferencia(ref, raizOrigen, claseARaiz, salida);
					}
					for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
						agregarDestinoReferencia(ref, raizOrigen, claseARaiz, salida);
					}
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private void agregarDestinoReferencia(ArchivoDeMod.Referencia ref, Path raizOrigen, Map<String, Path> claseARaiz,
			Set<Path> salida) {
		if (ref == null) {
			return;
		}
		String clase = normalizarClase(ref.obtenerClase());
		Path destino = clase == null ? null : claseARaiz.get(clase);
		if (destino != null && !rutasIguales(destino, raizOrigen)) {
			salida.add(destino);
		}
	}

	private void construirDependenciasDeMetadatos() {
		Map<String, Path> idAMod = new HashMap<String, Path>();

		try {
			for (ArchivoDeMod raiz : Buscador.obtenerModsPrimerNivel()) {
				Path ruta = normalizarRutaArchivo(raiz.ubicacion());
				if (ruta == null || !todosLosMods.contains(ruta)) {
					continue;
				}
				List<ArchivoDeMod> nodos = new ArrayList<ArchivoDeMod>();
				agregarNodosRecursivos(raiz, nodos);
				for (ArchivoDeMod nodo : nodos) {
					for (String id : nodo.nombre()) {
						registrarId(idAMod, id, ruta);
					}
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		Map<Path, MetadataMod> metadataPorMod = new LinkedHashMap<Path, MetadataMod>();
		for (Path mod : todosLosMods) {
			MetadataMod metadata = leerMetadata(mod);
			metadataPorMod.put(mod, metadata);
			for (String id : metadata.ids) {
				registrarId(idAMod, id, mod);
			}
		}

		for (Map.Entry<Path, MetadataMod> entrada : metadataPorMod.entrySet()) {
			for (String idDependencia : entrada.getValue().dependenciasRequeridas) {
				Path destino = idAMod.get(normalizarId(idDependencia));
				if (destino != null && !rutasIguales(destino, entrada.getKey())) {
					dependencias.get(entrada.getKey()).add(destino);
				}
			}
		}
	}

	private void construirMapaDeDependientes() {
		for (Path mod : todosLosMods) {
			dependientes.putIfAbsent(mod, new LinkedHashSet<Path>());
		}

		for (Map.Entry<Path, Set<Path>> entrada : dependencias.entrySet()) {
			Path origen = entrada.getKey();
			for (Path dependencia : entrada.getValue()) {
				if (todosLosMods.contains(dependencia)) {
					dependientes.computeIfAbsent(dependencia, k -> new LinkedHashSet<Path>()).add(origen);
				}
			}
		}
	}

	private MetadataMod leerMetadata(Path archivo) {
		MetadataMod resultado = new MetadataMod();
		String nombre = archivo.getFileName().toString().toLowerCase(Locale.ROOT);
		if (!(nombre.endsWith(".jar") || nombre.endsWith(".zip") || nombre.endsWith(".litemod")
				|| nombre.endsWith(".fpm"))) {
			return resultado;
		}

		try (JarFile jar = new JarFile(archivo.toFile(), false)) {
			procesarJsonFabric(resultado, leerEntrada(jar, "fabric.mod.json"));
			procesarJsonQuilt(resultado, leerEntrada(jar, "quilt.mod.json"));
			procesarToml(resultado, leerEntrada(jar, "META-INF/mods.toml"));
			procesarToml(resultado, leerEntrada(jar, "META-INF/neoforge.mods.toml"));
			procesarMcmodInfo(resultado, leerEntrada(jar, "mcmod.info"));
			procesarMcmodInfo(resultado, leerEntrada(jar, "META-INF/mcmod.info"));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
		return resultado;
	}

	private String leerEntrada(JarFile jar, String nombre) throws IOException {
		ZipEntry entrada = jar.getEntry(nombre);
		if (entrada == null) {
			return null;
		}
		try (InputStream in = jar.getInputStream(entrada)) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int n;
			while ((n = in.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			return new String(out.toByteArray(), StandardCharsets.UTF_8);
		}
	}

	private void procesarJsonFabric(MetadataMod m, String json) {
		if (json == null) {
			return;
		}
		String id = extraerValorJson(json, "id");
		agregarIdMetadata(m, id);

		String objetoDepends = extraerBloqueJson(json, "depends", '{', '}');
		if (objetoDepends != null) {
			Matcher claves = Pattern.compile("\"([^\"]+)\"\\s*:").matcher(objetoDepends);
			while (claves.find()) {
				agregarDependenciaMetadata(m, claves.group(1));
			}
		}
	}

	private void procesarJsonQuilt(MetadataMod m, String json) {
		if (json == null) {
			return;
		}
		String loader = extraerBloqueJson(json, "quilt_loader", '{', '}');
		if (loader != null) {
			agregarIdMetadata(m, extraerValorJson(loader, "id"));
		}

		String depends = extraerBloqueJson(loader == null ? json : loader, "depends", '[', ']');
		if (depends != null) {
			Matcher ids = Pattern.compile("\"id\"\\s*:\\s*\"([^\"]+)\"").matcher(depends);
			while (ids.find()) {
				agregarDependenciaMetadata(m, ids.group(1));
			}
			Matcher textos = Pattern.compile("\"([A-Za-z0-9_.-]+)\"").matcher(depends);
			while (textos.find()) {
				String posible = textos.group(1);
				if (!"id".equals(posible) && !"versions".equals(posible) && !"unless".equals(posible)) {
					agregarDependenciaMetadata(m, posible);
				}
			}
		}
	}

	private void procesarToml(MetadataMod m, String toml) {
		if (toml == null) {
			return;
		}

		String seccion = "";
		String depId = null;
		Boolean obligatoria = null;
		String tipo = null;

		try (BufferedReader lector = new BufferedReader(new StringReader(toml))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				String limpia = quitarComentarioToml(linea).trim();
				if (limpia.isEmpty()) {
					continue;
				}

				if (limpia.startsWith("[[") && limpia.endsWith("]]")) {
					finalizarBloqueDependencia(m, depId, obligatoria, tipo);
					depId = null;
					obligatoria = null;
					tipo = null;
					seccion = limpia.substring(2, limpia.length() - 2).trim();
					continue;
				}

				String clave = claveToml(limpia);
				String valor = valorToml(limpia);
				if (clave == null) {
					continue;
				}

				if (seccion.startsWith("dependencies.")) {
					if ("modId".equalsIgnoreCase(clave)) {
						depId = descomillar(valor);
					} else if ("mandatory".equalsIgnoreCase(clave)) {
						obligatoria = Boolean.valueOf("true".equalsIgnoreCase(descomillar(valor)));
					} else if ("type".equalsIgnoreCase(clave)) {
						tipo = descomillar(valor);
					}
				} else if ("modId".equalsIgnoreCase(clave)) {
					agregarIdMetadata(m, descomillar(valor));
				}
			}
			finalizarBloqueDependencia(m, depId, obligatoria, tipo);
		} catch (IOException imposible) {
			// StringReader no lanza errores reales de E/S.
		}
	}

	private void finalizarBloqueDependencia(MetadataMod m, String depId, Boolean obligatoria, String tipo) {
		boolean requerida = Boolean.TRUE.equals(obligatoria) || "required".equalsIgnoreCase(tipo);
		if (requerida) {
			agregarDependenciaMetadata(m, depId);
		}
	}

	private void procesarMcmodInfo(MetadataMod m, String json) {
		if (json == null) {
			return;
		}
		Matcher ids = Pattern.compile("\"modid\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
		while (ids.find()) {
			agregarIdMetadata(m, ids.group(1));
		}

		Matcher requerida = Pattern.compile("(?i)(?:required-after:|required-before:)([A-Za-z0-9_.-]+)").matcher(json);
		while (requerida.find()) {
			agregarDependenciaMetadata(m, requerida.group(1));
		}
	}

	private String extraerValorJson(String json, String clave) {
		Matcher m = Pattern.compile("\"" + Pattern.quote(clave) + "\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
		return m.find() ? m.group(1) : null;
	}

	private String extraerBloqueJson(String json, String clave, char apertura, char cierre) {
		if (json == null) {
			return null;
		}
		Matcher m = Pattern.compile("\"" + Pattern.quote(clave) + "\"\\s*:").matcher(json);
		if (!m.find()) {
			return null;
		}
		int inicio = m.end();
		while (inicio < json.length() && Character.isWhitespace(json.charAt(inicio))) {
			inicio++;
		}
		if (inicio >= json.length() || json.charAt(inicio) != apertura) {
			return null;
		}

		int profundidad = 0;
		boolean enTexto = false;
		boolean escape = false;
		for (int i = inicio; i < json.length(); i++) {
			char c = json.charAt(i);
			if (enTexto) {
				if (escape) {
					escape = false;
				} else if (c == '\\') {
					escape = true;
				} else if (c == '"') {
					enTexto = false;
				}
				continue;
			}
			if (c == '"') {
				enTexto = true;
			} else if (c == apertura) {
				profundidad++;
			} else if (c == cierre) {
				profundidad--;
				if (profundidad == 0) {
					return json.substring(inicio, i + 1);
				}
			}
		}
		return null;
	}

	private void agregarNodosRecursivos(ArchivoDeMod mod, List<ArchivoDeMod> salida) {
		if (mod == null || salida.contains(mod)) {
			return;
		}
		salida.add(mod);
		for (ArchivoDeMod hijo : mod.mods_en_mods()) {
			agregarNodosRecursivos(hijo, salida);
		}
	}

	private void registrarId(Map<String, Path> mapa, String id, Path mod) {
		String normalizado = normalizarId(id);
		if (normalizado != null && !esIdSistema(normalizado)) {
			mapa.putIfAbsent(normalizado, mod);
		}
	}

	private void agregarIdMetadata(MetadataMod m, String id) {
		String normalizado = normalizarId(id);
		if (normalizado != null && !esIdSistema(normalizado)) {
			m.ids.add(normalizado);
		}
	}

	private void agregarDependenciaMetadata(MetadataMod m, String id) {
		String normalizado = normalizarId(id);
		if (normalizado != null && !esIdSistema(normalizado)) {
			m.dependenciasRequeridas.add(normalizado);
		}
	}

	private boolean esIdSistema(String id) {
		return "minecraft".equals(id) || "java".equals(id) || "forge".equals(id) || "neoforge".equals(id)
				|| "fabricloader".equals(id) || "fabric-loader".equals(id) || "quilt_loader".equals(id)
				|| "quilt-loader".equals(id);
	}

	private String normalizarId(String id) {
		if (id == null) {
			return null;
		}
		String n = id.trim().toLowerCase(Locale.ROOT);
		return n.isEmpty() ? null : n;
	}

	private String normalizarClase(String clase) {
		if (clase == null) {
			return null;
		}
		String n = clase.trim();
		if (n.isEmpty()) {
			return null;
		}
		if (n.length() > 2 && n.charAt(0) == 'L' && n.endsWith(";")) {
			n = n.substring(1, n.length() - 1);
		}
		if (n.endsWith(".class")) {
			n = n.substring(0, n.length() - 6);
		}
		return n.replace('.', '/');
	}

	private Path normalizarRutaArchivo(String texto) {
		if (texto == null || texto.trim().isEmpty() || texto.contains("!/")) {
			return null;
		}
		try {
			return Paths.get(texto).toAbsolutePath().normalize();
		} catch (Throwable t) {
			return null;
		}
	}

	private String quitarComentarioToml(String linea) {
		boolean texto = false;
		for (int i = 0; i < linea.length(); i++) {
			char c = linea.charAt(i);
			if (c == '"' && (i == 0 || linea.charAt(i - 1) != '\\')) {
				texto = !texto;
			} else if (c == '#' && !texto) {
				return linea.substring(0, i);
			}
		}
		return linea;
	}

	private String claveToml(String linea) {
		int i = linea.indexOf('=');
		return i < 0 ? null : linea.substring(0, i).trim();
	}

	private String valorToml(String linea) {
		int i = linea.indexOf('=');
		return i < 0 ? null : linea.substring(i + 1).trim();
	}

	private String descomillar(String valor) {
		if (valor == null) {
			return null;
		}
		String v = valor.trim();
		if (v.length() >= 2 && ((v.startsWith("\"") && v.endsWith("\"")) || (v.startsWith("'") && v.endsWith("'")))) {
			return v.substring(1, v.length() - 1);
		}
		return v;
	}

	private Path obtenerRutaDesactivadaDisponible(Path original) {
		Path propuesta = original.resolveSibling(original.getFileName().toString() + SUFIJO_DESACTIVADO);
		int numero = 1;
		while (Files.exists(propuesta)) {
			propuesta = original.resolveSibling(original.getFileName().toString() + SUFIJO_DESACTIVADO + "." + numero);
			numero++;
		}
		return propuesta;
	}

	private void guardarManifiesto(Map<Path, Path> movimientos) throws IOException {
		Files.createDirectories(carpetaEstado);
		Properties p = new Properties();
		p.setProperty("cantidad", String.valueOf(movimientos.size()));
		p.setProperty("ronda", String.valueOf(ronda + 1));

		int i = 0;
		for (Map.Entry<Path, Path> e : movimientos.entrySet()) {
			p.setProperty("original." + i, e.getKey().toString());
			p.setProperty("desactivado." + i, e.getValue().toString());
			i++;
		}

		Path temporal = manifiesto.resolveSibling(NOMBRE_MANIFIESTO + ".tmp");
		try (java.io.OutputStream out = Files.newOutputStream(temporal)) {
			p.store(out, "CrashDetector binary mod search session");
		}
		try {
			Files.move(temporal, manifiesto, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException noAtomico) {
			Files.move(temporal, manifiesto, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private Properties cargarManifiesto() throws IOException {
		Properties p = new Properties();
		try (InputStream in = Files.newInputStream(manifiesto)) {
			p.load(in);
		}
		return p;
	}

	private void restaurarTodoInterno() throws IOException {
		if (!Files.isRegularFile(manifiesto)) {
			return;
		}
		restaurarRecuperacionPendiente();
	}

	private void rollbackMovimientos(Map<Path, Path> movimientos) {
		List<Map.Entry<Path, Path>> entradas = new ArrayList<Map.Entry<Path, Path>>(movimientos.entrySet());
		Collections.reverse(entradas);
		for (Map.Entry<Path, Path> e : entradas) {
			try {
				if (Files.exists(e.getValue())) {
					moverConRespaldo(e.getValue(), e.getKey());
				}
			} catch (IOException ex) {
				CrashDetectorLogger.logException(ex);
			}
		}
		try {
			Files.deleteIfExists(manifiesto);
		} catch (IOException ignored) {
		}
	}

	private static void moverConRespaldo(Path origen, Path destino) throws IOException {
		try {
			Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException noAtomico) {
			try {
				Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException noMover) {
				Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
				try {
					Files.delete(origen);
				} catch (IOException noBorrar) {
					Files.deleteIfExists(destino);
					throw noBorrar;
				}
			}
		}
	}

	private void limpiarCarpetaEstado() {
		try {
			if (Files.isDirectory(carpetaEstado)) {
				File[] archivos = carpetaEstado.toFile().listFiles();
				if (archivos != null && archivos.length == 0) {
					Files.deleteIfExists(carpetaEstado);
				}
			}
		} catch (IOException ignored) {
		}
	}

	private void limpiarEstadoEnMemoria() {
		todosLosMods.clear();
		dependencias.clear();
		dependientes.clear();
		candidatos.clear();
		candidatosPrueba.clear();
		desactivadosPrueba.clear();
		historial.clear();
		ronda = 0;
		iniciada = false;
		terminada = false;
	}

	private void comprobarIniciada() throws IOException {
		if (!iniciada) {
			throw new IOException("SIN_SESION");
		}
	}

	private static int compararConjuntos(Set<Path> a, Set<Path> b) {
		return unirNombres(a).compareToIgnoreCase(unirNombres(b));
	}

	private static boolean rutasIguales(Path a, Path b) {
		return a != null && b != null && a.toAbsolutePath().normalize().equals(b.toAbsolutePath().normalize());
	}

	private static int parsearEntero(String texto, int defecto) {
		try {
			return Integer.parseInt(texto);
		} catch (Exception e) {
			return defecto;
		}
	}

	private static String unirNombres(Collection<Path> rutas) {
		StringBuilder sb = new StringBuilder();
		for (Path ruta : listaOrdenada(rutas)) {
			if (sb.length() > 0) {
				sb.append('|');
			}
			sb.append(ruta.getFileName());
		}
		return sb.toString();
	}

	private static List<Path> listaOrdenada(Collection<Path> rutas) {
		List<Path> lista = new ArrayList<Path>();
		if (rutas != null) {
			lista.addAll(rutas);
		}
		Collections.sort(lista, new Comparator<Path>() {
			@Override
			public int compare(Path a, Path b) {
				String an = a == null ? "" : a.getFileName().toString();
				String bn = b == null ? "" : b.getFileName().toString();
				int c = an.compareToIgnoreCase(bn);
				return c != 0 ? c : String.valueOf(a).compareToIgnoreCase(String.valueOf(b));
			}
		});
		return lista;
	}

	private static final class MetadataMod {
		final Set<String> ids = new LinkedHashSet<String>();
		final Set<String> dependenciasRequeridas = new LinkedHashSet<String>();
	}
}
