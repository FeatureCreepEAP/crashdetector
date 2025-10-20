package com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public class NoRegistroDeLauncherVShojo extends NoRegistroLanzadorGUI {

    private static final long serialVersionUID = 1L;

    public static String ID = "vshojo";

    @Override
    public void init() {
        // Inicializar colores PRIMERO
        colorFondoVentana = ConfigColor.de("tema.vshojo.no_registro.color.fondo.ventana", Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
        colorTexto = ConfigColor.de("tema.vshojo.no_registro.color.texto", Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
        colorBoton = ConfigColor.de("tema.vshojo.no_registro.color.boton", Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
        colorCajaTexto = ConfigColor.de("tema.vshojo.no_registro.color.caja_texto", Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
        colorEnlace = ConfigColor.de("tema.vshojo.no_registro.color.enlace", Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));

        // AHORA llamamos al init del padre
        super.init();
    }

    @Override
    public void aplicarApariencia() {
        // Contenedor raíz y todos los paneles estructurales
        getContentPane().setBackground(colorFondoVentana.obtener());
        if (raizPanel != null)
            raizPanel.setBackground(colorFondoVentana.obtener());
        if (encabezadoPanel != null)
            encabezadoPanel.setBackground(colorFondoVentana.obtener());
        if (centroPanel != null)
            centroPanel.setBackground(colorFondoVentana.obtener());
        if (panelImagenYBoton != null)
            panelImagenYBoton.setBackground(colorFondoVentana.obtener());
        if (piePanel != null)
            piePanel.setBackground(colorFondoVentana.obtener());
        if (botonesPanel != null)
            botonesPanel.setBackground(colorFondoVentana.obtener());

        // Descripción (scroll + editor)
        if (descScroll != null) {
            descScroll.getViewport().setBackground(colorCajaTexto.obtener());
            descScroll.setBorder(javax.swing.BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1));
        }
        if (descripcionHtml != null) {
            descripcionHtml.setOpaque(true);
            descripcionHtml.setBackground(colorCajaTexto.obtener());
        }

        // Imagen / contenedores
        if (imagenLbl != null) {
            imagenLbl.setBackground(colorFondoVentana.obtener());
            imagenLbl.setBorder(javax.swing.BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1));
        }
        if (panelBajoImagen != null)
            panelBajoImagen.setBackground(colorFondoVentana.obtener());

        // Área de pegado
        if (panelAreaTexto != null) {
            panelAreaTexto.setBackground(colorFondoVentana.obtener());
            panelAreaTexto.setBorder(bordeTitulado(MonitorDePID.idioma.pegaLosRegistrosAqui()));
        }
        if (areaTexto != null) {
            areaTexto.setBackground(colorCajaTexto.obtener());
            areaTexto.setForeground(colorEnlace.obtener());
        }

        // Pie
        if (vshojoLbl != null)
            vshojoLbl.setForeground(colorTexto.obtener());

        // Botones explícitamente opacos (macOS / L&F)
        if (seleccionarCarpetaBtn != null)
            estilizarBoton(seleccionarCarpetaBtn);
        if (botonGuardar != null)
            estilizarBoton(botonGuardar, 4);
        if (botonOmitir != null)
            estilizarBoton(botonOmitir, 4);
        if (botonProxy != null)
            estilizarBoton(botonProxy, 4);

        // Combos
        if (comboBoxIdioma != null)
            estilizarCombo(comboBoxIdioma);
        if (selector != null)
            estilizarCombo(selector);

        // Forzar repaint
        revalidate();
        repaint();
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public List<ElementoConfig> obtenerElementosConfigs() {
        List<ElementoConfig> elementos = new ArrayList<>();
        elementos.add(colorFondoVentana);
        elementos.add(colorTexto);
        elementos.add(colorBoton);
        elementos.add(colorCajaTexto);
        elementos.add(colorEnlace);
        return elementos;
    }
}