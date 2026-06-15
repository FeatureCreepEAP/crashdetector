package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta códigos de error específicos como -1073741819 que pueden ser causados
 * por overlays de software como GameCaster de Razer, Discord, OBS Studio o
 * problemas con drivers de NVIDIA.
 */
public class ErrorCodigo1073741819 implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean analizarLineas = false;

	private static final String CODIGO_ERROR = "-1073741819";
	private static final String TEXTO_EXIT = "exit";
	private static final String TEXTO_ERROR = "error";

	@Override
	public String[] patronesRapidos() {
		return new String[] { CODIGO_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		analizarLineas = true;
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(CODIGO_ERROR)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del código de error -1073741819 que puede
	 * estar relacionado con overlays o problemas de drivers.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!analizarLineas || activado || linea == null) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el código de error específico
		if (linea.contains(CODIGO_ERROR) && contieneIgnoreCase(linea, TEXTO_EXIT)) {
			activar(consola, numero_de_linea);
			return;
		}

		// También buscamos líneas que contengan "error code" y el valor específico
		if (linea.contains(CODIGO_ERROR) && contieneIgnoreCase(linea, TEXTO_ERROR)) {
			activar(consola, numero_de_linea);
		}
	}

	private void activar(Consola consola, int numero_de_linea) {
		// Enlazar a la línea del error en el lector
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		// Mensaje de error en HTML con referencia al problema de overlays o drivers
		mensaje = MonitorDePID.idioma.errorCodigo1073741819() + Verificaciones.nl_html;
		activado = true;
	}

	private boolean contieneIgnoreCase(String texto, String buscadoLower) {
		int max = texto.length() - buscadoLower.length();
		for (int i = 0; i <= max; i++) {
			if (regionMatchesLowerAscii(texto, i, buscadoLower)) {
				return true;
			}
		}
		return false;
	}

	private boolean regionMatchesLowerAscii(String texto, int inicio, String buscadoLower) {
		for (int i = 0; i < buscadoLower.length(); i++) {
			char c = texto.charAt(inicio + i);
			if (c >= 'A' && c <= 'Z') {
				c = (char) (c + 32);
			}

			if (c != buscadoLower.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCodigo1073741819();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1200.0f; // Prioridad media: indica problemas externos al juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorCodigo1073741819();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorCodigo1073741819())
				.construir();
	}

	@Override
	public String id() {
		return "error_codigo_especial";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene el código de error específico.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return (t.contains("exit code") && t.contains(CODIGO_ERROR))
				|| (t.contains("error code") && t.contains(CODIGO_ERROR));
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}