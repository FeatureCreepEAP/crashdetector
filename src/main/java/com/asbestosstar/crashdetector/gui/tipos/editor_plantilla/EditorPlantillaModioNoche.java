package com.asbestosstar.crashdetector.gui.tipos.editor_plantilla;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación del editor de plantilla con tema oscuro "Modio Noche".
 * Proporciona una interfaz adecuada para entornos con poca luz, con colores que
 * reducen la fatiga visual durante la edición nocturna.
 * 
 * Este tema utiliza ConfigColor para todos sus elementos de color, permitiendo
 * una personalización completa por parte del usuario sin hardcodear valores.
 */
public class EditorPlantillaModioNoche extends EditorPlantilla {

	public static final String ID = "editor_plantilla_modio_noche";

	// Mapa de colores específicos para el tema oscuro
	private Map<String, ConfigColor> coloresEditor = new HashMap<>();

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void inicializarComponentes() {
		setLayout(new BorderLayout());

		// Inicializar los colores del tema oscuro
		inicializarColoresEditor();

		// Panel superior con botones de acción
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

		// Panel principal con división horizontal
		JSplitPane splitPanePrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPanePrincipal.setDividerLocation(0.65);
		splitPanePrincipal.setBackground(coloresEditor.get("borde").obtener());

		// Panel izquierdo: editor y vista previa (editor más alto)
		JSplitPane splitPaneEditor = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPaneEditor.setDividerLocation(0.75);
		splitPaneEditor.setBackground(coloresEditor.get("borde").obtener());

		// Editor HTML con tema oscuro
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

		// Vista previa con fondo oscuro
		JPanel panelVistaPrevia = new JPanel(new BorderLayout());
		panelVistaPrevia.setBorder(BorderFactory.createTitledBorder("Vista Previa"));
		panelVistaPrevia.setBackground(coloresEditor.get("fondo").obtener());

		vistaPrevia = new JEditorPane();
		vistaPrevia.setEditable(false);
		vistaPrevia.setContentType("text/html");
		vistaPrevia.setBackground(coloresEditor.get("fondo_vista_previa").obtener());
		vistaPrevia.setForeground(coloresEditor.get("texto").obtener());

		JScrollPane scrollVistaPrevia = new JScrollPane(vistaPrevia);
		scrollVistaPrevia.setBorder(BorderFactory.createLineBorder(coloresEditor.get("borde").obtener(), 1));
		panelVistaPrevia.add(scrollVistaPrevia, BorderLayout.CENTER);

		splitPaneEditor.setTopComponent(panelEditor);
		splitPaneEditor.setBottomComponent(panelVistaPrevia);

		// Panel derecho: configuración de colores e imágenes
		panelConfiguracion = new JPanel(new BorderLayout());
		panelConfiguracion.setBorder(BorderFactory.createTitledBorder("Configuración de Colores e Imágenes"));
		panelConfiguracion.setBackground(coloresEditor.get("fondo").obtener());

		// Panel de colores con tema oscuro
		JPanel panelColores = new JPanel(new GridLayout(0, 1, 5, 5));
		panelColores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelColores.setBackground(coloresEditor.get("fondo").obtener());

		// Inicializar la configuración de colores usando el método de la clase
		// abstracta
		inicializarConfiguracionColores(panelColores);

		// Panel de imágenes con ruta formateada
		JPanel panelImagenes = new JPanel(new BorderLayout());

		String rutaFormateada = MonitorDePID.carpeta.resolve("imagenes").toString().replace("\\", "/");
		panelImagenes.setBorder(BorderFactory.createTitledBorder("Imágenes (" + rutaFormateada + ")"));
		panelImagenes.setBackground(coloresEditor.get("fondo").obtener());

		JPanel panelContenidoImagenes = new JPanel(new GridLayout(0, 1, 5, 5));
		panelContenidoImagenes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelContenidoImagenes.setBackground(coloresEditor.get("fondo").obtener());

		// Agregar los paneles de imágenes usando los nombres obtenidos
		for (String imagen : obtenerNombresImágenesVTuber()) {
			panelContenidoImagenes.add(crearPanelImagen(imagen));
		}

		panelImagenes.add(panelContenidoImagenes, BorderLayout.CENTER);

		panelConfiguracion.add(panelColores, BorderLayout.CENTER);
		panelConfiguracion.add(panelImagenes, BorderLayout.SOUTH);

		splitPanePrincipal.setLeftComponent(splitPaneEditor);
		splitPanePrincipal.setRightComponent(panelConfiguracion);

		add(splitPanePrincipal, BorderLayout.CENTER);

		// Listener para actualizar vista previa cuando el contenido cambia
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

		// Listener para resaltar sintaxis cuando se escribe en el editor
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

	/**
	 * Inicializa los colores específicos para el tema oscuro "Modio Noche". Define
	 * todos los colores como ConfigColor para permitir personalización completa.
	 */
	private void inicializarColoresEditor() {
		// Colores básicos del tema
		coloresEditor.put("fondo",
				ConfigColor.de("color_fondo_editor_plantilla_modio_noche", new Color(0x1e, 0x1e, 0x1e)));
		coloresEditor.put("texto",
				ConfigColor.de("color_texto_editor_plantilla_modio_noche", new Color(0xe0, 0xe0, 0xe0)));
		coloresEditor.put("caja_texto",
				ConfigColor.de("color_caja_texto_editor_plantilla_modio_noche", new Color(0x2d, 0x2d, 0x2d)));
		coloresEditor.put("boton",
				ConfigColor.de("color_boton_editor_plantilla_modio_noche", new Color(0x2a, 0x2a, 0x2a)));
		coloresEditor.put("borde",
				ConfigColor.de("color_borde_editor_plantilla_modio_noche", new Color(0x40, 0x40, 0x40)));
		coloresEditor.put("fondo_vista_previa",
				ConfigColor.de("color_fondo_vista_previa_modio_noche", new Color(0x1e, 0x1e, 0x1e)));

		// Colores para resaltado de sintaxis
		coloresEditor.put("sintaxis_constructor",
				ConfigColor.de("color_sintaxis_constructor_modio_noche", new Color(0x00, 0xcc, 0xff)));
		coloresEditor.put("sintaxis_mensaje_ayudar",
				ConfigColor.de("color_sintaxis_mensaje_ayudar_modio_noche", new Color(0xff, 0x66, 0xff)));
		coloresEditor.put("sintaxis_etiquetas_html",
				ConfigColor.de("color_sintaxis_etiquetas_html_modio_noche", new Color(0xff, 0xcc, 0x00)));
	}

	/**
	 * Configura los botones con el estilo del tema oscuro. Ajusta el color de
	 * texto, fondo, fuente y borde para mantener la coherencia visual.
	 * 
	 * @param boton El botón a configurar con el estilo oscuro
	 */
	private void configurarBoton(JButton boton) {
		boton.setForeground(coloresEditor.get("texto").obtener());
		boton.setBackground(coloresEditor.get("boton").obtener());
		boton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		boton.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		boton.setFocusPainted(false);
		boton.setOpaque(true);
	}

	@Override
	public void recargarApariencia() {
		// Actualizar la apariencia de todos los componentes
		SwingUtilities.updateComponentTreeUI(this);

		// Reinicializar los colores del tema oscuro
		inicializarColoresEditor();

		// Volver a configurar los componentes con los nuevos colores
		if (getParent() != null) {
			inicializarComponentes();
			repaint();
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		ArrayList<ElementoConfig> configs = new ArrayList<>();
		configs.add(coloresEditor.get("fondo"));
		configs.add(coloresEditor.get("texto"));
		configs.add(coloresEditor.get("caja_texto"));
		configs.add(coloresEditor.get("boton"));
		configs.add(coloresEditor.get("borde"));
		configs.add(coloresEditor.get("fondo_vista_previa"));
		configs.add(coloresEditor.get("sintaxis_constructor"));
		configs.add(coloresEditor.get("sintaxis_mensaje_ayudar"));
		configs.add(coloresEditor.get("sintaxis_etiquetas_html"));
		return configs;
	}

	/**
	 * Resalta la sintaxis con colores definidos en ConfigColor. Asegura que los
	 * elementos clave sean visibles y diferenciables.
	 */
	@Override
	public void resaltarSintaxis() {
		if (actualizandoVista)
			return;
		actualizandoVista = true;

		StyledDocument doc = editorHTML.getStyledDocument();
		String text = editorHTML.getText();

		// Estilo normal para texto sin formato
		SimpleAttributeSet normal = new SimpleAttributeSet();
		StyleConstants.setForeground(normal, coloresEditor.get("texto").obtener());
		doc.setCharacterAttributes(0, text.length(), normal, true);

		// Resaltar {constructor} con color azul cian
		int inicio = 0;
		while ((inicio = text.indexOf("{constructor}", inicio)) != -1) {
			SimpleAttributeSet style = new SimpleAttributeSet();
			StyleConstants.setForeground(style, coloresEditor.get("sintaxis_constructor").obtener());
			StyleConstants.setBold(style, true);
			doc.setCharacterAttributes(inicio, 13, style, false);
			inicio += 13;
		}

		// Resaltar {mensaje_ayudar} con color magenta
		inicio = 0;
		while ((inicio = text.indexOf("{mensaje_ayudar}", inicio)) != -1) {
			SimpleAttributeSet style = new SimpleAttributeSet();
			StyleConstants.setForeground(style, coloresEditor.get("sintaxis_mensaje_ayudar").obtener());
			StyleConstants.setBold(style, true);
			doc.setCharacterAttributes(inicio, 16, style, false);
			inicio += 16;
		}

		// Resaltar etiquetas HTML con color amarillo
		inicio = 0;
		while (inicio < text.length()) {
			int abertura = text.indexOf("<", inicio);
			if (abertura == -1)
				break;

			int cierre = text.indexOf(">", abertura);
			if (cierre == -1)
				break;

			SimpleAttributeSet style = new SimpleAttributeSet();
			StyleConstants.setForeground(style, coloresEditor.get("sintaxis_etiquetas_html").obtener());
			doc.setCharacterAttributes(abertura, cierre - abertura + 1, style, false);

			inicio = cierre + 1;
		}

		actualizandoVista = false;
	}

	/**
	 * Actualiza la vista previa con el tema oscuro. Modifica los estilos para
	 * asegurar que se vea bien con el fondo oscuro.
	 */
	@Override
	public void actualizarVistaPrevia() {
		if (actualizandoVista)
			return;
		actualizandoVista = true;

		vistaPrevia.removeAll();

		String contenido = editorHTML.getText();
		String colorError = configuracion.obtenerColorError();
		String colorAdvertencia = configuracion.obtenerColorAdvertencia();
		String colorInfo = configuracion.obtenerColorInfo();
		String colorTitulo = configuracion.obtenerColorTitulo();
		String colorTitulosConsolas = configuracion.obtenerColorDeTitulosDeConsolas();
		String colorEnlace = configuracion.obtenerColorEnlace();

		// Ejemplo de análisis con estilos para tema oscuro
		String ejemploAnalisis = "<div style='color:#" + colorTitulo
				+ "; font-weight: bold; margin-bottom: 10px;'>Ejemplo de Análisis</div>" + "<div style='color:#"
				+ colorTitulosConsolas + "; font-weight: bold; margin-bottom: 5px;'>Título de Consola de Ejemplo</div>"
				+ "<div style='color:#" + colorError
				+ "'>[EJEMPLO] Error crítico: No se pudo cargar el mod 'ExampleMod'</div>" + "<div style='color:#"
				+ colorAdvertencia + "'>[EJEMPLO] Advertencia: Conflictos entre mods detectados</div>"
				+ "<div style='color:#" + colorInfo
				+ "'>[EJEMPLO] Información: 5 soluciones potenciales encontradas</div>";

		// Mensaje de ayuda con estilo oscuro
		String ejemploAyuda = "<div style='color:#" + colorEnlace + "; margin-top: 20px;'>"
				+ "¿Necesitas ayuda? Usa el botón Compartir para obtener enlaces a los registros y a los resultados."
				+ "</div>";

		String contenidoVista = contenido.replace("{constructor}", ejemploAnalisis).replace("{mensaje_ayudar}",
				ejemploAyuda);

		// Agregar estilos CSS para el tema oscuro
		String htmlConEstilos = "<html><head><style>" + "body { background-color: "
				+ Config.colorAHexHtml(coloresEditor.get("fondo_vista_previa").obtener()) + "; " + "color: "
				+ Config.colorAHexHtml(coloresEditor.get("texto").obtener()) + "; }" + "a { color: " + colorEnlace
				+ "; text-decoration: underline; }" + "</style></head><body>" + contenidoVista + "</body></html>";

		vistaPrevia.setText(htmlConEstilos);
		vistaPrevia.setCaretPosition(0);

		actualizandoVista = false;
	}
}