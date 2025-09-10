package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Analiza errores relacionados con el sistema de sonido, específicamente el
 * error "Error starting SoundSystem. Turning off sounds & music" asociado con
 * SoundPhysicsMod.
 */
public class ErrorSistemaSonido implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Analiza cada línea del registro buscando el mensaje específico de error de
		// sonido
		for (String linea : contenidoConsola.split(Verificaciones.nl)) {
			if (linea.contains("Error starting SoundSystem. Turning off sounds & music")) {
				mensaje = MonitorDePID.idioma.errorSistemaSonido() + Verificaciones.nl_html;
				activado = true;
				break; // Detiene al encontrar el primer error
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorSistemaSonido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 750.0f; // Prioridad media-alta - el juego funciona pero sin sonido
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_sistema_sonido();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_sistema_sonido())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_sistema_sonido())
				.agregarEtiqueta(MonitorDePID.idioma.paso3_sistema_sonido()).construir();
	}
}