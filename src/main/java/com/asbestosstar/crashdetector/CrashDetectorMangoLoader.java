package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.ByteBuffer;

import org.mangorage.loader.api.IClassTransformer;
import org.mangorage.loader.api.TransformResult;

public class CrashDetectorMangoLoader implements IClassTransformer{
	static {
		if(!Statics.cargador)	{
			Statics.cargador= true;
			Statics.carpetas_de_mods.add(new File("mods/").toPath());
			MonitorDePID.main(new String[]{});
			Transformaciones.init();
		}
		}

	static Transformaciones trans = new Transformaciones();
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "crashdetector";
	}

	@Override
	public TransformResult transform(String arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		
		return null;
		//return trans.transform(this.getClass().getClassLoader(), arg0, new ByteBuffer(arg1), null, arg1);
	}
	
	
	
	

}
