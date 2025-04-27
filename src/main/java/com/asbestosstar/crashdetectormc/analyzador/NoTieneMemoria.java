package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class NoTieneMemoria implements Verificaciones {

	public boolean activado=false;

	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub

		if(contento_de_consola.contains("Server crashed because it ran out of memory. Try reducing the load to avoid crashes like this in the future")||
				contento_de_consola.contains("There is insufficient memory for the Java Runtime Environment to continue")				
				) {
			constructor.append(MonitorDePID.idioma.noTieneMemoria()).append(nl_html);
			activado=true;
		}
			
		
		
		
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new NoTieneMemoria();
	}
	
	
	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
