package com.asbestosstar.crashdetector.parches.minecraft;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

import com.asbestosstar.crashdetector.hw.gpu.HacerVerificacionGPU;
import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.ParcheClassNode;

/**
 * Parche que inyecta una llamada al verificador de GPU al final de los constructores
 * de las clases principales del cliente de Minecraft.
 * 
 * Clases objetivo (múltiples nombres debido a diferentes mappings):
 * - game.Client (FCI Inglés)
 * - net.minecraft.class_310 (FAIN)
 * - net.minecraft.client.Minecraft (SugarCane/Oficial)
 * 
 * El parche se activa una sola vez para evitar inyecciones duplicadas.
 */
public class TransformacionDeMinecraftCodigoGPU implements ParcheClassNode {

    // Bandera para asegurar que el parche se aplica solo una vez.
    private static boolean completa_cliente = false;

    @Override
    public Set<String> clases() {
        Set<String> clases = new HashSet<>();
        // Añadimos todas las posibles clases del cliente de Minecraft
        clases.add("game.Client");
        clases.add("net.minecraft.class_310");
        clases.add("net.minecraft.client.Minecraft");
        return clases;
    }

    @Override
    public void parchClassNode(ClassNode node, String nombre_de_clase) {
        // Solo aplicamos el parche si aún no se ha completado y es una clase cliente
        if (!completa_cliente) {
            
            System.out.println("CD: Parche de verificación de GPU encontrado para la clase: " + nombre_de_clase);

            // Iteramos sobre todos los métodos de la clase para encontrar los constructores
            node.methods.forEach(method -> {
                // Un constructor en bytecode siempre se llama "<init>" y su descriptor termina en "V" (void)
                // aunque técnicamente no devuelve nada, el descriptor describe los parámetros.
                // El descriptor más común es "()V" para un constructor sin argumentos.
                if (method.name.equals("<init>")) {
                    
                    System.out.println("CD: Inyectando verificación de GPU en el constructor de: " + nombre_de_clase);

                    // Creamos la lista de instrucciones a inyectar
                    InsnList insnList = new InsnList();
                    
                    // Añadimos la llamada al método estático 'hacer()' de nuestra clase de verificación
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                            HacerVerificacionGPU.class.getCanonicalName().replace('.', '/'),
                            "hacer",
                            "()V", // Descriptor del método: no recibe argumentos, no devuelve nada
                            false));

                    // --- Lógica para insertar al final del constructor ---
                    // Buscamos la última instrucción, que suele ser un 'return' (Opcodes.RETURN).
                    // Insertamos nuestro código justo antes de esa instrucción de retorno.
                    
                        AbstractInsnNode ultimaInstruccion = method.instructions.getLast();
                        
                        // Verificamos si la última instrucción es un retorno para insertar antes de él.
                        // Esto es más seguro que insertar al final, ya que el bytecode debe terminar con un return.
                        if (ultimaInstruccion.getOpcode() >= Opcodes.IRETURN && ultimaInstruccion.getOpcode() <= Opcodes.RETURN) {
                             method.instructions.insertBefore(ultimaInstruccion, insnList);
                        } else {
                            // Caso de seguridad: si no hay un 'return' explícito (raro en constructores),
                            // lo añadimos al final.
                            method.instructions.add(insnList);
                        }
            
                }
            });

            // Marcamos el parche como completado para no volver a procesarlo
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
        // Asumimos que MonitorDePID.idioma tiene un método para obtener el nombre
        // return MonitorDePID.idioma.transformacionDeMinecraftCodigoGPU();
        return "Verificación de GPU al iniciar el cliente";
    }

    @Override
    public boolean predeterminado() {
        // Se recomienda activar este parche por defecto
        return true;
    }
}
