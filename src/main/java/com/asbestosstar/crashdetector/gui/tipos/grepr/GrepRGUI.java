package com.asbestosstar.crashdetector.gui.tipos.grepr;

import java.awt.BorderLayout;
import java.awt.Color;
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
import com.asbestosstar.crashdetector.grepr.BusquedaArchivos;
import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class GrepRGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	public static java.util.Map<String, java.util.function.Supplier<GrepRGUI>> GUIS = new java.util.HashMap<>();

	private JTextField campoDirectorio;
	private JTextField campoCadena;
	private JCheckBox chkRegex;
	private JCheckBox chkIgnorarMayus;
	private JCheckBox chkBuscarEnComprimidos;
	private JTextArea areaResultados;

	// Colores del tema noche
	private final Color colorFondoVentana = new Color(12, 18, 56);
	private final Color colorPanel = new Color(20, 28, 78);
	private final Color colorTexto = Color.WHITE;
	private final Color colorBoton = new Color(220, 64, 92);
	private final Color colorBotonTexto = Color.WHITE;
	private final Color colorCampo = new Color(16, 22, 66);

	public GrepRGUI() {
		this.recargarApariencia();
	}

	// Carga y escala la imagen solicitada
	private JLabel crearImagenEscalada(String ruta, int w, int h) {
		// intenta ruta tal cual en disco
		java.io.File f = new java.io.File(ruta);
		if (!f.isAbsolute()) {
			// si es relativa, pruÃ©bala relativa a user.dir
			f = new java.io.File(System.getProperty("user.dir"), ruta);
		}
		if (f.exists() && f.isFile()) {
			javax.swing.ImageIcon base = new javax.swing.ImageIcon(f.getAbsolutePath());
			java.awt.Image esc = base.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			return new javax.swing.JLabel(new javax.swing.ImageIcon(esc));
		}

		// respaldo por si en el futuro va dentro del jar
		java.net.URL url = getClass().getClassLoader().getResource(ruta);
		if (url != null) {
			javax.swing.ImageIcon base = new javax.swing.ImageIcon(url);
			java.awt.Image esc = base.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
			return new javax.swing.JLabel(new javax.swing.ImageIcon(esc));
		}

		// mensaje si no se encuentra
		javax.swing.JLabel fallo = new javax.swing.JLabel("imagen no encontrada " + ruta);
		fallo.setForeground(colorTexto);
		return fallo;
	}

	// Estilo para botones
	private void estilizarBoton(JButton b) {
		b.setBackground(colorBoton);
		b.setForeground(colorBotonTexto);
		b.setFocusPainted(false);
		b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1),
				BorderFactory.createEmptyBorder(6, 12, 6, 12)));
	}

	// Estilo para campos de texto
	private void estilizarCampo(JTextField f) {
		f.setBackground(colorCampo);
		f.setForeground(colorTexto);
		f.setCaretColor(colorTexto);
		f.setSelectionColor(new Color(255, 215, 0));
		f.setSelectedTextColor(Color.BLACK);
		f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1),
				BorderFactory.createEmptyBorder(4, 8, 4, 8)));
	}

	// Estilo para checkboxes y etiquetas
	private void estilizarCheck(JCheckBox c) {
		c.setBackground(colorPanel);
		c.setForeground(colorTexto);
		c.setFocusPainted(false);
	}

	private void seleccionarCarpeta() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			campoDirectorio.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	private void iniciarBusqueda() {
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
	public void init() {
		this.setVisible(true);
	}

	@Override
	public TipoGUI tipo() {
		// TODO Auto-generated method stub
		return TipoGUI.GREPR;
	}

	@Override
	public void recargarApariencia() {
		// TODO Auto-generated method stub
		setTitle("grepr/fgrepr");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		getContentPane().setBackground(colorFondoVentana);
		setLayout(new BorderLayout(10, 10));

		java.awt.GridBagLayout gbl = new java.awt.GridBagLayout();
		JPanel panelEntrada = new JPanel(gbl);
		panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelEntrada.setBackground(colorPanel);

		// izquierda sin espacio vertical entre controles
		java.awt.GridBagConstraints L = new java.awt.GridBagConstraints();
		L.insets = new java.awt.Insets(0, 0, 0, 8);
		L.fill = java.awt.GridBagConstraints.HORIZONTAL;
		L.weightx = 0.4;
		L.anchor = java.awt.GridBagConstraints.WEST;

		// derecha con mayor peso para ocupar 60 por ciento aprox
		java.awt.GridBagConstraints R = new java.awt.GridBagConstraints();
		R.insets = new java.awt.Insets(5, 8, 5, 0);
		R.fill = java.awt.GridBagConstraints.HORIZONTAL;
		R.weightx = 0.6;
		R.anchor = java.awt.GridBagConstraints.WEST;

		// fila 0 directorio y boton
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

		// fila 1 etiqueta y campo de cadena
		JLabel lblCadena = new JLabel(MonitorDePID.idioma.cadenaBusqueda());
		lblCadena.setForeground(colorTexto);
		L.gridx = 0;
		L.gridy = 1;
		panelEntrada.add(lblCadena, L);

		campoCadena = new JTextField();
		estilizarCampo(campoCadena);
		R.gridx = 1;
		R.gridy = 1;
		panelEntrada.add(campoCadena, R);

		// fila 2 check izquierda y derecha
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

		// fila 3 checkbox de comprimidos a la izquierda
		chkBuscarEnComprimidos = new JCheckBox(MonitorDePID.idioma.buscarDentroDeComprimidos());
		estilizarCheck(chkBuscarEnComprimidos);
		L.gridx = 0;
		L.gridy = 3;
		panelEntrada.add(chkBuscarEnComprimidos, L);

		// fila 3 imagen a la derecha bajo el checkbox derecho
		JLabel lblImagen = crearImagenEscalada(MonitorDePID.carpeta.resolve("imagenes/saliormoongrep.png").toString(),
				150, 100);
		lblImagen.setOpaque(true);
		lblImagen.setBackground(colorPanel);
		lblImagen.setPreferredSize(new java.awt.Dimension(150, 100));
		R.gridx = 1;
		R.gridy = 3;
		panelEntrada.add(lblImagen, R);

		// fila 4 boton buscar a la izquierda sin espacio vertical
		JButton btnBuscar = new JButton(MonitorDePID.idioma.buscar());
		estilizarBoton(btnBuscar);
		btnBuscar.addActionListener(e -> iniciarBusqueda());
		L.gridx = 0;
		L.gridy = 4;
		panelEntrada.add(btnBuscar, L);

		// relleno a la derecha para mantener el 60 por ciento
		R.gridx = 1;
		R.gridy = 4;
		R.weightx = 0.6;
		panelEntrada.add(new JLabel(), R);

		areaResultados = new JTextArea();
		areaResultados.setEditable(false);
		areaResultados.setBackground(colorCampo);
		areaResultados.setForeground(colorTexto);
		areaResultados.setCaretColor(colorTexto);
		areaResultados.setSelectionColor(new Color(255, 215, 0));
		areaResultados.setSelectedTextColor(Color.BLACK);

		JScrollPane scroll = new JScrollPane(areaResultados);
		scroll.getViewport().setBackground(colorCampo);
		scroll.setBackground(colorPanel);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));

		add(panelEntrada, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}

}
