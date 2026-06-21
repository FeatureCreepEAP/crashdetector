package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta un patrón frecuente asociado a errores de sombreadores con
 * Oculus/Iris.
 *
 * <p>
 * La detección se apoya en la presencia conjunta de estas dos cadenas:
 * <ul>
 * <li>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</li>
 * <li>java.lang.RuntimeException: Unknown variable:</li>
 * </ul>
 *
 * <p>
 * Cuando ambas aparecen en el log, normalmente indica un problema al evaluar
 * expresiones dentro de shaders, muy a menudo relacionado con Oculus o Iris, un
 * pack de shaders incompatible, o una combinación conflictiva entre shaders y
 * otros mods gráficos.
 */
public class OculusIrisUnknownShaderVariable implements Verificaciones {

	// Indica si esta verificación ya quedó activada
	private boolean activado = false;

	// Enlace a la línea más representativa del problema
	private String enlace = "";

	private static final String EXPRESSION_RESOLVER = "kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal";
	private static final String UNKNOWN_VARIABLE = "java.lang.RuntimeException: Unknown variable:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { EXPRESSION_RESOLVER, UNKNOWN_VARIABLE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Guardamos como enlace la línea más útil para el usuario
		if (linea.contains(UNKNOWN_VARIABLE)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Respaldo: si por alguna razón no se enlazó con la línea principal,
		// usar la otra línea relevante
		if (linea.contains(EXPRESSION_RESOLVER)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new OculusIrisUnknownShaderVariable();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 700;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeOculusIrisUnknownShaderVariable() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreOculusIrisUnknownShaderVariable();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "oculus_iris_unknown_shader_variable";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}