package com.asbestosstar.crashdetector.parches.minecraft;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.ParcheClassNode;

/**
 * Parche que inyecta una llamada segura al verificador de GPU (HacerVerificacionGPU)
 * al final de los constructores de Minecraft.
 */
public class TransformacionDeMinecraftCodigoGPU implements ParcheClassNode {

    // Bandera para asegurar que el parche se aplica solo una vez por ejecución.
    private static boolean completa_cliente = false;

    @Override
    public Set<String> clases() {
        Set<String> clases = new HashSet<>();
        // Nombres posibles de la clase principal del cliente según distintos mappings
        clases.add("game.Client");
        clases.add("net.minecraft.class_310");
        clases.add("net.minecraft.client.Minecraft");
        return clases;
    }

    @Override
    public void parchClassNode(ClassNode node, String nombre_de_clase) {
        // Solo aplicamos si no se ha completado ya
        if (!completa_cliente) {

            System.out.println("CD: Parche de verificación de GPU encontrado para la clase: " + nombre_de_clase);

            // Iteramos sobre los métodos para encontrar el constructor
            for (MethodNode method : node.methods) {
                // Buscamos el constructor (<init>)
                if (method.name.equals("<init>")) {
                    
                    System.out.println("CD: Inyectando llamada a HacerVerificacionGPU.hacer() en el constructor.");

                    // Creamos la instrucción: INVOKESTATIC HacerVerificacionGPU.hacer()V
                    InsnList toInject = new InsnList();
                    toInject.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            "com/asbestosstar/crashdetector/hw/gpu/HacerVerificacionGPU", // Ruta interna de la clase (con /)
                            "hacer",                                                 // Nombre del método
                            "()V",                                                   // Descriptor (void)
                            false                                                    // No es interfaz
                    ));

                    // --- LÓGICA DE INSERCIÓN CORREGIDA ---
                    // Buscamos la instrucción RETURN dentro del método.
                    // Es vital insertar ANTES del return, no después.
                    // Usamos toArray() para evitar ConcurrentModificationException si modificamos la lista mientras iteramos.
                    for (AbstractInsnNode insn : method.instructions.toArray()) {
                        // En constructores normalmente solo hay RETURN (void return)
                        if (insn.getOpcode() == Opcodes.RETURN) {
                            method.instructions.insertBefore(insn, toInject);
                            // Nota: Si hubiera múltiples returns (raro en constructores simples), 
                            // esto inyectaría antes de cada uno. Para este caso es aceptable.
                        }
                    }
                }
            }

            // Marcamos como completado para no procesar otras clases objetivo
            completa_cliente = true;
        }
    }

    @Override
    public Parche<ClassNode> nuevo() {
        return new TransformacionDeMinecraftCodigoGPU();
    }

    @Override
    public String id() {
        return "transformacion_de_minecraft_codigo_gpu";
    }

    @Override
    public String nombre_de_gui() {
        return "Verificación de GPU al iniciar el cliente";
    }

    @Override
    public boolean predeterminado() {
        return true;
    }
}