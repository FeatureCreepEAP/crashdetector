package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.cargador.Cargador;

import java.util.ArrayList;
import java.util.List;

/**
 * Verifica que todos los mods presentes en la carpeta 'mods' sean compatibles
 * con al menos uno de los cargadores activos (Forge, Fabric, NeoForge, etc.).
 * 
 * Un mod que no pertenece a ningún cargador activo probablemente está mal colocado,
 * es incompatible o pertenece a una plataforma diferente (ej: mod de Fabric en Forge).
 * https://discord.com/channels/1129059589325852724/1129069799545241703/1418708211636113498
 * ¡Verificación Numero 100!
 */
public class ModIncompatibleConCargadorActivo implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private final List<String> modsIncompatibles = new ArrayList<>();

    @Override
    public void verificar(Consola consola) {
        // Asegurarse de que los mods y cargadores estén cargados
        
    	if(Cargador.cargadores_activados == null || Cargador.cargadores_activados.isEmpty()) {
    		return;
    	}
    	
    	
    	Buscardor.cargar();
        
        modsIncompatibles.clear();

        // Recorrer todos los mods detectados
        for (ArchivoDeMod mod : Buscardor.mods) {
            boolean compatible = false;
            
            // Verificar contra cada cargador activo
            for (Cargador cargador : Cargador.cargadores_activados) {
                if (cargador.modEsDeCargador(mod)) {
                    compatible = true;
                    break;
                }
            }

            // Si no es compatible con ninguno, es sospechoso
            if (!compatible) {

                String     nombreMod = mod.ubicacion_para_publicar();
                
                modsIncompatibles.add(nombreMod);
            }
        }

        if (!modsIncompatibles.isEmpty()) {
            this.mensaje = MonitorDePID.idioma.modIncompatibleConCargadorActivo(modsIncompatibles);
            this.activado = true;
        }
    }

    @Override
    public void verificar(Consola consola, String linea, int numero_de_linea) {
        // No se usa análisis por línea; todo se hace en verificar(Consola)
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return false; // No requiere stack trace
    }

    @Override
    public Verificaciones nueva() {
        return new ModIncompatibleConCargadorActivo();
    }

    @Override
    public boolean activado() {
        return this.activado;
    }

    @Override
    public String mensaje() {
        return this.mensaje;
    }

    @Override
    public float prioridad() {
        return 780.0f; // Media-alta: puede causar fallos silenciosos o errores posteriores
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionModIncompatibleConCargadorActivo())
            .construir();
    }

    @Override
    public String id() {
        return "mod_incompatible_con_cargador_activo";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreModIncompatibleConCargadorActivo();
    }
}