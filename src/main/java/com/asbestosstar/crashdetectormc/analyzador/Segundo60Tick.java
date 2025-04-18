package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetectormc.MonitorDePID;

public class Segundo60Tick implements Verificaciones{

	@Override
	public void verificar(String contento_de_consola, StringBuilder constructor) {
		// TODO Auto-generated method stub
		if(contento_de_consola.contains("[Server Watchdog/FATAL] [net.minecraft.server.dedicated.ServerHangWatchdog]: A single server tick took 60.00 seconds (should be max 0.05)")) {
			constructor.append(MonitorDePID.idioma.segundo60Tick());
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new Segundo60Tick();
	}

}
