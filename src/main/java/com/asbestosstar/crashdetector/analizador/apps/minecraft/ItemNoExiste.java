package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores donde un datapack, mod o configuración hace referencia a un
 * registro que no existe en el juego.
 *
 * <p>
 * Aunque el mensaje al usuario sigue hablando de "ítem" para mantener la
 * localización existente, esta verificación también acepta patrones como:
 *
 * <ul>
 * <li>java.lang.IllegalStateException: Item: namespace:cosa does not exist</li>
 * <li>java.lang.IllegalStateException: Block: namespace:cosa does not
 * exist</li>
 * <li>java.lang.IllegalStateException: Fluid: namespace:cosa does not
 * exist</li>
 * </ul>
 *
 * <p>
 * Esto suele significar que en alguna parte se está pidiendo contenido cuyo mod
 * no está instalado, está en una versión incorrecta, o tiene incompatibilidades
 * / contenido removido.
 */
public class ItemNoExiste implements Verificaciones {

	// Tipos aceptados en el mensaje original de la excepción
	private static final String[] TIPOS_REGISTRO = { "Item:", "Block:", "Fluid:" };

	private static final String PREFIJO_BASE = "java.lang.IllegalStateException: ";
	private static final String SUFIJO = " does not exist";

	// Indica si esta verificación ya fue activada
	private boolean activado = false;

	// Guarda el valor faltante detectado, por ejemplo "galosphere:silver_ingot"
	private String itemFaltante = "";

	// Guarda el namespace detectado (parte antes de :)
	private String namespace = "";

	// Enlace a la línea representativa del error
	private String enlace = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { PREFIJO_BASE, "Item:", "Block:", "Fluid:", SUFIJO };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		if (!linea.contains(PREFIJO_BASE) || !linea.contains(SUFIJO)) {
			return;
		}

		for (String tipo : TIPOS_REGISTRO) {
			String prefijoCompleto = PREFIJO_BASE + tipo;

			if (linea.contains(prefijoCompleto)) {
				int inicio = linea.indexOf(prefijoCompleto);
				int fin = linea.indexOf(SUFIJO, inicio);

				if (inicio >= 0 && fin > inicio) {
					String valor = limpiarEspacios(linea, inicio + prefijoCompleto.length(), fin);

					if (!valor.isEmpty()) {
						this.itemFaltante = valor;

						int separadorNamespace = valor.indexOf(':');
						if (separadorNamespace > 0) {
							this.namespace = limpiarEspacios(valor, 0, separadorNamespace);
						}

						this.enlace = consola.agregarErrorALectador(num, this);
						this.activado = true;
						return;
					}
				}
			}
		}
	}

	private boolean lineaContieneItemNoExiste(String linea) {
		if (!linea.contains(PREFIJO_BASE) || !linea.contains(SUFIJO)) {
			return false;
		}

		for (String tipo : TIPOS_REGISTRO) {
			if (linea.contains(PREFIJO_BASE + tipo)) {
				return true;
			}
		}

		return false;
	}

	private String limpiarEspacios(String texto, int inicio, int fin) {
		while (inicio < fin && texto.charAt(inicio) <= ' ') {
			inicio++;
		}

		while (fin > inicio && texto.charAt(fin - 1) <= ' ') {
			fin--;
		}

		if (inicio >= fin) {
			return "";
		}

		return texto.substring(inicio, fin);
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ItemNoExiste();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeItemNoExiste(itemFaltante, namespace) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreItemNoExiste();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "item_no_existe";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}