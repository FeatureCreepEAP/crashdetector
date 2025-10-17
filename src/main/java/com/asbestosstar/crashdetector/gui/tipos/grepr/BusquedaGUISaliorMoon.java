package com.asbestosstar.crashdetector.gui.tipos.grepr;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.MonitorDePID;

public class BusquedaGUISaliorMoon extends GrepRGUI {

    // Colores del tema noche
    private final Color colorFondoVentana = new Color(12, 18, 56);
    private final Color colorPanel = new Color(20, 28, 78);
    private final Color colorTexto = Color.WHITE;
    private final Color colorBoton = new Color(220, 64, 92);
    private final Color colorBotonTexto = Color.WHITE;
    private final Color colorCampo = new Color(16, 22, 66);

    @Override
    protected void construirInterfaz() {
        setTitle("grepr/fgrepr");
        getContentPane().setBackground(colorFondoVentana);
        setLayout(new BorderLayout(10, 10));

        // --- Panel superior (entradas) con GridBag ---
        java.awt.GridBagLayout gbl = new java.awt.GridBagLayout();
        JPanel panelEntrada = new JPanel(gbl);
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelEntrada.setBackground(colorPanel);

        java.awt.GridBagConstraints L = new java.awt.GridBagConstraints();
        L.insets = new java.awt.Insets(0, 0, 0, 8);
        L.fill = java.awt.GridBagConstraints.HORIZONTAL;
        L.weightx = 0.4;
        L.anchor = java.awt.GridBagConstraints.WEST;

        java.awt.GridBagConstraints R = new java.awt.GridBagConstraints();
        R.insets = new java.awt.Insets(5, 8, 5, 0);
        R.fill = java.awt.GridBagConstraints.HORIZONTAL;
        R.weightx = 0.6;
        R.anchor = java.awt.GridBagConstraints.WEST;

        // Fila 0: directorio + botón examinar
        estilizarCampo(campoDirectorio);
        L.gridx = 0; L.gridy = 0;
        panelEntrada.add(campoDirectorio, L);

        JButton btnExaminar = new JButton(MonitorDePID.idioma.seleccionarCarpeta());
        estilizarBoton(btnExaminar);
        R.gridx = 1; R.gridy = 0;
        panelEntrada.add(btnExaminar, R);

        // Fila 1: etiqueta + campo cadena
        JLabel lblCadena = new JLabel(MonitorDePID.idioma.cadenaBusqueda());
        lblCadena.setForeground(colorTexto);
        L.gridx = 0; L.gridy = 1;
        panelEntrada.add(lblCadena, L);

        estilizarCampo(campoCadena);
        R.gridx = 1; R.gridy = 1;
        panelEntrada.add(campoCadena, R);

        // Fila 2: checks
        estilizarCheck(chkRegex);
        L.gridx = 0; L.gridy = 2;
        panelEntrada.add(chkRegex, L);

        estilizarCheck(chkIgnorarMayus);
        R.gridx = 1; R.gridy = 2;
        panelEntrada.add(chkIgnorarMayus, R);

        // Fila 3: check de comprimidos + imagen
        estilizarCheck(chkBuscarEnComprimidos);
        L.gridx = 0; L.gridy = 3;
        panelEntrada.add(chkBuscarEnComprimidos, L);

        JLabel lblImagen = crearImagenEscalada(
                MonitorDePID.carpeta.resolve("imagenes/saliormoongrep.png").toString(), 150, 100);
        lblImagen.setOpaque(true);
        lblImagen.setBackground(colorPanel);
        lblImagen.setForeground(colorTexto);
        R.gridx = 1; R.gridy = 3;
        panelEntrada.add(lblImagen, R);

        // Fila 4: botón buscar + relleno
        JButton btnBuscar = new JButton(MonitorDePID.idioma.buscar());
        estilizarBoton(btnBuscar);
        L.gridx = 0; L.gridy = 4;
        panelEntrada.add(btnBuscar, L);

        R.gridx = 1; R.gridy = 4;
        panelEntrada.add(new JLabel(), R);

        // Resultados
        areaResultados.setBackground(colorCampo);
        areaResultados.setForeground(colorTexto);
        areaResultados.setCaretColor(colorTexto);
        areaResultados.setSelectionColor(new Color(255, 215, 0));
        areaResultados.setSelectedTextColor(Color.BLACK);

        scrollResultados.getViewport().setBackground(colorCampo);
        scrollResultados.setBackground(colorPanel);
        scrollResultados.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));

        add(panelEntrada, BorderLayout.NORTH);
        add(scrollResultados, BorderLayout.CENTER);

        // Acciones
        conectarAcciones(btnExaminar, btnBuscar);
    }

    // --- Estilo del tema ---
    @Override
    protected void estilizarBoton(JButton b) {
        b.setBackground(colorBoton);
        b.setForeground(colorBotonTexto);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
    }

    @Override
    protected void estilizarCampo(javax.swing.JTextField f) {
        f.setBackground(colorCampo);
        f.setForeground(colorTexto);
        f.setCaretColor(colorTexto);
        f.setSelectionColor(new Color(255, 215, 0));
        f.setSelectedTextColor(Color.BLACK);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    @Override
    protected void estilizarCheck(JCheckBox c) {
        c.setBackground(colorPanel);
        c.setForeground(colorTexto);
        c.setFocusPainted(false);
    }

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recargarApariencia() {
	    // Reaplicar colores principales
	    getContentPane().setBackground(colorFondoVentana);

	    // Campos
	    estilizarCampo(campoDirectorio);
	    estilizarCampo(campoCadena);

	    // Checkboxes con textos actualizados
	    chkRegex.setText(MonitorDePID.idioma.usarRegex());
	    chkIgnorarMayus.setText(MonitorDePID.idioma.ignorarMayusculas());
	    chkBuscarEnComprimidos.setText(MonitorDePID.idioma.buscarDentroDeComprimidos());
	    estilizarCheck(chkRegex);
	    estilizarCheck(chkIgnorarMayus);
	    estilizarCheck(chkBuscarEnComprimidos);

	    // Área de resultados
	    areaResultados.setBackground(colorCampo);
	    areaResultados.setForeground(colorTexto);
	    areaResultados.setCaretColor(colorTexto);
	    areaResultados.setSelectionColor(new Color(255, 215, 0));
	    areaResultados.setSelectedTextColor(Color.BLACK);

	    scrollResultados.getViewport().setBackground(colorCampo);
	    scrollResultados.setBackground(colorPanel);
	    scrollResultados.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));

	    // Actualizar botones
	    for (java.awt.Component comp : ((java.awt.Container)getContentPane().getComponent(0)).getComponents()) {
	        if (comp instanceof JButton) {
	            JButton b = (JButton) comp;
	            // Detecta botones por texto anterior o heurística simple
	            String txt = b.getText().toLowerCase();
	            if (txt.contains("buscar")) {
	                b.setText(MonitorDePID.idioma.buscar());
	            } else if (txt.contains("carpeta")) {
	                b.setText(MonitorDePID.idioma.seleccionarCarpeta());
	            }
	            estilizarBoton(b);
	        } else if (comp instanceof JLabel) {
	            JLabel lbl = (JLabel) comp;
	            // Reaplica color de texto para etiquetas
	            lbl.setForeground(colorTexto);
	        }
	    }

	    // Reaplicar imagen de tema
	    // Busca dentro del panel superior cualquier JLabel con icono
	    actualizarImagenesRecursivamente(getContentPane());

	    // Forzar actualización visual
	    revalidate();
	    repaint();
	}

	/**
	 * Busca recursivamente etiquetas con íconos para reestablecer las imágenes
	 * (en caso de cambio de idioma o tema)
	 */
	private void actualizarImagenesRecursivamente(java.awt.Container contenedor) {
	    for (java.awt.Component comp : contenedor.getComponents()) {
	        if (comp instanceof JLabel) {
	            JLabel lbl = (JLabel) comp;
	            // Reasigna la imagen si era la de Sailor Moon
	            if (lbl.getIcon() != null || lbl.getText().contains("saliormoongrep")) {
	                JLabel nueva = crearImagenEscalada(
	                    MonitorDePID.carpeta.resolve("imagenes/saliormoongrep.png").toString(),
	                    150, 100
	                );
	                lbl.setIcon(nueva.getIcon());
	                lbl.setText("");
	                lbl.setBackground(colorPanel);
	            } else {
	                lbl.setForeground(colorTexto);
	            }
	        } else if (comp instanceof java.awt.Container) {
	            actualizarImagenesRecursivamente((java.awt.Container) comp);
	        }
	    }
	}

}
