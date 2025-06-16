package com.asbestosstar.crashdetector.parches;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

public interface Parche<T> {
	
	public static Set<Parche<?>> parches = new HashSet<Parche<?>>();

	public Set<String> clases();

	public default boolean activar() {
		return ConfigDeParches.obtenerInstancia().estaActivo(this.id());
	}
	public default boolean predeterminado() {
		return false;
	}

	public T parch(T contento, String nombre);

	public Class<T> tipo();
	
	public Parche<T> nuevo();
	
	public String id();
	public String nombre_de_gui();

	/**
	 * Aplica todos los parches compatibles del tipo T que: 1. Coinciden con el tipo
	 * de contenido (T) 2. Tienen activar() == true 3. Su lista de clases() contiene
	 * el nombre proporcionado
	 */
	public static <T> T applicarParches(T contento, String nombre) {
	    if (contento == null || nombre == null) return contento;
	    String nombre_codigo=nombre.replace("/", ".");
	    T resultado = contento;

	    for (Parche<?> parche : parches) {
	        if (!parche.tipo().isAssignableFrom(resultado.getClass())) continue;

	        @SuppressWarnings("unchecked")
	        Parche<T> tipo = (Parche<T>) parche;

	        if (!tipo.activar()) continue;
	        if (!tipo.clases().contains(nombre_codigo)) continue;

	        resultado = tipo.nuevo().parch(resultado, nombre_codigo);
	    }

	    return resultado;
	}

	/**
	 * ClassNode es diferente porque en fabric-loader la transformacion es un void
	 */
	public static void applicarParches(ClassNode contento, String nombre) {
		if (contento == null || nombre == null)
			return;
		String nombre_codigo=nombre.replace("/", ".");
		for (Parche<?> parche : parches) {
			if (parche instanceof ParcheClassNode) {
				if (parche.activar() && parche.clases().contains(nombre_codigo)) {
					ParcheClassNode node = (ParcheClassNode) parche;
					node.nuevo().parch(contento, nombre_codigo);
				}
			}

		}

	}

}
