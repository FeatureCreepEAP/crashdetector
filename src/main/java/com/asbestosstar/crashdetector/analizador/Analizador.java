package com.asbestosstar.crashdetector.analizador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.*;
import com.asbestosstar.crashdetector.analizador.firmas.CargadorDeCodice;
import com.asbestosstar.crashdetector.analizador.general.AccesoDenegadoBackupConfig;
import com.asbestosstar.crashdetector.analizador.general.AccesoIlegalMod;
import com.asbestosstar.crashdetector.analizador.general.AdvertenciaFaltasClases;
import com.asbestosstar.crashdetector.analizador.general.AntiManipulacion;
import com.asbestosstar.crashdetector.analizador.general.ClassCastExceptionGeneral;
import com.asbestosstar.crashdetector.analizador.general.ContenidoDeTrazos;
import com.asbestosstar.crashdetector.analizador.general.DifDeMods;
import com.asbestosstar.crashdetector.analizador.general.Drivers;
import com.asbestosstar.crashdetector.analizador.general.ErrorArchivoBloqueadoPorOtroProceso;
import com.asbestosstar.crashdetector.analizador.general.ErrorArchivoUsadoPorOtroProceso;
import com.asbestosstar.crashdetector.analizador.general.ErrorCampoInexistente;
import com.asbestosstar.crashdetector.analizador.general.ErrorCaracteresInvalidosEnNombre;
import com.asbestosstar.crashdetector.analizador.general.ErrorClaseFinalExtendida;
import com.asbestosstar.crashdetector.analizador.general.ErrorContextoOpenGL;
import com.asbestosstar.crashdetector.analizador.general.ErrorDeEnlaceInsatisfecho;
import com.asbestosstar.crashdetector.analizador.general.ErrorDeMonitorLWJGL;
import com.asbestosstar.crashdetector.analizador.general.ErrorEntrypointFabric;
import com.asbestosstar.crashdetector.analizador.general.ErrorJarCorruptoConNombre;
import com.asbestosstar.crashdetector.analizador.general.ErrorMetodoAbstractoNoImplementado;
import com.asbestosstar.crashdetector.analizador.general.ErrorMetodoFinalSobrescrito;
import com.asbestosstar.crashdetector.analizador.general.ErrorMetodoInexistente;
import com.asbestosstar.crashdetector.analizador.general.ErrorOpenGLMemoriaInsuficiente;
import com.asbestosstar.crashdetector.analizador.general.ErrorStackSmashingDetected;
import com.asbestosstar.crashdetector.analizador.general.ErrorVerificacionBytecode;
import com.asbestosstar.crashdetector.analizador.general.ErrorVersionInvalidaModMaven;
import com.asbestosstar.crashdetector.analizador.general.FallosEjecucionTareas;
import com.asbestosstar.crashdetector.analizador.general.FaltaModAnimado;
import com.asbestosstar.crashdetector.analizador.general.FaltaModuleJPMS;
import com.asbestosstar.crashdetector.analizador.general.FaltasClases;
import com.asbestosstar.crashdetector.analizador.general.JPMSIllegalAccess;
import com.asbestosstar.crashdetector.analizador.general.JavaVersiones;
import com.asbestosstar.crashdetector.analizador.general.LanzerDesAnimado;
import com.asbestosstar.crashdetector.analizador.general.LanzerNoAnimado;
import com.asbestosstar.crashdetector.analizador.general.ModIncompatibleConCargadorActivo;
import com.asbestosstar.crashdetector.analizador.general.ModulesDuplicadosJavaModulePlatform;
import com.asbestosstar.crashdetector.analizador.general.NoTieneMemoria;
import com.asbestosstar.crashdetector.analizador.general.NullPointer;
import com.asbestosstar.crashdetector.analizador.general.OpcionesJavaGCInvalidas;
import com.asbestosstar.crashdetector.analizador.general.PreferIPV4Trace;
import com.asbestosstar.crashdetector.analizador.general.ProblemaSafeFetch32JDK17;
import com.asbestosstar.crashdetector.analizador.general.RaptorLakeInestable;
import com.asbestosstar.crashdetector.analizador.general.RutaCaracteresInvalidos;
import com.asbestosstar.crashdetector.analizador.general.SpongeMixinClaseMalUbicada;
import com.asbestosstar.crashdetector.analizador.general.SpongeMixinConfigsProblematicos;
import com.asbestosstar.crashdetector.analizador.general.TienesModDesAnimado;
import com.asbestosstar.crashdetector.analizador.general.VerificacionGPU;
import com.asbestosstar.crashdetector.analizador.general.VersionInvalidaSemver;
import com.asbestosstar.crashdetector.analizador.rapido.AnalizadorNuevo;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.config.ConfigStringArray;

public class Analizador {

	/*
	 * Registro de TODAS las verificaciones disponibles.
	 */
	public static HashSet<VerificacionesLegacy> verificaciones = new HashSet<>();

	/**
	 * Conjuntos separados de verificaciones activadas: - normales: se ejecutan
	 * primero en paralelo (multinúcleo) - tardías: se ejecutan después, en un solo
	 * hilo
	 */
	public HashSet<VerificacionesLegacy> verificaciones_normales_activadas = new LinkedHashSet<>();
	public HashSet<VerificacionesLegacy> verificaciones_tardias_activadas = new LinkedHashSet<>();

	/**
	 * Conservamos este conjunto como unión de ambas para compatibilidad con
	 * toString() y obtenerSoluciones().
	 */
	public HashSet<VerificacionesLegacy> verificaciones_activados = new LinkedHashSet<>();

	/**
	 * Clave de configuración para la lista de denegación de verificaciones.
	 */
	private static final String CONFIG_CLAVE_LISTA_DENEGACION = "verificaciones.lista_de_denegacion";

	/**
	 * Elemento de configuración que maneja la lista de denegación (por defecto
	 * vacío).
	 */
	public static final ConfigStringArray CONFIG_LISTA_DENEGACION = ConfigStringArray.de(CONFIG_CLAVE_LISTA_DENEGACION,
			new ArrayList<>());

	/**
	 * Repositorios en memoria para declarar verificaciones "tardías" SOLO vía
	 * código. No hay configuración externa; otros módulos deben llamar a los
	 * métodos registrar*.
	 */
	private static final Set<Class<? extends VerificacionesLegacy>> CLASES_TARDIAS_REGISTRADAS = Collections
			.newSetFromMap(new ConcurrentHashMap<>());
	private static final Set<String> IDS_TARDIAS_REGISTRADAS = Collections.newSetFromMap(new ConcurrentHashMap<>());

	static {
		// Registrar por defecto las que deben ser tardías usando la API en-código:
		registrarVerificacionTardia(ContenidoDeTrazos.class);
		registrarVerificacionTardia(AdvertenciaFaltasClases.class);

		CrashDetectorLogger.log(Criticalidad.ADVERTENCIA.toString() + " buscando para advertencias ");

		verificaciones.add(new SpongeMixinConfigsProblematicos());
		verificaciones.add(new NoSuchElementAnimacionMinecraft());
		verificaciones.add(new PreferIPV4Trace());
		verificaciones.add(new RegistrosMalMapeados());
		verificaciones.add(new ModulesDuplicadosJavaModulePlatform());
		verificaciones.add(new FaltaModuleJPMS());
		verificaciones.add(new ModsDuplicadosModLauncher());
		verificaciones.add(new LenguajeProveedorCheck());
		verificaciones.add(new WaterMediaTL());
		verificaciones.add(new WaterMediaVLC());

		verificaciones.add(new NoPuedeAnalizarJSONDeRegistro());
		verificaciones.add(new BloqueTeselado());
		verificaciones.add(new OptifineObsoleta());
		verificaciones.add(new ServicioDeModLauncherNoFunciona());
		verificaciones.add(new FabricMCRuntimeErrorProvidedBy());
		verificaciones.add(new MCForgeModsSuspechoso());
		verificaciones.add(new JavaVersiones());
		verificaciones.add(new FaltasClases()); // regla distinta a la "advertencia"
		verificaciones.add(new Drivers());
		verificaciones.add(new ErrorDeMonitorLWJGL());
		verificaciones.add(new OpcionesJavaGCInvalidas());
		verificaciones.add(new ErrorConfiguracionMCForge());
		verificaciones.add(new EarlyWindow());
		verificaciones.add(new NecesitasSodium());
		verificaciones.add(new FaltasDependenciasModLaunche());
		verificaciones.add(new KubeJSResourcePack());
		verificaciones.add(new Segundo60Tick());
		verificaciones.add(new NoTieneMemoria());
		verificaciones.add(new Theseus());
		verificaciones.add(new CursedConsola());
		verificaciones.add(new NullPointer());
		verificaciones.add(new ContenidoDeTrazos());
		verificaciones.add(new AuditorTransformer());
		verificaciones.add(new AdvertenciaFaltasClases());
		verificaciones.add(new MalwareFalsoCrashAssistant());
		verificaciones.add(new LegacyRandomSourceMultiHilos());

		// De Codex Aternos
		verificaciones.add(new ProblemaCargaChunk());
		verificaciones.add(new ProblemaCierreAuthMe());
		verificaciones.add(new ProblemaCargaMultiverso());
		verificaciones.add(new ProblemaConfiguracionPermissionsEx());
		verificaciones.add(new ProblemaDependenciaPlugin());
		verificaciones.add(new ProblemaDependenciaPluginPocketMine());
		verificaciones.add(new ProblemaDependenciaPTRLib());
		verificaciones.add(new ProblemaEjecucionPlugin());
		verificaciones.add(new ProblemaExcepcionComandoPlugin());
		verificaciones.add(new ProblemaSpongeMixinFabric());
		verificaciones.add(new ProblemaModDuplicadoFabric());
		verificaciones.add(new ProblemaModFaltanteEnDatapack());
		verificaciones.add(new ProblemaModFaltanteEnMundo());
		verificaciones.add(new ProblemaModIncompatibleConMinecraft());
		verificaciones.add(new ProblemaModIncompatibleFabric());
		verificaciones.add(new ProblemaMundoDuplicado());
		verificaciones.add(new ProblemaNombrePluginAmbiguo());
		verificaciones.add(new ProblemaTickingEntidadBloque());
		verificaciones.add(new ProblemaTickingRegionalPlugin());
		verificaciones.add(new ProblemaVersionAPIIncompatible());
		verificaciones.add(new ProblemaVersionDowngrade());
		verificaciones.add(new ProblemaVersionModMundo());
		verificaciones.add(new ProblemaDependenciaModFabric());
		// Fin de Codex Aternos

		verificaciones.add(new MCForgeInstallacionNoEstaCompleta());
		verificaciones.add(new ErrorDeEnlaceInsatisfecho());
		verificaciones.add(new ConflictoDeIDsMinecraft());
		verificaciones.add(new ErrorCaracteresInvalidosEnNombre());
		verificaciones.add(new ErrorDependenciaModFaltante());
		verificaciones.add(new ErrorAccessTransformerInvalido());
		verificaciones.add(new ErrorDiscrepanciaModID());
		verificaciones.add(new ErrorCargaClaseEntornoInvalido());
		verificaciones.add(new ErrorMetadataModsTomlFaltante());
		verificaciones.add(new ErrorSistemaSonido());
		verificaciones.add(new ErrorSinListenersEnClase());
		verificaciones.add(new ErrorUnionFileSystemCorrupto());
		verificaciones.add(new ErrorRegistroSuscriptoresAutomaticos());
		verificaciones.add(new AzureGeckoLibInicializoPronto());
		verificaciones.add(new SCOErrorCompatibilidadC2ME());
		verificaciones.add(new ErrorJEIPluginFallido());
		verificaciones.add(new LexForgeMLTransformerEnNeoForge());
		verificaciones.add(new WaterMediaXenonIncompatible());
		verificaciones.add(new TaczDeflaterCerrado());
		verificaciones.add(new FuncionesDeDensidadNoVinculadas());
		verificaciones.add(new RailwaysCreate6Alfa());
		verificaciones.add(new ConflictoMultiworldRendimiento());
		verificaciones.add(new ErrorContextoOpenGL());
		verificaciones.add(new ErrorResolucionDeTextura());
		verificaciones.add(new ErrorRutaModLauncher());
		verificaciones.add(new ErrorConfiguracionServicioIDependencyLocator());
		verificaciones.add(new ErrorCampoInexistente());
		verificaciones.add(new ErrorMetodoInexistente());
		verificaciones.add(new ErrorHealightINT());
		verificaciones.add(new ErrorMetodoAbstractoNoImplementado());
		verificaciones.add(new ErrorMetadataAnimacionEnServidor());
		verificaciones.add(new ErrorConfiguracionConnectorCorrupta());
		verificaciones.add(new ErrorJarCorruptoConNombre());
		verificaciones.add(new ErrorCargaNBTMundoCorrupto());
		verificaciones.add(new ErrorArchivoBloqueadoPorOtroProceso());
		verificaciones.add(new ErrorClaseFinalExtendida());
		verificaciones.add(new ErrorRubidiumObsoletoConIris());
		verificaciones.add(new ErrorVoiceChatPuertoOcupado());
		verificaciones.add(new ErrorBlockItemNuloCreate());
		verificaciones.add(new ModIncompatibleConCargadorActivo());
		verificaciones.add(new ErrorCreacionModeloFallida());
		verificaciones.add(new ConflictoMoonlightIceberg());

		// Plan de 5 Semanas

		verificaciones.add(new FallosEjecucionTareas());
		verificaciones.add(new ConflictoOptiFineEMF());
		verificaciones.add(new ConflictoOptiFineFusion());
		verificaciones.add(new ConflictoFlywheelRubidium());
		verificaciones.add(new ConflictoOptiFineEpicFight());
		verificaciones.add(new ConflictoOptiFineRubidium());
		verificaciones.add(new ErrorFreeCamServidor());
		verificaciones.add(new ErrorEntityTextureFeaturesServidor());
		verificaciones.add(new ErrorEULANoAceptado());
		verificaciones.add(new ErrorOptiFineServidor());
		verificaciones.add(new ErrorIronSpellbooksVersion());
		verificaciones.add(new ConflictoOptiFineEmbeddium());
		verificaciones.add(new ErrorControllableServidor());
		verificaciones.add(new ErrorSupplementariesCargaServidor());
		verificaciones.add(new ErrorGroovyModloaderModuloFaltante());
		verificaciones.add(new ErrorEveryCompatNombreInvalido());
		verificaciones.add(new ErrorCodigo1073741819());
		verificaciones.add(new ErrorImmersiveTooltipsSinDependencia());
		verificaciones.add(new ErrorMedievalOriginsCast());
		verificaciones.add(new ErrorReignOfNetherMusicManager());
		verificaciones.add(new ErrorYesSteveModelLinux());
		verificaciones.add(new ConflictoMovingElevatorsOptiFine());
		verificaciones.add(new ConflictoFabricAPIOptiFine());

		verificaciones.add(new ErrorModLauncherTransformationService());
		verificaciones.add(new ErrorVersionInvalidaModMaven());
		verificaciones.add(new ErrorStackSmashingDetected());
		verificaciones.add(new ErrorVersionClaseGregTechEasyCore());
		verificaciones.add(new ConflictoMoniLabsConnectorExtras());
		verificaciones.add(new ErrorCompatibilidadIrisDH());
		verificaciones.add(new UraniumLag());
		verificaciones.add(new ErrorFallingAttackVersion());

		verificaciones.add(new ProblemaSafeFetch32JDK17());
		verificaciones.add(new ProblemaMCEFInicializacion());
		verificaciones.add(new ConflictoIrisOptifine());
		verificaciones.add(new ConflictoModernFixOptifine());
		verificaciones.add(new ErrorClaveRegistroMayusculas());
		verificaciones.add(new ErrorEntrypointFabric());
		verificaciones.add(new ErrorEnGarde());
		verificaciones.add(new ErrorIdleTweaks());

		verificaciones.add(new IndependenteFlywheel());
		verificaciones.add(new FloralEnchantmentsTagKeyNull());
		verificaciones.add(new IrisSombrasTerreno());
		verificaciones.add(new TickLargoServidorMinecraft());

		verificaciones.add(new LuckPermsNoCargado());
		verificaciones.add(new IrisShaderpackNoEncontrado());
		verificaciones.add(new NightConfigNoSePuedeEscribir());
		verificaciones.add(new AccesoDenegadoBackupConfig());
		verificaciones.add(new SimpleCloudsIrisDH());

		verificaciones.add(new RutaCaracteresInvalidos());
		verificaciones.add(new TwilightForestIntelShaders());
		verificaciones.add(new ForgeLanguageProviderNoCarga());
		verificaciones.add(new LetsDoCompatInterceptApply());
		verificaciones.add(new JEIItemGroupCrash());
		verificaciones.add(new VersionInvalidaSemver());
		verificaciones.add(new JPMSIllegalAccess());
		verificaciones.add(new SpongeMixinClaseMalUbicada());
		verificaciones.add(new VulkanModGPUIncompatible());
		verificaciones.add(new RenderOutlineRendertypeInvalidoBetterEnchants());
		verificaciones.add(new DivineRPGDimensionalInventoryNPE());
		verificaciones.add(new RenderPassNoCerrado());
		verificaciones.add(new ProblemaFeatherClient());
		verificaciones.add(new ConflictoIrisFlywheelCreate());
		verificaciones.add(new ModeloGeckoLibNoEncontrado());
		verificaciones.add(new ProblemaAnimacionCobblemon());
		verificaciones.add(new ProblemaLunarClient());
		verificaciones.add(new AccesoIlegalMod());
		verificaciones.add(new ErrorParseoDataPack());
		verificaciones.add(new ErrorCompilacionShader());

		verificaciones.add(new ErrorCreacionModelo());
		verificaciones.add(new ProblemaBlockStarlightEngine());
		verificaciones.add(new ProblemaAAAParticlesEffekseer());
		verificaciones.add(new ProblemaParanoiaC2ME());
		verificaciones.add(new ProblemaAssetsDirFaltante());
		verificaciones.add(new ProblemaCupboardVersion());
		verificaciones.add(new AnimacionGeckoLibNoEncontrada());
		verificaciones.add(new AnimacionGeckoLibInExiste());
		verificaciones.add(new RegistroDuplicadoObjeto());
		verificaciones.add(new FalloFabricRenderingAPI());
		verificaciones.add(new RestriccionesDependenciaNoCumplidas());
		verificaciones.add(new NeruinaOcultaAdvertencia());
		verificaciones.add(new ApothicAttributeSinDueno());
		verificaciones.add(new ErrorPotBlockEntity());
		verificaciones.add(new ErrorPreloadingTricks());
		verificaciones.add(new ErrorSimpleRadioLexiconfig());
		verificaciones.add(new ErrorMobAITweaks());
		verificaciones.add(new VerificacionGPU());
		verificaciones.add(new RaptorLakeInestable());// TODO Spectre y Meltdown
		verificaciones.add(new ProblemaAzureLibAnimaciones());
		verificaciones.add(new ProblemaDecocraftNatureEssentialPartnerMod());
		verificaciones.add(new TetraDeserializadorModeloEstatico());
		verificaciones.add(new SimpleEmotesSetupAnimTail());
		verificaciones.add(new SKLauncherAdvertencia());
		verificaciones.add(new OculusIrisUnknownShaderVariable());
		verificaciones.add(new ItemNoExiste());
		verificaciones.add(new CobblemonPinkanIslandsRhyhornModelo());
		verificaciones.add(new ColdSweatInitDynamicTags());
		verificaciones.add(new ClassCastExceptionGeneral());
		verificaciones.add(new ValkyrienSkiesTournamentLithiumPoiInjection());
		verificaciones.add(new VSTournamentVSConfigClassNoExiste());
		verificaciones.add(new ControlifyRemoveReloadingScreen());
		verificaciones.add(new BiomesOPlentyFogDataCustomLiquids());
		verificaciones.add(new KotlinReflectionInternalErrorVersion());
		verificaciones.add(new MotionBlurBufferCerrado());
		verificaciones.add(new GeneratorAcceleratorOwoVersion());
		verificaciones.add(new FabricRenderingApiFaltaIndium());
		verificaciones.add(new EntradaDuplicadaIdModerno());
		verificaciones.add(new ErrorOpenGLMemoriaInsuficiente());
		verificaciones.add(new ErrorVerificacionBytecode());
		verificaciones.add(new ErrorMetodoFinalSobrescrito());
		verificaciones.add(new CrashProvocadoPorComando());
		verificaciones.add(new ErrorJvmDllC2Sodium());
		verificaciones.add(new ErrorJvmDllCurseForgeG1());
		verificaciones.add(new ErrorArchivoUsadoPorOtroProceso());
		verificaciones.add(new ErrorBetterEndPaletaChunkAgua());

		verificaciones.add(new PirataMC());
		verificaciones.add(new LanzerNoAnimado());
		verificaciones.add(new LanzerDesAnimado());
		verificaciones.add(new FaltaModAnimado());
		verificaciones.add(new TienesModDesAnimado());
		verificaciones.add(new AntiManipulacion());

		verificaciones.addAll(CargadorDeCodice.cargarVerificaciones());

		CrashDetectorLogger.log("Número de códices " + String.valueOf(CargadorDeCodice.cargarVerificaciones().size()));

		verificaciones.add(new DifDeMods());
	}

	/**
	 * API pública para que terceros registren verificaciones como "tardías" (sólo
	 * código). Se puede registrar por clase o por ID/nombre de clase.
	 */
	public static void registrarVerificacionTardia(Class<? extends VerificacionesLegacy> clase) {
		if (clase != null) {
			CLASES_TARDIAS_REGISTRADAS.add(clase);
		}
	}

	public static void registrarVerificacionesTardias(List<Class<? extends VerificacionesLegacy>> clases) {
		if (clases != null) {
			for (Class<? extends VerificacionesLegacy> c : clases) {
				if (c != null) {
					CLASES_TARDIAS_REGISTRADAS.add(c);
				}
			}
		}
	}

	public static void registrarVerificacionTardiaPorId(String idOClase) {
		if (idOClase != null && !idOClase.isEmpty()) {
			IDS_TARDIAS_REGISTRADAS.add(idOClase);
		}
	}

	public Analizador() {
		// 1) cargar lista de denegación desde config
		List<String> denegadas = CONFIG_LISTA_DENEGACION.obtener();

		// 2) construir conjuntos activados clasificando por "tardía" o "normal"
		for (VerificacionesLegacy ver : verificaciones) {
			String id = ver.id();
			if (denegadas.contains(id)) {
				CrashDetectorLogger.log("Verificación '" + id + "' está en la lista de denegación; se desactiva.");
				continue;
			}
			VerificacionesLegacy instancia = ver.nueva();

			// Criterios de "tardía": clase registrada o ID/nombre registrado por código
			boolean esTardia = CLASES_TARDIAS_REGISTRADAS.contains(instancia.getClass())
					|| IDS_TARDIAS_REGISTRADAS.contains(id)
					|| IDS_TARDIAS_REGISTRADAS.contains(instancia.getClass().getName())
					|| IDS_TARDIAS_REGISTRADAS.contains(instancia.getClass().getSimpleName());

			if (esTardia) {
				verificaciones_tardias_activadas.add(instancia);
			} else {
				verificaciones_normales_activadas.add(instancia);
			}
		}

		// Unión para mantener compatibilidad con APIs existentes de esta clase
		verificaciones_activados.addAll(verificaciones_normales_activadas);
		verificaciones_activados.addAll(verificaciones_tardias_activadas);
	}

	public void analizar(List<Consola> consolas, Set<VerificacionesLegacy> todasLasVerificaciones) {
		Buscador.cargar();

		// Reiniciar estados globales de deduplicación antes de comenzar
		FaltasClases.reiniciarGlobal();
		NullPointer.reiniciarGlobal();
		ClassCastExceptionGeneral.reiniciarGlobal();
		ContenidoDeTrazos.reiniciarGlobal();
		Drivers.reiniciarGlobal();

		// Debug logging to verify analyze is called
		CrashDetectorLogger.log("[DEBUG_LOG] Analizador.analizar() called with " + consolas.size() + " consolas");

		// Delegamos el análisis a AnalizadorNuevo que maneja el motor de streaming
		// y la compatibilidad con verificaciones legacy.
		// AnalizadorNuevo ya se encarga del log de inicio.
		AnalizadorNuevo nuevo = new AnalizadorNuevo(this);

		// Pasamos TODAS las verificaciones disponibles para que el analizador nuevo las
		// gestione.
		// El método obtenerActivados() puede devolver una lista vacía si ninguna ha
		// sido activada aún.
		// En AnalizadorNuevo.analizar() se clasificarán en rápidas o legacy.
		nuevo.analizar(consolas, todasLasVerificaciones);

		// Verificamos si se encontraron resultados
		int totalErrores = 0;
		for (Consola c : consolas) {
			totalErrores += c.errores_de_lectadores.size();
		}
		CrashDetectorLogger.log("[DEBUG_LOG] Análisis finalizado. Total errores encontrados: " + totalErrores);
	}

	public void analizar(List<Consola> consolas) {
		analizar(consolas, obtenerVerificacionesUnion());
	}

	public Set<VerificacionesLegacy> organizar(Set<VerificacionesLegacy> vers) {
		List<VerificacionesLegacy> ret = new ArrayList<>(vers);
		ret.sort((v1, v2) -> Float.compare(v2.prioridad(), v1.prioridad()));
		return new LinkedHashSet<>(ret);
	}

	/**
	 * Todos los verificaciones en toString y obtenerSoluciones
	 * 
	 * @return
	 */
	public Set<VerificacionesLegacy> obtenerVerificacionesUnion() {
		// Unir ambas listas y ordenar por prioridad
		HashSet<VerificacionesLegacy> union = new LinkedHashSet<>();
		union.addAll(this.verificaciones_normales_activadas);
		union.addAll(this.verificaciones_tardias_activadas);
		return organizar(union);
	}

	/**
	 * Todos los activados
	 * 
	 * @return
	 */
	public Set<VerificacionesLegacy> obtenerActivados() {
		HashSet<VerificacionesLegacy> act = new LinkedHashSet<>();
		for (VerificacionesLegacy ver : obtenerVerificacionesUnion()) {
			if (ver.activado()) {
				act.add(ver);
			}
		}
		return act;
	}

	@Override
	public String toString() {
		StringBuilder constructor = new StringBuilder();
		constructor.append("<ol>");

		for (VerificacionesLegacy ver : obtenerActivados()) {
			String str = ver.comoString();
			if (!str.isEmpty()) {
				constructor.append("<li>").append(str)

						.append("</li>");

				CrashDetectorLogger.log("razon " + ver.mensaje());
			} else {
				CrashDetectorLogger.log("razon es blanco " + ver.nombre());
			}
		}

		constructor.append("</ol>");
		return constructor.toString();
	}

	public List<QuickFix> obtenerSoluciones() {
		List<QuickFix> soluciones = new ArrayList<>();

		for (VerificacionesLegacy ver : obtenerActivados()) {

			if (ver.solucion() != null && !ver.solucion().equals(QuickFix.NINGUN)) {
				soluciones.add(ver.solucion());
			}
		}

		return soluciones;
	}

	/**
	 * Obtener una solucion con enlace
	 * 
	 * @return
	 */
	public @Nullable QuickFix obtenerQuickFixConEnlace(String enlace) {
		for (QuickFix sol : obtenerSoluciones()) {
			if (sol.obtenerEnlace().equals(enlace)) {
				return sol;
			}
		}
		return null;
	}

}
