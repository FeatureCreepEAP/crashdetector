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

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		String[] lineas = contenido.split("\n");

		// Verificar errores de conexión relacionados con IPv6
		boolean errorIpv6 = contenido.contains("java.net.ConnectException")
				&& contenido.contains("ClosedChannelException")
				&& contenido.contains("jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java")
				&& contenido.contains("jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java");

		if (errorIpv6) {
			boolean argIpv4Encontrado = false;

			// Buscar argumento JVM en el contenido del reporte
			for (String linea : lineas) {
				if (linea.trim().startsWith("JVM Flags:")) {
					argIpv4Encontrado = linea.contains("-Djava.net.preferIPv4Stack=true");
					break;
				}
			}

			// Si no se encontraron flags JVM, verificar propiedad del sistema actual
			if (!argIpv4Encontrado) {
				String propiedadIpv4 = System.getProperty("java.net.preferIPv4Stack");
				argIpv4Encontrado = "true".equalsIgnoreCase(propiedadIpv4);
			}

			// Activar solo si falta el argumento
			activado = !argIpv4Encontrado;

			// Si está activado, registrar el error con la primera línea relevante
			if (activado) {
				for (int i = 0; i < lineas.length; i++) {
					String linea = lineas[i];
					if (linea.contains("java.net.ConnectException") && linea.contains("ClosedChannelException")) {
						enlaceHtml = consola.agregarErrorALectador(i, this);
						break;
					}
				}
			}
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