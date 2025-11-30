package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class CrashDetectorFabricMC implements PreLaunchEntrypoint {

	@Override
	public void onPreLaunch() {
		// TODO Auto-generated method stub
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.FABRICMC);
	}

}
