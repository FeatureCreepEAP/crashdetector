package com.asbestosstar.crashdetector.bittorrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Implementación del protocolo de tracker UDP de BitTorrent. */
final class ClienteTrackerUdp implements ClienteTracker {

	private static final long ID_PROTOCOLO = 0x41727101980L;
	private final URI uri;

	ClienteTrackerUdp(URI uri) {
		this.uri = uri;
	}

	@Override
	public RespuestaTracker anunciar(SolicitudTracker solicitud) throws Exception {
		InetAddress direccion = InetAddress.getByName(uri.getHost());
		InetSocketAddress remoto = new InetSocketAddress(direccion, uri.getPort());
		try (DatagramSocket socket = new DatagramSocket()) {
			socket.connect(remoto);
			socket.setSoTimeout(ConfiguracionBitTorrent.obtenerTimeoutMilisegundos());
			long conexion = conectar(socket);
			return anunciar(socket, conexion, solicitud);
		}
	}

	private long conectar(DatagramSocket socket) throws Exception {
		int transaccion = UtilBitTorrent.enteroAleatorio();
		ByteBuffer solicitud = ByteBuffer.allocate(16).order(ByteOrder.BIG_ENDIAN);
		solicitud.putLong(ID_PROTOCOLO).putInt(0).putInt(transaccion);
		byte[] respuesta = intercambiar(socket, solicitud.array(), 16, 2048);
		ByteBuffer datos = ByteBuffer.wrap(respuesta).order(ByteOrder.BIG_ENDIAN);
		int accion = datos.getInt();
		int recibido = datos.getInt();
		if (accion == 3) {
			throw new IOException(leerError(respuesta, 8));
		}
		if (accion != 0 || recibido != transaccion || respuesta.length < 16) {
			throw new IOException();
		}
		return datos.getLong();
	}

	private RespuestaTracker anunciar(DatagramSocket socket, long conexion, SolicitudTracker datosSolicitud)
			throws Exception {
		int transaccion = UtilBitTorrent.enteroAleatorio();
		ByteBuffer solicitud = ByteBuffer.allocate(98).order(ByteOrder.BIG_ENDIAN);
		solicitud.putLong(conexion);
		solicitud.putInt(1);
		solicitud.putInt(transaccion);
		solicitud.put(datosSolicitud.infoHash);
		solicitud.put(datosSolicitud.peerId);
		solicitud.putLong(datosSolicitud.descargado);
		solicitud.putLong(datosSolicitud.restante);
		solicitud.putLong(datosSolicitud.subido);
		solicitud.putInt(datosSolicitud.evento.codigoUdp());
		solicitud.putInt(0);
		solicitud.putInt(UtilBitTorrent.enteroAleatorio());
		solicitud.putInt(datosSolicitud.cantidadDeseada);
		solicitud.putShort((short) (datosSolicitud.puerto & 0xffff));

		byte[] respuesta = intercambiar(socket, solicitud.array(), 20, 65507);
		ByteBuffer entrada = ByteBuffer.wrap(respuesta).order(ByteOrder.BIG_ENDIAN);
		int accion = entrada.getInt();
		int recibido = entrada.getInt();
		if (accion == 3) {
			throw new IOException(leerError(respuesta, 8));
		}
		if (accion != 1 || recibido != transaccion || respuesta.length < 20) {
			throw new IOException();
		}
		int intervalo = entrada.getInt();
		int incompletos = entrada.getInt();
		int completos = entrada.getInt();
		List<DireccionPeer> pares = new ArrayList<DireccionPeer>();
		while (entrada.remaining() >= 6) {
			byte[] ip = new byte[4];
			entrada.get(ip);
			int puerto = entrada.getShort() & 0xffff;
			if (puerto > 0) {
				pares.add(new DireccionPeer(InetAddress.getByAddress(ip).getHostAddress(), puerto));
			}
		}
		return new RespuestaTracker(intervalo, completos, incompletos, pares);
	}

	private static byte[] intercambiar(DatagramSocket socket, byte[] solicitud, int minimo, int maximo)
			throws Exception {
		DatagramPacket salida = new DatagramPacket(solicitud, solicitud.length, socket.getRemoteSocketAddress());
		socket.send(salida);
		byte[] buffer = new byte[maximo];
		DatagramPacket entrada = new DatagramPacket(buffer, buffer.length);
		socket.receive(entrada);
		if (entrada.getLength() < minimo) {
			throw new IOException();
		}
		byte[] respuesta = new byte[entrada.getLength()];
		System.arraycopy(buffer, 0, respuesta, 0, respuesta.length);
		return respuesta;
	}

	private static String leerError(byte[] respuesta, int inicio) {
		if (respuesta.length <= inicio) {
			return "";
		}
		return new String(respuesta, inicio, respuesta.length - inicio, StandardCharsets.UTF_8);
	}
}
