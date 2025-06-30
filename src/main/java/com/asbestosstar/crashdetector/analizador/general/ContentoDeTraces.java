package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.BiMap.DoubleKey;
import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Para el contento en "at" en Traces
 */
public class ContentoDeTraces implements Verificaciones {

	public boolean activado = false;

	public Set<String> todos_jars = new HashSet<String>();
	public Set<String> todos_modids = new HashSet<String>();
	public Set<String> todos_packs = new HashSet<String>();
	public Set<String> todos_sm_configs = new HashSet<String>();

	public Map<String, StringBuilder> contento = new HashMap<String, StringBuilder>();

	private static List<Double> parseNivelNumerico(String texto) {
	    List<Double> numeros = new ArrayList<>();
	    if (texto == null) return numeros;

	    Pattern pattern = Pattern.compile("\\b(\\d+[\\.,]?\\d*)\\b");
	    Matcher matcher = pattern.matcher(texto);

	    while (matcher.find()) {
	        String grupo = matcher.group(1).replace(',', '.');
	        try {
	            numeros.add(Double.parseDouble(grupo));
	        } catch (NumberFormatException ignored) {}
	    }

	    return numeros;
	}
	
	

    /**
     * Extrae los números [nivel, línea, …] a partir de la sub‑cadena posterior al
     * último espacio (donde se encuentra «nivelX,Y»). No interpreta comas como
     * decimales, sino como separador.
     */
    private static List<Integer> extraerNumerosNivel(String texto) {
        List<Integer> nums = new ArrayList<>();
        if (texto == null) return nums;

        int idx = texto.lastIndexOf(' ');
        String segmento = idx == -1 ? texto : texto.substring(idx + 1);

        Matcher m = Pattern.compile("\\d+").matcher(segmento);
        while (m.find()) {
            try {
                nums.add(Integer.parseInt(m.group()));
            } catch (NumberFormatException ignored) {}
        }
        return nums;
    }

    /** Comparator numérico por nivel → línea → resto. */
    private static <T> Comparator<T> compNumerico(java.util.function.Function<T, String> extractor) {
        return Comparator
                .comparingInt((T e) -> {
                    List<Integer> n = extraerNumerosNivel(extractor.apply(e));
                    return n.isEmpty() ? Integer.MAX_VALUE : n.get(0);
                })
                .thenComparingInt(e -> {
                    List<Integer> n = extraerNumerosNivel(extractor.apply(e));
                    return n.size() > 1 ? n.get(1) : Integer.MAX_VALUE;
                })
                .thenComparingInt(e -> {
                    List<Integer> n = extraerNumerosNivel(extractor.apply(e));
                    return n.size() > 2 ? n.get(2) : Integer.MAX_VALUE;
                });
    }

    /** Ordena una BiMap por el campo key1 («nivelX,Y»). */
    private static <K> List<Entry<DoubleKey<K, String>, Boolean>> ordenarPorNivel(BiMap<K, String, Boolean> mapa) {
        List<Entry<DoubleKey<K, String>, Boolean>> lista = new ArrayList<>(mapa.entrySet());
        lista.sort(compNumerico(e -> e.getKey().key1));
        return lista;
    }
	
	
	
	@Override
	public void verificar(Consola consola) {
		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;
		StringBuilder constructor = new StringBuilder();

		// Añadir mensajes de la consola si existen
		String mensajeFatal = consola.obtainerMensajeFatalUltimaTrace();
		if (mensajeFatal != null && !mensajeFatal.trim().isEmpty()) {
			constructor.append(nl_html).append("<strong>")
					.append(MonitorDePID.idioma.mensaje_de_trace_fatal_ultima_no_traductado()).append("</strong> ")
					.append(mensajeFatal).append(nl_html);
		}

		String mensajeNormal = consola.obtainerMensajeUltimaTrace();
		if (mensajeNormal != null && !mensajeNormal.trim().isEmpty() && !mensajeNormal.equals(mensajeFatal)) {
			constructor.append(nl_html).append("<strong>")
					.append(MonitorDePID.idioma.mensaje_de_trace_ultima_no_traductado()).append("</strong> ")
					.append(mensajeNormal).append(nl_html);
		}

		Map<String, Boolean> jar_nombres = new HashMap<>();
		if (!vdst.jars.isEmpty()) {
			activado = true;
			for (Map.Entry<String, Boolean> jar : vdst.jars.entrySet()) {
				String[] lvl_info_arr = jar.getKey().split(Pattern.quote(MonitorDePID.idioma.nivel()));
				String lvl_info = "";
				if (lvl_info_arr.length > 1) {
					lvl_info = MonitorDePID.idioma.nivel() + lvl_info_arr[1];
				}
				String jar_nombre = jar.getKey().split(".jar")[0] + ".jar " + lvl_info;
				if (!todos_jars.contains(jar_nombre)) {
					todos_jars.add(jar_nombre);
					jar_nombres.put(jar_nombre, jar.getValue());
				}
			}
		}

        if (!jar_nombres.isEmpty()) {
            activado = true;
            constructor.append(MonitorDePID.idioma.problematico_jar()).append(nl_html).append("<ul>");
            jar_nombres.entrySet().stream().sorted(compNumerico(Map.Entry::getKey)).forEach(e -> {
            	constructor.append("<li>");
                if (e.getValue()) constructor.append(MonitorDePID.idioma.possibladad_fatal());
                constructor.append(e.getKey()).append("</li>");
            });
            constructor.append("</ul>");
        }

		BiMap<String, String, Boolean> modids_filt = new BiMap<>();

		if (!vdst.modids.isEmpty()) {
			activado = true;
			for (Entry<DoubleKey<String, String>, Boolean> modid : vdst.modids.entrySet()) {
				if (!todos_modids.contains(modid.getKey().key0)) {
					todos_modids.add(modid.getKey().key0);
					modids_filt.put(modid.getKey().key0, modid.getKey().key1, modid.getValue());
				}
			}
		}

		if (!modids_filt.isEmpty()) {
			Buscardor.cargar();
			constructor.append(nl_html).append(MonitorDePID.idioma.modids_problematicos()).append(nl_html)
					.append("<ul>");

			for (Entry<DoubleKey<String, String>, Boolean> modid : ordenarPorNivel(modids_filt)) {
				String llev = modid.getKey().key0;
				String jars_de_modid_string = "";
				List<String> jars_de_modids = Buscardor.obternerModsConNombre(llev);
				if (!jars_de_modids.isEmpty()) {
					jars_de_modid_string = " (" + String.join(", ", jars_de_modids) + ")";
				}
				StringBuilder item = new StringBuilder("<li>");
				if (modid.getValue()) {
					item.append(MonitorDePID.idioma.possibladad_fatal());
				}
				item.append(llev).append(jars_de_modid_string).append(" ").append(modid.getKey().key1).append("</li>");
				constructor.append(item.toString());
			}
			constructor.append("</ul>");
		}

		BiMap<String, String, Boolean> packs_filt = new BiMap<>();

		if (!vdst.packs.isEmpty()) {
			activado = true;
			for (Entry<DoubleKey<String, String>, Boolean> pack : vdst.packs.entrySet()) {
				if (!todos_packs.contains(pack.getKey().key0)) {
					todos_packs.add(pack.getKey().key0);
					packs_filt.put(pack.getKey().key0, pack.getKey().key1, pack.getValue());
				}
			}
		}

		if (!packs_filt.isEmpty()) {
			Buscardor.cargar();
			constructor.append(nl_html).append(MonitorDePID.idioma.packages_problematicos()).append(nl_html)
					.append("<ul>");

			for (Entry<DoubleKey<String, String>, Boolean> pack : ordenarPorNivel(packs_filt)) {
				String llev = pack.getKey().key0;
				String jars_de_pack_string = "";
				String pack_de_llev = obtenerRutaDePaquete(llev);
				List<String> jars_de_packs = Buscardor
						.obternerUbicaciones(Buscardor.buscarModsConTermino(pack_de_llev));
				if (!jars_de_packs.isEmpty()) {
					jars_de_pack_string = " (" + String.join(",", jars_de_packs) + ")";
				}

				StringBuilder item = new StringBuilder("<li>");
				if (pack.getValue()) {
					item.append(MonitorDePID.idioma.possibladad_fatal());
				}
				item.append(llev).append(jars_de_pack_string).append(" ").append(pack.getKey().key1).append("</li>");
				constructor.append(item.toString());
			}
			constructor.append("</ul>");
		}

		BiMap<String, String, Boolean> configs_inject = new BiMap<>();
		for (Entry<DoubleKey<String, String>, Boolean> brace : vdst.braces.entrySet()) {
			for (String ind : VerificacionDeStackTrace.eliminarDuplicados(brace.getKey().key0.split(","))) {
				String limpiado = ind.replace("pl:runtimedistcleaner:A", "").replace("re:classloading", "")
						.replace("pl:mixin:APP:", "").replace("re:computing_frames", "")
						.replace("pl:accesstransformer:B", "").replace("pl:mixin:A", "").replace("xf:fml", "")
						.replace("featurecreep", "").replace("re:mixin", "").replace("xf:crashdetector:default", "");

				if (!limpiado.isEmpty()) {
					configs_inject.put(limpiado, brace.getKey().key1, brace.getValue());
				}
			}
		}

		BiMap<String, String, Boolean> sm_configs_filt = new BiMap<>();
		if (!configs_inject.isEmpty()) {
			activado = true;
			int count = 0;
			for (Entry<DoubleKey<String, String>, Boolean> cfg : configs_inject.entrySet()) {
				String nombre = cfg.getKey().key0;
				if (!todos_sm_configs.contains(nombre)) {
					todos_sm_configs.add(nombre);
					if (count < 20) {
						sm_configs_filt.put(nombre, cfg.getKey().key1, cfg.getValue());
						count++;
					}
				}
			}
		}

		if (!sm_configs_filt.isEmpty()) {
			constructor.append(nl_html).append(MonitorDePID.idioma.corchetes_ondulados()).append(nl_html)
					.append("<ul>");

			for (Entry<DoubleKey<String, String>, Boolean> cfg : ordenarPorNivel(sm_configs_filt)) {
				String cleanConf = cfg.getKey().key0.split("\\.json")[0].replace(".mixins", "").replace(".mixin", "")
						.replace("mixins.", "").replace("mixin.", "");

				StringBuilder item = new StringBuilder("<li>");
				if (cfg.getValue()) {
					item.append(MonitorDePID.idioma.possibladad_fatal());
				}
				item.append(cleanConf).append(" ").append(cfg.getKey().key1).append("</li>");
				constructor.append(item.toString());
			}
			constructor.append("</ul>");
		}

		if (!constructor.toString().isEmpty()) {
			contento.put(consola.archivo.getFileName().toString(), new StringBuilder(constructor.toString().trim()));
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ContentoDeTraces();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 10;
	}

	@Override
	public String mensaje() {
		StringBuilder constructor = new StringBuilder();
		for (Map.Entry<String, StringBuilder> entry : contento.entrySet()) {
			String titilo = "<span style='color: #" + Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas()
					+ "; font-weight: bold;'>";
			constructor.append(titilo).append(entry.getKey()).append("").append("</span>");
			constructor.append(entry.getValue().toString());
		}
		return constructor.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_contento_de_stacktrace();
	}

	/**
	 * Convierte una referencia completa de método a la ruta del paquete.
	 */
	private static String obtenerRutaDePaquete(String input) {
		if (input == null || input.trim().isEmpty()) {
			return "";
		}
		String antesDelParentesis = input.split("\\(", 2)[0].trim();
		antesDelParentesis = antesDelParentesis.replaceAll("\\$[^\\.]*", "");
		int ultimoIndicePunto = antesDelParentesis.lastIndexOf('.');
		if (ultimoIndicePunto <= 0) {
			return "";
		}
		String nombreCompletoClase = antesDelParentesis.substring(0, ultimoIndicePunto);
		String[] partesDelPaquete = nombreCompletoClase.split("\\.");
		StringBuilder rutaDelPaquete = new StringBuilder();
		for (int i = 0; i < partesDelPaquete.length; i++) {
			if (i > 0) {
				rutaDelPaquete.append("/");
			}
			rutaDelPaquete.append(partesDelPaquete[i]);
		}
		return rutaDelPaquete.toString();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
}
