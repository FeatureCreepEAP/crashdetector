package com.asbestosstar.crashdetector.gui.tipos.jgit.forge;

public class ConfigCrearRepositorio {

	public String forgeUrl;
	public String token;

	/*
	 * En Pagure esto puede dejarse vacío para un repo normal:
	 * https://pagure.io/mi-repo
	 *
	 * Si la instancia usa namespaces, se puede usar:
	 * rpms, modules, forks, etc.
	 */
	public String namespace;

	public String propietario;
	public String nombreRepositorio;
	public String descripcion;
	public String urlProyecto;
	public String defaultBranch;

	public boolean privado;
	public boolean crearReadme = true;

	public ConfigCrearRepositorio() {
	}
}