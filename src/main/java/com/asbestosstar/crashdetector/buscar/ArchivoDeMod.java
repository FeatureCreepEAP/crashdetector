package com.asbestosstar.crashdetector.buscar;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;

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
     * @param termino Nombre del archivo, clase o paquete a buscar
     * @return Lista de mods que contienen el elemento buscado
     */
    public List<ArchivoDeMod> buscarModsCon(String termino);
    
    // NUEVOS MÉTODOS PARA ANÁLISIS DE BYTECODE (ASM)
    
    /**
     * Verifica si una clase existe en el mod.
     * @param nombreClase Nombre completo de la clase (ej: "java/lang/Object")
     * @return true si la clase existe, false en caso contrario
     */
    boolean existeClase(String nombreClase);
    
    /**
     * Obtiene información de métodos de una clase incluyendo referencias internas.
     * NOTA: Devuelve lista vacía si ASM no está disponible.
     * @param nombreClase Nombre completo de la clase
     * @return Lista con información de métodos y sus referencias
     */
    default List<InfoMetodo> obtenerMetodosConReferencias(String nombreClase) {
        if (!Buscardor.esASMDisponible()) {
            return new ArrayList<>();
        }
        return Buscardor.analizarMetodosConReferencias(this, nombreClase);
    }
    
    /**
     * Obtiene campos declarados en una clase.
     * NOTA: Devuelve lista vacía si ASM no está disponible.
     * @param nombreClase Nombre completo de la clase
     * @return Lista con información de campos
     */
    default List<InfoCampo> obtenerCampos(String nombreClase) {
        if (!Buscardor.esASMDisponible()) {
            return new ArrayList<>();
        }
        return Buscardor.analizarCampos(this, nombreClase);
    }
    
    /**
     * Busca todas las referencias dentro de un método específico.
     * NOTA: Devuelve lista vacía si ASM no está disponible.
     * @param nombreClase Nombre completo de la clase
     * @param nombreMetodo Nombre del método
     * @param descriptor Descriptor del método (ej: "(Ljava/lang/String;)V")
     * @return Lista de referencias encontradas en el método
     */
    default List<Referencia> buscarReferenciasEnMetodo(String nombreClase, String nombreMetodo, String descriptor) {
        if (!Buscardor.esASMDisponible()) {
            return new ArrayList<>();
        }
        return Buscardor.analizarReferenciasEnMetodo(this, nombreClase, nombreMetodo, descriptor);
    }
    
    /**
     * Busca todas las referencias a un método específico (llamadas externas).
     * NOTA: Devuelve lista vacía si ASM no está disponible.
     * @param claseObjetivo Clase objetivo del método (ej: "java/lang/String")
     * @param metodoObjetivo Nombre del método objetivo
     * @param descriptorObjetivo Descriptor del método objetivo
     * @return Lista de referencias que llaman al método objetivo
     */
    default List<Referencia> buscarReferenciasAMetodo(String claseObjetivo, String metodoObjetivo, String descriptorObjetivo) {
        if (!Buscardor.esASMDisponible()) {
            return new ArrayList<>();
        }
        return Buscardor.analizarReferenciasAMetodo(this, claseObjetivo, metodoObjetivo, descriptorObjetivo);
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
    
    // ESTRUCTURAS DE DATOS PARA ANÁLISIS DE BYTECODE
    
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

    class Origin implements ArchivoDeMod {

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

        // Implementación para nuevos métodos
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

}