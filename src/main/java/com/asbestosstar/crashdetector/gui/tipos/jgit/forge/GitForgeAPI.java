package com.asbestosstar.crashdetector.gui.tipos.jgit.forge;

/**
 * API base para crear repositorios en forges como GitHub, Pagure, GitLab, etc.
 *
 * No depende de JGit. Solo se encarga de crear/obtener repos remotos.
 */
public interface GitForgeAPI {

	public String id();

	public String nombreParaMostrar();

	public String urlPorDefecto();

	public ResultadoCrearRepositorio crearRepositorio(ConfigCrearRepositorio config) throws Exception;
}