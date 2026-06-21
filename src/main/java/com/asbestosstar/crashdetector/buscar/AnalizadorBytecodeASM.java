package com.asbestosstar.crashdetector.buscar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinCampoInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinMetodoInfo;

/**
 * Analizador de bytecode utilizando la biblioteca ASM. Proporciona métodos para
 * inspeccionar clases, métodos, referencias y mixins sin depender de Javassist.
 */
public class AnalizadorBytecodeASM {

	/**
	 * Verifica si una clase es un mixin de SpongePowered (tiene @Mixin).
	 */
	public static boolean esClaseMixin(ArchivoDeMod mod, String nombreClase) {
		byte[] bytes = mod.obtenerBytesClase(nombreClase);
		if (bytes == null)
			return false;
		try {
			ClassReader cr = new ClassReader(bytes);
			DetectorMixinVisitor detector = new DetectorMixinVisitor();
			cr.accept(detector, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
			return detector.encontroMixin;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return false;
		}
	}

	/**
	 * Obtiene información detallada de un mixin usando ASM.
	 */
	public static MixinInfo obtenerInfoMixin(ArchivoDeMod mod, String nombreClase) {
		byte[] bytes = mod.obtenerBytesClase(nombreClase);
		if (bytes == null) {
			return null;
		}

		try {
			ClassReader cr = new ClassReader(bytes);
			ClassNode cn = new ClassNode(obtenerVersionMaximaASM());
			cr.accept(cn, ClassReader.SKIP_FRAMES);

			// Extraer targets del @Mixin
			List<String> targets = extraerTargetsMixin(cn);

			// Extraer métodos @Inject/@Overwrite y campos @Shadow
			List<MixinMetodoInfo> metodos = extraerMetodosMixin(cn);
			List<MixinCampoInfo> campos = extraerCamposMixin(cn);
			List<String> targetsInject = extraerTargetsInject(metodos, campos);

			// Refmaps se cargan externamente desde archivos JSON, no desde bytecode
			Map<String, String> refmapMetodos = new HashMap<>();
			Map<String, String> refmapCampos = new HashMap<>();

			String nombrePunto = nombreClase.replace('/', '.');

			// Log temporal útil para confirmar que ya está entrando
			CrashDetectorLogger.log("[mixin] " + nombrePunto + " targets=" + targets);

			return new MixinInfo(nombrePunto, mod.ubicacion(), targets, metodos, campos, targetsInject, refmapMetodos,
					refmapCampos);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return null;
		}
	}

	/**
	 * Extrae los targets de la anotación @Mixin.
	 *
	 * Soporta: - @Mixin(targets = { "a.b.C", "x.y.Z" }) - @Mixin(Foo.class)
	 * - @Mixin(value = { Foo.class, Bar.class })
	 *
	 * Importante:
	 * 
	 * @Mixin tiene RetentionPolicy.CLASS, así que normalmente aparece en
	 *        invisibleAnnotations, no en visibleAnnotations.
	 */
	private static List<String> extraerTargetsMixin(ClassNode cn) {
		List<String> targets = new ArrayList<>();

		for (AnnotationNode ann : obtenerTodasLasAnotacionesClase(cn)) {
			if (ann == null || ann.desc == null) {
				continue;
			}

			if (!"Lorg/spongepowered/asm/mixin/Mixin;".equals(ann.desc)) {
				continue;
			}

			if (ann.values == null) {
				break;
			}

			for (int i = 0; i + 1 < ann.values.size(); i += 2) {
				Object keyObj = ann.values.get(i);
				Object valueObj = ann.values.get(i + 1);

				if (!(keyObj instanceof String)) {
					continue;
				}

				String key = (String) keyObj;

				// targets = { "net.minecraft.X", ... }
				if ("targets".equals(key)) {
					agregarTargetsDesdeValorDeAnotacion(valueObj, targets, true);
				}

				// value = { Foo.class, Bar.class } o @Mixin(Foo.class)
				else if ("value".equals(key)) {
					agregarTargetsDesdeValorDeAnotacion(valueObj, targets, false);
				}
			}

			break;
		}

		return targets;
	}

	/**
	 * Extrae métodos con @Inject o @Overwrite.
	 *
	 * Importante: Estas anotaciones también pueden estar en invisibleAnnotations.
	 */
	private static List<MixinMetodoInfo> extraerMetodosMixin(ClassNode cn) {
		List<MixinMetodoInfo> resultados = new ArrayList<>();
		if (cn.methods == null) {
			return resultados;
		}

		for (MethodNode method : cn.methods) {
			for (AnnotationNode ann : obtenerTodasLasAnotacionesMetodo(method)) {
				if (ann == null || ann.desc == null) {
					continue;
				}

				// @Inject
				if ("Lorg/spongepowered/asm/mixin/injection/Inject;".equals(ann.desc)) {
					MixinMetodoInfo info = new MixinMetodoInfo(method.name, method.desc, new ArrayList<>(), false);

					if (ann.values != null) {
						for (int i = 0; i + 1 < ann.values.size(); i += 2) {
							Object key = ann.values.get(i);
							Object value = ann.values.get(i + 1);

							if ("method".equals(key)) {
								agregarStringsDesdeValorDeAnotacion(value, info.obtenerTargets());
							}
						}
					}

					resultados.add(info);
				}

				// @Overwrite
				else if ("Lorg/spongepowered/asm/mixin/Overwrite;".equals(ann.desc)) {
					MixinMetodoInfo info = new MixinMetodoInfo(method.name, method.desc, new ArrayList<>(), true);
					resultados.add(info);
				}
			}
		}

		return resultados;
	}

	/**
	 * Extrae campos con @Shadow.
	 *
	 * Importante:
	 * 
	 * @Shadow también puede venir por invisibleAnnotations.
	 */
	private static List<MixinCampoInfo> extraerCamposMixin(ClassNode cn) {
		List<MixinCampoInfo> resultados = new ArrayList<>();
		if (cn.fields == null) {
			return resultados;
		}

		for (FieldNode field : cn.fields) {
			for (AnnotationNode ann : obtenerTodasLasAnotacionesCampo(field)) {
				if (ann != null && "Lorg/spongepowered/asm/mixin/Shadow;".equals(ann.desc)) {
					resultados.add(new MixinCampoInfo(field.name, field.desc));
					break;
				}
			}
		}

		return resultados;
	}

	/**
	 * Devuelve todas las anotaciones de clase: visibles e invisibles.
	 */
	private static List<AnnotationNode> obtenerTodasLasAnotacionesClase(ClassNode cn) {
		List<AnnotationNode> anns = new ArrayList<>();
		if (cn.visibleAnnotations != null) {
			anns.addAll(cn.visibleAnnotations);
		}
		if (cn.invisibleAnnotations != null) {
			anns.addAll(cn.invisibleAnnotations);
		}
		return anns;
	}

	/**
	 * Devuelve todas las anotaciones de método: visibles e invisibles.
	 */
	private static List<AnnotationNode> obtenerTodasLasAnotacionesMetodo(MethodNode mn) {
		List<AnnotationNode> anns = new ArrayList<>();
		if (mn.visibleAnnotations != null) {
			anns.addAll(mn.visibleAnnotations);
		}
		if (mn.invisibleAnnotations != null) {
			anns.addAll(mn.invisibleAnnotations);
		}
		return anns;
	}

	/**
	 * Devuelve todas las anotaciones de campo: visibles e invisibles.
	 */
	private static List<AnnotationNode> obtenerTodasLasAnotacionesCampo(FieldNode fn) {
		List<AnnotationNode> anns = new ArrayList<>();
		if (fn.visibleAnnotations != null) {
			anns.addAll(fn.visibleAnnotations);
		}
		if (fn.invisibleAnnotations != null) {
			anns.addAll(fn.invisibleAnnotations);
		}
		return anns;
	}

	/**
	 * Agrega targets a partir del valor crudo de ASM.
	 *
	 * @param value        valor asociado a la clave de la anotación
	 * @param targets      lista destino
	 * @param aceptarTexto true si se aceptan Strings (caso targets())
	 */
	private static void agregarTargetsDesdeValorDeAnotacion(Object value, List<String> targets, boolean aceptarTexto) {
		if (value == null) {
			return;
		}

		// Caso único: Type
		if (value instanceof Type) {
			Type t = (Type) value;
			if (t.getSort() == Type.OBJECT) {
				agregarTargetNormalizado(t.getClassName(), targets);
			}
			return;
		}

		// Caso único: String
		if (aceptarTexto && value instanceof String) {
			agregarTargetNormalizado((String) value, targets);
			return;
		}

		// Caso habitual: List<?>
		if (value instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> lista = (List<Object>) value;

			for (Object item : lista) {
				if (item == null) {
					continue;
				}

				if (item instanceof Type) {
					Type t = (Type) item;
					if (t.getSort() == Type.OBJECT) {
						agregarTargetNormalizado(t.getClassName(), targets);
					}
				} else if (aceptarTexto && item instanceof String) {
					agregarTargetNormalizado((String) item, targets);
				}
			}
			return;
		}

		// Fallback defensivo por si llegara como array real
		if (value.getClass().isArray()) {
			int len = java.lang.reflect.Array.getLength(value);
			for (int i = 0; i < len; i++) {
				Object item = java.lang.reflect.Array.get(value, i);
				agregarTargetsDesdeValorDeAnotacion(item, targets, aceptarTexto);
			}
		}
	}

	/**
	 * Agrega strings desde un valor de anotación sin asumir List<String> exacto.
	 */
	private static void agregarStringsDesdeValorDeAnotacion(Object value, List<String> destino) {
		if (value == null) {
			return;
		}

		if (value instanceof String) {
			String s = ((String) value).trim();
			if (!s.isEmpty() && !destino.contains(s)) {
				destino.add(s);
			}
			return;
		}

		if (value instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> lista = (List<Object>) value;

			for (Object item : lista) {
				if (item instanceof String) {
					String s = ((String) item).trim();
					if (!s.isEmpty() && !destino.contains(s)) {
						destino.add(s);
					}
				}
			}
		}
	}

	/**
	 * Normaliza el nombre y evita duplicados. Deja el formato con puntos para la
	 * UI.
	 */
	private static void agregarTargetNormalizado(String nombre, List<String> targets) {
		if (nombre == null) {
			return;
		}

		String limpio = nombre.trim();
		if (limpio.isEmpty()) {
			return;
		}

		// Descriptor tipo Lcom/x/Y;
		if (limpio.length() >= 2 && limpio.charAt(0) == 'L' && limpio.endsWith(";")) {
			limpio = limpio.substring(1, limpio.length() - 1);
		}

		// Pasar ASM interno a formato punto
		limpio = limpio.replace('/', '.');

		if (!targets.contains(limpio)) {
			targets.add(limpio);
		}
	}

	/**
	 * Agrega targets a la lista a partir del valor crudo que ASM expone en
	 * AnnotationNode.values.
	 * 
	 * @param value        valor asociado a la clave de la anotación
	 * @param targets      lista destino
	 * @param aceptarTexto true para aceptar Strings como targets directos
	 */
	private static void agregarTargetsDesdeObjeto(Object value, List<String> targets, boolean aceptarTexto) {
		if (value == null) {
			return;
		}

		// Caso directo: un solo Type
		if (value instanceof Type) {
			Type t = (Type) value;
			if (t.getSort() == Type.OBJECT) {
				agregarTargetNormalizado(t.getClassName(), targets);
			}
			return;
		}

		// Caso directo: un solo String
		if (aceptarTexto && value instanceof String) {
			agregarTargetNormalizado((String) value, targets);
			return;
		}

		// Caso normal en ASM: List<?>
		if (value instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> lista = (List<Object>) value;

			for (Object item : lista) {
				if (item == null) {
					continue;
				}

				if (item instanceof Type) {
					Type t = (Type) item;
					if (t.getSort() == Type.OBJECT) {
						agregarTargetNormalizado(t.getClassName(), targets);
					}
				} else if (aceptarTexto && item instanceof String) {
					agregarTargetNormalizado((String) item, targets);
				}
			}
			return;
		}

		// Fallback extra defensivo por si llega como array real
		if (value.getClass().isArray()) {
			int len = java.lang.reflect.Array.getLength(value);
			for (int i = 0; i < len; i++) {
				Object item = java.lang.reflect.Array.get(value, i);
				agregarTargetsDesdeObjeto(item, targets, aceptarTexto);
			}
		}
	}

	/**
	 * Extrae targets desde el atributo value() del @Mixin.
	 */
	private static void extraerTargetsDeValue(Object value, List<String> targets) {
		if (value == null) {
			return;
		}

		if (value instanceof Type) {
			Type t = (Type) value;
			if (t.getSort() == Type.OBJECT) {
				String nombre = t.getClassName();
				if (nombre != null && !nombre.isEmpty() && !targets.contains(nombre)) {
					targets.add(nombre);
				}
			}
			return;
		}

		if (value instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> lista = (List<Object>) value;

			for (Object item : lista) {
				if (item instanceof Type) {
					Type t = (Type) item;
					if (t.getSort() == Type.OBJECT) {
						String nombre = t.getClassName();
						if (nombre != null && !nombre.isEmpty() && !targets.contains(nombre)) {
							targets.add(nombre);
						}
					}
				} else if (item instanceof String) {
					String nombre = ((String) item).trim();
					if (!nombre.isEmpty() && !targets.contains(nombre)) {
						targets.add(nombre);
					}
				}
			}
		}
	}

	/**
	 * Colecta todos los targets de @Inject para búsqueda en refmap.
	 */
	private static List<String> extraerTargetsInject(List<MixinMetodoInfo> metodos, List<MixinCampoInfo> campos) {
		List<String> targets = new ArrayList<>();
		for (MixinMetodoInfo m : metodos) {
			for (String t : m.obtenerTargets()) {
				if (!targets.contains(t))
					targets.add(t);
			}
			// Para @Overwrite, el target es el método mismo
			if (m.esOverwrite()) {
				String sig = m.obtenerNombre() + m.obtenerDescriptor();
				if (!targets.contains(sig))
					targets.add(sig);
			}
		}
		for (MixinCampoInfo f : campos) {
			if (!targets.contains(f.obtenerNombre()))
				targets.add(f.obtenerNombre());
		}
		return targets;
	}

	// ========================================================================
	// MÉTODOS EXISTENTES (sin cambios funcionales, solo agregando compatibilidad)
	// ========================================================================

	public static List<ArchivoDeMod.Constante> analizarConstantesEnMetodo(ArchivoDeMod mod, String nombreClase,
			String nombreMetodo, String descriptor) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();
		try {
			ClassReader lector = new ClassReader(bytesClase);
			RecolectorConstantesMetodo recolector = new RecolectorConstantesMetodo(nombreMetodo, descriptor);
			lector.accept(recolector, ClassReader.SKIP_DEBUG);
			return recolector.obtenerResultados();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	static class RecolectorConstantesMetodo extends ClassVisitor {
		private String claseActual;
		private final String metodoObjetivo;
		private final String descriptorObjetivo;
		private final List<ArchivoDeMod.Constante> resultados = new ArrayList<>();

		public RecolectorConstantesMetodo(String metodo, String descriptor) {
			super(obtenerVersionMaximaASM());
			this.metodoObjetivo = metodo;
			this.descriptorObjetivo = descriptor;
		}

		@Override
		public void visit(int version, int access, String name, String signature, String superName,
				String[] interfaces) {
			this.claseActual = name;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if (!name.equals(metodoObjetivo) || !desc.equals(descriptorObjetivo))
				return null;
			return new MethodVisitor(obtenerVersionMaximaASM()) {
				@Override
				public void visitLdcInsn(Object cst) {
					agregar(cst);
				}

				@Override
				public void visitIntInsn(int opcode, int operand) {
					if (opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH)
						agregar(Integer.valueOf(operand));
				}

				@Override
				public void visitInsn(int opcode) {
					switch (opcode) {
					case Opcodes.ICONST_M1:
						agregar(Integer.valueOf(-1));
						break;
					case Opcodes.ICONST_0:
						agregar(Integer.valueOf(0));
						break;
					case Opcodes.ICONST_1:
						agregar(Integer.valueOf(1));
						break;
					case Opcodes.ICONST_2:
						agregar(Integer.valueOf(2));
						break;
					case Opcodes.ICONST_3:
						agregar(Integer.valueOf(3));
						break;
					case Opcodes.ICONST_4:
						agregar(Integer.valueOf(4));
						break;
					case Opcodes.ICONST_5:
						agregar(Integer.valueOf(5));
						break;
					case Opcodes.LCONST_0:
						agregar(Long.valueOf(0L));
						break;
					case Opcodes.LCONST_1:
						agregar(Long.valueOf(1L));
						break;
					case Opcodes.FCONST_0:
						agregar(Float.valueOf(0f));
						break;
					case Opcodes.FCONST_1:
						agregar(Float.valueOf(1f));
						break;
					case Opcodes.FCONST_2:
						agregar(Float.valueOf(2f));
						break;
					case Opcodes.DCONST_0:
						agregar(Double.valueOf(0d));
						break;
					case Opcodes.DCONST_1:
						agregar(Double.valueOf(1d));
						break;
					}
				}

				private void agregar(Object valor) {
					String tipo = (valor == null) ? "null" : valor.getClass().getSimpleName();
					resultados.add(
							new ArchivoDeMod.Constante(claseActual, metodoObjetivo, descriptorObjetivo, valor, tipo));
				}
			};
		}

		public List<ArchivoDeMod.Constante> obtenerResultados() {
			return new ArrayList<>(resultados);
		}
	}

	public static List<ArchivoDeMod.InfoMetodo> analizarMetodos(ArchivoDeMod mod, String nombreClase) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();
		try {
			ClassReader lector = new ClassReader(bytesClase);
			RecolectorInformacionMetodos recolector = new RecolectorInformacionMetodos();
			lector.accept(recolector, ClassReader.SKIP_DEBUG);
			return recolector.obtenerResultados();
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
			ClassReader lector = new ClassReader(bytesClase);
			RecolectorInformacionCampos recolector = new RecolectorInformacionCampos();
			lector.accept(recolector, ClassReader.SKIP_DEBUG);
			return recolector.obtenerResultados();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	public static List<ArchivoDeMod.Referencia> analizarReferenciasEnMetodo(ArchivoDeMod mod, String nombreClase,
			String nombreMetodo, String descriptor) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();
		try {
			ClassReader lector = new ClassReader(bytesClase);
			RecolectorReferenciasMetodo recolector = new RecolectorReferenciasMetodo(nombreMetodo, descriptor);
			lector.accept(recolector, ClassReader.SKIP_DEBUG);
			return recolector.obtenerResultados();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	public static List<ArchivoDeMod.Referencia> analizarReferenciasAMetodo(ArchivoDeMod mod, String claseObjetivo,
			String metodoObjetivo, String descriptorObjetivo) {
		List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();

		String claseObjetivoInterna = claseObjetivo.replace('.', '/');

		for (String nombreClase : mod.obtenerTodosLosNombresDeClases()) {
			byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
			if (bytesClase == null) {
				continue;
			}

			try {
				ClassReader lector = new ClassReader(bytesClase);
				RecolectorLlamadasAMetodo recolector = new RecolectorLlamadasAMetodo(claseObjetivoInterna,
						metodoObjetivo, descriptorObjetivo);
				lector.accept(recolector, ClassReader.SKIP_DEBUG);
				resultados.addAll(recolector.obtenerResultados());
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		return resultados;
	}

	static class DetectorMixinVisitor extends ClassVisitor {
		boolean encontroMixin = false;

		public DetectorMixinVisitor() {
			super(obtenerVersionMaximaASM());
		}

		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			if ("Lorg/spongepowered/asm/mixin/Mixin;".equals(desc))
				encontroMixin = true;
			return null;
		}
	}

	static class RecolectorInformacionMetodos extends ClassVisitor {
		private final List<ArchivoDeMod.InfoMetodo> resultados = new ArrayList<>();
		private String claseActual;

		public RecolectorInformacionMetodos() {
			super(obtenerVersionMaximaASM());
		}

		@Override
		public void visit(int version, int access, String name, String signature, String superName,
				String[] interfaces) {
			this.claseActual = name;
		}

		@Override
		public MethodVisitor visitMethod(int acceso, String nombre, String descriptor, String firma,
				String[] excepciones) {
			List<ArchivoDeMod.Referencia> refMetodos = new ArrayList<>();
			List<ArchivoDeMod.Referencia> refCampos = new ArrayList<>();
			return new RecolectorReferenciasMetodoInterno(nombre, descriptor, refMetodos, refCampos);
		}

		public List<ArchivoDeMod.InfoMetodo> obtenerResultados() {
			return new ArrayList<>(resultados);
		}

		private class RecolectorReferenciasMetodoInterno extends MethodVisitor {
			private final String nombre;
			private final String descriptor;
			private final List<ArchivoDeMod.Referencia> refMetodos;
			private final List<ArchivoDeMod.Referencia> refCampos;

			public RecolectorReferenciasMetodoInterno(String nombre, String descriptor,
					List<ArchivoDeMod.Referencia> refMetodos, List<ArchivoDeMod.Referencia> refCampos) {
				super(obtenerVersionMaximaASM());
				this.nombre = nombre;
				this.descriptor = descriptor;
				this.refMetodos = refMetodos;
				this.refCampos = refCampos;
			}

			@Override
			public void visitMethodInsn(int opcode, String propietario, String nombreRef, String descriptorRef,
					boolean esInterfaz) {
				refMetodos.add(new ArchivoDeMod.Referencia(propietario, nombreRef, descriptorRef, true, claseActual,
						nombre, descriptor));
			}

			@Override
			public void visitFieldInsn(int opcode, String propietario, String nombreRef, String descriptorRef) {
				refCampos.add(new ArchivoDeMod.Referencia(propietario, nombreRef, descriptorRef, false, claseActual,
						nombre, descriptor));
			}

			@Override
			public void visitEnd() {
				resultados.add(new ArchivoDeMod.InfoMetodo(nombre, descriptor, refMetodos, refCampos));
			}
		}
	}

	static class RecolectorInformacionCampos extends ClassVisitor {
		private final List<ArchivoDeMod.InfoCampo> resultados = new ArrayList<>();

		public RecolectorInformacionCampos() {
			super(obtenerVersionMaximaASM());
		}

		@Override
		public FieldVisitor visitField(int acceso, String nombre, String descriptor, String firma, Object valor) {
			resultados.add(new ArchivoDeMod.InfoCampo(nombre, descriptor));
			return null;
		}

		public List<ArchivoDeMod.InfoCampo> obtenerResultados() {
			return new ArrayList<>(resultados);
		}
	}

	static class RecolectorReferenciasMetodo extends ClassVisitor {
		private final String metodoObjetivo;
		private final String descriptorObjetivo;
		private final List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();

		public RecolectorReferenciasMetodo(String metodo, String descriptor) {
			super(obtenerVersionMaximaASM());
			this.metodoObjetivo = metodo;
			this.descriptorObjetivo = descriptor;
		}

		@Override
		public MethodVisitor visitMethod(int acceso, String nombre, String descriptor, String firma,
				String[] excepciones) {
			if (nombre.equals(metodoObjetivo) && descriptor.equals(descriptorObjetivo))
				return new RecolectorReferencias();
			return null;
		}

		public List<ArchivoDeMod.Referencia> obtenerResultados() {
			return new ArrayList<>(resultados);
		}

		private class RecolectorReferencias extends MethodVisitor {
			public RecolectorReferencias() {
				super(obtenerVersionMaximaASM());
			}

			@Override
			public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor,
					boolean esInterfaz) {
				resultados.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true));
			}

			@Override
			public void visitFieldInsn(int opcode, String propietario, String nombre, String descriptor) {
				resultados.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, false));
			}
		}
	}

	static class RecolectorLlamadasAMetodo extends ClassVisitor {
		private final String claseObjetivo;
		private final String metodoObjetivo;
		private final String descriptorObjetivo;
		private final List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();

		private String claseActual;

		public RecolectorLlamadasAMetodo(String clase, String metodo, String descriptor) {
			super(obtenerVersionMaximaASM());
			this.claseObjetivo = clase;
			this.metodoObjetivo = metodo;
			this.descriptorObjetivo = descriptor;
		}

		@Override
		public void visit(int version, int access, String name, String signature, String superName,
				String[] interfaces) {
			this.claseActual = name;
		}

		@Override
		public MethodVisitor visitMethod(int acceso, String nombreMetodoOrigen, String descriptorMetodoOrigen,
				String firma, String[] excepciones) {
			return new RecolectorLlamadas(nombreMetodoOrigen, descriptorMetodoOrigen);
		}

		public List<ArchivoDeMod.Referencia> obtenerResultados() {
			return new ArrayList<>(resultados);
		}

		private class RecolectorLlamadas extends MethodVisitor {
			private final String metodoOrigen;
			private final String descriptorOrigen;

			public RecolectorLlamadas(String metodoOrigen, String descriptorOrigen) {
				super(obtenerVersionMaximaASM());
				this.metodoOrigen = metodoOrigen;
				this.descriptorOrigen = descriptorOrigen;
			}

			@Override
			public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor,
					boolean esInterfaz) {
				if (propietario.equals(claseObjetivo) && nombre.equals(metodoObjetivo)
						&& descriptor.equals(descriptorObjetivo)) {
					resultados.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true, claseActual,
							metodoOrigen, descriptorOrigen));
				}
			}
		}
	}

	public static int obtenerVersionMaximaASM() {
		try {
			Class<?> clase = Opcodes.class;
			for (int i = 10; i >= 4; i--) {
				String nombreCampo = "ASM" + i;
				try {
					java.lang.reflect.Field campo = clase.getField(nombreCampo);
					return campo.getInt(null);
				} catch (NoSuchFieldException e) {
					/* seguir */ }
			}
			return (4 << 16) | (0 << 8);
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener la versión ASM", e);
		}
	}

	public static List<String> obtenerNombreModuloInfo(byte[] classBytes) {
		List<String> modulos = new ArrayList<>();
		ClassReader classReader = new ClassReader(classBytes);
		ClassVisitor classVisitor = new ClassVisitor(obtenerVersionMaximaASM()) {
			@Override
			public ModuleVisitor visitModule(String name, int access, String version) {
				if (name != null && !name.isEmpty())
					modulos.add(name);
				return null;
			}
		};
		classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
		return modulos;
	}
}