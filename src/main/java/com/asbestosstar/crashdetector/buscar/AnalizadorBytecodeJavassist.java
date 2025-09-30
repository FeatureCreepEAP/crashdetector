package com.asbestosstar.crashdetector.buscar;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Opcode;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

/**
 * Analizador de bytecode utilizando la biblioteca Javassist. Proporciona
 * métodos para inspeccionar clases, métodos y referencias sin depender de ASM.
 */
public class AnalizadorBytecodeJavassist {

	public static List<ArchivoDeMod.Constante> analizarConstantesEnMetodo(ArchivoDeMod mod, String nombreClase,
			String nombreMetodo, String descriptor) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();

		try {
			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));

			// localizar el método
			javassist.bytecode.MethodInfo mi = null;
			for (javassist.bytecode.MethodInfo m : classFile.getMethods()) {
				if (m.getName().equals(nombreMetodo) && m.getDescriptor().equals(descriptor)) {
					mi = m;
					break;
				}
			}
			if (mi == null)
				return new ArrayList<>();
			if ((mi.getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0)
				return new ArrayList<>();

			CodeAttribute ca = mi.getCodeAttribute();
			if (ca == null)
				return new ArrayList<>();

			ConstPool cp = classFile.getConstPool();
			CodeIterator it = ca.iterator();

			List<ArchivoDeMod.Constante> resultados = new ArrayList<>();
			while (it.hasNext()) {
				int index = it.next();
				int op = it.byteAt(index) & 0xFF;

				switch (op) {
				// inmediatOS (iconst/…, fconst/…, lconst/…, dconst/…)
				case Opcode.ICONST_M1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(-1));
					break;
				case Opcode.ICONST_0:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(0));
					break;
				case Opcode.ICONST_1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(1));
					break;
				case Opcode.ICONST_2:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(2));
					break;
				case Opcode.ICONST_3:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(3));
					break;
				case Opcode.ICONST_4:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(4));
					break;
				case Opcode.ICONST_5:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(5));
					break;

				case Opcode.LCONST_0:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Long.valueOf(0L));
					break;
				case Opcode.LCONST_1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Long.valueOf(1L));
					break;

				case Opcode.FCONST_0:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Float.valueOf(0f));
					break;
				case Opcode.FCONST_1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Float.valueOf(1f));
					break;
				case Opcode.FCONST_2:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Float.valueOf(2f));
					break;

				case Opcode.DCONST_0:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Double.valueOf(0d));
					break;
				case Opcode.DCONST_1:
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Double.valueOf(1d));
					break;

				// enteros inmediatos de 8/16 bits
				case Opcode.BIPUSH: {
					int val = (byte) it.byteAt(index + 1); // signed
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(val));
					break;
				}
				case Opcode.SIPUSH: {
					int val = it.s16bitAt(index + 1); // signed short
					agregar(resultados, nombreClase, nombreMetodo, descriptor, Integer.valueOf(val));
					break;
				}

				// LDC / LDC_W / LDC2_W
				case Opcode.LDC: {
					int cpIndex = it.byteAt(index + 1) & 0xFF;
					agregarDesdeConstPool(resultados, nombreClase, nombreMetodo, descriptor, cp, cpIndex);
					break;
				}
				case Opcode.LDC_W: {
					int cpIndex = it.u16bitAt(index + 1);
					agregarDesdeConstPool(resultados, nombreClase, nombreMetodo, descriptor, cp, cpIndex);
					break;
				}
				case Opcode.LDC2_W: {
					int cpIndex = it.u16bitAt(index + 1);
					agregarDesdeConstPool(resultados, nombreClase, nombreMetodo, descriptor, cp, cpIndex);
					break;
				}
				default:
					break;
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
			// devolver el nombre de clase como String (formato punto)
			agregar(out, clase, metodo, desc, cp.getClassInfo(index));
			break;
		default:
			// otros tags (MethodHandle, MethodType, Dynamic) se pueden mapear si te
			// interesan
			agregar(out, clase, metodo, desc, ("<constpool tag " + tag + ">"));
			break;
		}
	}

	/**
	 * Analiza los métodos de una clase y sus referencias internas utilizando
	 * Javassist.
	 * 
	 * @param mod         Referencia al mod que contiene la clase
	 * @param nombreClase Nombre de la clase en formato interno (ej:
	 *                    "java/lang/Object")
	 * @return Lista con información detallada de los métodos
	 */
	public static List<ArchivoDeMod.InfoMetodo> analizarMetodos(ArchivoDeMod mod, String nombreClase) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();

		try {
			ClassPool pool = new ClassPool();
			pool.appendSystemPath();
			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));

			List<ArchivoDeMod.InfoMetodo> resultados = new ArrayList<>();
			for (javassist.bytecode.MethodInfo methodInfo : classFile.getMethods()) {
				String methodName = methodInfo.getName();
				String descriptor = methodInfo.getDescriptor();

				// Saltar métodos abstractos/nativos (sin bytecode para analizar)
				if ((methodInfo.getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
					resultados.add(
							new ArchivoDeMod.InfoMetodo(methodName, descriptor, new ArrayList<>(), new ArrayList<>()));
					continue;
				}

				List<ArchivoDeMod.Referencia> referenciasMetodos = new ArrayList<>();
				List<ArchivoDeMod.Referencia> referenciasCampos = new ArrayList<>();

				CtClass ctClass = pool.makeClass(classFile, false);
				CtMethod ctMethod = ctClass.getMethod(methodName, descriptor);

				ctMethod.instrument(new ExprEditor() {
					@Override
					public void edit(MethodCall m) {
						referenciasMetodos.add(convertirAMiReferencia(m));
					}

					@Override
					public void edit(FieldAccess f) {
						referenciasCampos.add(convertirAMiReferencia(f));
					}
				});

				resultados.add(
						new ArchivoDeMod.InfoMetodo(methodName, descriptor, referenciasMetodos, referenciasCampos));
			}
			return resultados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	/**
	 * Analiza los campos declarados en una clase utilizando Javassist.
	 * 
	 * @param mod         Referencia al mod que contiene la clase
	 * @param nombreClase Nombre de la clase en formato interno
	 * @return Lista con información de los campos declarados
	 */
	public static List<ArchivoDeMod.InfoCampo> analizarCampos(ArchivoDeMod mod, String nombreClase) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();

		try {
			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));
			List<ArchivoDeMod.InfoCampo> resultados = new ArrayList<>();

			// Obtener campos directamente desde ClassFile (más rápido y ligero)
			for (javassist.bytecode.FieldInfo fieldInfo : classFile.getFields()) {
				resultados.add(new ArchivoDeMod.InfoCampo(fieldInfo.getName(), fieldInfo.getDescriptor()));
			}
			return resultados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	/**
	 * Analiza las referencias dentro de un método específico utilizando Javassist.
	 * 
	 * @param mod          Referencia al mod que contiene la clase
	 * @param nombreClase  Nombre de la clase en formato interno
	 * @param nombreMetodo Nombre del método a analizar
	 * @param descriptor   Descriptor del método
	 * @return Lista de referencias encontradas en el método
	 */
	public static List<ArchivoDeMod.Referencia> analizarReferenciasEnMetodo(ArchivoDeMod mod, String nombreClase,
			String nombreMetodo, String descriptor) {
		byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
		if (bytesClase == null)
			return new ArrayList<>();

		try {
			ClassPool pool = new ClassPool();
			pool.appendSystemPath();
			ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytesClase)));

			// Buscar método específico usando ClassFile
			javassist.bytecode.MethodInfo methodInfo = null;
			for (javassist.bytecode.MethodInfo mi : classFile.getMethods()) {
				if (mi.getName().equals(nombreMetodo) && mi.getDescriptor().equals(descriptor)) {
					methodInfo = mi;
					break;
				}
			}

			// Validar si el método existe y tiene bytecode analizable
			if (methodInfo == null || (methodInfo.getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
				return new ArrayList<>();
			}

			// Coleccionar referencias del método específico
			List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();
			CtClass ctClass = pool.makeClass(classFile, false);
			CtMethod ctMethod = ctClass.getMethod(nombreMetodo, descriptor);

			// Instrumentar SOLO el método objetivo
			ctMethod.instrument(new ExprEditor() {
				@Override
				public void edit(MethodCall m) {
					resultados.add(convertirAMiReferencia(m));
				}

				@Override
				public void edit(FieldAccess f) {
					resultados.add(convertirAMiReferencia(f));
				}
			});

			return resultados;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new ArrayList<>();
		}
	}

	/**
	 * Busca todas las llamadas a un método específico en todo el mod utilizando
	 * Javassist.
	 * 
	 * @param mod                Referencia al mod que contiene las clases
	 * @param claseObjetivo      Clase objetivo del método (formato interno)
	 * @param metodoObjetivo     Nombre del método objetivo
	 * @param descriptorObjetivo Descriptor del método objetivo
	 * @return Lista de referencias que llaman al método objetivo
	 */
	public static List<ArchivoDeMod.Referencia> analizarReferenciasAMetodo(ArchivoDeMod mod, String claseObjetivo,
			String metodoObjetivo, String descriptorObjetivo) {
		List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();
		List<String> todosNombresClases = mod.obtenerTodosLosNombresDeClases();

		// Reutilizar ClassPool para todas las clases (mejor rendimiento)
		ClassPool pool = new ClassPool();
		pool.appendSystemPath();

		for (String className : todosNombresClases) {
			byte[] bytes = mod.obtenerBytesClase(className);
			if (bytes == null)
				continue;

			try {
				ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(bytes)));
				CtClass ctClass = pool.makeClass(classFile, false);

				// Recorrer SOLO métodos declarados en esta clase
				for (CtMethod method : ctClass.getDeclaredMethods()) {
					// Saltar métodos sin bytecode (abstractos/nativos)
					if ((method.getMethodInfo().getAccessFlags() & (AccessFlag.ABSTRACT | AccessFlag.NATIVE)) != 0) {
						continue;
					}

					// Instrumentar SOLO para buscar llamadas específicas
					method.instrument(new ExprEditor() {
						@Override
						public void edit(MethodCall m) {
							// Convertir a formato interno de nombres (barra invertida → punto)
							String owner = m.getClassName().replace('.', '/');
							if (owner.equals(claseObjetivo) && m.getMethodName().equals(metodoObjetivo)
									&& m.getSignature().equals(descriptorObjetivo)) {
								resultados.add(
										new ArchivoDeMod.Referencia(owner, metodoObjetivo, descriptorObjetivo, true));
							}
						}
					});
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}
		return resultados;
	}

	/**
	 * Convierte una referencia de Javassist (llamada a método) a nuestro formato
	 * estándar. Maneja la conversión de nombres de clase (punto → barra invertida).
	 * 
	 * @param m Referencia a método de Javassist
	 * @return Referencia en formato interno
	 */
	public static ArchivoDeMod.Referencia convertirAMiReferencia(MethodCall m) {
		return new ArchivoDeMod.Referencia(m.getClassName().replace('.', '/'), // Formato interno de ASM
				m.getMethodName(), m.getSignature(), true);
	}

	/**
	 * Convierte una referencia de Javassist (acceso a campo) a nuestro formato
	 * estándar. Maneja la conversión de nombres de clase (punto → barra invertida).
	 * 
	 * @param f Referencia a campo de Javassist
	 * @return Referencia en formato interno
	 */
	public static ArchivoDeMod.Referencia convertirAMiReferencia(FieldAccess f) {
		return new ArchivoDeMod.Referencia(f.getClassName().replace('.', '/'), // Formato interno de ASM
				f.getFieldName(), f.getSignature(), false);
	}
}