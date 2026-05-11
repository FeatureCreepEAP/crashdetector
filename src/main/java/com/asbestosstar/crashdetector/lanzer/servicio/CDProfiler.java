package com.asbestosstar.crashdetector.lanzer.servicio;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.asbestosstar.crashdetector.buscar.AnalizadorBytecodeASM;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.profiler.ProfilerGUI;
import com.asbestosstar.crashdetector.gui.tipos.profiler.ProfilerGUIMinaly;

/**
 * CDProfiler compatible con Java 8.
 *
 * Características: - Hooks de nivel superior para evitar NoClassDefFoundError.
 * - Transformer basado en AdviceAdapter (visitor) y ASM Tree (ClassNode). -
 * Denylist centralizada para evitar instrumentar clases críticas o del sistema.
 * - No se hardcodean nombres de clases externas. - Usa COMPUTE_MAXS para evitar
 * errores de clases no cargadas.
 */
public class CDProfiler implements ServicioCDLauncher {

	public static final String ID = "cdprofiler_wip";

	/**
	 * Lista centralizada de prefijos de clases a ignorar en la instrumentación. Se
	 * puede extender dinámicamente si se requiere.
	 */
	private static final List<String> DENYLIST_PREFIXES = Arrays.asList("java/", "javax/", "sun/", "com/sun/", "jdk/",
			"org/objectweb/asm/", "com/asbestosstar/crashdetector/", "com/google/", "org/apache/logging/", "org/lwjgl/",
			"org/antlr/", "org/apache/log4j/", "org/apache/commons/logging/", "org/slf4j/", "ch/qos/logback/",
			"cpw/mods/", "joptsimple/", "net/neoforged/", "net/minecraftforge/", "net/fabricmc/loader/",
			"org/spongepowered/asm/");

	@Override
	public void activar(Instrumentation inst) {
		System.out.println("No JPMS");
		activarGUI();

		// Registrar transformer basado en visitor (AdviceAdapter)
		inst.addTransformer(new TransformadorProfiler(), true);

		// Registrar transformer basado en ASM Tree (ClassNode)
		inst.addTransformer(new TransformadorClassNode(new AceptadorClassNodeNulo()), true);
	}

	/**
	 * Inicializa la GUI del profiler y la asigna en CDProfilerHooks para que el
	 * bytecode instrumentado pueda reportar tiempos.
	 */
	public static void activarGUI() {
		ProfilerGUI gui = TipoGUI.PROFILER.obtenerGUIPredeterminado(ProfilerGUIMinaly.ID,
				() -> new ProfilerGUIMinaly());

		CDProfilerHooks.guiActiva = gui;
		gui.init();
	}

	/**
	 * Comprueba si una clase debe ser ignorada según la denylist.
	 */
	private static boolean estaEnListaIgnorar(String nombreClase) {
		if (nombreClase == null)
			return true;
		return DENYLIST_PREFIXES.stream().anyMatch(nombreClase::startsWith);
	}

	/**
	 * Instrumenta un ClassNode usando ASM Tree API.
	 */
	public static void instrumentarClassNode(ClassNode cn) {
		if (CDProfilerHooks.guiActiva == null || cn.name == null)
			return;
		if (estaEnListaIgnorar(cn.name))
			return;

		for (MethodNode mn : cn.methods) {
			if ((mn.access & Opcodes.ACC_ABSTRACT) != 0 || (mn.access & Opcodes.ACC_NATIVE) != 0)
				continue;
			if ("<clinit>".equals(mn.name))
				continue;
			instrumentarMetodo(cn.name, mn);
		}
	}

	/**
	 * Instrumenta un método inyectando medición de tiempo al inicio y justo antes
	 * de cada retorno.
	 */
	private static void instrumentarMetodo(String claseInterna, MethodNode mn) {
		final String hooksInterno = Type.getInternalName(CDProfilerHooks.class);
		final String registrarDesc = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V";

		int localInicio = mn.maxLocals;
		mn.maxLocals += 2;
		int localDur = mn.maxLocals;
		mn.maxLocals += 2;

		// Bloque de inicio: timestamp al entrar al método
		org.objectweb.asm.tree.InsnList start = new org.objectweb.asm.tree.InsnList();
		start.add(new org.objectweb.asm.tree.MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime", "()J",
				false));
		start.add(new org.objectweb.asm.tree.VarInsnNode(Opcodes.LSTORE, localInicio));
		mn.instructions.insert(start);

		// Bloque de salida: medir duración antes de cada return
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

				hook.add(new org.objectweb.asm.tree.MethodInsnNode(Opcodes.INVOKESTATIC, hooksInterno,
						"registrarLlamada", registrarDesc, false));

				mn.instructions.insertBefore(insn, hook);
			}
		}
	}

	@Override
	public String id() {
		return ID;
	}

	// =========================
	// Transformer basado en visitor (AdviceAdapter)
	// =========================
	static final class TransformadorProfiler implements ClassFileTransformer {
		@Override
		public byte[] transform(ClassLoader loader, String nombreClase, Class<?> redef, ProtectionDomain domain,
				byte[] bytecode) throws IllegalClassFormatException {
			if (estaEnListaIgnorar(nombreClase))
				return null;

			try {
				final int asmVer = AnalizadorBytecodeASM.obtenerVersionMaximaASM();
				ClassReader reader = new ClassReader(bytecode);
				// Usar COMPUTE_MAXS para evitar errores de clases no cargadas
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				ClassVisitor visitor = new VisitadorClaseProfiler(asmVer, writer, nombreClase);
				reader.accept(visitor, ClassReader.SKIP_DEBUG);
				return writer.toByteArray();
			} catch (Throwable t) {
				// Solo imprimir advertencia sin stacktrace completo
				System.out.println("[CDProfiler] ⚠ No se pudo instrumentar la clase: " + nombreClase);
				return null;
			}
		}
	}

	static final class VisitadorClaseProfiler extends ClassVisitor {
		private final String nombreClaseInterno;

		VisitadorClaseProfiler(int api, ClassVisitor cv, String clase) {
			super(api, cv);
			this.nombreClaseInterno = clase;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String sig, String[] ex) {
			MethodVisitor mv = super.visitMethod(access, name, desc, sig, ex);
			if ((access & Opcodes.ACC_ABSTRACT) != 0 || (access & Opcodes.ACC_NATIVE) != 0)
				return mv;
			if ("<clinit>".equals(name))
				return mv;
			return new VisitadorMetodoProfiler(api, mv, access, name, desc, nombreClaseInterno);
		}
	}

	static final class VisitadorMetodoProfiler extends AdviceAdapter {
		private final String claseInterna, metodo, desc;
		private int localInicioNs = -1;

		protected VisitadorMetodoProfiler(int api, MethodVisitor mv, int access, String name, String desc,
				String clase) {
			super(api, mv, access, name, desc);
			this.claseInterna = clase;
			this.metodo = name;
			this.desc = desc;
		}

		@Override
		protected void onMethodEnter() {
			localInicioNs = newLocal(Type.LONG_TYPE);
			invokeStatic(Type.getType(System.class), new org.objectweb.asm.commons.Method("nanoTime", "()J"));
			storeLocal(localInicioNs, Type.LONG_TYPE);
		}

		@Override
		protected void onMethodExit(int opcode) {
			try {
				// código normal del profiler
				invokeStatic(Type.getType(System.class), new org.objectweb.asm.commons.Method("nanoTime", "()J"));
				loadLocal(localInicioNs, Type.LONG_TYPE);
				math(SUB, Type.LONG_TYPE);

				int localDur = newLocal(Type.LONG_TYPE);
				storeLocal(localDur, Type.LONG_TYPE);

				visitLdcInsn(claseInterna);
				visitLdcInsn(metodo);
				visitLdcInsn(desc);
				loadLocal(localDur, Type.LONG_TYPE);

				invokeStatic(Type.getObjectType(Type.getInternalName(CDProfilerHooks.class)),
						new org.objectweb.asm.commons.Method("registrarLlamada",
								"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V"));

			} catch (IllegalArgumentException | TypeNotPresentException e) {
				// Aviso simple, sin llenar log con stacktrace
				System.out.println("[CDProfiler] No se pudo adjuntar profiler a " + claseInterna + "." + metodo);
			}
		}

	}

	// =========================
	// Transformer basado en ASM Tree (ClassNode)
	// =========================
	public interface AceptadorClassNode {
		void aceptar(ClassNode cn);
	}

	public static final class AceptadorClassNodeNulo implements AceptadorClassNode {
		public void aceptar(ClassNode cn) {
		}
	}

	static final class TransformadorClassNode implements ClassFileTransformer {
		private final AceptadorClassNode aceptador;

		TransformadorClassNode(AceptadorClassNode ace) {
			this.aceptador = (ace == null) ? new AceptadorClassNodeNulo() : ace;
		}

		@Override
		public byte[] transform(ClassLoader loader, String nombreClase, Class<?> redef, ProtectionDomain domain,
				byte[] bytecode) throws IllegalClassFormatException {
			if (estaEnListaIgnorar(nombreClase))
				return null;
			try {
				final int asmVer = AnalizadorBytecodeASM.obtenerVersionMaximaASM();
				ClassReader cr = new ClassReader(bytecode);
				ClassNode cn = new ClassNode(asmVer);
				cr.accept(cn, ClassReader.SKIP_DEBUG);
				aceptador.aceptar(cn);
				ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				cn.accept(cw);
				return cw.toByteArray();
			} catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
		}
	}
}