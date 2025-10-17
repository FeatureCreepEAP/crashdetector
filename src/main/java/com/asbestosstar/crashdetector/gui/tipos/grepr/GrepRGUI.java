package com.asbestosstar.crashdetector.gui.tipos.grepr;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.grepr.BusquedaArchivos;
import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Base abstracta para GrepR:
 * - Expone los componentes clave (campos, checks, resultados)
 * - Contiene la lógica de seleccionar carpeta e iniciar búsqueda
 * - Proporciona hooks para estilo (estilizarBoton/campo/check) que las
 *   implementaciones pueden sobrescribir
 * - La subclase se encarga del layout y del look&feel
 */
public abstract class GrepRGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

    public static java.util.Map<String, java.util.function.Supplier<GrepRGUI>> GUIS =
            new java.util.HashMap<String, java.util.function.Supplier<GrepRGUI>>();

    // --- Componentes compartidos (la subclase los coloca en el layout que quiera) ---
    protected JTextField campoDirectorio;
    protected JTextField campoCadena;
    protected JCheckBox chkRegex;
    protected JCheckBox chkIgnorarMayus;
    protected JCheckBox chkBuscarEnComprimidos;

    protected JTextArea areaResultados;
    protected JScrollPane scrollResultados;

    // --- Construcción de UI (plantilla) ---
    public GrepRGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setTitle("grepr/fgrepr");
        construirComponentesBase();
        construirInterfaz(); // la subclase arma el layout y aplica sus estilos
    }

    /**
     * Crea instancias de los componentes base.
     * La subclase NO debe reinstanciarlos; solo colocarlos y darles estilo.
     */
    protected void construirComponentesBase() {
        campoDirectorio = new JTextField();
        campoCadena = new JTextField();
        chkRegex = new JCheckBox(MonitorDePID.idioma.usarRegex());
        chkIgnorarMayus = new JCheckBox(MonitorDePID.idioma.ignorarMayusculas());
        chkBuscarEnComprimidos = new JCheckBox(MonitorDePID.idioma.buscarDentroDeComprimidos());

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        scrollResultados = new JScrollPane(areaResultados);
    }

    /**
     * Método de plantilla: la implementación construye el layout,
     * aplica colores/estilos y llama a {@link #conectarAcciones}.
     */
    protected abstract void construirInterfaz();

    /**
     * Enlaza los botones de "Examinar" y "Buscar" con la lógica común.
     */
    protected void conectarAcciones(JButton btnExaminar, JButton btnBuscar) {
        if (btnExaminar != null) {
            btnExaminar.addActionListener(e -> seleccionarCarpeta());
        }
        if (btnBuscar != null) {
            btnBuscar.addActionListener(e -> iniciarBusqueda());
        }
    }

    // -------- Lógica compartida --------

    protected void seleccionarCarpeta() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            campoDirectorio.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    protected void iniciarBusqueda() {
        String dirCrudo = campoDirectorio.getText() == null ? "" : campoDirectorio.getText().trim();
        final String directorio = dirCrudo.replace(" ", "").isEmpty()
                ? System.getProperty("user.dir")
                : dirCrudo;

        String cadena = campoCadena.getText() == null ? "" : campoCadena.getText().trim();
        boolean usarRegex = chkRegex.isSelected();
        boolean ignorarMayus = chkIgnorarMayus.isSelected();
        boolean buscarEnComprimidos = chkBuscarEnComprimidos.isSelected();

        areaResultados.setText(MonitorDePID.idioma.busquedaEnProgreso() + "\n");

        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() {
                return BusquedaArchivos.buscar(directorio, cadena, usarRegex, ignorarMayus, buscarEnComprimidos);
            }

            @Override
            protected void done() {
                try {
                    List<String> resultados = get();
                    areaResultados.setText("");
                    if (resultados.isEmpty()) {
                        areaResultados.append(MonitorDePID.idioma.noSeEncontraronResultados());
                    } else {
                        for (String r : resultados) {
                            areaResultados.append(r + "\n");
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    areaResultados.append(MonitorDePID.idioma.errorBusqueda() + e.getMessage());
                }
            }
        }.execute();
    }

    // -------- Hooks de estilo (opcional sobrescribir en la subclase) --------

    protected void estilizarBoton(JButton b) { /* por defecto nada */ }
    protected void estilizarCampo(JTextField f) { /* por defecto nada */ }
    protected void estilizarCheck(JCheckBox c) { /* por defecto nada */ }

    // -------- Utilidad opcional para imágenes (reutilizable por skins) --------
    protected javax.swing.JLabel crearImagenEscalada(String ruta, int w, int h) {
        java.io.File f = new java.io.File(ruta);
        if (!f.isAbsolute()) {
            f = new java.io.File(System.getProperty("user.dir"), ruta);
        }
        if (f.exists() && f.isFile()) {
            javax.swing.ImageIcon base = new javax.swing.ImageIcon(f.getAbsolutePath());
            java.awt.Image esc = base.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
            return new javax.swing.JLabel(new javax.swing.ImageIcon(esc));
        }
        java.net.URL url = getClass().getClassLoader().getResource(ruta);
        if (url != null) {
            javax.swing.ImageIcon base = new javax.swing.ImageIcon(url);
            java.awt.Image esc = base.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
            return new javax.swing.JLabel(new javax.swing.ImageIcon(esc));
        }
        javax.swing.JLabel fallo = new javax.swing.JLabel("imagen no encontrada " + ruta);
        return fallo;
    }

    // -------- Infraestructura CrashDetectorGUI --------

    public TipoGUI<GrepRGUI> tipo() {
        return TipoGUI.GREPR;
    }

    @Override
    public void init() {
        setVisible(true);
    }
}
