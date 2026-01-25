package com.asbestosstar.crashdetector.gui.tipos.miranda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * GUI base para editar derechos tipo Miranda usados por PirataMC. Escribe el
 * archivo derechos_piratas.json.
 *
 * Formato JSON: { "es": "", "en": "" }
 */
public abstract class DerechosPiratasGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<DerechosPiratasGUI>> GUIS = new HashMap<>();

	protected final Path archivo = Statics.carpeta.resolve("derechos_piratas.json");

	/** mapa lang -> html */
	protected final Map<String, String> derechos = new HashMap<>();

	/*
	 * ========================= CARGA / GUARDADO =========================
	 */

	protected void cargarDatos() {
		derechos.clear();
		if (!Files.exists(archivo))
			return;

		try {
			String json = new String(Files.readAllBytes(archivo), StandardCharsets.UTF_8);
			if (json == null || json.trim().isEmpty())
				return;

			Nodo raiz = Json.leer(json);
			if (!raiz.esObjeto())
				return;

			for (String k : raiz.claves()) {
				Nodo v = raiz.obtener(k);
				if (v != null && !v.esObjeto() && !v.esArreglo()) {
					String txt = v.comoCadena();
					if (txt != null && !txt.trim().isEmpty()) {
						derechos.put(k, txt);
					}
				}
			}
		} catch (Exception ignored) {
		}
	}

	protected void guardarDatos() {
		try {
			Nodo raiz = Json.crearObjeto();
			for (Map.Entry<String, String> e : derechos.entrySet()) {
				if (e.getKey() == null)
					continue;
				String v = e.getValue();
				if (v == null)
					continue;
				v = v.trim();
				if (v.isEmpty())
					continue;
				raiz.obtener(e.getKey()).poner(v);
			}
			Files.write(archivo, raiz.aBytesUtf8());
		} catch (IOException ignored) {
		}
		dispose();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.MIRANDA;
	}

	/*
	 * ========================= UTILIDADES =========================
	 */

	protected static String obtenerCodigoIdioma(String nombre) {
		if (nombre == null)
			return "es";
		switch (nombre) {
		case "Español":
			return "es";
		case "English":
			return "en";
		case "Русский":
			return "ru";
		case "简体中文":
			return "zh";
		case "فارسی":
			return "fa";
		case "한국어":
			return "ko";
		case "日本語":
			return "ja";
		case "العربية":
			return "ar";
		default:
			return "es";
		}
	}

	protected void forzarFondo(JComponent c, java.awt.Color fondo) {
		if (c == null)
			return;
		c.setOpaque(true);
		c.setBackground(fondo);
	}

	/**
	 * Editor multilenguaje HTML, similar a SylentBell pero con JTextArea grande.
	 */
	protected void editarDerechosMultilenguaje(String titulo, Color fondo, Color texto, Color caja, Color borde) {
		if (derechos == null)
			return;

		JDialog dialogo = new JDialog(this, titulo, true);
		dialogo.setLayout(new BorderLayout(10, 10));
		dialogo.setSize(760, 520);

		JPanel panelCampos = new JPanel(new GridBagLayout());
		panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelCampos.setBackground(fondo);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.BOTH;

		LinkedHashMap<String, String> idiomas = mapaParaComboBoxIdiomas();

		int fila = 0;
		Map<String, JTextArea> campos = new HashMap<>();

		for (Entry<String, String> entry : idiomas.entrySet()) {
			String nombreVisible = entry.getKey();
			String rutaBandera = entry.getValue();
			String codigo = obtenerCodigoIdioma(nombreVisible);

			// ===== LABEL CON BANDERA =====
			JLabel etiqueta = new JLabel(codigo);
			etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
			etiqueta.setForeground(texto);

			try {
				File f = Statics.carpeta.resolve(rutaBandera).toFile();
				if (f.exists()) {
					ImageIcon ic = new ImageIcon(f.getAbsolutePath());
					Image img = ic.getImage().getScaledInstance(18, 12, Image.SCALE_SMOOTH);
					etiqueta.setIcon(new ImageIcon(img));
					etiqueta.setIconTextGap(8);
				}
			} catch (Exception ignored) {
			}

			gbc.gridx = 0;
			gbc.gridy = fila;
			gbc.weightx = 0.15;
			panelCampos.add(etiqueta, gbc);

			// ===== AREA HTML =====
			JTextArea area = new JTextArea(derechos.getOrDefault(codigo, ""));
			area.setLineWrap(true);
			area.setWrapStyleWord(true);
			area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			area.setBackground(caja);
			area.setForeground(texto);
			area.setCaretColor(texto);

			JScrollPane scroll = new JScrollPane(area);
			scroll.setBorder(BorderFactory.createLineBorder(borde, 1));

			gbc.gridx = 1;
			gbc.weightx = 0.85;
			panelCampos.add(scroll, gbc);

			campos.put(codigo, area);
			fila++;
		}

		// ===== BOTONES =====
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botones.setBackground(fondo);

		JButton ok = new JButton(MonitorDePID.idioma.aceptar());
		JButton cancel = new JButton(MonitorDePID.idioma.cancelar());

		ok.setBackground(borde);
		ok.setForeground(Color.BLACK);
		ok.setFont(new Font("Segoe UI", Font.BOLD, 12));

		cancel.setBackground(borde);
		cancel.setForeground(Color.BLACK);
		cancel.setFont(new Font("Segoe UI", Font.BOLD, 12));

		ok.addActionListener(e -> {
			derechos.clear();
			for (Entry<String, JTextArea> c : campos.entrySet()) {
				String v = c.getValue().getText().trim();
				if (!v.isEmpty())
					derechos.put(c.getKey(), v);
			}

			// Verificación pasiva: si hay texto en coreano ("ko"), verificar jerga sureña
			String textoCoreano = derechos.get("ko");
			if (textoCoreano != null && !textoCoreano.isEmpty()) {
				com.asbestosstar.crashdetector.idioma.cumplimiento.ActaDeProteccionDelIdiomaCulturalDePyongyang
						.contieneJergaSur(textoCoreano);
			}

			dialogo.dispose();
		});

		cancel.addActionListener(e -> dialogo.dispose());

		botones.add(ok);
		botones.add(cancel);

		dialogo.add(new JScrollPane(panelCampos), BorderLayout.CENTER);
		dialogo.add(botones, BorderLayout.SOUTH);
		dialogo.setLocationRelativeTo(this);
		dialogo.setVisible(true);
	}

}
