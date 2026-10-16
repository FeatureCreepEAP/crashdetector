package com.asbestosstar.crashdetector.pruebas;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Se coloca sobre una clase de prueba unitaria para declarar qué clases del
 * programa ejercita. Es el contraenlace de {@code @ProbadoPor}.
 *
 * Ejemplo:
 * <pre>
 * {@literal @}PruebaDe(valor = { "com.asbestosstar.crashdetector.analizador.general.NullPointer" })
 * public class PruebaNullPointer { ... }
 * </pre>
 *
 * La prueba {@code PruebaSimetriaDeAnotaciones} falla si un enlace aparece en
 * una sola dirección: toda clase con {@code @ProbadoPor} debe apuntar a una
 * prueba real, y toda prueba con {@code @PruebaDe} debe apuntar a una clase
 * real que la declare.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PruebaDe {

	/**
	 * Nombres completos (con paquete) de las clases del programa que esta
	 * prueba ejercita. Se declaran como cadenas por la misma razón que en
	 * {@code @ProbadoPor}: evitar ciclos de compilación entre main y test.
	 */
	String[] valor();

	/** Nota opcional sobre lo que se ejercita de cada clase. */
	String nota() default "";
}
