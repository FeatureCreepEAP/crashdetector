package com.asbestosstar.crashdetectormc.analyzador;

import java.util.ArrayList;
import java.util.List;

public class Analyzador {

	public static List<Verificaciones> verificaciones= new ArrayList<Verificaciones>();

	
	static {
		verificaciones.add(new Drivers());
		verificaciones.add(new EarlyWindow());
		verificaciones.add(new FaltasDependenciasModLaunche());
		verificaciones.add(new FaltasClases());
		verificaciones.add(new FabricMCRuntimeErrorProvidedBy());
		verificaciones.add(new ModulesDuplicadosJavaModulePlatform());
		verificaciones.add(new ModsDuplicadosModLauncher());
		verificaciones.add(new MCForgeModsSuspechoso());
		verificaciones.add(new VerificacionDeStackTrace());
	}
	
	
}
