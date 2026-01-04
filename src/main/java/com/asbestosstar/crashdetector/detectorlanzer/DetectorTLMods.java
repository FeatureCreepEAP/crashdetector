package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

/**
 * La secion de TL de la boton TLMods
 */
public class DetectorTLMods extends DetectorTL {

    @Override
    public String id() {
        return "tlmods";
    }


    @Override
    public boolean detectar(App app, String cmd) {
        boolean esTL = super.detectar(app, cmd);
        if (!esTL) {
            return false;
        }

        // Segundo: ¿la carpeta actual contiene "versions"?
        String userDir = System.getProperty("user.dir", "").toLowerCase();
        return userDir.contains("versions");
    }
}