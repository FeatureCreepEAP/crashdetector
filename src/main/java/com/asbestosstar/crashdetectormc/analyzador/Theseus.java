package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class Theseus implements Verificaciones {

	@Override
	public void verificar(String contento_de_consola, StringBuilder constructor) {
		// TODO Auto-generated method stub
		
		if(contento_de_consola.contains("com.modrinth.theseus")	|| contento_de_consola.contains("ModrinthApp")) {
			constructor.append(MonitorDePID.idioma.theseus());
		}	
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new Theseus();
	}

}
