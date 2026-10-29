package com.asbestosstar.crashdetector.cargador;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Lector autónomo de descriptores {@code module.xml} de FeatureCreep 12.
 *
 * No depende de featurecreep-loader en tiempo de ejecución. El parser está
 * endurecido: no admite DOCTYPE, no resuelve entidades externas y no procesa
 * XInclude. Cualquier documento mal formado devuelve un Optional vacío.
 *
 * Rutas válidas dentro de un mod: {@code module.xml} y
 * {@code modules/module.xml}.
 */
public final class LectorModuleXmlFC12 {

	public static final String NOMBRE_ARCHIVO = "module.xml";

	private static final String RUTA_EN_MODULES = "modules/module.xml";
	private static final String PROPIEDAD_MOD_ID = "FeatureCreep-Mod-Id";
	private static final String PROPIEDAD_VERSION = "Module-Version";
	private static final String ADAPTADOR_POR_DEFECTO = "java";
	private static final String TIPO_MAIN = "main";
	private static final String[] RUTAS_DESCRIPTOR = { NOMBRE_ARCHIVO, RUTA_EN_MODULES };

	private LectorModuleXmlFC12() {
	}

	/**
	 * Indica si una ruta relativa a la raíz del mod es un descriptor FC12 válido.
	 * Acepta separadores de Windows y barras iniciales.
	 */
	public static boolean esRutaDescriptor(String ruta) {
		if (ruta == null) {
			return false;
		}
		String normalizada = ruta.replace('\\', '/');
		while (normalizada.startsWith("/")) {
			normalizada = normalizada.substring(1);
		}
		return normalizada.equals(NOMBRE_ARCHIVO) || normalizada.equals(RUTA_EN_MODULES);
	}

	/**
	 * Interpreta el contenido de un {@code module.xml}.
	 *
	 * @return el descriptor, o vacío si el XML es inválido, no tiene elemento raíz
	 *         {@code module} o no declara nombre.
	 */
	public static Optional<Descriptor> leer(byte[] contenido) {
		if (contenido == null || contenido.length == 0) {
			return Optional.empty();
		}
		try {
			Element raiz = parsear(contenido);
			if (raiz == null || !"module".equals(nombreLocal(raiz))) {
				return Optional.empty();
			}
			return Optional.ofNullable(construir(raiz));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	/**
	 * Busca el descriptor FC12 dentro de una carpeta de mod o de un JAR.
	 */
	public static Optional<Descriptor> buscarEnOrigen(File origen) {
		if (origen == null || !origen.exists()) {
			return Optional.empty();
		}
		try {
			if (origen.isDirectory()) {
				for (String ruta : RUTAS_DESCRIPTOR) {
					File archivo = new File(origen, ruta);
					if (archivo.isFile()) {
						return leer(Files.readAllBytes(archivo.toPath()));
					}
				}
				return Optional.empty();
			}
			try (ZipFile zip = new ZipFile(origen)) {
				for (String ruta : RUTAS_DESCRIPTOR) {
					ZipEntry entrada = zip.getEntry(ruta);
					if (entrada != null) {
						try (InputStream in = zip.getInputStream(entrada)) {
							return leer(leerTodo(in));
						}
					}
				}
			}
		} catch (IOException e) {
			return Optional.empty();
		}
		return Optional.empty();
	}

	/**
	 * Main-Class efectiva. Si existe {@code module.xml}, el Main-Class del
	 * manifiesto se ignora aunque el descriptor no declare main-class.
	 */
	public static String resolverMainClass(Optional<Descriptor> descriptor, String mainClassManifiesto) {
		if (descriptor.isPresent()) {
			return descriptor.get().getMainClass();
		}
		return mainClassManifiesto == null ? "" : mainClassManifiesto.trim();
	}

	private static Element parsear(byte[] contenido) throws Exception {
		DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
		fabrica.setNamespaceAware(true);
		fabrica.setValidating(false);
		fabrica.setXIncludeAware(false);
		fabrica.setExpandEntityReferences(false);
		fabrica.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		fabrica.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		fabrica.setFeature("http://xml.org/sax/features/external-general-entities", false);
		fabrica.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		fabrica.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		fabrica.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		fabrica.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

		DocumentBuilder constructor = fabrica.newDocumentBuilder();
		constructor.setErrorHandler(new DefaultHandler());
		Document documento = constructor.parse(new ByteArrayInputStream(contenido));
		return documento.getDocumentElement();
	}

	private static Descriptor construir(Element raiz) {
		String nombre = raiz.getAttribute("name").trim();
		if (nombre.isEmpty()) {
			return null;
		}

		Map<String, String> propiedades = new HashMap<>();
		List<Entrypoint> entrypoints = new ArrayList<>();
		String mainClassElemento = "";

		for (Element hijo : hijosElemento(raiz)) {
			String tipo = nombreLocal(hijo);
			if ("properties".equals(tipo)) {
				for (Element propiedad : hijosElemento(hijo)) {
					if ("property".equals(nombreLocal(propiedad))) {
						String clave = propiedad.getAttribute("name").trim();
						if (!clave.isEmpty()) {
							propiedades.put(clave, propiedad.getAttribute("value").trim());
						}
					}
				}
			} else if ("main-class".equals(tipo)) {
				if (mainClassElemento.isEmpty()) {
					mainClassElemento = hijo.getAttribute("name").trim();
				}
			} else if ("entrypoint".equals(tipo)) {
				anadirEntrypoint(hijo, entrypoints);
			} else if ("entrypoints".equals(tipo)) {
				for (Element entrypoint : hijosElemento(hijo)) {
					if ("entrypoint".equals(nombreLocal(entrypoint))) {
						anadirEntrypoint(entrypoint, entrypoints);
					}
				}
			}
		}

		String version = raiz.getAttribute("version").trim();
		if (version.isEmpty()) {
			version = propiedades.containsKey(PROPIEDAD_VERSION) ? propiedades.get(PROPIEDAD_VERSION) : "";
		}

		String idMod = propiedades.get(PROPIEDAD_MOD_ID);
		if (idMod == null || idMod.isEmpty()) {
			idMod = nombre;
		}

		String mainClass = mainClassElemento;
		if (mainClass.isEmpty()) {
			for (Entrypoint entrypoint : entrypoints) {
				if (TIPO_MAIN.equals(entrypoint.getTipo())) {
					mainClass = entrypoint.getClase();
					break;
				}
			}
		}

		return new Descriptor(nombre, version, idMod, mainClass, entrypoints, propiedades);
	}

	private static void anadirEntrypoint(Element elemento, List<Entrypoint> destino) {
		String clase = elemento.getAttribute("class").trim();
		if (clase.isEmpty()) {
			return;
		}
		String tipo = elemento.getAttribute("type").trim();
		String adaptador = elemento.getAttribute("adapter").trim();
		destino.add(new Entrypoint(tipo, clase, adaptador.isEmpty() ? ADAPTADOR_POR_DEFECTO : adaptador));
	}

	private static List<Element> hijosElemento(Element padre) {
		List<Element> salida = new ArrayList<>();
		for (Node nodo = padre.getFirstChild(); nodo != null; nodo = nodo.getNextSibling()) {
			if (nodo instanceof Element) {
				salida.add((Element) nodo);
			}
		}
		return salida;
	}

	private static String nombreLocal(Node nodo) {
		String local = nodo.getLocalName();
		return local != null ? local : nodo.getNodeName();
	}

	private static byte[] leerTodo(InputStream in) throws IOException {
		ByteArrayOutputStream salida = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int leidos;
		while ((leidos = in.read(buffer)) != -1) {
			salida.write(buffer, 0, leidos);
		}
		return salida.toByteArray();
	}

	/** Descriptor FC12 ya interpretado. */
	public static final class Descriptor {

		private final String nombre;
		private final String version;
		private final String idMod;
		private final String mainClass;
		private final List<Entrypoint> entrypoints;
		private final Map<String, String> propiedades;

		Descriptor(String nombre, String version, String idMod, String mainClass, List<Entrypoint> entrypoints,
				Map<String, String> propiedades) {
			this.nombre = nombre;
			this.version = version;
			this.idMod = idMod;
			this.mainClass = mainClass;
			this.entrypoints = Collections.unmodifiableList(new ArrayList<>(entrypoints));
			this.propiedades = Collections.unmodifiableMap(new HashMap<>(propiedades));
		}

		/** Nombre del módulo JBoss (atributo {@code name} del elemento raíz). */
		public String getNombre() {
			return nombre;
		}

		/** Versión declarada en la raíz o en la propiedad {@code Module-Version}. */
		public String getVersion() {
			return version;
		}

		/** Identificador FeatureCreep: {@code FeatureCreep-Mod-Id} o, si falta, el nombre. */
		public String getIdMod() {
			return idMod;
		}

		/** Clase principal: {@code main-class} o el entrypoint de tipo {@code main}. */
		public String getMainClass() {
			return mainClass;
		}

		/** Entrypoints declarados explícitamente, en orden de documento. */
		public List<Entrypoint> getEntrypoints() {
			return entrypoints;
		}

		/** Propiedades declaradas en {@code <properties>}. */
		public Map<String, String> getPropiedades() {
			return propiedades;
		}
	}

	/** Entrypoint declarado con {@code <entrypoint type class adapter/>}. */
	public static final class Entrypoint {

		private final String tipo;
		private final String clase;
		private final String adaptador;

		Entrypoint(String tipo, String clase, String adaptador) {
			this.tipo = tipo;
			this.clase = clase;
			this.adaptador = adaptador;
		}

		public String getTipo() {
			return tipo;
		}

		public String getClase() {
			return clase;
		}

		public String getAdaptador() {
			return adaptador;
		}
	}
}
