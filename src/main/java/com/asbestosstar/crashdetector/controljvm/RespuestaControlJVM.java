package com.asbestosstar.crashdetector.controljvm;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Respuesta del canal local entre la JVM monitora y la JVM del juego.
 */
public final class RespuestaControlJVM {

	public static final String CODIGO_PONG = "PONG";
	public static final String CODIGO_GC_ACEPTADO = "GC_ACEPTADO";
	public static final String CODIGO_HEAP_DUMP_CREADO = "HEAP_DUMP_CREADO";
	public static final String CODIGO_CRASH_ACEPTADO = "CRASH_ACEPTADO";
	public static final String CODIGO_CLASE_TRANSFERIDA = "CLASE_TRANSFERIDA";
	public static final String CODIGO_CLASE_NO_ENCONTRADA = "CLASE_NO_ENCONTRADA";
	public static final String CODIGO_INSTRUMENTACION_NO_DISPONIBLE = "INSTRUMENTACION_NO_DISPONIBLE";
	public static final String CODIGO_RETRANSFORMACION_NO_DISPONIBLE = "RETRANSFORMACION_NO_DISPONIBLE";
	public static final String CODIGO_CLASE_NO_MODIFICABLE = "CLASE_NO_MODIFICABLE";
	public static final String CODIGO_DATOS_INVALIDOS = "DATOS_INVALIDOS";
	public static final String CODIGO_NO_DISPONIBLE = "NO_DISPONIBLE";
	public static final String CODIGO_NO_AUTORIZADO = "NO_AUTORIZADO";
	public static final String CODIGO_ERROR = "ERROR";

	private final boolean correcta;
	private final String codigo;
	private final String detalle;
	private final byte[] datos;

	public RespuestaControlJVM(boolean correcta, String codigo, String detalle) {
		this(correcta, codigo, detalle, null);
	}

	public RespuestaControlJVM(boolean correcta, String codigo, String detalle, byte[] datos) {
		this.correcta = correcta;
		this.codigo = codigo == null ? CODIGO_ERROR : codigo;
		this.detalle = detalle == null ? "" : detalle;
		this.datos = datos == null ? null : datos.clone();
	}

	public boolean esCorrecta() {
		return correcta;
	}

	public String codigo() {
		return codigo;
	}

	public String detalle() {
		return detalle;
	}

	public boolean tieneDatos() {
		return datos != null && datos.length > 0;
	}

	public byte[] datos() {
		return datos == null ? null : datos.clone();
	}

	public int longitudDatos() {
		return datos == null ? 0 : datos.length;
	}

	/**
	 * Convierte un código interno estable en un texto localizado para la GUI.
	 */
	public String mensajeUsuario() {
		if (CODIGO_PONG.equals(codigo)) {
			return MonitorDePID.idioma.controlJVMDisponible();
		}
		if (CODIGO_GC_ACEPTADO.equals(codigo)) {
			return MonitorDePID.idioma.controlJVMGcAceptado();
		}
		if (CODIGO_HEAP_DUMP_CREADO.equals(codigo)) {
			return MonitorDePID.idioma.controlJVMHeapDumpCreado(detalle);
		}
		if (CODIGO_CRASH_ACEPTADO.equals(codigo)) {
			return MonitorDePID.idioma.controlJVMCrashAceptado();
		}
		if (CODIGO_CLASE_TRANSFERIDA.equals(codigo)) {
			return MonitorDePID.idioma.transferidorClasesExito(detalle, longitudDatos());
		}
		if (CODIGO_CLASE_NO_ENCONTRADA.equals(codigo) || CODIGO_INSTRUMENTACION_NO_DISPONIBLE.equals(codigo)
				|| CODIGO_RETRANSFORMACION_NO_DISPONIBLE.equals(codigo) || CODIGO_CLASE_NO_MODIFICABLE.equals(codigo)
				|| CODIGO_DATOS_INVALIDOS.equals(codigo)) {
			return MonitorDePID.idioma.transferidorClasesError(codigo + (detalle.isEmpty() ? "" : ": " + detalle));
		}
		if (CODIGO_NO_DISPONIBLE.equals(codigo)) {
			return MonitorDePID.idioma.controlJVMNoDisponible();
		}
		if (CODIGO_NO_AUTORIZADO.equals(codigo)) {
			return MonitorDePID.idioma.controlJVMNoAutorizado();
		}
		return MonitorDePID.idioma.controlJVMError(detalle);
	}
}
