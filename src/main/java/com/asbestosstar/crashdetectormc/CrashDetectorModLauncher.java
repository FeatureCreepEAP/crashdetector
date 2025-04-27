package com.asbestosstar.crashdetectormc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;


public class CrashDetectorModLauncher implements ITransformationService  {

static {
if(!Statics.cargador)	{
	Statics.cargador= true;
	Statics.carpetas_de_mods.add(new File("mods/").toPath());
	MonitorDePID.main(new String[]{});
}
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

	
	@Override
	public List<Resource> completeScan(IModuleLayerManager layerManager) {
		List<Resource> list = new ArrayList<Resource>();
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ITransformer> transformers() {
		// TODO Auto-generated method stub
		ArrayList<ITransformer> transformers = new ArrayList<ITransformer>();
		transformers.add(new TransformacionesCPW());
		return transformers;
	}

}