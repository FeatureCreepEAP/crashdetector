package com.asbestosstar.crashdetector.app.container.podman;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.app.container.AbstractContainerLogProvider;
import com.asbestosstar.crashdetector.app.container.ContainerBinaryResolver;
import com.asbestosstar.crashdetector.app.container.ContainerIntegrationException;
import com.asbestosstar.crashdetector.app.container.ContainerLogRequest;

/**
 * Obtiene logs mediante el cliente oficial podman.
 */
public final class PodmanContainerLogs extends AbstractContainerLogProvider {

	@Override
	public String getId() {
		return "podman";
	}

	@Override
	protected void validar(ContainerLogRequest solicitud) throws ContainerIntegrationException {
		rechazar(tieneTexto(solicitud.getNamespace()), getId(), "--namespace");
		rechazar(tieneTexto(solicitud.getContenedor()), getId(), "--container");
		rechazar(tieneTexto(solicitud.getContexto()), getId(), "--context");
		rechazar(tieneTexto(solicitud.getKubeconfig()), getId(), "--kubeconfig");
		rechazar(solicitud.isPrevious(), getId(), "--previous");
		rechazar(solicitud.isPrefix(), getId(), "--prefix");
		rechazar(solicitud.isTodosLosContenedores(), getId(), "--all-containers");
		rechazar(solicitud.isDetails(), getId(), "--details");
		rechazar(tieneTexto(solicitud.getRutaLogSolaris()), getId(), "--path");
	}

	@Override
	protected List<String> construirComando(ContainerLogRequest solicitud) throws ContainerIntegrationException {
		String binario = ContainerBinaryResolver.resolver(getId(), "podman", solicitud.getBinario());
		List<String> comando = new ArrayList<String>();
		comando.add(binario);
		comando.add("logs");

		if (tieneTexto(solicitud.getSince())) {
			comando.add("--since");
			comando.add(solicitud.getSince());
		}
		if (tieneTexto(solicitud.getUntil())) {
			comando.add("--until");
			comando.add(solicitud.getUntil());
		}
		if (tieneTexto(solicitud.getTail())) {
			comando.add("--tail");
			comando.add("all".equalsIgnoreCase(solicitud.getTail()) ? "-1" : solicitud.getTail());
		}
		if (solicitud.isTimestamps()) {
			comando.add("--timestamps");
		}

		comando.add(solicitud.getObjetivo());
		return comando;
	}
}
