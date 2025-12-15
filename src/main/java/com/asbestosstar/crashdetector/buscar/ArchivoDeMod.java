package com.asbestosstar.crashdetector.buscar;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;
import com.asbestosstar.crashdetector.cargador.Cargador;

/**
 * Interfaz que representa un archivo de mod (JAR, ZIP, directorio) y sus
 * contenidos. Proporciona métodos para explorar y analizar mods sin depender
 * directamente de ASM o Javassist.
 */
public interface ArchivoDeMod {

	public static ArchivoDeMod origin = new Origin();

	/**
	 * Obtiene la fuente original de este archivo de mod.
	 * 
	 * @return Referencia al origen del mod
	 */
	public ArchivoDeMod obtenerDesde();

	/**
	 * Obtiene la lista de mods embebidos dentro de este mod.
	 * 
	 * @return Lista de mods contenidos
	 */
	public List<ArchivoDeMod> mods_en_mods();

	/**
	 * Obtiene los nombres del mod en diferentes formatos.
	 * 
	 * @return Lista de nombres del mod
	 */
	public List<String> nombre();

	/**
	 * Obtiene la ubicación física del archivo de mod.
	 * 
	 * @return Ruta del archivo
	 */
	public String ubicacion();

	/**
	 * Obtiene la ubicación anonimizada para publicar en informes.
	 * 
	 * @return Ruta anonimizada
	 */
	public default String ubicacion_para_publicar() {
		return AnonimizadorDeRuta.anonimizarNombreDeUsuario(this.ubicacion());
	}

	/**
	 * Obtiene la lista de clases contenidas en el mod.
	 * 
	 * @return Lista de nombres de clases
	 */
	public List<String> clases();

	/**
	 * Verifica si existe un nombre específico de forma recursiva.
	 * 
	 * @param nombre Nombre a buscar
	 * @return true si existe, false en caso contrario
	 */
	public boolean tieneNombreRecursivo(String nombre);

	/**
	 * Obtiene el contenido de un nombre específico de forma recursiva.
	 * 
	 * @param nombre Nombre a buscar
	 * @return Contenido del nombre o null si no existe
	 */
	public String obtenerNombreRecursivo(String nombre);

	/**
	 * Verifica si existe un archivo específico de forma recursiva.
	 * 
	 * @param archivo Archivo a buscar
	 * @return true si existe, false en caso contrario
	 */
	public boolean tieneArchivoRecursivo(String archivo);

	/**
	 * Obtiene el contenido de un archivo específico de forma recursiva.
	 * 
	 * @param archivo Archivo a buscar
	 * @return Contenido del archivo o null si no existe
	 */
	public String obtenerArchivoRecursivo(String archivo);

	/**
	 * Obtiene la lista de todos los archivos contenidos en el mod.
	 * 
	 * @return Lista de nombres de archivos
	 */
	public List<String> archivos();

	/**
	 * Busca recursivamente mods que contengan el archivo, clase o paquete
	 * especificado.
	 * 
	 * @param termino Término a buscar (archivo, clase o paquete)
	 * @return Lista de mods que contienen el elemento buscado
	 */
	public List<ArchivoDeMod> buscarModsCon(String termino);

	// VERIFICACIÓN DE BIBLIOTECAS DE BYTECODE
	public static final boolean ASM_DISPONIBLE = verificarClaseEnClasspath("org.objectweb.asm.ClassReader");
	public static final boolean JAVASSIST_DISPONIBLE = verificarClaseEnClasspath("javassist.bytecode.ClassFile");

	/**
	 * Verifica si una clase está disponible en el classpath.
	 * 
	 * @param nombreClase Nombre completo de la clase a verificar
	 * @return true si la clase existe, false en caso contrario
	 */
	public static boolean verificarClaseEnClasspath(String nombreClase) {
		try {
			Class.forName(nombreClase);
			CrashDetectorLogger.log("tiene clase " + nombreClase);
			return true;
		} catch (ClassNotFoundException e) {
			CrashDetectorLogger.log("NO tiene clase " + nombreClase);
			return false;
		}
	}

	/**
	 * Verifica si alguna biblioteca de análisis de bytecode está disponible.
	 * 
	 * @return true si ASM o Javassist están disponibles, false en caso contrario
	 */
	public static boolean esAnalisisDeBytecodeDisponible() {
		return ASM_DISPONIBLE || JAVASSIST_DISPONIBLE;
	}

	// MÉTODOS DE ANÁLISIS DE BYTECODE

	/**
	 * Verifica si una clase existe en el mod.
	 * 
	 * @param nombreClase Nombre completo de la clase (ej: "java/lang/Object")
	 * @return true si la clase existe, false en caso contrario
	 */
	boolean existeClase(String nombreClase);

	/**
	 * Obtiene información de métodos de una clase incluyendo referencias internas.
	 * 
	 * @param nombreClase Nombre completo de la clase
	 * @return Lista con información de métodos y sus referencias
	 */
	default List<InfoMetodo> obtenerMetodosConReferencias(String nombreClase) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarMetodos(this, nombreClase);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarMetodos(this, nombreClase);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Obtiene campos declarados en una clase.
	 * 
	 * @param nombreClase Nombre completo de la clase
	 * @return Lista con información de campos
	 */
	default List<InfoCampo> obtenerCampos(String nombreClase) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarCampos(this, nombreClase);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarCampos(this, nombreClase);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Busca todas las referencias dentro de un método específico.
	 * 
	 * @param nombreClase  Nombre completo de la clase
	 * @param nombreMetodo Nombre del método
	 * @param descriptor   Descriptor del método (ej: "(Ljava/lang/String;)V")
	 * @return Lista de referencias encontradas en el método
	 */
	default List<Referencia> buscarReferenciasEnMetodo(String nombreClase, String nombreMetodo, String descriptor) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarReferenciasEnMetodo(this, nombreClase, nombreMetodo, descriptor);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarReferenciasEnMetodo(this, nombreClase, nombreMetodo, descriptor);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Busca todas las referencias a un método específico (llamadas externas).
	 * 
	 * @param claseObjetivo      Clase objetivo del método (ej: "java/lang/String")
	 * @param metodoObjetivo     Nombre del método objetivo
	 * @param descriptorObjetivo Descriptor del método objetivo
	 * @return Lista de referencias que llaman al método objetivo
	 */
	default List<Referencia> buscarReferenciasAMetodo(String claseObjetivo, String metodoObjetivo,
			String descriptorObjetivo) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarReferenciasAMetodo(this, claseObjetivo, metodoObjetivo,
					descriptorObjetivo);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarReferenciasAMetodo(this, claseObjetivo, metodoObjetivo,
					descriptorObjetivo);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @param moduleinfo
	 * @return
	 */
	public default List<String> obtenerNombresDeModuleInfo(byte[] moduleinfo) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.obtenerNombreModuloInfo(moduleinfo);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.obtenerNombreModuloInfo(moduleinfo);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Obtiene los bytes de una clase.
	 * 
	 * @param nombreClase Nombre completo de la clase en formato interno (ej:
	 *                    "java/lang/Object")
	 * @return Bytes de la clase o null si no existe
	 */
	byte[] obtenerBytesClase(String nombreClase);

	/**
	 * Obtiene todos los nombres de clases en el mod.
	 * 
	 * @return Lista de nombres de clases en formato interno
	 */
	List<String> obtenerTodosLosNombresDeClases();

	/**
	 * Los cargadors la mod suporte
	 * 
	 * @return
	 */
	public List<Cargador> cargadores();

	public default boolean funcionarConCargadoresActuales() {
		for (Cargador car : cargadores()) {

			if (Cargador.cargadores_activados.contains(car)) {
				return true;
			}

		}

		return false;
	}

	/**
	 * Si el archivo (descriptor.mod, modules.xml,fabric.mod.json,mods.toml,etc)
	 * tiene String mcreator.
	 * 
	 * @return
	 */
	public default boolean MetaDataTieneReferenciaDeMCReator() {
		return false;
	}

	// CLASES AUXILIARES PARA ANÁLISIS DE BYTECODE

	/**
	 * Información detallada de un método incluyendo sus referencias internas.
	 */
	public static class InfoMetodo {
		private final String nombre;
		private final String descriptor;
		private final List<Referencia> referenciasAMetodos;
		private final List<Referencia> referenciasACampos;

		public InfoMetodo(String nombre, String descriptor, List<Referencia> referenciasAMetodos,
				List<Referencia> referenciasACampos) {
			this.nombre = nombre;
			this.descriptor = descriptor;
			this.referenciasAMetodos = new ArrayList<>(referenciasAMetodos);
			this.referenciasACampos = new ArrayList<>(referenciasACampos);
		}

		public String obtenerNombre() {
			return nombre;
		}

		public String obtenerDescriptor() {
			return descriptor;
		}

		public List<Referencia> obtenerReferenciasAMetodos() {
			return new ArrayList<>(referenciasAMetodos);
		}

		public List<Referencia> obtenerReferenciasACampos() {
			return new ArrayList<>(referenciasACampos);
		}
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

		public String obtenerNombre() {
			return nombre;
		}

		public String obtenerDescriptor() {
			return descriptor;
		}
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

		public String obtenerClase() {
			return clase;
		}

		public String obtenerNombre() {
			return nombre;
		}

		public String obtenerDescriptor() {
			return descriptor;
		}

		public boolean esMetodo() {
			return esMetodo;
		}

		public boolean esCampo() {
			return !esMetodo;
		}
	}

	/**
	 * Implementación vacía para el origen predeterminado.
	 */
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

		@Override
		public List<Cargador> cargadores() {
			// TODO Auto-generated method stub
			return new ArrayList<Cargador>();
		}

		@Override
		public int precargarTodasLasClasesRecursivo() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	public static class Constante {
		private final String clase; // clase donde se encontró (formato interno ej. a/b/C)
		private final String metodo; // nombre del método
		private final String descriptorMetodo; // descriptor del método
		private final Object valor; // el valor literal (String, Integer, Float, Long, Double, etc.)
		private final String tipo; // nombre simple del tipo (String, int, float, long, double, Class, …)

		public Constante(String clase, String metodo, String descriptorMetodo, Object valor, String tipo) {
			this.clase = clase;
			this.metodo = metodo;
			this.descriptorMetodo = descriptorMetodo;
			this.valor = valor;
			this.tipo = tipo;
		}

		public String obtenerClase() {
			return clase;
		}

		public String obtenerMetodo() {
			return metodo;
		}

		public String obtenerDescriptorMetodo() {
			return descriptorMetodo;
		}

		public Object obtenerValor() {
			return valor;
		}

		public String obtenerTipo() {
			return tipo;
		}

		@Override
		public String toString() {
			return "Constante{clase=" + clase + ", metodo=" + metodo + ", desc=" + descriptorMetodo + ", tipo=" + tipo
					+ ", valor=" + String.valueOf(valor) + "}";
		}
	}

	// === NUEVO: API de alto nivel para constantes ===
	/**
	 * Busca constantes (LDC, BIPUSH, SIPUSH, ICONST_x, FCONST_x, LCONST_x,
	 * DCONST_x, etc.) usadas dentro de un método específico.
	 *
	 * @param nombreClase      Clase (formato interno ej. "a/b/C")
	 * @param nombreMetodo     Nombre del método
	 * @param descriptorMetodo Descriptor del método
	 * @return Lista de constantes encontradas
	 */
	default List<Constante> buscarConstantesEnMetodo(String nombreClase, String nombreMetodo, String descriptorMetodo) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarConstantesEnMetodo(this, nombreClase, nombreMetodo, descriptorMetodo);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarConstantesEnMetodo(this, nombreClase, nombreMetodo,
					descriptorMetodo);
		} else {
			return new ArrayList<>();
		}
	}
	
	
	

	public int precargarTodasLasClasesRecursivo();

}