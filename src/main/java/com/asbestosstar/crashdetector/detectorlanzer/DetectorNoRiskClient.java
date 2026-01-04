package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorNoRiskClient implements DetectorLanzer {
    @Override public String id() { return "norisk_client"; }
    @Override public boolean animado() { return false; }
    @Override public boolean desanimado() { return true; }//PVP Cliente

    @Override
    public boolean detectar(App app, String cmd) {
        return app.equals(App.MINECRAFT) && cmd.contains("-Dminecraft.launcher.brand=noriskclient-launcher");
    }
}
