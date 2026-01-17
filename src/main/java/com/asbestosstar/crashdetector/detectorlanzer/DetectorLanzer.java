package com.asbestosstar.crashdetector.detectorlanzer;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.App;

public interface DetectorLanzer {

	public static final List<DetectorLanzer> DETECTORES_DE_LANZERES = new ArrayList<>();
	public static final DetectorLanzer OTRA = new LanzerOtra();

	public static void registrarLanzeresDefectos() {
		// Recomendados (animado = true)
		DETECTORES_DE_LANZERES.add(new DetectorTLMods());
		DETECTORES_DE_LANZERES.add(new DetectorTLLegacyFlatpak());
		DETECTORES_DE_LANZERES.add(new DetectorTLLegacy());
		DETECTORES_DE_LANZERES.add(new DetectorTL());
		DETECTORES_DE_LANZERES.add(new DetectorHMCL());
		DETECTORES_DE_LANZERES.add(new DetectorPrismLauncher());
		DETECTORES_DE_LANZERES.add(new DetectorATLauncher());
		DETECTORES_DE_LANZERES.add(new DetectorGDL());
		DETECTORES_DE_LANZERES.add(new DetectorCurseForgeApp());
		DETECTORES_DE_LANZERES.add(new DetectorCurseMojang());
		DETECTORES_DE_LANZERES.add(new DetectorKLauncher());
		DETECTORES_DE_LANZERES.add(new DetectorPCLCE());
		DETECTORES_DE_LANZERES.add(new DetectorPCL());
		DETECTORES_DE_LANZERES.add(new DetectorNightWorld());
		DETECTORES_DE_LANZERES.add(new DetectorFTBApp());
		DETECTORES_DE_LANZERES.add(new DetectorXMLC());
		DETECTORES_DE_LANZERES.add(new DetectorHeliosLauncher());
		DETECTORES_DE_LANZERES.add(new DetectorMinecraftServer());

		DETECTORES_DE_LANZERES.add(new DetectorSKLauncher());
		DETECTORES_DE_LANZERES.add(new DetectorFeather());
		DETECTORES_DE_LANZERES.add(new DetectorLunar());
		DETECTORES_DE_LANZERES.add(new DetectorLabyMod());
		DETECTORES_DE_LANZERES.add(new DetectorNoRiskClient());
		DETECTORES_DE_LANZERES.add(new DetectorTheseus());
		DETECTORES_DE_LANZERES.add(new DetectorCrystalLauncher());
		DETECTORES_DE_LANZERES.add(new DetectorMultiMC());
		DETECTORES_DE_LANZERES.add(new DetectorPolyMC());
		DETECTORES_DE_LANZERES.add(new DetectorLanzerCreativeMode());
		DETECTORES_DE_LANZERES.add(new DetectorMinecraftLauncher());
		DETECTORES_DE_LANZERES.add(new DetectorBattly());
		DETECTORES_DE_LANZERES.add(new DetectorLauncherFenix());

	}

	public static String detectarLanzer(App app, String cmd) {
		if (!app.equals(App.MINECRAFT)) {
			return OTRA.id();
		}
		for (DetectorLanzer detector : DETECTORES_DE_LANZERES) {
			if (detector.detectar(app, cmd)) {
				return detector.id();
			}
		}
		return OTRA.id();
	}

	public String id();

	public boolean animado();

	public boolean desanimado();

	public boolean detectar(App app, String cmd);
}