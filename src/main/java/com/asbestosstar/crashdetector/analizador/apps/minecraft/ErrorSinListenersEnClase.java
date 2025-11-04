package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Analiza errores cuando una clase registrada como listener de eventos no
 * contiene métodos válidos. Detecta específicamente el error "No listeners
 * found in class [nombre de clase]". Utiliza Buscardor para identificar mods
 * que contienen la clase problemática.
 */
public class ErrorSinListenersEnClase implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreClase = "";
	private List<String> modsUbicacion = new ArrayList<>();
	private String enlaceHtml = "";

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección real se hace por línea en
	 * {@link #verificar(Consola, String, int)}, llamada por el analizador línea a
	 * línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón: "No listeners found in class <clase>" en la línea actual,
	 * extrae el nombre de la clase, localiza los mods que contienen esa clase y
	 * registra la línea en el lector.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		// Detecta el error específico de clase sin listeners
		if (linea.contains("No listeners found in class")) {

			// Extrae el nombre de la clase usando expresión regular
			Pattern pattern = Pattern.compile("No listeners found in class ([^\\s]+)");
			Matcher matcher = pattern.matcher(linea);
			if (matcher.find()) {
				nombreClase = matcher.group(1);

				// Convierte el nombre de clase de notación con puntos a notación con barras
				String classPath = nombreClase.replace('.', '/');

				// Busca mods que contienen esta clase
				List<ArchivoDeMod> modsPotenciales = Buscardor.buscarModsConTermino(classPath + ".class");

				// Extrae las ubicaciones para publicar de cada mod encontrado
				for (ArchivoDeMod mod : modsPotenciales) {
					modsUbicacion.add(mod.ubicacion_para_publicar());
				}

				mensaje = MonitorDePID.idioma.errorSinListenersEnClase(nombreClase, modsUbicacion)
						+ Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorSinListenersEnClase();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 880.0f; // Prioridad alta - error que impide registrar eventos del mod
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_sin_listeners_en_clase();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_sin_listeners_en_clase(nombreClase, modsUbicacion))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_sin_listeners_en_clase(nombreClase))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_sin_listeners_en_clase(nombreClase, modsUbicacion))
				.construir();
	}

	@Override
	public String id() {
		return "sin_listeners_en_clase";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene el texto completo del error con la clase conocida, o,
	 * como fallback muy estricto, el patrón base.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a marcar un
	 * trazo que no corresponda a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (nombreClase != null && !nombreClase.isEmpty()) {
			String esperado = "No listeners found in class " + nombreClase;
			return t.contains(esperado);
		}

		// Fallback muy estricto si por alguna razón no se capturó el nombre de la
		// clase.
		return t.contains("No listeners found in class ");
	}

}
