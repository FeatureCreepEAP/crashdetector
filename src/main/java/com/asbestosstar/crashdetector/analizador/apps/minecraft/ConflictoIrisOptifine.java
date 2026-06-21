package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflicto entre OptiFine e Iris/Oculus basado en errores de inyección
 * de mixins. Requiere que aparezca "mixins.iris.json" o "mixins.oculus.json" y
 * "OptiFine" en el log.
 */
public class ConflictoIrisOptifine implements Verificaciones {

	private boolean activado = false;
	private String enlaceHtml = "";
	private boolean encontroOptifine = false;
	public boolean analizarLineas = false;

	private static final String OPTIFINE_CLASE = "optifine.Utils";
	private static final String OPTIFINE_MINUSCULA = "optifine";
	private static final String OPTIFINE_MIXTA = "Optifine";
	private static final String OPTIFINE_CORRECTA = "OptiFine";

	private static final String MIXINS_IRIS = "mixins.iris";
	private static final String MIXINS_OCULUS = "mixins.oculus.json";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { OPTIFINE_MINUSCULA, OPTIFINE_MIXTA, OPTIFINE_CORRECTA, MIXINS_IRIS, MIXINS_OCULUS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (!encontroOptifine && Buscador.existeClaseEnAlgunMod(OPTIFINE_CLASE)) {
			encontroOptifine = true;
		}

		if (contieneIrisOculusJson(linea)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || !encontroOptifine || linea == null) {
			return;
		}

		String l = linea;

		// Verificar que la línea contenga:
		// - El archivo de mixin de Iris u Oculus
		// - El error de inyección
		// - La clase MixinLevelRenderer
		boolean tieneIrisOculusJson = contieneIrisOculusJson(l);

		if (tieneIrisOculusJson) {
			activar(consola, numero_de_linea);
		}
	}

	private boolean contieneOptifine(String linea) {
		return linea.contains(OPTIFINE_MINUSCULA) || linea.contains(OPTIFINE_MIXTA)
				|| linea.contains(OPTIFINE_CORRECTA);
	}

	private boolean contieneIrisOculusJson(String linea) {
		return linea.contains(MIXINS_IRIS) || linea.contains(MIXINS_OCULUS);
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		this.activado = true;
		this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoIrisOptifine();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return MonitorDePID.idioma.conflicto_iris_optifine_html() + (enlaceHtml.isEmpty() ? "" : " " + enlaceHtml);
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_conflicto_iris_optifine();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_optifine());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_usar_iris_sin_optifine());
		return builder.construir();
	}

	@Override
	public String id() {
		return "conflicto_iris_optifine";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}