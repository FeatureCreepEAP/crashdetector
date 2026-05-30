package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

import java.util.LinkedHashSet;
import java.util.Set;

public class JEIItemGroupCrash implements Verificaciones {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleJEIPlugin = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Lista de grupos/plugins que fallaron
	private final Set<String> pluginsAfectados = new LinkedHashSet<>();

	@Override
	public void verificar(Consola consola) {

		// Detección global ligera:
		// Se buscan subcadenas clave sin usar regex
		if (consola.contenido_verificar.contains("Item Group crashed while building contents")
				&& consola.contenido_verificar.contains("JEI ingredient list")) {

			posibleJEIPlugin = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Salida temprana si no hay indicios globales
		if (!posibleJEIPlugin) {
			return;
		}

		// Verificación precisa de la línea
		if (linea.contains("Item Group crashed while building contents") && linea.contains("JEI ingredient list")) {

			// Extraer únicamente el nombre del grupo/plugin usando el último ':'
			int indice = linea.lastIndexOf(":");

			if (indice != -1 && indice + 1 < linea.length()) {

				String nombre = linea.substring(indice + 1).trim();

				// Evitar capturar líneas completas del log o texto inválido
				if (!nombre.isEmpty()) {

					pluginsAfectados.add(nombre);
				}
			}

			// Registrar el error solo una vez
			if (!activado) {
				this.enlace = consola.agregarErrorALectador(num, this);
				this.activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new JEIItemGroupCrash();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1250;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeJEIItemGroupCrash(pluginsAfectados) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreJEIItemGroupCrash();
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
		return "jei_item_group_crash";
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