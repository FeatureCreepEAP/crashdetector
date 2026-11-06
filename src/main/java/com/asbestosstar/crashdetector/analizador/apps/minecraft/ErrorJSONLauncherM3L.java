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
 * Detecta cuando M3L falla por corrupciÃ³n del archivo launcher_profiles.json.
 *
 * Los errores tÃ­picos son:
 * "Unable to read M3L installs!" con IOException
 * "Installation failed!" con IOException al leer/escribir JSON
 *
 * M3L modifica launcher_profiles.json para agregar perfiles. Si el archivo estÃ¡
 * corrupto o no se puede leer, el instalador no puede funcionar.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaM3L"
 * }, nota = "Cubre deteccion de corrupcion JSON en M3L y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaM3L" },
		nota = "Cubre deteccion de corrupcion JSON en M3L y falsos positivos")
public class ErrorJSONLauncherM3L implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneM3L = new IdentityHashMap<Consola, Boolean>();

	private static final String UNABLE_READ_M3L = "Unable to read M3L installs";

	private static final String INSTALLATION_FAILED = "Installation failed";

	private static final String M3L_CONTEXT = "m3l";

	@Override
	public String[] patronesRapidos() {
		return new String[] { UNABLE_READ_M3L, INSTALLATION_FAILED, M3L_CONTEXT };
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

		if (lineaContieneM3L(linea)) {
			tieneM3L.put(consola, true);
		}

		if (lineaContieneErrorJSON(linea) && tieneContextoM3L(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorJSON(String linea) {
		return linea.contains(UNABLE_READ_M3L) || linea.contains(INSTALLATION_FAILED);
	}

	private boolean lineaContieneM3L(String linea) {
		return linea.toLowerCase().contains(M3L_CONTEXT);
	}

	private boolean tieneContextoM3L(Consola consola) {
		Boolean encontrado = tieneM3L.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorJSONLauncherM3L();
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
		return MonitorDePID.idioma.mensajeErrorJSONLauncherM3L() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorJSONLauncherM3L();
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
		return "error_json_launcher_m3l";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
