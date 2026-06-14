package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;

/**
 * Versión optimizada de NoTieneMemoria que usa el escaneo rápido.
 */
public class NoTieneMemoriaRapida extends NoTieneMemoria implements VerificacionRapida {

	@Override
	public String[] patronesRapidos() {
		return new String[] { "java.lang.OutOfMemoryError", "GC overhead limit exceeded", "Direct buffer memory",
				"unable to create new native thread", "Out of Memory Error", "Insufficient memory", "Problem with RAM",
				"Could not reserve enough space for", "The specified size exceeds the maximum representable size",
				"Invalid maximum heap size",
				"There is insufficient memory for the Java Runtime Environment to continue", "PermGen error",
				"-805306369" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		// En modo streaming, verificamos la línea que disparó la coincidencia.
		if (esProblemaMemoriaInsuficiente(evento.linea) || esProblemaMemoriaExcesiva(evento.linea)) {
			// Si la línea por sí sola es suficiente para detectar el problema.
			// NoTieneMemoria.verificar normalmente usa contenido_verificar completo.
			// Vamos a simular la detección activando las banderas.
			// Pero mejor aún, permitimos que NoTieneMemoria trabaje con lo que tenga.
			verificar(evento.consola);
		}

		// Fallback: si el contenido completo no está, al menos marcamos como activado
		// si la línea es clara
		if (!activado()) {
			if (evento.linea.contains("java.lang.OutOfMemoryError") || evento.linea.contains("Out of Memory")) {
				// Forzamos activación mínima
				verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
			}
		}
	}

	@Override
	public void verificarPorLinea(com.asbestosstar.crashdetector.Consola consola, String linea, int numero_de_linea) {
		if (esProblemaMemoriaInsuficiente(linea) || esProblemaMemoriaExcesiva(linea)) {
			// Usamos un truco: pasamos la línea como si fuera el contenido completo a
			// verificar()
			// Pero para no romper el estado de la consola, creamos una consola temporal o
			// usamos reflexión/acceso directo.
			// O simplemente implementamos la lógica aquí.

			// Para mantenerlo simple y compatible:
			if (linea.contains("java.lang.OutOfMemoryError") || linea.contains("Out of Memory")) {
				consola.agregarErrorALectador(numero_de_linea, this);
				// Llamamos a verificar con la línea como "contenido"
				String original = consola.contenido_verificar;
				consola.contenido_verificar = linea;
				super.verificar(consola);
				consola.contenido_verificar = original;
			}
		}
	}

	@Override
	public VerificacionRapida nueva() {
		return new NoTieneMemoriaRapida();
	}
}
