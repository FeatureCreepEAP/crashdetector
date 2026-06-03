package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.services.LanguageClient;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Cliente LSP minimo para recibir mensajes y diagnosticos del servidor.
 */
public class ScriptLspClient implements LanguageClient {

	public final Map<String, List<ProblemaScript>> diagnosticosPorUri = new ConcurrentHashMap<String, List<ProblemaScript>>();

	@Override
	public void telemetryEvent(Object object) {
	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		if (diagnostics == null || diagnostics.getUri() == null) {
			return;
		}

		List<ProblemaScript> lista = new ArrayList<ProblemaScript>();
		if (diagnostics.getDiagnostics() != null) {
			for (Diagnostic d : diagnostics.getDiagnostics()) {
				if (d == null || d.getRange() == null || d.getRange().getStart() == null) {
					continue;
				}
				int severidad = d.getSeverity() == null ? 0 : d.getSeverity().getValue();
				lista.add(new ProblemaScript(diagnostics.getUri(), d.getRange().getStart().getLine() + 1,
						d.getRange().getStart().getCharacter() + 1, d.getMessage(), severidad));
			}
		}
		diagnosticosPorUri.put(diagnostics.getUri(), lista);
	}

	@Override
	public void showMessage(MessageParams messageParams) {
		if (messageParams != null) {
			CrashDetectorLogger.log("LSP: " + messageParams.getMessage());
		}
	}

	@Override
	public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
		return CompletableFuture.completedFuture(new MessageActionItem("OK"));
	}

	@Override
	public void logMessage(MessageParams message) {
		if (message != null) {
			CrashDetectorLogger.log("LSP log: " + message.getMessage());
		}
	}

	public List<ProblemaScript> todosDiagnosticos() {
		List<ProblemaScript> ret = new ArrayList<ProblemaScript>();
		for (List<ProblemaScript> l : diagnosticosPorUri.values()) {
			if (l != null) {
				ret.addAll(l);
			}
		}
		return Collections.unmodifiableList(ret);
	}
}