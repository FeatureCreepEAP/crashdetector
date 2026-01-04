package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorCurseForgeApp implements DetectorLanzer {
    @Override public String id() { return "cursedforge_app"; }
    @Override public boolean animado() { return false; }
    @Override public boolean desanimado() { return true; }//Tiene muchas problemas instalando mods y con instalcaciones del juego corruptos. Pero con la opcion sin Mojang Lanzer hay mas problemas Y no hay una consola con SysOut o SysErr https://curseforge-ideas.overwolf.com/ideas/CF-I-7632

    @Override
    public boolean detectar(App app, String cmd) {
        return app.equals(App.MINECRAFT) && cmd.contains("-Dminecraft.launcher.brand=CurseForge");
    }
}