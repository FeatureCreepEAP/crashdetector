package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class FaltaModuleJPMS implements Verificaciones {

	public boolean activado=false;
	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		
		for(String linea:contento_de_consola.split(nl)) {
			if(linea.contains("java.lang.module.FindException: Module ") && linea.contains("not found") ) {
				//Exception in thread "main" java.lang.module.FindException: Module com.fasterxml.jackson.core not found, required by com.fasterxml.jackson.module.paramnames
				activado=true;
				String mod_necesitas = linea.split("java.lang.module.FindException: Module ")[1].split(" not found, required by ")[0];
				String submod = linea.split(" not found, required by ")[1];
				constructor.append(MonitorDePID.idioma.jpms_modules_faltas(mod_necesitas,submod)).append(nl_html);
			}
			
			
			
		}
		
		

		
		
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new FaltaModuleJPMS();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
