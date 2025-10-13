package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta dependencias faltantes o versiones incorrectas en mods de Fabric.
 *
 * Soporta tanto el bloque "A potential solution has been determined" (líneas
 * "Install ..." y "Replace ...") como la sección "More details:" con mensajes
 * de dependencias faltantes y rangos de versión. También limpia códigos de
 * color/caracteres extraños que suelen aparecer en logs (�6, �E, ·, etc.).
 */
public class ProblemaDependenciaModFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	// Listas paralelas para armar el mensaje y el QuickFix
	private final List<String> nombresMods = new ArrayList<>();
	private final List<String> dependencias = new ArrayList<>();
	private final List<String> versiones = new ArrayList<>();
	private final List<String> enlaces = new ArrayList<>();

	// Conjunto para deduplicar (clave: mod|dep|ver)
	private final Set<String> clavesUnicas = new LinkedHashSet<>();

	// Patrones reutilizables (en minúsculas/insensible a mayúsculas)
	private static final Pattern P_INSTALAR = Pattern.compile(
			"^-\\s*Install\\s+([^,]+),\\s*version\\s+([^\\.]+)\\s+or\\s+later\\.", Pattern.CASE_INSENSITIVE);

	private static final Pattern P_REEMPLAZO_RANGO = Pattern.compile(
			"^-\\s*Replace\\s+.*?\\(([^\\)]+)\\).*?with\\s+any\\s+version\\s+between\\s+([^\\s]+)\\s*\\(inclusive\\)\\s*and\\s*([^\\s]+)\\s*\\(exclusive\\)\\.",
			Pattern.CASE_INSENSITIVE);

	// Detalles: faltante "requires version X or later of Y, which is missing!"
	private static final Pattern P_FALTANTE_MINIMO = Pattern.compile(
	    "^-\\s*mod\\s+.+?\\(([^\\)]+)\\)\\s*[^\\)]*\\)?\\s*.*?requires\\s+version\\s+([^\\s]+)\\s*.*?or\\s+later\\s+of\\s+([a-z0-9_\\-]+)\\s*,\\s*which\\s+is\\s+missing",
	    Pattern.CASE_INSENSITIVE
	);

	// Detalles: rango requerido de otro mod y versión equivocada presente
	private static final Pattern P_RANGO_PRESENTE_INCORRECTO = Pattern.compile(
			"^-\\s*mod\\s+.+?\\(([^\\)]+)\\).*?requires\\s+any\\s+version\\s+between\\s+([^\\s]+)\\s*\\(inclusive\\)\\s*and\\s*([^\\s]+)\\s*\\(exclusive\\)\\s+of\\s+mod\\s+.+?\\(([^\\)]+)\\).*?wrong\\s+version\\s+is\\s+present:\\s*([^!\\s]+)!",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Verifica si el log contiene dependencias faltantes o versiones incorrectas en mods de Fabric.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		String[] lineas = contenido.split("\n");

		boolean enDetalles = false;
		boolean enSugerencias = false;

		for (int i = 0; i < lineas.length; i++) {
			String cruda = lineas[i];
			String linea = limpiarLinea(cruda);

			// Detectar inicio de bloque de sugerencias
			if (linea.startsWith("A potential solution has been determined")) {
				enSugerencias = true;
				enDetalles = false;
				continue;
			}
			// Detectar bloque de detalles
			if (linea.equals("More details:")) {
				enDetalles = true;
				enSugerencias = false;
				continue;
			}

			// Salir del bloque si se detecta comienzo de stacktrace
			if ((enDetalles || enSugerencias) && (linea.startsWith("at ") || linea.startsWith("Caused by:"))) {
				enDetalles = false;
				enSugerencias = false;
				continue;
			}

			// Procesar bloque de sugerencias "Install/Replace"
			if (enSugerencias && linea.startsWith("- ")) {
				Matcher mInst = P_INSTALAR.matcher(linea);
				if (mInst.find()) {
					String enlace = consola.agregarErrorALectador(i, this);
					String depNombre = mInst.group(1).trim();
					String min = normalizarVersion(mInst.group(2));
					// Guardar descriptor como "mínima X" para evitar duplicar la palabra "versión"
					agregarProblema("instalacion", depNombre.toLowerCase(Locale.ROOT), "mínima " + min, enlace);
					continue;
				}
				Matcher mReemp = P_REEMPLAZO_RANGO.matcher(linea);
				if (mReemp.find()) {
					String enlace = consola.agregarErrorALectador(i, this);
					String depId = mReemp.group(1).toLowerCase(Locale.ROOT);
					String min = normalizarVersion(mReemp.group(2));
					String max = normalizarVersion(mReemp.group(3));
					agregarProblema("reemplazo", depId, "rango:" + min + ":" + max, enlace);
					continue;
				}
			}

			// Procesar bloque "More details"
			if (enDetalles && linea.startsWith("- ")) {
				// Caso: rango requerido y versión presente incorrecta
				Matcher mRango = P_RANGO_PRESENTE_INCORRECTO.matcher(linea);
				if (mRango.find()) {
					String enlace = consola.agregarErrorALectador(i, this);
					String modSolicitante = mRango.group(1);
					String min = normalizarVersion(mRango.group(2));
					String max = normalizarVersion(mRango.group(3));
					String depId = mRango.group(4).toLowerCase(Locale.ROOT);
					String actual = normalizarVersion(mRango.group(5));
					agregarProblema(modSolicitante, depId, "rango:" + min + ":" + max + ":" + actual, enlace);
					continue;
				}

				// Caso: faltante con versión mínima
				Matcher mFalta = P_FALTANTE_MINIMO.matcher(linea);
				if (mFalta.find()) {
					String enlace = consola.agregarErrorALectador(i, this);
					String modId = mFalta.group(1);
					String min = normalizarVersion(mFalta.group(2));
					String depId = mFalta.group(3).toLowerCase(Locale.ROOT);
					agregarProblema(modId, depId, "mínima " + min, enlace);
					continue;
				}

				// Compatibilidad con formato anterior
				if (linea.startsWith("- Mod ") || linea.startsWith("- mod ")) {
					int inicioModID = linea.indexOf("(");
					int finModID = linea.indexOf(")");
					String nombreMod = "";
					if (inicioModID > 0 && finModID > inicioModID) {
						nombreMod = linea.substring(inicioModID + 1, finModID);
					}
					if (!nombreMod.isEmpty() && linea.contains("requires version")) {
						String dependencia = "";
						String version = "";

						if (linea.contains(" of ")) {
							int idxOf = linea.indexOf(" of ") + 4;
							int finDep = linea.indexOf(",", idxOf);
							if (finDep > idxOf) {
								dependencia = linea.substring(idxOf, finDep).trim().toLowerCase(Locale.ROOT);
							}
						}
						int inicioVer = linea.indexOf("version ") + 8;
						int finVer = linea.indexOf(" ", inicioVer);
						if (inicioVer > 0 && finVer > inicioVer) {
							version = normalizarVersion(linea.substring(inicioVer, finVer));
						}

						if (!nombreMod.isEmpty() && !dependencia.isEmpty()) {
							String enlace = consola.agregarErrorALectador(i, this);
							if (!version.isEmpty()) {
								agregarProblema(nombreMod, dependencia, "mínima " + version, enlace);
							} else {
								agregarProblema(nombreMod, dependencia, "no encontrada", enlace);
							}
						}
					}
				}
			}
		}

		// Construir el mensaje si se detectaron problemas
		if (!nombresMods.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < nombresMods.size(); i++) {
				String enlace = i < enlaces.size() ? enlaces.get(i) : "";
				String mod = nombresMods.get(i);
				String dep = dependencias.get(i);
				String ver = versiones.get(i);

				if (ver.startsWith("rango:")) {
					// Formato "rango:min[:max[:actual]]"
					String[] partes = ver.split(":");
					String min = partes.length > 1 ? partes[1] : "";
					String max = partes.length > 2 ? partes[2] : "";
					String actual = partes.length > 3 ? partes[3] : "";
					sb.append("Requiere un rango de versión para <b>")
					  .append(dep).append("</b>: [").append(min).append(" – ").append(max)
					  .append(")");
					if (!actual.isEmpty()) {
						sb.append(" (actual: ").append(actual).append(")");
					}
					sb.append(" ").append(enlace).append("<br><br>");

				} else if (ver.startsWith("mínima ")) {
					String min = ver.substring("mínima ".length()).trim();
					sb.append(MonitorDePID.idioma.mensajeDependenciaModFaltante(mod, dep, "mínima " + min))
					  .append(" ").append(enlace).append("<br><br>");

				} else if (ver.startsWith("requiere ")) {
					sb.append(MonitorDePID.idioma.mensajeDependenciaModFaltante(mod, dep, ver))
					  .append(" ").append(enlace).append("<br><br>");

				} else if (ver.startsWith("no encontrada")) {
					sb.append(MonitorDePID.idioma.mensajeDependenciaModFaltante(mod, dep, "no encontrada"))
					  .append(" ").append(enlace).append("<br><br>");

				} else {
					// Caso genérico
					sb.append(MonitorDePID.idioma.mensajeDependenciaModFaltante(mod, dep, ver))
					  .append(" ").append(enlace).append("<br><br>");
				}
			}

			this.mensaje = sb.toString().replaceAll("<br><br>$", "");
			activado = true;
		}
	}

	/**
	 * Inserta un problema si no existe ya (deduplicación por clave).
	 * @return true si se insertó; false si era duplicado.
	 */
	private boolean agregarProblema(String mod, String dep, String ver, String enlace) {
		String clave = (String.valueOf(mod) + "|" + String.valueOf(dep) + "|" + String.valueOf(ver))
				.toLowerCase(Locale.ROOT);
		if (clavesUnicas.add(clave)) {
			nombresMods.add(mod);
			dependencias.add(dep);
			versiones.add(ver);
			enlaces.add(enlace != null ? enlace : "");
			return true;
		}
		return false;
	}

	/**
	 * Limpia caracteres de color/estilo y símbolos raros para poder hacer matching.
	 */
	private static String limpiarLinea(String s) {
		if (s == null) return "";
		String out = s;

		// Códigos de color §x
		out = out.replaceAll("[\\u00A7][0-9A-FK-ORa-fk-or]", "");

		// Pares "�X" (códigos de formato rotos)
		out = out.replaceAll("\\uFFFD.", "");

		// Normalizar separadores "∙" -> "."
		out = out.replace('∙', '.');

		// Comillas estilizadas -> normales
		out = out.replace('’', '\'').replace('‘', '\'').replace('“', '"').replace('”', '"');

		// Colapsar dobles espacios
		out = out.replaceAll("\\s{2,}", " ").trim();

		return out;
	}

	/**
	 * Normaliza una cadena de versión (limpieza básica).
	 */
	private static String normalizarVersion(String v) {
		if (v == null) return "";
		return limpiarLinea(v).trim();
	}

	/** Nueva instancia del verificador. */
	@Override
	public Verificaciones nueva() {
		return new ProblemaDependenciaModFabric();
	}

	/** Indica si el problema fue detectado. */
	@Override
	public boolean activado() {
		return activado;
	}

	/** Prioridad del problema (alta). */
	@Override
	public float prioridad() {
		return 1100.0f;
	}

	/** Devuelve el mensaje de error almacenado. */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/** Nombre del problema para la interfaz. */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaDependenciaMod();
	}

	/** Devuelve las soluciones posibles para este problema. */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		for (int i = 0; i < dependencias.size(); i++) {
			String dep = dependencias.get(i);
			String ver = versiones.get(i);

			if (ver.startsWith("rango:")) {
				String[] partes = ver.split(":");
				String min = partes.length > 1 ? partes[1] : "";
				String max = partes.length > 2 ? partes[2] : "";
				String actual = partes.length > 3 ? partes[3] : "";

				String etiqueta = "Reemplazar/ajustar \"" + dep + "\" a una versión entre "
						+ min + " y " + max + " (exclusivo).";
				if (!actual.isEmpty()) {
					etiqueta += " Versión actual detectada: " + actual + ".";
				}
				builder.agregarEtiqueta(etiqueta);

			} else if (ver.startsWith("mínima ")) {
				String versionMinima = ver.substring("mínima ".length()).trim();
				builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarModConVersion(dep, versionMinima));

			} else if (ver.startsWith("no encontrada")) {
				builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(dep));

			} else if (ver.startsWith("requiere ")) {
				String versionRequerida = ver.replaceFirst("^requiere\\s+", "").trim();
				builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarModConVersion(dep, versionRequerida));

			} else {
				builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(dep));
			}
		}

		return builder.construir();
	}

	@Override
	public String id() {
		return "dependencia_mod_fabric";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false; // no consumimos trazos específicos
	}
}
