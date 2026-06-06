package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageServer;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.gui.tipos.scriptide.TipoProyectoScript;

/**
 * Proveedor real de intellisense por LSP4J.
 *
 * Esta clase se debe compilar solamente cuando LSP4J esta en el classpath, o en
 * un perfil Maven opcional. La GUI principal nunca la referencia directamente.
 */
public class ScriptIntellisenseLsp4j implements ScriptIntellisense {

	public TipoProyectoScript tipo;
	public File carpetaProyecto;
	public ScriptLspServidorConfig configServidor;
	public ScriptLspClient cliente;
	public LanguageServer servidor;
	public Process proceso;
	public Future<Void> escucha;
	public boolean iniciado;
	public int versionDocumento = 1;

	@Override
	public boolean disponible() {
		return iniciado && servidor != null;
	}

	@Override
	public String nombre() {
		return "LSP4J";
	}

	@Override
	public void iniciar(TipoProyectoScript tipo, File carpetaProyecto) throws Exception {
		this.tipo = tipo;
		this.carpetaProyecto = carpetaProyecto;
		this.configServidor = ScriptLspServidorConfig.paraTipo(tipo);

		if (configServidor == null || !configServidor.tieneComando()) {
			iniciado = false;
			return;
		}

		List<String> comando = configServidor.comandoComoLista();
		if (comando.isEmpty()) {
			iniciado = false;
			return;
		}

		ProcessBuilder pb = new ProcessBuilder(comando);
		File trabajo = configServidor.carpetaTrabajo(carpetaProyecto);
		if (trabajo != null && trabajo.isDirectory()) {
			pb.directory(trabajo);
		}

		// For KubeJS, generate a tsconfig.json that includes the probe-generated .d.ts
		// files
		if (tipo == TipoProyectoScript.KUBEJS && carpetaProyecto != null) {
			generarTsconfigKubeJS(carpetaProyecto);
		}

		if (tipo == TipoProyectoScript.MINEFLAYER && carpetaProyecto != null) {
			generarJsconfigMineflayer(carpetaProyecto);
		}

		if (tipo == TipoProyectoScript.WORLDEDIT_CRAFTSCRIPT && carpetaProyecto != null) {
			generarJsconfigWorldEditCraftScript(carpetaProyecto);
			generarTiposWorldEditCraftScript(carpetaProyecto);
		}
		if (tipo == TipoProyectoScript.COMPUTERCRAFT_LUA && carpetaProyecto != null) {
			generarLuarcComputerCraft(carpetaProyecto);
		}

		pb.redirectErrorStream(true);
		proceso = pb.start();

		cliente = new ScriptLspClient();
		Launcher<LanguageServer> launcher = Launcher.createLauncher(cliente, LanguageServer.class,
				proceso.getInputStream(), proceso.getOutputStream());
		escucha = launcher.startListening();
		servidor = launcher.getRemoteProxy();

		InitializeParams params = new InitializeParams();
		if (carpetaProyecto != null) {
			URI uri = carpetaProyecto.toURI();
			params.setRootUri(uri.toString());
			WorkspaceFolder wf = new WorkspaceFolder();
			wf.setUri(uri.toString());
			wf.setName(carpetaProyecto.getName());
			params.setWorkspaceFolders(java.util.Collections.singletonList(wf));
		}

		CompletableFuture<InitializeResult> futuro = servidor.initialize(params);
		futuro.get(10, TimeUnit.SECONDS);
		servidor.initialized(null);
		iniciado = true;
	}

	@Override
	public void cerrar() {
		try {
			if (servidor != null) {
				servidor.shutdown().get(3, TimeUnit.SECONDS);
				servidor.exit();
			}
		} catch (Throwable t) {
			CrashDetectorLogger.log("Error cerrando LSP: " + t.getMessage());
		}
		try {
			if (proceso != null) {
				proceso.destroy();
			}
		} catch (Throwable ignored) {
		}
		iniciado = false;
	}

	@Override
	public void abrirDocumento(File archivo, String texto) throws Exception {
		if (!disponible() || archivo == null) {
			return;
		}
		TextDocumentItem item = new TextDocumentItem();
		item.setUri(uri(archivo));
		item.setLanguageId(languageId(archivo));
		item.setVersion(versionDocumento++);
		item.setText(texto == null ? "" : texto);
		servidor.getTextDocumentService().didOpen(new DidOpenTextDocumentParams(item));
	}

	@Override
	public void cambiarDocumento(File archivo, String texto) throws Exception {
		if (!disponible() || archivo == null) {
			return;
		}
		VersionedTextDocumentIdentifier id = new VersionedTextDocumentIdentifier();
		id.setUri(uri(archivo));
		id.setVersion(versionDocumento++);

		TextDocumentContentChangeEvent cambio = new TextDocumentContentChangeEvent();
		cambio.setText(texto == null ? "" : texto);

		List<TextDocumentContentChangeEvent> cambios = new ArrayList<TextDocumentContentChangeEvent>();
		cambios.add(cambio);

		servidor.getTextDocumentService().didChange(new DidChangeTextDocumentParams(id, cambios));
	}

	public static void generarJsconfigMineflayer(File carpetaProyecto) {
		File jsconfig = new File(carpetaProyecto, "jsconfig.json");
		if (jsconfig.exists()) {
			return;
		}

		String contenido = "{\n" + "  \"compilerOptions\": {\n" + "    \"target\": \"ES2020\",\n"
				+ "    \"module\": \"commonjs\",\n" + "    \"checkJs\": true,\n" + "    \"allowJs\": true,\n"
				+ "    \"noEmit\": true,\n" + "    \"moduleResolution\": \"node\"\n" + "  },\n" + "  \"include\": [\n"
				+ "    \"**/*.js\"\n" + "  ],\n" + "  \"exclude\": [\n" + "    \"node_modules\"\n" + "  ]\n" + "}\n";

		try (FileWriter fw = new FileWriter(jsconfig)) {
			fw.write(contenido);
		} catch (Throwable t) {
			CrashDetectorLogger.log("No se pudo generar jsconfig.json para Mineflayer: " + t.getMessage());
		}
	}

	public static void generarLuarcComputerCraft(File carpetaProyecto) {
		File luarc = new File(carpetaProyecto, ".luarc.json");
		if (luarc.exists()) {
			return;
		}

		File tiposCC = new File(carpetaProyecto, ".crashdetector/types/cc-tweaked");

		String rutaTipos = tiposCC.getPath().replace("\\", "\\\\");

		String contenido = "{\n" + "  \"runtime.version\": \"Lua 5.2\",\n" + "  \"runtime.special\": {\n"
				+ "    \"include\": \"require\"\n" + "  },\n" + "  \"diagnostics.globals\": [\n" + "    \"_HOST\",\n"
				+ "    \"_CC_DEFAULT_SETTINGS\",\n" + "    \"colors\",\n" + "    \"colours\",\n" + "    \"commands\",\n"
				+ "    \"disk\",\n" + "    \"fs\",\n" + "    \"gps\",\n" + "    \"help\",\n" + "    \"http\",\n"
				+ "    \"io\",\n" + "    \"keys\",\n" + "    \"multishell\",\n" + "    \"os\",\n"
				+ "    \"paintutils\",\n" + "    \"parallel\",\n" + "    \"peripheral\",\n" + "    \"pocket\",\n"
				+ "    \"rednet\",\n" + "    \"redstone\",\n" + "    \"rs\",\n" + "    \"settings\",\n"
				+ "    \"shell\",\n" + "    \"term\",\n" + "    \"textutils\",\n" + "    \"turtle\",\n"
				+ "    \"vector\",\n" + "    \"window\"\n" + "  ],\n" + "  \"workspace.library\": [\n" + "    \""
				+ rutaTipos + "\"\n" + "  ],\n" + "  \"workspace.checkThirdParty\": false,\n"
				+ "  \"telemetry.enable\": false\n" + "}\n";

		try (FileWriter fw = new FileWriter(luarc)) {
			fw.write(contenido);
		} catch (Throwable t) {
			CrashDetectorLogger.log("No se pudo generar .luarc.json para CC:Tweaked: " + t.getMessage());
		}
	}

	public static void generarJsconfigWorldEditCraftScript(File carpetaProyecto) {
		File jsconfig = new File(carpetaProyecto, "jsconfig.json");
		if (jsconfig.exists()) {
			return;
		}

		String contenido = "{\n" + "  \"compilerOptions\": {\n" + "    \"target\": \"ES2020\",\n"
				+ "    \"module\": \"commonjs\",\n" + "    \"allowJs\": true,\n" + "    \"checkJs\": true,\n"
				+ "    \"noEmit\": true,\n" + "    \"moduleResolution\": \"node\",\n" + "    \"types\": [],\n"
				+ "    \"typeRoots\": [\"./.crashdetector/types\"]\n" + "  },\n" + "  \"include\": [\n"
				+ "    \"**/*.js\",\n" + "    \".crashdetector/types/**/*.d.ts\"\n" + "  ],\n" + "  \"exclude\": [\n"
				+ "    \"node_modules\"\n" + "  ]\n" + "}\n";

		try (FileWriter fw = new FileWriter(jsconfig)) {
			fw.write(contenido);
		} catch (Throwable t) {
			CrashDetectorLogger.log("No se pudo generar jsconfig.json para WorldEdit CraftScript: " + t.getMessage());
		}
	}

	public static void generarTiposWorldEditCraftScript(File carpetaProyecto) {
		File carpetaTipos = new File(carpetaProyecto, ".crashdetector/types/worldedit-craftscript");
		if (!carpetaTipos.exists()) {
			carpetaTipos.mkdirs();
		}

		File tipos = new File(carpetaTipos, "index.d.ts");

		String contenido = "" + "declare var argv: string[];\n" + "declare var player: WorldEditPlayer;\n"
				+ "declare var context: WorldEditCraftScriptContext;\n" + "declare var Packages: any;\n"
				+ "declare function importPackage(pkg: any): void;\n"
				+ "declare function importClass(cls: any): void;\n" + "\n" + "interface WorldEditCraftScriptContext {\n"
				+ "  checkArgs(min: number, max: number, usage: string): void;\n"
				+ "  remember(): WorldEditEditSession;\n" + "  getBlock(id: string): WorldEditBlockState;\n"
				+ "  getSafeOpenFile(folder: string, filename: string, defaultExt: string, allowedExts: string[]): any;\n"
				+ "}\n" + "\n" + "interface WorldEditPlayer {\n" + "  print(message: string): void;\n"
				+ "  printError(message: string): void;\n" + "  getBlockIn(): WorldEditBlockVectorLike;\n"
				+ "  getBlockOn(): WorldEditBlockVectorLike;\n" + "  getPosition(): WorldEditVectorLike;\n" + "}\n"
				+ "\n" + "interface WorldEditEditSession {\n"
				+ "  setBlock(position: WorldEditVectorLike, block: WorldEditBlockState): void;\n"
				+ "  getBlock(position: WorldEditVectorLike): WorldEditBlockState;\n" + "}\n" + "\n"
				+ "interface WorldEditVectorLike {\n" + "  add(x: number, y: number, z: number): WorldEditVectorLike;\n"
				+ "  subtract(x: number, y: number, z: number): WorldEditVectorLike;\n"
				+ "  multiply(x: number): WorldEditVectorLike;\n" + "  divide(x: number): WorldEditVectorLike;\n"
				+ "  toVector(): WorldEditVectorLike;\n" + "  toBlockPoint(): WorldEditBlockVectorLike;\n" + "}\n"
				+ "\n" + "interface WorldEditBlockVectorLike extends WorldEditVectorLike {}\n" + "\n"
				+ "interface WorldEditBlockState {\n" + "  id?: string;\n" + "}\n";

		try (FileWriter fw = new FileWriter(tipos)) {
			fw.write(contenido);
		} catch (Throwable t) {
			CrashDetectorLogger.log("No se pudo generar tipos WorldEdit CraftScript: " + t.getMessage());
		}
	}

	@Override
	public void cerrarDocumento(File archivo) throws Exception {
		if (!disponible() || archivo == null) {
			return;
		}
		servidor.getTextDocumentService()
				.didClose(new DidCloseTextDocumentParams(new TextDocumentIdentifier(uri(archivo))));
	}

	@Override
	public List<SugerenciaScript> completar(File archivo, String texto, int posicionCaret) throws Exception {
		if (!disponible() || archivo == null) {
			return Collections.emptyList();
		}

		CompletionParams params = new CompletionParams();
		TextDocumentPositionParams base = new TextDocumentPositionParams();
		base.setTextDocument(new TextDocumentIdentifier(uri(archivo)));
		base.setPosition(posicion(texto, posicionCaret));
		params.setTextDocument(base.getTextDocument());
		params.setPosition(base.getPosition());

		CompletableFuture<Either<List<CompletionItem>, org.eclipse.lsp4j.CompletionList>> futuro = servidor
				.getTextDocumentService().completion(params);
		Either<List<CompletionItem>, org.eclipse.lsp4j.CompletionList> respuesta = futuro.get(4, TimeUnit.SECONDS);

		List<CompletionItem> items = new ArrayList<CompletionItem>();
		if (respuesta == null) {
			return Collections.emptyList();
		}
		if (respuesta.isLeft() && respuesta.getLeft() != null) {
			items.addAll(respuesta.getLeft());
		} else if (respuesta.isRight() && respuesta.getRight() != null && respuesta.getRight().getItems() != null) {
			items.addAll(respuesta.getRight().getItems());
		}

		List<SugerenciaScript> ret = new ArrayList<SugerenciaScript>();
		for (CompletionItem item : items) {
			if (item == null || item.getLabel() == null) {
				continue;
			}
			String insertar = item.getInsertText() == null ? item.getLabel() : item.getInsertText();
			String detalle = item.getDetail() == null ? "" : item.getDetail();
			ret.add(new SugerenciaScript(item.getLabel(), insertar, detalle));
		}
		Collections.sort(ret);
		return ret;
	}

	@Override
	public List<ProblemaScript> diagnosticos() {
		if (cliente == null) {
			return new ArrayList<ProblemaScript>();
		}
		return cliente.todosDiagnosticos();
	}

	public static void generarTsconfigKubeJS(File carpetaProyecto) {
		File tsconfig = new File(carpetaProyecto, "tsconfig.json");
		if (tsconfig.exists()) {
			return; // respect existing tsconfig
		}
		String contenido = "{\n" + "  \"compilerOptions\": {\n" + "    \"target\": \"ES6\",\n"
				+ "    \"module\": \"commonjs\",\n" + "    \"allowJs\": true,\n" + "    \"checkJs\": false,\n"
				+ "    \"noEmit\": true,\n" + "    \"typeRoots\": []\n" + "  },\n" + "  \"include\": [\n"
				+ "    \"**/*.js\",\n" + "    \"probe/generated/**/*.d.ts\"\n" + "  ],\n" + "  \"exclude\": [\n"
				+ "    \"node_modules\"\n" + "  ]\n" + "}\n";
		try (FileWriter fw = new FileWriter(tsconfig)) {
			fw.write(contenido);
		} catch (Throwable t) {
			CrashDetectorLogger.log("No se pudo generar tsconfig.json: " + t.getMessage());
		}
	}

	public String uri(File archivo) {
		return archivo.toURI().toString();
	}

	public String languageId(File archivo) {
		if (tipo == TipoProyectoScript.KUBEJS) {
			return "javascript";
		}
		if (tipo == TipoProyectoScript.MINEFLAYER) {
			return "javascript";
		}
		if (tipo == TipoProyectoScript.WORLDEDIT_CRAFTSCRIPT) {
			return "javascript";
		}
		if (tipo == TipoProyectoScript.ZENSCRIPT) {
			return "zenscript";
		}
		if (tipo == TipoProyectoScript.COMPUTERCRAFT_LUA) {
			return "lua";
		}
		if (tipo == TipoProyectoScript.GROOVYSCRIPT) {
			return "groovy";
		}
		if (tipo == TipoProyectoScript.DATAPACK_RESOURCEPACK) {
			String nombre = archivo == null ? "" : archivo.getName().toLowerCase();

			if (nombre.endsWith(".mcfunction")) {
				return "mcfunction";
			}
			if (nombre.endsWith(".json") || nombre.endsWith(".mcmeta")) {
				return "json";
			}
		}
		return "plaintext";
	}

	public Position posicion(String texto, int posicionCaret) {
		String t = texto == null ? "" : texto;
		int pos = Math.max(0, Math.min(posicionCaret, t.length()));
		int linea = 0;
		int columna = 0;
		for (int i = 0; i < pos; i++) {
			char c = t.charAt(i);
			if (c == '\n') {
				linea++;
				columna = 0;
			} else {
				columna++;
			}
		}
		return new Position(linea, columna);
	}
}