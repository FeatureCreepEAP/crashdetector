package com.asbestosstar.crashdetector.app.container.solaris;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.app.container.AbstractContainerLogProvider;
import com.asbestosstar.crashdetector.app.container.ContainerBinaryResolver;
import com.asbestosstar.crashdetector.app.container.ContainerIntegrationException;
import com.asbestosstar.crashdetector.app.container.ContainerLogRequest;

/**
 * Lee un archivo de log dentro de una Oracle Solaris Zone mediante zlogin.
 *
 * Solaris Zones no ofrece un equivalente genérico a "docker logs"; por ello la
 * ruta del archivo es obligatoria.
 */
public final class SolarisZoneContainerLogs extends AbstractContainerLogProvider {

	@Override
	public String getId() {
		return "solaris";
	}

	@Override
	protected void validar(ContainerLogRequest solicitud) throws ContainerIntegrationException {
		rechazar(tieneTexto(solicitud.getNamespace()), getId(), "--namespace");
		rechazar(tieneTexto(solicitud.getContenedor()), getId(), "--container");
		rechazar(tieneTexto(solicitud.getContexto()), getId(), "--context");
		rechazar(tieneTexto(solicitud.getKubeconfig()), getId(), "--kubeconfig");
		rechazar(solicitud.isPrevious(), getId(), "--previous");
		rechazar(solicitud.isTimestamps(), getId(), "--timestamps");
		rechazar(solicitud.isPrefix(), getId(), "--prefix");
		rechazar(solicitud.isTodosLosContenedores(), getId(), "--all-containers");
		rechazar(solicitud.isDetails(), getId(), "--details");
		rechazar(tieneTexto(solicitud.getSince()), getId(), "--since");
		rechazar(tieneTexto(solicitud.getUntil()), getId(), "--until");

		if (!tieneTexto(solicitud.getRutaLogSolaris())) {
			throw new ContainerIntegrationException(
					"Solaris Zones requiere --path <ruta-absoluta-del-log-dentro-de-la-zona>.");
		}

		File ruta = new File(solicitud.getRutaLogSolaris());
		if (!ruta.isAbsolute()) {
			throw new ContainerIntegrationException("La ruta de Solaris debe ser absoluta: " + ruta);
		}
	}

	@Override
	protected List<String> construirComando(ContainerLogRequest solicitud) throws ContainerIntegrationException {
		String binario = ContainerBinaryResolver.resolver(getId(), "zlogin", solicitud.getBinario());
		List<String> comando = new ArrayList<String>();
		comando.add(binario);
		comando.add(solicitud.getObjetivo());

		if (tieneTexto(solicitud.getTail()) && !"all".equalsIgnoreCase(solicitud.getTail())) {
			comando.add("/usr/bin/tail");
			comando.add("-n");
			comando.add(solicitud.getTail());
		} else {
			comando.add("/usr/bin/cat");
		}

		comando.add(solicitud.getRutaLogSolaris());
		return comando;
	}
}
