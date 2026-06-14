package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta problemas con el mod Supplementaries que impiden la carga del
 * servidor debido a errores en el registro de comportamientos de fuego,
 * causando un NullPointerException durante la carga de datapacks.
 */
public class ErrorSupplementariesCargaServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoSupplementaries = false;

	private static final String SUPPLEMENTARIES_FIRE_BEHAVIOR = "net.mehvahdjukaar.supplementaries.common.block.fire_behaviors.FireBehaviorsManager.registerBehaviors";

	/**
	 * Método de compatibilidad — busca si Supplementaries está presente en el
	 * contenido completo del registro.
	 */

	@Override
	public void verificar(Consola consola) {
		if (consola.contenido_verificar != null
				&& consola.contenido_verificar.contains(SUPPLEMENTARIES_FIRE_BEHAVIOR)) {
			encontradoSupplementaries = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!encontradoSupplementaries)
			return false;

		return true;
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Supplementaries falla
	 * durante la carga de datapacks, específicamente con FireBehaviorsManager.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la combinación de mensajes que indican el problema de
		// Supplementaries
		if (linea.contains("Failed to load datapacks") && encontradoSupplementaries) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de Supplementaries
			mensaje = MonitorDePID.idioma.errorSupplementariesCargaServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorSupplementariesCargaServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1100.0f; // Alta prioridad: impide la carga del servidor
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorSupplementariesCargaServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorSupplementariesCargaServidor()).construir();
	}

	@Override
	public String id() {
		return "error_supplementaries_carga_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad de Supplementaries con la carga del servidor.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("Failed to load datapacks, can't proceed with server load")
				&& t.contains("java.util.concurrent.ExecutionException") && t.contains("java.lang.NullPointerException")
				&& t.contains("Cannot invoke") && t.contains(
						"et.mehvahdjukaar.supplementaries.common.block.fire_behaviors.FireBehaviorsManager.registerBehaviors");
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}