package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class CrashDetectorMCForge172 implements IFMLLoadingPlugin {

	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath() },
				CargadoresComun.CDOrigin.MCFORGE_PREMODLAUNCHER);
		Transformaciones.init();
	}

	public String[] getASMTransformerClass() {
		return new String[] { LaunchWrapperTransformaciones.class.getName() };
	}

	@Override
	public String getModContainerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getLibraryRequestClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
