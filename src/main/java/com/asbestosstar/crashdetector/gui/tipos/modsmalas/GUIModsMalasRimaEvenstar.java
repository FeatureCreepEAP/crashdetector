package com.asbestosstar.crashdetector.gui.tipos.modsmalas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class GUIModsMalasRimaEvenstar extends GUIModsMalas {

	private static final long serialVersionUID = 1L;
	public static final String ID = "rima_evenstar";
	public static File imagen = Statics.carpeta.resolve("imagenes/rima_evenstar.png").toFile(); // 194x268 pix

	// UI
	private JTable tabla;
	private DefaultTableModel modelo;
	private JButton botonAgregar;
	private JButton botonQuitar;
	private JButton botonEditarRazones;
	private JButton botonGuardar;
	private JButton botonCancelar;

	// Colores
	private ConfigColor colorFondo = ConfigColor.de("tema.rima_evenstar.fondo", new Color(46, 32, 64));
	private ConfigColor colorTexto = ConfigColor.de("tema.rima_evenstar.texto", new Color(240, 230, 255));
	private ConfigColor colorBoton = ConfigColor.de("tema.rima_evenstar.boton", new Color(168, 90, 200));
	private ConfigColor colorTabla = ConfigColor.de("tema.rima_evenstar.tabla", new Color(33, 23, 48));
	private ConfigColor colorBorde = ConfigColor.de("tema.rima_evenstar.borde", new Color(110, 84, 140));

	@Override
	public void init() {
		// Paleta base (puedes ajustarla luego con el panel de configs)

		setTitle(MonitorDePID.idioma.modsNoRecomendados());
		setSize(900, 650);
		setModal(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		cargarDatos();

		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(220, 0));
		panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		forzarFondoEnPanel(panelIzquierdo, colorFondo.obtener());

		JLabel imagenLabel = new JLabel();
		imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imagenLabel.setVerticalAlignment(SwingConstants.TOP);
		imagenLabel.setOpaque(true);
		imagenLabel.setBackground(colorFondo.obtener());

		cargarImagenEnLabel(imagenLabel);

		JLabel aviso = new JLabel("<html><div style='width:160px; text-align:center;'>"
				+ MonitorDePID.idioma.modsNoRecomendadosAviso() + "</div></html>");
		aviso.setForeground(colorTexto.obtener());
		aviso.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		aviso.setHorizontalAlignment(SwingConstants.CENTER);

		panelIzquierdo.add(imagenLabel, BorderLayout.NORTH);
		panelIzquierdo.add(aviso, BorderLayout.SOUTH);

		add(panelIzquierdo, BorderLayout.WEST);

		// ===== PANEL DERECHO (TITULO + TABLA + BOTONES) =====
		JPanel panelDerecho = new JPanel(new BorderLayout(6, 6));
		panelDerecho.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		forzarFondoEnPanel(panelDerecho, colorFondo.obtener());

		// Título
		JLabel titulo = new JLabel(MonitorDePID.idioma.modsNoRecomendados());
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titulo.setForeground(colorTexto.obtener());

		JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		forzarFondoEnPanel(panelTitulo, colorFondo.obtener());
		panelTitulo.add(titulo);

		panelDerecho.add(panelTitulo, BorderLayout.NORTH);

		// Tabla (con columna "Override normal")
		modelo = new DefaultTableModel(new Object[] { MonitorDePID.idioma.modId(), MonitorDePID.idioma.rutaMod(),
				MonitorDePID.idioma.anularNormal(), MonitorDePID.idioma.razones() }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		tabla = new JTable(modelo);
		tabla.setRowHeight(24);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(tabla);
		panelDerecho.add(scroll, BorderLayout.CENTER);

		// Botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
		forzarFondoEnPanel(panelBotones, colorFondo.obtener());

		botonAgregar = new JButton(MonitorDePID.idioma.agregarMod());
		botonQuitar = new JButton(MonitorDePID.idioma.quitarMod());
		botonEditarRazones = new JButton(MonitorDePID.idioma.editarRazones());
		botonGuardar = new JButton(MonitorDePID.idioma.guardarCambios());
		botonCancelar = new JButton(MonitorDePID.idioma.cancelar());

		panelBotones.add(botonAgregar);
		panelBotones.add(botonQuitar);
		panelBotones.add(botonEditarRazones);
		panelBotones.add(botonGuardar);
		panelBotones.add(botonCancelar);

		panelDerecho.add(panelBotones, BorderLayout.SOUTH);

		add(panelDerecho, BorderLayout.CENTER);

		// Listeners
		botonAgregar.addActionListener(e -> agregarMod());
		botonQuitar.addActionListener(e -> quitarMod());
		botonEditarRazones.addActionListener(e -> editarRazonesMultilingueSeleccion());
		botonGuardar.addActionListener(e -> {
			guardarDatos();
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.cambiosGuardadosExitosamente(),
					MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
		});
		botonCancelar.addActionListener(e -> dispose());

		recargarTabla();
		recargarApariencia();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void cargarImagenEnLabel(JLabel imagenLabel) {
		try {
			if (imagen != null && imagen.exists()) {
				ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
				Image img = icon.getImage();

				int newWidth = 200;
				int newHeight = (int) ((double) img.getHeight(null) / img.getWidth(null) * newWidth);
				img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

				imagenLabel.setIcon(new ImageIcon(img));
			} else {
				imagenLabel.setText(MonitorDePID.idioma.imagenNoEncontrada());
				imagenLabel.setForeground(colorTexto.obtener());
			}
		} catch (Exception e) {
			imagenLabel.setText(MonitorDePID.idioma.errorCargandoImagen());
			imagenLabel.setForeground(colorTexto.obtener());
		}
	}

	// =====================
	// LÓGICA
	// =====================

	private void agregarMod() {
		JTextField campoModid = new JTextField();
		JTextField campoRuta = new JTextField();

		// Checkbox para "anularNormal" (en JSON: abrir_cd_si_coincide)
		JCheckBox checkOverride = new JCheckBox(MonitorDePID.idioma.anularNormalDescripcion());
		checkOverride.setOpaque(false);
		// checkOverride.setForeground(colorTexto.obtener());
		checkOverride.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		Object[] msg = { MonitorDePID.idioma.modId(), campoModid, MonitorDePID.idioma.rutaMod(), campoRuta, " ",
				checkOverride };

		int res = JOptionPane.showConfirmDialog(this, msg, MonitorDePID.idioma.agregarMod(),
				JOptionPane.OK_CANCEL_OPTION);

		if (res != JOptionPane.OK_OPTION)
			return;

		String modid = campoModid.getText() != null ? campoModid.getText().trim() : "";
		String ruta = campoRuta.getText() != null ? campoRuta.getText().trim() : "";

		if (modid.isEmpty() && ruta.isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorDebeIndicarMod(), MonitorDePID.idioma.error(),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		EntradaMod e = new EntradaMod();
		e.modid = modid.isEmpty() ? null : modid;
		e.ruta = ruta.isEmpty() ? null : ruta;
		e.abrirCD = checkOverride.isSelected();

		mods.add(e);
		recargarTabla();
	}

	private void quitarMod() {
		int fila = tabla.getSelectedRow();
		if (fila < 0)
			return;

		mods.remove(fila);
		recargarTabla();
	}

	private void editarRazonesMultilingueSeleccion() {
		int fila = tabla.getSelectedRow();
		if (fila < 0)
			return;

		EntradaMod e = mods.get(fila);
		if (e == null)
			return;

		String ref = (e.modid != null && !e.modid.trim().isEmpty()) ? e.modid : (e.ruta != null ? e.ruta : "");
		String titulo = MonitorDePID.idioma.editarRazonesPara(ref);

		// Diálogo multilingüe (igual idea que SylentBell)
		editarRazonesMultilingue(titulo, e.razones, colorFondo.obtener(), colorTexto.obtener(), colorTabla.obtener(),
				colorBorde.obtener(), colorBoton.obtener());

		recargarTabla();
	}

	private void recargarTabla() {
		modelo.setRowCount(0);

		for (EntradaMod e : mods) {
			String modid = (e.modid == null) ? "" : e.modid;
			String ruta = (e.ruta == null) ? "" : e.ruta;

			String overrideTxt = e.abrirCD ? "true" : "false";
			String razonesTxt = resumenRazones(e.razones);

			modelo.addRow(new Object[] { modid, ruta, overrideTxt, razonesTxt });
		}

		boolean hay = modelo.getRowCount() > 0;
		if (botonQuitar != null)
			botonQuitar.setEnabled(hay);
		if (botonEditarRazones != null)
			botonEditarRazones.setEnabled(hay);
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		getContentPane().setBackground(colorFondo.obtener());

		if (tabla != null) {
			tabla.setBackground(colorTabla.obtener());
			tabla.setForeground(colorTexto.obtener());
			tabla.setGridColor(colorBorde.obtener());
			tabla.setSelectionBackground(colorBoton.obtener());
			tabla.setSelectionForeground(Color.WHITE);
			tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		}

		JButton[] botones = { botonAgregar, botonQuitar, botonEditarRazones, botonGuardar, botonCancelar };
		for (JButton b : botones) {
			estilizarBotonSimple(b, colorBoton.obtener(), Color.WHITE, colorBorde.obtener());
			if (b != null)
				b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
		}

		revalidate();
		repaint();
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

		return ret;
	}
}
