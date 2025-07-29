package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

public class BloqueTeselado implements Verificaciones {

    private boolean activado = false;
    private String mensaje = ""; // Almacena el mensaje HTML

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contenido_verificar;

        String patron = "(?sm).*Tesselating block in world$.*";
        if (contenidoConsola.matches(patron)) {
            mensaje = MonitorDePID.idioma.errorDeBloqueTeselado() + Verificaciones.nl_html;
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new BloqueTeselado();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 500.0f;
 }

    @Override
    public String mensaje() {
        return mensaje; 
    }
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_bloque_teselado();
	}
	
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
            .construir();
    }
}