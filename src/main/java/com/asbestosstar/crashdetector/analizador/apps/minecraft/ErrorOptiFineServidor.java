package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el uso de OptiFine en un servidor dedicado, lo cual provoca múltiples
 * errores porque OptiFine intenta cargar clases del cliente en un entorno de
 * servidor.
 */
public class ErrorOptiFineServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoOptiFine = false;

	private static final String TEXTO_OPTIFINE_PATCHER = "optifine.Patcher.applyPatch";
	private static final String TEXTO_BASE_RESOURCE_NOT_FOUND = "Base resource not found";
	private static final String TEXTO_BASE_RESOURCE_NOT_FOUND_COMPLETO = "java.io.IOException: Base resource not found:";
	private static final String TEXTO_LAYER_OPTIFINE = "LAYER SERVICE/optifine/optifine.Patcher.applyPatch";
	private static final String TEXTO_OPTIFINE = "optifine";

	/**
	 * Método global ligero.
	 *
	 * Busca si OptiFine está presente en el contenido completo del registro sin
	 * usar toLowerCase(), para evitar crear una copia grande del log.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		encontradoOptiFine = contieneIgnoreCase(consola.contenido_verificar, TEXTO_OPTIFINE_PATCHER);
	}

	/**
	 * Análisis por línea del registro.
	 *
	 * Se busca el patrón característico del error donde OptiFine falla en un
	 * servidor dedicado porque intenta cargar clases del cliente.
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado || !encontradoOptiFine || linea == null || linea.isEmpty()) {
			return;
		}

		// En esta línea normalmente aparece el error principal.
		if (linea.contains(TEXTO_BASE_RESOURCE_NOT_FOUND)) {
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			mensaje = MonitorDePID.idioma.errorOptiFineServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	/**
	 * Busca un texto ignorando mayúsculas/minúsculas sin crear copias con
	 * toLowerCase().
	 */
	private boolean contieneIgnoreCase(String texto, String buscar) {
		return indexOfIgnoreCase(texto, buscar) >= 0;
	}

	/**
	 * Implementación rápida de búsqueda case-insensitive.
	 *
	 * Usa regionMatches(true, ...) para evitar: - texto.toLowerCase() -
	 * buscar.toLowerCase() - nuevas copias grandes del log
	 */
	private int indexOfIgnoreCase(String texto, String buscar) {
		if (texto == null || buscar == null) {
			return -1;
		}

		int largoTexto = texto.length();
		int largoBuscar = buscar.length();

		if (largoBuscar == 0) {
			return 0;
		}

		if (largoBuscar > largoTexto) {
			return -1;
		}

		int limite = largoTexto - largoBuscar;

		for (int i = 0; i <= limite; i++) {
			if (texto.regionMatches(true, i, buscar, 0, largoBuscar)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorOptiFineServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f; // Prioridad máxima: rompe la carga del servidor
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorOptiFineServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorOptiFineServidor())
				.construir();
	}

	@Override
	public String id() {
		return "error_optifine_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 *
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre OptiFine y servidor dedicado.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(TEXTO_BASE_RESOURCE_NOT_FOUND_COMPLETO) && t.contains(TEXTO_LAYER_OPTIFINE)
				&& contieneIgnoreCase(t, TEXTO_OPTIFINE);
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