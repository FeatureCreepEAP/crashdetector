package com.asbestosstar.crashdetector;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.minecraft.TransformacionDeMinecraftCodigo0;
import com.asbestosstar.crashdetector.parches.minecraft.ParcheWaterMediaTL;

public class Transformaciones implements ClassFileTransformer {

	public static void init() {
		Parche.parches.add(new TransformacionDeMinecraftCodigo0());// TODO detecion para app/juego
		Parche.parches.add(new ParcheWaterMediaTL());

		
	}

	// Para FeatureCreep y Agentes
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		byte[] bytes = Parche.applicarParches(classfileBuffer, className);

		try {
			Class.forName("org.objectweb.asm.tree.ClassNode", false, Transformaciones.class.getClassLoader());

			ClassReader classReader = new ClassReader(bytes);
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);
			Parche.applicarParches(classNode, className);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			classNode.accept(cw);
			bytes = cw.toByteArray();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace(); No ASM
			CrashDetectorLogger.log("No ASM");
		}

		return bytes;

	}

}
