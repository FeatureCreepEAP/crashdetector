package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NullPointer implements Verificaciones {

	private static final java.util.Set<String> ERRORES_GLOBALES_VISTOS = java.util.Collections
			.synchronizedSet(new java.util.HashSet<>());

	public static void reiniciarGlobal() {
		ERRORES_GLOBALES_VISTOS.clear();
	}

	private final Map<String, Set<String>> errores = new HashMap<>();
	private final Map<String, String> enlacesPorLinea = new HashMap<>();
	private final Map<String, Integer> lineaPorMensaje = new HashMap<>();

	private Consola consolaDetectada = null;
	private boolean origenesResueltos = false;
	private boolean activado = false;

	public static List<String> lineas_ignorar = new ArrayList<>();

	static {
		lineas_ignorar.add("Cannot invoke \"java.util.Map.get(Object)\" because \"promos\" is null");
		lineas_ignorar.add("com.github.alexthe666.citadel.web.WebHelper:getURLContent");
		lineas_ignorar.add(
				"Cannot invoke \"org.spongepowered.asm.mixin.transformer.ClassInfo.isMixin()\" because \"superClass\" is null");
		lineas_ignorar.add(
				"Cannot invoke \"org.spongepowered.asm.mixin.transformer.ClassInfo.hasSuperClass(org.spongepowered.asm.mixin.transformer.ClassInfo)\" because the return value of \"org.spongepowered.asm.mixin.transformer.ClassInfo.forType(org.objectweb.asm.Type, org.spongepowered.asm.mixin.transformer.ClassInfo$TypeLookup)\" is null");
		lineas_ignorar.add(
				"Cannot invoke \"net.minecraft.client.renderer.texture.TextureManager.m_118495_(net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.texture.AbstractTexture)\" because the return value of \"net.minecraft.client.Minecraft.m_91097_()\" is null");
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { "NullPointerException", "Cannot invoke", "because", "is null" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroLinea) {
		if (linea == null || linea.isEmpty()) {
			return;
		}

		if (linea.contains("NullPointerException") && !linea.contains("at ")
				&& VerificacionDeStackTrace.tracePermite(linea)) {
			procesarLineaSinTraza(linea, numeroLinea, consola);
		}
	}

	private void procesarLineaSinTraza(String linea, int numeroLinea, Consola consola) {
		for (String patron : lineas_ignorar) {
			if (patron != null && linea.contains(patron)) {
				return;
			}
		}

		DatosNPE datos = extraerDatosNPE(linea, false);

		if (datos == null) {
			return;
		}

		String mensajeBase = MonitorDePID.idioma.null_pointer_error(datos.metodo, datos.objeto);

		if (!ERRORES_GLOBALES_VISTOS.add(mensajeBase)) {
			return;
		}

		consolaDetectada = consola;
		origenesResueltos = false;

		enlacesPorLinea.put(mensajeBase, consola != null ? consola.agregarErrorALectador(numeroLinea, this) : "");
		lineaPorMensaje.put(mensajeBase, numeroLinea);
		errores.computeIfAbsent(mensajeBase, k -> new HashSet<>());

		activado = true;
	}

	private void resolverOrigenesSiNecesario() {
		if (origenesResueltos) {
			return;
		}

		origenesResueltos = true;

		if (consolaDetectada == null || consolaDetectada.verificacion_de_stacktrace == null) {
			return;
		}

		VerificacionDeStackTrace vdst = consolaDetectada.verificacion_de_stacktrace;

		if (vdst.trazos_completos == null || vdst.trazos_completos.isEmpty()) {
			return;
		}

		for (Map.Entry<String, Integer> entry : lineaPorMensaje.entrySet()) {
			String mensajeBase = entry.getKey();
			int lineaError = entry.getValue();

			String origen = buscarOrigenDesdeVDST(vdst, lineaError);

			if (origen != null && !origen.isEmpty()) {
				errores.computeIfAbsent(mensajeBase, k -> new HashSet<>()).add(origen);
			}
		}
	}

	private String buscarOrigenDesdeVDST(VerificacionDeStackTrace vdst, int lineaError) {
		int ventanaSuperior = lineaError + 30;

		for (VerificacionDeStackTrace.TraceInfo ti : vdst.trazos_completos) {
			if (ti == null) {
				continue;
			}

			if (ti.consolaLineaComenzar < lineaError || ti.consolaLineaComenzar > ventanaSuperior) {
				continue;
			}

			if (ti.lineas == null || ti.lineas.isEmpty()) {
				continue;
			}

			for (VerificacionDeStackTrace.LineaTrazo lt : ti.lineas) {
				if (lt == null) {
					continue;
				}

				if (lt.clase != null && !lt.clase.isEmpty()) {
					return lt.clase;
				}

				if (lt.origen != null && !lt.origen.isEmpty()) {
					return lt.origen;
				}
			}
		}

		return "";
	}

	private static class DatosNPE {
		String metodo;
		String objeto;

		DatosNPE(String metodo, String objeto) {
			this.metodo = metodo;
			this.objeto = objeto;
		}
	}

	private static DatosNPE extraerDatosNPE(String texto, boolean permitirCabecera) {
		if (texto == null) {
			return null;
		}

		DatosNPE cannot = extraerFormatoCannot(texto);
		if (cannot != null) {
			return cannot;
		}

		DatosNPE json = extraerFormatoJson(texto);
		if (json != null) {
			return json;
		}

		if (permitirCabecera) {
			String detalle = extraerDetalleCabeceraNPE(texto);
			if (detalle != null && !detalle.isEmpty()) {
				return new DatosNPE(detalle, "objeto");
			}
		}

		return null;
	}

	private static DatosNPE extraerFormatoCannot(String texto) {
		int pos = indexOfIgnoreCase(texto, "Cannot");
		if (pos < 0) {
			return null;
		}

		int accionInicio = saltarEspacios(texto, pos + "Cannot".length());

		if (!empiezaConPalabra(texto, accionInicio, "invoke") && !empiezaConPalabra(texto, accionInicio, "read")
				&& !empiezaConPalabra(texto, accionInicio, "assign")) {
			return null;
		}

		int busqueda = accionInicio;

		while (true) {
			int q1 = texto.indexOf('"', busqueda);
			if (q1 < 0) {
				return null;
			}

			int q2 = texto.indexOf('"', q1 + 1);
			if (q2 < 0) {
				return null;
			}

			int q3 = texto.indexOf('"', q2 + 1);
			if (q3 < 0) {
				return null;
			}

			int q4 = texto.indexOf('"', q3 + 1);
			if (q4 < 0) {
				return null;
			}

			String entreComillas = texto.substring(q2 + 1, q3);
			if (!contieneIgnoreCase(entreComillas, "because")) {
				busqueda = q2 + 1;
				continue;
			}

			String metodo = texto.substring(q1 + 1, q2).trim();
			String objeto = texto.substring(q3 + 1, q4).trim();

			if (!metodo.isEmpty() && !objeto.isEmpty()) {
				return new DatosNPE(metodo, objeto);
			}

			busqueda = q4 + 1;
		}
	}

	private static DatosNPE extraerFormatoJson(String texto) {
		int pos = texto.indexOf("JsonObject.");
		if (pos < 0) {
			return null;
		}

		int metodoInicio = pos + "JsonObject.".length();
		if (metodoInicio >= texto.length() || !esLetraAscii(texto.charAt(metodoInicio))) {
			return null;
		}

		int parentesis = texto.indexOf("()", metodoInicio);
		if (parentesis < 0) {
			return null;
		}

		for (int i = metodoInicio; i < parentesis; i++) {
			if (!esLetraAscii(texto.charAt(i))) {
				return null;
			}
		}

		int busqueda = parentesis + 2;

		while (true) {
			int q1 = texto.indexOf('"', busqueda);
			if (q1 < 0) {
				return null;
			}

			int q2 = texto.indexOf('"', q1 + 1);
			if (q2 < 0) {
				return null;
			}

			int despues = saltarEspacios(texto, q2 + 1);
			if (empiezaConFrase(texto, despues, "is null")) {
				String objeto = texto.substring(q1 + 1, q2).trim();
				if (!objeto.isEmpty()) {
					return new DatosNPE("JsonObject.*()", objeto);
				}
			}

			busqueda = q1 + 1;
		}
	}

	private static String extraerDetalleCabeceraNPE(String texto) {
		int pos = indexOfIgnoreCase(texto, "java.lang.NullPointerException");
		if (pos < 0) {
			return null;
		}

		int fin = pos + "java.lang.NullPointerException".length();
		fin = saltarEspacios(texto, fin);

		if (fin >= texto.length()) {
			return "";
		}

		if (texto.charAt(fin) == ':') {
			return texto.substring(fin + 1).trim();
		}

		return texto.substring(fin).trim();
	}

	private static int indexOfIgnoreCase(String texto, String buscar) {
		if (texto == null || buscar == null) {
			return -1;
		}

		int max = texto.length() - buscar.length();
		for (int i = 0; i <= max; i++) {
			if (texto.regionMatches(true, i, buscar, 0, buscar.length())) {
				return i;
			}
		}
		return -1;
	}

	private static boolean contieneIgnoreCase(String texto, String buscar) {
		return indexOfIgnoreCase(texto, buscar) >= 0;
	}

	private static boolean empiezaConPalabra(String texto, int pos, String palabra) {
		if (pos < 0 || pos + palabra.length() > texto.length()) {
			return false;
		}

		if (!texto.regionMatches(true, pos, palabra, 0, palabra.length())) {
			return false;
		}

		int fin = pos + palabra.length();
		return fin >= texto.length() || !Character.isLetterOrDigit(texto.charAt(fin));
	}

	private static boolean empiezaConFrase(String texto, int pos, String frase) {
		return pos >= 0 && pos + frase.length() <= texto.length()
				&& texto.regionMatches(false, pos, frase, 0, frase.length());
	}

	private static int saltarEspacios(String texto, int pos) {
		while (pos < texto.length() && Character.isWhitespace(texto.charAt(pos))) {
			pos++;
		}
		return pos;
	}

	private static boolean esLetraAscii(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	@Override
	public Verificaciones nueva() {
		return new NullPointer();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 50f;
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty()) {
			return "";
		}

		resolverOrigenesSiNecesario();

		StringBuilder sb = new StringBuilder("<ul>");

		for (Map.Entry<String, Set<String>> entry : errores.entrySet()) {
			String claveBase = entry.getKey();
			String mensajeMostrado = claveBase;
			Set<String> origenes = entry.getValue();

			if (!origenes.isEmpty()) {
				StringBuilder origenesStr = new StringBuilder();
				for (String origen : origenes) {
					if (origenesStr.length() > 0) {
						origenesStr.append(", ");
					}
					origenesStr.append(origen);
				}
				mensajeMostrado += " (" + origenesStr + ")";
			}

			String enlace = enlacesPorLinea.getOrDefault(claveBase, "");
			if (!enlace.isEmpty()) {
				mensajeMostrado += " " + enlace;
			}

			sb.append("<li>").append(mensajeMostrado).append("</li>");
		}

		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_null_pointer();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "nullpointer";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}