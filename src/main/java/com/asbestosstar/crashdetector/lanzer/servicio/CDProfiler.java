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
import org.objectweb.asm.tree.ClassNode;

import com.asbestosstar.crashdetector.buscar.AnalizadorBytecodeASM;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.profiler.ProfilerGUI;
import com.asbestosstar.crashdetector.gui.tipos.profiler.ProfilerGUIMinaly;

/**
 * Servicio de profiler WIP.
 *
 * - Abre la GUI del profiler - Registra transformers: - Uno basado en visitors
 * (AdviceAdapter) que instrumenta métodos (no abstractos / no nativos) - Uno
 * basado en ClassNode (ASM Tree) que "solo acepta" el ClassNode (para
 * modificaciones externas) - Mide duración con System.nanoTime() - Alimenta la
 * GUI con (clase, método, descriptor, duración)
 */
public class CDProfiler implements ServicioCDLauncher {

	public static String ID = "cdprofiler_wip";

	/**
	 * Referencia global a la GUI activa para alimentar eventos desde bytecode
	 * instrumentado
	 */
	private static volatile ProfilerGUI guiActiva;

	@Override
	public void activar(Instrumentation inst) {
		System.out.println("No JPMS");
		activarGUI();

		// Registrar transformer con versión ASM dinámica (visitor-based)
		inst.addTransformer(new TransformadorProfiler(), true);

		// Registrar transformer "Tree API" (ClassNode) que solo expone el ClassNode a
		// un callback
		// (Útil para WIP, pruebas, o instrumentaciones que prefieren ASM Tree)
		inst.addTransformer(new TransformadorClassNode(new AceptadorClassNodeNulo()), true);
	}

	public static void activarGUI() {
		// TODO Auto-generated method stub
		// Crear/obtener GUI
		ProfilerGUI gui = TipoGUI.PROFILER.obtenerGUIPredeterminado(ProfilerGUIMinaly.ID,
				() -> new ProfilerGUIMinaly());

		guiActiva = gui;

		// Mostrar/construir la GUI (según tu arquitectura, init() debe construir la
		// ventana)
		gui.init();
	}

	/**
	 * Instrumenta un ClassNode usando ASM Tree API. Este método puede ser llamado
	 * desde ModLauncher transformer.
	 */
	public static void instrumentarClassNode(ClassNode cn) {

		if (guiActiva == null) {
			return;
		}

		// Evitar instrumentar el propio profiler o infra
		String nombreClase = cn.name;

		System.out.println("Nombre Clase " + nombreClase);

		if (nombreClase == null)
			return;

		if (nombreClase.startsWith("java/") || nombreClase.startsWith("javax/") || nombreClase.startsWith("sun/")
				|| nombreClase.startsWith("jdk/") || nombreClase.startsWith("org/objectweb/asm/")
				|| nombreClase.startsWith("com/asbestosstar/crashdetector/lanzer/servicio/CDProfiler")
				|| nombreClase.startsWith("com/asbestosstar/crashdetector/gui/")) {
			return;
		}

		for (org.objectweb.asm.tree.MethodNode mn : cn.methods) {

			// Ignorar abstractos y nativos
			if ((mn.access & Opcodes.ACC_ABSTRACT) != 0 || (mn.access & Opcodes.ACC_NATIVE) != 0) {
				continue;
			}

			// Ignorar clinit
			if ("<clinit>".equals(mn.name)) {
				continue;
			}

			instrumentarMetodo(cn.name, mn);
		}
	}

	private static void instrumentarMetodo(String claseInterna, org.objectweb.asm.tree.MethodNode mn) {

		org.objectweb.asm.tree.InsnList inicio = new org.objectweb.asm.tree.InsnList();
		org.objectweb.asm.tree.InsnList fin = new org.objectweb.asm.tree.InsnList();

		int localInicio = mn.maxLocals;
		mn.maxLocals += 2;

		int localDur = mn.maxLocals;
		mn.maxLocals += 2;

		// inicio = System.nanoTime()

		inicio.add(new org.objectweb.asm.tree.MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime",
				"()J", false));

		inicio.add(new org.objectweb.asm.tree.VarInsnNode(Opcodes.LSTORE, localInicio));

		mn.instructions.insert(inicio);

		// antes de cada return

		for (org.objectweb.asm.tree.AbstractInsnNode insn : mn.instructions.toArray()) {

			int opcode = insn.getOpcode();

			if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) {

				org.objectweb.asm.tree.InsnList hook = new org.objectweb.asm.tree.InsnList();

				hook.add(new org.objectweb.asm.tree.MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime",
						"()J", false));

				hook.add(new org.objectweb.asm.tree.VarInsnNode(Opcodes.LLOAD, localInicio));

				hook.add(new org.objectweb.asm.tree.InsnNode(Opcodes.LSUB));

				hook.add(new org.objectweb.asm.tree.VarInsnNode(Opcodes.LSTORE, localDur));

				hook.add(new org.objectweb.asm.tree.LdcInsnNode(claseInterna));
				hook.add(new org.objectweb.asm.tree.LdcInsnNode(mn.name));
				hook.add(new org.objectweb.asm.tree.LdcInsnNode(mn.desc));

				hook.add(new org.objectweb.asm.tree.VarInsnNode(Opcodes.LLOAD, localDur));

				hook.add(new org.objectweb.asm.tree.MethodInsnNode(Opcodes.INVOKESTATIC,
						Type.getInternalName(CDProfiler.Hooks.class), "registrarLlamada",
						"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", false));

				mn.instructions.insertBefore(insn, hook);
			}
		}
	}

	@Override
	public String id() {
		return ID;
	}

	/**
	 * Punto de entrada estático para el bytecode instrumentado.
	 *
	 * IMPORTANTE: - No debe depender de clases que puedan re-entrar en
	 * instrumentación (evitar bucles). - Debe ser lo más liviano posible.
	 */
	public static final class Hooks {

		private Hooks() {
		}

		/**
		 * Registra una medición de método ya calculada.
		 *
		 * @param claseInterna nombre interno "a/b/C"
		 * @param metodo       nombre del método
		 * @param descriptor   descriptor JVM
		 * @param duracionNs   duración en nanosegundos
		 */
		public static void registrarLlamada(String claseInterna, String metodo, String descriptor, long duracionNs) {
			ProfilerGUI gui = guiActiva;
			if (gui == null)
				return;

			// Convertimos a nombre con puntos para presentación
			String clase = (claseInterna == null) ? "?" : claseInterna.replace('/', '.');

			gui.agregarLlamadaMetodo(clase, metodo, descriptor, duracionNs);
		}
	}

	/**
	 * Transformer que instrumenta cada método no abstracto y no nativo, midiendo
	 * tiempo y llamando a Hooks.registrarLlamada(...).
	 */
	static final class TransformadorProfiler implements ClassFileTransformer {

		@Override
		public byte[] transform(ClassLoader loader, String nombreClase, Class<?> claseRedefinida,
				ProtectionDomain dominioProteccion, byte[] bytecodeClase) throws IllegalClassFormatException {

			// Filtrado básico para evitar recursión/instrumentar el propio profiler
			if (nombreClase == null)
				return null;

			// Evitar JDK y nuestra infraestructura (ajusta prefijos si lo necesitas)
			if (nombreClase.startsWith("java/") || nombreClase.startsWith("javax/") || nombreClase.startsWith("sun/")
					|| nombreClase.startsWith("jdk/") || nombreClase.startsWith("org/objectweb/asm/")
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
		public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
				String[] exceptions) {
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

		protected VisitadorMetodoProfiler(int api, MethodVisitor mv, int access, String name, String descriptor,
				String nombreClaseInterno) {
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

			invokeStatic(Type.getType(CDProfiler.Hooks.class), new org.objectweb.asm.commons.Method("registrarLlamada",
					"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V"));
		}
	}

	// =====================================================================
	// TRANSFORMER ADICIONAL: ASM TREE (ClassNode) "solo acepta el ClassNode"
	// =====================================================================

	/**
	 * Interfaz mínima para "aceptar" un ClassNode. La idea es que puedas enchufar
	 * aquí cualquier lógica basada en ASM Tree.
	 */
	public interface AceptadorClassNode {
		/**
		 * Recibe el ClassNode ya parseado. Puedes mutarlo (añadir
		 * métodos/campos/anotaciones, etc.).
		 *
		 * IMPORTANTE: - Mantenerlo liviano para no impactar carga de clases. - Evitar
		 * tocar clases que disparen instrumentación recursiva.
		 */
		void aceptar(ClassNode cn);
	}

	/**
	 * Aceptador por defecto que no hace nada. Útil para tener el transformer
	 * registrado sin modificar comportamiento.
	 */
	public static final class AceptadorClassNodeNulo implements AceptadorClassNode {
		@Override
		public void aceptar(ClassNode cn) {
			// No-op intencional
		}
	}

	/**
	 * Transformer basado en ASM Tree:
	 *
	 * - Lee bytecode -> ClassNode - Llama a un "aceptador" que puede modificar el
	 * ClassNode - Escribe ClassNode -> bytecode
	 *
	 * Si el aceptador no cambia nada, el resultado será equivalente (salvo
	 * frames/re-escritura).
	 */
	static final class TransformadorClassNode implements ClassFileTransformer {

		private final AceptadorClassNode aceptador;

		TransformadorClassNode(AceptadorClassNode aceptador) {
			this.aceptador = (aceptador == null) ? new AceptadorClassNodeNulo() : aceptador;
		}

		@Override
		public byte[] transform(ClassLoader loader, String nombreClase, Class<?> claseRedefinida,
				ProtectionDomain dominioProteccion, byte[] bytecodeClase) throws IllegalClassFormatException {

			if (nombreClase == null)
				return null;

			// Reutilizamos el mismo filtrado básico para evitar recursión
			if (nombreClase.startsWith("java/") || nombreClase.startsWith("javax/") || nombreClase.startsWith("sun/")
					|| nombreClase.startsWith("jdk/") || nombreClase.startsWith("org/objectweb/asm/")
					|| nombreClase.startsWith("com/asbestosstar/crashdetector/lanzer/servicio/CDProfiler")
					|| nombreClase.startsWith("com/asbestosstar/crashdetector/gui/")) {
				return null;
			}

			try {
				final int versionASM = AnalizadorBytecodeASM.obtenerVersionMaximaASM();

				// 1) Parsear a ClassNode
				ClassReader cr = new ClassReader(bytecodeClase);
				ClassNode cn = new ClassNode(versionASM);
				cr.accept(cn, ClassReader.EXPAND_FRAMES);

				// 2) "Aceptar" el ClassNode (mutación opcional)
				aceptador.aceptar(cn);

				// 3) Volver a escribir a bytecode
				ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
				cn.accept(cw);

				return cw.toByteArray();
			} catch (Throwable t) {
				// Si algo falla, no rompemos la carga de clases
				t.printStackTrace();
				return null;
			}
		}
	}
}
