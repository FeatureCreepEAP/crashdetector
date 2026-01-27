package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Ventana base abstracta (JFrame) para mostrar UN ÚNICO QuickFix.
 * 
 * Ahora es una JFrame independiente, no un JPanel.
 */
public abstract class QuickFixGUI extends JFrame implements CrashDetectorGUI {

	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<QuickFixGUI>> GUIS = new HashMap<>();

	protected QuickFix quickFixActual;
	protected Component contenidoPrincipal;

	// ====== Colores configurables ======
	public ConfigColor colorFondo = ConfigColor.de("quickfix_fondo", java.awt.Color.WHITE);
	public ConfigColor colorTexto = ConfigColor.de("quickfix_texto", java.awt.Color.BLACK);
	public ConfigColor colorBorde = ConfigColor.de("quickfix_borde", java.awt.Color.GRAY);
	public ConfigColor colorResaltado = ConfigColor.de("quickfix_resaltado", new java.awt.Color(255, 230, 0));

	// Panel interno que contiene el contenido real (BoxLayout)
	protected JPanel panelContenido;

	public QuickFixGUI() {
		super();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.panelContenido = new JPanel();
		this.add(this.panelContenido);
	}

	/**
	 * Construye y muestra la interfaz para el QuickFix dado.
	 */
	public void constructir(QuickFix fix) {
		if (fix == null) {
			CrashDetectorLogger.log("Advertencia: intento de construir QuickFixGUI con fix nulo");
			return;
		}

		this.quickFixActual = fix;

		// Limpiar panel interno
		this.panelContenido.removeAll();

		// Aplicar apariencia base al panel interno
		aplicarAparienciaBase();

		// Crear contenido específico
		this.contenidoPrincipal = crearContenido(fix);
		if (this.contenidoPrincipal != null) {
			this.panelContenido.add(this.contenidoPrincipal);
		}

		// Aplicar apariencia final
		aplicarApariencia();

		// Ajustar ventana
		this.pack();
		this.setLocationRelativeTo(null); // centrar
		this.setVisible(true);
	}

	public QuickFix obtenerQuickFixActual() {
		return this.quickFixActual;
	}

	// ====== Métodos abstractos (igual que antes) ======

	protected abstract Component crearContenido(QuickFix fix);
	protected abstract void aplicarApariencia();
	protected abstract String rutaImagenDecorativa();
	protected abstract int[] dimensionesImagenDecorativa();

	/**
	 * Aplica layout y estilo básico al panel interno (NO a la JFrame).
	 */
	protected void aplicarAparienciaBase() {
		this.panelContenido.setLayout(new BoxLayout(this.panelContenido, BoxLayout.Y_AXIS));
		this.panelContenido.setOpaque(true);
		this.panelContenido.setBorder(new EmptyBorder(8, 12, 8, 12));
	}

	protected JLabel crearEtiquetaImagenEscalada(String ruta, int ancho, int alto) {
		if (ruta == null || ruta.isEmpty()) return null;
		try {
			File archivo = new File(ruta);
			if (!archivo.exists() || !archivo.isFile()) return null;
			Image imagenOriginal = ImageIO.read(archivo);
			if (imagenOriginal == null) return null;
			Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
			JLabel etiqueta = new JLabel(new ImageIcon(imagenEscalada));
			etiqueta.setBorder(new EmptyBorder(4, 0, 4, 0));
			return etiqueta;
		} catch (IOException e) {
			CrashDetectorLogger.log("Error cargando imagen decorativa: " + ruta);
			return null;
		}
	}

	protected String formatearTexto(String texto) {
		if (texto == null || texto.trim().isEmpty()) return "";
		return texto; // HTML ya se maneja en las subclases si se desea
	}

	// ====== CrashDetectorGUI ======

	@Override
	public final void init() {
		CrashDetectorLogger.log("Advertencia: llamada a init() en QuickFixGUI. Usar constructir(QuickFix).");
	}

	@Override
	public void recargarApariencia() {
		if (this.quickFixActual != null) {
			constructir(this.quickFixActual);
		} else {
			aplicarApariencia();
		}
	}

	@Override
	public TipoGUI<QuickFixGUI> tipo() {
		return TipoGUI.QUICKFIX;
	}

	@Override
	public abstract String id();
}