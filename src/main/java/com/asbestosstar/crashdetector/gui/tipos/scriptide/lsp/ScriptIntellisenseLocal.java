package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.asbestosstar.crashdetector.gui.tipos.scriptide.TipoProyectoScript;

/**
 * Respaldo local cuando no hay LSP4J o no hay servidor de lenguaje configurado.
 */
public class ScriptIntellisenseLocal implements ScriptIntellisense {

	public TipoProyectoScript tipoActual;

	@Override
	public boolean disponible() {
		return true;
	}

	@Override
	public String nombre() {
		return "local";
	}

	@Override
	public void iniciar(TipoProyectoScript tipo, File carpetaProyecto) {
		tipoActual = tipo;
	}

	@Override
	public void cerrar() {
	}

	@Override
	public void abrirDocumento(File archivo, String texto) {
	}

	@Override
	public void cambiarDocumento(File archivo, String texto) {
	}

	@Override
	public void cerrarDocumento(File archivo) {
	}

	@Override
	public List<SugerenciaScript> completar(File archivo, String texto, int posicionCaret) {
		String prefijo = prefijo(texto, posicionCaret).toLowerCase();
		List<SugerenciaScript> ret = new ArrayList<SugerenciaScript>();
		for (String palabra : palabras()) {
			if (prefijo.isEmpty() || palabra.toLowerCase().startsWith(prefijo)) {
				ret.add(new SugerenciaScript(palabra, palabra, ""));
			}
		}
		Collections.sort(ret);
		return ret;
	}

	public String prefijo(String texto, int posicionCaret) {
		if (texto == null) {
			return "";
		}
		int pos = Math.max(0, Math.min(posicionCaret, texto.length()));
		int ini = pos;
		while (ini > 0) {
			char c = texto.charAt(ini - 1);
			if (!Character.isLetterOrDigit(c) && c != '_' && c != '$') {
				break;
			}
			ini--;
		}
		return texto.substring(ini, pos);
	}

	public List<String> palabras() {
		if (tipoActual == TipoProyectoScript.KUBEJS) {
			return Arrays.asList("ServerEvents", "ClientEvents", "StartupEvents", "ItemEvents", "BlockEvents",
					"PlayerEvents", "EntityEvents", "LevelEvents", "recipes", "tags", "loaded", "event", "remove",
					"shaped", "shapeless", "smelting", "blasting", "custom", "Item", "Ingredient", "JsonIO");
		}
		if (tipoActual == TipoProyectoScript.ZENSCRIPT) {
			return Arrays.asList("import", "val", "var", "function", "as", "in", "for", "if", "else", "return",
					"recipes", "craftingTable", "removeRecipe", "addShaped", "addShapeless", "IItemStack",
					"IIngredient", "mods", "crafttweaker");
		}
		return Arrays.asList("true", "false", "null");
	}

	@Override
	public List<ProblemaScript> diagnosticos() {
		return new ArrayList<ProblemaScript>();
	}
}