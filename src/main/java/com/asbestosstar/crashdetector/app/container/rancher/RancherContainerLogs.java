package com.asbestosstar.crashdetector.app.container.rancher;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.app.container.kubernetes.AbstractKubectlContainerLogs;

/**
 * Usa el contexto previamente autenticado de Rancher y ejecuta "rancher kubectl
 * logs ...".
 */
public final class RancherContainerLogs extends AbstractKubectlContainerLogs {

	@Override
	public String getId() {
		return "rancher";
	}

	@Override
	protected String getBinarioPredeterminado() {
		return "rancher";
	}

	@Override
	protected List<String> prefijo(String binario) {
		List<String> salida = new ArrayList<String>();
		salida.add(binario);
		salida.add("kubectl");
		return salida;
	}
}
