package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EliminadorDeMod;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.Buscardor;

public class NoSuchElementAnimacionMinecraft implements Verificaciones {

	private boolean activado = false;
	private String namespaceEncontrado = null;
	private String enlaceHtml = "";

	// Patrón para detectar el error con cualquier namespace (cacheado a nivel de
	// clase)
	private static final Pattern PATTERN = Pattern
			.compile("java\\.util\\.NoSuchElementException: No animation with registry name ([a-zA-Z0-9_]+):");

	@Override
	public void verificar(Consola consola) {
		// La detección se realiza en el método por línea para evitar recorrer dos veces
		// el contenido completo de la consola y aprovechar el escaneo línea a línea.
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos buscando más coincidencias.
		if (activado) {
			return;
		}

		Matcher matcher = PATTERN.matcher(linea);
		if (matcher.find()) {
			namespaceEncontrado = matcher.group(1);
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
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

		Buscardor.cargar();
		String terminoBusqueda = "assets/" + namespaceEncontrado + "/";
		List<String> ubicaciones = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(terminoBusqueda));

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

		Buscardor.cargar();
		String terminoBusqueda = "assets/" + namespaceEncontrado + "/";
		List<String> ubicaciones = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(terminoBusqueda));

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

}
