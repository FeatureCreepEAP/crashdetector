package com.asbestosstar.crashdetector.gui.tipos.arbol;

/**
 * Descriptor ligero de una ruta en el árbol de mods. No contiene Swing, solo
 * datos estructurales.
 */
public class PathDescriptor {
	private final String modUbicacion;
	private final String paquete; // "" para paquete por defecto
	private final String clase;
	private final String metodo; // null si no es método
	private final String descriptor; // null si no aplica
	private final String tipo; // "MOD", "PAQUETE", "CLASE", "METODO", "CAMPO", "CONSTANTE", "REFERENCIA"

	public PathDescriptor(String modUbicacion, String paquete, String clase, String metodo, String descriptor,
			String tipo) {
		this.modUbicacion = modUbicacion;
		this.paquete = paquete != null ? paquete : "";
		this.clase = clase;
		this.metodo = metodo;
		this.descriptor = descriptor;
		this.tipo = tipo;
	}

	// Getters
	public String obtenerModUbicacion() {
		return modUbicacion;
	}

	public String obtenerPaquete() {
		return paquete;
	}

	public String obtenerClase() {
		return clase;
	}

	public String obtenerMetodo() {
		return metodo;
	}

	public String obtenerDescriptor() {
		return descriptor;
	}

	public String obtenerTipo() {
		return tipo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PathDescriptor))
			return false;
		PathDescriptor that = (PathDescriptor) o;
		return java.util.Objects.equals(modUbicacion, that.modUbicacion)
				&& java.util.Objects.equals(paquete, that.paquete) && java.util.Objects.equals(clase, that.clase)
				&& java.util.Objects.equals(metodo, that.metodo)
				&& java.util.Objects.equals(descriptor, that.descriptor) && java.util.Objects.equals(tipo, that.tipo);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(modUbicacion, paquete, clase, metodo, descriptor, tipo);
	}
}