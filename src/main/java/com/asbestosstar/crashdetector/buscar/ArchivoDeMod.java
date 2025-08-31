package com.asbestosstar.crashdetector.buscar;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.management.Descriptor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.ClassFile;
import javassist.bytecode.Opcode;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

//TODO Mods de Carpetas
public interface ArchivoDeMod {

    public static ArchivoDeMod origin = new Origin();

    public ArchivoDeMod obtenerDesde();

    public List<ArchivoDeMod> mods_en_mods();
            
    public List<String> nombre();
    
    public String ubicacion();
    
    public default String ubicacion_para_publicar() {
        return AnonimizadorDeRuta.anonimizarNombreDeUsuario(this.ubicacion());
    }
    
    public List<String> clases();
    
    public boolean tieneNombreRecursivo(String nombre);
    
    public String obtenerNombreRecursivo(String nombre);
    
    public boolean tieneArchivoRecursivo(String archivo);
    
    public String obtenerArchivoRecursivo(String archivo);
    
    public List<String> archivos();
    
    /**
     * Busca recursivamente mods que contengan el archivo, clase o paquete especificado.
     * @param termino Término a buscar (archivo, clase o paquete)
     * @return Lista de mods que contienen el elemento buscado
     */
    public List<ArchivoDeMod> buscarModsCon(String termino);
    
    // VERIFICACIÓN DE BIBLIOTECAS DE BYTECODE
    static final boolean ASM_DISPONIBLE= verificarClaseEnClasspath("org.objectweb.asm.ClassReader");;
    static final boolean JAVASSIST_DISPONIBLE= verificarClaseEnClasspath("javassist.bytecode.ClassFile");;

    static boolean verificarClaseEnClasspath(String nombreClase) {
        try {
            Class.forName(nombreClase);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Verifica si alguna biblioteca de análisis de bytecode está disponible.
     * @return true si ASM o Javassist están disponibles, false en caso contrario
     */
    public static boolean esAnalisisDeBytecodeDisponible() {
        return ASM_DISPONIBLE || JAVASSIST_DISPONIBLE;
    }
    
    // MÉTODOS DE ANÁLISIS DE BYTECODE
    
    /**
     * Verifica si una clase existe en el mod.
     * @param nombreClase Nombre completo de la clase (ej: "java/lang/Object")
     * @return true si la clase existe, false en caso contrario
     */
    boolean existeClase(String nombreClase);
    
    /**
     * Obtiene información de métodos de una clase incluyendo referencias internas.
     * @param nombreClase Nombre completo de la clase
     * @return Lista con información de métodos y sus referencias
     */
    default List<InfoMetodo> obtenerMetodosConReferencias(String nombreClase) {
        if (ASM_DISPONIBLE) {
            return analizarMetodosConASM(nombreClase);
        } else if (JAVASSIST_DISPONIBLE) {
            return analizarMetodosConJavassist(nombreClase);
        } else {
            return new ArrayList<>();
        }
    }
    
    default List<InfoMetodo> analizarMetodosConASM(String nombreClase) {
        byte[] bytesClase = obtenerBytesClase(nombreClase);
        if (bytesClase == null) {
            return new ArrayList<>();
        }
        
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
    
    default List<InfoMetodo> analizarMetodosConJavassist(String nombreClase) {
        byte[] bytesClase = obtenerBytesClase(nombreClase);
        if (bytesClase == null) {
            return new ArrayList<>();
        }
        
        try {
            // Configurar ClassPool sin crear instancias innecesarias de CtClass
            ClassPool pool = new ClassPool();
            pool.appendSystemPath(); // Añadir classpath del sistema para resolver tipos básicos
            
            // Análisis directo con ClassFile para evitar conversiones innecesarias a CtClass
            ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));
            
            List<InfoMetodo> resultados = new ArrayList<>();
            // Recorrer métodos usando ClassFile directamente (más eficiente que CtClass)
            for (javassist.bytecode.MethodInfo methodInfo : classFile.getMethods()) {
                String methodName = methodInfo.getName();
                String descriptor = methodInfo.getDescriptor();
                
                // Saltar métodos abstractos/nativos (no tienen bytecode para analizar)
                if ((methodInfo.getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
                    resultados.add(new InfoMetodo(methodName, descriptor, new ArrayList<>(), new ArrayList<>()));
                    continue;
                }
                
                // Coleccionar referencias usando instrumentación dirigida
                List<Referencia> referenciasMetodos = new ArrayList<>();
                List<Referencia> referenciasCampos = new ArrayList<>();
                
                // Crear CtClass SOLO cuando sea necesario para la instrumentación
                CtClass ctClass = pool.makeClass(classFile, false);
                // Obtener método específico sin resolver tipos completos
                CtMethod ctMethod = ctClass.getMethod(methodName, descriptor);//TODO Declared
                
                // Instrumentar SOLO el método objetivo para minimizar overhead
                ctMethod.instrument(new ExprEditor() {
                    @Override
                    public void edit(MethodCall m) {
                        referenciasMetodos.add(convertirAMiReferencia(m));
                    }
                    
                    @Override
                    public void edit(FieldAccess f) {
                        referenciasCampos.add(convertirAMiReferencia(f));
                    }
                });
                
                resultados.add(new InfoMetodo(methodName, descriptor, referenciasMetodos, referenciasCampos));
            }
            return resultados;
        } catch (Throwable t) {
            CrashDetectorLogger.logException(t);
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene campos declarados en una clase.
     * @param nombreClase Nombre completo de la clase
     * @return Lista con información de campos
     */
    default List<InfoCampo> obtenerCampos(String nombreClase) {
        if (ASM_DISPONIBLE) {
            return analizarCamposConASM(nombreClase);
        } else if (JAVASSIST_DISPONIBLE) {
            return analizarCamposConJavassist(nombreClase);
        } else {
            return new ArrayList<>();
        }
    }
    
    default List<InfoCampo> analizarCamposConASM(String nombreClase) {
        byte[] bytesClase = obtenerBytesClase(nombreClase);
        if (bytesClase == null) {
            return new ArrayList<>();
        }
        
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
    default List<InfoCampo> analizarCamposConJavassist(String nombreClase) {
        byte[] bytesClase = obtenerBytesClase(nombreClase);
        if (bytesClase == null) {
            return new ArrayList<>();
        }
        
        try {
            // Análisis DIRECTO con ClassFile - NO requiere CtClass para metadatos de campos
            ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));
            List<InfoCampo> resultados = new ArrayList<>();
            
            // Obtener campos directamente desde ClassFile (más rápido y ligero)
            for (javassist.bytecode.FieldInfo fieldInfo : classFile.getFields()) {
                resultados.add(new InfoCampo(fieldInfo.getName(), fieldInfo.getDescriptor()));
            }
            return resultados;
        } catch (Throwable t) {
            CrashDetectorLogger.logException(t);
            return new ArrayList<>();
        }
    }
    
    /**
     * Busca todas las referencias dentro de un método específico.
     * @param nombreClase Nombre completo de la clase
     * @param nombreMetodo Nombre del método
     * @param descriptor Descriptor del método (ej: "(Ljava/lang/String;)V")
     * @return Lista de referencias encontradas en el método
     */
    default List<Referencia> buscarReferenciasEnMetodo(String nombreClase, String nombreMetodo, String descriptor) {
        if (ASM_DISPONIBLE) {
            return analizarReferenciasEnMetodoConASM(nombreClase, nombreMetodo, descriptor);
        } else if (JAVASSIST_DISPONIBLE) {
            return analizarReferenciasEnMetodoConJavassist(nombreClase, nombreMetodo, descriptor);
        } else {
            return new ArrayList<>();
        }
    }
    
    default List<Referencia> analizarReferenciasEnMetodoConASM(String nombreClase, String nombreMetodo, String descriptor) {
        byte[] bytesClase = obtenerBytesClase(nombreClase);
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
    
    default List<Referencia> analizarReferenciasEnMetodoConJavassist(String nombreClase, String nombreMetodo, String descriptor) {
        byte[] bytesClase = obtenerBytesClase(nombreClase);
        if (bytesClase == null) {
            return new ArrayList<>();
        }
        
        try {
            ClassPool pool = new ClassPool();
            pool.appendSystemPath();
            ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));
            
            // Buscar método específico usando ClassFile (evita crear CtClass innecesario)
            javassist.bytecode.MethodInfo methodInfo = null;
            for (javassist.bytecode.MethodInfo mi : classFile.getMethods()) {
                if (mi.getName().equals(nombreMetodo) && mi.getDescriptor().equals(descriptor)) {
                    methodInfo = mi;
                    break;
                }
            }
            
            // Validar si el método existe y tiene bytecode analizable
            if (methodInfo == null || (methodInfo.getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
                return new ArrayList<>();
            }
            
            // Coleccionar referencias del método específico
            List<Referencia> resultados = new ArrayList<>();
            CtClass ctClass = pool.makeClass(classFile, false);
            CtMethod ctMethod = ctClass.getMethod(nombreMetodo, descriptor);//TODO Declared            
            // Instrumentar SOLO el método objetivo
            ctMethod.instrument(new ExprEditor() {
                @Override
                public void edit(MethodCall m) {
                    resultados.add(convertirAMiReferencia(m));
                }
                
                @Override
                public void edit(FieldAccess f) {
                    resultados.add(convertirAMiReferencia(f));
                }
            });
            
            return resultados;
        } catch (Throwable t) {
            CrashDetectorLogger.logException(t);
            return new ArrayList<>();
        }
    }
    
    /**
     * Busca todas las referencias a un método específico (llamadas externas).
     * @param claseObjetivo Clase objetivo del método (ej: "java/lang/String")
     * @param metodoObjetivo Nombre del método objetivo
     * @param descriptorObjetivo Descriptor del método objetivo
     * @return Lista de referencias que llaman al método objetivo
     */
    default List<Referencia> buscarReferenciasAMetodo(String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        if (ASM_DISPONIBLE) {
            return analizarReferenciasAMetodoConASM(claseObjetivo, metodoObjetivo, descriptorObjetivo);
        } else if (JAVASSIST_DISPONIBLE) {
            return analizarReferenciasAMetodoConJavassist(claseObjetivo, metodoObjetivo, descriptorObjetivo);
        } else {
            return new ArrayList<>();
        }
    }
    
    default List<Referencia> analizarReferenciasAMetodoConASM(String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        List<Referencia> resultados = new ArrayList<>();
        for (String nombreClase : obtenerTodosLosNombresDeClases()) {
            byte[] bytesClase = obtenerBytesClase(nombreClase);
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
    
    default List<Referencia> analizarReferenciasAMetodoConJavassist(String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        List<Referencia> resultados = new ArrayList<>();
        List<String> todosNombresClases = obtenerTodosLosNombresDeClases();
        
        // Reutilizar ClassPool para todas las clases (mejor rendimiento)
        ClassPool pool = new ClassPool();
        pool.appendSystemPath();
        
        for (String className : todosNombresClases) {
            byte[] bytes = obtenerBytesClase(className);
            if (bytes == null) continue;
            
            try {
                ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytes)));
                CtClass ctClass = pool.makeClass(classFile, false);
                
                // Recorrer SOLO métodos declarados en esta clase
                for (CtMethod method : ctClass.getDeclaredMethods()) {
                    // Saltar métodos sin bytecode (abstractos/nativos)
                    if ((method.getMethodInfo().getAccessFlags() & 
                        (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
                        continue;
                    }
                    
                    // Instrumentar SOLO para buscar llamadas específicas
                    method.instrument(new ExprEditor() {
                        @Override
                        public void edit(MethodCall m) {
                            // Convertir a formato interno de nombres (barra invertida → punto)
                            String owner = m.getClassName().replace('.', '/');
                            if (owner.equals(claseObjetivo) && 
                                m.getMethodName().equals(metodoObjetivo) && 
                                m.getSignature().equals(descriptorObjetivo)) {
                                resultados.add(new Referencia(
                                    owner, 
                                    metodoObjetivo, 
                                    descriptorObjetivo, 
                                    true
                                ));
                            }
                        }
                    });
                }
            } catch (Throwable t) {
                CrashDetectorLogger.logException(t);
            }
        }
        return resultados;
    }
    
    
    /**
     * Convierte una referencia de Javassist (llamada a método) a nuestro formato estándar
     * Maneja la conversión de nombres de clase (punto → barra invertida)
     */
    public static Referencia convertirAMiReferencia(MethodCall m) {
        return new Referencia(
            m.getClassName().replace('.', '/'), // Formato interno de ASM
            m.getMethodName(),
            m.getSignature(),
            true
        );
    }

    /**
     * Convierte una referencia de Javassist (acceso a campo) a nuestro formato estándar
     * Maneja la conversión de nombres de clase (punto → barra invertida)
     */
    public static Referencia convertirAMiReferencia(FieldAccess f) {
        return new Referencia(
            f.getClassName().replace('.', '/'), // Formato interno de ASM
            f.getFieldName(),
            f.getSignature(),
            false
        );
    }
    
    
    
    /**
     * Obtiene los bytes de una clase.
     * @param nombreClase Nombre completo de la clase en formato interno (ej: "java/lang/Object")
     * @return Bytes de la clase o null si no existe
     */
    byte[] obtenerBytesClase(String nombreClase);
    
    /**
     * Obtiene todos los nombres de clases en el mod.
     * @return Lista de nombres de clases en formato interno
     */
    List<String> obtenerTodosLosNombresDeClases();
    
    // CLASES AUXILIARES PARA ANÁLISIS DE BYTECODE
    
    /**
     * Información detallada de un método incluyendo sus referencias internas.
     */
    public static class InfoMetodo {
        private final String nombre;
        private final String descriptor;
        private final List<Referencia> referenciasAMetodos;
        private final List<Referencia> referenciasACampos;
        
        public InfoMetodo(String nombre, String descriptor, 
                         List<Referencia> referenciasAMetodos, 
                         List<Referencia> referenciasACampos) {
            this.nombre = nombre;
            this.descriptor = descriptor;
            this.referenciasAMetodos = new ArrayList<>(referenciasAMetodos);
            this.referenciasACampos = new ArrayList<>(referenciasACampos);
        }
        
        public String obtenerNombre() { return nombre; }
        public String obtenerDescriptor() { return descriptor; }
        public List<Referencia> obtenerReferenciasAMetodos() { return new ArrayList<>(referenciasAMetodos); }
        public List<Referencia> obtenerReferenciasACampos() { return new ArrayList<>(referenciasACampos); }
    }
    
    /**
     * Información básica de un campo declarado.
     */
    public static class InfoCampo {
        private final String nombre;
        private final String descriptor;
        
        public InfoCampo(String nombre, String descriptor) {
            this.nombre = nombre;
            this.descriptor = descriptor;
        }
        
        public String obtenerNombre() { return nombre; }
        public String obtenerDescriptor() { return descriptor; }
    }
    
    /**
     * Representa una referencia a un método o campo.
     */
    public static class Referencia {
        private final String clase;
        private final String nombre;
        private final String descriptor;
        private final boolean esMetodo;
        
        public Referencia(String clase, String nombre, String descriptor, boolean esMetodo) {
            this.clase = clase;
            this.nombre = nombre;
            this.descriptor = descriptor;
            this.esMetodo = esMetodo;
        }
        
        public String obtenerClase() { return clase; }
        public String obtenerNombre() { return nombre; }
        public String obtenerDescriptor() { return descriptor; }
        public boolean esMetodo() { return esMetodo; }
        public boolean esCampo() { return !esMetodo; }
    }

    // VISITORES DE ASM (ahora en la interfaz)
    
    static class RecolectorInformacionMetodos extends ClassVisitor {
        private final List<InfoMetodo> resultados = new ArrayList<>();

        public RecolectorInformacionMetodos() {
            super(obtenerVersionMaximaASM());
        }

        @Override
        public MethodVisitor visitMethod(int acceso, String nombre, String descriptor, String firma, String[] excepciones) {
            List<Referencia> referenciasMetodos = new ArrayList<>();
            List<Referencia> referenciasCampos = new ArrayList<>();
            
            return new RecolectorReferenciasMetodoInterno(nombre, descriptor, referenciasMetodos, referenciasCampos);
        }

        public List<InfoMetodo> obtenerResultados() {
            return new ArrayList<>(resultados);
        }

        private class RecolectorReferenciasMetodoInterno extends MethodVisitor {
            private final String nombre;
            private final String descriptor;
            private final List<Referencia> referenciasMetodos;
            private final List<Referencia> referenciasCampos;
            
            public RecolectorReferenciasMetodoInterno(String nombre, String descriptor,
                    List<Referencia> referenciasMetodos,
                    List<Referencia> referenciasCampos) {
                super(obtenerVersionMaximaASM());
                this.nombre = nombre;
                this.descriptor = descriptor;
                this.referenciasMetodos = referenciasMetodos;
                this.referenciasCampos = referenciasCampos;
            }

            @Override
            public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor, boolean esInterfaz) {
                referenciasMetodos.add(new Referencia(propietario, nombre, descriptor, true));
            }

            @Override
            public void visitFieldInsn(int opcode, String propietario, String nombre, String descriptor) {
                referenciasCampos.add(new Referencia(propietario, nombre, descriptor, false));
            }
            
            @Override
            public void visitEnd() {
                resultados.add(new InfoMetodo(nombre, descriptor, referenciasMetodos, referenciasCampos));
            }
        }
    }

    static class RecolectorInformacionCampos extends ClassVisitor {
        private final List<InfoCampo> resultados = new ArrayList<>();

        public RecolectorInformacionCampos() {
            super(obtenerVersionMaximaASM());
        }

        @Override
        public FieldVisitor visitField(int acceso, String nombre, String descriptor, String firma, Object valor) {
            resultados.add(new InfoCampo(nombre, descriptor));
            return null;
        }

        public List<InfoCampo> obtenerResultados() {
            return new ArrayList<>(resultados);
        }
    }

    static class RecolectorReferenciasMetodo extends ClassVisitor {
        private final String metodoObjetivo;
        private final String descriptorObjetivo;
        private final List<Referencia> resultados = new ArrayList<>();

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

        public List<Referencia> obtenerResultados() {
            return new ArrayList<>(resultados);
        }

        private class RecolectorReferencias extends MethodVisitor {
            public RecolectorReferencias() {
                super(obtenerVersionMaximaASM());
            }

            @Override
            public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor, boolean esInterfaz) {
                resultados.add(new Referencia(propietario, nombre, descriptor, true));
            }

            @Override
            public void visitFieldInsn(int opcode, String propietario, String nombre, String descriptor) {
                resultados.add(new Referencia(propietario, nombre, descriptor, false));
            }
        }
    }

    static class RecolectorLlamadasAMetodo extends ClassVisitor {
        private final String claseObjetivo;
        private final String metodoObjetivo;
        private final String descriptorObjetivo;
        private final List<Referencia> resultados = new ArrayList<>();

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

        public List<Referencia> obtenerResultados() {
            return new ArrayList<>(resultados);
        }

        private class RecolectorLlamadas extends MethodVisitor {
            public RecolectorLlamadas() {
                super(obtenerVersionMaximaASM());
            }

            @Override
            public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor, boolean esInterfaz) {
                if (propietario.equals(claseObjetivo) && nombre.equals(metodoObjetivo) && descriptor.equals(descriptorObjetivo)) {
                    resultados.add(new Referencia(propietario, nombre, descriptor, true));
                }
            }
        }
    }
    
    static class Origin implements ArchivoDeMod {

        @Override
        public ArchivoDeMod obtenerDesde() {
            return origin;
        }

        @Override
        public List<ArchivoDeMod> mods_en_mods() {
            return new ArrayList<>();
        }

        @Override
        public List<String> nombre() {
            return new ArrayList<String>();
        }

        @Override
        public String ubicacion() {
            return "";
        }

        @Override
        public List<String> clases() {
            return new ArrayList<String>();
        }

        @Override
        public boolean tieneNombreRecursivo(String nombre) {
            return false;
        }

        @Override
        public String obtenerNombreRecursivo(String nombre) {
            return null;
        }

        @Override
        public boolean tieneArchivoRecursivo(String paquete) {
            return false;
        }

        @Override
        public String obtenerArchivoRecursivo(String paquete) {
            return null;
        }

        @Override
        public List<String> archivos() {
            return new ArrayList<String>();
        }

        @Override
        public List<ArchivoDeMod> buscarModsCon(String termino) {
            return new ArrayList<ArchivoDeMod>();
        }

        // Implementación para métodos de análisis
        @Override
        public boolean existeClase(String nombreClase) {
            return false;
        }

        @Override
        public byte[] obtenerBytesClase(String nombreClase) {
            return null;
        }

        @Override
        public List<String> obtenerTodosLosNombresDeClases() {
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene la versión máxima de ASM compatible con el entorno.
     * Verifica desde ASM10 hacia ASM4 y devuelve la primera disponible.
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