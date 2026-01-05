package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta el error nativo "StubRoutines::SafeFetch32" relacionado con JDK
 * 17.0.9 en macOS. Este problema se resuelve actualizando a JDK 17.0.10 o
 * superior.
 */
public class ProblemaSafeFetch32JDK17 implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> coincidencias = new ArrayList<>();

	private static final Pattern PATRON_SAFE_FETCH = Pattern.compile("StubRoutines::SafeFetch32",
			Pattern.CASE_INSENSITIVE);

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		if (PATRON_SAFE_FETCH.matcher(contenido).find()) {
			coincidencias.add("StubRoutines::SafeFetch32");
			activado = true;
			mensaje = MonitorDePID.idioma.problema_safe_fetch32_jdk17();
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// No se necesita procesamiento por línea; se maneja en verificar(Consola)
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaSafeFetch32JDK17();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 850.0f; // Alta prioridad por ser un fallo nativo
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_problema_safe_fetch32_jdk17();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_actualizar_jdk_macos());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_usar_lanzador_con_jdk_actualizado());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_desactivar_spark_mod());
		return builder.construir();
	}

	@Override
	public String id() {
		return "problema_safe_fetch32_jdk17";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"+this.getClass().getSimpleName()+".java";
	}
	
	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
	
	
	
	
	
	
}