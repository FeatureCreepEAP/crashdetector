package com.asbestosstar.crashdetector.app.container.kubernetes;

/**
 * Obtiene logs mediante el cliente oficial kubectl.
 */
public final class KubernetesContainerLogs extends AbstractKubectlContainerLogs {

	@Override
	public String getId() {
		return "kubernetes";
	}

	@Override
	protected String getBinarioPredeterminado() {
		return "kubectl";
	}
}
