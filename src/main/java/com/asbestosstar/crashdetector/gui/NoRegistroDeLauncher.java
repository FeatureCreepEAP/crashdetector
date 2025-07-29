package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EnlanceMD;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Diálogo para que el usuario pegue o seleccione registros según su launcher.
 */
public class NoRegistroDeLauncher extends JDialog {
    private static final String GEN       = "GENERIC";
    private static final String CURSE     = "CURSEDFORGE";
    private static final String PRISM     = "PRISM/MultiMC+++"; // incluye MultiMC & derivados
    private static final String HMCL      = "HMCL";
    private static final String FENIX     = "FENIX";
    private static final String ATL       = "ATLAUNCHER";
    private static final String GD        = "GDLAUNCHER";
    private static final String ENLANCE_MD    = "ENLANCE_MD";

    private final JTextArea areaTexto = new JTextArea(15, 50);  // solo visible para algunos launchers
    private final JLabel    imagenLbl = new JLabel("", SwingConstants.CENTER);
    private final JButton   seleccionarCarpetaBtn = new JButton(MonitorDePID.idioma.seleccionarCarpeta());
    private JTextArea descripcionTextArea; // Añade esta variable de instancia

    private final JComboBox<String> selector;
    public static File cd_launcherlog = new File("cd_launcherlog");
    public Instant instant;

    public NoRegistroDeLauncher(JFrame blanco, Instant instant) {
        super(blanco, true);
        this.instant = instant;

        setTitle("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());

        setSize(600, 650);
        setLocationRelativeTo(blanco);
        setLayout(new BorderLayout(10, 10));

        

        JPanel principal = new JPanel();
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        add(principal, BorderLayout.CENTER);


        // Selector de launcher
        selector = new JComboBox<>(new String[]{GEN, CURSE, PRISM, HMCL, FENIX, ATL, GD, ENLANCE_MD});
        selector.addActionListener(e -> refrescarUI());
        principal.add(selector);

        // Descripción

//        JTextArea descripcion = new JTextArea();
//        descripcion.setEditable(false);
//        descripcion.setLineWrap(true);
//        descripcion.setWrapStyleWord(true);
//        descripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        principal.add(descripcion);
        descripcionTextArea = new JTextArea();
        descripcionTextArea.setEditable(false);
        descripcionTextArea.setLineWrap(true);
        descripcionTextArea.setWrapStyleWord(true);
        descripcionTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        principal.add(descripcionTextArea);

        // Panel de imagen

        imagenLbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imagenLbl, BorderLayout.CENTER);
        imagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        principal.add(imagePanel);

        // Área de texto con scroll

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            MonitorDePID.idioma.pegaLosRegistrosAqui()
        );
        border.setTitleJustification(TitledBorder.LEFT);
        scrollPane.setBorder(border);
        principal.add(scrollPane);


        // Botón de carpeta (solo HMCL)
        seleccionarCarpetaBtn.addActionListener(ev -> abrirSelectorCarpeta());
        seleccionarCarpetaBtn.setAlignmentX(LEFT_ALIGNMENT);
        principal.add(seleccionarCarpetaBtn);


        // Botones de guardar/omitir
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton guardarBtn = new JButton(MonitorDePID.idioma.guardarYCerrar());
        JButton omitirBtn = new JButton(MonitorDePID.idioma.omitirYCerrar());
        guardarBtn.addActionListener(e -> guardarRegistros());
        omitirBtn.addActionListener(e -> dispose());
        panelBotones.add(guardarBtn);
        panelBotones.add(omitirBtn);
        principal.add(panelBotones);

        

        // Estado inicial
        refrescarUI();

        pack(); // Ajusta tamaño automáticamente

        setVisible(true);
    }


    private void refrescarUI() {
        String tipo = (String) selector.getSelectedItem();
        String desc = MonitorDePID.idioma.noRegistroDeLauncher(); // Default
        ImageIcon icon = null;
        areaTexto.setVisible(true);
        seleccionarCarpetaBtn.setVisible(false);

        switch (tipo) {
            case CURSE:
                desc = MonitorDePID.idioma.descripcionCurseforge();
                icon = cargarIcono("/imagenes/cfskiplauncher.png");
                areaTexto.setVisible(false);
                break;
            case PRISM:
                desc = MonitorDePID.idioma.descripcionPrism();
                icon = cargarIcono("/imagenes/registros_de_launcher_prism.png");
                break;
            case HMCL:
                desc = MonitorDePID.idioma.descripcionHMCL();
                icon = cargarIcono("/imagenes/hcml.png");
                areaTexto.setVisible(false);
                seleccionarCarpetaBtn.setVisible(true);
                break;
            case FENIX:
                desc = MonitorDePID.idioma.descripcionFenix();
                icon = cargarIcono("/imagenes/fenix.png");
                break;
            case ATL:
                desc = MonitorDePID.idioma.descripcionATLauncher();
                icon = cargarIcono("/imagenes/atlogs.png");
                break;
            case GD:
                desc = MonitorDePID.idioma.descripcionGDLauncher();
                icon = cargarIcono("/imagenes/gdconsola.png");
                break;
            case ENLANCE_MD:
                desc = MonitorDePID.idioma.descripcionLinksMarkdown();
                icon = null;
                break;
            default:
                icon = null;
        }

        // Establece el texto en el JTextArea directamente
        descripcionTextArea.setText(desc);

        // Actualiza la imagen y otros componentes
        imagenLbl.setIcon(icon);

        imagenLbl.setText(icon == null ? MonitorDePID.idioma.imagenNoEncontrada() : "");

        revalidate();

        repaint();
    }

    /** Carga y escala icono, devuelve null si no existe */
    private ImageIcon cargarIcono(String ruta) {
        URL url = getClass().getResource(ruta);
        if (url == null) {
            CrashDetectorLogger.log("No se encontró la imagen " + ruta);
            return null;
        }
        ImageIcon raw = new ImageIcon(url);
        Image scaled = raw.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    /** Abre diálogo de carpeta para HMCL y guarda en Config */
    private void abrirSelectorCarpeta() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = fc.getSelectedFile();
            Config.obtenerInstancia().guardarCarpetaHMCL(dir.getAbsolutePath());
        }
    }

    /** Guarda los registros pegados en areaTexto (si aplica) y cierra. */
    private void guardarRegistros() {
        String tipo = (String) selector.getSelectedItem();
        if (tipo.equals(HMCL) || tipo.equals(CURSE)) {
            // Para estos launchers, no hay área de texto; simplemente cerrar
            dispose();
            return;
        }else if (tipo.equals(ENLANCE_MD)) {
        	CrashDetectorLogger.log("areatexto");
        	EnlanceMD.guardar(areaTexto.getText());
        	CrashDetectorLogger.log("completa");
            dispose();
        	return;
        }
        
        

        try (FileOutputStream fos = new FileOutputStream(cd_launcherlog);
             FileChannel ch = fos.getChannel()) {
            byte[] data = areaTexto.getText().getBytes(StandardCharsets.UTF_8);
            fos.write(data);
            // ch.force(true); // opcional
            CrashDetectorLogger.log("Archivo cd_launcherlog guardado");

            Consola cons = new Consola(cd_launcherlog.toPath());
            cons.finalizarContenidoInyectado(areaTexto.getText());
            MonitorDePID.consolas.add(cons);
            MonitorDePID.consola_de_launcher_inyectado = true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            dispose();
        }
    }
}
