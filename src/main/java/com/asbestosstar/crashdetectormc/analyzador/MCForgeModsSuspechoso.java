package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class MCForgeModsSuspechoso implements Verificaciones {

	@Override
	public void verificar(String contento_de_consola, StringBuilder constructor) {
		// TODO Auto-generated method stub
		String[] lineas = contento_de_consola.split(nl);
		int len = lineas.length;
		for (int i = 0; i < len - 1; i++) {
		    if (lineas[i].contains("Suspected Mod:")) {
		        constructor.append(MonitorDePID.idioma.mcforge_mod_suspechoso() + " " + lineas[i + 1]);
		    }
		}
	
	
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new MCForgeModsSuspechoso();
	}

}
