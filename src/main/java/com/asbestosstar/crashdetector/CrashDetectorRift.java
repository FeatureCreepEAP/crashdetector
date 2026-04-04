package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import org.dimdev.riftloader.listener.InitializationListener;

import net.minecraft.launchwrapper.Launch;

public class CrashDetectorRift extends LaunchWrapperTransformaciones implements InitializationListener {

	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.RIFT);
		Launch.classLoader.registerTransformer(CrashDetectorRift.class.getCanonicalName());
		Transformaciones.init();
	}

	static Transformaciones trans = new Transformaciones();

	@Override
	public void onInitialization() {
		// TODO Auto-generated method stub

	}

}
