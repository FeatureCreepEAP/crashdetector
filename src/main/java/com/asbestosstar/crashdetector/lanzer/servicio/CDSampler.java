package com.asbestosstar.crashdetector.lanzer.servicio;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.asbestosstar.crashdetector.buscar.AnalizadorBytecodeASM;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.sampler.SamplerGUI;
import com.asbestosstar.crashdetector.gui.tipos.sampler.SamplerGUIEineLotta;

/**
 * Sampler basado en ASM.
 *
 * Instrumenta métodos de forma ligera y acumula tiempo para detectar qué
 * métodos consumen más tiempo total.
 */
public class CDSampler implements ServicioCDLauncher {

	public static final String ID = "cdsampler_wip";

	private static volatile SamplerGUI guiActiva;

	/** Tiempo acumulado por método */
	private static final Map<String, AtomicLong> tiempos = new ConcurrentHashMap<>();

	/** Control del sampler */
	private static volatile boolean samplerActivo = false;

	@Override
	public void activar(Instrumentation inst) {

		iniciarGUI();
		inst.addTransformer(new TransformadorSampler(), true);

	}

	public static void iniciarGUI() {
		SamplerGUI gui = TipoGUI.SAMPLER.obtenerGUIPredeterminado(SamplerGUIEineLotta.ID,
				() -> new SamplerGUIEineLotta());

		guiActiva = gui;
		gui.init();

		samplerActivo = true;

		iniciarPublicador();

	}

	/**
	 * Instrumenta un ClassNode usando ASM Tree API para el sampler.
	 *
	 * Compatible con ModLauncher y entornos sin Instrumentation.
	 */
	public static void instrumentarClassNode(ClassNode cn) {

		if (!samplerActivo)
			return;

		if (cn == null || cn.name == null)
			return;

		String nombreClase = cn.name;
		System.out.println("Nombre Clase " + nombreClase);

		// Filtrado básico
		if (nombreClase.startsWith("java/") || nombreClase.startsWith("javax/") || nombreClase.startsWith("sun/")
				|| nombreClase.startsWith("jdk/") || nombreClase.startsWith("org/objectweb/asm/")
				|| nombreClase.startsWith("com/asbestosstar/crashdetector/gui/")) {
			return;
		}

		String claseDot = nombreClase.replace('/', '.');

		for (MethodNode mn : cn.methods) {

			if ((mn.access & Opcodes.ACC_ABSTRACT) != 0 || (mn.access & Opcodes.ACC_NATIVE) != 0
					|| "<clinit>".equals(mn.name)) {
				continue;
			}

			instrumentarMetodoSampler(cn, mn, claseDot);
		}
	}

	private static void instrumentarMetodoSampler(ClassNode cn, MethodNode mn, String claseDot) {

		String idMetodo = claseDot + "." + mn.name + mn.desc;

		int localInicio = mn.maxLocals;
		mn.maxLocals += 2;

		InsnList inicio = new InsnList();

		// long inicio = System.nanoTime()

		inicio.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false));

		inicio.add(new VarInsnNode(Opcodes.LSTORE, localInicio));

		mn.instructions.insert(inicio);

		// Instrumentar todos los returns

		for (AbstractInsnNode insn : mn.instructions.toArray()) {

			int opcode = insn.getOpcode();

			if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) {

				InsnList hook = new InsnList();

				// long dur = System.nanoTime() - inicio

				hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false));

				hook.add(new VarInsnNode(Opcodes.LLOAD, localInicio));

				hook.add(new InsnNode(Opcodes.LSUB));

				// push idMetodo

				hook.add(new LdcInsnNode(idMetodo));

				// swap to match (String, long)

				hook.add(new InsnNode(Opcodes.DUP_X2));
				hook.add(new InsnNode(Opcodes.POP));

				// Hooks.acumularTiempo(String, long)

				hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "acumularTiempo",
						"(Ljava/lang/String;J)V", false));

				mn.instructions.insertBefore(insn, hook);
			}
		}
	}

	@Override
	public String id() {
		return ID;
	}

	/*
	 * ========================================================== Publicador
	 * periódico hacia la GUI
	 * ==========================================================
	 */

	private static void iniciarPublicador() {
		Thread t = new Thread(() -> {
			while (samplerActivo) {
				try {
					Thread.sleep(500);

					SamplerGUI gui = guiActiva;
					if (gui == null || !gui.estaSamplerActivo())
						continue;

					for (Map.Entry<String, AtomicLong> e : tiempos.entrySet()) {
						long ns = e.getValue().getAndSet(0);
						if (ns > 0) {
							gui.agregarMuestra(e.getKey(), new StackTraceElement[0]);
						}
					}
				} catch (InterruptedException ignored) {
					return;
				}
			}
		}, "CDSampler-Publicador");

		t.setDaemon(true);
		t.start();
	}

	/*
	 * ========================================================== Hook estático
	 * llamado desde bytecode
	 * ==========================================================
	 */

	public static final class Hooks {

		private Hooks() {
		}

		public static void acumularTiempo(String metodo, long duracionNs) {
			if (!samplerActivo)
				return;

			tiempos.computeIfAbsent(metodo, k -> new AtomicLong()).addAndGet(duracionNs);
		}
	}

	/*
	 * ========================================================== Transformer ASM
	 * ==========================================================
	 */

	static final class TransformadorSampler implements ClassFileTransformer {

		@Override
		public byte[] transform(ClassLoader loader, String nombreClase, Class<?> claseRedefinida,
				ProtectionDomain dominio, byte[] bytecode) throws IllegalClassFormatException {

			if (nombreClase == null || nombreClase.startsWith("java/") || nombreClase.startsWith("javax/")
					|| nombreClase.startsWith("sun/") || nombreClase.startsWith("jdk/")
					|| nombreClase.startsWith("org/objectweb/asm/")
					|| nombreClase.startsWith("com/asbestosstar/crashdetector/gui/")) {
				return null;
			}

			try {
				int asm = AnalizadorBytecodeASM.obtenerVersionMaximaASM();

				ClassReader cr = new ClassReader(bytecode);
				ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);

				ClassVisitor cv = new VisitadorClaseSampler(asm, cw, nombreClase);
				cr.accept(cv, ClassReader.EXPAND_FRAMES);

				return cw.toByteArray();
			} catch (Throwable t) {
				return null;
			}
		}
	}

	static final class VisitadorClaseSampler extends ClassVisitor {

		private final String clase;

		VisitadorClaseSampler(int api, ClassVisitor cv, String clase) {
			super(api, cv);
			this.clase = clase.replace('/', '.');
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String sig, String[] ex) {
			MethodVisitor mv = super.visitMethod(access, name, desc, sig, ex);

			if ((access & Opcodes.ACC_ABSTRACT) != 0 || (access & Opcodes.ACC_NATIVE) != 0 || "<clinit>".equals(name)) {
				return mv;
			}

			return new VisitadorMetodoSampler(api, mv, access, clase + "." + name + desc);
		}
	}

	static final class VisitadorMetodoSampler extends AdviceAdapter {

		private final String idMetodo;
		private int inicioNs;

		protected VisitadorMetodoSampler(int api, MethodVisitor mv, int access, String idMetodo) {
			super(api, mv, access, "<init>", "()V");
			this.idMetodo = idMetodo;
		}

		@Override
		protected void onMethodEnter() {
			inicioNs = newLocal(Type.LONG_TYPE);
			invokeStatic(Type.getType(System.class), new Method("nanoTime", "()J"));
			storeLocal(inicioNs);
		}

		@Override
		protected void onMethodExit(int opcode) {
			invokeStatic(Type.getType(System.class), new Method("nanoTime", "()J"));
			loadLocal(inicioNs);
			math(SUB, Type.LONG_TYPE);

			visitLdcInsn(idMetodo);
			swap();
			invokeStatic(Type.getType(Hooks.class), new Method("acumularTiempo", "(Ljava/lang/String;J)V"));
		}
	}
}
