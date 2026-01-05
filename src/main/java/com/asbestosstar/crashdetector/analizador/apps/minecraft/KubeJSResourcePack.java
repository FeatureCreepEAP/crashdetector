package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class KubeJSResourcePack implements Verificaciones {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final Map<String, String> enlacesPorError = new HashMap<>();

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificar(Consola, String, int)}, llamada por el analizador línea a
	 * línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca líneas que contengan:
	 * <ul>
	 * <li>"from pack KubeJS Resource Pack [data]"</li>
	 * <li>"Failed to parse "</li>
	 * </ul>
	 * e intenta extraer el nombre "lógico" del recurso/mod que aparece justo
	 * después de "Failed to parse ".
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!linea.contains("from pack KubeJS Resource Pack [data]") || !linea.contains("Failed to parse ")) {
			return;
		}

		try {
			String modNombre = linea.split("Failed to parse ")[1].split(":")[0];
			String mensaje = MonitorDePID.idioma.kubeJSResourcePack(modNombre);

			// Solo registrar si es un error nuevo
			if (errores.add(mensaje)) {
				String enlace = consola.agregarErrorALectador(numero_de_linea, this);
				enlacesPorError.put(mensaje, enlace);
			}
			activado = true;
		} catch (Exception e) {
			// Ignora errores de parseo para evitar fallos críticos,
			// pero aún así registra la línea como problemática.
			consola.agregarErrorALectador(numero_de_linea, this);
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
		return MonitorDePID.idioma.nombre_de_faltar_de_kubejs_resourcepack();
	}
	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;//TODO
	}


	
	

	@Override
	public String id() {
		return "kubejs_resourcepack";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene las cadenas características del problema de KubeJS
	 * Resource Pack:</li>
	 * <ul>
	 * <li>"from pack KubeJS Resource Pack [data]"</li>
	 * <li>"Failed to parse "</li>
	 * </ul>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a marcar un
	 * trazo que no corresponda realmente a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;
		return t.contains("from pack KubeJS Resource Pack [data]") && t.contains("Failed to parse ");
	}
	
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"+this.getClass().getSimpleName()+".java";
	}
	
	@Override
	public boolean recomendadoParaCorperata() {
		return true;//Intalacion corrupta
	}
	
	
	
	
	
	
	

}
