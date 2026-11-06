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
 * Detecta cuando Canary/Neptune no puede acceder a la base de datos.
 *
 * El error típico es:
 * "DatabaseAccessException" o "Could not add database"
 *
 * Canary usa bases de datos (SQLite por defecto, u otras mediante adaptadores)
 * para almacenar usuarios, grupos, warps, kits, y banlist. Si la conexión falla,
 * el servidor no puede funcionar correctamente.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaCanary"
 * }, nota = "Cubre deteccion de error de acceso a base de datos en Canary/Neptune")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaCanary" },
		nota = "Cubre deteccion de error de acceso a base de datos en Canary/Neptune")
public class ErrorBaseDeDatosCanary implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneCanary = new IdentityHashMap<Consola, Boolean>();

	private static final String DATABASE_ACCESS = "DatabaseAccessException";

	private static final String COULD_NOT_ADD_DATABASE = "Could not add database";

	private static final String CANARY_CONTEXT = "canary";

	private static final String NEPTUNE_CONTEXT = "neptune";

	@Override
	public String[] patronesRapidos() {
		return new String[] { DATABASE_ACCESS, COULD_NOT_ADD_DATABASE, CANARY_CONTEXT, NEPTUNE_CONTEXT };
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
		return linea.contains(DATABASE_ACCESS) || linea.contains(COULD_NOT_ADD_DATABASE);
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
		return new ErrorBaseDeDatosCanary();
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
		return MonitorDePID.idioma.mensajeErrorBaseDeDatosCanary() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorBaseDeDatosCanary();
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
		return "error_base_de_datos_canary";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
