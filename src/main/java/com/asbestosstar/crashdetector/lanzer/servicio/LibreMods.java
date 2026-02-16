package com.asbestosstar.crashdetector.lanzer.servicio;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Collections;
import java.util.Set;

/**
 * LibreMods
 *
 * Gestiona compatibilidad JPMS (Java 9+) desde un javaagent.
 *
 * Funcionalidad: - Compatible con Java 8 - No referencia java.lang.Module
 * directamente - Añade dinámicamente: 1) addReads: módulos externos leen
 * crashdetector 2) addOpens: crashdetector abre sus paquetes - Registra
 * transformer auxiliar - Nunca interrumpe el arranque
 */
public class LibreMods {

	/* ===================================================== */
	/* ==================== API PÚBLICA ==================== */
	/* ===================================================== */

	public static void librar(Instrumentation inst) {

		/* Java 8 no tiene JPMS */
		if (!esJava9OMayor()) {
			return;
		}

		try {

			/* Registrar transformer auxiliar */
			try {
				inst.addTransformer(new DetectorDeModuloTransformer(), true);
			} catch (Throwable ignored) {
			}

			/* Obtener clase java.lang.Module por reflexión */
			Class<?> claseModule = Class.forName("java.lang.Module");

			/* Class#getModule() */
			Method mGetModule = Class.class.getMethod("getModule");

			/* Módulo del agente (crashdetector) */
			Object moduloAgente = mGetModule.invoke(LibreMods.class);

			/* Instrumentation#getAllModules() */
			Method mGetAllModules = Instrumentation.class.getMethod("getAllModules");

			@SuppressWarnings("unchecked")
			Set<Object> todosLosModulos = (Set<Object>) mGetAllModules.invoke(inst);

			/* Module#getPackages() */
			Method mGetPackages = claseModule.getMethod("getPackages");

			@SuppressWarnings("unchecked")
			Set<String> paquetesAgente = (Set<String>) mGetPackages.invoke(moduloAgente);

			/* Instrumentation#redefineModule(...) */
			Method mRedefineModule = Instrumentation.class.getMethod("redefineModule", claseModule, Set.class,
					java.util.Map.class, java.util.Map.class, Set.class, java.util.Map.class);

			/* Module#addOpens(String, Module) */
			Method mAddOpens = claseModule.getDeclaredMethod("addOpens", String.class, claseModule);

			mAddOpens.setAccessible(true);

			/*
			 * ===================================================== 1) addReads
			 *
			 * Cada módulo externo debe leer crashdetector (dirección correcta para evitar
			 * IllegalAccessError) =====================================================
			 */
			for (Object moduloDestino : todosLosModulos) {

				try {

					if (moduloDestino == moduloAgente) {
						continue;
					}

					Set<Object> extraReads = Collections.singleton(moduloAgente);

					mRedefineModule.invoke(inst, moduloDestino, // redefinir módulo externo
							extraReads, // ahora lee crashdetector
							Collections.emptyMap(), Collections.emptyMap(), Collections.emptySet(),
							Collections.emptyMap());

				} catch (Throwable ignored) {
				}
			}

			/*
			 * ===================================================== 2) addOpens
			 *
			 * Abrir paquetes del agente hacia todos los módulos
			 * =====================================================
			 */
			for (Object moduloDestino : todosLosModulos) {

				for (String paquete : paquetesAgente) {

					try {
						mAddOpens.invoke(moduloAgente, paquete, moduloDestino);
					} catch (Throwable ignored) {
					}
				}
			}

		} catch (Throwable ignored) {
			/* Nunca impedir arranque */
		}
	}

	/* ===================================================== */
	/* ==================== TRANSFORMER ==================== */
	/* ===================================================== */

	/**
	 * Transformer auxiliar de diagnóstico. No modifica bytecode.
	 */
	private static final class DetectorDeModuloTransformer implements ClassFileTransformer {

		@Override
		public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
				ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

			/* En carga inicial classBeingRedefined es null */
			if (classBeingRedefined == null) {
				return null;
			}

			if (!esJava9OMayor()) {
				return null;
			}

			try {

				Method mGetModule = Class.class.getMethod("getModule");

				Object modulo = mGetModule.invoke(classBeingRedefined);

				if (modulo == null) {
					return null;
				}

				Method mGetName = modulo.getClass().getMethod("getName");

				String nombreModulo = (String) mGetName.invoke(modulo);

				/* Punto opcional de depuración */
				// System.out.println("Clase " + className + " en módulo " + nombreModulo);

			} catch (Throwable ignored) {
			}

			return null;
		}
	}

	/* ===================================================== */
	/* ==================== UTILIDAD ======================= */
	/* ===================================================== */

	protected static boolean esJava9OMayor() {

		String version = System.getProperty("java.specification.version");

		try {

			/* Java 8 -> 1.8 */
			if (version.startsWith("1.")) {
				return Integer.parseInt(version.substring(2)) >= 9;
			}

			/* Java 9+ -> 9, 11, 17, 21... */
			return Integer.parseInt(version) >= 9;

		} catch (Throwable t) {
			return false;
		}
	}
}
