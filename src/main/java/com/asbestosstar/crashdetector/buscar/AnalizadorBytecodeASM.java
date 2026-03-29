package com.asbestosstar.crashdetector.buscar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
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
		if (bytes == null)
			return null;
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
			// Aquí retornamos estructuras vacías que pueden llenarse después
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
	 * Extrae los targets de la anotación @Mixin.
	 * 
	 * Soporta: - @Mixin(targets = { "a.b.C" }) - @Mixin(value = Foo.class)
	 * - @Mixin({ Foo.class, Bar.class })
	 */
	private static List<String> extraerTargetsMixin(ClassNode cn) {
		List<String> targets = new ArrayList<>();
		if (cn.visibleAnnotations == null) {
			return targets;
		}

		for (AnnotationNode ann : cn.visibleAnnotations) {
			if (ann.desc == null || !ann.desc.contains("org/spongepowered/asm/mixin/Mixin")) {
				continue;
			}

			if (ann.values == null) {
				break;
			}

			for (int i = 0; i < ann.values.size(); i += 2) {
				Object key = ann.values.get(i);
				Object value = ann.values.get(i + 1);

				// Caso: @Mixin(targets = { "a.b.C", "x.y.Z" })
				if ("targets".equals(key) && value instanceof List) {
					@SuppressWarnings("unchecked")
					List<Object> lista = (List<Object>) value;

					for (Object item : lista) {
						if (item == null) {
							continue;
						}

						if (item instanceof String) {
							String target = ((String) item).trim();
							if (!target.isEmpty() && !targets.contains(target)) {
								targets.add(target);
							}
						} else if (item instanceof Type) {
							Type t = (Type) item;
							if (t.getSort() == Type.OBJECT) {
								String nombre = t.getClassName();
								if (nombre != null && !nombre.isEmpty() && !targets.contains(nombre)) {
									targets.add(nombre);
								}
							}
						}
					}
					continue;
				}

				// Caso: @Mixin(Foo.class) o @Mixin(value = { Foo.class, Bar.class })
				if ("value".equals(key)) {
					extraerTargetsDeValue(value, targets);
				}
			}

			break;
		}

		return targets;
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
	 * Extrae métodos con @Inject o @Overwrite.
	 */
	private static List<MixinMetodoInfo> extraerMetodosMixin(ClassNode cn) {
		List<MixinMetodoInfo> resultados = new ArrayList<>();
		if (cn.methods == null)
			return resultados;

		for (MethodNode method : cn.methods) {
			if (method.visibleAnnotations == null)
				continue;
			for (AnnotationNode ann : method.visibleAnnotations) {
				if (ann.desc == null)
					continue;

				// @Inject
				if (ann.desc.contains("org/spongepowered/asm/mixin/injection/Inject")) {
					MixinMetodoInfo info = new MixinMetodoInfo(method.name, method.desc, new ArrayList<>(), false);
					if (ann.values != null) {
						for (int i = 0; i < ann.values.size(); i += 2) {
							Object key = ann.values.get(i);
							Object value = ann.values.get(i + 1);
							if ("method".equals(key) && value instanceof List) {
								@SuppressWarnings("unchecked")
								List<String> methodTargets = (List<String>) value;
								for (String t : methodTargets) {
									if (t != null && !t.isEmpty() && !info.obtenerTargets().contains(t)) {
										info.obtenerTargets().add(t);
									}
								}
							}
						}
					}
					resultados.add(info);
				}
				// @Overwrite
				else if (ann.desc.contains("org/spongepowered/asm/mixin/Overwrite")) {
					MixinMetodoInfo info = new MixinMetodoInfo(method.name, method.desc, new ArrayList<>(), true);
					resultados.add(info);
				}
			}
		}
		return resultados;
	}

	/**
	 * Extrae campos con @Shadow.
	 */
	private static List<MixinCampoInfo> extraerCamposMixin(ClassNode cn) {
		List<MixinCampoInfo> resultados = new ArrayList<>();
		if (cn.fields == null)
			return resultados;

		for (FieldNode field : cn.fields) {
			if (field.visibleAnnotations == null)
				continue;
			for (AnnotationNode ann : field.visibleAnnotations) {
				if (ann.desc != null && ann.desc.contains("org/spongepowered/asm/mixin/Shadow")) {
					resultados.add(new MixinCampoInfo(field.name, field.desc));
					break;
				}
			}
		}
		return resultados;
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
		for (String nombreClase : mod.obtenerTodosLosNombresDeClases()) {
			byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
			if (bytesClase != null) {
				try {
					ClassReader lector = new ClassReader(bytesClase);
					RecolectorLlamadasAMetodo recolector = new RecolectorLlamadasAMetodo(claseObjetivo, metodoObjetivo,
							descriptorObjetivo);
					lector.accept(recolector, ClassReader.SKIP_DEBUG);
					resultados.addAll(recolector.obtenerResultados());
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}
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

		public RecolectorInformacionMetodos() {
			super(obtenerVersionMaximaASM());
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
			public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor,
					boolean esInterfaz) {
				refMetodos.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true));
			}

			@Override
			public void visitFieldInsn(int opcode, String propietario, String nombre, String descriptor) {
				refCampos.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, false));
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

		public RecolectorLlamadasAMetodo(String clase, String metodo, String descriptor) {
			super(obtenerVersionMaximaASM());
			this.claseObjetivo = clase;
			this.metodoObjetivo = metodo;
			this.descriptorObjetivo = descriptor;
		}

		@Override
		public MethodVisitor visitMethod(int acceso, String nombre, String descriptor, String firma,
				String[] excepciones) {
			return new RecolectorLlamadas();
		}

		public List<ArchivoDeMod.Referencia> obtenerResultados() {
			return new ArrayList<>(resultados);
		}

		private class RecolectorLlamadas extends MethodVisitor {
			public RecolectorLlamadas() {
				super(obtenerVersionMaximaASM());
			}

			@Override
			public void visitMethodInsn(int opcode, String propietario, String nombre, String descriptor,
					boolean esInterfaz) {
				if (propietario.equals(claseObjetivo) && nombre.equals(metodoObjetivo)
						&& descriptor.equals(descriptorObjetivo)) {
					resultados.add(new ArchivoDeMod.Referencia(propietario, nombre, descriptor, true));
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