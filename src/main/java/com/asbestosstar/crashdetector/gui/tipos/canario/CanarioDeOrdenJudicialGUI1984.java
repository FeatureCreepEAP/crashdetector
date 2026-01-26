package com.asbestosstar.crashdetector.gui.tipos.canario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class CanarioDeOrdenJudicialGUI1984 extends CanarioDeOrdenJudicialGUI {

	private static final long serialVersionUID = 1L;

	public static String ID = "1984";

	private ConfigColor colorFondo = ConfigColor.de("tema.canario1984.color.fondo", new Color(15, 15, 15));
	private ConfigColor colorTexto = ConfigColor.de("tema.canario1984.color.texto", new Color(220, 220, 220));
	private ConfigColor colorBoton = ConfigColor.de("tema.canario1984.color.boton", new Color(110, 0, 0));
	private ConfigColor colorAlerta = ConfigColor.de("tema.canario1984.color.alerta", new Color(200, 40, 40));

	private JPanel panelRaiz;
	private JLabel titulo;
	private JTextArea descripcion;
	private JTextArea resultado;
	private JButton botonRevisar;
	private JButton botonCerrar;

	@Override
	public void init() {

		setTitle(MonitorDePID.idioma.canarioTitulo());
		setModal(true);
		setResizable(false);
		setMinimumSize(new Dimension(640, 380));

		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		getContentPane().add(panelRaiz);

		titulo = new JLabel(MonitorDePID.idioma.canario1984Titulo());
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		panelRaiz.add(titulo, BorderLayout.NORTH);

		// Descripción del sistema de canarios
		descripcion = new JTextArea(MonitorDePID.idioma.canario1984Descripcion());
		descripcion.setEditable(false);
		descripcion.setLineWrap(true);
		descripcion.setWrapStyleWord(true);
		descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		descripcion.setOpaque(false);
		descripcion.setBorder(BorderFactory.createEmptyBorder(4, 0, 8, 0));

		resultado = new JTextArea();
		resultado.setEditable(false);
		resultado.setLineWrap(true);
		resultado.setWrapStyleWord(true);
		resultado.setFont(new Font("Consolas", Font.PLAIN, 13));

		JPanel panelCentro = new JPanel(new BorderLayout(6, 6));
		panelCentro.setOpaque(false);
		panelCentro.add(descripcion, BorderLayout.NORTH);
		panelCentro.add(resultado, BorderLayout.CENTER);

		panelRaiz.add(panelCentro, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botonRevisar = new JButton(MonitorDePID.idioma.revisar());
		botonCerrar = new JButton(MonitorDePID.idioma.cerrar());

		panelBotones.add(botonCerrar);
		panelBotones.add(botonRevisar);

		panelRaiz.add(panelBotones, BorderLayout.SOUTH);

		aplicarApariencia();
		agregarListeners();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void agregarListeners() {
		botonRevisar.addActionListener(e -> revisarCanarios());
		botonCerrar.addActionListener(e -> dispose());
	}

	private void revisarCanarios() {

		StringBuilder sb = new StringBuilder();

		Map<String, Boolean> estados = obtenerEstados();
		int inseguros = 0;

		for (Map.Entry<String, Boolean> e : estados.entrySet()) {

			boolean seguro = e.getValue();

			if (!seguro) {
				inseguros++;
			}

			sb.append(seguro ? "✔ " : "✖ ");
			sb.append(e.getKey());
			sb.append("\n");
		}

		sb.append("\n");

		if (inseguros == 0) {
			sb.append(MonitorDePID.idioma.canarioTodoSeguro());
		} else {
			sb.append(MonitorDePID.idioma.canarioComprometido(inseguros));
		}

		resultado.setText(sb.toString());
	}

	@Override
	public void aplicarApariencia() {

		panelRaiz.setBackground(colorFondo.obtener());
		resultado.setBackground(colorFondo.obtener());

		titulo.setForeground(colorTexto.obtener());
		resultado.setForeground(colorTexto.obtener());
		descripcion.setForeground(colorTexto.obtener());

		botonRevisar.setBackground(colorBoton.obtener());
		botonCerrar.setBackground(colorBoton.obtener());

		revalidate();
		repaint();
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {

		List<ElementoConfig> elementos = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorAlerta.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorAlerta());

		elementos.add(colorFondo);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorAlerta);

		return elementos;
	}
}
