package com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fotografía del estado del repositorio actual.
 */
public final class EstadoGitAvanzado {

	private final String ramaActual;
	private final String remote;
	private final List<String> ramas;
	private final List<CambioGitAvanzado> preparados;
	private final List<CambioGitAvanzado> noPreparados;
	private final List<CommitGitAvanzado> historial;

	public EstadoGitAvanzado(String ramaActual, String remote, List<String> ramas, List<CambioGitAvanzado> preparados,
			List<CambioGitAvanzado> noPreparados, List<CommitGitAvanzado> historial) {
		this.ramaActual = ramaActual == null ? "" : ramaActual;
		this.remote = remote == null ? "" : remote;
		this.ramas = copia(ramas);
		this.preparados = copiaCambios(preparados);
		this.noPreparados = copiaCambios(noPreparados);
		this.historial = copiaCommits(historial);
	}

	private static List<String> copia(List<String> valores) {
		return valores == null ? Collections.<String>emptyList()
				: Collections.unmodifiableList(new ArrayList<String>(valores));
	}

	private static List<CambioGitAvanzado> copiaCambios(List<CambioGitAvanzado> valores) {
		return valores == null ? Collections.<CambioGitAvanzado>emptyList()
				: Collections.unmodifiableList(new ArrayList<CambioGitAvanzado>(valores));
	}

	private static List<CommitGitAvanzado> copiaCommits(List<CommitGitAvanzado> valores) {
		return valores == null ? Collections.<CommitGitAvanzado>emptyList()
				: Collections.unmodifiableList(new ArrayList<CommitGitAvanzado>(valores));
	}

	public String ramaActual() {
		return ramaActual;
	}

	public String remote() {
		return remote;
	}

	public List<String> ramas() {
		return ramas;
	}

	public List<CambioGitAvanzado> preparados() {
		return preparados;
	}

	public List<CambioGitAvanzado> noPreparados() {
		return noPreparados;
	}

	public List<CommitGitAvanzado> historial() {
		return historial;
	}
}
