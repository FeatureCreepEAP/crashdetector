package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta mods faltantes en datapacks. Gracias a Aternos porque esta
 * es una implementacion de su codex:
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModFaltanteEnDatapack implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";

	private final List<String> nombresMods = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	private static final String TEXTO_MOD_FALTANTE = "Missing data pack mod:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_MOD_FALTANTE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea.
	 *
	 * Aquí se detecta el mod exacto y se agrega el enlace a la línea del log.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		int indiceBusqueda = 0;

		while (indiceBusqueda < linea.length()) {
			int indice = linea.indexOf(TEXTO_MOD_FALTANTE, indiceBusqueda);

			if (indice < 0) {
				break;
			}

			int inicioMod = indice + TEXTO_MOD_FALTANTE.length();

			while (inicioMod < linea.length() && Character.isWhitespace(linea.charAt(inicioMod))) {
				inicioMod++;
			}

			int finMod = leerFinModIdDatapack(linea, inicioMod);

			if (finMod > inicioMod) {
				String nombreMod = linea.substring(inicioMod, finMod).trim();

				if (esModIdValido(nombreMod) && !contieneMod(nombreMod)) {
					nombresMods.add(nombreMod);
					enlaces.add(consola.agregarErrorALectador(numero_de_linea, this));
					activado = true;
					actualizarMensaje();
				}

				indiceBusqueda = finMod;
			} else {
				indiceBusqueda = inicioMod + 1;
			}
		}
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
			mensaje = MonitorDePID.idioma.mensajeModFaltanteEnDatapackPlural(nombresMods);
		} else {
			mensaje = MonitorDePID.idioma.mensajeModFaltanteEnDatapackSingular(nombresMods.get(0));
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
	 * Lee el final del modid después de: Missing data pack mod:
	 *
	 * El patron original aceptaba: letras, numeros, guion bajo y guion.
	 */
	private int leerFinModIdDatapack(String texto, int inicio) {
		int i = inicio;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (esCaracterModIdDatapack(c)) {
				i++;
			} else {
				break;
			}
		}

		return i;
	}

	private boolean esModIdValido(String modId) {
		if (modId == null || modId.isEmpty()) {
			return false;
		}

		for (int i = 0; i < modId.length(); i++) {
			if (!esCaracterModIdDatapack(modId.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Equivalente manual de [\w\d\-].
	 *
	 * \w ya incluye letras, numeros y guion bajo. También se acepta guion normal.
	 */
	private boolean esCaracterModIdDatapack(char c) {
		return Character.isLetterOrDigit(c) || c == '_' || c == '-';
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
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModFaltanteEnDatapack();
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
		return MonitorDePID.idioma.nombreProblemaModFaltanteEnDatapack();
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
	public String id() {
		return "mod_faltante_en_datapack";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}