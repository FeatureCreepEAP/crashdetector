package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class FaltasClases implements Verificaciones {

	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
	
		for (String linea:contento_de_consola.split(nl)) {
			if(linea.contains("Caused by: java.lang.ClassNotFoundException")) {
				constructor.append(MonitorDePID.idioma.faltar_de_clases_fatales()+linea.split(":")[2]+nl_html);
			}else if (linea.contains("Error loading class:")) {
				constructor.append(MonitorDePID.idioma.faltar_de_clases_fatales()+linea.split("Error loading class: ")[1].split(" ")[0]+nl_html);
			}
		}
		
		
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new FaltasClases();
	}

}
