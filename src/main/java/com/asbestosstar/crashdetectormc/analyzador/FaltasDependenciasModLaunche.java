package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class FaltasDependenciasModLaunche  implements Verificaciones {

	@Override
	public void verificar(String str, StringBuilder messanje) {
		if (str.contains("Missing or unsupported mandatory dependencies:")) {

			StringBuilder out = new StringBuilder(MonitorDePID.idioma.no_tienes_las_dependencias_necesitas()+nl_html);

			for(String line: str.split(nl)) {
					if (line.contains("Mod ID") && line.contains("Requested by") && line.contains("Expected range")) {
						out.append(MonitorDePID.idioma.linea_de_dependencia(line)).append(nl_html);
					}
			}
			messanje.append(nl_html).append(out);
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new FaltasDependenciasModLaunche();
	}
}