package com.asbestosstar.crashdetector.gui.tipos.mcreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class EscanerMCreatorGUIRosemiLoveLock extends EscanerMCreatorGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "rosemi_lovelock";

	// ====== Campos de color configurables ======
	public ConfigColor colorFondoVentana;
	public ConfigColor colorTextoPrincipal;
	public ConfigColor colorFondoResultados;
	public ConfigColor colorEstado;
	public ConfigColor colorBotonFondo;
	public ConfigColor colorBotonTexto;
	public ConfigColor colorBordeScroll;
	public ConfigColor colorTextoDescripcion;

	/**
	 * 
	 * 
	 * Color para el texto extra
	 */
	public ConfigColor colorTextoExtra;
	public ConfigColor colorTextoEstado;

// public ConfigColor colorTextoEstado = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.estado", new Color(100, 100, 100)); // Gris medio
	// public ConfigColor colorTextoExtra =
	// ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.extra", new
	// Color(100, 100, 100)); // Gris medio

	public EscanerMCreatorGUIRosemiLoveLock() {
		colorFondoVentana = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.fondo.ventana",
				new Color(229, 175, 177)); // #e5afb1
		colorTextoPrincipal = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.principal",
				new Color(51, 51, 51)); // Gris oscuro
		colorFondoResultados = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.fondo.resultados",
				new Color(255, 230, 237)); // Rosa claro
		colorEstado = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.estado", new Color(220, 220, 220)); // Gris
																												// muy
																												// claro
		colorBotonFondo = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.boton.fondo", new Color(255, 153, 180)); // Rosa
																														// medio
																														// #FF99B4
		colorBotonTexto = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.boton.texto", Color.WHITE);
		colorBordeScroll = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.borde.scroll", new Color(255, 193, 208)); // Rosa
																															// oscuro
																															// #FFC1D0
		colorTextoDescripcion = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.descripcion", Color.WHITE);

	}

	@Override
	public void init() {
		// Inicializar colores con tonos que coincidan con la imagen

		// AHORA llamamos al init del padre
		super.init();

		// Ajustar la apariencia adicional después de que el padre haya construido la
		// interfaz
		aplicarAparienciaAdicional();
	}

	/**
	 * Aplicar ajustes adicionales de apariencia que no se pueden hacer en la
	 * implementación base
	 */
	private void aplicarAparienciaAdicional() {
		// Configurar fondo del panel de descripción
		Component[] components = panelContenido.getComponents();
		for (Component comp : components) {
			if (comp instanceof JPanel) {
				JPanel panel = (JPanel) comp;
				Component[] panelComponents = panel.getComponents();
				for (Component panelComp : panelComponents) {
					if (panelComp instanceof JLabel) {
						JLabel label = (JLabel) panelComp;
						// Verificar que el label tenga texto antes de acceder a él
						String labelText = label.getText();
						if (labelText != null && labelText.contains(MonitorDePID.idioma.descripcionEscanerMCreator())) {
							label.setBackground(colorBordeScroll.obtener());
							label.setOpaque(true);
							label.setForeground(colorTextoDescripcion.obtener());
							label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
						}
					}
				}
			}
		}

		// Configurar fondo del estado
		if (etiquetaEstado != null) {
			etiquetaEstado.setBackground(colorEstado.obtener());
			etiquetaEstado.setForeground(colorTextoEstado.obtener());
			etiquetaEstado.setOpaque(true);
			etiquetaEstado.setPreferredSize(new Dimension(0, 30));
			etiquetaEstado.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
		}

		// Ajustar el color del borde del scroll
		if (panelDesplazamiento != null) {
			panelDesplazamiento.setBorder(BorderFactory.createLineBorder(colorBordeScroll.obtener()));
		}

		// Ajustar el tamaño del área de resultados
		if (areaResultados != null) {
			areaResultados.setBorder(BorderFactory.createLineBorder(colorBordeScroll.obtener()));
			areaResultados.setMargin(new java.awt.Insets(10, 10, 10, 10));
		}

		// Ajustar el tamaño del botón
		if (botonEscanear != null) {
			botonEscanear.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		}

		// Ajustar el tamaño del panel de contenido con imagen
		if (panelContenidoConImagen != null) {
			panelContenidoConImagen.setMaximumSize(new Dimension(9999, 9999));
		}
	}

	@Override
	protected Font fuenteDescripcion() {
		return new Font("Segoe UI", Font.BOLD, 16);
	}

	@Override
	protected Font fuenteResultados() {
		return new Font("Segoe UI", Font.PLAIN, 15);
	}

	@Override
	protected Font fuenteBoton() {
		return new Font("Segoe UI", Font.BOLD, 17);
	}

	@Override
	protected ImageIcon iconoDecorativo() {
		try {
			File archivoImagen = Statics.carpeta.resolve("imagenes/rosemi.png").toFile();
			if (archivoImagen.exists()) {
				BufferedImage originalImage = ImageIO.read(archivoImagen);
				// Escalar a 200x250 para que coincida con el diseño
				Image image = originalImage.getScaledInstance(200, 250, java.awt.Image.SCALE_SMOOTH);
				return new ImageIcon(image);
			}
		} catch (Exception e) {
			System.err.println("Error al cargar la imagen decorativa: " + e.getMessage());
		}
		return null;
	}

	@Override
	protected String textoEstadoCargando() {
		return "Escaneando archivos...";
	}

	@Override
	protected String textoEstadoCompletado() {
		return "Escaneo completado";
	}

	@Override
	protected String textoEstadoError() {
		return "Error en escaneo";
	}

	@Override
	protected String tituloVentanaNoLocalizado() {
		return "Escaneo MCreator - Rosemi LoveLock";
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
		colorTextoPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoPrincipal());
		colorFondoResultados.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoResultados());
		colorEstado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEstado());
		colorBotonFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonFondo());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonTexto());
		colorBordeScroll.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeScroll());
		colorTextoDescripcion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoDescripcion());
		// colorTextoEstado.establecerNombreParaMostrar(() ->
		// MonitorDePID.idioma.colorTextoEstado());
		// colorTextoExtra.establecerNombreParaMostrar(() ->
		// MonitorDePID.idioma.colorTextoExtra());

		elementos.add(colorFondoVentana);
		elementos.add(colorTextoPrincipal);
		elementos.add(colorFondoResultados);
		elementos.add(colorEstado);
		elementos.add(colorBotonFondo);
		elementos.add(colorBotonTexto);
		elementos.add(colorBordeScroll);
		elementos.add(colorTextoDescripcion);
		// elementos.add(colorTextoEstado);
		// elementos.add(colorTextoExtra);

		return elementos;
	}

	protected void aplicarApariencia() {
		// Fondo ventana y paneles
		getContentPane().setBackground(colorFondoVentana.obtener());
		panelContenido.setBackground(colorFondoVentana.obtener());
		panelContenidoConImagen.setBackground(colorFondoVentana.obtener());
		setBackground(colorFondoVentana.obtener());

		// Texto y área resultados
		areaResultados.setForeground(colorTextoPrincipal.obtener());
		areaResultados.setBackground(colorFondoResultados.obtener());
		etiquetaEstado.setForeground(colorEstado.obtener());

		// Botón
		// En macOS, no cambiamos el fondo del botón para mantener el estilo nativo
		if(!CrashDetectorGUI.esMac()) {
			botonEscanear.setBackground(colorBotonFondo.obtener());
		
		botonEscanear.setForeground(colorBotonTexto.obtener());
		}
		// Título
		String titulo = tituloVentanaNoLocalizado();
		if (titulo != null && !titulo.isEmpty()) {
			setTitle(titulo);
		}
	}

	// ====== Construcción base ======
	protected void construirEstructuraBase() {
		// Panel principal
		panelContenido = new JPanel(new BorderLayout(10, 10));
		panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(panelContenido, BorderLayout.CENTER);

		// Descripción (localizada desde MonitorDePID)
		JLabel etiquetaDescripcion = new JLabel("<html><div style='text-align: center;'>"
				+ MonitorDePID.idioma.descripcionEscanerMCreator() + "</div></html>", JLabel.CENTER);
		etiquetaDescripcion.setFont(fuenteDescripcion());
		aplicarColor(etiquetaDescripcion, colorTextoDescripcion);
		etiquetaDescripcion.setOpaque(true);

		// Área de resultados
		areaResultados = new JTextArea();
		areaResultados.setEditable(false);
		areaResultados.setFont(fuenteResultados());
		areaResultados.setLineWrap(true);
		areaResultados.setWrapStyleWord(true);
		areaResultados.setOpaque(true);

		panelDesplazamiento = new JScrollPane(areaResultados);
		panelDesplazamiento.setBorder(BorderFactory.createLineBorder(colorBordeScroll.obtener()));

		// Imagen opcional
		JLabel etiquetaImagen = null;
		ImageIcon icono = iconoDecorativo();
		if (icono != null && icono.getIconWidth() > 0) {
			// Escalar a 200x112
			Image img = icono.getImage().getScaledInstance(200, 112, Image.SCALE_SMOOTH);
			ImageIcon esc = new ImageIcon(img);
			etiquetaImagen = new JLabel(esc);
			etiquetaImagen.setPreferredSize(new Dimension(200, 112));
			etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
			etiquetaImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
		}

		// Contenedor central (texto + imagen si existe)
		panelContenidoConImagen = new JPanel();
		panelContenidoConImagen.setLayout(new BoxLayout(panelContenidoConImagen, BoxLayout.Y_AXIS));
		panelContenidoConImagen.add(panelDesplazamiento);
		if (etiquetaImagen != null) {
			panelContenidoConImagen.add(etiquetaImagen);
		}

		// Estado (texto NO localizado — hook)
		etiquetaEstado = new JLabel(" ");
		etiquetaEstado.setHorizontalAlignment(JLabel.CENTER);
		aplicarColor(etiquetaEstado, colorEstado);
		etiquetaEstado.setOpaque(true);

		// Botón de escaneo (texto localizado)
		botonEscanear = new JButton(MonitorDePID.idioma.escanear());
		botonEscanear.setFocusPainted(false);
		botonEscanear.setFont(fuenteBoton());
		botonEscanear.setPreferredSize(new Dimension(200, 50));
		botonEscanear.addActionListener(e -> iniciarEscaneo());

		// Organización
		JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
		panelSuperior.add(etiquetaDescripcion, BorderLayout.CENTER);
		panelSuperior.add(botonEscanear, BorderLayout.PAGE_END);

		panelContenido.add(panelSuperior, BorderLayout.NORTH);
		panelContenido.add(panelContenidoConImagen, BorderLayout.CENTER);
		panelContenido.add(etiquetaEstado, BorderLayout.SOUTH);
	}

}