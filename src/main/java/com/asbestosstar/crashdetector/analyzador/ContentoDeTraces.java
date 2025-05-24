package com.asbestosstar.crashdetector.analyzador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

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

	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub

		VerificacionDeStackTrace vdst = consola.verificacion_de_stacktrace;
		StringBuilder constructor = new StringBuilder();
		HashMap<String, Boolean> jar_nombres = new HashMap<String, Boolean>();
		if (!vdst.jars.isEmpty()) {
			activado = true;
			for (Map.Entry<String, Boolean> jar : vdst.jars.entrySet()) {

				// TODO mejor a FATAL
				String[] lvl_info_arr = jar.getKey().split(Pattern.quote(MonitorDePID.idioma.nivel()));
				String lvl_info = "";
				if (lvl_info_arr.length > 1) {
					lvl_info = MonitorDePID.idioma.nivel() + lvl_info_arr[1];
				} else {
					System.out.println(lvl_info_arr[0]);
				}
				String jar_nombre = jar.getKey().split(".jar")[0] + ".jar " + lvl_info;
				if (!todos_jars.contains(jar_nombre)) {
					todos_jars.add(jar_nombre);
					jar_nombres.put(jar_nombre, jar.getValue());
				}

			}
		}

		if (!jar_nombres.isEmpty()) {
			constructor.append(MonitorDePID.idioma.problematico_jar()).append(nl_html);
			for (Map.Entry<String, Boolean> jar : jar_nombres.entrySet()) {
				if (jar.getValue()) {
					constructor.append(MonitorDePID.idioma.possibladad_fatal());
				}
				constructor.append(jar.getKey()).append(nl_html);
			}

		}

		HashMap<String, Boolean> modids_filt = new HashMap<String, Boolean>();

		if (!vdst.modids.isEmpty()) {
			activado = true;
			for (Map.Entry<String, Boolean> modid : vdst.modids.entrySet()) {

				if (!todos_modids.contains(modid.getKey())) {
					todos_modids.add(modid.getKey());
					modids_filt.put(modid.getKey(), modid.getValue());
				}
			}
		}

		if (!modids_filt.isEmpty()) {
			constructor.append(MonitorDePID.idioma.modids_problematicos()).append(nl_html);
			for (Map.Entry<String, Boolean> modid : modids_filt.entrySet()) {
				if (modid.getValue()) {
					constructor.append(MonitorDePID.idioma.possibladad_fatal());
				}
				constructor.append(modid.getKey()).append(nl_html);

			}

		}

		HashMap<String, Boolean> packs_filt = new HashMap<String, Boolean>();

		if (!vdst.packs.isEmpty()) {
			activado = true;
			for (Map.Entry<String, Boolean> pack : vdst.packs.entrySet()) {

				if (!todos_packs.contains(pack.getKey())) {
					todos_packs.add(pack.getKey());
					packs_filt.put(pack.getKey(), pack.getValue());
				}

			}
		}

		if (!packs_filt.isEmpty()) {
			constructor.append(MonitorDePID.idioma.packages_problematicos()).append(nl_html);
			for (Map.Entry<String, Boolean> pack : packs_filt.entrySet()) {
				if (pack.getValue()) {
					constructor.append(MonitorDePID.idioma.possibladad_fatal());
				}
				constructor.append(pack.getKey()).append(nl_html);

			}

		}

		List<String> configs_inject = new LinkedList<String>();
		for (String content : VerificacionDeStackTrace.inverso(vdst.braceContentos)) {
			for (String ind : VerificacionDeStackTrace.eliminarDuplicados(content.split(","))) {
				String limpiado = ind.replace("pl:runtimedistcleaner:A", "").replace("re:classloading", "")
						.replace("pl:mixin:APP:", "").replace("re:computing_frames", "")
						.replace("pl:accesstransformer:B", "").replace("pl:mixin:A", "").replace("xf:fml", "")
						.replace("featurecreep", "").replace("re:mixin", "").replace("xf:crashdetector:default", "");
				if (!configs_inject.contains(limpiado) && !limpiado.isEmpty()) {
					configs_inject.add(limpiado);
				}
			}
		}

		List<String> sm_configs_filt = new ArrayList<String>();
		if (!configs_inject.isEmpty()) {
			activado = true;
			int tamano = 0;
			for (String conf : configs_inject) {
				if (!todos_sm_configs.contains(conf)) {
					todos_sm_configs.add(conf);
					if (tamano <= 20) {
						sm_configs_filt.add(conf);
						tamano++;
					}
				}

			}
		}

		if (!sm_configs_filt.isEmpty()) {
			constructor.append(MonitorDePID.idioma.corchetes_ondulados()).append(nl_html);
			for (String conf : sm_configs_filt) {
				constructor.append(conf.split(".json")[0].replace(".mixins", "").replace(".mixin", "")
						.replace("mixins.", "").replace("mixin.", "")).append(nl_html);

			}

		}

		if (!constructor.toString().isEmpty()) {
			contento.put(consola.archivo.getFileName().toString(), constructor);
		}

	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new ContentoDeTraces();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

	@Override
	public float prioridad() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public String mensaje() {
		// TODO Auto-generated method stub
		StringBuilder constructor = new StringBuilder();
		for (Map.Entry<String, StringBuilder> entry : contento.entrySet()) {
			
			String titilo = "<span style='color: #" + Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas()
					+ "; font-weight: bold;'>";
			constructor.append(titilo).append(entry.getKey()).append("").append("</span>")
					.append(Verificaciones.nl_html);
			
			constructor.append(entry.getValue().toString());

		}

		return constructor.toString();
	}
	
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_contento_de_stacktrace();
	}

}
