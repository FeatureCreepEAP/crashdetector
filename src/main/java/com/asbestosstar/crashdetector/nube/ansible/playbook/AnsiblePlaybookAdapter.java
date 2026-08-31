package com.asbestosstar.crashdetector.nube.ansible.playbook;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.nube.ansible.AnsibleArguments;
import com.asbestosstar.crashdetector.nube.ansible.AnsibleBinaryResolver;
import com.asbestosstar.crashdetector.nube.ansible.AnsibleCommandExecutor;
import com.asbestosstar.crashdetector.nube.ansible.AnsibleCommandResult;
import com.asbestosstar.crashdetector.nube.ansible.AnsibleIntegrationException;
import com.asbestosstar.crashdetector.nube.ansible.AnsibleLogRequest;
import com.asbestosstar.crashdetector.nube.ansible.AnsibleWorkspace;

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
