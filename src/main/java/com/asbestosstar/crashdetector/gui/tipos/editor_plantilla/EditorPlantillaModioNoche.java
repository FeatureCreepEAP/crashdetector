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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigString;
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

		// Panel derecho: configuración de colores, imágenes y ENLACES DE IMÁGENES
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
		String rutaFormateada = Statics.carpeta.resolve("imagenes").toString().replace("\\", "/");
		panelImagenes.setBorder(BorderFactory.createTitledBorder("Imágenes (" + rutaFormateada + ")"));
		panelImagenes.setBackground(coloresEditor.get("fondo").obtener());

		JPanel panelContenidoImagenes = new JPanel(new GridLayout(0, 1, 5, 5));
		panelContenidoImagenes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelContenidoImagenes.setBackground(coloresEditor.get("fondo").obtener());

		for (String imagen : obtenerNombresImágenesVTuber()) {
			panelContenidoImagenes.add(crearPanelImagen(imagen));
		}
		panelImagenes.add(panelContenidoImagenes, BorderLayout.CENTER);

		// === NUEVO: Panel de ENLACES para imágenes del reporte compartido ===
		JPanel panelEnlaces = new JPanel(new GridLayout(0, 1, 6, 6));
		panelEnlaces.setBorder(BorderFactory.createTitledBorder("Enlaces de imágenes (reportes compartidos)"));
		panelEnlaces.setBackground(coloresEditor.get("fondo").obtener());

		// Campos ligados a ConfigString (persisten)
		ConfigString cfgGura = ConfigString.de("enlace_imagen_gura",
				"http://asbestosstar.egoism.jp/crash_detector/gura.png");
		ConfigString cfgMumei = ConfigString.de("enlace_imagen_mumei",
				"http://asbestosstar.egoism.jp/crash_detector/nanashi_mumei.png");
		ConfigString cfgShion = ConfigString.de("enlace_imagen_shion",
				"http://asbestosstar.egoism.jp/crash_detector/shion.png");

		panelEnlaces.add(crearFilaEnlace("Gura", cfgGura));
		panelEnlaces.add(crearFilaEnlace("Nanashi Mumei", cfgMumei));
		panelEnlaces.add(crearFilaEnlace("Shion", cfgShion));

		// Armar la derecha
		JPanel derechaArriba = new JPanel(new BorderLayout());
		derechaArriba.setBackground(coloresEditor.get("fondo").obtener());
		derechaArriba.add(panelColores, BorderLayout.CENTER);

		JPanel derechaAbajo = new JPanel(new BorderLayout());
		derechaAbajo.setBackground(coloresEditor.get("fondo").obtener());
		derechaAbajo.add(panelImagenes, BorderLayout.CENTER);
		derechaAbajo.add(panelEnlaces, BorderLayout.SOUTH);

		JPanel derecha = new JPanel(new BorderLayout());
		derecha.setBackground(coloresEditor.get("fondo").obtener());
		derecha.add(derechaArriba, BorderLayout.CENTER);
		derecha.add(derechaAbajo, BorderLayout.SOUTH);

		// Hacer scroll en la derecha para que no expanda sin límite
		JScrollPane scrollDerecha = new JScrollPane(derecha);
		scrollDerecha.setBorder(BorderFactory.createEmptyBorder());
		panelConfiguracion.add(scrollDerecha, BorderLayout.CENTER);

		splitPanePrincipal.setLeftComponent(splitPaneEditor);
		splitPanePrincipal.setRightComponent(panelConfiguracion);

		// Mantener 50/50 y que ambas mitades crezcan por igual
		splitPanePrincipal.setContinuousLayout(true);
		splitPanePrincipal.setResizeWeight(0.5); // reparto proporcional al redimensionar
		// mínimos razonables para que la derecha no “empuje” a la izquierda
		panelConfiguracion.setMinimumSize(new Dimension(340, 200));
		splitPaneEditor.setMinimumSize(new Dimension(420, 200));

		add(splitPanePrincipal, BorderLayout.CENTER);

		// Colocar el divisor al 50% tras hacer layout
		SwingUtilities.invokeLater(() -> splitPanePrincipal.setDividerLocation(0.5));

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

		// Asignar nombres localizados y añadir a la lista
		ConfigColor fondo = coloresEditor.get("fondo");
		fondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		configs.add(fondo);

		ConfigColor texto = coloresEditor.get("texto");
		texto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		configs.add(texto);

		ConfigColor cajaTexto = coloresEditor.get("caja_texto");
		cajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		configs.add(cajaTexto);

		ConfigColor boton = coloresEditor.get("boton");
		boton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		configs.add(boton);

		ConfigColor borde = coloresEditor.get("borde");
		borde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		configs.add(borde);

		ConfigColor fondoVistaPrevia = coloresEditor.get("fondo_vista_previa");
		fondoVistaPrevia.establecerNombreParaMostrar(() -> MonitorDePID.idioma.fondoVistaPrevia());
		configs.add(fondoVistaPrevia);

		ConfigColor sintaxisConstructor = coloresEditor.get("sintaxis_constructor");
		sintaxisConstructor.establecerNombreParaMostrar(() -> MonitorDePID.idioma.sintaxisConstructor());
		configs.add(sintaxisConstructor);

		ConfigColor sintaxisMensajeAyudar = coloresEditor.get("sintaxis_mensaje_ayudar");
		sintaxisMensajeAyudar.establecerNombreParaMostrar(() -> MonitorDePID.idioma.sintaxisMensajeAyudar());
		configs.add(sintaxisMensajeAyudar);

		ConfigColor sintaxisEtiquetasHtml = coloresEditor.get("sintaxis_etiquetas_html");
		sintaxisEtiquetasHtml.establecerNombreParaMostrar(() -> MonitorDePID.idioma.sintaxisEtiquetasHtml());
		configs.add(sintaxisEtiquetasHtml);

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

		// Resaltar {constructor}
		int inicio = 0;
		while ((inicio = text.indexOf("{constructor}", inicio)) != -1) {
			SimpleAttributeSet style = new SimpleAttributeSet();
			StyleConstants.setForeground(style, coloresEditor.get("sintaxis_constructor").obtener());
			StyleConstants.setBold(style, true);
			doc.setCharacterAttributes(inicio, 13, style, false);
			inicio += 13;
		}

		// Resaltar {mensaje_ayudar}
		inicio = 0;
		while ((inicio = text.indexOf("{mensaje_ayudar}", inicio)) != -1) {
			SimpleAttributeSet style = new SimpleAttributeSet();
			StyleConstants.setForeground(style, coloresEditor.get("sintaxis_mensaje_ayudar").obtener());
			StyleConstants.setBold(style, true);
			doc.setCharacterAttributes(inicio, 16, style, false);
			inicio += 16;
		}

		// Resaltar etiquetas HTML
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
		String htmlConEstilos = "<html><head><meta charset='UTF-8'><style>" + "body { background-color: "
				+ Config.colorAHexHtml(coloresEditor.get("fondo_vista_previa").obtener()) + "; color: "
				+ Config.colorAHexHtml(coloresEditor.get("texto").obtener()) + "; }" + "a { color: " + colorEnlace
				+ "; text-decoration: underline; }" + "</style></head><body>" + contenidoVista + "</body></html>";

		vistaPrevia.setText(htmlConEstilos);
		vistaPrevia.setCaretPosition(0);

		actualizandoVista = false;
	}

	// === Helpers ===

	private JPanel crearFilaEnlace(String etiqueta, ConfigString cfg) {
		JPanel fila = new JPanel(new BorderLayout(8, 0));
		fila.setBackground(coloresEditor.get("fondo").obtener());

		JLabel lbl = new JLabel(etiqueta + ":");
		lbl.setForeground(coloresEditor.get("texto").obtener());
		lbl.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));

		JTextField campo = new JTextField(cfg.obtener());
		campo.setForeground(coloresEditor.get("texto").obtener());
		campo.setBackground(coloresEditor.get("caja_texto").obtener());
		campo.setCaretColor(coloresEditor.get("texto").obtener());
		campo.setToolTipText("URL usada en reportes compartidos");

		// Persistir al vuelo cambios del usuario
		campo.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				escribir();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				escribir();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				escribir();
			}

			private void escribir() {
				try {
					cfg.escribir(campo.getText());
				} catch (Throwable ignored) {
				}
			}
		});

		fila.add(lbl, BorderLayout.WEST);
		fila.add(campo, BorderLayout.CENTER);
		return fila;
	}
}
