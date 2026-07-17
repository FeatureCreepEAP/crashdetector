package com.asbestosstar.crashdetector.app.ansible;

/**
 * Error controlado producido por la integración opcional con Ansible.
 */
public class AnsibleIntegrationException extends Exception {

	private static final long serialVersionUID = 1L;

	public AnsibleIntegrationException(String mensaje) {
		super(mensaje);
	}

	public AnsibleIntegrationException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
