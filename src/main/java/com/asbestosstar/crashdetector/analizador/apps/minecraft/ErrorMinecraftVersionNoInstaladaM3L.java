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
 * Detecta cuando M3L falla porque la versiÃ³n de Minecraft no estÃ¡ instalada.
 *
 * El error tÃ­pico es:
 * "Install Minecraft &lt;version&gt; before installing M3L"
 *
 * M3L necesita que la versiÃ³n base de Minecraft estÃ© instalada antes de poder
 * crear el perfil modificado. Si el JAR de la versiÃ³n no existe, el instalador
 * lanza CantInstallException.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaM3L"
 * }, nota = "Cubre deteccion de Minecraft no instalada en M3L y falsos positivos")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaM3L" },
		nota = "Cubre deteccion de Minecraft no instalada en M3L y falsos positivos")
public class ErrorMinecraftVersionNoInstaladaM3L implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneM3L = new IdentityHashMap<Consola, Boolean>();

	private static final String INSTALL_MC_BEFORE = "Install Minecraft";

	private static final String BEFORE_INSTALLING_M3L = "before installing M3L";

	private static final String M3L_CONTEXT = "m3l";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INSTALL_MC_BEFORE, BEFORE_INSTALLING_M3L, M3L_CONTEXT };
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

		if (lineaContieneErrorMCNoInstalada(linea) && tieneContextoM3L(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneErrorMCNoInstalada(String linea) {
		return linea.contains(INSTALL_MC_BEFORE) && linea.contains(BEFORE_INSTALLING_M3L);
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
		return new ErrorMinecraftVersionNoInstaladaM3L();
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
		return MonitorDePID.idioma.mensajeErrorMinecraftVersionNoInstaladaM3L() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorMinecraftVersionNoInstaladaM3L();
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
		return "error_minecraft_version_no_instalada_m3l";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
