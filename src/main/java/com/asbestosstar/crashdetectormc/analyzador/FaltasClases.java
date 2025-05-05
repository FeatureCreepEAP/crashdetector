package com.asbestosstar.crashdetectormc.analyzador;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class FaltasClases implements Verificaciones {

	public boolean activado = false;

	public Set<String> clases = new HashSet<String>();
	public static Set<String> todos = new LinkedHashSet<String>();

	VerificacionDeStackTrace vdst;

	public FaltasClases(VerificacionDeStackTrace vdst) {
		this.vdst = vdst;
	}

	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub

		for (String clase : vdst.fatal_clases_no_existe) {
			if (!todos.contains(clase)) {
				clases.add(clase);
				todos.add(clase);
			}

		}

		for (String linea : contento_de_consola.split(nl)) {
			if (linea.contains("Caused by: java.lang.ClassNotFoundException")) {
				String clase = linea.split(":")[2];
				if (!todos.contains(clase)) {
					clases.add(clase);
					todos.add(clase);
				}
			} else if (linea.contains("Error loading class:") && !linea.contains("WARN")) {
				String clase = linea.split("Error loading class: ")[1].split(" ")[0];
				if (!todos.contains(clase)) {
					clases.add(clase);
					todos.add(clase);
				}
			}
		}

		if (!clases.isEmpty()) {
			activado = true;

			constructor.appendDupe(MonitorDePID.idioma.faltar_de_clases_fatales()).append(nl_html);

			for (String miss : clases) {
				constructor.append(miss).append(nl_html);
			}

		}

	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new FaltasClases(vdst);
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
