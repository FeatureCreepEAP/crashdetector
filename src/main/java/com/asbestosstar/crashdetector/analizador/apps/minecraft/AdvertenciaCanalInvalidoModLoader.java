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
 * Detecta cuando ModLoader registra un canal de paquetes con nombre inválido.
 *
 * El error típico es:
 * "Invalid channel name: %s. Must be less than 16 characters."
 *
 * ModLoader usa canales de paquetes personalizados para comunicación cliente-servidor.
 * Si un mod registra un canal con nombre ≥ 16 caracteres, se lanza RuntimeException.
 * Es una advertencia porque puede ser un mod específico el causante, no todo el sistema.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaModLoader"
 * }, nota = "Cubre deteccion de canales invalidos en ModLoader y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaModLoader" },
		nota = "Cubre deteccion de canales invalidos en ModLoader y falsos positivos")
public class AdvertenciaCanalInvalidoModLoader implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneModLoader = new IdentityHashMap<Consola, Boolean>();

	private static final String INVALID_CHANNEL_NAME = "Invalid channel name";

	private static final String MODLOADER_CONTEXT = "modloader";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INVALID_CHANNEL_NAME, MODLOADER_CONTEXT };
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

		if (lineaContieneModLoader(linea)) {
			tieneModLoader.put(consola, true);
		}

		if (lineaContieneErrorCanal(linea) && tieneContextoModLoader(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorCanal(String linea) {
		return linea.contains(INVALID_CHANNEL_NAME);
	}

	private boolean lineaContieneModLoader(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(MODLOADER_CONTEXT);
	}

	private boolean tieneContextoModLoader(Consola consola) {
		Boolean encontrado = tieneModLoader.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new AdvertenciaCanalInvalidoModLoader();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeAdvertenciaCanalInvalidoModLoader() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAdvertenciaCanalInvalidoModLoader();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "advertencia_canal_invalido_modloader";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
