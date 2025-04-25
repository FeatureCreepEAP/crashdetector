package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class NecesitasSodium implements Verificaciones{

	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		if(contento_de_consola.contains("Error loading class: net/caffeinemc/mods/sodium/api/memory/MemoryIntrinsics")) {
			constructor.append(MonitorDePID.idioma.necesitasSodiumParaIris()+nl_html);
		}	

	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new NecesitasSodium();
	}

	
	
	
	
	
	
	
	
}
