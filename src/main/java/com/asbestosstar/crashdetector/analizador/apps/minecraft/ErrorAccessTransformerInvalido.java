package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores cuando un mod tiene una configuración inválida de access
 * transformer. Detecta específicamente el error "Invalid access transformer
 * line in [nombre.jar]".
 */
public class ErrorAccessTransformerInvalido implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreJar = "";
	private String enlaceHtml = "";

	private static final String MARCADOR_INICIO = "Invalid access transformer line in ";
	private static final String MARCADOR_FIN = ":";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MARCADOR_INICIO };
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
	 * Busca el patrón "Invalid access transformer line in [nombre.jar]:" en la
	 * línea actual, extrae el nombre del JAR y registra el enlace al lector.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó o el chequeo global dijo que no es posible,
		// no seguimos revisando líneas.
		if (activado || linea == null) {
			return;
		}

		// Detecta el error específico de access transformer inválido.
		int inicio = linea.indexOf(MARCADOR_INICIO);
		if (inicio < 0) {
			return;
		}

		// Mueve el índice hasta el inicio real del nombre del JAR.
		inicio += MARCADOR_INICIO.length();

		// Busca los dos puntos después del nombre del JAR.
		int fin = linea.indexOf(MARCADOR_FIN, inicio);
		if (fin < 0 || fin <= inicio) {
			return;
		}

		// Extrae el nombre del JAR problemático.
		nombreJar = linea.substring(inicio, fin).trim();

		if (nombreJar.length() == 0) {
			return;
		}

		mensaje = MonitorDePID.idioma.errorAccessTransformerInvalido(nombreJar) + Verificaciones.nl_html;
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorAccessTransformerInvalido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 910.0f; // Prioridad muy alta - error crítico que impide cargar mods
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_access_transformer_invalido();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_access_transformer_invalido(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_access_transformer_invalido(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_access_transformer_invalido()).construir();
	}

	@Override
	public String id() {
		return "access_transformer_invalido";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene la cadena base del error "Invalid access transformer
	 * line in" y, si se conoce, el nombre del JAR problemático.</li>
	 * </ul>
	 * Es intencionadamente conservador: mejor falsos negativos que falsos
	 * positivos.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!nombreJar.isEmpty()) {
			return t.contains(MARCADOR_INICIO) && t.contains(nombreJar);
		}

		// Caso de fallback si por alguna razón no se llegó a capturar el nombre del
		// JAR.
		return t.contains(MARCADOR_INICIO);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}