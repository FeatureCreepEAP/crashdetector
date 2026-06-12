package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el uso de Controllable en un servidor dedicado, lo cual provoca un
 * error porque Controllable intenta cargar clases del cliente en un entorno de
 * servidor.
 */
public class ErrorControllableServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoControllable = false;
	public boolean posible = false;

	/**
	 * Método de compatibilidad — busca si Controllable está presente en el
	 * contenido completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si Controllable está presente en el contenido del registro
		if (consola.contenido_verificar != null) {
			if (consola.contenido_verificar.contains("com.mrcrayfish.controllable")) {
				encontradoControllable = true;
			}

			if (encontradoControllable && consola.contenido_verificar.contains("java.lang.BootstrapMethodError")
					&& consola.contenido_verificar.contains("Attempted to load class")
					&& consola.contenido_verificar.contains("net/minecraft/client/gui/screens/Screen")
					&& consola.contenido_verificar.contains("for invalid dist DEDICATED_SERVER")) {
				posible = true;
			}

		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posible)
			return false;

		return true;
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Controllable falla al crear
	 * su instancia en un servidor dedicado porque intenta cargar clases del
	 * cliente.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de carga de clase para servidor
		// dedicado de Controllable
		if (encontradoControllable && linea.contains("java.lang.BootstrapMethodError")
				&& linea.contains("Attempted to load class")
				&& linea.contains("net/minecraft/client/gui/screens/Screen")
				&& linea.contains("for invalid dist DEDICATED_SERVER")) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al uso incorrecto de Controllable
			mensaje = MonitorDePID.idioma.errorControllableServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorControllableServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Alta prioridad: rompe la carga del mod
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorControllableServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorControllableServidor())
				.construir();
	}

	@Override
	public String id() {
		return "error_controllable_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre Controllable y servidor dedicado.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("java.lang.BootstrapMethodError") && t.contains("Attempted to load class")
				&& t.contains("net/minecraft/client/gui/screens/Screen")
				&& t.contains("for invalid dist DEDICATED_SERVER") && t.contains("com.mrcrayfish.controllable");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}