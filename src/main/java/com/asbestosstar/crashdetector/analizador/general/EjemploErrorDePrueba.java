package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/** Verificacion minima de ejemplo para una causa directa del fallo. */
@ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaEjemplosDeVerificaciones" },
		nota = "Cubre el disparador y la criticalidad ERROR predeterminada")
public class EjemploErrorDePrueba implements Verificaciones {

	private static final String PATRON = "CRASHDETECTOR_EJEMPLO_ERROR";

	private boolean activado;
	private String mensaje = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { PATRON };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento != null && evento.linea != null) {
			verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null || !linea.contains(PATRON)) {
			return;
		}
		if (consola != null) {
			consola.agregarErrorALectador(numero_de_linea, this);
		}
		mensaje = "<b>" + MonitorDePID.idioma.ejemploErrorTitulo() + "</b>" + Verificaciones.nl_html
				+ MonitorDePID.idioma.ejemploErrorDetalle()
				+ Verificaciones.nl_html + MonitorDePID.idioma.error();
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new EjemploErrorDePrueba();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return -1000;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.ejemploErrorTitulo();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "ejemplo_error_prueba";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
