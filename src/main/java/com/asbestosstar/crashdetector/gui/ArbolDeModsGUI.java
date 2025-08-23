package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        imagenHamu = new JLabel(new ImageIcon("crash_detector/imagenes/hamu.png"));
        imagenHamu.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Botón Reset
        botonReset = new JButton(MonitorDePID.idioma.botonResetearArbol());
        botonReset.addActionListener(e -> resetearArbol());

        // Árbol de módulos
        arbolModulos = new JTree();
        arbolModulos.setRootVisible(false);
        arbolModulos.setShowsRootHandles(true);
        arbolModulos.setCellRenderer(new RenderizadorCeldasArbol());
        
        // Agregar soporte para menú contextual
        arbolModulos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContexto(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuContexto(e);
                }
            }
        });
        
        arbolModulos.addTreeSelectionListener(e -> {
            TreePath ruta = arbolModulos.getSelectionPath();
            if (ruta == null) return;

            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) ruta.getLastPathComponent();
            if (nodo == null) return;

            Object objetoUsuario = nodo.getUserObject();

            // Si el objeto es el envoltorio NodoConTexto, lo desempacamos
            if (objetoUsuario instanceof NodoConTexto) {
                mostrarDetallesNodo(((NodoConTexto) objetoUsuario).getObjeto());
            } else {
                mostrarDetallesNodo(objetoUsuario);
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
        JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(arbolModulos),
                new JScrollPane(areaContenido));
        panelDividido.setDividerLocation(500);
        barraSuperior.add(panelDividido, BorderLayout.CENTER);

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

    /**
     * Agrupa las clases por paquete para una mejor organización en el árbol.
     */
    private Map<String, List<String>> agruparClasesPorPaquete(List<String> clases) {
        Map<String, List<String>> clasesPorPaquete = new TreeMap<>(); // TreeMap para orden alfabético
        
        for (String clase : clases) {
            String paquete = "";
            String nombreClase = clase;
            
            int indiceUltimoPunto = clase.lastIndexOf('.');
            if (indiceUltimoPunto > 0) {
                paquete = clase.substring(0, indiceUltimoPunto);
                nombreClase = clase.substring(indiceUltimoPunto + 1);
            }
            
            clasesPorPaquete.computeIfAbsent(paquete, k -> new ArrayList<>()).add(nombreClase);
        }
        
        return clasesPorPaquete;
    }

    private void agregarModuloARaiz(DefaultMutableTreeNode padre, ArchivoDeMod mod) {
        String ubicacionPublica = mod.ubicacion_para_publicar();
        DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(
            new NodoConTexto(ubicacionPublica, mod)
        );
        padre.add(nodoModulo);

        // Agregar submods recursivamente
        for (ArchivoDeMod submod : mod.mods_en_mods()) {
            agregarModuloARaiz(nodoModulo, submod);
        }

        // Agregar paquetes directamente bajo el mod (sin carpeta "Clases")
        if (!mod.clases().isEmpty()) {
            // Agrupar clases por paquete
            Map<String, List<String>> clasesPorPaquete = agruparClasesPorPaquete(mod.clases());
            
            for (Map.Entry<String, List<String>> entrada : clasesPorPaquete.entrySet()) {
                String paquete = entrada.getKey();
                List<String> clasesEnPaquete = entrada.getValue();
                
                // Crear nodo de paquete
                DefaultMutableTreeNode nodoPaquete;
                if (paquete.isEmpty()) {
                    nodoPaquete = new DefaultMutableTreeNode(
                        new NodoConTexto(
                            "(paquete por defecto) (" + clasesEnPaquete.size() + " clases)",
                            paquete
                        )
                    );
                } else {
                    nodoPaquete = new DefaultMutableTreeNode(
                        new NodoConTexto(
                            paquete + " (" + clasesEnPaquete.size() + " clases)",
                            paquete
                        )
                    );
                }
                
                // Agregar clases al paquete
                for (String nombreClase : clasesEnPaquete) {
                    String clasePuntos = paquete.isEmpty() ? nombreClase : paquete + "." + nombreClase;
                    String claseInterna = Buscardor.convertirFormatoClase(clasePuntos);
                    
                    DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
                        new NodoConTexto(nombreClase, new Object[]{mod, clasePuntos})
                    );
                    
                    if (Buscardor.esASMDisponible() && mod.existeClase(claseInterna)) {
                        // Usar formato interno para análisis ASM
                        List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
                        if (!metodos.isEmpty()) {
                            DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(
                                new NodoConTexto(
                                    MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")",
                                    "contenedor_metodos"
                                )
                            );
                            for (ArchivoDeMod.InfoMetodo metodo : metodos) {
                                DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
                                    new NodoConTexto(
                                        metodo.obtenerNombre() + metodo.obtenerDescriptor(),
                                        new Object[]{mod, clasePuntos, metodo}
                                    )
                                );
                                
                                DefaultMutableTreeNode nodoReferencias = new DefaultMutableTreeNode(
                                    new NodoConTexto(
                                        MonitorDePID.idioma.referencias(),
                                        "contenedor_referencias"
                                    )
                                );
                                for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
                                    // Convertir nombres de clases en referencias para mostrar
                                    String nombreClaseMostrar = Buscardor.convertirFormatoClasePuntos(refMetodo.obtenerClase());
                                    nodoReferencias.add(new DefaultMutableTreeNode(
                                        new NodoConTexto(
                                            MonitorDePID.idioma.metodo() + ": " + refMetodo.obtenerNombre() + 
                                            refMetodo.obtenerDescriptor() + " (" + nombreClaseMostrar + ")",
                                            refMetodo
                                        )
                                    ));
                                }
                                for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
                                    // Convertir nombres de clases en referencias para mostrar
                                    String nombreClaseMostrar = Buscardor.convertirFormatoClasePuntos(refCampo.obtenerClase());
                                    nodoReferencias.add(new DefaultMutableTreeNode(
                                        new NodoConTexto(
                                            MonitorDePID.idioma.campo() + ": " + refCampo.obtenerNombre() + 
                                            " (" + nombreClaseMostrar + ")",
                                            refCampo
                                        )
                                    ));
                                }
                                if (nodoReferencias.getChildCount() > 0) {
                                    nodoMetodo.add(nodoReferencias);
                                }
                                nodoMetodos.add(nodoMetodo);
                            }
                            nodoClase.add(nodoMetodos);
                        }
                        
                        List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseInterna);
                        if (!campos.isEmpty()) {
                            DefaultMutableTreeNode nodoCampos = new DefaultMutableTreeNode(
                                new NodoConTexto(
                                    MonitorDePID.idioma.campos() + " (" + campos.size() + ")",
                                    "contenedor_campos"
                                )
                            );
                            for (ArchivoDeMod.InfoCampo campo : campos) {
                                DefaultMutableTreeNode nodoCampo = new DefaultMutableTreeNode(
                                    new NodoConTexto(
                                        campo.obtenerNombre() + " " + campo.obtenerDescriptor(),
                                        new Object[]{mod, clasePuntos, campo}
                                    )
                                );
                                nodoCampos.add(nodoCampo);
                            }
                            nodoClase.add(nodoCampos);
                        }
                    }
                    nodoPaquete.add(nodoClase);
                }
                nodoModulo.add(nodoPaquete);
            }
        }
    }
    
    private boolean buscarEnModulo(DefaultMutableTreeNode padre, ArchivoDeMod mod, String filtro, String tipoFiltro) {
        boolean agregarModulo = false;
        String ubicacionPublica = mod.ubicacion_para_publicar().toLowerCase();

        // Crear nodo del módulo con NodoConTexto apropiado
        DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(
            new NodoConTexto(mod.ubicacion_para_publicar(), mod)
        );

        if (ubicacionPublica.contains(filtro)) agregarModulo = true;

        for (ArchivoDeMod submod : mod.mods_en_mods()) {
            if (buscarEnModulo(nodoModulo, submod, filtro, tipoFiltro)) {
                agregarModulo = true;
            }
        }

        if ("Todos".equals(tipoFiltro) || MonitorDePID.idioma.filtroPaquetes().equals(tipoFiltro)) {
            Map<String, List<String>> clasesPorPaquete = new HashMap<>();
            for (String clase : mod.clases()) {
                String paquete = "";
                int indiceUltimoPunto = clase.lastIndexOf('.');
                if (indiceUltimoPunto > 0) paquete = clase.substring(0, indiceUltimoPunto);
                clasesPorPaquete.computeIfAbsent(paquete, k -> new ArrayList<>()).add(clase);
            }

            for (Map.Entry<String, List<String>> entrada : clasesPorPaquete.entrySet()) {
                String paquete = entrada.getKey();
                if (paquete.toLowerCase().contains(filtro)) {
                    if (paquete.isEmpty()) {
                        for (String clase : entrada.getValue()) {
                            String nombreClase = clase.substring(clase.lastIndexOf('.') + 1);
                            DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
                                new NodoConTexto(clase, new Object[]{mod, clase})
                            );
                            nodoModulo.add(nodoClase);
                        }
                    } else {
                        DefaultMutableTreeNode nodoPaquete = new DefaultMutableTreeNode(
                            new NodoConTexto(paquete, paquete)
                        );
                        nodoModulo.add(nodoPaquete);
                    }
                    agregarModulo = true;
                }
            }
        }

        if ("Todos".equals(tipoFiltro) || MonitorDePID.idioma.filtroClases().equals(tipoFiltro)) {
            for (String clase : mod.clases()) {
                if (clase.toLowerCase().contains(filtro)) {
                    DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
                        new NodoConTexto(clase, new Object[]{mod, clase})
                    );
                    nodoModulo.add(nodoClase);
                    agregarModulo = true;
                }
            }
        }

        if (Buscardor.esASMDisponible() && ("Todos".equals(tipoFiltro) || MonitorDePID.idioma.filtroMetodos().equals(tipoFiltro))) {
            for (String clase : mod.clases()) {
                String claseInterna = Buscardor.convertirFormatoClase(clase);
                if (mod.existeClase(claseInterna)) {
                    for (ArchivoDeMod.InfoMetodo metodo : mod.obtenerMetodosConReferencias(claseInterna)) {
                        String nombreMetodo = metodo.obtenerNombre() + metodo.obtenerDescriptor();
                        if (nombreMetodo.toLowerCase().contains(filtro)) {
                            DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
                                new NodoConTexto(nombreMetodo, new Object[]{mod, clase, metodo})
                            );
                            nodoModulo.add(nodoMetodo);
                            agregarModulo = true;
                        }
                    }
                }
            }
        }

        if (Buscardor.esASMDisponible() && ("Todos".equals(tipoFiltro) || MonitorDePID.idioma.filtroCampos().equals(tipoFiltro))) {
            for (String clase : mod.clases()) {
                String claseInterna = Buscardor.convertirFormatoClase(clase);
                if (mod.existeClase(claseInterna)) {
                    for (ArchivoDeMod.InfoCampo campo : mod.obtenerCampos(claseInterna)) {
                        String nombreCampo = campo.obtenerNombre() + " " + campo.obtenerDescriptor();
                        if (nombreCampo.toLowerCase().contains(filtro)) {
                            DefaultMutableTreeNode nodoCampo = new DefaultMutableTreeNode(
                                new NodoConTexto(nombreCampo, new Object[]{mod, clase, campo})
                            );
                            nodoModulo.add(nodoCampo);
                            agregarModulo = true;
                        }
                    }
                }
            }
        }

        if (agregarModulo) {
            padre.add(nodoModulo);
        }

        return agregarModulo;
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

        Object objetoUsuario = ((DefaultMutableTreeNode)nodo).getUserObject();
        if (objetoUsuario == null) return;

        // Extraer el objeto real de NodoConTexto si es necesario
        final Object objetoReal;
        if (objetoUsuario instanceof NodoConTexto) {
            objetoReal = ((NodoConTexto)objetoUsuario).getObjeto();
        } else {
            objetoReal = objetoUsuario;
        }

        JPopupMenu menu = new JPopupMenu();

        // Opción para buscar referencias
        JMenuItem itemBuscarRef = new JMenuItem(MonitorDePID.idioma.buscarReferencias());
        itemBuscarRef.addActionListener(ae -> {
            if (objetoReal instanceof ArchivoDeMod) {
                // Es un mod
                List<ArchivoDeMod> mods = new ArrayList<>();
                mods.add((ArchivoDeMod)objetoReal);
                List<String> ubicaciones = Buscardor.obtenerUbicaciones(mods);
                mostrarResultadosBusqueda(ubicaciones, MonitorDePID.idioma.referenciasMod());
            } else if (objetoReal instanceof Object[]) {
                Object[] datos = (Object[])objetoReal;
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
                            String claseInterna = Buscardor.convertirFormatoClase(clase);
                            List<ArchivoDeMod.Referencia> referencias = mod.buscarReferenciasEnMetodo(claseInterna, metodo.obtenerNombre(), metodo.obtenerDescriptor());
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
                String nombreClaseMostrar = Buscardor.convertirFormatoClasePuntos(ref.obtenerClase());
                raiz.add(new DefaultMutableTreeNode(tipo + ": " + ref.obtenerNombre() + " (" + nombreClaseMostrar + ")"));
            }
        }
        modeloArbol = new DefaultTreeModel(raiz);
        arbolModulos.setModel(modeloArbol);
    }

    /**
     * Muestra los detalles del nodo seleccionado en el área de contenido.
     * Este método maneja correctamente el formato de nombres de clases para ASM,
     * asegurando que los métodos, campos y referencias se muestren adecuadamente.
     * 
     * @param nodo El nodo del árbol seleccionado por el usuario
     */
    private void mostrarDetallesNodo(Object nodo) {
        if (nodo == null) {
            areaContenido.setText("");
            return;
        }
        
        StringBuilder detalles = new StringBuilder();
        Object objetoReal = nodo;
        
        // Si es un nodo del árbol, obtener el userObject
        if (nodo instanceof DefaultMutableTreeNode) {
            Object objetoUsuario = ((DefaultMutableTreeNode)nodo).getUserObject();
            if (objetoUsuario == null) {
                areaContenido.setText("");
                return;
            }
            
            // Si es un NodoConTexto, obtener el objeto real
            if (objetoUsuario instanceof NodoConTexto) {
                objetoReal = ((NodoConTexto)objetoUsuario).getObjeto();
            } else {
                objetoReal = objetoUsuario;
            }
        }
        
        // Manejar diferentes tipos de objetos
        if (objetoReal instanceof ArchivoDeMod) {
            ArchivoDeMod mod = (ArchivoDeMod)objetoReal;
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
            
            // Mostrar clases organizadas por paquete
            Map<String, List<String>> clasesPorPaquete = agruparClasesPorPaquete(mod.clases());
            
            for (Map.Entry<String, List<String>> entrada : clasesPorPaquete.entrySet()) {
                String paquete = entrada.getKey();
                List<String> clases = entrada.getValue();
                
                if (paquete.isEmpty()) {
                    detalles.append(MonitorDePID.idioma.clases()).append(" (").append(clases.size()).append("):\n");
                    for (String nombreClase : clases) {
                        detalles.append("- ").append(nombreClase).append("\n");
                    }
                } else {
                    detalles.append(paquete).append(" (").append(clases.size()).append("):\n");
                    for (String nombreClase : clases) {
                        detalles.append("  - ").append(nombreClase).append("\n");
                    }
                }
                detalles.append("\n");
            }
        } 
        else if (objetoReal instanceof String) {
            // Mostrar detalles del paquete
            String paquete = (String)objetoReal;
            detalles.append(MonitorDePID.idioma.paquete()).append(" ").append(paquete).append("\n");
            
            // Encontrar todos los mods que contienen clases en este paquete
            List<ArchivoDeMod> modsConPaquete = new ArrayList<>();
            for (ArchivoDeMod mod : Buscardor.mods) {
                for (String clase : mod.clases()) {
                    String paqueteClase = "";
                    int indiceUltimoPunto = clase.lastIndexOf('.');
                    if (indiceUltimoPunto > 0) paqueteClase = clase.substring(0, indiceUltimoPunto);
                    if (paqueteClase.equals(paquete)) {
                        modsConPaquete.add(mod);
                        break;
                    }
                }
            }
            
            detalles.append(MonitorDePID.idioma.modulos()).append(" (").append(modsConPaquete.size()).append("):\n");
            for (ArchivoDeMod mod : modsConPaquete) {
                detalles.append("- ").append(mod.ubicacion_para_publicar()).append("\n");
            }
            
            detalles.append("\n");
            
            // Mostrar clases en este paquete
            detalles.append(MonitorDePID.idioma.clases()).append(":\n");
            for (ArchivoDeMod mod : modsConPaquete) {
                for (String clase : mod.clases()) {
                    String paqueteClase = "";
                    int indiceUltimoPunto = clase.lastIndexOf('.');
                    if (indiceUltimoPunto > 0) paqueteClase = clase.substring(0, indiceUltimoPunto);
                    if (paqueteClase.equals(paquete)) {
                        String nombreClase = clase.substring(indiceUltimoPunto + 1);
                        detalles.append("  - ").append(nombreClase).append("\n");
                    }
                }
            }
        }
        else if (objetoReal instanceof Object[]) {
            Object[] datos = (Object[])objetoReal;
            if (datos.length >= 2 && datos[0] instanceof ArchivoDeMod) {
                ArchivoDeMod mod = (ArchivoDeMod)datos[0];
                String clase = (datos[1] instanceof String) ? (String)datos[1] : null;
                
                if (clase != null) {
                    // Convertir al formato interno de ASM
                    String claseInterna = convertirFormatoClase(clase);
                    
                    // Verificar si la clase existe
                    boolean claseExiste = mod.existeClase(claseInterna);
                    
                    // Detalles de clase
                    if (datos.length == 2) {
                        String nombreClase = clase;
                        String paquete = "";
                        
                        int indicePunto = clase.lastIndexOf('.');
                        if (indicePunto > 0) {
                            paquete = clase.substring(0, indicePunto);
                            nombreClase = clase.substring(indicePunto + 1);
                        }
                        
                        detalles.append(MonitorDePID.idioma.detalleClase()).append(" ").append(nombreClase).append("\n");
                        if (!paquete.isEmpty()) {
                            detalles.append(MonitorDePID.idioma.paquete()).append(": ").append(paquete).append("\n\n");
                        }
                        detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar()).append("\n\n");
                        
                        // Mostrar métodos si ASM está disponible y la clase existe
                        if (Buscardor.esASMDisponible() && claseExiste) {
                            List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
                            detalles.append(MonitorDePID.idioma.metodos()).append(" (").append(metodos.size()).append("):\n");
                            for (ArchivoDeMod.InfoMetodo metodo : metodos) {
                                detalles.append("- ").append(metodo.obtenerNombre()).append(metodo.obtenerDescriptor()).append("\n");
                            }
                            detalles.append("\n");
                            
                            // Mostrar campos
                            List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseInterna);
                            detalles.append(MonitorDePID.idioma.campos()).append(" (").append(campos.size()).append("):\n");
                            for (ArchivoDeMod.InfoCampo campo : campos) {
                                detalles.append("- ").append(campo.obtenerNombre()).append(" ").append(campo.obtenerDescriptor()).append("\n");
                            }
                        }
                    }
                    // Detalles de método
                    else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
                        ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo)datos[2];
                        String nombreClase = ((String)datos[1]);
                        
                        int indicePunto = nombreClase.lastIndexOf('.');
                        if (indicePunto > 0) {
                            nombreClase = nombreClase.substring(indicePunto + 1);
                        }
                        
                        detalles.append(MonitorDePID.idioma.detalleMetodo()).append(" ").append(metodo.obtenerNombre()).append(metodo.obtenerDescriptor()).append("\n");
                        detalles.append(MonitorDePID.idioma.clase()).append(": ").append(nombreClase).append("\n");
                        detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar()).append("\n\n");
                        
                        // Mostrar referencias de métodos
                        detalles.append(MonitorDePID.idioma.referenciasAMetodos()).append(" (").append(metodo.obtenerReferenciasAMetodos().size()).append("):\n");
                        for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
                            String nombreClaseRef = convertirFormatoClasePuntos(ref.obtenerClase());
                            detalles.append("- ").append(nombreClaseRef).append(".").append(ref.obtenerNombre()).append(ref.obtenerDescriptor()).append("\n");
                        }
                        detalles.append("\n");
                        
                        // Mostrar referencias de campos
                        detalles.append(MonitorDePID.idioma.referenciasACampos()).append(" (").append(metodo.obtenerReferenciasACampos().size()).append("):\n");
                        for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
                            String nombreClaseRef = convertirFormatoClasePuntos(ref.obtenerClase());
                            detalles.append("- ").append(nombreClaseRef).append(".").append(ref.obtenerNombre()).append(" ").append(ref.obtenerDescriptor()).append("\n");
                        }
                    }
                    // Detalles de campo
                    else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
                        ArchivoDeMod.InfoCampo campo = (ArchivoDeMod.InfoCampo)datos[2];
                        String nombreClase = ((String)datos[1]);
                        
                        int indicePunto = nombreClase.lastIndexOf('.');
                        if (indicePunto > 0) {
                            nombreClase = nombreClase.substring(indicePunto + 1);
                        }
                        
                        detalles.append(MonitorDePID.idioma.detalleCampo()).append(" ").append(campo.obtenerNombre()).append("\n");
                        detalles.append(MonitorDePID.idioma.clase()).append(": ").append(nombreClase).append("\n");
                        detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar()).append("\n");
                        detalles.append(MonitorDePID.idioma.tipo()).append(": ").append(campo.obtenerDescriptor()).append("\n");
                    }
                }
            }
        }
        
        areaContenido.setText(detalles.toString());
    }

    /**
     * Convierte un nombre de clase del formato de puntos (java.lang.Object) 
     * al formato interno de ASM (java/lang/Object)
     */
    private String convertirFormatoClase(String nombreClase) {
        // Si ya tiene '/', asumimos que está en formato interno
        if (nombreClase.contains("/")) {
            return nombreClase;
        }
        // Si tiene '.', convertimos los puntos a barras
        else if (nombreClase.contains(".")) {
            return nombreClase.replace('.', '/');
        }
        // Si no tiene ni '.' ni '/', es una clase en el paquete por defecto
        else {
            return nombreClase;
        }
    }

    /**
     * Convierte un nombre de clase del formato interno de ASM (java/lang/Object)
     * al formato de puntos (java.lang.Object) para mostrar en la interfaz
     */
    private String convertirFormatoClasePuntos(String nombreClase) {
        // Si ya tiene '.', asumimos que está en formato de puntos
        if (nombreClase.contains(".")) {
            return nombreClase;
        }
        // Si tiene '/', convertimos las barras a puntos
        else if (nombreClase.contains("/")) {
            return nombreClase.replace('/', '.');
        }
        // Si no tiene ni '.' ni '/', es una clase en el paquete por defecto
        else {
            return nombreClase;
        }
    }

    // Renderizador personalizado para el árbol
    private class RenderizadorCeldasArbol extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            if (value instanceof DefaultMutableTreeNode) {
                Object objetoUsuario = ((DefaultMutableTreeNode) value).getUserObject();

                if (objetoUsuario instanceof NodoConTexto) {
                    NodoConTexto nct = (NodoConTexto) objetoUsuario;
                    setText(nct.getTexto());

                    Object objeto = nct.getObjeto();
                    if (objeto instanceof ArchivoDeMod) {
                        setForeground(new Color(0, 0, 128)); // Azul para mods
                    } else if (objeto instanceof Object[]) {
                        Object[] datos = (Object[]) objeto;
                        if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
                            setForeground(new Color(0, 0, 153)); // Azul métodos
                        } else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
                            setForeground(new Color(153, 0, 0)); // Rojo campos
                        } else {
                            setForeground(Color.DARK_GRAY); // Gris clases
                        }
                    } else if (objeto instanceof String && !nct.getTexto().contains("contenedor")) {
                        setForeground(new Color(0, 102, 0)); // Verde paquetes
                    } else {
                        setForeground(Color.BLACK); // Color por defecto para contenedores
                    }
                } else {
                    // Manejar casos donde objetoUsuario no es NodoConTexto (como nodo raíz)
                    setForeground(Color.BLACK);
                }
            }
            return this;
        }
    }
    
    // Nodo de árbol con texto fijo + objeto asociado
    private static class NodoConTexto {
        private final String texto;
        private final Object objeto;

        public NodoConTexto(String texto, Object objeto) {
            this.texto = texto;
            this.objeto = objeto;
        }

        public String getTexto() {
            return texto;
        }

        public Object getObjeto() {
            return objeto;
        }

        @Override
        public String toString() {
            return texto; // siempre muestra el texto esperado
        }
    }
}