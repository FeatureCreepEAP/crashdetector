package com.asbestosstar.crashdetector.parches.minecraft;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.ParcheClassNode;

/**
 * Parche que escribe el archivo "SOY_PIRATA" justo antes de lanzar
 * AuthenticationException en fetchProperties(), sin importar el código de
 * error.
 */
public class ParcheSoyPirata implements ParcheClassNode {

	public static final String id = "soy_pirata";
	public static File SOY_PIRATA = new File(id);

	@Override
	public Set<String> clases() {
		Set<String> clases = new HashSet<>();
		clases.add("com/mojang/authlib/yggdrasil/YggdrasilUserApiService");
		return clases;
	}

	@Override
	public Parche<org.objectweb.asm.tree.ClassNode> nuevo() {
		return new ParcheSoyPirata();
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public String nombre_de_gui() {
		return "AntiPirata";
	}

	@Override
	public void parchClassNode(org.objectweb.asm.tree.ClassNode node, String nombre) {
		for (org.objectweb.asm.tree.MethodNode method : node.methods) {
			if ("fetchProperties".equals(method.name)
					&& "()Lcom/mojang/authlib/minecraft/UserProperties;".equals(method.desc)) {
				inyectarLlamadaAntesDeThrow(method);
				return;
			}
		}
	}

	/**
	 * Inyecta una llamada estática a escribirArchivoSoyPirate() inmediatamente
	 * antes de la instrucción "throw e.toAuthenticationException();".
	 */
	private void inyectarLlamadaAntesDeThrow(org.objectweb.asm.tree.MethodNode method) {
		AbstractInsnNode actual = method.instructions.getFirst();
		while (actual != null) {
			if (actual.getOpcode() == Opcodes.INVOKEVIRTUAL) {
				MethodInsnNode min = (MethodInsnNode) actual;
				if ("toAuthenticationException".equals(min.name)
						&& "com/mojang/authlib/exceptions/MinecraftClientHttpException".equals(min.owner)
						&& "()Lcom/mojang/authlib/exceptions/AuthenticationException;".equals(min.desc)) {

					// Inyectar llamada estática justo antes
					InsnList inyeccion = new InsnList();
					inyeccion.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
							"parches/minecraft/ParcheSoyPirate",
							"escribirArchivoSoyPirata", "()V", false));

					method.instructions.insertBefore(actual, inyeccion);
					return;
				}
			}
			actual = actual.getNext();
		}
	}

	/**
	 * Método estático que crea el archivo "SOY_PIRATA" en el directorio actual. Se
	 * invoca desde el bytecode inyectado.
	 */
	public static void escribirArchivoSoyPirata() {
		try {
			SOY_PIRATA.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}