package com.asbestosstar.crashdetector.gui.tipos.lectador;

import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class LectadorDeConsolasHoloTalk extends LectadorDeConsolasGUI {

	public static String ID = "holotalk";

	private static final long serialVersionUID = 1L;

	/**
	 * Última ventana activa del lectador HoloTalk.
	 * 
	 * Se reutiliza para evitar abrir una ventana nueva y reconstruir toda la UI
	 * cada vez que se pulsa un enlace lectador://.
	 */
	public static volatile LectadorDeConsolasHoloTalk instanciaActiva;

	public LectadorDeConsolasHoloTalk() {

		super();

		colorFondo = ConfigColor.de("tema.holotalk.lectador.color.fondo", java.awt.Color.BLACK);
		colorTexto = ConfigColor.de("tema.holotalk.lectador.color.texto", java.awt.Color.WHITE);
		colorError = ConfigColor.de("tema.holotalk.lectador.color.error", new java.awt.Color(255, 165, 0));
		colorPila = ConfigColor.de("tema.holotalk.lectador.color.pila", new java.awt.Color(80, 170, 255));
		colorFondoPanel = ConfigColor.de("tema.holotalk.lectador.color.fondo.panel", new java.awt.Color(30, 30, 30));
		colorTextoPanel = ConfigColor.de("tema.holotalk.lectador.color.texto.panel", new java.awt.Color(200, 200, 200));
		colorTextoNegro = ConfigColor.de("tema.holotalk.lectador.color.texto.negro", java.awt.Color.BLACK);

	}

	@Override
	public void aplicarApariencia() {
		// Solo apariencia, no datos
		// Applicar fondo otra vez
		if (getContentPane() instanceof JLayeredPane) {
			instalarFondoApariencia((JLayeredPane) getContentPane());
		}
		repaint();
	}

	@Override
	protected String textoNormalLeyenda() {
		return MonitorDePID.idioma.textoNormal();
	}

	@Override
	public void instalarFondoApariencia(JLayeredPane capa) {
		FondoPanel fondo = new FondoPanel(Statics.carpeta.resolve("imagenes/kiara_ame.png").toString());
		fondo.setBounds(0, 0, getWidth(), getHeight());
		capa.add(fondo, JLayeredPane.DEFAULT_LAYER);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				if (fondo != null)
					fondo.setBounds(0, 0, getWidth(), getHeight());
			}
		});

		fondo.cargarAsincrono(pool, new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	private static class FondoPanel extends JPanel {
		private volatile java.awt.Image imagen;
		private final String ruta;

		public FondoPanel(String ruta) {
			this.ruta = ruta;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			if (imagen != null) {
				g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			}
		}

		public void cargarAsincrono(java.util.concurrent.ExecutorService pool, final Runnable whenLoadedOnEDT) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						imagen = ImageIO.read(new File(ruta));
					} catch (Exception ex) {
						CrashDetectorLogger.log("No se pudo cargar fondo: " + ex.getMessage());
					}
					SwingUtilities.invokeLater(whenLoadedOnEDT);
				}
			});
		}
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		// Luego inicializar la interfaz
		super.init();
		aplicarApariencia();

		// Registrar esta instancia como la activa para poder reutilizarla
		instanciaActiva = this;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new java.util.ArrayList<>();

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
			CrashDetectorLogger.log("sin prefijo " + sinPrefijo);

			int idx = sinPrefijo.lastIndexOf(":");

			if (idx == -1) {
				CrashDetectorLogger.logException(new IllegalArgumentException("URL de lectador inválida: " + url));
				return;
			}

			String rutaArchivo = sinPrefijo.substring(0, idx);
			int numeroLinea = Integer.parseInt(sinPrefijo.substring(idx + 1));

			CrashDetectorLogger.log("ruta " + rutaArchivo);

			Consola consolaSeleccionada = null;

			for (Consola c : MonitorDePID.consolas) {
				if (c == null || c.archivo == null) {
					continue;
				}

				if (c.archivo.toString().equals(rutaArchivo)) {
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

			CrashDetectorLogger.log("seleccionada " + consolaSeleccionada.archivo.toString());

			final LectadorDeConsolasHoloTalk lector;
			final boolean nuevaInstancia;

			if (instanciaActiva != null && instanciaActiva.isDisplayable()) {
				lector = instanciaActiva;
				nuevaInstancia = false;
			} else {
				lector = new LectadorDeConsolasHoloTalk();
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
							boolean encontrado = false;

							for (int i = 0; i < lector.cmbConsolas.getItemCount(); i++) {
								String item = lector.cmbConsolas.getItemAt(i);

								if (nombreArchivo.equals(item)) {
									lector.cmbConsolas.setSelectedIndex(i);
									encontrado = true;
									break;
								}
							}

							if (!encontrado) {
								lector.lineaDestinoPendiente = null;
								CrashDetectorLogger.log("No se encontró la consola en cmbConsolas: " + nombreArchivo);
								return;
							}

							// NO llamar actualizarConsola() aquí.
							// setSelectedIndex() ya disparó el ActionListener del combo.
							return;
						}

						lector.saltarDirectamenteALinea(destino);
						lector.lineaDestinoPendiente = null;

						CrashDetectorLogger.log("línea seleccionada en JList: " + destino);
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