package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class ModsDuplicadosModLauncher  implements Verificaciones {

	public boolean activado=false;

	
	@Override
	public void verificar(String str, CDStringBuilder messanje) {
		if (str.contains("Found duplicate mods")) {

			StringBuilder out = new StringBuilder(MonitorDePID.idioma.no_tienes_las_dependencias_necesitas()).append(nl_html);

			for(String line: str.split(nl)) {
					if (line.contains("Mod ID") && line.contains("from mod files")) {
						out.append(MonitorDePID.idioma.modlauncher_mods_duplicado(line)).append(nl_html);
					}
			}
			messanje.append(nl_html).append(out.toString()).append(nl_html);
			activado=true;
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new ModsDuplicadosModLauncher();
	}
	
	
	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}
}