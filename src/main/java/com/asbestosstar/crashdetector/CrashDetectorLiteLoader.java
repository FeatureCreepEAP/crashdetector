package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

public class CrashDetectorLiteLoader extends LaunchWrapperTransformaciones {
//No Necesitemos usar las clases y deps liteloader :)
	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.RIFT);
		Transformaciones.init();
	}

	static Transformaciones trans = new Transformaciones();

}
