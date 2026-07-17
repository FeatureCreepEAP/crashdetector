package com.asbestosstar.crashdetector.app.container.docker;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.app.container.AbstractContainerLogProvider;
import com.asbestosstar.crashdetector.app.container.ContainerBinaryResolver;
import com.asbestosstar.crashdetector.app.container.ContainerIntegrationException;
import com.asbestosstar.crashdetector.app.container.ContainerLogRequest;

/**
 * Obtiene la salida registrada por Docker mediante "docker logs".
 */
public final class DockerContainerLogs extends AbstractContainerLogProvider {

	@Override
	public String getId() {
		return "docker";
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
		rechazar(tieneTexto(solicitud.getRutaLogSolaris()), getId(), "--path");
	}

	@Override
	protected List<String> construirComando(ContainerLogRequest solicitud) throws ContainerIntegrationException {
		String binario = ContainerBinaryResolver.resolver(getId(), "docker", solicitud.getBinario());
		List<String> comando = new ArrayList<String>();
		comando.add(binario);
		comando.add("logs");

		if (solicitud.isDetails()) {
			comando.add("--details");
		}
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
			comando.add(solicitud.getTail());
		}
		if (solicitud.isTimestamps()) {
			comando.add("--timestamps");
		}

		comando.add(solicitud.getObjetivo());
		return comando;
	}
}
