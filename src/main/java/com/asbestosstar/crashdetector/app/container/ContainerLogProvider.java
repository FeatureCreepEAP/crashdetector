package com.asbestosstar.crashdetector.app.container;

/**
 * Adaptador opcional capaz de obtener un bloque finito de logs.
 */
public interface ContainerLogProvider {

	String getId();

	ContainerLogResult obtenerLogs(ContainerLogRequest solicitud) throws ContainerIntegrationException;
}
