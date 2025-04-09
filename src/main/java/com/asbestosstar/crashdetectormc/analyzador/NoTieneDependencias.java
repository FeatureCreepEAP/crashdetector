package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class NoTieneDependencias  implements Verificaciones {

	@Override
	public void verificar(String str, StringBuilder messanje) {
		if (str.contains("Missing or unsupported mandatory dependencies:")) {

			StringBuilder out = new StringBuilder(MonitorDePID.idioma.no_tienes_las_dependencias_necesitas()+nl);

			for(String line: str.split(nl)) {
					if (line.contains("Mod ID") && line.contains("Requested by") && line.contains("Expected range")) {
						out.append(MonitorDePID.idioma.linea_de_dependencia(line)).append("\n");
					}
			}
			messanje.append(nl).append(out);
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new NoTieneDependencias();
	}
}