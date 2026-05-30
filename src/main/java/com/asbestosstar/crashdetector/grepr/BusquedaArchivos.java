package com.asbestosstar.crashdetector.grepr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BusquedaArchivos {

	private static final Set<String> EXT_COMPRIMIDOS_ZIP = new HashSet<>(
			Arrays.asList(".zip", ".jar", ".war", ".ear", ".fpm", ".litemod"));
	private static final String EXT_RAR = ".rar";
	private static final int PROFUNDIDAD_MAX_ANIDADO = 2;

	public static List<String> buscar(String directorio, String cadenaBusqueda, boolean usarRegex,
			boolean ignorarMayusculas) {
		return buscar(directorio, cadenaBusqueda, usarRegex, ignorarMayusculas, false);
	}

	public static List<String> buscar(String directorio, String cadenaBusqueda, boolean usarRegex,
			boolean ignorarMayusculas, boolean buscarEnComprimidos) {

		// resultados seguro para varios hilos
		final ConcurrentLinkedQueue<String> resultados = new ConcurrentLinkedQueue<String>();

		// preparar patron y literales una vez
		final Pattern patron;
		final byte[] literalBytes;
		final String literalTextoISO;
		if (usarRegex) {
			int flags = ignorarMayusculas ? Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE : 0;
			patron = Pattern.compile(cadenaBusqueda, flags);
			literalBytes = null;
			literalTextoISO = null;
		} else {
			patron = null;
			literalBytes = cadenaBusqueda.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
			literalTextoISO = ignorarMayusculas ? cadenaBusqueda.toLowerCase(Locale.ROOT) : cadenaBusqueda;
		}

		// recolectar archivos primero
		final List<Path> archivos;
		try (Stream<Path> stream = Files.walk(Paths.get(directorio))) {
			archivos = stream.filter(Files::isRegularFile).collect(Collectors.toList());
		} catch (IOException e) {
			return Arrays.asList("Error al recorrer directorio " + e.getMessage());
		}

		// tamano del pool segun nucleos
		final int hilos = Math.max(1, Runtime.getRuntime().availableProcessors());
		ExecutorService pool = Executors.newFixedThreadPool(hilos);

		try {
			List<Future<?>> futures = new ArrayList<Future<?>>(archivos.size());

			for (final Path ruta : archivos) {
				futures.add(pool.submit(new Runnable() {
					@Override
					public void run() {
						String nombre = ruta.getFileName().toString();
						String lower = nombre.toLowerCase(Locale.ROOT);

						try {
							// comprimidos tipo zip por extension
							if (buscarEnComprimidos && terminaCon(lower, EXT_COMPRIMIDOS_ZIP)) {
								buscarDentroDeZip(ruta, resultados, patron, literalBytes, literalTextoISO,
										ignorarMayusculas, 0, ruta.toAbsolutePath().toString());
								return;
							}

							// rar con deteccion por cabecera
							if (buscarEnComprimidos && lower.endsWith(EXT_RAR)) {
								if (cabeceraPareceZip(ruta)) {
									buscarDentroDeZip(ruta, resultados, patron, literalBytes, literalTextoISO,
											ignorarMayusculas, 0, ruta.toAbsolutePath().toString());
								} else if (cabeceraPareceRar(ruta)) {
									resultados.add(ruta.toAbsolutePath() + " [OMITIDO rar real no soportado]");
								} else {
									procesarArchivoPlano(ruta, resultados, patron, literalBytes, literalTextoISO,
											ignorarMayusculas);
								}
								return;
							}

							// archivo normal
							procesarArchivoPlano(ruta, resultados, patron, literalBytes, literalTextoISO,
									ignorarMayusculas);

						} catch (IOException e) {
							resultados.add(ruta.toAbsolutePath() + " [ERROR " + e.getMessage() + "]");
						}
					}
				}));
			}

			// esperar tareas
			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					resultados.add("Busqueda interrumpida");
				} catch (ExecutionException ee) {
					resultados.add("Error en tarea " + ee.getCause());
				}
			}

		} finally {
			pool.shutdown();
		}

		return new ArrayList<String>(resultados);
	}

	private static boolean coincideContenido(byte[] contenidoBytes, Pattern patron, byte[] literalBytes,
			String literalTextoISO, boolean ignorarMayusculas) {
		if (patron != null) {
			String texto = new String(contenidoBytes, java.nio.charset.StandardCharsets.ISO_8859_1);
			return patron.matcher(texto).find();
		} else {
			if (ignorarMayusculas) {
				String texto = new String(contenidoBytes, java.nio.charset.StandardCharsets.ISO_8859_1);
				return texto.toLowerCase(Locale.ROOT).contains(literalTextoISO);
			} else {
				return indexOf(contenidoBytes, literalBytes) >= 0;
			}
		}
	}

	private static int indexOf(byte[] data, byte[] pattern) {
		if (pattern.length == 0)
			return 0;
		int[] lps = buildLps(pattern);
		int i = 0;
		int j = 0;
		while (i < data.length) {
			if (data[i] == pattern[j]) {
				i++;
				j++;
				if (j == pattern.length)
					return i - j;
			} else {
				if (j != 0) {
					j = lps[j - 1];
				} else {
					i++;
				}
			}
		}
		return -1;
	}

	private static int[] buildLps(byte[] pat) {
		int[] lps = new int[pat.length];
		int len = 0;
		int i = 1;
		while (i < pat.length) {
			if (pat[i] == pat[len]) {
				len++;
				lps[i] = len;
				i++;
			} else {
				if (len != 0) {
					len = lps[len - 1];
				} else {
					lps[i] = 0;
					i++;
				}
			}
		}
		return lps;
	}

	private static void procesarArchivoPlano(Path ruta, ConcurrentLinkedQueue<String> resultados, Pattern patron,
			byte[] literalBytes, String literalTextoISO, boolean ignorarMayusculas) throws IOException {
		byte[] bytes = Files.readAllBytes(ruta);
		if (bytes.length == 0)
			return;
		if (coincideContenido(bytes, patron, literalBytes, literalTextoISO, ignorarMayusculas)) {
			resultados.add(ruta.toAbsolutePath().toString());
		}
	}

	private static boolean terminaCon(String lowerName, Set<String> extensiones) {
		for (String ext : extensiones) {
			if (lowerName.endsWith(ext))
				return true;
		}
		return false;
	}

	private static boolean cabeceraPareceZip(Path ruta) {
		InputStream in = null;
		try {
			in = Files.newInputStream(ruta);
			byte[] sig = new byte[4];
			int r = in.read(sig);
			if (r >= 2) {
				return sig[0] == 'P' && sig[1] == 'K';
			}
		} catch (IOException ignored) {
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException ignored) {
				}
		}
		return false;
	}

	private static boolean cabeceraPareceRar(Path ruta) {
		InputStream in = null;
		try {
			in = Files.newInputStream(ruta);
			byte[] sig = new byte[7];
			int r = in.read(sig);
			if (r >= 7) {
				return sig[0] == 'R' && sig[1] == 'a' && sig[2] == 'r' && sig[3] == '!' && sig[4] == 0x1A
						&& sig[5] == 0x07;
			}
		} catch (IOException ignored) {
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException ignored) {
				}
		}
		return false;
	}

	private static void buscarDentroDeZip(Path rutaZip, ConcurrentLinkedQueue<String> resultados, Pattern patron,
			byte[] literalBytes, String literalTextoISO, boolean ignorarMayusculas, int profundidad, String rutaVisual)
			throws IOException {

		try (ZipFile zip = new ZipFile(rutaZip.toFile())) {
			Enumeration<? extends ZipEntry> entradas = zip.entries();
			while (entradas.hasMoreElements()) {
				ZipEntry entrada = entradas.nextElement();
				if (entrada.isDirectory())
					continue;

				String nombreEntrada = entrada.getName();
				String lower = nombreEntrada.toLowerCase(Locale.ROOT);

				if (profundidad < PROFUNDIDAD_MAX_ANIDADO && terminaCon(lower, EXT_COMPRIMIDOS_ZIP)) {
					byte[] bytesAnidado = leerEntrada(zip, entrada);
					java.util.zip.ZipInputStream zin = null;
					try {
						zin = new java.util.zip.ZipInputStream(new java.io.ByteArrayInputStream(bytesAnidado));
						procesarZipAnidado(zin, resultados, patron, literalBytes, literalTextoISO, ignorarMayusculas,
								profundidad + 1, rutaVisual + "!" + nombreEntrada);
					} finally {
						if (zin != null)
							try {
								zin.close();
							} catch (IOException ignored) {
							}
					}
					continue;
				}

				byte[] bytes = leerEntrada(zip, entrada);
				if (bytes.length == 0)
					continue;
				if (coincideContenido(bytes, patron, literalBytes, literalTextoISO, ignorarMayusculas)) {
					resultados.add(rutaZip.toAbsolutePath() + "!" + nombreEntrada);
				}
			}
		}
	}

	private static void procesarZipAnidado(java.util.zip.ZipInputStream zin, ConcurrentLinkedQueue<String> resultados,
			Pattern patron, byte[] literalBytes, String literalTextoISO, boolean ignorarMayusculas, int profundidad,
			String rutaVisual) throws IOException {
		ZipEntry e;
		while ((e = zin.getNextEntry()) != null) {
			if (e.isDirectory()) {
				zin.closeEntry();
				continue;
			}
			String nombreEntrada = e.getName();
			String lower = nombreEntrada.toLowerCase(Locale.ROOT);

			if (profundidad < PROFUNDIDAD_MAX_ANIDADO && terminaCon(lower, EXT_COMPRIMIDOS_ZIP)) {
				byte[] nested = leerTodo(zin);
				java.util.zip.ZipInputStream zin2 = null;
				try {
					zin2 = new java.util.zip.ZipInputStream(new java.io.ByteArrayInputStream(nested));
					procesarZipAnidado(zin2, resultados, patron, literalBytes, literalTextoISO, ignorarMayusculas,
							profundidad + 1, rutaVisual + "!" + nombreEntrada);
				} finally {
					if (zin2 != null)
						try {
							zin2.close();
						} catch (IOException ignored) {
						}
				}
			} else {
				byte[] bytes = leerTodo(zin);
				if (bytes.length == 0) {
					zin.closeEntry();
					continue;
				}
				if (coincideContenido(bytes, patron, literalBytes, literalTextoISO, ignorarMayusculas)) {
					resultados.add(rutaVisual + "!" + nombreEntrada);
				}
			}
			zin.closeEntry();
		}
	}

	private static byte[] leerEntrada(ZipFile zip, ZipEntry entrada) throws IOException {
		InputStream in = null;
		try {
			in = zip.getInputStream(entrada);
			return leerTodo(in);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException ignored) {
				}
		}
	}

	private static byte[] leerTodo(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(Math.max(32768, in.available()));
		byte[] buf = new byte[64 * 1024];
		int r;
		while ((r = in.read(buf)) != -1) {
			bos.write(buf, 0, r);
		}
		return bos.toByteArray();
	}
}
