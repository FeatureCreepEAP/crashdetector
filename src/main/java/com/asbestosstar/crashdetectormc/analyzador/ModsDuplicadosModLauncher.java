package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ModsDuplicadosModLauncher implements Verificaciones {

    private boolean activado = false;
    private final CDStringBuilder mensaje = new CDStringBuilder();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;


        if (contenidoConsola.contains("Found duplicate mods")) {
            mensaje.append(MonitorDePID.idioma.no_tienes_las_dependencias_necesitas())
                   .append(Verificaciones.nl_html);

            for (String linea : contenidoConsola.split(Verificaciones.nl)) {
                if (linea.contains("Mod ID") && linea.contains("from mod files")) {
                    mensaje.append(MonitorDePID.idioma.modlauncher_mods_duplicado(linea))
                           .append(Verificaciones.nl_html);
                }
            }
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ModsDuplicadosModLauncher();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1000.0f;
    }

    @Override
    public String mensaje() {
        return mensaje.toString();
    }
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_mods_duplicados_modlauncher();
	}


}