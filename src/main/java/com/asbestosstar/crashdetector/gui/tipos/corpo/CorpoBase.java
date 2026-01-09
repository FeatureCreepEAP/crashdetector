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
import com.asbestosstar.crashdetector.gui.tipos.deshablicarverificaciones.DeshabilitarVerificacionesAmaneKanata;
import com.asbestosstar.crashdetector.gui.tipos.editor.EditorCodiceGUIIronMouse;
import com.asbestosstar.crashdetector.gui.tipos.lanzeresbuenos.LanzerBuenoGUIMaidMint;
import com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos.LanzerMaloGUISylentBell;

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
    
    public static void abrirEditorCodice() {
    	TipoGUI.EDITOR_FIRMAS.obtenerGUIPredeterminado(EditorCodiceGUIIronMouse.ID,
    			() -> new EditorCodiceGUIIronMouse()).init();
    }
    public static void abrirVerificaciones() {
    	TipoGUI.DESHABLICAR_VERIFICACIONES.obtenerGUIPredeterminado(DeshabilitarVerificacionesAmaneKanata.ID,
    			() -> new DeshabilitarVerificacionesAmaneKanata()).init();
    }
    public static void abrirLanzeresMalos() {
    	TipoGUI.LANZER_MALO.obtenerGUIPredeterminado(LanzerMaloGUISylentBell.ID,
    			() -> new LanzerMaloGUISylentBell()).init();
    }
    public static void abrirLanzeresBuenos() {
    	TipoGUI.LANZER_BUENO.obtenerGUIPredeterminado(LanzerBuenoGUIMaidMint.ID,
    			() -> new LanzerBuenoGUIMaidMint()).init();
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