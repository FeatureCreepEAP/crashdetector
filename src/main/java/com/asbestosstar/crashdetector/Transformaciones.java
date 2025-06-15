package com.asbestosstar.crashdetector;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;

public class Transformaciones implements ClassFileTransformer {

	// net.minecraft.server.MinecraftServer//todos pero no FCI
	// game.Server//FCI Ingles
	// TODO FCI Español

	// shutdown//FCI Ingles
	// TODO FCI Español
	// stopServer //SugarCane
	// m_7041_ //SRG
	// method_3782//FAIN
	// TODO OBF

	// game.Client//FCI Ingles
	// TODO FCI Español
	// net.minecraft.class_310 //FAIN
	// net.minecraft.client.Minecraft//SugarCane
	// TODO OBJ

	// shutdown FCI Ingles
	// TODO FCI Español
	// stop SugarCane
	// method_1592 FAIN
	// m_91395_ //SRG
	

	public static boolean completa_servidor = false;
	public static boolean completa_cliente = false;

	public static boolean tiene_clase_cliente = false;
//No funciona con Balm3
	public static ClassNode transformarASM(String nombre_de_clase, ClassNode node) {

		if (nombre_de_clase.equals("net.minecraft.server.MinecraftServer") || nombre_de_clase.equals("game.Server")) {

			if (!completa_servidor&&!completa_cliente) {

				// si es un nombre de una clase en la lista , modificar el method incluir
				// hechoArchivoDeCodioError0 en la cima.
//				node.methods.forEach(method -> {
//					String methodName = method.name;
//					String methodDesc = method.desc;
//
//					if ((methodName.equals("shutdown") || methodName.equals("stopServer")
//							|| methodName.equals("m_7041_") || methodName.equals("method_3782"))
//							&& methodDesc.equals("()V")) {
//
//		                InsnList insnList = new InsnList();
//		                insnList.add(new InsnNode(Opcodes.ICONST_1));//es_servidor=true
//		                insnList.add(new MethodInsnNode(
//		                    Opcodes.INVOKESTATIC,
//		                    "com/asbestosstar/crashdetector/Transformaciones",
//		                    "hechoArchivoDeCodioError0",
//		                    "(Z)V",
//		                    false
//		                ));
//		                method.instructions.insert(insnList);
//					}
//				});
				//completa_servidor = true;
			}

		} else if (nombre_de_clase.equals("net.minecraft.client.Minecraft")
				|| nombre_de_clase.equals("net.minecraft.class_310") || nombre_de_clase.equals("game.Client")) {
			tiene_clase_cliente = true;
			if (!completa_cliente) {

				node.methods.forEach(method -> {
					String methodName = method.name;
					String methodDesc = method.desc;

					if ((methodName.equals("shutdown") || methodName.equals("stop") || methodName.equals("m_91395_")
							|| methodName.equals("method_1592")) && methodDesc.equals("()V")) {
						System.out.println ("CD Transformando CLIENT");

		                InsnList insnList = new InsnList();
		                insnList.add(new InsnNode(Opcodes.ICONST_0)); //es_servidor=false
		                insnList.add(new MethodInsnNode(
		                    Opcodes.INVOKESTATIC,
		                    "com/asbestosstar/crashdetector/Transformaciones",
		                    "hechoArchivoDeCodioError0",
		                    "(Z)V",
		                    false
		                ));
		                method.instructions.insert(insnList);
					}
				});

				//completa_cliente = true;

			}

		}
		return node;
	}

	public static void hechoArchivoDeCodioError0(boolean es_servidor) {
		System.out.println("hechoArchivoDeCodioError0 "+String.valueOf(es_servidor));
		if (tiene_clase_cliente && es_servidor) {
		} else {
			try {
				MonitorDePID.ArchivoDeCodioError0.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Para FeatureCreep y Agentes
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		String nombre = className.replace("/", ".");

		if (nombre.equals("net.minecraft.server.MinecraftServer") || nombre.equals("net.minecraft.client.Minecraft")
				|| nombre.equals("net.minecraft.class_310") || nombre.equals("game.Server")
				|| nombre.equals("game.Client")) {
			ClassReader classReader = new ClassReader(classfileBuffer);
			// 使用ClassNode来接收解析后的类
			ClassNode classNode = new ClassNode();
			classReader.accept(classNode, 0);
			transformarASM(nombre,classNode);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

			classNode.accept(cw);

			return cw.toByteArray();

		}
		return classfileBuffer;
	}

	
}
