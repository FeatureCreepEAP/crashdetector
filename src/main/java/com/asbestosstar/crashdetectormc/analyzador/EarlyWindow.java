
package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class EarlyWindow  implements Verificaciones {

	public boolean activado=false;

	
	@Override
	public void verificar(String str, CDStringBuilder messanje) {
		String[] lines = str.split(nl);
		if (lines.length > 0) {
			String ultima = lines[lines.length - 1];
			if (ultima.contains("Loading ImmediateWindowProvider fmlearlywindow")) {
				messanje.append(nl_html).append(
						MonitorDePID.idioma.fmlEarlyWindow()
				);
				activado=true;
			}
		}
	}


	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new EarlyWindow();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}
	
}