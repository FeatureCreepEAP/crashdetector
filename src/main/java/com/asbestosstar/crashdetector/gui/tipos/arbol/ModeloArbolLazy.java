package com.asbestosstar.crashdetector.gui.tipos.arbol;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Modelo de árbol virtualizado que carga nodos solo cuando se expanden.
 */
public class ModeloArbolLazy implements TreeModel {
    private final Object raiz;
    private final List<TreeModelListener> oyentes = new ArrayList<>();

    public ModeloArbolLazy(Object raiz) {
        this.raiz = raiz;
    }

    @Override
    public Object getRoot() {
        return raiz;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof NodoVirtual) {
            return ((NodoVirtual) parent).obtenerHijo(index);
        }
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof NodoVirtual) {
            return ((NodoVirtual) parent).obtenerNumeroHijos();
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        // No editable
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof NodoVirtual && child instanceof NodoVirtual) {
            return ((NodoVirtual) parent).obtenerIndiceDeHijo((NodoVirtual) child);
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        oyentes.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        oyentes.remove(l);
    }

    public void notificarCambioEstructura() {
        TreeModelEvent evento = new TreeModelEvent(this, new Object[]{raiz});
        for (TreeModelListener l : oyentes) {
            l.treeStructureChanged(evento);
        }
    }
}