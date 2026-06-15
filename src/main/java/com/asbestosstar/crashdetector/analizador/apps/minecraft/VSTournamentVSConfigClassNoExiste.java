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
 * Detecta un fallo donde VS Tournament intenta usar una clase de configuración
 * antigua que ya no existe en versiones nuevas de Valkyrien Skies.
 *
 * <p>
 * Patrón típico:
 * <ul>
 * <li>java.lang.NoClassDefFoundError:
 * org/valkyrienskies/core/impl/config/VSConfigClass</li>
 * <li>org.valkyrienskies.tournament.TournamentMod.init(TournamentMod.java:18)</li>
 * </ul>
 *
 * <p>
 * Cuando ambas líneas aparecen juntas, normalmente significa que el usuario
 * tiene una versión vieja de VS Tournament que no funciona con Valkyrien Skies
 * moderno.
 */
public class VSTournamentVSConfigClassNoExiste implements VerificacionRapida {

	// Indica si apareció la clase faltante de Valkyrien Skies
	private boolean indicioClaseFaltante = false;

	// Indica si apareció la inicialización de VS Tournament
	private boolean indicioTournamentInit = false;

	// Indica si esta verificación ya fue activada
	private boolean activado = false;

	// Enlace a la línea representativa del error
	private String enlace = "";

	private static final String NO_CLASS_DEF = "java.lang.NoClassDefFoundError: org/valkyrienskies/core/impl/config/VSConfigClass";
	private static final String TOURNAMENT_INIT = "org.valkyrienskies.tournament.TournamentMod.init(TournamentMod.java";

	@Override
	public String[] patronesRapidos() {
		return new String[] { NO_CLASS_DEF, TOURNAMENT_INIT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(NO_CLASS_DEF)) {
			indicioClaseFaltante = true;
		}

		if (evento.linea.contains(TOURNAMENT_INIT)) {
			indicioTournamentInit = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		// Compatibilidad legacy: en modo streaming puro contenido_verificar puede ser
		// nulo
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Detección global ligera para evitar trabajo innecesario por línea
		String contenido = consola.contenido_verificar;

		if (contenido.contains(NO_CLASS_DEF)) {
			indicioClaseFaltante = true;
		}

		if (contenido.contains(TOURNAMENT_INIT)) {
			indicioTournamentInit = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return indicioClaseFaltante && indicioTournamentInit && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Solo activar cuando ya están presentes ambos indicios globales
		if (activado || !indicioClaseFaltante || !indicioTournamentInit || linea == null) {
			return;
		}

		// Preferimos enlazar la línea del NoClassDefFoundError
		if (linea.contains(NO_CLASS_DEF)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Respaldo por si esa línea no está disponible en el formato del log
		if (linea.contains(TOURNAMENT_INIT)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new VSTournamentVSConfigClassNoExiste();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1420;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeVSTournamentVSConfigClassNoExiste() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreVSTournamentVSConfigClassNoExiste();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "vs_tournament_vsconfigclass_no_existe";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}