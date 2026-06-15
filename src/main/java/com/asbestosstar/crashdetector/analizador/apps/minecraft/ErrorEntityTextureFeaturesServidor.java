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
 * Detecta el uso de Entity Texture Features (ETF) en un servidor dedicado, lo
 * cual provoca un error porque ETF intenta cargar clases del cliente en un
 * entorno de servidor.
 */
public class ErrorEntityTextureFeaturesServidor implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoETF = false;

	private static final String ENTITY_TEXTURE_FEATURES = "$entity_texture_features$";
	private static final String ETF = "$etf$";
	private static final String ATTEMPTED_TO_LOAD_CLASS = "Attempted to load class";
	private static final String SCREEN = "net/minecraft/client/gui/screens/Screen";
	private static final String DEDICATED_SERVER = "for invalid dist DEDICATED_SERVER";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ENTITY_TEXTURE_FEATURES, ETF, ATTEMPTED_TO_LOAD_CLASS, SCREEN, DEDICATED_SERVER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		// Verificamos si Entity Texture Features está presente en la línea que disparó
		// el patrón rápido.
		if (lineaContieneETF(evento.linea)) {
			encontradoETF = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — no hace nada en modo rápido/streaming.
	 */
	@Override
	public void verificar(Consola consola) {
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return encontradoETF && !activado;
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Entity Texture Features
	 * falla porque intenta cargar clases del cliente en un servidor dedicado.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!encontradoETF || activado || linea == null) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de carga de clase para servidor
		// dedicado relacionado con ETF
		if (lineaContieneErrorServidor(linea)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al uso incorrecto de Entity Texture
			// Features
			mensaje = MonitorDePID.idioma.errorEntityTextureFeaturesServidor() + Verificaciones.nl_html;
			activado = true;
		}
	}

	private boolean lineaContieneETF(String linea) {
		return linea.contains(ENTITY_TEXTURE_FEATURES) || linea.contains(ETF);
	}

	private boolean lineaContieneErrorServidor(String linea) {
		return linea.contains(ATTEMPTED_TO_LOAD_CLASS) && linea.contains(SCREEN) && linea.contains(DEDICATED_SERVER);
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

		return t.contains(ATTEMPTED_TO_LOAD_CLASS) && t.contains(SCREEN) && t.contains(DEDICATED_SERVER)
				&& (t.contains("entity texture features") || t.contains("etf")
						|| t.contains("ResourceLocation.handler$zca000$etf$illegalPathOverride"));
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}