package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
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
public class LexForgeMLTransformerEnNeoForge implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private String claseReceptora = "";
	private final String interfazObjetivo = "cpw.mods.modlauncher.api.ITransformer";
	private final String firmaMetodoFaltante = "TargetType getTargetType()";
	private final List<String> modsUbicacion = new ArrayList<>();
	public boolean posible = false;

	private static final String RECEIVER_CLASS = "Receiver class ";
	private static final String DOES_NOT_DEFINE = "does not define or inherit an implementation";
	private static final String TARGET_TYPE_GET_TARGET_TYPE = "cpw.mods.modlauncher.api.TargetType getTargetType()";
	private static final String I_TRANSFORMER = "cpw.mods.modlauncher.api.ITransformer";
	private static final String DOES_NOT_DEFINE_PREFIX = " does not define";

	@Override
	public String[] patronesRapidos() {
		return new String[] { RECEIVER_CLASS, DOES_NOT_DEFINE, TARGET_TYPE_GET_TARGET_TYPE, I_TRANSFORMER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneErrorTransformer(evento.linea)) {
			posible = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

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

		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String l = consola.contenido_verificar;

		if (lineaContieneErrorTransformer(l)) {
			posible = true;
		}

	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posible && !activado;
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
		if (activado || !posible || linea == null) {
			return;
		}

		String l = linea;

		// Comprobación mínima: todo en la misma línea
		if (lineaContieneErrorTransformer(l)) {

			CrashDetectorLogger.log("LexForgeMLTransformerEnNeoForge: coincidencia -> " + l);

			// Extraer la clase entre "Receiver class " y " does not define"
			int s = l.indexOf(RECEIVER_CLASS);
			if (s >= 0) {
				s += RECEIVER_CLASS.length();
				int e = l.indexOf(DOES_NOT_DEFINE_PREFIX, s);
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

	private boolean lineaContieneErrorTransformer(String l) {
		return l.contains(RECEIVER_CLASS) && l.contains(DOES_NOT_DEFINE) && l.contains(TARGET_TYPE_GET_TARGET_TYPE)
				&& l.contains(I_TRANSFORMER);
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

		boolean contieneBase = lineaContieneErrorTransformer(t);

		if (!contieneBase) {
			return false;
		}

		if (claseReceptora != null && !claseReceptora.isEmpty()) {
			return t.contains(RECEIVER_CLASS + claseReceptora);
		}

		// Fallback muy estricto si no se pudo extraer la clase
		return true;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}