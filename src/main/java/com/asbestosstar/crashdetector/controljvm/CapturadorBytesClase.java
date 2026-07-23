package com.asbestosstar.crashdetector.controljvm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Captura los bytes que atraviesan la cadena de transformación de una clase ya
 * cargada en la JVM observada.
 *
 * La captura usa una retransformation sin modificar la clase: el transformer
 * temporal devuelve null. Como se registra al final de la cadena, recibe los
 * bytes producidos por los transformers anteriores durante esa
 * retransformation.
 */
public final class CapturadorBytesClase {

	private static final int MAX_BYTES = 64 * 1024 * 1024;
	private static final Object BLOQUEO = new Object();

	private CapturadorBytesClase() {
	}

	public static Resultado capturar(String nombreSolicitado) {
		String nombreJava = normalizarNombreJava(nombreSolicitado);
		if (nombreJava == null || nombreJava.isEmpty()) {
			return Resultado.error(RespuestaControlJVM.CODIGO_ERROR, "NOMBRE_CLASE_VACIO");
		}

		Instrumentation instrumentacion = InstrumentacionCrashDetector.obtener();
		if (instrumentacion == null) {
			return Resultado.error(RespuestaControlJVM.CODIGO_INSTRUMENTACION_NO_DISPONIBLE, nombreJava);
		}
		if (!instrumentacion.isRetransformClassesSupported()) {
			return Resultado.error(RespuestaControlJVM.CODIGO_RETRANSFORMACION_NO_DISPONIBLE, nombreJava);
		}

		Class<?> objetivo = buscarClase(instrumentacion, nombreJava);
		if (objetivo == null) {
			return Resultado.error(RespuestaControlJVM.CODIGO_CLASE_NO_ENCONTRADA, nombreJava);
		}
		if (!instrumentacion.isModifiableClass(objetivo)) {
			return Resultado.error(RespuestaControlJVM.CODIGO_CLASE_NO_MODIFICABLE, nombreJava);
		}

		synchronized (BLOQUEO) {
			return capturarSinConcurrencia(instrumentacion, objetivo, nombreJava);
		}
	}

	private static Resultado capturarSinConcurrencia(final Instrumentation instrumentacion, final Class<?> objetivo,
			final String nombreJava) {
		final AtomicReference<byte[]> capturados = new AtomicReference<byte[]>();

		ClassFileTransformer transformer = new ClassFileTransformer() {
			@Override
			public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
					ProtectionDomain protectionDomain, byte[] classfileBuffer) {
				if (classBeingRedefined == objetivo && classfileBuffer != null) {
					capturados.compareAndSet(null, classfileBuffer.clone());
				}
				return null;
			}
		};

		boolean agregado = false;
		try {
			instrumentacion.addTransformer(transformer, true);
			agregado = true;
			instrumentacion.retransformClasses(objetivo);

			byte[] bytes = capturados.get();
			if (!bytesClaseValidos(bytes)) {
				return Resultado.error(RespuestaControlJVM.CODIGO_DATOS_INVALIDOS, nombreJava);
			}
			return Resultado.correcto(nombreJava, bytes);
		} catch (Throwable t) {
			return Resultado.error(RespuestaControlJVM.CODIGO_ERROR, nombreJava + ": " + mensajeSeguro(t));
		} finally {
			if (agregado) {
				try {
					instrumentacion.removeTransformer(transformer);
				} catch (Throwable ignorado) {
				}
			}
		}
	}

	private static Class<?> buscarClase(Instrumentation instrumentacion, String nombreJava) {
		Class<?> mejor = null;
		for (Class<?> clase : instrumentacion.getAllLoadedClasses()) {
			if (clase == null || !nombreJava.equals(clase.getName())) {
				continue;
			}
			if (!instrumentacion.isModifiableClass(clase)) {
				if (mejor == null) {
					mejor = clase;
				}
				continue;
			}
			if (mejor == null || mejor.getClassLoader() == null && clase.getClassLoader() != null) {
				mejor = clase;
			}
		}
		return mejor;
	}

	private static boolean bytesClaseValidos(byte[] bytes) {
		return bytes != null && bytes.length >= 8 && bytes.length <= MAX_BYTES && (bytes[0] & 0xff) == 0xca
				&& (bytes[1] & 0xff) == 0xfe && (bytes[2] & 0xff) == 0xba && (bytes[3] & 0xff) == 0xbe;
	}

	public static String normalizarNombreJava(String nombre) {
		if (nombre == null) {
			return null;
		}
		String valor = nombre.trim();
		if (valor.startsWith("L") && valor.endsWith(";") && valor.length() > 2) {
			valor = valor.substring(1, valor.length() - 1);
		}
		if (valor.toLowerCase(java.util.Locale.ROOT).endsWith(".class")) {
			valor = valor.substring(0, valor.length() - 6);
		}
		valor = valor.replace('/', '.');
		while (valor.startsWith(".")) {
			valor = valor.substring(1);
		}
		return valor;
	}

	private static String mensajeSeguro(Throwable t) {
		String mensaje = t == null ? "" : t.getMessage();
		if (mensaje == null || mensaje.trim().isEmpty()) {
			mensaje = t == null ? "" : t.getClass().getSimpleName();
		}
		mensaje = mensaje.replace('\r', ' ').replace('\n', ' ').trim();
		return mensaje.length() > 500 ? mensaje.substring(0, 500) : mensaje;
	}

	public static final class Resultado {
		public final boolean correcto;
		public final String codigo;
		public final String detalle;
		public final byte[] bytes;

		private Resultado(boolean correcto, String codigo, String detalle, byte[] bytes) {
			this.correcto = correcto;
			this.codigo = codigo;
			this.detalle = detalle == null ? "" : detalle;
			this.bytes = bytes == null ? null : bytes.clone();
		}

		public static Resultado correcto(String nombreJava, byte[] bytes) {
			return new Resultado(true, RespuestaControlJVM.CODIGO_CLASE_TRANSFERIDA, nombreJava, bytes);
		}

		public static Resultado error(String codigo, String detalle) {
			return new Resultado(false, codigo, detalle, null);
		}
	}
}
