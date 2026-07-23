package com.asbestosstar.crashdetector.parches.proyecto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Stream;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Extencion;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.parches.Parche;

/**
 * Crea, edita y compila proyectos de extensiones de parches de CrashDetector.
 *
 * Los proyectos se reconocen por un archivo marcador. Las operaciones de MCP
 * solo pueden leer o escribir dentro de una raíz marcada.
 */
public final class ServicioProyectosParche {

	public static final String ARCHIVO_MARCADOR = ".crashdetector-patch-project.properties";
	public static final String ARCHIVO_ESTADO = "generador-parches.properties";
	public static final long MAXIMO_ARCHIVO_TEXTO = 4L * 1024L * 1024L;
	public static final long TIEMPO_COMPILACION_SEGUNDOS = 300L;

	private static final Object BLOQUEO_ESTADO = new Object();

	private ServicioProyectosParche() {
	}

	public static File carpetaProyectosPredeterminada() {
		return Statics.carpeta.resolve("proyectos-parches").toFile();
	}

	public static File archivoEstado() {
		return Statics.carpeta.resolve(ARCHIVO_ESTADO).toFile();
	}

	public static File obtenerProyectoActivo() {
		Properties p = leerEstado();
		String ruta = p.getProperty("proyecto.activo", "").trim();
		if (ruta.isEmpty()) {
			return null;
		}
		try {
			File proyecto = validarProyecto(new File(ruta));
			return proyecto;
		} catch (Exception e) {
			return null;
		}
	}

	public static File obtenerJdkActivo() {
		Properties p = leerEstado();
		String ruta = p.getProperty("jdk.activo", "").trim();
		File guardado = ruta.isEmpty() ? null : LocalizadorJdk.normalizarJdk(new File(ruta));
		return guardado != null ? guardado : LocalizadorJdk.localizarJdkActual();
	}

	public static void establecerProyectoActivo(File proyecto) throws IOException {
		File valido = validarProyecto(proyecto);
		Properties p = leerEstado();
		p.setProperty("proyecto.activo", valido.getCanonicalPath());
		guardarEstado(p);
	}

	public static void establecerJdkActivo(File jdk) throws IOException {
		File valido = LocalizadorJdk.normalizarJdk(jdk);
		if (valido == null) {
			throw new IOException("La ruta no contiene un ejecutable javac válido.");
		}
		Properties p = leerEstado();
		p.setProperty("jdk.activo", valido.getCanonicalPath());
		guardarEstado(p);
	}

	private static Properties leerEstado() {
		synchronized (BLOQUEO_ESTADO) {
			Properties p = new Properties();
			File archivo = archivoEstado();
			if (archivo.isFile()) {
				try (FileInputStream entrada = new FileInputStream(archivo)) {
					p.load(entrada);
				} catch (IOException e) {
					CrashDetectorLogger.logException(e);
				}
			}
			return p;
		}
	}

	private static void guardarEstado(Properties p) throws IOException {
		synchronized (BLOQUEO_ESTADO) {
			File archivo = archivoEstado();
			File padre = archivo.getParentFile();
			if (padre != null) {
				Files.createDirectories(padre.toPath());
			}
			try (FileOutputStream salida = new FileOutputStream(archivo)) {
				p.store(salida, "Generador de parches CrashDetector");
			}
		}
	}

	public static File crearProyecto(File directorioProyecto, ConfiguracionProyectoParche configuracion,
			boolean permitirProyectoExistente) throws IOException {
		if (directorioProyecto == null || configuracion == null) {
			throw new IOException("Falta la ruta o la configuración del proyecto.");
		}

		ConfiguracionProyectoParche c = validarConfiguracion(configuracion);
		File raiz = directorioProyecto.getCanonicalFile();

		if (raiz.exists() && !raiz.isDirectory()) {
			throw new IOException("La ruta del proyecto no es una carpeta.");
		}

		if (raiz.isDirectory()) {
			File[] existentes = raiz.listFiles();
			boolean tieneContenido = existentes != null && existentes.length > 0;
			boolean esProyecto = new File(raiz, ARCHIVO_MARCADOR).isFile();
			if (tieneContenido && !(permitirProyectoExistente && esProyecto)) {
				throw new IOException("La carpeta ya contiene archivos y no es un proyecto de parches editable.");
			}
		}

		Files.createDirectories(raiz.toPath());
		String rutaPaquete = c.paqueteBase.replace('.', File.separatorChar);
		File carpetaJava = new File(raiz, "src/main/java/" + rutaPaquete);
		File carpetaRecursos = new File(raiz, "src/main/resources/META-INF");
		Files.createDirectories(carpetaJava.toPath());
		Files.createDirectories(carpetaRecursos.toPath());
		Files.createDirectories(new File(raiz, "build/classes").toPath());
		Files.createDirectories(new File(raiz, "build/libs").toPath());

		escribirUtf8(new File(carpetaJava, c.claseExtension + ".java"), fuenteExtension(c));
		escribirUtf8(new File(carpetaJava, c.claseParche + ".java"), fuenteParche(c));
		escribirUtf8(new File(carpetaRecursos, "MANIFEST.MF"), manifiesto(c));
		escribirUtf8(new File(raiz, "README_AI_MCP.md"), readme(c));
		escribirUtf8(new File(raiz, ".gitignore"), "build/\n*.class\n");

		Properties marcador = new Properties();
		marcador.setProperty("formato", "crashdetector-patch-project-1");
		marcador.setProperty("nombre", c.nombreProyecto);
		marcador.setProperty("paquete", c.paqueteBase);
		marcador.setProperty("clase.extension", c.claseExtension);
		marcador.setProperty("clase.parche", c.claseParche);
		marcador.setProperty("clase.objetivo", c.claseObjetivo);
		try (FileOutputStream salida = new FileOutputStream(new File(raiz, ARCHIVO_MARCADOR))) {
			marcador.store(salida, "Proyecto de parches CrashDetector");
		}

		establecerProyectoActivo(raiz);
		return raiz;
	}

	public static ResultadoCompilacionParche compilarProyecto(File proyecto, File jdk) throws IOException {
		File raiz = validarProyecto(proyecto);
		File jdkValido = LocalizadorJdk.normalizarJdk(jdk);
		if (jdkValido == null) {
			jdkValido = obtenerJdkActivo();
		}
		if (jdkValido == null) {
			throw new IOException("No se encontró un JDK con javac.");
		}

		File javac = LocalizadorJdk.localizarJavac(jdkValido);
		if (javac == null) {
			throw new IOException("No se encontró javac en el JDK seleccionado.");
		}

		File fuentes = new File(raiz, "src/main/java");
		List<File> archivosJava = listarPorExtension(fuentes, ".java");
		if (archivosJava.isEmpty()) {
			throw new IOException("El proyecto no contiene archivos Java.");
		}

		File clases = new File(raiz, "build/classes");
		borrarContenido(clases);
		Files.createDirectories(clases.toPath());

		File log = new File(raiz, "build/javac.log");
		Files.createDirectories(log.getParentFile().toPath());

		List<String> comando = new ArrayList<String>();
		comando.add(javac.getAbsolutePath());
		comando.add("-encoding");
		comando.add("UTF-8");
		comando.add("-source");
		comando.add("8");
		comando.add("-target");
		comando.add("8");
		comando.add("-classpath");
		comando.add(construirClasspathActual());
		comando.add("-d");
		comando.add(clases.getAbsolutePath());
		for (File archivo : archivosJava) {
			comando.add(archivo.getAbsolutePath());
		}

		ProcessBuilder pb = new ProcessBuilder(comando);
		pb.directory(raiz);
		pb.redirectErrorStream(true);
		pb.redirectOutput(ProcessBuilder.Redirect.to(log));
		Process proceso = pb.start();

		boolean termino;
		try {
			termino = proceso.waitFor(TIEMPO_COMPILACION_SEGUNDOS, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			proceso.destroyForcibly();
			throw new IOException("La compilación fue interrumpida.", e);
		}

		if (!termino) {
			proceso.destroyForcibly();
			throw new IOException("La compilación excedió " + TIEMPO_COMPILACION_SEGUNDOS + " segundos.");
		}

		String salida = log.isFile() ? new String(Files.readAllBytes(log.toPath()), StandardCharsets.UTF_8) : "";
		if (proceso.exitValue() != 0) {
			return new ResultadoCompilacionParche(false, null, salida);
		}

		copiarRecursos(new File(raiz, "src/main/resources"), clases);
		Properties marcador = cargarMarcador(raiz);
		String nombre = limpiarNombreArchivo(marcador.getProperty("nombre", raiz.getName()));
		File jar = new File(raiz, "build/libs/" + nombre + ".jar");
		crearJar(raiz, clases, jar);

		establecerProyectoActivo(raiz);
		establecerJdkActivo(jdkValido);
		return new ResultadoCompilacionParche(true, jar.getCanonicalFile(), salida);
	}

	public static List<String> listarArchivos(File proyecto) throws IOException {
		File raiz = validarProyecto(proyecto);
		List<String> rutas = new ArrayList<String>();
		try (Stream<Path> flujo = Files.walk(raiz.toPath())) {
			flujo.filter(Files::isRegularFile).forEach(path -> {
				String relativo = raiz.toPath().relativize(path).toString().replace(File.separatorChar, '/');
				rutas.add(relativo);
			});
		}
		Collections.sort(rutas);
		return rutas;
	}

	public static String leerArchivoTexto(File proyecto, String rutaRelativa) throws IOException {
		File archivo = resolverDentroDelProyecto(proyecto, rutaRelativa, true);
		if (!archivo.isFile()) {
			throw new IOException("El archivo no existe: " + rutaRelativa);
		}
		if (archivo.length() > MAXIMO_ARCHIVO_TEXTO) {
			throw new IOException("El archivo excede el límite de texto permitido.");
		}
		return new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);
	}

	public static File escribirArchivoTexto(File proyecto, String rutaRelativa, String contenido) throws IOException {
		if (contenido == null) {
			contenido = "";
		}
		byte[] datos = contenido.getBytes(StandardCharsets.UTF_8);
		if (datos.length > MAXIMO_ARCHIVO_TEXTO) {
			throw new IOException("El contenido excede el límite permitido.");
		}
		File archivo = resolverDentroDelProyecto(proyecto, rutaRelativa, false);
		validarExtensionEditable(archivo.getName());
		File padre = archivo.getParentFile();
		if (padre != null) {
			Files.createDirectories(padre.toPath());
		}
		Files.write(archivo.toPath(), datos);
		return archivo;
	}

	public static File validarProyecto(File proyecto) throws IOException {
		if (proyecto == null) {
			throw new IOException("No se indicó un proyecto.");
		}
		File raiz = proyecto.getCanonicalFile();
		if (!raiz.isDirectory() || !new File(raiz, ARCHIVO_MARCADOR).isFile()) {
			throw new IOException("La carpeta no es un proyecto de parches de CrashDetector.");
		}
		return raiz;
	}

	public static String contextoApiParches() {
		return "Formato del proyecto: " + ARCHIVO_MARCADOR + " en la raíz.\n"
				+ "La clase de entrada implementa com.asbestosstar.crashdetector.Extencion.\n"
				+ "El manifiesto usa ExtencionCrashDetector con el nombre completo de esa clase.\n"
				+ "El parche generado implementa ParcheClassNode y se registra en Parche.parches.\n"
				+ "Edite parchClassNode(ClassNode node, String nombre) para aplicar ASM.\n"
				+ "No cambie el nombre interno de la clase objetivo durante retransformation.\n"
				+ "Compile con generador_parches_compilar; el JAR queda en build/libs.\n";
	}

	private static ConfiguracionProyectoParche validarConfiguracion(ConfiguracionProyectoParche c) throws IOException {
		String nombre = textoRequerido(c.nombreProyecto, "nombre del proyecto");
		String paquete = validarPaquete(c.paqueteBase);
		String extension = validarIdentificadorJava(c.claseExtension, "clase de extensión");
		String parche = validarIdentificadorJava(c.claseParche, "clase del parche");
		String objetivo = normalizarClaseObjetivo(c.claseObjetivo);
		return new ConfiguracionProyectoParche(nombre, paquete, extension, parche, objetivo);
	}

	private static String textoRequerido(String valor, String nombre) throws IOException {
		String limpio = valor == null ? "" : valor.trim();
		if (limpio.isEmpty()) {
			throw new IOException("Falta " + nombre + ".");
		}
		return limpio;
	}

	private static String validarPaquete(String paquete) throws IOException {
		String limpio = textoRequerido(paquete, "el paquete base");
		String[] partes = limpio.split("\\.");
		for (String parte : partes) {
			validarIdentificadorJava(parte, "segmento de paquete");
		}
		return limpio;
	}

	private static String validarIdentificadorJava(String valor, String descripcion) throws IOException {
		String limpio = textoRequerido(valor, descripcion);
		if (!Character.isJavaIdentifierStart(limpio.charAt(0))) {
			throw new IOException("Nombre Java no válido para " + descripcion + ": " + limpio);
		}
		for (int i = 1; i < limpio.length(); i++) {
			if (!Character.isJavaIdentifierPart(limpio.charAt(i))) {
				throw new IOException("Nombre Java no válido para " + descripcion + ": " + limpio);
			}
		}
		return limpio;
	}

	private static String normalizarClaseObjetivo(String clase) throws IOException {
		String limpio = textoRequerido(clase, "la clase objetivo");
		if (limpio.startsWith("L") && limpio.endsWith(";")) {
			limpio = limpio.substring(1, limpio.length() - 1);
		}
		if (limpio.endsWith(".class")) {
			limpio = limpio.substring(0, limpio.length() - 6);
		}
		return limpio.replace('/', '.');
	}

	private static String fuenteExtension(ConfiguracionProyectoParche c) {
		return "package " + c.paqueteBase + ";\n\n" + "import com.asbestosstar.crashdetector.Extencion;\n"
				+ "import com.asbestosstar.crashdetector.parches.ConfigDeParches;\n"
				+ "import com.asbestosstar.crashdetector.parches.Parche;\n\n"
				+ "/** Punto de entrada de la extensión generada por CrashDetector. */\n" + "public final class "
				+ c.claseExtension + " implements Extencion {\n\n" + "    private static void registrarParche() {\n"
				+ "        for (Parche<?> existente : Parche.parches) {\n" + "            if (" + c.claseParche
				+ ".ID.equals(existente.id())) {\n" + "                return;\n" + "            }\n" + "        }\n"
				+ "        Parche.parches.add(new " + c.claseParche + "());\n"
				+ "        ConfigDeParches.obtenerInstancia().asegurarParches();\n" + "    }\n\n" + "    @Override\n"
				+ "    public void procesoDelApp() {\n" + "        registrarParche();\n" + "    }\n\n"
				+ "    @Override\n" + "    public void procesoDeLaMonitorizacionDePID() {\n"
				+ "        registrarParche();\n" + "    }\n" + "}\n";
	}

	private static String fuenteParche(ConfiguracionProyectoParche c) {
		String id = limpiarId(c.nombreProyecto);
		return "package " + c.paqueteBase + ";\n\n" + "import java.util.Collections;\n" + "import java.util.Set;\n\n"
				+ "import org.objectweb.asm.tree.ClassNode;\n\n"
				+ "import com.asbestosstar.crashdetector.parches.Parche;\n"
				+ "import com.asbestosstar.crashdetector.parches.ParcheClassNode;\n\n"
				+ "/** Parche generado para ser completado con ayuda de AI > MCP. */\n" + "public final class "
				+ c.claseParche + " implements ParcheClassNode {\n\n" + "    public static final String ID = \""
				+ escaparJava(id) + "\";\n\n" + "    @Override\n" + "    public Set<String> clases() {\n"
				+ "        return Collections.singleton(\"" + escaparJava(c.claseObjetivo) + "\");\n" + "    }\n\n"
				+ "    @Override\n" + "    public boolean predeterminado() {\n" + "        return false;\n"
				+ "    }\n\n" + "    @Override\n" + "    public void parchClassNode(ClassNode node, String nombre) {\n"
				+ "        // TODO: describa a la AI el cambio y permita que edite este método.\n"
				+ "        // La plantilla no modifica bytecode hasta que usted agregue instrucciones ASM.\n"
				+ "    }\n\n" + "    @Override\n" + "    public Parche<ClassNode> nuevo() {\n" + "        return new "
				+ c.claseParche + "();\n" + "    }\n\n" + "    @Override\n" + "    public String id() {\n"
				+ "        return ID;\n" + "    }\n\n" + "    @Override\n" + "    public String nombre_de_gui() {\n"
				+ "        return \"" + escaparJava(c.nombreProyecto) + "\";\n" + "    }\n" + "}\n";
	}

	private static String manifiesto(ConfiguracionProyectoParche c) {
		return "Manifest-Version: 1.0\n" + "ExtencionCrashDetector: " + c.paqueteBase + "." + c.claseExtension + "\n"
				+ "Created-By: CrashDetector Generador de Parches\n\n";
	}

	private static String readme(ConfiguracionProyectoParche c) {
		return "# " + c.nombreProyecto + "\n\n" + "Proyecto de extensión de parches para CrashDetector.\n\n"
				+ "## Flujo con AI\n\n" + "1. Abra CrashDetector y vaya a **AI > MCP**.\n"
				+ "2. Inicie el servidor MCP.\n" + "3. Pida a la AI que use `generador_parches_contexto`.\n"
				+ "4. La AI puede leer y editar este proyecto con las herramientas `generador_parches_*`.\n"
				+ "5. Compile con la GUI o con `generador_parches_compilar`.\n\n" + "Clase objetivo inicial: `"
				+ c.claseObjetivo + "`\n\n"
				+ "El JAR compilado queda en `build/libs/`. Para cargarlo, colóquelo donde CrashDetector "
				+ "incluya extensiones `.jar`, `.fpm` o `.cdext` en la lista de mods.\n";
	}

	private static void crearJar(File raiz, File clases, File destino) throws IOException {
		File manifestFile = new File(raiz, "src/main/resources/META-INF/MANIFEST.MF");
		Manifest manifest;
		try (InputStream entrada = new FileInputStream(manifestFile)) {
			manifest = new Manifest(entrada);
		}
		if (manifest.getMainAttributes().getValue(Attributes.Name.MANIFEST_VERSION) == null) {
			manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		}

		File padre = destino.getParentFile();
		if (padre != null) {
			Files.createDirectories(padre.toPath());
		}
		File temporal = new File(destino.getParentFile(), destino.getName() + ".tmp");

		List<File> entradas = new ArrayList<File>();
		try (Stream<Path> flujo = Files.walk(clases.toPath())) {
			flujo.filter(Files::isRegularFile).forEach(p -> entradas.add(p.toFile()));
		}
		Collections.sort(entradas, Comparator.comparing(File::getAbsolutePath));

		try (JarOutputStream jar = new JarOutputStream(new FileOutputStream(temporal), manifest)) {
			byte[] buffer = new byte[64 * 1024];
			for (File archivo : entradas) {
				String nombre = clases.toPath().relativize(archivo.toPath()).toString().replace(File.separatorChar,
						'/');
				if ("META-INF/MANIFEST.MF".equalsIgnoreCase(nombre)) {
					continue;
				}
				JarEntry entradaJar = new JarEntry(nombre);
				entradaJar.setTime(archivo.lastModified());
				jar.putNextEntry(entradaJar);
				try (InputStream entrada = new FileInputStream(archivo)) {
					int n;
					while ((n = entrada.read(buffer)) >= 0) {
						jar.write(buffer, 0, n);
					}
				}
				jar.closeEntry();
			}
		}
		Files.move(temporal.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	private static String construirClasspathActual() {
		Set<String> entradas = new LinkedHashSet<String>();
		String actual = System.getProperty("java.class.path", "");
		if (!actual.trim().isEmpty()) {
			String[] partes = actual.split(java.util.regex.Pattern.quote(File.pathSeparator));
			for (String parte : partes) {
				if (!parte.trim().isEmpty()) {
					entradas.add(parte);
				}
			}
		}

		agregarCodeSource(entradas, ServicioProyectosParche.class);
		agregarCodeSource(entradas, Extencion.class);
		agregarCodeSource(entradas, Parche.class);
		try {
			agregarCodeSource(entradas, Class.forName("org.objectweb.asm.tree.ClassNode"));
		} catch (Throwable ignorado) {
		}

		StringBuilder cp = new StringBuilder();
		for (String entrada : entradas) {
			if (cp.length() > 0) {
				cp.append(File.pathSeparatorChar);
			}
			cp.append(entrada);
		}
		return cp.toString();
	}

	private static void agregarCodeSource(Set<String> entradas, Class<?> clase) {
		try {
			CodeSource fuente = clase.getProtectionDomain().getCodeSource();
			URL ubicacion = fuente == null ? null : fuente.getLocation();
			if (ubicacion != null && "file".equalsIgnoreCase(ubicacion.getProtocol())) {
				entradas.add(new File(ubicacion.toURI()).getAbsolutePath());
			}
		} catch (Throwable ignorado) {
		}
	}

	private static void copiarRecursos(File origen, File destino) throws IOException {
		if (!origen.isDirectory()) {
			return;
		}
		try (Stream<Path> flujo = Files.walk(origen.toPath())) {
			flujo.forEach(path -> {
				try {
					Path relativo = origen.toPath().relativize(path);
					Path salida = destino.toPath().resolve(relativo);
					if (Files.isDirectory(path)) {
						Files.createDirectories(salida);
					} else {
						Files.createDirectories(salida.getParent());
						Files.copy(path, salida, StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (IOException e) {
					throw new CopiaRecursoException(e);
				}
			});
		} catch (CopiaRecursoException e) {
			throw e.causa;
		}
	}

	private static List<File> listarPorExtension(File carpeta, String extension) throws IOException {
		List<File> archivos = new ArrayList<File>();
		if (!carpeta.isDirectory()) {
			return archivos;
		}
		try (Stream<Path> flujo = Files.walk(carpeta.toPath())) {
			flujo.filter(Files::isRegularFile).forEach(path -> {
				if (path.getFileName().toString().toLowerCase(java.util.Locale.ROOT).endsWith(extension)) {
					archivos.add(path.toFile());
				}
			});
		}
		Collections.sort(archivos, Comparator.comparing(File::getAbsolutePath));
		return archivos;
	}

	private static void borrarContenido(File carpeta) throws IOException {
		if (!carpeta.exists()) {
			return;
		}
		List<Path> rutas = new ArrayList<Path>();
		try (Stream<Path> flujo = Files.walk(carpeta.toPath())) {
			flujo.forEach(rutas::add);
		}
		Collections.sort(rutas, Comparator.reverseOrder());
		for (Path ruta : rutas) {
			if (!ruta.equals(carpeta.toPath())) {
				Files.deleteIfExists(ruta);
			}
		}
	}

	private static File resolverDentroDelProyecto(File proyecto, String rutaRelativa, boolean exigirTexto)
			throws IOException {
		File raiz = validarProyecto(proyecto);
		String ruta = rutaRelativa == null ? "" : rutaRelativa.trim().replace('\\', '/');
		if (ruta.isEmpty() || ruta.startsWith("/") || ruta.contains("\u0000")) {
			throw new IOException("Ruta relativa no válida.");
		}
		File archivo = new File(raiz, ruta).getCanonicalFile();
		String prefijo = raiz.getCanonicalPath() + File.separator;
		if (!archivo.getCanonicalPath().startsWith(prefijo)) {
			throw new IOException("La ruta sale de la carpeta del proyecto.");
		}
		if (exigirTexto) {
			validarExtensionEditable(archivo.getName());
		}
		return archivo;
	}

	private static void validarExtensionEditable(String nombre) throws IOException {
		String n = nombre.toLowerCase(java.util.Locale.ROOT);
		String[] permitidas = { ".java", ".md", ".txt", ".properties", ".xml", ".json", ".yml", ".yaml", ".gradle",
				".gitignore" };
		for (String extension : permitidas) {
			if (n.endsWith(extension) || n.equals(extension)) {
				return;
			}
		}
		throw new IOException("El MCP solo puede editar archivos de texto del proyecto.");
	}

	private static Properties cargarMarcador(File raiz) throws IOException {
		Properties p = new Properties();
		try (FileInputStream entrada = new FileInputStream(new File(raiz, ARCHIVO_MARCADOR))) {
			p.load(entrada);
		}
		return p;
	}

	private static void escribirUtf8(File archivo, String texto) throws IOException {
		File padre = archivo.getParentFile();
		if (padre != null) {
			Files.createDirectories(padre.toPath());
		}
		Files.write(archivo.toPath(), texto.getBytes(StandardCharsets.UTF_8));
	}

	private static String limpiarId(String texto) {
		String n = texto == null ? "parche_generado" : texto.toLowerCase(java.util.Locale.ROOT);
		n = n.replaceAll("[^a-z0-9._-]+", "_").replaceAll("_+", "_");
		n = n.replaceAll("^[._-]+|[._-]+$", "");
		return n.isEmpty() ? "parche_generado" : n;
	}

	private static String limpiarNombreArchivo(String texto) {
		String n = limpiarId(texto);
		return n.isEmpty() ? "parche-crashdetector" : n;
	}

	private static String escaparJava(String texto) {
		return texto.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n");
	}

	private static final class CopiaRecursoException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		final IOException causa;

		CopiaRecursoException(IOException causa) {
			this.causa = causa;
		}
	}
}
