package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorHeliosLauncher implements DetectorLanzer {
    @Override public String id() { return "helios_launcher"; }
    @Override public boolean animado() { return false; }
    @Override public boolean desanimado() { return false; }

    @Override
    public boolean detectar(App app, String cmd) {
        return app.equals(App.MINECRAFT) && cmd.contains("-Dminecraft.launcher.brand=Helios-Launcher");
    }
}