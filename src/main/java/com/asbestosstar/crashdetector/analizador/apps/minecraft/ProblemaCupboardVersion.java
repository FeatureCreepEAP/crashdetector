package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el crash causado por versiones antiguas de Cupboard (ej. 3.2).
 *
 * Ejemplo:
 *
 * Caused by: java.lang.NullPointerException: Cannot invoke
 * "net.minecraftforge.fml.ModList.forEachModInOrder(...)" because the return
 * value of "net.minecraftforge.fml.ModList.get()" is null
 *
 * at com.cupboard.compat.ClientConfigCompat.setupNeoforge(...)
 */
public class ProblemaCupboardVersion implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	private boolean vistoErrorModList = false;
	private boolean vistoCupboard = false;

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global
		if (log.contains("ModList.get()") && log.contains("ClientConfigCompat")) {
			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null)
			return;

		if (linea.contains("ModList.get()") && linea.contains("is null")) {
			vistoErrorModList = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains("com.cupboard.compat.ClientConfigCompat.setupNeoforge")) {
			vistoCupboard = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (vistoErrorModList && vistoCupboard) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaCupboardVersion();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		return MonitorDePID.idioma.problemaCupboardVersion() + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaCupboardVersion();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_cupboard_version";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (trazo == null || trazo.trace == null)
			return false;

		String t = trazo.trace;

		return t.contains("ClientConfigCompat.setupNeoforge") && t.contains("ModList.get()");
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}