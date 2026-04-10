package com.asbestosstar.crashdetector.analizador.firmas.v0;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.firmas.FiltrodeCodice;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * Parser / escritor / builder para el esquema V0 (schema = 0) de firmas.json
 *
 * Soporta: - idiomas faltantes - idiomas nuevos no previstos originalmente -
 * compatibilidad con códigos antiguos incorrectos: kp -> ko jp -> ja
 */
public class FirmasV0 {

	public static final int SCHEMA = 0;
	public static final String NOMBRE_ARCHIVO = "firmas.json";

	public static List<VerificacionFirmasV0> cargar() {
		final Path ruta = Statics.carpeta.resolve(NOMBRE_ARCHIVO);
		final List<VerificacionFirmasV0> lista = new ArrayList<VerificacionFirmasV0>();

		try {
			if (!Files.exists(ruta)) {
				return lista;
			}

			byte[] bytes = Files.readAllBytes(ruta);
			String json = new String(bytes, StandardCharsets.UTF_8);

			Nodo raiz = Json.leer(json);

			Nodo nSchema = raiz != null ? raiz.obtener("schema") : null;

			int schema;
			try {
				schema = (nSchema == null) ? SCHEMA : nSchema.comoEntero();
			} catch (Throwable te) {
				String sraw = (nSchema == null ? null : nSchema.comoCadena());
				if (sraw != null) {
					try {
						schema = Integer.parseInt(sraw.trim());
					} catch (NumberFormatException nfe) {
						schema = SCHEMA;
					}
				} else {
					schema = SCHEMA;
				}
			}

			if (schema != SCHEMA) {
				return lista;
			}

			Nodo arr = raiz.obtener("verificaciones");
			if (arr != null) {
				boolean esArr;
				int tam = 0;
				try {
					esArr = arr.esArreglo();
				} catch (Throwable te) {
					esArr = false;
				}
				try {
					tam = arr.tamano();
				} catch (Throwable te) {
					tam = 0;
				}

				if (esArr && tam > 0) {
					for (int i = 0; i < tam; i++) {
						try {
							Nodo v = arr.en(i);

							String id = seguro(v.obtener("id"));
							String para_buscar = seguro(v.obtener("para_buscar"));
							String idFiltro = seguro(v.obtener("filtro"));

							FiltrodeCodice filtro = FiltrodeCodice.obtener(idFiltro);

							String critStr = seguro(v.obtener("criticalidad"));
							Criticalidad criticalidad = parseCriticalidad(critStr);

							int prioridad = 0;
							Nodo nPrio = v.obtener("prioridad");
							if (nPrio != null) {
								try {
									prioridad = nPrio.comoEntero();
								} catch (Throwable te) {
									String pr = nPrio.comoCadena();
									try {
										prioridad = Integer.parseInt(pr.trim());
									} catch (Throwable ignore) {
										prioridad = 0;
									}
								}
							}

							Map<String, String> nombres = leerMapaIdiomas(v.obtener("nombres"));
							Map<String, String> resultados = leerMapaIdiomas(v.obtener("resultados"));

							VerificacionFirmasV0 ver = new VerificacionFirmasV0(id, nombres, resultados, criticalidad,
									prioridad, para_buscar, filtro);
							lista.add(ver);

						} catch (Throwable ite) {
							CrashDetectorLogger.log("[FirmasV0] ERROR en item i=" + i + ": " + ite.getClass().getName()
									+ " - " + ite.getMessage());
						}
					}
				} else {
					CrashDetectorLogger.log("[FirmasV0] 'verificaciones' sin elementos.");
				}
			} else {
				CrashDetectorLogger.log("[FirmasV0] 'verificaciones' es null.");
			}
		} catch (Throwable t) {
			CrashDetectorLogger
					.log("[FirmasV0] EXCEPCIÓN cargar(): " + t.getClass().getName() + " - " + t.getMessage());
		}

		CrashDetectorLogger.log("[FirmasV0] retorno lista con size=" + lista.size());
		return lista;
	}

	public static void guardar(List<VerificacionFirmasV0> verificaciones) throws IOException {
		final Path carpeta = Statics.carpeta;
		final Path ruta = carpeta.resolve(NOMBRE_ARCHIVO);

		if (!Files.exists(carpeta)) {
			Files.createDirectories(carpeta);
		}

		Nodo raiz = Json.crearObjeto();
		raiz.obtener("schema").poner(SCHEMA);

		Nodo arr = raiz.obtener("verificaciones");

		if (verificaciones != null) {
			for (VerificacionFirmasV0 v : verificaciones) {
				if (v == null)
					continue;

				Nodo item = Json.crearObjeto();
				item.obtener("id").poner(nz(v.id()));
				item.obtener("para_buscar").poner(nz(v.para_buscar));
				item.obtener("filtro").poner(v.filtro != null ? nz(v.filtro.id) : "");
				item.obtener("criticalidad").poner(v.criticalidad != null ? v.criticalidad.nombre : "ADVERTENCIA");
				item.obtener("prioridad").poner((int) v.prioridad());

				Nodo nombres = Json.crearObjeto();
				for (Map.Entry<String, String> e : v.nombres_por_codigo.entrySet()) {
					String codigo = VerificacionFirmasV0.normalizarCodigoIdioma(e.getKey());
					String valor = nz(e.getValue());
					if (codigo != null && !valor.isEmpty()) {
						nombres.obtener(codigo).poner(valor);
					}
				}
				item.obtener("nombres").poner(nombres);

				Nodo resultados = Json.crearObjeto();
				for (Map.Entry<String, String> e : v.resultados_por_codigo.entrySet()) {
					String codigo = VerificacionFirmasV0.normalizarCodigoIdioma(e.getKey());
					String valor = nz(e.getValue());
					if (codigo != null && !valor.isEmpty()) {
						resultados.obtener(codigo).poner(valor);
					}
				}
				item.obtener("resultados").poner(resultados);

				arr.agregar(item);
			}
		}

		String out = raiz.escribir();
		Files.write(ruta, out.getBytes(StandardCharsets.UTF_8));
	}

	private static Map<String, String> leerMapaIdiomas(Nodo nodoObjeto) {
		Map<String, String> mapa = new LinkedHashMap<String, String>();

		if (nodoObjeto == null) {
			return mapa;
		}

		for (String codigo : codigosIdiomaParaLectura()) {
			String valor = val(nodoObjeto, codigo);
			if (valor != null && !valor.trim().isEmpty()) {
				String normalizado = VerificacionFirmasV0.normalizarCodigoIdioma(codigo);
				if (normalizado != null) {
					mapa.put(normalizado, valor);
				}
			}
		}

		return mapa;
	}

	private static List<String> codigosIdiomaParaLectura() {
		LinkedHashSet<String> codigos = new LinkedHashSet<String>();

		try {
			codigos.addAll(Idioma.codigosRegistrados());
		} catch (Throwable t) {
			// ignorar
		}

		codigos.add("es");
		codigos.add("en");
		codigos.add("ar");
		codigos.add("pt");
		codigos.add("fa");
		codigos.add("ru");
		codigos.add("zh");
		codigos.add("eo");
		codigos.add("ja");
		codigos.add("ko");
		codigos.add("uk");

		// Compatibilidad con archivos viejos
		codigos.add("jp");
		codigos.add("kp");

		return new ArrayList<String>(codigos);
	}

	public static class Builder {
		private final List<VerificacionFirmasV0> lista = new ArrayList<VerificacionFirmasV0>();

		public Builder agregar(VerificacionFirmasV0 v) {
			if (v != null) {
				lista.add(v);
			}
			return this;
		}

		public List<VerificacionFirmasV0> build() {
			return new ArrayList<VerificacionFirmasV0>(lista);
		}

		public void guardar() throws IOException {
			FirmasV0.guardar(lista);
		}
	}

	private static String seguro(Nodo n) {
		if (n == null) {
			return "";
		}
		try {
			String s = n.comoCadena();
			return s == null ? "" : s;
		} catch (Throwable te) {
			return "";
		}
	}

	private static String val(Nodo obj, String key) {
		if (obj == null || key == null) {
			return "";
		}
		try {
			Nodo n = obj.obtener(key);
			if (n == null) {
				return "";
			}
			String s = n.comoCadena();
			return s == null ? "" : s;
		} catch (Throwable te) {
			return "";
		}
	}

	private static Criticalidad parseCriticalidad(String s) {
		if (s == null) {
			return Criticalidad.ADVERTENCIA;
		}
		String c = s.trim().toUpperCase();
		if ("FATAL".equals(c)) {
			return Criticalidad.FATAL;
		}
		if ("ERROR".equals(c)) {
			return Criticalidad.ERROR;
		}
		return Criticalidad.ADVERTENCIA;
	}

	private static String nz(String s) {
		return s == null ? "" : s;
	}
}