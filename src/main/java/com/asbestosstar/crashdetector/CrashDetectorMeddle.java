package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import net.fybertech.meddle.MeddleMod;
import net.minecraft.launchwrapper.IClassTransformer;

@MeddleMod(id = "crashdetector", name = "CrashDetector", author = "Asbestosstar", version = "0", depends = {})
public class CrashDetectorMeddle extends LaunchWrapperTransformaciones implements IClassTransformer {
	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.MEDDLE);
		Transformaciones.init();
	}

	static Transformaciones trans = new Transformaciones();

}