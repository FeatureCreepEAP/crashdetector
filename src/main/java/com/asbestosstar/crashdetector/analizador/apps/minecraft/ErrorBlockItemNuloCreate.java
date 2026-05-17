package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores NullPointerException relacionados con BlockItem nulo en
 * addons de Create, típicamente causados por conflictos con Amendments,
 * Moonshine o mala inicialización de bloques.
 */
public class ErrorBlockItemNuloCreate implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	private static final String PREFIJO_ERROR = "java.lang.NullPointerException: BlockItem ";
	private static final String SUFIJO_ERROR = " has a NULL block!";

	private boolean analizarLineas = false;

	@Override
	public void verificar(Consola consola) {

		// Chequeo global barato:
		// si el log completo no contiene las dos señales principales,
		// no hace falta procesar línea por línea.
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains("NullPointerException: BlockItem") && contenido.contains("has a NULL block!")) {
			this.analizarLineas = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!analizarLineas || this.activado) {
			return;
		}

		String nombreBlockItem = extraerNombreBlockItem(linea);
		if (nombreBlockItem != null) {
			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorBlockItemNuloCreate(nombreBlockItem) + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo != null && extraerNombreBlockItem(trazo.trace) != null;
	}

	private String extraerNombreBlockItem(String texto) {
		if (texto == null || texto.isEmpty()) {
			return null;
		}

		int inicioPrefijo = texto.indexOf(PREFIJO_ERROR);
		if (inicioPrefijo < 0) {
			return null;
		}

		int inicioNombre = inicioPrefijo + PREFIJO_ERROR.length();
		int finNombre = texto.indexOf(SUFIJO_ERROR, inicioNombre);
		if (finNombre <= inicioNombre) {
			return null;
		}

		String nombre = texto.substring(inicioNombre, finNombre).trim();
		if (nombre.isEmpty() || nombre.indexOf(' ') >= 0) {
			return null;
		}

		return nombre;
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
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}