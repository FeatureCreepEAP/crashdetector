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
 * Detecta cuando M3L no detecta correctamente el sistema operativo.
 *
 * M3L usa commons-lang3 SystemUtils para detectar Windows/Mac, y cualquier
 * otro OS se clasifica como Linux. En Solaris/SPARC, esto significa que
 * M3L se comporta como si fuera Linux, lo cual puede causar problemas con
 * rutas de directorios o archivos nativos.
 *
 * Esto es una advertencia de baja prioridad porque M3L puede funcionar
 * parcialmente en Solaris, pero las rutas pueden ser incorrectas.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaM3L"
 * }, nota = "Cubre deteccion de OS no detectado en M3L y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaM3L" },
		nota = "Cubre deteccion de OS no detectado en M3L y falsos positivos")
public class AdvertenciaOSNoDetectadoM3L implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneM3L = new IdentityHashMap<Consola, Boolean>();

	private static final String M3L_CLIENT_INSTALLER = "M3L Client Installer";

	private static final String SUNOS = "sunos";

	private static final String OS_NAME = "os.name";

	@Override
	public String[] patronesRapidos() {
		return new String[] { M3L_CLIENT_INSTALLER, SUNOS };
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

		if (lineaContieneSolaris(linea) && tieneContextoM3L(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneSolaris(String linea) {
		String lineaLower = linea.toLowerCase();
		return lineaLower.contains(SUNOS) || lineaLower.contains(OS_NAME) && lineaLower.contains("sunos");
	}

	private boolean lineaContieneM3L(String linea) {
		return linea.toLowerCase().contains("m3l");
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
		return new AdvertenciaOSNoDetectadoM3L();
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
		return MonitorDePID.idioma.mensajeAdvertenciaOSNoDetectadoM3L() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAdvertenciaOSNoDetectadoM3L();
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
		return "advertencia_os_no_detectado_m3l";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
