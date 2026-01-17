package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre Multiworld y mods de rendimiento (Sodium, Embeddium,
 * Rubidium) que provocan un error de clase incompatible durante la
 * inicialización del cliente.
 */
public class ConflictoMultiworldRendimiento implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	/**
	 * Método de compatibilidad — no hace nada, ya que el análisis es por línea.
	 */
	@Override
	public void verificar(Consola consola) {
		// Se usa el método verificar(Consola, String, int) en lugar de este.
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error: "IncompatibleClassChangeError",
	 * "net.fabricmc.loader.api.FabricLoader.getInstance()" y "must be Methodref
	 * constant".
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		if (linea.contains("IncompatibleClassChangeError")
				&& linea.contains("net.fabricmc.loader.api.FabricLoader.getInstance()")
				&& linea.contains("must be Methodref constant")) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia a mods de rendimiento
			mensaje = MonitorDePID.idioma.errorConflictoMultiworldRendimiento() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoMultiworldRendimiento();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Alta prioridad: rompe inicialización del cliente
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoMultiworldRendimiento();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoConflictoMultiworldRendimiento())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_multiworld_rendimiento";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Ultra conservador: solo devuelve true si el trazo contiene las tres cadenas
	 * clave exactas del error. Evita falsos positivos.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("IncompatibleClassChangeError")
				&& t.contains("net.fabricmc.loader.api.FabricLoader.getInstance()")
				&& t.contains("must be Methodref constant");
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
