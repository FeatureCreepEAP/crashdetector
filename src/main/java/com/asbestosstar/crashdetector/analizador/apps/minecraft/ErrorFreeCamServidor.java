package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el uso de FreeCam en un servidor dedicado, lo cual provoca un error
 * porque FreeCam intenta cargar clases del cliente en un entorno de servidor.
 */
public class ErrorFreeCamServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoErrorLocalPlayer = false;

	/**
	 * Método de compatibilidad — busca el error específico en el contenido completo
	 * del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si el error de LocalPlayer para servidor dedicado está presente
		// en el contenido del registro
		if (consola.contenido_verificar != null) {
			encontradoErrorLocalPlayer = consola.contenido_verificar.contains(
					"Attempted to load class net/minecraft/client/player/LocalPlayer for invalid dist DEDICATED_SERVER");
		}
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde FreeCam falla al crear su
	 * instancia en un servidor dedicado porque intenta cargar clases del cliente.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene ambos elementos clave en una sola línea
		if (linea.contains("Failed to create mod instance") && linea.contains("ModID: freecam")
				&& linea.contains("net.xolt.freecam.Freecam") && encontradoErrorLocalPlayer) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al uso incorrecto de FreeCam
			mensaje = MonitorDePID.idioma.errorFreeCamServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorFreeCamServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Alta prioridad: rompe la carga del mod
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorFreeCamServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorFreeCamServidor())
				.construir();
	}

	@Override
	public String id() {
		return "error_freecam_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre FreeCam y servidor dedicado.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("Failed to create mod instance") && t.contains("ModID: freecam")
				&& t.contains("net.xolt.freecam.Freecam") && t.contains(
						"Attempted to load class net/minecraft/client/player/LocalPlayer for invalid dist DEDICATED_SERVER");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}