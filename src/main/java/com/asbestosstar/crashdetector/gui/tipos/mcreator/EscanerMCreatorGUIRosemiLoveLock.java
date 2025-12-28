package com.asbestosstar.crashdetector.gui.tipos.mcreator;

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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class EscanerMCreatorGUIRosemiLoveLock extends EscanerMCreatorGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "rosemi_lovelock";
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
		colorTextoEstado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoEstado());
		colorTextoExtra.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoExtra());

		elementos.add(colorFondoVentana);
		elementos.add(colorTextoPrincipal);
		elementos.add(colorFondoResultados);
		elementos.add(colorEstado);
		elementos.add(colorBotonFondo);
		elementos.add(colorBotonTexto);
		elementos.add(colorBordeScroll);
		elementos.add(colorTextoDescripcion);
		elementos.add(colorTextoEstado);
		elementos.add(colorTextoExtra);

		return elementos;
	}

	/**
	 * Color para el texto extra
	 */
	public ConfigColor colorTextoExtra;
	public ConfigColor colorTextoEstado;

}