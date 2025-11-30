package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;

public class CrashDetectorModLauncher implements ITransformationService {

	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.MODLAUNCHER);
	}

	@Override
	public void initialize(IEnvironment arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "crashdetector";
	}

	@Override
	public void onLoad(IEnvironment arg0, Set<String> arg1) throws IncompatibleEnvironmentException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformers() {
		// TODO Auto-generated method stub
		ArrayList<ITransformer> transformers = new ArrayList<ITransformer>();
		transformers.add(new TransformacionesCPW());
		return transformers;
	}
//
//	@Override
//	public void beginScanning(IEnvironment environment) {
//		// TODO Auto-generated method stub
//		
//	}

}