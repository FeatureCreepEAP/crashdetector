package com.asbestosstar.crashdetector.pruebas;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Se coloca sobre una clase del programa (por ejemplo una verificación del
 * analizador) para declarar qué pruebas unitarias la cubren.
 *
 * Ejemplo:
 * <pre>
 * {@literal @}ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaNullPointer" },
 * nota = "Cubre el disparador por línea y el reinicio global")
 * public class NullPointer implements Verificaciones { ... }
 * </pre>
 *
 * La prueba {@code PruebaSimetriaDeAnotaciones} comprueba que cada enlace
 * declarado aquí tenga su contraenlace {@code @PruebaDe} en la clase de
 * prueba correspondiente, y viceversa.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProbadoPor {

	/**
	 * Nombres completos (con paquete) de las clases de prueba que cubren esta
	 * clase. Se declaran como cadenas y no como objetos {@code Class} para
	 * evitar una dependencia de compilación desde el programa principal hacia
	 * el árbol de pruebas.
	 */
	String[] valor();

	/** Nota opcional sobre el alcance de la cobertura. */
	String nota() default "";
}
