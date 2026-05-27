package com.asbestosstar.crashdetector.gui.tipos.editorgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigDouble;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.editor.BotonRedondeadoTL;

public class CDSkinCape extends EditorGUI {

	public static String ID = "cdskincape";

	// Colores base (ajustados a TL Skin / Cape)
	public static final Color TL_FONDO_IZQ = new Color(245, 249, 253);
	public static final Color TL_TEXTO = new Color(40, 40, 40);
	public static final Color TL_BOTON_PRIMARIO = new Color(76, 175, 80);
	public static final Color TL_BOTON_SECUNDARIO = new Color(220, 228, 236);
	public static final Color TL_BORDE = new Color(190, 200, 210);

	// Colores TL configurables
	public ConfigColor tlBlanco = ConfigColor.de("tema.tl.color.blanco", Color.WHITE);

	public ConfigColor tlAzul = ConfigColor.de("tema.tl.color.azul", new Color(66, 133, 244));

	public ConfigColor tlRojo = ConfigColor.de("tema.tl.color.rojo", new Color(220, 53, 69));

	public ConfigColor tlFondoIzquierdo = ConfigColor.de("tema.tl.color.fondo_izquierdo", new Color(245, 248, 251));

	public ConfigColor colorFondo = ConfigColor.de("tema.cdskincape.editor.color.fondo", TL_FONDO_IZQ);
	public ConfigColor colorTexto = ConfigColor.de("tema.cdskincape.editor.color.texto", TL_TEXTO);
	public ConfigColor colorBoton = ConfigColor.de("tema.cdskincape.editor.color.boton", TL_BOTON_PRIMARIO);
	public ConfigColor colorCajaTexto = ConfigColor.de("tema.cdskincape.editor.color.caja_texto", Color.WHITE);
	public ConfigColor colorBorde = ConfigColor.de("tema.cdskincape.editor.color.borde", TL_BORDE);

	public JPanel panelConfiguracionGUI;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		inicializarInterfaz();
		setVisible(true);
	}

	public void inicializarInterfaz() {

		setTitle("CDSkinCape");
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1000, 650));
		setMinimumSize(new Dimension(900, 550));

		// ================= IZQUIERDA =================

		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
		panelIzquierdo.setPreferredSize(new Dimension(260, 0));
		panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		panelIzquierdo.setBackground(new Color(245, 248, 251)); // TL-like fondo

		panelEdicion = new JPanel(new BorderLayout());
		panelEdicion.setPreferredSize(new Dimension(220, 260));
		panelEdicion.setMaximumSize(new Dimension(220, 260));
		panelEdicion.setBorder(BorderFactory.createLineBorder(colorBorde.obtener()));
		panelEdicion.setBackground(Color.WHITE);

		panelIzquierdo.add(panelEdicion);
		panelIzquierdo.add(Box.createVerticalStrut(12));

		JLabel lblTipo = new JLabel(MonitorDePID.idioma.tipo());
		panelIzquierdo.add(lblTipo);

		comboTipoGUI = new JComboBox<>();
		comboTipoGUI.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		comboTipoGUI.addActionListener(e -> actualizarComboGUI());
		panelIzquierdo.add(comboTipoGUI);

		panelIzquierdo.add(Box.createVerticalStrut(8));

		JLabel lblGui = new JLabel(MonitorDePID.idioma.gui());
		panelIzquierdo.add(lblGui);

		comboGUI = new JComboBox<>();
		comboGUI.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
		comboGUI.addActionListener(e -> cargarGUISeleccionada());
		panelIzquierdo.add(comboGUI);

		panelIzquierdo.add(Box.createVerticalStrut(14));

		JButton botonMostrar = new BotonRedondeadoTL(MonitorDePID.idioma.botonMostrarGUI(), tlBlanco.obtener(),
				tlRojo.obtener(), tlRojo.obtener(), false);

		botonMostrar.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonMostrar.addActionListener(e -> mostrarGUI());
		panelIzquierdo.add(botonMostrar);

		panelIzquierdo.add(Box.createVerticalStrut(8));

		JButton guardarTodo = new BotonRedondeadoTL(MonitorDePID.idioma.botonGuardarTodo(), tlBlanco.obtener(),
				tlAzul.obtener(), tlAzul.obtener(), false);

		guardarTodo.setAlignmentX(Component.CENTER_ALIGNMENT);
		guardarTodo.addActionListener(e -> guardarTodo());
		panelIzquierdo.add(guardarTodo);

		panelIzquierdo.add(Box.createVerticalStrut(8));

		JButton resetearTodo = new BotonRedondeadoTL(MonitorDePID.idioma.botonRestablecerTodo(), tlBlanco.obtener(),
				tlAzul.obtener(), tlAzul.obtener(), false);

		resetearTodo.setAlignmentX(Component.CENTER_ALIGNMENT);
		resetearTodo.addActionListener(e -> resetearTodo());
		panelIzquierdo.add(resetearTodo);

		add(panelIzquierdo, BorderLayout.WEST);

		// ================= DERECHA =================

		panelConfiguracionGUI = new JPanel(new BorderLayout());
		panelConfiguracionGUI.setBorder(BorderFactory.createTitledBorder("Configuración"));
		panelConfiguracionGUI.setBackground(Color.WHITE);
		add(panelConfiguracionGUI, BorderLayout.CENTER);

		for (TipoGUI<?> tipo : TipoGUI.TIPOS_DE_GUI) {
			comboTipoGUI.addItem(tipo.id());
		}
	}

	public Object guardarTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	public JButton crearBotonTL(String texto, Color fondo, Color textoColor) {
		JButton b = new JButton(texto);

		b.setBackground(fondo);
		b.setForeground(textoColor);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setOpaque(true);

		b.setPreferredSize(new Dimension(180, 34));
		b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));

		b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(fondo.darker(), 1, true),
				BorderFactory.createEmptyBorder(6, 18, 6, 18)));

		return b;
	}

	@Override
	public void cargarGUISeleccionada() {
		super.cargarGUISeleccionada();
		actualizarPanelConfiguracionGUI();
	}

	public void actualizarPanelConfiguracionGUI() {

		panelConfiguracionGUI.removeAll();

		List<ElementoConfig> elementos = obtenerElementosConfigSeleccionados();

		if (elementos != null && !elementos.isEmpty()) {

			JPanel contenido = new JPanel();
			contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
			contenido.setBackground(Color.WHITE);

			for (ElementoConfig<?> e : elementos) {

				JPanel fila = new JPanel(new BorderLayout(8, 0));
				fila.setBackground(Color.WHITE);

				JLabel nombre = new JLabel(e.obtenerNombreParaMostrar());
				fila.add(nombre, BorderLayout.WEST);

				Component editor = crearComponenteEditable(e);
				if (editor != null)
					fila.add(editor, BorderLayout.CENTER);

				JPanel acciones = new JPanel();
				acciones.setBackground(Color.WHITE);

				JButton reset = new BotonRedondeadoTL(MonitorDePID.idioma.restablecer(), tlAzul.obtener(),
						tlAzul.obtener(), Color.WHITE, true // sólido
				);
				reset.addActionListener(x -> {
					e.resetearAPorDefecto();
					actualizarPanelConfiguracionGUI();
				});
				acciones.add(reset);

				fila.add(acciones, BorderLayout.EAST);

				contenido.add(fila);
				contenido.add(Box.createVerticalStrut(6));
			}

			panelConfiguracionGUI.add(new JScrollPane(contenido), BorderLayout.CENTER);

		} else {
			panelConfiguracionGUI.add(new JLabel(MonitorDePID.idioma.sinOpciones()), BorderLayout.CENTER);
		}

		panelConfiguracionGUI.revalidate();
		panelConfiguracionGUI.repaint();
	}

	public void resetearTodo() {
		if (JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.confirmacionReEstablarTodos(),
				MonitorDePID.idioma.aceptar(), JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
			return;

		List<ElementoConfig> elementos = obtenerElementosConfigSeleccionados();
		if (elementos == null)
			return;

		for (ElementoConfig<?> e : elementos) {
			e.resetearAPorDefecto();
		}
		actualizarPanelConfiguracionGUI();
	}

	public Component crearComponenteEditable(ElementoConfig<?> elemento) {

		if (elemento instanceof ConfigBoolean) {
			JCheckBox cb = new JCheckBox();
			cb.setSelected((Boolean) elemento.obtener());
			cb.addActionListener(e -> ((ConfigBoolean) elemento).escribir(cb.isSelected()));
			return cb;
		}

		if (elemento instanceof ConfigColor) {

			JPanel p = new JPanel(new BorderLayout(6, 0));

			JTextField tf = new JTextField(
					"#" + Integer.toHexString(((ConfigColor) elemento).obtener().getRGB()).substring(2));

			JPanel preview = new JPanel();
			preview.setPreferredSize(new Dimension(18, 18));
			preview.setBackground(((ConfigColor) elemento).obtener());
			preview.setBorder(BorderFactory.createLineBorder(Color.GRAY));

			preview.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Color nuevo = javax.swing.JColorChooser.showDialog(CDSkinCape.this,
							MonitorDePID.idioma.seleccionaColor(), ((ConfigColor) elemento).obtener());
					if (nuevo != null) {
						((ConfigColor) elemento).escribir(nuevo);
						preview.setBackground(nuevo);
						tf.setText("#" + Integer.toHexString(nuevo.getRGB()).substring(2));
					}
				}
			});

			tf.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
				public void insertUpdate(javax.swing.event.DocumentEvent e) {
					aplicar();
				}

				public void removeUpdate(javax.swing.event.DocumentEvent e) {
					aplicar();
				}

				public void changedUpdate(javax.swing.event.DocumentEvent e) {
					aplicar();
				}

				private void aplicar() {
					try {
						Color c = Color.decode(tf.getText());
						((ConfigColor) elemento).escribir(c);
						preview.setBackground(c);
					} catch (Exception ignored) {
					}
				}
			});

			p.add(preview, BorderLayout.WEST);
			p.add(tf, BorderLayout.CENTER);
			return p;
		}

		if (elemento instanceof ConfigDouble) {
			JTextField tf = new JTextField(elemento.obtener().toString());
			tf.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
				public void insertUpdate(javax.swing.event.DocumentEvent e) {
					aplicar();
				}

				public void removeUpdate(javax.swing.event.DocumentEvent e) {
					aplicar();
				}

				public void changedUpdate(javax.swing.event.DocumentEvent e) {
					aplicar();
				}

				private void aplicar() {
					try {
						((ConfigDouble) elemento).escribir(Double.parseDouble(tf.getText()));
					} catch (Exception ignored) {
					}
				}
			});
			return tf;
		}

		if (elemento instanceof ConfigString) {
			JTextField tf = new JTextField((String) elemento.obtener());
			tf.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
				public void insertUpdate(javax.swing.event.DocumentEvent e) {
					((ConfigString) elemento).escribir(tf.getText());
				}

				public void removeUpdate(javax.swing.event.DocumentEvent e) {
					((ConfigString) elemento).escribir(tf.getText());
				}

				public void changedUpdate(javax.swing.event.DocumentEvent e) {
					((ConfigString) elemento).escribir(tf.getText());
				}
			});
			return tf;
		}

		return new JLabel(String.valueOf(elemento.obtener()));
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {

		List<ElementoConfig> l = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());

		l.add(colorFondo);
		l.add(colorTexto);
		l.add(colorBoton);
		l.add(colorCajaTexto);
		l.add(colorBorde);

		return l;
	}

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	public void aplicarApariencia() {
		getContentPane().setBackground(colorFondo.obtener());
		revalidate();
		repaint();
	}

	@Override
	public void editarElemento(ElementoConfig<?> elemento) {
		// No usado: todos los editores son inline
	}
}
