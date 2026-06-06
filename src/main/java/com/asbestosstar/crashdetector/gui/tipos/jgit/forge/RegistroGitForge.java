package com.asbestosstar.crashdetector.gui.tipos.jgit.forge;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registro de APIs de forges. Ahora queda preparado para GitHub, Pagure, etc.
 */
public class RegistroGitForge {

	public static final Map<String, GitForgeAPI> APIS = new LinkedHashMap<String, GitForgeAPI>();

	public static void registrarPredeterminados() {
		registrar(new PagureGitForgeAPI());
		registrar(new GitHubGitForgeAPI());
		registrar(new GitLabGitForgeAPI());
		registrar(new BitbucketCloudGitForgeAPI());
		registrar(new BitbucketServerGitForgeAPI());
		registrar(new BeanstalkGitForgeAPI());

	}

	public static void registrar(GitForgeAPI api) {
		if (api != null) {
			APIS.put(api.id(), api);
		}
	}

	public static Map<String, GitForgeAPI> obtenerApis() {
		return APIS;
	}

	public static GitForgeAPI obtener(String id) {
		return APIS.get(id);
	}
}