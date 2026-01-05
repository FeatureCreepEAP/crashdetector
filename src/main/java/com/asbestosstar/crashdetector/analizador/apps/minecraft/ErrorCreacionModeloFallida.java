package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detecta errores al crear modelos de bloques/items: "Failed to create model
 * for <modid>:<nombre_modelo>".
 * 
 * Este error suele indicar que un mod tiene recursos corruptos, faltantes, o
 * incompatibles con la versión actual de Minecraft/Forge.
 */
public class ErrorCreacionModeloFallida implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private static final Pattern PATRON_MODELO_FALLIDO = Pattern
			.compile("java\\.lang\\.IllegalStateException: Failed to create model for ([^:\\s]+):([^\\s]+)");

	@Override
	public void verificar(Consola consola) {
		// No se usa; análisis por línea
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (this.activado) {
			return;
		}

		Matcher m = PATRON_MODELO_FALLIDO.matcher(linea);
		if (m.find()) {
			String modid = m.group(1);
			String nombreModelo = m.group(2);

			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorCreacionModeloFallida(modid, nombreModelo) + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return PATRON_MODELO_FALLIDO.matcher(trazo.trace).find();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCreacionModeloFallida();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 790.0f; // Media-alta: puede causar crashes o renders rotos
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionErrorCreacionModeloFallida())
				.construir();
	}

	@Override
	public String id() {
		return "creacion_modelo_fallida";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorCreacionModeloFallida();
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
	
	
}