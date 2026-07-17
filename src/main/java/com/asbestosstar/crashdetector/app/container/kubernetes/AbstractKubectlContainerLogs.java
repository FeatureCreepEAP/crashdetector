package com.asbestosstar.crashdetector.app.container.kubernetes;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.app.container.AbstractContainerLogProvider;
import com.asbestosstar.crashdetector.app.container.ContainerBinaryResolver;
import com.asbestosstar.crashdetector.app.container.ContainerIntegrationException;
import com.asbestosstar.crashdetector.app.container.ContainerLogRequest;

/**
 * Construcción común para kubectl, oc y el subcomando kubectl de Rancher.
 */
public abstract class AbstractKubectlContainerLogs extends AbstractContainerLogProvider {

	protected abstract String getBinarioPredeterminado();

	protected List<String> prefijo(String binario) {
		List<String> salida = new ArrayList<String>();
		salida.add(binario);
		return salida;
	}

	@Override
	protected void validar(ContainerLogRequest solicitud) throws ContainerIntegrationException {
		rechazar(tieneTexto(solicitud.getUntil()), getId(), "--until");
		rechazar(solicitud.isDetails(), getId(), "--details");
		rechazar(tieneTexto(solicitud.getRutaLogSolaris()), getId(), "--path");
		rechazar(solicitud.isTodosLosContenedores() && tieneTexto(solicitud.getContenedor()), getId(),
				"--all-containers junto con --container");
	}

	@Override
	protected List<String> construirComando(ContainerLogRequest solicitud) throws ContainerIntegrationException {
		String binario = ContainerBinaryResolver.resolver(getId(), getBinarioPredeterminado(), solicitud.getBinario());

		List<String> comando = prefijo(binario);

		if (tieneTexto(solicitud.getContexto())) {
			comando.add("--context");
			comando.add(solicitud.getContexto());
		}
		if (tieneTexto(solicitud.getKubeconfig())) {
			comando.add("--kubeconfig");
			comando.add(solicitud.getKubeconfig());
		}

		comando.add("logs");
		comando.add(solicitud.getObjetivo());

		if (tieneTexto(solicitud.getNamespace())) {
			comando.add("--namespace");
			comando.add(solicitud.getNamespace());
		}
		if (tieneTexto(solicitud.getContenedor())) {
			comando.add("--container");
			comando.add(solicitud.getContenedor());
		}
		if (solicitud.isTodosLosContenedores()) {
			comando.add("--all-containers=true");
		}
		if (solicitud.isPrevious()) {
			comando.add("--previous=true");
		}
		if (tieneTexto(solicitud.getTail())) {
			comando.add("--tail=" + convertirTailKubernetes(solicitud.getTail()));
		}
		if (tieneTexto(solicitud.getSince())) {
			comando.add("--since=" + solicitud.getSince());
		}
		if (solicitud.isTimestamps()) {
			comando.add("--timestamps=true");
		}
		if (solicitud.isPrefix()) {
			comando.add("--prefix=true");
		}

		return comando;
	}

	private static String convertirTailKubernetes(String tail) {
		return "all".equalsIgnoreCase(tail) ? "-1" : tail;
	}
}
