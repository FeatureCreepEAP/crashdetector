package com.asbestosstar.crashdetector.analyzador;

import java.awt.Font;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Representa una solución rápida con componentes GUI configurables
 */
public class QuickFix {
    // Campo para almacenar el estado del checkbox
    public boolean tieneRetener = false;
    
    public final String etiqueta;
    public boolean tieneCheckbox = false;
    public java.util.List<ComponenteGUI> componentes = new java.util.ArrayList<>();

    private QuickFix(Builder builder) {
        this.etiqueta = builder.etiqueta;
        this.tieneCheckbox = builder.tieneCheckbox;
        componentes=builder.componentes;
    }

    public static class Builder {
        private final String etiqueta;
        private boolean tieneCheckbox = false;
        private final java.util.List<ComponenteGUI> componentes = new java.util.ArrayList<>();

        public Builder(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        /**
         * Añade un checkbox de retención
         */
        public Builder conRetener() {
            this.tieneCheckbox = true;
            return this;
        }

        /**
         * Añade un botón con acción
         */
        public Builder agregarBoton(String texto, AccionBoton accion) {
            componentes.add(new BotonGUI(texto, accion));
            return this;
        }

        /**
         * Añade una etiqueta adicional
         */
        public Builder agregarEtiqueta(String texto) {
            componentes.add(new EtiquetaGUI(texto));
            return this;
        }

        public QuickFix construir() {
            return new QuickFix(this);
        }
    }

    // Interfaz para componentes GUI
    public interface ComponenteGUI {
        JComponent crearComponente(Supplier<Boolean> estadoRetener);
    }

    // Clase para botones
    static class BotonGUI implements ComponenteGUI {
        private final String texto;
        private final AccionBoton accion;

        public BotonGUI(String texto, AccionBoton accion) {
            this.texto = texto;
            this.accion = accion;
        }

        @Override
        public JComponent crearComponente(Supplier<Boolean> estadoRetener) {
            JButton boton = new JButton(texto);
            estilizarBoton(boton);  
            boton.addActionListener(e -> 
                accion.ejecutar(estadoRetener != null ? estadoRetener.get() : false)
            );
            return boton;
        }

        private void estilizarBoton(JButton boton) {
            if (!CrashDetectorGUI.esMac()) {
                boton.setBackground(CrashDetectorGUI.colorBoton);
                boton.setForeground(CrashDetectorGUI.colorTexto);
                boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            }
        }
    }

    // Clase para etiquetas
    static class EtiquetaGUI implements ComponenteGUI {
        private final String texto;

        public EtiquetaGUI(String texto) {
            this.texto = texto;
        }

        @Override
        public JComponent crearComponente(Supplier<Boolean> estadoRetener) {
            return new JLabel(texto);
        }
    }

    // Interfaz para acciones de botón
    @FunctionalInterface
    public interface AccionBoton {
        void ejecutar(boolean retenerSeleccionado);
    }
}