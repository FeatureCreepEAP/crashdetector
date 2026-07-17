package com.asbestosstar.crashdetector.app.ansible.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.asbestosstar.crashdetector.app.ansible.AnsibleArguments;
import com.asbestosstar.crashdetector.app.ansible.AnsibleBinaryResolver;
import com.asbestosstar.crashdetector.app.ansible.AnsibleCommandExecutor;
import com.asbestosstar.crashdetector.app.ansible.AnsibleCommandResult;
import com.asbestosstar.crashdetector.app.ansible.AnsibleIntegrationException;
import com.asbestosstar.crashdetector.app.ansible.AnsibleLogRequest;
import com.asbestosstar.crashdetector.app.ansible.AnsibleWorkspace;

/**
 * Ejecuta el playbook integrado mediante ansible-runner.
 */
public final class AnsibleRunnerAdapter {

	public AnsibleCommandResult ejecutar(AnsibleLogRequest request, AnsibleWorkspace workspace)
			throws AnsibleIntegrationException {
		String runner = AnsibleBinaryResolver.resolverRunner(request.getOptions().getRunnerBinary());

		try {
			AnsibleArguments.escribirCmdlineRunner(workspace.getEnv().resolve("cmdline"), request.getOptions());
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudo preparar env/cmdline para Ansible Runner.", e);
		}

		List<String> comando = new ArrayList<String>();
		comando.add(runner);
		comando.add("run");
		comando.add(workspace.getRoot().toAbsolutePath().toString());
		comando.add("--playbook");
		comando.add(workspace.getLogPlaybook().getFileName().toString());
		comando.add("--inventory");
		comando.add(request.getOptions().getInventory().getAbsolutePath());
		comando.add("-i");
		comando.add("crashdetector-" + UUID.randomUUID().toString());
		comando.add("--rotate-artifacts");
		comando.add("1");

		return AnsibleCommandExecutor.ejecutar(comando, workspace.getProject().toFile(), workspace.entorno(request),
				request.getOptions().getTimeoutSeconds(), request.getOptions().getMaxBytes());
	}
}
