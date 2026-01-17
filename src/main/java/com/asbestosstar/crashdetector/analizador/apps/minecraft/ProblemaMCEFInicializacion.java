package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta cuando el mod MCEF se inicializa al final del log, lo que indica
 * probablemente un cuelgue silencioso durante la carga.
 */
public class ProblemaMCEFInicializacion implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";
	private String[] lineasTotales = null;

	private static final Pattern PATRON_MCEF = Pattern
			.compile("(?i)Initializing CEF on |\\[org\\.cef\\.CefApp:initialize:");

	@Override
	public void verificar(Consola consola) {
		this.lineasTotales = consola.contenido_verificar.split(Verificaciones.nl);
		this.activado = false;
		this.enlace = "";
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || lineasTotales == null) {
			return;
		}

		// Solo considerar si está entre las últimas 5 líneas
		if (numero_de_linea < Math.max(0, lineasTotales.length - 5)) {
			return;
		}

		if (PATRON_MCEF.matcher(linea).find()) {
			this.activado = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaMCEFInicializacion();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 750.0f;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}
		String html = MonitorDePID.idioma.problema_mcef_inicializacion_html();
		if (enlace.isEmpty()) {
			return html;
		}
		return html + " " + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_problema_mcef_inicializacion();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_mod_mcef());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_verificar_compatibilidad_mcef());
		return builder.construir();
	}

	@Override
	public String id() {
		return "problema_mcef_inicializacion";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}