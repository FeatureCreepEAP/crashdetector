package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

public class ModsDuplicadosModLauncher implements Verificaciones {

	private boolean activado = false;
	private final CDStringBuilder mensaje = new CDStringBuilder();

	// Indicador de que el log contiene mensajes de mods duplicados.
	private boolean hayDuplicados = false;
	// Para no repetir la cabecera explicativa varias veces.
	private boolean cabeceraAñadida = false;

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Detectar de forma global si en el log hay mensajes de mods duplicados.
		hayDuplicados = contenidoConsola.contains("Found duplicate mods")
				|| contenidoConsola.contains("Found more than one mod with modid");

		// Si no hay duplicados, no hacemos nada más.
		if (!hayDuplicados) {
			return;
		}

		// Añadir la cabecera una sola vez antes de procesar líneas individuales.
		if (!cabeceraAñadida) {
			mensaje.append(MonitorDePID.idioma.no_tienes_las_dependencias_necesarias()).append(Verificaciones.nl_html);
			cabeceraAñadida = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si globalmente no se detectaron duplicados, no hay nada que hacer por línea.
		if (!hayDuplicados) {
			return;
		}

		String lineaActual = linea;

		if (lineaActual.contains("Mod ID") && lineaActual.contains("from mod files")) {
			String mensajeMod = MonitorDePID.idioma.modlauncher_mods_duplicado(lineaActual);
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			// Añadir mensaje + enlace en la misma línea
			mensaje.append(mensajeMod).append(" ").append(enlace).append(Verificaciones.nl_html);
			activado = true;
		} else if (lineaActual.contains(
				"Found more than one mod with modid \"crash_assistant\". Crash Assistant is duplicated. Crashing!")) {
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			mensaje.append("crash_assistant").append(" ").append(enlace).append(Verificaciones.nl_html);
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
		return 1050.0f;
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

//  @Override
//  public QuickFix solucion() {
//      QuickFix.Builder builder = new QuickFix.Builder(MonitorDePID.idioma.nombre_de_mods_duplicados_modlauncher());
//      
//      if (!activado || rutasDeMods.isEmpty()) {
//          builder.agregarEtiqueta(MonitorDePID.idioma.no_se_encontraron_mods_duplicados());
//          return builder.construir();
//      }
//
//      // Crear botones para eliminar cada mod duplicado
//      for (String rutaJar : rutasDeMods) {
//          String textoBoton = MonitorDePID.idioma.eliminar_jar() + ": " + rutaJar;
//          
//          builder.agregarBoton(textoBoton, retener -> {
//              try {
//                  EliminadorDeMod.eliminarMod(rutaJar);
//                  
//                  // Mostrar mensaje solo si hay GUI
//                  if (!EliminadorDeMod.esModoHeadless()) {
//                      JOptionPane.showMessageDialog(null,
//                          MonitorDePID.idioma.jar_eliminado_exitosamente() + ": " + rutaJar,
//                          MonitorDePID.idioma.exito(),
//                          JOptionPane.INFORMATION_MESSAGE);
//                  }
//              } catch (Exception e) {
//                  // Log y mostrar mensaje de error
//                  CrashDetectorLogger.logException(e);
//                  
//                  if (!EliminadorDeMod.esModoHeadless()) {
//                      JOptionPane.showMessageDialog(null,
//                          MonitorDePID.idioma.error_al_eliminar_jar() + ": " + rutaJar,
//                          MonitorDePID.idioma.error(),
//                          JOptionPane.ERROR_MESSAGE);
//                  }
//              }
//          });
//      }
//
//      return builder.construir();
//  }

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "mods_duplicados_modlauncher";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}
