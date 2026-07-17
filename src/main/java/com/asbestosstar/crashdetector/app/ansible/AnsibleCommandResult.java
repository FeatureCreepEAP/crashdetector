package com.asbestosstar.crashdetector.app.ansible;

/**
 * Resultado inmutable de ejecutar un cliente Ansible externo.
 */
public final class AnsibleCommandResult {

	private final String stdout;
	private final String stderr;
	private final int exitCode;
	private final String comandoVisible;

	public AnsibleCommandResult(String stdout, String stderr, int exitCode, String comandoVisible) {
		this.stdout = stdout == null ? "" : stdout;
		this.stderr = stderr == null ? "" : stderr;
		this.exitCode = exitCode;
		this.comandoVisible = comandoVisible == null ? "" : comandoVisible;
	}

	public String getStdout() {
		return stdout;
	}

	public String getStderr() {
		return stderr;
	}

	public int getExitCode() {
		return exitCode;
	}

	public String getComandoVisible() {
		return comandoVisible;
	}

	public String salidaCombinada() {
		StringBuilder salida = new StringBuilder();
		if (!stdout.isEmpty()) {
			salida.append(stdout);
		}
		if (!stderr.isEmpty()) {
			if (salida.length() > 0 && salida.charAt(salida.length() - 1) != '\n') {
				salida.append(System.lineSeparator());
			}
			salida.append(stderr);
		}
		return salida.toString();
	}
}
