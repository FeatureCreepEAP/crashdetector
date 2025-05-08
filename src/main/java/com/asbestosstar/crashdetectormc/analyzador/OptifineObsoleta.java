package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class OptifineObsoleta implements Verificaciones {

	boolean activado=false;

	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		if (contento_de_consola.contains("Error loading OptiFine ZIP file")
				&& contento_de_consola.contains("URI is not hierarchical")
				&& contento_de_consola.contains("cpw.mods.modlauncher.api.IncompatibleEnvironmentException")
				&& contento_de_consola.contains("Service failed to load OptiFine")

		) {
			constructor.append(MonitorDePID.idioma.optifineObsoleta()).append(nl_html);
			activado=true;
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new OptifineObsoleta();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
