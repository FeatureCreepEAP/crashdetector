package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta problemas con Reign of Nether que causan un error de Registry Object
 * no presente en MusicManager, relacionado con el mixin de MusicManager.
 */
public class ErrorReignOfNetherMusicManager implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoReignOfNether = false;

	/**
	 * Método de compatibilidad — busca si Reign of Nether está presente en el
	 * contenido completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si Reign of Nether está presente en el contenido del registro
		if (consola.contenido_verificar != null) {
			if (consola.contenido_verificar
					.contains("Mixin class: com.solegendary.reignofnether.mixin.MusicManagerMixin")) {
				encontradoReignOfNether = true;
			}
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!encontradoReignOfNether)
			return false;

		return true;
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
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea específica con el handler de mixin de MusicManager
		if (linea.contains("at net.minecraft.client.sounds.MusicManager") && linea.contains("$reignofnether$tick")
				&& encontradoReignOfNether) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de Reign of Nether
			mensaje = MonitorDePID.idioma.errorReignOfNetherMusicManager() + Verificaciones.nl_html;
			activado = true;

		}

	}

	@Override
	public Verificaciones nueva() {
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

		return t.contains("at net.minecraft.client.sounds.MusicManager") && t.contains("$reignofnether$tick");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}