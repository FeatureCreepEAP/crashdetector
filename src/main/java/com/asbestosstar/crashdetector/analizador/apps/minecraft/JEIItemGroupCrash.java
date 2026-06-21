package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.LinkedHashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class JEIItemGroupCrash implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Lista de grupos/plugins que fallaron
	private final Set<String> pluginsAfectados = new LinkedHashSet<>();

	private static final String ITEM_GROUP_CRASHED = "Item Group crashed while building contents";
	private static final String JEI_INGREDIENT_LIST = "JEI ingredient list";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ITEM_GROUP_CRASHED, JEI_INGREDIENT_LIST };
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

		// Verificación precisa de la línea
		if (lineaContieneCrashJEI(linea)) {

			// Extraer únicamente el nombre del grupo/plugin usando el último ':'
			int indice = linea.lastIndexOf(":");

			if (indice != -1 && indice + 1 < linea.length()) {

				String nombre = limpiarEspacios(linea, indice + 1, linea.length());

				// Evitar capturar líneas completas del log o texto inválido
				if (!nombre.isEmpty()) {

					pluginsAfectados.add(nombre);
				}
			}

			// Registrar el error solo una vez
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneCrashJEI(String linea) {
		return linea.contains(ITEM_GROUP_CRASHED) && linea.contains(JEI_INGREDIENT_LIST);
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
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "jei_item_group_crash";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}