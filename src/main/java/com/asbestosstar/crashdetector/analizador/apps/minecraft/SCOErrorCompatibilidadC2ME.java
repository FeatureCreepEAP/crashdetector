package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores de compatibilidad entre C2ME y mods de conexión. Detecta
 * específicamente el error de IllegalAccessException relacionado con el sistema
 * de módulos de Java. Recomienda usar C3ME en lugar de C2ME cuando se utilizan
 * mods de conexión.
 */
public class SCOErrorCompatibilidadC2ME implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private boolean c2mePresente = false;
	private boolean connectorPresente = false;
	private String enlaceHtml = "";

	/**
	 * Verificación global del contenido de la consola.
	 * <p>
	 * Aquí solo se detecta la presencia de C2ME y de los mods de conexión en el
	 * texto completo del log. La detección de la línea concreta con el
	 * IllegalAccessException se realiza en
	 * {@link #verificarPorLinea(Consola, String, int)}, que es llamado para cada
	 * línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Detecta la presencia de C2ME en los logs
		if (contenidoConsola.contains("com.ishland.c2me")) {
			c2mePresente = true;
		}

		// Detecta la presencia de mods de conexión
		if (contenidoConsola.contains("SINYTRA CONNECTOR IS PRESENT!")
				|| contenidoConsola.contains("specialcompatibilityoperation")) {
			connectorPresente = true;
		}
	}

	/**
	 * Analiza cada línea del registro para detectar los errores específicos.
	 * <p>
	 * Solo se procesa la línea si previamente se ha detectado que C2ME y un mod de
	 * conexión están presentes en el log (para reducir trabajo y falsos positivos).
	 * Cuando se encuentra el patrón de IllegalAccessException contra
	 * {@code jdk.internal.misc.Unsafe}, se construye el mensaje y se registra el
	 * enlace.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, o no tenemos el contexto necesario, no seguimos
		if (activado || linea == null || !c2mePresente || !connectorPresente) {
			return;
		}

		// Detecta el error específico de acceso ilegal entre módulos de Java
		if (linea.contains("java.lang.IllegalAccessException")
				&& linea.contains("cannot access class jdk.internal.misc.Unsafe")
				&& linea.contains("because module java.base does not export")) {
			mensaje = MonitorDePID.idioma.errorCompatibilidadC2ME() + Verificaciones.nl_html;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new SCOErrorCompatibilidadC2ME();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 945.0f; // Alta prioridad - error que impide la carga correcta del juego
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_compatibilidad_c2me();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_compatibilidad_c2me())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_compatibilidad_c2me())
				.agregarEtiqueta(MonitorDePID.idioma.paso3_compatibilidad_c2me()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "sco_error_compatibilidad_c2me";
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
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
