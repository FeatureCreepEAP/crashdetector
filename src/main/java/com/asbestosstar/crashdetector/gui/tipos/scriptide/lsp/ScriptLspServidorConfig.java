package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.gui.tipos.scriptide.TipoProyectoScript;

/**
 * Configuracion de servidores LSP externos.
 *
 * LSP4J es cliente/protocolo; el servidor real debe existir como proceso
 * aparte.
 */
public class ScriptLspServidorConfig {

	public TipoProyectoScript tipo;
	public ConfigString comando;
	public ConfigString carpetaTrabajo;

	public ScriptLspServidorConfig(TipoProyectoScript tipo, ConfigString comando, ConfigString carpetaTrabajo) {
		this.tipo = tipo;
		this.comando = comando;
		this.carpetaTrabajo = carpetaTrabajo;
	}

	public static ScriptLspServidorConfig paraTipo(TipoProyectoScript tipo) {
		if (tipo == TipoProyectoScript.KUBEJS) {
			return new ScriptLspServidorConfig(tipo,
					ConfigString.de("scriptide.lsp.kubejs.comando", "typescript-language-server --stdio"),
					ConfigString.de("scriptide.lsp.kubejs.carpeta", ""));
		}

		if (tipo == TipoProyectoScript.ZENSCRIPT) {
			return new ScriptLspServidorConfig(tipo, ConfigString.de("scriptide.lsp.zenscript.comando", ""),
					ConfigString.de("scriptide.lsp.zenscript.carpeta", ""));
		}

		return new ScriptLspServidorConfig(tipo,
				ConfigString.de("scriptide.lsp." + tipo.name().toLowerCase() + ".comando", ""),
				ConfigString.de("scriptide.lsp." + tipo.name().toLowerCase() + ".carpeta", ""));
	}

	public boolean tieneComando() {
		String c = comando == null ? null : comando.obtener();
		return c != null && !c.trim().isEmpty();
	}

	public List<String> comandoComoLista() {
		return separarArgumentos(comando == null ? "" : comando.obtener());
	}

	public File carpetaTrabajo(File carpetaProyecto) {
		String c = carpetaTrabajo == null ? null : carpetaTrabajo.obtener();
		if (c != null && !c.trim().isEmpty()) {
			return new File(c.trim());
		}
		return carpetaProyecto;
	}

	public static List<String> separarArgumentos(String texto) {
		List<String> ret = new ArrayList<String>();
		if (texto == null) {
			return ret;
		}

		StringBuilder actual = new StringBuilder();
		boolean comillas = false;
		char tipoComilla = 0;

		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);
			if ((c == '"' || c == '\'') && (!comillas || c == tipoComilla)) {
				comillas = !comillas;
				tipoComilla = comillas ? c : 0;
				continue;
			}
			if (Character.isWhitespace(c) && !comillas) {
				if (actual.length() > 0) {
					ret.add(actual.toString());
					actual.setLength(0);
				}
				continue;
			}
			actual.append(c);
		}

		if (actual.length() > 0) {
			ret.add(actual.toString());
		}
		return ret;
	}
}