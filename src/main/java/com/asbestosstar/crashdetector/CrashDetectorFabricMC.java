package com.asbestosstar.crashdetector;

import java.io.File;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class CrashDetectorFabricMC implements PreLaunchEntrypoint{

	@Override
	public void onPreLaunch() {
		// TODO Auto-generated method stub
		if(!Statics.cargador)	{
			Statics.carpetas_de_mods.add(new File("mods/").toPath());
			Statics.cargador= true;
			MonitorDePID.main(new String[]{});
		}
	}

}
