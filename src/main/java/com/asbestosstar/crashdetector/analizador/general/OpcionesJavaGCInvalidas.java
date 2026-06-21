package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta la opción de GC inválida "Conflicting collector combinations in
 * option list" en Java. Esto genera errores de arranque en la JVM.
 */
public class OpcionesJavaGCInvalidas implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_GC_INVALIDO = "Conflicting collector combinations in option list";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_GC_INVALIDO };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea. Detecta la línea exacta y agrega enlace al lector.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(TEXTO_GC_INVALIDO)) {
			if (consola != null) {
				this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			}

			this.mensaje = MonitorDePID.idioma.errorOpcionesGCJava() + " " + enlace;
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new OpcionesJavaGCInvalidas();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_opciones_java_gc_invalidas();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.opcionesGCInvalidas()).construir();
	}

	@Override
	public String id() {
		return "opciones_java_gc_invalidas";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_GC_INVALIDO };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}