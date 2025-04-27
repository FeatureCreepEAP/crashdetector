package com.asbestosstar.crashdetectormc.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class AnalisisAvanzadoGUI extends JFrame {

    public AnalisisAvanzadoGUI() {
        setTitle(MonitorDePID.idioma.analisisAvanzado());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CrashDetectorGUI.colorFondo);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Crear botones de ejemplo
        String[] botones = {"grepr/fgrepr"};
        
        for (String texto : botones) {
            JButton btn = new JButton(texto);
            estilizarBoton(btn);
            
            // Nuevo comportamiento del botón
            btn.addActionListener(e -> {
                // Crear y mostrar la interfaz de búsqueda
                BusquedaGUI busquedaGUI = new BusquedaGUI();
                busquedaGUI.setVisible(true);
            });
            
            add(btn);
        }
    }

    private void estilizarBoton(JButton boton) {
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(CrashDetectorGUI.colorBoton);
        boton.setForeground(CrashDetectorGUI.colorTexto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setMaximumSize(new Dimension(250, 40));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}