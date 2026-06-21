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
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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

	@Override
	public String[] patronesRapidos() {
		return new String[] { "Missing or unsupported mandatory dependencies:", "Failure message: Mod ",
				"requires any version of", "which is missing", "only supports", "Currently,",
				"wrong version is present", "Remove Iris/Oculus & GeckoLib Compat", "geckoanimfix" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
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

	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		String lineaActual = linea.trim();

		// NUEVO: formato moderno de dependencia faltante con "which is missing!"
		// Ejemplo:
		// - §Emod Something (modid)§r 1.0.0 requires any version of §6dependency§r,
		// which is missing!

		if (lineaActual.contains("requires any version of") && lineaActual.contains("which is missing")) {

			try {

				String limpia = limpiarFormato(lineaActual);

				// Extraer mod principal entre paréntesis
				int inicioMod = limpia.indexOf("(");
				int finMod = limpia.indexOf(")", inicioMod);

				String modId = "desconocido";

				if (inicioMod != -1 && finMod != -1) {
					modId = limpia.substring(inicioMod + 1, finMod).trim();
				}

				// Extraer dependencia requerida
				int inicioDep = limpia.indexOf("requires any version of") + "requires any version of".length();
				int finDep = limpia.indexOf(",", inicioDep);

				String dependencia = limpia.substring(inicioDep, finDep).trim();

				String mensaje = MonitorDePID.idioma.errorDependenciaNoInstalada(modId, dependencia, "any");

				if (errores.add(mensaje)) {

					String enlace = consola.agregarErrorALectador(numero_de_linea, this);
					enlacesPorError.put(mensaje, enlace);
					activado = true;

				}

			} catch (Exception e) {

				consola.agregarErrorALectador(numero_de_linea, this);

			}

			return;
		}

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

			String[] lineasConsola = consola.lineas_verificar;

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

		} // Formato moderno de dependencia con versión incorrecta.
			// Ejemplo:
			// - §Emod Geckolib (geckolib3)§r 3.0.42 requires any 1.16.x version of
			// §6mod Minecraft (minecraft)§r, but only the wrong version is present: 1.20.1!
		if (lineaActual.contains("requires any ") && lineaActual.contains(" version of ")
				&& lineaActual.contains("wrong version is present")) {

			procesarFormatoVersionEquivocada(consola, lineaActual, numero_de_linea);
			return;
		}

	}

	private void procesarFormatoVersionEquivocada(Consola consola, String lineaActual, int numero_de_linea) {
		try {
			String limpia = limpiarFormato(lineaActual);

			// Extraer el mod que tiene el problema.
			String modId = extraerPrimerTextoEntreParentesis(limpia, 0);
			if (modId.isEmpty()) {
				modId = "desconocido";
			}

			// Extraer la versión requerida.
			String claveInicioVersion = "requires any ";
			String claveFinVersion = " version of ";

			int inicioVersion = limpia.indexOf(claveInicioVersion);
			int finVersion = limpia.indexOf(claveFinVersion, inicioVersion);

			String versionRequerida = "desconocida";

			if (inicioVersion != -1 && finVersion != -1) {
				versionRequerida = limpia.substring(inicioVersion + claveInicioVersion.length(), finVersion).trim();
			}

			// Extraer la dependencia. Normalmente está entre paréntesis después de "version
			// of".
			String dependencia = "desconocida";

			int inicioDependencia = finVersion == -1 ? -1 : finVersion + claveFinVersion.length();

			if (inicioDependencia != -1 && inicioDependencia < limpia.length()) {
				String depEntreParentesis = extraerPrimerTextoEntreParentesis(limpia, inicioDependencia);

				if (!depEntreParentesis.isEmpty()) {
					dependencia = depEntreParentesis;
				} else {
					int finDependencia = limpia.indexOf(",", inicioDependencia);
					if (finDependencia == -1) {
						finDependencia = limpia.length();
					}

					dependencia = limpia.substring(inicioDependencia, finDependencia).trim();
				}
			}

			// Extraer la versión presente.
			String versionActual = "desconocida";

			String claveVersionActual = "present:";
			int inicioActual = limpia.indexOf(claveVersionActual);

			if (inicioActual != -1) {
				inicioActual += claveVersionActual.length();

				int finActual = limpia.indexOf("!", inicioActual);
				if (finActual == -1) {
					finActual = limpia.length();
				}

				versionActual = limpia.substring(inicioActual, finActual).trim();
			}

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

	private String extraerPrimerTextoEntreParentesis(String texto, int desde) {
		if (texto == null || desde < 0 || desde >= texto.length()) {
			return "";
		}

		int inicio = texto.indexOf("(", desde);
		if (inicio == -1) {
			return "";
		}

		int fin = texto.indexOf(")", inicio + 1);
		if (fin == -1) {
			return "";
		}

		return texto.substring(inicio + 1, fin).trim();
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

		String[] lineasConsola = consola.lineas_verificar;

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
			String[] lineasConsola = consola.lineas_verificar;

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
		if (texto == null || texto.indexOf('§') == -1) {
			return texto;
		}

		StringBuilder sb = new StringBuilder(texto.length());

		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);

			if (c == '§' && i + 1 < texto.length()) {
				i++;
				continue;
			}

			sb.append(c);
		}

		return sb.toString();
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

			String[] lineasConsola = consola.lineas_verificar;
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

		return 1500f;

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

		if (errores.isEmpty()) {
			return "";
		}

		CDStringBuilder html = new CDStringBuilder();

		// Título principal del bloque
		html.append(MonitorDePID.idioma.no_tienes_las_dependencias_necesarias());

		// Abrir la lista
		html.append("<ul>");

		for (String error : errores) {

			if (error == null || error.isEmpty()) {
				continue;
			}

			String enlace = enlacesPorError.get(error);

			// Asegurar que nunca sea null
			if (enlace == null) {
				enlace = "";
			}

			// Construir cada elemento completo de una sola vez para evitar
			// que el HTML quede partido o mal anidado.
			String itemHtml = "<li>" + error + (enlace.isEmpty() ? "" : " " + enlace) + "</li>";

			html.append(itemHtml);
		}

		// Cerrar la lista
		html.append("</ul>");

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
		return QuickFix.NINGUN;// TODO
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
	 * Devuelve los patrones que identifican este error en un stacktrace.
	 *
	 * @return Array con los patrones de dependencia.
	 *
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { "Missing or unsupported mandatory dependencies:" };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}