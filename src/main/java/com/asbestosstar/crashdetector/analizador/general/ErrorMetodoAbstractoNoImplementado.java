package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores AbstractMethodError específicos donde una clase no implementa
 * un método de una interfaz. Extrae los nombres concretos y el origen desde el
 * trace, sin usar regex ni mantener todas las líneas en memoria.
 */
public class ErrorMetodoAbstractoNoImplementado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean posibleError = false;

	private static final String TEXTO_ABSTRACT = "java.lang.AbstractMethodError";
	private static final String TEXTO_NO_IMPLEMENTA = "does not define or inherit an implementation";
	private static final String TEXTO_INTERFACE = "of interface";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Marcar posible error si los textos clave están presentes
		posibleError = consola.contenido_verificar.contains(TEXTO_ABSTRACT)
				&& consola.contenido_verificar.contains(TEXTO_NO_IMPLEMENTA)
				&& consola.contenido_verificar.contains(TEXTO_INTERFACE);
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!posibleError || activado || linea == null)
			return;

		String recorte = linea.trim();
		if (!recorte.contains(TEXTO_ABSTRACT) || !recorte.contains(TEXTO_NO_IMPLEMENTA)
				|| !recorte.contains(TEXTO_INTERFACE)) {
			return;
		}

		// Extraer clase, método e interfaz
		int idxClaseStart = recorte.indexOf(":") + 1;
		int idxMetodoStart = recorte.indexOf("'", idxClaseStart);
		int idxMetodoEnd = recorte.indexOf("'", idxMetodoStart + 1);
		int idxInterfaz = recorte.lastIndexOf("of interface");

		if (idxClaseStart < 0 || idxMetodoStart < 0 || idxMetodoEnd < 0 || idxInterfaz < 0)
			return;

		String claseConcreta = recorte.substring(idxClaseStart, idxMetodoStart).trim();
		String firmaMetodo = recorte.substring(idxMetodoStart + 1, idxMetodoEnd).trim();
		String interfaz = recorte.substring(idxInterfaz + "of interface".length()).trim();

		// Buscar origen dinámicamente en las siguientes 10 líneas
		String origen = "";
		if (consola != null && consola.contenido_verificar != null) {
			String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);
			for (int j = numero_de_linea + 1; j < Math.min(numero_de_linea + 11, lineas.length); j++) {
				String l = lineas[j].trim();
				if (l.startsWith("at ")) {
					String posibleOrigen = com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace
							.extraerModidDeLinea(l);
					if (posibleOrigen == null || com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace
							.esModNoPermite(posibleOrigen)) {
						java.util.List<String> jars = com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace
								.extraerJarsDeLinea(l);
						if (!jars.isEmpty())
							posibleOrigen = jars.get(0);
						else
							posibleOrigen = com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace
									.extraerPaqueteDeLinea(l);
					}
					if (posibleOrigen != null && !posibleOrigen.isEmpty()
							&& !com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace
									.esModNoPermite(posibleOrigen)) {
						origen = posibleOrigen;
						break;
					}
				} else if (!l.isEmpty() && !l.startsWith("Caused by") && !l.startsWith("...")) {
					break;
				}
			}
		}

		// Registrar el enlace a la línea concreta
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		mensaje = MonitorDePID.idioma.errorMetodoAbstractoNoImplementadoDetallado(claseConcreta, firmaMetodo, interfaz,
				origen) + Verificaciones.nl_html + enlaceHtml;

		activado = true;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null)
			return false;
		return trazo.trace.contains(TEXTO_ABSTRACT) && trazo.trace.contains(TEXTO_NO_IMPLEMENTA)
				&& trazo.trace.contains(TEXTO_INTERFACE);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMetodoAbstractoNoImplementado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public float prioridad() {
		return 1200.0f;
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionMetodoAbstractoNoImplementado()).construir();
	}

	@Override
	public String id() {
		return "metodo_abstracto_no_implementado";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorMetodoAbstractoNoImplementado();
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}
}