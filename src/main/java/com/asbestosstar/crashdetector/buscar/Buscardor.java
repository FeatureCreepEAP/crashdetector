package com.asbestosstar.crashdetector.buscar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;

public class Buscardor {

    public static Set<ArchivoDeMod> mods = new HashSet<ArchivoDeMod>();

    public static boolean cargado = false;
    
    private static final boolean ASM_DISPONIBLE;
    
    static {
        boolean disponible = false;
        try {
            // Verificar si ASM está disponible en el classpath
            Class.forName("org.objectweb.asm.ClassReader");
            disponible = true;
        } catch (ClassNotFoundException e) {
            // ASM no está presente, continuar sin funcionalidad de análisis de bytecode
        }
        ASM_DISPONIBLE = disponible;
    }
    
    /**
     * Verifica si la biblioteca ASM está disponible en el classpath.
     * @return true si ASM está presente, false en caso contrario
     */
    public static boolean esASMDisponible() {
        return ASM_DISPONIBLE;
    }
    
    /**
     * Analiza los métodos de una clase y sus referencias internas.
     */
    public static List<ArchivoDeMod.InfoMetodo> analizarMetodosConReferencias(ArchivoDeMod mod, String nombreClase) {
        if (!ASM_DISPONIBLE) {
            return new ArrayList<>();
        }
        
        byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
        if (bytesClase == null) {
            return new ArrayList<>();
        }
        
        try {
            // Usar ASM para analizar la clase
            ClassReader lector = new ClassReader(bytesClase);
            RecolectorInfoMetodos recolector = new RecolectorInfoMetodos();
            lector.accept(recolector, ClassReader.SKIP_DEBUG);
            return recolector.obtenerResultados();
        } catch (Throwable t) {
            // Capturar cualquier error de ASM (incluyendo NoClassDefFoundError)
            CrashDetectorLogger.logException(t);
            return new ArrayList<>();
        }
    }
    
    /**
     * Analiza los campos declarados en una clase.
     */
    public static List<ArchivoDeMod.InfoCampo> analizarCampos(ArchivoDeMod mod, String nombreClase) {
        if (!ASM_DISPONIBLE) {
            return new ArrayList<>();
        }
        
        byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
        if (bytesClase == null) {
            return new ArrayList<>();
        }
        
        try {
            ClassReader lector = new ClassReader(bytesClase);
            RecolectorInfoCampos recolector = new RecolectorInfoCampos();
            lector.accept(recolector, ClassReader.SKIP_DEBUG);
            return recolector.obtenerResultados();
        } catch (Throwable t) {
            CrashDetectorLogger.logException(t);
            return new ArrayList<>();
        }
    }
    
    /**
     * Analiza las referencias dentro de un método específico.
     */
    public static List<ArchivoDeMod.Referencia> analizarReferenciasEnMetodo(
            ArchivoDeMod mod, String nombreClase, String nombreMetodo, String descriptor) {
        if (!ASM_DISPONIBLE) {
            return new ArrayList<>();
        }
        
        byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
        if (bytesClase == null) {
            return new ArrayList<>();
        }
        
        try {
            ClassReader lector = new ClassReader(bytesClase);
            RecolectorReferenciasMetodo recolector = new RecolectorReferenciasMetodo(nombreMetodo, descriptor);
            lector.accept(recolector, ClassReader.SKIP_DEBUG);
            return recolector.obtenerResultados();
        } catch (Throwable t) {
            CrashDetectorLogger.logException(t);
            return new ArrayList<>();
        }
    }
    
    /**
     * Analiza todas las referencias a un método específico (llamadas externas).
     */
    public static List<ArchivoDeMod.Referencia> analizarReferenciasAMetodo(
            ArchivoDeMod mod, String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        if (!ASM_DISPONIBLE) {
            return new ArrayList<>();
        }
        
        List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();
        for (String nombreClase : mod.obtenerTodosLosNombresDeClases()) {
            byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
            if (bytesClase != null) {
                try {
                    ClassReader lector = new ClassReader(bytesClase);
                    RecolectorLlamadasMetodo recolector = new RecolectorLlamadasMetodo(
                            claseObjetivo, metodoObjetivo, descriptorObjetivo);
                    lector.accept(recolector, ClassReader.SKIP_DEBUG);
                    resultados.addAll(recolector.obtenerResultados());
                } catch (Throwable t) {
                    CrashDetectorLogger.logException(t);
                }
            }
        }
        return resultados;
    }
    
    // Clases internas para análisis de bytecode con ASM
    // Solo se utilizan cuando ASM está disponible
    private static class RecolectorInfoMetodos extends ClassVisitor {
        private final List<ArchivoDeMod.InfoMetodo> resultados = new ArrayList<>();
        private String metodoActual;
        private String descriptorActual;
        private final List<ArchivoDeMod.Referencia> referenciasMetodos = new ArrayList<>();
        private final List<ArchivoDeMod.Referencia> referenciasCampos = new ArrayList<>();

        public RecolectorInfoMetodos() {
            super(Opcodes.ASM6);
        }

        @Override
        public MethodVisitor visitMethod(int acceso, String nombre, String descriptor, String firma, String[] excepciones) {
            if (metodoActual != null) {
                resultados.add(new ArchivoDeMod.InfoMetodo(
                    metodoActual, descriptorActual, referenciasMetodos, referenciasCampos
                ));
                referenciasMetodos.clear();
                referenciasCampos.clear();
            }
            metodoActual = nombre;
            descriptorActual = descriptor;
            return new RecolectorReferenciasMetodoInterno();
        }

        @Override
        public void visitEnd() {
            if (metodoActual != null) {
                resultados.add(new ArchivoDeMod.InfoMetodo(
                    metodoActual, descriptorActual, referenciasMetodos, referenciasCampos
                ));
            }
        }

        public List<ArchivoDeMod.InfoMetodo> obtenerResultados() {
            return new ArrayList<>(resultados);
        }

        private class RecolectorReferenciasMetodoInterno extends MethodVisitor {
            public RecolectorReferenciasMetodoInterno() {
                super(Opcodes.ASM6);
            }

            @Override
            public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor, boolean esInterfaz) {
                referenciasMetodos.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true));
            }

            @Override
            public void visitFieldInsn(int opcode, String propietario, String nombre, String descriptor) {
                referenciasCampos.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, false));
            }
        }
    }

    private static class RecolectorInfoCampos extends ClassVisitor {
        private final List<ArchivoDeMod.InfoCampo> resultados = new ArrayList<>();

        public RecolectorInfoCampos() {
            super(Opcodes.ASM6);
        }

        @Override
        public FieldVisitor visitField(int acceso, String nombre, String descriptor, String firma, Object valor) {
            resultados.add(new ArchivoDeMod.InfoCampo(nombre, descriptor));
            return null;
        }

        public List<ArchivoDeMod.InfoCampo> obtenerResultados() {
            return new ArrayList<>(resultados);
        }
    }

    private static class RecolectorReferenciasMetodo extends ClassVisitor {
        private final String metodoObjetivo;
        private final String descriptorObjetivo;
        private final List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();

        public RecolectorReferenciasMetodo(String metodo, String descriptor) {
            super(Opcodes.ASM6);
            this.metodoObjetivo = metodo;
            this.descriptorObjetivo = descriptor;
        }

        @Override
        public MethodVisitor visitMethod(int acceso, String nombre, String descriptor, String firma, String[] excepciones) {
            if (nombre.equals(metodoObjetivo) && descriptor.equals(descriptorObjetivo)) {
                return new RecolectorReferencias();
            }
            return null;
        }

        public List<ArchivoDeMod.Referencia> obtenerResultados() {
            return new ArrayList<>(resultados);
        }

        private class RecolectorReferencias extends MethodVisitor {
            public RecolectorReferencias() {
                super(Opcodes.ASM6);
            }

            @Override
            public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor, boolean esInterfaz) {
                resultados.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true));
            }

            @Override
            public void visitFieldInsn(int opcode, String propietario, String nombre, String descriptor) {
                resultados.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, false));
            }
        }
    }

    private static class RecolectorLlamadasMetodo extends ClassVisitor {
        private final String claseObjetivo;
        private final String metodoObjetivo;
        private final String descriptorObjetivo;
        private final List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();

        public RecolectorLlamadasMetodo(String clase, String metodo, String descriptor) {
            super(Opcodes.ASM6);
            this.claseObjetivo = clase;
            this.metodoObjetivo = metodo;
            this.descriptorObjetivo = descriptor;
        }

        @Override
        public MethodVisitor visitMethod(int acceso, String nombre, String descriptor, String firma, String[] excepciones) {
            return new RecolectorLlamadas();
        }

        public List<ArchivoDeMod.Referencia> obtenerResultados() {
            return new ArrayList<>(resultados);
        }

        private class RecolectorLlamadas extends MethodVisitor {
            public RecolectorLlamadas() {
                super(Opcodes.ASM6);
            }

            @Override
            public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor, boolean esInterfaz) {
                if (propietario.equals(claseObjetivo) && nombre.equals(metodoObjetivo) && descriptor.equals(descriptorObjetivo)) {
                    resultados.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true));
                }
            }
        }
    }
    
    // ... Métodos existentes continuación ...
    
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
    
    public static String rutaParaPublicar(String ruta) {
        boolean anonimo = Config.obtenerInstancia().esAnonimizarRegistros();
        if (anonimo) {
            return AnonimizadorDeRuta.anonimizarNombreDeUsuario(ruta.toString());
        }
        return ruta.toString();
    }
    
    
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
     * @return Lista de ubicaciones de mods que contienen el término
     */

    public static List<ArchivoDeMod> buscarModsConTermino(String termino) {
        List<ArchivoDeMod> resultados = new ArrayList<>();
        for (ArchivoDeMod mod : mods) {
            resultados.addAll(mod.buscarModsCon(termino));
        }
        return resultados;
    }
    
    /**
     * Si tienes una lista de ArchivoDeMods pero quieres las ubicaciones
     * @param mods
     * @return
     */
    public static List<String> obtenerUbicaciones(List<ArchivoDeMod> mods){
        List<String> ret = new ArrayList<String>();
        for(ArchivoDeMod mod : mods) {
            ret.add(mod.ubicacion_para_publicar());
        }
        return ret;
    }
    
    // NUEVOS MÉTODOS DE ANÁLISIS DE BYTECODE (ASM)
    
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
     * NOTA: Devuelve lista vacía si ASM no está disponible.
     * @param nombreClase Nombre completo de la clase
     * @return Lista de información de métodos con sus referencias
     */
    public static List<InfoMetodoMod> obtenerMetodosConReferenciasDeClase(String nombreClase) {
        if (!esASMDisponible()) {
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
     * NOTA: Devuelve lista vacía si ASM no está disponible.
     * @param nombreClase Nombre completo de la clase
     * @return Lista de información de campos
     */
    public static List<InfoCampoMod> obtenerCamposDeClase(String nombreClase) {
        if (!esASMDisponible()) {
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
     * NOTA: Devuelve lista vacía si ASM no está disponible.
     * @param nombreClase Nombre completo de la clase
     * @param nombreMetodo Nombre del método
     * @param descriptor Descriptor del método
     * @return Lista de referencias encontradas en el método
     */
    public static List<ArchivoDeMod.Referencia> buscarReferenciasEnMetodoEnMods(
            String nombreClase, String nombreMetodo, String descriptor) {
        if (!esASMDisponible()) {
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
     * NOTA: Devuelve lista vacía si ASM no está disponible.
     * @param claseObjetivo Clase objetivo del método
     * @param metodoObjetivo Nombre del método objetivo
     * @param descriptorObjetivo Descriptor del método objetivo
     * @return Lista de referencias que llaman al método objetivo
     */
    public static List<ReferenciaMod> buscarReferenciasDeMetodoEnMods(
            String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        if (!esASMDisponible()) {
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
     * Combina una referencia con su mod de origen.
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
}