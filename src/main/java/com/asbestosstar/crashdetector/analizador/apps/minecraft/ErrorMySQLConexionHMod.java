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
 * Detecta cuando HMod no puede conectar con la base de datos MySQL.
 *
 * El error tÃ­pico es:
 * "SEVERE: Unable to retreive connection" con SQLException
 *
 * HMod usa MySQL como fuente de datos opcional. Si la conexiÃ³n falla,
 * todas las operaciones de base de datos (usuarios, grupos, kits, warps)
 * fallan y el servidor no puede funcionar correctamente.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaHMod"
 * }, nota = "Cubre deteccion de error de conexiÃ³n MySQL en HMod y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaHMod" },
		nota = "Cubre deteccion de error de conexiÃ³n MySQL en HMod y falsos positivos")
public class ErrorMySQLConexionHMod implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneHMod = new IdentityHashMap<Consola, Boolean>();

	private static final String UNABLE_RETRIEVE_CONNECTION = "Unable to retreive connection";

	private static final String HMOD_CONTEXT = "hmod";

	private static final String MYSQL_SOURCE = "mysqlsource";

	private static final String SQL_CONNECTION = "sqlconnection";

	@Override
	public String[] patronesRapidos() {
		return new String[] { UNABLE_RETRIEVE_CONNECTION, HMOD_CONTEXT, MYSQL_SOURCE, SQL_CONNECTION };
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

		if (lineaContieneHMod(linea)) {
			tieneHMod.put(consola, true);
		}

		if (lineaContieneErrorMySQL(linea) && tieneContextoHMod(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorMySQL(String linea) {
		return linea.contains(UNABLE_RETRIEVE_CONNECTION);
	}

	private boolean lineaContieneHMod(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(HMOD_CONTEXT) || lower.contains(MYSQL_SOURCE) || lower.contains(SQL_CONNECTION);
	}

	private boolean tieneContextoHMod(Consola consola) {
		Boolean encontrado = tieneHMod.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMySQLConexionHMod();
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
		return MonitorDePID.idioma.mensajeErrorMySQLConexionHMod() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorMySQLConexionHMod();
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
		return "error_mysql_conexion_hmod";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
