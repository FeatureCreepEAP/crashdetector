package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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
		return MonitorDePID.idioma.nombre_de_bloque_teselado();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;//TODO
	}

	
	
	

	@Override
	public String id() {
		return "bloqueteselado";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto.
	 * <p>
	 * Para evitar falsos positivos, solo marcamos el trazo como ocupado si:
	 * <ul>
	 * <li>El verificador ya se activó (hay un error de teselado detectado en el log
	 * completo), y</li>
	 * <li>El texto del trazo contiene explícitamente la cadena "Tesselating block
	 * in world".</li>
	 * </ul>
	 * Esto puede producir falsos negativos si el trazo no incluye esa línea
	 * concreta, pero minimiza casi por completo los falsos positivos.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains("Tesselating block in world");
	}
	
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"+this.getClass().getSimpleName()+".java";
	}
	
	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
	

}
