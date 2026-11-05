package com.asbestosstar.crashdetector.gui.tipos.miranda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JViewport;

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class DerechosPiratasGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<DerechosPiratasGUI>> GUIS = new HashMap<String, Supplier<DerechosPiratasGUI>>();

	public final Path archivo = Statics.carpeta.resolve("derechos_piratas.json");

	/** mapa codigo_idioma -> html */
	public final Map<String, String> derechos = new HashMap<String, String>();

	public void cargarDatos() {
		derechos.clear();
		if (!Files.exists(archivo)) {
			return;
		}

		try {
			String json = new String(Files.readAllBytes(archivo), StandardCharsets.UTF_8);
			if (json == null || json.trim().isEmpty()) {
				return;
			}

			Nodo raiz = Json.leer(json);
			if (!raiz.esObjeto()) {
				return;
			}

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

	public void guardarDatos() {
		try {
			Nodo raiz = Json.crearObjeto();

			for (Map.Entry<String, String> e : derechos.entrySet()) {
				if (e.getKey() == null) {
					continue;
				}

				String codigo = e.getKey().trim();
				if (codigo.isEmpty()) {
					continue;
				}

				String v = e.getValue();
				if (v == null) {
					continue;
				}

				v = v.trim();
				if (v.isEmpty()) {
					continue;
				}

				raiz.obtener(codigo).poner(v);
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

	/**
	 * Convierte el nombre visible del idioma a su código usando el registro
	 * dinámico actual.
	 */
	public String obtenerCodigoIdiomaDinamico(String nombreVisible) {
		return Idioma.codigoDesdeNombreVisible(nombreVisible);
	}

	public void forzarFondo(JComponent c, Color fondo) {
		if (c == null) {
			return;
		}
		c.setOpaque(true);
		c.setBackground(fondo);
	}

	/**
	 * En macOS conviene usar una fuente más segura que Segoe UI, porque Aqua a
	 * veces combina peor tamaños/preferencias con fuentes no nativas.
	 */
	public Font fuenteUI(int estilo, int tam) {
		if (CrashDetectorGUI.esMac()) {
			return new Font("SansSerif", estilo, tam);
		}
		return new Font("Segoe UI", estilo, tam);
	}

	/**
	 * Crea un JTextArea con tamaño explícito para evitar que en macOS colapse a una
	 * altura casi nula dentro de GridBagLayout.
	 */
	public JTextArea crearAreaMultilinea(String textoInicial, Color fondo, Color texto) {
		JTextArea area = new JTextArea(textoInicial != null ? textoInicial : "");
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(fuenteUI(Font.PLAIN, 13));
		area.setBackground(fondo);
		area.setForeground(texto);
		area.setCaretColor(texto);

		// Filas/columnas para que el preferred size no dependa del LAF.
		area.setRows(CrashDetectorGUI.esMac() ? 5 : 4);
		area.setColumns(32);

		// En macOS hacemos aún más explícito el tamaño.
		if (CrashDetectorGUI.esMac()) {
			area.setMinimumSize(new Dimension(320, 96));
			area.setPreferredSize(new Dimension(480, 110));
		}

		return area;
	}

	/**
	 * Crea un JScrollPane robusto para macOS, forzando viewport opaco y tamaño
	 * mínimo.
	 */
	public JScrollPane crearScrollRobusto(JTextArea area, Color fondoScroll, Color borde) {
		JScrollPane scroll = new JScrollPane(area);
		scroll.setBorder(BorderFactory.createLineBorder(borde, 1));
		scroll.getViewport().setOpaque(true);
		scroll.getViewport().setBackground(fondoScroll);
		scroll.setOpaque(true);
		scroll.setBackground(fondoScroll);

		if (CrashDetectorGUI.esMac()) {
			scroll.setMinimumSize(new Dimension(360, 110));
			scroll.setPreferredSize(new Dimension(520, 120));
		}

		return scroll;
	}

	/**
	 * Estiliza un botón de forma más determinista para macOS.
	 */
	public void estilizarBoton(JButton boton, Color fondo, Color texto) {
		if (boton == null) {
			return;
		}

		boton.setFont(fuenteUI(Font.BOLD, 12));
		boton.setOpaque(true);
		boton.setBackground(fondo);
		boton.setForeground(texto);
		boton.setFocusPainted(false);
		boton.setBorderPainted(true);

		if (CrashDetectorGUI.esMac()) {
			boton.setContentAreaFilled(true);
			boton.setMinimumSize(new Dimension(110, 32));
			boton.setPreferredSize(new Dimension(130, 34));
		}
	}

	/**
	 * Fuerza colores/opacidad recursivamente también en componentes que en macOS
	 * suelen quedar “transparentes” o visualmente incoherentes.
	 */
	public void aplicarTemaRecursivo(Component c, Color fondoBase, Color textoBase) {
		if (c == null) {
			return;
		}

		if (c instanceof JPanel || c instanceof JViewport) {
			c.setBackground(fondoBase);
			if (c instanceof JComponent) {
				((JComponent) c).setOpaque(true);
			}
		}

		if (c instanceof JLabel) {
			c.setForeground(textoBase);
			if (c instanceof JComponent) {
				((JComponent) c).setOpaque(false);
			}
		}

		if (c instanceof JButton) {
			JButton b = (JButton) c;
			b.setOpaque(true);
			if (CrashDetectorGUI.esMac()) {
				b.setContentAreaFilled(true);
			}
		}

		if (c instanceof JScrollPane) {
			JScrollPane sp = (JScrollPane) c;
			sp.setOpaque(true);
			sp.setBackground(fondoBase);
			sp.getViewport().setOpaque(true);
			sp.getViewport().setBackground(fondoBase);
		}

		if (c instanceof java.awt.Container) {
			for (Component hijo : ((java.awt.Container) c).getComponents()) {
				aplicarTemaRecursivo(hijo, fondoBase, textoBase);
			}
		}
	}

	/**
	 * Editor multilenguaje HTML robusto para Linux y macOS.
	 */
	public void editarDerechosMultilenguaje(String titulo, Color fondo, Color texto, Color caja, Color borde) {
		if (derechos == null) {
			return;
		}

		JDialog dialogo = new JDialog(this, titulo, true);
		dialogo.setLayout(new BorderLayout(10, 10));

		// En macOS conviene usar dimensiones algo mayores para evitar recortes.
		if (CrashDetectorGUI.esMac()) {
			dialogo.setSize(860, 620);
			dialogo.setMinimumSize(new Dimension(760, 520));
		} else {
			dialogo.setSize(760, 520);
		}

		JPanel panelCampos = new JPanel(new GridBagLayout());
		panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelCampos.setBackground(fondo);
		panelCampos.setOpaque(true);

		java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
		gbc.insets = new java.awt.Insets(6, 6, 6, 6);
		gbc.fill = java.awt.GridBagConstraints.BOTH;
		gbc.anchor = java.awt.GridBagConstraints.NORTHWEST;

		LinkedHashMap<String, String> idiomas = Idioma.mapaParaComboBoxIdiomas();
		Map<String, JTextArea> campos = new LinkedHashMap<String, JTextArea>();

		int fila = 0;

		// Idiomas registrados dinámicamente
		for (Entry<String, String> entry : idiomas.entrySet()) {
			String nombreVisible = entry.getKey();
			String rutaBandera = entry.getValue();
			String codigo = obtenerCodigoIdiomaDinamico(nombreVisible);

			if (codigo == null || codigo.trim().isEmpty()) {
				continue;
			}

			JLabel etiqueta = new JLabel(codigo);
			etiqueta.setFont(fuenteUI(Font.BOLD, 12));
			etiqueta.setForeground(texto);

			try {
				if (rutaBandera != null) {
					File f = Statics.carpeta.resolve(rutaBandera).toFile();
					if (f.exists()) {
						ImageIcon ic = new ImageIcon(f.getAbsolutePath());
						Image img = ic.getImage().getScaledInstance(18, 12, Image.SCALE_SMOOTH);
						etiqueta.setIcon(new ImageIcon(img));
						etiqueta.setIconTextGap(8);
					}
				}
			} catch (Exception ignored) {
			}

			gbc.gridx = 0;
			gbc.gridy = fila;
			gbc.weightx = 0.15;
			gbc.weighty = 0.0;
			panelCampos.add(etiqueta, gbc);

			JTextArea area = crearAreaMultilinea(derechos.getOrDefault(codigo, ""), caja, texto);
			JScrollPane scroll = crearScrollRobusto(area, caja, borde);

			gbc.gridx = 1;
			gbc.weightx = 0.85;
			gbc.weighty = 0.0;
			panelCampos.add(scroll, gbc);

			campos.put(codigo, area);
			fila++;
		}

		// Códigos extra ya presentes en el JSON
		for (Entry<String, String> extra : derechos.entrySet()) {
			String codigo = extra.getKey();

			if (codigo == null || codigo.trim().isEmpty() || campos.containsKey(codigo)) {
				continue;
			}

			JLabel etiqueta = new JLabel(codigo);
			etiqueta.setFont(fuenteUI(Font.BOLD, 12));
			etiqueta.setForeground(texto);

			gbc.gridx = 0;
			gbc.gridy = fila;
			gbc.weightx = 0.15;
			gbc.weighty = 0.0;
			panelCampos.add(etiqueta, gbc);

			JTextArea area = crearAreaMultilinea(extra.getValue(), caja, texto);
			JScrollPane scroll = crearScrollRobusto(area, caja, borde);

			gbc.gridx = 1;
			gbc.weightx = 0.85;
			gbc.weighty = 0.0;
			panelCampos.add(scroll, gbc);

			campos.put(codigo, area);
			fila++;
		}

		// Empujador final para que GridBag no “aplast[e]” filas arriba en algunos LAF.
		JPanel relleno = new JPanel();
		relleno.setOpaque(true);
		relleno.setBackground(fondo);

		gbc.gridx = 0;
		gbc.gridy = fila;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panelCampos.add(relleno, gbc);

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botones.setBackground(fondo);
		botones.setOpaque(true);

		JButton ok = new JButton(MonitorDePID.idioma.aceptar());
		JButton cancel = new JButton(MonitorDePID.idioma.cancelar());

		estilizarBoton(ok, borde, Color.BLACK);
		estilizarBoton(cancel, borde, Color.BLACK);

		ok.addActionListener(e -> {
			derechos.clear();

			for (Entry<String, JTextArea> c : campos.entrySet()) {
				String v = c.getValue().getText().trim();
				if (!v.isEmpty()) {
					derechos.put(c.getKey(), v);
				}
			}

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

		JScrollPane scrollPrincipal = new JScrollPane(panelCampos);
		scrollPrincipal.setBorder(BorderFactory.createEmptyBorder());
		scrollPrincipal.setOpaque(true);
		scrollPrincipal.setBackground(fondo);
		scrollPrincipal.getViewport().setOpaque(true);
		scrollPrincipal.getViewport().setBackground(fondo);

		dialogo.add(scrollPrincipal, BorderLayout.CENTER);
		dialogo.add(botones, BorderLayout.SOUTH);

		aplicarTemaRecursivo(dialogo.getContentPane(), fondo, texto);

		dialogo.setLocationRelativeTo(this);
		dialogo.setVisible(true);
	}
}