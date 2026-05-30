package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta el error causado por usar Rubidium (fork obsoleto de Sodium) con
 * Iris/Oculus en versiones recientes de Minecraft donde OptionInstance es
 * final.
 */
public class ErrorRubidiumObsoletoConIris implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		// No se usa; análisis por línea
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (this.activado) {
			return;
		}

		if (linea.contains("java.lang.IncompatibleClassChangeError") && linea.contains("ShadowDistanceOption")
				&& linea.contains("cannot inherit from final class net.minecraft.client.OptionInstance")) {

			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorRubidiumObsoletoConIris() + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo.trace.contains("ShadowDistanceOption")
				&& trazo.trace.contains("cannot inherit from final class net.minecraft.client.OptionInstance");
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorRubidiumObsoletoConIris();
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
		return 825.0f; // Alta: impide cargar Iris correctamente
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionRubidiumObsoletoConIris())
				.construir();
	}

	@Override
	public String id() {
		return "rubidium_obsoleto_con_iris";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorRubidiumObsoletoConIris();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}