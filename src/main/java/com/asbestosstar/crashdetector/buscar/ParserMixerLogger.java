// ============================================================================
// ARCHIVO NUEVO: com/asbestosstar/crashdetector/util/ParserMixerLogger.java
// ============================================================================
package com.asbestosstar.crashdetector.buscar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.InfoCampo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.InfoMetodo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinCampoInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.MixinMetodoInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod.Referencia;

/**
 * Parser especializado para leer y procesar logs generados por MixerLoggerMain.
 * 
 * <p>
 * Responsabilidades:
 * </p>
 * <ul>
 * <li>Buscar el archivo MixerLogger.log en las consolas de MonitorDePID</li>
 * <li>Parsear el formato parsable Key: Value con listas [a,b] y metadata
 * {key:val}</li>
 * <li>Construir objetos MixinInfo, InfoMetodo, InfoCampo, Referencia</li>
 * <li>Almacenar resultados en cache estático para consulta rápida</li>
 * </ul>
 * 
 * <p>
 * Formato esperado del log:
 * </p>
 * 
 * <pre>
 * Class Name: net.example.Mixin
 * Targets: [net.minecraft.block.CropBlock]
 * ClassRef: [java.lang.Object]
 * MethodRef: [net.minecraft.util.MathHelper.clamp(DDD)D]
 * FieldRef: [net.minecraft.block.Block.stateId:I]
 * Mixin Methods: [injectMethod(Ljava/lang/Object;)V{targets:[target]}{refmap:m_1234_}]
 * Mixin Fields: [shadowField:I{refmap:f_5678_}]
 * Inject Targets: [target(Ljava/lang/Object;)V{refmap:m_1234_}]
 * Originating JAR: mod-file.jar
 * --------------------
 * </pre>
 */
public class ParserMixerLogger {

	// ========================================================================
	// CACHE ESTÁTICO PARA RESULTADOS PARSEADOS
	// ========================================================================

	/**
	 * Cache centralizado para todos los datos parseados desde MixerLogger. Claves
	 * en formato punto (ej: "net.example.Mixin") para consistencia con Java.
	 */
	private static final class Cache {
		static final Map<String, MixinInfo> mixinsPorClase = new HashMap<>();
		static final Map<String, List<InfoMetodo>> metodosPorClase = new HashMap<>();
		static final Map<String, List<InfoCampo>> camposPorClase = new HashMap<>();
		static final Map<String, List<Referencia>> referenciasPorClase = new HashMap<>();
		static boolean cargado = false;

		static void limpiar() {
			mixinsPorClase.clear();
			metodosPorClase.clear();
			camposPorClase.clear();
			referenciasPorClase.clear();
			cargado = false;
		}
	}

	// ========================================================================
	// MÉTODO PRINCIPAL: BUSCAR Y CARGAR DESDE CONSOLAS
	// ========================================================================

	/**
	 * Busca el archivo MixerLogger.log en las consolas de MonitorDePID, lo parsea y
	 * carga todos los datos en cache.
	 * 
	 * <p>
	 * Este método debe llamarse desde un contexto donde MonitorDePID.consolas esté
	 * disponible (ej: dentro de CrashDetector o un método estático de utilidad).
	 * </p>
	 * 
	 * @return true si se encontró y procesó un log, false si no hay log disponible
	 */
	public static boolean cargarDesdeConsolas() {
		if (Cache.cargado) {
			CrashDetectorLogger.log("[ParserMixerLogger] Cache ya cargado, omitiendo");
			return true;
		}

		// Buscar el archivo MixerLogger.log en las consolas
		Path rutaLog = buscarArchivoEnConsolas("MixerLogger.log");
		if (rutaLog == null) {
			CrashDetectorLogger.log("[ParserMixerLogger] No se encontró MixerLogger.log en las consolas");
			return false;
		}

		CrashDetectorLogger.log("[ParserMixerLogger] Leyendo log desde: " + rutaLog);
		return procesarArchivoLog(rutaLog.toFile());
	}

	/**
	 * Busca un archivo específico en todas las consolas de MonitorDePID.
	 * 
	 * @param nombreArchivo Nombre del archivo a buscar (ej: "MixerLogger.log")
	 * @return Path del archivo si se encuentra, null en caso contrario
	 */
	private static Path buscarArchivoEnConsolas(String nombreArchivo) {
		try {
			// Acceder a MonitorDePID.consolas vía reflexión para evitar dependencia
			// circular
			Class<?> claseMonitor = Class.forName("com.asbestosstar.crashdetector.MonitorDePID");
			java.lang.reflect.Field fieldConsolas = claseMonitor.getDeclaredField("consolas");
			fieldConsolas.setAccessible(true);
			@SuppressWarnings("unchecked")
			List<Object> consolas = (List<Object>) fieldConsolas.get(null);

			if (consolas == null)
				return null;

			for (Object consolaObj : consolas) {
				if (consolaObj == null)
					continue;

				// Obtener el campo 'archivo' de la Consola
				java.lang.reflect.Field fieldArchivo = consolaObj.getClass().getDeclaredField("archivo");
				fieldArchivo.setAccessible(true);
				Path archivo = (Path) fieldArchivo.get(consolaObj);

				if (archivo != null) {
					File file = archivo.toFile();
					// Verificar si es el archivo exacto o está en un subdirectorio logs/
					if (file.getName().equals(nombreArchivo) && file.isFile()) {
						return archivo;
					}
					// También buscar en subcarpeta logs/
					File enLogs = new File(file.getParentFile(), "logs");
					if (enLogs.isDirectory()) {
						File candidato = new File(enLogs, nombreArchivo);
						if (candidato.isFile()) {
							return candidato.toPath();
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			CrashDetectorLogger.log("[ParserMixerLogger] Clase MonitorDePID no encontrada");
		} catch (NoSuchFieldException | IllegalAccessException e) {
			CrashDetectorLogger.logException(e);
			CrashDetectorLogger.log("[ParserMixerLogger] Error accediendo a consolas");
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
		return null;
	}

	/**
	 * Procesa un archivo de log de MixerLogger y carga los datos en cache.
	 * 
	 * @param archivoLog Archivo MixerLogger.log a procesar
	 * @return true si se procesó exitosamente, false si hubo error
	 */
	public static boolean procesarArchivoLog(File archivoLog) {
		if (archivoLog == null || !archivoLog.exists() || !archivoLog.canRead()) {
			return false;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(archivoLog))) {
			String linea;
			Map<String, String> entradaActual = new HashMap<>();
			int entradasProcesadas = 0;

			while ((linea = reader.readLine()) != null) {
				linea = linea.trim();

				// Separador de entradas o línea vacía = procesar entrada acumulada
				if (linea.isEmpty() || linea.startsWith("--------------------")) {
					if (!entradaActual.isEmpty()) {
						if (procesarEntradaMixin(entradaActual)) {
							entradasProcesadas++;
						}
						entradaActual.clear();
					}
					continue;
				}

				// Parsear línea Key: Value
				Map<String, String> parsed = parsearLineaMixerLogger(linea);
				if (parsed != null && !parsed.isEmpty()) {
					Map.Entry<String, String> entry = parsed.entrySet().iterator().next();
					entradaActual.put(entry.getKey(), entry.getValue());
				}
			}

			// Procesar última entrada si no terminó con separador
			if (!entradaActual.isEmpty()) {
				if (procesarEntradaMixin(entradaActual)) {
					entradasProcesadas++;
				}
			}

			Cache.cargado = true;
			CrashDetectorLogger.log("[ParserMixerLogger] Cache cargado: " + entradasProcesadas + " mixins procesados");
			return true;

		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			CrashDetectorLogger.log("[ParserMixerLogger] Error al leer el log: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Procesa una entrada completa de mixin parseada del log y la almacena en
	 * cache.
	 * 
	 * @param entrada Mapa con claves/valores de una entrada de mixin
	 * @return true si se procesó exitosamente, false si hubo error
	 */
	private static boolean procesarEntradaMixin(Map<String, String> entrada) {
		try {
			String nombreClasePunto = entrada.get("Class Name");
			String jarOrigen = entrada.get("Originating JAR");
			if (nombreClasePunto == null || nombreClasePunto.isEmpty()) {
				return false;
			}

			// 1. Construir y cachear MixinInfo
			MixinInfo mixinInfo = construirMixinInfo(entrada, nombreClasePunto, jarOrigen);
			if (mixinInfo != null) {
				Cache.mixinsPorClase.put(nombreClasePunto, mixinInfo);
			}

			// 2. Parsear y cachear InfoMetodo desde MethodRef
			List<InfoMetodo> metodosInfo = parsearMethodRefParaInfoMetodo(entrada.get("MethodRef"));
			if (!metodosInfo.isEmpty()) {
				Cache.metodosPorClase.put(nombreClasePunto, metodosInfo);
			}

			// 3. Parsear y cachear InfoCampo desde FieldRef
			List<InfoCampo> camposInfo = parsearFieldRefParaInfoCampo(entrada.get("FieldRef"));
			if (!camposInfo.isEmpty()) {
				Cache.camposPorClase.put(nombreClasePunto, camposInfo);
			}

			// 4. Parsear y cachear Referencias combinadas
			List<Referencia> referencias = parsearReferenciasDesdeEntrada(entrada);
			if (!referencias.isEmpty()) {
				Cache.referenciasPorClase.put(nombreClasePunto, referencias);
			}

			return true;
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			return false;
		}
	}

	/**
	 * Construye un MixinInfo desde la entrada parseada del log.
	 */
	private static MixinInfo construirMixinInfo(Map<String, String> entrada, String nombreClase, String jarOrigen) {
		List<String> targets = parsearListaMixerLogger(entrada.get("Targets"));
		List<MixinMetodoInfo> metodosMixin = parsearMixinMethods(entrada.get("Mixin Methods"));
		List<MixinCampoInfo> camposMixin = parsearMixinFields(entrada.get("Mixin Fields"));

		// Parsear Inject Targets y extraer refmaps
		List<String> targetsInject = new ArrayList<>();
		Map<String, String> refmapMetodos = new HashMap<>();
		Map<String, String> refmapCampos = new HashMap<>();

		List<String> injectTargetsRaw = parsearListaMixerLogger(entrada.get("Inject Targets"));
		for (String target : injectTargetsRaw) {
			int idxMetadata = target.indexOf('{');
			String targetLimpio = idxMetadata >= 0 ? target.substring(0, idxMetadata) : target;
			targetsInject.add(targetLimpio);

			if (idxMetadata >= 0) {
				Map<String, String> metadata = extraerMetadataMixerLogger(target);
				String refmap = metadata.get("refmap");
				if (refmap != null) {
					if (targetLimpio.contains("(") && targetLimpio.contains(")")) {
						refmapMetodos.put(targetLimpio, refmap);
					} else {
						refmapCampos.put(targetLimpio, refmap);
					}
				}
			}
		}

		return new MixinInfo(nombreClase, jarOrigen, targets, metodosMixin, camposMixin, targetsInject, refmapMetodos,
				refmapCampos);
	}

	/**
	 * Parsea MethodRef para crear objetos InfoMetodo. Formato:
	 * [owner.methodName(descriptor), ...]
	 */
	private static List<InfoMetodo> parsearMethodRefParaInfoMetodo(String methodRefStr) {
		List<InfoMetodo> resultados = new ArrayList<>();
		if (methodRefStr == null || methodRefStr.equals("[]"))
			return resultados;

		List<String> items = parsearListaMixerLogger(methodRefStr);
		for (String item : items) {
			int lastDot = item.lastIndexOf('.');
			if (lastDot < 0)
				continue;

			String owner = item.substring(0, lastDot).replace('.', '/');
			String resto = item.substring(lastDot + 1);

			int parenOpen = resto.indexOf('(');
			int parenClose = resto.indexOf(')', parenOpen);
			if (parenOpen < 0 || parenClose < 0)
				continue;

			String nombreMetodo = resto.substring(0, parenOpen);
			String descriptor = resto.substring(parenOpen, parenClose + 1);

			resultados.add(new InfoMetodo(nombreMetodo, descriptor, new ArrayList<>(), new ArrayList<>()));
		}
		return resultados;
	}

	/**
	 * Parsea FieldRef para crear objetos InfoCampo. Formato:
	 * [owner.fieldName:descriptor, ...]
	 */
	private static List<InfoCampo> parsearFieldRefParaInfoCampo(String fieldRefStr) {
		List<InfoCampo> resultados = new ArrayList<>();
		if (fieldRefStr == null || fieldRefStr.equals("[]"))
			return resultados;

		List<String> items = parsearListaMixerLogger(fieldRefStr);
		for (String item : items) {
			int lastDot = item.lastIndexOf('.');
			if (lastDot < 0)
				continue;

			String owner = item.substring(0, lastDot).replace('.', '/');
			String resto = item.substring(lastDot + 1);

			int colonIdx = resto.indexOf(':');
			if (colonIdx < 0)
				continue;

			String nombreCampo = resto.substring(0, colonIdx);
			String descriptor = resto.substring(colonIdx + 1);

			resultados.add(new InfoCampo(nombreCampo, descriptor));
		}
		return resultados;
	}

	/**
	 * Parsea todas las referencias desde ClassRef, MethodRef y FieldRef.
	 */
	private static List<Referencia> parsearReferenciasDesdeEntrada(Map<String, String> entrada) {
		List<Referencia> resultados = new ArrayList<>();

		// MethodRef -> Referencia a métodos
		String methodRefStr = entrada.get("MethodRef");
		if (methodRefStr != null && !methodRefStr.equals("[]")) {
			List<String> items = parsearListaMixerLogger(methodRefStr);
			for (String item : items) {
				int lastDot = item.lastIndexOf('.');
				if (lastDot < 0)
					continue;
				String owner = item.substring(0, lastDot).replace('.', '/');
				String resto = item.substring(lastDot + 1);
				int parenOpen = resto.indexOf('(');
				int parenClose = resto.indexOf(')', parenOpen);
				if (parenOpen < 0 || parenClose < 0)
					continue;
				String nombre = resto.substring(0, parenOpen);
				String descriptor = resto.substring(parenOpen, parenClose + 1);
				resultados.add(new Referencia(owner, nombre, descriptor, true));
			}
		}

		// FieldRef -> Referencia a campos
		String fieldRefStr = entrada.get("FieldRef");
		if (fieldRefStr != null && !fieldRefStr.equals("[]")) {
			List<String> items = parsearListaMixerLogger(fieldRefStr);
			for (String item : items) {
				int lastDot = item.lastIndexOf('.');
				if (lastDot < 0)
					continue;
				String owner = item.substring(0, lastDot).replace('.', '/');
				String resto = item.substring(lastDot + 1);
				int colonIdx = resto.indexOf(':');
				if (colonIdx < 0)
					continue;
				String nombre = resto.substring(0, colonIdx);
				String descriptor = resto.substring(colonIdx + 1);
				resultados.add(new Referencia(owner, nombre, descriptor, false));
			}
		}

		return resultados;
	}

	/**
	 * Parsea la lista de Mixin Methods del log. Formato:
	 * [name(desc){targets:[a,b]}{refmap:xyz}, ...]
	 */
	private static List<MixinMetodoInfo> parsearMixinMethods(String listaStr) {
		List<MixinMetodoInfo> resultados = new ArrayList<>();
		if (listaStr == null || listaStr.equals("[]"))
			return resultados;

		List<String> items = parsearListaMixerLogger(listaStr);
		for (String item : items) {
			int parenOpen = item.indexOf('(');
			int parenClose = item.indexOf(')', parenOpen);
			if (parenOpen < 0 || parenClose < 0)
				continue;

			String nombre = item.substring(0, parenOpen);
			String descriptor = item.substring(parenOpen, parenClose + 1);

			Map<String, String> metadata = extraerMetadataMixerLogger(item);
			List<String> targets = parsearListaMixerLogger(metadata.get("targets"));
			boolean esOverwrite = metadata.containsKey("overwrite");

			resultados.add(new MixinMetodoInfo(nombre, descriptor, targets, esOverwrite));
		}
		return resultados;
	}

	/**
	 * Parsea la lista de Mixin Fields del log. Formato:
	 * [fieldName:descriptor{refmap:xyz}, ...]
	 */
	private static List<MixinCampoInfo> parsearMixinFields(String listaStr) {
		List<MixinCampoInfo> resultados = new ArrayList<>();
		if (listaStr == null || listaStr.equals("[]"))
			return resultados;

		List<String> items = parsearListaMixerLogger(listaStr);
		for (String item : items) {
			int colonIdx = item.indexOf(':');
			if (colonIdx < 0)
				continue;

			String nombre = item.substring(0, colonIdx);
			int braceIdx = item.indexOf('{', colonIdx);
			String descriptor = braceIdx >= 0 ? item.substring(colonIdx + 1, braceIdx) : item.substring(colonIdx + 1);

			resultados.add(new MixinCampoInfo(nombre, descriptor));
		}
		return resultados;
	}

	// ========================================================================
	// MÉTODOS DE PARSING AUXILIARES (públicos para reuso externo)
	// ========================================================================

	/**
	 * Parsea una línea del log de MixerLogger en formato parsable. Formato
	 * esperado: "Key: Value" donde Value puede ser lista [a,b] o con metadata
	 * {key:val}
	 * 
	 * @param linea Línea del log a parsear
	 * @return Mapa con clave y valor extraídos, o null si el formato no es válido
	 */
	public static Map<String, String> parsearLineaMixerLogger(String linea) {
		if (linea == null || !linea.contains(": "))
			return null;
		int idx = linea.indexOf(": ");
		String clave = linea.substring(0, idx);
		String valor = linea.substring(idx + 2);
		Map<String, String> mapa = new HashMap<>();
		mapa.put(clave, valor);
		return mapa;
	}

	/**
	 * Parsea una lista en formato [item1, item2, item3] respetando escapes.
	 * 
	 * @param listaStr String con formato de lista
	 * @return Lista de strings parseados
	 */
	public static List<String> parsearListaMixerLogger(String listaStr) {
		List<String> resultado = new ArrayList<>();
		if (listaStr == null || listaStr.equals("[]"))
			return resultado;
		if (!listaStr.startsWith("[") || !listaStr.endsWith("]"))
			return resultado;

		String contenido = listaStr.substring(1, listaStr.length() - 1);
		if (contenido.isEmpty())
			return resultado;

		StringBuilder actual = new StringBuilder();
		boolean escapado = false;
		int profundidad = 0; // para manejar { } anidados

		for (char c : contenido.toCharArray()) {
			if (escapado) {
				actual.append(c);
				escapado = false;
			} else if (c == '\\') {
				escapado = true;
			} else if (c == '{') {
				profundidad++;
				actual.append(c);
			} else if (c == '}') {
				profundidad--;
				actual.append(c);
			} else if (c == ',' && profundidad == 0) {
				String item = actual.toString().trim();
				if (!item.isEmpty())
					resultado.add(desescapar(item));
				actual.setLength(0);
			} else {
				actual.append(c);
			}
		}
		if (actual.length() > 0) {
			String item = actual.toString().trim();
			if (!item.isEmpty())
				resultado.add(desescapar(item));
		}
		return resultado;
	}

	/**
	 * Extrae metadata en formato {key:value} de un string.
	 * 
	 * @param texto Texto que puede contener bloques {key:val}
	 * @return Mapa con las claves y valores extraídos
	 */
	public static Map<String, String> extraerMetadataMixerLogger(String texto) {
		Map<String, String> metadata = new HashMap<>();
		if (texto == null)
			return metadata;

		int inicio = 0;
		while ((inicio = texto.indexOf('{', inicio)) >= 0) {
			int fin = texto.indexOf('}', inicio);
			if (fin < 0)
				break;
			String bloque = texto.substring(inicio + 1, fin);
			int dosPuntos = bloque.indexOf(':');
			if (dosPuntos > 0) {
				String clave = bloque.substring(0, dosPuntos).trim();
				String valor = desescapar(bloque.substring(dosPuntos + 1).trim());
				metadata.put(clave, valor);
			}
			inicio = fin + 1;
		}
		return metadata;
	}

	/**
	 * Desescapa caracteres en strings parseados de MixerLogger.
	 */
	public static String desescapar(String texto) {
		if (texto == null)
			return "";
		return texto.replace("\\,", ",").replace("\\{", "{").replace("\\}", "}").replace("\\\\", "\\");
	}

	// ========================================================================
	// MÉTODOS DE CONSULTA AL CACHE (públicos para uso desde ArchivoDeMod)
	// ========================================================================

	/**
	 * Obtiene un MixinInfo desde el cache si está disponible.
	 * 
	 * @param nombreClasePunto Nombre de clase en formato punto (ej:
	 *                         "net.example.Mixin")
	 * @return MixinInfo cacheado o null si no está en cache
	 */
	public static MixinInfo obtenerMixinDesdeCache(String nombreClasePunto) {
		return Cache.mixinsPorClase.get(nombreClasePunto);
	}

	/**
	 * Obtiene InfoMetodo cacheados para una clase.
	 * 
	 * @param nombreClasePunto Nombre de clase en formato punto
	 * @return Lista de InfoMetodo o lista vacía si no hay cache
	 */
	public static List<InfoMetodo> obtenerMetodosDesdeCache(String nombreClasePunto) {
		List<InfoMetodo> cacheados = Cache.metodosPorClase.get(nombreClasePunto);
		return cacheados != null ? new ArrayList<>(cacheados) : new ArrayList<>();
	}

	/**
	 * Obtiene InfoCampo cacheados para una clase.
	 * 
	 * @param nombreClasePunto Nombre de clase en formato punto
	 * @return Lista de InfoCampo o lista vacía si no hay cache
	 */
	public static List<InfoCampo> obtenerCamposDesdeCache(String nombreClasePunto) {
		List<InfoCampo> cacheados = Cache.camposPorClase.get(nombreClasePunto);
		return cacheados != null ? new ArrayList<>(cacheados) : new ArrayList<>();
	}

	/**
	 * Obtiene Referencias cacheadas para una clase.
	 * 
	 * @param nombreClasePunto Nombre de clase en formato punto
	 * @return Lista de Referencia o lista vacía si no hay cache
	 */
	public static List<Referencia> obtenerReferenciasDesdeCache(String nombreClasePunto) {
		List<Referencia> cacheadas = Cache.referenciasPorClase.get(nombreClasePunto);
		return cacheadas != null ? new ArrayList<>(cacheadas) : new ArrayList<>();
	}

	/**
	 * Verifica si el cache ha sido cargado desde un log.
	 * 
	 * @return true si el cache está poblado
	 */
	public static boolean esCacheCargado() {
		return Cache.cargado;
	}

	/**
	 * Limpia todo el cache. Útil para recargar datos si el log cambia.
	 */
	public static void limpiarCache() {
		Cache.limpiar();
	}
}