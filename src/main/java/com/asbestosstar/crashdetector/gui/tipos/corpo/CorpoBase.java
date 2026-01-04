package com.asbestosstar.crashdetector.gui.tipos.corpo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.PirataMC;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class CorpoBase extends JDialog implements CrashDetectorGUI,BotonDeBarraLateralDerecha {
    public static Map<String, Supplier<CorpoBase>> GUIS = new HashMap<String, Supplier<CorpoBase>>();
    
    public static void escribirIdiomaRespaldo(String codigo) {
        Idioma.idioma_respaldo.escribir(codigo);
    }
    
    public static String obtenerIdiomaRespaldo() {
        return Idioma.idioma_respaldo.obtener();
    }
    
    public static void escribirBuscardorHablicar(boolean valor) {
        Buscardor.hablicar.escribir(valor);
    }
    
    public static boolean obtenerBuscardorHablicar() {
        return Buscardor.hablicar.obtener();
    }
    
    public static void escribirNombreCD(String valor) {
        ConfigString.de("nombre_cd", "CrashDetector").escribir(valor);
    }
    
    public static String obtenerNombreCD() {
        return ConfigString.de("nombre_cd", "CrashDetector").obtener();
    }
    
    public static void escribirCondenarPirata(boolean valor) {
        PirataMC.config.escribir(valor);
    }
    
    public static boolean obtenerCondenarPirata() {
        return PirataMC.config.obtener();
    }
    
    @Override
    public TipoGUI tipo() {
        return TipoGUI.CORPO;
    }
    
    @Override
    public void recargarApariencia() {
        aplicarApariencia();
    }
    
    /**
     * Aplica la apariencia a todos los componentes de la GUI
     */
    public abstract void aplicarApariencia();
}