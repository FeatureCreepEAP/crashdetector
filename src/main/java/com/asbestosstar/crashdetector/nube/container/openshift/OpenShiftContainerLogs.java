package com.asbestosstar.crashdetector.nube.container.openshift;

import com.asbestosstar.crashdetector.nube.container.kubernetes.AbstractKubectlContainerLogs;

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
