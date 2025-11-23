package com.asbestosstar.crashdetector.parches;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

/**
 * Solo usar Si Hay ASM o hay errores dificil detecter!
 */
public class TransASM {

	public static byte[] obtenerBytes(String clase,byte[] bytes) {
		ClassReader classReader = new ClassReader(bytes);
		ClassNode classNode = new ClassNode();
		classReader.accept(classNode, 0);
		Parche.applicarParches(classNode, clase);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(cw);
		return cw.toByteArray();
	}
	
	
}
