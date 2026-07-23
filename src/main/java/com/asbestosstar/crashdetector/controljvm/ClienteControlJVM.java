package com.asbestosstar.crashdetector.controljvm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

import com.asbestosstar.crashdetector.Statics;

/**
 * Cliente Java 8 del canal local de control de la JVM observada.
 *
 * El transporte usa TCP de loopback y un token aleatorio guardado en un archivo
 * de control de la instancia. No depende de comandos del sistema operativo.
 */
public final class ClienteControlJVM {

	private static final String PROTOCOLO = "CD-JVM-CONTROL-1";
	private static final int TIEMPO_CONEXION_MS = 5_000;
	private static final int TIEMPO_LECTURA_MS = 120_000;
	private static final int MAX_BYTES_CLASE = 64 * 1024 * 1024;

	private final long pid;

	public ClienteControlJVM(long pid) {
		this.pid = pid;
	}

	public RespuestaControlJVM comprobar() {
		return enviar("PING", "", false);
	}

	public RespuestaControlJVM solicitarGc() {
		return enviar("GC", "", false);
	}

	public RespuestaControlJVM crearHeapDump(File destino, boolean soloVivos) {
		if (destino == null) {
			return new RespuestaControlJVM(false, RespuestaControlJVM.CODIGO_ERROR, "");
		}
		return enviar("HEAP_DUMP", destino.getAbsolutePath(), soloVivos);
	}

	public RespuestaControlJVM provocarCrashHsErr() {
		return enviar("CRASH_HS_ERR", "", false);
	}

	/**
	 * Solicita los bytes actuales de una clase cargada en la JVM observada.
	 *
	 * El nombre puede usar puntos, barras, el sufijo .class o un descriptor JVM.
	 */
	public RespuestaControlJVM solicitarBytesClase(String nombreClase) {
		if (nombreClase == null || nombreClase.trim().isEmpty()) {
			return new RespuestaControlJVM(false, RespuestaControlJVM.CODIGO_ERROR, "NOMBRE_CLASE_VACIO");
		}
		return enviarBytes("CLASS_BYTES", nombreClase.trim());
	}

	private RespuestaControlJVM enviar(String comando, String argumento, boolean bandera) {
		ConfiguracionCanal configuracion;

		try {
			configuracion = leerConfiguracion();
		} catch (Exception e) {
			return new RespuestaControlJVM(false, RespuestaControlJVM.CODIGO_NO_DISPONIBLE, mensajeSeguro(e));
		}

		Socket socket = new Socket();

		try {
			socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), configuracion.puerto),
					TIEMPO_CONEXION_MS);
			socket.setSoTimeout(TIEMPO_LECTURA_MS);

			try (DataOutputStream salida = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
					DataInputStream entrada = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {

				salida.writeUTF(PROTOCOLO);
				salida.writeUTF(configuracion.token);
				salida.writeUTF(comando);
				salida.writeUTF(argumento == null ? "" : argumento);
				salida.writeBoolean(bandera);
				salida.flush();

				boolean correcta = entrada.readBoolean();
				String codigo = entrada.readUTF();
				String detalle = entrada.readUTF();

				return new RespuestaControlJVM(correcta, codigo, detalle);
			}
		} catch (Exception e) {
			return new RespuestaControlJVM(false, RespuestaControlJVM.CODIGO_NO_DISPONIBLE, mensajeSeguro(e));
		} finally {
			try {
				socket.close();
			} catch (Exception ignorado) {
			}
		}
	}

	private RespuestaControlJVM enviarBytes(String comando, String argumento) {
		ConfiguracionCanal configuracion;

		try {
			configuracion = leerConfiguracion();
		} catch (Exception e) {
			return new RespuestaControlJVM(false, RespuestaControlJVM.CODIGO_NO_DISPONIBLE, mensajeSeguro(e));
		}

		Socket socket = new Socket();

		try {
			socket.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), configuracion.puerto),
					TIEMPO_CONEXION_MS);
			socket.setSoTimeout(TIEMPO_LECTURA_MS);

			try (DataOutputStream salida = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
					DataInputStream entrada = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {

				salida.writeUTF(PROTOCOLO);
				salida.writeUTF(configuracion.token);
				salida.writeUTF(comando);
				salida.writeUTF(argumento == null ? "" : argumento);
				salida.writeBoolean(false);
				salida.flush();

				boolean correcta = entrada.readBoolean();
				String codigo = entrada.readUTF();
				String detalle = entrada.readUTF();

				if (!correcta) {
					return new RespuestaControlJVM(false, codigo, detalle);
				}

				int longitud = entrada.readInt();
				if (longitud <= 0 || longitud > MAX_BYTES_CLASE) {
					return new RespuestaControlJVM(false, RespuestaControlJVM.CODIGO_DATOS_INVALIDOS,
							"LONGITUD=" + longitud);
				}

				byte[] datos = new byte[longitud];
				entrada.readFully(datos);
				return new RespuestaControlJVM(true, codigo, detalle, datos);
			}
		} catch (Exception e) {
			return new RespuestaControlJVM(false, RespuestaControlJVM.CODIGO_NO_DISPONIBLE, mensajeSeguro(e));
		} finally {
			try {
				socket.close();
			} catch (Exception ignorado) {
			}
		}
	}

	private ConfiguracionCanal leerConfiguracion() throws Exception {
		if (pid <= 0L) {
			throw new IllegalStateException("PID no válido");
		}

		File archivo = archivoControl(pid);
		if (!archivo.isFile() || !archivo.canRead()) {
			throw new IllegalStateException("No existe el archivo de control");
		}

		Properties propiedades = new Properties();
		try (FileInputStream entrada = new FileInputStream(archivo)) {
			propiedades.load(entrada);
		}

		int puerto = Integer.parseInt(propiedades.getProperty("puerto", "0"));
		String token = propiedades.getProperty("token", "").trim();

		if (puerto <= 0 || puerto > 65535 || token.isEmpty()) {
			throw new IllegalStateException("Archivo de control no válido");
		}

		return new ConfiguracionCanal(puerto, token);
	}

	public static File archivoControl(long pid) {
		return Statics.carpeta.resolve("control-jvm-" + pid + ".properties").toFile();
	}

	private static String mensajeSeguro(Throwable t) {
		if (t == null || t.getMessage() == null) {
			return "";
		}
		String mensaje = t.getMessage().replace('\r', ' ').replace('\n', ' ').trim();
		return mensaje.length() > 500 ? mensaje.substring(0, 500) : mensaje;
	}

	private static final class ConfiguracionCanal {
		final int puerto;
		final String token;

		ConfiguracionCanal(int puerto, String token) {
			this.puerto = puerto;
			this.token = token;
		}
	}
}
