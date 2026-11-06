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
 * Detecta cuando Rift no puede leer un archivo JAR en la carpeta de mods.
 *
 * El error típico es:
 * "Could not read file &lt;file&gt; as a jar file"
 *
 * Esto ocurre cuando un archivo en la carpeta de mods no es un JAR válido.
 * Rift registra el error pero continúa con los demás mods, por lo que es
 * una advertencia de baja prioridad, no un error fatal.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaRift"
 * }, nota = "Cubre deteccion de JAR invalido en Rift y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaRift" },
		nota = "Cubre deteccion de JAR invalido en Rift y falsos positivos")
public class AdvertenciaJarNoValidoRift implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneRift = new IdentityHashMap<Consola, Boolean>();

	private static final String COULD_NOT_READ_AS_JAR = "Could not read file";

	private static final String AS_JAR_FILE = "as a jar file";

	private static final String RIFT_LOADER = "rift";

	@Override
	public String[] patronesRapidos() {
		return new String[] { COULD_NOT_READ_AS_JAR, AS_JAR_FILE, RIFT_LOADER };
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

		if (lineaContieneRift(linea)) {
			tieneRift.put(consola, true);
		}

		if (lineaContieneErrorJar(linea) && tieneContextoRift(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorJar(String linea) {
		return linea.contains(COULD_NOT_READ_AS_JAR) && linea.contains(AS_JAR_FILE);
	}

	private boolean lineaContieneRift(String linea) {
		return linea.toLowerCase().contains(RIFT_LOADER);
	}

	private boolean tieneContextoRift(Consola consola) {
		Boolean encontrado = tieneRift.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new AdvertenciaJarNoValidoRift();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500f; // Prioridad baja: es una advertencia, no un error critico
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeAdvertenciaJarNoValidoRift() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAdvertenciaJarNoValidoRift();
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
		return "advertencia_jar_no_valido_rift";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
