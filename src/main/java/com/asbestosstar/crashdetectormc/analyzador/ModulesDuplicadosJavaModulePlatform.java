package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class ModulesDuplicadosJavaModulePlatform implements Verificaciones {

	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub

		for (String linea : contento_de_consola.split(nl)) {
			if (linea.contains("java.lang.module.ResolutionException")) {
				constructor.append(MonitorDePID.idioma.module_resolution_exception(
						"<b>"+linea.split(":")[1].split(" exports ")[0].replace("and", "+")+"</b>",
						"<b>"+linea.split("exports package ")[1].split("to module")[0]+"</b>") + nl_html);
			}
		}

	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new ModulesDuplicadosJavaModulePlatform();
	}

}
