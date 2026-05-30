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
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FaltaModuleJPMS implements Verificaciones {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final List<String> enlaces = new ArrayList<>();

	/**
	 * Bandera ligera para indicar si el log contiene, en general, el patrón de
	 * módulos JPMS faltantes. Esto permite que el verificador por línea se salte
	 * todo el trabajo si no hay ninguna coincidencia global.
	 */
	private boolean posibleFaltaModulo = false;

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		if (contenidoConsola == null || contenidoConsola.isEmpty()) {
			posibleFaltaModulo = false;
			return;
		}

		// Verificación global mínima: solo comprobamos si aparecen los fragmentos
		// clave del error JPMS. El análisis real se hace en el método por línea.
		posibleFaltaModulo = contenidoConsola.contains("java.lang.module.FindException: Module ")
				&& contenidoConsola.contains(" not found, required by ");
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si el log no parece contener el error, evitamos trabajo extra por cada línea.
		if (!posibleFaltaModulo || linea == null) {
			return;
		}

		if (linea.contains("java.lang.module.FindException: Module ") && linea.contains(" not found, required by ")) {

			try {
				String modNecesitado = linea.split("Module ")[1].split(" not found")[0].trim();
				String modRequeridor = linea.split("required by ")[1].trim();

				String mensaje = MonitorDePID.idioma.jpms_modules_faltas(modNecesitado,
						modRequeridor + ModulesDuplicadosJavaModulePlatform.procesarModulo(modRequeridor.trim()));

				// Solo agregar si es un error nuevo
				if (errores.add(mensaje)) {
					String enlace = consola.agregarErrorALectador(numero_de_linea, this);
					enlaces.add(enlace);
					activado = true;
				}
			} catch (Exception e) {
				// Ignora errores de parseo para evitar fallos críticos,
				// pero aún así registra la línea como problema.
				consola.agregarErrorALectador(numero_de_linea, this);
			}
		}
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

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "falta_module_jpms";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// Para ser conservadores, solo marcamos el trazo como perteneciente a este
		// verificador si:
		// - Ya se ha activado (es decir, se detectó al menos un error JPMS en el log),
		// y
		// - El texto del trazo contiene claramente el patrón de FindException de
		// módulos faltantes.
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;
		return t.contains("java.lang.module.FindException: Module ") && t.contains(" not found, required by ");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/general/" + this.getClass().getSimpleName()
				+ ".java";
	}

}
