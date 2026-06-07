package com.asbestosstar.crashdetector.lanzer.servicio;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.*;

import com.asbestosstar.crashdetector.buscar.AnalizadorBytecodeASM;

/**
 * Servicio que instrumenta el bytecode para imprimir por consola cada entrada a
 * método (no abstracto y no nativo).
 */
public class CDTracer implements ServicioCDLauncher {

	public static String ID = "cdtracer";

	/**
	 * Activa el tracer registrando un transformer en la Instrumentation.
	 */
	@Override
	public void activar(Instrumentation inst) {
		inst.addTransformer(new TransformadorTrazasMetodos(), true);
	}

	/**
	 * Identificador del servicio.
	 */
	@Override
	public String id() {
		return ID;
	}

	/**
	 * Transformer que modifica las clases cargadas por la JVM.
	 */
	static class TransformadorTrazasMetodos implements ClassFileTransformer {

		@Override
		public byte[] transform(ClassLoader loader, String nombreClase, Class<?> claseRedefinida,
				ProtectionDomain dominioProteccion, byte[] bytecodeClase) throws IllegalClassFormatException {

			// Ignorar clases del JDK para evitar recursión infinita
			if (nombreClase == null || nombreClase.startsWith("java/")

					|| nombreClase.startsWith("org/apache/logging/log4j/") || nombreClase.startsWith("org/slf4j/")
					|| nombreClase.startsWith("ch/qos/logback/") || nombreClase.startsWith("sun/")
					|| nombreClase.startsWith("com/sun/")
					|| nombreClase.startsWith("net/minecraftforge/fml/loading/TracingPrintStream")
					|| nombreClase.startsWith("com/google/") || nombreClase.startsWith("com/electronwill/nightconfig/")
					|| nombreClase.startsWith("javax/") || nombreClase.startsWith("jdk/")

					|| nombreClase.startsWith("org/lwjgl/") || nombreClase.startsWith("org/antlr/")

					|| nombreClase.startsWith("org/apache/commons/lang3/")
					|| nombreClase.startsWith("cpw/mods/modlauncher/Transform")

			)

			{
				return null;
			}

			try {
				int versionASM = AnalizadorBytecodeASM.obtenerVersionMaximaASM();

				ClassReader lector = new ClassReader(bytecodeClase);
				ClassWriter escritor = new ClassWriter(lector, ClassWriter.COMPUTE_FRAMES);

				ClassVisitor visitante = new VisitadorClaseTrazas(versionASM, escritor, nombreClase);

				lector.accept(visitante, ClassReader.EXPAND_FRAMES);
				return escritor.toByteArray();

			} catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Visitador de clases que envuelve los métodos.
	 */
	static class VisitadorClaseTrazas extends ClassVisitor {

		private final String nombreClase;

		VisitadorClaseTrazas(int api, ClassVisitor cv, String nombreClase) {
			super(api, cv);
			this.nombreClase = nombreClase.replace('/', '.');
		}

		@Override
		public MethodVisitor visitMethod(int access, String nombreMetodo, String descriptor, String signature,
				String[] exceptions) {
			MethodVisitor mv = super.visitMethod(access, nombreMetodo, descriptor, signature, exceptions);

			// Ignorar métodos abstractos o nativos
			if ((access & Opcodes.ACC_ABSTRACT) != 0 || (access & Opcodes.ACC_NATIVE) != 0) {
				return mv;
			}

			return new VisitadorMetodoTraza(api, mv, nombreClase, nombreMetodo, descriptor);
		}
	}

	/**
	 * Visitador de métodos que inyecta el println al inicio del método.
	 */
	static class VisitadorMetodoTraza extends MethodVisitor {

		private final String nombreClase;
		private final String nombreMetodo;
		private final String descriptorMetodo;

		VisitadorMetodoTraza(int api, MethodVisitor mv, String nombreClase, String nombreMetodo,
				String descriptorMetodo) {
			super(api, mv);
			this.nombreClase = nombreClase;
			this.nombreMetodo = nombreMetodo;
			this.descriptorMetodo = descriptorMetodo;
		}

		/**
		 * Se ejecuta al comienzo del método. Inserta:
		 * System.out.println("paquete.Clase.metodo(descriptor)");
		 */
		@Override
		public void visitCode() {
			super.visitCode();

			// System.out
			mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

			// Texto con ruta completa + descriptor JVM
			mv.visitLdcInsn(nombreClase + "." + nombreMetodo + descriptorMetodo);

			// println(String)
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
		}
	}
}
