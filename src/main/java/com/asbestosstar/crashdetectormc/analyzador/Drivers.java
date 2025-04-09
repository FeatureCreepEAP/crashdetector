
package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class Drivers implements Verificaciones {

	@Override
	public void verificar(String str, StringBuilder messanje) {
		if (str.contains("EXCEPTION_ACCESS_VIOLATION") && str.contains("atio6axx.dll")) {
			messanje.append(nl).append(
					MonitorDePID.idioma.probelma_con_graficas_ati()
			);
		} else if (str.contains("EXCEPTION_ACCESS_VIOLATION") && str.contains("nouveau")) {
			messanje.append(nl).append(
					MonitorDePID.idioma.probelma_con_graficas_nouveau()
					);
		} else {
			String ultima = null;

			for (String linea : str.split(nl)) {
				ultima = linea;
			}

			if (ultima != null) {
				if (ultima.contains("Backend library: LWJGL") || ultima.contains("Trying GL version") || ultima.contains("you probably have a driver issue")) {
					messanje.append("\n").append(
							MonitorDePID.idioma.probelma_con_graficas_general()
					);
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new Drivers();
	}

}