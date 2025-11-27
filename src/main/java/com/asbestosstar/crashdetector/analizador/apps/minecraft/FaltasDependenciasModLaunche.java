package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.HashSet;

import java.util.List;

import java.util.Map;

import java.util.Set;

import com.asbestosstar.crashdetector.CDStringBuilder;

import com.asbestosstar.crashdetector.Consola;

import com.asbestosstar.crashdetector.MonitorDePID;

import com.asbestosstar.crashdetector.analizador.QuickFix;

import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * 
 * Clase de verificación que analiza la salida de la consola de Minecraft para
 * 
 * detectar errores de dependencias de mods, específicamente los generados por
 * 
 * ModLauncher.
 *
 * 
 * 
 * Esta clase está diseñada para manejar múltiples formatos de mensajes de
 * error,
 * 
 * tanto antiguos como nuevos, y extraer la información relevante (ID del mod,
 * 
 * dependencia faltante, versión requerida y versión actual) para generar un
 * 
 * informe claro y útil para el usuario.
 * 
 */

public class FaltasDependenciasModLaunche implements Verificaciones {

	/**
	 * 
	 * Indica si se ha encontrado al menos un error de dependencia.
	 * 
	 */

	private boolean activado = false;

	/**
	 * 
	 * Conjunto de mensajes de error únicos para evitar duplicados.
	 * 
	 */

	private final Set<String> errores = new HashSet<>();

	/**
	 * 
	 * Mapa que asocia cada mensaje de error con un enlace para ver más detalles
	 * 
	 * en la consola.
	 * 
	 */

	private final Map<String, String> enlacesPorError = new HashMap<>();

	/**
	 * 
	 * Almacena las líneas de la consola para poder acceder a líneas posteriores
	 * 
	 * a la actual durante el análisis.
	 * 
	 */

	private String[] lineasConsola = null;

	/**
	 * 
	 * Método de verificación principal que se deja vacío intencionadamente.
	 * 
	 * Toda la lógica de análisis se ha centralizado en el método
	 * 
	 * {@link #verificar(Consola, String, int)} para un procesamiento más
	 * 
	 * eficiente línea por línea.
	 *
	 * 
	 * 
	 * @param consola La consola a verificar.
	 * 
	 */

	@Override

	public void verificar(Consola consola) {

		// Este método se deja vacío, toda la lógica está en verificar(Consola, String,
		// int)

	}

	/**
	 * 
	 * Analiza cada línea de la consola para detectar patrones de error de
	 * 
	 * dependencias.
	 *
	 * 
	 * 
	 * @param consola         La consola que contiene la salida del juego.
	 * 
	 * @param linea           La línea actual que se está analizando.
	 * 
	 * @param numero_de_linea El número de línea actual en la consola.
	 * 
	 */

	@Override

	public void verificar(Consola consola, String linea, int numero_de_linea) {

		// Si el array de líneas no ha sido inicializado, se divide el contenido total.

		if (lineasConsola == null) {

			lineasConsola = consola.contenido_verificar.split(Verificaciones.nl);

		}

		String lineaActual = linea.trim();

		// NUEVO: Detectar el formato especial con versión larga y texto adicional

		// Ejemplo: "Mod 'examplemod' only supports version 1.2.0 or above. Remove
		// 'examplemod'."

		if (lineaActual.contains("only supports") && lineaActual.contains("or above")

				&& lineaActual.contains(".Remove ")) {

			procesarFormatoEspecial(consola, lineaActual, numero_de_linea);

			return;

		}

		// NUEVO: Detectar el formato "-- MOD modid --"

		// Este es un marcador que precede a los detalles de un mod. La lógica para

		// procesar los detalles se manejará en los métodos de abajo.

		if (lineaActual.startsWith("-- MOD ") && lineaActual.endsWith(" --")) {

			// Preparar para procesar las líneas de detalles del mod

			// La lógica real está en los métodos que buscan "Failure message"

			return;

		}

		// NUEVO: Detectar formato de "Failure message" para los nuevos estilos de
		// error.

		// Ejemplo: "Failure message: Mod 'requiem' requires 'geckolib' version 3.0.32
		// or above"

		if (lineaActual.contains("Failure message: Mod ")

				&& (lineaActual.contains(" requires ") || lineaActual.contains(" only supports "))) {

			procesarNuevoFormatoDependencia(consola, lineaActual, numero_de_linea);

			return;

		}

		// Formato antiguo de error de dependencia.

		// Ejemplo: "Missing or unsupported mandatory dependencies:"

		if (lineaActual.contains("Missing or unsupported mandatory dependencies:")) {

			int i = numero_de_linea;

			// Itera sobre las siguientes líneas para encontrar los mods con dependencias
			// faltantes.

			for (int j = i + 1; j < lineasConsola.length; j++) {

				String lineaDep = lineasConsola[j].trim();

				// El bucle termina si se encuentra una línea vacía o que empieza con
				// "Currently,".

				if (lineaDep.startsWith("Currently,") || lineaDep.isEmpty())

					break;

				// Busca la línea que detalla la dependencia.

				if (lineaDep.contains("Mod ID") && lineaDep.contains("Requested by")) {

					String mensaje = MonitorDePID.idioma.linea_de_dependencia(lineaDep);

					// Si el mensaje es nuevo, lo añade al conjunto de errores.

					if (errores.add(mensaje)) {

						String enlace = consola.agregarErrorALectador(j, this);

						enlacesPorError.put(mensaje, enlace);

					}

					// Marca la verificación como activada.

					activado = true;

				}

			}

		}

	}

	/**
	 * 
	 * Procesa el nuevo formato de mensaje de error que puede contener múltiples
	 * 
	 * dependencias en una sola línea.
	 *
	 * 
	 * 
	 * @param consola         La consola actual.
	 * 
	 * @param lineaActual     La línea que contiene el mensaje de error.
	 * 
	 * @param numero_de_linea El número de la línea actual.
	 * 
	 */

	private void procesarNuevoFormatoDependencia(Consola consola, String lineaActual, int numero_de_linea) {

		try {

			// Caso especial para el mod 'superbwarfare' que tiene un mensaje de error
			// único.

			if (lineaActual.contains("Remove Iris/Oculus & GeckoLib Compat")) {

				procesarFormatoSuperbWarfare(consola, lineaActual, numero_de_linea);

				return;

			}

			// Extraer el ID del mod que está fallando.

			String modId = extraerModIdNuevoFormato(lineaActual);

			// Verificar si la línea contiene múltiples dependencias (separadas por coma o
			// "and").

			if (lineaActual.contains(",") || lineaActual.contains(" and ")) {

				List<String[]> dependencias = extraerMultiplesDependencias(lineaActual);

				// Procesar cada dependencia encontrada.

				for (String[] dependenciaInfo : dependencias) {

					String dependencia = dependenciaInfo[0];

					String versionRequerida = dependenciaInfo[1];

					// Buscar información adicional (versión actual) en las siguientes líneas.

					String versionActual = "no encontrada";

					for (int i = 1; i <= 3; i++) {

						if (numero_de_linea + i < lineasConsola.length) {

							String lineaSiguiente = lineasConsola[numero_de_linea + i].trim();

							// La información de la versión actual suele estar en una línea que empieza con
							// "Currently,".

							if (lineaSiguiente.startsWith("Currently,") && lineaSiguiente.contains(dependencia)) {

								versionActual = extraerVersionActualNuevoFormato(lineaSiguiente);

								break;

							}

						}

					}

					// Crear el mensaje de error basado en la información extraída.

					String mensaje;

					if (versionRequerida.isEmpty() || versionRequerida.equals("any")) {

						// Caso simple: solo requiere el mod, sin versión específica.

						mensaje = MonitorDePID.idioma.errorDependenciaSimple(modId, dependencia, versionActual);

					} else if (versionActual.contains("not installed")) {

						// Caso: la dependencia no está instalada.

						mensaje = MonitorDePID.idioma.errorDependenciaNoInstalada(modId, dependencia, versionRequerida);

					} else {

						// Caso: la versión de la dependencia es incorrecta.

						mensaje = MonitorDePID.idioma.errorVersionDependencia(modId, dependencia, versionRequerida,

								versionActual);

					}

					// Añadir el error si es nuevo.

					if (errores.add(mensaje)) {

						String enlace = consola.agregarErrorALectador(numero_de_linea, this);

						enlacesPorError.put(mensaje, enlace);

						activado = true;

					}

				}

			} else {

				// Manejar el caso de una sola dependencia (lógica similar a la anterior pero
				// más simple).

				String dependencia = extraerDependenciaNuevoFormato(lineaActual);

				String versionRequerida = extraerVersionRequeridaNuevoFormato(lineaActual);

				// Buscar la versión actual en las líneas siguientes.

				String versionActual = "no encontrada";

				for (int i = 1; i <= 3; i++) {

					if (numero_de_linea + i < lineasConsola.length) {

						String lineaSiguiente = lineasConsola[numero_de_linea + i].trim();

						if (lineaSiguiente.startsWith("Currently,")) {

							versionActual = extraerVersionActualNuevoFormato(lineaSiguiente);

							break;

						}

					}

				}

				// Crear el mensaje de error.

				String mensaje;

				if (versionRequerida.isEmpty() || versionRequerida.equals("any")) {

					mensaje = MonitorDePID.idioma.errorDependenciaSimple(modId, dependencia, versionActual);

				} else if (versionActual.contains("not installed")) {

					mensaje = MonitorDePID.idioma.errorDependenciaNoInstalada(modId, dependencia, versionRequerida);

				} else {

					mensaje = MonitorDePID.idioma.errorVersionDependencia(modId, dependencia, versionRequerida,

							versionActual);

				}

				// Añadir el error si es nuevo.

				if (errores.add(mensaje)) {

					String enlace = consola.agregarErrorALectador(numero_de_linea, this);

					enlacesPorError.put(mensaje, enlace);

					activado = true;

				}

			}

		} catch (Exception e) {

			// En caso de un error de parseo, se marca la línea como error para revisión
			// manual.

			consola.agregarErrorALectador(numero_de_linea, this);

		}

	}

	/**
	 * 
	 * Procesa un caso de error específico para el mod 'superbwarfare'.
	 * 
	 */

	private void procesarFormatoSuperbWarfare(Consola consola, String lineaActual, int numero_de_linea) {

		try {

			String modId = "superbwarfare";

			String dependencia = "geckoanimfix"; // La dependencia es fija en este caso.

			// Buscar la versión actual de 'geckoanimfix' en las líneas siguientes.

			String versionActual = "desconocida";

			for (int i = 1; i <= 3; i++) {

				if (numero_de_linea + i < lineasConsola.length) {

					String lineaSiguiente = lineasConsola[numero_de_linea + i].trim();

					if (lineaSiguiente.contains("Currently,") && lineaSiguiente.contains("geckoanimfix")) {

						// Extraer la versión de la línea, manejando diferentes formatos.

						if (lineaSiguiente.contains("is §o")) {

							versionActual = lineaSiguiente.substring(lineaSiguiente.indexOf("is §o") + 5).trim();

						} else if (lineaSiguiente.contains("is ")) {

							versionActual = lineaSiguiente.substring(lineaSiguiente.indexOf("is ") + 3).trim();

						}

						break;

					}

				}

			}

			// Crear un mensaje de error especializado para este caso.

			String mensaje = MonitorDePID.idioma.errorSuperbWarfareIncompatible(modId, dependencia, versionActual);

			if (errores.add(mensaje)) {

				String enlace = consola.agregarErrorALectador(numero_de_linea, this);

				enlacesPorError.put(mensaje, enlace);

				activado = true;

			}

		} catch (Exception e) {

			consola.agregarErrorALectador(numero_de_linea, this);

		}

	}

	/**
	 * 
	 * Extrae múltiples dependencias de una sola línea, manejando separadores
	 * 
	 * por comas y la palabra "and".
	 *
	 * 
	 * 
	 * @param linea La línea que contiene las dependencias.
	 * 
	 * @return Una lista de arrays, donde cada array contiene el nombre de la
	 * 
	 *         dependencia y su versión requerida.
	 * 
	 */

	private List<String[]> extraerMultiplesDependencias(String linea) {

		List<String[]> dependencias = new ArrayList<>();

		// Extraer la parte de la línea que contiene las dependencias.

		String parteDependencias = "";

		if (linea.contains(" requires ")) {

			parteDependencias = linea.substring(linea.indexOf(" requires ") + 10);

		} else if (linea.contains(" only supports ")) {

			parteDependencias = linea.substring(linea.indexOf(" only supports ") + 16);

		}

		// Limpiar texto sobrante como " or above" o " and below".

		if (parteDependencias.contains(" or above")) {

			parteDependencias = parteDependencias.substring(0, parteDependencias.indexOf(" or above"));

		}

		if (parteDependencias.contains(" and below")) {

			parteDependencias = parteDependencias.substring(0, parteDependencias.indexOf(" and below"));

		}

		// Reemplazar "and" con coma para unificar el formato y facilitar el split.

		parteDependencias = parteDependencias.replace(" and ", ", ");

		// Dividir la cadena por comas para obtener cada dependencia.

		String[] partes = parteDependencias.split(",");

		for (String parte : partes) {

			String dependenciaLimpia = parte.trim();

			// Si la dependencia tiene una versión especificada (contiene un espacio).

			if (dependenciaLimpia.contains(" ")) {

				String[] partesDep = dependenciaLimpia.split(" ", 2);

				String nombreDep = partesDep[0].trim();

				String versionDep = partesDep[1].trim();

				dependencias.add(new String[] { nombreDep, versionDep });

			} else {

				// Si no se especifica versión.

				dependencias.add(new String[] { dependenciaLimpia, "" });

			}

		}

		return dependencias;

	}

	// --- MÉTODOS DE PARSEO PARA EL NUEVO FORMATO ---

	/**
	 * 
	 * Extrae el ID del mod de una línea de error.
	 * 
	 * Ejemplo: "Failure message: Mod 'requiem' requires..." -> "requiem"
	 * 
	 */

	private String extraerModIdNuevoFormato(String linea) {

		int inicio = linea.indexOf("Mod ") + 4;

		int fin = linea.indexOf(" ", inicio);

		return limpiarFormato(linea.substring(inicio, fin));

	}

	/**
	 * 
	 * Extrae el nombre de la dependencia de una línea de error.
	 * 
	 */

	private String extraerDependenciaNuevoFormato(String linea) {

		int inicio = linea.contains(" requires ") ? linea.indexOf(" requires ") + 10

				: linea.indexOf(" only supports ") + 16;

		int fin = linea.indexOf(" ", inicio);

		if (fin == -1 || fin < inicio)

			fin = linea.length();

		return limpiarFormato(linea.substring(inicio, fin));

	}

	/**
	 * 
	 * Extrae la versión requerida de la dependencia.
	 * 
	 */

	private String extraerVersionRequeridaNuevoFormato(String linea) {

		int inicioVersion = linea.contains(" requires ") ? linea.indexOf(" requires ") + 10

				: linea.indexOf(" only supports ") + 16;

		int finMod = linea.indexOf(" ", inicioVersion);

		if (finMod == -1)

			return "";

		int inicio = linea.indexOf(" ", finMod) + 1;

		int fin = linea.indexOf(" or above");

		if (fin == -1)

			fin = linea.indexOf(" and below");

		if (fin == -1)

			fin = linea.length();

		String resultado = linea.substring(inicio, fin).trim();

		// Si hay múltiples versiones (separadas por coma), tomar solo la primera.

		if (resultado.contains(",")) {

			resultado = resultado.substring(0, resultado.indexOf(",")).trim();

		}

		if (resultado.contains(" and ")) {

			resultado = resultado.substring(0, resultado.indexOf(" and ")).trim();

		}

		return resultado;

	}

	/**
	 * 
	 * Extrae la versión actual de una dependencia desde una línea "Currently,".
	 * 
	 */

	private String extraerVersionActualNuevoFormato(String linea) {

		int inicio = linea.indexOf("is ") + 3;

		if (inicio < 3) {

			inicio = linea.indexOf("not installed") > 0 ? linea.indexOf("not installed") : linea.length();

		}

		return inicio < linea.length() ? limpiarFormato(linea.substring(inicio)) : "no especificada";

	}

	// --- MÉTODOS DE PARSEO EXISTENTES (PARA COMPATIBILIDAD CON FORMATOS ANTIGUOS)
	// ---

	private String extraerModId(String linea) {

		int inicio = linea.indexOf("Mod ") + 4;

		int fin = linea.indexOf(" only supports");

		if (fin == -1) {

			fin = linea.indexOf(" requires");

		}

		return limpiarFormato(linea.substring(inicio, fin));

	}

	private String extraerDependencia(String linea) {

		String clave;

		if (linea.contains("only supports")) {

			clave = "supports ";

		} else {

			clave = "requires ";

		}

		int inicio = linea.indexOf(clave) + clave.length();

		int fin = linea.indexOf(" ", inicio);

		return limpiarFormato(linea.substring(inicio, fin));

	}

	private String extraerVersionRequerida(String linea) {

		String clave;

		if (linea.contains("only supports")) {

			clave = "supports ";

		} else {

			clave = "requires ";

		}

		int inicio = linea.indexOf(clave) + clave.length();

		int fin = linea.indexOf(" or above");

		return limpiarFormato(linea.substring(inicio, fin));

	}

	private String extraerVersionActual(String linea, String dependencia) {

		int inicio = linea.indexOf("is ") + 3;

		return limpiarFormato(linea.substring(inicio));

	}

	/**
	 * 
	 * Limpia los códigos de formato de Minecraft (§) de una cadena de texto.
	 *
	 * 
	 * 
	 * @param texto El texto a limpiar.
	 * 
	 * @return El texto sin códigos de formato.
	 * 
	 */

	private String limpiarFormato(String texto) {

		return texto.replaceAll("§[a-zA-Z0-9]", "");

	}

	/**
	 * 
	 * Procesa un formato de error especial y más antiguo.
	 * 
	 */

	private void procesarFormatoEspecial(Consola consola, String lineaActual, int numero_de_linea) {

		try {

			String lineaLimpia = limpiarFormato(lineaActual);

			// Extraer mod ID

			String modId = lineaLimpia.substring(lineaLimpia.indexOf("Mod ") + 4, lineaLimpia.indexOf(" only supports"))

					.trim();

			// Extraer dependencia

			String dependencia = lineaLimpia.substring(lineaLimpia.indexOf("supports ") + 9,

					lineaLimpia.indexOf(" ", lineaLimpia.indexOf("supports ") + 9)).trim();

			// Extraer versión requerida

			String versionRequerida = lineaLimpia.substring(lineaLimpia.indexOf(dependencia) + dependencia.length(),

					lineaLimpia.indexOf(" or above")).trim();

			// Buscar versión actual en las siguientes líneas

			String versionActual = "no encontrada";

			for (int i = 1; i <= 3; i++) {

				if (numero_de_linea + i < lineasConsola.length) {

					String lineaSiguiente = limpiarFormato(lineasConsola[numero_de_linea + i].trim());

					if (lineaSiguiente.startsWith("Currently,") && lineaSiguiente.contains(dependencia)) {

						versionActual = lineaSiguiente.substring(lineaSiguiente.indexOf("is ") + 3).trim();

						break;

					}

				}

			}

			// Crear mensaje de error

			String mensaje = MonitorDePID.idioma.errorVersionDependencia(modId, dependencia, versionRequerida,

					versionActual);

			if (errores.add(mensaje)) {

				String enlace = consola.agregarErrorALectador(numero_de_linea, this);

				enlacesPorError.put(mensaje, enlace);

				activado = true;

			}

		} catch (Exception e) {

			consola.agregarErrorALectador(numero_de_linea, this);

		}

	}

	/**
	 * 
	 * Crea una nueva instancia de esta clase de verificación.
	 *
	 * 
	 * 
	 * @return Una nueva instancia de FaltasDependenciasModLaunche.
	 * 
	 */

	@Override

	public Verificaciones nueva() {

		return new FaltasDependenciasModLaunche();

	}

	/**
	 * 
	 * Devuelve si se encontró algún error de dependencia.
	 *
	 * 
	 * 
	 * @return true si se activó la verificación, false en caso contrario.
	 * 
	 */

	@Override

	public boolean activado() {

		return activado;

	}

	/**
	 * 
	 * Devuelve la prioridad de esta verificación. Un valor más alto significa
	 * 
	 * que se ejecuta antes.
	 *
	 * 
	 * 
	 * @return La prioridad de la verificación (1100f).
	 * 
	 */

	@Override

	public float prioridad() {

		return 1100f;

	}

	/**
	 * 
	 * Construye el mensaje final HTML que se mostrará al usuario, listando
	 * 
	 * todos los errores de dependencias encontrados.
	 *
	 * 
	 * 
	 * @return Un string HTML con la lista de errores.
	 * 
	 */

	@Override
	public String mensaje() {

		if (errores.isEmpty())

			return "";

		CDStringBuilder html = new CDStringBuilder();

		// Añade el título de la sección de errores.

		html.append(MonitorDePID.idioma.no_tienes_las_dependencias_necesarias());

		// Inicia la lista no ordenada (unordered list).

		// Itera sobre los errores y añade cada uno como un elemento de lista (<li>).

		for (String error : errores) {

			String cleanError = error;// .trim().replace("\t", " ").replaceAll("\\s+", " ");

			// Evita añadir el mismo error varias veces si aparece en diferentes formatos.

			if (!html.toString().contains(cleanError)) {

				String enlace = enlacesPorError.getOrDefault(cleanError, "");

				// Inicia un nuevo elemento de lista, añade el texto del error y el enlace.

				// El navegador o la interfaz de usuario se encargará de formatear el salto de
				// línea.

				html.append("•").append(cleanError).append(" ").append(enlace);
				if (!html.toString().endsWith(nl_html)) {
					html.append(nl_html);
				}
			}

		}

		return html.toString();

	}

	/**
	 * 
	 * Devuelve el nombre de esta verificación, traducido según el idioma
	 * 
	 * configurado.
	 *
	 * 
	 * 
	 * @return El nombre de la verificación.
	 * 
	 */

	@Override

	public String nombre() {

		return MonitorDePID.idioma.nombre_de_faltas_dependencias_de_modlauncher();

	}

	/**
	 * 
	 * Define una "solución rápida" (QuickFix). Para este caso, no hay una
	 * 
	 * solución automática, por lo que se informa al usuario.
	 *
	 * 
	 * 
	 * @return Un objeto QuickFix indicando que no hay solución disponible.
	 * 
	 */

	@Override

	public QuickFix solucion() {

		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())

				.construir();

	}

	/**
	 * 
	 * Devuelve el identificador único de esta verificación.
	 *
	 * 
	 * 
	 * @return El ID "faltas_dependencias_modlauncher".
	 * 
	 */

	@Override

	public String id() {

		return "faltas_dependencias_modlauncher";

	}

	/**
	 * 
	 * Verifica si un "trazo" (trace) de la pila de errores contiene alguna de
	 * 
	 * las cadenas de error de dependencias que hemos encontrado.
	 *
	 * 
	 * 
	 * @param trazo El TraceInfo a verificar.
	 * 
	 * @return true si el trazo contiene un error de dependencia conocido.
	 * 
	 */

	@Override

	public boolean ocupaTrazo(TraceInfo trazo) {

		if (!activado || trazo == null || trazo.trace == null) {

			return false;

		}

		String contenido = trazo.trace;

		// Comprueba si el contenido del trazo contiene alguno de los mensajes de error
		// ya guardados.

		for (String error : errores) {

			if (error != null && !error.isEmpty()) {

				String trim = error.trim();

				if (!trim.isEmpty() && contenido.contains(trim)) {

					return true;

				}

			}

		}

		// NUEVO: Añadir detección para los nuevos formatos directamente en el trazo.

		// Esto ayuda a identificar la causa raíz incluso si el error no se procesó

		// en la verificación inicial de la consola.

		if (contenido.contains("Failure message: Mod ")

				&& (contenido.contains(" requires ") || contenido.contains(" only supports "))

				&& contenido.contains("Currently,")) {

			return true;

		}

		if (contenido.contains("Missing or unsupported mandatory dependencies:")

				|| (contenido.contains("only supports") && contenido.contains("or above"))

				|| (contenido.contains("requires") && contenido.contains("or above")

						&& contenido.contains("Currently,"))) {

			return true;

		}

		return false;

	}

}