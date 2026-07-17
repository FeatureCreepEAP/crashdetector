package com.asbestosstar.crashdetector.app.lanzermod.tlauncher;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.tlauncher.tlauncher.minecraft.crash.Crash;

import com.asbestosstar.crashdetector.App;
import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Entregar;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.Transformaciones;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;

/**
 * Punto de entrada de CrashDetector para TLModLoader/TLauncher.
 *
 * TLauncher ya es el proceso monitor. Minecraft es el proceso hijo observado.
 * Por tanto, esta integración no inicia otra JVM de MonitorDePID.
 */
public final class CrashDetectorTLauncher {

	private static final String TOKEN_OCULTO = "*********************************";

	private static final AtomicBoolean INICIALIZADO = new AtomicBoolean(false);

	private static final AtomicBoolean ANALISIS_FINAL_INICIADO = new AtomicBoolean(false);

	private static volatile Process procesoMinecraft;
	private static volatile long pidMinecraft = -1L;
	private static volatile int codigoSalidaPendiente = Integer.MIN_VALUE;
	private static volatile Throwable errorProcesoPendiente;
	private static volatile Crash crashTLauncherPendiente;

	private CrashDetectorTLauncher() {
	}

	/**
	 * Método estático invocado por TLModLoader.
	 */
	public static void init() {
		if (!INICIALIZADO.compareAndSet(false, true)) {
			return;
		}

		try {
			CrashDetectorLogger.log("[CrashDetector/TLauncher] Inicializando monitor integrado.");

			/*
			 * Estas operaciones normalmente se realizan desde MonitorDePID.main(...), pero
			 * aquí no debe iniciarse un segundo proceso.
			 */
			MonitorDePID.registrarGUISPredeterminado();
			DetectorLanzer.registrarLanzeresDefectos();
			Transformaciones.init();
			Buscador.cargadoresPredetermindado();

			Statics.cargador = true;
			Statics.lanzer_del_app = "tlauncher";
			Statics.APP = App.MINECRAFT;
			Entregar.app_detecta = App.MINECRAFT;

			CrashDetectorLogger.log("[CrashDetector/TLauncher] Monitor integrado listo.");
		} catch (Throwable error) {
			INICIALIZADO.set(false);
			CrashDetectorLogger.log("[CrashDetector/TLauncher] Falló la inicialización.");
			CrashDetectorLogger.logException(error);
		}
	}

	/**
	 * Registra el proceso real de Minecraft inmediatamente después de que
	 * JavaProcessLauncher.start() lo haya creado.
	 *
	 * @param proceso         proceso Java real de Minecraft.
	 * @param comandoCompleto ejecutable Java, argumentos JVM, clase principal y
	 *                        argumentos del juego.
	 * @param clasePrincipal  clase main obtenida de CompleteVersion.
	 * @param carpetaJuego    carpeta activa de Minecraft; puede ser la carpeta
	 *                        .minecraft normal o una carpeta de instancia.
	 * @param inicioMillis    instante en que TLauncher inició Minecraft.
	 */
	public static synchronized void registrarProcesoMinecraft(Process proceso, List<String> comandoCompleto,
			String clasePrincipal, File carpetaJuego, long inicioMillis) {

		if (!INICIALIZADO.get()) {
			init();
		}

		if (proceso == null) {
			throw new IllegalArgumentException("El proceso de Minecraft no puede ser null.");
		}

		List<String> comando = comandoCompleto == null ? Collections.<String>emptyList()
				: new ArrayList<String>(comandoCompleto);

		/*
		 * Separa los argumentos JVM de la clase principal y los argumentos de
		 * Minecraft.
		 */
		ArgumentosSeparados argumentos = separarArgumentos(comando, clasePrincipal);

		/*
		 * Reinicia el estado correspondiente a una ejecución anterior de Minecraft.
		 */
		procesoMinecraft = proceso;
		pidMinecraft = obtenerPid(proceso);
		codigoSalidaPendiente = Integer.MIN_VALUE;
		errorProcesoPendiente = null;
		crashTLauncherPendiente = null;
		ANALISIS_FINAL_INICIADO.set(false);

		/*
		 * Registra el PID real del proceso de Minecraft.
		 */
		if (pidMinecraft > 0L) {
			MonitorDePID.pid = pidMinecraft;
		}

		/*
		 * Dirige los valores del proceso directamente al estado de CrashDetector. No se
		 * usa Entregar.comenzarEntregar() porque TLauncher ya es el proceso monitor.
		 */
		Statics.JVM_ARGS = argumentos.jvm;
		Statics.APP_ARGS = ocultarTokens(argumentos.aplicacion);
		Statics.INICIO_DE_LA_APP = inicioMillis;
		Statics.lanzer_del_app = "tlauncher";
		Statics.APP = App.MINECRAFT;

		Entregar.app_detecta = App.MINECRAFT;

		if (inicioMillis > 0L) {
			MonitorDePID.utc = Instant.ofEpochMilli(inicioMillis);
		}

		/*
		 * runningMinecraftDir representa la carpeta real de la instalación activa.
		 * Puede ser la carpeta .minecraft predeterminada o una instancia separada.
		 */
		if (carpetaJuego != null) {
			File carpetaNormalizada = carpetaJuego.toPath().toAbsolutePath().normalize().toFile();

			Statics.CARPETA_DE_APP = carpetaNormalizada;

			Path carpetaMods = carpetaNormalizada.toPath().resolve("mods").toAbsolutePath().normalize();

			/*
			 * La nueva ejecución puede utilizar una instancia distinta, por lo que se
			 * elimina la carpeta de mods registrada para la ejecución anterior.
			 */
			Statics.carpetas_de_mods.clear();
			Statics.carpetas_de_mods.add(carpetaMods);

			CrashDetectorLogger
					.log("[CrashDetector/TLauncher] Carpeta de Minecraft: " + Statics.CARPETA_DE_APP.getAbsolutePath());

			CrashDetectorLogger.log("[CrashDetector/TLauncher] Carpeta de mods: " + carpetaMods);
		}

		CrashDetectorLogger.log("[CrashDetector/TLauncher] Minecraft registrado. PID=" + pidMinecraft + ", jvmArgs="
				+ Statics.JVM_ARGS.size() + ", appArgs=" + Statics.APP_ARGS.size());
	}

	/**
	 * Guarda el código de salida tan pronto como MinecraftLauncher lo copia desde
	 * JavaProcess. El análisis se inicia después, cuando CrashDescriptor termina.
	 */
	public static synchronized void registrarSalidaPendiente(int codigoSalida) {
		codigoSalidaPendiente = codigoSalida;

		CrashDetectorLogger.log("[CrashDetector/TLauncher] Código de salida recibido: " + codigoSalida);
	}

	/**
	 * Recibe el resultado de CrashDescriptor.scan().
	 *
	 * Un valor null significa que TLauncher no clasificó la salida como crash.
	 */
	public static synchronized void finalizarDesdeDescriptor(Crash crash) {
		crashTLauncherPendiente = crash;

		if (crash == null) {
			CrashDetectorLogger.log("[CrashDetector/TLauncher] TLauncher no detectó un crash.");
		} else {
			CrashDetectorLogger.log("[CrashDetector/TLauncher] TLauncher detectó un crash. " + "Reconocido="
					+ crash.isRecognized() + ", informe=" + crash.getFile());
		}

		iniciarAnalisisFinal();
	}

	/**
	 * Maneja un error del sistema JavaProcess que impidió completar el ciclo normal
	 * de cierre.
	 */
	public static synchronized void finalizarDesdeError(Throwable error) {
		errorProcesoPendiente = error;

		CrashDetectorLogger.log("[CrashDetector/TLauncher] JavaProcess notificó un error.");

		if (error != null) {
			CrashDetectorLogger.logException(error);
		}

		iniciarAnalisisFinal();
	}

	/**
	 * Punto opcional para futuras funciones de análisis en vivo.
	 *
	 * TLauncher ya entrega aquí una línea completa y sin el salto final.
	 */
	public static void recibirLineaMinecraft(String linea) {
		if (linea == null || linea.isEmpty()) {
			return;
		}

		/*
		 * No se reimprime la línea para evitar duplicarla en la consola de TLauncher.
		 * En una fase posterior puede dirigirse a un analizador incremental.
		 */
	}

	private static void iniciarAnalisisFinal() {
		final Process proceso = procesoMinecraft;

		if (proceso == null) {
			CrashDetectorLogger.log("[CrashDetector/TLauncher] No hay proceso registrado para analizar.");
			return;
		}

		if (!ANALISIS_FINAL_INICIADO.compareAndSet(false, true)) {
			return;
		}

		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					/*
					 * El ProcessMonitorThread de TLauncher ya drenó stdout antes de llamar
					 * onJavaProcessEnded(...). waitFor() regresará inmediatamente porque el proceso
					 * ya terminó.
					 */
					MonitorDePID.monitor_cdlauncher(proceso);
				} catch (Throwable error) {
					CrashDetectorLogger.log("[CrashDetector/TLauncher] Falló el análisis final.");
					CrashDetectorLogger.logException(error);
				}
			}
		}, "CrashDetector-TLauncher-AnalisisFinal");

		hilo.setDaemon(true);
		hilo.start();
	}

	private static ArgumentosSeparados separarArgumentos(List<String> comando, String clasePrincipal) {

		if (comando == null || comando.isEmpty()) {
			return new ArgumentosSeparados(new ArrayList<String>(), new ArrayList<String>());
		}

		int indiceMain = -1;

		if (clasePrincipal != null && !clasePrincipal.isEmpty()) {
			for (int i = 0; i < comando.size(); i++) {
				if (clasePrincipal.equals(comando.get(i))) {
					indiceMain = i;
					break;
				}
			}
		}

		if (indiceMain < 0) {
			/*
			 * No se adivina la frontera si una versión futura cambia la construcción del
			 * comando. Se conserva todo como APP_ARGS para no perder información.
			 */
			return new ArgumentosSeparados(new ArrayList<String>(), new ArrayList<String>(comando));
		}

		int inicioJvm = comando.size() > 0 ? 1 : 0;

		List<String> jvm = new ArrayList<String>(comando.subList(inicioJvm, indiceMain));

		/*
		 * APP_ARGS conserva la clase main, igual que sun.java.command.
		 */
		List<String> aplicacion = new ArrayList<String>(comando.subList(indiceMain, comando.size()));

		return new ArgumentosSeparados(jvm, aplicacion);
	}

	private static List<String> ocultarTokens(List<String> argumentos) {
		if (argumentos == null || argumentos.isEmpty()) {
			return new ArrayList<String>();
		}

		boolean permitirToken = ConfigMundial.obtenerInstancia()
				.obtenerHabilitarTokenDeAccesoEnLaEntregaDelMonitorDePID();

		if (permitirToken) {
			return new ArrayList<String>(argumentos);
		}

		List<String> salida = new ArrayList<String>(argumentos.size());

		for (int i = 0; i < argumentos.size(); i++) {
			String argumento = argumentos.get(i);

			if ("--accessToken".equalsIgnoreCase(argumento) && i + 1 < argumentos.size()) {
				salida.add(argumento);
				salida.add(TOKEN_OCULTO);
				i++;
				continue;
			}

			if (argumento != null && argumento.regionMatches(true, 0, "--accessToken=", 0, "--accessToken=".length())) {
				salida.add("--accessToken=" + TOKEN_OCULTO);
				continue;
			}

			salida.add(argumento);
		}

		return salida;
	}

	/**
	 * Java 9+ expone Process.pid(). El acceso al campo "pid" es únicamente un
	 * respaldo para Java 8 en implementaciones Unix.
	 */
	private static long obtenerPid(Process proceso) {
		try {
			Method metodoPid = Process.class.getMethod("pid");
			Object valor = metodoPid.invoke(proceso);

			if (valor instanceof Number) {
				return ((Number) valor).longValue();
			}
		} catch (Throwable ignorado) {
		}

		Class<?> tipo = proceso.getClass();

		while (tipo != null) {
			try {
				Field campoPid = tipo.getDeclaredField("pid");
				campoPid.setAccessible(true);
				Object valor = campoPid.get(proceso);

				if (valor instanceof Number) {
					return ((Number) valor).longValue();
				}
			} catch (Throwable ignorado) {
			}

			tipo = tipo.getSuperclass();
		}

		return -1L;
	}

	public static long obtenerPidMinecraft() {
		return pidMinecraft;
	}

	public static int obtenerCodigoSalidaPendiente() {
		return codigoSalidaPendiente;
	}

	public static Throwable obtenerErrorProcesoPendiente() {
		return errorProcesoPendiente;
	}

	public static Crash obtenerCrashTLauncherPendiente() {
		return crashTLauncherPendiente;
	}

	private static final class ArgumentosSeparados {
		private final List<String> jvm;
		private final List<String> aplicacion;

		private ArgumentosSeparados(List<String> jvm, List<String> aplicacion) {
			this.jvm = jvm;
			this.aplicacion = aplicacion;
		}
	}
}
