package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ServicioDeModLauncherNoFunciona implements Verificaciones {

	boolean activado = false;
	
	
	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub

		for(String linea:contento_de_consola.split(nl)) {
			String carga="Service failed to load";
			if(linea.contains("[cpw.mods.modlauncher.TransformationServiceDecorator/MODLAUNCHER]")&&linea.contains(carga)) {
				String servicio = linea.split(carga)[1].trim();
				constructor.append(MonitorDePID.idioma.servicioMLNoPudoCargar(servicio)).append(nl_html);
				
			}
			
			
			
		}
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new ServicioDeModLauncherNoFunciona();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
