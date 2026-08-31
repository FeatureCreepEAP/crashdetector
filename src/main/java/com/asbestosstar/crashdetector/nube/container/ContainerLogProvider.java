package com.asbestosstar.crashdetector.nube.container;

/**
 * Adaptador opcional capaz de obtener un bloque finito de logs.
 */
public interface ContainerLogProvider {

	String getId();

	ContainerLogResult obtenerLogs(ContainerLogRequest solicitud) throws ContainerIntegrationException;
}
