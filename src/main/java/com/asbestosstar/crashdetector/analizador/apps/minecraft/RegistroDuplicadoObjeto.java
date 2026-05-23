package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
	private boolean analizarLineas = false;
	private String enlace = "";
	private String mod1 = "";
	private String mod2 = "";
	private String objeto = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para activar el análisis línea por línea
		if (log.contains("IllegalArgumentException") && log.contains("has been registered twice")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("IllegalArgumentException") && linea.contains("has been registered twice")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			// Intentamos extraer el nombre del objeto (ej: follow{7c1bf98b})
			int inicioObj = linea.indexOf("The object ");
			int finObj = linea.indexOf(" has been registered twice");
			if (inicioObj != -1 && finObj != -1) {
				objeto = linea.substring(inicioObj + "The object ".length(), finObj).trim();
			}

			// Intentamos extraer los nombres de los mods (ej: tfc y fowlplay)
			int inicioNombres = linea.indexOf("using the names ");
			if (inicioNombres != -1) {
				String temp = linea.substring(inicioNombres + "using the names ".length());
				// Dividimos por " and " que separa ambos registros
				String[] partes = temp.split(" and ");
				if (partes.length >= 2) {
					// Limpiamos el nombre del mod (todo lo que está antes de los dos puntos)
					mod1 = partes[0].contains(":") ? partes[0].split(":")[0] : partes[0];
					mod2 = partes[1].contains(":") ? partes[1].split(":")[0] : partes[1];

					// Limpieza extra para eliminar puntuación residual
					if (mod2.contains(".")) {
						mod2 = mod2.substring(0, mod2.indexOf("."));
					}
					if (mod2.contains("(")) {
						mod2 = mod2.substring(0, mod2.indexOf("(")).trim();
					}
				}
			}

			activado = true;
		}
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
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}