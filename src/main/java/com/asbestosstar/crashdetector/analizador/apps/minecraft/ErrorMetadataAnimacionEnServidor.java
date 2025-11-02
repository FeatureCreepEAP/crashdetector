package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta el error crítico donde un mod intenta cargar una clase del lado del
 * cliente (AnimationMetadataSection) en un servidor dedicado, lo que provoca
 * una falla al iniciar.
 * 
 * Este error suele manifestarse con ModernFix presente, ya que ModernFix aplica
 * optimizaciones que exponen incompatibilidades de mods que no separan
 * correctamente lógica de cliente/servidor.
 */
public class ErrorMetadataAnimacionEnServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		// Este método no se usa; el análisis se hace por línea
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguir analizando
		if (this.activado) {
			return;
		}

		String contenidoCompleto = consola.contenido_verificar;

		// Verificar si la línea actual contiene el error específico
		if (linea.contains("org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException")
				&& linea.contains("net.minecraft.client.resources.metadata.animation.AnimationMetadataSection")) {

			// Confirmar que ModernFix está presente
			boolean modernFixPresente = contenidoCompleto.contains("Loaded configuration file for ModernFix");

			// Confirmar que es un servidor dedicado
			boolean esServidor = contenidoCompleto.contains("Launching target 'forgeserver'")
					|| contenidoCompleto.contains("DEDICATED_SERVER");

			if (modernFixPresente && esServidor) {
				String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				this.mensaje = MonitorDePID.idioma.errorMetadataAnimacionEnServidor() + enlaceHtml;
				this.activado = true;
				// No hace falta return explícito; la bandera evita más procesamiento
			}
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		String t = trazo.trace;
		return t.contains("ClassMetadataNotFoundException") && t.contains("AnimationMetadataSection");
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMetadataAnimacionEnServidor();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 1200.0f; // Alta: impide iniciar el servidor
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionErrorMetadataAnimacionEnServidor()).construir();
	}

	@Override
	public String id() {
		return "metadata_animacion_en_servidor";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorMetadataAnimacionEnServidor();
	}
}