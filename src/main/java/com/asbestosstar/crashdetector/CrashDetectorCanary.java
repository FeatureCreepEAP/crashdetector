package com.asbestosstar.crashdetector;

import net.canarymod.plugin.Plugin;

public class CrashDetectorCanary extends Plugin {

	static {
		try {
			com.asbestosstar.crashdetector.CargadoresComun.init(
					new java.nio.file.Path[] { new java.io.File("plugins/").toPath() },
					com.asbestosstar.crashdetector.CargadoresComun.CDOrigin.CANARY);
			com.asbestosstar.crashdetector.Transformaciones.init();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean enable() {
		// TODO Auto-generated method stub
		return true;
	}

}
