package com.asbestosstar.crashdetector.gui.tipos.editorgui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Clase base abstracta para el editor de GUIs.
 * Contiene la lógica para seleccionar tipos de GUI y sus instancias.
 * La apariencia y el layout se definen en implementaciones concretas.
 */
public abstract class EditorGUI extends JFrame implements CrashDetectorGUI {

    public static Map<String, Supplier<EditorGUI>> GUIS = new HashMap<>();

    // Componentes públicos para que la implementación concreta los estilice
    public JComboBox<String> comboTipoGUI;
    public JComboBox<String> comboGUI;
    public JPanel panelEdicion;

    /**
     * Constructor vacío según los requisitos.
     */
    public EditorGUI() {
        super();
    }

    /**
     * Inicializa los componentes básicos del editor.
     */
    protected void inicializarComponentesBase() {
        // Crear el combo para seleccionar el tipo de GUI
        comboTipoGUI = new JComboBox<>();
        comboTipoGUI.addActionListener(e -> actualizarComboGUI());

        // Crear el combo para seleccionar la GUI específica
        comboGUI = new JComboBox<>();
        comboGUI.addActionListener(e -> cargarGUISeleccionada());

        // Panel para mostrar los elementos editables
        panelEdicion = new JPanel();
        panelEdicion.setLayout(new java.awt.BorderLayout());
    }

    /**
     * Actualiza el combo de GUIs según el tipo seleccionado.
     */
    protected void actualizarComboGUI() {
        String tipoSeleccionado = (String) comboTipoGUI.getSelectedItem();
        if (tipoSeleccionado == null) {
            return;
        }
        comboGUI.removeAllItems();
        TipoGUI<?> tipo = obtenerTipoGUI(tipoSeleccionado);
        if (tipo != null) {
            for (String id : tipo.obtenerGUIs().keySet()) {
                comboGUI.addItem(id);
            }
        }
    }

    /**
     * Carga la GUI seleccionada en el panel de edición.
     */
    public void cargarGUISeleccionada() {
        String tipoSeleccionado = (String) comboTipoGUI.getSelectedItem();
        String guiSeleccionada = (String) comboGUI.getSelectedItem();
        
        if (tipoSeleccionado == null || guiSeleccionada == null) {
            return;
        }

        TipoGUI<?> tipo = obtenerTipoGUI(tipoSeleccionado);
        
        if (tipo != null) {
            Supplier<? extends CrashDetectorGUI> supplier = tipo.obtenerGUIs().get(guiSeleccionada);
            
            if (supplier != null) {
                CrashDetectorGUI gui = supplier.get();
                if (gui != null) {
                    panelEdicion.removeAll();

                    // Check the type and cast accordingly to avoid ClassCastException
                    if (gui instanceof JFrame) {
                        JFrame frame = (JFrame) gui;
                        // Optionally set frame properties, e.g.:
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        panelEdicion.add(frame.getContentPane(), java.awt.BorderLayout.CENTER);
                    } else if (gui instanceof JPanel) {
                        JPanel panel = (JPanel) gui;
                        panelEdicion.add(panel, java.awt.BorderLayout.CENTER);
                    } else if (gui instanceof JDialog) {
                        JDialog dialog = (JDialog) gui;
                        panelEdicion.add(dialog.getContentPane(), java.awt.BorderLayout.CENTER);
                    } else {
                        // Handle any other implementations of CrashDetectorGUI as necessary
                        // For example, if it's just a JPanel-like component:
                        panelEdicion.add((java.awt.Component) gui, java.awt.BorderLayout.CENTER);
                    }

                    panelEdicion.revalidate();
                    panelEdicion.repaint();
                }
            }
        }
    }


    /**
     * Obtiene el TipoGUI correspondiente al nombre proporcionado.
     *
     * @param nombre El nombre del tipo de GUI.
     * @return El TipoGUI correspondiente o null si no se encuentra.
     */
    protected TipoGUI<?> obtenerTipoGUI(String nombre) {
        for (TipoGUI<?> tipo : TipoGUI.TIPOS_DE_GUI) {
            if (tipo.id().equals(nombre)) {
                return tipo;
            }
        }
        return null;
    }

    /**
     * Muestra la GUI seleccionada en una ventana nueva.
     */
    protected void mostrarGUI() {
        String tipoSeleccionado = (String) comboTipoGUI.getSelectedItem();
        String guiSeleccionada = (String) comboGUI.getSelectedItem();
        if (tipoSeleccionado == null || guiSeleccionada == null) {
            return;
        }
        TipoGUI<?> tipo = obtenerTipoGUI(tipoSeleccionado);
        if (tipo != null) {
            CrashDetectorGUI gui = tipo.obtenerGUIPredeterminado(guiSeleccionada, () -> {
                throw new RuntimeException("No se pudo crear la GUI: " + guiSeleccionada);
            });
            // No mostrar PrincipalGUI, DialogoCompartir, o NoRegistroLauncher
            if (gui instanceof com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI ||
                gui instanceof com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartir ||
                gui instanceof com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroLanzadorGUI) {
                return;
            }
            gui.init();
        }
    }

    /**
     * Obtiene los elementos de configuración de la GUI seleccionada.
     * @return Una lista de ElementoConfig o null si no se puede obtener.
     */
    protected java.util.List<com.asbestosstar.crashdetector.config.ElementoConfig> obtenerElementosConfigSeleccionados() {
        String tipoSeleccionado = (String) comboTipoGUI.getSelectedItem();
        String guiSeleccionada = (String) comboGUI.getSelectedItem();
        if (tipoSeleccionado == null || guiSeleccionada == null) {
            return null;
        }
        TipoGUI<?> tipo = obtenerTipoGUI(tipoSeleccionado);
        if (tipo != null) {
            Supplier<? extends CrashDetectorGUI> supplier = tipo.obtenerGUIs().get(guiSeleccionada);
            if (supplier != null) {
                CrashDetectorGUI gui = supplier.get();
                if (gui != null) {
                    try {
                        java.util.List<com.asbestosstar.crashdetector.config.ElementoConfig> elementos = gui.obtenerElementosConfigs();
                        // Filtrar elementos nulos
                        if (elementos != null) {
                            java.util.List<com.asbestosstar.crashdetector.config.ElementoConfig> elementosFiltrados = new java.util.ArrayList<>();
                            for (com.asbestosstar.crashdetector.config.ElementoConfig elem : elementos) {
                                if (elem != null) {
                                    elementosFiltrados.add(elem);
                                }
                            }
                            return elementosFiltrados;
                        }
                    } catch (Exception e) {
                        // Manejar la excepción si el método no está implementado o falla
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Edita un elemento de configuración.
     *
     * @param elemento El elemento de configuración a editar.
     */
    public abstract void editarElemento(com.asbestosstar.crashdetector.config.ElementoConfig<?> elemento);

    @Override
    public TipoGUI tipo() {
        return TipoGUI.EDITOR_GUI;
    }

    @Override
    public abstract void init();

    @Override
    public abstract String id();

    @Override
    public abstract void recargarApariencia();

    @Override
    public abstract java.util.List<com.asbestosstar.crashdetector.config.ElementoConfig> obtenerElementosConfigs();
}