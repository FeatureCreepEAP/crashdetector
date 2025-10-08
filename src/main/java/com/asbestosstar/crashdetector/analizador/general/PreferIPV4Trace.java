package com.asbestosstar.crashdetector.analizador.general;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;
import com.asbestosstar.crashdetector.parches.minecraft.PreferIPv4StackParch;

public class PreferIPV4Trace implements Verificaciones {
	private boolean activado = false;
	private String enlaceHtml = "";

	private boolean seenConnectException = false;
	private boolean seenClosedChannelException = false;
	private boolean seenHttpClientImplSend = false;
	private boolean seenHttpClientFacadeSend = false;
	private boolean argIpv4Encontrado = false;
	private int firstExceptionLine = -1;

	@Override
	public void verificar(Consola consola) {
		activado = false;
		enlaceHtml = "";
		seenConnectException = false;
		seenClosedChannelException = false;
		seenHttpClientImplSend = false;
		seenHttpClientFacadeSend = false;
		firstExceptionLine = -1;

		// Verificar errores de conexión relacionados con IPv6
		// Buscar argumento JVM en el contenido del reporte
		// Si no se encontraron flags JVM, verificar propiedad del sistema actual
		String propiedadIpv4 = System.getProperty("java.net.preferIPv4Stack");
		argIpv4Encontrado = "true".equalsIgnoreCase(propiedadIpv4);
	}

	@Override
	public void verificar(Consola consola, String linea, int i) {
		if (!argIpv4Encontrado && linea.trim().startsWith("JVM Flags:")) {
			argIpv4Encontrado = linea.contains("-Djava.net.preferIPv4Stack=true");
		}

		if (linea.contains("java.net.ConnectException"))
			seenConnectException = true;
		if (linea.contains("ClosedChannelException"))
			seenClosedChannelException = true;
		if (linea.contains("jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java"))
			seenHttpClientImplSend = true;
		if (linea.contains("jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java"))
			seenHttpClientFacadeSend = true;

		if (firstExceptionLine < 0 && linea.contains("java.net.ConnectException")
				&& linea.contains("ClosedChannelException")) {
			firstExceptionLine = i;
		}

		boolean errorIpv6 = seenConnectException && seenClosedChannelException && seenHttpClientImplSend
				&& seenHttpClientFacadeSend;

		if (!activado && errorIpv6 && !argIpv4Encontrado) {
			activado = true;
			if (firstExceptionLine >= 0) {
				enlaceHtml = consola.agregarErrorALectador(firstExceptionLine, this);
			}
		}

		if (activado && enlaceHtml.isEmpty() && linea.contains("java.net.ConnectException")
				&& linea.contains("ClosedChannelException")) {
			enlaceHtml = consola.agregarErrorALectador(i, this);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new PreferIPV4Trace();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return MonitorDePID.idioma.tieneErrorIPV6() + " " + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.parcheIPv4();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(MonitorDePID.idioma.parcheIPv4())
				.agregarBoton(MonitorDePID.idioma.activar_parche(), retener -> {
					ConfigDeParches.obtenerInstancia().establecerActivo(PreferIPv4StackParch.id, true);

					// Mostrar mensaje de éxito después de aplicar el parche
					JOptionPane.showMessageDialog(null, MonitorDePID.idioma.exito(), MonitorDePID.idioma.parcheIPv4(),
							JOptionPane.INFORMATION_MESSAGE);
				}, true).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "prefer_ipv4_trace";
	}
}
