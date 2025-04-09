package com.asbestosstar.crashdetectormc.analyzador;

import java.util.ArrayList;
import java.util.List;

public class Analyzador {

	public static List<Verificaciones> verificaciones= new ArrayList<Verificaciones>();

	
	static {
		verificaciones.add(new Drivers());
		verificaciones.add(new EarlyWindow());
		verificaciones.add(new NoTieneDependencias());
	}
	
	
}
