package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Statics;
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

		if (tipo == TipoProyectoScript.MINEFLAYER) {
			/*
			 * Mineflayer es principalmente una API de JavaScript para crear bots de
			 * Minecraft. Como el paquete npm de mineflayer trae definiciones TypeScript en
			 * index.d.ts, el servidor typescript-language-server puede dar completado,
			 * navegacion y diagnosticos para scripts .js.
			 *
			 * Requisitos del proyecto Mineflayer: npm install mineflayer
			 *
			 * Requisitos del servidor de lenguaje: npm install -g typescript
			 * typescript-language-server
			 *
			 * Plugins opcionales comunes: npm install mineflayer-pathfinder
			 */
			return new ScriptLspServidorConfig(tipo,
					ConfigString.de("scriptide.lsp.mineflayer.comando", "typescript-language-server --stdio"),
					ConfigString.de("scriptide.lsp.mineflayer.carpeta", ""));
		}

		if (tipo == TipoProyectoScript.ZENSCRIPT) {
			return new ScriptLspServidorConfig(tipo, ConfigString.de("scriptide.lsp.zenscript.comando", ""),
					ConfigString.de("scriptide.lsp.zenscript.carpeta", ""));
		}

		if (tipo == TipoProyectoScript.GROOVYSCRIPT) {
			/*
			 * Groovy debe usar un servidor LSP externo.
			 *
			 * Servidor recomendado: GroovyLanguageServer/groovy-language-server
			 *
			 * Este archivo debe existir en:
			 * Statics.carpeta_mundial/groovy-language-server-all.jar
			 *
			 * Si no existe, dejamos el comando vacio para que la fabrica caiga al
			 * intellisense local en lugar de intentar arrancar un proceso roto.
			 */
			File servidorGroovy = Statics.carpeta_mundial.resolve("groovy-language-server-all.jar").toFile();

			String comandoGroovy = "";
			if (servidorGroovy.exists() && servidorGroovy.isFile()) {
				comandoGroovy = "java -jar \"" + servidorGroovy.getAbsolutePath() + "\"";
			}

			return new ScriptLspServidorConfig(tipo,
					ConfigString.de("scriptide.lsp.groovyscript.comando", comandoGroovy),
					ConfigString.de("scriptide.lsp.groovyscript.carpeta", ""));
		}

		if (tipo == TipoProyectoScript.WORLDEDIT_CRAFTSCRIPT) {
			return new ScriptLspServidorConfig(tipo,
					ConfigString.de("scriptide.lsp.worldedit_craftscript.comando",
							"typescript-language-server --stdio"),
					ConfigString.de("scriptide.lsp.worldedit_craftscript.carpeta", ""));
		}

		if (tipo == TipoProyectoScript.COMPUTERCRAFT_LUA) {
			/*
			 * ComputerCraft / CC:Tweaked usa Lua, no JavaScript.
			 *
			 * Servidor recomendado: lua-language-server
			 *
			 * Para autocompletado real de CC:Tweaked, tambien conviene instalar una
			 * biblioteca de definiciones para LuaLS: nvim-computercraft/lua-ls-cc-tweaked
			 *
			 * CC:Tweaked usa Cobalt y se comporta principalmente como Lua 5.2, con algunas
			 * funciones de Lua 5.3. Por eso generamos .luarc.json para ajustar el runtime y
			 * registrar la carpeta de tipos de CC:Tweaked si existe.
			 */
			return new ScriptLspServidorConfig(tipo,
					ConfigString.de("scriptide.lsp.computercraft_lua.comando", "lua-language-server"),
					ConfigString.de("scriptide.lsp.computercraft_lua.carpeta", ""));
		}

		if (tipo == TipoProyectoScript.DATAPACK_RESOURCEPACK) {
			/*
			 * Datapacks/resourcepacks no deberian usar TypeScript.
			 *
			 * Mejor opcion para datapacks: SpyglassMC / Spyglass
			 *
			 * Sirve para mcfunction, JSON/NBT de datapacks, diagnosticos, autocompletado y
			 * analisis especifico de Minecraft Java Edition.
			 *
			 * Para resourcepacks puros, especialmente modelos/blockstates/lang/pack.mcmeta,
			 * puede hacer falta complementar con JSON schemas, porque Spyglass esta mas
			 * enfocado en datapacks.
			 */
			return new ScriptLspServidorConfig(tipo,
					ConfigString.de("scriptide.lsp.datapack_resourcepack.comando",
							"spyglassmc language-server --stdio"),
					ConfigString.de("scriptide.lsp.datapack_resourcepack.carpeta", ""));
		}

		if (tipo == TipoProyectoScript.PARADOX_CWTOOLS) {
			/*
			 * CWTools / cwtools-vscode: https://github.com/cwtools/cwtools-vscode
			 *
			 * Este LSP se usa para scripts Clausewitz/Paradox.
			 *
			 * Configs esperadas dentro de: Statics.carpeta_mundial/cwtools_lsp/configs/
			 *
			 * Subcarpetas: vic2-config vic3-config ck2-config eu4-config hoi4-config
			 * stellaris-config ir-config ck3-config
			 */
			File carpeta = CwtoolsCarpetas.carpetaCwtoolsLsp();
			File servidor = new File(carpeta, "cwtools-language-server");

			String comando = "";
			if (servidor.exists() && servidor.isFile()) {
				comando = "\"" + servidor.getAbsolutePath() + "\"";
			}

			return new ScriptLspServidorConfig(tipo, ConfigString.de("scriptide.lsp.paradox_cwtools.comando", comando),
					ConfigString.de("scriptide.lsp.paradox_cwtools.carpeta", carpeta.getAbsolutePath()));
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