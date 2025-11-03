package com.asbestosstar.crashdetector;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.asbestosstar.crashdetector.divisor.HolaMundoConsolaDivisidor;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class CrashDetectorFabricMC implements PreLaunchEntrypoint {

	@Override
	public void onPreLaunch() {
		// TODO Auto-generated method stub
		LogManager.getLogger(HolaMundoConsolaDivisidor.class).log(Level.ERROR, HolaMundoConsolaDivisidor.HOLA_MUNDO);
		if (!Statics.cargador) {
			Statics.carpetas_de_mods.add(new File("mods/").toPath());
			Statics.cargador = true;
			MonitorDePID.main(new String[] {});
		}
	}

}
