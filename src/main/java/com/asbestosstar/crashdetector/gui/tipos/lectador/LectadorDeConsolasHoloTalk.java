package com.asbestosstar.crashdetector.gui.tipos.lectador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;

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

/**
 * Implementación "HoloTalk" centrada en apariencia.
 * - Paleta de colores
 * - Fondo con imagen (kiara_ame.png)
 * - Textos NO localizados (p.ej. "Texto normal" en la leyenda)
 * 
 * Conserva método estático procesarHipervinculo(...) que instancia esta clase.
 */
public class LectadorDeConsolasHoloTalk extends LectadorDeConsolasGUI {

	public static String ID = "holotalk";
	
    private static final long serialVersionUID = 1L;

    // ====== Paleta (apariencia) ======
    private final Color COLOR_FONDO = new Color(17, 17, 17);
    private final Color COLOR_TEXTO = Color.WHITE;
    private final Color COLOR_ERROR = new Color(255, 165, 0);
    private final Color COLOR_PILA  = Color.BLUE;

    // Fondo cargado en segundo plano
    private FondoPanel fondo;

    public LectadorDeConsolasHoloTalk() {
        super();
        // Apariencia inicial
        recargarApariencia();
    }

    // ====== Hooks de colores/texto NO localizado ======
    @Override protected Color colorFondo() { return COLOR_FONDO; }
    @Override protected Color colorTexto() { return COLOR_TEXTO; }
    @Override protected Color colorError() { return COLOR_ERROR; }
    @Override protected Color colorPila()  { return COLOR_PILA;  }
    @Override protected String textoNormalLeyenda() { return "Texto normal"; }

    // ====== Fondo (apariencia) ======
    @Override
    protected void instalarFondoApariencia(JLayeredPane capa) {
        fondo = new FondoPanel(MonitorDePID.carpeta.resolve("imagenes/kiara_ame.png").toString());
        fondo.setBounds(0, 0, getWidth(), getHeight());
        capa.add(fondo, JLayeredPane.DEFAULT_LAYER);

        // Ajustar con resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                if (fondo != null) fondo.setBounds(0, 0, getWidth(), getHeight());
            }
        });

        // Cargar en segundo plano
        fondo.cargarAsincrono(pool, new Runnable() {
            @Override public void run() { repaint(); }
        });
    }

    /** Panel de fondo con imagen */
    private static class FondoPanel extends JPanel {
        private volatile java.awt.Image imagen;
        private final String ruta;

        public FondoPanel(String ruta) { this.ruta = ruta; setOpaque(false); }

        @Override protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            if (imagen != null) { g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this); }
        }

        public void cargarAsincrono(java.util.concurrent.ExecutorService pool, final Runnable whenLoadedOnEDT) {
            pool.submit(new Runnable() {
                @Override public void run() {
                    try { imagen = ImageIO.read(new File(ruta)); }
                    catch (Exception ex) { CrashDetectorLogger.log("No se pudo cargar fondo: " + ex.getMessage()); }
                    SwingUtilities.invokeLater(whenLoadedOnEDT);
                }
            });
        }
    }

    // ====== Apariencia (restablecer colores/textos NO localizados) ======
    @Override
    protected void aplicarApariencia() {
        // Aquí podrías restablecer bordes, fuentes, paddings, iconos, etc.
        // Ejemplo: no tocamos datos/selecciones; solo estilos no localizados.
        // (Si deseas añadir más estética, hazlo aquí.)
        repaint();
    }

    // ====== CrashDetectorGUI ======
    @Override
    public String id() {
        // Codename en español
        return ID;
    }

    // ====== Navegación por enlace (mantener API estática) ======
    public static void procesarHipervinculo(String url) {
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
                        "No se encontró la consola para el archivo: " + rutaArchivo,
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            CrashDetectorLogger.log("seleccionada " + consolaSeleccionada.archivo.toString());

            final LectadorDeConsolasHoloTalk lector = new LectadorDeConsolasHoloTalk();
            lector.setVisible(true);

            final String nombreArchivo = new File(consolaSeleccionada.archivo.toString()).getName();
            lector.cmbConsolas.setSelectedItem(nombreArchivo);

            final Consola consolaFinal = consolaSeleccionada;
            lector.pool.submit(new Runnable() {
                @Override public void run() {
                    java.util.List<String> lineas = lector.cacheLineasPorConsola.get(nombreArchivo);
                    if (lineas == null) {
                        lineas = java.util.Arrays.asList(consolaFinal.contenido_verificar.split(Verificaciones.nl));
                        lector.cacheLineasPorConsola.put(nombreArchivo, lineas);
                    }

                    final java.util.List<String> lineasFinal = lineas;
                    final int salto = Math.max(0, Math.min(numeroLinea, lineasFinal.size() - 1));

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() {
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
