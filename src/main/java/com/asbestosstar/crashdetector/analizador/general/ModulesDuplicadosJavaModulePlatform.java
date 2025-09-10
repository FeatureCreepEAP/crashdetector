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
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

public class ModulesDuplicadosJavaModulePlatform implements Verificaciones {

	private boolean activado = false;
	private final CDStringBuilder mensajes = new CDStringBuilder();
	private String paqueteProblematico;
	private final Map<String, String> enlacesPorPaquete = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i].trim();

			if (linea.contains("java.lang.module.ResolutionException:") || linea.contains("contains package")
					|| linea.contains("export package") || linea.contains("exports package")) {

				try {
					StringBuilder mensajeBuilder = new StringBuilder(linea);

					// Recolectar líneas completas del error
					while (i + 1 < lineas.length && !lineas[i + 1].trim().startsWith("\tat ")) {
						mensajeBuilder.append(" ").append(lineas[++i].trim());
					}

					String mensaje = mensajeBuilder.toString().replace("ResolutionException:", "").trim();

					// Eliminar texto antes de "Modules" o "Module"
					mensaje = mensaje.replaceFirst(".*?(?=Modules|Module)", "").trim();

					String modulosCombinados = "";
					String paquete = "";

					if (mensaje.contains("contains package")) {
						String[] partes = mensaje.split("contains package");
						modulosCombinados = partes[0].replace("Module", "").trim();
						paquete = partes[1].split("module ")[1].split(" ")[0].trim();
						activado = true;

					} else if (mensaje.contains("export package")) {
						String[] partes = mensaje.split("export package");
						modulosCombinados = partes[0].replace("Modules", "").replace(" and ", "+").trim();
						paquete = partes[1].split("to module")[0].trim();
						activado = true;

					} else if (mensaje.contains("exports package")) {
						String[] partes = mensaje.split("exports package");
						modulosCombinados = partes[0].trim().replace("and", "+");
						paquete = partes[1].split("to module")[0].trim();
						activado = true;
					}

					if (activado) {
						paquete = paquete.replaceAll("^\"|\"$", "").trim();
						paqueteProblematico = paquete;
						Buscardor.cargar();
						List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino(paquete);
						String resultado = formatearResultadoBusqueda(mods);

						// Registrar el error en el sistema de lectura
						String enlace = consola.agregarErrorALectador(i, this);
						enlacesPorPaquete.put(paquete, enlace);

						// Construir mensaje manualmente ya que module_resolution_exception ya no acepta
						// parámetros
						StringBuilder mensajeFinal = new StringBuilder();
						mensajeFinal.append(MonitorDePID.idioma.module_resolution_exception());

						// Añadir sección de módulos
						if (!modulosCombinados.isEmpty() && !paquete.isEmpty()) {
							mensajeFinal.append("<br><br><b>" + MonitorDePID.idioma.modulos() + ":</b><br>")
									.append("<ul>");

							// Convertir módulos combinados en lista
							for (String modulo : modulosCombinados.split("\\+")) {
								mensajeFinal.append("<li>").append(modulo.trim()).append("</li>");
							}

							mensajeFinal.append("</ul>");

							mensajeFinal.append("<b>" + MonitorDePID.idioma.paquete() + ":</b><br>").append("<code>")
									.append(paquete.replace(".", "/")).append("</code> ");
							mensajeFinal.append(resultado).append(Verificaciones.nl_html);
							mensajeFinal.append(" ").append(enlace); // Incluir el enlace aquí
						}

						// Añadir mensaje final
						mensajes.append(mensajeFinal.toString());
					}
				} catch (Exception e) {
					CrashDetectorLogger.logException(e);
				}
			}
		}
	}

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
		QuickFix.Builder builder = new QuickFix.Builder(MonitorDePID.idioma.nombre_de_modules_duplicados_jmps());

		// Solo continuar si se detectó el problema
		if (!activado || paqueteProblematico == null || paqueteProblematico.isEmpty()) {
			builder.agregarEtiqueta(MonitorDePID.idioma.no_se_puede_eliminar_paquete());
			return builder.construir();
		}

		// Buscar mods que contengan el paquete duplicado
		List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino(paqueteProblematico);

		if (mods.isEmpty()) {
			builder.agregarEtiqueta(
					MonitorDePID.idioma.no_se_encontraron_mods_con_paquete() + ": " + paqueteProblematico);
			return builder.construir();
		}

		// Agregar botón para cada mod encontrado
		for (ArchivoDeMod mod : mods) {
			String rutaMod = mod.ubicacion_para_publicar();
			String rutaPaquete = paqueteProblematico.replace('.', '/');

			String buttonText = MonitorDePID.idioma.eliminar_paquete() + ": " + rutaMod + " (" + paqueteProblematico
					+ ")";

			builder.agregarBoton(buttonText, retener -> {
				try {
					// Eliminar solo el paquete específico del mod
					EliminadorDeMod.eliminarElementoEnMod(rutaMod, rutaPaquete);

					// Mostrar mensaje solo si hay GUI disponible
					if (!EliminadorDeMod.esModoHeadless()) {
						JOptionPane.showMessageDialog(null,
								MonitorDePID.idioma.paquete_eliminado_exitosamente() + ": " + paqueteProblematico
										+ " en " + rutaMod,
								MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e) {
					// Log y mostrar error si aplica
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
}