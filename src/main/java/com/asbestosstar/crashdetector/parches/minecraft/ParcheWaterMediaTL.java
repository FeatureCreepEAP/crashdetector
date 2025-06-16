package com.asbestosstar.crashdetector.parches.minecraft;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.ParcheClassNode;

public class ParcheWaterMediaTL implements ParcheClassNode {

	public static String id ="watermedia_tl";
	
    @Override
    public Set<String> clases() {
        Set<String> clases = new HashSet<>();
        clases.add("org.watermedia.loaders.FavricLoader");
        clases.add("org.watermedia.loaders.ForgeLoader");
        clases.add("org.watermedia.loaders.NeoForgeLoader");
        return clases;
    }

    @Override
    public Parche<ClassNode> nuevo() {
        return new ParcheWaterMediaTL();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String nombre_de_gui() {
        return "WATERMeDIA TL";
    }

    @Override
    public void parchClassNode(ClassNode node, String nombre) {
        for (MethodNode method : node.methods) {
            if ("tlcheck".equals(method.name)) {
                method.instructions = instrucionFalsa();
                method.maxStack = 1;
            }
        }
    }

    /**
     * Crea una lista de instrucciones que simplemente devuelve false
     */
    private InsnList instrucionFalsa() {
        InsnList il = new InsnList();
        il.add(new InsnNode(Opcodes.ICONST_0));  // Push 0 (false)
        il.add(new InsnNode(Opcodes.IRETURN));  // Return int
        
        return il;
    }
}