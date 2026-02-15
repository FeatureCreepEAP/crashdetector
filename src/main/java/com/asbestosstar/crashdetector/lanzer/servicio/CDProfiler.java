package com.asbestosstar.crashdetector.lanzer.servicio;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import com.asbestosstar.crashdetector.buscar.AnalizadorBytecodeASM;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.profiler.ProfilerGUI;
import com.asbestosstar.crashdetector.gui.tipos.profiler.ProfilerGUIMinaly;

/**
 * Servicio de profiler WIP.
 *
 * - Abre la GUI del profiler
 * - Registra un transformer que instrumenta métodos (no abstractos / no nativos)
 * - Mide duración con System.nanoTime()
 * - Alimenta la GUI con (clase, método, descriptor, duración)
 */
public class CDProfiler implements ServicioCDLauncher {

	public static String ID = "cdprofiler_wip";

	/** Referencia global a la GUI activa para alimentar eventos desde bytecode instrumentado */
	private static volatile ProfilerGUI guiActiva;

	@Override
	public void activar(Instrumentation inst) {
		// Crear/obtener GUI
		ProfilerGUI gui = TipoGUI.PROFILER.obtenerGUIPredeterminado(
				ProfilerGUIMinaly.ID,
				() -> new ProfilerGUIMinaly()
		);

		guiActiva = gui;

		// Mostrar/construir la GUI (según tu arquitectura, init() debe construir la ventana)
		gui.init();

		// Registrar transformer con versión ASM dinámica
		inst.addTransformer(new TransformadorProfiler(), true);
	}

	@Override
	public String id() {
		return ID;
	}

	/**
	 * Punto de entrada estático para el bytecode instrumentado.
	 *
	 * IMPORTANTE:
	 * - No debe depender de clases que puedan re-entrar en instrumentación (evitar bucles).
	 * - Debe ser lo más liviano posible.
	 */
	public static final class Hooks {

		private Hooks() {
		}

		/**
		 * Registra una medición de método ya calculada.
		 *
		 * @param claseInterna  nombre interno "a/b/C"
		 * @param metodo        nombre del método
		 * @param descriptor    descriptor JVM
		 * @param duracionNs    duración en nanosegundos
		 */
		public static void registrarLlamada(String claseInterna, String metodo, String descriptor, long duracionNs) {
			ProfilerGUI gui = guiActiva;
			if (gui == null) return;

			// Convertimos a nombre con puntos para presentación
			String clase = (claseInterna == null) ? "?" : claseInterna.replace('/', '.');

			gui.agregarLlamadaMetodo(clase, metodo, descriptor, duracionNs);
		}
	}

	/**
	 * Transformer que instrumenta cada método no abstracto y no nativo,
	 * midiendo tiempo y llamando a Hooks.registrarLlamada(...).
	 */
	static final class TransformadorProfiler implements ClassFileTransformer {

		@Override
		public byte[] transform(
				ClassLoader loader,
				String nombreClase,
				Class<?> claseRedefinida,
				ProtectionDomain dominioProteccion,
				byte[] bytecodeClase
		) throws IllegalClassFormatException {

			// Filtrado básico para evitar recursión/instrumentar el propio profiler
			if (nombreClase == null) return null;

			// Evitar JDK y nuestra infraestructura (ajusta prefijos si lo necesitas)
			if (nombreClase.startsWith("java/")
					|| nombreClase.startsWith("javax/")
					|| nombreClase.startsWith("sun/")
					|| nombreClase.startsWith("com/")
					|| nombreClase.startsWith("jdk/")
					|| nombreClase.startsWith("org/objectweb/asm/")
					|| nombreClase.startsWith("org/apache/logging/")
					|| nombreClase.startsWith("org/slf4j/")
					|| nombreClase.startsWith("cpw/mods/")

					|| nombreClase.startsWith("com/asbestosstar/crashdetector/")

					
					|| nombreClase.startsWith("com/asbestosstar/crashdetector/lanzer/servicio/CDProfiler")
					|| nombreClase.startsWith("com/asbestosstar/crashdetector/gui/")) {
				return null;
			}

			try {
				final int versionASM = AnalizadorBytecodeASM.obtenerVersionMaximaASM();

				ClassReader lector = new ClassReader(bytecodeClase);
				ClassWriter escritor = new ClassWriter(lector, ClassWriter.COMPUTE_FRAMES);

				ClassVisitor visitante = new VisitadorClaseProfiler(versionASM, escritor, nombreClase);
				lector.accept(visitante, ClassReader.EXPAND_FRAMES);

				return escritor.toByteArray();
			} catch (Throwable t) {
				// Si algo falla, no rompemos la carga de clases
				t.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Visitador de clase que envuelve los métodos para inyectar medición.
	 */
	static final class VisitadorClaseProfiler extends ClassVisitor {

		private final String nombreClaseInterno;

		VisitadorClaseProfiler(int api, ClassVisitor cv, String nombreClaseInterno) {
			super(api, cv);
			this.nombreClaseInterno = nombreClaseInterno;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
			MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

			// Ignorar métodos abstractos o nativos
			if ((access & Opcodes.ACC_ABSTRACT) != 0 || (access & Opcodes.ACC_NATIVE) != 0) {
				return mv;
			}

			// Evitar <clinit> para reducir riesgos (puede ejecutarse muy temprano)
			if ("<clinit>".equals(name)) {
				return mv;
			}

			return new VisitadorMetodoProfiler(api, mv, access, name, descriptor, nombreClaseInterno);
		}
	}

	/**
	 * Visitador de método que mide tiempo en entrada/salida y reporta al hook.
	 */
	static final class VisitadorMetodoProfiler extends AdviceAdapter {

		private final String nombreClaseInterno;
		private final String nombreMetodo;
		private final String descriptorMetodo;

		private int localInicioNs = -1;

		protected VisitadorMetodoProfiler(
				int api,
				MethodVisitor mv,
				int access,
				String name,
				String descriptor,
				String nombreClaseInterno
		) {
			super(api, mv, access, name, descriptor);
			this.nombreClaseInterno = nombreClaseInterno;
			this.nombreMetodo = name;
			this.descriptorMetodo = descriptor;
		}

		@Override
		protected void onMethodEnter() {
			// long inicio = System.nanoTime();
			localInicioNs = newLocal(Type.LONG_TYPE);
			invokeStatic(Type.getType(System.class), new org.objectweb.asm.commons.Method("nanoTime", "()J"));
			storeLocal(localInicioNs, Type.LONG_TYPE);
		}

		@Override
		protected void onMethodExit(int opcode) {
			// long dur = System.nanoTime() - inicio;
			invokeStatic(Type.getType(System.class), new org.objectweb.asm.commons.Method("nanoTime", "()J"));
			loadLocal(localInicioNs, Type.LONG_TYPE);
			math(SUB, Type.LONG_TYPE);

			// Llamar: CDProfiler.Hooks.registrarLlamada(clase, metodo, desc, dur)
			// Stack actual: [dur]
			// Necesitamos reordenar para pasar (String, String, String, long)
			int localDur = newLocal(Type.LONG_TYPE);
			storeLocal(localDur, Type.LONG_TYPE);

			visitLdcInsn(nombreClaseInterno);
			visitLdcInsn(nombreMetodo);
			visitLdcInsn(descriptorMetodo);
			loadLocal(localDur, Type.LONG_TYPE);

			invokeStatic(
					Type.getType(CDProfiler.Hooks.class),
					new org.objectweb.asm.commons.Method(
							"registrarLlamada",
							"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V"
					)
			);
		}
	}
}
