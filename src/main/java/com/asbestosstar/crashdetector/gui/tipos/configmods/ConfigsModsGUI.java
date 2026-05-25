package com.asbestosstar.crashdetector.gui.tipos.configmods;

import java.awt.Color;
import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.dmr.Dmr;
import com.asbestosstar.crashdetector.config.hocon.Hocon;
import com.asbestosstar.crashdetector.config.ini.Ini;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json5.Json5;
import com.asbestosstar.crashdetector.config.jsonc.Jsonc;
import com.asbestosstar.crashdetector.config.nbt.Nbt;
import com.asbestosstar.crashdetector.config.snbt.Snbt;
import com.asbestosstar.crashdetector.config.toml.Toml;
import com.asbestosstar.crashdetector.config.yaml.Yaml;
import com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class ConfigsModsGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<ConfigsModsGUI>> GUIS = new java.util.HashMap<String, Supplier<ConfigsModsGUI>>();

	private static final long serialVersionUID = 1L;

	protected final List<File> archivosConfigs = new ArrayList<File>();
	protected final List<EntradaConfig> entradas = new ArrayList<EntradaConfig>();

	protected File archivoActual;
	protected Object nodoActual;
	protected String extensionActual;

	protected static class EntradaConfig {
		public String ruta;
		public String valor;
		public TipoValor tipo;
		public Object nodo;
		public JComponent editor;
	}

	protected enum TipoValor {
		BOOLEANO, ENTERO, LARGO, DECIMAL, TEXTO
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.CONFIG_MODS;
	}

	protected void buscarConfigsEditables() {
		archivosConfigs.clear();
		buscarEnCarpeta(new File("./config"));
		buscarEnCarpeta(new File("./etc"));
		buscarEnCarpeta(new File("./serverconfig"));
		Collections.sort(archivosConfigs, (a, b) -> a.getPath().compareToIgnoreCase(b.getPath()));
	}

	private void buscarEnCarpeta(File carpeta) {
		if (carpeta == null || !carpeta.exists() || !carpeta.isDirectory()) {
			return;
		}

		File[] archivos = carpeta.listFiles();
		if (archivos == null) {
			return;
		}

		for (File f : archivos) {
			if (f == null) {
				continue;
			}

			if (f.isDirectory()) {
				buscarEnCarpeta(f);
				continue;
			}

			if (esConfigEditable(f)) {
				archivosConfigs.add(f);
			}
		}
	}

	protected boolean esConfigEditable(File archivo) {
		String ext = extension(archivo);

		if ("properties".equals(ext) || "xml".equals(ext)) {
			return true;
		}

		if ("json".equals(ext) || "jsonc".equals(ext)) {
			return motorDisponible(Json.nombreMotor());
		}

		if ("json5".equals(ext)) {
			return motorDisponible(Json5.nombreMotor());
		}

		if ("yaml".equals(ext) || "yml".equals(ext)) {
			return motorDisponible(Yaml.nombreMotor());
		}

		if ("toml".equals(ext)) {
			return motorDisponible(Toml.nombreMotor());
		}

		if ("ini".equals(ext) || "cfg".equals(ext)) {
			return motorDisponible(Ini.nombreMotor());
		}

		if ("conf".equals(ext) || "hocon".equals(ext)) {
			return motorDisponible(Hocon.nombreMotor());
		}

		if ("dmr".equals(ext)) {
			return motorDisponible(Dmr.nombreMotor());
		}

		if ("nbt".equals(ext) || "dat".equals(ext)) {
			return motorDisponible(Nbt.nombreMotor());
		}

		if ("snbt".equals(ext)) {
			return motorDisponible(Snbt.nombreMotor());
		}

		return false;
	}

	protected boolean motorDisponible(String nombre) {
		return nombre != null && !"ninguno".equalsIgnoreCase(nombre);
	}

	protected String extension(File archivo) {
		if (archivo == null || archivo.getName() == null) {
			return "";
		}

		String n = archivo.getName().toLowerCase();
		int p = n.lastIndexOf('.');
		if (p < 0 || p >= n.length() - 1) {
			return "";
		}

		return n.substring(p + 1);
	}

	protected void cargarArchivo(File archivo) throws Exception {
		if (archivo == null) {
			return;
		}

		archivoActual = archivo;
		extensionActual = extension(archivo);
		entradas.clear();

		if ("nbt".equals(extensionActual) || "dat".equals(extensionActual)) {
			nodoActual = Nbt.leerArchivo(archivo);
			extraerNbt((Nbt.Nodo) nodoActual, "");
		} else {
			String texto = new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);

			if ("json".equals(extensionActual)) {
				nodoActual = Json.leer(texto);
				extraerJson((Json.Nodo) nodoActual, "");
			} else if ("jsonc".equals(extensionActual)) {
				nodoActual = Jsonc.leer(texto);
				extraerJson((Json.Nodo) nodoActual, "");
			} else if ("json5".equals(extensionActual)) {
				nodoActual = Json5.leer(texto);
				extraerJson((Json.Nodo) nodoActual, "");
			} else if ("yaml".equals(extensionActual) || "yml".equals(extensionActual)) {
				nodoActual = Yaml.leer(texto);
				extraerYaml((Yaml.Nodo) nodoActual, "");
			} else if ("toml".equals(extensionActual)) {
				nodoActual = Toml.leer(texto);
				extraerToml((Toml.Nodo) nodoActual, "");
			} else if ("ini".equals(extensionActual) || "cfg".equals(extensionActual)) {
				nodoActual = Ini.leer(texto);
				extraerIni((Ini.Nodo) nodoActual);
			} else if ("conf".equals(extensionActual) || "hocon".equals(extensionActual)) {
				nodoActual = Hocon.leer(texto);
				extraerHocon((Hocon.Nodo) nodoActual, "");
			} else if ("dmr".equals(extensionActual)) {
				nodoActual = Dmr.leer(texto);
				extraerJson((Json.Nodo) nodoActual, "");
			} else if ("snbt".equals(extensionActual)) {
				nodoActual = Snbt.leer(texto);
				extraerNbt((Nbt.Nodo) nodoActual, "");
			} else if ("properties".equals(extensionActual)) {
				nodoActual = leerProperties(archivo);
				extraerProperties((Properties) nodoActual);
			} else if ("xml".equals(extensionActual)) {
				nodoActual = leerXml(archivo);
				extraerXml(((Document) nodoActual).getDocumentElement(), "");
			} else {
				throw new IllegalStateException(MonitorDePID.idioma.archivoNoSoportado());
			}
		}

		for (EntradaConfig e : entradas) {
			e.editor = crearEditor(e);
		}
	}

	protected void guardarArchivoActual() throws Exception {
		if (archivoActual == null || nodoActual == null) {
			throw new IllegalStateException(MonitorDePID.idioma.sinArchivoSeleccionado());
		}

		actualizarValoresDesdeEditores();

		if ("json".equals(extensionActual)) {
			escribirTexto(Json.escribir((Json.Nodo) nodoActual));
		} else if ("jsonc".equals(extensionActual)) {
			escribirTexto(Jsonc.escribir((Json.Nodo) nodoActual));
		} else if ("json5".equals(extensionActual)) {
			escribirTexto(Json5.escribir((Json.Nodo) nodoActual));
		} else if ("yaml".equals(extensionActual) || "yml".equals(extensionActual)) {
			escribirTexto(Yaml.escribir((Yaml.Nodo) nodoActual));
		} else if ("toml".equals(extensionActual)) {
			escribirTexto(Toml.escribir((Toml.Nodo) nodoActual));
		} else if ("ini".equals(extensionActual) || "cfg".equals(extensionActual)) {
			escribirTexto(Ini.escribir((Ini.Nodo) nodoActual));
		} else if ("conf".equals(extensionActual) || "hocon".equals(extensionActual)) {
			escribirTexto(Hocon.escribir((Hocon.Nodo) nodoActual));
		} else if ("dmr".equals(extensionActual)) {
			escribirTexto(Dmr.escribir((Json.Nodo) nodoActual));
		} else if ("snbt".equals(extensionActual)) {
			escribirTexto(Snbt.escribir((Nbt.Nodo) nodoActual));
		} else if ("nbt".equals(extensionActual) || "dat".equals(extensionActual)) {
			Nbt.escribirArchivo((Nbt.Nodo) nodoActual, archivoActual);
		} else if ("properties".equals(extensionActual)) {
			escribirProperties((Properties) nodoActual);
		} else if ("xml".equals(extensionActual)) {
			escribirXml((Document) nodoActual);
		}
	}

	private void escribirTexto(String texto) throws Exception {
		Files.write(archivoActual.toPath(), texto.getBytes(StandardCharsets.UTF_8));
	}

	protected void actualizarValoresDesdeEditores() {
		for (EntradaConfig e : entradas) {
			if (e.editor instanceof JCheckBox) {
				e.valor = Boolean.toString(((JCheckBox) e.editor).isSelected());
			} else if (e.editor instanceof JSpinner) {
				e.valor = String.valueOf(((JSpinner) e.editor).getValue());
			} else if (e.editor instanceof JTextField) {
				e.valor = ((JTextField) e.editor).getText();
			}

			aplicarValor(e);
		}
	}

	private void aplicarValor(EntradaConfig e) {
		Object valor = convertirValor(e);

		if (e.nodo instanceof Json.Nodo) {
			((Json.Nodo) e.nodo).ponerValorFlexible(valor);
		} else if (e.nodo instanceof Yaml.Nodo) {
			ponerYaml((Yaml.Nodo) e.nodo, valor);
		} else if (e.nodo instanceof Toml.Nodo) {
			((Toml.Nodo) e.nodo).poner(valor);
		} else if (e.nodo instanceof Ini.Nodo) {
			((Ini.Nodo) e.nodo).poner(String.valueOf(valor));
		} else if (e.nodo instanceof Hocon.Nodo) {
			((Hocon.Nodo) e.nodo).poner(valor);
		} else if (e.nodo instanceof Nbt.Nodo) {
			ponerNbt((Nbt.Nodo) e.nodo, valor);
		} else if (e.nodo instanceof Properties) {
			((Properties) e.nodo).setProperty(e.ruta, String.valueOf(valor));
		} else if (e.nodo instanceof Element) {
			((Element) e.nodo).setTextContent(String.valueOf(valor));
		}
	}

	private void ponerNbt(Nbt.Nodo n, Object valor) {
		if (valor instanceof Boolean) {
			n.poner(((Boolean) valor).booleanValue());
		} else if (valor instanceof Integer) {
			n.poner(((Integer) valor).intValue());
		} else if (valor instanceof Long) {
			n.poner(((Long) valor).longValue());
		} else if (valor instanceof Double) {
			n.poner(((Double) valor).doubleValue());
		} else if (valor instanceof Number) {
			n.poner(((Number) valor).doubleValue());
		} else {
			n.poner(valor == null ? "" : String.valueOf(valor));
		}
	}

	private void extraerNbt(Nbt.Nodo nodo, String ruta) {
		if (nodo == null) {
			return;
		}

		if (nodo.esObjeto()) {
			for (String k : nodo.claves()) {
				extraerNbt(nodo.obtener(k), unirRuta(ruta, k));
			}
			return;
		}

		if (nodo.esLista()) {
			for (int i = 0; i < nodo.tamano(); i++) {
				extraerNbt(nodo.en(i), ruta + "[" + i + "]");
			}
			return;
		}

		if (nodo.esByteArray() || nodo.esIntArray() || nodo.esLongArray()) {
			// Arrays NBT tipados no son seguros de editar como un solo campo simple.
			// Se muestran como SNBT para referencia.
			agregarEntrada(ruta, nodo.escribirSnbt(), nodo);
			return;
		}

		agregarEntrada(ruta, nodo.comoCadena(), nodo);
	}

	private void ponerYaml(Yaml.Nodo n, Object valor) {
		if (valor instanceof Boolean) {
			n.poner(((Boolean) valor).booleanValue());
		} else if (valor instanceof Integer) {
			n.poner(((Integer) valor).intValue());
		} else if (valor instanceof Long) {
			n.poner(((Long) valor).longValue());
		} else if (valor instanceof Double) {
			n.poner(((Double) valor).doubleValue());
		} else if (valor instanceof Number) {
			n.poner(((Number) valor).doubleValue());
		} else {
			n.poner(valor == null ? "" : String.valueOf(valor));
		}
	}

	private Object convertirValor(EntradaConfig e) {
		try {
			if (e.tipo == TipoValor.BOOLEANO) {
				return Boolean.valueOf(e.valor);
			}
			if (e.tipo == TipoValor.ENTERO) {
				return Integer.valueOf(e.valor);
			}
			if (e.tipo == TipoValor.LARGO) {
				return Long.valueOf(e.valor);
			}
			if (e.tipo == TipoValor.DECIMAL) {
				return Double.valueOf(e.valor);
			}
		} catch (Exception ignored) {
		}

		return e.valor == null ? "" : e.valor;
	}

	protected JComponent crearEditor(EntradaConfig e) {
		if (e.tipo == TipoValor.BOOLEANO) {
			JCheckBox c = new JCheckBox();
			c.setSelected(Boolean.parseBoolean(e.valor));
			return c;
		}

		if (e.tipo == TipoValor.ENTERO) {
			return new JSpinner(new SpinnerNumberModel(parseInt(e.valor), Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		}

		if (e.tipo == TipoValor.LARGO) {
			return new JSpinner(new SpinnerNumberModel(parseLong(e.valor), Long.MIN_VALUE, Long.MAX_VALUE, 1L));
		}

		if (e.tipo == TipoValor.DECIMAL) {
			return new JSpinner(new SpinnerNumberModel(parseDouble(e.valor), -999999999D, 999999999D, 0.1D));
		}

		return new JTextField(e.valor == null ? "" : e.valor);
	}

	private int parseInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
	}

	private long parseLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return 0L;
		}
	}

	private double parseDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return 0D;
		}
	}

	protected TipoValor detectarTipo(String valor) {
		if (valor == null) {
			return TipoValor.TEXTO;
		}

		String v = valor.trim();

		if ("true".equalsIgnoreCase(v) || "false".equalsIgnoreCase(v)) {
			return TipoValor.BOOLEANO;
		}

		try {
			Integer.parseInt(v);
			return TipoValor.ENTERO;
		} catch (Exception ignored) {
		}

		try {
			Long.parseLong(v);
			return TipoValor.LARGO;
		} catch (Exception ignored) {
		}

		try {
			Double.parseDouble(v);
			return TipoValor.DECIMAL;
		} catch (Exception ignored) {
		}

		return TipoValor.TEXTO;
	}

	private void agregarEntrada(String ruta, String valor, Object nodo) {
		EntradaConfig e = new EntradaConfig();
		e.ruta = ruta;
		e.valor = valor == null ? "" : valor;
		e.tipo = detectarTipo(e.valor);
		e.nodo = nodo;
		entradas.add(e);
	}

	private void extraerJson(Json.Nodo nodo, String ruta) {
		if (nodo == null) {
			return;
		}

		if (nodo.esObjeto()) {
			for (String k : nodo.claves()) {
				extraerJson(nodo.obtener(k), unirRuta(ruta, k));
			}
			return;
		}

		if (nodo.esArreglo()) {
			for (int i = 0; i < nodo.tamano(); i++) {
				extraerJson(nodo.en(i), ruta + "[" + i + "]");
			}
			return;
		}

		agregarEntrada(ruta, nodo.comoCadena(), nodo);
	}

	private void extraerYaml(Yaml.Nodo nodo, String ruta) {
		if (nodo == null) {
			return;
		}

		if (nodo.esObjeto()) {
			for (String k : nodo.claves()) {
				extraerYaml(nodo.obtener(k), unirRuta(ruta, k));
			}
			return;
		}

		if (nodo.esArreglo()) {
			for (int i = 0; i < nodo.tamano(); i++) {
				extraerYaml(nodo.en(i), ruta + "[" + i + "]");
			}
			return;
		}

		agregarEntrada(ruta, nodo.comoCadena(), nodo);
	}

	private void extraerToml(Toml.Nodo nodo, String ruta) {
		if (nodo == null) {
			return;
		}

		if (nodo.esObjeto()) {
			for (String k : nodo.claves()) {
				extraerToml(nodo.obtener(k), unirRuta(ruta, k));
			}
			return;
		}

		if (nodo.esArreglo()) {
			for (int i = 0; i < nodo.tamano(); i++) {
				extraerToml(nodo.en(i), ruta + "[" + i + "]");
			}
			return;
		}

		agregarEntrada(ruta, nodo.comoCadena(), nodo);
	}

	private void extraerIni(Ini.Nodo raiz) {
		for (String seccion : raiz.secciones()) {
			Ini.Nodo sec = raiz.obtenerSeccion(seccion);
			for (String clave : sec.claves()) {
				Ini.Nodo valor = sec.obtener(clave);
				agregarEntrada(seccion + "." + clave, valor.comoCadena(), valor);
			}
		}
	}

	private void extraerHocon(Hocon.Nodo nodo, String ruta) {
		List<String> claves = nodo.claves();

		if (claves != null && !claves.isEmpty()) {
			for (String k : claves) {
				extraerHocon(nodo.obtener(k), unirRuta(ruta, k));
			}
			return;
		}

		agregarEntrada(ruta, nodo.comoCadena(), nodo);
	}

	private Properties leerProperties(File archivo) throws Exception {
		Properties p = new Properties();
		FileInputStream in = new FileInputStream(archivo);
		try {
			p.load(in);
		} finally {
			in.close();
		}
		return p;
	}

	private void extraerProperties(Properties p) {
		for (String k : p.stringPropertyNames()) {
			agregarEntrada(k, p.getProperty(k), p);
		}
	}

	private void escribirProperties(Properties p) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		p.store(out, MonitorDePID.idioma.editorConfigsMods());
		Files.write(archivoActual.toPath(), out.toByteArray());
	}

	private Document leerXml(File archivo) throws Exception {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(archivo);
	}

	private void extraerXml(Element el, String ruta) {
		String nombre = unirRuta(ruta, el.getNodeName());
		boolean tieneHijos = false;

		for (int i = 0; i < el.getChildNodes().getLength(); i++) {
			if (el.getChildNodes().item(i) instanceof Element) {
				tieneHijos = true;
				extraerXml((Element) el.getChildNodes().item(i), nombre);
			}
		}

		if (!tieneHijos) {
			agregarEntrada(nombre, el.getTextContent(), el);
		}
	}

	private void escribirXml(Document doc) throws Exception {
		javax.xml.transform.Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.transform(new DOMSource(doc), new StreamResult(archivoActual));
	}

	private String unirRuta(String a, String b) {
		if (a == null || a.isEmpty()) {
			return b;
		}
		return a + "." + b;
	}

	protected void estilizarBoton(javax.swing.JButton b, Color fondo, Color texto, Color borde) {
		if (b == null) {
			return;
		}

		b.setBackground(fondo);
		b.setForeground(texto);
		b.setFocusPainted(false);
		b.setOpaque(true);
		b.setContentAreaFilled(true);
		b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borde, 1),
				BorderFactory.createEmptyBorder(6, 12, 6, 12)));
	}

	protected class RenderValorConfig extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus,
				int row, int col) {
			if (value instanceof EntradaConfig) {
				return ((EntradaConfig) value).editor;
			}
			return super.getTableCellRendererComponent(table, value, selected, focus, row, col);
		}
	}

	protected class EditorValorConfig extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private JComponent editorActual;

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean selected, int row, int col) {
			if (value instanceof EntradaConfig) {
				editorActual = ((EntradaConfig) value).editor;
				return editorActual;
			}
			editorActual = new JTextField(String.valueOf(value));
			return editorActual;
		}

		@Override
		public Object getCellEditorValue() {
			return editorActual;
		}
	}

	protected static final File CARPETA_DEPS_CONFIGS = new File(System.getProperty("user.home"), "crash_detector/deps");

	protected static class DependenciaConfigMods {

		public final String groupId;
		public final String artifactId;
		public final String version;

		public DependenciaConfigMods(String groupId, String artifactId, String version) {
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
		}

		public String nombreVisible() {
			return groupId + ":" + artifactId + ":" + version;
		}

		public String nombreJar() {
			return artifactId + "-" + version + ".jar";
		}

		public boolean coincideConNombreJar(String nombreArchivo) {
			if (nombreArchivo == null) {
				return false;
			}

			String n = nombreArchivo.trim().toLowerCase();

			return n.equals(nombreJar().toLowerCase()) || n.startsWith((artifactId + "-").toLowerCase());
		}

		public boolean coordenadaValida() {
			return groupId != null && !groupId.trim().isEmpty() && artifactId != null && !artifactId.trim().isEmpty()
					&& version != null && !version.trim().isEmpty();
		}
	}

	/*
	 * Dependencias opcionales usadas por el editor de configs.
	 *
	 * El descargador Maven resolvera tambien las transitivas.
	 */
	protected static final List<DependenciaConfigMods> DEPENDENCIAS_CONFIGS_REQUERIDAS = Arrays.asList(
			// JSON
			new DependenciaConfigMods("com.google.code.gson", "gson", "2.10.1"),
			new DependenciaConfigMods("org.jboss", "jboss-dmr", "1.6.1.Final"),
			new DependenciaConfigMods("com.googlecode.json-simple", "json-simple", "1.1.1"),

			// JSON5
			new DependenciaConfigMods("de.marhali", "json5-java", "3.0.0"),

			// YAML
			new DependenciaConfigMods("org.yaml", "snakeyaml", "1.33"),

			// TOML
			new DependenciaConfigMods("com.electronwill.night-config", "toml", "3.6.0"),

			// HOCON
			new DependenciaConfigMods("org.spongepowered", "configurate-hocon", "4.2.0"),

			// INI / CFG / HOCON extra fallback helpers
			new DependenciaConfigMods("org.apache.commons", "commons-configuration2", "2.15.0"),
			new DependenciaConfigMods("org.ini4j", "ini4j", "0.5.4"),

			// NBT / SNBT
			new DependenciaConfigMods("com.github.Querz", "NBT", "6.1"));

	protected List<DependenciaConfigMods> dependenciasConfigsFaltantesEnCarpetaInstalacion() {
		List<DependenciaConfigMods> faltantes = new ArrayList<DependenciaConfigMods>();

		List<File> jars = encontrarJarsDepsConfigs();

		for (DependenciaConfigMods dep : DEPENDENCIAS_CONFIGS_REQUERIDAS) {
			boolean encontrada = false;

			for (File jar : jars) {
				if (jar == null || !jar.isFile()) {
					continue;
				}

				if (jar.length() <= 0L) {
					continue;
				}

				if (dep.coincideConNombreJar(jar.getName())) {
					encontrada = true;
					break;
				}
			}

			if (!encontrada) {
				faltantes.add(dep);
			}
		}

		return faltantes;
	}

	protected List<File> encontrarJarsDepsConfigs() {
		List<File> ret = new ArrayList<File>();

		if (!CARPETA_DEPS_CONFIGS.exists() || !CARPETA_DEPS_CONFIGS.isDirectory()) {
			return ret;
		}

		File[] archivos = CARPETA_DEPS_CONFIGS.listFiles();

		if (archivos == null) {
			return ret;
		}

		for (File archivo : archivos) {
			if (archivo != null && archivo.isFile() && archivo.getName().toLowerCase().endsWith(".jar")) {
				ret.add(archivo);
			}
		}

		return ret;
	}

	protected int descargarTodasLasDependenciasConfigsFaltantes() {
		List<DependenciaConfigMods> faltantes = dependenciasConfigsFaltantesEnCarpetaInstalacion();

		if (faltantes == null || faltantes.isEmpty()) {
			return 0;
		}

		if (!CARPETA_DEPS_CONFIGS.exists()) {
			CARPETA_DEPS_CONFIGS.mkdirs();
		}

		List<DescargadorDependenciasMaven.CoordenadaMaven> coordenadas = new ArrayList<DescargadorDependenciasMaven.CoordenadaMaven>();

		for (DependenciaConfigMods dep : faltantes) {
			if (dep == null || !dep.coordenadaValida()) {
				continue;
			}

			coordenadas.add(new DescargadorDependenciasMaven.CoordenadaMaven(dep.groupId, dep.artifactId, dep.version));
		}

		if (coordenadas.isEmpty()) {
			return 0;
		}

		DescargadorDependenciasMaven.ResultadoDescarga r = DescargadorDependenciasMaven
				.descargarDependencias(coordenadas);

		if (!r.exito) {
			CrashDetectorLogger.log("Error descargando dependencias del editor de configs: " + r.mensaje);
		}

		return contarDependenciasConfigsInstaladas(faltantes);
	}

	private int contarDependenciasConfigsInstaladas(List<DependenciaConfigMods> deps) {
		if (deps == null || deps.isEmpty()) {
			return 0;
		}

		int total = 0;
		List<File> jars = encontrarJarsDepsConfigs();

		for (DependenciaConfigMods dep : deps) {
			if (dep == null) {
				continue;
			}

			for (File jar : jars) {
				if (jar == null || !jar.isFile()) {
					continue;
				}

				if (jar.length() <= 0L) {
					continue;
				}

				if (dep.coincideConNombreJar(jar.getName())) {
					total++;
					break;
				}
			}
		}

		return total;
	}

}