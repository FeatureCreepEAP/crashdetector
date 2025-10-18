package com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador;

import java.awt.Color;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Implementación VShojo centrada en APARIENCIA.
 * - Lee la paleta desde Config (convertida a Color)
 * - Restablece estilos/colores en recargarApariencia()
 * - En macOS fuerza opaco para que se pinten los colores
 *
 * Llamado recomendado:
 *   NoRegistroDeLauncherVShojo d = new NoRegistroDeLauncherVShojo();
 *   d.preparar(ownerFrame, instant);   // build + theme (ANTES de init)
 *   d.init();                          // mostrar
 */
public class NoRegistroDeLauncherVShojo extends NoRegistroLanzadorGUI {

    private static final long serialVersionUID = 1L;

    public static String ID = "vshojo";

    // Paleta desde Config (se evalúa al cargar la clase)
    private static final Color COLOR_FONDO  = Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo());
    private static final Color COLOR_TEXTO  = Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto());
    private static final Color COLOR_BOTON  = Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton());
    private static final Color COLOR_CAJA   = Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto());
    private static final Color COLOR_ENLACE = Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace());

    // Constructor vacío (requerido)
    public NoRegistroDeLauncherVShojo() {}

    // ====== Apariencia ======
    @Override
    protected void aplicarApariencia() {
        // Contenedor raíz y todos los paneles estructurales
        getContentPane().setBackground(COLOR_FONDO);
        if (raizPanel != null)         raizPanel.setBackground(COLOR_FONDO);
        if (encabezadoPanel != null)   encabezadoPanel.setBackground(COLOR_FONDO);
        if (centroPanel != null)       centroPanel.setBackground(COLOR_FONDO);
        if (panelImagenYBoton != null) panelImagenYBoton.setBackground(COLOR_FONDO);
        if (piePanel != null)          piePanel.setBackground(COLOR_FONDO);
        if (botonesPanel != null)      botonesPanel.setBackground(COLOR_FONDO);

        // Descripción (scroll + editor)
        if (descScroll != null) {
            descScroll.getViewport().setBackground(COLOR_CAJA);
            descScroll.setBorder(javax.swing.BorderFactory.createLineBorder(COLOR_BOTON.darker(), 1));
        }
        if (descripcionHtml != null) {
            descripcionHtml.setOpaque(true);
            descripcionHtml.setBackground(COLOR_CAJA);
            // El contenido HTML ya usa htmlWrap() con la paleta de color base
        }

        // Imagen / contenedores
        if (imagenLbl != null) {
            imagenLbl.setBackground(COLOR_FONDO);
            imagenLbl.setBorder(javax.swing.BorderFactory.createLineBorder(COLOR_BOTON.darker(), 1));
        }
        if (panelBajoImagen != null) panelBajoImagen.setBackground(COLOR_FONDO);

        // Área de pegado
        if (panelAreaTexto != null) {
            panelAreaTexto.setBackground(COLOR_FONDO);
            panelAreaTexto.setBorder(bordeTitulado(MonitorDePID.idioma.pegaLosRegistrosAqui()));
        }
        if (areaTexto != null) {
            areaTexto.setBackground(COLOR_CAJA);
            areaTexto.setForeground(COLOR_ENLACE);
        }

        // Pie
        if (vshojoLbl != null) vshojoLbl.setForeground(COLOR_TEXTO);

        // Botones explícitamente opacos (macOS / L&F)
        if (seleccionarCarpetaBtn != null) estilizarBoton(seleccionarCarpetaBtn);
        if (botonGuardar != null)          estilizarBoton(botonGuardar, 4);
        if (botonOmitir != null)           estilizarBoton(botonOmitir, 4);
        if (botonProxy != null)            estilizarBoton(botonProxy, 4);

        // Combos
        if (comboBoxIdioma != null) estilizarCombo(comboBoxIdioma);
        if (selector != null)       estilizarCombo(selector);

        // Forzar repaint
        revalidate();
        repaint();
    }

    @Override protected Color colorFondoBase()     { return COLOR_FONDO; }
    @Override protected Color colorTextoBase()     { return COLOR_TEXTO; }
    @Override protected Color colorBotonBase()     { return COLOR_BOTON; }
    @Override protected Color colorCajaTextoBase() { return COLOR_CAJA; }
    @Override protected Color colorEnlaceBase()    { return COLOR_ENLACE; }

    @Override protected String overlayMensaje() { return "Descargando y preparando enlaces..."; }
    @Override protected String gifDescargaPath() { return "/imagenes/descargando.gif"; }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public void init() {
        // Mostrar el diálogo (preparar(...) debe llamarse antes)
        setVisible(true);
    }

    // Fuerza opacidad y colores en botones/combos (sobre-escribe helpers base)
    @Override
    protected void estilizarBoton(javax.swing.JButton btn, int paddingV) {
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(COLOR_TEXTO);
        btn.setFocusPainted(false);
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(paddingV, 18, paddingV, 18));
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    }

    @Override
    protected void estilizarCombo(javax.swing.JComboBox<?> combo) {
        combo.setOpaque(true);
        combo.setBackground(COLOR_BOTON);
        combo.setForeground(COLOR_TEXTO);
        combo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        combo.setPreferredSize(new java.awt.Dimension(220, 32));
        if (!com.asbestosstar.crashdetector.gui.CrashDetectorGUI.esMac()
            && comboBoxIdioma instanceof com.asbestosstar.crashdetector.gui.ComboIdiomasConIcono) {
            ((com.asbestosstar.crashdetector.gui.ComboIdiomasConIcono) comboBoxIdioma)
                    .aplicarColores(COLOR_BOTON, COLOR_TEXTO);
        }
    }
}
