package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FuncionesDeDensidadNoVinculadas implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private final List<String> clavesFaltantes = new ArrayList<>();
	private final List<String> modsUbicacion = new ArrayList<>();

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Buscar la frase clave para el formato de lista
		int posLista = indexOfIgnoreCase(contenido, "unbound values in registry");
		boolean foundList = posLista >= 0;

		// Buscar la frase clave para el formato individual (ej. Tectonic)
		// Se usa "Trying to access unbound value" que es único y rápido de encontrar
		int posIndividual = indexOfIgnoreCase(contenido, "Trying to access unbound value");
		boolean foundIndividual = posIndividual >= 0;

		if (!foundList && !foundIndividual)
			return;

		clavesFaltantes.clear();
		Set<String> namespaces = new HashSet<>();

		// Procesar formato lista: "unbound values in registry ...: [namespace:key,
		// ...]"
		if (foundList) {
			int marker = contenido.indexOf("]: [", posLista);
			if (marker >= 0) {
				int start = marker + 4;
				int end = contenido.indexOf(']', start);
				if (end >= 0) {
					String lista = contenido.substring(start, end);
					String[] partes = lista.split(",");

					for (String p : partes) {
						String k = p.trim();
						if (k.isEmpty())
							continue;
						int c = k.indexOf(':');
						if (c <= 0)
							continue;
						String ns = k.substring(0, c).trim();
						if (!"minecraft".equals(ns)) {
							clavesFaltantes.add(k);
							namespaces.add(ns);
						}
					}
				}
			}
		}

		// Procesar formato individual: "Trying to access unbound value 'ResourceKey[...
		// / namespace:key]'"
		// Implementación SIN Regex para mejor rendimiento
		if (foundIndividual) {
			int searchFrom = posIndividual;
			String target = "ResourceKey[";
			String endMarker = "]'";

			// Buscar hacia adelante desde el mensaje de error
			int keyStart = contenido.indexOf(target, searchFrom);

			// Buscar un poco hacia adelante también por si acaso (hasta 100 chars)
			if (keyStart == -1 && searchFrom > 100) {
				keyStart = contenido.indexOf(target, searchFrom - 100);
			}

			if (keyStart != -1) {
				// Encontrar el cierre del ResourceKey
				int keyEnd = contenido.indexOf(endMarker, keyStart);
				if (keyEnd != -1) {
					// Extraer el contenido: "minecraft:worldgen/density_function /
					// tectonic:overworld/depth"
					String inner = contenido.substring(keyStart + target.length(), keyEnd);

					// Buscar el último separador " / " que divide el tipo de la clave real
					int lastSeparator = inner.lastIndexOf(" / ");
					if (lastSeparator != -1) {
						String valorClave = inner.substring(lastSeparator + 3).trim(); // "tectonic:overworld/depth"

						int colonIdx = valorClave.indexOf(':');
						if (colonIdx > 0) {
							String ns = valorClave.substring(0, colonIdx);
							if (!"minecraft".equals(ns)) {
								clavesFaltantes.add(valorClave);
								namespaces.add(ns);
							}
						}
					}
				}
			}
		}

		if (clavesFaltantes.isEmpty())
			return;

		// Intentar localizar posibles proveedores por namespace
		modsUbicacion.clear();
		Buscardor.cargar();
		for (String ns : namespaces) {
			List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino("data/" + ns + "/");
			if (mods.isEmpty()) {
				mods = Buscardor.buscarModsConTermino(ns);
			}
			modsUbicacion.addAll(Buscardor.obtenerUbicaciones(mods));
		}

		// Mensaje base desde idioma
		mensaje = MonitorDePID.idioma.errorFuncionesDeDensidadNoVinculadas(clavesFaltantes);

		// Añadir proveedores aquí (fuera de idioma)
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

		// Calcular número de línea para el enlace (usar la primera ocurrencia
		// encontrada)
		int linea = contarSaltosDeLinea(contenido, foundList ? posLista : posIndividual);
		enlaceHtml = consola.agregarErrorALectador(linea, this);

		activado = true;
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
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoFuncionesDeDensidadNoVinculadas())
				.construir();
	}

	// Utilidades

	private static int indexOfIgnoreCase(String haystack, String needle) {
		String h = haystack.toLowerCase(Locale.ROOT);
		String n = needle.toLowerCase(Locale.ROOT);
		return h.indexOf(n);
	}

	private static int contarSaltosDeLinea(String s, int hasta) {
		int lineas = 0;
		for (int i = 0; i < hasta && i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == '\n')
				lineas++;
		}
		return lineas;
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

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}