package com.asbestosstar.crashdetectormc.analyzador;

import java.util.LinkedHashSet;
import java.util.Set;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class AdvertenciaFaltasClases implements Verificaciones {

	public boolean activado = false;

	public Set<String> clases = new LinkedHashSet<String>();

	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
		// TODO Auto-generated method stub
		for (String linea : contento_de_consola.split(nl)) {

			if (linea.contains("Error loading class:") && linea.contains("WARN")) {
				String clase = linea.split("Error loading class: ")[1].split(" ")[0];

				if (!FaltasClases.todos.contains(clase)) {
					clases.add(clase);
					FaltasClases.todos.add(clase);
				}
			}
		}

		if (!clases.isEmpty()) {
			activado = true;
			constructor.appendDupe(MonitorDePID.idioma.faltar_de_clases_advertencia()).append(nl_html);
			for (String clase : clases) {
				constructor.append(clase).append(nl_html);
			}

		}

	}

	@Override
	public AdvertenciaFaltasClases nueva() {
		// TODO Auto-generated method stub
		return new AdvertenciaFaltasClases();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
