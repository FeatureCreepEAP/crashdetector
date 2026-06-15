package com.asbestosstar.crashdetector.analizador.general;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;
import com.asbestosstar.crashdetector.parches.minecraft.PreferIPv4StackParch;

public class PreferIPV4Trace implements VerificacionRapida {

	private boolean activado = false;
	private String enlaceHtml = "";

	private boolean seenConnectException = false;
	private boolean seenClosedChannelException = false;
	private boolean seenHttpClientImplSend = false;
	private boolean seenHttpClientFacadeSend = false;
	private boolean argIpv4Encontrado = false;
	private int firstExceptionLine = -1;
	public boolean gml = false;

	private static final String JVM_FLAGS = "JVM Flags:";
	private static final String ARG_PREFER_IPV4 = "-Djava.net.preferIPv4Stack=true";
	private static final String PROP_PREFER_IPV4 = "java.net.preferIPv4Stack";
	private static final String CONNECT_EXCEPTION = "java.net.ConnectException";
	private static final String CLOSED_CHANNEL_EXCEPTION = "ClosedChannelException";
	private static final String HTTP_CLIENT_IMPL_SEND = "jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java";
	private static final String HTTP_CLIENT_FACADE_SEND = "jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java";
	private static final String GML = "gml";
	private static final String GML_MAPPINGS_PROVIDER = "org.groovymc.gml.mappings.MappingsProvider.loadLayeredMappings";

	@Override
	public String[] patronesRapidos() {
		return new String[] { JVM_FLAGS, ARG_PREFER_IPV4, CONNECT_EXCEPTION, CLOSED_CHANNEL_EXCEPTION,
				HTTP_CLIENT_IMPL_SEND, HTTP_CLIENT_FACADE_SEND, GML, GML_MAPPINGS_PROVIDER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Verificar errores de conexión relacionados con IPv6
		// Buscar argumento JVM en el contenido del reporte
		// Si no se encontraron flags JVM, verificar propiedad del sistema actual
		String propiedadIpv4 = System.getProperty(PROP_PREFER_IPV4);
		if ("true".equalsIgnoreCase(propiedadIpv4)) {
			argIpv4Encontrado = true;
		}

		if (consola.contenido_verificar.contains(ARG_PREFER_IPV4)) {
			argIpv4Encontrado = true;
		}

		if (consola.contenido_verificar.contains(GML)) {
			gml = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int i) {
		if (linea == null || linea.isEmpty() || activado) {
			return;
		}

		if (!argIpv4Encontrado && linea.startsWith(JVM_FLAGS)) {
			argIpv4Encontrado = linea.contains(ARG_PREFER_IPV4);
		}

		if (!lineaEsRelevante(linea)) {
			return;
		}

		if (linea.contains(GML)) {
			gml = true;
		}

		if (linea.contains(CONNECT_EXCEPTION)) {
			seenConnectException = true;
		}

		if (linea.contains(CLOSED_CHANNEL_EXCEPTION)) {
			seenClosedChannelException = true;
		}

		if (linea.contains(HTTP_CLIENT_IMPL_SEND)) {
			seenHttpClientImplSend = true;
		}

		if (linea.contains(HTTP_CLIENT_FACADE_SEND)) {
			seenHttpClientFacadeSend = true;
		}

		if (firstExceptionLine < 0 && linea.contains(CONNECT_EXCEPTION) && linea.contains(CLOSED_CHANNEL_EXCEPTION)) {
			firstExceptionLine = i;
		}

		boolean errorIpv6 = seenConnectException && seenClosedChannelException && seenHttpClientImplSend
				&& seenHttpClientFacadeSend;

		if (linea.contains(GML_MAPPINGS_PROVIDER)) {
			errorIpv6 = true;// gml error
			argIpv4Encontrado = false;
			gml = true;

			if (firstExceptionLine < 0) {
				firstExceptionLine = i;
			}
		}

		if (!activado && errorIpv6 && !argIpv4Encontrado) {
			activado = true;

			if (consola != null && firstExceptionLine >= 0) {
				enlaceHtml = consola.agregarErrorALectador(firstExceptionLine, this);
			}
		}

		if (activado && enlaceHtml.isEmpty() && consola != null && linea.contains(CONNECT_EXCEPTION)
				&& linea.contains(CLOSED_CHANNEL_EXCEPTION)) {
			enlaceHtml = consola.agregarErrorALectador(i, this);
		}
	}

	private boolean lineaEsRelevante(String linea) {
		return linea.contains(JVM_FLAGS) || linea.contains(ARG_PREFER_IPV4) || linea.contains(CONNECT_EXCEPTION)
				|| linea.contains(CLOSED_CHANNEL_EXCEPTION) || linea.contains(HTTP_CLIENT_IMPL_SEND)
				|| linea.contains(HTTP_CLIENT_FACADE_SEND) || linea.contains(GML)
				|| linea.contains(GML_MAPPINGS_PROVIDER);
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
		if (!activado) {
			return "";
		}

		StringBuilder cons = new StringBuilder(MonitorDePID.idioma.tieneErrorIPV6());

		if (gml) {
			cons.append(nl_html).append(MonitorDePID.idioma.gmlIPV6());
		}

		cons.append(" ").append(enlaceHtml);
		return cons.toString();
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

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}