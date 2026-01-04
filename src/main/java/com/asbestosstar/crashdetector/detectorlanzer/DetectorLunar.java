package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorLunar implements DetectorLanzer {
    @Override public String id() { return "lunar"; }
    @Override public boolean animado() { return false; }
    @Override public boolean desanimado() { return true; }//PVP Cliente con muchas problemas

    @Override
    public boolean detectar(App app, String cmd) {
        if (!app.equals(App.MINECRAFT)) return false;

        // Verificar args
        if (cmd.contains("-Dlunar") || cmd.contains("-Dichor")) {
            return true;
        }

        // Verificar clase sin inicializar
        try {
            Class.forName("com.moonsworth.lunar.genesis.Genesis", false, this.getClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }
}
