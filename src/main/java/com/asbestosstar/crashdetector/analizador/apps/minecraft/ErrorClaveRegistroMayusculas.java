package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores de claves de registro inválidas con mayúsculas o caracteres
 * no permitidos.
 */
public class ErrorClaveRegistroMayusculas implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		// No se usa; el sistema llama a verificar(Consola, String, int)
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			return;
		}

		if (linea.contains("java.lang.IllegalStateException") && linea.contains("key must be lowercase:")) {

			// Extraer la clave problemática
			int idx = linea.indexOf("key must be lowercase:");
			String clave = idx != -1 ? linea.substring(idx + 22).trim() : "clave desconocida";

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
	public Verificaciones nueva() {
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