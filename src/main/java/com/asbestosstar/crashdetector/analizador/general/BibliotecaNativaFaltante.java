package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/**
 * Detecta errores de bibliotecas nativas faltantes donde el m&#233;todo nativo
 * no se puede vincular (UnsatisfiedLinkError con m&#233;todos nativos).
 *
 * Esto es diferente de "Failed to locate library" que ya cubre
 * ErrorDeEnlaceInsatisfecho. Este detecta casos como:
 * - UnsatisfiedLinkError: 'long org.lwjgl.system.libc.LibCString.nmemset(...)'
 * - UnsatisfiedLinkError: 'long org.lwjgl.system.linux.DynamicLinkLoader.ndlopen(...)'
 *
 * Com&#250;n en Solaris/Illumos, SPARC, y sistemas donde LWJGL no tiene
 * bibliotecas nativas para la plataforma.
 */
@ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaMesaYNativa" },
		nota = "Cubre deteccion de metodos nativos faltantes en UnsatisfiedLinkError")
public class BibliotecaNativaFaltante implements Verificaciones {

	private static final java.util.Set<String> MENSAJES_GLOBALES_VISTOS = java.util.Collections
			.synchronizedSet(new java.util.HashSet<>());

	/**
	 * Limpia el estado global de mensajes agregados.
	 */
	public static void reiniciarGlobal() {
		MENSAJES_GLOBALES_VISTOS.clear();
	}

	private boolean activado = false;

	private String metodoNativo = "";
	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_ERROR = "UnsatisfiedLinkError: '";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
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

		// Solo procesar si contiene el patrón de método nativo con comillas simples
		// Esto excluye "Failed to locate library:" que ya cubre ErrorDeEnlaceInsatisfecho
		if (!linea.contains(TEXTO_ERROR)) {
			return;
		}

		// Excluir si es "Failed to locate library" (ya cubierto por ErrorDeEnlaceInsatisfecho)
		if (linea.contains("Failed to locate library")) {
			return;
		}

		String metodo = extraerMetodoNativo(linea);
		if (metodo == null || metodo.isEmpty()) {
			return;
		}

		String clave = "nativo_" + metodo;
		if (MENSAJES_GLOBALES_VISTOS.contains(clave)) {
			return;
		}
		MENSAJES_GLOBALES_VISTOS.add(clave);

		this.metodoNativo = metodo;
		this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		this.mensaje = MonitorDePID.idioma.biblioteca_nativa_faltante(metodoNativo) + " " + enlace;
		this.activado = true;
	}

	/**
	 * Extrae el nombre del m&#233;todo nativo entre comillas simples.
	 */
	private String extraerMetodoNativo(String linea) {
		int inicio = linea.indexOf("'");
		if (inicio < 0) {
			return null;
		}
		int fin = linea.indexOf("'", inicio + 1);
		if (fin <= inicio) {
			return null;
		}
		String metodo = linea.substring(inicio + 1, fin).trim();
		if (metodo.isEmpty()) {
			return null;
		}
		return metodo;
	}

	@Override
	public Verificaciones nueva() {
		return new BibliotecaNativaFaltante();
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
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_biblioteca_nativa_faltante();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_para_biblioteca_nativa_faltante_titulo()).construir();
	}

	@Override
	public String id() {
		return "biblioteca_nativa_faltante";
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
	public boolean recomendadoParaCorperata() {
		return true;
	}
}
