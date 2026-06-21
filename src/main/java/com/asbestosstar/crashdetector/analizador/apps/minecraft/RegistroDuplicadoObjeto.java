package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de registro donde un objeto es registrado dos veces por
 * diferentes mods.
 *
 * Ejemplo: java.lang.IllegalArgumentException: The object follow{7c1bf98b} has
 * been registered twice, using the names tfc:follow and fowlplay:follow.
 *
 * Generalmente causado por: - Dos mods intentando registrar un objeto con el
 * mismo nombre. - Conflictos de IDs entre mods. - Error interno de un mod al no
 * verificar si el nombre ya está en uso.
 */
public class RegistroDuplicadoObjeto implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";
	private String mod1 = "";
	private String mod2 = "";
	private String objeto = "";

	private static final String TEXTO_ILLEGAL_ARGUMENT = "IllegalArgumentException";
	private static final String TEXTO_REGISTERED_TWICE = "has been registered twice";
	private static final String TEXTO_THE_OBJECT = "The object ";
	private static final String TEXTO_USING_THE_NAMES = "using the names ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ILLEGAL_ARGUMENT, TEXTO_REGISTERED_TWICE };
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

		if (linea.contains(TEXTO_ILLEGAL_ARGUMENT) && linea.contains(TEXTO_REGISTERED_TWICE)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Intentamos extraer el nombre del objeto (ej: follow{7c1bf98b})
			int inicioObj = linea.indexOf(TEXTO_THE_OBJECT);
			int finObj = linea.indexOf(" " + TEXTO_REGISTERED_TWICE);
			if (inicioObj != -1 && finObj != -1) {
				objeto = linea.substring(inicioObj + TEXTO_THE_OBJECT.length(), finObj).trim();
			}

			// Intentamos extraer los nombres de los mods (ej: tfc y fowlplay)
			int inicioNombres = linea.indexOf(TEXTO_USING_THE_NAMES);
			if (inicioNombres != -1) {
				String temp = linea.substring(inicioNombres + TEXTO_USING_THE_NAMES.length());

				// Dividimos por " and " que separa ambos registros, pero sin split()
				int separador = temp.indexOf(" and ");
				if (separador >= 0) {
					String parte1 = temp.substring(0, separador);
					String parte2 = temp.substring(separador + " and ".length());

					// Limpiamos el nombre del mod (todo lo que está antes de los dos puntos)
					mod1 = extraerModAntesDeDosPuntos(parte1);
					mod2 = extraerModAntesDeDosPuntos(parte2);

					// Limpieza extra para eliminar puntuación residual
					mod2 = limpiarPuntuacionResidual(mod2);
				}
			}

			activado = true;
		}
	}

	private String extraerModAntesDeDosPuntos(String texto) {
		if (texto == null) {
			return "";
		}

		int dosPuntos = texto.indexOf(':');
		if (dosPuntos >= 0) {
			return texto.substring(0, dosPuntos).trim();
		}

		return texto.trim();
	}

	private String limpiarPuntuacionResidual(String texto) {
		if (texto == null || texto.isEmpty()) {
			return "";
		}

		int punto = texto.indexOf(".");
		if (punto >= 0) {
			texto = texto.substring(0, punto);
		}

		int parentesis = texto.indexOf("(");
		if (parentesis >= 0) {
			texto = texto.substring(0, parentesis);
		}

		return texto.trim();
	}

	@Override
	public Verificaciones nueva() {
		return new RegistroDuplicadoObjeto();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1200.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeRegistroDuplicadoObjeto(mod1, mod2, objeto) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreRegistroDuplicadoObjeto();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "registro_objeto_duplicado";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}