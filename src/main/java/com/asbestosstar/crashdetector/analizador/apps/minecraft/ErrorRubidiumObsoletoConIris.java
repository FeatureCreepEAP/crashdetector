package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error causado por usar Rubidium (fork obsoleto de Sodium) con
 * Iris/Oculus en versiones recientes de Minecraft donde OptionInstance es
 * final.
 */
public class ErrorRubidiumObsoletoConIris implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	public boolean posible = false;

	private static final String INCOMPATIBLE_CLASS_CHANGE_ERROR = "java.lang.IncompatibleClassChangeError";
	private static final String SHADOW_DISTANCE_OPTION = "ShadowDistanceOption";
	private static final String FINAL_OPTION_INSTANCE = "cannot inherit from final class net.minecraft.client.OptionInstance";

	@Override
	public String[] patronesRapidos() {
		return new String[] { INCOMPATIBLE_CLASS_CHANGE_ERROR, SHADOW_DISTANCE_OPTION, FINAL_OPTION_INSTANCE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneErrorRubidium(evento.linea)) {
			posible = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(INCOMPATIBLE_CLASS_CHANGE_ERROR) && contenido.contains(SHADOW_DISTANCE_OPTION)
				&& contenido.contains(FINAL_OPTION_INSTANCE)) {
			posible = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posible && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posible || this.activado || linea == null) {
			return;
		}

		if (lineaContieneErrorRubidium(linea)) {
			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorRubidiumObsoletoConIris() + enlaceHtml;
			this.activado = true;
		}
	}

	private boolean lineaContieneErrorRubidium(String linea) {
		return linea.contains(INCOMPATIBLE_CLASS_CHANGE_ERROR) && linea.contains(SHADOW_DISTANCE_OPTION)
				&& linea.contains(FINAL_OPTION_INSTANCE);
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo != null && trazo.trace != null && trazo.trace.contains(SHADOW_DISTANCE_OPTION)
				&& trazo.trace.contains(FINAL_OPTION_INSTANCE);
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