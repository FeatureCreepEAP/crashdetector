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
public class ItemNoExiste implements VerificacionRapida {

	// Tipos aceptados en el mensaje original de la excepción
	private static final String[] TIPOS_REGISTRO = { "Item:", "Block:", "Fluid:" };

	private static final String PREFIJO_BASE = "java.lang.IllegalStateException: ";
	private static final String SUFIJO = " does not exist";

	// Indica si el log contiene el patrón general del problema
	private boolean posibleError = false;

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

		if (lineaContieneItemNoExiste(evento.linea)) {
			posibleError = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Detección global ligera para evitar trabajo innecesario por línea
		if (consola.contenido_verificar.contains(PREFIJO_BASE) && consola.contenido_verificar.contains(SUFIJO)) {
			for (String tipo : TIPOS_REGISTRO) {
				if (consola.contenido_verificar.contains(tipo)) {
					posibleError = true;
					return;
				}
			}
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleError && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales o si ya fue activado
		if (!posibleError || activado || linea == null) {
			return;
		}

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
	public Verificaciones nueva() {
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