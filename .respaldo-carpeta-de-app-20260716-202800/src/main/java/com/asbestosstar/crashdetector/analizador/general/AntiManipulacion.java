package com.asbestosstar.crashdetector.analizador.general;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Verificador anti-manipulación: compara hashes de archivos y carpetas contra
 * un archivo de control. Soporta archivos individuales y carpetas recursivas.
 * Usa hashing rápido y ForkJoinPool para paralelismo.
 *
 * Esta versión está adaptada para funcionar correctamente con el comportamiento
 * original de Json.obtener(...), donde una clave inexistente puede devolver un
 * nodo "fantasma". Por eso aquí se valida la existencia real de claves con
 * claves().contains(...) antes de confiar en obtener(...).
 */
public class AntiManipulacion implements Verificaciones {

	public static final Path ARCHIVO_ANTIMANIPULACION = Statics.carpeta.resolve("antimanipulacion.json");

	private boolean activado = false;
	private String mensaje = "";
	boolean completa = false;

	@Override
	public String[] patronesRapidos() {
		// No necesita activar por línea.
		// Usa vdst.trazos_completos en finalizarArchivo().
		verificar();
		return new String[0];
	}

	public void verificarCoincidencia(EventoDeCoincidencia evento) {
	}

	public void verificar() {

		List<String> errores = new ArrayList<>();

		Path archivoHashes = Paths.get("anti_manipulacion.json");
		if (!Files.exists(archivoHashes)) {
			return;
		}

		Nodo raiz;
		try {
			String contenido = new String(Files.readAllBytes(archivoHashes), java.nio.charset.StandardCharsets.UTF_8);
			raiz = Json.leer(contenido);
		} catch (Exception e) {
			return;
		}

		if (raiz == null || !raiz.esArreglo()) {
			return;
		}

		ForkJoinPool pool = new ForkJoinPool(Math.max(1, Runtime.getRuntime().availableProcessors()));

		try {
			for (int i = 0; i < raiz.tamano(); i++) {
				Nodo item = raiz.en(i);

				if (item == null || !item.esObjeto()) {
					continue;
				}

				if (!item.claves().contains("ruta")) {
					continue;
				}

				Nodo nodoRuta = item.obtener("ruta");
				if (nodoRuta == null || nodoRuta.esObjeto() || nodoRuta.esArreglo()) {
					continue;
				}

				String rutaRelativa = nodoRuta.comoCadena();
				if (rutaRelativa == null || rutaRelativa.trim().isEmpty()) {
					continue;
				}

				rutaRelativa = rutaRelativa.trim();

				// Convertir a ruta absoluta.
				Path rutaAbsoluta = Paths.get(rutaRelativa);
				if (!rutaAbsoluta.isAbsolute()) {
					Path userDir = Paths.get(System.getProperty("user.dir"));
					rutaAbsoluta = userDir.resolve(rutaRelativa);
				}

				// Leer el indicador de carpeta solo si la clave existe realmente.
				boolean esCarpeta = false;
				if (item.claves().contains("es_carpeta")) {
					Nodo nodoCarpetas = item.obtener("es_carpeta");
					if (nodoCarpetas != null) {
						try {
							esCarpeta = nodoCarpetas.comoBooleano();
						} catch (Exception ignored) {
							esCarpeta = false;
						}
					}
				}

				if (esCarpeta) {
					// ----- Verificación de carpeta -----

					// Validar existencia real de la clave "hashes".
					if (!item.claves().contains("hashes")) {
						errores.add(MonitorDePID.idioma.entradaCarpetaInvalida(rutaRelativa));
						continue;
					}

					Nodo nodoHashes = item.obtener("hashes");
					if (nodoHashes == null || !nodoHashes.esObjeto()) {
						errores.add(MonitorDePID.idioma.entradaCarpetaInvalida(rutaRelativa));
						continue;
					}

					// Leer mapa de hashes esperados.
					Map<String, String> hashesEsperados = new HashMap<>();

					// Importante:
					// No usar un while con obtener("0"), obtener("1"), etc.,
					// porque con el comportamiento original de Json.obtener(...)
					// eso puede no devolver null nunca.
					for (String key : nodoHashes.claves()) {
						Nodo clave = nodoHashes.obtener(key);

						if (clave != null && clave.esArreglo() && clave.tamano() == 2) {
							Nodo subRuta = clave.en(0);
							Nodo hash = clave.en(1);

							if (subRuta != null && hash != null) {
								String r = subRuta.comoCadena();
								String h = hash.comoCadena();

								if (r != null && h != null) {
									hashesEsperados.put(r, h);
								}
							}
						}
					}

					if (hashesEsperados.isEmpty()) {
						errores.add(MonitorDePID.idioma.carpetaSinHashes(rutaRelativa));
						continue;
					}

					// Escanear archivos reales.
					Map<String, String> hashesReales = new ConcurrentHashMap<>();
					List<Path> archivosParaHash = new ArrayList<>();

					try {
						Files.walk(rutaAbsoluta).filter(Files::isRegularFile).forEach(archivosParaHash::add);
					} catch (IOException e) {
						errores.add(MonitorDePID.idioma.noSePudoAccederCarpeta(rutaRelativa));
						continue;
					}

					// Calcular hashes en paralelo.
					pool.submit(new HashTask(archivosParaHash, rutaAbsoluta, hashesReales)).join();

					// 1. Verificar archivos faltantes o con hash incorrecto.
					for (Map.Entry<String, String> entry : hashesEsperados.entrySet()) {
						String subRuta = entry.getKey();
						String hashEsperado = entry.getValue();
						String hashReal = hashesReales.get(subRuta);

						if (hashReal == null) {
							errores.add(MonitorDePID.idioma.archivoFaltanteEnCarpeta(rutaRelativa, subRuta));
						} else if (!hashReal.equals(hashEsperado)) {
							errores.add(MonitorDePID.idioma.hashIncorrectoEn(rutaRelativa, subRuta));
						}
					}

					// 2. Verificar archivos nuevos no esperados.
					for (String subRuta : hashesReales.keySet()) {
						if (!hashesEsperados.containsKey(subRuta)) {
							errores.add(MonitorDePID.idioma.archivoNoAutorizadoEnCarpeta(rutaRelativa, subRuta));
						}
					}

				} else {
					// ----- Verificación de archivo individual -----

					// Validar existencia real de la clave "hash".
					if (!item.claves().contains("hash")) {
						errores.add(MonitorDePID.idioma.entradaArchivoInvalida(rutaRelativa));
						continue;
					}

					Nodo nodoHash = item.obtener("hash");
					if (nodoHash == null || nodoHash.esObjeto() || nodoHash.esArreglo()) {
						errores.add(MonitorDePID.idioma.entradaArchivoInvalida(rutaRelativa));
						continue;
					}

					String hashEsperado = nodoHash.comoCadena();
					if (hashEsperado == null || hashEsperado.trim().isEmpty()) {
						errores.add(MonitorDePID.idioma.hashFaltanteParaArchivo(rutaRelativa));
						continue;
					}

					if (!Files.exists(rutaAbsoluta)) {
						errores.add(MonitorDePID.idioma.archivoFaltante(rutaRelativa));
						continue;
					}

					String hashReal;
					try {
						hashReal = calcularHash(rutaAbsoluta);
					} catch (IOException e) {
						errores.add(MonitorDePID.idioma.errorAlLeerArchivo(rutaRelativa));
						continue;
					}

					if (!hashReal.equals(hashEsperado)) {
						errores.add(MonitorDePID.idioma.hashIncorrectoParaArchivo(rutaRelativa));
					}
				}
			}
		} finally {
			pool.shutdown();
		}

		if (!errores.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append(MonitorDePID.idioma.antimanipulacion_titulo());
			sb.append("<ul>");

			for (String error : errores) {
				// Convertir rutas absolutas a relativas en el mensaje.
				String errorRel = error.replace(Paths.get(System.getProperty("user.dir")).toString(), ".");
				sb.append("<li>").append(errorRel).append("</li>");
			}

			sb.append("</ul>");
			this.mensaje = sb.toString();
			this.activado = true;
		}
	}

	public boolean quiereAnalizarLineas() {
		return false;
	}

	/**
	 * Tarea recursiva para calcular hashes en paralelo.
	 */
	private static class HashTask extends RecursiveAction {
		private final List<Path> archivos;
		private final Path raiz;
		private final Map<String, String> resultado;

		HashTask(List<Path> archivos, Path raiz, Map<String, String> resultado) {
			this.archivos = archivos;
			this.raiz = raiz;
			this.resultado = resultado;
		}

		@Override
		protected void compute() {
			if (archivos.size() <= 100) {
				// Procesar directamente cuando el bloque es pequeño.
				for (Path archivo : archivos) {
					try {
						String rel = raiz.relativize(archivo).toString();
						String hash = calcularHash(archivo);
						resultado.put(rel, hash);
					} catch (IOException ignored) {
						// Ignorar archivos no legibles.
					}
				}
			} else {
				// Dividir en dos subtareas.
				int mitad = archivos.size() / 2;
				List<Path> izq = archivos.subList(0, mitad);
				List<Path> der = archivos.subList(mitad, archivos.size());
				invokeAll(new HashTask(izq, raiz, resultado), new HashTask(der, raiz, resultado));
			}
		}
	}

	/**
	 * Calcula un hash rápido del archivo.
	 */
	private static String calcularHash(Path archivo) throws IOException {
		try (InputStream in = Files.newInputStream(archivo)) {
			long hash = 0x9E3779B1L;
			byte[] buffer = new byte[8192];
			int n;

			while ((n = in.read(buffer)) != -1) {
				for (int i = 0; i < n; i++) {
					hash ^= (hash << 5) + (hash >>> 2) + (buffer[i] & 0xFF);
				}
			}

			return String.format("%08x", hash & 0xFFFFFFFFL);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new AntiManipulacion();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_antimanipulacion();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.antimanipulacion_reinstalar());
		return builder.construir();
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "antimanipulacion";
	}

	@Override
	public boolean anularNormal() {
		// Si hay manipulación, forzar apertura de CrashDetector.
		return activado;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}