package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorPolyMC implements DetectorLanzer {
    @Override public String id() { return "polymc"; }
    @Override public boolean animado() { return false; }
    @Override public boolean desanimado() { return false; }

    @Override
    public boolean detectar(App app, String cmd) {
        if (!app.equals(App.MINECRAFT)) return false;
        try {
            Class.forName("org.polymc.impl.OneSixLauncher", false, this.getClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}