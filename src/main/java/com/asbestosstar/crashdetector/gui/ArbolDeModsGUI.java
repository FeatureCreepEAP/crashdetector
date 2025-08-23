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
        arbolModulos.setCellRenderer(new ModuloTreeCellRenderer());
        
        
        
        arbolModulos.addTreeSelectionListener(e -> {
            TreePath path = arbolModulos.getSelectionPath();
            if (path == null) return;

            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (nodo == null) return;

            Object userObject = nodo.getUserObject();

            // Si el objeto es tu envoltorio NodoConTexto, lo desempacamos
            if (userObject instanceof NodoConTexto) {
                mostrarDetallesNodo(((NodoConTexto) userObject).getObjeto());
            } else {
                mostrarDetallesNodo(userObject);
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

         for (ArchivoDeMod submod : mod.mods_en_mods()) {
             agregarModuloARaiz(nodoModulo, submod);
         }

         if (!mod.clases().isEmpty()) {
             Map<String, List<String>> clasesPorPaquete = agruparClasesPorPaquete(mod.clases());

             for (Map.Entry<String, List<String>> entry : clasesPorPaquete.entrySet()) {
                 String paquete = entry.getKey();
                 List<String> clasesEnPaquete = entry.getValue();

                 if (paquete.isEmpty()) {
                     for (String nombreClase : clasesEnPaquete) {
                         DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
                             new NodoConTexto(nombreClase, new Object[]{mod, nombreClase})
                         );

                         if (Buscardor.esASMDisponible()) {
                             String claseCompleta = nombreClase;
                             List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseCompleta);
                             if (!metodos.isEmpty()) {
                                 DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(
                                     new NodoConTexto(MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")", null)
                                 );
                                 for (ArchivoDeMod.InfoMetodo metodo : metodos) {
                                     DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
                                         new NodoConTexto(metodo.obtenerNombre() + metodo.obtenerDescriptor(), new Object[]{mod, claseCompleta, metodo})
                                     );

                                     DefaultMutableTreeNode nodoReferencias = new DefaultMutableTreeNode(
                                         new NodoConTexto(MonitorDePID.idioma.referencias(), null)
                                     );
                                     for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
                                         nodoReferencias.add(new DefaultMutableTreeNode(
                                             new NodoConTexto(MonitorDePID.idioma.metodo() + ": " + refMetodo.obtenerNombre() + refMetodo.obtenerDescriptor() +
                                                              " (" + refMetodo.obtenerClase() + ")", null)
                                         ));
                                     }
                                     for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
                                         nodoReferencias.add(new DefaultMutableTreeNode(
                                             new NodoConTexto(MonitorDePID.idioma.campo() + ": " + refCampo.obtenerNombre() +
                                                              " (" + refCampo.obtenerClase() + ")", null)
                                         ));
                                     }
                                     if (nodoReferencias.getChildCount() > 0) nodoMetodo.add(nodoReferencias);
                                     nodoMetodos.add(nodoMetodo);
                                 }
                                 nodoClase.add(nodoMetodos);
                             }

                             List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseCompleta);
                             if (!campos.isEmpty()) {
                                 DefaultMutableTreeNode nodoCampos = new DefaultMutableTreeNode(
                                     new NodoConTexto(MonitorDePID.idioma.campos() + " (" + campos.size() + ")", null)
                                 );
                                 for (ArchivoDeMod.InfoCampo campo : campos) {
                                     DefaultMutableTreeNode nodoCampo = new DefaultMutableTreeNode(
                                         new NodoConTexto(campo.obtenerNombre() + " " + campo.obtenerDescriptor(), new Object[]{mod, claseCompleta, campo})
                                     );
                                     nodoCampos.add(nodoCampo);
                                 }
                                 nodoClase.add(nodoCampos);
                             }
                         }
                         nodoModulo.add(nodoClase);
                     }
                 } else {
                     DefaultMutableTreeNode nodoPaquete = new DefaultMutableTreeNode(
                         new NodoConTexto(paquete, paquete)
                     );

                     for (String nombreClase : clasesEnPaquete) {
                         DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
                             new NodoConTexto(nombreClase, new Object[]{mod, paquete + "." + nombreClase})
                         );

                         if (Buscardor.esASMDisponible()) {
                             String claseCompleta = paquete + "." + nombreClase;
                             List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseCompleta);
                             if (!metodos.isEmpty()) {
                                 DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(
                                     new NodoConTexto(MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")", null)
                                 );
                                 for (ArchivoDeMod.InfoMetodo metodo : metodos) {
                                     DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
                                         new NodoConTexto(metodo.obtenerNombre() + metodo.obtenerDescriptor(), new Object[]{mod, claseCompleta, metodo})
                                     );

                                     DefaultMutableTreeNode nodoReferencias = new DefaultMutableTreeNode(
                                         new NodoConTexto(MonitorDePID.idioma.referencias(), null)
                                     );
                                     for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
                                         nodoReferencias.add(new DefaultMutableTreeNode(
                                             new NodoConTexto(MonitorDePID.idioma.metodo() + ": " + refMetodo.obtenerNombre() + refMetodo.obtenerDescriptor() +
                                                              " (" + refMetodo.obtenerClase() + ")", null)
                                         ));
                                     }
                                     for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
                                         nodoReferencias.add(new DefaultMutableTreeNode(
                                             new NodoConTexto(MonitorDePID.idioma.campo() + ": " + refCampo.obtenerNombre() +
                                                              " (" + refCampo.obtenerClase() + ")", null)
                                         ));
                                     }
                                     if (nodoReferencias.getChildCount() > 0) nodoMetodo.add(nodoReferencias);
                                     nodoMetodos.add(nodoMetodo);
                                 }
                                 nodoClase.add(nodoMetodos);
                             }

                             List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseCompleta);
                             if (!campos.isEmpty()) {
                                 DefaultMutableTreeNode nodoCampos = new DefaultMutableTreeNode(
                                     new NodoConTexto(MonitorDePID.idioma.campos() + " (" + campos.size() + ")", null)
                                 );
                                 for (ArchivoDeMod.InfoCampo campo : campos) {
                                     DefaultMutableTreeNode nodoCampo = new DefaultMutableTreeNode(
                                         new NodoConTexto(campo.obtenerNombre() + " " + campo.obtenerDescriptor(), new Object[]{mod, claseCompleta, campo})
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
     }

     private boolean buscarEnModulo(DefaultMutableTreeNode padre, ArchivoDeMod mod, String filtro, String tipoFiltro) {
         boolean agregarModulo = false;
         String ubicacionPublica = mod.ubicacion_para_publicar().toLowerCase();

         if (ubicacionPublica.contains(filtro)) agregarModulo = true;

         for (ArchivoDeMod submod : mod.mods_en_mods()) {
             DefaultMutableTreeNode nodoSubmod = new DefaultMutableTreeNode(
                 new NodoConTexto(submod.ubicacion_para_publicar(), submod)
             );
             if (buscarEnModulo(nodoSubmod, submod, filtro, tipoFiltro)) {
                 padre.add(nodoSubmod);
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

             for (Map.Entry<String, List<String>> entry : clasesPorPaquete.entrySet()) {
                 String paquete = entry.getKey();
                 if (paquete.toLowerCase().contains(filtro)) {
                     if (paquete.isEmpty()) {
                         for (String clase : entry.getValue()) {
                             String nombreClase = clase.substring(clase.lastIndexOf('.') + 1);
                             DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
                                 new NodoConTexto(nombreClase, new Object[]{mod, clase})
                             );
                             padre.add(nodoClase);
                         }
                     } else {
                         DefaultMutableTreeNode nodoPaquete = new DefaultMutableTreeNode(
                             new NodoConTexto(paquete, paquete)
                         );
                         padre.add(nodoPaquete);
                         agregarModulo = true;
                     }
                 }
             }
         }

         if ("Todos".equals(tipoFiltro) || MonitorDePID.idioma.filtroClases().equals(tipoFiltro)) {
             for (String clase : mod.clases()) {
                 String nombreClase = clase.substring(clase.lastIndexOf('.') + 1);
                 if (nombreClase.toLowerCase().contains(filtro)) {
                     DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
                         new NodoConTexto(nombreClase, new Object[]{mod, clase})
                     );
                     padre.add(nodoClase);
                     agregarModulo = true;
                 }
             }
         }

         if (Buscardor.esASMDisponible() && ("Todos".equals(tipoFiltro) || MonitorDePID.idioma.filtroMetodos().equals(tipoFiltro))) {
             for (String clase : mod.clases()) {
                 for (ArchivoDeMod.InfoMetodo metodo : mod.obtenerMetodosConReferencias(clase)) {
                     String nombreMetodo = metodo.obtenerNombre() + metodo.obtenerDescriptor();
                     if (nombreMetodo.toLowerCase().contains(filtro)) {
                         DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
                             new NodoConTexto(nombreMetodo, new Object[]{mod, clase, metodo})
                         );
                         padre.add(nodoMetodo);
                         agregarModulo = true;
                     }
                 }
             }
         }

         if (Buscardor.esASMDisponible() && ("Todos".equals(tipoFiltro) || MonitorDePID.idioma.filtroCampos().equals(tipoFiltro))) {
             for (String clase : mod.clases()) {
                 for (ArchivoDeMod.InfoCampo campo : mod.obtenerCampos(clase)) {
                     String nombreCampo = campo.obtenerNombre() + " " + campo.obtenerDescriptor();
                     if (nombreCampo.toLowerCase().contains(filtro)) {
                         DefaultMutableTreeNode nodoCampo = new DefaultMutableTreeNode(
                             new NodoConTexto(nombreCampo, new Object[]{mod, clase, campo})
                         );
                         padre.add(nodoCampo);
                         agregarModulo = true;
                     }
                 }
             }
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
        if (nodo == null) {
            areaContenido.setText("");
            return;
        }
        
        StringBuilder detalles = new StringBuilder();
        
        if (nodo instanceof ArchivoDeMod) {
            ArchivoDeMod mod = (ArchivoDeMod)nodo;
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
            
            for (Map.Entry<String, List<String>> entry : clasesPorPaquete.entrySet()) {
                String paquete = entry.getKey();
                List<String> clases = entry.getValue();
                
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
        else if (nodo instanceof String) {
            // Mostrar detalles del paquete
            String paquete = (String)nodo;
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
        else if (nodo instanceof Object[]) {
            Object[] datos = (Object[])nodo;
            if (datos.length >= 2 && datos[0] instanceof ArchivoDeMod) {
                ArchivoDeMod mod = (ArchivoDeMod)datos[0];
                String clase = (datos[1] instanceof String) ? (String)datos[1] : null;
                
                if (clase != null) {
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
    
    
    
    
    

    // Renderizador personalizado para el árbol
    private class ModuloTreeCellRenderer extends DefaultTreeCellRenderer {


    	@Override
    	public Component getTreeCellRendererComponent(JTree tree, Object value,
    	                                              boolean selected, boolean expanded,
    	                                              boolean leaf, int row, boolean hasFocus) {
    	    super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

    	    if (value instanceof DefaultMutableTreeNode) {
    	        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

    	        if (userObject instanceof NodoConTexto) {
    	            NodoConTexto nct = (NodoConTexto) userObject;
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
    	            } else {
    	                setForeground(new Color(0, 102, 0)); // Verde paquetes
    	            }
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