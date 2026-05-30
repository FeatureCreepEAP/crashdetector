package com.asbestosstar.crashdetector.optimizacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;

public class AnalizadorDeModsOptimizacion {

	public static class MejoraMod {
		public final String modId;
		public final String titulo;
		public final String descripcion;
		public final String impacto;
		public final String riesgo;

		public MejoraMod(String modId, String titulo, String descripcion, String impacto, String riesgo) {
			this.modId = modId;
			this.titulo = titulo;
			this.descripcion = descripcion;
			this.impacto = impacto;
			this.riesgo = riesgo;
		}

		@Override
		public String toString() {
			return "[" + impacto + " / " + riesgo + "] " + modId + " - " + titulo;
		}
	}

	public static List<MejoraMod> obtenerMejorasPotenciales() {
		Buscador.cargar();

		Set<String> modIds = obtenerModIdsInstalados();
		List<MejoraMod> mejoras = new ArrayList<>();

		revisarFalta(mejoras, modIds, "badoptimizations", MonitorDePID.idioma.tituloModBadOptimizations(),
				MonitorDePID.idioma.descripcionModBadOptimizations(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoBajo());

		revisarFalta(mejoras, modIds, "betterblockentities", MonitorDePID.idioma.tituloModBBE(),
				MonitorDePID.idioma.descripcionModBBE(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoBajo());

		revisarFalta(mejoras, modIds, "c2me", MonitorDePID.idioma.tituloModC2ME(),
				MonitorDePID.idioma.descripcionModC2ME(), MonitorDePID.idioma.impactoAlto(),
				MonitorDePID.idioma.riesgoMedioAlto());

		revisarFalta(mejoras, modIds, "entityculling", MonitorDePID.idioma.tituloModEntityCulling(),
				MonitorDePID.idioma.descripcionModEntityCulling(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoMedioAlto());

		revisarFalta(mejoras, modIds, "ferritecore", MonitorDePID.idioma.tituloModFerriteCore(),
				MonitorDePID.idioma.descripcionModFerriteCore(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoBajo());

		revisarFalta(mejoras, modIds, "immediatelyfast", MonitorDePID.idioma.tituloModImmediatelyFast(),
				MonitorDePID.idioma.descripcionModImmediatelyFast(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoMedio());

		revisarFalta(mejoras, modIds, "lithium", MonitorDePID.idioma.tituloModLithium(),
				MonitorDePID.idioma.descripcionModLithium(), MonitorDePID.idioma.impactoAlto(),
				MonitorDePID.idioma.riesgoBajo());

		revisarFalta(mejoras, modIds, "modernfix", MonitorDePID.idioma.tituloModModernFix(),
				MonitorDePID.idioma.descripcionModModernFix(), MonitorDePID.idioma.impactoAlto(),
				MonitorDePID.idioma.riesgoMedio());

		revisarFalta(mejoras, modIds, "moreculling", MonitorDePID.idioma.tituloModMoreCulling(),
				MonitorDePID.idioma.descripcionModMoreCulling(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoMedioAlto());

		revisarFalta(mejoras, modIds, "scalablelux", MonitorDePID.idioma.tituloModScalableLux(),
				MonitorDePID.idioma.descripcionModScalableLux(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoMedio());

		revisarFalta(mejoras, modIds, "servercore", MonitorDePID.idioma.tituloModServerCore(),
				MonitorDePID.idioma.descripcionModServerCore(), MonitorDePID.idioma.impactoAlto(),
				MonitorDePID.idioma.riesgoMedioAlto());

		revisarFalta(mejoras, modIds, "sodium", MonitorDePID.idioma.tituloModSodium(),
				MonitorDePID.idioma.descripcionModSodium(), MonitorDePID.idioma.impactoAlto(),
				MonitorDePID.idioma.riesgoBajo());

		revisarFalta(mejoras, modIds, "vmp", MonitorDePID.idioma.tituloModVMP(),
				MonitorDePID.idioma.descripcionModVMP(), MonitorDePID.idioma.impactoMedio(),
				MonitorDePID.idioma.riesgoMedio());

		revisarFalta(mejoras, modIds, "mcmt", MonitorDePID.idioma.tituloModMCMT(),
				MonitorDePID.idioma.descripcionModMCMT(), MonitorDePID.idioma.impactoAlto(),
				MonitorDePID.idioma.riesgoAlto());

		revisarLiability(mejoras, modIds, "uranium", MonitorDePID.idioma.tituloLiabilityUranium(),
				MonitorDePID.idioma.descripcionLiabilityUranium(), MonitorDePID.idioma.impactoNegativoAlto(),
				MonitorDePID.idioma.riesgoAlto());

		return mejoras;
	}

	private static void revisarFalta(List<MejoraMod> mejoras, Set<String> modIds, String modId, String titulo,
			String descripcion, String impacto, String riesgo) {
		if (!modIds.contains(modId.toLowerCase())) {
			mejoras.add(new MejoraMod(modId, titulo, descripcion, impacto, riesgo));
		}
	}

	private static void revisarLiability(List<MejoraMod> mejoras, Set<String> modIds, String modId, String titulo,
			String descripcion, String impacto, String riesgo) {
		if (modIds.contains(modId.toLowerCase())) {
			mejoras.add(new MejoraMod(modId, titulo, descripcion, impacto, riesgo));
		}
	}

	private static Set<String> obtenerModIdsInstalados() {
		Set<String> modIds = new HashSet<>();

		for (ArchivoDeMod mod : Buscador.obtenerTodosLosModsYSubmodsRecursivos()) {
			for (String nombre : mod.nombre()) {
				if (nombre != null && !nombre.trim().isEmpty()) {
					modIds.add(nombre.trim().toLowerCase());
				}
			}
		}

		return modIds;
	}
}