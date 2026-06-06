package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.dmr.ModelNode;

import com.asbestosstar.crashdetector.gui.tipos.scriptide.TipoProyectoScript;

/**
 * Servidor de lenguaje interno para FeatureCreep Datafied Contents.
 *
 * No usa LSP4J ni proceso externo. JBoss DMR debe existir en el classpath.
 */
public class ServidorLenguajeDatafiedFC implements ScriptIntellisense {

	public File carpetaProyecto;
	public final List<ProblemaScript> problemas = new ArrayList<ProblemaScript>();

	@Override
	public boolean disponible() {
		return true;
	}

	@Override
	public String nombre() {
		return "FC Datafied interno";
	}

	@Override
	public void iniciar(TipoProyectoScript tipo, File carpetaProyecto) {
		this.carpetaProyecto = carpetaProyecto;
	}

	@Override
	public void cerrar() {
		problemas.clear();
	}

	@Override
	public void abrirDocumento(File archivo, String texto) {
		validar(archivo, texto);
	}

	@Override
	public void cambiarDocumento(File archivo, String texto) {
		validar(archivo, texto);
	}

	@Override
	public void cerrarDocumento(File archivo) {
		problemas.clear();
	}

	@Override
	public List<SugerenciaScript> completar(File archivo, String texto, int posicionCaret) {
		String prefijo = obtenerPrefijo(texto, posicionCaret).toLowerCase();
		List<SugerenciaScript> ret = new ArrayList<SugerenciaScript>();

		agregarSiCoincide(ret, prefijo, "type", "\"type\" => \"generic_item\"", "Campo requerido");
		agregarSiCoincide(ret, prefijo, "modid", "\"modid\" => \"featurecreep\"", "Campo requerido");
		agregarSiCoincide(ret, prefijo, "item_name", "\"item_name\" => \"sample_item\"", "Campo requerido");
		agregarSiCoincide(ret, prefijo, "id", "\"id\" => 3000", "Campo requerido");
		agregarSiCoincide(ret, prefijo, "group", "\"group\" => \"materials\"", "Campo requerido");

		agregarSiCoincide(ret, prefijo, "generic_item", "\"generic_item\"", "Tipo basico");
		agregarSiCoincide(ret, prefijo, "generic_axe", "\"generic_axe\"", "Herramienta");
		agregarSiCoincide(ret, prefijo, "generic_pickaxe", "\"generic_pickaxe\"", "Herramienta");
		agregarSiCoincide(ret, prefijo, "generic_shovel", "\"generic_shovel\"", "Herramienta");
		agregarSiCoincide(ret, prefijo, "generic_sword", "\"generic_sword\"", "Herramienta");
		agregarSiCoincide(ret, prefijo, "generic_hoe", "\"generic_hoe\"", "Herramienta");

		agregarSiCoincide(ret, prefijo, "attack_damage", "\"attack_damage\" => 1", "Campo de herramienta");
		agregarSiCoincide(ret, prefijo, "attack_speed", "\"attack_speed\" => 1", "Campo de herramienta");
		agregarSiCoincide(ret, prefijo, "material", "\"material\" => {}", "Nodo de material");
		agregarSiCoincide(ret, prefijo, "max_uses", "\"max_uses\" => 250", "Campo de material");
		agregarSiCoincide(ret, prefijo, "efficiency", "\"efficiency\" => 6", "Campo de material");
		agregarSiCoincide(ret, prefijo, "harvest_level", "\"harvest_level\" => 2", "Campo de material");
		agregarSiCoincide(ret, prefijo, "enchantability", "\"enchantability\" => 14", "Campo de material");

		Collections.sort(ret);
		return ret;
	}

	@Override
	public List<ProblemaScript> diagnosticos() {
		return new ArrayList<ProblemaScript>(problemas);
	}

	public void validar(File archivo, String texto) {
		problemas.clear();

		if (texto == null || texto.trim().isEmpty()) {
			return;
		}

		ModelNode node;
		try {
			node = ModelNode.fromString(texto);
		} catch (Throwable t) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "DMR invalido: " + t.getMessage(), 1));
			return;
		}

		requerido(archivo, node, "type");
		requerido(archivo, node, "modid");
		requerido(archivo, node, "item_name");
		requerido(archivo, node, "id");
		requerido(archivo, node, "group");

		if (!node.hasDefined("type")) {
			return;
		}

		String tipo = node.get("type").asString();
		boolean herramienta = tipo.equals("generic_axe") || tipo.equals("generic_pickaxe")
				|| tipo.equals("generic_shovel") || tipo.equals("generic_sword") || tipo.equals("generic_hoe");

		if (!tipo.equals("generic_item") && !herramienta) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Tipo Datafied desconocido: " + tipo, 2));
		}

		if (herramienta) {
			requerido(archivo, node, "attack_damage");
			requerido(archivo, node, "attack_speed");
			requerido(archivo, node, "material");

			if (node.hasDefined("material")) {
				ModelNode mat = node.get("material");
				requerido(archivo, mat, "max_uses");
				requerido(archivo, mat, "efficiency");
				requerido(archivo, mat, "attack_damage");
				requerido(archivo, mat, "harvest_level");
				requerido(archivo, mat, "enchantability");
			}
		}
	}

	public void requerido(File archivo, ModelNode node, String campo) {
		if (node == null || !node.hasDefined(campo)) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Falta campo requerido: " + campo, 1));
		}
	}

	public void agregarSiCoincide(List<SugerenciaScript> ret, String prefijo, String etiqueta, String insercion,
			String detalle) {
		if (prefijo == null || prefijo.isEmpty() || etiqueta.toLowerCase().startsWith(prefijo)) {
			ret.add(new SugerenciaScript(etiqueta, insercion, detalle));
		}
	}

	public String obtenerPrefijo(String texto, int posicionCaret) {
		if (texto == null) {
			return "";
		}

		int pos = Math.max(0, Math.min(posicionCaret, texto.length()));
		int ini = pos;

		while (ini > 0) {
			char c = texto.charAt(ini - 1);
			if (!Character.isLetterOrDigit(c) && c != '_' && c != '-') {
				break;
			}
			ini--;
		}

		return texto.substring(ini, pos);
	}

	public String uri(File archivo) {
		return archivo == null ? "datafied://documento" : archivo.toURI().toString();
	}
}