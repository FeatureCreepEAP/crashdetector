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

public class FuncionesDeDensidadNoVinculadas implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private final List<String> clavesFaltantes = new ArrayList<>();
	private final List<String> modsUbicacion = new ArrayList<>();

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Buscar la frase clave en todo el texto, sin distinguir mayúsculas
		int pos = indexOfIgnoreCase(contenido, "unbound values in registry");
		if (pos < 0)
			return;

		// Encontrar la lista que viene tras "]: ["
		int marker = contenido.indexOf("]: [", pos);
		if (marker < 0)
			return;

		int start = marker + 4; // justo después de "]: ["
		int end = contenido.indexOf(']', start);
		if (end < 0)
			return;

		String lista = contenido.substring(start, end);
		String[] partes = lista.split(",");

		clavesFaltantes.clear();
		Set<String> namespaces = new HashSet<>();

		for (String p : partes) {
			String k = p.trim();
			if (k.isEmpty())
				continue;
			int c = k.indexOf(':');
			if (c <= 0)
				continue;
			String ns = k.substring(0, c).trim();
			// quedarse solo con terceros (p.ej. tectonic), no minecraft
			if (!"minecraft".equals(ns)) {
				clavesFaltantes.add(k);
				namespaces.add(ns);
			}
		}

		if (clavesFaltantes.isEmpty())
			return;

		// Intentar localizar posibles proveedores por namespace (sin meterlos en
		// idioma)
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

		// Calcular número de línea para el enlace
		int linea = contarSaltosDeLinea(contenido, pos);
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
		// TODO Auto-generated method stub
		return "funciones_de_densidad_no_vinculadas";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}
