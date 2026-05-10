package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta que el mod PTRLib no está instalado. Gracias a Aternos
 * porque esta es una implementacion de su codex:
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaDependenciaPTRLib implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final String nombreMod = "PTRLib";

	private static final String TEXTO_EXCEPCION = "Encountered an unexpected exception";

	private static final String TEXTO_CLASE_FALTANTE = "java.lang.NoClassDefFoundError: com/mia/craftstudio/IPackReaderCallback";

	/**
	 * Verifica si el log contiene el error de dependencia faltante de PTRLib.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		if (contenido == null || contenido.isEmpty()) {
			return;
		}

		if (contieneErrorPTRLib(contenido)) {
			this.mensaje = MonitorDePID.idioma.mensajeDependenciaModFaltante(nombreMod);
			activado = true;
		}
	}

	/**
	 * Detecta el error sin usar Pattern/Matcher.
	 *
	 * El patron original permitia espacios o saltos de linea entre: "Encountered an
	 * unexpected exception" y "java.lang.NoClassDefFoundError:
	 * com/mia/craftstudio/IPackReaderCallback"
	 */
	private boolean contieneErrorPTRLib(String contenido) {
		int inicio = contenido.indexOf(TEXTO_EXCEPCION);

		while (inicio >= 0) {
			int despuesInicio = inicio + TEXTO_EXCEPCION.length();

			int indiceClase = contenido.indexOf(TEXTO_CLASE_FALTANTE, despuesInicio);

			if (indiceClase >= 0) {
				// Confirmar que entre los dos textos solo haya espacios, tabs o saltos de
				// linea.
				if (soloEspaciosEntre(contenido, despuesInicio, indiceClase)) {
					return true;
				}
			}

			inicio = contenido.indexOf(TEXTO_EXCEPCION, despuesInicio);
		}

		return false;
	}

	/**
	 * Verifica que entre dos posiciones solo existan caracteres de espacio.
	 */
	private boolean soloEspaciosEntre(String texto, int inicio, int fin) {
		if (inicio < 0 || fin < inicio || fin > texto.length()) {
			return false;
		}

		for (int i = inicio; i < fin; i++) {
			if (!Character.isWhitespace(texto.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaDependenciaPTRLib();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema.
	 */
	@Override
	public float prioridad() {
		return 1000.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaDependenciaModFaltante();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(nombreMod)).construir();
	}

	@Override
	public String id() {
		return "problema_dependencia_ptrlib";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}