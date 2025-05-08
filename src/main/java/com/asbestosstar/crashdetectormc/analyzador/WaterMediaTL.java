package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class WaterMediaTL implements Verificaciones {

	boolean activado=false;
	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		if(contento_de_consola.contains("TLauncher is NOT supported by WATERMeDIA, please stop using it")) {
			constructor.append(MonitorDePID.idioma.waterMediaTL()).append(nl_html);
			activado=true;
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new WaterMediaTL();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
