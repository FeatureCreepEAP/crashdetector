package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores relacionados con el sistema de sonido, específicamente el
 * error "Error starting SoundSystem. Turning off sounds & music" asociado con
 * SoundPhysicsMod.
 */
public class ErrorSistemaSonido implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificarPorLinea(Consola, String, int)}, llamada por el analizador
	 * línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el mensaje: "Error starting SoundSystem. Turning off sounds & music" en
	 * la línea actual y registra el enlace correspondiente.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		if (linea.contains("Error starting SoundSystem. Turning off sounds & music")) {
			mensaje = MonitorDePID.idioma.errorSistemaSonido() + Verificaciones.nl_html;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
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
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
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

	@Override
	public String id() {
		return "error_en_sistema_sonido";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene exactamente el mensaje de error del sistema de
	 * sonido.</li>
	 * </ul>
	 * Es intencionadamente conservador: mejor un falso negativo que marcar un trazo
	 * que no corresponda a este problema.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains("Error starting SoundSystem. Turning off sounds & music");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
