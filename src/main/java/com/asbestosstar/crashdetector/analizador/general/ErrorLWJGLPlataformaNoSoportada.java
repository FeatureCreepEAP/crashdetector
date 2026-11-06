package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta cuando LWJGL no tiene bibliotecas nativas para la plataforma actual,
 * reportando "Platform/architecture mismatch".
 *
 * Ocurre cuando la plataforma o arquitectura del sistema no coincide con las
 * bibliotecas nativas precompiladas de LWJGL. Común en plataformas no
 * soportadas oficialmente.
 */
@ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaErroresGraficos" },
		nota = "Cubre deteccion de Platform/architecture mismatch de LWJGL")
public class ErrorLWJGLPlataformaNoSoportada implements Verificaciones {

	private static final java.util.Set<String> MENSAJES_GLOBALES_VISTOS = java.util.Collections
			.synchronizedSet(new java.util.HashSet<>());

	/**
	 * Limpia el estado global de mensajes agregados.
	 */
	public static void reiniciarGlobal() {
		MENSAJES_GLOBALES_VISTOS.clear();
	}

	private boolean activado = false;

	private String mensaje = "";
	private String enlace = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { "Platform/architecture mismatch detected" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			return;
		}

		if (linea == null || linea.isEmpty()) {
			return;
		}

		// Detectar el error de plataforma no soportada de LWJGL
		// Ejemplo: "[LWJGL] Platform/architecture mismatch detected for module: org.lwjgl"
		if (linea.contains("Platform/architecture mismatch detected")) {
			String clave = "error_lwjgl_plataforma_no_soportada";
			if (MENSAJES_GLOBALES_VISTOS.contains(clave)) {
				return;
			}
			MENSAJES_GLOBALES_VISTOS.add(clave);

			this.enlace = (consola != null) ? consola.agregarErrorALectador(numero_de_linea, this) : "";
			this.mensaje = construirMensaje();
			this.activado = true;
		}
	}

	private String construirMensaje() {
		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.error_lwjgl_plataforma_no_soportada_titulo_y_detalle());
		if (!enlace.isEmpty()) {
			sb.append(" ").append(enlace);
		}
		return sb.toString();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorLWJGLPlataformaNoSoportada();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 830.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_lwjgl_plataforma_no_soportada();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(
						MonitorDePID.idioma.solucion_para_error_lwjgl_plataforma_no_soportada_titulo()).construir();
	}

	@Override
	public String id() {
		return "error_lwjgl_plataforma_no_soportada";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ERROR;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}
