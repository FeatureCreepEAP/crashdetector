package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detección simple del caso en el que un transformador de LexForge/Forge se
 * ejecuta bajo NeoForge usando CPW ModLauncher y falta el método
 * getTargetType() de ITransformer. No usa expresiones regulares.
 *
 * Condición: en una misma línea del log deben aparecer las cadenas clave: -
 * "Receiver class " - "does not define or inherit an implementation" -
 * "cpw.mods.modlauncher.api.TargetType getTargetType()" -
 * "cpw.mods.modlauncher.api.ITransformer"
 *
 * Luego se extrae el nombre de la clase receptora, se buscan los JARs que
 * contienen esa clase y se construye el mensaje y el enlace.
 */
public class LexForgeMLTransformerEnNeoForge implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private String claseReceptora = "";
	private final String interfazObjetivo = "cpw.mods.modlauncher.api.ITransformer";
	private final String firmaMetodoFaltante = "TargetType getTargetType()";
	private final List<String> modsUbicacion = new ArrayList<>();

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificarPorLinea(Consola, String, int)}, llamada por el analizador
	 * línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Condición: en una misma línea del log deben aparecer las cadenas clave:
	 * <ul>
	 * <li>"Receiver class "</li>
	 * <li>"does not define or inherit an implementation"</li>
	 * <li>"cpw.mods.modlauncher.api.TargetType getTargetType()"</li>
	 * <li>"cpw.mods.modlauncher.api.ITransformer"</li>
	 * </ul>
	 * Cuando se detecta, se extrae la clase receptora, se buscan los JARs que la
	 * contienen y se registra la línea en el lector.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		String l = linea;

		// Comprobación mínima: todo en la misma línea
		if (l.contains("Receiver class ") && l.contains("does not define or inherit an implementation")
				&& l.contains("cpw.mods.modlauncher.api.TargetType getTargetType()")
				&& l.contains("cpw.mods.modlauncher.api.ITransformer")) {

			CrashDetectorLogger.log("LexForgeMLTransformerEnNeoForge: coincidencia -> " + l);

			// Extraer la clase entre "Receiver class " y " does not define"
			int s = l.indexOf("Receiver class ");
			if (s >= 0) {
				s += "Receiver class ".length();
				int e = l.indexOf(" does not define", s);
				if (e > s) {
					claseReceptora = l.substring(s, e).trim();
				}
			}

			if (!claseReceptora.isEmpty()) {
				// Buscar únicamente mods que contengan exactamente esa clase
				String classPath = claseReceptora.replace('.', '/') + ".class";
				List<ArchivoDeMod> mods = Buscador.buscarModsConTermino(classPath);
				for (ArchivoDeMod m : mods) {
					modsUbicacion.add(m.ubicacion_para_publicar());
				}
			} else {
				CrashDetectorLogger.log("LexForgeMLTransformerEnNeoForge: no se pudo extraer clase receptora");
			}

			// Activar SIEMPRE tras detectar la línea, aunque no se encuentren mods
			mensaje = MonitorDePID.idioma.errorLexForgeMLTransformerEnNeoForge(
					claseReceptora.isEmpty() ? "(desconocida)" : claseReceptora, interfazObjetivo, firmaMetodoFaltante,
					modsUbicacion) + Verificaciones.nl_html;

			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new LexForgeMLTransformerEnNeoForge();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Bloquea el arranque
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_LexForgeMLTransformerEnNeoForge();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_LexForgeMLTransformerEnNeoForge(
						claseReceptora.isEmpty() ? "(desconocida)" : claseReceptora, interfazObjetivo,
						firmaMetodoFaltante))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_LexForgeMLTransformerEnNeoForge(modsUbicacion))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_LexForgeMLTransformerEnNeoForge())
				.agregarEtiqueta(MonitorDePID.idioma.paso4_LexForgeMLTransformerEnNeoForge()).construir();
	}

	@Override
	public String id() {
		return "lexforge_ml_transformer_en_neoforge";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, se comprueba:
	 * <ul>
	 * <li>Que el verificador ya esté activado.</li>
	 * <li>Que el trazo contenga las mismas cadenas clave utilizadas para detectar
	 * el error.</li>
	 * <li>Si se conoce, que también incluya el nombre de la clase receptora.</li>
	 * </ul>
	 * Es deliberadamente conservador: se prefiere un falso negativo antes que
	 * marcar un trazo que no corresponda a este problema.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		boolean contieneBase = t.contains("Receiver class ")
				&& t.contains("does not define or inherit an implementation")
				&& t.contains("cpw.mods.modlauncher.api.TargetType getTargetType()")
				&& t.contains("cpw.mods.modlauncher.api.ITransformer");

		if (!contieneBase) {
			return false;
		}

		if (claseReceptora != null && !claseReceptora.isEmpty()) {
			return t.contains("Receiver class " + claseReceptora);
		}

		// Fallback muy estricto si no se pudo extraer la clase
		return true;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
