package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class KubeJSResourcePack implements Verificaciones {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final Map<String, String> enlacesPorError = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("from pack KubeJS Resource Pack [data]") && linea.contains("Failed to parse ")) {

				try {
					String modNombre = linea.split("Failed to parse ")[1].split(":")[0];
					String mensaje = MonitorDePID.idioma.kubeJSResourcePack(modNombre);

					// Solo registrar si es un error nuevo
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorError.put(mensaje, enlace);
					}
					activado = true;
				} catch (Exception e) {
					// Ignora errores de parseo para evitar fallos críticos
					consola.agregarErrorALectador(i, this); // Registrar línea como problema
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new KubeJSResourcePack();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 300.0f; // Prioridad media-alta para errores de recursos críticos [[3]]
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String error : errores) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			html.append("<li>").append(error).append(" ").append(enlace).append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_faltar_de_kubejs_resourcepack();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
}