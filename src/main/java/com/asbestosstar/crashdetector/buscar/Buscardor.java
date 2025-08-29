package com.asbestosstar.crashdetector.buscar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;

public class Buscardor {

    public static Set<ArchivoDeMod> mods = new HashSet<ArchivoDeMod>();

    public static boolean cargado = false;
    
    /**
     * Carga los mods desde el archivo de monitoreo.
     */
    public static void cargar() {
        if (!cargado) {
            try {
                String[] rutasMods = MonitorDePID.leer_archivo(MonitorDePID.ultimo_mods).split(MonitorDePID.nl);
                for (String mod : rutasMods) {
                    File archivo = new File(mod);
                    if (archivo.isFile()) {
                        try (FileInputStream fis = new FileInputStream(archivo)) {
                            Buscardor.mods.add(new ModPKZip(mod, ArchivoDeMod.origin, fis));
                        } catch (IOException e) {
                            CrashDetectorLogger.logException(e);
                        }
                    }
                }
                cargado = true;
            } catch (IOException e) {
                CrashDetectorLogger.logException(e);
            }
        }
    }
    
    /**
     * Prepara una ruta para publicación, anonimizando si es necesario.
     * @param ruta Ruta a preparar
     * @return Ruta anonimizada o original según configuración
     */
    public static String rutaParaPublicar(String ruta) {
        boolean anonimo = Config.obtenerInstancia().esAnonimizarRegistros();
        if (anonimo) {
            return AnonimizadorDeRuta.anonimizarNombreDeUsuario(ruta.toString());
        }
        return ruta.toString();
    }
    
    /**
     * Obtiene mods que contienen un archivo con el nombre especificado.
     * @param nombre Nombre del archivo a buscar
     * @return Lista de rutas de mods que contienen el archivo
     */
    public static List<String> obtenerModsConNombre(String nombre){
        List<String> modsConNombre = new ArrayList<String>();
        for(ArchivoDeMod mod : mods) {
            if(mod.tieneNombreRecursivo(nombre)) {
                modsConNombre.add(rutaParaPublicar(mod.obtenerNombreRecursivo(nombre)));
            }
        }
        return modsConNombre;
    }

    /**
     * Busca todos los mods que contienen un archivo, clase o paquete específico.
     * @param termino Término a buscar (archivo, clase o paquete)
     * @return Lista de mods que contienen el término
     */
    public static List<ArchivoDeMod> buscarModsConTermino(String termino) {
        List<ArchivoDeMod> resultados = new ArrayList<>();
        for (ArchivoDeMod mod : mods) {
            resultados.addAll(mod.buscarModsCon(termino));
        }
        return resultados;
    }
    
    /**
     * Convierte una lista de mods a sus ubicaciones para publicación.
     * @param mods Lista de mods
     * @return Lista de ubicaciones anonimizadas
     */
    public static List<String> obtenerUbicaciones(List<ArchivoDeMod> mods){
        List<String> ret = new ArrayList<String>();
        for(ArchivoDeMod mod : mods) {
            ret.add(mod.ubicacion_para_publicar());
        }
        return ret;
    }
    
    // MÉTODOS DE ANÁLISIS DE BYTECODE (recorren todos los mods)
    
    /**
     * Verifica si alguna mod contiene la clase especificada.
     * @param nombreClase Nombre completo de la clase (ej: "java/lang/Object")
     * @return true si al menos un mod contiene la clase, false en caso contrario
     */
    public static boolean existeClaseEnAlgunMod(String nombreClase) {
        for (ArchivoDeMod mod : mods) {
            if (mod.existeClase(nombreClase)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Obtiene información detallada de métodos para una clase específica.
     * @param nombreClase Nombre completo de la clase
     * @return Lista de información de métodos con sus referencias
     */
    public static List<InfoMetodoMod> obtenerMetodosConReferenciasDeClase(String nombreClase) {
        if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
            return new ArrayList<>();
        }
        
        List<InfoMetodoMod> resultados = new ArrayList<>();
        for (ArchivoDeMod mod : mods) {
            if (mod.existeClase(nombreClase)) {
                List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(nombreClase);
                for (ArchivoDeMod.InfoMetodo metodo : metodos) {
                    resultados.add(new InfoMetodoMod(mod, metodo));
                }
            }
        }
        return resultados;
    }
    
    /**
     * Obtiene campos declarados en una clase específica.
     * @param nombreClase Nombre completo de la clase
     * @return Lista de información de campos
     */
    public static List<InfoCampoMod> obtenerCamposDeClase(String nombreClase) {
        if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
            return new ArrayList<>();
        }
        
        List<InfoCampoMod> resultados = new ArrayList<>();
        for (ArchivoDeMod mod : mods) {
            if (mod.existeClase(nombreClase)) {
                List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(nombreClase);
                for (ArchivoDeMod.InfoCampo campo : campos) {
                    resultados.add(new InfoCampoMod(mod, campo));
                }
            }
        }
        return resultados;
    }
    
    /**
     * Busca todas las referencias dentro de un método específico.
     * @param nombreClase Nombre completo de la clase
     * @param nombreMetodo Nombre del método
     * @param descriptor Descriptor del método
     * @return Lista de referencias encontradas en el método
     */
    public static List<ArchivoDeMod.Referencia> buscarReferenciasEnMetodoEnMods(
            String nombreClase, String nombreMetodo, String descriptor) {
        if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
            return new ArrayList<>();
        }
        
        List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();
        for (ArchivoDeMod mod : mods) {
            if (mod.existeClase(nombreClase)) {
                resultados.addAll(mod.buscarReferenciasEnMetodo(nombreClase, nombreMetodo, descriptor));
            }
        }
        return resultados;
    }
    
    /**
     * Busca todas las referencias a un método específico (llamadas externas).
     * @param claseObjetivo Clase objetivo del método
     * @param metodoObjetivo Nombre del método objetivo
     * @param descriptorObjetivo Descriptor del método objetivo
     * @return Lista de referencias que llaman al método objetivo
     */
    public static List<ReferenciaMod> buscarReferenciasDeMetodoEnMods(
            String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
            return new ArrayList<>();
        }
        
        List<ReferenciaMod> resultados = new ArrayList<>();
        for (ArchivoDeMod mod : mods) {
            List<ArchivoDeMod.Referencia> referencias = mod.buscarReferenciasAMetodo(
                    claseObjetivo, metodoObjetivo, descriptorObjetivo);
            for (ArchivoDeMod.Referencia ref : referencias) {
                resultados.add(new ReferenciaMod(mod, ref));
            }
        }
        return resultados;
    }
    
    /**
     * Convierte un nombre de clase del formato de puntos (java.lang.Object) 
     * al formato interno de ASM (java/lang/Object)
     */
    public static String convertirFormatoClase(String nombreClase) {
        // Si ya tiene '/', asumimos que está en formato interno
        if (nombreClase.contains("/")) {
            return nombreClase;
        }
        // Si tiene '.', convertimos los puntos a barras
        else if (nombreClase.contains(".")) {
            return nombreClase.replace('.', '/');
        }
        // Si no tiene ni '.' ni '/', es una clase en el paquete por defecto
        else {
            return nombreClase;
        }
    }

    /**
     * Convierte un nombre de clase del formato interno de ASM (java/lang/Object)
     * al formato de puntos (java.lang.Object)
     */
    public static String convertirFormatoClasePuntos(String nombreClase) {
        // Si ya tiene '.', asumimos que está en formato de puntos
        if (nombreClase.contains(".")) {
            return nombreClase;
        }
        // Si tiene '/', convertimos las barras a puntos
        else if (nombreClase.contains("/")) {
            return nombreClase.replace('/', '.');
        }
        // Si no tiene ni '.' ni '/', es una clase en el paquete por defecto
        else {
            return nombreClase;
        }
    }
    
    /**
     * Busca todas las referencias hacia un método específico en todos los mods.
     * @param claseObjetivo Clase objetivo del método
     * @param metodoObjetivo Nombre del método objetivo
     * @param descriptorObjetivo Descriptor del método objetivo
     * @return Lista de referencias con información del mod y clase origen
     */
    public static List<ReferenciaMod> buscarReferenciasHaciaMetodo(String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
            return new ArrayList<>();
        }
        
        List<ReferenciaMod> resultados = new ArrayList<>();
        for (ArchivoDeMod mod : mods) {
            List<ArchivoDeMod.Referencia> referencias = mod.buscarReferenciasAMetodo(
                    claseObjetivo, metodoObjetivo, descriptorObjetivo);
            for (ArchivoDeMod.Referencia ref : referencias) {
                resultados.add(new ReferenciaMod(mod, ref));
            }
        }
        return resultados;
    }
    
    // CLASES AUXILIARES PARA RESULTADOS
    
    /**
     * Combina información de método con su mod de origen.
     */
    public static class InfoMetodoMod {
        private final ArchivoDeMod mod;
        private final ArchivoDeMod.InfoMetodo infoMetodo;
        
        public InfoMetodoMod(ArchivoDeMod mod, ArchivoDeMod.InfoMetodo infoMetodo) {
            this.mod = mod;
            this.infoMetodo = infoMetodo;
        }
        
        public ArchivoDeMod obtenerMod() { return mod; }
        public ArchivoDeMod.InfoMetodo obtenerInfoMetodo() { return infoMetodo; }
    }
    
    /**
     * Combina información de campo con su mod de origen.
     */
    public static class InfoCampoMod {
        private final ArchivoDeMod mod;
        private final ArchivoDeMod.InfoCampo infoCampo;
        
        public InfoCampoMod(ArchivoDeMod mod, ArchivoDeMod.InfoCampo infoCampo) {
            this.mod = mod;
            this.infoCampo = infoCampo;
        }
        
        public ArchivoDeMod obtenerMod() { return mod; }
        public ArchivoDeMod.InfoCampo obtenerInfoCampo() { return infoCampo; }
    }
    
    /**
     * Representa una referencia a un método con información del mod origen.
     */
    public static class ReferenciaMod {
        private final ArchivoDeMod mod;
        private final ArchivoDeMod.Referencia referencia;
        
        public ReferenciaMod(ArchivoDeMod mod, ArchivoDeMod.Referencia referencia) {
            this.mod = mod;
            this.referencia = referencia;
        }
        
        public ArchivoDeMod obtenerMod() { return mod; }
        public ArchivoDeMod.Referencia obtenerReferencia() { return referencia; }
    }

	public static boolean puedeAnalizarElContentidoDeClase() {
		// TODO Auto-generated method stub
		if(ArchivoDeMod.ASM_DISPONIBLE) {return true;}
		if(ArchivoDeMod.JAVASSIST_DISPONIBLE) {return true;}
		return false;
	}
}