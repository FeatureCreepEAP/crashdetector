package com.asbestosstar.crashdetector.gui.tipos.correo;

import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import jakarta.mail.Address;
import jakarta.mail.Flags.Flag;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.UIDFolder;
import jakarta.mail.internet.InternetAddress;

/**
 * Implementación real de IMAPS mediante Jakarta Mail/Eclipse Angus.
 *
 * IMPORTANTE: ninguna clase de la GUI debe referenciar esta clase directamente.
 * {@link CorreoImapOpcional} la carga por reflexión únicamente después de
 * comprobar que las dependencias de correo existen en tiempo de ejecución.
 */
public final class CorreoImapJakarta {

	private CorreoImapJakarta() {
	}

	public static List<MensajeCorreo> descargarMensajes(CuentaCorreo cuenta, int cantidadMaxima,
			int cuerpoMaximoCaracteres) throws Exception {
		Properties propiedades = new Properties();
		propiedades.setProperty("mail.store.protocol", "imaps");
		propiedades.setProperty("mail.imaps.ssl.enable", "true");
		propiedades.setProperty("mail.imaps.ssl.checkserveridentity", "true");
		propiedades.setProperty("mail.imaps.connectiontimeout", "15000");
		propiedades.setProperty("mail.imaps.timeout", "30000");
		propiedades.setProperty("mail.imaps.writetimeout", "30000");
		propiedades.setProperty("mail.imaps.peek", "true");

		Session sesion = Session.getInstance(propiedades);
		Store store = null;
		Folder folder = null;
		char[] secreto = cuenta.copiarSecreto();
		try {
			store = sesion.getStore("imaps");
			store.connect(cuenta.servidorImap(), cuenta.puerto(), cuenta.usuario(), new String(secreto));
			folder = store.getFolder(cuenta.carpeta());
			folder.open(Folder.READ_ONLY);

			int total = folder.getMessageCount();
			if (total <= 0) {
				return Collections.emptyList();
			}

			int maximo = Math.max(1, cantidadMaxima);
			int inicio = Math.max(1, total - maximo + 1);
			Message[] mensajes = folder.getMessages(inicio, total);
			List<MensajeCorreo> resultado = new ArrayList<MensajeCorreo>(mensajes.length);

			for (Message mensaje : mensajes) {
				long uid = obtenerUid(folder, mensaje);
				String remitente = direcciones(mensaje.getFrom());
				String destinatarios = direcciones(mensaje.getAllRecipients());
				String asunto = valorSeguro(mensaje.getSubject());
				Date fechaMensaje = mensaje.getReceivedDate();
				if (fechaMensaje == null) {
					fechaMensaje = mensaje.getSentDate();
				}
				Instant fecha = fechaMensaje == null ? Instant.EPOCH : fechaMensaje.toInstant();
				String cuerpo = limitar(extraerTextoSeguro(mensaje), cuerpoMaximoCaracteres);
				boolean leido = mensaje.isSet(Flag.SEEN);

				resultado.add(new MensajeCorreo(cuenta.id(), cuenta.carpeta(), uid, remitente, destinatarios,
						asunto, fecha, cuerpo, leido));
			}
			return resultado;
		} finally {
			Arrays.fill(secreto, '\0');
			cerrarFolder(folder);
			cerrarStore(store);
		}
	}

	private static long obtenerUid(Folder folder, Message mensaje) throws Exception {
		if (folder instanceof UIDFolder) {
			long uid = ((UIDFolder) folder).getUID(mensaje);
			if (uid != UIDFolder.LASTUID && uid > 0) {
				return uid;
			}
		}
		return mensaje.getMessageNumber();
	}

	private static String direcciones(Address[] direcciones) {
		if (direcciones == null || direcciones.length == 0) {
			return "";
		}
		StringBuilder salida = new StringBuilder();
		for (Address direccion : direcciones) {
			if (direccion == null) {
				continue;
			}
			if (salida.length() > 0) {
				salida.append(", ");
			}
			if (direccion instanceof InternetAddress) {
				salida.append(((InternetAddress) direccion).toUnicodeString());
			} else {
				salida.append(direccion.toString());
			}
		}
		return salida.toString();
	}

	private static String extraerTextoSeguro(Part parte) throws Exception {
		String disposicion = parte.getDisposition();
		if (Part.ATTACHMENT.equalsIgnoreCase(disposicion)) {
			return "";
		}

		if (parte.isMimeType("text/plain")) {
			Object contenido = parte.getContent();
			return contenido instanceof String ? (String) contenido : "";
		}
		if (parte.isMimeType("text/html")) {
			Object contenido = parte.getContent();
			return contenido instanceof String ? htmlATexto((String) contenido) : "";
		}
		if (parte.isMimeType("multipart/alternative")) {
			Object contenido = parte.getContent();
			if (!(contenido instanceof Multipart)) {
				return "";
			}
			Multipart multipart = (Multipart) contenido;
			String html = "";
			for (int i = 0; i < multipart.getCount(); i++) {
				Part subparte = multipart.getBodyPart(i);
				if (subparte.isMimeType("text/plain")) {
					String texto = extraerTextoSeguro(subparte);
					if (!texto.isEmpty()) {
						return texto;
					}
				} else if (subparte.isMimeType("text/html")) {
					html = extraerTextoSeguro(subparte);
				}
			}
			return html;
		}
		if (parte.isMimeType("multipart/*")) {
			Object contenido = parte.getContent();
			if (!(contenido instanceof Multipart)) {
				return "";
			}
			Multipart multipart = (Multipart) contenido;
			StringBuilder salida = new StringBuilder();
			for (int i = 0; i < multipart.getCount(); i++) {
				String texto = extraerTextoSeguro(multipart.getBodyPart(i));
				if (!texto.trim().isEmpty()) {
					if (salida.length() > 0) {
						salida.append("\n\n");
					}
					salida.append(texto);
				}
			}
			return salida.toString();
		}
		if (parte.isMimeType("message/rfc822")) {
			Object contenido = parte.getContent();
			return contenido instanceof Part ? extraerTextoSeguro((Part) contenido) : "";
		}
		return "";
	}

	private static void cerrarFolder(Folder folder) {
		if (folder == null) {
			return;
		}
		try {
			if (folder.isOpen()) {
				folder.close(false);
			}
		} catch (Throwable ignored) {
		}
	}

	private static void cerrarStore(Store store) {
		if (store == null) {
			return;
		}
		try {
			if (store.isConnected()) {
				store.close();
			}
		} catch (Throwable ignored) {
		}
	}

	private static String valorSeguro(String valor) {
		return valor == null ? "" : valor;
	}

	private static String limitar(String texto, int maximo) {
		if (texto == null) {
			return "";
		}
		int limite = Math.max(1, maximo);
		return texto.length() <= limite ? texto : texto.substring(0, limite);
	}

	private static String htmlATexto(String html) throws IOException {
		if (html == null || html.isEmpty()) {
			return "";
		}
		StringBuilder texto = new StringBuilder();
		new ParserDelegator().parse(new StringReader(html), new HTMLEditorKit.ParserCallback() {
			@Override
			public void handleText(char[] data, int pos) {
				texto.append(data);
			}

			@Override
			public void handleStartTag(HTML.Tag tag, MutableAttributeSet atributos, int pos) {
				if (tag == HTML.Tag.P || tag == HTML.Tag.DIV || tag == HTML.Tag.BR || tag == HTML.Tag.LI) {
					texto.append('\n');
				}
			}
		}, true);
		return texto.toString().replaceAll("(?m)^[ \\t]+", "").replaceAll("\\n{3,}", "\n\n").trim();
	}
}
