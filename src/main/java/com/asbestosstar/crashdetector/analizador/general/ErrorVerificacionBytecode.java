package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores VerifyError causados normalmente por bytecode inválido,
 * mixins fallidos, transformers incompatibles o mods que modificaron una clase
 * de forma incorrecta.
 *
 * Ejemplo: java.lang.VerifyError: Bad type on operand stack
 */
public class ErrorVerificacionBytecode implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private String ubicacion = "";
	private String razon = "";
	private String claseSospechosa = "";

	private boolean esperandoUbicacion = false;
	private boolean esperandoRazon = false;

	private static final String VERIFY_ERROR = "java.lang.VerifyError";
	private static final String BAD_TYPE = "Bad type on operand stack";
	private static final String BAD_LOCAL_VARIABLE_TYPE = "Bad local variable type";
	private static final String INCONSISTENT_STACKMAP = "Inconsistent stackmap frames";
	private static final String EXPECTING_STACKMAP = "Expecting a stackmap frame";
	private static final String OPERAND_STACK_OVERFLOW = "Operand stack overflow";
	private static final String REGISTER_FINALIZER = "Register finalizer expects";

	private static final String LOCATION = "Location:";
	private static final String REASON = "Reason:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { VERIFY_ERROR };
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

		if (!activado && linea.contains(VERIFY_ERROR)) {

			activado = true;
			enlace = consola.agregarErrorALectador(num, this);
			return;
		}

		if (!activado)
			return;

		if (equalsSinEspaciosLaterales(linea, LOCATION)) {

			esperandoUbicacion = true;
			esperandoRazon = false;
			return;
		}

		if (esperandoUbicacion && ubicacion.length() == 0 && tieneTexto(linea)) {

			if (pareceUbicacionVerifyError(linea)) {

				ubicacion = sinEspaciosLaterales(linea);
				claseSospechosa = extraerClaseDesdeUbicacion(ubicacion);
				esperandoUbicacion = false;
				return;
			}

			if (!empiezaConSinEspacios(linea, "["))
				esperandoUbicacion = false;

			return;
		}

		if (equalsSinEspaciosLaterales(linea, REASON)) {

			esperandoRazon = true;
			esperandoUbicacion = false;
			return;
		}

		if (esperandoRazon && razon.length() == 0 && tieneTexto(linea)) {

			if (pareceRazonVerifyError(linea)) {

				razon = sinEspaciosLaterales(linea);
				esperandoRazon = false;
				return;
			}

			if (!empiezaConSinEspacios(linea, "["))
				esperandoRazon = false;

			return;
		}

		if (ubicacion.length() == 0 && empiezaConSinEspacios(linea, LOCATION)) {

			String posibleUbicacion = despuesDePrefijoSinEspacios(linea, LOCATION);

			if (pareceUbicacionVerifyError(posibleUbicacion)) {

				ubicacion = posibleUbicacion;
				claseSospechosa = extraerClaseDesdeUbicacion(ubicacion);
			}

			return;
		}

		if (razon.length() == 0 && empiezaConSinEspacios(linea, REASON)) {

			String posibleRazon = despuesDePrefijoSinEspacios(linea, REASON);

			if (pareceRazonVerifyError(posibleRazon))
				razon = posibleRazon;

			return;
		}

		if (razon.length() == 0 && pareceRazonVerifyError(linea)) {

			razon = sinEspaciosLaterales(linea);
			return;
		}

		if (claseSospechosa.length() == 0 && linea.contains("~[") && linea.contains(".jar")) {

			claseSospechosa = extraerClaseDesdeStack(linea);
		}
	}

	private boolean pareceUbicacionVerifyError(String linea) {

		if (linea == null)
			return false;

		String limpia = sinEspaciosLaterales(linea);

		if (limpia.startsWith("["))
			return false;

		return limpia.contains("/") && limpia.contains(".") && limpia.contains("@")
				&& (limpia.contains(":") || limpia.contains("("));
	}

	private boolean pareceRazonVerifyError(String linea) {

		if (linea == null)
			return false;

		String limpia = sinEspaciosLaterales(linea);

		if (limpia.startsWith("["))
			return false;

		return limpia.contains("Type ") || limpia.contains("Bad type") || limpia.contains(BAD_LOCAL_VARIABLE_TYPE)
				|| limpia.contains(INCONSISTENT_STACKMAP) || limpia.contains(EXPECTING_STACKMAP)
				|| limpia.contains("Operand stack") || limpia.contains("Current Frame")
				|| limpia.contains("is not assignable to");
	}

	private String extraerClaseDesdeUbicacion(String ubicacion) {

		if (ubicacion == null)
			return "";

		String limpia = sinEspaciosLaterales(ubicacion);

		int idxMetodo = limpia.indexOf(".");
		if (idxMetodo > 0)
			limpia = limpia.substring(0, idxMetodo);

		limpia = limpia.replace('/', '.');

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

		String limpia = sinEspaciosLaterales(linea);

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

	private boolean tieneTexto(String texto) {
		if (texto == null)
			return false;

		for (int i = 0; i < texto.length(); i++) {
			if (!Character.isWhitespace(texto.charAt(i)))
				return true;
		}

		return false;
	}

	private boolean equalsSinEspaciosLaterales(String texto, String esperado) {
		if (texto == null || esperado == null)
			return false;

		int inicio = primerNoEspacio(texto);
		if (inicio < 0)
			return esperado.length() == 0;

		int fin = ultimoNoEspacioMasUno(texto);

		if (fin - inicio != esperado.length())
			return false;

		return texto.regionMatches(inicio, esperado, 0, esperado.length());
	}

	private boolean empiezaConSinEspacios(String texto, String prefijo) {
		if (texto == null || prefijo == null)
			return false;

		int inicio = primerNoEspacio(texto);
		if (inicio < 0)
			return prefijo.length() == 0;

		if (texto.length() - inicio < prefijo.length())
			return false;

		return texto.regionMatches(inicio, prefijo, 0, prefijo.length());
	}

	private String despuesDePrefijoSinEspacios(String texto, String prefijo) {
		if (texto == null || prefijo == null)
			return "";

		int inicio = primerNoEspacio(texto);
		if (inicio < 0)
			return "";

		if (texto.length() - inicio < prefijo.length())
			return "";

		if (!texto.regionMatches(inicio, prefijo, 0, prefijo.length()))
			return "";

		return sinEspaciosLaterales(texto.substring(inicio + prefijo.length()));
	}

	private String sinEspaciosLaterales(String texto) {
		if (texto == null)
			return "";

		int inicio = primerNoEspacio(texto);
		if (inicio < 0)
			return "";

		int fin = ultimoNoEspacioMasUno(texto);

		return texto.substring(inicio, fin);
	}

	private int primerNoEspacio(String texto) {
		for (int i = 0; i < texto.length(); i++) {
			if (!Character.isWhitespace(texto.charAt(i)))
				return i;
		}

		return -1;
	}

	private int ultimoNoEspacioMasUno(String texto) {
		for (int i = texto.length() - 1; i >= 0; i--) {
			if (!Character.isWhitespace(texto.charAt(i)))
				return i + 1;
		}

		return 0;
	}

	@Override
	public VerificacionesLegacy nueva() {
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