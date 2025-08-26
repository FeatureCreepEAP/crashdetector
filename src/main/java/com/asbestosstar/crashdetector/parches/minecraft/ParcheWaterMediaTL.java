package com.asbestosstar.crashdetector.parches.minecraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        clases.add("org.watermedia.loaders.FavricMCLoader");
        clases.add("org.watermedia.loaders.ForgeMCLoader");
        clases.add("org.watermedia.loaders.NeoFLoader");
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
        System.out.println("Aplicando parche TL a clase: " + node.name);
        
        // Creamos una nueva lista de métodos para evitar problemas de modificación concurrente
        List<MethodNode> nuevosMetodos = new ArrayList<>();
        boolean modificado = false;
        
        for (MethodNode method : node.methods) {
            // Verificamos el nombre y descriptor del método
            if ("tlcheck".equals(method.name) && "()Z".equals(method.desc)) {
                System.out.println("Encontrado método tlcheck en " + node.name);
                
                // EN LUGAR DE MODIFICAR EL MÉTODO EXISTENTE, CREAMOS UNO NUEVO
                // Esto evita problemas con la información residual que causa el NullPointerException
                MethodNode nuevoMetodo = new MethodNode(
                    Opcodes.ASM5,  // Usamos la versión actual de ASM
                    method.access,
                    method.name,
                    method.desc,
                    method.signature,
                    method.exceptions == null ? null : method.exceptions.toArray(new String[0])
                );
                
                nuevoMetodo.tryCatchBlocks = null;
                nuevoMetodo.localVariables = null;
                nuevoMetodo.maxStack = 0; 
                nuevoMetodo.maxLocals = 0; 
                
                nuevoMetodo.instructions = instrucionFalsa();
                
                nuevosMetodos.add(nuevoMetodo);
                modificado = true;
                System.out.println("Método tlcheck reemplazado con éxito");
            } else {
                nuevosMetodos.add(method);
            }
        }
        
        if (modificado) {
            // Reemplazamos la lista completa de métodos
            node.methods = nuevosMetodos;
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