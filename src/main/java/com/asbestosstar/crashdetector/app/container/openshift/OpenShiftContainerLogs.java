package com.asbestosstar.crashdetector.app.container.openshift;

import com.asbestosstar.crashdetector.app.container.kubernetes.AbstractKubectlContainerLogs;

/**
 * Obtiene logs de OpenShift mediante el cliente oficial oc.
 */
public final class OpenShiftContainerLogs extends AbstractKubectlContainerLogs {

	@Override
	public String getId() {
		return "openshift";
	}

	@Override
	protected String getBinarioPredeterminado() {
		return "oc";
	}
}
