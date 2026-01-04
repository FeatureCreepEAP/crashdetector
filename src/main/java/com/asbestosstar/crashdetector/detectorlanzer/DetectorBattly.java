package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorBattly implements DetectorLanzer {

    @Override
    public String id() {
        return "battly"; // ID técnico en snake_case, sin traducir
    }

    @Override
    public boolean animado() {
        return false; // asumido como desaconsejado
    }

    @Override
    public boolean desanimado() {
        return true;//Tiene muchas problemas
    }

    @Override
    public boolean detectar(App app, String cmd) {
        if (!app.equals(App.MINECRAFT)) {
            return false;
        }

        // Normalizar separadores para compatibilidad entre sistemas
        String comandoNormalizado = cmd.replace('\\', '/');

        // Buscar la cadena distintiva en el comando (generalmente en classpath o assets)
        return comandoNormalizado.contains("battly/assets");
    }
}