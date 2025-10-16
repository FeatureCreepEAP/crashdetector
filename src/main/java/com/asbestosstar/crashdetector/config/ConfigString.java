package com.asbestosstar.crashdetector.config;

import com.asbestosstar.crashdetector.Config;

public class ConfigString implements ElementoConfig<String> {

    private String clave;

    private ConfigString(String clave) {
        this.clave = clave;
    }

    @Override
    public String clave() {
        return clave;
    }

    @Override
    public String obtener() {
        return Config.obtenerInstancia().propiedadesConfig.getProperty(clave);
    }

    @Override
    public void escribir(String valor) {
        Config.obtenerInstancia().propiedadesConfig.setProperty(clave, valor);
        Config.obtenerInstancia().guardar();
    }

 // Método auxiliar para crear una instancia solo si la clave existe
    public static ConfigString de(String clave) throws IllegalArgumentException{
        if (!Config.obtenerInstancia().propiedadesConfig.containsKey(clave)) {
            throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
        }
        return new ConfigString(clave);
    }

}
