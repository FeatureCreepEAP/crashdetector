package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores ServiceConfigurationError relacionados con IDependencyLocator
 * en Forge o NeoForge. Este error ocurre cuando un archivo de servicio de
 * modlauncher esta corrupto, es de version incorrecta, o el mod no es
 * compatible con la version actual del cargador de mods.
 */
public class ErrorConfiguracionServicioIDependencyLocator implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String claseProblematica = "";
	private List<String> modsUbicacion = new ArrayList<>();
	private String enlaceHtml = "";

	private static final String SERVICE_CONFIGURATION_ERROR = "ServiceConfigurationError";
	private static final String IDEPENDENCY_LOCATOR = "IDependencyLocator";
	private static final String UNABLE_TO_LOAD = "Unable to load";
	private static final String MARCADOR = "IDependencyLocator: Unable to load ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { SERVICE_CONFIGURATION_ERROR, IDEPENDENCY_LOCATOR, MARCADOR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca líneas con: "ServiceConfigurationError" + "IDependencyLocator" y aplica
	 * el patrón PATRON_ERROR para extraer la clase problemática. Luego intenta
	 * localizar los mods que contienen dicha clase.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Verificación rápida inicial.
		if (!linea.contains(SERVICE_CONFIGURATION_ERROR) || !linea.contains(IDEPENDENCY_LOCATOR)) {
			return;
		}

		int inicio = linea.indexOf(MARCADOR);

		// Si no existe el marcador, salir.
		if (inicio < 0) {
			return;
		}

		// Mover al inicio real del nombre de clase.
		inicio += MARCADOR.length();

		// Buscar el final de la clase.
		// Normalmente termina en espacio o final de línea.
		int fin = linea.indexOf(' ', inicio);

		if (fin < 0) {
			fin = linea.length();
		}

		// Extraer clase problemática.
		claseProblematica = linea.substring(inicio, fin).trim();

		// Validación básica.
		if (claseProblematica.isEmpty()) {
			return;
		}

		// Buscar el mod que contiene esta clase.
		String classPath = claseProblematica.replace('.', '/') + ".class";

		List<ArchivoDeMod> mods = Buscador.buscarModsConTermino(classPath);

		for (ArchivoDeMod mod : mods) {
			modsUbicacion.add(mod.ubicacion_para_publicar());
		}

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		activado = true;

		// Construir mensaje final.
		mensaje = MonitorDePID.idioma.errorConfiguracionServicio(claseProblematica,
				modsUbicacion.isEmpty() ? null : modsUbicacion) + Verificaciones.nl_html + enlaceHtml;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorConfiguracionServicioIDependencyLocator();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Prioridad alta: impide el inicio del juego
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_configuracion_servicio();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_configuracion_servicio(modsUbicacion))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_configuracion_servicio()).construir();
	}

	@Override
	public String id() {
		return "error_configuracion_servicio"; // Sin acentos, en minusculas, guiones bajos
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} si:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la firma del problema de configuración de servicio para
	 * IDependencyLocator, idealmente con la clase problemática.</li>
	 * </ul>
	 * Es deliberadamente conservador: mejor un falso negativo que marcar un trazo
	 * que no corresponde a este error.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { SERVICE_CONFIGURATION_ERROR, IDEPENDENCY_LOCATOR };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}