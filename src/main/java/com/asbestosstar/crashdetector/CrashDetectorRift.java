package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import org.dimdev.riftloader.listener.InitializationListener;

public class CrashDetectorRift implements InitializationListener{

	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.RIFT);
		//Launch.classLoader.registerTransformer("featurecreep.unsupported.FCLaunchWrapperTransformer");
		Transformaciones.init();
	}

	static Transformaciones trans = new Transformaciones();

	@Override
	public void onInitialization() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
