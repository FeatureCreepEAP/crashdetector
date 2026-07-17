package com.asbestosstar.crashdetector.app.ansible;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Ejecuta un playbook externo solamente tras consentimiento explícito.
 */
public final class AnsibleExternalPlaybookExecutor {

	public AnsibleCommandResult execute(AnsiblePlaybookRequest request) throws AnsibleIntegrationException {
		if (!request.isExternalPlaybookAllowed()) {
			throw new AnsibleIntegrationException(
					"Los playbooks externos pueden modificar hosts. Repita con --allow-external-playbook.");
		}

		String binary = AnsibleBinaryResolver.resolverPlaybook(request.getOptions().getPlaybookBinary());
		if (!AnsibleBinaryResolver.estaDisponible(binary)) {
			throw new AnsibleIntegrationException("ansible-playbook no está disponible: " + binary);
		}

		List<String> command = new ArrayList<String>();
		command.add(binary);
		AnsibleArguments.agregarComunesPlaybook(command, request.getOptions(), true);
		if (request.isCheck()) {
			command.add("--check");
		}
		if (request.isSyntaxCheck()) {
			command.add("--syntax-check");
		}
		command.add(request.getPlaybook().getAbsolutePath());

		Map<String, String> environment = new LinkedHashMap<String, String>();
		environment.put("ANSIBLE_NOCOLOR", "1");
		environment.put("NO_COLOR", "1");

		AnsibleCommandResult result = AnsibleCommandExecutor.ejecutar(command, request.getProjectDirectory(),
				environment, request.getOptions().getTimeoutSeconds(), request.getOptions().getMaxBytes());

		StringBuilder log = new StringBuilder();
		log.append("CrashDetector Ansible playbook execution").append(System.lineSeparator());
		log.append("Playbook: ").append(request.getPlaybook().getAbsolutePath()).append(System.lineSeparator());
		log.append("Exit code: ").append(result.getExitCode()).append(System.lineSeparator());
		log.append("Command: ").append(result.getComandoVisible()).append(System.lineSeparator())
				.append(System.lineSeparator());
		log.append(result.salidaCombinada());
		if (log.charAt(log.length() - 1) != '\n') {
			log.append(System.lineSeparator());
		}

		List<String> temporaries = new ArrayList<String>();
		try {
			Path temporary = MonitorDePID.crearArchivoTemporalTextoCLI(log.toString());
			temporaries.add(temporary.toFile().getCanonicalPath());
			MonitorDePID.configurarEntradasCLI(Collections.<String>emptyList(), temporaries);
			MonitorDePID.forzarIdeMode = true;
		} catch (IOException e) {
			for (String temporary : temporaries) {
				try {
					Files.deleteIfExists(new java.io.File(temporary).toPath());
				} catch (IOException ignored) {
				}
			}
			throw new AnsibleIntegrationException("No se pudo preparar la salida del playbook para análisis.", e);
		}

		return result;
	}
}
