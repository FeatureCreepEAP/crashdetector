package com.asbestosstar.crashdetector.parches.minecraft;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.ParcheClassNode;

public class TransformacionDeMinecraftCodigo0 implements ParcheClassNode {

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

	public boolean completa_servidor = false;
	public boolean completa_cliente = false;

	public static boolean tiene_clase_cliente = false;

	@Override
	public Set<String> clases() {
		// TODO Auto-generated method stub
		Set<String> clases = new HashSet<String>();
		clases.add("net.minecraft.server.MinecraftServer");
		clases.add("game.Server");

		clases.add("game.Client");
		clases.add("net.minecraft.class_310");
		clases.add("net.minecraft.client.Minecraft");
		return clases;
	}

	@Override
	public void parchClassNode(ClassNode node, String nombre_de_clase) {
		// TODO Auto-generated method stub

		if (nombre_de_clase.equals("net.minecraft.server.MinecraftServer") || nombre_de_clase.equals("game.Server")) {

			if (!completa_servidor && !completa_cliente) {

				// si es un nombre de una clase en la lista , modificar el method incluir
				// hechoArchivoDeCodioError0 en la cima.
				node.methods.forEach(method -> {
					String methodName = method.name;
					String methodDesc = method.desc;

					if ((methodName.equals("shutdown") || methodName.equals("stopServer")
							|| methodName.equals("m_7041_") || methodName.equals("method_3782"))
							&& methodDesc.equals("()V")) {

						InsnList insnList = new InsnList();
						insnList.add(new InsnNode(Opcodes.ICONST_1));// es_servidor=true
						insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
								"com/asbestosstar/crashdetector/parches/minecraft/TransformacionDeMinecraftCodigo0",
								"hechoArchivoDeCodigoError0", "(Z)V", false));
						method.instructions.insert(insnList);
					}
				});
				completa_servidor = true;
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
						System.out.println("CD Transformando CLIENT");

						InsnList insnList = new InsnList();
						insnList.add(new InsnNode(Opcodes.ICONST_0)); // es_servidor=false
						insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
								"com/asbestosstar/crashdetector/parches/minecraft/TransformacionDeMinecraftCodigo0",
								"hechoArchivoDeCodigoError0", "(Z)V", false));
						method.instructions.insert(insnList);
					}
				});

				completa_cliente = true;

			}

		}

	}

	public static void hechoArchivoDeCodigoError0(boolean es_servidor) {
		System.out.println("hechoArchivoDeCodigoError0 " + String.valueOf(es_servidor));
		if (tiene_clase_cliente && es_servidor) {
		} else {
			try {
				MonitorDePID.ArchivoDeCodigoError0.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Parche<ClassNode> nuevo() {
		// TODO Auto-generated method stub
		return new TransformacionDeMinecraftCodigo0();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "transformacion_de_minecraft_codigo0";
	}

	@Override
	public String nombre_de_gui() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.transformacionDeMinecraftCodigo0();
	}

	@Override
	public boolean predeterminado() {
		return true;
	}

}
