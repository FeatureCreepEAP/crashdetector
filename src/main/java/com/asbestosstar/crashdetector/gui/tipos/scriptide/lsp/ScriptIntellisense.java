package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.util.List;

import com.asbestosstar.crashdetector.gui.tipos.scriptide.TipoProyectoScript;

/**
 * Interfaz minima para intellisense del IDE de scripts.
 *
 * Esta interfaz no depende de LSP4J. La GUI puede compilar y funcionar aunque
 * las dependencias opcionales no existan.
 */
public interface ScriptIntellisense {

	public boolean disponible();

	public String nombre();

	public void iniciar(TipoProyectoScript tipo, File carpetaProyecto) throws Exception;

	public void cerrar();

	public void abrirDocumento(File archivo, String texto) throws Exception;

	public void cambiarDocumento(File archivo, String texto) throws Exception;

	public void cerrarDocumento(File archivo) throws Exception;

	public List<SugerenciaScript> completar(File archivo, String texto, int posicionCaret) throws Exception;

	public List<ProblemaScript> diagnosticos();
}