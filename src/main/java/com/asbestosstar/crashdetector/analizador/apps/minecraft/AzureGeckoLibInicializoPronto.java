package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores cuando AzureLib o GeckoLib se inicializan demasiado pronto.
 * Detecta específicamente los errores "AzureLib was initialized too early!" y
 * "GeckoLib was initialized too early!". Identifica la presencia de mods de
 * conexión como Sinytra Connector o specialcompatibilityoperation.
 */
public class AzureGeckoLibInicializoPronto implements Verificaciones {

	private static final Set<String> REPORTADOS_GLOBAL = Collections.synchronizedSet(new HashSet<>());

	private boolean activado = false;
	private String mensaje = "";
	private boolean azureLibError = false;
	private boolean geckoLibError = false;
	private boolean connectorPresente = false;

	private String enlaceHtml = "";

	// Cadenas que se buscan en el log para detectar el problema.
	private final String azure = "AzureLib was initialized too early!";
	private final String geck = "GeckoLib was initialized too early!";

	@Override
	public String[] patronesRapidos() {
		return new String[] { "AzureLib was initialized too early!", "GeckoLib was initialized too early!",
				"SINYTRA CONNECTOR IS PRESENT!"
				// "specialcompatibilityoperation" TODO
		};
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null)
			return;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea. Este es el método que debe usarse en el nuevo flujo.
	 * Marca el error, registra el enlace al lector y actualiza el mensaje cuando
	 * detecta las cadenas relevantes.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (consola == null || linea == null)
			return;

		// Detecta la presencia de Sinytra Connector para añadir contexto al mensaje.
		if (!connectorPresente && linea.indexOf("SINYTRA CONNECTOR IS PRESENT!") >= 0

		// || linea.contains("specialcompatibilityoperation")TODO

		) {
			connectorPresente = true;
			actualizarMensaje();
			return;
		}

		boolean cambio = false;

		// Detecta el error específico de AzureLib inicializada demasiado pronto.
		if (linea.indexOf(azure) >= 0) {
			azureLibError = true;
			cambio = true;

			// Mantener la semántica original: AzureLib siempre sobrescribe el enlace.
			if (REPORTADOS_GLOBAL.add(id() + ":azure")) {
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			}
		}

		// Detecta el error específico de GeckoLib inicializada demasiado pronto.
		if (linea.indexOf(geck) >= 0) {
			geckoLibError = true;
			cambio = true;

			// Solo registrar el enlace si aún no hay uno (mismo comportamiento original:
			// si ya hubo AzureLib, se mantiene ese enlace).
			if (enlaceHtml.isEmpty() && REPORTADOS_GLOBAL.add(id() + ":gecko")) {
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			}
		}

		// Si ya se activó el error, actualizamos el mensaje que se va a mostrar.
		if (cambio) {
			activado = true;
			actualizarMensaje();
		}
	}

	private void actualizarMensaje() {
		if (!activado)
			return;

		mensaje = MonitorDePID.idioma.errorAzureGeckoLibInicializoPronto(azureLibError, geckoLibError,
				connectorPresente) + VerificacionesLegacy.nl_html + enlaceHtml;
	}

	private String extraerLinea(String log, int pos) {
		int inicio = log.lastIndexOf('\n', pos);
		int fin = log.indexOf('\n', pos);

		if (inicio < 0)
			inicio = 0;
		else
			inicio++;

		if (fin < 0)
			fin = log.length();

		return log.substring(inicio, fin);
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		// No necesita procesamiento final.
	}

	public static void reiniciarGlobal() {
		REPORTADOS_GLOBAL.clear();
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new AzureGeckoLibInicializoPronto();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		// Alta prioridad - error que impide la carga correcta de mods.
		return 950.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_azure_geckolib_inicializo_pronto();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_azure_geckolib_inicializo_pronto())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_azure_geckolib_inicializo_pronto()).construir();
	}

	@Override
	public String id() {
		// ID estable para esta verificación.
		return "azuregeckolibinicialaizo";
	}

	/**
	 * Indica si este error está asociado a un trazo concreto de stack trace. En
	 * este caso, cualquier trazo que contenga las cadenas de AzureLib o GeckoLib
	 * inicializadas demasiado pronto se considera ocupado por esta verificación.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo != null && trazo.trace != null && (trazo.trace.contains(geck) || trazo.trace.contains(azure));
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}