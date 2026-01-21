package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorMinecraftServer implements DetectorLanzer {
	@Override
	public String id() {
		return "minecraft_server";
	}

	@Override
	public boolean animado() {
		return true;
	} // asumo recomendado

	@Override
	public boolean desanimado() {
		return false;
	}

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT))
			return false;

		boolean tieneServer = false;
		boolean tieneClient = false;
//Necesito un otra solucion

		tieneServer = buscarClase("net.minecraft.server.MinecraftServer");

		tieneClient = buscarClase("net.minecraft.client.main.Main");

		return tieneServer && !tieneClient;
	}
}