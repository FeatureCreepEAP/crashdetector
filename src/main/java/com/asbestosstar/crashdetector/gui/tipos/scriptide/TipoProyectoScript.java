package com.asbestosstar.crashdetector.gui.tipos.scriptide;

import java.util.Arrays;
import java.util.List;

import com.asbestosstar.crashdetector.MonitorDePID;

public enum TipoProyectoScript {

	FEATURECREEP_DMR_JSON("featurecreep_dmr_json", true, "dmr", "json"),
	DATAPACK_RESOURCEPACK("datapack_resourcepack", true, "json", "mcmeta", "mcfunction"),
	KUBEJS("kubejs", true, "js", "json"), ZENSCRIPT("zenscript", true, "zs", "zenscript"),
	MINEFLAYER("mineflayer", true, "js", "json"), GROOVYSCRIPT("groovyscript", true, "groovy"),
	COMPUTERCRAFT_LUA("computercraft_lua", true, "lua"), WORLDEDIT_CRAFTSCRIPT("worldedit_craftscript", true, "js"),
	JEXEL3("jexel3", true, "jexel", "txt"), JBOSS_FEEL("jboss_feel", true, "feel", "dmn"),
	PARADOX_CWTOOLS("paradox_cwtools", true, "txt", "gui", "asset", "gfx", "mod", "info", "yml", "yaml");

	public final String id;
	public final boolean habilitadoAhora;
	public final List<String> extensiones;

	TipoProyectoScript(String id, boolean habilitadoAhora, String... extensiones) {
		this.id = id;
		this.habilitadoAhora = habilitadoAhora;
		this.extensiones = Arrays.asList(extensiones);
	}

	public String etiqueta() {
		if (this == FEATURECREEP_DMR_JSON) {
			return MonitorDePID.idioma.ideScriptProyectoFeatureCreep();
		}
		if (this == DATAPACK_RESOURCEPACK) {
			return MonitorDePID.idioma.ideScriptProyectoDatapackResourcepack();
		}
		if (this == KUBEJS) {
			return MonitorDePID.idioma.ideScriptProyectoKubeJS();
		}
		if (this == ZENSCRIPT) {
			return MonitorDePID.idioma.ideScriptProyectoZenScript();
		}
		if (this == MINEFLAYER) {
			return MonitorDePID.idioma.ideScriptProyectoMineFlayer();
		}
		if (this == GROOVYSCRIPT) {
			return MonitorDePID.idioma.ideScriptProyectoGroovyScript();
		}
		if (this == COMPUTERCRAFT_LUA) {
			return MonitorDePID.idioma.ideScriptProyectoComputerCraftLua();
		}
		if (this == WORLDEDIT_CRAFTSCRIPT) {
			return MonitorDePID.idioma.ideScriptProyectoWorldEditCraftScript();
		}
		if (this == JEXEL3) {
			return MonitorDePID.idioma.ideScriptProyectoJexel3();
		}
		if (this == JBOSS_FEEL) {
			return "JBoss FEEL";
		}
		if (this == PARADOX_CWTOOLS) {
			return "Paradox CWTools";
		}
		return id;
	}

	public boolean aceptaArchivo(String nombre) {
		if (nombre == null) {
			return false;
		}

		String n = nombre.toLowerCase();
		int p = n.lastIndexOf('.');
		String ext = p < 0 ? "" : n.substring(p + 1);
		return extensiones.contains(ext);
	}

	@Override
	public String toString() {
		return etiqueta();
	}
}