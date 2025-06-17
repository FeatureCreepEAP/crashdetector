package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

public class OpcionesJavaGCInvalidas implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;

        String patron = "Conflicting collector combinations in option list";
        if (contenidoConsola.matches("(?sm).*" + patron + ".*")) { // Usa regex multilinea [[8]]
            this.mensaje = MonitorDePID.idioma.errorOpcionesGCJava() + Verificaciones.nl_html;
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new OpcionesJavaGCInvalidas(); 
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 500f; 
    }

    @Override
    public String mensaje() {
        return mensaje;
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_opciones_java_gc_invalidas();
	}
    
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
            .construir();
    }
    
    
}