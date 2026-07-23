package com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Tema predeterminado de Wendy Mizumi inspirado en su serie Lost Media.
 *
 * Combina tonos azul archivo, rosa de cinta recuperada y paneles oscuros de
 * preservación digital. Toda la apariencia configurable se mantiene aquí.
 */
public final class ClienteGitAvanzadoWendyMizumi extends ClienteGitAvanzadoGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "wendy_mizumi_lost_media";

	private final ConfigColor colorFondo = ConfigColor.de("tema.git_avanzado.wendy.fondo", new Color(25, 31, 48));
	private final ConfigColor colorPanel = ConfigColor.de("tema.git_avanzado.wendy.panel", new Color(43, 52, 76));
	private final ConfigColor colorPanelSecundario = ConfigColor.de("tema.git_avanzado.wendy.panel_secundario",
			new Color(57, 69, 98));
	private final ConfigColor colorTexto = ConfigColor.de("tema.git_avanzado.wendy.texto", new Color(244, 241, 247));
	private final ConfigColor colorTextoSuave = ConfigColor.de("tema.git_avanzado.wendy.texto_suave",
			new Color(198, 207, 231));
	private final ConfigColor colorAzul = ConfigColor.de("tema.git_avanzado.wendy.azul", new Color(67, 137, 207));
	private final ConfigColor colorRosa = ConfigColor.de("tema.git_avanzado.wendy.rosa", new Color(226, 150, 182));
	private final ConfigColor colorBorde = ConfigColor.de("tema.git_avanzado.wendy.borde", new Color(112, 145, 193));
	private final ConfigColor colorTabla = ConfigColor.de("tema.git_avanzado.wendy.tabla", new Color(32, 39, 58));
	private final ConfigColor colorSeleccion = ConfigColor.de("tema.git_avanzado.wendy.seleccion",
			new Color(78, 102, 145));
	private final ConfigColor colorDiff = ConfigColor.de("tema.git_avanzado.wendy.diff", new Color(21, 26, 38));
	private final ConfigColor colorBoton = ConfigColor.de("tema.git_avanzado.wendy.boton", new Color(61, 104, 158));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		if (panelRaiz == null) {
			return;
		}

		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color panelSecundario = colorPanelSecundario.obtener();
		Color texto = colorTexto.obtener();
		Color textoSuave = colorTextoSuave.obtener();
		Color azul = colorAzul.obtener();
		Color rosa = colorRosa.obtener();
		Color borde = colorBorde.obtener();
		Color tabla = colorTabla.obtener();
		Color seleccion = colorSeleccion.obtener();
		Color diff = colorDiff.obtener();
		Color boton = colorBoton.obtener();

		panelRaiz.setBackground(fondo);
		aplicarTema(panelRaiz, fondo, panel, panelSecundario, texto, textoSuave, borde, tabla, seleccion, boton);

		if (panelCabecera != null) {
			panelCabecera.setBackground(fondo);
			panelCabecera.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(rosa, 2),
					BorderFactory.createEmptyBorder(6, 6, 6, 6)));
		}
		if (panelBarra != null) {
			panelBarra.setBackground(fondo);
		}
		if (panelEstado != null) {
			panelEstado.setBackground(fondo);
			panelEstado.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, azul));
		}
		if (etiquetaSerie != null) {
			etiquetaSerie.setForeground(rosa);
			etiquetaSerie.setFont(etiquetaSerie.getFont().deriveFont(Font.BOLD, 18f));
		}
		if (etiquetaRepositorio != null)
			etiquetaRepositorio.setForeground(texto);
		if (etiquetaRama != null)
			etiquetaRama.setForeground(textoSuave);
		if (etiquetaEstado != null)
			etiquetaEstado.setForeground(textoSuave);

		estilizarTabla(tablaHistorial, tabla, texto, seleccion, azul);
		estilizarTabla(tablaNoPreparados, tabla, texto, seleccion, rosa);
		estilizarTabla(tablaPreparados, tabla, texto, seleccion, azul);

		estilizarArea(areaMensajeCommit, panelSecundario, texto, borde, false);
		estilizarArea(areaDiff, diff, texto, azul, true);
		estilizarArea(areaBlame, diff, textoSuave, rosa, true);
		estilizarArea(areaActividad, diff, textoSuave, borde, true);

		actualizarBordesTitulados(panelRaiz, borde, texto);
		cargarImagen();
		revalidate();
		repaint();
	}

	private void aplicarTema(Container contenedor, Color fondo, Color panel, Color panelSecundario, Color texto,
			Color textoSuave, Color borde, Color tabla, Color seleccion, Color boton) {
		for (Component componente : contenedor.getComponents()) {
			if (componente instanceof JPanel) {
				componente.setBackground(panel);
			}
			if (componente instanceof JLabel) {
				componente.setForeground(texto);
			}
			if (componente instanceof JButton) {
				JButton b = (JButton) componente;
				if (!CrashDetectorGUI.esMac()) {
					b.setBackground(boton);
					b.setForeground(texto);
					b.setOpaque(true);
				}
				b.setFocusPainted(false);
				b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borde),
						BorderFactory.createEmptyBorder(5, 9, 5, 9)));
			}
			if (componente instanceof JComboBox) {
				componente.setBackground(panelSecundario);
				if (!CrashDetectorGUI.esMac()) {
					componente.setForeground(texto);
				}
			}
			if (componente instanceof JTextField) {
				componente.setBackground(panelSecundario);
				componente.setForeground(texto);
			}
			if (componente instanceof JTabbedPane) {
				componente.setBackground(panel);
				componente.setForeground(texto);
			}
			if (componente instanceof JScrollPane) {
				JScrollPane scroll = (JScrollPane) componente;
				scroll.setBackground(panel);
				scroll.getViewport().setBackground(tabla);
				scroll.setBorder(BorderFactory.createLineBorder(borde));
			}
			if (componente instanceof JViewport) {
				componente.setBackground(tabla);
			}
			if (componente instanceof Container) {
				aplicarTema((Container) componente, fondo, panel, panelSecundario, texto, textoSuave, borde, tabla,
						seleccion, boton);
			}
		}
	}

	private void estilizarTabla(JTable tabla, Color fondo, Color texto, Color seleccion, Color cabecera) {
		if (tabla == null) {
			return;
		}
		tabla.setBackground(fondo);
		tabla.setForeground(texto);
		tabla.setSelectionBackground(seleccion);
		tabla.setSelectionForeground(texto);
		tabla.setGridColor(colorBorde.obtener());
		tabla.getTableHeader().setBackground(cabecera);
		tabla.getTableHeader().setForeground(colorTexto.obtener());
	}

	private void estilizarArea(JTextArea area, Color fondo, Color texto, Color borde, boolean monoespaciada) {
		if (area == null) {
			return;
		}
		area.setBackground(fondo);
		area.setForeground(texto);
		area.setCaretColor(texto);
		area.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		if (monoespaciada) {
			area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		}
	}

	private void actualizarBordesTitulados(Container contenedor, Color borde, Color texto) {
		if (contenedor instanceof JComponent) {
			JComponent componente = (JComponent) contenedor;
			if (componente.getBorder() instanceof TitledBorder) {
				TitledBorder actual = (TitledBorder) componente.getBorder();
				componente.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borde),
						actual.getTitle(), TitledBorder.LEFT, TitledBorder.TOP, null, texto));
			}
		}
		for (Component hijo : contenedor.getComponents()) {
			if (hijo instanceof Container) {
				actualizarBordesTitulados((Container) hijo, borde, texto);
			}
		}
	}

	private void cargarImagen() {
		if (imagenTema == null) {
			return;
		}
		ImageIcon icono = null;
		URL recurso = getClass().getResource("/imagenes/wendy_mizumi.png");
		if (recurso != null) {
			icono = new ImageIcon(recurso);
		}
		if (icono == null || icono.getIconWidth() <= 0) {
			icono = new ImageIcon(Statics.carpeta.resolve("imagenes/wendy_mizumi.png").toString());
		}
		if (icono.getIconWidth() > 0) {
			Image imagen = icono.getImage().getScaledInstance(134, 150, Image.SCALE_SMOOTH);
			imagenTema.setIcon(new ImageIcon(imagen));
			imagenTema.setText("");
		} else {
			imagenTema.setIcon(null);
			imagenTema.setText(MonitorDePID.idioma.gitAvanzadoWendyImagenAlternativa());
			imagenTema.setForeground(colorTextoSuave.obtener());
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();
		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorPanel());
		colorPanelSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorPanelSecundario());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorTexto());
		colorTextoSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorTextoSuave());
		colorAzul.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorAzul());
		colorRosa.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorRosa());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorBorde());
		colorTabla.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorTabla());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorSeleccion());
		colorDiff.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorDiff());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.gitAvanzadoColorBoton());

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorPanelSecundario);
		elementos.add(colorTexto);
		elementos.add(colorTextoSuave);
		elementos.add(colorAzul);
		elementos.add(colorRosa);
		elementos.add(colorBorde);
		elementos.add(colorTabla);
		elementos.add(colorSeleccion);
		elementos.add(colorDiff);
		elementos.add(colorBoton);
		return elementos;
	}
}
