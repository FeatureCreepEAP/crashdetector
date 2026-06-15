package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta que el servidor no puede iniciarse porque el usuario no ha aceptado
 * el EULA de Minecraft, lo cual requiere cambiar 'eula=false' a 'eula=true' en
 * eula.txt.
 */
public class ErrorEULANoAceptado implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	public boolean posible = false;

	private static final String TEXTO_EULA = "You need to agree to the EULA in order to run the server";
	private static final String EULA_TXT = "eula.txt";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_EULA, EULA_TXT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneEulaNoAceptado(evento.linea)) {
			posible = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posible && !activado;
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el mensaje característico que indica que el usuario necesita aceptar
	 * el EULA para poder ejecutar el servidor.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posible || activado || linea == null) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el mensaje de EULA no aceptado
		if (lineaContieneEulaNoAceptado(linea)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al EULA no aceptado
			mensaje = MonitorDePID.idioma.errorEULANoAceptado() + Verificaciones.nl_html;
			activado = true;
		}
	}

	private boolean lineaContieneEulaNoAceptado(String linea) {
		return linea.contains(TEXTO_EULA) && linea.contains(EULA_TXT);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorEULANoAceptado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f; // Prioridad máxima: es un error de configuración básico que detiene todo
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorEULANoAceptado();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorEULANoAceptado())
				.construir();
	}

	@Override
	public String id() {
		return "error_eula_no_aceptado";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene el mensaje de EULA no aceptado.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(TEXTO_EULA) && t.contains(EULA_TXT);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;// Si la paquete para servidores no tiene el archivo de eula.txt
	}
}