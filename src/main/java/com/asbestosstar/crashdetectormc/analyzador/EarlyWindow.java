
package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class EarlyWindow  implements Verificaciones {

	@Override
	public void verificar(String str, StringBuilder messanje) {
		String[] lines = str.split(nl);
		if (lines.length > 0) {
			String ultima = lines[lines.length - 1];
			if (ultima.contains("Loading ImmediateWindowProvider fmlearlywindow")) {
				messanje.append(nl).append(
						MonitorDePID.idioma.fmlEarlyWindow()
				);
			}
		}
	}


	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new EarlyWindow();
	}

}