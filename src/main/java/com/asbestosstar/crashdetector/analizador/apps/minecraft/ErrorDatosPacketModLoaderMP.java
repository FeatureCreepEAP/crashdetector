package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.IdentityHashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta cuando ModLoaderMP recibe o envía un paquete con datos que exceden el tamaño máximo.
 *
 * El error típico es:
 * "Integer data size of %d is higher than the max (65535)."
 * "Float data size of %d is higher than the max (65535)."
 * "Double data size of %d is higher than the max (65535)."
 * "String data size of %d is higher than the max (65535)."
 * "String length of %d is higher than the max (65535)."
 *
 * Packet230ModLoader limita los datos a 65535 elementos. Si un mod intenta
 * enviar más datos de los permitidos, se lanza IOException y la comunicación
 * multijugador falla.
 *
 * @ProbadoPor(valor = {
 *     "com.asbestosstar.crashdetector.pruebas.PruebaModLoaderMP"
 * }, nota = "Cubre deteccion de error de datos de packet en ModLoaderMP")
 */
@ProbadoPor(valor = {
		"com.asbestosstar.crashdetector.pruebas.PruebaModLoaderMP" },
		nota = "Cubre deteccion de error de datos de packet en ModLoaderMP")
public class ErrorDatosPacketModLoaderMP implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private final Map<Consola, Boolean> tieneModLoaderMP = new IdentityHashMap<Consola, Boolean>();

	private static final String DATA_SIZE_EXCEEDED = "is higher than the max";

	private static final String MODLOADERMP_CONTEXT = "modloadermp";

	@Override
	public String[] patronesRapidos() {
		return new String[] { DATA_SIZE_EXCEEDED, MODLOADERMP_CONTEXT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.consola == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || consola == null || linea == null) {
			return;
		}

		if (lineaContieneModLoaderMP(linea)) {
			tieneModLoaderMP.put(consola, true);
		}

		if (lineaContieneError(linea) && tieneContextoModLoaderMP(consola)) {
			activar(consola, numeroDeLinea);
		}
	}

	private boolean lineaContieneError(String linea) {
		return linea.contains(DATA_SIZE_EXCEEDED);
	}

	private boolean lineaContieneModLoaderMP(String linea) {
		String lower = linea.toLowerCase();
		return lower.contains(MODLOADERMP_CONTEXT);
	}

	private boolean tieneContextoModLoaderMP(Consola consola) {
		Boolean encontrado = tieneModLoaderMP.get(consola);
		return encontrado != null && encontrado;
	}

	private void activar(Consola consola, int numeroDeLinea) {
		this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDatosPacketModLoaderMP();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1800f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorDatosPacketModLoaderMP() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorDatosPacketModLoaderMP();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ERROR;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "error_datos_packet_modloadermp";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
