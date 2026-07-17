package com.asbestosstar.crashdetector.app.ansible;

import java.util.Locale;

/**
 * Motor utilizado para ejecutar la recopilación de logs.
 */
public enum AnsibleEngine {
	AUTO, RUNNER, PLAYBOOK;

	public static AnsibleEngine parsear(String valor) throws AnsibleIntegrationException {
		if (valor == null || valor.trim().isEmpty()) {
			return AUTO;
		}

		String normalizado = valor.trim().toLowerCase(Locale.ROOT);
		if ("auto".equals(normalizado)) {
			return AUTO;
		}
		if ("runner".equals(normalizado) || "ansible-runner".equals(normalizado)) {
			return RUNNER;
		}
		if ("playbook".equals(normalizado) || "ansible-playbook".equals(normalizado)) {
			return PLAYBOOK;
		}

		throw new AnsibleIntegrationException("Motor Ansible desconocido: " + valor + ". Use auto, runner o playbook.");
	}
}
