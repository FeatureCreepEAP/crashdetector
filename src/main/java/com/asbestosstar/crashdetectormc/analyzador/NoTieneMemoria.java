package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class NoTieneMemoria implements Verificaciones {

	@Override
	public void verificar(String contento_de_consola, StringBuilder constructor) {
		// TODO Auto-generated method stub

		if(contento_de_consola.contains("Server crashed because it ran out of memory. Try reducing the load to avoid crashes like this in the future")||
				contento_de_consola.contains("There is insufficient memory for the Java Runtime Environment to continue")				
				) {
			constructor.append(MonitorDePID.idioma.noTieneMemoria());
		}
			
		
		
		
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new NoTieneMemoria();
	}

}
