package com.asbestosstar.crashdetector.gui.tipos.lfpdppp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos
		extends LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI {

	private static final long serialVersionUID = 1L;

	public static String ID = "lfpdppp_con_logos";

	private ConfigColor colorFondo = ConfigColor.de("tema.lfpdppp.color.fondo", new java.awt.Color(240, 240, 240));
	private ConfigColor colorTexto = ConfigColor.de("tema.lfpdppp.color.texto", new java.awt.Color(30, 30, 30));
	private ConfigColor colorBoton = ConfigColor.de("tema.lfpdppp.color.boton", new java.awt.Color(120, 120, 120));

	private JLabel textoPrincipal;
	private JCheckBox checkPermanente;

	private JButton botonAceptar;
	private JButton botonCancelar;

	private JPanel panelRaiz;

	@Override
	public void init() {

		setTitle(MonitorDePID.idioma.tituloLFPDPPP());
		setModal(true);
		setResizable(false);
		setMinimumSize(new Dimension(640, 420));

		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		getContentPane().add(panelRaiz);

		// Logos
		JPanel panelLogos = new JPanel(new GridLayout(1, 3, 12, 0));
		panelLogos.setOpaque(false);

		panelLogos.add(crearLogo("imagenes/profeco.jpg"));
		panelLogos.add(crearLogo("imagenes/edps.png"));
		panelLogos.add(crearLogo("imagenes/ppc_jp.png"));

		panelRaiz.add(panelLogos, BorderLayout.NORTH);

		// Texto principal
		textoPrincipal = new JLabel("<html><body style='width:560px'>" + MonitorDePID.idioma.arco() + "</body></html>");
		panelRaiz.add(textoPrincipal, BorderLayout.CENTER);

		// Parte inferior
		JPanel panelInferior = new JPanel(new BorderLayout());

		checkPermanente = new JCheckBox(MonitorDePID.idioma.aceptarPermanentemente());
		panelInferior.add(checkPermanente, BorderLayout.NORTH);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		botonAceptar = new JButton(MonitorDePID.idioma.aceptar());
		botonCancelar = new JButton(MonitorDePID.idioma.cancelar());

		panelBotones.add(botonCancelar);
		panelBotones.add(botonAceptar);

		panelInferior.add(panelBotones, BorderLayout.SOUTH);

		panelRaiz.add(panelInferior, BorderLayout.SOUTH);

		aplicarApariencia();
		agregarListeners();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JLabel crearLogo(String ruta) {
		JLabel l = new JLabel();
		try {
			Image img = new javax.swing.ImageIcon(Statics.carpeta.resolve(ruta).toFile().toURL()).getImage();

			int max = 110;
			int w = img.getWidth(null);
			int h = img.getHeight(null);

			float escala = Math.min((float) max / w, (float) max / h);

			Image esc = img.getScaledInstance((int) (w * escala), (int) (h * escala), Image.SCALE_SMOOTH);
			l.setIcon(new javax.swing.ImageIcon(esc));
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
		return l;
	}

	private void agregarListeners() {

		botonAceptar.addActionListener(e -> {

			if (checkPermanente.isSelected()) {
				guardarConsentimiento();
			}

			if (despuesDeAceptar != null) {
				try {
					despuesDeAceptar.run();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}
			}

			dispose();
		});

		botonCancelar.addActionListener(e -> dispose());
	}

	@Override
	public void aplicarApariencia() {

		panelRaiz.setBackground(colorFondo.obtener());
		textoPrincipal.setForeground(colorTexto.obtener());
		checkPermanente.setForeground(colorTexto.obtener());

		botonAceptar.setBackground(colorBoton.obtener());
		botonCancelar.setBackground(colorBoton.obtener());

		botonAceptar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		botonCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		revalidate();
		repaint();
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public java.util.List<ElementoConfig> obtenerElementosConfigs() {

		java.util.List<ElementoConfig> elementos = new java.util.ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());

		elementos.add(colorFondo);
		elementos.add(colorTexto);
		elementos.add(colorBoton);

		return elementos;
	}

}
