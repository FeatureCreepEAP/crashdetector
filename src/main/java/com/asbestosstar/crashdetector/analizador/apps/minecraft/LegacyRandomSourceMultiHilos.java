package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

public class LegacyRandomSourceMultiHilos implements Verificaciones{

	boolean activado=false;
	
	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub
		if(consola.contenido_verificar.contains("net.minecraft.ReportedException: Accessing LegacyRandomSource from multiple threads")) {
			activado=true;
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new LegacyRandomSourceMultiHilos();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

	@Override
	public float prioridad() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public String mensaje() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.mensajeLegacyRandomSourceMultiHilos();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombreLegacyRandomSourceMultiHilos();
	}

	@Override
	public QuickFix solucion() {
		// TODO Auto-generated method stub
        return new Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod("Unsafe World Random Access Detector"))
                .construir();

	
	}

}
