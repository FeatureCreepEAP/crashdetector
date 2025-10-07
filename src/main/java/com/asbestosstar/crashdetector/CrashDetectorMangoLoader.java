package com.asbestosstar.crashdetector;

import java.io.File;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.ByteBuffer;

import org.mangorage.loader.api.IClassTransformer;
import org.mangorage.loader.api.TransformResult;
import org.mangorage.loader.api.TransformerFlag;

public class CrashDetectorMangoLoader implements IClassTransformer {
	static {
		if (!Statics.cargador) {
			Statics.cargador = true;
			Statics.carpetas_de_mods.add(new File("mods/").toPath());
			MonitorDePID.main(new String[] {});
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

		// return null;
		try {
			return new TransformResult(trans.transform(this.getClass().getClassLoader(), arg0, null,
					this.getClass().getProtectionDomain(), arg1), TransformerFlag.FULL_REWRITE);
		} catch (IllegalClassFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TransformResult.none(arg1);

	}

}
