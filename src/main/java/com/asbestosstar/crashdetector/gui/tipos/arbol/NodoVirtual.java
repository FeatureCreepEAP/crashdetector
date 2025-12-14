package com.asbestosstar.crashdetector.gui.tipos.arbol;

import java.util.List;

/**
 * Nodo virtual que carga sus hijos solo cuando se solicitan.
 */
public abstract class NodoVirtual {
    protected volatile List<NodoVirtual> hijosCache;
    protected final String nombre;
    protected final Object payload; // ArchivoDeMod, PathDescriptor, etc.

    public NodoVirtual(String nombre, Object payload) {
        this.nombre = nombre;
        this.payload = payload;
    }

    public String obtenerNombre() { return nombre; }
    public Object obtenerPayload() { return payload; }

    /**
     * Carga los hijos si no están en caché.
     */
    protected synchronized void cargarHijosSiEsNecesario() {
        if (hijosCache == null) {
            hijosCache = cargarHijos();
        }
    }

    protected abstract List<NodoVirtual> cargarHijos();

    public List<NodoVirtual> obtenerHijos() {
        cargarHijosSiEsNecesario();
        return hijosCache;
    }

    public int obtenerNumeroHijos() {
        return obtenerHijos().size();
    }

    public NodoVirtual obtenerHijo(int indice) {
        return obtenerHijos().get(indice);
    }

    public int obtenerIndiceDeHijo(NodoVirtual hijo) {
        return obtenerHijos().indexOf(hijo);
    }
}