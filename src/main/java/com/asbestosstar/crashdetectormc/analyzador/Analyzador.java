package com.asbestosstar.crashdetectormc.analyzador;

import java.util.LinkedHashSet;

public class Analyzador {

	public LinkedHashSet<Verificaciones> verificaciones = new LinkedHashSet<Verificaciones>();

	public Analyzador(VerificacionDeStackTrace verificacion_de_stacktrace) {
		verificaciones.add(new ModulesDuplicadosJavaModulePlatform());
		verificaciones.add(new FaltaModuleJPMS());
		verificaciones.add(new ModsDuplicadosModLauncher());
		verificaciones.add(new LenguajeProveedorCheck());

		
		
		//verificaciones.add(verificacion_de_stacktrace); No necesitemos aqui, es en la clase Consola antes de esta contructor // Para Configs de SpongeMixin problematicos
		verificaciones.add(new FabricMCRuntimeErrorProvidedBy());
		verificaciones.add(new MCForgeModsSuspechoso());
		verificaciones.add(new JavaVersiones());
		verificaciones.add(new FaltasClases(verificacion_de_stacktrace));
		verificaciones.add(new Drivers());
		verificaciones.add(new EarlyWindow());
		verificaciones.add(new NecesitasSodium());
		verificaciones.add(new FaltasDependenciasModLaunche());
		verificaciones.add(new KubeJSResourcePack());
		verificaciones.add(new Segundo60Tick());
		verificaciones.add(new NoTieneMemoria());
		verificaciones.add(new Theseus());
		verificaciones.add(new CursedConsola());
		verificaciones.add(new NullPointer());
		verificaciones.add(new ContentoDeTraces(verificacion_de_stacktrace));
		verificaciones.add(new AdvertenciaFaltasClases());
		verificaciones.add(new MalwareFalsoCrashAssistant());

		//verificaciones.add(new ObjetoDeRegistroNoPresente());

				
		
		
		
	}

}
