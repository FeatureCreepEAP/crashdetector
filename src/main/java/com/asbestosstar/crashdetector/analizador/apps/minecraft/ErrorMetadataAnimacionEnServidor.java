package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error crítico donde un mod intenta cargar una clase del lado del
 * cliente (AnimationMetadataSection) en un servidor dedicado, lo que provoca
 * una falla al iniciar.
 * 
 * Este error suele manifestarse con ModernFix presente, ya que ModernFix aplica
 * optimizaciones que exponen incompatibilidades de mods que no separan
 * correctamente lógica de cliente/servidor.
 */
public class ErrorMetadataAnimacionEnServidor implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";

	private boolean posibleErrorMetadata = false;
	private boolean modernFixPresente = false;
	private boolean esServidor = false;

	private static final String CLASS_METADATA_NOT_FOUND = "org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException";
	private static final String ANIMATION_METADATA_SECTION = "net.minecraft.client.resources.metadata.animation.AnimationMetadataSection";
	private static final String MODERNFIX = "Loaded configuration file for ModernFix";
	private static final String FORGE_SERVER = "Launching target 'forgeserver'";
	private static final String DEDICATED_SERVER = "DEDICATED_SERVER";

	@Override
	public String[] patronesRapidos() {
		return new String[] { CLASS_METADATA_NOT_FOUND, ANIMATION_METADATA_SECTION, MODERNFIX, FORGE_SERVER,
				DEDICATED_SERVER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(MODERNFIX)) {
			modernFixPresente = true;
		}

		if (linea.contains(FORGE_SERVER) || linea.contains(DEDICATED_SERVER)) {
			esServidor = true;
		}

		if (lineaContieneErrorMetadata(linea)) {
			posibleErrorMetadata = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Este método no se usa; el análisis se hace por línea.
	 * 
	 * Método legacy seguro: si existe contenido completo, conserva compatibilidad
	 * con el motor anterior.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenidoCompleto = consola.contenido_verificar;

		this.modernFixPresente = contenidoCompleto.contains(MODERNFIX);
		this.esServidor = contenidoCompleto.contains(FORGE_SERVER) || contenidoCompleto.contains(DEDICATED_SERVER);
		this.posibleErrorMetadata = contenidoCompleto.contains(CLASS_METADATA_NOT_FOUND)
				&& contenidoCompleto.contains(ANIMATION_METADATA_SECTION);
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleErrorMetadata && modernFixPresente && esServidor && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguir analizando
		if (this.activado || linea == null) {
			return;
		}

		// Verificación precisa en la línea principal del error
		if (!lineaContieneErrorMetadata(linea)) {
			return;
		}

		// Confirmar que ModernFix está presente
		if (!modernFixPresente && consola != null && consola.contenido_verificar != null) {
			modernFixPresente = consola.contenido_verificar.contains(MODERNFIX);
		}

		// Confirmar que es un servidor dedicado
		if (!esServidor && consola != null && consola.contenido_verificar != null) {
			String contenidoCompleto = consola.contenido_verificar;
			esServidor = contenidoCompleto.contains(FORGE_SERVER) || contenidoCompleto.contains(DEDICATED_SERVER);
		}

		if (modernFixPresente && esServidor) {
			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorMetadataAnimacionEnServidor() + enlaceHtml;
			this.activado = true;
			// No hace falta return explícito; la bandera evita más procesamiento
		}
	}

	private boolean lineaContieneErrorMetadata(String linea) {
		return linea.contains(CLASS_METADATA_NOT_FOUND) && linea.contains(ANIMATION_METADATA_SECTION);
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

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}