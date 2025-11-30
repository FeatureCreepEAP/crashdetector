package com.asbestosstar.crashdetector.gui.tipos.grepr;

import java.awt.BorderLayout;
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
			f = new java.io.File(System.getProperty("user.dir"), ruta);
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
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			campoDirectorio.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	public void iniciarBusqueda() {
		String dirCrudo = campoDirectorio.getText().trim();
		final String directorio = dirCrudo.replace(" ", "").isEmpty() ? System.getProperty("user.dir") : dirCrudo;

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

		java.awt.GridBagLayout gbl = new java.awt.GridBagLayout();
		JPanel panelEntrada = new JPanel(gbl);
		panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelEntrada.setBackground(colorPanel.obtener());

		java.awt.GridBagConstraints L = new java.awt.GridBagConstraints();
		L.insets = new java.awt.Insets(0, 0, 0, 8);
		L.fill = java.awt.GridBagConstraints.HORIZONTAL;
		L.weightx = 0.4;
		L.anchor = java.awt.GridBagConstraints.WEST;

		java.awt.GridBagConstraints R = new java.awt.GridBagConstraints();
		R.insets = new java.awt.Insets(5, 8, 5, 0);
		R.fill = java.awt.GridBagConstraints.HORIZONTAL;
		R.weightx = 0.6;
		R.anchor = java.awt.GridBagConstraints.WEST;

		// fila 0: directorio y botón examinar
		campoDirectorio = new JTextField();
		estilizarCampo(campoDirectorio);
		L.gridx = 0;
		L.gridy = 0;
		panelEntrada.add(campoDirectorio, L);

		JButton btnExaminar = new JButton(MonitorDePID.idioma.seleccionarCarpeta());
		estilizarBoton(btnExaminar);
		btnExaminar.addActionListener(e -> seleccionarCarpeta());
		R.gridx = 1;
		R.gridy = 0;
		panelEntrada.add(btnExaminar, R);

		// fila 1: etiqueta y campo de cadena
		JLabel lblCadena = new JLabel(MonitorDePID.idioma.cadenaBusqueda());
		lblCadena.setForeground(colorTexto.obtener());
		L.gridx = 0;
		L.gridy = 1;
		panelEntrada.add(lblCadena, L);

		campoCadena = new JTextField();
		estilizarCampo(campoCadena);
		R.gridx = 1;
		R.gridy = 1;
		panelEntrada.add(campoCadena, R);

		// fila 2: checkbox regex y ignorar mayúsculas
		chkRegex = new JCheckBox(MonitorDePID.idioma.usarRegex());
		estilizarCheck(chkRegex);
		L.gridx = 0;
		L.gridy = 2;
		panelEntrada.add(chkRegex, L);

		chkIgnorarMayus = new JCheckBox(MonitorDePID.idioma.ignorarMayusculas());
		estilizarCheck(chkIgnorarMayus);
		R.gridx = 1;
		R.gridy = 2;
		panelEntrada.add(chkIgnorarMayus, R);

		// fila 3: checkbox comprimidos y imagen
		chkBuscarEnComprimidos = new JCheckBox(MonitorDePID.idioma.buscarDentroDeComprimidos());
		estilizarCheck(chkBuscarEnComprimidos);
		L.gridx = 0;
		L.gridy = 3;
		panelEntrada.add(chkBuscarEnComprimidos, L);

		JLabel lblImagen = crearImagenEscalada(Statics.carpeta.resolve("imagenes/saliormoongrep.png").toString(), 150,
				100);
		lblImagen.setOpaque(true);
		lblImagen.setBackground(colorPanel.obtener());
		lblImagen.setPreferredSize(new java.awt.Dimension(150, 100));
		R.gridx = 1;
		R.gridy = 3;
		panelEntrada.add(lblImagen, R);

		// fila 4: botón buscar
		JButton btnBuscar = new JButton(MonitorDePID.idioma.buscar());
		estilizarBoton(btnBuscar);
		btnBuscar.addActionListener(e -> iniciarBusqueda());
		L.gridx = 0;
		L.gridy = 4;
		panelEntrada.add(btnBuscar, L);

		R.gridx = 1;
		R.gridy = 4;
		R.weightx = 0.6;
		panelEntrada.add(new JLabel(), R);

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