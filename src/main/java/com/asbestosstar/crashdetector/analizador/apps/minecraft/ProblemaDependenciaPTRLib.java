package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta que el mod PTRLib no está instalado. Modernizado: global barato +
 * verificación por línea, sin recorrer todo el log.
 */
public class ProblemaDependenciaPTRLib implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private final String nombreMod = "PTRLib";

	private static final String TEXTO_EXCEPCION = "Encountered an unexpected exception";
	private static final String TEXTO_CLASE_FALTANTE = "java.lang.NoClassDefFoundError: com/mia/craftstudio/IPackReaderCallback";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_EXCEPCION, TEXTO_CLASE_FALTANTE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea: activa detector y agrega enlace exacto a la línea
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		int idxExcepcion = linea.indexOf(TEXTO_EXCEPCION);
		if (idxExcepcion < 0)
			return;

		int despuesExcepcion = idxExcepcion + TEXTO_EXCEPCION.length();
		int idxClase = linea.indexOf(TEXTO_CLASE_FALTANTE, despuesExcepcion);

		if (idxClase >= 0 && soloEspaciosEntre(linea, despuesExcepcion, idxClase)) {
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			mensaje = MonitorDePID.idioma.mensajeDependenciaModFaltante(nombreMod) + " " + enlaceHtml;
			activado = true;
		}
	}

	/**
	 * Verifica que entre dos posiciones solo existan espacios, tabs o saltos de
	 * línea
	 */
	private boolean soloEspaciosEntre(String texto, int inicio, int fin) {
		if (inicio < 0 || fin < inicio || fin > texto.length())
			return false;
		for (int i = inicio; i < fin; i++) {
			if (!Character.isWhitespace(texto.charAt(i)))
				return false;
		}
		return true;
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaDependenciaPTRLib();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaDependenciaModFaltante();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(nombreMod)).construir();
	}

	@Override
	public String id() {
		return "problema_dependencia_ptrlib";
	}

	@Override
	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}