package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorLabyMod implements DetectorLanzer {
    @Override public String id() { return "labymod"; }
    @Override public boolean animado() { return false; }
    @Override public boolean desanimado() { return true; }//PVP Cliente

    @Override
    public boolean detectar(App app, String cmd) {
        if (!app.equals(App.MINECRAFT)) return false;
        String n = cmd.replace('\\', '/');
        return n.contains("/net/labymod/") ||
               cmd.contains("-Dnet.labymod.launcher=labymod-launcher");
    }
}