package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta funciones de densidad no vinculadas en mods de Minecraft. Patrón
 * moderno: verificación global barata (contains) + per-línea.
 */
public class FuncionesDeDensidadNoVinculadas implements VerificacionRapida {

	private boolean activado = false;
	private boolean posibleError = false;

	private String mensaje = "";
	private String enlaceHtml = "";

	private final List<String> clavesFaltantes = new ArrayList<>();
	private final List<String> modsUbicacion = new ArrayList<>();

	private static final String TEXTO_GLOBAL_1 = "unbound values in registry";
	private static final String TEXTO_GLOBAL_2 = "Trying to access unbound value";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_GLOBAL_1, TEXTO_GLOBAL_2 };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneError(evento.linea)) {
			posibleError = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty())
			return;

		// Global barato: solo contains, sin recorrer todas las líneas
		String contenido = consola.contenido_verificar;
		if (contenido.contains(TEXTO_GLOBAL_1) || contenido.contains(TEXTO_GLOBAL_2)) {
			posibleError = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleError && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleError || linea == null || linea.isEmpty() || activado)
			return;

		// Formato lista: "unbound values in registry ...: [namespace:key,...]"
		if (linea.contains(TEXTO_GLOBAL_1)) {
			int start = linea.indexOf("]: [");
			if (start >= 0) {
				start += 4;
				int end = linea.indexOf(']', start);
				if (end >= 0) {
					String lista = linea.substring(start, end);
					Set<String> namespaces = new HashSet<>();

					int desde = 0;
					while (desde < lista.length()) {
						int coma = lista.indexOf(',', desde);
						int hasta = coma >= 0 ? coma : lista.length();

						String k = limpiarEspacios(lista, desde, hasta);
						if (!k.isEmpty()) {
							int c = k.indexOf(':');
							if (c > 0) {
								String ns = k.substring(0, c);
								if (!"minecraft".equals(ns)) {
									clavesFaltantes.add(k);
									namespaces.add(ns);
								}
							}
						}

						if (coma < 0)
							break;

						desde = coma + 1;
					}

					if (!namespaces.isEmpty()) {
						modsUbicacion.clear();
						Buscador.cargar();
						for (String ns : namespaces) {
							List<ArchivoDeMod> mods = Buscador.buscarModsConTermino("data/" + ns + "/");
							if (mods.isEmpty())
								mods = Buscador.buscarModsConTermino(ns);
							modsUbicacion.addAll(Buscador.obtenerUbicaciones(mods));
						}
					}
				}
			}
		}

		// Formato individual: "Trying to access unbound value 'ResourceKey[...] /
		// namespace:key]'"
		if (linea.contains(TEXTO_GLOBAL_2)) {
			int keyStart = linea.indexOf("ResourceKey[");
			if (keyStart >= 0) {
				int keyEnd = linea.indexOf("]'", keyStart);
				if (keyEnd > keyStart) {
					String inner = linea.substring(keyStart + "ResourceKey[".length(), keyEnd);
					int lastSeparator = inner.lastIndexOf(" / ");
					if (lastSeparator >= 0) {
						String valorClave = limpiarEspacios(inner, lastSeparator + 3, inner.length());
						int colonIdx = valorClave.indexOf(':');
						if (colonIdx > 0 && !"minecraft".equals(valorClave.substring(0, colonIdx))) {
							clavesFaltantes.add(valorClave);
							Buscador.cargar();
							List<ArchivoDeMod> mods = Buscador.buscarModsConTermino(valorClave.substring(0, colonIdx));
							modsUbicacion.addAll(Buscador.obtenerUbicaciones(mods));
						}
					}
				}
			}
		}

		if (!clavesFaltantes.isEmpty()) {
			mensaje = MonitorDePID.idioma.errorFuncionesDeDensidadNoVinculadas(clavesFaltantes);
			if (!modsUbicacion.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append(Verificaciones.nl_html).append("Posibles proveedores: <b>");
				for (int i = 0; i < modsUbicacion.size(); i++) {
					if (i > 0)
						sb.append(", ");
					sb.append(modsUbicacion.get(i));
					if (i >= 4 && i + 1 < modsUbicacion.size()) {
						sb.append(", y otros");
						break;
					}
				}
				sb.append("</b>.");
				mensaje += sb.toString();
			}
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	private boolean lineaContieneError(String linea) {
		return linea.contains(TEXTO_GLOBAL_1) || linea.contains(TEXTO_GLOBAL_2);
	}

	private String limpiarEspacios(String texto, int inicio, int fin) {
		while (inicio < fin && texto.charAt(inicio) <= ' ') {
			inicio++;
		}

		while (fin > inicio && texto.charAt(fin - 1) <= ' ') {
			fin--;
		}

		if (inicio >= fin) {
			return "";
		}

		return texto.substring(inicio, fin);
	}

	@Override
	public Verificaciones nueva() {
		return new FuncionesDeDensidadNoVinculadas();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 820.0f;
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeFuncionesDeDensidadNoVinculadas();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoFuncionesDeDensidadNoVinculadas())
				.construir();
	}

	@Override
	public String id() {
		return "funciones_de_densidad_no_vinculadas";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}