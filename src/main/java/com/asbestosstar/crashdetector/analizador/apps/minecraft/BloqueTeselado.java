package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Verificador que detecta errores relacionados con el teselado de bloques en
 * Minecraft.
 * <p>
 * Esta clase analiza la salida de la consola en busca de mensajes que indiquen
 * un fallo durante el proceso de "Tesselating block in world", un error común
 * en entornos de Minecraft cuando se intenta renderizar un bloque incompatible
 * o corrupto, generalmente causado por mods mal compatibles, versiones
 * incorrectas o problemas de renderizado.
 * </p>
 * 
 * @author asbestosstar
 */
public class BloqueTeselado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = ""; // Almacena el mensaje HTML

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		String patron = "(?sm).*Tesselating block in world$.*";
		if (contenidoConsola.matches(patron)) {
			mensaje = MonitorDePID.idioma.errorDeBloqueTeselado() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new BloqueTeselado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_bloque_teselado();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
	
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "bloqueteselado";
	}
	
}