package com.asbestosstar.crashdetector.deps;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Descargador simple de dependencias Maven.
 *
 * Uso:
 *
 * java com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven groupId
 * artifactId version
 *
 * Ejemplo:
 *
 * java com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven
 * com.github.Querz NBT 6.1
 *
 * Guarda todos los JAR/POM directamente en:
 *
 * ~/crash_detector/deps/
 *
 * No genera archivos .txt.
 */
public class DescargadorDependenciasMaven {

	private static final File UNIVERSAL_DEPS = new File(System.getProperty("user.home"), "crash_detector/deps");

	/*
	 * Guardado plano: no se usa estructura Maven local.
	 *
	 * Ejemplo: ~/crash_detector/deps/org.eclipse.jgit-5.13.3.202401111512-r.jar
	 * ~/crash_detector/deps/org.eclipse.jgit-5.13.3.202401111512-r.pom
	 */
	private static final File REPOSITORY_DIR = UNIVERSAL_DEPS;

	private static final int HILOS_DESCARGA = Math.max(4, Runtime.getRuntime().availableProcessors());
	private static final int TIMEOUT_MS = 15000;

	private final List<Repositorio> repositoriosBase = new ArrayList<Repositorio>();
	private final List<Repositorio> repositoriosEncontrados = new ArrayList<Repositorio>();

	private final Map<String, ModeloPom> cachePom = new HashMap<String, ModeloPom>();
	private final Map<String, byte[]> cacheBytesPom = new HashMap<String, byte[]>();

	private final Map<String, Artefacto> artefactosResueltos = new LinkedHashMap<String, Artefacto>();

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Uso:");
			System.out.println(
					"java com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven <groupId> <artifactId> <version>");
			System.out.println();
			System.out.println("Ejemplo:");
			System.out.println(
					"java com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven com.github.Querz NBT 6.1");
			return;
		}

		String groupId = args[0];
		String artifactId = args[1];
		String version = args[2];

		DescargadorDependenciasMaven d = new DescargadorDependenciasMaven();
		d.inicializarRepositorios();
		d.resolverYDescargar(groupId, artifactId, version);
	}

	public static ResultadoDescarga descargarDependencia(String groupId, String artifactId, String version) {
		try {
			DescargadorDependenciasMaven d = new DescargadorDependenciasMaven();
			d.inicializarRepositorios();
			d.resolverYDescargar(groupId, artifactId, version);

			return new ResultadoDescarga(true, UNIVERSAL_DEPS,
					"Dependencia Maven descargada: " + groupId + ":" + artifactId + ":" + version);
		} catch (Throwable t) {
			return new ResultadoDescarga(false, UNIVERSAL_DEPS,
					"Error descargando " + groupId + ":" + artifactId + ":" + version + ": " + t.getMessage());
		}
	}

	public static ResultadoDescarga descargarDependencias(List<CoordenadaMaven> dependencias) {
		if (dependencias == null || dependencias.isEmpty()) {
			return new ResultadoDescarga(true, UNIVERSAL_DEPS, "No hay dependencias para descargar.");
		}

		int ok = 0;
		StringBuilder errores = new StringBuilder();

		for (CoordenadaMaven d : dependencias) {
			if (d == null) {
				continue;
			}

			ResultadoDescarga r = descargarDependencia(d.groupId, d.artifactId, d.version);

			if (r.exito) {
				ok++;
			} else {
				errores.append(d.groupId).append(":").append(d.artifactId).append(":").append(d.version).append(" -> ")
						.append(r.mensaje).append("\n");
			}
		}

		boolean exito = errores.length() == 0;
		String mensaje = "Dependencias raiz descargadas: " + ok + " de " + dependencias.size();

		if (errores.length() > 0) {
			mensaje += "\n" + errores.toString();
		}

		return new ResultadoDescarga(exito, UNIVERSAL_DEPS, mensaje);
	}

	public void resolverYDescargar(String groupId, String artifactId, String version) throws Exception {
		UNIVERSAL_DEPS.mkdirs();
		REPOSITORY_DIR.mkdirs();

		Artefacto raiz = new Artefacto(groupId, artifactId, version, "jar", null);

		System.out.println("Resolviendo dependencias de: " + raiz);
		resolverTransitivo(raiz);

		System.out.println();
		System.out.println("Artefactos encontrados: " + artefactosResueltos.size());
		for (Artefacto a : artefactosResueltos.values()) {
			System.out.println(" - " + a);
		}

		System.out.println();
		System.out.println("Descargando con " + HILOS_DESCARGA + " hilos...");
		descargarArtefactos();

		System.out.println();
		System.out.println("Listo. JARs/POMs guardados directamente en:");
		System.out.println(UNIVERSAL_DEPS.getAbsolutePath());
	}

	private void inicializarRepositorios() {
		agregarRepo("central", "central", "https://repo1.maven.org/maven2/");
		agregarRepo("jitpack.io", "jitpack.io", "https://www.jitpack.io");
		agregarRepo("cursemaven", "cursemaven", "https://cursemaven.com");
		agregarRepo("kaolinmc", "kaolinmc", "https://maven.kaolinmc.com/snapshots");
		agregarRepo("mangorage", "mangorage", "https://maven.mangorage.org/releases/");
		agregarRepo("velocity", "velocity", "https://repo.papermc.io/repository/maven-public/");
		agregarRepo("nf", "nf", "https://maven.neoforged.net/releases/");
		agregarRepo("carm", "carm", "https://repo.carm.cc/repository/maven-public/");
		agregarRepo("MCForge", "MCForge", "https://files.minecraftforge.net/maven/");
		agregarRepo("TLauncher", "TLauncher", "https://res.tlauncher.org/unb/libraries/");
		agregarRepo("SleepingTown", "SleepingTown", "https://repo.sleeping.town");
		agregarRepo("Neptune", "Neptune", "https://repo.neptunepowered.org/maven/");
		agregarRepo("cuchazinteractive", "cuchazinteractive", "https://maven.cuchazinteractive.com/");
		agregarRepo("spongepowered-repo", "spongepowered", "https://repo.spongepowered.org/repository/maven-public/");
		agregarRepo("modrinth", "modrinth", "https://api.modrinth.com/maven/");
	}

	private void agregarRepo(String id, String nombre, String url) {
		Repositorio r = new Repositorio(id, nombre, url);

		if (!repositoriosBase.contains(r)) {
			repositoriosBase.add(r);
		}

		if (!repositoriosEncontrados.contains(r)) {
			repositoriosEncontrados.add(r);
		}
	}

	private void agregarRepositorioEncontrado(Repositorio r) {
		if (r == null || r.url == null || r.url.trim().isEmpty()) {
			return;
		}

		if (!repositoriosEncontrados.contains(r)) {
			repositoriosEncontrados.add(r);
		}
	}

	private void resolverTransitivo(Artefacto raiz) {
		Queue<TrabajoResolucion> cola = new ArrayDeque<TrabajoResolucion>();
		cola.add(new TrabajoResolucion(raiz, new HashSet<String>()));

		while (!cola.isEmpty()) {
			TrabajoResolucion trabajo = cola.remove();
			Artefacto actual = trabajo.artefacto;

			if (actual == null || actual.groupId == null || actual.artifactId == null || actual.version == null) {
				continue;
			}

			String gav = actual.gav();
			if (artefactosResueltos.containsKey(gav)) {
				continue;
			}

			artefactosResueltos.put(gav, actual);

			ModeloPom modelo = cargarPomSeguro(actual);
			if (modelo == null) {
				System.out.println("No se pudo leer POM de " + actual + ". Se descargara solo el artefacto.");
				continue;
			}

			for (Repositorio r : modelo.repositorios) {
				agregarRepositorioEncontrado(r);
			}

			for (Dependencia dep : modelo.dependencias) {
				if (!dep.esUsableParaRuntime()) {
					continue;
				}

				String ga = dep.groupId + ":" + dep.artifactId;
				if (trabajo.exclusiones.contains(ga)) {
					continue;
				}

				String versionDep = dep.version;
				if (versionDep == null || versionDep.trim().isEmpty()) {
					versionDep = modelo.dependencyManagement.get(dep.groupId + ":" + dep.artifactId);
				}

				if (versionDep == null || versionDep.trim().isEmpty()) {
					System.out.println("Dependencia sin version omitida: " + dep.groupId + ":" + dep.artifactId);
					continue;
				}

				String tipo = dep.type == null || dep.type.trim().isEmpty() ? "jar" : dep.type.trim();

				if ("pom".equals(tipo)) {
					continue;
				}

				Artefacto hijo = new Artefacto(dep.groupId, dep.artifactId, versionDep, tipo, dep.classifier);

				Set<String> nuevasExclusiones = new HashSet<String>(trabajo.exclusiones);
				nuevasExclusiones.addAll(dep.exclusiones);

				cola.add(new TrabajoResolucion(hijo, nuevasExclusiones));
			}
		}
	}

	private ModeloPom cargarPomSeguro(Artefacto a) {
		try {
			return cargarPom(a, new HashSet<String>());
		} catch (Exception e) {
			System.out.println("Error leyendo POM " + a + ": " + e.getMessage());
			return null;
		}
	}

	private ModeloPom cargarPom(Artefacto a, Set<String> pila) throws Exception {
		String key = a.gav();

		if (cachePom.containsKey(key)) {
			return cachePom.get(key);
		}

		if (pila.contains(key)) {
			return null;
		}

		pila.add(key);

		byte[] pomBytes = descargarBytesPom(a);
		if (pomBytes == null) {
			return null;
		}

		Document doc = leerXml(pomBytes);
		Element project = doc.getDocumentElement();

		ModeloPom modeloBase = new ModeloPom();

		Element parentEl = hijo(project, "parent");
		if (parentEl != null) {
			String pg = textoHijo(parentEl, "groupId");
			String pa = textoHijo(parentEl, "artifactId");
			String pv = textoHijo(parentEl, "version");

			if (pg != null && pa != null && pv != null) {
				Artefacto parent = new Artefacto(pg, pa, pv, "pom", null);
				ModeloPom pm = cargarPom(parent, pila);

				if (pm != null) {
					modeloBase.copiarDesde(pm);
				}
			}
		}

		ModeloPom modelo = new ModeloPom();
		modelo.copiarDesde(modeloBase);

		String groupId = textoHijo(project, "groupId");
		String artifactId = textoHijo(project, "artifactId");
		String version = textoHijo(project, "version");

		if (groupId == null && parentEl != null) {
			groupId = textoHijo(parentEl, "groupId");
		}

		if (version == null && parentEl != null) {
			version = textoHijo(parentEl, "version");
		}

		modelo.groupId = resolverTexto(groupId, modelo.propiedades);
		modelo.artifactId = resolverTexto(artifactId, modelo.propiedades);
		modelo.version = resolverTexto(version, modelo.propiedades);

		if (modelo.groupId == null) {
			modelo.groupId = a.groupId;
		}

		if (modelo.artifactId == null) {
			modelo.artifactId = a.artifactId;
		}

		if (modelo.version == null) {
			modelo.version = a.version;
		}

		modelo.propiedades.put("project.groupId", modelo.groupId);
		modelo.propiedades.put("project.artifactId", modelo.artifactId);
		modelo.propiedades.put("project.version", modelo.version);
		modelo.propiedades.put("pom.groupId", modelo.groupId);
		modelo.propiedades.put("pom.artifactId", modelo.artifactId);
		modelo.propiedades.put("pom.version", modelo.version);

		Element propertiesEl = hijo(project, "properties");
		if (propertiesEl != null) {
			for (Element e : hijos(propertiesEl)) {
				String nombre = e.getTagName();
				String valor = texto(e);
				modelo.propiedades.put(nombre, resolverTexto(valor, modelo.propiedades));
			}
		}

		leerRepositorios(project, modelo);
		leerDependencyManagement(project, modelo);
		leerDependencias(project, modelo);

		cachePom.put(key, modelo);
		pila.remove(key);

		return modelo;
	}

	private void leerRepositorios(Element project, ModeloPom modelo) {
		Element reposEl = hijo(project, "repositories");
		if (reposEl == null) {
			return;
		}

		for (Element repoEl : hijos(reposEl, "repository")) {
			String id = resolverTexto(textoHijo(repoEl, "id"), modelo.propiedades);
			String name = resolverTexto(textoHijo(repoEl, "name"), modelo.propiedades);
			String url = resolverTexto(textoHijo(repoEl, "url"), modelo.propiedades);

			if (url != null && !url.trim().isEmpty()) {
				Repositorio r = new Repositorio(id, name, url);

				if (!modelo.repositorios.contains(r)) {
					modelo.repositorios.add(r);
				}

				agregarRepositorioEncontrado(r);
			}
		}
	}

	private void leerDependencyManagement(Element project, ModeloPom modelo) {
		Element dmEl = hijo(project, "dependencyManagement");
		if (dmEl == null) {
			return;
		}

		Element depsEl = hijo(dmEl, "dependencies");
		if (depsEl == null) {
			return;
		}

		for (Element depEl : hijos(depsEl, "dependency")) {
			String g = resolverTexto(textoHijo(depEl, "groupId"), modelo.propiedades);
			String a = resolverTexto(textoHijo(depEl, "artifactId"), modelo.propiedades);
			String v = resolverTexto(textoHijo(depEl, "version"), modelo.propiedades);

			if (g != null && a != null && v != null) {
				modelo.dependencyManagement.put(g + ":" + a, v);
			}
		}
	}

	private void leerDependencias(Element project, ModeloPom modelo) {
		Element depsEl = hijo(project, "dependencies");
		if (depsEl == null) {
			return;
		}

		for (Element depEl : hijos(depsEl, "dependency")) {
			Dependencia d = new Dependencia();

			d.groupId = resolverTexto(textoHijo(depEl, "groupId"), modelo.propiedades);
			d.artifactId = resolverTexto(textoHijo(depEl, "artifactId"), modelo.propiedades);
			d.version = resolverTexto(textoHijo(depEl, "version"), modelo.propiedades);
			d.scope = resolverTexto(textoHijo(depEl, "scope"), modelo.propiedades);
			d.type = resolverTexto(textoHijo(depEl, "type"), modelo.propiedades);
			d.classifier = resolverTexto(textoHijo(depEl, "classifier"), modelo.propiedades);

			String optional = resolverTexto(textoHijo(depEl, "optional"), modelo.propiedades);
			d.optional = "true".equalsIgnoreCase(optional);

			Element exclusionsEl = hijo(depEl, "exclusions");
			if (exclusionsEl != null) {
				for (Element exEl : hijos(exclusionsEl, "exclusion")) {
					String eg = resolverTexto(textoHijo(exEl, "groupId"), modelo.propiedades);
					String ea = resolverTexto(textoHijo(exEl, "artifactId"), modelo.propiedades);

					if (eg != null && ea != null) {
						d.exclusiones.add(eg + ":" + ea);
					}
				}
			}

			if (d.groupId != null && d.artifactId != null) {
				modelo.dependencias.add(d);
			}
		}
	}

	private byte[] descargarBytesPom(Artefacto a) throws Exception {
		String key = a.gav() + ":pom";

		if (cacheBytesPom.containsKey(key)) {
			return cacheBytesPom.get(key);
		}

		for (Repositorio r : reposActuales()) {
			try {
				String url = construirUrlArtefacto(r, a, "pom");
				byte[] datos = leerUrl(url);
				cacheBytesPom.put(key, datos);
				return datos;
			} catch (Exception ignored) {
			}
		}

		return null;
	}

	private void descargarArtefactos() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(HILOS_DESCARGA);
		List<Future<Void>> futuros = new ArrayList<Future<Void>>();

		for (Artefacto a : artefactosResueltos.values()) {
			futuros.add(executor.submit(new TareaDescarga(a, "pom")));

			if (!"pom".equals(a.type)) {
				futuros.add(executor.submit(new TareaDescarga(a, a.extensionPrincipal())));
			}
		}

		for (Future<Void> f : futuros) {
			try {
				f.get();
			} catch (Exception e) {
				System.out.println("Error en descarga: " + e.getMessage());
			}
		}

		executor.shutdown();
	}

	private class TareaDescarga implements Callable<Void> {

		private final Artefacto artefacto;
		private final String extension;

		private TareaDescarga(Artefacto artefacto, String extension) {
			this.artefacto = artefacto;
			this.extension = extension;
		}

		@Override
		public Void call() throws Exception {
			File destino = archivoLocal(artefacto, extension);

			if (destino.exists() && destino.length() > 0L) {
				return null;
			}

			File carpetaPadre = destino.getParentFile();
			if (carpetaPadre != null) {
				carpetaPadre.mkdirs();
			}

			for (Repositorio r : reposActuales()) {
				try {
					String url = construirUrlArtefacto(r, artefacto, extension);
					byte[] datos = leerUrl(url);

					File tmp = new File(destino.getAbsolutePath() + ".tmp");

					FileOutputStream out = new FileOutputStream(tmp);
					try {
						out.write(datos);
					} finally {
						out.close();
					}

					if (destino.exists()) {
						destino.delete();
					}

					if (!tmp.renameTo(destino)) {
						Files.copy(tmp.toPath(), destino.toPath());
						tmp.delete();
					}

					System.out.println("Descargado: " + artefacto + " ." + extension + " desde " + r.id);
					return null;
				} catch (Exception ignored) {
				}
			}

			System.out.println("No se pudo descargar: " + artefacto + " ." + extension);
			return null;
		}
	}

	private List<Repositorio> reposActuales() {
		List<Repositorio> ret = new ArrayList<Repositorio>();

		for (Repositorio r : repositoriosBase) {
			if (!ret.contains(r)) {
				ret.add(r);
			}
		}

		for (Repositorio r : repositoriosEncontrados) {
			if (!ret.contains(r)) {
				ret.add(r);
			}
		}

		return ret;
	}

	private String construirUrlArtefacto(Repositorio repo, Artefacto a, String extension) throws Exception {
		String base = normalizarRepo(repo.url);
		String groupPath = a.groupId.replace('.', '/');
		String fileVersion = a.version;

		if (a.version.endsWith("-SNAPSHOT")) {
			String snapshot = resolverSnapshot(repo, a, extension);
			if (snapshot != null) {
				fileVersion = snapshot;
			}
		}

		String nombreArchivo = a.artifactId + "-" + fileVersion;

		if (a.classifier != null && !a.classifier.trim().isEmpty()) {
			nombreArchivo += "-" + a.classifier;
		}

		nombreArchivo += "." + extension;

		return base + groupPath + "/" + a.artifactId + "/" + a.version + "/" + nombreArchivo;
	}

	private String resolverSnapshot(Repositorio repo, Artefacto a, String extension) {
		try {
			String base = normalizarRepo(repo.url);
			String groupPath = a.groupId.replace('.', '/');

			String metadataUrl = base + groupPath + "/" + a.artifactId + "/" + a.version + "/maven-metadata.xml";
			byte[] datos = leerUrl(metadataUrl);

			Document doc = leerXml(datos);
			Element root = doc.getDocumentElement();

			Element versioning = hijo(root, "versioning");
			if (versioning == null) {
				return null;
			}

			Element snapshotVersions = hijo(versioning, "snapshotVersions");
			if (snapshotVersions != null) {
				for (Element sv : hijos(snapshotVersions, "snapshotVersion")) {
					String ext = textoHijo(sv, "extension");
					String classifier = textoHijo(sv, "classifier");
					String value = textoHijo(sv, "value");

					boolean extOk = extension.equals(ext);
					boolean classifierOk = (a.classifier == null && classifier == null)
							|| (a.classifier != null && a.classifier.equals(classifier));

					if (extOk && classifierOk && value != null) {
						return value;
					}
				}
			}

			Element snapshot = hijo(versioning, "snapshot");
			if (snapshot != null) {
				String timestamp = textoHijo(snapshot, "timestamp");
				String buildNumber = textoHijo(snapshot, "buildNumber");

				if (timestamp != null && buildNumber != null) {
					String baseVersion = a.version.substring(0, a.version.length() - "-SNAPSHOT".length());
					return baseVersion + "-" + timestamp + "-" + buildNumber;
				}
			}
		} catch (Exception ignored) {
		}

		return null;
	}

	private File archivoLocal(Artefacto a, String extension) {
		String nombreArchivo = a.artifactId + "-" + a.version;

		if (a.classifier != null && !a.classifier.trim().isEmpty()) {
			nombreArchivo += "-" + a.classifier;
		}

		nombreArchivo += "." + extension;

		return new File(REPOSITORY_DIR, nombreArchivo);
	}

	private static byte[] leerUrl(String url) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setConnectTimeout(TIMEOUT_MS);
		con.setReadTimeout(TIMEOUT_MS);
		con.setRequestProperty("User-Agent", "CrashDetector-DescargadorDependenciasMaven/1.0");
		con.setInstanceFollowRedirects(true);

		int code = con.getResponseCode();

		if (code < 200 || code >= 300) {
			throw new RuntimeException("HTTP " + code + " en " + url);
		}

		InputStream in = con.getInputStream();

		try {
			byte[] buf = new byte[8192];
			java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

			int len;
			while ((len = in.read(buf)) >= 0) {
				out.write(buf, 0, len);
			}

			return out.toByteArray();
		} finally {
			in.close();
		}
	}

	private static Document leerXml(byte[] datos) throws Exception {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();

		f.setNamespaceAware(false);
		f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		f.setFeature("http://xml.org/sax/features/external-general-entities", false);
		f.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		f.setExpandEntityReferences(false);

		return f.newDocumentBuilder().parse(new ByteArrayInputStream(datos));
	}

	private static String normalizarRepo(String url) {
		String u = url == null ? "" : url.trim();

		if (!u.endsWith("/")) {
			u += "/";
		}

		return u;
	}

	private static Element hijo(Element e, String nombre) {
		if (e == null) {
			return null;
		}

		for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n instanceof Element && nombre.equals(((Element) n).getTagName())) {
				return (Element) n;
			}
		}

		return null;
	}

	private static List<Element> hijos(Element e) {
		if (e == null) {
			return Collections.emptyList();
		}

		List<Element> ret = new ArrayList<Element>();

		for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n instanceof Element) {
				ret.add((Element) n);
			}
		}

		return ret;
	}

	private static List<Element> hijos(Element e, String nombre) {
		if (e == null) {
			return Collections.emptyList();
		}

		List<Element> ret = new ArrayList<Element>();

		for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n instanceof Element && nombre.equals(((Element) n).getTagName())) {
				ret.add((Element) n);
			}
		}

		return ret;
	}

	private static String textoHijo(Element e, String nombre) {
		Element h = hijo(e, nombre);

		if (h == null) {
			return null;
		}

		return texto(h);
	}

	private static String texto(Element e) {
		if (e == null) {
			return null;
		}

		String s = e.getTextContent();

		if (s == null) {
			return null;
		}

		s = s.trim();

		return s.isEmpty() ? null : s;
	}

	private static String resolverTexto(String texto, Map<String, String> props) {
		if (texto == null) {
			return null;
		}

		String r = texto;

		for (int vuelta = 0; vuelta < 10; vuelta++) {
			int ini = r.indexOf("${");

			if (ini < 0) {
				break;
			}

			int fin = r.indexOf("}", ini);

			if (fin < 0) {
				break;
			}

			String clave = r.substring(ini + 2, fin);
			String valor = props.get(clave);

			if (valor == null) {
				break;
			}

			r = r.substring(0, ini) + valor + r.substring(fin + 1);
		}

		return r;
	}

	public static class CoordenadaMaven {

		public final String groupId;
		public final String artifactId;
		public final String version;

		public CoordenadaMaven(String groupId, String artifactId, String version) {
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
		}
	}

	public static class ResultadoDescarga {

		public final boolean exito;
		public final File carpeta;
		public final String mensaje;

		public ResultadoDescarga(boolean exito, File carpeta, String mensaje) {
			this.exito = exito;
			this.carpeta = carpeta;
			this.mensaje = mensaje;
		}
	}

	private static class Repositorio {

		String id;
		String nombre;
		String url;

		Repositorio(String id, String nombre, String url) {
			this.id = id == null || id.trim().isEmpty() ? url : id.trim();
			this.nombre = nombre == null ? this.id : nombre.trim();
			this.url = normalizarRepo(url);
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Repositorio)) {
				return false;
			}

			return url.equals(((Repositorio) o).url);
		}

		@Override
		public int hashCode() {
			return url.hashCode();
		}
	}

	private static class Artefacto {

		String groupId;
		String artifactId;
		String version;
		String type;
		String classifier;

		Artefacto(String groupId, String artifactId, String version, String type, String classifier) {
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
			this.type = type == null || type.trim().isEmpty() ? "jar" : type.trim();
			this.classifier = classifier;
		}

		String gav() {
			return groupId + ":" + artifactId + ":" + version + ":" + type + ":"
					+ (classifier == null ? "" : classifier);
		}

		String extensionPrincipal() {
			if ("bundle".equals(type)) {
				return "jar";
			}

			if ("maven-plugin".equals(type)) {
				return "jar";
			}

			return type == null || type.trim().isEmpty() ? "jar" : type;
		}

		@Override
		public String toString() {
			String s = groupId + ":" + artifactId + ":" + version;

			if (type != null && !"jar".equals(type)) {
				s += ":" + type;
			}

			if (classifier != null && !classifier.trim().isEmpty()) {
				s += ":" + classifier;
			}

			return s;
		}
	}

	private static class Dependencia {

		String groupId;
		String artifactId;
		String version;
		String scope;
		String type;
		String classifier;
		boolean optional;

		Set<String> exclusiones = new HashSet<String>();

		boolean esUsableParaRuntime() {
			if (optional) {
				return false;
			}

			if (scope == null || scope.trim().isEmpty()) {
				return true;
			}

			String s = scope.trim();

			if ("compile".equals(s)) {
				return true;
			}

			if ("runtime".equals(s)) {
				return true;
			}

			return false;
		}
	}

	private static class ModeloPom {

		String groupId;
		String artifactId;
		String version;

		Map<String, String> propiedades = new HashMap<String, String>();
		Map<String, String> dependencyManagement = new HashMap<String, String>();

		List<Dependencia> dependencias = new ArrayList<Dependencia>();
		List<Repositorio> repositorios = new ArrayList<Repositorio>();

		void copiarDesde(ModeloPom otro) {
			if (otro == null) {
				return;
			}

			this.groupId = otro.groupId;
			this.artifactId = otro.artifactId;
			this.version = otro.version;

			this.propiedades.putAll(otro.propiedades);
			this.dependencyManagement.putAll(otro.dependencyManagement);

			for (Repositorio r : otro.repositorios) {
				if (!this.repositorios.contains(r)) {
					this.repositorios.add(r);
				}
			}
		}
	}

	private static class TrabajoResolucion {

		Artefacto artefacto;
		Set<String> exclusiones;

		TrabajoResolucion(Artefacto artefacto, Set<String> exclusiones) {
			this.artefacto = artefacto;
			this.exclusiones = exclusiones == null ? new HashSet<String>() : exclusiones;
		}
	}
}