package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Verificación especializada para detectar errores de ModLauncher causados por rutas
 * que contienen caracteres no ASCII o caracteres especiales.
 * <p>
 * - Analiza la consola en busca del error específico "Bad escape" de ModLauncher
 * - Detecta cuando las rutas contienen caracteres no ASCII que rompen el sistema de servicios
 * - Incluye la solución directamente en el mensaje principal
 * - La salida es un bloque HTML con información detallada para la UI
 */
public class ErrorRutaModLauncher implements Verificaciones {

	/**
	 * Separador de líneas, definido en la interfaz base
	 */
	private static final String NL = Verificaciones.nl;

	/**
	 * Almacena los mensajes de error únicos detectados
	 */
	private final Map<String, String> errores = new HashMap<>();

	/**
	 * Indica si se encontró al menos un error de ruta en ModLauncher
	 */
	private boolean activado = false;

	/**
	 * Almacena el enlace HTML por mensaje base
	 */
	private final Map<String, String> enlacesPorLinea = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		// Limpiar resultados anteriores
		errores.clear();
		activado = false;

		// Buscar el error específico en la consola
		if (!consola.contenido_verificar.contains("java.lang.IllegalArgumentException: Bad escape") || 
			!consola.contenido_verificar.contains("sun.nio.fs.UnixUriUtils.fromUri")) {
			return;
		}

		// Analizar líneas para encontrar el error específico
		String[] lineas = consola.contenido_verificar.split(NL);
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("java.lang.IllegalArgumentException: Bad escape")) {
				String mensajeBase = MonitorDePID.idioma.error_modlauncher_path();
				
				// Registrar el enlace a esta línea
				String enlace = consola.agregarErrorALectador(i, this);
				enlacesPorLinea.putIfAbsent(mensajeBase, enlace);
				
				// Agregar el error al mapa
				errores.put(mensajeBase, "");
				activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorRutaModLauncher();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f; // Prioridad máxima: error crítico que impide el arranque
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder("<ul>");

		// Para cada tipo de error
		for (String mensajeBase : errores.keySet()) {
			// Recuperar el enlace
			String enlace = enlacesPorLinea.getOrDefault(mensajeBase, "");
			if (!enlace.isEmpty()) {
				sb.append("<li>").append(mensajeBase).append(" ").append(enlace).append("</li>");
			} else {
				sb.append("<li>").append(mensajeBase).append("</li>");
			}
		}

		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_modlauncher_path();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_modlauncher_path())
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "ruta_modlauncher";
	}
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}



}