package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta problemas de compatibilidad entre Iris/Oculus y Distant Horizons
 * donde faltan métodos de API necesarios, requiriendo versiones específicas.
 */
public class ErrorCompatibilidadIrisDH implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String TEXTO_DH_MISSING_API = "java.lang.RuntimeException: DH found, but one or more API methods are missing.";
	private static final String TEXTO_IRIS_REQUIRES_DH = "Iris requires DH";
	private static final String TEXTO_DH_API_VERSION = "DH API version";
	private static final String TEXTO_OR_NEWER = "or newer";
	private static final String VERSION_204 = "[2.0.4]";
	private static final String VERSION_110 = "[1.1.0]";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_DH_MISSING_API, TEXTO_IRIS_REQUIRES_DH, TEXTO_DH_API_VERSION };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Iris encuentra Distant
	 * Horizons pero faltan métodos de API necesarios.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea que contiene el error de compatibilidad entre Iris y
		// Distant Horizons
		if (linea.contains(TEXTO_DH_MISSING_API)
				&& (linea.contains(TEXTO_IRIS_REQUIRES_DH) || linea.contains(TEXTO_DH_API_VERSION))
				&& (linea.contains(TEXTO_OR_NEWER) || linea.contains(VERSION_204) || linea.contains(VERSION_110))) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de compatibilidad
			mensaje = MonitorDePID.idioma.errorCompatibilidadIrisDH() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCompatibilidadIrisDH();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Prioridad media-alta: rompe la compatibilidad entre mods
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorCompatibilidadIrisDH();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorCompatibilidadIrisDH())
				.construir();
	}

	@Override
	public String id() {
		return "error_compatibilidad_iris_dh";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre Iris y Distant Horizons.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_DH_MISSING_API };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}