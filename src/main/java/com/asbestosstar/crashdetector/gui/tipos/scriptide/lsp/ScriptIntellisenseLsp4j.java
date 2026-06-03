package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
	public CompletableFuture<Void> escucha;
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
		item.setLanguageId(languageId());
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

	@Override
	public void cerrarDocumento(File archivo) throws Exception {
		if (!disponible() || archivo == null) {
			return;
		}
		servidor.getTextDocumentService().didClose(new DidCloseTextDocumentParams(new TextDocumentIdentifier(uri(archivo))));
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

	public String uri(File archivo) {
		return archivo.toURI().toString();
	}

	public String languageId() {
		if (tipo == TipoProyectoScript.KUBEJS) {
			return "javascript";
		}
		if (tipo == TipoProyectoScript.ZENSCRIPT) {
			return "zenscript";
		}
		if (tipo == TipoProyectoScript.COMPUTERCRAFT_LUA) {
			return "lua";
		}
		if (tipo == TipoProyectoScript.MINEFLAYER) {
			return "python";
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