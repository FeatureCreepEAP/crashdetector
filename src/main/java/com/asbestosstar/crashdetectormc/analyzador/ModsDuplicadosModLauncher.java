package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class ModsDuplicadosModLauncher  implements Verificaciones {

	@Override
	public void verificar(String str, StringBuilder messanje) {
		if (str.contains("Found duplicate mods")) {

			StringBuilder out = new StringBuilder(MonitorDePID.idioma.no_tienes_las_dependencias_necesitas()+nl_html);

			for(String line: str.split(nl)) {
					if (line.contains("Mod ID") && line.contains("from mod files")) {
						out.append(MonitorDePID.idioma.modlauncher_mods_duplicado(line)).append(nl_html);
					}
			}
			messanje.append(nl_html).append(out);
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new ModsDuplicadosModLauncher();
	}
}