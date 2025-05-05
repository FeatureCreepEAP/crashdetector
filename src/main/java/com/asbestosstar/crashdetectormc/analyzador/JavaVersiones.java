package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class JavaVersiones implements Verificaciones {

	public boolean activado=false;
	
	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub

		String out = MonitorDePID.idioma.versionDeJava();
		if (contento_de_consola.contains("java.lang.IllegalArgumentException: Unsupported class file major version")) {
			if (contento_de_consola.contains("--fml.forgeVersion, 4") || contento_de_consola.contains("--fml.forgeVersion, 3")) {
				if (contento_de_consola.contains("java version 2") && !contento_de_consola.contains("java version 20") && !contento_de_consola.contains("java version 21")) {
				out =  MonitorDePID.idioma.java22();;
				constructor.append(out).append(nl_html);
				activado=true;
				}
			}
		}else if(contento_de_consola.contains("has been compiled by a more recent version of the Java Runtime")){
			activado=true;
			constructor.append(MonitorDePID.idioma.javaObsoleta()).append(nl_html);
		}
		
		
	}




	
	
	
	
	
	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new JavaVersiones();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
