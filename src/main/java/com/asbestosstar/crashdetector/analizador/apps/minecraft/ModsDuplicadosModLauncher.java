package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class ModsDuplicadosModLauncher implements Verificaciones {

    private boolean activado = false;
    private final CDStringBuilder mensaje = new CDStringBuilder();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contenido_verificar;


        if (contenidoConsola.contains("Found duplicate mods")) {
            mensaje.append(MonitorDePID.idioma.no_tienes_las_dependencias_necesarias())
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

//    @Override
//    public QuickFix solucion() {
//        QuickFix.Builder builder = new QuickFix.Builder(MonitorDePID.idioma.nombre_de_mods_duplicados_modlauncher());
//        
//        if (!activado || rutasDeMods.isEmpty()) {
//            builder.agregarEtiqueta(MonitorDePID.idioma.no_se_encontraron_mods_duplicados());
//            return builder.construir();
//        }
//
//        // Crear botones para eliminar cada mod duplicado
//        for (String rutaJar : rutasDeMods) {
//            String textoBoton = MonitorDePID.idioma.eliminar_jar() + ": " + rutaJar;
//            
//            builder.agregarBoton(textoBoton, retener -> {
//                try {
//                    EliminadorDeMod.eliminarMod(rutaJar);
//                    
//                    // Mostrar mensaje solo si hay GUI
//                    if (!EliminadorDeMod.esModoHeadless()) {
//                        JOptionPane.showMessageDialog(null,
//                            MonitorDePID.idioma.jar_eliminado_exitosamente() + ": " + rutaJar,
//                            MonitorDePID.idioma.exito(),
//                            JOptionPane.INFORMATION_MESSAGE);
//                    }
//                } catch (Exception e) {
//                    // Log y mostrar mensaje de error
//                    CrashDetectorLogger.logException(e);
//                    
//                    if (!EliminadorDeMod.esModoHeadless()) {
//                        JOptionPane.showMessageDialog(null,
//                            MonitorDePID.idioma.error_al_eliminar_jar() + ": " + rutaJar,
//                            MonitorDePID.idioma.error(),
//                            JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            });
//        }
//
//        return builder.construir();
//    }
	
	
	 @Override
	    public QuickFix solucion() {
	        return new QuickFix.Builder(nombre())
	            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
	            .construir();
	    }


}