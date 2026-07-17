package com.asbestosstar.crashdetector.app.ansible;

import java.util.Arrays;

/**
 * Diagnóstico de disponibilidad de los clientes opcionales.
 */
public final class AnsibleStatus {

	private AnsibleStatus() {
	}

	public static void mostrar(String runnerExplicit, String playbookExplicit) {
		mostrarUno("ansible-runner", true, runnerExplicit);
		mostrarUno("ansible-playbook", false, playbookExplicit);
	}

	private static void mostrarUno(String name, boolean runner, String explicit) {
		try {
			String binary = runner ? AnsibleBinaryResolver.resolverRunner(explicit)
					: AnsibleBinaryResolver.resolverPlaybook(explicit);
			AnsibleCommandResult result = AnsibleCommandExecutor.ejecutar(Arrays.asList(binary, "--version"), 15L,
					1024L * 1024L);
			if (result.getExitCode() != 0) {
				System.out.println(name + ": no disponible (código " + result.getExitCode() + ")");
				return;
			}
			String first = firstNonEmptyLine(result.salidaCombinada());
			System.out.println(name + ": disponible");
			System.out.println("  Binario: " + binary);
			if (!first.isEmpty()) {
				System.out.println("  Versión: " + first);
			}
		} catch (AnsibleIntegrationException e) {
			System.out.println(name + ": no disponible");
			System.out.println("  Motivo: " + e.getMessage());
		}
	}

	private static String firstNonEmptyLine(String text) {
		if (text == null) {
			return "";
		}
		String[] lines = text.split("\\r?\\n");
		for (String line : lines) {
			if (!line.trim().isEmpty()) {
				return line.trim();
			}
		}
		return "";
	}
}
