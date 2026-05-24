package com.asbestosstar.crashdetector.gui.tipos.cdpaste;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.api_sito_registro.CrashDetectorPasteAPI;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * GUI estilo Wilhelmia Frost para ver historial CDPaste y eliminar registros.
 */
public class CDPasteHistorialGUIWilhelmiaFrost extends CDPasteHistorialGUI {
	private static final long serialVersionUID = 1L;

	public static final String ID = "wilhelmia_frost_cdpaste";
	public static final File imagen = Statics.carpeta.resolve("imagenes/wilhelmia_frost.png").toFile();

	private JTextField campoEndpoint;
	private JTextField campoSlug;
	private JTextField campoUrl;
	private JTextField campoRaw;
	private JButton botonEliminar;
	private JTable tabla;
	private DefaultTableModel modelo;
	private JLabel imagenLabel;
	private JPanel panelPrincipal;

	private ConfigColor colorFondo;
	private ConfigColor colorTexto;
	private ConfigColor colorBoton;
	private ConfigColor colorTabla;
	private ConfigColor colorBorde;
	private ConfigColor colorCampo;

	public CDPasteHistorialGUIWilhelmiaFrost() {
		colorFondo = ConfigColor.de("tema.wilhelmia_frost_cdpaste.color.fondo", new Color(222, 232, 246));
		colorTexto = ConfigColor.de("tema.wilhelmia_frost_cdpaste.color.texto", new Color(42, 49, 70));
		colorBoton = ConfigColor.de("tema.wilhelmia_frost_cdpaste.color.boton", new Color(139, 166, 210));
		colorTabla = ConfigColor.de("tema.wilhelmia_frost_cdpaste.color.tabla", new Color(248, 250, 255));
		colorBorde = ConfigColor.de("tema.wilhelmia_frost_cdpaste.color.borde", new Color(107, 126, 164));
		colorCampo = ConfigColor.de("tema.wilhelmia_frost_cdpaste.color.campo", new Color(255, 255, 255));
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.historialCDPaste());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(false);
		setResizable(true);
		setSize(950, 650);

		cargarDatos();

		panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelPrincipal.add(crearPanelSuperior(), BorderLayout.NORTH);
		panelPrincipal.add(crearPanelCentro(), BorderLayout.CENTER);

		getContentPane().add(panelPrincipal, BorderLayout.CENTER);

		cargarTabla();
		agregarListeners();

		recargarApariencia();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel crearPanelSuperior() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));

		imagenLabel = new JLabel();
		imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imagenLabel.setPreferredSize(new Dimension(150, 150));
		cargarImagen();

		JPanel panelCampos = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(4, 4, 4, 4);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;

		campoEndpoint = new JTextField();
		campoSlug = new JTextField();
		campoUrl = new JTextField();
		campoRaw = new JTextField();

		campoUrl.setEditable(false);
		campoRaw.setEditable(false);

		int y = 0;
		agregarFila(panelCampos, gbc, y++, MonitorDePID.idioma.endpoint(), campoEndpoint);
		agregarFila(panelCampos, gbc, y++, MonitorDePID.idioma.slug(), campoSlug);
		agregarFila(panelCampos, gbc, y++, MonitorDePID.idioma.enlace(), campoUrl);
		agregarFila(panelCampos, gbc, y++, MonitorDePID.idioma.enlaceRaw(), campoRaw);

		botonEliminar = new JButton(MonitorDePID.idioma.eliminarRegistroCDPaste());

		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBoton.add(botonEliminar);

		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		panelCampos.add(panelBoton, gbc);

		panel.add(imagenLabel, BorderLayout.WEST);
		panel.add(panelCampos, BorderLayout.CENTER);

		return panel;
	}

	private void agregarFila(JPanel panel, GridBagConstraints gbc, int y, String texto, JTextField campo) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Segoe UI", Font.BOLD, 13));

		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.weightx = 0.0;
		panel.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.weightx = 1.0;
		panel.add(campo, gbc);
	}

	private JPanel crearPanelCentro() {
		JPanel panel = new JPanel(new BorderLayout());

		modelo = new DefaultTableModel(
				new Object[] { MonitorDePID.idioma.endpoint(), MonitorDePID.idioma.slug(), MonitorDePID.idioma.enlace(),
						MonitorDePID.idioma.enlaceRaw(), MonitorDePID.idioma.lineas(), MonitorDePID.idioma.tamano() },
				0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabla = new JTable(modelo);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowHeight(26);
		tabla.setFillsViewportHeight(true);

		panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
		return panel;
	}

	private void cargarImagen() {
		try {
			if (imagen.exists()) {
				ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
				Image img = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
				imagenLabel.setIcon(new ImageIcon(img));
			} else {
				imagenLabel.setText(MonitorDePID.idioma.imagenNoEncontrada());
			}
		} catch (Exception e) {
			imagenLabel.setText(MonitorDePID.idioma.errorCargandoImagen());
		}
	}

	private void cargarTabla() {
		modelo.setRowCount(0);

		if (historial == null || !historial.esArreglo())
			return;

		for (int i = 0; i < historial.tamano(); i++) {
			Nodo n = historial.en(i);
			if (n == null || !n.esObjeto())
				continue;

			modelo.addRow(new Object[] { cadena(n, "endpoint"), cadena(n, "id"), cadena(n, "url"), cadena(n, "raw"),
					cadena(n, "lines"), cadena(n, "size") });
		}
	}

	private String cadena(Nodo n, String key) {
		try {
			Nodo v = n.obtener(key);
			return v == null ? "" : v.comoCadena();
		} catch (Exception e) {
			return "";
		}
	}

	private void agregarListeners() {
		tabla.getSelectionModel().addListSelectionListener(e -> {
			if (e.getValueIsAdjusting())
				return;

			int fila = tabla.getSelectedRow();
			if (fila < 0)
				return;

			campoEndpoint.setText(valor(fila, 0));
			campoSlug.setText(valor(fila, 1));
			campoUrl.setText(valor(fila, 2));
			campoRaw.setText(valor(fila, 3));
		});

		botonEliminar.addActionListener(e -> eliminarSeleccionado());
	}

	private String valor(int fila, int col) {
		Object v = modelo.getValueAt(fila, col);
		return v == null ? "" : v.toString();
	}

	private void eliminarSeleccionado() {
		String endpoint = campoEndpoint.getText();
		String slug = campoSlug.getText();

		if (slug == null || slug.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.faltanDatosParaEliminarCDPaste(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
			return;
		}

		int confirmar = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.confirmarEliminarCDPaste(),
				MonitorDePID.idioma.confirmar(), JOptionPane.YES_NO_OPTION);

		if (confirmar != JOptionPane.YES_OPTION)
			return;

		try {
			CrashDetectorPasteAPI api = new CrashDetectorPasteAPI();
			api.eliminarRegistro(endpoint, slug);

			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.registroEliminadoCDPaste(),
					MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
		} catch (ErrorConPublicar ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), MonitorDePID.idioma.error(),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void recargarApariencia() {
		Color fondo = colorFondo.obtener();

		getContentPane().setBackground(fondo);
		panelPrincipal.setBackground(fondo);

		estilizarCampo(campoEndpoint);
		estilizarCampo(campoSlug);
		estilizarCampo(campoUrl);
		estilizarCampo(campoRaw);

		tabla.setBackground(colorTabla.obtener());
		tabla.setForeground(colorTexto.obtener());
		tabla.setGridColor(colorBorde.obtener());

		estilizarBoton(botonEliminar);

		revalidate();
		repaint();
	}

	private void estilizarCampo(JTextField campo) {
		if (campo == null)
			return;

		campo.setBackground(colorCampo.obtener());
		campo.setForeground(colorTexto.obtener());
		campo.setBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1));
		campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
	}

	private void estilizarBoton(JButton btn) {
		if (btn == null)
			return;

		if (!CrashDetectorGUI.esMac()) {
			btn.setBackground(colorBoton.obtener());
			btn.setForeground(Color.WHITE);
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 2));
			btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		}
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		ret.add(colorFondo);

		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		ret.add(colorTexto);

		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		ret.add(colorBoton);

		colorTabla.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeListas());
		ret.add(colorTabla);

		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		ret.add(colorBorde);

		colorCampo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCampoTexto());
		ret.add(colorCampo);

		return ret;
	}
}