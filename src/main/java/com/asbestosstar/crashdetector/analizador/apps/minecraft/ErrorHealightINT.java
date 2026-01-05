package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Verificación que detecta el error crítico causado por el mod 'healight' en
 * Minecraft 1.20.1 MCForge 47.10, donde se produce un
 * 'java.lang.NoSuchFieldError: INT'.
 * 
 * Este error ocurre porque el mod 'healight' hace referencia a un campo
 * estático 'INT' que ya no existe en la clase 'AttributeModifier.Operation' en
 * versiones recientes de Minecraft (1.20+), lo que provoca un fallo al
 * inicializar entidades.
 */
public class ErrorHealightINT implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	/**
	 * Analiza el contenido del log en busca del patrón de error específico
	 * relacionado con 'healight' y el campo faltante 'INT'.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón que detecta la cadena de errores típica:
		// Caused by: java.lang.NoSuchFieldError: INT
		// y que aparezca en contexto de inicialización de LivingEntity o EntityType
		if (contenido.contains("java.lang.NoSuchFieldError: INT") && (contenido.contains("LivingEntity.<clinit>")
				|| contenido.contains("EntityType.<clinit>") || contenido.contains("net.minecraft.world.entity"))) {

			// Verificar si el mod 'healight' está presente en la lista de mods cargados
			if (contenido.contains("healight")) {
				this.mensaje = MonitorDePID.idioma.errorHealightINT();
				this.activado = true;
			}
		}
	}

	/**
	 * Devuelve una nueva instancia de esta verificación.
	 */
	@Override
	public Verificaciones nueva() {
		return new ErrorHealightINT();
	}

	/**
	 * Indica si se detectó el error durante la verificación.
	 */
	@Override
	public boolean activado() {
		return this.activado;
	}

	/**
	 * Devuelve el mensaje de error formateado para mostrar al usuario.
	 */
	public String obtenerMensaje() {
		return this.mensaje;
	}

	/**
	 * Prioridad alta, ya que impide el inicio del juego.
	 */
	@Override
	public float prioridad() {
		return 900.0f;
	}

	/**
	 * Devuelve una solución rápida (QuickFix) sugerida.
	 */
	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionHealightINT()).construir();
	}

	/**
	 * Identificador único de este tipo de error.
	 */
	@Override
	public String id() {
		return "error_healight_int";
	}

	/**
	 * Nombre legible del error para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorHealightINT();
	}

	/**
	 * Este error no requiere análisis de trazas específicas (stack trace), ya que
	 * se detecta por contenido del log general.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		String contenido = trazo.trace;
		if (contenido.contains("java.lang.NoSuchFieldError: INT") && (contenido.contains("LivingEntity.<clinit>")
				|| contenido.contains("EntityType.<clinit>") || contenido.contains("net.minecraft.world.entity"))) {
			return true;
		}

		return false;
	}

	@Override
	public String mensaje() {
		// TODO Auto-generated method stub
		return mensaje;
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
	
	
}