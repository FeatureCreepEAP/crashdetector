package com.asbestosstar.crashdetector.app.ansible.playbook;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.app.ansible.AnsibleArguments;
import com.asbestosstar.crashdetector.app.ansible.AnsibleBinaryResolver;
import com.asbestosstar.crashdetector.app.ansible.AnsibleCommandExecutor;
import com.asbestosstar.crashdetector.app.ansible.AnsibleCommandResult;
import com.asbestosstar.crashdetector.app.ansible.AnsibleIntegrationException;
import com.asbestosstar.crashdetector.app.ansible.AnsibleLogRequest;
import com.asbestosstar.crashdetector.app.ansible.AnsibleWorkspace;

/**
 * Fallback que ejecuta directamente ansible-playbook.
 */
public final class AnsiblePlaybookAdapter {

	public AnsibleCommandResult ejecutarLogs(AnsibleLogRequest request, AnsibleWorkspace workspace)
			throws AnsibleIntegrationException {
		String playbook = AnsibleBinaryResolver.resolverPlaybook(request.getOptions().getPlaybookBinary());
		List<String> comando = new ArrayList<String>();
		comando.add(playbook);
		AnsibleArguments.agregarComunesPlaybook(comando, request.getOptions(), true);
		comando.add(workspace.getLogPlaybook().toAbsolutePath().toString());

		return AnsibleCommandExecutor.ejecutar(comando, workspace.getProject().toFile(), workspace.entorno(request),
				request.getOptions().getTimeoutSeconds(), request.getOptions().getMaxBytes());
	}
}
