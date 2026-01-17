package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class LanzerOtra implements DetectorLanzer {

	public static String ID = "otra";

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public boolean animado() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean desanimado() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean detectar(App app, String cmd) {
		// TODO Auto-generated method stub
		return false;
	}

}
