package com.asbestosstar.crashdetector.buscar;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinCampoInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinMetodoInfo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

/**
 * Analizador de bytecode utilizando la biblioteca Javassist. Proporciona
 * métodos para inspeccionar clases, métodos, referencias y mixins sin depender
 * de ASM.
 */
public class AnalizadorBytecodeJavassist {

	/**
	 * Verifica si una clase es un mixin de SpongePowered usando Javassist.
	 */
	public static boolean esClaseMixin(ArchivoDeMod mod, String nombreClase) {
		byte[] bytes = mod.obtenerBytesClase(nombreClase);
		if (bytes == null)
			return false;
		try {
			ClassFile cf = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytes)));
			AnnotationsAttribute attr = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
			if (attr == null)
				attr = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
			if (attr != null) {
				Annotation ann = attr.getAnnotation("org.spongepowered.asm.mixin.Mixin");
				if (ann != null)
					return true;
			}
			return false;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	/**
	 * Obtiene información detallada de un mixin usando Javassist.
	 */
	public static MixinInfo obtenerInfoMixin(ArchivoDeMod mod, String nombreClase) {
		byte[] bytes = mod.obtenerBytesClase(nombreClase);
		if (bytes == null)
			return null;
		try {
			ClassPool pool = new ClassPool();
			pool.appendSystemPath();
			ClassFile cf = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytes)));
			CtClass ct = pool.makeClass(cf, false);

			// Extraer targets del @Mixin
			List<String> targets = extraerTargetsMixinJavassist(ct, cf);

			// Extraer métodos @Inject/@Overwrite y campos @Shadow
			List<MixinMetodoInfo> metodos = extraerMetodosMixinJavassist(ct, cf);
			List<MixinCampoInfo> campos = extraerCamposMixinJavassist(ct, cf);
			List<String> targetsInject = extraerTargetsInjectJavassist(metodos, campos);

			// Refmaps se cargan externamente
			Map<String, String> refmapMetodos = new HashMap<>();
			Map<String, String> refmapCampos = new HashMap<>();

			String nombrePunto = nombreClase.replace('/', '.');
			return new MixinInfo(nombrePunto, mod.ubicacion(), targets, metodos, campos, targetsInject, refmapMetodos,
					refmapCampos);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return null;
		}
	}

	/**
	 * Extrae targets del @Mixin usando Javassist.
	 * 
	 * Soporta: - @Mixin(targets = { "a.b.C", "x.y.Z" }) - @Mixin(Foo.class)
	 * - @Mixin(value = { Foo.class, Bar.class })
	 * 
	 * Además tolera tanto anotaciones visibles como invisibles.
	 */
	private static List<String> extraerTargetsMixinJavassist(CtClass ct, ClassFile cf) throws Exception {
		List<String> targets = new ArrayList<>();

		Annotation mixinAnn = obtenerAnotacionClase(cf, "org.spongepowered.asm.mixin.Mixin");
		if (mixinAnn == null) {
			return targets;
		}

		// targets() -> String[]
		MemberValue targetsVal = mixinAnn.getMemberValue("targets");
		agregarTargetsDesdeMemberValue(targetsVal, targets, true);

		// value() -> Class<?>[]
		MemberValue valueVal = mixinAnn.getMemberValue("value");
		agregarTargetsDesdeMemberValue(valueVal, targets, false);

		return targets;
	}

	/**
	 * Busca una anotación de clase primero en visibles y luego en invisibles.
	 */
	private static Annotation obtenerAnotacionClase(ClassFile cf, String nombreAnotacion) {
		AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
		if (visible != null) {
			Annotation ann = visible.getAnnotation(nombreAnotacion);
			if (ann != null) {
				return ann;
			}
		}

		AnnotationsAttribute invisible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
		if (invisible != null) {
			Annotation ann = invisible.getAnnotation(nombreAnotacion);
			if (ann != null) {
				return ann;
			}
		}

		return null;
	}

	/**
	 * Busca una anotación de método primero en visibles y luego en invisibles.
	 */
	private static Annotation obtenerAnotacionMetodo(MethodInfo mi, String nombreAnotacion) {
		AnnotationsAttribute visible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.visibleTag);
		if (visible != null) {
			Annotation ann = visible.getAnnotation(nombreAnotacion);
			if (ann != null) {
				return ann;
			}
		}

		AnnotationsAttribute invisible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.invisibleTag);
		if (invisible != null) {
			Annotation ann = invisible.getAnnotation(nombreAnotacion);
			if (ann != null) {
				return ann;
			}
		}

		return null;
	}

	/**
	 * Busca una anotación de campo primero en visibles y luego en invisibles.
	 */
	private static Annotation obtenerAnotacionCampo(javassist.bytecode.FieldInfo fi, String nombreAnotacion) {
		AnnotationsAttribute visible = (AnnotationsAttribute) fi.getAttribute(AnnotationsAttribute.visibleTag);
		if (visible != null) {
			Annotation ann = visible.getAnnotation(nombreAnotacion);
			if (ann != null) {
				return ann;
			}
		}

		AnnotationsAttribute invisible = (AnnotationsAttribute) fi.getAttribute(AnnotationsAttribute.invisibleTag);
		if (invisible != null) {
			Annotation ann = invisible.getAnnotation(nombreAnotacion);
			if (ann != null) {
				return ann;
			}
		}

		return null;
	}

	/**
	 * Extrae targets desde un MemberValue de Javassist.
	 * 
	 * @param memberValue  valor de la anotación
	 * @param targets      lista destino
	 * @param aceptarTexto true para targets() que usa String[], false para value()
	 *                     que usa Class[]
	 */
	private static void agregarTargetsDesdeMemberValue(MemberValue memberValue, List<String> targets,
			boolean aceptarTexto) {
		if (memberValue == null) {
			return;
		}

		// Caso array: String[] o Class<?>[]
		if (memberValue instanceof ArrayMemberValue) {
			MemberValue[] valores = ((ArrayMemberValue) memberValue).getValue();
			if (valores == null) {
				return;
			}

			for (MemberValue mv : valores) {
				agregarTargetsDesdeMemberValue(mv, targets, aceptarTexto);
			}
			return;
		}

		// Caso String individual (targets = "a.b.C")
		if (aceptarTexto && memberValue instanceof javassist.bytecode.annotation.StringMemberValue) {
			String t = ((javassist.bytecode.annotation.StringMemberValue) memberValue).getValue();
			agregarTargetNormalizadoJavassist(t, targets);
			return;
		}

		// Caso Class individual (value = Foo.class)
		if (memberValue instanceof ClassMemberValue) {
			String className = ((ClassMemberValue) memberValue).getValue();
			agregarTargetNormalizadoJavassist(className, targets);
		}
	}

	/**
	 * Normaliza el nombre de target para UI y evita duplicados.
	 */
	private static void agregarTargetNormalizadoJavassist(String className, List<String> targets) {
		if (className == null) {
			return;
		}

		String limpio = className.trim();
		if (limpio.isEmpty()) {
			return;
		}

		// Por seguridad: convertir formato interno a puntos
		limpio = limpio.replace('/', '.');

		// Si llegara como descriptor tipo Lcom/x/Y;
		if (limpio.length() >= 2 && limpio.charAt(0) == 'L' && limpio.endsWith(";")) {
			limpio = limpio.substring(1, limpio.length() - 1).replace('/', '.');
		}

		if (!targets.contains(limpio)) {
			targets.add(limpio);
		}
	}

	/**
	 * Agrega strings desde un MemberValue sin asumir que siempre viene como array.
	 */
	private static void agregarStringsDesdeMemberValue(MemberValue memberValue, List<String> destino) {
		if (memberValue == null) {
			return;
		}

		if (memberValue instanceof ArrayMemberValue) {
			MemberValue[] valores = ((ArrayMemberValue) memberValue).getValue();
			if (valores == null) {
				return;
			}

			for (MemberValue mv : valores) {
				agregarStringsDesdeMemberValue(mv, destino);
			}
			return;
		}

		if (memberValue instanceof javassist.bytecode.annotation.StringMemberValue) {
			String s = ((javassist.bytecode.annotation.StringMemberValue) memberValue).getValue();
			if (s != null) {
				s = s.trim();
				if (!s.isEmpty() && !destino.contains(s)) {
					destino.add(s);
				}
			}
		}
	}

	/**
	 * Extrae métodos @Inject/@Overwrite usando Javassist.
	 * 
	 * Importante: revisa anotaciones visibles e invisibles.
	 */
	private static List<MixinMetodoInfo> extraerMetodosMixinJavassist(CtClass ct, ClassFile cf) throws Exception {
		List<MixinMetodoInfo> resultados = new ArrayList<>();

		for (MethodInfo mi : cf.getMethods()) {
			// @Inject
			Annotation injectAnn = obtenerAnotacionMetodo(mi, "org.spongepowered.asm.mixin.injection.Inject");
			if (injectAnn != null) {
				MixinMetodoInfo info = new MixinMetodoInfo(mi.getName(), mi.getDescriptor(), new ArrayList<>(), false);

				MemberValue methodVal = injectAnn.getMemberValue("method");
				agregarStringsDesdeMemberValue(methodVal, info.obtenerTargets());

				resultados.add(info);
				continue;
			}

			// @Overwrite
			Annotation overwriteAnn = obtenerAnotacionMetodo(mi, "org.spongepowered.asm.mixin.Overwrite");
			if (overwriteAnn != null) {
				resultados.add(new MixinMetodoInfo(mi.getName(), mi.getDescriptor(), new ArrayList<>(), true));
			}
		}

		return resultados;
	}

	/**
	 * Extrae campos @Shadow usando Javassist.
	 * 
	 * Importante: revisa anotaciones visibles e invisibles.
	 */
	private static List<MixinCampoInfo> extraerCamposMixinJavassist(CtClass ct, ClassFile cf) throws Exception {
		List<MixinCampoInfo> resultados = new ArrayList<>();

		for (javassist.bytecode.FieldInfo fi : cf.getFields()) {
			Annotation shadowAnn = obtenerAnotacionCampo(fi, "org.spongepowered.asm.mixin.Shadow");
			if (shadowAnn != null) {
				resultados.add(new MixinCampoInfo(fi.getName(), fi.getDescriptor()));
			}
		}

		return resultados;
	}

	/**
	 * Colecta targets de @Inject para refmap.
	 */
	private static List<String> extraerTargetsInjectJavassist(List<MixinMetodoInfo> metodos,
			List<MixinCampoInfo> campos) {
		List<String> targets = new ArrayList<>();
		for (MixinMetodoInfo m : metodos) {
			for (String t : m.obtenerTargets())
				if (!targets.contains(t))
					targets.add(t);
			if (m.esOverwrite()) {
				String sig = m.obtenerNombre() + m.obtenerDescriptor();
				if (!targets.contains(sig))
					targets.add(sig);
			}
		}
		for (MixinCampoInfo f : campos)
			if (!targets.contains(f.obtenerNombre()))
				targets.add(f.obtenerNombre());
		return targets;
	}

	// ========================================================================
	// MÉTODOS EXISTENTES (sin cambios funcionales)
	// ========================================================================

	public static List<ArchivoDeMod.Constante> analizarConstantesEnMetodo(ArchivoDeMod mod, String nombreClase,
			String nombreMetodo, String descriptor) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();
		try {
			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));
			MethodInfo mi = null;
			for (MethodInfo m : classFile.getMethods()) {
				if (m.getName().equals(nombreMetodo) && m.getDescriptor().equals(descriptor)) {
					mi = m;
					break;
				}
			}
			if (mi == null || (mi.getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0)
				return new ArrayList<>();
			javassist.bytecode.CodeAttribute ca = mi.getCodeAttribute();
			if (ca == null)
				return new ArrayList<>();
			ConstPool cp = classFile.getConstPool();
			javassist.bytecode.CodeIterator it = ca.iterator();
			List<ArchivoDeMod.Constante> resultados = new ArrayList<>();
			while (it.hasNext()) {
				int index = it.next();
				int op = it.byteAt(index) & 0xFF;
				switch (op) {
				case javassist.bytecode.Opcode.ICONST_M1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(-1));
					break;
				case javassist.bytecode.Opcode.ICONST_0:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(0));
					break;
				case javassist.bytecode.Opcode.ICONST_1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(1));
					break;
				case javassist.bytecode.Opcode.ICONST_2:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(2));
					break;
				case javassist.bytecode.Opcode.ICONST_3:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(3));
					break;
				case javassist.bytecode.Opcode.ICONST_4:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(4));
					break;
				case javassist.bytecode.Opcode.ICONST_5:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(5));
					break;
				case javassist.bytecode.Opcode.LCONST_0:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Long.valueOf(0L));
					break;
				case javassist.bytecode.Opcode.LCONST_1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Long.valueOf(1L));
					break;
				case javassist.bytecode.Opcode.FCONST_0:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Float.valueOf(0f));
					break;
				case javassist.bytecode.Opcode.FCONST_1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Float.valueOf(1f));
					break;
				case javassist.bytecode.Opcode.FCONST_2:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Float.valueOf(2f));
					break;
				case javassist.bytecode.Opcode.DCONST_0:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Double.valueOf(0d));
					break;
				case javassist.bytecode.Opcode.DCONST_1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Double.valueOf(1d));
					break;
				case javassist.bytecode.Opcode.BIPUSH: {
					int val = (byte) it.byteAt(index + 1);
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(val));
					break;
				}
				case javassist.bytecode.Opcode.SIPUSH: {
					int val = it.s16bitAt(index + 1);
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(val));
					break;
				}
				case javassist.bytecode.Opcode.LDC: {
					int cpIndex = it.byteAt(index + 1) & 0xFF;
					agregarDesdeConstPool(resultados, nombreClase, nombreMetodo, descriptor, cp, cpIndex);
					break;
				}
				case javassist.bytecode.Opcode.LDC_W: {
					int cpIndex = it.u16bitAt(index + 1);
					agregarDesdeConstPool(resultados, nombreClase, nombreMetodo, descriptor, cp, cpIndex);
					break;
				}
				case javassist.bytecode.Opcode.LDC2_W: {
					int cpIndex = it.u16bitAt(index + 1);
					agregarDesdeConstPool(resultados, nombreClase, nombreMetodo, descriptor, cp, cpIndex);
					break;
				}
				}
			}
			return resultados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	private static void agregar(List<ArchivoDeMod.Constante> out, String clase, String metodo, String desc,
			Object valor) {
		String tipo = (valor == null) ? "null" : valor.getClass().getSimpleName();
		out.add(new ArchivoDeMod.Constante(clase, metodo, desc, valor, tipo));
	}

	private static void agregarDesdeConstPool(List<ArchivoDeMod.Constante> out, String clase, String metodo,
			String desc, ConstPool cp, int index) {
		int tag = cp.getTag(index);
		switch (tag) {
		case ConstPool.CONST_String:
			agregar(out, clase, metodo, desc, cp.getStringInfo(index));
			break;
		case ConstPool.CONST_Integer:
			agregar(out, clase, metodo, desc, Integer.valueOf(cp.getIntegerInfo(index)));
			break;
		case ConstPool.CONST_Float:
			agregar(out, clase, metodo, desc, Float.valueOf(cp.getFloatInfo(index)));
			break;
		case ConstPool.CONST_Long:
			agregar(out, clase, metodo, desc, Long.valueOf(cp.getLongInfo(index)));
			break;
		case ConstPool.CONST_Double:
			agregar(out, clase, metodo, desc, Double.valueOf(cp.getDoubleInfo(index)));
			break;
		case ConstPool.CONST_Class:
			agregar(out, clase, metodo, desc, cp.getClassInfo(index));
			break;
		default:
			agregar(out, clase, metodo, desc, ("<constpool tag " + tag + ">"));
			break;
		}
	}

	public static List<ArchivoDeMod.InfoMetodo> analizarMetodos(ArchivoDeMod mod, String nombreClase) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null) {
			return new ArrayList<>();
		}

		try {
			ClassPool pool = new ClassPool();
			pool.appendSystemPath();

			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));
			CtClass ctClass = pool.makeClass(classFile, false);

			String claseOrigen = classFile.getName().replace('.', '/');
			List<ArchivoDeMod.InfoMetodo> resultados = new ArrayList<>();

			for (MethodInfo methodInfo : classFile.getMethods()) {
				String methodName = methodInfo.getName();
				String descriptor = methodInfo.getDescriptor();

				if ((methodInfo.getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
					resultados.add(
							new ArchivoDeMod.InfoMetodo(methodName, descriptor, new ArrayList<>(), new ArrayList<>()));
					continue;
				}

				List<ArchivoDeMod.Referencia> refMetodos = new ArrayList<>();
				List<ArchivoDeMod.Referencia> refCampos = new ArrayList<>();

				CtMethod ctMethod = ctClass.getMethod(methodName, descriptor);
				ctMethod.instrument(new ExprEditor() {
					@Override
					public void edit(MethodCall m) {
						refMetodos.add(convertirAMiReferencia(m, claseOrigen, methodName, descriptor));
					}

					@Override
					public void edit(FieldAccess f) {
						refCampos.add(convertirAMiReferencia(f, claseOrigen, methodName, descriptor));
					}
				});

				resultados.add(new ArchivoDeMod.InfoMetodo(methodName, descriptor, refMetodos, refCampos));
			}

			ctClass.detach();
			return resultados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	public static List<ArchivoDeMod.InfoCampo> analizarCampos(ArchivoDeMod mod, String nombreClase) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();
		try {
			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));
			List<ArchivoDeMod.InfoCampo> resultados = new ArrayList<>();
			for (javassist.bytecode.FieldInfo fieldInfo : classFile.getFields()) {
				resultados.add(new ArchivoDeMod.InfoCampo(fieldInfo.getName(), fieldInfo.getDescriptor()));
			}
			return resultados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	public static List<ArchivoDeMod.Referencia> analizarReferenciasEnMetodo(ArchivoDeMod mod, String nombreClase,
			String nombreMetodo, String descriptor) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null) {
			return new ArrayList<>();
		}

		try {
			ClassPool pool = new ClassPool();
			pool.appendSystemPath();

			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));
			String claseOrigen = classFile.getName().replace('.', '/');

			MethodInfo methodInfo = null;
			for (MethodInfo mi : classFile.getMethods()) {
				if (mi.getName().equals(nombreMetodo) && mi.getDescriptor().equals(descriptor)) {
					methodInfo = mi;
					break;
				}
			}

			if (methodInfo == null || (methodInfo.getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
				return new ArrayList<>();
			}

			List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();
			CtClass ctClass = pool.makeClass(classFile, false);
			CtMethod ctMethod = ctClass.getMethod(nombreMetodo, descriptor);

			ctMethod.instrument(new ExprEditor() {
				@Override
				public void edit(MethodCall m) {
					resultados.add(convertirAMiReferencia(m, claseOrigen, nombreMetodo, descriptor));
				}

				@Override
				public void edit(FieldAccess f) {
					resultados.add(convertirAMiReferencia(f, claseOrigen, nombreMetodo, descriptor));
				}
			});

			ctClass.detach();
			return resultados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	public static List<ArchivoDeMod.Referencia> analizarReferenciasAMetodo(ArchivoDeMod mod, String claseObjetivo,
			String metodoObjetivo, String descriptorObjetivo) {
		List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();

		String claseObjetivoInterna = claseObjetivo.replace('.', '/');

		ClassPool pool = new ClassPool();
		pool.appendSystemPath();

		for (String className : mod.obtenerTodosLosNombresDeClases()) {
			byte[] bytes = mod.obtenerBytesClase(className);
			if (bytes == null) {
				continue;
			}

			try {
				ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytes)));
				CtClass ctClass = pool.makeClass(classFile, false);

				String claseOrigen = classFile.getName().replace('.', '/');

				for (CtMethod method : ctClass.getDeclaredMethods()) {
					if ((method.getMethodInfo().getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
						continue;
					}

					String metodoOrigen = method.getName();
					String descriptorOrigen = method.getSignature();

					method.instrument(new ExprEditor() {
						@Override
						public void edit(MethodCall m) {
							String owner = m.getClassName().replace('.', '/');

							if (owner.equals(claseObjetivoInterna) && m.getMethodName().equals(metodoObjetivo)
									&& m.getSignature().equals(descriptorObjetivo)) {
								resultados.add(new ArchivoDeMod.Referencia(owner, metodoObjetivo, descriptorObjetivo,
										true, claseOrigen, metodoOrigen, descriptorOrigen));
							}
						}
					});
				}

				ctClass.detach();
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		return resultados;
	}

	public static ArchivoDeMod.Referencia convertirAMiReferencia(MethodCall m) {
		return new ArchivoDeMod.Referencia(m.getClassName().replace('.', '/'), m.getMethodName(), m.getSignature(),
				true);
	}

	public static ArchivoDeMod.Referencia convertirAMiReferencia(MethodCall m, String claseOrigen, String metodoOrigen,
			String descriptorOrigen) {
		return new ArchivoDeMod.Referencia(m.getClassName().replace('.', '/'), m.getMethodName(), m.getSignature(),
				true, claseOrigen, metodoOrigen, descriptorOrigen);
	}

	public static ArchivoDeMod.Referencia convertirAMiReferencia(FieldAccess f) {
		return new ArchivoDeMod.Referencia(f.getClassName().replace('.', '/'), f.getFieldName(), f.getSignature(),
				false);
	}

	public static ArchivoDeMod.Referencia convertirAMiReferencia(FieldAccess f, String claseOrigen, String metodoOrigen,
			String descriptorOrigen) {
		return new ArchivoDeMod.Referencia(f.getClassName().replace('.', '/'), f.getFieldName(), f.getSignature(),
				false, claseOrigen, metodoOrigen, descriptorOrigen);
	}

	public static List<String> obtenerNombreModuloInfo(byte[] classBytes) {
		List<String> modulos = new ArrayList<>();
		try {
			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(classBytes)));
			ConstPool constPool = classFile.getConstPool();
			for (int i = 1; i < constPool.getSize(); i++) {
				if (constPool.getTag(i) == ConstPool.CONST_Module) {
					String moduleName = constPool.getModuleInfo(i);
					if (moduleName != null && !moduleName.isEmpty())
						modulos.add(moduleName);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modulos;
	}
}