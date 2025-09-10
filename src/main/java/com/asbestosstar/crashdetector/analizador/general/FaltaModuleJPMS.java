package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

public class FaltaModuleJPMS implements Verificaciones {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final List<String> enlaces = new ArrayList<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("java.lang.module.FindException: Module ")
					&& linea.contains(" not found, required by ")) {

				try {
					String modNecesitado = linea.split("Module ")[1].split(" not found")[0].trim();
					String modRequeridor = linea.split("required by ")[1].trim();

					String mensaje = MonitorDePID.idioma.jpms_modules_faltas(modNecesitado, modRequeridor);

					// Solo agregar si es un error nuevo
					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(i, this);
						enlaces.add(enlace);
					}
				} catch (Exception e) {
					// Ignora errores de parseo para evitar fallos críticos
					consola.agregarErrorALectador(i, this); // Aún así registra la línea como problema
				}
			}
		}

		activado = !errores.isEmpty();
	}

	@Override
	public Verificaciones nueva() {
		return new FaltaModuleJPMS();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f; // Prioridad alta para errores de módulos JPMS [[7]]
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		Iterator<String> erroresIter = errores.iterator();
		Iterator<String> enlacesIter = enlaces.iterator();

		while (erroresIter.hasNext() && enlacesIter.hasNext()) {
			String error = erroresIter.next();
			String enlace = enlacesIter.next();
			html.append("<li>").append(error).append(" ").append(enlace).append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_falta_module_jmps();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionFaltasClases()).construir();
	}

}