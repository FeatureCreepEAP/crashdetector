package com.asbestosstar.crashdetector.gui.tipos.mcreator;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public class EscanerMCreatorGUIRosemiLoveLock extends EscanerMCreatorGUI {

    private static final long serialVersionUID = 1L;
    public static final String ID = "rosemi_lovelock";

    @Override
    public void init() {
        // Inicializar colores PRIMERO
        colorFondoVentana = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.fondo.ventana", new Color(248, 205, 205));
        colorTextoPrincipal = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.principal", Color.WHITE);
        colorFondoResultados = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.fondo.resultados", new Color(240, 200, 200));
        colorEstado = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.estado", new Color(230, 180, 180));
        colorBotonFondo = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.boton.fondo", new Color(220, 150, 150));
        colorBotonTexto = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.boton.texto", Color.WHITE);
        colorBordeScroll = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.borde.scroll", new Color(200, 150, 150));
        colorTextoDescripcion = ConfigColor.de("tema.rosemi_lovelock.mcreator.color.texto.descripcion", Color.WHITE);

        // AHORA llamamos al init del padre
        super.init();
    }

    @Override
    protected Font fuenteDescripcion() {
        return new Font("Segoe UI", Font.BOLD, 18);
    }

    @Override
    protected Font fuenteResultados() {
        return new Font("Segoe UI", Font.PLAIN, 13);
    }

    @Override
    protected Font fuenteBoton() {
        return new Font("Segoe UI", Font.BOLD, 16);
    }

    @Override
    protected ImageIcon iconoDecorativo() {
        File archivoImagen = MonitorDePID.carpeta.resolve("imagenes/rosemi.png").toFile();
        if (archivoImagen.exists()) {
            ImageIcon icon = new ImageIcon(archivoImagen.getPath());
            if (icon.getIconWidth() > 0)
                return icon;
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
        elementos.add(colorFondoVentana);
        elementos.add(colorTextoPrincipal);
        elementos.add(colorFondoResultados);
        elementos.add(colorEstado);
        elementos.add(colorBotonFondo);
        elementos.add(colorBotonTexto);
        elementos.add(colorBordeScroll);
        elementos.add(colorTextoDescripcion);
        return elementos;
    }
}