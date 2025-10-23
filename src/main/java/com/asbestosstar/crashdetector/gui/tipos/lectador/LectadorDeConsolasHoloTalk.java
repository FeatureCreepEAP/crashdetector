package com.asbestosstar.crashdetector.gui.tipos.lectador;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public class LectadorDeConsolasHoloTalk extends LectadorDeConsolasGUI {

    public static String ID = "holotalk";

    private static final long serialVersionUID = 1L;

    

    
    public LectadorDeConsolasHoloTalk() {
    	
        super();
    
        colorFondo = ConfigColor.de("tema.holotalk.lectador.color.fondo", java.awt.Color.BLACK);
        colorTexto = ConfigColor.de("tema.holotalk.lectador.color.texto", java.awt.Color.WHITE);
           colorError = ConfigColor.de("tema.holotalk.lectador.color.error", new java.awt.Color(255, 165, 0));
          colorPila = ConfigColor.de("tema.holotalk.lectador.color.pila", java.awt.Color.BLUE);
          colorFondoPanel = ConfigColor.de("tema.holotalk.lectador.color.fondo.panel", new java.awt.Color(30, 30, 30));
        colorTextoPanel = ConfigColor.de("tema.holotalk.lectador.color.texto.panel", new java.awt.Color(200, 200, 200));
        colorTextoNegro = ConfigColor.de("tema.holotalk.lectador.color.texto.negro", java.awt.Color.BLACK);

    }

    @Override
    protected void aplicarApariencia() {
        // Solo apariencia, no datos
        repaint();
    }

    @Override
    protected String textoNormalLeyenda() {
        return "Texto normal";
    }

    @Override
    protected void instalarFondoApariencia(JLayeredPane capa) {
        FondoPanel fondo = new FondoPanel(MonitorDePID.carpeta.resolve("imagenes/kiara_ame.png").toString());
        fondo.setBounds(0, 0, getWidth(), getHeight());
        capa.add(fondo, JLayeredPane.DEFAULT_LAYER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (fondo != null)
                    fondo.setBounds(0, 0, getWidth(), getHeight());
            }
        });

        fondo.cargarAsincrono(pool, new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
    }

    private static class FondoPanel extends JPanel {
        private volatile java.awt.Image imagen;
        private final String ruta;

        public FondoPanel(String ruta) {
            this.ruta = ruta;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }

        public void cargarAsincrono(java.util.concurrent.ExecutorService pool, final Runnable whenLoadedOnEDT) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        imagen = ImageIO.read(new File(ruta));
                    } catch (Exception ex) {
                        CrashDetectorLogger.log("No se pudo cargar fondo: " + ex.getMessage());
                    }
                    SwingUtilities.invokeLater(whenLoadedOnEDT);
                }
            });
        }
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public void init() {
        // Inicializar todos los colores primero

        // Luego inicializar la interfaz
        super.init();
    }

    @Override
    public List<ElementoConfig> obtenerElementosConfigs() {
        List<ElementoConfig> elementos = new java.util.ArrayList<>();
        elementos.add(colorFondo);
        elementos.add(colorTexto);
        elementos.add(colorError);
        elementos.add(colorPila);
        elementos.add(colorFondoPanel);
        elementos.add(colorTextoPanel);
        elementos.add(colorTextoNegro);
        return elementos;
    }

    @Override
    public void procesarHipervinculo(String url) {
        try {
            String sinPrefijo = url.substring("lectador://".length());
            CrashDetectorLogger.log("sin prefijo " + sinPrefijo);
            int idx = sinPrefijo.lastIndexOf(":");
            if (idx == -1) {
                CrashDetectorLogger.logException(new IllegalArgumentException("URL de lectador inválida: " + url));
                return;
            }

            String rutaArchivo = sinPrefijo.substring(0, idx);
            int numeroLinea = Integer.parseInt(sinPrefijo.substring(idx + 1));
            CrashDetectorLogger.log("ruta " + rutaArchivo);

            Consola consolaSeleccionada = null;
            for (Consola c : MonitorDePID.consolas) {
                if (c.archivo.toString().equals(rutaArchivo)) {
                    consolaSeleccionada = c;
                    break;
                }
            }

            if (consolaSeleccionada == null) {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "No se encontró la consola para el archivo: " + rutaArchivo, "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            CrashDetectorLogger.log("seleccionada " + consolaSeleccionada.archivo.toString());

            final LectadorDeConsolasHoloTalk lector = new LectadorDeConsolasHoloTalk();
            lector.init();
            lector.setVisible(true);

            final String nombreArchivo = new File(consolaSeleccionada.archivo.toString()).getName();
            lector.cmbConsolas.setSelectedItem(nombreArchivo);

            final Consola consolaFinal = consolaSeleccionada;
            lector.pool.submit(new Runnable() {
                @Override
                public void run() {
                    java.util.List<String> lineas = lector.cacheLineasPorConsola.get(nombreArchivo);
                    if (lineas == null) {
                        lineas = java.util.Arrays.asList(consolaFinal.contenido_verificar.split(Verificaciones.nl));
                        lector.cacheLineasPorConsola.put(nombreArchivo, lineas);
                    }

                    final java.util.List<String> lineasFinal = lineas;
                    final int salto = Math.max(0, Math.min(numeroLinea, lineasFinal.size() - 1));

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            lector.refrescarModeloCon(lineasFinal);
                            try {
                                if (salto >= 0 && salto < lector.lineasActuales.size()) {
                                    lector.listaRegistros.setSelectedIndex(salto);
                                    lector.listaRegistros.ensureIndexIsVisible(salto);
                                    lector.listaRegistros.requestFocus();
                                    CrashDetectorLogger.log("línea seleccionada en JList: " + salto);
                                }
                            } catch (Exception ex) {
                                CrashDetectorLogger.logException(ex);
                            }
                        }
                    });
                }
            });

        } catch (Exception ex) {
            CrashDetectorLogger.logException(ex);
        }
    }
}