package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class lithostictchctov implements Verificaciones{

	public boolean activado=false;

	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		
		
		if(contento_de_consola.contains("Caused by: java.lang.RuntimeException: Unknown registry key in ResourceKey[minecraft:root / minecraft:worldgen/structure_type]: lithostitched:jigsaw")&&contento_de_consola.contains("ctov")) {
			constructor.append(MonitorDePID.idioma.lithostichctov()).append(nl_html);
			activado=true;
		}
		
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new lithostictchctov();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}
	
	
	
}
