package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta problemas de compatibilidad entre Iris/Oculus y Distant Horizons
 * donde faltan métodos de API necesarios, requiriendo versiones específicas.
 */
public class ErrorCompatibilidadIrisDH implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean analizarLineas = false;

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains("java.lang.RuntimeException: DH found, but one or more API methods are missing.")
				&& (contenido.contains("Iris requires DH") || contenido.contains("DH API version"))
				&& (contenido.contains("or newer") || contenido.contains("[2.0.4]") || contenido.contains("[1.1.0]"))) {
			analizarLineas = true;
		}

	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!analizarLineas)
			return false;

		return true;
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
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de compatibilidad entre Iris y
		// Distant Horizons
		if (linea.contains("java.lang.RuntimeException: DH found, but one or more API methods are missing.")
				&& (linea.contains("Iris requires DH") || linea.contains("DH API version"))
				&& (linea.contains("or newer") || linea.contains("[2.0.4]") || linea.contains("[1.1.0]"))) {

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
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("java.lang.RuntimeException: DH found, but one or more API methods are missing.")
				&& (t.contains("Iris requires DH") || t.contains("DH API version"))
				&& (t.contains("or newer") || t.contains("[2.0.4]") || t.contains("[1.1.0]"));
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}