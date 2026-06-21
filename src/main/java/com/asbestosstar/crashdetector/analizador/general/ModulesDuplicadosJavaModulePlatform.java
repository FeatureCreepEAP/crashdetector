package com.asbestosstar.crashdetector.analizador.general;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EliminadorDeMod;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ModulesDuplicadosJavaModulePlatform implements Verificaciones {

	private static final Set<String> REPORTADOS_GLOBALMENTE = Collections.synchronizedSet(new HashSet<>());

	private boolean activado = false;
	private final CDStringBuilder mensajes = new CDStringBuilder();
	private String paqueteProblematico;
	private final Map<String, String> enlacesPorPaquete = new HashMap<>();

	private boolean recolectando = false;
	private StringBuilder mensajeBuilder = null;
	private int indiceUltimaNoStack = -1;
	boolean mixinextras = false;

	private static final String RESOLUTION_EXCEPTION = "java.lang.module.ResolutionException";
	private static final String CONTAINS_PACKAGE = "contains package";
	private static final String EXPORT_PACKAGE = "export package";
	private static final String EXPORTS_PACKAGE = "exports package";
	private static final String TO_MODULE = "to module";
	private static final String EXCEPTION_IN_THREAD = "Exception in thread";

	@Override
	public String[] patronesRapidos() {
		return new String[] { RESOLUTION_EXCEPTION, CONTAINS_PACKAGE, EXPORT_PACKAGE, EXPORTS_PACKAGE, TO_MODULE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		if (recolectando && mensajeBuilder != null) {
			CrashDetectorLogger.log("Finalizando bloque pendiente al final del procesamiento");

			int indice = indiceUltimaNoStack >= 0 ? indiceUltimaNoStack : 0;
			finalizarBloque(consola, indice);
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String lineaOriginal, int i) {
		if (lineaOriginal == null)
			return;

		String linea = sinEspaciosLaterales(lineaOriginal);

		if (esCabecera(linea)) {
			if (recolectando) {
				finalizarBloque(consola, i - 1);
			}

			recolectando = true;
			mensajeBuilder = new StringBuilder(linea);
			indiceUltimaNoStack = i;

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

	private boolean esCabecera(String linea) {
		if (linea == null)
			return false;

		return linea.contains(RESOLUTION_EXCEPTION) || linea.contains(CONTAINS_PACKAGE)
				|| linea.contains(EXPORT_PACKAGE) || linea.contains(EXPORTS_PACKAGE)
				|| (linea.startsWith(EXCEPTION_IN_THREAD) && linea.contains("ResolutionException"));
	}

	private void finalizarBloque(Consola consola, int indiceParaEnlace) {
		try {
			if (mensajeBuilder == null)
				return;

			String mensajeCompleto = sinEspaciosLaterales(mensajeBuilder.toString());

			// Limpieza específica para el formato problemático
			String mensaje = mensajeCompleto.replace("Exception in thread \"main\" ", "")
					.replace("java.lang.module.ResolutionException: ", "");
			mensaje = sinEspaciosLaterales(mensaje);

			String modulosCombinados = "";
			String paquete = "";
			boolean encontrado = false;

			String mensajeLower = mensaje.toLowerCase();

			// Detección específica para: "Modules jlayer and mts export package
			// javazoom.jl.player to module fabric_screen_handler_api_v1"
			if (mensajeLower.contains("modules") && mensajeLower.contains(EXPORT_PACKAGE)
					&& mensajeLower.contains(TO_MODULE)) {

				// Extraer los módulos conflictivos
				int idxExportPackage = mensajeLower.indexOf(EXPORT_PACKAGE);
				String parteModulos = sinEspaciosLaterales(mensaje.substring(0, idxExportPackage));

				if (parteModulos.toLowerCase().startsWith("modules")) {
					parteModulos = sinEspaciosLaterales(parteModulos.substring("modules".length()));
				}

				modulosCombinados = parteModulos.replace("and", "+");

				// Extraer el paquete problemático
				int inicioPaquete = idxExportPackage + EXPORT_PACKAGE.length();
				int finPaquete = mensajeLower.indexOf(TO_MODULE, inicioPaquete);

				if (finPaquete > inicioPaquete) {
					paquete = limpiarComillas(mensaje.substring(inicioPaquete, finPaquete));
					encontrado = true;
				}
			}

			// Mantener compatibilidad con otros formatos
			if (!encontrado && mensajeLower.contains(CONTAINS_PACKAGE)) {
				int idxContains = mensajeLower.indexOf(CONTAINS_PACKAGE);

				if (idxContains > 0) {
					modulosCombinados = sinEspaciosLaterales(mensaje.substring(0, idxContains).replace("Module", ""));

					int inicioResto = idxContains + CONTAINS_PACKAGE.length();
					int idxModule = mensaje.indexOf("module ", inicioResto);

					if (idxModule >= 0) {
						int inicioPaquete = idxModule + "module ".length();
						int finPaquete = inicioPaquete;

						while (finPaquete < mensaje.length() && !Character.isWhitespace(mensaje.charAt(finPaquete))) {
							finPaquete++;
						}

						paquete = limpiarComillas(mensaje.substring(inicioPaquete, finPaquete));
						encontrado = true;
					}
				}
			}

			if (encontrado && !paquete.isEmpty()) {
				String claveGlobal = paquete + "|" + modulosCombinados;

				if (!REPORTADOS_GLOBALMENTE.add(claveGlobal)) {
					return;
				}

				paqueteProblematico = paquete;
				CrashDetectorLogger.log("paqueteProblematico " + paqueteProblematico);

				Buscador.cargar();
				List<ArchivoDeMod> mods = Buscador.buscarModsConTermino(paquete);

				// Si no encuentra mods, igual mostrar el mensaje básico
				String resultado = mods.isEmpty() ? "" : formatearResultadoBusqueda(mods);

				String enlace = consola.agregarErrorALectador(indiceParaEnlace, this);
				enlacesPorPaquete.put(paquete, enlace);

				StringBuilder mensajeFinal = new StringBuilder();
				mensajeFinal.append(MonitorDePID.idioma.module_resolution_exception());

				mensajeFinal.append("<br><br><b>").append(MonitorDePID.idioma.modulos()).append(":</b><br><ul>");

				agregarModulosAlMensaje(mensajeFinal, modulosCombinados);

				mensajeFinal.append("</ul>");

				if (modulosCombinados.contains("MixinExtras") || modulosCombinados.contains("mixinextras.neoforge")) {
					mixinextras = true;

					mensajeFinal.append(nl_html).append(MonitorDePID.idioma.mixinExtrasDuplicados()).append(nl_html);
				}

				mensajeFinal.append("<b>").append(MonitorDePID.idioma.paquete()).append(":</b><br>").append("<code>")
						.append(paquete.replace(".", "/")).append("</code>");

				if (!mods.isEmpty()) {
					mensajeFinal.append(" ").append(resultado);
				}

				mensajeFinal.append(Verificaciones.nl_html).append(enlace);

				mensajes.append(mensajeFinal.toString());
				activado = true;
			}
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		} finally {
			recolectando = false;
			mensajeBuilder = null;
			indiceUltimaNoStack = -1;
		}
	}

	private void agregarModulosAlMensaje(StringBuilder mensajeFinal, String modulosCombinados) {
		if (modulosCombinados == null || modulosCombinados.isEmpty())
			return;

		int inicio = 0;

		while (inicio < modulosCombinados.length()) {
			int fin = modulosCombinados.indexOf('+', inicio);

			if (fin < 0) {
				fin = modulosCombinados.length();
			}

			String modulo = sinEspaciosLaterales(modulosCombinados.substring(inicio, fin));

			if (!modulo.isEmpty()) {
				mensajeFinal.append("<li>").append("<b>").append(modulo).append("</b>").append(procesarModulo(modulo))
						.append("</li>");
			}

			inicio = fin + 1;
		}
	}

	public static String procesarModulo(String modulo) {
		List<String> mods = Buscador.obtenerModsConNombre(modulo);
		if (!mods.isEmpty()) {
			return "(" + String.join(",", mods) + ")";
		}

		return "";
	}

	private String formatearResultadoBusqueda(List<ArchivoDeMod> mods) {
		if (mods.isEmpty())
			return "()";

		String contenido = mods.stream()
				.map(mod -> "<b>" + Buscador.rutaParaPublicar(mod.ubicacion_para_publicar()) + "</b>")
				.collect(Collectors.joining(", "));

		return "(" + contenido + ")";
	}

	private String limpiarComillas(String texto) {
		if (texto == null)
			return "";

		String limpia = sinEspaciosLaterales(texto);
		StringBuilder sb = new StringBuilder(limpia.length());

		for (int i = 0; i < limpia.length(); i++) {
			char c = limpia.charAt(i);

			if (c != '"' && c != '\'') {
				sb.append(c);
			}
		}

		return sinEspaciosLaterales(sb.toString());
	}

	private String sinEspaciosLaterales(String texto) {
		if (texto == null)
			return "";

		int inicio = 0;
		int fin = texto.length();

		while (inicio < fin && Character.isWhitespace(texto.charAt(inicio))) {
			inicio++;
		}

		while (fin > inicio && Character.isWhitespace(texto.charAt(fin - 1))) {
			fin--;
		}

		return texto.substring(inicio, fin);
	}

	public static void reiniciarGlobal() {
		REPORTADOS_GLOBALMENTE.clear();
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

		if (!activado || paqueteProblematico == null || paqueteProblematico.isEmpty()) {
			builder.agregarEtiqueta(MonitorDePID.idioma.no_se_puede_eliminar_paquete());
			return builder.construir();
		}

		List<ArchivoDeMod> mods = Buscador.buscarModsConTermino(paqueteProblematico);

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
	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}