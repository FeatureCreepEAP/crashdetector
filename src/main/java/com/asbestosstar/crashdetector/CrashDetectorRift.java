package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

public class CrashDetectorRift {

	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.RIFT);
		//Launch.classLoader.registerTransformer("featurecreep.unsupported.FCLaunchWrapperTransformer");
		Transformaciones.init();
	}

	static Transformaciones trans = new Transformaciones();
	
	
	
	
}
