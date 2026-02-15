package com.asbestosstar.crashdetector.lanzer.servicio;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
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
 * - Reporta por reflexión a CDProfiler$Hooks si existe
 *
 * IMPORTANTE:
 * - En entornos Forge/ModLauncher el cálculo de FRAMES puede fallar si ASM
 *   no puede resolver jerarquías con el ClassLoader modular.
 * - Se usa ClassWriter seguro (getCommonSuperClass con fallback) para evitar VerifyError.
 */
public class CDProfiler implements ServicioCDLauncher {

	public static final String ID = "cdprofiler_wip";

	/** GUI activa para consumir eventos */
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
	 * Punto de entrada estático “ideal”.
	 * OJO: si este tipo no es visible desde el classloader del juego (MC-BOOTSTRAP),
	 * llamar directo puede causar NoClassDefFoundError/IllegalAccessError.
	 */
	public static final class Hooks {

		private Hooks() {}

		public static void registrarLlamada(String claseInterna, String metodo, String descriptor, long duracionNs) {
			ProfilerGUI gui = guiActiva;
			if (gui == null) return;

			String clase = (claseInterna == null) ? "?" : claseInterna.replace('/', '.');
			gui.agregarLlamadaMetodo(clase, metodo, descriptor, duracionNs);
		}
	}

	/**
	 * Transformer que instrumenta métodos y reporta duración.
	 */
	static final class TransformadorProfiler implements ClassFileTransformer {

		private static final java.util.Set<String> CLASES_TRANSFORMADAS =
		        java.util.concurrent.ConcurrentHashMap.newKeySet();

		
		
@Override
public byte[] transform(
        ClassLoader loader,
        String nombreClase,
        Class<?> claseRedefinida,
        ProtectionDomain dominioProteccion,
        byte[] bytecodeClase
) throws IllegalClassFormatException {

    if (nombreClase == null) return null;

    // Requisito técnico: evitar bootstrap puro
    if (loader == null) return null;

    // Evitar recursión (mínimo inevitable)
    if (nombreClase.startsWith("java/")
            || nombreClase.startsWith("javax/")
            || nombreClase.startsWith("sun/")
            || nombreClase.startsWith("jdk/")
            || nombreClase.startsWith("org/objectweb/asm/")
            || nombreClase.startsWith("com/asbestosstar/crashdetector/")) {
        return null;
    }

    // Idempotencia: no transformar dos veces
    if (!CLASES_TRANSFORMADAS.add(nombreClase)) {
        return null;
    }

    try {
        final int api = AnalizadorBytecodeASM.obtenerVersionMaximaASM();

        ClassReader lector = new ClassReader(bytecodeClase);

        // COMPUTE_FRAMES necesario si metes try/finally o try/catch nuevos
        ClassWriter escritor = new ClassWriterSeguro(
                ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS,
                loader
        );

        ClassVisitor visitante = new VisitadorClaseProfiler(api, escritor, nombreClase);

        // Alimentar frames a ASM
        lector.accept(visitante, ClassReader.EXPAND_FRAMES);

        return escritor.toByteArray();

    } catch (Throwable t) {
        // Si falla, no rompas el arranque: deja la clase original
        CLASES_TRANSFORMADAS.remove(nombreClase);
        t.printStackTrace();
        return null;
    }
}


	}

static final class ClassWriterSeguro extends ClassWriter {

    private final ClassLoader loader;

    ClassWriterSeguro(int flags, ClassLoader loader) {
        super(flags);
        this.loader = loader;
    }

    @Override
    protected ClassLoader getClassLoader() {
        // Importante: devolver el loader target, pero NO cargar clases aquí
        return (loader != null) ? loader : CDProfiler.class.getClassLoader();
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        /*
         * CRÍTICO:
         * En Forge/ModLauncher NO intentes resolver jerarquías con Class.forName:
         * puede disparar cargas reentrantes y romper el bootstrap.
         *
         * Devolver Object es conservador (supertipo) y evita VerifyError por carga reentrante.
         */
        return "java/lang/Object";
    }
}





	/**
	 * Visitador de clase que envuelve métodos.
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

			if ((access & Opcodes.ACC_ABSTRACT) != 0 || (access & Opcodes.ACC_NATIVE) != 0) {
				return mv;
			}

			if ("<clinit>".equals(name)) {
				return mv;
			}

			return new VisitadorMetodoProfiler(api, mv, access, name, descriptor, nombreClaseInterno);
		}
	}

static final class VisitadorMetodoProfiler extends AdviceAdapter {

    private final String nombreClaseInterno;
    private final String nombreMetodo;
    private final String descriptorMetodo;

    private int localInicioNs;
    private int localDurNs;

    // Labels para try/finally
    private final Label iniTry = new Label();
    private final Label finTry = new Label();
    private final Label handler = new Label();

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
    public void visitCode() {
        super.visitCode();

        // try { ... } finally { ... }
        visitLabel(iniTry);
    }

    @Override
    protected void onMethodEnter() {
        // long inicio = System.nanoTime();
        localInicioNs = newLocal(Type.LONG_TYPE);
        invokeStatic(Type.getType(System.class),
                new org.objectweb.asm.commons.Method("nanoTime", "()J"));
        storeLocal(localInicioNs, Type.LONG_TYPE);
    }

    @Override
    protected void onMethodExit(int opcode) {
        // IMPORTANTE:
        // NO hacer nada aquí. Evitamos tocar retornos/throws y su pila.
        // El "finally" se ejecutará en visitMaxs().
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {

        // Fin del try normal
        visitLabel(finTry);

        // finally normal (ruta sin excepción)
        emitirFinally(false);

        // Handler del finally para excepciones
        visitLabel(handler);
        // En handler la excepción está en la pila. Guardarla, ejecutar finally, y re-lanzar.
        int localEx = newLocal(Type.getType(Throwable.class));
        storeLocal(localEx);

        emitirFinally(true);

        loadLocal(localEx);
        throwException();

        // Registrar try/catch para finally
        visitTryCatchBlock(iniTry, finTry, handler, null);

        super.visitMaxs(maxStack, maxLocals);
    }

    /**
     * Emite el bloque finally:
     * - calcula duración
     * - llama a Hooks por reflexión dentro de try/catch (para que no reviente el juego)
     *
     * @param desdeHandler true si venimos del handler (excepción), false si es salida normal
     */
    private void emitirFinally(boolean desdeHandler) {

        // dur = System.nanoTime() - inicio
        invokeStatic(Type.getType(System.class),
                new org.objectweb.asm.commons.Method("nanoTime", "()J"));
        loadLocal(localInicioNs, Type.LONG_TYPE);
        math(SUB, Type.LONG_TYPE);

        localDurNs = newLocal(Type.LONG_TYPE);
        storeLocal(localDurNs, Type.LONG_TYPE);

        // try { llamar Hooks reflectivo } catch(Throwable) { }
        Label lTry = new Label();
        Label lEnd = new Label();
        Label lCatch = new Label();

        visitTryCatchBlock(lTry, lEnd, lCatch, "java/lang/Throwable");
        visitLabel(lTry);

        // Class<?> c = Class.forName("...CDProfiler$Hooks");
        visitLdcInsn("com.asbestosstar.crashdetector.lanzer.servicio.CDProfiler$Hooks");
        invokeStatic(Type.getType(Class.class),
                new org.objectweb.asm.commons.Method("forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
        int localClass = newLocal(Type.getType(Class.class));
        storeLocal(localClass);

        // Method m = c.getDeclaredMethod("registrarLlamada", String, String, String, long)
        loadLocal(localClass);
        visitLdcInsn("registrarLlamada");

        push(4);
        newArray(Type.getType(Class.class));
        dup(); push(0); visitLdcInsn(Type.getType(String.class)); arrayStore(Type.getType(Class.class));
        dup(); push(1); visitLdcInsn(Type.getType(String.class)); arrayStore(Type.getType(Class.class));
        dup(); push(2); visitLdcInsn(Type.getType(String.class)); arrayStore(Type.getType(Class.class));
        dup(); push(3); visitLdcInsn(Type.LONG_TYPE);              arrayStore(Type.getType(Class.class));

        invokeVirtual(Type.getType(Class.class),
                new org.objectweb.asm.commons.Method("getDeclaredMethod",
                        "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
        int localMethod = newLocal(Type.getType(java.lang.reflect.Method.class));
        storeLocal(localMethod);

        // m.invoke(null, clase, metodo, desc, Long.valueOf(dur))
        loadLocal(localMethod);
        visitInsn(ACONST_NULL);

        push(4);
        newArray(Type.getType(Object.class));
        dup(); push(0); visitLdcInsn(nombreClaseInterno); arrayStore(Type.getType(Object.class));
        dup(); push(1); visitLdcInsn(nombreMetodo);       arrayStore(Type.getType(Object.class));
        dup(); push(2); visitLdcInsn(descriptorMetodo);   arrayStore(Type.getType(Object.class));
        dup(); push(3); loadLocal(localDurNs); box(Type.LONG_TYPE); arrayStore(Type.getType(Object.class));

        invokeVirtual(Type.getType(java.lang.reflect.Method.class),
                new org.objectweb.asm.commons.Method("invoke",
                        "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"));

        // descartar retorno de invoke()
        pop();

        visitLabel(lEnd);
        goTo(new Label()); // se parchea abajo

        // catch(Throwable) { /*silencio*/ }
        visitLabel(lCatch);
        pop();

        // Salida del try/catch interno
        Label lOut = new Label();
        visitLabel(lOut);
    }
}

}
