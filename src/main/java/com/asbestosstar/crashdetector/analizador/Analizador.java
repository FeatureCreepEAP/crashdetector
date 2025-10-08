package com.asbestosstar.crashdetector.analizador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.AuditorTransformer;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.AzureGeckoLibInicializoPronto;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.BloqueTeselado;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ConflictoDeIDsMinecraft;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ConflictoMultiworldRendimiento;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.CursedConsola;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.EarlyWindow;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorAccessTransformerInvalido;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorCargaClaseEntornoInvalido;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorConfiguracionMCForge;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorDependenciaModFaltante;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorDiscrepanciaModID;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorJEIPluginFallido;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorMetadataModsTomlFaltante;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorRegistroSuscriptoresAutomaticos;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorResolucionDeTextura;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorRutaModLauncher;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorSinListenersEnClase;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorSistemaSonido;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorUnionFileSystemCorrupto;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.FabricMCRuntimeErrorProvidedBy;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.FaltasDependenciasModLaunche;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.FuncionesDeDensidadNoVinculadas;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.KubeJSResourcePack;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.LegacyRandomSourceMultiHilos;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.LenguajeProveedorCheck;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.LexForgeMLTransformerEnNeoForge;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.MCForgeInstallacionNoEstaCompleta;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.MCForgeModsSuspechoso;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ModsDuplicadosModLauncher;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.NecesitasSodium;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.NoPuedeAnalizarJSONDeRegistro;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.NoSuchElementAnimacionMinecraft;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.OptifineObsoleta;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaCargaChunk;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaCargaMultiverso;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaCierreAuthMe;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaConfiguracionPermissionsEx;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaDependenciaModFabric;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaDependenciaPTRLib;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaDependenciaPlugin;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaDependenciaPluginPocketMine;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaEjecucionPlugin;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaExcepcionComandoPlugin;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaModDuplicadoFabric;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaModFaltanteEnDatapack;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaModFaltanteEnMundo;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaModIncompatibleConMinecraft;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaModIncompatibleFabric;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaMundoDuplicado;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaNombrePluginAmbiguo;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaSpongeMixinFabric;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaTickingEntidadBloque;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaTickingRegionalPlugin;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaVersionAPIIncompatible;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaVersionDowngrade;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ProblemaVersionModMundo;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.RailwaysCreate6Alfa;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.RegistrosMalMapeados;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.SCOErrorCompatibilidadC2ME;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.Segundo60Tick;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ServicioDeModLauncherNoFunciona;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.TaczDeflaterCerrado;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.WaterMediaTL;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.WaterMediaVLC;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.WaterMediaXenonIncompatible;
import com.asbestosstar.crashdetector.analizador.general.AdvertenciaFaltasClases;
import com.asbestosstar.crashdetector.analizador.general.ContentoDeTraces;
import com.asbestosstar.crashdetector.analizador.general.DifDeMods;
import com.asbestosstar.crashdetector.analizador.general.Drivers;
import com.asbestosstar.crashdetector.analizador.general.ErrorCaracteresInvalidosEnNombre;
import com.asbestosstar.crashdetector.analizador.general.ErrorContextoOpenGL;
import com.asbestosstar.crashdetector.analizador.general.ErrorDeEnlaceInsatisfecho;
import com.asbestosstar.crashdetector.analizador.general.ErrorDeMonitorLWJGL;
import com.asbestosstar.crashdetector.analizador.general.FaltaModuleJPMS;
import com.asbestosstar.crashdetector.analizador.general.FaltasClases;
import com.asbestosstar.crashdetector.analizador.general.JavaVersiones;
import com.asbestosstar.crashdetector.analizador.general.ModulesDuplicadosJavaModulePlatform;
import com.asbestosstar.crashdetector.analizador.general.NoTieneMemoria;
import com.asbestosstar.crashdetector.analizador.general.NullPointer;
import com.asbestosstar.crashdetector.analizador.general.OpcionesJavaGCInvalidas;
import com.asbestosstar.crashdetector.analizador.general.PreferIPV4Trace;
import com.asbestosstar.crashdetector.analizador.general.SpongeMixinConfigsProblematicos;

public class Analizador {

	/*
	 * Para Registrar
	 */
	public static HashSet<Verificaciones> verificaciones = new HashSet<Verificaciones>();

	/**
	 * para analizando
	 */
	public HashSet<Verificaciones> verificaciones_activados = new LinkedHashSet<Verificaciones>();

	static {
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

		// verificaciones.add(verificacion_de_stacktrace); No necesitemos aqui, es en la
		// clase Consola antes de esta contructor // Para Configs de SpongeMixin
		// problematicos
		verificaciones.add(new OptifineObsoleta());
		verificaciones.add(new ServicioDeModLauncherNoFunciona());
		verificaciones.add(new FabricMCRuntimeErrorProvidedBy());
		verificaciones.add(new MCForgeModsSuspechoso());
		verificaciones.add(new JavaVersiones());
		verificaciones.add(new FaltasClases());
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
		// verificaciones.add(new Theseus());
		verificaciones.add(new CursedConsola());
		verificaciones.add(new NullPointer());
		verificaciones.add(new ContentoDeTraces());

		verificaciones.add(new AuditorTransformer());

		verificaciones.add(new AdvertenciaFaltasClases());
		// verificaciones.add(new MalwareFalsoCrashAssistant());

		// verificaciones.add(new ObjetoDeRegistroNoPresente());
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
		// verificaciones.add(new ProblemaExcepcionMod());
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

		verificaciones.add(new DifDeMods());

		// TODO
		// https://discord.com/channels/1129059589325852724/1129069799545241703/1418708211636113498

	}

	public Analizador() {
		for (Verificaciones ver : verificaciones) {
			verificaciones_activados.add(ver.nueva());
		}
	}

	public void analizar(List<Consola> consolas) {
		long totalStartTime = System.nanoTime();
		CrashDetectorLogger.log("Iniciando análisis de " + consolas.size() + " registros");

		for (Consola consola : consolas) {
			CrashDetectorLogger.log("comenz analiz");
			consola.verificacion_de_stacktrace.reiniciar();
			CrashDetectorLogger.log("reinciar vdst");

			long consolaStartTime = System.nanoTime();
			CrashDetectorLogger.log("Analizando registro: " + consola.archivo.getFileName());


			final String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

			for (Verificaciones ver : verificaciones_activados) {
				long t0 = System.nanoTime();
				try {
					CrashDetectorLogger.log(consola.archivo + " " + ver.nombre());
					ver.verificar(consola);
					long t1 = System.nanoTime();
					CrashDetectorLogger.log(String.format("Pase global completado: %s - Tiempo: %.2f ms", ver.nombre(),
							(t1 - t0) / 1_000_000.0));
				} catch (Exception e) {
					CrashDetectorLogger.logException(e);
				}
			}


			for (int i = 0; i < lineas.length; i++) {
				final String line = lineas[i];
				for (Verificaciones ver : verificaciones_activados) {
					try {
						ver.verificar(consola, line, i);
					} catch (Exception e) {
						CrashDetectorLogger.logException(e);
					}
				}
			}

			// 3) MÉTRICAS POR CONSOLA
			long consolaEndTime = System.nanoTime();
			double tiempoConsola = (consolaEndTime - consolaStartTime) / 1_000_000.0;
			CrashDetectorLogger.log(String.format("Análisis del registro %s completado en: %.2f ms",
					consola.archivo.getFileName(), tiempoConsola));
		}

		long totalEndTime = System.nanoTime();
		double tiempoTotal = (totalEndTime - totalStartTime) / 1_000_000.0;
		CrashDetectorLogger
				.log(String.format("Análisis completado para %d registros en: %.2f ms", consolas.size(), tiempoTotal));
	}

	public Set<Verificaciones> organizar(Set<Verificaciones> vers) {
		List<Verificaciones> ret = new ArrayList<>(vers);
		ret.sort((v1, v2) -> Float.compare(v2.prioridad(), v1.prioridad()));
		return new LinkedHashSet<>(ret);
	}

	public String toString() {
		StringBuilder constructor = new StringBuilder();
		constructor.append("<ol>");

		for (Verificaciones ver : organizar(this.verificaciones_activados)) {
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
		List<QuickFix> soluciones = new ArrayList<QuickFix>();
		for (Verificaciones ver : organizar(this.verificaciones_activados)) {
			if (ver.activado()) {
				soluciones.add(ver.solucion());
			}

		}
		return soluciones;
	}

}
