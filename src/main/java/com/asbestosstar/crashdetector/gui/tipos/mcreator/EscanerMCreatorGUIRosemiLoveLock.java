package com.asbestosstar.crashdetector.gui.tipos.mcreator;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.ImageIcon;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Apariencia que replica EXACTAMENTE la GUI antigua:
 * - Fondo de ventana = colorFondo de Config
 * - Área de resultados: Consolas 12, fg (50,50,50), bg blanco
 * - Estado: gris (150,150,150)
 * - Botón: fondo = colorBoton de Config, texto = verde
 * - Título: "Escaner MCreator"
 * - Imagen rosemi.png escalada (200x112)
 * - Textos de estado: "cargando", "esta completdo", "error"
 */
public class EscanerMCreatorGUIRosemiLoveLock extends EscanerMCreatorGUI {

    private static final long serialVersionUID = 1L;
    public static final String ID = "rosemi_lovelock";

    // Colores tomados para igualar la GUI antigua
    private static Color cfgColor(String hex) {
        // helper if you ever want literals; not used now
        return Color.decode(hex);
    }
    private static Color colorFromConfig(java.util.function.Supplier<String> hex) {
        return Config.convertirAColor(hex.get());
    }

    // === Paleta EXACTA del viejo layout ===
    @Override protected Color colorFondoVentana()   { return colorFromConfig(() -> Config.obtenerInstancia().obtenerColorFondo()); }
    @Override protected Color colorTextoPrincipal()  { return new Color(50, 50, 50); }         // área de resultados (fg)
    @Override protected Color colorFondoResultados() { return Color.WHITE; }                   // área de resultados (bg)
    @Override protected Color colorEstado()          { return new Color(150, 150, 150); }      // etiqueta de estado
    @Override protected Color colorBotonFondo()      { return colorFromConfig(() -> Config.obtenerInstancia().obtenerColorBoton()); }
    @Override protected Color colorBotonTexto()      { return Color.GREEN; }                   // botón "Escanear"

    // === Fuentes EXACTAS del viejo layout ===
    @Override protected Font fuenteResultados()  { return new Font("Consolas", Font.PLAIN, 12); }
    @Override protected Font fuenteDescripcion() { return new Font("Segoe UI", Font.PLAIN, 14); }
    @Override protected Font fuenteBoton()       { return new Font("Segoe UI", Font.BOLD, 14); }

    // === Imagen decorativa (se escala a 200x112 en la base) ===
    @Override
    protected ImageIcon iconoDecorativo() {
        File archivoImagen = MonitorDePID.carpeta.resolve("imagenes/rosemi.png").toFile();
        if (archivoImagen.exists()) {
            ImageIcon icon = new ImageIcon(archivoImagen.getPath());
            if (icon.getIconWidth() > 0) return icon;
        }
        return null;
    }

    // === Textos (replican los de la clase antigua) ===
    @Override protected String textoEstadoCargando()   { return "cargando"; }
    @Override protected String textoEstadoCompletado() { return "esta completdo"; } // coincide con el texto/typo original
    @Override protected String textoEstadoError()      { return "error"; }
    @Override protected String tituloVentanaNoLocalizado() { return "Escaner MCreator"; }

    @Override
    public String id() { return ID; }
}
