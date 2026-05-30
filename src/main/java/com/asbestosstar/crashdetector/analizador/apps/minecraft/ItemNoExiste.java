package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
	public void verificar(Consola consola) {
		// Detección global ligera para evitar trabajo innecesario por línea
		if (consola.contenido_verificar.contains("java.lang.IllegalStateException:")
				&& consola.contenido_verificar.contains(" does not exist")) {
			for (String tipo : TIPOS_REGISTRO) {
				if (consola.contenido_verificar.contains(tipo)) {
					posibleError = true;
					return;
				}
			}
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales o si ya fue activado
		if (!posibleError || activado) {
			return;
		}

		String prefijoBase = "java.lang.IllegalStateException: ";
		String sufijo = " does not exist";

		if (!linea.contains(prefijoBase) || !linea.contains(sufijo)) {
			return;
		}

		for (String tipo : TIPOS_REGISTRO) {
			String prefijoCompleto = prefijoBase + tipo;

			if (linea.contains(prefijoCompleto)) {
				int inicio = linea.indexOf(prefijoCompleto);
				int fin = linea.indexOf(sufijo, inicio);

				if (inicio >= 0 && fin > inicio) {
					String valor = linea.substring(inicio + prefijoCompleto.length(), fin).trim();

					if (!valor.isEmpty()) {
						this.itemFaltante = valor;

						int separadorNamespace = valor.indexOf(':');
						if (separadorNamespace > 0) {
							this.namespace = valor.substring(0, separadorNamespace).trim();
						}

						this.enlace = consola.agregarErrorALectador(num, this);
						this.activado = true;
						return;
					}
				}
			}
		}
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

	@Override
	public String enlaceACodigo() {
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}