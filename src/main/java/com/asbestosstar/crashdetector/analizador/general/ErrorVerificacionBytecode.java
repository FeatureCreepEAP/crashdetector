package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores VerifyError causados normalmente por bytecode inválido,
 * mixins fallidos, transformers incompatibles o mods que modificaron una clase
 * de forma incorrecta.
 *
 * Ejemplo: java.lang.VerifyError: Bad type on operand stack
 */
public class ErrorVerificacionBytecode implements Verificaciones {

	private boolean posibleError = false;
	private boolean activado = false;
	private String enlace = "";

	private String ubicacion = "";
	private String razon = "";
	private String claseSospechosa = "";

	private boolean esperandoUbicacion = false;
	private boolean esperandoRazon = false;

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Detección global ligera
		if (log.contains("java.lang.VerifyError")
				&& (log.contains("Bad type on operand stack") || log.contains("Bad local variable type")
						|| log.contains("Inconsistent stackmap frames") || log.contains("Expecting a stackmap frame")
						|| log.contains("Operand stack overflow") || log.contains("Register finalizer expects"))) {

			posibleError = true;
		}
	}

	public boolean quiereAnalizarLineas() {
		if (!posibleError)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		if (!posibleError)
			return;

		String limpia = linea.trim();

		if (!activado && limpia.contains("java.lang.VerifyError")) {

			activado = true;
			enlace = consola.agregarErrorALectador(num, this);
			return;
		}

		if (!activado)
			return;

		if (limpia.equals("Location:")) {

			esperandoUbicacion = true;
			esperandoRazon = false;
			return;
		}

		if (esperandoUbicacion && ubicacion.length() == 0 && limpia.length() > 0) {

			if (pareceUbicacionVerifyError(limpia)) {

				ubicacion = limpia;
				claseSospechosa = extraerClaseDesdeUbicacion(ubicacion);
				esperandoUbicacion = false;
				return;
			}

			if (!limpia.startsWith("["))
				esperandoUbicacion = false;

			return;
		}

		if (limpia.equals("Reason:")) {

			esperandoRazon = true;
			esperandoUbicacion = false;
			return;
		}

		if (esperandoRazon && razon.length() == 0 && limpia.length() > 0) {

			if (pareceRazonVerifyError(limpia)) {

				razon = limpia;
				esperandoRazon = false;
				return;
			}

			if (!limpia.startsWith("["))
				esperandoRazon = false;

			return;
		}

		if (ubicacion.length() == 0 && limpia.startsWith("Location:")) {

			String posibleUbicacion = limpia.substring("Location:".length()).trim();

			if (pareceUbicacionVerifyError(posibleUbicacion)) {

				ubicacion = posibleUbicacion;
				claseSospechosa = extraerClaseDesdeUbicacion(ubicacion);
			}

			return;
		}

		if (razon.length() == 0 && limpia.startsWith("Reason:")) {

			String posibleRazon = limpia.substring("Reason:".length()).trim();

			if (pareceRazonVerifyError(posibleRazon))
				razon = posibleRazon;

			return;
		}

		if (razon.length() == 0 && pareceRazonVerifyError(limpia)) {

			razon = limpia;
			return;
		}

		if (claseSospechosa.length() == 0 && limpia.contains("~[") && limpia.contains(".jar")) {

			claseSospechosa = extraerClaseDesdeStack(limpia);
		}
	}

	private boolean pareceUbicacionVerifyError(String linea) {

		if (linea == null)
			return false;

		String limpia = linea.trim();

		if (limpia.startsWith("["))
			return false;

		return limpia.contains("/") && limpia.contains(".") && limpia.contains("@")
				&& (limpia.contains(":") || limpia.contains("("));
	}

	private boolean pareceRazonVerifyError(String linea) {

		if (linea == null)
			return false;

		String limpia = linea.trim();

		if (limpia.startsWith("["))
			return false;

		return limpia.contains("Type ") || limpia.contains("Bad type") || limpia.contains("Bad local variable type")
				|| limpia.contains("Inconsistent stackmap frames") || limpia.contains("Expecting a stackmap frame")
				|| limpia.contains("Operand stack") || limpia.contains("Current Frame")
				|| limpia.contains("is not assignable to");
	}

	private String extraerClaseDesdeUbicacion(String ubicacion) {

		if (ubicacion == null)
			return "";

		String limpia = ubicacion.trim();

		int idxMetodo = limpia.indexOf(".");
		if (idxMetodo > 0)
			limpia = limpia.substring(0, idxMetodo);

		limpia = limpia.replace('/', '.').trim();

		return obtenerNombreBasicoClase(limpia);
	}

	private String obtenerNombreBasicoClase(String claseCompleta) {

		if (claseCompleta == null)
			return "";

		int idx = claseCompleta.lastIndexOf('.');

		if (idx >= 0 && idx + 1 < claseCompleta.length())
			return claseCompleta.substring(idx + 1);

		return claseCompleta;
	}

	private String extraerClaseDesdeStack(String linea) {

		if (linea == null)
			return "";

		String limpia = linea.trim();

		if (limpia.startsWith("at "))
			limpia = limpia.substring(3);

		int idxParentesis = limpia.indexOf("(");
		if (idxParentesis > 0)
			limpia = limpia.substring(0, idxParentesis);

		int idxMetodo = limpia.lastIndexOf(".");
		if (idxMetodo > 0)
			limpia = limpia.substring(0, idxMetodo);

		return obtenerNombreBasicoClase(limpia);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorVerificacionBytecode();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1750;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorVerificacionBytecode(ubicacion, razon, claseSospechosa) + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorVerificacionBytecode();
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
		return "error_verificacion_bytecode";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}