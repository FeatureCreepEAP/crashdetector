package com.asbestosstar.crashdetector.buscar;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Analizador de bytecode utilizando la biblioteca ASM.
 * Proporciona métodos para inspeccionar clases, métodos y referencias sin depender de Javassist.
 */
public class AnalizadorBytecodeASM {

    /**
     * Analiza los métodos de una clase y sus referencias internas utilizando ASM.
     * 
     * @param mod Referencia al mod que contiene la clase
     * @param nombreClase Nombre de la clase en formato interno (ej: "java/lang/Object")
     * @return Lista con información detallada de los métodos
     */
    public static List<ArchivoDeMod.InfoMetodo> analizarMetodos(ArchivoDeMod mod, String nombreClase) {
        byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
        if (bytesClase == null) return new ArrayList<>();
        
        try {
            ClassReader lector = new ClassReader(bytesClase);
            RecolectorInformacionMetodos recolector = new RecolectorInformacionMetodos();
            lector.accept(recolector, ClassReader.SKIP_DEBUG);
            return recolector.obtenerResultados();
        } catch (Throwable t) {
            CrashDetectorLogger.logException(t);
            return new ArrayList<>();
        }
    }

    /**
     * Analiza los campos declarados en una clase utilizando ASM.
     * 
     * @param mod Referencia al mod que contiene la clase
     * @param nombreClase Nombre de la clase en formato interno
     * @return Lista con información de los campos declarados
     */
    public static List<ArchivoDeMod.InfoCampo> analizarCampos(ArchivoDeMod mod, String nombreClase) {
        byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
        if (bytesClase == null) return new ArrayList<>();
        
        try {
            ClassReader lector = new ClassReader(bytesClase);
            RecolectorInformacionCampos recolector = new RecolectorInformacionCampos();
            lector.accept(recolector, ClassReader.SKIP_DEBUG);
            return recolector.obtenerResultados();
        } catch (Throwable t) {
            CrashDetectorLogger.logException(t);
            return new ArrayList<>();
        }
    }

    /**
     * Analiza las referencias dentro de un método específico utilizando ASM.
     * 
     * @param mod Referencia al mod que contiene la clase
     * @param nombreClase Nombre de la clase en formato interno
     * @param nombreMetodo Nombre del método a analizar
     * @param descriptor Descriptor del método
     * @return Lista de referencias encontradas en el método
     */
    public static List<ArchivoDeMod.Referencia> analizarReferenciasEnMetodo(
            ArchivoDeMod mod, String nombreClase, String nombreMetodo, String descriptor) {
        byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
        if (bytesClase == null) return new ArrayList<>();
        
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
     * Busca todas las llamadas a un método específico en todo el mod utilizando ASM.
     * 
     * @param mod Referencia al mod que contiene las clases
     * @param claseObjetivo Clase objetivo del método (formato interno)
     * @param metodoObjetivo Nombre del método objetivo
     * @param descriptorObjetivo Descriptor del método objetivo
     * @return Lista de referencias que llaman al método objetivo
     */
    public static List<ArchivoDeMod.Referencia> analizarReferenciasAMetodo(
            ArchivoDeMod mod, String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();
        for (String nombreClase : mod.obtenerTodosLosNombresDeClases()) {
            byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
            if (bytesClase != null) {
                try {
                    ClassReader lector = new ClassReader(bytesClase);
                    RecolectorLlamadasAMetodo recolector = new RecolectorLlamadasAMetodo(
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

    /**
     * Recolector que analiza información de métodos y sus referencias.
     */
    static class RecolectorInformacionMetodos extends ClassVisitor {
        private final List<ArchivoDeMod.InfoMetodo> resultados = new ArrayList<>();

        public RecolectorInformacionMetodos() {
            super(obtenerVersionMaximaASM());
        }

        @Override
        public MethodVisitor visitMethod(int acceso, String nombre, String descriptor, 
                                        String firma, String[] excepciones) {
            List<ArchivoDeMod.Referencia> referenciasMetodos = new ArrayList<>();
            List<ArchivoDeMod.Referencia> referenciasCampos = new ArrayList<>();
            return new RecolectorReferenciasMetodoInterno(
                nombre, descriptor, referenciasMetodos, referenciasCampos);
        }

        public List<ArchivoDeMod.InfoMetodo> obtenerResultados() {
            return new ArrayList<>(resultados);
        }

        /**
         * Recolector interno para analizar referencias dentro de un método específico.
         */
        private class RecolectorReferenciasMetodoInterno extends MethodVisitor {
            private final String nombre;
            private final String descriptor;
            private final List<ArchivoDeMod.Referencia> referenciasMetodos;
            private final List<ArchivoDeMod.Referencia> referenciasCampos;
            
            public RecolectorReferenciasMetodoInterno(String nombre, String descriptor,
                    List<ArchivoDeMod.Referencia> referenciasMetodos,
                    List<ArchivoDeMod.Referencia> referenciasCampos) {
                super(obtenerVersionMaximaASM());
                this.nombre = nombre;
                this.descriptor = descriptor;
                this.referenciasMetodos = referenciasMetodos;
                this.referenciasCampos = referenciasCampos;
            }

            @Override
            public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor, boolean esInterfaz) {
                referenciasMetodos.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true));
            }

            @Override
            public void visitFieldInsn(int opcode, String propietario, String nombre, String descriptor) {
                referenciasCampos.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, false));
            }
            
            @Override
            public void visitEnd() {
                resultados.add(new ArchivoDeMod.InfoMetodo(nombre, descriptor, referenciasMetodos, referenciasCampos));
            }
        }
    }

    /**
     * Recolector que analiza información de campos declarados en una clase.
     */
    static class RecolectorInformacionCampos extends ClassVisitor {
        private final List<ArchivoDeMod.InfoCampo> resultados = new ArrayList<>();

        public RecolectorInformacionCampos() {
            super(obtenerVersionMaximaASM());
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

    /**
     * Recolector que analiza referencias dentro de un método específico.
     */
    static class RecolectorReferenciasMetodo extends ClassVisitor {
        private final String metodoObjetivo;
        private final String descriptorObjetivo;
        private final List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();

        public RecolectorReferenciasMetodo(String metodo, String descriptor) {
            super(obtenerVersionMaximaASM());
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

        /**
         * Recolector interno para analizar referencias dentro del método objetivo.
         */
        private class RecolectorReferencias extends MethodVisitor {
            public RecolectorReferencias() {
                super(obtenerVersionMaximaASM());
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

    /**
     * Recolector que busca llamadas a un método específico en toda la clase.
     */
    static class RecolectorLlamadasAMetodo extends ClassVisitor {
        private final String claseObjetivo;
        private final String metodoObjetivo;
        private final String descriptorObjetivo;
        private final List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();

        public RecolectorLlamadasAMetodo(String clase, String metodo, String descriptor) {
            super(obtenerVersionMaximaASM());
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

        /**
         * Recolector interno para identificar llamadas al método objetivo.
         */
        private class RecolectorLlamadas extends MethodVisitor {
            public RecolectorLlamadas() {
                super(obtenerVersionMaximaASM());
            }

            @Override
            public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor, boolean esInterfaz) {
                if (propietario.equals(claseObjetivo) && nombre.equals(metodoObjetivo) && descriptor.equals(descriptorObjetivo)) {
                    resultados.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true));
                }
            }
        }
    }
    
    /**
     * Obtiene la versión máxima de ASM compatible con el entorno actual.
     * Verifica desde ASM10 hacia ASM4 y devuelve la primera disponible.
     * 
     * @return Versión compatible de ASM
     */
    static int obtenerVersionMaximaASM() {
        try {
            Class<?> clase = Opcodes.class;

            // Recorremos de mayor a menor (10 → 4)
            for (int i = 10; i >= 4; i--) {
                String nombreCampo = "ASM" + i;
                try {
                    java.lang.reflect.Field campo = clase.getField(nombreCampo);
                    return campo.getInt(null);
                } catch (NoSuchFieldException e) {
                    // no existe, seguimos al siguiente menor
                }
            }

            // Si no se encontró ninguno, devolvemos ASM4 por defecto
            return (4 << 16) | (0 << 8);

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la versión ASM", e);
        }
    }
}