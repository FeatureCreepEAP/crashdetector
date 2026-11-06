package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.IdentityHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta cuando un comando de Canary/Neptune falla al registrarse.
 *
 * Los errores típicos son:
 * "CommandDependencyException" - el comando padre no está registrado
 * "DuplicateCommandException" - "The command X already exists!"
 * "Failed to set up system commands"
 *
 * Canary usa un sistema de comandos jerárquico. Si un comando depende de un
 * padre que no existe o si dos plugins registran el mismo comando, se lanza
 * una excepción y el comando no funciona.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaCanary"
 * }, nota = "Cubre deteccion de error de comando en Canary/Neptune")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaCanary" },
		nota = "Cubre deteccion de error de comando en Canary/Neptune")
public class ErrorComandoCanary implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneCanary = new IdentityHashMap<Consola, Boolean>();

	private static final String COMMAND_DEPENDENCY = "CommandDependencyException";

	private static final String DUPLICATE_COMMAND = "DuplicateCommandException";

	private static final String FAILED_SYSTEM_COMMANDS = "Failed to set up system commands";

	private static final String CANARY_CONTEXT = "canary";

	private static final String NEPTUNE_CONTEXT = "neptune";

	@Override
	public String[] patronesRapidos() {
		return new String[] { COMMAND_DEPENDENCY, DUPLICATE_COMMAND, FAILED_SYSTEM_COMMANDS, CANARY_CONTEXT, NEPTUNE_CONTEXT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || consola == null || linea == null) {
			return;
		}

		if (lineaContieneCanary(linea)) {
			tieneCanary.put(consola, true);
		}

		if (lineaContieneError(linea) && tieneContextoCanary(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneError(String linea) {
		return linea.contains(COMMAND_DEPENDENCY) || linea.contains(DUPLICATE_COMMAND)
				|| linea.contains(FAILED_SYSTEM_COMMANDS);
	}

	private boolean lineaContieneCanary(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(CANARY_CONTEXT) || lower.contains(NEPTUNE_CONTEXT);
	}

	private boolean tieneContextoCanary(Consola consola) {
		Boolean encontrado = tieneCanary.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorComandoCanary();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1800f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorComandoCanary() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorComandoCanary();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ERROR;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "error_comando_canary";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
