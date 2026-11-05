package com.asbestosstar.crashdetector.gui.tipos.escanernube;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Tema visual del escáner remoto basado en parallel_artist_project.png.
 *
 * Todos los colores son ConfigColor para que puedan cambiarse desde la
 * configuración de apariencias.
 */
public final class EscanerNubeParallelArtistProject extends EscanerNubeGUI {

	public static final String ID = "parallel_artist_project";

	private final ConfigColor colorFondo = ConfigColor.de("tema.escaner.nube.parallel.color.fondo",
			new Color(48, 54, 94));
	private final ConfigColor colorPanel = ConfigColor.de("tema.escaner.nube.parallel.color.panel",
			new Color(66, 70, 112));
	private final ConfigColor colorPanelClaro = ConfigColor.de("tema.escaner.nube.parallel.color.panel.claro",
			new Color(242, 243, 249));
	private final ConfigColor colorTexto = ConfigColor.de("tema.escaner.nube.parallel.color.texto",
			new Color(248, 248, 252));
	private final ConfigColor colorTextoSecundario = ConfigColor.de("tema.escaner.nube.parallel.color.texto.secundario",
			new Color(206, 211, 234));
	private final ConfigColor colorCampo = ConfigColor.de("tema.escaner.nube.parallel.color.campo",
			new Color(252, 252, 255));
	private final ConfigColor colorCampoTexto = ConfigColor.de("tema.escaner.nube.parallel.color.campo.texto",
			new Color(47, 52, 88));
	private final ConfigColor colorVirusTotal = ConfigColor.de("tema.escaner.nube.parallel.color.virustotal",
			new Color(55, 119, 224));
	private final ConfigColor colorMetaDefender = ConfigColor.de("tema.escaner.nube.parallel.color.metadefender",
			new Color(0, 170, 132));
	private final ConfigColor colorAmbos = ConfigColor.de("tema.escaner.nube.parallel.color.ambos",
			new Color(242, 72, 132));
	private final ConfigColor colorAdvertencia = ConfigColor.de("tema.escaner.nube.parallel.color.advertencia",
			new Color(244, 205, 32));
	private final ConfigColor colorTabla = ConfigColor.de("tema.escaner.nube.parallel.color.tabla",
			new Color(245, 246, 251));
	private final ConfigColor colorTablaTexto = ConfigColor.de("tema.escaner.nube.parallel.color.tabla.texto",
			new Color(43, 48, 82));
	private final ConfigColor colorSeleccion = ConfigColor.de("tema.escaner.nube.parallel.color.seleccion",
			new Color(77, 125, 218));
	private final ConfigColor colorSeleccionTexto = ConfigColor.de("tema.escaner.nube.parallel.color.seleccion.texto",
			new Color(255, 255, 255));
	private final ConfigColor colorBorde = ConfigColor.de("tema.escaner.nube.parallel.color.borde",
			new Color(134, 141, 184));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		recargarTextos();

		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color panelClaro = colorPanelClaro.obtener();
		Color texto = colorTexto.obtener();
		Color textoSecundario = colorTextoSecundario.obtener();
		Color campo = colorCampo.obtener();
		Color campoTexto = colorCampoTexto.obtener();
		Color tabla = colorTabla.obtener();
		Color tablaTexto = colorTablaTexto.obtener();
		Color seleccion = colorSeleccion.obtener();
		Color seleccionTexto = colorSeleccionTexto.obtener();
		Color borde = colorBorde.obtener();

		if (panelRaiz != null) {
			panelRaiz.setBackground(fondo);
		}
		if (panelHero != null) {
			panelHero.setBackground(panel);
			panelHero.setBorder(BorderFactory.createLineBorder(borde));
		}
		if (panelClaves != null) {
			panelClaves.setBackground(panel);
		}
		if (panelControles != null) {
			panelControles.setBackground(panel);
		}
		if (panelResultados != null) {
			panelResultados.setBackground(panel);
		}

		configurarBorde(bordeClaves, texto);
		configurarBorde(bordeResultados, texto);

		if (areaRecuerdo != null) {
			areaRecuerdo.setOpaque(true);
			areaRecuerdo.setBackground(panel);
			areaRecuerdo.setForeground(textoSecundario);
			areaRecuerdo.setCaretColor(texto);
			areaRecuerdo
					.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorAmbos.obtener()),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		}

		if (areaAvisoPrivacidad != null) {
			areaAvisoPrivacidad.setOpaque(true);
			areaAvisoPrivacidad.setBackground(panel);
			areaAvisoPrivacidad.setForeground(colorAdvertencia.obtener());
			areaAvisoPrivacidad.setCaretColor(colorAdvertencia.obtener());
			areaAvisoPrivacidad.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorAdvertencia.obtener()),
							BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		}

		configurarCampo(campoVirusTotal, campo, campoTexto, seleccion, seleccionTexto);
		configurarCampo(campoMetaDefender, campo, campoTexto, seleccion, seleccionTexto);

		if (selectorHilos != null) {
			selectorHilos.setBackground(campo);
			selectorHilos.setForeground(campoTexto);
			JComponent editor = selectorHilos.getEditor();
			if (editor != null) {
				aplicarTemaRecursivo(editor, panel, panelClaro, texto, textoSecundario);
			}
		}

		estilizarBoton(botonGuardarClaves, panelClaro, campoTexto);
		estilizarBoton(botonEscanearVirusTotal, colorVirusTotal.obtener(), texto);
		estilizarBoton(botonEscanearMetaDefender, colorMetaDefender.obtener(), texto);
		estilizarBoton(botonEscanearAmbos, colorAmbos.obtener(), texto);
		estilizarBoton(botonCancelar, colorAdvertencia.obtener(), campoTexto);

		if (tablaResultados != null) {
			estilizarTabla(tablaResultados, tabla, tablaTexto, seleccion, seleccionTexto, borde);
		}
		if (barraProgreso != null) {
			barraProgreso.setBackground(panelClaro);
			barraProgreso.setForeground(colorMetaDefender.obtener());
			barraProgreso.setBorder(BorderFactory.createLineBorder(borde));
		}

		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(textoSecundario);
		}
		if (imagenProyecto != null) {
			cargarImagenProyecto();
		}

		aplicarTemaRecursivo(panelRaiz, fondo, panel, texto, textoSecundario);

		/*
		 * El recorrido genérico no debe sustituir los acentos asignados a botones,
		 * campos y tablas.
		 */
		configurarCampo(campoVirusTotal, campo, campoTexto, seleccion, seleccionTexto);
		configurarCampo(campoMetaDefender, campo, campoTexto, seleccion, seleccionTexto);
		estilizarBoton(botonGuardarClaves, panelClaro, campoTexto);
		estilizarBoton(botonEscanearVirusTotal, colorVirusTotal.obtener(), texto);
		estilizarBoton(botonEscanearMetaDefender, colorMetaDefender.obtener(), texto);
		estilizarBoton(botonEscanearAmbos, colorAmbos.obtener(), texto);
		estilizarBoton(botonCancelar, colorAdvertencia.obtener(), campoTexto);
		estilizarTabla(tablaResultados, tabla, tablaTexto, seleccion, seleccionTexto, borde);

		revalidate();
		repaint();
	}

	private void configurarBorde(TitledBorder borde, Color texto) {
		if (borde != null) {
			borde.setTitleColor(texto);
			borde.setBorder(BorderFactory.createLineBorder(colorBorde.obtener()));
		}
	}

	private void configurarCampo(JPasswordField campo, Color fondo, Color texto, Color seleccion,
			Color seleccionTexto) {
		if (campo == null) {
			return;
		}
		campo.setOpaque(true);
		campo.setBackground(fondo);
		campo.setForeground(texto);
		campo.setCaretColor(texto);
		campo.setSelectionColor(seleccion);
		campo.setSelectedTextColor(seleccionTexto);
		campo.setBorder(BorderFactory.createLineBorder(colorBorde.obtener()));
	}

	private void estilizarBoton(JButton boton, Color fondo, Color texto) {
		if (boton == null) {
			return;
		}

		boton.setFocusPainted(false);
		boton.setBackground(fondo);

		if (!CrashDetectorGUI.esMac()) {
			boton.setOpaque(true);
			boton.setContentAreaFilled(true);
			boton.setBorderPainted(true);
			boton.setForeground(texto);
		}
	}

	private void estilizarTabla(JTable tabla, Color fondo, Color texto, Color seleccion, Color seleccionTexto,
			Color borde) {
		if (tabla == null) {
			return;
		}
		tabla.setBackground(fondo);
		tabla.setForeground(texto);
		tabla.setSelectionBackground(seleccion);
		tabla.setSelectionForeground(seleccionTexto);
		tabla.setGridColor(borde);
		tabla.getTableHeader().setBackground(colorPanel.obtener());
		tabla.getTableHeader().setForeground(colorTexto.obtener());
	}

	private void cargarImagenProyecto() {
		ImageIcon icono = new ImageIcon(Statics.carpeta.resolve("imagenes/parallel_artist_project.png").toString());

		if (icono.getIconWidth() <= 0) {
			java.net.URL recurso = EscanerNubeParallelArtistProject.class
					.getResource("/imagenes/parallel_artist_project.png");
			if (recurso != null) {
				icono = new ImageIcon(recurso);
			}
		}

		if (icono.getIconWidth() > 0) {
			int anchoMaximo = 320;
			if (icono.getIconWidth() > anchoMaximo) {
				int alto = Math.max(1,
						(int) Math.round(icono.getIconHeight() * (anchoMaximo / (double) icono.getIconWidth())));
				Image escalada = icono.getImage().getScaledInstance(anchoMaximo, alto, Image.SCALE_SMOOTH);
				icono = new ImageIcon(escalada);
			}
			imagenProyecto.setIcon(icono);
			imagenProyecto.setText("");
		} else {
			imagenProyecto.setIcon(null);
			imagenProyecto.setText(MonitorDePID.idioma.escanerNubeImagenNoDisponible());
		}

		imagenProyecto.setHorizontalAlignment(SwingConstants.CENTER);
		imagenProyecto.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void aplicarTemaRecursivo(Container contenedor, Color fondo, Color panel, Color texto,
			Color textoSecundario) {
		if (contenedor == null) {
			return;
		}

		for (Component componente : contenedor.getComponents()) {
			if (componente instanceof JPanel) {
				componente.setBackground(panel);
				componente.setForeground(texto);
			} else if (componente instanceof JLabel) {
				componente.setForeground(texto);
			} else if (componente instanceof JScrollPane) {
				JScrollPane scroll = (JScrollPane) componente;
				scroll.setBackground(panel);
				scroll.getViewport().setBackground(panel);
			} else if (componente instanceof JViewport) {
				componente.setBackground(panel);
			} else if (componente instanceof JTextArea) {
				JTextArea area = (JTextArea) componente;
				if (area != areaRecuerdo && area != areaAvisoPrivacidad) {
					area.setForeground(textoSecundario);
				}
			} else if (componente instanceof javax.swing.JSplitPane) {
				componente.setBackground(fondo);
				componente.setForeground(texto);
			} else if (componente instanceof javax.swing.JTextField) {
				javax.swing.JTextField campo = (javax.swing.JTextField) componente;
				campo.setBackground(colorCampo.obtener());
				campo.setForeground(colorCampoTexto.obtener());
				campo.setCaretColor(colorCampoTexto.obtener());
				campo.setSelectionColor(colorSeleccion.obtener());
				campo.setSelectedTextColor(colorSeleccionTexto.obtener());
			} else if (componente instanceof JProgressBar) {
				componente.setBackground(colorPanelClaro.obtener());
				componente.setForeground(colorMetaDefender.obtener());
			} else if (componente instanceof JSpinner) {
				componente.setBackground(colorCampo.obtener());
				componente.setForeground(colorCampoTexto.obtener());
			}

			if (componente instanceof Container) {
				aplicarTemaRecursivo((Container) componente, fondo, panel, texto, textoSecundario);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorPanel());
		colorPanelClaro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorPanelClaro());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorTexto());
		colorTextoSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorTextoSecundario());
		colorCampo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorCampo());
		colorCampoTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorCampoTexto());
		colorVirusTotal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorVirusTotal());
		colorMetaDefender.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorMetaDefender());
		colorAmbos.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorAmbos());
		colorAdvertencia.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorAdvertencia());
		colorTabla.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorTabla());
		colorTablaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorTablaTexto());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorSeleccion());
		colorSeleccionTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorSeleccionTexto());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.escanerNubeColorBorde());

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorPanelClaro);
		elementos.add(colorTexto);
		elementos.add(colorTextoSecundario);
		elementos.add(colorCampo);
		elementos.add(colorCampoTexto);
		elementos.add(colorVirusTotal);
		elementos.add(colorMetaDefender);
		elementos.add(colorAmbos);
		elementos.add(colorAdvertencia);
		elementos.add(colorTabla);
		elementos.add(colorTablaTexto);
		elementos.add(colorSeleccion);
		elementos.add(colorSeleccionTexto);
		elementos.add(colorBorde);

		return elementos;
	}
}
