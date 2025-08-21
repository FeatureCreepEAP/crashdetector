package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

public class ArbolDeModsGUI extends JFrame implements BotonDeBarraLateralDerecha {

    private JTree arbolModulos;
    private DefaultTreeModel modeloArbol;
    private JTextField campoBuscar;
    private JComboBox<String> comboFiltro;
    private JTextArea areaContenido;
    private JButton botonReset;
    private JLabel imagenHamu;

    public ArbolDeModsGUI() {
        setTitle(MonitorDePID.idioma.tituloArbolDeMods());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Barra superior
        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Elementos de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel etiquetaTipo = new JLabel(MonitorDePID.idioma.tipoBusqueda() + ":");
        String[] opcionesFiltro = {
            MonitorDePID.idioma.filtroTodos(),
            MonitorDePID.idioma.filtroPaquetes(),
            MonitorDePID.idioma.filtroClases(),
            MonitorDePID.idioma.filtroMetodos(),
            MonitorDePID.idioma.filtroCampos(),
            MonitorDePID.idioma.filtroReferenciasCampo(),
            MonitorDePID.idioma.filtroReferenciasMetodo(),
            MonitorDePID.idioma.filtroReferenciasClase()
        };
        comboFiltro = new JComboBox<>(opcionesFiltro);
        campoBuscar = new JTextField(30);
        campoBuscar.setToolTipText(MonitorDePID.idioma.tipBuscar());
        JButton botonBuscar = new JButton(MonitorDePID.idioma.botonBuscar());
        panelBusqueda.add(etiquetaTipo);
        panelBusqueda.add(comboFiltro);
        panelBusqueda.add(campoBuscar);
        panelBusqueda.add(botonBuscar);

        // Imagen Hamu
        File imgFile = new File("crash_detector/imagenes/hamu.png");
        if (imgFile.exists()) {
            imagenHamu = new JLabel(new ImageIcon(imgFile.getAbsolutePath()));
        } else {
            imagenHamu = new JLabel("Image not found: " + imgFile.getAbsolutePath());
            imagenHamu.setForeground(Color.RED);
        }
        imagenHamu.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Botón Reset
        botonReset = new JButton(MonitorDePID.idioma.botonResetearArbol());
        botonReset.addActionListener(e -> resetearArbol());

        // Árbol de módulos
        arbolModulos = new JTree();
        arbolModulos.setRootVisible(false);
        arbolModulos.setShowsRootHandles(true);
        arbolModulos.setCellRenderer(new ModuloTreeCellRenderer());
        arbolModulos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) mostrarMenuContexto(e);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    TreePath seleccion = arbolModulos.getPathForLocation(e.getX(), e.getY());
                    if (seleccion != null) {
                        mostrarDetallesNodo(seleccion.getLastPathComponent());
                    }
                }
            }
        });

        // Área de contenido
        areaContenido = new JTextArea();
        areaContenido.setEditable(false);
        areaContenido.setLineWrap(true);
        areaContenido.setWrapStyleWord(true);
        areaContenido.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Configuración de paneles
        add(imagenHamu, BorderLayout.SOUTH);
        add(botonReset, BorderLayout.NORTH);
        add(barraSuperior, BorderLayout.CENTER);

        barraSuperior.add(panelBusqueda, BorderLayout.NORTH);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(arbolModulos),
                new JScrollPane(areaContenido));
        splitPane.setDividerLocation(500);
        barraSuperior.add(splitPane, BorderLayout.CENTER);

        // Eventos
        campoBuscar.addActionListener(e -> filtrarArbol());
        botonBuscar.addActionListener(e -> filtrarArbol());
        comboFiltro.addActionListener(e -> filtrarArbol());

        // Inicialización
        construirArbolInicial();
    }

    @Override
    public void init() {
        this.setVisible(true);
        Buscardor.cargar();
    }

    @Override
    public String etiquetaDelBoton() {
        return MonitorDePID.idioma.arbolDeMods();
    }

    private void construirArbolInicial() {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.modsCargados());
        for (ArchivoDeMod mod : Buscardor.mods) {
            agregarModuloARaiz(raiz, mod);
        }
        modeloArbol = new DefaultTreeModel(raiz);
        arbolModulos.setModel(modeloArbol);
    }

    private void agregarModuloARaiz(DefaultMutableTreeNode padre, ArchivoDeMod mod) {
        String ubicacionPublica = mod.ubicacion_para_publicar();
        DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(ubicacionPublica);
        nodoModulo.setUserObject(mod); // Guardamos la referencia al mod para uso posterior
        padre.add(nodoModulo);

        // Agregar submódulos
        for (ArchivoDeMod submod : mod.mods_en_mods()) {
            agregarModuloARaiz(nodoModulo, submod);
        }

        // Agregar clases
        if (!mod.clases().isEmpty()) {
            DefaultMutableTreeNode nodoClases = new DefaultMutableTreeNode(MonitorDePID.idioma.clases() + " (" + mod.clases().size() + ")");
            for (String clase : mod.clases()) {
                DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(clase);
                nodoClase.setUserObject(new Object[]{mod, clase});
                
                // Agregar métodos y campos si ASM está disponible
                if (Buscardor.esASMDisponible()) {
                    List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(clase);
                    if (!metodos.isEmpty()) {
                        DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")");
                        for (ArchivoDeMod.InfoMetodo metodo : metodos) {
                            DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(metodo.obtenerNombre() + metodo.obtenerDescriptor());
                            nodoMetodo.setUserObject(new Object[]{mod, clase, metodo});
                            
                            // Agregar referencias de métodos y campos
                            DefaultMutableTreeNode nodoReferencias = new DefaultMutableTreeNode(MonitorDePID.idioma.referencias());
                            for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
                                nodoReferencias.add(new DefaultMutableTreeNode(
                                    MonitorDePID.idioma.metodo() + ": " + refMetodo.obtenerNombre() + refMetodo.obtenerDescriptor() + 
                                    " (" + refMetodo.obtenerClase() + ")"));
                            }
                            for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
                                nodoReferencias.add(new DefaultMutableTreeNode(
                                    MonitorDePID.idioma.campo() + ": " + refCampo.obtenerNombre() + 
                                    " (" + refCampo.obtenerClase() + ")"));
                            }
                            if (nodoReferencias.getChildCount() > 0) {
                                nodoMetodo.add(nodoReferencias);
                            }
                            nodoMetodos.add(nodoMetodo);
                        }
                        nodoClase.add(nodoMetodos);
                    }
                    
                    List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(clase);
                    if (!campos.isEmpty()) {
                        DefaultMutableTreeNode nodoCampos = new DefaultMutableTreeNode(MonitorDePID.idioma.campos() + " (" + campos.size() + ")");
                        for (ArchivoDeMod.InfoCampo campo : campos) {
                            DefaultMutableTreeNode nodoCampo = new DefaultMutableTreeNode(campo.obtenerNombre() + " " + campo.obtenerDescriptor());
                            nodoCampo.setUserObject(new Object[]{mod, clase, campo});
                            nodoCampos.add(nodoCampo);
                        }
                        nodoClase.add(nodoCampos);
                    }
                }
                nodoClases.add(nodoClase);
            }
            nodoModulo.add(nodoClases);
        }
    }

    private void filtrarArbol() {
        String filtro = campoBuscar.getText().trim().toLowerCase();
        String tipoFiltro = (String) comboFiltro.getSelectedItem();

        if (filtro.isEmpty()) {
            construirArbolInicial();
            return;
        }

        DefaultMutableTreeNode nuevaRaiz = new DefaultMutableTreeNode(MonitorDePID.idioma.resultadosBusqueda());
        boolean encontrado = false;

        for (ArchivoDeMod mod : Buscardor.mods) {
            encontrado |= buscarEnModulo(nuevaRaiz, mod, filtro, tipoFiltro);
        }

        if (!encontrado) {
            nuevaRaiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronResultados()));
        }

        modeloArbol = new DefaultTreeModel(nuevaRaiz);
        arbolModulos.setModel(modeloArbol);
    }

    private boolean buscarEnModulo(DefaultMutableTreeNode padre, ArchivoDeMod mod, String filtro, String tipoFiltro) {
        boolean agregarModulo = false;
        String ubicacionPublica = mod.ubicacion_para_publicar().toLowerCase();

        // Buscar en la ubicación del módulo
        if (ubicacionPublica.contains(filtro)) {
            agregarModulo = true;
        }

        // Buscar en submódulos
        for (ArchivoDeMod submod : mod.mods_en_mods()) {
            DefaultMutableTreeNode nodoSubmod = new DefaultMutableTreeNode(submod.ubicacion_para_publicar());
            nodoSubmod.setUserObject(submod);
            if (buscarEnModulo(nodoSubmod, submod, filtro, tipoFiltro)) {
                padre.add(nodoSubmod);
                agregarModulo = true;
            }
        }

        // Buscar en clases
        if (MonitorDePID.idioma.filtroTodos().equals(tipoFiltro) || MonitorDePID.idioma.filtroClases().equals(tipoFiltro)) {
            for (String clase : mod.clases()) {
                if (clase.toLowerCase().contains(filtro)) {
                    DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(clase);
                    nodoClase.setUserObject(new Object[]{mod, clase});
                    padre.add(nodoClase);
                    agregarModulo = true;
                }
            }
        }

        // Buscar en paquetes
        if (MonitorDePID.idioma.filtroTodos().equals(tipoFiltro) || MonitorDePID.idioma.filtroPaquetes().equals(tipoFiltro)) {
            Set<String> paquetes = new HashSet<>();
            for (String clase : mod.clases()) {
                int indice = clase.lastIndexOf('.');
                if (indice != -1) {
                    paquetes.add(clase.substring(0, indice));
                }
            }
            for (String paquete : paquetes) {
                if (paquete.toLowerCase().contains(filtro)) {
                    DefaultMutableTreeNode nodoPaquete = new DefaultMutableTreeNode(paquete);
                    padre.add(nodoPaquete);
                    agregarModulo = true;
                }
            }
        }

        // Buscar en métodos (si ASM está disponible)
        if (Buscardor.esASMDisponible() && (MonitorDePID.idioma.filtroTodos().equals(tipoFiltro) || MonitorDePID.idioma.filtroMetodos().equals(tipoFiltro))) {
            for (String clase : mod.clases()) {
                List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(clase);
                for (ArchivoDeMod.InfoMetodo metodo : metodos) {
                    String nombreMetodo = metodo.obtenerNombre() + metodo.obtenerDescriptor();
                    if (nombreMetodo.toLowerCase().contains(filtro)) {
                        DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(nombreMetodo);
                        nodoMetodo.setUserObject(new Object[]{mod, clase, metodo});
                        padre.add(nodoMetodo);
                        agregarModulo = true;
                    }
                }
            }
        }

        // Buscar en campos (si ASM está disponible)
        if (Buscardor.esASMDisponible() && (MonitorDePID.idioma.filtroTodos().equals(tipoFiltro) || MonitorDePID.idioma.filtroCampos().equals(tipoFiltro))) {
            for (String clase : mod.clases()) {
                List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(clase);
                for (ArchivoDeMod.InfoCampo campo : campos) {
                    String nombreCampo = campo.obtenerNombre() + " " + campo.obtenerDescriptor();
                    if (nombreCampo.toLowerCase().contains(filtro)) {
                        DefaultMutableTreeNode nodoCampo = new DefaultMutableTreeNode(nombreCampo);
                        nodoCampo.setUserObject(new Object[]{mod, clase, campo});
                        padre.add(nodoCampo);
                        agregarModulo = true;
                    }
                }
            }
        }

        return agregarModulo;
    }

    private void resetearArbol() {
        construirArbolInicial();
        areaContenido.setText("");
        campoBuscar.setText("");
    }

    private void mostrarMenuContexto(MouseEvent e) {
        TreePath seleccion = arbolModulos.getPathForLocation(e.getX(), e.getY());
        if (seleccion == null) return;

        Object nodo = seleccion.getLastPathComponent();
        if (!(nodo instanceof DefaultMutableTreeNode)) return;

        Object userObject = ((DefaultMutableTreeNode)nodo).getUserObject();
        if (userObject == null) return;

        JPopupMenu menu = new JPopupMenu();

        // Opción para buscar referencias
        JMenuItem itemBuscarRef = new JMenuItem(MonitorDePID.idioma.buscarReferencias());
        itemBuscarRef.addActionListener(ae -> {
            if (userObject instanceof ArchivoDeMod) {
                // Es un mod
                List<ArchivoDeMod> mods = new ArrayList<>();
                mods.add((ArchivoDeMod)userObject);
                List<String> ubicaciones = Buscardor.obtenerUbicaciones(mods);
                mostrarResultadosBusqueda(ubicaciones, MonitorDePID.idioma.referenciasMod());
            } else if (userObject instanceof Object[]) {
                Object[] datos = (Object[])userObject;
                if (datos.length >= 2 && datos[0] instanceof ArchivoDeMod) {
                    ArchivoDeMod mod = (ArchivoDeMod)datos[0];
                    String clase = (datos[1] instanceof String) ? (String)datos[1] : null;
                    
                    if (clase != null) {
                        // Es una clase
                        if (datos.length == 2) {
                            List<String> ubicaciones = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(clase));
                            mostrarResultadosBusqueda(ubicaciones, MonitorDePID.idioma.referenciasClase());
                        } 
                        // Es un método
                        else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
                            ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo)datos[2];
                            List<ArchivoDeMod.Referencia> referencias = mod.buscarReferenciasEnMetodo(clase, metodo.obtenerNombre(), metodo.obtenerDescriptor());
                            mostrarReferenciasMetodo(referencias, MonitorDePID.idioma.referenciasMetodo());
                        }
                        // Es un campo
                        else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
                            ArchivoDeMod.InfoCampo campo = (ArchivoDeMod.InfoCampo)datos[2];
                            // Para campos, podríamos buscar referencias específicas si tenemos ASM
                            List<String> ubicaciones = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(clase));
                            mostrarResultadosBusqueda(ubicaciones, MonitorDePID.idioma.referenciasCampo());
                        }
                    }
                }
            }
        });
        menu.add(itemBuscarRef);

        menu.show(arbolModulos, e.getX(), e.getY());
    }

    private void mostrarResultadosBusqueda(List<String> resultados, String titulo) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(titulo);
        if (resultados.isEmpty()) {
            raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
        } else {
            for (String resultado : resultados) {
                raiz.add(new DefaultMutableTreeNode(resultado));
            }
        }
        modeloArbol = new DefaultTreeModel(raiz);
        arbolModulos.setModel(modeloArbol);
    }

    private void mostrarReferenciasMetodo(List<ArchivoDeMod.Referencia> referencias, String titulo) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(titulo);
        if (referencias.isEmpty()) {
            raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
        } else {
            for (ArchivoDeMod.Referencia ref : referencias) {
                String tipo = ref.esMetodo() ? MonitorDePID.idioma.metodo() : MonitorDePID.idioma.campo();
                raiz.add(new DefaultMutableTreeNode(tipo + ": " + ref.obtenerNombre() + " (" + ref.obtenerClase() + ")"));
            }
        }
        modeloArbol = new DefaultTreeModel(raiz);
        arbolModulos.setModel(modeloArbol);
    }

    private void mostrarDetallesNodo(Object nodo) {
        if (!(nodo instanceof DefaultMutableTreeNode)) return;
        
        Object userObject = ((DefaultMutableTreeNode)nodo).getUserObject();
        if (userObject == null) return;
        
        StringBuilder detalles = new StringBuilder();
        
        if (userObject instanceof ArchivoDeMod) {
            ArchivoDeMod mod = (ArchivoDeMod)userObject;
            detalles.append(MonitorDePID.idioma.detalleMod())
                    .append("\n")
                    .append(MonitorDePID.idioma.ubicacion()).append(": ").append(mod.ubicacion_para_publicar())
                    .append("\n\n");
            
            // Mostrar nombres del mod
            if (!mod.nombre().isEmpty()) {
                detalles.append(MonitorDePID.idioma.nombres()).append(":\n");
                for (String nombre : mod.nombre()) {
                    detalles.append("- ").append(nombre).append("\n");
                }
                detalles.append("\n");
            }
            
            // Mostrar clases
            detalles.append(MonitorDePID.idioma.clases()).append(" (").append(mod.clases().size()).append("):\n");
            for (String clase : mod.clases()) {
                detalles.append("- ").append(clase).append("\n");
            }
        } 
        else if (userObject instanceof Object[]) {
            Object[] datos = (Object[])userObject;
            if (datos.length >= 2 && datos[0] instanceof ArchivoDeMod) {
                ArchivoDeMod mod = (ArchivoDeMod)datos[0];
                String clase = (datos[1] instanceof String) ? (String)datos[1] : null;
                
                if (clase != null) {
                    // Detalles de clase
                    if (datos.length == 2) {
                        detalles.append(MonitorDePID.idioma.detalleClase()).append(" ").append(clase).append("\n\n");
                        detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar()).append("\n\n");
                        
                        // Mostrar métodos si ASM está disponible
                        if (Buscardor.esASMDisponible()) {
                            List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(clase);
                            detalles.append(MonitorDePID.idioma.metodos()).append(" (").append(metodos.size()).append("):\n");
                            for (ArchivoDeMod.InfoMetodo metodo : metodos) {
                                detalles.append("- ").append(metodo.obtenerNombre()).append(metodo.obtenerDescriptor()).append("\n");
                            }
                            detalles.append("\n");
                            
                            // Mostrar campos
                            List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(clase);
                            detalles.append(MonitorDePID.idioma.campos()).append(" (").append(campos.size()).append("):\n");
                            for (ArchivoDeMod.InfoCampo campo : campos) {
                                detalles.append("- ").append(campo.obtenerNombre()).append(" ").append(campo.obtenerDescriptor()).append("\n");
                            }
                        }
                    }
                    // Detalles de método
                    else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
                        ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo)datos[2];
                        detalles.append(MonitorDePID.idioma.detalleMetodo()).append(" ").append(metodo.obtenerNombre()).append(metodo.obtenerDescriptor()).append("\n\n");
                        detalles.append(MonitorDePID.idioma.clase()).append(": ").append(clase).append("\n");
                        detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar()).append("\n\n");
                        
                        // Mostrar referencias de métodos
                        detalles.append(MonitorDePID.idioma.referenciasAMetodos()).append(" (").append(metodo.obtenerReferenciasAMetodos().size()).append("):\n");
                        for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
                            detalles.append("- ").append(ref.obtenerClase()).append(".").append(ref.obtenerNombre()).append(ref.obtenerDescriptor()).append("\n");
                        }
                        detalles.append("\n");
                        
                        // Mostrar referencias de campos
                        detalles.append(MonitorDePID.idioma.referenciasACampos()).append(" (").append(metodo.obtenerReferenciasACampos().size()).append("):\n");
                        for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
                            detalles.append("- ").append(ref.obtenerClase()).append(".").append(ref.obtenerNombre()).append(" ").append(ref.obtenerDescriptor()).append("\n");
                        }
                    }
                    // Detalles de campo
                    else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
                        ArchivoDeMod.InfoCampo campo = (ArchivoDeMod.InfoCampo)datos[2];
                        detalles.append(MonitorDePID.idioma.detalleCampo()).append(" ").append(campo.obtenerNombre()).append("\n\n");
                        detalles.append(MonitorDePID.idioma.clase()).append(": ").append(clase).append("\n");
                        detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar()).append("\n");
                        detalles.append(MonitorDePID.idioma.tipo()).append(": ").append(campo.obtenerDescriptor()).append("\n");
                    }
                }
            }
        }
        
        areaContenido.setText(detalles.toString());
    }

    // Renderizador personalizado para el árbol
    private class ModuloTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                    boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component componente = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            if (value instanceof DefaultMutableTreeNode) {
                Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
                if (userObject != null) {
                    if (userObject instanceof ArchivoDeMod) {
                        setForeground(new Color(0, 0, 128)); // Azul oscuro para mods
                    } else if (userObject instanceof Object[]) {
                        Object[] datos = (Object[])userObject;
                        if (datos.length >= 2) {
                            if (datos[1] instanceof String && !((String)datos[1]).contains("(")) {
                                setForeground(Color.DARK_GRAY); // Gris oscuro para clases
                            } else if (datos[2] instanceof ArchivoDeMod.InfoMetodo) {
                                setForeground(new Color(0, 0, 153)); // Azul para métodos
                            } else if (datos[2] instanceof ArchivoDeMod.InfoCampo) {
                                setForeground(new Color(153, 0, 0)); // Rojo para campos
                            }
                        }
                    } else if (value.toString().contains(MonitorDePID.idioma.clases()) || 
                               value.toString().contains(MonitorDePID.idioma.metodos()) || 
                               value.toString().contains(MonitorDePID.idioma.campos()) ||
                               value.toString().contains(MonitorDePID.idioma.referencias())) {
                        setForeground(new Color(0, 102, 0)); // Verde para categorías
                    }
                }
            }
            return componente;
        }
    }
}