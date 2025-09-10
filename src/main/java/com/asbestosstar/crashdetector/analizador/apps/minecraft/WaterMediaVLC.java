package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.awt.Desktop;
import java.net.URI;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.Verificaciones.Criticalidad;

public class WaterMediaVLC implements Verificaciones {
	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		if (contenido.contains(
				"WATERMeDIA doesn't contains VLC binaries for your OS and ARCH, you had to download it manually")) {
			this.mensaje = MonitorDePID.idioma.noTienesVLCBin() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new WaterMediaVLC();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 700.0f; // Prioridad ligeramente menor que el original
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return "WaterMediaVLC";
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder("WaterMediaVLC").agregarBoton(MonitorDePID.idioma.descargar_vlc(), retener -> {
			try {
				String url = "https://www.videolan.org/vlc/"; // URL real del sitio
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					Desktop.getDesktop().browse(new URI(url));
				}
			} catch (Exception e) {
				// Manejo de errores (podrías mostrar un mensaje al usuario)
				e.printStackTrace();
			}
		}, true).construir();
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}
}