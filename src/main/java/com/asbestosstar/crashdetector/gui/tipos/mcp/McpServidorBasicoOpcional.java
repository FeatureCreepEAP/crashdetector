package com.asbestosstar.crashdetector.gui.tipos.mcp;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.config.json.Json;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * Servidor MCP básico y opcional.
 *
 * Esta clase NO depende directamente de las clases MCP/CFR en tiempo de
 * compilación. Usa reflection y APIs internas para que la GUI pueda abrirse
 * aunque esas dependencias no estén presentes.
 */
public class McpServidorBasicoOpcional {

	public static final String[] CLASES_MCP_POSIBLES = new String[] { "io.modelcontextprotocol.server.McpServer",
			"io.modelcontextprotocol.server.McpServerFeatures",
			"io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider",
			"io.modelcontextprotocol.spec.McpSchema", "io.modelcontextprotocol.spec.McpServerSession",
			"io.modelcontextprotocol.sdk.server.McpServer", "io.modelcontextprotocol.sdk.server.McpServerFeatures",
			"io.modelcontextprotocol.sdk.spec.McpSchema" };

	public static final String[] CLASES_CFR_POSIBLES = new String[] { "org.benf.cfr.reader.Main",
			"org.benf.cfr.reader.api.CfrDriver", "org.benf.cfr.reader.api.OutputSinkFactory" };

	/**
	 * Para este servidor básico, solo necesitamos HttpServer y un motor JSON. MCP
	 * SDK y CFR son capacidades adicionales, no condiciones para activar el botón.
	 */
	public static boolean dependenciasDisponibles() {
		return httpServerDisponible() && jsonDisponible();
	}

	public static boolean httpServerDisponible() {
		return existeClase("com.sun.net.httpserver.HttpServer");
	}

	public static boolean jsonDisponible() {
		try {
			Json.crearObjeto();
			return true;
		} catch (Throwable t) {
			CrashDetectorLogger.log("MCP JSON no disponible: " + t.getMessage());
			return false;
		}
	}

	public static boolean mcpSdkDisponible() {
		return existeAlgunaClase(CLASES_MCP_POSIBLES);
	}

	public static boolean cfrDisponible() {
		return existeAlgunaClase(CLASES_CFR_POSIBLES);
	}

	public static String diagnosticoDependencias() {
		return "http=" + httpServerDisponible() + ", json=" + jsonDisponible() + ", mcpSdk=" + mcpSdkDisponible()
				+ ", cfr=" + cfrDisponible();
	}

	public static boolean existeAlgunaClase(String[] nombres) {
		for (String nombre : nombres) {
			if (existeClase(nombre)) {
				return true;
			}
		}
		return false;
	}

	public static boolean existeClase(String nombreClase) {
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if (cl == null) {
				cl = McpServidorBasicoOpcional.class.getClassLoader();
			}
			Class.forName(nombreClase, false, cl);
			CrashDetectorLogger.log("MCP tiene clase " + nombreClase);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public static final Map<String, HerramientaMcp> HERRAMIENTAS_MCP = new LinkedHashMap<String, HerramientaMcp>();

	static {
		registrarHerramientasPredeterminadas();
	}

	public static void registrarHerramientasPredeterminadas() {
		if (!HERRAMIENTAS_MCP.isEmpty()) {
			return;
		}

		registrarHerramienta(new HerramientaMcp("crashdetector_estado", Collections.<ParametroMcp>emptyList()) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return herramientaEstado();
			}
		});

		registrarHerramienta(new HerramientaMcp("listar_mods_cargados", Collections.<ParametroMcp>emptyList()) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return herramientaListarModsCargados();
			}
		});

		registrarHerramienta(
				new HerramientaMcp("buscar_mods_con_termino", parametros(new ParametroMcp("termino", true))) {
					@Override
					public String ejecutar(Json.Nodo argumentos) {
						return herramientaBuscarModsConTermino(argumentos.obtener("termino").comoCadena());
					}
				});

		registrarHerramienta(new HerramientaMcp("existe_clase_en_mods", parametros(new ParametroMcp("clase", true))) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return herramientaExisteClase(argumentos.obtener("clase").comoCadena());
			}
		});

		registrarHerramienta(
				new HerramientaMcp("obtener_bytes_clase_info", parametros(new ParametroMcp("clase", true))) {
					@Override
					public String ejecutar(Json.Nodo argumentos) {
						return herramientaBytesClaseInfo(argumentos.obtener("clase").comoCadena());
					}
				});

		registrarHerramienta(
				new HerramientaMcp("buscar_archivo_en_mods", parametros(new ParametroMcp("archivo", true))) {
					@Override
					public String ejecutar(Json.Nodo argumentos) {
						return herramientaBuscarArchivoEnMods(argumentos.obtener("archivo").comoCadena());
					}
				});

		registrarHerramienta(new HerramientaMcp("cfr_disponible", Collections.<ParametroMcp>emptyList()) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return "CFR disponible: " + cfrDisponible();
			}
		});

		registrarHerramienta(
				new HerramientaMcp("listar_verificaciones_activas", Collections.<ParametroMcp>emptyList()) {
					@Override
					public String ejecutar(Json.Nodo argumentos) {
						return herramientaListarVerificacionesActivas();
					}
				});

		registrarHerramienta(new HerramientaMcp("leer_verificacion_activa", parametros(new ParametroMcp("id", true))) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return herramientaLeerVerificacionActiva(argumentos.obtener("id").comoCadena());
			}
		});

		registrarHerramienta(new HerramientaMcp("listar_sponge_mixins", Collections.<ParametroMcp>emptyList()) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return herramientaListarSpongeMixins();
			}
		});

		registrarHerramienta(new HerramientaMcp("ver_sponge_mixin", parametros(new ParametroMcp("clase", true))) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return herramientaVerSpongeMixin(argumentos.obtener("clase").comoCadena());
			}
		});

		registrarHerramienta(
				new HerramientaMcp("listar_conflictos_sponge_mixin", Collections.<ParametroMcp>emptyList()) {
					@Override
					public String ejecutar(Json.Nodo argumentos) {
						return herramientaListarConflictosSpongeMixin();
					}
				});

		registrarHerramienta(new HerramientaMcp("depmap_resumen", Collections.<ParametroMcp>emptyList()) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return herramientaDepmapResumen();
			}
		});

		registrarHerramienta(new HerramientaMcp("depmap_mod", parametros(new ParametroMcp("mod", true))) {
			@Override
			public String ejecutar(Json.Nodo argumentos) {
				return herramientaDepmapMod(argumentos.obtener("mod").comoCadena());
			}
		});

	}

	public static String herramientaListarSpongeMixins() {
		Buscador.cargarYPrecargarClasesEnCache();

		List<ArchivoDeMod> mods = Buscador.obtenerTodosLosModsYSubmodsRecursivos();
		if (mods.isEmpty()) {
			return "No hay mods cargados.";
		}

		StringBuilder sb = new StringBuilder();
		int total = 0;

		for (ArchivoDeMod mod : mods) {
			List<String> mixins = mod.buscarClasesMixin();
			if (mixins == null || mixins.isEmpty()) {
				continue;
			}

			sb.append("\nMOD: ").append(mod.ubicacion_para_publicar()).append("\n");

			int max = Math.min(mixins.size(), 100);
			for (int i = 0; i < max; i++) {
				sb.append("- ").append(mixins.get(i)).append("\n");
				total++;
			}

			if (mixins.size() > max) {
				sb.append("... y ").append(mixins.size() - max).append(" mas en este mod.\n");
			}
		}

		if (total == 0) {
			return "No se encontraron Sponge mixins.";
		}

		return "Sponge mixins encontrados: " + total + "\n" + sb.toString();
	}

	public static String herramientaVerSpongeMixin(String clase) {
		if (clase == null || clase.trim().isEmpty()) {
			return "Clase vacia.";
		}

		Buscador.cargarYPrecargarClasesEnCache();

		String claseInterna = Buscador.convertirFormatoClase(clase.trim());

		for (ArchivoDeMod mod : Buscador.obtenerTodosLosModsYSubmodsRecursivos()) {
			try {
				ArchivoDeMod.MixinInfo info = mod.obtenerInfoMixin(claseInterna);
				if (info != null) {
					return formatearMixinInfo(info);
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		String clasePuntos = claseInterna.replace('/', '.');

		for (ArchivoDeMod mod : Buscador.obtenerTodosLosModsYSubmodsRecursivos()) {
			try {
				ArchivoDeMod.MixinInfo info = ArchivoDeMod.obtenerMixinDesdeCache(clasePuntos);
				if (info != null) {
					return formatearMixinInfo(info);
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		return "No se encontro Sponge mixin: " + claseInterna;
	}

	public static String formatearMixinInfo(ArchivoDeMod.MixinInfo info) {
		StringBuilder sb = new StringBuilder();

		sb.append("Mixin: ").append(info.obtenerNombreClase()).append("\n");
		sb.append("Jar origen: ").append(info.obtenerJarOrigen()).append("\n");

		sb.append("Targets:\n");
		for (String target : info.obtenerTargets()) {
			sb.append("- ").append(target).append("\n");
		}

		sb.append("Targets Inject:\n");
		for (String target : info.obtenerTargetsInject()) {
			sb.append("- ").append(target).append("\n");
		}

		sb.append("Metodos mixin:\n");
		for (ArchivoDeMod.MixinMetodoInfo metodo : info.obtenerMetodosMixin()) {
			sb.append("- ").append(metodo.toString()).append("\n");
		}

		sb.append("Campos mixin:\n");
		for (ArchivoDeMod.MixinCampoInfo campo : info.obtenerCamposMixin()) {
			sb.append("- ").append(campo.toString()).append("\n");
		}

		sb.append("Refmap metodos: ").append(info.obtenerRefmapMetodos().size()).append("\n");
		sb.append("Refmap campos: ").append(info.obtenerRefmapCampos().size()).append("\n");

		return sb.toString();
	}

	public static String herramientaListarConflictosSpongeMixin() {
		Analizador analizador = obtenerAnalizadorActual();

		StringBuilder sb = new StringBuilder();
		int total = 0;

		for (Verificaciones ver : analizador.obtenerActivados()) {
			String clase = ver.getClass().getSimpleName().toLowerCase();
			String id = ver.id() == null ? "" : ver.id().toLowerCase();
			String nombre = ver.nombre() == null ? "" : ver.nombre().toLowerCase();

			boolean esMixin = clase.contains("sponge") || clase.contains("mixin") || id.contains("sponge")
					|| id.contains("mixin") || nombre.contains("sponge") || nombre.contains("mixin");

			if (!esMixin) {
				continue;
			}

			total++;
			sb.append("ID: ").append(ver.id()).append("\n");
			sb.append("Nombre: ").append(ver.nombre()).append("\n");
			sb.append("Clase: ").append(ver.getClass().getName()).append("\n");
			sb.append("Mensaje: ").append(textoSeguro(ver.mensaje())).append("\n");
			sb.append("Como string: ").append(textoSeguro(ver.comoString())).append("\n");
			sb.append("\n");
		}

		if (total == 0) {
			return "No hay conflictos Sponge/Mixin activos.";
		}

		return "Conflictos Sponge/Mixin activos: " + total + "\n\n" + sb.toString();
	}

	public static String herramientaDepmapResumen() {
		DependenciasMcp deps = calcularDependenciasMcp();

		if (deps.mods.isEmpty()) {
			return "No hay mods cargados.";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Dep map MCP\n");
		sb.append("Mods: ").append(deps.mods.size()).append("\n");
		sb.append("Enlaces: ").append(deps.referenciasPorEnlace.size()).append("\n\n");

		List<ArchivoDeMod> ordenados = new ArrayList<ArchivoDeMod>(deps.mods);
		ordenados.sort((a, b) -> {
			int da = deps.dependientesDirectos.getOrDefault(a, Collections.emptySet()).size();
			int db = deps.dependientesDirectos.getOrDefault(b, Collections.emptySet()).size();
			if (da != db) {
				return Integer.compare(db, da);
			}
			return a.ubicacion_para_publicar().compareToIgnoreCase(b.ubicacion_para_publicar());
		});

		int max = Math.min(ordenados.size(), 100);
		for (int i = 0; i < max; i++) {
			ArchivoDeMod mod = ordenados.get(i);
			int dependencias = deps.dependenciasDirectas.getOrDefault(mod, Collections.emptySet()).size();
			int dependientes = deps.dependientesDirectos.getOrDefault(mod, Collections.emptySet()).size();

			sb.append("- ").append(mod.ubicacion_para_publicar()).append(" | dependencias=").append(dependencias)
					.append(" | dependientes=").append(dependientes).append("\n");
		}

		if (ordenados.size() > max) {
			sb.append("... y ").append(ordenados.size() - max).append(" mods mas.\n");
		}

		return sb.toString();
	}

	public static String herramientaDepmapMod(String modTexto) {
		if (modTexto == null || modTexto.trim().isEmpty()) {
			return "Mod vacio.";
		}

		DependenciasMcp deps = calcularDependenciasMcp();
		ArchivoDeMod mod = buscarModPorTexto(deps.mods, modTexto.trim());

		if (mod == null) {
			return "No se encontro mod: " + modTexto;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Mod: ").append(mod.ubicacion_para_publicar()).append("\n");
		sb.append("Nombres: ").append(nombresMod(mod)).append("\n\n");

		Set<ArchivoDeMod> dependencias = deps.dependenciasDirectas.getOrDefault(mod, Collections.emptySet());
		Set<ArchivoDeMod> dependientes = deps.dependientesDirectos.getOrDefault(mod, Collections.emptySet());

		sb.append("Dependencias directas: ").append(dependencias.size()).append("\n");
		for (ArchivoDeMod dep : dependencias) {
			sb.append("- ").append(dep.ubicacion_para_publicar()).append("\n");

			String clave = claveEnlace(mod, dep);
			List<String> refs = deps.referenciasPorEnlace.getOrDefault(clave, Collections.emptyList());
			int maxRefs = Math.min(refs.size(), 10);
			for (int i = 0; i < maxRefs; i++) {
				sb.append("  -> ").append(refs.get(i)).append("\n");
			}
			if (refs.size() > maxRefs) {
				sb.append("  ... y ").append(refs.size() - maxRefs).append(" referencias mas.\n");
			}
		}

		sb.append("\nDependientes directos: ").append(dependientes.size()).append("\n");
		for (ArchivoDeMod dep : dependientes) {
			sb.append("- ").append(dep.ubicacion_para_publicar()).append("\n");
		}

		return sb.toString();
	}

	public static DependenciasMcp calcularDependenciasMcp() {
		Buscador.cargarYPrecargarClasesEnCache();

		DependenciasMcp deps = new DependenciasMcp();
		deps.mods.addAll(Buscador.obtenerTodosLosModsYSubmodsRecursivos());

		Map<String, ArchivoDeMod> dueñoPorClase = new LinkedHashMap<String, ArchivoDeMod>();

		for (ArchivoDeMod mod : deps.mods) {
			for (String clase : mod.obtenerTodosLosNombresDeClases()) {
				if (clase != null && !dueñoPorClase.containsKey(clase)) {
					dueñoPorClase.put(clase, mod);
				}
			}
		}

		for (ArchivoDeMod mod : deps.mods) {
			for (String clase : mod.obtenerTodosLosNombresDeClases()) {
				List<ArchivoDeMod.InfoMetodo> metodos;

				try {
					metodos = mod.obtenerMetodosConReferencias(clase);
				} catch (Throwable t) {
					continue;
				}

				if (metodos == null) {
					continue;
				}

				for (ArchivoDeMod.InfoMetodo metodo : metodos) {
					registrarReferenciasDeMetodoMcp(deps, dueñoPorClase, mod, clase, metodo,
							metodo.obtenerReferenciasAMetodos());
					registrarReferenciasDeMetodoMcp(deps, dueñoPorClase, mod, clase, metodo,
							metodo.obtenerReferenciasACampos());
				}
			}
		}

		return deps;
	}

	public static void registrarReferenciasDeMetodoMcp(DependenciasMcp deps, Map<String, ArchivoDeMod> dueñoPorClase,
			ArchivoDeMod modOrigen, String claseOrigen, ArchivoDeMod.InfoMetodo metodoOrigen,
			List<ArchivoDeMod.Referencia> referencias) {

		if (referencias == null) {
			return;
		}

		for (ArchivoDeMod.Referencia ref : referencias) {
			String claseRef = normalizarNombreClaseInternoMcp(ref.obtenerClase());
			ArchivoDeMod modDestino = dueñoPorClase.get(claseRef);

			if (modDestino == null || modDestino == modOrigen) {
				continue;
			}

			deps.dependenciasDirectas.computeIfAbsent(modOrigen, k -> new LinkedHashSet<ArchivoDeMod>())
					.add(modDestino);

			deps.dependientesDirectos.computeIfAbsent(modDestino, k -> new LinkedHashSet<ArchivoDeMod>())
					.add(modOrigen);

			String texto = convertirFormatoClasePuntosMcp(claseOrigen) + "." + metodoOrigen.obtenerNombre()
					+ metodoOrigen.obtenerDescriptor() + " -> " + convertirFormatoClasePuntosMcp(claseRef) + "."
					+ ref.obtenerNombre();

			deps.referenciasPorEnlace.computeIfAbsent(claveEnlace(modOrigen, modDestino), k -> new ArrayList<String>())
					.add(texto);
		}
	}

	public static ArchivoDeMod buscarModPorTexto(List<ArchivoDeMod> mods, String texto) {
		String buscado = texto.toLowerCase();

		for (ArchivoDeMod mod : mods) {
			if (mod.ubicacion_para_publicar().toLowerCase().contains(buscado)) {
				return mod;
			}

			for (String nombre : mod.nombre()) {
				if (nombre != null && nombre.toLowerCase().contains(buscado)) {
					return mod;
				}
			}
		}

		return null;
	}

	public static String claveEnlace(ArchivoDeMod origen, ArchivoDeMod destino) {
		return origen.ubicacion_para_publicar() + " -> " + destino.ubicacion_para_publicar();
	}

	public static String normalizarNombreClaseInternoMcp(String nombre) {
		if (nombre == null) {
			return "";
		}

		String s = nombre.trim();

		if (s.startsWith("L") && s.endsWith(";")) {
			s = s.substring(1, s.length() - 1);
		}

		if (s.toLowerCase().endsWith(".class")) {
			s = s.substring(0, s.length() - ".class".length());
		}

		return s.replace('.', '/');
	}

	public static String convertirFormatoClasePuntosMcp(String nombre) {
		if (nombre == null) {
			return "";
		}
		return nombre.replace('/', '.');
	}

	public static class DependenciasMcp {
		public final List<ArchivoDeMod> mods = new ArrayList<ArchivoDeMod>();
		public final Map<ArchivoDeMod, Set<ArchivoDeMod>> dependenciasDirectas = new LinkedHashMap<ArchivoDeMod, Set<ArchivoDeMod>>();
		public final Map<ArchivoDeMod, Set<ArchivoDeMod>> dependientesDirectos = new LinkedHashMap<ArchivoDeMod, Set<ArchivoDeMod>>();
		public final Map<String, List<String>> referenciasPorEnlace = new LinkedHashMap<String, List<String>>();
	}

	public static Analizador obtenerAnalizadorActual() {
		if (MonitorDePID.analizador != null) {
			return MonitorDePID.analizador;
		}
		return new Analizador();
	}

	public static String herramientaListarVerificacionesActivas() {
		Analizador analizador = obtenerAnalizadorActual();

		Set<Verificaciones> activas = analizador.obtenerActivados();
		if (activas.isEmpty()) {
			return "No hay verificaciones activas.";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Verificaciones activas: ").append(activas.size()).append("\n");

		for (Verificaciones ver : activas) {
			sb.append("- ").append(ver.id()).append(" | ").append(ver.nombre()).append(" | prioridad=")
					.append(ver.prioridad()).append("\n");
		}

		return sb.toString();
	}

	public static String herramientaLeerVerificacionActiva(String id) {
		if (id == null || id.trim().isEmpty()) {
			return "ID vacio.";
		}

		String buscado = id.trim();
		Analizador analizador = obtenerAnalizadorActual();

		for (Verificaciones ver : analizador.obtenerActivados()) {
			if (coincideVerificacion(ver, buscado)) {
				StringBuilder sb = new StringBuilder();

				sb.append("ID: ").append(ver.id()).append("\n");
				sb.append("Nombre: ").append(ver.nombre()).append("\n");
				sb.append("Clase: ").append(ver.getClass().getName()).append("\n");
				sb.append("Prioridad: ").append(ver.prioridad()).append("\n");
				sb.append("Mensaje:\n").append(textoSeguro(ver.mensaje())).append("\n");
				sb.append("Como string:\n").append(textoSeguro(ver.comoString())).append("\n");

				QuickFix solucion = ver.solucion();
				if (solucion != null && solucion != QuickFix.NINGUN) {
					sb.append("QuickFix: ").append(solucion.obtenerEnlace()).append("\n");
				}

				return sb.toString();
			}
		}

		return "No se encontro verificacion activa con id/nombre/clase: " + buscado;
	}

	public static boolean coincideVerificacion(Verificaciones ver, String buscado) {
		if (ver == null || buscado == null) {
			return false;
		}

		if (buscado.equalsIgnoreCase(ver.id())) {
			return true;
		}

		if (buscado.equalsIgnoreCase(ver.nombre())) {
			return true;
		}

		if (buscado.equalsIgnoreCase(ver.getClass().getSimpleName())) {
			return true;
		}

		if (buscado.equalsIgnoreCase(ver.getClass().getName())) {
			return true;
		}

		return false;
	}

	public static String textoSeguro(String texto) {
		if (texto == null) {
			return "";
		}
		return texto.replace("<br>", "\n").replace("<br/>", "\n").replace("<br />", "\n").replaceAll("<[^>]+>", "");
	}

	public static String herramientaBuscarArchivoEnMods(String archivo) {
		if (archivo == null || archivo.trim().isEmpty()) {
			return "Archivo vacio.";
		}

		Buscador.cargar();

		List<String> encontrados = Buscador.obtenerModsConNombre(archivo.trim());
		if (encontrados.isEmpty()) {
			return "No se encontraron mods con archivo: " + archivo;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Archivos encontrados para ").append(archivo).append(":\n");

		int max = Math.min(encontrados.size(), 100);
		for (int i = 0; i < max; i++) {
			sb.append("- ").append(encontrados.get(i)).append("\n");
		}

		if (encontrados.size() > max) {
			sb.append("... y ").append(encontrados.size() - max).append(" mas.\n");
		}

		return sb.toString();
	}

	public static void registrarHerramienta(HerramientaMcp herramienta) {
		if (herramienta == null || herramienta.nombre == null || herramienta.nombre.trim().isEmpty()) {
			return;
		}

		HERRAMIENTAS_MCP.put(herramienta.nombre, herramienta);
	}

	public static List<ParametroMcp> parametros(ParametroMcp parametro) {
		List<ParametroMcp> lista = new ArrayList<ParametroMcp>();
		lista.add(parametro);
		return lista;
	}

	public static Object iniciar(int puerto) throws IOException {
		if (!dependenciasDisponibles()) {
			throw new IllegalStateException("Faltas " + diagnosticoDependencias());
		}

		HttpServer servidor = HttpServer.create(new InetSocketAddress("127.0.0.1", puerto), 0);

		servidor.createContext("/", intercambio -> {
			responderTexto(intercambio, "CrashDetector MCP activo\n" + diagnosticoDependencias());
		});

		servidor.createContext("/mcp", intercambio -> {
			if (!"POST".equalsIgnoreCase(intercambio.getRequestMethod())) {
				responderJson(intercambio, 405, errorJson(null, -32000, "method not allowed"));
				return;
			}

			try {
				String cuerpo = new String(leerTodo(intercambio), StandardCharsets.UTF_8);
				String respuesta = procesarJsonRpcBasico(cuerpo);
				responderJson(intercambio, 200, respuesta);
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				responderJson(intercambio, 200, errorJson(null, -32603, t.getMessage()));
			}
		});

		servidor.start();
		CrashDetectorLogger.log("Servidor MCP básico iniciado en 127.0.0.1:" + puerto);
		return servidor;
	}

	public static String procesarJsonRpcBasico(String cuerpo) {
		Json.Nodo peticion = Json.leer(cuerpo);

		String metodo = "";
		String id = "null";

		try {
			metodo = peticion.obtener("method").comoCadena();
		} catch (Throwable t) {
			metodo = "";
		}

		try {
			id = peticion.obtener("id").escribir();
		} catch (Throwable t) {
			id = "null";
		}

		if ("initialize".equals(metodo)) {
			return respuestaInicializar(id);
		}

		if ("notifications/initialized".equals(metodo)) {
			return respuestaVacia(id);
		}

		if ("tools/list".equals(metodo)) {
			return respuestaListaHerramientas(id);
		}

		if ("tools/call".equals(metodo)) {
			return ejecutarHerramienta(id, peticion);
		}

		return errorJson(id, -32601, "Metodo MCP no implementado todavia: " + metodo);
	}

	public static String respuestaInicializar(String id) {
		Json.Nodo raiz = Json.crearObjeto();
		raiz.obtener("jsonrpc").poner("2.0");
		raiz.obtener("id").ponerValorFlexible(idSinComillasSiNumero(id));

		Json.Nodo result = raiz.obtener("result");
		result.obtener("protocolVersion").poner("2025-06-18");

		Json.Nodo serverInfo = result.obtener("serverInfo");
		serverInfo.obtener("name").poner("CrashDetector MCP");
		serverInfo.obtener("version").poner("0.2");

		Json.Nodo capabilities = result.obtener("capabilities");
		capabilities.obtener("tools");

		return raiz.escribir();
	}

	public static String respuestaVacia(String id) {
		Json.Nodo raiz = Json.crearObjeto();
		raiz.obtener("jsonrpc").poner("2.0");
		raiz.obtener("id").ponerValorFlexible(idSinComillasSiNumero(id));
		raiz.obtener("result");
		return raiz.escribir();
	}

	public static String respuestaListaHerramientas(String id) {
		Json.Nodo raiz = Json.crearObjeto();
		raiz.obtener("jsonrpc").poner("2.0");
		raiz.obtener("id").ponerValorFlexible(idSinComillasSiNumero(id));

		Json.Nodo tools = raiz.obtener("result").obtener("tools");

		for (HerramientaMcp herramienta : HERRAMIENTAS_MCP.values()) {
			tools.agregar(herramienta.aJson());
		}

		return raiz.escribir();
	}

	public static void agregarHerramientaSinArgs(Json.Nodo tools, String nombre, String descripcion) {
		Json.Nodo herramienta = Json.crearObjeto();
		herramienta.obtener("name").poner(nombre);
		herramienta.obtener("description").poner(descripcion);

		Json.Nodo schema = herramienta.obtener("inputSchema");
		schema.obtener("type").poner("object");
		schema.obtener("properties");

		tools.agregar(herramienta);
	}

	public static void agregarHerramientaConCadena(Json.Nodo tools, String nombre, String descripcion, String parametro,
			String descripcionParametro) {
		Json.Nodo herramienta = Json.crearObjeto();
		herramienta.obtener("name").poner(nombre);
		herramienta.obtener("description").poner(descripcion);

		Json.Nodo schema = herramienta.obtener("inputSchema");
		schema.obtener("type").poner("object");

		Json.Nodo props = schema.obtener("properties");
		Json.Nodo prop = props.obtener(parametro);
		prop.obtener("type").poner("string");
		prop.obtener("description").poner(descripcionParametro);

		Json.Nodo required = schema.obtener("required");
		required.agregar(parametro);

		tools.agregar(herramienta);
	}

	public static String ejecutarHerramienta(String id, Json.Nodo peticion) {
		String nombre = "";

		try {
			nombre = peticion.obtener("params").obtener("name").comoCadena();
		} catch (Throwable t) {
			return errorJson(id, -32602, "Falta params.name");
		}

		Json.Nodo args = null;
		try {
			args = peticion.obtener("params").obtener("arguments");
		} catch (Throwable t) {
			args = Json.crearObjeto();
		}

		HerramientaMcp herramienta = HERRAMIENTAS_MCP.get(nombre);
		if (herramienta == null) {
			return errorJson(id, -32601, "Herramienta no registrada: " + nombre);
		}

		try {
			String resultado = herramienta.ejecutar(args);
			return respuestaTexto(id, resultado);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return errorJson(id, -32603, t.getMessage());
		}
	}

	public static String herramientaEstado() {
		return "CrashDetector MCP activo\n" + "Dependencias: " + diagnosticoDependencias() + "\n" + "Mods cargados: "
				+ Buscador.obtenerModsPrimerNivel().size() + "\n" + "Puede analizar bytecode: "
				+ Buscador.puedeAnalizarElContentidoDeClase();
	}

	public static String herramientaBuscarModsConTermino(String termino) {
		if (termino == null || termino.trim().isEmpty()) {
			return "Termino vacio.";
		}

		Buscador.cargar();

		List<ArchivoDeMod> encontrados = Buscador.buscarModsConTermino(termino.trim());
		if (encontrados.isEmpty()) {
			return "No se encontraron mods con: " + termino;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Mods encontrados para ").append(termino).append(":\n");

		int max = Math.min(encontrados.size(), 50);
		for (int i = 0; i < max; i++) {
			ArchivoDeMod mod = encontrados.get(i);
			sb.append("- ").append(nombresMod(mod)).append(" | ").append(mod.ubicacion_para_publicar()).append("\n");
		}

		if (encontrados.size() > max) {
			sb.append("... y ").append(encontrados.size() - max).append(" mas.\n");
		}

		return sb.toString();
	}

	public static String herramientaExisteClase(String clase) {
		if (clase == null || clase.trim().isEmpty()) {
			return "Clase vacia.";
		}

		Buscador.cargarYPrecargarClasesEnCache();

		String claseInterna = Buscador.convertirFormatoClase(clase.trim());
		boolean existe = Buscador.existeClaseEnAlgunMod(claseInterna);

		return "Clase: " + claseInterna + "\nExiste en algun mod: " + existe;
	}

	public static String herramientaBytesClaseInfo(String clase) {
		if (clase == null || clase.trim().isEmpty()) {
			return "Clase vacia.";
		}

		Buscador.cargarYPrecargarClasesEnCache();

		String claseInterna = Buscador.convertirFormatoClase(clase.trim());
		byte[] bytes = Buscador.obtenerBytesDeClase(claseInterna);

		if (bytes == null) {
			return "No se encontraron bytes para: " + claseInterna;
		}

		return "Clase: " + claseInterna + "\nBytes encontrados: " + bytes.length + "\nCFR disponible: "
				+ cfrDisponible();
	}

	public static String herramientaListarModsCargados() {
		Buscador.cargar();

		List<ArchivoDeMod> mods = Buscador.obtenerTodosLosModsYSubmodsRecursivos();
		if (mods.isEmpty()) {
			return "No hay mods cargados.";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Mods cargados: ").append(mods.size()).append("\n");

		int max = Math.min(mods.size(), 100);
		for (int i = 0; i < max; i++) {
			ArchivoDeMod mod = mods.get(i);
			sb.append("- ").append(nombresMod(mod)).append(" | ").append(mod.ubicacion_para_publicar()).append("\n");
		}

		if (mods.size() > max) {
			sb.append("... y ").append(mods.size() - max).append(" mas.\n");
		}

		return sb.toString();
	}

	public static String nombresMod(ArchivoDeMod mod) {
		try {
			List<String> nombres = mod.nombre();
			if (nombres == null || nombres.isEmpty()) {
				return "(sin nombre)";
			}
			return String.valueOf(nombres);
		} catch (Throwable t) {
			return "(sin nombre)";
		}
	}

	public static String respuestaTexto(String id, String texto) {
		Json.Nodo raiz = Json.crearObjeto();
		raiz.obtener("jsonrpc").poner("2.0");
		raiz.obtener("id").ponerValorFlexible(idSinComillasSiNumero(id));

		Json.Nodo content = raiz.obtener("result").obtener("content");
		Json.Nodo item = Json.crearObjeto();
		item.obtener("type").poner("text");
		item.obtener("text").poner(texto == null ? "" : texto);
		content.agregar(item);

		return raiz.escribir();
	}

	public static String errorJson(String id, int codigo, String mensaje) {
		Json.Nodo raiz = Json.crearObjeto();
		raiz.obtener("jsonrpc").poner("2.0");

		if (id == null) {
			raiz.obtener("id").poner("null");
		} else {
			raiz.obtener("id").ponerValorFlexible(idSinComillasSiNumero(id));
		}

		Json.Nodo error = raiz.obtener("error");
		error.obtener("code").poner(codigo);
		error.obtener("message").poner(mensaje == null ? "" : mensaje);

		return raiz.escribir();
	}

	public static Object idSinComillasSiNumero(String id) {
		if (id == null || "null".equals(id)) {
			return "null";
		}

		String limpio = id.trim();
		if (limpio.startsWith("\"") && limpio.endsWith("\"") && limpio.length() >= 2) {
			return limpio.substring(1, limpio.length() - 1);
		}

		try {
			return Integer.valueOf(limpio);
		} catch (Throwable t) {
			return limpio;
		}
	}

	public static byte[] leerTodo(HttpExchange intercambio) throws IOException {
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int leidos;
		while ((leidos = intercambio.getRequestBody().read(buffer)) != -1) {
			baos.write(buffer, 0, leidos);
		}
		return baos.toByteArray();
	}

	public static void responderTexto(HttpExchange intercambio, String texto) throws IOException {
		byte[] datos = texto.getBytes(StandardCharsets.UTF_8);
		intercambio.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
		intercambio.sendResponseHeaders(200, datos.length);
		OutputStream os = intercambio.getResponseBody();
		os.write(datos);
		os.close();
	}

	public static void responderJson(HttpExchange intercambio, int codigo, String json) throws IOException {
		byte[] datos = json.getBytes(StandardCharsets.UTF_8);
		intercambio.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
		intercambio.sendResponseHeaders(codigo, datos.length);
		OutputStream os = intercambio.getResponseBody();
		os.write(datos);
		os.close();
	}

	public static abstract class HerramientaMcp {
		public final String nombre;
		public final List<ParametroMcp> parametros;

		public HerramientaMcp(String nombre, List<ParametroMcp> parametros) {
			this.nombre = nombre;
			this.parametros = parametros == null ? Collections.<ParametroMcp>emptyList() : parametros;
		}

		public Json.Nodo aJson() {
			Json.Nodo herramienta = Json.crearObjeto();
			herramienta.obtener("name").poner(nombre);

			Json.Nodo schema = herramienta.obtener("inputSchema");
			schema.obtener("type").poner("object");

			Json.Nodo props = schema.obtener("properties");
			Json.Nodo required = schema.obtener("required");

			for (ParametroMcp parametro : parametros) {
				Json.Nodo prop = props.obtener(parametro.nombre);
				prop.obtener("type").poner("string");

				if (parametro.requerido) {
					required.agregar(parametro.nombre);
				}
			}

			return herramienta;
		}

		public abstract String ejecutar(Json.Nodo argumentos);
	}

	public static class ParametroMcp {
		public final String nombre;
		public final boolean requerido;

		public ParametroMcp(String nombre, boolean requerido) {
			this.nombre = nombre;
			this.requerido = requerido;
		}
	}

	public static void detener(Object servidor) {
		if (servidor == null) {
			return;
		}

		try {
			Method metodo = servidor.getClass().getMethod("stop", int.class);
			metodo.invoke(servidor, 0);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}
}