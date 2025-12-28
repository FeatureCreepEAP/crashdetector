package com.asbestosstar.crashdetector.gui.tipos.editor_plantilla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación predeterminada del editor de plantilla. Maneja la apariencia y
 * diseño de la interfaz.
 */
public class EditorPlantillaPredeterminado extends EditorPlantilla {

	public static final String ID = "editor_plantilla_predeterminado";

	// Mapa de colores específico para esta implementación
	private Map<String, ConfigColor> coloresEditor = new HashMap<>();

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void inicializarComponentes() {
		setLayout(new BorderLayout());

		// Colores predeterminados para esta implementación
		inicializarColoresEditor();

		// Panel superior con botones
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelSuperior.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panelSuperior.setBackground(coloresEditor.get("fondo").obtener());

		botonGuardar = new JButton(MonitorDePID.idioma.guardarTodo());
		configurarBoton(botonGuardar);
		botonGuardar.addActionListener(e -> guardarPlantilla());
		panelSuperior.add(botonGuardar);

		botonRestablecerPlantilla = new JButton(MonitorDePID.idioma.restablecerPlantilla());
		configurarBoton(botonRestablecerPlantilla);
		botonRestablecerPlantilla.addActionListener(e -> restablecerPlantilla());
		panelSuperior.add(botonRestablecerPlantilla);

		botonCerrar = new JButton(MonitorDePID.idioma.omitirYCerrar());
		configurarBoton(botonCerrar);
		botonCerrar.addActionListener(e -> cerrarEditor());
		panelSuperior.add(botonCerrar);

		add(panelSuperior, BorderLayout.NORTH);

		// Panel principal (división horizontal)
		JSplitPane splitPanePrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPanePrincipal.setDividerLocation(0.65);
		splitPanePrincipal.setBackground(coloresEditor.get("borde").obtener());

		// Panel izquierdo: editor y vista previa (editor más alto)
		JSplitPane splitPaneEditor = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPaneEditor.setDividerLocation(0.75); // Editor ocupa 75% del espacio vertical
		splitPaneEditor.setBackground(coloresEditor.get("borde").obtener());

		// Editor HTML
		JPanel panelEditor = new JPanel(new BorderLayout());
		panelEditor.setBorder(BorderFactory.createTitledBorder("Editor HTML"));
		panelEditor.setBackground(coloresEditor.get("fondo").obtener());

		editorHTML = new JTextPane();
		editorHTML.setFont(new Font("Monospaced", Font.PLAIN, 13));
		editorHTML.setCaretColor(coloresEditor.get("texto").obtener());
		editorHTML.setForeground(coloresEditor.get("texto").obtener());
		editorHTML.setBackground(coloresEditor.get("caja_texto").obtener());

		JScrollPane scrollEditor = new JScrollPane(editorHTML);
		scrollEditor.setBorder(BorderFactory.createLineBorder(coloresEditor.get("borde").obtener(), 1));
		panelEditor.add(scrollEditor, BorderLayout.CENTER);

		// Vista previa
		JPanel panelVistaPrevia = new JPanel(new BorderLayout());
		panelVistaPrevia.setBorder(BorderFactory.createTitledBorder("Vista Previa"));
		panelVistaPrevia.setBackground(coloresEditor.get("fondo").obtener());

		vistaPrevia = new JEditorPane();
		vistaPrevia.setEditable(false);
		vistaPrevia.setContentType("text/html");
		vistaPrevia.setBackground(Color.WHITE);

		JScrollPane scrollVistaPrevia = new JScrollPane(vistaPrevia);
		scrollVistaPrevia.setBorder(BorderFactory.createLineBorder(coloresEditor.get("borde").obtener(), 1));
		panelVistaPrevia.add(scrollVistaPrevia, BorderLayout.CENTER);

		splitPaneEditor.setTopComponent(panelEditor);
		splitPaneEditor.setBottomComponent(panelVistaPrevia);

		// Panel derecho: configuración de colores, enlaces e imágenes
		panelConfiguracion = new JPanel(new BorderLayout());
		panelConfiguracion.setBorder(BorderFactory.createTitledBorder("Configuración de Colores e Imágenes"));
		panelConfiguracion.setBackground(coloresEditor.get("fondo").obtener());

		splitPanePrincipal.setContinuousLayout(true);
		splitPanePrincipal.setResizeWeight(0.5); // reparto proporcional al redimensionar
		// mínimos razonables para que la derecha no “empuje” a la izquierda
		panelConfiguracion.setMinimumSize(new Dimension(340, 200));
		splitPaneEditor.setMinimumSize(new Dimension(420, 200));

		// Panel de colores
		JPanel panelColores = new JPanel(new GridLayout(0, 1, 5, 5));
		panelColores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelColores.setBackground(coloresEditor.get("fondo").obtener());
		inicializarConfiguracionColores(panelColores);

		// Panel de enlaces para imágenes del reporte compartido (NUEVO)
		JPanel panelEnlaces = new JPanel(new GridLayout(0, 1, 5, 5));
		panelEnlaces.setBorder(BorderFactory.createTitledBorder("Enlaces de imágenes (reporte compartido)"));
		panelEnlaces.setBackground(coloresEditor.get("fondo").obtener());
		inicializarConfiguracionEnlaces(panelEnlaces);

		// Panel de imágenes con ruta
		JPanel panelImagenes = new JPanel(new BorderLayout());
		String rutaFormateada = Statics.carpeta.resolve("imagenes").toString().replace("\\", "/");
		panelImagenes.setBorder(BorderFactory.createTitledBorder("Imágenes (" + rutaFormateada + ")"));
		panelImagenes.setBackground(coloresEditor.get("fondo").obtener());

		JPanel panelContenidoImagenes = new JPanel(new GridLayout(0, 1, 5, 5));
		panelContenidoImagenes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelContenidoImagenes.setBackground(coloresEditor.get("fondo").obtener());

		// Usar el método de la clase abstracta para obtener los nombres de imágenes
		for (String imagen : obtenerNombresImágenesVTuber()) {
			panelContenidoImagenes.add(crearPanelImagen(imagen));
		}
		panelImagenes.add(panelContenidoImagenes, BorderLayout.CENTER);

		// Apilar paneles en la derecha
		JPanel derechaStack = new JPanel(new GridLayout(0, 1, 8, 8));
		derechaStack.setBackground(coloresEditor.get("fondo").obtener());
		derechaStack.add(panelColores);
		derechaStack.add(panelEnlaces); // <-- NUEVO bloque de enlaces
		derechaStack.add(panelImagenes);

		panelConfiguracion.add(derechaStack, BorderLayout.CENTER);

		splitPanePrincipal.setLeftComponent(splitPaneEditor);
		splitPanePrincipal.setRightComponent(panelConfiguracion);

		add(splitPanePrincipal, BorderLayout.CENTER);

		// Listener para actualizar vista previa
		editorHTML.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				actualizarVistaPrevia();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				actualizarVistaPrevia();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				actualizarVistaPrevia();
			}
		});

		// Listener para resaltar sintaxis
		editorHTML.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				resaltarSintaxis();
			}
		});

		resaltarSintaxis();
	}

	private void inicializarColoresEditor() {
		// Inicializar colores específicos para esta implementación usando el método de
		coloresEditor.put("fondo", ConfigColor.de("color_fondo_editor_plantilla", new Color(240, 240, 245)));
		coloresEditor.put("texto", ConfigColor.de("color_texto_editor_plantilla", new Color(50, 50, 50)));
		coloresEditor.put("caja_texto", ConfigColor.de("color_caja_texto_editor_plantilla", new Color(255, 255, 255)));
		coloresEditor.put("boton", ConfigColor.de("color_boton_editor_plantilla", new Color(220, 220, 230)));
		coloresEditor.put("borde", ConfigColor.de("color_borde_editor_plantilla", new Color(180, 180, 200)));
	}

	private void configurarBoton(JButton boton) {
		boton.setForeground(coloresEditor.get("texto").obtener());
		boton.setBackground(coloresEditor.get("boton").obtener());
		boton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		boton.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		boton.setFocusPainted(false);
	}

	@Override
	public void recargarApariencia() {
		// Actualizar la apariencia de todos los componentes
		SwingUtilities.updateComponentTreeUI(this);

		// También necesitamos configurar los colores nuevamente
		inicializarColoresEditor();

		// Configurar apariencia de los componentes
		if (getParent() != null) {
			inicializarComponentes();
			repaint();
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		ArrayList<ElementoConfig> configs = new ArrayList<>();

		// Colores fijos del editor: asignar nombres localizados
		ConfigColor fondo = coloresEditor.get("fondo");
		if (fondo != null) {
			fondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
			configs.add(fondo);
		}

		ConfigColor texto = coloresEditor.get("texto");
		if (texto != null) {
			texto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
			configs.add(texto);
		}

		ConfigColor cajaTexto = coloresEditor.get("caja_texto");
		if (cajaTexto != null) {
			cajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
			configs.add(cajaTexto);
		}

		ConfigColor boton = coloresEditor.get("boton");
		if (boton != null) {
			boton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
			configs.add(boton);
		}

		ConfigColor borde = coloresEditor.get("borde");
		if (borde != null) {
			borde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
			configs.add(borde);
		}

		// Elementos dinámicos: asumimos que ya tienen nombre o no lo requieren
		if (enlacesMap != null && !enlacesMap.isEmpty()) {
			configs.addAll(enlacesMap.values());
		}

		return configs;
	}
}
