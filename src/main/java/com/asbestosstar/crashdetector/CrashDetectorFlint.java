package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import net.flintloader.loader.api.FlintModule;

public class CrashDetectorFlint implements FlintModule {

	static {

		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.FLINTLOADER);

		Transformaciones.init();

	}

	@Override
	public void earlyInitialization() {

	}

	@Override
	public void initializeModule() {
	}
}