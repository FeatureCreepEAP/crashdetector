package com.asbestosstar.crashdetector.analizador.firmas.v0;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.firmas.FiltrodeCodice;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * Parser / escritor / builder para el esquema V0 (schema = 0) de codice.json
 *
 * Formato esperado: { "schema": 0, "verificaciones": [ { "id": "string",
 * "para_buscar": "string", "filtro": "id_de_filtro", "criticalidad":
 * "ADVERTENCIA|ERROR|FATAL", "prioridad": 10, "nombres": {"ar": "...","zh":
 * "...","kp": "...","es": "...","eo": "...","en": "...","jp": "...","fa":
 * "...","pt": "...","ru": "..."}, "resultados": {"ar": "...","zh": "...","kp":
 * "...","es": "...","eo": "...","en": "...","jp": "...","fa": "...","pt":
 * "...","ru": "..."} } ] }
 *
 * Compatibilidad: Java 8 (sin usar APIs posteriores como Files.readString).
 */
public class FirmasV0 {

	public static final int SCHEMA = 0;
	private static final String NOMBRE_ARCHIVO = "firmas.json";

	/**
	 * Carga verificaciones desde codice.json. Si no existe o si el schema no es 0,
	 * retorna lista vacía.
	 */
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

			// Schema
			Nodo nSchema = raiz != null ? raiz.obtener("schema") : null;

			int schema;
			try {
				schema = (nSchema == null) ? SCHEMA : nSchema.comoEntero();
			} catch (Throwable te) {
				// Intento parsear desde cadena si vino en minúsculas o texto
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

			// verificaciones
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

				if (tam > 0) {
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

							// nombres
							Nodo nombres = v.obtener("nombres");
							String nombre_ar = val(nombres, "ar");
							String nombre_zh = val(nombres, "zh");
							String nombre_kp = val(nombres, "kp");
							String nombre_es = val(nombres, "es");
							String nombre_eo = val(nombres, "eo");
							String nombre_en = val(nombres, "en");
							String nombre_jp = val(nombres, "jp");
							String nombre_fa = val(nombres, "fa");
							String nombre_pt = val(nombres, "pt");
							String nombre_ru = val(nombres, "ru");

							// resultados
							Nodo resultados = v.obtener("resultados");
							String resultado_ar = val(resultados, "ar");
							String resultado_zh = val(resultados, "zh");
							String resultado_kp = val(resultados, "kp");
							String resultado_es = val(resultados, "es");
							String resultado_eo = val(resultados, "eo");
							String resultado_en = val(resultados, "en");
							String resultado_jp = val(resultados, "jp");
							String resultado_fa = val(resultados, "fa");
							String resultado_pt = val(resultados, "pt");
							String resultado_ru = val(resultados, "ru");

							VerificacionFirmasV0 ver = new VerificacionFirmasV0(id, nombre_ar, resultado_ar, nombre_zh,
									resultado_zh, nombre_kp, resultado_kp, nombre_es, resultado_es, nombre_eo,
									resultado_eo, nombre_en, resultado_en, nombre_jp, resultado_jp, nombre_fa,
									resultado_fa, nombre_pt, resultado_pt, nombre_ru, resultado_ru, criticalidad,
									prioridad, para_buscar, filtro);
							lista.add(ver);
						} catch (Throwable ite) {
							CrashDetectorLogger.log("[CodiceV0] ERROR en item i=" + i + ": " + ite.getClass().getName()
									+ " - " + ite.getMessage());
						}
					}
				} else {
					CrashDetectorLogger.log("[CodiceV0] 'verificaciones' sin elementos.");
				}
			} else {
				CrashDetectorLogger.log("[CodiceV0] 'verificaciones' es null.");
			}
		} catch (Throwable t) {
			CrashDetectorLogger
					.log("[CodiceV0] EXCEPCIÓN cargar(): " + t.getClass().getName() + " - " + t.getMessage());
		}

		CrashDetectorLogger.log("[CodiceV0] retorno lista con size=" + lista.size());
		return lista;
	}

	/**
	 * Guarda la lista de verificaciones en codice.json con schema=0. Crea
	 * carpeta/archivo si no existen.
	 */
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
				item.obtener("prioridad").poner((int) v.prioridad);

				Nodo nombres = Json.crearObjeto();
				nombres.obtener("ar").poner(nz(v.nombre_ar));
				nombres.obtener("zh").poner(nz(v.nombre_zh));
				nombres.obtener("kp").poner(nz(v.nombre_kp));
				nombres.obtener("es").poner(nz(v.nombre_es));
				nombres.obtener("eo").poner(nz(v.nombre_eo));
				nombres.obtener("en").poner(nz(v.nombre_en));
				nombres.obtener("jp").poner(nz(v.nombre_jp));
				nombres.obtener("fa").poner(nz(v.nombre_fa));
				nombres.obtener("pt").poner(nz(v.nombre_pt));
				nombres.obtener("ru").poner(nz(v.nombre_ru));
				item.obtener("nombres").poner(nombres);

				Nodo resultados = Json.crearObjeto();
				resultados.obtener("ar").poner(nz(v.resultado_ar));
				resultados.obtener("zh").poner(nz(v.resultado_zh));
				resultados.obtener("kp").poner(nz(v.resultado_kp));
				resultados.obtener("es").poner(nz(v.resultado_es));
				resultados.obtener("eo").poner(nz(v.resultado_eo));
				resultados.obtener("en").poner(nz(v.resultado_en));
				resultados.obtener("jp").poner(nz(v.resultado_jp));
				resultados.obtener("fa").poner(nz(v.resultado_fa));
				resultados.obtener("pt").poner(nz(v.resultado_pt));
				resultados.obtener("ru").poner(nz(v.resultado_ru));
				item.obtener("resultados").poner(resultados);

				arr.agregar(item);
			}
		}

		// Java 8: escribir como bytes UTF-8
		String out = raiz.escribir();
		Files.write(ruta, out.getBytes(StandardCharsets.UTF_8));
	}

	/** Builder sencillo para armar y persistir listas V0. */
	public static class Builder {
		private final List<VerificacionFirmasV0> lista = new ArrayList<VerificacionFirmasV0>();

		/** Agrega una verificación a la lista. */
		public Builder agregar(VerificacionFirmasV0 v) {
			if (v != null)
				lista.add(v);
			return this;
		}

		/** Retorna una copia de la lista construida. */
		public List<VerificacionFirmasV0> build() {
			return new ArrayList<VerificacionFirmasV0>(lista);
		}

		/** Guarda inmediatamente la lista construida en codice.json (schema 0). */
		public void guardar() throws IOException {
			FirmasV0.guardar(lista);
		}
	}

	// ---- Utilidades privadas (Java 8 friendly) ----

	/** Valor de clave en objeto; si falta, cadena vacía. */
	private static String val(Nodo obj, String clave) {
		if (obj == null)
			return "";
		Nodo n = obj.obtener(clave);
		return (n == null) ? "" : n.comoCadena();
	}

	/** Convierte Nodo a cadena segura. */
	private static String seguro(Nodo n) {
		return (n == null) ? "" : n.comoCadena();
	}

	/** Reemplaza null por "" para serializar. */
	private static String nz(String s) {
		return (s == null) ? "" : s;
	}

	/**
	 * Parse de criticalidad con respaldo a ADVERTENCIA. Soporta ADVERTENCIA, ERROR,
	 * FATAL.
	 */
	public static Criticalidad parseCriticalidad(String s) {
		if (s == null)
			return Criticalidad.ADVERTENCIA;
		String up = s.trim().toUpperCase();
		if ("ERROR".equals(up))
			return Criticalidad.ERROR;
		if ("FATAL".equals(up))
			return Criticalidad.FATAL;
		return Criticalidad.ADVERTENCIA;
	}
}
