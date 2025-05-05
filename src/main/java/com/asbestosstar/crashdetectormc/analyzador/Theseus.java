package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class Theseus implements Verificaciones {

	public boolean activado=false;

	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		
		if(contento_de_consola.contains("com.modrinth.theseus")	|| contento_de_consola.contains("ModrinthApp")) {
			constructor.append(MonitorDePID.idioma.theseus()).append(nl_html);
			activado=true;
		}	
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new Theseus();
	}

	
	
	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}
	
	
}
