package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta problemas con Reign of Nether que causan un error de Registry Object
 * no presente en MusicManager, relacionado con el mixin de MusicManager.
 */
public class ErrorReignOfNetherMusicManager implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String MIXIN_MUSIC_MANAGER = "Mixin class: com.solegendary.reignofnether.mixin.MusicManagerMixin";
	private static final String MUSIC_MANAGER = "at net.minecraft.client.sounds.MusicManager";
	private static final String REIGN_OF_NETHER_TICK = "$reignofnether$tick";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MIXIN_MUSIC_MANAGER, REIGN_OF_NETHER_TICK };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Reign of Nether causa un
	 * problema con Registry Object no presente en MusicManager.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea específica con el handler de mixin de MusicManager
		if (linea.contains(MUSIC_MANAGER) && linea.contains(REIGN_OF_NETHER_TICK)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de Reign of Nether
			mensaje = MonitorDePID.idioma.errorReignOfNetherMusicManager() + VerificacionesLegacy.nl_html;
			activado = true;
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ErrorReignOfNetherMusicManager();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Alta prioridad: rompe la carga del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorReignOfNetherMusicManager();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorReignOfNetherMusicManager())
				.construir();
	}

	@Override
	public String id() {
		return "error_reign_of_nether_music_manager";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad de Reign of Nether con MusicManager.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(MUSIC_MANAGER) && t.contains(REIGN_OF_NETHER_TICK);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}