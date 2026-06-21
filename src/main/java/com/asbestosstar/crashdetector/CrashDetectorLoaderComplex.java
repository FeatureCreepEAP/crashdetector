package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;

import com.enderzombi102.loadercomplex.api.Addon;
import com.enderzombi102.loadercomplex.api.Loader;

public class CrashDetectorLoaderComplex implements Addon {

	static {
		CargadoresComun.init(new Path[] { new File("mods/").toPath(), new File("addons/").toPath() },
				CargadoresComun.CDOrigin.LOADERCOMPLEX);

		Transformaciones.init();
	}

	@Override
	public void init(Loader loader) {

	}

	@Override
	public String getAuthors() {
		return "asbestosstar";
	}

	@Override
	public String getName() {
		return "crashdetector";
	}

	@Override
	public String getDescription() {
		return Statics.nombre_cd.obtener();
	}
}