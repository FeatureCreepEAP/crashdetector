package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de claves de registro inválidas con mayúsculas o caracteres
 * no permitidos.
 */
public class ErrorClaveRegistroMayusculas implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	private static final String TEXTO_ILLEGAL_STATE = "java.lang.IllegalStateException";
	private static final String TEXTO_KEY_LOWERCASE = "key must be lowercase:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ILLEGAL_STATE, TEXTO_KEY_LOWERCASE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(TEXTO_ILLEGAL_STATE) && linea.contains(TEXTO_KEY_LOWERCASE)) {

			// Extraer la clave problemática
			int idx = linea.indexOf(TEXTO_KEY_LOWERCASE);
			String clave = idx != -1 ? linea.substring(idx + TEXTO_KEY_LOWERCASE.length()).trim() : "clave desconocida";

			// Limitar longitud para seguridad
			if (clave.length() > 256)
				clave = clave.substring(0, 256) + "...";

			this.activado = true;
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.error_clave_registro_mayusculas_html(clave)
					+ (enlace.isEmpty() ? "" : " " + enlace);
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ErrorClaveRegistroMayusculas();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 920.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_clave_registro_mayusculas();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_buscar_clave_en_archivos());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_mod_reciente());
		return builder.construir();
	}

	@Override
	public String id() {
		return "error_clave_registro_mayusculas";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}