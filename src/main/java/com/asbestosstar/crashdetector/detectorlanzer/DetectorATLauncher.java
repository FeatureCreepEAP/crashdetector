package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorATLauncher implements DetectorLanzer {
    @Override public String id() { return "at_launcher"; }
    @Override public boolean animado() { return true; }
    @Override public boolean desanimado() { return false; }

    @Override
    public boolean detectar(App app, String cmd) {
        return app.equals(App.MINECRAFT) && cmd.contains("-Dminecraft.launcher.brand=ATLauncher");
    }
}