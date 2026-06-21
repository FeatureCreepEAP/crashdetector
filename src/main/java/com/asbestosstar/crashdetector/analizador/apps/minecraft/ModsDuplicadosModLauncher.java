package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ModsDuplicadosModLauncher implements Verificaciones {

	private boolean activado = false;
	private final CDStringBuilder mensaje = new CDStringBuilder();

	// Para no repetir la cabecera explicativa varias veces.
	private boolean cabeceraAñadida = false;

	private static final String FOUND_DUPLICATE_MODS = "Found duplicate mods";
	private static final String FOUND_MORE_THAN_ONE_MOD_WITH_MODID = "Found more than one mod with modid";
	private static final String MOD_ID = "Mod ID";
	private static final String FROM_MOD_FILES = "from mod files";
	private static final String CRASH_ASSISTANT_DUPLICADO = "Found more than one mod with modid \"crash_assistant\". Crash Assistant is duplicated. Crashing!";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FOUND_DUPLICATE_MODS, FOUND_MORE_THAN_ONE_MOD_WITH_MODID, MOD_ID,
				CRASH_ASSISTANT_DUPLICADO };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneDuplicados(evento.linea)) {
			asegurarCabecera();
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	private void asegurarCabecera() {
		if (!cabeceraAñadida) {
			// mensaje.append(MonitorDePID.idioma.no_tienes_las_dependencias_necesarias()).append(Verificaciones.nl_html);
			cabeceraAñadida = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		String lineaActual = linea;

		if (lineaActual.contains(MOD_ID) && lineaActual.contains(FROM_MOD_FILES)) {
			String mensajeMod = MonitorDePID.idioma.modlauncher_mods_duplicado(lineaActual);
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			// Añadir mensaje + enlace en la misma línea
			mensaje.append(mensajeMod).append(" ").append(enlace).append(Verificaciones.nl_html);
			activado = true;
		} else if (lineaActual.contains(CRASH_ASSISTANT_DUPLICADO)) {
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			mensaje.append("crash_assistant").append(" ").append(enlace).append(Verificaciones.nl_html);
			activado = true;
		}
	}

	private boolean lineaContieneDuplicados(String linea) {
		return linea.contains(FOUND_DUPLICATE_MODS) || linea.contains(FOUND_MORE_THAN_ONE_MOD_WITH_MODID)
				|| linea.contains(CRASH_ASSISTANT_DUPLICADO)
				|| (linea.contains(MOD_ID) && linea.contains(FROM_MOD_FILES));
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
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "mods_duplicados_modlauncher";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { FOUND_DUPLICATE_MODS };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}