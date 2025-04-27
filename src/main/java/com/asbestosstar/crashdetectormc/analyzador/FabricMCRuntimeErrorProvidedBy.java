package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class FabricMCRuntimeErrorProvidedBy implements Verificaciones {

	public boolean activado=false;

	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		
		
		for (String linea:contento_de_consola.split(nl)) {
			if(linea.contains("Could not execute entrypoint stage")) {
				constructor.append(MonitorDePID.idioma.modids_problematicos()+linea.split("provided by")[1]).append(nl_html);
				activado=true;
			}
		}
		
		
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new FabricMCRuntimeErrorProvidedBy();
	}

	
	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}
	
	
}
