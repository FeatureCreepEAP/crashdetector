package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.pruebas.ProbadoPor;

/** Verificacion minima de ejemplo para un aviso preventivo. */
@ProbadoPor(valor = { "com.asbestosstar.crashdetector.pruebas.PruebaEjemplosDeVerificaciones" },
		nota = "Cubre el disparador y la criticalidad ADVERTENCIA explicita")
public class EjemploAdvertenciaDePrueba implements Verificaciones {

	private static final String PATRON = "CRASHDETECTOR_EJEMPLO_ADVERTENCIA";

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
		mensaje = "<b>" + MonitorDePID.idioma.ejemploAdvertenciaTitulo() + "</b>"
				+ Verificaciones.nl_html + MonitorDePID.idioma.ejemploAdvertenciaDetalle()
				+ Verificaciones.nl_html + MonitorDePID.idioma.advertencia();
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new EjemploAdvertenciaDePrueba();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return -1001;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.ejemploAdvertenciaTitulo();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public String id() {
		return "ejemplo_advertencia_prueba";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}
