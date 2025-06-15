package com.asbestosstar.crashdetector.parches;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Parch<T> {

	public static Set<Parch<?>> parches = new HashSet<Parch<?>>();
	
	public List<String> clases();
	public boolean activar();
	public T parch(T contento,String nombre);
	public Class<T> tipo();
	
	/**
	 * Aplica todos los parches compatibles del tipo T que:
	 * 1. Coinciden con el tipo de contenido (T)
	 * 2. Tienen activar() == true
	 * 3. Su lista de clases() contiene el nombre proporcionado
	 */
	public static <T> T applicarParches(T contento, String nombre) {
	    if (contento == null || nombre == null) return contento;

	    
	    
	    for (Parch<?> parche : parches) {
	        // 1. Verificar que el parche sea del mismo tipo que contento
	        if (!parche.tipo().isAssignableFrom(contento.getClass())) continue;

	        @SuppressWarnings("unchecked")
	        Parch<T> parcheTyped = (Parch<T>) parche;

	        // 2. Verificar condiciones adicionales
	        if (!parcheTyped.activar()) continue;
	        if (!parcheTyped.clases().contains(nombre)) continue;

	        // 3. Aplicar el parche
	        contento = parcheTyped.parch(contento, nombre);
	    }

	    return contento;
	}
	
}
