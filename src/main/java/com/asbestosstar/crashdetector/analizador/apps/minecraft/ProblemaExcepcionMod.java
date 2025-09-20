//package com.asbestosstar.crashdetector.analizador.apps.minecraft;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.asbestosstar.crashdetector.Consola;
//import com.asbestosstar.crashdetector.MonitorDePID;
//import com.asbestosstar.crashdetector.analizador.QuickFix;
//import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
//import com.asbestosstar.crashdetector.analizador.Verificaciones;
//
///**
// * Clase que detecta excepciones causadas por mods en Forge.
// */
//public class ProblemaExcepcionMod implements Verificaciones {
//
//	private boolean activado = false;
//	private String mensaje = "";
//	private final List<String> nombresMods = new ArrayList<>();
//
//	// Patrón para ModLoadingException: "ModLoadingException: <Nombre Mod> (<modid>)
//	// encountered an error during the <fase> event phase"
//	private static final Pattern PATRON_MODLOADING_ENCONTRADO = Pattern.compile(
//			"ModLoadingException:\\s+([^\\(\\n]+?)\\s*\\(([^\\)\\n]+)\\)\\s+encountered\\s+an\\s+error\\s+during\\s+the\\s+([a-zA-Z_]+)\\s+event\\s+phase",
//			Pattern.CASE_INSENSITIVE);
//
//	@Override
//	public void verificar(Consola consola) {
//		String contenido = consola.contenido_verificar;
//
//		Pattern[] patrones = { Pattern.compile("Caught exception from ([^\\(\\n]+)", Pattern.DOTALL), Pattern.compile(
//				"net\\.forge\\..*?Loading errors encountered: \\[\\s+([^\\$\\n]+) \\$\\$[^\\)]+\\) encountered an error during",
//				Pattern.DOTALL) };
//
//		for (Pattern patron : patrones) {
//			Matcher coincidencia = patron.matcher(contenido);
//			while (coincidencia.find()) {
//				String nombreMod = coincidencia.group(1).trim();
//				if (!nombreMod.isEmpty() && !nombresMods.contains(nombreMod)) {
//					nombresMods.add(nombreMod);
//				}
//			}
//		}
//
//		Matcher m = PATRON_MODLOADING_ENCONTRADO.matcher(contenido);
//		while (m.find()) {
//			String nombreLegible = m.group(1).trim();
//			if (!nombreLegible.isEmpty() && !nombresMods.contains(nombreLegible)) {
//				nombresMods.add(nombreLegible);
//			}
//		}
//
//		if (!nombresMods.isEmpty()) {
//			StringBuilder sb = new StringBuilder();
//			sb.append(MonitorDePID.idioma.mensajeModExcepcionPlural(nombresMods));
//			sb.append("<ul>");
//			for (String mod : nombresMods) {
//				sb.append("<li>").append(mod).append("</li>");
//			}
//			sb.append("</ul>");
//			this.mensaje = sb.toString();
//			activado = true;
//		}
//	}
//
//	@Override
//	public Verificaciones nueva() {
//		return new ProblemaExcepcionMod();
//	}
//
//	@Override
//	public boolean activado() {
//		return activado;
//	}
//
//	@Override
//	public float prioridad() {
//		return 1000.0f;
//	}
//
//	@Override
//	public String mensaje() {
//		return mensaje;
//	}
//
//	@Override
//	public String nombre() {
//		return MonitorDePID.idioma.nombreProblemaModExcepcion();
//	}
//
//	@Override
//	public QuickFix solucion() {
//		Builder builder = new Builder(nombre());
//
//		for (String mod : nombresMods) {
//			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferenteMod(mod));
//			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(mod));
//		}
//
//		return builder.construir();
//	}
//}
