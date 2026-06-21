package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta cuando el mod MCEF se inicializa al final del log, lo que indica
 * probablemente un cuelgue silencioso durante la carga.
 */
public class ProblemaMCEFInicializacion implements Verificaciones {

	private boolean activado = false;

	private String enlace = "";
	private int totalLineasDelLog = -1;
	private int lineaMCEF = -1;

	private static final String TEXTO_MCEF_1 = "Initializing CEF on ";
	private static final String TEXTO_MCEF_2 = "[org.cef.CefApp:initialize:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_MCEF_1, TEXTO_MCEF_2 };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (contienePatronMCEF(evento.linea)) {
			lineaMCEF = evento.numeroDeLinea;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificacion por linea.
	 *
	 * En modo legacy, solo revisa las ultimas 5 lineas del log y agrega enlace a la
	 * linea exacta.
	 *
	 * En modo streaming puro, guarda la ultima linea MCEF encontrada y la valida en
	 * finalizarArchivo(), cuando ya se conoce el final real del archivo.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (!contienePatronMCEF(linea)) {
			return;
		}

		lineaMCEF = numero_de_linea;

		if (totalLineasDelLog <= 0) {
			return;
		}

		int primeraLineaPermitida = Math.max(0, totalLineasDelLog - 5);

		if (numero_de_linea < primeraLineaPermitida) {
			return;
		}

		this.activado = true;
		this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		if (activado || consola == null || lineaMCEF < 0) {
			return;
		}

		if (totalLineasDelLog <= 0 && estado != null) {
			totalLineasDelLog = estado.lineasLeidas;
		}

		if (totalLineasDelLog <= 0) {
			return;
		}

		int primeraLineaPermitida = Math.max(0, totalLineasDelLog - 5);

		if (lineaMCEF >= primeraLineaPermitida) {
			this.activado = true;
			this.enlace = consola.agregarErrorALectador(lineaMCEF, this);
		}
	}

	/**
	 * Cuenta lineas sin crear arreglo con split().
	 */
	private int contarLineas(String texto) {
		int lineas = 1;

		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) == '\n') {
				lineas++;
			}
		}

		return lineas;
	}

	/**
	 * Primero usa contains() exacto, que es muy rápido.
	 *
	 * Solo si falla, usa comparación ASCII case-insensitive manual.
	 */
	private boolean contienePatronMCEF(String texto) {
		return texto.contains(TEXTO_MCEF_1) || texto.contains(TEXTO_MCEF_2)
				|| contieneAsciiIgnoreCase(texto, TEXTO_MCEF_1) || contieneAsciiIgnoreCase(texto, TEXTO_MCEF_2);
	}

	/**
	 * Búsqueda case-insensitive ASCII sin regionMatches(), sin toLowerCase() y con
	 * filtro rápido por primer carácter.
	 *
	 * Esto es suficiente para estos patrones porque son texto ASCII.
	 */
	private boolean contieneAsciiIgnoreCase(String texto, String buscar) {
		if (texto == null || buscar == null) {
			return false;
		}

		int largoTexto = texto.length();
		int largoBuscar = buscar.length();

		if (largoBuscar == 0) {
			return true;
		}

		if (largoBuscar > largoTexto) {
			return false;
		}

		char primero = buscar.charAt(0);
		char primeroAlt = cambiarCasoAscii(primero);

		int limite = largoTexto - largoBuscar;

		for (int i = 0; i <= limite; i++) {
			char actual = texto.charAt(i);

			if (actual != primero && actual != primeroAlt) {
				continue;
			}

			if (coincideAsciiIgnoreCase(texto, i, buscar)) {
				return true;
			}
		}

		return false;
	}

	private boolean coincideAsciiIgnoreCase(String texto, int inicio, String buscar) {
		for (int i = 0; i < buscar.length(); i++) {
			char a = texto.charAt(inicio + i);
			char b = buscar.charAt(i);

			if (a == b) {
				continue;
			}

			if (a != cambiarCasoAscii(b)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Cambia mayúscula/minúscula solo para ASCII A-Z / a-z.
	 */
	private char cambiarCasoAscii(char c) {
		if (c >= 'A' && c <= 'Z') {
			return (char) (c + 32);
		}

		if (c >= 'a' && c <= 'z') {
			return (char) (c - 32);
		}

		return c;
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaMCEFInicializacion();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 750.0f;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}

		String html = MonitorDePID.idioma.problema_mcef_inicializacion_html();

		if (enlace.isEmpty()) {
			return html;
		}

		return html + " " + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_problema_mcef_inicializacion();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_mod_mcef());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_verificar_compatibilidad_mcef());
		return builder.construir();
	}

	@Override
	public String id() {
		return "problema_mcef_inicializacion";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_MCEF_1, TEXTO_MCEF_2 };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}