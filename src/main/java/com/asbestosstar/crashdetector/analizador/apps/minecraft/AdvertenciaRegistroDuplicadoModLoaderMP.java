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
 * Detecta cuando ModLoaderMP encuentra un registro duplicado de GUI o entidad.
 *
 * Los errores típicos son:
 * "RegisterGUI error: inventoryType already registered."
 * "RegisterNetClientHandlerEntity error: entityId already registered."
 * "RegisterNetClientHandlerEntity error: entityId cannot be greater than 255."
 *
 * Estos son advertencias porque ModLoaderMP registra el error pero continúa
 * funcionando — solo el registro duplicado falla, no todo el sistema.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaModLoaderMP"
 * }, nota = "Cubre deteccion de registro duplicado en ModLoaderMP")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaModLoaderMP" },
		nota = "Cubre deteccion de registro duplicado en ModLoaderMP")
public class AdvertenciaRegistroDuplicadoModLoaderMP implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneModLoaderMP = new IdentityHashMap<Consola, Boolean>();

	private static final String ALREADY_REGISTERED = "already registered";

	private static final String MODLOADERMP_CONTEXT = "modloadermp";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ALREADY_REGISTERED, MODLOADERMP_CONTEXT };
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

		if (lineaContieneModLoaderMP(linea)) {
			tieneModLoaderMP.put(consola, true);
		}

		if (lineaContieneError(linea) && tieneContextoModLoaderMP(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneError(String linea) {
		return linea.contains(ALREADY_REGISTERED);
	}

	private boolean lineaContieneModLoaderMP(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(MODLOADERMP_CONTEXT);
	}

	private boolean tieneContextoModLoaderMP(Consola consola) {
		Boolean encontrado = tieneModLoaderMP.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new AdvertenciaRegistroDuplicadoModLoaderMP();
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
		return MonitorDePID.idioma.mensajeAdvertenciaRegistroDuplicadoModLoaderMP() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAdvertenciaRegistroDuplicadoModLoaderMP();
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
		return "advertencia_registro_duplicado_modloadermp";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
