package com.asbestosstar.crashdetector.app.container;

/**
 * Error controlado al localizar un cliente de contenedores, ejecutar el comando
 * o validar sus argumentos.
 */
public class ContainerIntegrationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContainerIntegrationException(String message) {
		super(message);
	}

	public ContainerIntegrationException(String message, Throwable cause) {
		super(message, cause);
	}
}
