package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Detecta el uso de Entity Texture Features (ETF) en un servidor dedicado, lo
 * cual provoca un error porque ETF intenta cargar clases del cliente en un
 * entorno de servidor.
 */
public class ErrorEntityTextureFeaturesServidor implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoETF = false;

	/**
	 * Método de compatibilidad — busca si ETF está presente en el contenido
	 * completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si Entity Texture Features está presente en el contenido del
		// registro
		if (consola.contenido_verificar != null) {
			encontradoETF = consola.contenido_verificar.toLowerCase().contains("$entity_texture_features$")
					|| consola.contenido_verificar.contains("$etf$");
		}
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Entity Texture Features
	 * falla porque intenta cargar clases del cliente en un servidor dedicado.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de carga de clase para servidor
		// dedicado relacionado con ETF
		if (linea.contains("Attempted to load class") && linea.contains("net/minecraft/client/gui/screens/Screen")
				&& linea.contains("for invalid dist DEDICATED_SERVER") && encontradoETF) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al uso incorrecto de Entity Texture
			// Features
			mensaje = MonitorDePID.idioma.errorEntityTextureFeaturesServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorEntityTextureFeaturesServidor();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Alta prioridad: rompe la carga del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorEntityTextureFeaturesServidor();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoErrorEntityTextureFeaturesServidor()).construir();
	}

	@Override
	public String id() {
		return "error_etf_servidor";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad entre Entity Texture Features y servidor dedicado.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("Attempted to load class") && t.contains("net/minecraft/client/gui/screens/Screen")
				&& t.contains("for invalid dist DEDICATED_SERVER") && (t.contains("entity texture features")
						|| t.contains("etf") || t.contains("ResourceLocation.handler$zca000$etf$illegalPathOverride"));
	}
}