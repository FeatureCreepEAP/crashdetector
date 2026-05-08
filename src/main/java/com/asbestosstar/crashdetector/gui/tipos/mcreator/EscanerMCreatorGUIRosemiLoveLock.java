package com.asbestosstar.crashdetector.gui.tipos.mcreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public class EscanerMCreatorGUIRosemiLoveLock extends EscanerMCreatorGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "rosemi_lovelock";

	// ====== Colores configurables ======

	public ConfigColor colorFondoVentana;
	public ConfigColor colorTextoPrincipal;
	public ConfigColor colorFondoResultados;
	public ConfigColor colorEstado;
	public ConfigColor colorBotonFondo;
	public ConfigColor colorBotonTexto;
	public ConfigColor colorBordeScroll;
	public ConfigColor colorTextoDescripcion;
	public ConfigColor colorTextoEstado;
	public ConfigColor colorTarjeta;
	public ConfigColor colorTarjetaSuperior;

	// ====== Componentes propios del tema ======

	public JLabel etiquetaDescripcion;
	public JLabel etiquetaImagen;

	public JPanel panelSuperior;
	public JPanel panelTarjetaDescripcion;
	public JPanel panelTarjetaResultados;
	public JPanel panelBoton;

	public EscanerMCreatorGUIRosemiLoveLock() {
		colorFondoVentana = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.fondo.ventana",
				new Color(229, 175, 177));

		colorTextoPrincipal = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.principal",
				new Color(45, 45, 50));

		colorFondoResultados = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.fondo.resultados",
				new Color(255, 239, 244));

		colorEstado = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.estado", new Color(229, 175, 177));

		colorBotonFondo = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.boton.fondo", new Color(255, 122, 164));

		colorBotonTexto = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.boton.texto", Color.WHITE);

		colorBordeScroll = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.borde.scroll", new Color(255, 215, 226));

		colorTextoDescripcion = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.descripcion", Color.WHITE);

		colorTextoEstado = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.estado", new Color(115, 80, 90));

		colorTarjeta = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.tarjeta", new Color(255, 239, 244));

		colorTarjetaSuperior = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.tarjeta.superior",
				new Color(229, 175, 177));
	}

	@Override
	public void init() {
		super.init();
		cargarImagenDecorativaEnSegundoPlano();
	}

	@Override
	protected Font fuenteDescripcion() {
		return new Font("Segoe UI", Font.BOLD, 13);
	}

	@Override
	protected Font fuenteResultados() {
		return new Font("Consolas", Font.PLAIN, 14);
	}

	@Override
	protected Font fuenteBoton() {
		return new Font("Segoe UI", Font.BOLD, 18);
	}

	@Override
	protected ImageIcon iconoDecorativo() {
		return null;
	}

	@Override
	protected String textoEstadoCargando() {
		return MonitorDePID.idioma.escanerMCreatorCargando();
	}

	@Override
	protected String textoEstadoCompletado() {
		return MonitorDePID.idioma.escanerMCreatorCompletado();
	}

	@Override
	protected String textoEstadoError() {
		return MonitorDePID.idioma.escanerMCreatorError();
	}

	@Override
	protected String tituloVentanaNoLocalizado() {
		return MonitorDePID.idioma.tituloEscanerMCreator() + " - Rosemi LoveLock";
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public TipoGUI<EscanerMCreatorGUI> tipo() {
		return TipoGUI.ESCANER_MCREATOR;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
		colorTextoPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoPrincipal());
		colorFondoResultados.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoResultados());
		colorEstado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEstado());
		colorBotonFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonFondo());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonTexto());
		colorBordeScroll.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeScroll());
		colorTextoDescripcion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoDescripcion());

		elementos.add(colorFondoVentana);
		elementos.add(colorTextoPrincipal);
		elementos.add(colorFondoResultados);
		elementos.add(colorEstado);
		elementos.add(colorBotonFondo);
		elementos.add(colorBotonTexto);
		elementos.add(colorBordeScroll);
		elementos.add(colorTextoDescripcion);
		elementos.add(colorTextoEstado);
		elementos.add(colorTarjeta);
		elementos.add(colorTarjetaSuperior);

		return elementos;
	}

	@Override
	protected void aplicarApariencia() {
		Color fondo = colorFondoVentana.obtener();
		Color resultado = colorTarjeta.obtener();
		Color superior = colorTarjetaSuperior.obtener();
		Color borde = colorBordeScroll.obtener();

		getContentPane().setBackground(fondo);
		setBackground(fondo);

		if (panelContenido != null) {
			panelContenido.setBackground(fondo);
		}

		if (panelContenidoConImagen != null) {
			panelContenidoConImagen.setBackground(fondo);
		}

		if (panelSuperior != null) {
			panelSuperior.setBackground(fondo);
		}

		if (panelBoton != null) {
			panelBoton.setBackground(fondo);
		}

		if (panelTarjetaDescripcion != null) {
			panelTarjetaDescripcion.setBackground(superior);
			panelTarjetaDescripcion.setBorder(new BordeRedondeado(borde, 1, 10));
		}

		if (panelTarjetaResultados != null) {
			panelTarjetaResultados.setBackground(resultado);
			panelTarjetaResultados.setBorder(new BordeRedondeado(borde, 1, 10));
		}

		if (etiquetaDescripcion != null) {
			etiquetaDescripcion.setForeground(colorTextoDescripcion.obtener());
			etiquetaDescripcion.setBackground(superior);
		}

		if (etiquetaImagen != null) {
			etiquetaImagen.setBackground(superior);
			etiquetaImagen.setOpaque(false);
		}

		if (areaResultados != null) {
			areaResultados.setForeground(colorTextoPrincipal.obtener());
			areaResultados.setBackground(colorFondoResultados.obtener());
			areaResultados.setCaretColor(colorTextoPrincipal.obtener());
			areaResultados.setBorder(BorderFactory.createEmptyBorder());
		}

		if (panelDesplazamiento != null) {
			panelDesplazamiento.setBackground(colorFondoResultados.obtener());
			panelDesplazamiento.getViewport().setBackground(colorFondoResultados.obtener());
			panelDesplazamiento.setBorder(BorderFactory.createEmptyBorder());
		}

		if (botonEscanear != null) {
			botonEscanear.setFont(fuenteBoton());
			botonEscanear.setForeground(colorBotonTexto.obtener());
			botonEscanear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			botonEscanear.setBorder(new BordeRedondeado(new Color(255, 235, 242), 1, 10));
			botonEscanear.setFocusPainted(false);

			if (!CrashDetectorGUI.esMac()) {
				botonEscanear.setBackground(colorBotonFondo.obtener());
				botonEscanear.setOpaque(true);
			}
		}

		String titulo = tituloVentanaNoLocalizado();

		if (titulo != null && !titulo.isEmpty()) {
			setTitle(titulo);
		}
	}

	@Override
	protected void construirEstructuraBase() {
		setMinimumSize(new Dimension(720, 455));

		panelContenido = new JPanel(new BorderLayout(4, 4));
		panelContenido.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		add(panelContenido, BorderLayout.CENTER);

		// ====== Imagen decorativa en el encabezado ======

		etiquetaImagen = new JLabel(" ");
		etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
		etiquetaImagen.setVerticalAlignment(JLabel.CENTER);

		etiquetaImagen.setPreferredSize(new Dimension(230, 118));
		etiquetaImagen.setMinimumSize(new Dimension(230, 118));
		etiquetaImagen.setMaximumSize(new Dimension(230, 118));
		etiquetaImagen.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));

		// ====== Descripción ======

		etiquetaDescripcion = new JLabel("<html><div style='text-align:center; line-height:1.08;'>"
				+ MonitorDePID.idioma.descripcionEscanerMCreator() + "</div></html>", JLabel.CENTER);

		etiquetaDescripcion.setFont(fuenteDescripcion());
		etiquetaDescripcion.setOpaque(false);
		etiquetaDescripcion.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

		panelTarjetaDescripcion = new JPanel(new BorderLayout(4, 0));
		panelTarjetaDescripcion.setOpaque(true);
		panelTarjetaDescripcion.add(etiquetaDescripcion, BorderLayout.CENTER);
		panelTarjetaDescripcion.add(etiquetaImagen, BorderLayout.EAST);

		// ====== Botón ======

		botonEscanear = new JButton(MonitorDePID.idioma.escanear());
		botonEscanear.setFocusPainted(false);
		botonEscanear.setFont(fuenteBoton());
		botonEscanear.setPreferredSize(new Dimension(220, 42));
		botonEscanear.addActionListener(e -> iniciarEscaneo());

		panelBoton = new JPanel(new BorderLayout());
		panelBoton.setOpaque(false);
		panelBoton.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		panelBoton.add(botonEscanear, BorderLayout.CENTER);

		panelSuperior = new JPanel(new BorderLayout(0, 0));
		panelSuperior.setOpaque(false);
		panelSuperior.add(panelTarjetaDescripcion, BorderLayout.CENTER);
		panelSuperior.add(panelBoton, BorderLayout.SOUTH);

		// ====== Área de resultados ======

		areaResultados = new JTextArea();
		areaResultados.setEditable(false);
		areaResultados.setFont(fuenteResultados());
		areaResultados.setLineWrap(true);
		areaResultados.setWrapStyleWord(true);
		areaResultados.setOpaque(true);
		areaResultados.setMargin(new Insets(8, 8, 8, 8));
		areaResultados.setText(MonitorDePID.idioma.escanerMCreatorResultadosAnalisis() + "\n\n");

		panelDesplazamiento = new JScrollPane(areaResultados);
		panelDesplazamiento.setBorder(BorderFactory.createEmptyBorder());
		panelDesplazamiento.setPreferredSize(new Dimension(100, 250));

		panelTarjetaResultados = new JPanel(new BorderLayout());
		panelTarjetaResultados.setOpaque(true);
		panelTarjetaResultados.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		panelTarjetaResultados.add(panelDesplazamiento, BorderLayout.CENTER);

		// ====== Centro ======

		panelContenidoConImagen = new JPanel(new BorderLayout());
		panelContenidoConImagen.setOpaque(false);
		panelContenidoConImagen.add(panelTarjetaResultados, BorderLayout.CENTER);

		panelContenido.add(panelSuperior, BorderLayout.NORTH);
		panelContenido.add(panelContenidoConImagen, BorderLayout.CENTER);

		aplicarApariencia();
	}

	@Override
	protected void iniciarEscaneo() {
		areaResultados.setText(MonitorDePID.idioma.escanerMCreatorEscaneandoMods() + "\n\n"
				+ MonitorDePID.idioma.escanerMCreatorPorFavorEspera());

		areaResultados.setCaretPosition(0);

		botonEscanear.setEnabled(false);
		botonEscanear.setText(MonitorDePID.idioma.escanerMCreatorEscaneandoMods());

		new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() {
				return com.asbestosstar.crashdetector.EscanerMCreator.obtainerMCreatorMods();
			}

			@Override
			protected void done() {
				try {
					String resultado = get();
					resultado = limpiarResultadoEscaner(resultado);

					if (resultado == null || resultado.trim().isEmpty()) {
						resultado = MonitorDePID.idioma.escanerMCreatorNoSeEncontraronMods();
					}

					areaResultados.setText(MonitorDePID.idioma.escanerMCreatorResultadosAnalisis() + "\n\n" + resultado
							+ "\n\n" + MonitorDePID.idioma.escanerMCreatorEscaneoCompletado());

					areaResultados.setCaretPosition(0);
				} catch (Exception ex) {
					areaResultados.setText(
							MonitorDePID.idioma.escanerMCreatorErrorDuranteEscaneo() + "\n\n" + ex.getMessage());

					areaResultados.setCaretPosition(0);
				} finally {
					botonEscanear.setEnabled(true);
					botonEscanear.setText(MonitorDePID.idioma.escanear());
				}
			}
		}.execute();
	}

	@Override
	protected String limpiarResultadoEscaner(String resultado) {
		if (resultado == null) {
			return "";
		}

		String limpio = resultado.trim();
		String encabezado = MonitorDePID.idioma.escanerMCreatorResultadosAnalisis();

		if (limpio.startsWith(encabezado)) {
			limpio = limpio.substring(encabezado.length()).trim();
		}

		return limpio;
	}

	public void cargarImagenDecorativaEnSegundoPlano() {
		if (etiquetaImagen == null) {
			return;
		}

		etiquetaImagen.setText(" ");

		new SwingWorker<ImageIcon, Void>() {
			@Override
			protected ImageIcon doInBackground() {
				try {
					File archivoImagen = Statics.carpeta.resolve("imagenes/rosemi.png").toFile();

					if (!archivoImagen.exists()) {
						return null;
					}

					BufferedImage original = ImageIO.read(archivoImagen);

					if (original == null) {
						return null;
					}

					Image imagenEscalada = escalarManteniendoAspecto(original, 220, 112);
					return new ImageIcon(imagenEscalada);
				} catch (Exception ex) {
					System.err.println("Error al cargar la imagen decorativa: " + ex.getMessage());
					return null;
				}
			}

			@Override
			protected void done() {
				try {
					ImageIcon icono = get();

					if (icono != null) {
						etiquetaImagen.setIcon(icono);
						etiquetaImagen.setText(null);
					}
				} catch (Exception ignored) {
				}
			}
		}.execute();
	}

	public Image escalarManteniendoAspecto(BufferedImage original, int maxAncho, int maxAlto) {
		int anchoOriginal = original.getWidth();
		int altoOriginal = original.getHeight();

		if (anchoOriginal <= 0 || altoOriginal <= 0) {
			return original;
		}

		double escalaAncho = (double) maxAncho / (double) anchoOriginal;
		double escalaAlto = (double) maxAlto / (double) altoOriginal;
		double escala = Math.min(escalaAncho, escalaAlto);

		int nuevoAncho = Math.max(1, (int) Math.round(anchoOriginal * escala));
		int nuevoAlto = Math.max(1, (int) Math.round(altoOriginal * escala));

		return original.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
	}

	public static class BordeRedondeado extends LineBorder {

		private static final long serialVersionUID = 1L;

		public int radio;

		public BordeRedondeado(Color color, int grosor, int radio) {
			super(color, grosor, true);
			this.radio = radio;
		}

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(1, 1, 1, 1);
		}

		@Override
		public boolean isBorderOpaque() {
			return false;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();

			try {
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(lineColor);

				for (int i = 0; i < thickness; i++) {
					g2.drawRoundRect(x + i, y + i, width - i - i - 1, height - i - i - 1, radio, radio);
				}
			} finally {
				g2.dispose();
			}
		}
	}
}