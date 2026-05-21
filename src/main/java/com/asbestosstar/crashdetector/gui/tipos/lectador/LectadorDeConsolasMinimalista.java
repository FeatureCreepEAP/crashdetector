package com.asbestosstar.crashdetector.gui.tipos.lectador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class LectadorDeConsolasMinimalista extends LectadorDeConsolasGUI {

	public static String ID = "minimalista_lectador";

	private static final long serialVersionUID = 1L;

	// Última ventana activa para reutilizar lectador:// sin abrir muchas ventanas.
	public static volatile LectadorDeConsolasMinimalista instanciaActiva;

	public LectadorDeConsolasMinimalista() {
		super();

		colorFondo = ConfigColor.de("tema.minimalista.lectador.color.fondo", new java.awt.Color(15, 15, 15));
		colorTexto = ConfigColor.de("tema.minimalista.lectador.color.texto", new java.awt.Color(235, 235, 235));
		colorError = ConfigColor.de("tema.minimalista.lectador.color.error", new java.awt.Color(255, 170, 55));
		colorPila = ConfigColor.de("tema.minimalista.lectador.color.pila", new java.awt.Color(80, 170, 255));
		colorFondoPanel = ConfigColor.de("tema.minimalista.lectador.color.fondo.panel", new java.awt.Color(28, 28, 28));
		colorTextoPanel = ConfigColor.de("tema.minimalista.lectador.color.texto.panel",
				new java.awt.Color(205, 205, 205));
		colorTextoNegro = ConfigColor.de("tema.minimalista.lectador.color.texto.negro", java.awt.Color.BLACK);
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	protected String textoNormalLeyenda() {
		return MonitorDePID.idioma.textoNormal();
	}

	@Override
	public void init() {
		super.init();
		instanciaActiva = this;
	}

	@Override
	public void aplicarApariencia() {
		if (scrollLogs != null) {
			scrollLogs.getViewport().setBackground(colorFondo.obtener());
		}

		if (pnlSelector != null) {
			pnlSelector.setBackground(colorFondoPanel.obtener());
		}

		if (pnlLeyenda != null) {
			pnlLeyenda.setBackground(colorFondoPanel.obtener());
		}

		repaint();
	}

	@Override
	public void configurarVentanaBase() {
		super.configurarVentanaBase();
		setSize(1280, 720);
		setTitle(MonitorDePID.idioma.tituloLectador() + " - Minimalista");
	}

	@Override
	public JComponent crearPanelSelector() {
		JPanel pnl = new JPanel(new GridLayout(1, 1));
		pnl.setOpaque(true);
		pnl.setBackground(colorFondoPanel.obtener());
		pnl.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

		cmbConsolas.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				actualizarConsola();
			}
		});

		// En este modo no se muestra cmbModo: solo hay un dropdown para el log.
		pnl.add(cmbConsolas);

		return pnl;
	}

	@Override
	public JPanel crearPanelLeyenda() {
		JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		pnl.setOpaque(true);
		pnl.setBackground(colorFondoPanel.obtener());
		pnl.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

		java.util.Set<java.awt.Color> coloresMostrados = new java.util.HashSet<java.awt.Color>();

		for (Consola consola : consolas) {
			if (consola == null || consola.errores_de_lectadores == null) {
				continue;
			}

			for (ErrorDeLectador err : consola.errores_de_lectadores) {
				if (err == null || err.verificacion == null || err.verificacion.nivel_de_criticalidad() == null) {
					continue;
				}

				java.awt.Color c = err.obtenerColor();

				if (!coloresMostrados.contains(c)) {
					JLabel lbl = crearEtiquetaLeyenda(err.verificacion.nivel_de_criticalidad().nombre, c);
					pnl.add(lbl);
					coloresMostrados.add(c);
				}
			}
		}

		pnl.add(crearEtiquetaLeyenda(MonitorDePID.idioma.obtenerStacktraceEnLeyenda(), colorPila.obtener()));
		pnl.add(crearEtiquetaLeyenda(textoNormalLeyenda(), colorTextoPanel.obtener()));

		return pnl;
	}

	public JLabel crearEtiquetaLeyenda(String texto, java.awt.Color color) {
		JLabel lbl = new JLabel(texto == null ? "" : texto);
		lbl.setOpaque(true);
		lbl.setBackground(color);
		lbl.setForeground(colorTextoNegro.obtener());
		lbl.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
		lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		return lbl;
	}

	@Override
	public JPanel crearPanelInformacionErrores() {
		// Se crea para compatibilidad con el código base, pero no se muestra.
		JPanel pnl = super.crearPanelInformacionErrores();
		pnl.setVisible(false);
		return pnl;
	}

	@Override
	public void recolocarComponentes() {
		int ancho = getContentPane().getWidth();
		int alto = getContentPane().getHeight();

		int margen = 8;
		int altoBarra = 42;
		int anchoSelector = Math.min(360, Math.max(240, ancho / 4));

		if (pnlSelector != null) {
			pnlSelector.setBounds(0, 0, anchoSelector, altoBarra);
		}

		if (pnlLeyenda != null) {
			pnlLeyenda.setBounds(anchoSelector, 0, Math.max(0, ancho - anchoSelector), altoBarra);
		}

		if (scrollLogs != null) {
			scrollLogs.setBounds(0, altoBarra, ancho, Math.max(0, alto - altoBarra));
		}

		if (pnlInferior != null) {
			pnlInferior.setBounds(0, 0, 0, 0);
			pnlInferior.setVisible(false);
		}

		recolocarBuscador();

		revalidate();
		repaint();
	}

	@Override
	public void recolocarBuscador() {
		if (pnlBuscador == null) {
			return;
		}

		int ancho = getContentPane().getWidth();
		int w = Math.min(340, Math.max(220, ancho / 4));

		pnlBuscador.setBounds(Math.max(0, ancho - w - 8), 48, w, 42);
		pnlBuscador.revalidate();
		pnlBuscador.repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new java.util.ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorError.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorError());
		colorPila.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPila());
		colorFondoPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPanel());
		colorTextoPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoPanel());
		colorTextoNegro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoNegro());

		elementos.add(colorFondo);
		elementos.add(colorTexto);
		elementos.add(colorError);
		elementos.add(colorPila);
		elementos.add(colorFondoPanel);
		elementos.add(colorTextoPanel);
		elementos.add(colorTextoNegro);

		return elementos;
	}

	@Override
	public void procesarHipervinculo(String url) {
		try {
			String sinPrefijo = url.substring("lectador://".length());
			int idx = sinPrefijo.lastIndexOf(":");

			if (idx == -1) {
				CrashDetectorLogger.logException(new IllegalArgumentException("URL de lectador inválida: " + url));
				return;
			}

			String rutaArchivo = sinPrefijo.substring(0, idx);
			int numeroLinea = Integer.parseInt(sinPrefijo.substring(idx + 1));

			Consola consolaSeleccionada = null;

			for (Consola c : MonitorDePID.consolas) {
				if (c != null && c.archivo != null && c.archivo.toString().equals(rutaArchivo)) {
					consolaSeleccionada = c;
					break;
				}
			}

			if (consolaSeleccionada == null) {
				javax.swing.JOptionPane.showMessageDialog(null,
						MonitorDePID.idioma.noSeEncontroConsolaParaArchivo() + rutaArchivo, MonitorDePID.idioma.error(),
						javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}

			final LectadorDeConsolasMinimalista lector;
			final boolean nuevaInstancia;

			if (instanciaActiva != null && instanciaActiva.isDisplayable()) {
				lector = instanciaActiva;
				nuevaInstancia = false;
			} else {
				lector = new LectadorDeConsolasMinimalista();
				nuevaInstancia = true;
			}

			final String nombreArchivo = new File(consolaSeleccionada.archivo.toString()).getName();
			final int destino = numeroLinea;

			if (nuevaInstancia) {
				lector.init();
				lector.setVisible(true);
				instanciaActiva = lector;
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						lector.setVisible(true);
						lector.toFront();
						lector.requestFocus();

						Object seleccionActual = lector.cmbConsolas.getSelectedItem();
						String nombreActual = seleccionActual == null ? null : seleccionActual.toString();

						lector.lineaDestinoPendiente = Integer.valueOf(destino);

						if (nombreActual == null || !nombreArchivo.equals(nombreActual)) {
							for (int i = 0; i < lector.cmbConsolas.getItemCount(); i++) {
								String item = lector.cmbConsolas.getItemAt(i);

								if (nombreArchivo.equals(item)) {
									lector.cmbConsolas.setSelectedIndex(i);
									return;
								}
							}

							lector.lineaDestinoPendiente = null;
							return;
						}

						lector.saltarDirectamenteALinea(destino);
						lector.lineaDestinoPendiente = null;
					} catch (Exception ex) {
						CrashDetectorLogger.logException(ex);
					}
				}
			});
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}
}