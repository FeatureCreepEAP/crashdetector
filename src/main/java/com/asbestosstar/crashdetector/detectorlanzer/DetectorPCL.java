package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorPCL implements DetectorLanzer {
	@Override
	public String id() {
		return "pcl";
	}

	@Override
	public boolean animado() {
		return false;
	}// No puedo usar por que es solo para windows. No Se si tiene problemas

	@Override
	public boolean desanimado() {
		return false;
	}

	@Override
	public boolean detectar(App app, String cmd) {
		return app.equals(App.MINECRAFT) && cmd.contains("-Dminecraft.launcher.brand=PCL");
	}
}