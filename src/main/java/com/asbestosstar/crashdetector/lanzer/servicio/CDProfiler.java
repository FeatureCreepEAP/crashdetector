package com.asbestosstar.crashdetector.lanzer.servicio;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
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
 *
 * En JPMS/Layers, NO se debe invocar directamente a clases del agente desde clases instrumentadas.
 * Por eso el bytecode inyectado usa reflexión por nombre y cachea el Method por clase instrumentada.
 */
public class CDProfiler implements ServicioCDLauncher {

	public static String ID = "cdprofiler_wip";

	/** Referencia global a la GUI activa para alimentar eventos desde bytecode instrumentado */
	private static volatile ProfilerGUI guiActiva;

	@Override
	public void activar(Instrumentation inst) {
		ProfilerGUI gui = TipoGUI.PROFILER.obtenerGUIPredeterminado(
				ProfilerGUIMinaly.ID,
				() -> new ProfilerGUIMinaly()
		);

		guiActiva = gui;
		gui.init();

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
	 * - Esta clase NO debe ser referenciada directamente por bytecode instrumentado en entornos JPMS.
	 * - El bytecode instrumentado la localizará por reflexión usando Class.forName con el SystemClassLoader.
	 */
	public static final class Hooks {

		private Hooks() {
		}

		/**
		 * Registra una medición de método ya calculada.
		 */
		public static void registrarLlamada(String claseInterna, String metodo, String descriptor, long duracionNs) {
			ProfilerGUI gui = guiActiva;
			if (gui == null) return;

			String clase = (claseInterna == null) ? "?" : claseInterna.replace('/', '.');
			gui.agregarLlamadaMetodo(clase, metodo, descriptor, duracionNs);
		}
	}

	/**
	 * Transformer que instrumenta cada método no abstracto y no nativo,
	 * midiendo tiempo y reportando al hook vía reflexión (sin enlace directo a Hooks).
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

			if (nombreClase == null) return null;

			if (nombreClase.startsWith("java/")
					|| nombreClase.startsWith("javax/")
					|| nombreClase.startsWith("sun/")
					|| nombreClase.startsWith("jdk/")
					|| nombreClase.startsWith("org/objectweb/asm/")
					|| nombreClase.startsWith("com/asbestosstar/crashdetector/lanzer/servicio/CDProfiler")
					|| nombreClase.startsWith("com/asbestosstar/crashdetector/gui/")) {
				return null;
			}

			try {
				final int versionASM = AnalizadorBytecodeASM.obtenerVersionMaximaASM();

				ClassReader lector = new ClassReader(bytecodeClase);
				ClassWriter escritor = new ClassWriter(lector, ClassWriter.COMPUTE_FRAMES);

				ClassVisitor visitante = new VisitadorClaseProfiler(versionASM, escritor, nombreClase, lector.getAccess());
				lector.accept(visitante, ClassReader.EXPAND_FRAMES);

				return escritor.toByteArray();
			} catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Visitador de clase:
	 * - Envuelve métodos para inyectar medición.
	 * - Añade campos estáticos para cachear el Method reflejado (por clase instrumentada).
	 */
	static final class VisitadorClaseProfiler extends ClassVisitor {

		private final String nombreClaseInterno;

		// Campos sintéticos añadidos a la clase instrumentada para cachear reflexión
		private static final String CAMPO_METHOD = "__cdprof_m_registrar";
		private static final String CAMPO_INTENTADO = "__cdprof_m_intentado";
		private final boolean esInterface;
		
		VisitadorClaseProfiler(int api, ClassVisitor cv, String nombreClaseInterno, int access) {
			super(api, cv);
			this.nombreClaseInterno = nombreClaseInterno;
			this.esInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
			MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

			if ((access & Opcodes.ACC_ABSTRACT) != 0 || (access & Opcodes.ACC_NATIVE) != 0) {
				return mv;
			}

			if ("<clinit>".equals(name)) {
				return mv;
			}

			return new VisitadorMetodoProfiler(
					api, mv, access, name, descriptor, nombreClaseInterno,
					CAMPO_METHOD, CAMPO_INTENTADO
			);
		}

		@Override
		public void visitEnd() {
			if (esInterface) {
				super.visitEnd();
				return;
			}

			// SOLO para clases concretas
			FieldVisitor fv1 = super.visitField(
					Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_VOLATILE | Opcodes.ACC_SYNTHETIC,
					CAMPO_METHOD,
					"Ljava/lang/reflect/Method;",
					null,
					null
			);
			if (fv1 != null) fv1.visitEnd();

			FieldVisitor fv2 = super.visitField(
					Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_VOLATILE | Opcodes.ACC_SYNTHETIC,
					CAMPO_INTENTADO,
					"Z",
					null,
					null
			);
			if (fv2 != null) fv2.visitEnd();

			super.visitEnd();
		}

	}

	/**
	 * Visitador de método:
	 * - mide tiempo
	 * - en salida: resuelve e invoca el hook por reflexión SIN referenciar CDProfiler$Hooks como tipo
	 * - cachea Method en campo estático de la clase instrumentada
	 */
	static final class VisitadorMetodoProfiler extends AdviceAdapter {

		private final String nombreClaseInterno;
		private final String nombreMetodo;
		private final String descriptorMetodo;

		private final String campoMethod;
		private final String campoIntentado;

		
		// === locals reservados UNA SOLA VEZ ===
		private int localDur;
		private int localRet;
		private int localMethod;
		private int localHookClass;

		
		
		private int localInicioNs = -1;

		protected VisitadorMetodoProfiler(
				int api,
				MethodVisitor mv,
				int access,
				String name,
				String descriptor,
				String nombreClaseInterno,
				String campoMethod,
				String campoIntentado
		) {
			super(api, mv, access, name, descriptor);
			this.nombreClaseInterno = nombreClaseInterno;
			this.nombreMetodo = name;
			this.descriptorMetodo = descriptor;
			this.campoMethod = campoMethod;
			this.campoIntentado = campoIntentado;
		}

		@Override
		protected void onMethodEnter() {

			// =========================================================
			// RESERVA DE LOCALES (OBLIGATORIO AQUÍ, NUNCA EN onMethodExit)
			// =========================================================

			// long inicioNs
			localInicioNs = newLocal(Type.LONG_TYPE);

			// long duracion
			localDur = newLocal(Type.LONG_TYPE);

			// valor de retorno (solo si no es void)
			Type tipoRetorno = Type.getReturnType(methodDesc);
			if (tipoRetorno != Type.VOID_TYPE) {
				localRet = newLocal(tipoRetorno);
			} else {
				localRet = -1;
			}

			// java.lang.reflect.Method cacheado
			localMethod = newLocal(Type.getType("Ljava/lang/reflect/Method;"));

			// Class<?> del hook
			localHookClass = newLocal(Type.getType(Class.class));

			// =========================================================
			// CAPTURA DEL TIEMPO DE INICIO
			// =========================================================
			invokeStatic(
					Type.getType(System.class),
					new org.objectweb.asm.commons.Method("nanoTime", "()J")
			);
			storeLocal(localInicioNs);
		}


@Override
protected void onMethodExit(int opcode) {

	// =========================================================
	// IGNORAR EXCEPCIONES (ATHROW)
	// =========================================================
	// Forge y otros loaders generan muchos ATHROW internos.
	// Instrumentarlos rompe frames y no aporta valor.
	if (opcode == ATHROW) {
		return;
	}

	// =========================================================
	// PRESERVAR VALOR DE RETORNO
	// =========================================================
	// AdviceAdapter llega aquí con el retorno en la pila.
	// Lo guardamos para dejar la pila limpia.
	if (opcode != RETURN && localRet != -1) {
		storeLocal(localRet);
	}

	// =========================================================
	// CALCULAR DURACIÓN
	// =========================================================
	invokeStatic(
			Type.getType(System.class),
			new org.objectweb.asm.commons.Method("nanoTime", "()J")
	);
	loadLocal(localInicioNs);
	math(SUB, Type.LONG_TYPE);
	storeLocal(localDur);

	// =========================================================
	// BLOQUE TRY/CATCH PARA LA REFLEXIÓN
	// =========================================================
	Label L_tryStart = new Label();
	Label L_tryEnd = new Label();
	Label L_catch = new Label();
	Label L_after = new Label();

	visitTryCatchBlock(L_tryStart, L_tryEnd, L_catch, "java/lang/Throwable");
	visitLabel(L_tryStart);

	String owner = nombreClaseInterno;

	// =========================================================
	// Method m = __cdprof_m_registrar;
	// =========================================================
	visitFieldInsn(GETSTATIC, owner, campoMethod, "Ljava/lang/reflect/Method;");
	storeLocal(localMethod);

	Label L_tieneMethod = new Label();
	loadLocal(localMethod);
	ifNonNull(L_tieneMethod);

	// =========================================================
	// if (__cdprof_m_intentado) salir
	// =========================================================
	visitFieldInsn(GETSTATIC, owner, campoIntentado, "Z");
	Label L_init = new Label();
	ifZCmp(EQ, L_init);
	goTo(L_tryEnd);

	visitLabel(L_init);

	// __cdprof_m_intentado = true
	push(true);
	visitFieldInsn(PUTSTATIC, owner, campoIntentado, "Z");

	// =========================================================
	// Class.forName("...CDProfiler$Hooks", true, SystemClassLoader)
	// =========================================================
	visitLdcInsn("com.asbestosstar.crashdetector.lanzer.servicio.CDProfiler$Hooks");
	push(true);
	invokeStatic(
			Type.getType(ClassLoader.class),
			new org.objectweb.asm.commons.Method(
					"getSystemClassLoader",
					"()Ljava/lang/ClassLoader;"
			)
	);
	invokeStatic(
			Type.getType(Class.class),
			new org.objectweb.asm.commons.Method(
					"forName",
					"(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;"
			)
	);
	storeLocal(localHookClass);

	// =========================================================
	// m = c.getMethod("registrarLlamada", String,String,String,long)
	// =========================================================
	loadLocal(localHookClass);
	visitLdcInsn("registrarLlamada");

	push(4);
	newArray(Type.getType(Class.class));

	dup(); push(0); visitLdcInsn(Type.getType(String.class)); arrayStore(Type.getType(Class.class));
	dup(); push(1); visitLdcInsn(Type.getType(String.class)); arrayStore(Type.getType(Class.class));
	dup(); push(2); visitLdcInsn(Type.getType(String.class)); arrayStore(Type.getType(Class.class));
	dup(); push(3); visitFieldInsn(GETSTATIC, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
	arrayStore(Type.getType(Class.class));

	invokeVirtual(
			Type.getType(Class.class),
			new org.objectweb.asm.commons.Method(
					"getMethod",
					"(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"
			)
	);

	// cachear Method
	dup();
	visitFieldInsn(PUTSTATIC, owner, campoMethod, "Ljava/lang/reflect/Method;");
	storeLocal(localMethod);

	visitLabel(L_tieneMethod);

	// =========================================================
	// SI SIGUE NULL, SALIR
	// =========================================================
	loadLocal(localMethod);
	Label L_invocar = new Label();
	ifNonNull(L_invocar);
	goTo(L_tryEnd);

	visitLabel(L_invocar);

	// =========================================================
	// m.invoke(null, new Object[]{clase, metodo, desc, Long.valueOf(dur)})
	// =========================================================
	loadLocal(localMethod);
	visitInsn(ACONST_NULL);

	push(4);
	newArray(Type.getType(Object.class));

	dup(); push(0); visitLdcInsn(nombreClaseInterno); arrayStore(Type.getType(Object.class));
	dup(); push(1); visitLdcInsn(nombreMetodo); arrayStore(Type.getType(Object.class));
	dup(); push(2); visitLdcInsn(descriptorMetodo); arrayStore(Type.getType(Object.class));
	dup(); push(3);
	loadLocal(localDur);
	invokeStatic(
			Type.getType(Long.class),
			new org.objectweb.asm.commons.Method("valueOf", "(J)Ljava/lang/Long;")
	);
	arrayStore(Type.getType(Object.class));

	invokeVirtual(
			Type.getType(java.lang.reflect.Method.class),
			new org.objectweb.asm.commons.Method(
					"invoke",
					"(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"
			)
	);

	// descartar retorno
	pop();

	visitLabel(L_tryEnd);
	goTo(L_after);

	visitLabel(L_catch);
	// Consumir Throwable y continuar silenciosamente
	pop();

	visitLabel(L_after);

	// =========================================================
	// RESTAURAR VALOR DE RETORNO ORIGINAL
	// =========================================================
	if (opcode != RETURN && localRet != -1) {
		loadLocal(localRet);
	}
}


	}
}
