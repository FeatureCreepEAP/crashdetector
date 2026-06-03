package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.gui.tipos.scriptide.TipoProyectoScript;

/**
 * Fabrica segura: nunca referencia tipos LSP4J directamente.
 *
 * Si LSP4J y la clase opcional existen, usa el proveedor LSP4J. Si no, cae al
 * completado local.
 */
public class ScriptIntellisenseFactory {

	public static ScriptIntellisense crear(TipoProyectoScript tipo, File carpetaProyecto) {
		try {
			Class.forName("org.eclipse.lsp4j.services.LanguageServer");
			Class.forName("org.eclipse.lsp4j.jsonrpc.Launcher");

			Class<?> clase = Class
					.forName("com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp.ScriptIntellisenseLsp4j");
			ScriptIntellisense proveedor = (ScriptIntellisense) clase.getDeclaredConstructor().newInstance();
			proveedor.iniciar(tipo, carpetaProyecto);

			if (proveedor.disponible()) {
				return proveedor;
			}
		} catch (Throwable t) {
			CrashDetectorLogger.log("Intellisense LSP4J no disponible: " + t.getMessage());
		}

		ScriptIntellisenseLocal local = new ScriptIntellisenseLocal();
		local.iniciar(tipo, carpetaProyecto);
		return local;
	}
}