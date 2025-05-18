package com.asbestosstar.crashdetectormc.analyzador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;

public class Analizador {

	/*
	 * Para Registrar
	 */
	public static HashSet<Verificaciones> verificaciones = new HashSet<Verificaciones>();

	/**
	 * para analizando
	 */
	public HashSet<Verificaciones> verificaciones_activados = new LinkedHashSet<Verificaciones>();

	static {
		verificaciones.add(new SpongeMixinConfigsProblematicos());
		verificaciones.add(new ModulesDuplicadosJavaModulePlatform());
		verificaciones.add(new FaltaModuleJPMS());
		verificaciones.add(new ModsDuplicadosModLauncher());
		verificaciones.add(new LenguajeProveedorCheck());
		verificaciones.add(new WaterMediaTL());

		verificaciones.add(new NoPuedeAnalizarJSONDeRegistro());
		verificaciones.add(new BloqueTeselado());

		// verificaciones.add(verificacion_de_stacktrace); No necesitemos aqui, es en la
		// clase Consola antes de esta contructor // Para Configs de SpongeMixin
		// problematicos
		verificaciones.add(new OptifineObsoleta());
		verificaciones.add(new ServicioDeModLauncherNoFunciona());
		verificaciones.add(new FabricMCRuntimeErrorProvidedBy());
		verificaciones.add(new MCForgeModsSuspechoso());
		verificaciones.add(new JavaVersiones());
		verificaciones.add(new FaltasClases());
		verificaciones.add(new Drivers());
		verificaciones.add(new ErrorDeMonitorLWJGL());
		verificaciones.add(new OpcionesJavaGCInvalidas());
		verificaciones.add(new ErrorConfiguracionMCForge());

		verificaciones.add(new EarlyWindow());
		verificaciones.add(new NecesitasSodium());
		verificaciones.add(new FaltasDependenciasModLaunche());
		verificaciones.add(new KubeJSResourcePack());
		verificaciones.add(new Segundo60Tick());
		verificaciones.add(new NoTieneMemoria());
		verificaciones.add(new Theseus());
		verificaciones.add(new CursedConsola());
		verificaciones.add(new NullPointer());
		verificaciones.add(new ContentoDeTraces());
		verificaciones.add(new AdvertenciaFaltasClases());
		// verificaciones.add(new MalwareFalsoCrashAssistant());

		// verificaciones.add(new ObjetoDeRegistroNoPresente());

	}

	public Analizador() {
		for (Verificaciones ver : verificaciones) {
			verificaciones_activados.add(ver.nueva());
		}
	}

	public void analizar(List<Consola> consolas) {
		// TODO Auto-generated method stub
		for (Consola consola : consolas) {
			consola.verificacion_de_stacktrace.reincinar();
			for (Verificaciones ver : verificaciones_activados) {
				ver.verificar(consola);
			}
		}
	}

	public Set<Verificaciones> organizar(Set<Verificaciones> vers) {
	    List<Verificaciones> ret = new ArrayList<>(vers);
	    ret.sort((v1, v2) -> Float.compare(v2.prioridad(), v1.prioridad()));
	    return new LinkedHashSet<>(ret);
	}

	public String toString() {
	    StringBuilder constructor = new StringBuilder();
	    constructor.append("<ol>");

	    for (Verificaciones ver : organizar(this.verificaciones_activados)) {
	        if (ver.activado()) {
	            constructor.append("<li>");
	            
	            String tituloColor = Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas();
	            constructor.append("<span style='color: #")
	                      .append(tituloColor)
	                      .append("; font-weight: bold;'>")
	                      .append(ver.nombre())
	                      .append("</span>");
	            
	            constructor.append("<br>")
	                      .append(ver.mensaje())
	                      .append("<hr style='border: 0; border-top: 1px solid #")
	                      .append(tituloColor)
	                      .append("; margin: 8px 0;' />")
	                      .append("</li>");
	        }
	    }

	    constructor.append("</ol>");
	    return constructor.toString();
	}

}
