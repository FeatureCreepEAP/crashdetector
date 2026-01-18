package com.asbestosstar.crashdetector.analizador.general;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EliminadorDeMod;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ModulesDuplicadosJavaModulePlatform implements Verificaciones {

	private boolean activado = false;
	private final CDStringBuilder mensajes = new CDStringBuilder();
	private String paqueteProblematico;
	private final Map<String, String> enlacesPorPaquete = new HashMap<>();

	private boolean recolectando = false;
	private StringBuilder mensajeBuilder = null;
	private int indiceUltimaNoStack = -1;
	// Nuevo: almacenar consola para finalización
	private Consola consolaRef = null;
boolean mixinextras = false;
	
	
	@Override
	public void verificar(Consola consola) {
		String cont = consola.contenido_verificar;		
		
		
		
		// Finalizar cualquier bloque pendiente al final del procesamiento
		if (recolectando && consolaRef != null) {
			CrashDetectorLogger.log("Finalizando bloque pendiente al final del procesamiento");
			finalizarBloque(consolaRef, indiceUltimaNoStack >= 0 ? indiceUltimaNoStack : 0);
		}
		consolaRef = null;
	}

	@Override
	public void verificar(Consola consola, String lineaOriginal, int i) {
		String linea = lineaOriginal.trim();
		String lineaMinuscula = linea.toLowerCase();

		boolean esCabecera = lineaMinuscula.contains("java.lang.module.resolutionexception:")
				|| lineaMinuscula.contains("contains package") || lineaMinuscula.contains("export package")
				|| lineaMinuscula.contains("exports package")
				|| (lineaMinuscula.startsWith("exception in thread") && lineaMinuscula.contains("resolutionexception"));

		if (esCabecera) {
			if (recolectando) {
				finalizarBloque(consola, i - 1);
			}
			recolectando = true;
			mensajeBuilder = new StringBuilder(linea);

			// Procesar inmediatamente si es una línea completa sin stack trace
			if (!linea.contains("\tat ")) {
				finalizarBloque(consola, i);
				recolectando = false;
			}
			return;
		}

		if (recolectando) {
			if (!linea.startsWith("\tat ")) {
				mensajeBuilder.append(" ").append(linea);
				indiceUltimaNoStack = i;
			} else {
				finalizarBloque(consola, indiceUltimaNoStack);
			}
		}
	}

	// Resto del código igual, pero con este cambio crucial en finalizarBloque:
	private void finalizarBloque(Consola consola, int indiceParaEnlace) {
		try {
			String mensajeCompleto = mensajeBuilder.toString().trim();

			// Limpieza específica para el formato problemático
			String mensaje = mensajeCompleto.replace("Exception in thread \"main\" ", "")
					.replace("java.lang.module.ResolutionException: ", "").trim();

			String modulosCombinados = "";
			String paquete = "";
			boolean encontrado = false;

			// Detección específica para: "Modules jlayer and mts export package
			// javazoom.jl.player to module fabric_screen_handler_api_v1"
			if (mensaje.toLowerCase().contains("modules") && mensaje.toLowerCase().contains("export package")
					&& mensaje.toLowerCase().contains("to module")) {

				// Extraer los módulos conflictivos
				String parteModulos = mensaje.substring(0, mensaje.toLowerCase().indexOf("export package")).trim();
				if (parteModulos.toLowerCase().startsWith("modules")) {
					parteModulos = parteModulos.substring("modules".length()).trim();
				}
				modulosCombinados = parteModulos.replace("and", "+").trim();

				// Extraer el paquete problemático
				int inicioPaquete = mensaje.toLowerCase().indexOf("export package") + "export package".length();
				int finPaquete = mensaje.toLowerCase().indexOf("to module");
				paquete = mensaje.substring(inicioPaquete, finPaquete).trim().replace("\"", "");

				encontrado = true;
				activado = true;
			}

			// Mantener compatibilidad con otros formatos
			if (!encontrado && mensaje.toLowerCase().contains("contains package")) {
				String[] partes = mensaje.split("contains package", 2);
				if (partes.length > 1) {
					modulosCombinados = partes[0].replace("Module", "").trim();
					String[] resto = partes[1].split("module ");
					if (resto.length > 1) {
						paquete = resto[1].split(" ")[0].trim().replace("\"", "");
						encontrado = true;
						activado = true;
					}
				}
			}

			if (encontrado && !paquete.isEmpty()) {
				paqueteProblematico = paquete;
				CrashDetectorLogger.log("paqueteProblematico " + paqueteProblematico);
				Buscardor.cargar();
				List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino(paquete);

				// Si no encuentra mods, igual mostrar el mensaje básico
				String resultado = mods.isEmpty() ? "" : formatearResultadoBusqueda(mods);

				String enlace = consola.agregarErrorALectador(indiceParaEnlace, this);
				enlacesPorPaquete.put(paquete, enlace);

				StringBuilder mensajeFinal = new StringBuilder();
				mensajeFinal.append(MonitorDePID.idioma.module_resolution_exception());

				mensajeFinal.append("<br><br><b>").append(MonitorDePID.idioma.modulos()).append(":</b><br><ul>");
				for (String modulo : modulosCombinados.split("\\+")) {
					String trim = modulo.trim();
					mensajeFinal.append("<li>").append("<b>").append(trim).append("</b>").append(procesarModulo(trim))
							.append("</li>");
				}
				mensajeFinal.append("</ul>");

				if(modulosCombinados.contains("MixinExtras")||modulosCombinados.contains("mixinextras.neoforge")) {
				mixinextras=true;	
				
				mensajeFinal.append(nl_html).append(MonitorDePID.idioma.mixinExtrasDuplicados()).append(nl_html);

				
				}
				
				mensajeFinal.append("<b>").append(MonitorDePID.idioma.paquete()).append(":</b><br>").append("<code>")
						.append(paquete.replace(".", "/")).append("</code>");

				if (!mods.isEmpty()) {
					mensajeFinal.append(" ").append(resultado);
				}

				mensajeFinal.append(Verificaciones.nl_html).append(enlace);

				mensajes.append(mensajeFinal.toString());
			}
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		} finally {
			recolectando = false;
			mensajeBuilder = null;
			indiceUltimaNoStack = -1;
		}
	}

	// Resto de los métodos sin cambios (formatearResultadoBusqueda, nueva,
	// activado, etc.)
	// [Mantener todos los métodos existentes iguales]

	public static String procesarModulo(String modulo) {
		// TODO Auto-generated method stub
		List<String> mods = Buscardor.obtenerModsConNombre(modulo);
		if (!mods.isEmpty()) {
			return "(" + String.join(",", mods) + ")";
		}

		return "";
	}

	// Solo para referencia, asegurarse de tener estos métodos:
	private String formatearResultadoBusqueda(List<ArchivoDeMod> mods) {
		if (mods.isEmpty())
			return "()";

		String contenido = mods.stream()
				.map(mod -> "<b>" + Buscardor.rutaParaPublicar(mod.ubicacion_para_publicar()) + "</b>")
				.collect(Collectors.joining(", "));

		return "(" + contenido + ")";
	}

	@Override
	public Verificaciones nueva() {
		return new ModulesDuplicadosJavaModulePlatform();
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
		return mensajes.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_modules_duplicados_jmps();
	}

	@Override
	public QuickFix solucion() {
		// [Mantener implementación existente]
		QuickFix.Builder builder = new QuickFix.Builder(MonitorDePID.idioma.nombre_de_modules_duplicados_jmps());

		if (!activado || paqueteProblematico == null || paqueteProblematico.isEmpty()) {
			builder.agregarEtiqueta(MonitorDePID.idioma.no_se_puede_eliminar_paquete());
			return builder.construir();
		}

		List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino(paqueteProblematico);

		if (mods.isEmpty()) {
			builder.agregarEtiqueta(
					MonitorDePID.idioma.no_se_encontraron_mods_con_paquete() + ": " + paqueteProblematico);
			return builder.construir();
		}

		for (ArchivoDeMod mod : mods) {
			String rutaMod = mod.ubicacion_para_publicar();
			String rutaPaquete = paqueteProblematico.replace('.', '/');

			String buttonText = MonitorDePID.idioma.eliminar_paquete() + ": " + rutaMod + " (" + paqueteProblematico
					+ ")";

			builder.agregarBoton(buttonText, retener -> {
				try {
					EliminadorDeMod.eliminarElementoEnMod(rutaMod, rutaPaquete);

					if (!EliminadorDeMod.esModoHeadless()) {
						JOptionPane.showMessageDialog(null,
								MonitorDePID.idioma.paquete_eliminado_exitosamente() + ": " + paqueteProblematico
										+ " en " + rutaMod,
								MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e) {
					CrashDetectorLogger.logException(e);

					if (!EliminadorDeMod.esModoHeadless()) {
						JOptionPane.showMessageDialog(null,
								MonitorDePID.idioma.error_al_eliminar_paquete() + ": " + paqueteProblematico,
								MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}

		return builder.construir();
	}

	@Override
	public String id() {
		return "modules_duplicados_java_module_platform";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

}