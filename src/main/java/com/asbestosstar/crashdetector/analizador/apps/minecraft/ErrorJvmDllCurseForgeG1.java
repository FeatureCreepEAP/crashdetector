package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta un fallo nativo real de jvm.dll ocurrido durante el recolector G1
 * (hilo activo GCTaskThread) en un lanzamiento de CurseForge.
 *
 * Solo se activa cuando el MISMO archivo aporta TODAS las pruebas:
 *
 * 1. Un encabezado fatal del entorno de ejecucion de Java, o bien la senal o
 * excepcion fatal equivalente (EXCEPTION_ACCESS_VIOLATION, SIGSEGV, SIGBUS).
 *
 * 2. Una linea "Problematic frame:" seguida de cerca por un frame real de
 * jvm.dll/libjvm, es decir, con corchete y direccion ([jvm.dll+0x...]).
 *
 * 3. El hilo activo (Current thread) es un GCTaskThread, lo que indica que el
 * fallo ocurrio dentro del recolector de basura.
 *
 * 4. Indicios de que el juego se inicio desde CurseForge.
 *
 * La mera aparicion de jvm.dll en el listado de librerias cargadas
 * (Dynamic libraries) o de un argumento -XX:+UseG1GC en JVM Flags NO cuentan
 * como prueba: son lineas normales de cualquier informe.
 */
public class ErrorJvmDllCurseForgeG1 implements Verificaciones {

	private static final Set<String> REPORTADOS_GLOBAL = Collections.synchronizedSet(new HashSet<String>());

	/*
	 * La misma instancia de Verificaciones puede recibir lineas de mas de una
	 * Consola. El estado se conserva por identidad de Consola para no combinar
	 * pruebas procedentes de archivos diferentes (por ejemplo un informe de crash
	 * y el launcher_log de CurseForge).
	 */
	private final Map<Consola, EstadoDeteccion> estados = new IdentityHashMap<Consola, EstadoDeteccion>();

	private boolean activado = false;
	private boolean posibleOverwolf = false;
	private String enlace = "";

	/*
	 * La linea real del frame aparece normalmente justo despues del encabezado
	 * Problematic frame:.
	 */
	private static final int MAX_LINEAS_DESPUES_PROBLEMATIC_FRAME = 4;

	private static final int MAX_LINEAS_DESPUES_HILO_ACTUAL = 1;

	private static final String FATAL_ERROR = "A fatal error has been detected by the Java Runtime Environment";

	private static final String EXCEPTION_ACCESS_VIOLATION = "EXCEPTION_ACCESS_VIOLATION";

	private static final String SIGSEGV = "SIGSEGV";
	private static final String SIGBUS = "SIGBUS";

	private static final String PROBLEMATIC_FRAME = "Problematic frame:";

	private static final String JVM_DLL = "[jvm.dll";
	private static final String LIBJVM_DYLIB = "[libjvm.dylib";
	private static final String LIBJVM_SO = "[libjvm.so";

	private static final String HILO_ACTUAL = "Current thread";
	private static final String GC_TASK_THREAD = "GCTaskThread";

	private static final String CF_INSTANCE_ID = "DCFInstanceId";
	private static final String CF_BRAND = "minecraft.launcher.brand=CurseForge";
	private static final String CF_RUTA_WINDOWS = "curseforge\\minecraft";
	private static final String CF_RUTA_UNIX = "curseforge/minecraft";
	private static final String CF_NOMBRE = "CurseForge";

	private static final String OVERWOLF = "Overwolf";
	private static final String OVERWOLF_CLIENT = "OWClient.dll";
	private static final String OVERWOLF_UTILS = "OWUtils.dll";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FATAL_ERROR, EXCEPTION_ACCESS_VIOLATION, SIGSEGV, SIGBUS, PROBLEMATIC_FRAME, JVM_DLL,
				LIBJVM_DYLIB, LIBJVM_SO, GC_TASK_THREAD, CF_INSTANCE_ID, CF_BRAND, CF_RUTA_WINDOWS, CF_RUTA_UNIX,
				CF_NOMBRE, OVERWOLF, OVERWOLF_CLIENT, OVERWOLF_UTILS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null)
			return;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (consola == null || linea == null || activado)
			return;

		EstadoDeteccion estado = obtenerEstado(consola);

		/*
		 * Un nuevo encabezado fatal representa el comienzo de un informe nuevo. Se
		 * descarta cualquier prueba parcial anterior de esta Consola.
		 */
		if (linea.contains(FATAL_ERROR)) {
			estado = new EstadoDeteccion();
			estado.jvmFatal = true;
			estados.put(consola, estado);

		} else if (linea.contains(EXCEPTION_ACCESS_VIOLATION) || linea.contains(SIGSEGV) || linea.contains(SIGBUS)) {
			/*
			 * Permite analizar informes truncados que contengan la senal o la excepcion
			 * aunque falte el encabezado inicial.
			 */
			estado.jvmFatal = true;
		}

		/*
		 * El encabezado Problematic frame: es solo el inicio de la seccion. No basta
		 * por si solo: se espera la linea real del frame.
		 */
		if (linea.contains(PROBLEMATIC_FRAME)) {
			estado.lineaEncabezadoProblematicFrame = num;
			estado.problematicFrameEsJvm = false;
			estado.lineaProblematicFrameJvm = -1;
		}

		/*
		 * Una referencia a jvm.dll/libjvm solo cuenta cuando es el frame real, es
		 * decir, con corchete y justo despues del encabezado. La mencion del listado
		 * Dynamic libraries no puede activar esta verificacion.
		 */
		if (lineaContieneFrameJvm(linea)
				&& estaDentroDeVentana(estado.lineaEncabezadoProblematicFrame, num,
						MAX_LINEAS_DESPUES_PROBLEMATIC_FRAME, false)) {

			estado.problematicFrameEsJvm = true;
			estado.lineaProblematicFrameJvm = num;
		}

		/*
		 * El fallo ocurre dentro del recolector de basura solo cuando el hilo activo
		 * es un GCTaskThread. Se registra la linea del hilo activo para tolerar que el
		 * nombre del hilo aparezca en la linea siguiente.
		 */
		if (linea.contains(HILO_ACTUAL)) {
			estado.lineaHiloActual = num;

			if (linea.contains(GC_TASK_THREAD)) {
				estado.hiloGc = true;
			}

		} else if (linea.contains(GC_TASK_THREAD) && estaDentroDeVentana(estado.lineaHiloActual, num,
				MAX_LINEAS_DESPUES_HILO_ACTUAL, true)) {

			estado.hiloGc = true;
		}

		if (linea.contains(CF_INSTANCE_ID) || linea.contains(CF_BRAND) || linea.contains(CF_RUTA_WINDOWS)
				|| linea.contains(CF_RUTA_UNIX) || linea.contains(CF_NOMBRE)) {

			estado.lanzadorCurseForge = true;
		}

		if (linea.contains(OVERWOLF) || linea.contains(OVERWOLF_CLIENT) || linea.contains(OVERWOLF_UTILS)) {
			estado.overwolf = true;
		}

		activarSiCorresponde(consola, estado);
	}

	private EstadoDeteccion obtenerEstado(Consola consola) {
		EstadoDeteccion estado = estados.get(consola);

		if (estado == null) {
			estado = new EstadoDeteccion();
			estados.put(consola, estado);
		}

		return estado;
	}

	private void activarSiCorresponde(Consola consola, EstadoDeteccion estado) {
		if (activado || estado == null)
			return;

		if (!estado.jvmFatal || !estado.problematicFrameEsJvm || !estado.hiloGc || !estado.lanzadorCurseForge)
			return;

		if (!REPORTADOS_GLOBAL.add(id()))
			return;

		/*
		 * El enlace apunta a la linea real del frame de jvm.dll, no al encabezado
		 * generico Problematic frame:.
		 */
		this.posibleOverwolf = estado.overwolf;
		this.enlace = consola.agregarErrorALectador(estado.lineaProblematicFrameJvm, this);
		this.activado = true;
		estados.clear();
	}

	private boolean lineaContieneFrameJvm(String linea) {
		return linea.contains(JVM_DLL) || linea.contains(LIBJVM_DYLIB) || linea.contains(LIBJVM_SO);
	}

	/**
	 * Comprueba que una linea este cerca de un encabezado anterior.
	 *
	 * @param lineaEncabezado	 numero de linea del encabezado, o -1 si no aparecio
	 * @param lineaActual		 numero de la linea actual
	 * @param distanciaMaxima	 cantidad maxima de lineas permitida
	 * @param permitirMismaLinea true cuando encabezado y dato pueden compartir
	 *							 linea
	 */
	private boolean estaDentroDeVentana(int lineaEncabezado, int lineaActual, int distanciaMaxima,
			boolean permitirMismaLinea) {

		if (lineaEncabezado < 0 || lineaActual < lineaEncabezado)
			return false;

		int distancia = lineaActual - lineaEncabezado;

		if (!permitirMismaLinea && distancia == 0)
			return false;

		return distancia <= distanciaMaxima;
	}

	public static void reiniciarGlobal() {
		REPORTADOS_GLOBAL.clear();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorJvmDllCurseForgeG1();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1550;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorJvmDllCurseForgeG1(posibleOverwolf) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorJvmDllCurseForgeG1();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "error_jvm_dll_curseforge_g1";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	/**
	 * Estado parcial de deteccion perteneciente a un solo archivo o Consola.
	 */
	private static final class EstadoDeteccion {

		private boolean jvmFatal;

		private int lineaEncabezadoProblematicFrame = -1;

		private boolean problematicFrameEsJvm;

		private int lineaProblematicFrameJvm = -1;

		private int lineaHiloActual = -1;

		private boolean hiloGc;

		private boolean lanzadorCurseForge;

		private boolean overwolf;
	}
}
