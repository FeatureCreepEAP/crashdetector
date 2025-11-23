package com.asbestosstar.crashdetector;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

//import org.objectweb.asm.ClassReader;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.tree.ClassNode;

import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.TransASM;
import com.asbestosstar.crashdetector.parches.minecraft.ParcheWaterMediaTL;
import com.asbestosstar.crashdetector.parches.minecraft.PreferIPv4StackParch;
import com.asbestosstar.crashdetector.parches.minecraft.TransformacionDeMinecraftCodigo0;

public class Transformaciones implements ClassFileTransformer {

	public static void init() {
		if (hayASM()) {// Son ASM Parches, si tienese ASM Parches necesita esta verificacion o puede
//						// tener problemas
		Parche.parches.add(new TransformacionDeMinecraftCodigo0());// TODO detecion para app/juego
		Parche.parches.add(new ParcheWaterMediaTL());
		Parche.parches.add(new PreferIPv4StackParch());
		}
	}

	// Para FeatureCreep y MangoModLoader y Agentes
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		byte[] bytes = Parche.aplicarParches(classfileBuffer, className);

		if (hayASM()) {
			bytes = TransASM.obtenerBytes(className, bytes);
		}

		return bytes;

	}

	/**
	 * Si ASM Existe
	 * 
	 * @return
	 */
	public static boolean hayASM() {
		try {
			Class.forName("org.objectweb.asm.tree.ClassNode", false, Transformaciones.class.getClassLoader());
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			CrashDetectorLogger.log("No ASM");
			return false;
		}
	}

}
