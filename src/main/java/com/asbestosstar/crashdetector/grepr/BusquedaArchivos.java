package com.asbestosstar.crashdetector.grepr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class BusquedaArchivos {

	private static final Set<String> EXT_COMPRIMIDOS_ZIP = new HashSet<>(
			Arrays.asList(".zip", ".jar", ".war", ".ear", ".fpm", ".litemod"));
	private static final String EXT_RAR = ".rar";
	private static final int PROFUNDIDAD_MAX_ANIDADO = 2;

	private static final int BUFFER = 256 * 1024;
	private static final int COLA_MAX = 4096;
	private static final Path POISON = Paths.get("");

	public static List<String> buscar(String directorio, String cadenaBusqueda, boolean usarRegex,
			boolean ignorarMayusculas) {
		return buscar(directorio, cadenaBusqueda, usarRegex, ignorarMayusculas, false);
	}

	public static List<String> buscar(String directorio, String cadenaBusqueda, boolean usarRegex,
			boolean ignorarMayusculas, boolean buscarEnComprimidos) {

		final ConcurrentLinkedQueue<String> resultados = new ConcurrentLinkedQueue<>();
		final BuscadorContenido buscador;

		try {
			buscador = new BuscadorContenido(cadenaBusqueda, usarRegex, ignorarMayusculas);
		} catch (Throwable t) {
			return Arrays.asList("Error preparando busqueda " + t.getMessage());
		}

		final int hilos = Math.max(1, Runtime.getRuntime().availableProcessors());
		final BlockingQueue<Path> cola = new ArrayBlockingQueue<>(COLA_MAX);
		final ExecutorService pool = Executors.newFixedThreadPool(hilos);

		for (int i = 0; i < hilos; i++) {
			pool.submit(() -> {
				while (true) {
					Path ruta;
					try {
						ruta = cola.take();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}

					if (ruta == POISON)
						return;

					procesarRuta(ruta, resultados, buscador, buscarEnComprimidos);
				}
			});
		}

		try (Stream<Path> stream = Files.walk(Paths.get(directorio))) {
			Iterator<Path> it = stream.filter(Files::isRegularFile).iterator();
			while (it.hasNext()) {
				cola.put(it.next());
			}
		} catch (Throwable e) {
			resultados.add("Error al recorrer directorio " + e.getMessage());
		} finally {
			for (int i = 0; i < hilos; i++) {
				try {
					cola.put(POISON);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			pool.shutdown();
			try {
				pool.awaitTermination(365, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				resultados.add("Busqueda interrumpida");
			}
		}

		return new ArrayList<>(resultados);
	}

	private static void procesarRuta(Path ruta, ConcurrentLinkedQueue<String> resultados, BuscadorContenido buscador,
			boolean buscarEnComprimidos) {
		String nombre = ruta.getFileName().toString();
		String lower = nombre.toLowerCase(Locale.ROOT);

		try {
			if (buscarEnComprimidos && terminaCon(lower, EXT_COMPRIMIDOS_ZIP)) {
				buscarDentroDeZip(ruta, resultados, buscador, 0, ruta.toAbsolutePath().toString());
				return;
			}

			if (buscarEnComprimidos && lower.endsWith(EXT_RAR)) {
				if (cabeceraPareceZip(ruta)) {
					buscarDentroDeZip(ruta, resultados, buscador, 0, ruta.toAbsolutePath().toString());
				} else if (cabeceraPareceRar(ruta)) {
					resultados.add(ruta.toAbsolutePath() + " [OMITIDO rar real no soportado]");
				} else {
					procesarArchivoPlano(ruta, resultados, buscador);
				}
				return;
			}

			procesarArchivoPlano(ruta, resultados, buscador);

		} catch (IOException e) {
			resultados.add(ruta.toAbsolutePath() + " [ERROR " + e.getMessage() + "]");
		}
	}

	private static void procesarArchivoPlano(Path ruta, ConcurrentLinkedQueue<String> resultados,
			BuscadorContenido buscador) throws IOException {
		try (InputStream in = Files.newInputStream(ruta)) {
			if (buscador.coincide(in)) {
				resultados.add(ruta.toAbsolutePath().toString());
			}
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
		try (InputStream in = Files.newInputStream(ruta)) {
			byte[] sig = new byte[2];
			return in.read(sig) == 2 && sig[0] == 'P' && sig[1] == 'K';
		} catch (IOException ignored) {
			return false;
		}
	}

	private static boolean cabeceraPareceRar(Path ruta) {
		try (InputStream in = Files.newInputStream(ruta)) {
			byte[] sig = new byte[7];
			int r = in.read(sig);
			return r >= 7 && sig[0] == 'R' && sig[1] == 'a' && sig[2] == 'r' && sig[3] == '!' && sig[4] == 0x1A
					&& sig[5] == 0x07;
		} catch (IOException ignored) {
			return false;
		}
	}

	private static void buscarDentroDeZip(Path rutaZip, ConcurrentLinkedQueue<String> resultados,
			BuscadorContenido buscador, int profundidad, String rutaVisual) throws IOException {

		try (ZipFile zip = new ZipFile(rutaZip.toFile())) {
			Enumeration<? extends ZipEntry> entradas = zip.entries();

			while (entradas.hasMoreElements()) {
				ZipEntry entrada = entradas.nextElement();
				if (entrada.isDirectory())
					continue;

				String nombreEntrada = entrada.getName();
				String lower = nombreEntrada.toLowerCase(Locale.ROOT);

				try (InputStream in = zip.getInputStream(entrada)) {
					if (profundidad < PROFUNDIDAD_MAX_ANIDADO && terminaCon(lower, EXT_COMPRIMIDOS_ZIP)) {
						byte[] nested = leerTodo(in);
						procesarZipAnidado(new ZipInputStream(new ByteArrayInputStream(nested)), resultados, buscador,
								profundidad + 1, rutaVisual + "!" + nombreEntrada);
					} else if (buscador.coincide(in)) {
						resultados.add(rutaZip.toAbsolutePath() + "!" + nombreEntrada);
					}
				}
			}
		}
	}

	private static void procesarZipAnidado(ZipInputStream zin, ConcurrentLinkedQueue<String> resultados,
			BuscadorContenido buscador, int profundidad, String rutaVisual) throws IOException {

		try (ZipInputStream z = zin) {
			ZipEntry e;
			while ((e = z.getNextEntry()) != null) {
				if (e.isDirectory()) {
					z.closeEntry();
					continue;
				}

				String nombreEntrada = e.getName();
				String lower = nombreEntrada.toLowerCase(Locale.ROOT);

				if (profundidad < PROFUNDIDAD_MAX_ANIDADO && terminaCon(lower, EXT_COMPRIMIDOS_ZIP)) {
					byte[] nested = leerTodo(z);
					procesarZipAnidado(new ZipInputStream(new ByteArrayInputStream(nested)), resultados, buscador,
							profundidad + 1, rutaVisual + "!" + nombreEntrada);
				} else if (buscador.coincide(z)) {
					resultados.add(rutaVisual + "!" + nombreEntrada);
				}

				z.closeEntry();
			}
		}
	}

	private static byte[] leerTodo(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(64 * 1024);
		byte[] buf = new byte[BUFFER];
		int r;
		while ((r = in.read(buf)) != -1) {
			bos.write(buf, 0, r);
		}
		return bos.toByteArray();
	}

	private static final class BuscadorContenido {
		private final Pattern patron;
		private final byte[] literal;
		private final boolean regex;
		private final boolean ignorarMayusculas;
		private final int[] lps;

		BuscadorContenido(String cadena, boolean usarRegex, boolean ignorarMayusculas) {
			this.regex = usarRegex;
			this.ignorarMayusculas = ignorarMayusculas;

			if (usarRegex) {
				int flags = ignorarMayusculas ? Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE : 0;
				this.patron = Pattern.compile(cadena, flags);
				this.literal = null;
				this.lps = null;
			} else {
				this.patron = null;
				byte[] raw = cadena.getBytes(StandardCharsets.ISO_8859_1);
				this.literal = ignorarMayusculas ? lowerAscii(raw) : raw;
				this.lps = crearLps(this.literal);
			}
		}

		boolean coincide(InputStream in) throws IOException {
			if (regex)
				return coincideRegex(in);
			return coincideLiteral(in);
		}

		private boolean coincideRegex(InputStream in) throws IOException {
			StringBuilder sb = new StringBuilder(64 * 1024);
			byte[] buf = new byte[BUFFER];
			int r;

			while ((r = in.read(buf)) != -1) {
				sb.append(new String(buf, 0, r, StandardCharsets.ISO_8859_1));

				if (patron.matcher(sb).find())
					return true;

				if (sb.length() > 16384) {
					sb.delete(0, sb.length() - 8192);
				}
			}
			return false;
		}

		private boolean coincideLiteral(InputStream in) throws IOException {
			if (literal.length == 0)
				return true;

			byte[] buf = new byte[BUFFER];
			int j = 0;
			int r;

			while ((r = in.read(buf)) != -1) {
				for (int i = 0; i < r; i++) {
					byte b = ignorarMayusculas ? lowerAscii(buf[i]) : buf[i];

					while (j > 0 && b != literal[j]) {
						j = lps[j - 1];
					}

					if (b == literal[j]) {
						j++;
						if (j == literal.length)
							return true;
					}
				}
			}

			return false;
		}

		private static int[] crearLps(byte[] pat) {
			int[] lps = new int[pat.length];
			int len = 0;

			for (int i = 1; i < pat.length; i++) {
				while (len > 0 && pat[i] != pat[len]) {
					len = lps[len - 1];
				}

				if (pat[i] == pat[len]) {
					len++;
					lps[i] = len;
				}
			}

			return lps;
		}

		private static byte[] lowerAscii(byte[] in) {
			byte[] out = new byte[in.length];
			for (int i = 0; i < in.length; i++) {
				out[i] = lowerAscii(in[i]);
			}
			return out;
		}

		private static byte lowerAscii(byte b) {
			if (b >= 'A' && b <= 'Z')
				return (byte) (b + 32);
			return b;
		}
	}
}