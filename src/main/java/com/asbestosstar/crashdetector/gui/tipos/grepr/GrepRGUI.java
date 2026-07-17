package com.asbestosstar.crashdetector.gui.tipos.grepr;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.grepr.BusquedaArchivos;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class GrepRGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	public static java.util.Map<String, java.util.function.Supplier<GrepRGUI>> GUIS = new java.util.HashMap<>();

	// Campos de entrada (public nonfinal)
	public JTextField campoDirectorio;
	public JTextField campoCadena;
	public JCheckBox chkRegex;
	public JCheckBox chkIgnorarMayus;
	public JCheckBox chkBuscarEnComprimidos;
	public JTextArea areaResultados;

	// Colores (public nonfinal) — ¡NO redefinir en subclase!
	public ConfigColor colorFondoVentana;
	public ConfigColor colorPanel;
	public ConfigColor colorTexto;
	public ConfigColor colorBoton;
	public ConfigColor colorBotonTexto;
	public ConfigColor colorCampo;
	public ConfigColor colorBordeDestacado;
	public ConfigColor colorSeleccionTexto;
	public ConfigColor colorTextoSeleccionado;

	// Carga y escala la imagen solicitada
	public JLabel crearImagenEscalada(String ruta, int w, int h) {
		java.io.File f = new java.io.File(ruta);
		if (!f.isAbsolute()) {
			f = new java.io.File(Statics.CARPETA_DE_APP, ruta);
		}
		if (f.exists() && f.isFile()) {
			javax.swing.ImageIcon base = new javax.swing.ImageIcon(f.getAbsolutePath());
			java.awt.Image esc = base.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			return new javax.swing.JLabel(new javax.swing.ImageIcon(esc));
		}

		java.net.URL url = getClass().getClassLoader().getResource(ruta);
		if (url != null) {
			javax.swing.ImageIcon base = new javax.swing.ImageIcon(url);
			java.awt.Image esc = base.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			return new javax.swing.JLabel(new javax.swing.ImageIcon(esc));
		}

		javax.swing.JLabel fallo = new javax.swing.JLabel("imagen no encontrada " + ruta);
		fallo.setForeground(colorTexto.obtener());
		return fallo;
	}

	// Estilo para botones
	public void estilizarBoton(JButton b) {
		b.setBackground(colorBoton.obtener());
		b.setForeground(colorBotonTexto.obtener());
		b.setFocusPainted(false);
		b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBordeDestacado.obtener(), 1),
				BorderFactory.createEmptyBorder(6, 12, 6, 12)));
	}

	// Estilo para campos de texto
	public void estilizarCampo(JTextField f) {
		f.setBackground(colorCampo.obtener());
		f.setForeground(colorTexto.obtener());
		f.setCaretColor(colorTexto.obtener());
		f.setSelectionColor(colorSeleccionTexto.obtener());
		f.setSelectedTextColor(colorTextoSeleccionado.obtener());
		f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBordeDestacado.obtener(), 1),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
	}

	// Estilo para checkboxes y etiquetas
	public void estilizarCheck(JCheckBox c) {
		c.setBackground(colorPanel.obtener());
		c.setForeground(colorTexto.obtener());
		c.setFocusPainted(false);
	}

	public void seleccionarCarpeta() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(Statics.CARPETA_DE_APP);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			campoDirectorio.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	public void iniciarBusqueda() {
		String dirCrudo = campoDirectorio.getText().trim();
		final String directorio = dirCrudo.replace(" ", "").isEmpty() ? Statics.CARPETA_DE_APP.getAbsolutePath() : dirCrudo;

		String cadena = campoCadena.getText().trim();
		boolean usarRegex = chkRegex.isSelected();
		boolean ignorarMayus = chkIgnorarMayus.isSelected();
		boolean buscarEnComprimidos = chkBuscarEnComprimidos.isSelected();

		areaResultados.setText(MonitorDePID.idioma.busquedaEnProgreso() + "\n");

		new SwingWorker<List<String>, Void>() {
			@Override
			protected List<String> doInBackground() {
				return BusquedaArchivos.buscar(directorio, cadena, usarRegex, ignorarMayus, buscarEnComprimidos);
			}

			@Override
			protected void done() {
				try {
					List<String> resultados = get();
					areaResultados.setText("");
					if (resultados.isEmpty()) {
						areaResultados.append(MonitorDePID.idioma.noSeEncontraronResultados());
					} else {
						for (String r : resultados) {
							areaResultados.append(r + "\n");
						}
					}
				} catch (InterruptedException | ExecutionException e) {
					areaResultados.append(MonitorDePID.idioma.errorBusqueda() + e.getMessage());
				}
			}
		}.execute();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.GREPR;
	}

	/**
	 * Método público que las subclases deben sobrescribir. Debe inicializar los
	 * colores y luego llamar a aplicarEstilos().
	 */
	@Override
	public void recargarApariencia() {
		// Este método debe ser implementado por la subclase.
		// El padre no fuerza error — simplemente no hace nada por defecto.
		// Pero la subclase lo sobrescribirá.
	}

	/**
	 * Aplica todos los estilos visuales. Debe llamarse SOLO después de que los
	 * colores hayan sido inicializados.
	 */
	public void aplicarEstilos() {
		setTitle("grepr/fgrepr");
		setLayout(new BorderLayout(10, 10));
		getContentPane().setBackground(colorFondoVentana.obtener());

		JPanel panelEntrada = new JPanel(new java.awt.GridBagLayout());
		panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelEntrada.setBackground(colorPanel.obtener());

		java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
		gbc.insets = new java.awt.Insets(5, 5, 5, 5);
		gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.anchor = java.awt.GridBagConstraints.WEST;

		// ===== Fila 0: carpeta + botón carpeta dentro de borde =====

		JPanel panelCarpeta = new JPanel(new java.awt.GridBagLayout());
		panelCarpeta.setBackground(colorPanel.obtener());
		panelCarpeta.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBordeDestacado.obtener(), 1),
						BorderFactory.createEmptyBorder(8, 8, 8, 8)));

		java.awt.GridBagConstraints gbcCarpeta = new java.awt.GridBagConstraints();
		gbcCarpeta.insets = new java.awt.Insets(0, 0, 0, 8);
		gbcCarpeta.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gbcCarpeta.weightx = 1.0;

		campoDirectorio = new JTextField();
		estilizarCampo(campoDirectorio);

		gbcCarpeta.gridx = 0;
		gbcCarpeta.gridy = 0;
		panelCarpeta.add(campoDirectorio, gbcCarpeta);

		JButton btnExaminar = new JButton(MonitorDePID.idioma.seleccionarCarpeta());
		estilizarBoton(btnExaminar);
		btnExaminar.addActionListener(e -> seleccionarCarpeta());

		gbcCarpeta.gridx = 1;
		gbcCarpeta.gridy = 0;
		gbcCarpeta.weightx = 0.0;
		gbcCarpeta.insets = new java.awt.Insets(0, 8, 0, 0);
		panelCarpeta.add(btnExaminar, gbcCarpeta);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelEntrada.add(panelCarpeta, gbc);

		// ===== Fila 1: etiqueta + caja de búsqueda dentro de borde =====

		JPanel panelCadena = new JPanel(new java.awt.GridBagLayout());
		panelCadena.setBackground(colorPanel.obtener());
		panelCadena.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBordeDestacado.obtener(), 1),
						BorderFactory.createEmptyBorder(8, 8, 8, 8)));

		java.awt.GridBagConstraints gbcCadena = new java.awt.GridBagConstraints();
		gbcCadena.insets = new java.awt.Insets(0, 0, 0, 8);
		gbcCadena.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gbcCadena.anchor = java.awt.GridBagConstraints.WEST;

		JLabel lblCadena = new JLabel(MonitorDePID.idioma.cadenaBusqueda());
		lblCadena.setForeground(colorTexto.obtener());

		gbcCadena.gridx = 0;
		gbcCadena.gridy = 0;
		gbcCadena.weightx = 0.0;
		panelCadena.add(lblCadena, gbcCadena);

		campoCadena = new JTextField();
		estilizarCampo(campoCadena);

		gbcCadena.gridx = 1;
		gbcCadena.gridy = 0;
		gbcCadena.weightx = 1.0;
		gbcCadena.insets = new java.awt.Insets(0, 8, 0, 0);
		panelCadena.add(campoCadena, gbcCadena);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		panelEntrada.add(panelCadena, gbc);

		// ===== Fila 2: checkbox regex y ignorar mayúsculas =====

		chkRegex = new JCheckBox(MonitorDePID.idioma.usarRegex());
		estilizarCheck(chkRegex);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		panelEntrada.add(chkRegex, gbc);

		chkIgnorarMayus = new JCheckBox(MonitorDePID.idioma.ignorarMayusculas());
		estilizarCheck(chkIgnorarMayus);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelEntrada.add(chkIgnorarMayus, gbc);

		// ===== Fila 3: checkbox comprimidos e imagen =====

		chkBuscarEnComprimidos = new JCheckBox(MonitorDePID.idioma.buscarDentroDeComprimidos());
		estilizarCheck(chkBuscarEnComprimidos);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelEntrada.add(chkBuscarEnComprimidos, gbc);

		JLabel lblImagen = crearImagenEscalada(Statics.carpeta.resolve("imagenes/saliormoongrep.png").toString(), 150,
				100);
		lblImagen.setOpaque(true);
		lblImagen.setBackground(colorPanel.obtener());
		lblImagen.setPreferredSize(new java.awt.Dimension(150, 100));

		gbc.gridx = 1;
		gbc.gridy = 3;
		panelEntrada.add(lblImagen, gbc);

		// ===== Fila 4: botón buscar =====

		JButton btnBuscar = new JButton(MonitorDePID.idioma.buscar());
		estilizarBoton(btnBuscar);
		btnBuscar.addActionListener(e -> iniciarBusqueda());

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		panelEntrada.add(btnBuscar, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		panelEntrada.add(new JLabel(), gbc);

		// ===== Resultados =====

		areaResultados = new JTextArea();
		areaResultados.setEditable(false);
		areaResultados.setBackground(colorCampo.obtener());
		areaResultados.setForeground(colorTexto.obtener());
		areaResultados.setCaretColor(colorTexto.obtener());
		areaResultados.setSelectionColor(colorSeleccionTexto.obtener());
		areaResultados.setSelectedTextColor(colorTextoSeleccionado.obtener());

		JScrollPane scroll = new JScrollPane(areaResultados);
		scroll.getViewport().setBackground(colorCampo.obtener());
		scroll.setBackground(colorPanel.obtener());
		scroll.setBorder(BorderFactory.createLineBorder(colorBordeDestacado.obtener(), 1));

		add(panelEntrada, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);

		revalidate();
		repaint();
	}

}