package com.asbestosstar.crashdetector.controljvm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.MBeanServer;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Servidor local de diagnóstico que vive dentro de la JVM del juego.
 *
 * Se enlaza exclusivamente a loopback, usa un puerto efímero y exige un token
 * aleatorio. Esto permite que la JVM monitora solicite GC, heap dump o un crash
 * diagnóstico sin depender de taskkill, kill, jcmd, jmap ni tuberías
 * específicas de un sistema operativo.
 */
public final class ServidorControlJVM {

	private static final String PROTOCOLO = "CD-JVM-CONTROL-1";
	private static final SecureRandom ALEATORIO = new SecureRandom();
	private static final Object BLOQUEO = new Object();

	private static volatile ServerSocket servidor;
	private static volatile Thread hiloAceptador;
	private static volatile ExecutorService ejecutorClientes;
	private static volatile File archivoControl;

	private ServidorControlJVM() {
	}

	public static void iniciar() {
		synchronized (BLOQUEO) {
			if (servidor != null && !servidor.isClosed()) {
				return;
			}

			try {
				long pid = pidActual();
				ServerSocket nuevoServidor = new ServerSocket(0, 16, InetAddress.getLoopbackAddress());
				nuevoServidor.setReuseAddress(false);

				String token = crearToken();
				File nuevoArchivo = ClienteControlJVM.archivoControl(pid);
				guardarConfiguracionAtomica(nuevoArchivo, nuevoServidor.getLocalPort(), token);

				servidor = nuevoServidor;
				archivoControl = nuevoArchivo;
				ejecutorClientes = Executors.newFixedThreadPool(2, new FabricaHilos("ControlJVM-Cliente"));

				hiloAceptador = new Thread(new Aceptador(token), "ControlJVM-Aceptador");
				hiloAceptador.setDaemon(true);
				hiloAceptador.start();

				Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
					@Override
					public void run() {
						detener();
					}
				}, "ControlJVM-Limpieza"));

				CrashDetectorLogger.log("Control JVM local iniciado en loopback para PID " + pid + ".");
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				detener();
			}
		}
	}

	public static void detener() {
		synchronized (BLOQUEO) {
			if (servidor != null) {
				try {
					servidor.close();
				} catch (Exception ignorado) {
				}
				servidor = null;
			}

			if (ejecutorClientes != null) {
				ejecutorClientes.shutdownNow();
				ejecutorClientes = null;
			}

			if (archivoControl != null) {
				try {
					Files.deleteIfExists(archivoControl.toPath());
				} catch (Exception ignorado) {
				}
				archivoControl = null;
			}
		}
	}

	private static final class Aceptador implements Runnable {
		private final String token;

		Aceptador(String token) {
			this.token = token;
		}

		@Override
		public void run() {
			while (true) {
				ServerSocket actual = servidor;
				if (actual == null || actual.isClosed()) {
					return;
				}

				try {
					final Socket cliente = actual.accept();
					cliente.setSoTimeout(120_000);

					ExecutorService ejecutor = ejecutorClientes;
					if (ejecutor == null) {
						cliente.close();
						return;
					}

					ejecutor.execute(new Runnable() {
						@Override
						public void run() {
							atender(cliente, token);
						}
					});
				} catch (Exception e) {
					if (servidor != null && !servidor.isClosed()) {
						CrashDetectorLogger.logException(e);
					}
				}
			}
		}
	}

	private static void atender(Socket socket, String tokenEsperado) {
		try (Socket cliente = socket;
				DataInputStream entrada = new DataInputStream(new BufferedInputStream(cliente.getInputStream()));
				DataOutputStream salida = new DataOutputStream(new BufferedOutputStream(cliente.getOutputStream()))) {

			try {
				String protocolo = entrada.readUTF();
				String token = entrada.readUTF();
				String comando = entrada.readUTF();
				String argumento = entrada.readUTF();
				boolean bandera = entrada.readBoolean();

				if (!PROTOCOLO.equals(protocolo) || !comparacionConstante(tokenEsperado, token)) {
					responder(salida, false, RespuestaControlJVM.CODIGO_NO_AUTORIZADO, "");
					return;
				}

				if ("PING".equals(comando)) {
					responder(salida, true, RespuestaControlJVM.CODIGO_PONG, "");
					return;
				}

				if ("GC".equals(comando)) {
					System.gc();
					System.runFinalization();
					responder(salida, true, RespuestaControlJVM.CODIGO_GC_ACEPTADO, "");
					return;
				}

				if ("HEAP_DUMP".equals(comando)) {
					String ruta = crearHeapDump(argumento, bandera);
					responder(salida, true, RespuestaControlJVM.CODIGO_HEAP_DUMP_CREADO, ruta);
					return;
				}

				if ("CRASH_HS_ERR".equals(comando)) {
					responder(salida, true, RespuestaControlJVM.CODIGO_CRASH_ACEPTADO, "");
					provocarCrashDespuesDeResponder();
					return;
				}

				responder(salida, false, RespuestaControlJVM.CODIGO_ERROR, "Comando desconocido");
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				responder(salida, false, RespuestaControlJVM.CODIGO_ERROR, mensajeSeguro(t));
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private static void responder(DataOutputStream salida, boolean correcta, String codigo, String detalle)
			throws Exception {
		salida.writeBoolean(correcta);
		salida.writeUTF(codigo == null ? RespuestaControlJVM.CODIGO_ERROR : codigo);
		salida.writeUTF(detalle == null ? "" : detalle);
		salida.flush();
	}

	private static String crearHeapDump(String rutaSolicitada, boolean soloVivos) throws Exception {
		if (rutaSolicitada == null || rutaSolicitada.trim().isEmpty()) {
			throw new IllegalArgumentException("Ruta de heap dump vacía");
		}

		File destino = new File(rutaSolicitada).getCanonicalFile();
		if (!destino.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".hprof")) {
			throw new IllegalArgumentException("El archivo debe terminar en .hprof");
		}
		if (destino.exists()) {
			throw new IllegalArgumentException("El archivo ya existe");
		}

		File padre = destino.getParentFile();
		if (padre != null) {
			Files.createDirectories(padre.toPath());
		}

		Class<?> tipoMxBean = Class.forName("com.sun.management.HotSpotDiagnosticMXBean");
		MBeanServer servidorMBeans = ManagementFactory.getPlatformMBeanServer();

		@SuppressWarnings({ "unchecked", "rawtypes" })
		Object bean = ManagementFactory.newPlatformMXBeanProxy(servidorMBeans,
				"com.sun.management:type=HotSpotDiagnostic", (Class) tipoMxBean);

		Method dumpHeap = tipoMxBean.getMethod("dumpHeap", String.class, boolean.class);

		try {
			dumpHeap.invoke(bean, destino.getAbsolutePath(), Boolean.valueOf(soloVivos));
		} catch (InvocationTargetException e) {
			Throwable causa = e.getCause();
			if (causa instanceof Exception) {
				throw (Exception) causa;
			}
			throw e;
		}

		limitarPermisos(destino);
		return destino.getAbsolutePath();
	}

	private static void provocarCrashDespuesDeResponder() {
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(300L);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				provocarCrashNativo();
			}
		}, "ControlJVM-CrashHsErr");

		/*
		 * No es daemon: debe alcanzar la llamada nativa aunque el resto de hilos del
		 * juego termine durante los milisegundos de espera.
		 */
		hilo.setDaemon(false);
		hilo.start();
	}

	private static void provocarCrashNativo() {
		try {
			Class<?> tipoUnsafe = Class.forName("sun.misc.Unsafe");
			Field campo = tipoUnsafe.getDeclaredField("theUnsafe");
			campo.setAccessible(true);
			Object unsafe = campo.get(null);

			Method putAddress = tipoUnsafe.getMethod("putAddress", long.class, long.class);
			putAddress.invoke(unsafe, Long.valueOf(0L), Long.valueOf(0L));

			/*
			 * Esta línea no debería alcanzarse. Si una VM ignora putAddress, halt evita
			 * continuar en un estado incierto, aunque ese fallback no garantiza hs_err.
			 */
			Runtime.getRuntime().halt(134);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			Runtime.getRuntime().halt(134);
		}
	}

	private static void guardarConfiguracionAtomica(File destino, int puerto, String token) throws Exception {
		File padre = destino.getParentFile();
		if (padre != null) {
			Files.createDirectories(padre.toPath());
		}

		Path temporal = Files.createTempFile(
				padre == null ? destino.toPath().toAbsolutePath().getParent() : padre.toPath(), "control-jvm-", ".tmp");

		Properties propiedades = new Properties();
		propiedades.setProperty("puerto", String.valueOf(puerto));
		propiedades.setProperty("token", token);
		propiedades.setProperty("pid", String.valueOf(pidActual()));

		try (FileOutputStream salida = new FileOutputStream(temporal.toFile())) {
			propiedades.store(salida, "CrashDetector JVM control");
			salida.getFD().sync();
		}

		limitarPermisos(temporal.toFile());

		try {
			Files.move(temporal, destino.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
		} catch (AtomicMoveNotSupportedException e) {
			Files.move(temporal, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}

		limitarPermisos(destino);
	}

	private static void limitarPermisos(File archivo) {
		try {
			archivo.setReadable(false, false);
			archivo.setWritable(false, false);
			archivo.setExecutable(false, false);
			archivo.setReadable(true, true);
			archivo.setWritable(true, true);
		} catch (Throwable ignorado) {
		}
	}

	private static long pidActual() {
		String nombre = ManagementFactory.getRuntimeMXBean().getName();
		int arroba = nombre.indexOf('@');
		String valor = arroba >= 0 ? nombre.substring(0, arroba) : nombre;
		return Long.parseLong(valor);
	}

	private static String crearToken() {
		byte[] datos = new byte[32];
		ALEATORIO.nextBytes(datos);
		StringBuilder texto = new StringBuilder(datos.length * 2);
		for (byte dato : datos) {
			texto.append(String.format(java.util.Locale.ROOT, "%02x", dato & 0xff));
		}
		return texto.toString();
	}

	private static boolean comparacionConstante(String esperado, String recibido) {
		if (esperado == null || recibido == null) {
			return false;
		}

		int diferencia = esperado.length() ^ recibido.length();
		int maximo = Math.max(esperado.length(), recibido.length());

		for (int i = 0; i < maximo; i++) {
			char a = i < esperado.length() ? esperado.charAt(i) : 0;
			char b = i < recibido.length() ? recibido.charAt(i) : 0;
			diferencia |= a ^ b;
		}

		return diferencia == 0;
	}

	private static String mensajeSeguro(Throwable t) {
		Throwable actual = t;
		while (actual instanceof InvocationTargetException && ((InvocationTargetException) actual).getCause() != null) {
			actual = ((InvocationTargetException) actual).getCause();
		}

		String mensaje = actual == null ? "" : actual.getMessage();
		if (mensaje == null || mensaje.trim().isEmpty()) {
			mensaje = actual == null ? "" : actual.getClass().getSimpleName();
		}

		mensaje = mensaje.replace('\r', ' ').replace('\n', ' ').trim();
		return mensaje.length() > 600 ? mensaje.substring(0, 600) : mensaje;
	}

	private static final class FabricaHilos implements ThreadFactory {
		private final String prefijo;
		private final AtomicInteger secuencia = new AtomicInteger();

		FabricaHilos(String prefijo) {
			this.prefijo = prefijo;
		}

		@Override
		public Thread newThread(Runnable tarea) {
			Thread hilo = new Thread(tarea, prefijo + "-" + secuencia.incrementAndGet());
			hilo.setDaemon(true);
			return hilo;
		}
	}
}
