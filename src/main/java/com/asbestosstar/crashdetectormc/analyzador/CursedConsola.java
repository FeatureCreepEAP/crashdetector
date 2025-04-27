package com.asbestosstar.crashdetectormc.analyzador;

import java.io.File;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.Consola;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class CursedConsola implements Verificaciones {

	public boolean activado=false;
	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		String ubicacion = new File(".").getAbsolutePath();
		if(ubicacion.toLowerCase().contains("curseforge") && ubicacion.toLowerCase().contains("instances")) {
			
			boolean tiene_consola_de_launcher = false;
			
			for(Consola consola:MonitorDePID.consolas) {
				if(consola.archivo.toString().contains("launcher_log")) {//TODO para debug.log
					tiene_consola_de_launcher=true;
				}
			}
			
			if(!tiene_consola_de_launcher) {
				constructor.append(MonitorDePID.idioma.noTieneConsolaDeLauncherCursedForge()).append(nl_html);
				activado=true;
			}
			
			
		}
		
		
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new CursedConsola();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
