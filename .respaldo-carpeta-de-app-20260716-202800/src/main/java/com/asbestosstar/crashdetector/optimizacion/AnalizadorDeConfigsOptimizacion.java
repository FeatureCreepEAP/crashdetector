package com.asbestosstar.crashdetector.optimizacion;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;

public class AnalizadorDeConfigsOptimizacion {

	private static final Path CARPETA_CONFIG = new File("./config").toPath();

	public static class MejoraConfig {
		public final String archivo;
		public final String titulo;
		public final String descripcion;
		public final String sugerencia;
		public final String impacto;
		public final String riesgo;

		public MejoraConfig(String archivo, String titulo, String descripcion, String sugerencia, String impacto,
				String riesgo) {
			this.archivo = archivo;
			this.titulo = titulo;
			this.descripcion = descripcion;
			this.sugerencia = sugerencia;
			this.impacto = impacto;
			this.riesgo = riesgo;
		}

		@Override
		public String toString() {
			return "[" + impacto + " / " + riesgo + "] " + archivo + " - " + titulo + ": " + sugerencia;
		}
	}

	public static List<MejoraConfig> obtenerMejorasPotenciales() {
		List<MejoraConfig> mejoras = new ArrayList<>();

		revisarJsonBBE(mejoras);
		revisarJsonEntityCulling(mejoras);
		revisarJsonImmediatelyFast(mejoras);
		revisarJsonNoChatReports(mejoras);

		revisarPropertiesLithium(mejoras);
		revisarPropertiesFerriteCore(mejoras);
		revisarPropertiesModernFix(mejoras);
		revisarPropertiesSodium(mejoras);

		revisarTextoBadOptimizations(mejoras);
		revisarTextoC2ME(mejoras);
		revisarTextoMoreCulling(mejoras);
		revisarTextoServerCore(mejoras);

		return mejoras;
	}

	private static void revisarJsonBBE(List<MejoraConfig> mejoras) {
		String archivo = "BBEConfig.json";
		Nodo raiz = leerJson(archivo);

		if (raiz == null) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloCrearConfigBBE(),
					MonitorDePID.idioma.descripcionCrearConfigBBE(), MonitorDePID.idioma.sugerenciaCrearConfigBBE(),
					MonitorDePID.idioma.impactoMedio(), MonitorDePID.idioma.riesgoBajo());
			return;
		}

		Nodo lista = raiz.obtener("bbe.config.storage.main");

		if (!lista.esArreglo() || !arregloContieneOpcion(lista, "optimize.master", true)) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarOptimizacionMaestraBBE(),
					MonitorDePID.idioma.descripcionActivarOptimizacionMaestraBBE(),
					MonitorDePID.idioma.sugerenciaActivarOptimizacionMaestraBBE(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoBajo());
		}

		if (!lista.esArreglo() || !arregloContieneOpcion(lista, "misc.sign_text_culling", true)) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarCullingTextoCartelesBBE(),
					MonitorDePID.idioma.descripcionActivarCullingTextoCartelesBBE(),
					MonitorDePID.idioma.sugerenciaActivarCullingTextoCartelesBBE(),
					MonitorDePID.idioma.impactoBajoMedio(), MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarJsonEntityCulling(List<MejoraConfig> mejoras) {
		String archivo = "entityculling.json";
		Nodo raiz = leerJson(archivo);

		if (raiz == null)
			return;

		if (obtenerEnteroSeguro(raiz, "sleepDelay", 10) < 100) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloAumentarSleepDelayEntityCulling(),
					MonitorDePID.idioma.descripcionAumentarSleepDelayEntityCulling(),
					MonitorDePID.idioma.sugerenciaAumentarSleepDelayEntityCulling(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoBajo());
		}

		if (obtenerEnteroSeguro(raiz, "hitboxLimit", 50) < 90) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloSubirLimiteHitboxEntityCulling(),
					MonitorDePID.idioma.descripcionSubirLimiteHitboxEntityCulling(),
					MonitorDePID.idioma.sugerenciaSubirLimiteHitboxEntityCulling(),
					MonitorDePID.idioma.impactoBajoMedio(), MonitorDePID.idioma.riesgoBajo());
		}

		if (!obtenerBooleanoSeguro(raiz, "disableF3", false)) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloDesactivarDatosF3EntityCulling(),
					MonitorDePID.idioma.descripcionDesactivarDatosF3EntityCulling(),
					MonitorDePID.idioma.sugerenciaDesactivarDatosF3EntityCulling(), MonitorDePID.idioma.impactoBajo(),
					MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarJsonImmediatelyFast(List<MejoraConfig> mejoras) {
		String archivo = "immediatelyfast.json";
		Nodo raiz = leerJson(archivo);

		if (raiz == null)
			return;

		if (!obtenerBooleanoSeguro(raiz, "experimental_sign_text_buffering", false)) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarBufferingSignsImmediatelyFast(),
					MonitorDePID.idioma.descripcionActivarBufferingSignsImmediatelyFast(),
					MonitorDePID.idioma.sugerenciaActivarBufferingSignsImmediatelyFast(),
					MonitorDePID.idioma.impactoMedio(), MonitorDePID.idioma.riesgoMedio());
		}

		if (!obtenerBooleanoSeguro(raiz, "experimental_disable_resource_pack_conflict_handling", false)) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloReducirConflictosResourcePacksImmediatelyFast(),
					MonitorDePID.idioma.descripcionReducirConflictosResourcePacksImmediatelyFast(),
					MonitorDePID.idioma.sugerenciaReducirConflictosResourcePacksImmediatelyFast(),
					MonitorDePID.idioma.impactoBajoMedio(), MonitorDePID.idioma.riesgoMedio());
		}
	}

	private static void revisarJsonNoChatReports(List<MejoraConfig> mejoras) {
		String archivo = "NoChatReports/NCR-Client.json";
		Nodo raiz = leerJson(archivo);

		if (raiz == null)
			return;

		if (obtenerBooleanoSeguro(raiz, "showNCRButton", true)) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloOcultarBotonNCR(),
					MonitorDePID.idioma.descripcionOcultarBotonNCR(), MonitorDePID.idioma.sugerenciaOcultarBotonNCR(),
					MonitorDePID.idioma.impactoBajo(), MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarPropertiesLithium(List<MejoraConfig> mejoras) {
		String archivo = "lithium.properties";
		Properties props = leerProperties(archivo);

		if (props == null)
			return;

		if (!"true".equalsIgnoreCase(props.getProperty("mixin.experimental", "false"))) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarMixinsExperimentalesLithium(),
					MonitorDePID.idioma.descripcionActivarMixinsExperimentalesLithium(),
					MonitorDePID.idioma.sugerenciaActivarMixinsExperimentalesLithium(),
					MonitorDePID.idioma.impactoMedio(), MonitorDePID.idioma.riesgoMedioAlto());
		}
	}

	private static void revisarPropertiesFerriteCore(List<MejoraConfig> mejoras) {
		String archivo = "ferritecore.mixin.properties";
		Properties props = leerProperties(archivo);

		if (props == null)
			return;

		if (!"true".equalsIgnoreCase(props.getProperty("useSmallThreadingDetector", "false"))) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloUsarDetectorThreadingPequenoFerriteCore(),
					MonitorDePID.idioma.descripcionUsarDetectorThreadingPequenoFerriteCore(),
					MonitorDePID.idioma.sugerenciaUsarDetectorThreadingPequenoFerriteCore(),
					MonitorDePID.idioma.impactoMedio(), MonitorDePID.idioma.riesgoAlto());
		}
	}

	private static void revisarPropertiesModernFix(List<MejoraConfig> mejoras) {
		String archivo = "modernfix-mixins.properties";
		Properties props = leerProperties(archivo);

		if (props == null)
			return;

		revisarPropiedadTrue(mejoras, archivo, props, "mixin.perf.dynamic_resources",
				MonitorDePID.idioma.tituloModernFixRecursosDinamicos(),
				MonitorDePID.idioma.descripcionModernFixRecursosDinamicos(), MonitorDePID.idioma.impactoAlto(),
				MonitorDePID.idioma.riesgoMedio());

		revisarPropiedadTrue(mejoras, archivo, props, "mixin.perf.dynamic_entity_renderers",
				MonitorDePID.idioma.tituloModernFixRenderizadoresDinamicosEntidades(),
				MonitorDePID.idioma.descripcionModernFixRenderizadoresDinamicosEntidades(),
				MonitorDePID.idioma.impactoMedio(), MonitorDePID.idioma.riesgoMedio());

		revisarPropiedadTrue(mejoras, archivo, props, "mixin.perf.faster_item_rendering",
				MonitorDePID.idioma.tituloModernFixRenderizadoItemsRapido(),
				MonitorDePID.idioma.descripcionModernFixRenderizadoItemsRapido(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoMedio());

		revisarPropiedadTrue(mejoras, archivo, props, "mixin.perf.worldgen_allocation",
				MonitorDePID.idioma.tituloModernFixWorldgenAllocation(),
				MonitorDePID.idioma.descripcionModernFixWorldgenAllocation(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoMedio());

		revisarPropiedadTrue(mejoras, archivo, props, "mixin.perf.ingredient_item_deduplication",
				MonitorDePID.idioma.tituloModernFixDeduplicacionIngredientes(),
				MonitorDePID.idioma.descripcionModernFixDeduplicacionIngredientes(),
				MonitorDePID.idioma.impactoBajoMedio(), MonitorDePID.idioma.riesgoBajo());
	}

	private static void revisarPropertiesSodium(List<MejoraConfig> mejoras) {
		String archivo = "sodium-mixins.properties";
		Properties props = leerProperties(archivo);

		if (props == null)
			return;

		revisarPropiedadTrue(mejoras, archivo, props, "mixin.features.render.world.sky",
				MonitorDePID.idioma.tituloSodiumRenderCielo(), MonitorDePID.idioma.descripcionSodiumRenderCielo(),
				MonitorDePID.idioma.impactoBajo(), MonitorDePID.idioma.riesgoMedio());
	}

	private static void revisarPropiedadTrue(List<MejoraConfig> mejoras, String archivo, Properties props, String clave,
			String titulo, String descripcion, String impacto, String riesgo) {
		if (!"true".equalsIgnoreCase(props.getProperty(clave, "false"))) {
			agregar(mejoras, archivo, titulo, descripcion, clave + "=true", impacto, riesgo);
		}
	}

	private static void revisarTextoBadOptimizations(List<MejoraConfig> mejoras) {
		String archivo = "badoptimizations.txt";
		String texto = leerTexto(archivo);

		if (texto == null)
			return;

		if (!contieneLinea(texto, "enable_lightmap_caching: true")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarLightmapCaching(),
					MonitorDePID.idioma.descripcionActivarLightmapCaching(),
					MonitorDePID.idioma.sugerenciaActivarLightmapCaching(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoBajo());
		}

		if (contieneLinea(texto, "show_f3_text: true")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloOcultarTextoF3BadOptimizations(),
					MonitorDePID.idioma.descripcionOcultarTextoF3BadOptimizations(),
					MonitorDePID.idioma.sugerenciaOcultarTextoF3BadOptimizations(), MonitorDePID.idioma.impactoBajo(),
					MonitorDePID.idioma.riesgoBajo());
		}

		if (contieneLinea(texto, "log_config: true")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloDesactivarLogConfigBadOptimizations(),
					MonitorDePID.idioma.descripcionDesactivarLogConfigBadOptimizations(),
					MonitorDePID.idioma.sugerenciaDesactivarLogConfigBadOptimizations(),
					MonitorDePID.idioma.impactoBajo(), MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarTextoC2ME(List<MejoraConfig> mejoras) {
		String archivo = "c2me.toml";
		String texto = leerTexto(archivo);

		if (texto == null)
			return;

		if (!texto.contains("gcFreeChunkSerializer = true")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarSerializadorGCFreeC2ME(),
					MonitorDePID.idioma.descripcionActivarSerializadorGCFreeC2ME(),
					MonitorDePID.idioma.sugerenciaActivarSerializadorGCFreeC2ME(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoMedio());
		}

		if (!texto.contains("syncPlayerTickets = false")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloDesactivarSyncPlayerTicketsC2ME(),
					MonitorDePID.idioma.descripcionDesactivarSyncPlayerTicketsC2ME(),
					MonitorDePID.idioma.sugerenciaDesactivarSyncPlayerTicketsC2ME(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoMedioAlto());
		}
	}

	private static void revisarTextoMoreCulling(List<MejoraConfig> mejoras) {
		String archivo = "moreculling.toml";
		String texto = leerTexto(archivo);

		if (texto == null)
			return;

		if (!contieneLinea(texto, "leavesCullingMode = \"DEPTH\"")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloUsarCullingHojasDepthMoreCulling(),
					MonitorDePID.idioma.descripcionUsarCullingHojasDepthMoreCulling(),
					MonitorDePID.idioma.sugerenciaUsarCullingHojasDepthMoreCulling(),
					MonitorDePID.idioma.impactoMedio(), MonitorDePID.idioma.riesgoMedio());
		}

		if (!contieneLinea(texto, "endGatewayCulling = true")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarEndGatewayCullingMoreCulling(),
					MonitorDePID.idioma.descripcionActivarEndGatewayCullingMoreCulling(),
					MonitorDePID.idioma.sugerenciaActivarEndGatewayCullingMoreCulling(),
					MonitorDePID.idioma.impactoBajo(), MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarTextoServerCore(List<MejoraConfig> mejoras) {
		String archivo = "servercore/config.yml";
		String texto = leerTexto(archivo);

		if (texto == null)
			return;

		if (!texto.contains("activation-range:") || !texto.contains("enabled: true")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarActivationRangeServerCore(),
					MonitorDePID.idioma.descripcionActivarActivationRangeServerCore(),
					MonitorDePID.idioma.sugerenciaActivarActivationRangeServerCore(), MonitorDePID.idioma.impactoAlto(),
					MonitorDePID.idioma.riesgoMedioAlto());
		}

		if (!texto.contains("use-vertical-range: true")) {
			agregar(mejoras, archivo, MonitorDePID.idioma.tituloActivarRangoVerticalServerCore(),
					MonitorDePID.idioma.descripcionActivarRangoVerticalServerCore(),
					MonitorDePID.idioma.sugerenciaActivarRangoVerticalServerCore(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoMedio());
		}
	}

	private static Nodo leerJson(String archivoRelativo) {
		String texto = leerTexto(archivoRelativo);
		if (texto == null)
			return null;

		try {
			return Json.leer(texto);
		} catch (Throwable t) {
			return null;
		}
	}

	private static Properties leerProperties(String archivoRelativo) {
		Path ruta = CARPETA_CONFIG.resolve(archivoRelativo);

		if (!Files.exists(ruta))
			return null;

		Properties props = new Properties();

		try (Reader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
			props.load(lector);
			return props;
		} catch (IOException e) {
			return null;
		}
	}

	private static String leerTexto(String archivoRelativo) {
		Path ruta = CARPETA_CONFIG.resolve(archivoRelativo);

		if (!Files.exists(ruta))
			return null;

		try {
			return MonitorDePID.leer_archivo(ruta);
		} catch (IOException e) {
			return null;
		}
	}

	private static boolean arregloContieneOpcion(Nodo arreglo, String opcion, boolean valorEsperado) {
		if (arreglo == null || !arreglo.esArreglo())
			return false;

		for (int i = 0; i < arreglo.tamano(); i++) {
			Nodo item = arreglo.en(i);
			if (!item.esObjeto())
				continue;

			String opcionActual = obtenerCadenaSeguro(item, "option", null);
			boolean valorActual = obtenerBooleanoSeguro(item, "value", false);

			if (opcion.equals(opcionActual) && valorActual == valorEsperado)
				return true;
		}

		return false;
	}

	private static String obtenerCadenaSeguro(Nodo objeto, String clave, String defecto) {
		try {
			String valor = objeto.obtener(clave).comoCadena();
			return valor == null ? defecto : valor;
		} catch (Throwable t) {
			return defecto;
		}
	}

	private static int obtenerEnteroSeguro(Nodo objeto, String clave, int defecto) {
		try {
			return objeto.obtener(clave).comoEntero();
		} catch (Throwable t) {
			return defecto;
		}
	}

	private static boolean obtenerBooleanoSeguro(Nodo objeto, String clave, boolean defecto) {
		try {
			return objeto.obtener(clave).comoBooleano();
		} catch (Throwable t) {
			return defecto;
		}
	}

	private static boolean contieneLinea(String texto, String lineaEsperada) {
		String normalizado = texto.replace("\r\n", "\n").replace("\r", "\n");
		String[] lineas = normalizado.split("\n");

		for (String linea : lineas) {
			if (linea.trim().equals(lineaEsperada.trim()))
				return true;
		}

		return false;
	}

	private static void agregar(List<MejoraConfig> mejoras, String archivo, String titulo, String descripcion,
			String sugerencia, String impacto, String riesgo) {
		mejoras.add(new MejoraConfig(archivo, titulo, descripcion, sugerencia, impacto, riesgo));
	}
}