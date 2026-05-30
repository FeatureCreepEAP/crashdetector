package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.List;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EliminadorDeMod;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NoSuchElementAnimacionMinecraft implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;

	private String namespaceEncontrado = null;
	private String enlaceHtml = "";

	private static final String PREFIJO_ERROR = "java.util.NoSuchElementException: No animation with registry name ";

	@Override
	public void verificar(Consola consola) {

		// Chequeo global barato:
		// si el log completo no contiene la señal principal,
		// no hace falta procesar línea por línea.
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		if (consola.contenido_verificar.contains(PREFIJO_ERROR)) {
			this.analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si el chequeo global no encontró la señal principal, no analizamos líneas.
		if (!analizarLineas) {
			return;
		}

		// Si ya se activó, no seguimos buscando más coincidencias.
		if (activado) {
			return;
		}

		String namespace = extraerNamespace(linea);
		if (namespace != null) {
			namespaceEncontrado = namespace;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	private String extraerNamespace(String linea) {
		if (linea == null || linea.isEmpty()) {
			return null;
		}

		int inicioPrefijo = linea.indexOf(PREFIJO_ERROR);
		if (inicioPrefijo < 0) {
			return null;
		}

		int inicioNamespace = inicioPrefijo + PREFIJO_ERROR.length();
		int finNamespace = linea.indexOf(':', inicioNamespace);

		if (finNamespace <= inicioNamespace) {
			return null;
		}

		String namespace = linea.substring(inicioNamespace, finNamespace);

		if (!esNamespaceValido(namespace)) {
			return null;
		}

		return namespace;
	}

	private boolean esNamespaceValido(String namespace) {
		if (namespace == null || namespace.isEmpty()) {
			return false;
		}

		for (int i = 0; i < namespace.length(); i++) {
			char c = namespace.charAt(i);

			if (!(Character.isLetterOrDigit(c) || c == '_')) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Verificaciones nueva() {
		return new NoSuchElementAnimacionMinecraft();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Prioridad alta para errores críticos de recursos
	}

	@Override
	public String mensaje() {
		if (!activado())
			return "";

		Buscador.cargar();
		String terminoBusqueda = "assets/" + namespaceEncontrado + "/";
		List<String> ubicaciones = Buscador.obtenerUbicaciones(Buscador.buscarModsConTermino(terminoBusqueda));

		StringBuilder html = new StringBuilder();
		html.append("<span style='color: #").append(Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas())
				.append("; font-weight: bold;'>").append(MonitorDePID.idioma.error_animacion_no_encontrada())
				.append("</span>").append(Verificaciones.nl_html).append("<ul>");

		html.append("<li>").append(": <strong>").append(namespaceEncontrado).append("</strong> (")
				.append(String.join(",", ubicaciones)).append(") ").append(enlaceHtml).append("</li>");

		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_animacion_minecraft();
	}

	@Override
	public QuickFix solucion() {
		if (!activado())
			return null;

		Buscador.cargar();
		String terminoBusqueda = "assets/" + namespaceEncontrado + "/";
		List<String> ubicaciones = Buscador.obtenerUbicaciones(Buscador.buscarModsConTermino(terminoBusqueda));

		QuickFix.Builder builder = new QuickFix.Builder(nombre());

		if (ubicaciones.isEmpty()) {
			builder.agregarEtiqueta(MonitorDePID.idioma.no_se_encontraron_mods_para_eliminar());
		} else {
			for (String ubicacion : ubicaciones) {
				String botonTexto = MonitorDePID.idioma.eliminar() + ": " + ubicacion;

				builder.agregarBoton(botonTexto, retener -> {
					try {
						EliminadorDeMod.eliminarMod(ubicacion);

						if (!EliminadorDeMod.esModoHeadless()) {
							JOptionPane.showMessageDialog(null,
									MonitorDePID.idioma.jar_eliminado_exitosamente() + ": " + ubicacion,
									MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e) {
						CrashDetectorLogger.logException(e);

						if (!EliminadorDeMod.esModoHeadless()) {
							JOptionPane.showMessageDialog(null,
									MonitorDePID.idioma.error_al_eliminar_jar() + ": " + ubicacion,
									MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
		}

		return builder.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "no_such_element_animacion_minecraft";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}