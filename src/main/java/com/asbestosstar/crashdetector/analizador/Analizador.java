package com.asbestosstar.crashdetector.analizador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.*;
import com.asbestosstar.crashdetector.analizador.firmas.CargadorDeCodice;
import com.asbestosstar.crashdetector.analizador.general.AdvertenciaFaltasClases;
import com.asbestosstar.crashdetector.analizador.general.AntiManipulacion;
import com.asbestosstar.crashdetector.analizador.general.ContenidoDeTrazos;
import com.asbestosstar.crashdetector.analizador.general.DifDeMods;
import com.asbestosstar.crashdetector.analizador.general.Drivers;
import com.asbestosstar.crashdetector.analizador.general.ErrorArchivoBloqueadoPorOtroProceso;
import com.asbestosstar.crashdetector.analizador.general.ErrorCampoInexistente;
import com.asbestosstar.crashdetector.analizador.general.ErrorCaracteresInvalidosEnNombre;
import com.asbestosstar.crashdetector.analizador.general.ErrorClaseFinalExtendida;
import com.asbestosstar.crashdetector.analizador.general.ErrorContextoOpenGL;
import com.asbestosstar.crashdetector.analizador.general.ErrorDeEnlaceInsatisfecho;
import com.asbestosstar.crashdetector.analizador.general.ErrorDeMonitorLWJGL;
import com.asbestosstar.crashdetector.analizador.general.ErrorEntrypointFabric;
import com.asbestosstar.crashdetector.analizador.general.ErrorJarCorruptoConNombre;
import com.asbestosstar.crashdetector.analizador.general.ErrorMetodoAbstractoNoImplementado;
import com.asbestosstar.crashdetector.analizador.general.ErrorMetodoInexistente;
import com.asbestosstar.crashdetector.analizador.general.ErrorStackSmashingDetected;
import com.asbestosstar.crashdetector.analizador.general.ErrorVersionInvalidaModMaven;
import com.asbestosstar.crashdetector.analizador.general.FallosEjecucionTareas;
import com.asbestosstar.crashdetector.analizador.general.FaltaModAnimado;
import com.asbestosstar.crashdetector.analizador.general.FaltaModuleJPMS;
import com.asbestosstar.crashdetector.analizador.general.FaltasClases;
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
import com.asbestosstar.crashdetector.analizador.general.SpongeMixinConfigsProblematicos;
import com.asbestosstar.crashdetector.analizador.general.TienesModDesAnimado;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.config.ConfigStringArray;

public class Analizador {

	/*
	 * Registro de TODAS las verificaciones disponibles.
	 */
	public static HashSet<Verificaciones> verificaciones = new HashSet<>();

	/**
	 * Conjuntos separados de verificaciones activadas: - normales: se ejecutan
	 * primero en paralelo (multinúcleo) - tardías: se ejecutan después, en un solo
	 * hilo
	 */
	public HashSet<Verificaciones> verificaciones_normales_activadas = new LinkedHashSet<>();
	public HashSet<Verificaciones> verificaciones_tardias_activadas = new LinkedHashSet<>();

	/**
	 * Conservamos este conjunto como unión de ambas para compatibilidad con
	 * toString() y obtenerSoluciones().
	 */
	public HashSet<Verificaciones> verificaciones_activados = new LinkedHashSet<>();

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
	private static final Set<Class<? extends Verificaciones>> CLASES_TARDIAS_REGISTRADAS = Collections
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
	public static void registrarVerificacionTardia(Class<? extends Verificaciones> clase) {
		if (clase != null) {
			CLASES_TARDIAS_REGISTRADAS.add(clase);
		}
	}

	public static void registrarVerificacionesTardias(List<Class<? extends Verificaciones>> clases) {
		if (clases != null) {
			for (Class<? extends Verificaciones> c : clases) {
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
		for (Verificaciones ver : verificaciones) {
			String id = ver.id();
			if (denegadas.contains(id)) {
				CrashDetectorLogger.log("Verificación '" + id + "' está en la lista de denegación; se desactiva.");
				continue;
			}
			Verificaciones instancia = ver.nueva();

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

	public void analizar(List<Consola> consolas) {
		Buscardor.cargar();
		CrashDetectorLogger.log("Iniciando análisis de " + consolas.size() + " registros");

		// Hilos = 2 * núcleos lógicos
		final int hilos = Math.max(1, 2 * Runtime.getRuntime().availableProcessors());
		CrashDetectorLogger.log("Analizador paralelo (normales) con hilos=" + hilos);

		final ThreadFactory fabrica = r -> {
			Thread t = new Thread(r, "Analizador-Normal-" + r.hashCode());
			t.setDaemon(true);
			return t;
		};
		final ExecutorService pool = Executors.newFixedThreadPool(hilos, fabrica);

		try {
			for (Consola consola : consolas) {
				CrashDetectorLogger.log("comenz analiz");
				consola.verificacion_de_stacktrace.reiniciar();
				CrashDetectorLogger.log("reinciar vdst");

				CrashDetectorLogger.log("Analizando registro: " + consola.archivo.getFileName());
				final String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

				// 1) Pre-pass en paralelo: verificar(consola) para TODAS las verificaciones
				// normales
				List<Callable<Void>> tareasPre = new ArrayList<>(verificaciones_normales_activadas.size());
				for (Verificaciones ver : verificaciones_normales_activadas) {
					tareasPre.add(() -> {
						try {
							CrashDetectorLogger.log(consola.archivo + " " + ver.nombre());
							ver.verificar(consola);
						} catch (Exception e) {
							CrashDetectorLogger.logException(e);
						}
						return null;
					});
				}
				try {
					pool.invokeAll(tareasPre);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					CrashDetectorLogger.logException(ie);
				}

				// 2) PER-LINE multihilo para verificaciones NORMALES (un solo recorrido de
				// líneas total)
				final Verificaciones[] normales = verificaciones_normales_activadas.toArray(new Verificaciones[0]);

				// Particionar líneas en chunks para limitar overhead de tareas
				final int total = lineas.length;
				final int chunks = Math.min(total, Math.max(hilos * 4, 1));
				final int chunkSize = (total + chunks - 1) / chunks;

				List<Callable<Void>> tareasLineas = new ArrayList<>(chunks);
				for (int c = 0; c < chunks; c++) {
					final int start = c * chunkSize;
					final int end = Math.min(total, start + chunkSize);
					if (start >= end)
						break;

					tareasLineas.add(() -> {
						for (int i = start; i < end; i++) {
							final String linea = lineas[i];
							// IMPORTANTE: protegemos cada instancia de Verificaciones para evitar
							// condiciones de carrera.
							// Si ver.verificar(...) es thread-safe, se puede quitar el synchronized para
							// mayor rendimiento.
							for (Verificaciones ver : normales) {
								try {
									synchronized (ver) {
										ver.verificar(consola, linea, i);
									}
								} catch (Exception e) {
									CrashDetectorLogger.logException(e);
								}
							}
						}
						return null;
					});
				}
				try {
					pool.invokeAll(tareasLineas);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					CrashDetectorLogger.logException(ie);
				}

				// 3) Verificaciones TARDÍAS: secuenciales, con su propio recorrido por líneas
				// (sin cambios)
				for (Verificaciones ver : verificaciones_tardias_activadas) {
					try {
						CrashDetectorLogger.log(consola.archivo + " (tardía) " + ver.nombre());
						ver.verificar(consola);
					} catch (Exception e) {
						CrashDetectorLogger.logException(e);
					}
				}
				for (Verificaciones ver : verificaciones_tardias_activadas) {
					for (int i = 0; i < lineas.length; i++) {
						try {
							ver.verificar(consola, lineas[i], i);
						} catch (Exception e) {
							CrashDetectorLogger.logException(e);
						}
					}
				}
			}
		} finally {
			pool.shutdown();
		}
	}

	public Set<Verificaciones> organizar(Set<Verificaciones> vers) {
		List<Verificaciones> ret = new ArrayList<>(vers);
		ret.sort((v1, v2) -> Float.compare(v2.prioridad(), v1.prioridad()));
		return new LinkedHashSet<>(ret);
	}

	@Override
	public String toString() {
		StringBuilder constructor = new StringBuilder();
		constructor.append("<ol>");

		// Unir ambas listas y ordenar por prioridad
		HashSet<Verificaciones> union = new LinkedHashSet<>();
		union.addAll(this.verificaciones_normales_activadas);
		union.addAll(this.verificaciones_tardias_activadas);

		for (Verificaciones ver : organizar(union)) {
			if (ver.activado()) {
				constructor.append("<li>");

				String tituloColor = Config.obtenerInstancia().obtenerColorDeTitulosDeConsolas();
				constructor.append("<span style='color: #").append(tituloColor).append("; font-weight: bold;'>")
						.append(ver.nombre()).append("</span>");
				constructor.append("<br>").append(ver.mensaje()).append("<hr style='border: 0; border-top: 1px solid #")
						.append(tituloColor).append("; margin: 8px 0;' />").append("</li>");

				CrashDetectorLogger.log("razon " + ver.mensaje());
			}
		}

		constructor.append("</ol>");
		return constructor.toString();
	}

	public List<QuickFix> obtenerSoluciones() {
		List<QuickFix> soluciones = new ArrayList<>();

		// Unir ambas listas y ordenar por prioridad
		HashSet<Verificaciones> union = new LinkedHashSet<>();
		union.addAll(this.verificaciones_normales_activadas);
		union.addAll(this.verificaciones_tardias_activadas);

		for (Verificaciones ver : organizar(union)) {
			if (ver.activado()) {

				if (ver.solucion() != null && !ver.solucion().equals(QuickFix.NINGUN)) {
					soluciones.add(ver.solucion());
				}
			}
		}
		return soluciones;
	}
}
