package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta mods faltantes en mundos guardados con Forge. Gracias a
 * Aternos porque esta es una implementacion de su codex:
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModFaltanteEnMundo implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";

	private final List<String> nombresMods = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	private static final String TEXTO_INICIO = "This world was saved with mod ";
	private static final String TEXTO_FINAL = " which appears to be missing";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_INICIO, TEXTO_FINAL };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificacion por linea.
	 *
	 * Aquí se detecta el mod exacto y se guarda el enlace hacia la linea del log.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		int indiceBusqueda = 0;

		while (indiceBusqueda < linea.length()) {
			int inicio = indexOfIgnoreCase(linea, TEXTO_INICIO, indiceBusqueda);

			if (inicio < 0) {
				break;
			}

			int inicioNombre = inicio + TEXTO_INICIO.length();
			int fin = indexOfIgnoreCase(linea, TEXTO_FINAL, inicioNombre);

			if (fin < 0) {
				break;
			}

			String nombreMod = linea.substring(inicioNombre, fin).trim();

			if (esNombreModValido(nombreMod) && !nombreMod.equalsIgnoreCase("forge")
					&& !nombreMod.equalsIgnoreCase("minecraft") && !contieneMod(nombreMod)) {

				nombresMods.add(nombreMod);
				enlaces.add(consola.agregarErrorALectador(numero_de_linea, this));
				activado = true;
				actualizarMensaje();
			}

			indiceBusqueda = fin + TEXTO_FINAL.length();
		}
	}

	private boolean contieneIndicioModFaltanteEnMundo(String linea) {
		return linea.contains(TEXTO_INICIO) || linea.contains(TEXTO_FINAL)
				|| indexOfIgnoreCase(linea, TEXTO_INICIO, 0) >= 0 || indexOfIgnoreCase(linea, TEXTO_FINAL, 0) >= 0;
	}

	/**
	 * Actualiza el mensaje cada vez que se encuentra un nuevo mod faltante.
	 */
	private void actualizarMensaje() {
		if (nombresMods.isEmpty()) {
			mensaje = "";
			return;
		}

		if (nombresMods.size() > 1) {
			mensaje = MonitorDePID.idioma.mensajeWorldModFaltantePlural(nombresMods);
		} else {
			mensaje = MonitorDePID.idioma.mensajeWorldModFaltanteSingular(nombresMods.get(0));
		}

		StringBuilder sb = new StringBuilder(mensaje);

		for (String enlace : enlaces) {
			if (enlace != null && !enlace.isEmpty()) {
				sb.append(" ").append(enlace);
			}
		}

		mensaje = sb.toString();
	}

	/**
	 * Valida el nombre del mod de forma similar a \w+ pero sin usar regex.
	 *
	 * Acepta letras, numeros y guion bajo.
	 */
	private boolean esNombreModValido(String nombreMod) {
		if (nombreMod == null || nombreMod.isEmpty()) {
			return false;
		}

		for (int i = 0; i < nombreMod.length(); i++) {
			char c = nombreMod.charAt(i);

			if (!Character.isLetterOrDigit(c) && c != '_') {
				return false;
			}
		}

		return true;
	}

	/**
	 * Evita duplicados ignorando mayusculas y minusculas.
	 */
	private boolean contieneMod(String nombreMod) {
		for (String modExistente : nombresMods) {
			if (modExistente.equalsIgnoreCase(nombreMod)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Busca un texto ignorando mayusculas/minusculas sin crear copias con
	 * toLowerCase().
	 */
	private int indexOfIgnoreCase(String texto, String buscar, int desde) {
		if (texto == null || buscar == null) {
			return -1;
		}

		int largoTexto = texto.length();
		int largoBuscar = buscar.length();

		if (largoBuscar == 0) {
			return desde;
		}

		if (desde < 0) {
			desde = 0;
		}

		if (largoBuscar > largoTexto || desde >= largoTexto) {
			return -1;
		}

		int limite = largoTexto - largoBuscar;
		char primero = buscar.charAt(0);
		char primeroAlt = cambiarCasoAscii(primero);

		for (int i = desde; i <= limite; i++) {
			char actual = texto.charAt(i);

			if (actual != primero && actual != primeroAlt) {
				continue;
			}

			if (coincideAsciiIgnoreCase(texto, i, buscar)) {
				return i;
			}
		}

		return -1;
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

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModFaltanteEnMundo();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema.
	 */
	@Override
	public float prioridad() {
		return 850.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaWorldModFaltante();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		// Agregar solucion para cada mod faltante
		for (String mod : nombresMods) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(mod));
		}

		return builder.construir();
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public String id() {
		return "mod_faltante_en_mundo";
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
		return true; // Si el usuario elimino un mod.
	}
}