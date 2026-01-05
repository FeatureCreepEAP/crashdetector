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
 * Detecta errores NullPointerException relacionados con BlockItem nulo en
 * addons de Create, típicamente causados por conflictos con Amendments,
 * Moonshine o mala inicialización de bloques.
 */
public class ErrorBlockItemNuloCreate implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private static final Pattern PATRON_BLOCKITEM_NULO = Pattern
			.compile("java\\.lang\\.NullPointerException: BlockItem ([^ ]+) has a NULL block!");

	@Override
	public void verificar(Consola consola) {
		// No se usa; análisis por línea
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (this.activado) {
			return;
		}

		Matcher m = PATRON_BLOCKITEM_NULO.matcher(linea);
		if (m.find()) {
			String nombreBlockItem = m.group(1);

			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorBlockItemNuloCreate(nombreBlockItem) + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return PATRON_BLOCKITEM_NULO.matcher(trazo.trace).find();
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorBlockItemNuloCreate();
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
		return 1200.0f; // Alta: impide la carga correcta de bloques
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionErrorBlockItemNuloCreate())
				.construir();
	}

	@Override
	public String id() {
		return "blockitem_nulo_create";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorBlockItemNuloCreate();
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