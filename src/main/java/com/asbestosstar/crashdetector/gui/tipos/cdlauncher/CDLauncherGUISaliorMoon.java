package com.asbestosstar.crashdetector.gui.tipos.cdlauncher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI;
import com.asbestosstar.crashdetector.lanzer.ConfigCDLauncher;

/**
 * Implementación visual de CDLauncher estilo SaliorMoon.
 */
public class CDLauncherGUISaliorMoon extends CDLauncherGUI {

	public static final String ID = "cdlauncher_saliormoon";

	/** Fondo principal claro (blanco/gris muy claro) */
	public ConfigColor colorFondo = ConfigColor.de("gui.cdlauncher.color.fondo", new Color(245, 245, 245) // fondo claro
																											// tipo
																											// diálogo
	);

	/** Texto principal oscuro */
	public ConfigColor colorTexto = ConfigColor.de("gui.cdlauncher.color.texto", new Color(40, 40, 40) // gris oscuro,
																										// no negro puro
	);

	/** Botón principal (verde tipo “OK”) */
	public ConfigColor colorBoton = ConfigColor.de("gui.cdlauncher.color.boton", new Color(120, 200, 60) // verde
																											// CDLauncher
	);

	private final Map<String, JCheckBox> checkboxes = new HashMap<>();

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void construir(PrincipalGUI principal) {
		CrashDetectorLogger.log("CDLauncher Config comenzar");

		inicializarDialogo(principal);
		CrashDetectorLogger.log("init dialogo");

		cambiarAparienciaDeGUIPrincipal(principal);

		CrashDetectorLogger.log("cambiar principal");

		setSize(640, 420);
		setLayout(new BorderLayout());

		JPanel raiz = new JPanel(new BorderLayout(10, 10));
		raiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		raiz.setBackground(colorFondo.obtener());

		CrashDetectorLogger.log("raiz");

		/* Imagen izquierda */
		JLabel imagen = new JLabel();
		ImageIcon icon = new ImageIcon(Statics.carpeta.resolve("imagenes/salior_moon_skin.png").toString());
		Image esc = icon.getImage().getScaledInstance(90, -1, Image.SCALE_SMOOTH);
		imagen.setIcon(new ImageIcon(esc));
		raiz.add(imagen, BorderLayout.WEST);

		CrashDetectorLogger.log("imiegen");

		/* Panel central */
		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setBackground(colorFondo.obtener());

		/* Descripción */
		JEditorPane descripcion = new JEditorPane();
		descripcion.setContentType("text/html");
		descripcion.setEditable(false);
		descripcion.setOpaque(false);
		descripcion.setForeground(colorTexto.obtener());
		descripcion.setText(construirHTMLDescripcion());

		JScrollPane scroll = new JScrollPane(descripcion);
		scroll.setBorder(null);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);

		centro.add(scroll);
		centro.add(Box.createVerticalStrut(8));

		/* Checkboxes dinámicos */
		JPanel panelOpciones = new JPanel(new GridLayout(0, 1, 4, 4));
		panelOpciones.setOpaque(false);

		for (String clave : ConfigCDLauncher.opciones.keySet()) {
			boolean valor = ConfigCDLauncher.opciones.get(clave);
			JCheckBox chk = new JCheckBox(clave, valor);
			chk.setOpaque(false);
			chk.setForeground(colorTexto.obtener());
			chk.setFont(chk.getFont().deriveFont(11f));

			chk.addActionListener(e -> ConfigCDLauncher.opciones.put(clave, chk.isSelected()));

			checkboxes.put(clave, chk);
			panelOpciones.add(chk);
		}

		centro.add(panelOpciones);
		centro.add(Box.createVerticalStrut(10));

		/* Botón grande consola */
		JButton botonConsola = new JButton(MonitorDePID.idioma.cdlauncherHabilitarConsola());
		botonConsola.setFont(botonConsola.getFont().deriveFont(Font.BOLD, 12f));
		botonConsola.setBackground(colorBoton.obtener());
		botonConsola.setForeground(colorTexto.obtener());
		botonConsola.addActionListener(e -> habilitarConsola());

		centro.add(botonConsola);

		raiz.add(centro, BorderLayout.CENTER);

		/* Panel inferior con OK */
		JPanel inferior = new JPanel(new BorderLayout());
		inferior.setOpaque(false);

		JButton ok = new JButton("OK");
		ok.setPreferredSize(new Dimension(80, 30));
		ok.setBackground(colorBoton.obtener());
		ok.setForeground(colorTexto.obtener());
		ok.addActionListener(e -> dispose());

		inferior.add(ok, BorderLayout.EAST);

		raiz.add(inferior, BorderLayout.SOUTH);

		add(raiz);
		setLocationRelativeTo(principal);
		setVisible(true);
		CrashDetectorLogger.log("CDLauncher Config");
	}

	private String construirHTMLDescripcion() {
		String texto = MonitorDePID.idioma.cdlauncherDescripcionCompleta();

		return "<html><body>" + "<div style='font-size:11px; line-height:1.3;'>" + texto + "</div>" + "</body></html>";
	}

	@Override
	public void recargarApariencia() {
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> lista = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());

		lista.add(colorFondo);
		lista.add(colorTexto);
		lista.add(colorBoton);

		return lista;
	}
}
