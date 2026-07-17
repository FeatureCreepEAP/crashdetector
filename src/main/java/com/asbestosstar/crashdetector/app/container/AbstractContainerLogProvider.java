package com.asbestosstar.crashdetector.app.container;

import java.util.List;

/**
 * Base común para adaptadores basados en un cliente oficial de línea de
 * comandos.
 */
public abstract class AbstractContainerLogProvider implements ContainerLogProvider {

	@Override
	public final ContainerLogResult obtenerLogs(ContainerLogRequest solicitud) throws ContainerIntegrationException {
		validar(solicitud);
		List<String> comando = construirComando(solicitud);
		return ContainerCommandExecutor.ejecutar(comando, solicitud.getTimeoutSegundos(), solicitud.getMaxBytes());
	}

	protected abstract void validar(ContainerLogRequest solicitud) throws ContainerIntegrationException;

	protected abstract List<String> construirComando(ContainerLogRequest solicitud)
			throws ContainerIntegrationException;

	protected static void rechazar(boolean condicion, String plataforma, String opcion)
			throws ContainerIntegrationException {
		if (condicion) {
			throw new ContainerIntegrationException(
					"La opción " + opcion + " no es compatible con la integración " + plataforma + ".");
		}
	}

	protected static boolean tieneTexto(String valor) {
		return valor != null && !valor.trim().isEmpty();
	}
}
