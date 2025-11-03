package com.asbestosstar.crashdetector.divisor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Un marcador para consolas que admiten SysOut pero que no tienen otro marcador
 * memorable para dividir. Asegúrese de imprimir la cadena HOLA_MUNDO al
 * principio del punto de entrada del cargador.
 */
public class HolaMundoConsolaDivisidor  implements DivisorDeArchivos {

	public static String HOLA_MUNDO = "HOLA MUNDO DESDE CRASHDETECTOR";
	
	/**
	 * cadenas que se deben evitar en los nombres de archivo
	 */
	public static List<String> lista_denegar = new ArrayList<>();//todo hablicar agregando strings en la config
	
	static {
		lista_denegar.add("cd_launcherlog");//diferente todos vezes
		lista_denegar.add("latest.log");//diferente todos vezes
		lista_denegar.add("debug.log");//diferente todos vezes
		lista_denegar.add("tlauncher");//tiene un otro marcador
		lista_denegar.add("hs_");//no divisdor
	}
	
	
	
	
	
	@Override
	public int obtenerLineaOriginal(String contentido_existente) {
		// TODO Auto-generated method stub
		
		String[] lineas = contentido_existente.split(Verificaciones.nl);
		int ultima = 0;
		for (int i = 0; i < lineas.length - 1; i++) {
			String lin = lineas[i];
			if (lin.contains(HOLA_MUNDO)) {
				ultima = i;
			}

		}

		return ultima;
	}

	@Override
	public boolean predicado(Path archivo, String contento_existe) {
		// TODO Auto-generated method stub
		String completa = archivo.toAbsolutePath().toString();
		for(String denegar:lista_denegar) {
			if(completa.toLowerCase().contains(denegar.toLowerCase())) {
				return false;
			}
		}
		
		
		if(contento_existe.contains(HOLA_MUNDO)) {
			return true;
		}
	
		
		return false;
	}

	
	
	
}
