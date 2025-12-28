package com.asbestosstar.crashdetector.gui.tipos.cfr;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.api.OutputSinkFactory.Sink;
import org.benf.cfr.reader.api.OutputSinkFactory.SinkClass;
import org.benf.cfr.reader.api.OutputSinkFactory.SinkType;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.buscar.Buscardor;

public class DescompilarCFR {

	public static String descompilar(String clase) {
		if (clase == null || clase.trim().isEmpty()) {
			CrashDetectorLogger.log("no clase");
			return null;
		}

		final String claseInterna = normalizarNombreClaseInterno(clase); // a/b/C
		final String rutaClase = claseInterna + ".class";

		final byte[] bytesObjetivo = Buscardor.obtenerBytesDeClase(claseInterna);
		if (bytesObjetivo == null) {
			CrashDetectorLogger.log("No obj");
			// intentar con el original por si venía raro
			byte[] b2 = Buscardor.obtenerBytesDeClase(clase);
			if (b2 == null) {
				CrashDetectorLogger.log("no b2 CFR " + clase);
				return null;
			}
		}

		final StringBuilder salida = new StringBuilder();

		try {
			// Options CFR (puedes añadir más si quieres)
			Map<String, String> opciones = new HashMap<>();
			// Opcional: más legible / menos ruido
			opciones.put("hideutf", "true");
			opciones.put("comments", "false");

			// Fuente de clases en memoria (y fallback al Buscardor para dependencias)
			ClassFileSource source = new ClassFileSource() {

				private String normalizarRutaClass(String path) {
					if (path == null)
						return null;
					String p = path.trim();
					if (p.startsWith("L") && p.endsWith(";"))
						p = p.substring(1, p.length() - 1);
					if (p.toLowerCase().endsWith(".class"))
						p = p.substring(0, p.length() - 6);
					p = p.replace('.', '/');
					return p;
				}

				@Override
				public Pair<byte[], String> getClassFileContent(String path) throws IOException {
					String interna = normalizarRutaClass(path);

					// clase objetivo
					if (claseInterna.equals(interna)) {
						byte[] b = Buscardor.obtenerBytesDeClase(claseInterna);
						if (b != null)
							return Pair.make(b, "in-mem");
					}

					// dependencias: pedimos al Buscardor también
					byte[] dep = Buscardor.obtenerBytesDeClase(interna);
					if (dep != null) {
						return Pair.make(dep, "in-mem-dep");
					}

					throw new IOException("Clase no disponible: " + path);
				}

				// CFR llama esto para ajustar paths; devolvemos el mismo
				@Override
				public String getPossiblyRenamedPath(String path) {
					return path;
				}

				@Override
				public void informAnalysisRelativePathDetail(String usePath, String classFilePath) {
					// no-op
				}

				@Override
				public Collection<String> addJar(String jarPath) {
					return Collections.emptyList();
				}
			};

			OutputSinkFactory sinkFactory = new OutputSinkFactory() {
				@Override
				public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
					if (sinkType == SinkType.JAVA) {
						return Collections.singletonList(SinkClass.STRING);
					}
					// Ignora otros sinks por defecto
					return Collections.singletonList(SinkClass.EXCEPTION_MESSAGE);
				}

				@Override
				public <T> Sink<T> getSink(final SinkType sinkType, final SinkClass sinkClass) {
					return t -> {
						if (sinkType == SinkType.JAVA && t != null) {
							salida.append(t.toString());
							if (!salida.toString().endsWith("\n"))
								salida.append('\n');
						}
					};
				}
			};

			CfrDriver driver = new CfrDriver.Builder().withOptions(opciones).withClassFileSource(source)
					.withOutputSink(sinkFactory).build();

			driver.analyse(Collections.singletonList(rutaClase));

			String res = salida.toString().trim();
			CrashDetectorLogger.log("res " + res);

			return res.isEmpty() ? null : res;

		} catch (Throwable e) {
			CrashDetectorLogger.log("Error al descompilar con CFR: " + e.getMessage());
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	/**
	 * Normaliza un nombre de clase a formato interno ASM: a/b/C Soporta: -
	 * com.ejemplo.Clase - com/ejemplo/Clase - com/ejemplo/Clase.class -
	 * Lcom/ejemplo/Clase;
	 */
	private static String normalizarNombreClaseInterno(String nombre) {
		if (nombre == null)
			return null;
		String s = nombre.trim();
		if (s.isEmpty())
			return s;

		if (s.startsWith("L") && s.endsWith(";")) {
			s = s.substring(1, s.length() - 1);
		}
		if (s.toLowerCase().endsWith(".class")) {
			s = s.substring(0, s.length() - 6);
		}
		s = s.replace('.', '/');
		return s;
	}

}
